package com.example.javafxinterpretor.model.statements;

import com.example.javafxinterpretor.model.InterpretorException;
import com.example.javafxinterpretor.model.ProgramState;
import com.example.javafxinterpretor.model.expressions.Expression;
import com.example.javafxinterpretor.model.types.BoolType;
import com.example.javafxinterpretor.model.types.Type;
import com.example.javafxinterpretor.model.values.BoolValue;
import com.example.javafxinterpretor.model.values.Value;
import com.example.javafxinterpretor.utilities.MyIDictionary;
import com.example.javafxinterpretor.utilities.MyIStack;

public class WhileStatement implements IStatement{
    Expression expression;

    public WhileStatement(Expression expr){
        this.expression = expr;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpretorException {
        MyIStack<IStatement> executionStack = state.getStack();

        Value expEval = this.expression.eval(state.getSymbolTable(), state.getHeapTable());
        Type expType = expEval.getType();

        if (!expType.equals(new BoolType()))
            throw new InterpretorException("While expression not a boolean! ");

        BoolValue expValue = (BoolValue) expEval;

        if (expValue.getValue())
        {
            IStatement toExecute = executionStack.pop();
            IStatement copyToExecute = toExecute.deepCopy();

            executionStack.push(copyToExecute);
            executionStack.push(this.deepCopy());
            executionStack.push(toExecute);
        }
        else{
            executionStack.pop();
        }

        state.setStack(executionStack);
        return null;
    }

    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnvironment) throws InterpretorException{
        Type typeExpression = expression.typeCheck(typeEnvironment);

        if (typeExpression.equals(new BoolType())){
            return typeEnvironment;
        }
        else throw new InterpretorException("While condition not of bool type! ");
    }

    @Override
    public IStatement deepCopy() {
        return new WhileStatement(this.expression);
    }

    @Override
    public String toString() {
        return "While ( " + this.expression + " ) ";
    }
}
