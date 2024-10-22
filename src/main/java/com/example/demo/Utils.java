package com.example.demo;

public class Utils {
    public int[] kmp(String str, String pattern) {
        String newStr = pattern + '#' + str;
        int[] pi = new int[newStr.length()];
        int i = 1, k = 0;
        while (i < newStr.length()) {
            if (newStr.charAt(i) == newStr.charAt(k)) {
                k++;
                pi[i] = k;
                i++;
            } else {
                if (k > 0) {
                    k = pi[k - 1];
                } else {
                    pi[i] = 0;
                    i++;
                }
            }
        }
        return pi;
    }
}
