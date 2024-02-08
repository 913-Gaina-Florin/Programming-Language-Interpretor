package com.example.javafxinterpretor.model.statements;

import com.example.javafxinterpretor.model.InterpretorException;
import com.example.javafxinterpretor.model.ProgramState;
import com.example.javafxinterpretor.model.expressions.Expression;
import com.example.javafxinterpretor.model.types.StringType;
import com.example.javafxinterpretor.model.types.Type;
import com.example.javafxinterpretor.model.values.StringValue;
import com.example.javafxinterpretor.model.values.Value;
import com.example.javafxinterpretor.utilities.MyIDictionary;
import com.example.javafxinterpretor.utilities.MyIHeapDictionary;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFile implements IStatement{
    Expression expression;

    public CloseRFile(Expression exp){
        this.expression = exp;
    }
    @Override
    public ProgramState execute(ProgramState state) throws InterpretorException {
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        MyIHeapDictionary<Value> heapTable = state.getHeapTable();
        Value expEval = this.expression.eval(symbolTable, heapTable);

        if (expEval.getType().equals(new StringType())) {
            StringValue expValue = (StringValue) expEval;

            if (fileTable.isDefined(expValue)) {
                BufferedReader br = fileTable.lookUp(expValue);

                try {
                    br.close();
                }
                catch(IOException exception) {
                    throw new InterpretorException(exception.getMessage());
                }
                fileTable.remove(expValue);
            }
            else throw new InterpretorException("The file has not been previously opened! ");
        }
        else throw new InterpretorException("Expression type does not constitute a valid operand!");

        state.setFileTable(fileTable);
        return null;
    }

    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnvironment) throws InterpretorException{
        Type expressionType = expression.typeCheck(typeEnvironment);

        if (expressionType.equals(new StringType())){
            return typeEnvironment;
        }
        else
            throw new InterpretorException("CloseRFile: expression type not a String Type! ");
    }

    @Override
    public IStatement deepCopy() {
        return new CloseRFile(this.expression);
    }

    @Override
    public String toString() {
        return "CloseRFile " + this.expression;
    }
}
