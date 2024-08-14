import java.util.Iterator;

public class MyLinkedList <E> implements MyList<E>{
    private static class Node<T>{
        T data ;
        Node<T> next ;
        public Node(T data){
            this.data = data;
        }
    }
    Node<E> head ;
    Node<E> tail ;
    int size = 0;

    public MyLinkedList(){}
    public MyLinkedList(E[] elements){
        for(E element : elements)
            addLast(element);
    }

    public void addFirst(E object){
        if(object == null)
            throw new NullPointerException("can't add null");
        Node<E> newNode = new Node<>(object);
        newNode.next = head;
        head = newNode;

        if(tail == null)
            tail = head;
        size++;
    }
    public void addLast(E object){
        if(object == null)
            throw new NullPointerException("can't add null");
        Node<E> newNode = new Node<>(object);
        if(tail == null)
            head = newNode;
        else
            tail.next = newNode;

        tail = newNode;
        size++;
    }
    @Override
    public void add(int index, E object) {
        if(object == null)
            throw new NullPointerException("can't add null");
        if(index>size)
            throw new IndexOutOfBoundsException("index = " +index + "size = " +size);
        else if (index == 0) {
            addFirst(object);
            return;
        } else if (index == size) {
            addLast(object);
            return;
        }

        Node<E> current = head;
        Node<E> newNode = new Node<>(object);
        for(int i=0;i<index-1;i++){
            current = current.next;
        }
        newNode.next = current.next;
        current.next = newNode;
        size++;
    }
    public E getFirst(){
        if(size == 0)
            return null;
        else
            return head.data;
    }
    public E getLast(){
        if(size == 0)
            return null;
        else
            return tail.data;
    }
    @Override
    public E get(int index) {
        if(size == 0) return null;
        else if(index == 0)  return getFirst();
        else if(index == size-1) return getLast();
        else {
            Node<E> current = head;
            for(int i=0;i<index;i++) current = current.next;
            return current.data;
        }
    }
    @Override
    public int indexOf(Object object) {
        Node<E> tmp = head;
        for(int i=0;i<size;i++) {
            if(tmp.data.equals(object))
                return i;
            tmp = tmp.next;
        }
        return -1;
    }
    @Override
    public int lastIndexOf(E object) {
        Node<E> tmp = head;
        int index = -1 ;
        for(int i=0;i<size;i++) {
            if(tmp.data.equals(object))
                index = i;
            tmp = tmp.next;
        }
        return index;
    }
    public E removeFirst() {
        if(head != null){
            E removedVal = head.data;
            head = head.next;
            size--;

            if(head == null)  tail = null;
            return removedVal;
        }
        return null;
    }
    public E removeLast(){
        if(size == 0) return null;
        E removedVal = tail.data;
        if(size-- == 1) head = tail = null;
        else{
            //get the second last node
            Node<E> current  = head;
            while(current.next != tail)
                current = current.next;

            current.next = null;
            tail = current;
        }

        return removedVal;
    }
    @Override
    public E remove(int index) {
        if(size == 0 || index>= size) return null;
        if(index == 0) return removeFirst();
        if(index == size-1) return removeLast();

        Node<E> current = head;
        for(int i=0;i<index-1;i++)
            current = current.next;
        E removedVal = current.next.data;
        current.next = current.next.next;
        return removedVal;
    }
    @Override
    public E set(int index, E object) {
        if(size == 0 || index>= size) return null;
        Node<E> current = head;
        for(int i=0;i<index;i++){
            current = current.next;
        }
        E val = current.data;
        current.data = object;

        return val;
    }
    @Override
    public int size() {
        return size;
    }
    @Override
    public boolean contains(Object o) {
        return indexOf(o) != -1 ;
    }
    @Override
    public void clear() {
        head = tail = null;
        size =0;
    }
    @Override
    public String toString(){
        String result ="";
        Node<E> tmp = head;
        while(tmp != null) {
            result += tmp.data+" ,";
            tmp= tmp.next;
        }
        return result;
    }

    //enables you to use for-each loop and iterators
    @Override
    public Iterator<E> iterator() {
        return new LinkedListIterator();
    }
    private class LinkedListIterator implements Iterator<E> {
        private Node<E> current = head; // Current index
        @Override
        public boolean hasNext() {
            return (current != null);
        }
        @Override
        public E next() {
            E e = current.data;
            current = current.next;
            return e;
        }
        @Override
        public void remove() {
            if(current != null && current != head)
                MyLinkedList.this.remove(indexOf(current)-1);
        }
    }
}
