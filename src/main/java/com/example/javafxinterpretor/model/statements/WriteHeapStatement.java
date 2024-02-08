package com.example.javafxinterpretor.model.statements;

import com.example.javafxinterpretor.model.InterpretorException;
import com.example.javafxinterpretor.model.ProgramState;
import com.example.javafxinterpretor.model.expressions.Expression;
import com.example.javafxinterpretor.model.types.ReferenceType;
import com.example.javafxinterpretor.model.types.Type;
import com.example.javafxinterpretor.model.values.ReferenceValue;
import com.example.javafxinterpretor.model.values.Value;
import com.example.javafxinterpretor.utilities.MyIDictionary;
import com.example.javafxinterpretor.utilities.MyIHeapDictionary;

public class WriteHeapStatement implements IStatement{
    String variableName;
    Expression expression;

    public WriteHeapStatement(String name, Expression expr){
        this.variableName = name;
        this.expression = expr;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpretorException {
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        MyIHeapDictionary<Value> heapTable = state.getHeapTable();

        if (!symbolTable.isDefined(this.variableName))
            throw new InterpretorException("Variable not defined! ");

        if (!(symbolTable.lookUp(this.variableName).getType() instanceof ReferenceType))
            throw new InterpretorException("Variable not of Reference Type! ");

        ReferenceValue variableVal = (ReferenceValue) symbolTable.lookUp(this.variableName);
        Integer address = variableVal.getAddress();

        if (!heapTable.isDefined(address))
            throw new InterpretorException("Address not defined! ");

        ReferenceType variableType = (ReferenceType) variableVal.getType();
        Value expEval = this.expression.eval(symbolTable, heapTable);

        if (!(variableType.getInnerType().equals(expEval.getType())))
            throw new InterpretorException("Location type and expression type do not match! ");

        heapTable.update(address, expEval);
        state.setHeapTable(heapTable);
        return null;
    }

    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnvironment) throws InterpretorException{Type expressionType = expression.typeCheck(typeEnvironment);
        Type typeExpression = expression.typeCheck(typeEnvironment);
        Type typeVariable = typeEnvironment.lookUp(variableName);

        if (typeVariable.equals(new ReferenceType(typeExpression))){
            return typeEnvironment;
        }
        else
            throw new InterpretorException("WriteHeapStatement: TypeVariable not of Reference type! ");
    }

    @Override
    public IStatement deepCopy() {
        return new WriteHeapStatement(this.variableName, this.expression);
    }

    @Override
    public String toString() {
        return "WriteHeap " + this.variableName + " = " + this.expression;
    }
}
