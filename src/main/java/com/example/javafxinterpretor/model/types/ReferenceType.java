package com.example.javafxinterpretor.model.types;

import com.example.javafxinterpretor.model.values.ReferenceValue;
import com.example.javafxinterpretor.model.values.Value;

public class ReferenceType implements Type{
    Type innerType;

    public ReferenceType(Type inner){
        this.innerType = inner;
    }

    public Type getInnerType(){
        return this.innerType;
    }

    public boolean equals(Object obj){
        if (obj instanceof ReferenceType)
            return innerType.equals(((ReferenceType) obj).getInnerType());
        return false;
    }
    @Override
    public Value defaultValue() {
        return new ReferenceValue(0, this.innerType);
    }

    public String toString(){
        return "Ref( " + this.innerType.toString() + ") ";
    }
}
