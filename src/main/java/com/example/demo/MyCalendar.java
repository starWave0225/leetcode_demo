package com.example.demo;

// import java.util.HashMap;
import java.util.TreeMap;

public class MyCalendar {
    // HashMap<Integer, Integer> startMap = new HashMap<>();
    TreeMap<Integer, Integer> calendar;

    public MyCalendar() {
        calendar = new TreeMap<>();
    }

    public boolean book(int start, int end) {
        // for(int startP: startMap.keySet()){
        // int endP = startMap.get(startP);
        // if (start < endP && end > startP) {
        // return false; // 重叠
        // }
        // }
        // startMap.put(start, end);
        // return true;

        // 找到 start 之前最近的预约
        Integer prevStart = calendar.floorKey(start);
        // 找到 start 之后最近的预约
        Integer nextStart = calendar.ceilingKey(start);

        // 检查前一个预订是否和当前预订重叠
        if (prevStart != null && calendar.get(prevStart) > start) {
            return false;
        }

        // 检查后一个预订是否和当前预订重叠
        if (nextStart != null && nextStart < end) {
            return false;
        }

        // 没有冲突，添加预订
        calendar.put(start, end);
        return true;
    }
}