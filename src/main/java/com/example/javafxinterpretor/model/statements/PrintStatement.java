package com.example.javafxinterpretor.model.statements;

import com.example.javafxinterpretor.model.InterpretorException;
import com.example.javafxinterpretor.model.ProgramState;
import com.example.javafxinterpretor.model.expressions.Expression;
import com.example.javafxinterpretor.model.types.Type;
import com.example.javafxinterpretor.model.values.Value;
import com.example.javafxinterpretor.utilities.MyIDictionary;
import com.example.javafxinterpretor.utilities.MyIHeapDictionary;
import com.example.javafxinterpretor.utilities.MyIList;

public class PrintStatement implements IStatement {
    Expression expression;
    public PrintStatement(Expression exp){
        this.expression = exp;
    }
    @Override
    public ProgramState execute(ProgramState state) throws InterpretorException {
        MyIList<Value> outputList = state.getOutputList();
        MyIDictionary<String, Value> symbolTable = state.getSymbolTable();
        MyIHeapDictionary<Value> heapTable = state.getHeapTable();
        outputList.add(this.expression.eval(symbolTable, heapTable));
        state.setOutputList(outputList);

        return null;
    }

    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnvironment) throws InterpretorException{
            expression.typeCheck(typeEnvironment);
            return typeEnvironment;
    }

    @Override
    public IStatement deepCopy() {
        return new PrintStatement(this.expression);
    }

    public String toString(){
        return " Print( " + this.expression + " ) ";
    }
}
