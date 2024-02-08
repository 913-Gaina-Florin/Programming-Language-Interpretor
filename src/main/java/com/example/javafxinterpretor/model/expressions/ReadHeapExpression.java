package com.example.javafxinterpretor.model.expressions;

import com.example.javafxinterpretor.model.InterpretorException;
import com.example.javafxinterpretor.model.types.ReferenceType;
import com.example.javafxinterpretor.model.types.Type;
import com.example.javafxinterpretor.model.values.ReferenceValue;
import com.example.javafxinterpretor.model.values.Value;
import com.example.javafxinterpretor.utilities.MyIDictionary;
import com.example.javafxinterpretor.utilities.MyIHeapDictionary;

public class ReadHeapExpression implements Expression{
    Expression expression;

    public ReadHeapExpression(Expression expr){
        this.expression = expr;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> symbolTable, MyIHeapDictionary<Value> heapTable) throws InterpretorException {

        Value expEval = this.expression.eval(symbolTable, heapTable);
        Type expType = expEval.getType();

        if (! (expType instanceof ReferenceType))
            throw new InterpretorException("Expression type not Reference Type! ");

        ReferenceValue value = (ReferenceValue) expEval;
        Integer address = value.getAddress();

        if (!heapTable.isDefined(address))
            throw new InterpretorException("Address violation error! ");

        return heapTable.getValue(address);
    }

    public Type typeCheck(MyIDictionary<String, Type> typeEnvironment) throws InterpretorException{
        Type type = expression.typeCheck(typeEnvironment);
        if (type instanceof ReferenceType){
            ReferenceType ref = (ReferenceType) type;
            return ref.getInnerType();
        }
        else throw new InterpretorException("the ReadHeapExpression argument is not of Ref Type! ");
    }

    @Override
    public String toString() {
        return "ReadHeapExpression " + this.expression;
    }
}
