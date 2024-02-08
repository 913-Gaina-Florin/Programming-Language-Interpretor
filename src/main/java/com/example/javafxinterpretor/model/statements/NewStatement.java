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

public class NewStatement implements IStatement{
    String variableName;
    Expression expression;

    public NewStatement(String varName, Expression exp){
        this.variableName = varName;
        this.expression = exp;
    }
    @Override
    public ProgramState execute(ProgramState state) throws InterpretorException {
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        MyIHeapDictionary<Value> heapTable = state.getHeapTable();

        if (!symbolTable.isDefined(this.variableName))
            throw new InterpretorException("Variable not defined! ");

        if (!(symbolTable.lookUp(this.variableName).getType() instanceof ReferenceType))
            throw new InterpretorException("Variable not of Reference Type! ");

        Value expEval = this.expression.eval(symbolTable, heapTable);
        Type expType = expEval.getType();

        ReferenceValue variableValue = (ReferenceValue) symbolTable.lookUp(this.variableName);
        ReferenceType variableType = (ReferenceType) variableValue.getType();

        Type innerType = variableType.getInnerType();

        if (!(expType.equals(innerType)))
            throw new InterpretorException("Inner type does not match with expression type! ");

        heapTable.add(expEval);

        ReferenceValue newValue = new ReferenceValue(heapTable.getCurrentAddress(), innerType);
        symbolTable.update(this.variableName, newValue);

        state.setSymbolTable(symbolTable);
        state.setHeapTable(heapTable);

        return null;
    }

    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnvironment) throws InterpretorException{
        Type typeExpression = expression.typeCheck(typeEnvironment);
        Type typeVariable = typeEnvironment.lookUp(variableName);

        if (typeVariable.equals(new ReferenceType(typeExpression)))
            return typeEnvironment;
        else
            throw new InterpretorException("NewStatement: l side and r side have different types! ");
    }

    @Override
    public IStatement deepCopy() {
        return new NewStatement(this.variableName, this.expression);
    }

    @Override
    public String toString() {
        return "New " + this.variableName + " = " + this.expression;
    }
}
