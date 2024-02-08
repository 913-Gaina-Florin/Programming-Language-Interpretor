package com.example.javafxinterpretor;

import com.example.javafxinterpretor.controller.Controller;
import com.example.javafxinterpretor.model.ProgramState;
import com.example.javafxinterpretor.model.statements.IStatement;
import com.example.javafxinterpretor.model.values.StringValue;
import com.example.javafxinterpretor.model.values.Value;
import com.example.javafxinterpretor.repository.Repository;
import com.example.javafxinterpretor.utilities.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.BufferedReader;
import java.text.CollationElementIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class MainWindowController {

    public static ProgramState lastExecutedState;

    private Controller controller;

    private Repository repository;

    @FXML
    private ListView<String> exeStackListView;

    @FXML
    private ListView<String> outputListView;

    @FXML
    private ListView<String> fileTableListView;

    @FXML
    private ListView<String> programStateListView;

    @FXML
    private TableView< Pair<Integer, Value> > heapTableTableView;

    @FXML
    private TableView< Pair<String, Value> > symbolTableView;

    @FXML
    private TableColumn< Pair<String, Value>, String >stringColumn;

    @FXML
    private TableColumn< Pair<String, Value>, String > valColumn;

    @FXML
    private TableColumn< Pair<Integer, Value>, String >addressColumn;

    @FXML
    private TableColumn< Pair<Integer, Value>, String > valueColumn;

    @FXML
    private TextField numberOfProgramStatesField;


    private String returnNumberOfProgramStates(){
        return "Number of program states: " + repository.getProgramList().size();
    }

    private void initStack(){
        MyIStack<IStatement> exeStack = repository.getProgramList().getFirst().getStack();
        Vector<IStatement> toPrint = new Vector<>();
        List<String> istatementList = new ArrayList<>();

        while (!exeStack.isEmpty()){
            toPrint.add(exeStack.pop().deepCopy());
        }


        for (IStatement i : toPrint){
            istatementList.add(i.toString());
        }

        for (int j = toPrint.size() - 1; j >= 0; j--){
            exeStack.push(toPrint.get(j).deepCopy());
        }

        ObservableList<String> observableList = javafx.collections.FXCollections.observableList(istatementList);
        exeStackListView.setItems(observableList);

    }

    private void initProgramStateList(){
        List<String> programStateList = new ArrayList<>();
        List<ProgramState> stateList = repository.getProgramList();

        for (ProgramState state : stateList){
            programStateList.add(state.toString());
        }

        ObservableList<String> observableList = javafx.collections.FXCollections.observableList(programStateList);
        programStateListView.setItems(observableList);
    }

    private void updateNumberOfProgramStates(){
        initNumberOfProgramStates();
    }

    private void initNumberOfProgramStates(){
        numberOfProgramStatesField.setText(returnNumberOfProgramStates());
    }

    @FXML
    protected  void onListItemSelection(){
        updateOutputListView();
        updateStackListView();
        updateFileTableListView();
    }

    @FXML
    protected void onButtonClick(){
        try {
            controller.executeOneStep();
        }
        catch (Exception e){
            MainApplication.displayAlert(e.getMessage());
            return;
        }

        updateOutputListView();
        updateStackListView();
        updateFileTableListView();
        updateProgramStateListView();
        updateNumberOfProgramStates();
        updateHeapTableView();
        updateSymbolTableView();
    }

    private void updateOutputListView(){
        ProgramState selectedState;

        List<ProgramState> programsList = repository.getProgramList();

        if (repository.getProgramList().isEmpty())
            selectedState = lastExecutedState;
       else selectedState = programsList.getFirst();

        MyIList<Value> outputList = selectedState.getOutputList();
        List<Value> outputList2 = outputList.getList();

        List<String> toUpdateList = new ArrayList<>();

        for (Value val : outputList2){
            toUpdateList.add(val.toString());
        }

        ObservableList<String> observableList = javafx.collections.FXCollections.observableArrayList(toUpdateList);
        outputListView.setItems(observableList);
    }

    private void updateProgramStateListView(){
        initProgramStateList();
    }

    private void updateStackListView(){
        if (repository.getProgramList().isEmpty()) {
            exeStackListView.setItems(javafx.collections.FXCollections.observableList(new ArrayList<>()));
            return;
        }

        List<ProgramState> programsList = repository.getProgramList();
        int selectedIndex = programStateListView.getSelectionModel().getSelectedIndex();
        ProgramState selectedProgramState;


        if (selectedIndex == -1)
            selectedProgramState = programsList.getFirst();
        else selectedProgramState = programsList.get(selectedIndex);

        MyIStack<IStatement> exeStack = selectedProgramState.getStack();

        Vector<IStatement> toPrint = new Vector<>();
        List<String> istatementList = new ArrayList<>();

        while (!exeStack.isEmpty()){
            toPrint.add(exeStack.pop().deepCopy());
        }


        for (IStatement i : toPrint){
            istatementList.add(i.toString());
        }

        for (int j = toPrint.size() - 1; j >= 0; j--){
            exeStack.push(toPrint.get(j).deepCopy());
        }

        ObservableList<String> observableList = javafx.collections.FXCollections.observableList(istatementList);
        exeStackListView.setItems(observableList);
    }

    private void updateFileTableListView () {
        ProgramState selectedState;

        List<ProgramState> programsList = repository.getProgramList();

        if (repository.getProgramList().isEmpty())
            selectedState = lastExecutedState;
        else selectedState = programsList.getFirst();

        MyIDictionary<StringValue, BufferedReader> fileTableDictionary = selectedState.getFileTable();
        Map<StringValue, BufferedReader> fileTableMap = fileTableDictionary.getMap();
        List<String> toStringList = new ArrayList<>();

       fileTableMap.forEach((K, V) -> {
           toStringList.add(K.toString() + " --> " + V.toString());
       });

        ObservableList<String> observableList = javafx.collections.FXCollections.observableList(toStringList);
        fileTableListView.setItems(observableList);
    }

    @FXML
    protected void updateHeapTableView(){
        heapTableTableView.getItems().clear();
        ProgramState state;

        if (repository.getProgramList().isEmpty())
            state = lastExecutedState;
        else state = repository.getProgramList().getFirst();

        MyIHeapDictionary<Value> heapTable = state.getHeapTable();
        Map<Integer, Value> heapMap = heapTable.getMap();

        heapMap.forEach(
                (K, V) -> {
                    Pair<Integer, Value> heapItem = new Pair<>(K, V);
                    heapTableTableView.getItems().add(heapItem);
                }
        );
    }

    protected void updateSymbolTableView(){
        symbolTableView.getItems().clear();
        ProgramState state;

        if (repository.getProgramList().isEmpty())
            state = lastExecutedState;
        else state = repository.getProgramList().getFirst();

        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        Map<String , Value> symbolMap = symbolTable.getMap();

        symbolMap.forEach(
                (K, V) -> {
                    Pair<String, Value> symbolItem = new Pair<>(K, V);
                    symbolTableView.getItems().add(symbolItem);
                }
        );
    }

    private void initTableView(){
        addressColumn.setCellValueFactory(new PropertyValueFactory<Pair<Integer, Value>, String>("first"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<Pair<Integer, Value>, String>("second"));

        heapTableTableView.getColumns().setAll(addressColumn, valueColumn);
    }

    private void initSymbolTableView(){
        stringColumn.setCellValueFactory(new PropertyValueFactory<>("first"));
        valColumn.setCellValueFactory(new PropertyValueFactory<>("second"));

        symbolTableView.getColumns().setAll(stringColumn, valColumn);
    }

    @FXML
    protected void initialize(){
        controller = MainController.launchController;
        repository = controller.getRepository();

        initNumberOfProgramStates();
        initProgramStateList();
        initStack();
        initTableView();
        initSymbolTableView();
        controller.initExecutor();

    }
}
