package com.example.javafxinterpretor.model.statements;

import com.example.javafxinterpretor.model.InterpretorException;
import com.example.javafxinterpretor.model.ProgramState;
import com.example.javafxinterpretor.model.types.Type;
import com.example.javafxinterpretor.utilities.MyIDictionary;

public class NopStatement implements IStatement{
    @Override
    public ProgramState execute(ProgramState state) throws InterpretorException {
        return null;
    }
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnvironment) throws InterpretorException{
        return typeEnvironment;
    }

    @Override
    public IStatement deepCopy() {
        return new NopStatement();
    }

    public String toString(){
        return "NOP ";
    }
}
