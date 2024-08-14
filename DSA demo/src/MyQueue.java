public class MyQueue<E>{
    private static class Node<T>{
        T data ;
        Node<T> next;
        public Node(T data){
            this.data = data;
        }
    }
    private Node<E> head;
    private Node<E> tail;
    private int size = 0 ;
    public MyQueue(){
    }
    public void enqueue(E object){
        if(object != null){
            Node<E> newNode = new Node<>(object);
            if(size > 0)
                tail.next = newNode;
            else
                head = newNode;
            tail = newNode;
            size++;
        }
    }
    public E dequeue(){
        E removed = null;
        if(size > 0){
            removed = head.data;
            head = head.next;
            size--;
        }
        return removed;
    }
    public E front(){
        if(size > 0)
            return head.data;
        return null;
    }
    public boolean isEmpty(){
        return size == 0;
    }
    public int size(){ return size;}
    @Override
    public String toString(){
        StringBuilder str = new StringBuilder("[ ");
        Node<E> temp = head;
        while(head != null) {
            str.append(head.data).append(" ,");
            head = head.next;
        }
        return str.substring(0,str.length()-1) + "]";
    }
}
