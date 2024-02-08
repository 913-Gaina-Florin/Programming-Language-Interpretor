package com.example.javafxinterpretor.model.expressions;

import com.example.javafxinterpretor.model.InterpretorException;
import com.example.javafxinterpretor.model.types.IntType;
import com.example.javafxinterpretor.model.types.Type;
import com.example.javafxinterpretor.model.values.IntValue;
import com.example.javafxinterpretor.model.values.Value;
import com.example.javafxinterpretor.utilities.MyIDictionary;
import com.example.javafxinterpretor.utilities.MyIHeapDictionary;

import java.util.Objects;

public class ArithmeticExpression implements Expression{
    private final Expression exp1;
    private final Expression exp2;
    private final int operator; // 1 -> +; 2 -> -; 3 -> *; 4 -> /

    public ArithmeticExpression(Expression ex1, Expression ex2, int op) {
        this.exp1 = ex1;
        this.exp2 = ex2;
        this.operator = op;
    }
    @Override
    public Value eval(MyIDictionary<String, Value> symbolTable, MyIHeapDictionary<Value> heapTable) throws InterpretorException {
        Value v1 = this.exp1.eval(symbolTable, heapTable);

        if (v1.getType().equals(new IntType())){
            Value v2 = this.exp2.eval(symbolTable, heapTable);

            if (v2.getType().equals(new IntType())){
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;

                int n1 = i1.getVal();
                int n2 = i2.getVal();

                if (this.operator == 1) {
                    return new IntValue(n1 + n2);
                }
                else if (this.operator == 2){
                    return new IntValue(n1 - n2);
                }
                else if (this.operator == 3){
                    return new IntValue(n1 * n2);
                }
                else if (this.operator == 4){
                    if (n2 == 0)
                        throw new InterpretorException("Division by zero! ");
                    else
                        return new IntValue(n1 / n2);
                }
                else
                    throw new InterpretorException("Invalid expression operator! ");
            }
            else
                throw new InterpretorException("Second operand is not an integer! ");
        }
        else
            throw new InterpretorException("First operand is not an integer! ");
    }

    public Type typeCheck(MyIDictionary<String, Type> typeEnvironment) throws InterpretorException{
        Type type1, type2;
        type1 = exp1.typeCheck(typeEnvironment);
        type2 = exp2.typeCheck(typeEnvironment);

        if (type1.equals(new IntType())){
            if (type2.equals(new IntType())){
                return new IntType();
            }
            else throw new InterpretorException("Second operand is not an integer! ");
        }
        else throw new InterpretorException("First operand is not an integer! ");
    }

    public String toString(){
        return switch (this.operator) {
            case 1 -> this.exp1 + " + " + this.exp2;
            case 2 -> this.exp1 + " - " + this.exp2;
            case 3 -> this.exp1 + " * " + this.exp2;
            case 4 -> this.exp1 + " / " + this.exp2;
            default -> "Invalid arithmetic expression! ";
        };
    }
}
