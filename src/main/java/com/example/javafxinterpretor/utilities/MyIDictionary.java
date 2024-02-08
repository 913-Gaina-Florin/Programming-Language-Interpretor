package com.example.javafxinterpretor.utilities;

import java.util.Map;

public interface MyIDictionary<K, V> {

    Map<K, V> getMap();

    void setMap(Map<K, V> newMap);

    MyIDictionary<K, V> deepCopy();
    boolean isDefined(K key);

    void add(K key, V value);

    V lookUp(K key);

    void update(K key, V value);

    void remove(K key);
}
