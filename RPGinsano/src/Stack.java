class Stack<T> {
    private Node<T> top;
    private int size = 0;
    
    private static class Node<T> {
        T data;
        Node<T> next;
        
        Node(T data) {
            this.data = data;
        }
    }
    
    public void push(T item) {
        Node<T> newNode = new Node<>(item);
        newNode.next = top;
        top = newNode;
        size++;
    }
    
    public T pop() {
        if (top == null) {
            throw new IllegalStateException("Stack is empty");
        }
        
        T item = top.data;
        top = top.next;
        size--;
        return item;
    }
    
    public boolean isEmpty() {
        return top == null;
    }
    
    public int size() {
        return size;
    }
}