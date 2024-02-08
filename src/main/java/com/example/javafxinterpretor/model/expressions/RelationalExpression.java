package com.example.javafxinterpretor.model.expressions;

import com.example.javafxinterpretor.model.InterpretorException;
import com.example.javafxinterpretor.model.types.BoolType;
import com.example.javafxinterpretor.model.types.IntType;
import com.example.javafxinterpretor.model.types.Type;
import com.example.javafxinterpretor.model.values.BoolValue;
import com.example.javafxinterpretor.model.values.IntValue;
import com.example.javafxinterpretor.model.values.Value;
import com.example.javafxinterpretor.utilities.MyIDictionary;
import com.example.javafxinterpretor.utilities.MyIHeapDictionary;

public class RelationalExpression implements Expression{
    Expression expression1;
    Expression expression2;

    int operator; // 1: <, 2: <=, 3: ==, 4: !=, 5: >, 6: >=

    public RelationalExpression(Expression exp1, Expression exp2, int op){
        this.expression1 = exp1;
        this.expression2 = exp2;
        this.operator = op;
    }
    @Override
    public Value eval(MyIDictionary<String, Value> symbolTable, MyIHeapDictionary<Value> heapTable) throws InterpretorException {
        if (!expression1.eval(symbolTable, heapTable).getType().equals(new IntType())){
            throw new InterpretorException("Expression1 not an integer! ");
        }

        if (!expression2.eval(symbolTable, heapTable).getType().equals(new IntType())){
            throw new InterpretorException("Expression2 not an integer! ");
        }

        if (operator < 1 || operator > 6){
            throw new InterpretorException("Invalid operator! ");
        }

        Value val1 = this.expression1.eval(symbolTable, heapTable);
        Value val2 = this.expression2.eval(symbolTable, heapTable);

        IntValue valueExpression1 = (IntValue) val1;
        IntValue valueExpression2 = (IntValue) val2;

        BoolValue returnVal = switch (this.operator) {
            case 1 -> new BoolValue(valueExpression1.getVal() < valueExpression2.getVal());
            case 2 -> new BoolValue(valueExpression1.getVal() <= valueExpression2.getVal());
            case 3 -> new BoolValue(valueExpression1.getVal() == valueExpression2.getVal());
            case 4 -> new BoolValue(valueExpression1.getVal() != valueExpression2.getVal());
            case 5 -> new BoolValue(valueExpression1.getVal() > valueExpression2.getVal());
            case 6 -> new BoolValue(valueExpression1.getVal() >= valueExpression2.getVal());
            default -> new BoolValue(false);
        };

        return returnVal;
    }

    public Type typeCheck(MyIDictionary<String, Type> typeEnvironment) throws InterpretorException{
        Type type1, type2;
        type1 = expression1.typeCheck(typeEnvironment);
        type2 = expression2.typeCheck(typeEnvironment);

        if (operator < 1 || operator > 6)
            throw new InterpretorException("Invalid operator in relational expression! ");

        if (type1.equals(new IntType())){
            if (type2.equals(new IntType())){
                return new BoolType();
            }
            else throw new InterpretorException("Second operand is not an integer! ");
        }
        else throw new InterpretorException("First operand is not an integer! ");
    }

    public String toString(){
        return switch (this.operator) {
            case 1 -> this.expression1 + " < " + this.expression2;
            case 2 -> this.expression1 + " <= " + this.expression2;
            case 3 -> this.expression1 + " == " + this.expression2;
            case 4 -> this.expression1 + " != " + this.expression2;
            case 5 -> this.expression1 + " > " + this.expression2;
            case 6 -> this.expression1 + " >= " + this.expression2;
            default -> "Invalid relational expression! ";
        };
    }
}
