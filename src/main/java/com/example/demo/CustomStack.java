package com.example.demo;

class CustomStack {
    int[] array;
    int top = 0;
    int size;

    public CustomStack(int maxSize) {
        this.array = new int[maxSize];
        size=maxSize;
    }
    
    public void push(int x) {
        if(top == size) return;
        array[top] = x;
        top++;
    }
    
    public int pop() {
        if(top == 0) return -1;
        return array[--top];
    }
    
    public void increment(int k, int val) {
        if(k > size) k =size;
        if(k > top) k = top;
        for(int i =0;i<k;i++){
            array[i]+=val;
        }
    }
}

/**
 * Your CustomStack object will be instantiated and called as such:
 * CustomStack obj = new CustomStack(maxSize);
 * obj.push(x);
 * int param_2 = obj.pop();
 * obj.increment(k,val);
 */
