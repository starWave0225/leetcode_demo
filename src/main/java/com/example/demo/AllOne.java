package com.example.demo;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import ch.qos.logback.core.joran.sanity.Pair;

class AllOne {
    Map<String, Integer> map = new HashMap<>();
    TreeSet<Pair<Integer, String>> countSet = new TreeSet<>((a, b) -> a.getKey().equals(b.getKey()) ? a.getValue().compareTo(b.getValue()) : a.getKey() - b.getKey());
    public AllOne() {
        
    }
    
    public void inc(String key) {
        map.put(key, map.getOrDefault(key, 0)+1);
        int n = map.get(key);
        if(n != 1){
            countSet.remove(new Pair<>(n-1, key));
        }
        countSet.add(new Pair<>(n, key));
    }
    
    public void dec(String key) {
        int count = map.get(key);
        countSet.remove(new Pair<>(count, key));
        if(count == 1){
            map.remove(key);
        }
        else{
            count--;
            map.put(key, count);
            countSet.add(new Pair<>(count, key));
        }
    }
    
    public String getMaxKey() {
        return countSet.isEmpty() ? "" : countSet.last().getValue();

    }
    
    public String getMinKey() {
        return countSet.isEmpty() ? "" : countSet.first().getValue();
    }
}

/**
 * Your AllOne object will be instantiated and called as such:
 * AllOne obj = new AllOne();
 * obj.inc(key);
 * obj.dec(key);
 * String param_3 = obj.getMaxKey();
 * String param_4 = obj.getMinKey();
 */