package com.example.javafxinterpretor.controller;

import com.example.javafxinterpretor.MainWindowController;
import com.example.javafxinterpretor.model.InterpretorException;
import com.example.javafxinterpretor.model.ProgramState;
import com.example.javafxinterpretor.model.values.ReferenceValue;
import com.example.javafxinterpretor.model.values.Value;
import com.example.javafxinterpretor.repository.Repository;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Controller {
    Repository repo;

    ExecutorService executor;

    public Controller(Repository repo){
        this.repo = repo;
    }

    private Map<Integer, Value> unsafeGarbageCollector(List<Integer> symbolTableAddress,
                                                       Map<Integer, Value> heapTable){
        return heapTable.entrySet().stream()
                .filter(e->symbolTableAddress.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Map<Integer, Value> safeGarbageCollector(List<Integer> symbolTableAddress,
                                                     List<Integer> heapTableAddress,
                                                     Map<Integer, Value> heapTable){
        return heapTable.entrySet().stream()
                .filter(e->symbolTableAddress.contains(e.getKey()) || heapTableAddress.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private List<ProgramState> removeCompletedPrograms(List<ProgramState> programStateList){
        return programStateList.stream()
                .filter(p->p.isNotCompleted())
                .collect(Collectors.toList());
    }

    private List<Integer> getAddrFromSymbolTable(Collection<Value> symTableValues){
        return symTableValues.stream()
                .filter(v-> v instanceof ReferenceValue)
                .map(v-> {ReferenceValue v1 = (ReferenceValue) v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    private Collection<Value> joinAllSymbolTables(List<ProgramState> programList){
        List<Collection<Value>> list =  programList.stream()
                .map(program->program.getSymbolTable().getMap().values())
                .toList();

       List<Value> baseCollection = new ArrayList<>(list.get(0).stream().toList());
       for (int i = 1; i < list.size(); i++){
           baseCollection.addAll(list.get(i).stream().toList());
       }
       return baseCollection;
    }

    private List<Integer> getAddrFromHeapTable(Collection<Value> heapTableValues){
            return heapTableValues.stream()
                    .filter(v-> v instanceof ReferenceValue)
                    .map(v-> {ReferenceValue v1 = (ReferenceValue) v; return v1.getAddress();})
                    .collect(Collectors.toList());
    }

    public void oneStepForAllPrograms(List<ProgramState> programStateList) throws InterpretorException, InterruptedException {
        programStateList.forEach(program-> {
            try {
                repo.logProgramState(program);
            } catch (InterpretorException e) {
                throw new RuntimeException(e);
            }
        });

        List<Callable<ProgramState>> callList = programStateList.stream()
                .map( (ProgramState p)-> (Callable<ProgramState>)(()-> { return p.oneStep(); }) )
                .collect(Collectors.toList());

        List<ProgramState> newProgramStateList = executor.invokeAll(callList).stream()
                .map(future-> {
                        try {
                            return future.get();
                        }
                        catch(Exception exception){
                            try {
                                throw new InterpretorException(exception.getMessage());
                            } catch (InterpretorException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                )
                .filter(p-> p != null)
                .collect(Collectors.toList());

        programStateList.addAll(newProgramStateList);

        programStateList.forEach(program-> {
            try {
                repo.logProgramState(program);
            } catch (InterpretorException e) {
                throw new RuntimeException(e);
            }
        });
        repo.setProgramList(programStateList);
    }

    public void initExecutor(){
        executor = Executors.newFixedThreadPool(2);
    }

    public void executeOneStep() throws InterpretorException, InterruptedException {
        List<ProgramState> programsList = removeCompletedPrograms(repo.getProgramList());

        if (!programsList.isEmpty()) {
            oneStepForAllPrograms(programsList);

            // conservativeGarbageCollector
            ProgramState baseState = repo.getProgramList().getFirst();
            baseState.getHeapTable().setContent(safeGarbageCollector(getAddrFromSymbolTable(
                            joinAllSymbolTables(programsList)),
                    getAddrFromHeapTable(baseState.getHeapTable().getMap().values()),
                    baseState.getHeapTable().getMap()));

            MainWindowController.lastExecutedState = repo.getProgramList().getFirst();
        }
        else if (!executor.isShutdown())
        {
            executor.shutdown();
        }

        programsList = removeCompletedPrograms(programsList);
        repo.setProgramList(programsList);
    }

    public void allSteps() throws InterpretorException, InterruptedException {
        initExecutor();
        List<ProgramState> programsList = removeCompletedPrograms(repo.getProgramList());

        while (!programsList.isEmpty()){
            oneStepForAllPrograms(programsList);


            // conservativeGarbageCollector
            ProgramState baseState = repo.getProgramList().getFirst();
            baseState.getHeapTable().setContent(safeGarbageCollector(getAddrFromSymbolTable(
                            joinAllSymbolTables(programsList)),
                    getAddrFromHeapTable(baseState.getHeapTable().getMap().values()),
                    baseState.getHeapTable().getMap()));


            programsList = removeCompletedPrograms(programsList);
        }
        executor.shutdownNow();
        repo.setProgramList(programsList);
    }

    public Repository getRepository(){
        return this.repo;
    }
}
