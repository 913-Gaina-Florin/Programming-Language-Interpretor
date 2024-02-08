package com.example.javafxinterpretor.utilities;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements  MyIList<T>{
    List<T> list;

    public MyList(){
        list = new ArrayList<>();
    }

    @Override
    public List<T> getList() {
        return this.list;
    }

    @Override
    public void add(T itemToAdd) {
        list.add(itemToAdd);
    }

    @Override
    public String toString() {
        return "List{ " + this.list + " }";
    }
}
