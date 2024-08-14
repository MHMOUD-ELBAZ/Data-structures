import java.util.Iterator;

public class MyArrayList<E> implements MyList<E>{
    public static final int INITIAL_CAPACITY = 16;
    private E[] data = (E[]) new Object[INITIAL_CAPACITY];
    private int size = 0;
    public MyArrayList() {

    }
    public MyArrayList(E[] objects) {
        for (E object : objects) add(object);
    }
    private void ensureCapacity(){
        if(size + 1 >= data.length){
            E[] tmp = (E[]) new Object[data.length*2];
            if (size >= 0) System.arraycopy(data, 0, tmp, 0, size);
            data = tmp;
        }

    }
    private void checkIndex(int index) {
        if(index >= size || index<0)
            throw new IndexOutOfBoundsException("index "+index + ", size " + size);
    }
    @Override
    public void add(int index, E object) {
        if(index > size || index<0)
            throw new IndexOutOfBoundsException("index "+index + ", size " + size);

        ensureCapacity();
        size++;

        for(int i = size ; i>index;i--)
            data[i] = data[i-1];

        data[index] = object;
    }

    @Override
    public E get(int index) {
        checkIndex(index);
        return data[index];
    }

    @Override
    public int indexOf(Object object) {
        for(int i=0;i<size;i++){
            if(object.equals(data[i]))
                return i;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(E object) {
        for(int i=size-1;i>=0;i--){
            if(object.equals(data[i]))
                return i;
        }
        return -1;
    }

    @Override
    public E remove(int index) {
        checkIndex(index);
        E removed  = data[index];
        size--;
        for(int i=index;i<size;i++){
            data[i] = data[i+1];
        }

        return removed;
    }

    @Override
    public E set(int index, E object) {
        checkIndex(index);
        E replaced = data[index];
        data[index] = object;
        return replaced;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(Object o) {
        for (int i=0;i<size;i++)
            if(data[i].equals(o))
                return true;
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new ArrayListIterator();
    }
    private class ArrayListIterator implements Iterator<E>{
        private int current = 0;
        @Override
        public boolean hasNext() {
            return current < size;
        }

        @Override
        public E next() {
            return data[current++];
        }

        // Remove the element returned by the last next()
        @Override
        public void remove() {
            if(current !=0)
                MyArrayList.this.remove(--current);
        }
    }

    @Override
    public void clear() {
        data =  (E[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    @Override
    public String toString(){
        String str = "[ ";
        for(int i=0;i<size;i++)
            str += data[i] +" ,";

        return str.substring(0,str.length()-1) + "]";
    }
}
