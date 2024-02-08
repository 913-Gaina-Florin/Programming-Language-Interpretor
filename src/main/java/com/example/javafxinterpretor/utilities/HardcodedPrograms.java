package com.example.javafxinterpretor.utilities;

import com.example.javafxinterpretor.model.expressions.*;
import com.example.javafxinterpretor.model.statements.*;
import com.example.javafxinterpretor.model.types.*;
import com.example.javafxinterpretor.model.values.*;

public class HardcodedPrograms {
    public static IStatement Program1(){
        IStatement program = new CompoundStatement(new VariableDeclaration("v", new IntType()),
                new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v"))));
        return program;
    }

    public static IStatement Program2(){
        IStatement program = new CompoundStatement(new VariableDeclaration("varf", new StringType()),
                new CompoundStatement(
                        new AssignStatement("varf", new ValueExpression(new StringValue("test.in"))),
                        new CompoundStatement(
                                new OpenRFile(new VariableExpression("varf")),
                                new CompoundStatement(
                                        new VariableDeclaration("varc", new IntType()),
                                        new CompoundStatement(
                                                new ReadFile(new VariableExpression("varf"), "varc"),
                                                new CompoundStatement(
                                                        new PrintStatement(new VariableExpression("varc")),
                                                        new CompoundStatement(
                                                                new ReadFile(new VariableExpression("varf"), "varc"),
                                                                new CompoundStatement(
                                                                        new PrintStatement(new VariableExpression("varc")),
                                                                        new CloseRFile(new VariableExpression("varf"))
                                                                )
                                                        )
                                                )

                                        )
                                )
                        )
                )

        );
        return program;
    }

    public static IStatement Program3(){
        IStatement program = new CompoundStatement(new VariableDeclaration("v", new ReferenceType(new IntType())),
                new CompoundStatement(
                        new NewStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new VariableDeclaration("a",
                                        new ReferenceType(new ReferenceType(new IntType()))),
                                new CompoundStatement(
                                        new NewStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(
                                                new NewStatement("v", new ValueExpression(new IntValue(30))),
                                                new PrintStatement(new ReadHeapExpression(new ReadHeapExpression(
                                                        new VariableExpression("a")
                                                )))
                                        )
                                )
                        )
                ));

        return program;
    }

    public static IStatement Program4(){
        IStatement program = new CompoundStatement(
                new VariableDeclaration("v", new IntType()),
                new CompoundStatement(
                        new AssignStatement("v", new ValueExpression(new IntValue(4))),
                        new CompoundStatement(
                                new CompoundStatement(
                                        new WhileStatement(new RelationalExpression(
                                                new VariableExpression("v"),
                                                new ValueExpression(new IntValue(0)),
                                                5
                                        )),
                                        new CompoundStatement(
                                                new PrintStatement(new VariableExpression ("v")),
                                                new AssignStatement("v",
                                                        new ArithmeticExpression(
                                                                new VariableExpression("v"),
                                                                new ValueExpression(new IntValue(1)),
                                                                2
                                                        )
                                                )
                                        )
                                ),
                                new PrintStatement(new VariableExpression("v"))
                        )
                )
        );

        return program;
    }

    public static IStatement Program5(){
        // Ref int v; new(v,20); Ref Ref int a; new(a, v); print(v); print(a);
        IStatement program =
                new CompoundStatement(
                        new VariableDeclaration("v", new ReferenceType(new IntType())),
                        new CompoundStatement(
                                new NewStatement("v", new ValueExpression(new IntValue(20))),
                                new CompoundStatement(
                                        new VariableDeclaration("a", new ReferenceType(new ReferenceType(new IntType()))),
                                        new CompoundStatement(
                                                new NewStatement("a", new VariableExpression("v")),
                                                new CompoundStatement(
                                                        new PrintStatement(new VariableExpression("v")),
                                                        new PrintStatement(new VariableExpression("a"))
                                                )
                                        )
                                )
                        )
                );
        return program;
    }

    public static IStatement Program6(){
        IStatement program =
                new CompoundStatement(
                        new VariableDeclaration("v", new ReferenceType(new IntType())),
                        new CompoundStatement(
                                new NewStatement("v", new ValueExpression(new IntValue(20))),
                                new CompoundStatement(
                                        new VariableDeclaration("a", new ReferenceType(new ReferenceType(new IntType()))),
                                        new CompoundStatement(
                                                new NewStatement("a", new VariableExpression("v")),
                                                new CompoundStatement(
                                                        new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                                                        new PrintStatement(new ArithmeticExpression(new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a"))), new ValueExpression(new IntValue(5)), 1))
                                                )
                                        )
                                )
                        )
                );
        return program;
    }

    public static IStatement Program7(){
        IStatement program = new CompoundStatement(
                new VariableDeclaration("v", new ReferenceType(new IntType())),
                new CompoundStatement(
                        new NewStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                                new CompoundStatement(
                                        new WriteHeapStatement("v", new ValueExpression(new IntValue(30))),
                                        new PrintStatement(new ArithmeticExpression(new ReadHeapExpression(new VariableExpression("v")), new ValueExpression(new IntValue(5)), 1))
                                )
                        )
                )
        );
        return program;
    }

    public static IStatement Program8(){
        IStatement childProgram = new CompoundStatement(
                new WriteHeapStatement("a", new ValueExpression(new IntValue(30))),
                new CompoundStatement(
                        new AssignStatement("v", new ValueExpression(new IntValue(32))),
                        new CompoundStatement(
                                new PrintStatement(new VariableExpression("v")),
                                new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
                        )
                )
        );
        IStatement program = new CompoundStatement(
                new VariableDeclaration("v", new IntType()),
                new CompoundStatement(
                        new VariableDeclaration("a", new ReferenceType(new IntType())),
                        new CompoundStatement(
                                new AssignStatement("v", new ValueExpression(new IntValue(10))),
                                new CompoundStatement(
                                        new NewStatement("a", new ValueExpression(new IntValue(22))),
                                        new CompoundStatement(
                                                new ForkStatement(childProgram),
                                                new CompoundStatement(
                                                        new PrintStatement(new VariableExpression("v")),
                                                        new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
                                                )
                                        )
                                )

                        )
                )
        );
        return program;
    }
}
