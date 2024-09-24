package com.example.demo;

public class Trie {
    Trie[] children;

    Trie() {
        this.children = new Trie[26];
    }

    Trie(int len) {
        this.children = new Trie[len];
    }

}
