package com.example.javafxinterpretor.utilities;

import com.example.javafxinterpretor.model.values.Value;

public class Pair<F, S> {
    private F first;
    private S second;

    public Pair(F first, S second){
        this.first = first;
        this.second = second;
    }

    public F getFirst() { return this.first; }

    public S getSecond() {
        return this.second;
    }
}
