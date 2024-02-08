package com.example.javafxinterpretor.model.types;

import com.example.javafxinterpretor.model.values.BoolValue;
import com.example.javafxinterpretor.model.values.Value;

public class BoolType implements Type{
    public boolean equals(Object obj){
        return obj instanceof BoolType;
    }

    public String toString(){
        return "bool";
    }

    @Override
    public Value defaultValue() {
        return new BoolValue(false);
    }
}
