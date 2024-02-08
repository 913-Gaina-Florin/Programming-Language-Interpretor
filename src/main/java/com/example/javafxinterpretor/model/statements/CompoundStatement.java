package com.example.javafxinterpretor.model.statements;

import com.example.javafxinterpretor.model.InterpretorException;
import com.example.javafxinterpretor.model.ProgramState;
import com.example.javafxinterpretor.model.types.Type;
import com.example.javafxinterpretor.utilities.MyIDictionary;
import com.example.javafxinterpretor.utilities.MyIStack;

public class CompoundStatement implements IStatement {
    IStatement statement1;
    IStatement statement2;

    public CompoundStatement(IStatement s1, IStatement s2){
        this.statement1 = s1;
        this.statement2 = s2;
    }
    public ProgramState execute(ProgramState state) throws InterpretorException {
        MyIStack<IStatement> stack = state.getStack();
        stack.push(statement2);
        stack.push(statement1);

        state.setStack(stack);
        return null;
    }

    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnvironment) throws InterpretorException{
        MyIDictionary<String, Type> typeEnv1 = statement1.typeCheck(typeEnvironment);
        return statement2.typeCheck(typeEnv1);
    }

    @Override
    public IStatement deepCopy() {
        return new CompoundStatement(this.statement1.deepCopy(), this.statement2.deepCopy());
    }


    public String toString(){
        return "( " + statement1.toString() + ";" + statement2.toString() + " )";
    }
}
