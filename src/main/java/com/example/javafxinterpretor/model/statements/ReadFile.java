package com.example.javafxinterpretor.model.statements;

import com.example.javafxinterpretor.model.InterpretorException;
import com.example.javafxinterpretor.model.ProgramState;
import com.example.javafxinterpretor.model.expressions.Expression;
import com.example.javafxinterpretor.model.types.IntType;
import com.example.javafxinterpretor.model.types.StringType;
import com.example.javafxinterpretor.model.types.Type;
import com.example.javafxinterpretor.model.values.IntValue;
import com.example.javafxinterpretor.model.values.StringValue;
import com.example.javafxinterpretor.model.values.Value;
import com.example.javafxinterpretor.utilities.MyIDictionary;
import com.example.javafxinterpretor.utilities.MyIHeapDictionary;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFile implements IStatement{
    Expression expression;
    String variableName;

    public ReadFile(Expression exp, String var){
        this.expression = exp;
        this.variableName = var;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpretorException {
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        MyIHeapDictionary<Value> heapTable = state.getHeapTable();
        Value expEval = this.expression.eval(symbolTable, heapTable);

        if (!expEval.getType().equals(new StringType())){
            throw new InterpretorException("Expression not string type! ");
        }

        if (!symbolTable.isDefined(this.variableName)){
            throw new InterpretorException("Variable not defined! ");
        }

        if (!symbolTable.lookUp(this.variableName).getType().equals(new IntType())) {
            throw new InterpretorException("Variable type is not integer! ");
        }

        StringValue expVal = (StringValue) expEval;
        BufferedReader br = fileTable.lookUp(expVal);

        String readLine;
        try {
            readLine = br.readLine();
        }
        catch(IOException exception){
            throw new InterpretorException(exception.getMessage());
        }

        int valueRead = 0;
        if (readLine != null){
            valueRead = Integer.parseInt(readLine);
        }

        symbolTable.update(this.variableName, new IntValue(valueRead));
        state.setSymbolTable(symbolTable);
        return null;
    }

    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnvironment) throws InterpretorException{
        Type typeExpression = expression.typeCheck(typeEnvironment);
        Type typeVariable = typeEnvironment.lookUp(variableName);

        if (typeExpression.equals(new StringType())){
                if (typeVariable != null)
                {
                    return typeEnvironment;
                }
                else
                    throw new InterpretorException("ReadFile: Variable not defined! ");
        }
        else throw new InterpretorException("ReadFile: Expression not of String Type! ");
    }

    @Override
    public IStatement deepCopy() {
        return new ReadFile(this.expression, this.variableName);
    }

    @Override
    public String toString() {
        return "ReadFile: name = " + this.expression + " variable = " + this.variableName;
    }
}
