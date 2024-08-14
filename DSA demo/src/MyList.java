import java.util.Collection;
import java.util.Iterator;

public interface MyList <E>  extends Collection<E> {
    void add(int index , E object);
    E get(int index);

    /*Returns the index of the first matching element in this list, -1 otherwise */
    int indexOf(Object object);
    int lastIndexOf(E object);

    /*Removes the element at the specified index and returns the removed element.*/
    E remove(int index);

    /*Sets the element at the specified index and returns the element being replaced.*/
    E set(int index , E object);

    @Override
    default boolean add(E object){
        add(size() , object);
        return true;
    }
    @Override
    default boolean isEmpty(){
        return size() == 0;
    }
    @Override
    default Object[] toArray(){
        Object[] arr = new Object[size()];
        Iterator<E> it = this.iterator();
        for(int i =0 ;i<size() ; i++){
            arr[i] = it.next();
        }
        return arr;
    }

    @Override
    default <T> T[] toArray(T[] a) {
        if(a.length >= size()){
            Iterator<E> it = this.iterator();
            int i =0;
            for( ;i<size() ; i++)
                a[i] = (T) it.next();
            while (i<a.length){
                a[i++] = null;
            }
        }
        else{
            a = (T[]) toArray();
        }
        return a;
    }

    @Override
    default boolean remove(Object o){
        int index = indexOf(o);
        if(index >= 0 ) {
            remove(index);
            return true;
        }
        return false;
    }
    @Override
    default boolean containsAll(Collection<?> c){
        for(Object e : c)
            if(!contains(e))
                return false;

        return true;
    }

    @Override
    default boolean addAll(Collection<? extends E> c) {
        int sizeBefore = size();
        for(E e : c){
            add(e);
        }
        return !(sizeBefore == size());
    }

    @Override
    default boolean removeAll(Collection<?> c) {
        int sizeBefore = size();
        for(Object e : c)
            remove(e);
        return !(sizeBefore == size());
    }

    @Override
    default boolean retainAll(Collection<?> c) {
        int sizeBefore = size();

        for(Object o : this){
            if(!c.contains(o))
                remove(o);
        }
        return  !(sizeBefore == size());
    }
}
