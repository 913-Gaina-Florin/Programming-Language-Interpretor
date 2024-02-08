package com.example.javafxinterpretor.model.types;

import com.example.javafxinterpretor.model.values.IntValue;
import com.example.javafxinterpretor.model.values.Value;

import java.util.Objects;

public class IntType implements Type{
    public boolean equals(Object obj){
        return obj instanceof IntType;
    }

    public String toString(){
        return "int";
    }

    @Override
    public Value defaultValue() {
        return new IntValue(0);
    }
}
