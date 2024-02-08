package com.example.javafxinterpretor.model.expressions;

import com.example.javafxinterpretor.model.InterpretorException;
import com.example.javafxinterpretor.model.types.Type;
import com.example.javafxinterpretor.model.values.Value;
import com.example.javafxinterpretor.utilities.MyIDictionary;
import com.example.javafxinterpretor.utilities.MyIHeapDictionary;

public class ValueExpression implements Expression{
    private final Value val;

    public ValueExpression(Value v){
        this.val = v;
    }
    @Override
    public Value eval(MyIDictionary<String, Value> symbolTable, MyIHeapDictionary<Value> heapTable) throws InterpretorException {
        return this.val;
    }

    public Type typeCheck(MyIDictionary<String, Type> typeEnvironment) throws InterpretorException {
        return val.getType();
    }

    public String toString(){
        return "value: " + this.val;
    }
}
