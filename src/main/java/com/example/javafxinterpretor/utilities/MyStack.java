package com.example.javafxinterpretor.utilities;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class MyStack<T> implements  MyIStack<T>{

    Stack<T> stack;

    public MyStack(){
        stack = new Stack<T>();
    }
    @Override
    public void push(T elem) {
        stack.push(elem);
    }

    @Override
    public T pop() {
        return stack.pop();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public List<T> getReverse() {
        List<T> auxList = Arrays.asList((T[])stack.toArray());
        Collections.reverse(auxList);
        return auxList;
    }

    @Override
    public String toString() {
        return "Stack: { " + this.getReverse() + " }";
    }

    /*
    public String toString() {
        return "Stack: { " + this.stack + " } ";
    }

     */
}
