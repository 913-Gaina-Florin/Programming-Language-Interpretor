package com.example.javafxinterpretor.model.expressions;

import com.example.javafxinterpretor.model.InterpretorException;
import com.example.javafxinterpretor.model.types.Type;
import com.example.javafxinterpretor.model.values.Value;
import com.example.javafxinterpretor.utilities.MyIDictionary;
import com.example.javafxinterpretor.utilities.MyIHeapDictionary;

public class VariableExpression implements Expression{
    private final String varId;
    public VariableExpression(String id){
        this.varId = id;
    }
    @Override
    public Value eval(MyIDictionary<String, Value> symbolTable, MyIHeapDictionary<Value> heapTable) throws InterpretorException {
        if (!symbolTable.isDefined(this.varId)){
            throw new InterpretorException("Variable identifier not defined! ");
        }
        return symbolTable.lookUp(this.varId);
    }

    public Type typeCheck(MyIDictionary<String, Type> typeEnvironment) throws InterpretorException{
        return typeEnvironment.lookUp(varId);
    }

    public String toString(){
        return "variable = " + varId;
    }
}
