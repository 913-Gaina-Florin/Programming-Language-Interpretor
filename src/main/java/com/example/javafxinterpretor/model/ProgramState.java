package com.example.javafxinterpretor.model;

import com.example.javafxinterpretor.model.statements.IStatement;
import com.example.javafxinterpretor.model.values.StringValue;
import com.example.javafxinterpretor.model.values.Value;
import com.example.javafxinterpretor.utilities.MyIDictionary;
import com.example.javafxinterpretor.utilities.MyIHeapDictionary;
import com.example.javafxinterpretor.utilities.MyIList;
import com.example.javafxinterpretor.utilities.MyIStack;

import java.io.BufferedReader;

public class ProgramState {
    MyIStack<IStatement> exeStack;
    MyIDictionary<String, Value> symbolTable;

    MyIDictionary<StringValue, BufferedReader> fileTable;

    MyIList<Value> outputList;

    MyIHeapDictionary<Value> heapTable;

    IStatement originalProgram;

    private int threadId;

    private static int stateId = 0;

    private static synchronized void increment(){
        stateId++;
    }

    public ProgramState(MyIStack<IStatement> exeStack, MyIDictionary<String, Value> symbolTable,
                        MyIList<Value> outputList, MyIDictionary<StringValue, BufferedReader> fileTable,
                        MyIHeapDictionary<Value> heapTable ,IStatement program){
        this.exeStack = exeStack;
        this.symbolTable = symbolTable;
        this.outputList = outputList;
        this.fileTable = fileTable;
        this.heapTable = heapTable;
        this.exeStack.push(program);
        this.originalProgram = program.deepCopy();

        increment();
        this.threadId = stateId;
    }

    public ProgramState oneStep() throws InterpretorException {
        if (exeStack.isEmpty())
            throw new InterpretorException("Execution stack is empty! ");
        else {
            IStatement currentStatement = exeStack.pop();
            return currentStatement.execute(this);
        }
    }

    public Boolean isNotCompleted(){
        return !this.exeStack.isEmpty();
    }

    public int getThreadId(){
        return this.threadId;
    }

    public MyIStack<IStatement> getStack() {
        return exeStack;
    }

    public MyIList<Value> getOutputList() {return this.outputList; }

    public MyIDictionary<String, Value> getSymbolTable() {return this.symbolTable; }

    public MyIDictionary<StringValue, BufferedReader> getFileTable() {return this.fileTable; }

    public MyIHeapDictionary<Value> getHeapTable() {return this.heapTable; }

    public void setStack(MyIStack<IStatement> stack) {this.exeStack = stack; }

    public void setOutputList(MyIList<Value> list) { this.outputList = list; }

    public void setSymbolTable(MyIDictionary<String, Value> dict) {this.symbolTable = dict; }

    public void setFileTable(MyIDictionary<StringValue, BufferedReader> dict) {this.fileTable = dict; }

    public void setHeapTable(MyIHeapDictionary<Value> heapTable) {this.heapTable =heapTable; }

    @Override
    public String toString() {
        return "ID = " + this.threadId + " \n\nexeStack = { " + this.exeStack + "} \n\n { symbolTable = { " +
                this.symbolTable + " } \n\n " + "fileTable = " +  this.fileTable
                + " \n\n { output = " + this.outputList + " } \n\n " + "{ heapTable = { " + this.heapTable + " } \n\n";
    }
}
