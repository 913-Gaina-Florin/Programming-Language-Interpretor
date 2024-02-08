package com.example.javafxinterpretor;

import com.example.javafxinterpretor.controller.Controller;
import com.example.javafxinterpretor.model.InterpretorException;
import com.example.javafxinterpretor.model.ProgramState;
import com.example.javafxinterpretor.model.statements.IStatement;
import com.example.javafxinterpretor.model.values.StringValue;
import com.example.javafxinterpretor.model.values.Value;
import com.example.javafxinterpretor.repository.MemoryRepository;
import com.example.javafxinterpretor.utilities.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainController {

    public static Controller launchController;

    Map<String, IStatement> nameToProgram;

    @FXML
    private ListView<String > listView;

    @FXML
    protected void initialize(){
        nameToProgram = new HashMap<>();
        List<String> programList = new ArrayList<>();

        nameToProgram.put(HardcodedPrograms.Program1().toString(), HardcodedPrograms.Program1());
        programList.add(HardcodedPrograms.Program1().toString());

        nameToProgram.put(HardcodedPrograms.Program2().toString(), HardcodedPrograms.Program2());
        programList.add(HardcodedPrograms.Program2().toString());

        nameToProgram.put(HardcodedPrograms.Program3().toString(), HardcodedPrograms.Program3());
        programList.add(HardcodedPrograms.Program3().toString());

        nameToProgram.put(HardcodedPrograms.Program4().toString(), HardcodedPrograms.Program4());
        programList.add(HardcodedPrograms.Program4().toString());

        nameToProgram.put(HardcodedPrograms.Program5().toString(), HardcodedPrograms.Program5());
        programList.add(HardcodedPrograms.Program5().toString());

        nameToProgram.put(HardcodedPrograms.Program6().toString(), HardcodedPrograms.Program6());
        programList.add(HardcodedPrograms.Program6().toString());

        nameToProgram.put(HardcodedPrograms.Program7().toString(), HardcodedPrograms.Program7());
        programList.add(HardcodedPrograms.Program7().toString());

        nameToProgram.put(HardcodedPrograms.Program8().toString(), HardcodedPrograms.Program8());
        programList.add(HardcodedPrograms.Program8().toString());


        ObservableList<String> observableList = javafx.collections.FXCollections.observableList(programList);
        listView.setItems(observableList);
    }

    @FXML
    protected void onItemCLicked(){
        String clickedProgram = listView.getSelectionModel().getSelectedItem();
        IStatement programToRun = nameToProgram.get(clickedProgram);

        try {
            programToRun.typeCheck(new MyDictionary<>());
        }
        catch (InterpretorException e){
            MainApplication.displayAlert(e.getMessage());
            return;
        }

        MemoryRepository repo = new MemoryRepository("log");

        MyIStack<IStatement> exeStack = new MyStack<IStatement>();
        MyIDictionary<String, Value> symbolTable = new MyDictionary<String, Value>();
        MyIList<Value> outputList = new MyList<Value>();
        MyIDictionary<StringValue, BufferedReader> fileTable = new MyDictionary<>();
        MyIHeapDictionary<Value> heapTable = new MyHeap<>();

        ProgramState state = new ProgramState(exeStack, symbolTable, outputList,
                fileTable, heapTable, programToRun);
        repo.add(state);

        launchController = new Controller(repo);
        MainApplication.startMainWindow();

    }
}