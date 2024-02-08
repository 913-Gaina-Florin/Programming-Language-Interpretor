package com.example.javafxinterpretor.model.values;

import com.example.javafxinterpretor.model.types.StringType;
import com.example.javafxinterpretor.model.types.Type;

public class StringValue implements Value{
    private final String value;

    public StringValue(String val){
        this.value = val;
    }

    public String getValue(){
        return this.value;
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    public String toString(){
        return "string= " + this.value;
    }
}
