import java.util.ArrayList;
import java.util.List;

public class MaxHeap<E extends Comparable<E>>{
    private List<E> list = new ArrayList<>();
    public MaxHeap(){}
    public MaxHeap(E[] array){
        for(E o : array)
            if(o != null)
                add(o);
    }
    /*Adds a new element to the heap and ensures the heap property by swapping elements up the tree */
    public void add(E newObject){
        list.add(newObject);
        int newObjectIndex = list.size()-1;
        int parent = parent(newObjectIndex);

        E temp;
        while(parent >= 0 && newObject.compareTo(list.get(parent))>0){
            temp = list.get(parent);
            list.set(parent,newObject);
            list.set(newObjectIndex,temp);
            newObjectIndex = parent;
            parent = parent(newObjectIndex);
        }
    }
    public E removeRoot(){
        E removed = list.getFirst();
        list.set(0, list.getLast());
        list.removeLast();

        heapify(0);

        return  removed;
    }
    public int getSize(){
        return list.size();
    }
    public boolean isEmpty(){ return list.isEmpty(); }
    private void heapify(int root){
        int leftChild , rightChild,largest = root;

        while(root<list.size()){
            leftChild = leftChild(root);
            rightChild = rightChild(root);

            if(leftChild< list.size() && list.get(leftChild).compareTo(list.get(largest))>0)
                largest = leftChild;
            if(rightChild< list.size() && list.get(rightChild).compareTo(list.get(largest))>0)
                largest = rightChild;

            if(largest != root){
                E tmp = list.get(largest);
                list.set(largest , list.get(root));
                list.set(root , tmp);
                root = largest;
            }
            else
                break;
        }
    }
    private int parent(int i){
        return (i-1) / 2 ;
    }
    private int leftChild(int i){
        return 2*i +1 ;
    }
    private int rightChild(int i){
        return 2*i +2 ;
    }

}
