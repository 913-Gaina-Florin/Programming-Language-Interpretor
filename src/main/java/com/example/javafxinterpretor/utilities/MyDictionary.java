package com.example.javafxinterpretor.utilities;

import java.util.HashMap;
import java.util.Map;

public class MyDictionary<K, V> implements MyIDictionary<K, V>{
    protected Map<K,V> dictionary;

    public MyDictionary(){
        dictionary = new HashMap<>();
    }

    @Override
    public Map<K, V> getMap() {
        return this.dictionary;
    }

    @Override
    public boolean isDefined(K key) {
        return dictionary.containsKey(key);
    }

    public void remove(K key) {
        this.dictionary.remove(key);
    }

    public Map<K, V> cloneMap(){
        return new HashMap<>(this.dictionary);
    }

    @Override
    public void add(K key, V value) {
        dictionary.put(key, value);
    }

    @Override
    public V lookUp(K key) {
        return dictionary.get(key);
    }

    @Override
    public void update(K key, V value) {
        dictionary.put(key, value);
    }

    public void setMap(Map<K, V> newMap){
        this.dictionary = newMap;
    }

    public MyIDictionary<K, V> deepCopy(){
        MyIDictionary<K, V> copy = new MyDictionary<>();
        copy.setMap(this.cloneMap());
        return copy;
    }

    @Override
    public String toString() {
        return "dictionary= { " + dictionary +
                " }";
    }
}
