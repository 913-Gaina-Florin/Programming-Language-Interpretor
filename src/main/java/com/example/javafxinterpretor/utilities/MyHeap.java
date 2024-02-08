package com.example.javafxinterpretor.utilities;

import java.util.HashMap;
import java.util.Map;

public class MyHeap<V> implements MyIHeapDictionary<V>{

    Map<Integer, V> dictionary;
    static Integer currentAddress;

    public MyHeap(){
        this.dictionary = new HashMap<>();
        currentAddress = 0;
    }
    @Override
    public Map<Integer, V> getMap() {
        return this.dictionary;
    }

    @Override
    public void add(V Value) {
        currentAddress++;
        this.dictionary.put(currentAddress, Value);
    }

    public Integer getCurrentAddress(){
        return currentAddress;
    }

    @Override
    public boolean isDefined(Integer key) {
        return this.dictionary.containsKey(key);
    }

    @Override
    public V getValue(Integer key) {
        return this.dictionary.get(key);
    }

    @Override
    public void update(Integer key, V value) {
        this.dictionary.put(key, value);
    }

    @Override
    public void delete(Integer key) {
        this.dictionary.remove(key);
    }

    @Override
    public void setContent(Map<Integer, V> newMap) {
        this.dictionary = newMap;
    }

    @Override
    public String toString() {
        return " dictionary = { " + this.dictionary + " } ";
    }
}
