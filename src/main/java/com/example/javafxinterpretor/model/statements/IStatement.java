package com.example.javafxinterpretor.model.statements;

import com.example.javafxinterpretor.model.InterpretorException;
import com.example.javafxinterpretor.model.ProgramState;
import com.example.javafxinterpretor.model.types.Type;
import com.example.javafxinterpretor.utilities.MyIDictionary;

public interface IStatement {
    ProgramState execute(ProgramState state) throws InterpretorException;

    MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnvironment) throws InterpretorException;

    public IStatement deepCopy();
}
