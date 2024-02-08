package com.example.javafxinterpretor.model.values;

import com.example.javafxinterpretor.model.types.ReferenceType;
import com.example.javafxinterpretor.model.types.Type;

public class ReferenceValue implements Value{
    int locationAddress;
    Type locationType;

    public ReferenceValue(int address, Type locationType){
        this.locationAddress = address;
        this.locationType = locationType;
    }

    public int getAddress(){
        return this.locationAddress;
    }

    @Override
    public Type getType() {
        return new ReferenceType(this.locationType);
    }

    @Override
    public String toString() {
        return "RefValue addr= " + this.locationAddress + " type= " + this.locationType;
    }
}
