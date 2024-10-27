package com.example.demo;

class MyCircularDeque {

    int[] array;
    int front = 0, rear = 0, size = 0, maxSize = 0;

    public MyCircularDeque(int k) {
        array = new int[k];
        maxSize = k;
    }

    public boolean insertFront(int value) {
        if (isFull())
            return false;
        front = (front - 1 + maxSize) % maxSize;
        array[front] = value;
        size++;
        return true;
    }

    public boolean insertLast(int value) {
        if (isFull())
            return false;
        array[rear] = value;
        rear = (rear + 1) % maxSize;
        size++;
        return true;
    }

    public boolean deleteFront() {
        if (isEmpty())
            return false;
        this.front = (front + 1) % maxSize;
        size--;
        return true;
    }

    public boolean deleteLast() {
        if (isEmpty())
            return false;
        rear = (rear - 1 + maxSize) % maxSize;
        size--;
        return true;
    }

    public int getFront() {
        if (isEmpty()) {
            return -1;
        }
        return array[front];
    }

    public int getRear() {
        if (isEmpty()) {
            return -1;
        }
        return array[(rear - 1 + maxSize) % maxSize];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == maxSize;
    }
}

/**
 * Your MyCircularDeque object will be instantiated and called as such:
 * MyCircularDeque obj = new MyCircularDeque(k);
 * boolean param_1 = obj.insertFront(value);
 * boolean param_2 = obj.insertLast(value);
 * boolean param_3 = obj.deleteFront();
 * boolean param_4 = obj.deleteLast();
 * int param_5 = obj.getFront();
 * int param_6 = obj.getRear();
 * boolean param_7 = obj.isEmpty();
 * boolean param_8 = obj.isFull();
 */