package com.example.javafxinterpretor.repository;

import com.example.javafxinterpretor.model.InterpretorException;
import com.example.javafxinterpretor.model.ProgramState;
import com.example.javafxinterpretor.model.statements.IStatement;
import com.example.javafxinterpretor.model.values.StringValue;
import com.example.javafxinterpretor.model.values.Value;
import com.example.javafxinterpretor.utilities.MyIDictionary;
import com.example.javafxinterpretor.utilities.MyIHeapDictionary;
import com.example.javafxinterpretor.utilities.MyIList;
import com.example.javafxinterpretor.utilities.MyIStack;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class MemoryRepository implements Repository{
    List<ProgramState> programStateList;
    String logFilePath;

    public List<ProgramState> getProgramList(){
        return this.programStateList;
    }

    public void setProgramList(List<ProgramState> newList){
        this.programStateList = newList;
    }

    public MemoryRepository(String filePath) {
        this.logFilePath = filePath;
        programStateList = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Repository { " +
                "programStateList= " + this.programStateList +
                " }";
    }

    @Override
    public void add(ProgramState programState) {
        this.programStateList.add(programState);
    }

    private void printStack(PrintWriter writer, ProgramState state){
        MyIStack<IStatement> stack = state.getStack();

        Vector<IStatement> toPrint = new Vector<>();

        while (!stack.isEmpty()){
            toPrint.add(stack.pop().deepCopy());
        }

        writer.println("Stack");

        for (IStatement i : toPrint){
            writer.println(i.toString());
        }

        for (int j = toPrint.size() - 1; j >= 0; j--){
            stack.push(toPrint.get(j).deepCopy());
        }
        writer.println();
        writer.flush();
    }

    private void printSymbolTable(PrintWriter writer, ProgramState state){
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();

        Map<String, Value> map = symbolTable.getMap();
        writer.println("Symbol Table");

        map.forEach( (K, V) -> {
            writer.println(K + " --> " + V.toString());
        });
        writer.println();
        writer.flush();
    }

    private void printOutput(PrintWriter writer, ProgramState state){
        MyIList<Value> output = state.getOutputList();

        List<Value> outputList = output.getList();
        writer.println("Output");

        for (Value i : outputList){
            writer.println(i.toString());
        }
        writer.println();
        writer.flush();
    }

    private void printFileTable(PrintWriter writer, ProgramState state){
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();

        Map<StringValue, BufferedReader> map = fileTable.getMap();

        writer.println("FileTable");
        map.forEach((K, V) ->{
            writer.println(K.toString() + " --> " + V.toString() );
        });
        writer.println();
    }

    private void printHeapTable(PrintWriter writer, ProgramState state){
        MyIHeapDictionary<Value> heapTable = state.getHeapTable();

        Map<Integer, Value> map = heapTable.getMap();

        writer.println("heapTable");
        map.forEach((K, V) ->{
            writer.println(K.toString() + " --> " + V.toString());
        });
        writer.println();
    }

    private void printId(PrintWriter writer, ProgramState state){
        writer.println("ID = " + state.getThreadId());
        writer.println();
    }

    @Override
    public void logProgramState(ProgramState stateToLog) throws InterpretorException {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, true)));
        }
        catch (IOException exception)
        {
            throw new InterpretorException(exception.getMessage());
        }

        this.printId(writer, stateToLog);
        this.printStack(writer, stateToLog);
        this.printSymbolTable(writer, stateToLog);
        this.printOutput(writer, stateToLog);
        this.printFileTable(writer, stateToLog);
        this.printHeapTable(writer, stateToLog);
        writer.println("-----------------------------------");
        writer.flush();
    }
}
