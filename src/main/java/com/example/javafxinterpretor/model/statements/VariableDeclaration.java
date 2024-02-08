package com.example.javafxinterpretor.model.statements;


import com.example.javafxinterpretor.model.InterpretorException;
import com.example.javafxinterpretor.model.ProgramState;
import com.example.javafxinterpretor.model.types.*;
import com.example.javafxinterpretor.model.values.Value;
import com.example.javafxinterpretor.utilities.MyIDictionary;

public class VariableDeclaration implements IStatement{
    String variableName;
    Type variableType;

    public VariableDeclaration(String name, Type type){
        this.variableName = name;
        this.variableType = type;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpretorException {
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();

        if (symbolTable.isDefined(variableName)){
            throw new InterpretorException("Variable already defined! ");
        }
        else{
            if (variableType.equals(new IntType())){
                symbolTable.add(variableName, new IntType().defaultValue());
            }
            else if (variableType.equals(new BoolType())){
                symbolTable.add(variableName, new BoolType().defaultValue());
            }
            else if (variableType.equals(new StringType())) {
                symbolTable.add(variableName, new StringType().defaultValue());
            }
            else if (variableType instanceof ReferenceType){
                symbolTable.add(variableName, variableType.defaultValue());
            }
            else
                throw new InterpretorException("Variable type does not exist! ");
        }
        state.setSymbolTable(symbolTable);
        return null;
    }

    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnvironment) throws InterpretorException{
        typeEnvironment.add(variableName, variableType);
        return typeEnvironment;
    }

    @Override
    public IStatement deepCopy() {
        return new VariableDeclaration(this.variableName, this.variableType);
    }

    public String toString(){
        return "Declaration: " + this.variableName + " , " + this.variableType + " ";
    }
}
