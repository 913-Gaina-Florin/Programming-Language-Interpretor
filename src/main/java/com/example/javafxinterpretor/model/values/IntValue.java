package com.example.javafxinterpretor.model.values;

import com.example.javafxinterpretor.model.types.IntType;
import com.example.javafxinterpretor.model.types.Type;

public class IntValue implements Value{
    private final int value;
    @Override
    public Type getType() {
        return new IntType();
    }

    public IntValue(int val){
        this.value = val;
    }

    public int getVal(){
        return this.value;
    }

    public String toString(){
        return "int = " + Integer.toString(this.value);
    }
}
