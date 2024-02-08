package com.example.javafxinterpretor.model.statements;

import com.example.javafxinterpretor.model.InterpretorException;
import com.example.javafxinterpretor.model.ProgramState;
import com.example.javafxinterpretor.model.expressions.Expression;
import com.example.javafxinterpretor.model.types.Type;
import com.example.javafxinterpretor.model.values.Value;
import com.example.javafxinterpretor.utilities.MyIDictionary;
import com.example.javafxinterpretor.utilities.MyIHeapDictionary;

public class AssignStatement implements IStatement{
    String id;
    Expression expression;

    public AssignStatement(String id, Expression exp){
        this.id = id;
        this.expression = exp;
    }
    @Override
    public ProgramState execute(ProgramState state) throws InterpretorException {
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        MyIHeapDictionary<Value> heapTable = state.getHeapTable();
        Value expEvaluated = this.expression.eval(symbolTable, heapTable);

        if (symbolTable.isDefined(id)){
            if (symbolTable.lookUp(id).getType().equals(expEvaluated.getType())){
                symbolTable.update(id, expEvaluated);
            }
            else
                throw new InterpretorException("Cannot enforce assignment on different types! ");
        }
        else
            throw new InterpretorException("Variable id not declared! ");

        state.setSymbolTable(symbolTable);
        return null;
    }

    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnvironment) throws InterpretorException{
        Type typeVariable= typeEnvironment.lookUp(id);
        Type typeExpression = expression.typeCheck(typeEnvironment);

        if (typeVariable.equals(typeExpression)){
            return typeEnvironment;
        }
        else throw new InterpretorException("AssignStatement: l side and r side have different types! ");
    }

    @Override
    public IStatement deepCopy() {
        return new AssignStatement(this.id, this.expression);
    }

    public String toString(){
        return "Assignment: " + id + " = " + this.expression;
    }
}
