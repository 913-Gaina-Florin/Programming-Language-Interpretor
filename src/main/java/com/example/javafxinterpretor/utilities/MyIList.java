package com.example.javafxinterpretor.utilities;

import java.util.List;

public interface MyIList<T> {

    List<T> getList();
    void add(T itemToAdd);
}
