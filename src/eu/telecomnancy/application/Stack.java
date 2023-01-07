package eu.telecomnancy.application;

import java.util.ArrayList;

public class Stack<E> {

    /**
     * The stack
     * 
     * @param <E> the type of the stack
     */
    private ArrayList<E> stack;

    /**
     * Constructor for the Stack class
     */
    public Stack() {
        this.stack = new ArrayList<E>();
    }

    /**
     * Check if the stack is empty
     */
    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    /**
     * Stack<Card> cards
     * Push a value in the stack if it's not already in it
     */
    public void push(E value) {
        for (int i = 0; i < this.stack.size(); i++) {
            if (this.stack.get(i).equals(value)) {
                return;
            }
        }
        this.stack.add(value);
    }

    /**
     * Pop a value from the stack
     */
    public E pop() {
        if (this.stack.isEmpty()) {
            return null;
        }

        E value = this.stack.get(this.stack.size() - 1);
        this.stack.remove(this.stack.size() - 1);
        return value;
    }

    /**
     * Shuffle the stack
     */
    public void shuffle() {
        ArrayList<E> shuffled = new ArrayList<>();

        while (!this.stack.isEmpty()) {
            int index = (int) (Math.random() * this.stack.size());
            shuffled.add(this.stack.get(index));
            this.stack.remove(index);
        }

        this.stack = shuffled;
    }

    /**
     * Get the size of the stack
     */
    public int size() {
        return this.stack.size();
    }

    /**
     * Convert the stack to a string
     */
    public String toString() {
        if (this.stack.isEmpty()) {
            return "[]";
        } else {
            String s = "[";
            for (int i = 0; i < this.stack.size() - 1; i++) {
                s += this.stack.get(i) + ", ";
            }
            s += this.stack.get(this.stack.size() - 1);
            s += "]";
            return s;
        }
    }

    /**
     * Get the value at the given index
     */
    public E get(int index) {
        if (index < 0 || index >= this.stack.size()) {
            return null;
        } else {
            return this.stack.get(index);
        }
    }

    /**
     * Remove the value at the given index
     */
    public E remove(int index) {
        if (index < 0 || index >= this.stack.size()) {
            return null;
        } else {
            return this.stack.remove(index);
        }
    }
    
    public void putBack(E value) {
        this.stack.add(0, value);
    }

    public void clear() {
        this.stack.clear();
    }

    public void moveToBack(int index) {
        E value = this.stack.remove(index);
        this.stack.add(0, value);
    }

}
