package com.example.javafxinterpretor.model.values;

import com.example.javafxinterpretor.model.types.BoolType;
import com.example.javafxinterpretor.model.types.Type;

public class BoolValue implements Value{
    private final boolean value;
    @Override
    public Type getType() {
        return new BoolType();
    }

    public BoolValue(boolean val){
        this.value = val;
    }

    public boolean getValue(){
        return this.value;
    }

    public String toString(){
        return "bool = " + Boolean.toString(value);
    }
}
