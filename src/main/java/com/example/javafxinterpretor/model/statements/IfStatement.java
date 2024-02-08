package com.example.javafxinterpretor.model.statements;

import com.example.javafxinterpretor.model.InterpretorException;
import com.example.javafxinterpretor.model.ProgramState;
import com.example.javafxinterpretor.model.expressions.Expression;
import com.example.javafxinterpretor.model.types.BoolType;
import com.example.javafxinterpretor.model.types.IntType;
import com.example.javafxinterpretor.model.types.Type;
import com.example.javafxinterpretor.model.values.BoolValue;
import com.example.javafxinterpretor.model.values.IntValue;
import com.example.javafxinterpretor.model.values.Value;
import com.example.javafxinterpretor.utilities.MyIDictionary;

public class IfStatement implements IStatement{
    Expression expression;
    IStatement thenStatement;
    IStatement elseStatement;

    public IfStatement(Expression exp, IStatement thenS, IStatement elseS){
        this.expression = exp;
        this.thenStatement = thenS;
        this.elseStatement = elseS;
    }
    @Override
    public ProgramState execute(ProgramState state) throws InterpretorException {
        Value expEval = this.expression.eval(state.getSymbolTable(), state.getHeapTable());

        if (expEval.getType().equals(new BoolType())){
            BoolValue boolVal = (BoolValue) expEval;

            if (boolVal.getValue()){
                return this.thenStatement.execute(state);
            }
            else
                return this.elseStatement.execute(state);
        }
        else if (expEval.getType().equals(new IntType())){
            IntValue intVal = (IntValue) expEval;

            if (intVal.getVal() > 0){
                return this.thenStatement.execute(state);
            }
            else
                return this.elseStatement.execute(state);
        }
        else
            throw new InterpretorException("Invalid if expression type! ");
    }

    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeEnvironment) throws InterpretorException{
        Type typeExpression = expression.typeCheck(typeEnvironment);
        if (typeExpression.equals(new BoolType())){
            thenStatement.typeCheck(typeEnvironment.deepCopy());
            elseStatement.typeCheck(typeEnvironment.deepCopy());
            return typeEnvironment;
        }
        else throw new InterpretorException("If condition not of bool type! ");
    }

    @Override
    public IStatement deepCopy() {
        return new IfStatement(this.expression, this.thenStatement.deepCopy(), this.elseStatement.deepCopy());
    }

    public String toString(){
        return "IF ( " + this.expression + " ) " + "Then { " + this.thenStatement + "} Else { " +
                this.elseStatement + " } ";
    }
}
