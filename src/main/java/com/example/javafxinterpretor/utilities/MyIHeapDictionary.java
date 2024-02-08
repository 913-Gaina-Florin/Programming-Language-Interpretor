package com.example.javafxinterpretor.utilities;

import java.util.Map;

public interface MyIHeapDictionary<V> {
    public Map<Integer, V> getMap();

    public void add(V Value);

    public Integer getCurrentAddress();

    public boolean isDefined(Integer key);

    public V getValue(Integer key);

    public void update(Integer key, V value);

    public void delete(Integer key);

    public void setContent(Map<Integer, V> newMap);
}
