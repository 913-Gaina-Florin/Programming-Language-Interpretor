package com.example.javafxinterpretor.model.statements;


import com.example.javafxinterpretor.model.InterpretorException;
import com.example.javafxinterpretor.model.ProgramState;
import com.example.javafxinterpretor.model.types.Type;
import com.example.javafxinterpretor.model.values.StringValue;
import com.example.javafxinterpretor.model.values.Value;
import com.example.javafxinterpretor.utilities.*;

import java.io.BufferedReader;

public class ForkStatement implements IStatement{
    IStatement childStatement;

    public ForkStatement(IStatement statement){
        this.childStatement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpretorException {
        ProgramState childState;
        MyIStack<IStatement> exeStack = new MyStack<>();
        MyIDictionary<String, Value> childSymbolTable = state.getSymbolTable().deepCopy();
        MyIList<Value> childOutputList = state.getOutputList();
        MyIDictionary<StringValue, BufferedReader> childFileTable = state.getFileTable();
        MyIHeapDictionary<Value> childHeapTable = state.getHeapTable();

        childState = new ProgramState(exeStack, childSymbolTable, childOutputList, childFileTable,
                 childHeapTable, childStatement);

        return childState;
    }

    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnvironment) throws InterpretorException{
        childStatement.typeCheck(typeEnvironment.deepCopy());
        return typeEnvironment;
    }

    @Override
    public IStatement deepCopy() {
        return new ForkStatement(childStatement.deepCopy());
    }

    @Override
    public String toString() {
        return "ForkStatement (" + this.childStatement + ") ";
    }
}
