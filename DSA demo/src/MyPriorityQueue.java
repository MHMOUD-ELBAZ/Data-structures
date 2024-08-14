public class MyPriorityQueue<E extends Comparable<E>> {
    private MaxHeap<E> heap = new MaxHeap<>() ;
    public MyPriorityQueue(){}
    public void enqueue(E object){
        heap.add(object);
    }
    public E dequeue(){ return heap.removeRoot(); }
    public int size(){ return heap.getSize();}
    public boolean isEmpty(){ return heap.isEmpty(); }
}
