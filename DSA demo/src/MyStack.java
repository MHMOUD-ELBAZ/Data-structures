public class MyStack <E> {
    private static class Node<T>{
        T data;
        Node<T> next;
        public Node(T data){
            this.data = data;
        }
    }
    private Node<E> head;
    private int size;
    public MyStack(){
    }
    public void push(E object){
        if(object != null){
            Node<E> newNode = new Node<>(object);
            newNode.next = head;
            head = newNode;
            size++;
        }
    }
    public E pop(){
        E removed = null;
        if(size > 0){
            removed = head.data;
            head = head.next;
            size--;
        }
        return removed;
    }
    public E peek(){
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
