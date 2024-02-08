package com.example.javafxinterpretor.model.expressions;

import com.example.javafxinterpretor.model.InterpretorException;
import com.example.javafxinterpretor.model.types.BoolType;
import com.example.javafxinterpretor.model.types.Type;
import com.example.javafxinterpretor.model.values.BoolValue;
import com.example.javafxinterpretor.model.values.Value;
import com.example.javafxinterpretor.utilities.MyIDictionary;
import com.example.javafxinterpretor.utilities.MyIHeapDictionary;

public class LogicExpression implements Expression{
    private final Expression exp1;
    private final Expression exp2;
    private final int operator; // 1 - and; 2 - or;

    public LogicExpression(Expression ex1, Expression ex2, int op){
        this.exp1 = ex1;
        this.exp2 = ex2;
        this.operator = op;
    }
    @Override
    public Value eval(MyIDictionary<String, Value> symbolTable, MyIHeapDictionary<Value> heapTable) throws InterpretorException {
        Value v1 = this.exp1.eval(symbolTable, heapTable);

        if (v1.getType().equals(new BoolType())){
            Value v2 = this.exp2.eval(symbolTable, heapTable);

            if (v2.getType().equals(new BoolType())){
                BoolValue b1 = (BoolValue) v1;
                BoolValue b2 = (BoolValue) v2;

                boolean n1 = b1.getValue();
                boolean n2 = b2.getValue();

                if (operator == 1){
                    return new BoolValue(n1 && n2);
                }
                else if (operator == 2){
                    return new BoolValue(n1 || n2);
                }
                else
                    throw new InterpretorException("Invalid expression operator! ");
            }
            else
                throw new InterpretorException("Second operand is not a boolean! ");
        }
        else
            throw new InterpretorException("First operand is not a boolean! ");
    }

    public Type typeCheck(MyIDictionary<String, Type> typeEnvironment) throws InterpretorException{
        Type type1, type2;
        type1 = exp1.typeCheck(typeEnvironment);
        type2 = exp2.typeCheck(typeEnvironment);

        if (type1.equals(new BoolType())){
            if (type2.equals(new BoolType())){
                return new BoolType();
            }
            else throw new InterpretorException("Second operand is not a boolean! ");
        }
        else throw new InterpretorException("First operand is not a boolean! ");
    }

    public String toString(){
        return switch (this.operator) {
            case 1 -> this.exp1 + " && " + this.exp2;
            case 2 -> this.exp1 + " || " + this.exp2;
            default -> "Invalid logic expression! ";
        };
    }
}
