/**
 *  this class implements a hash table and uses double hashing to deal with collisions
 *  if i = hash(key) then double hashing is to search in ( i + j*secondaryHash(key) ) % N
 *  where j = 0,1,2,3,...,N-1 and preserving N to be an exact power of 2 and secondaryHash always odd
 * */

public class MyHashMap<K,V> {
    private int N ; //capacity
    private int size = 0 ;
    private final double loadFactorThreshold ;
    private double loadFactor ;
    private static class Entry<K,V>{
        K key;
        V value ;
        public Entry(K _key,V _value){
            key = _key;
            value = _value;
        }
        @Override
        public String toString(){
            return "key = " + key + " value = " + value;
        }
    }
    private Entry<K,V>[] data  ;

    public MyHashMap(){ this(4,0.75); }
    public MyHashMap(int capacity, double loadFactorThreshold){
        N = 1;
        while(N < capacity) N <<= 2;
        this.loadFactorThreshold =loadFactorThreshold;
        data = new Entry[N];
    }

    public void put(K key, V value){
        if(key ==null || value == null)
            throw new NullPointerException("can't insert a null keys or values");

        if(loadFactor > loadFactorThreshold)
            rehash();

        Entry<K,V> newEntry = new Entry<>(key,value);
        int index,i = hash(key), secondaryHash = secondaryHash(key);

        for(int j =0 ;j<N;j++){
            index = (i + j*secondaryHash) % N;

            if(data[index] == null) {
                data[index] = newEntry;
                size++;
                loadFactor = (double) size / N;
                break ;
            }
            else if(data[index].key.equals(key))
                data[index].value = value;
        }

    }
    public boolean contains(K key){
        return get(key) != null;
    }
    public V get(K key){
        int i = hash(key) , secondary = secondaryHash(key),index ;

        for(int j=0;j<N;j++){
            index = (i + j*secondary) %N ;
            if(data[index] == null )
                return null;
            else if (data[index].key.equals(key) )
                return data[index].value;
        }

        return null;
    }
    public boolean remove(K key){
        int i = hash(key) , secondary = secondaryHash(key),index ;

        for(int j=0;j<N;j++){
            index = (i + j*secondary) %N ;
            if(data[index] == null )
                return false;
            else if (data[index].key.equals(key) ) {
                data[index] = null;
                return true;
            }
        }

        return false;
    }
    public int size() {
        return size;
    }

    /**
     * For Compressing Hash Codes, it takes a hash code and return a valid index in the array
     * */
    private int hash(K key){
        return supplementalHash(key.hashCode()) & (N-1);}
    private static int supplementalHash(int h) {
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }
    /**
     * since we use open addressing (Double Hashing) to deal with collisions, we need a secondary hash
     * function with a return value > 0
     * */
    private int secondaryHash(K key){
        return ((N/2 + 1) - key.hashCode() % (N/2 +1) | 1 );//bitwise or guarantee that its always odd number
    }
    /**
     * this function copy the data to a new larger array
     * */
    private void rehash(){
        N <<= 1 ; //same as *= 2
        Entry<K,V>[] dataCopy = data;
        data  = new Entry[N];
        size = 0;
        loadFactor = 0;
        for (Entry<K,V>entry : dataCopy) {
            if(entry != null)
                put(entry.key, entry.value);
        }

    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        for (Entry<K,V>entry : data)
            s.append(entry).append("\n");

        return s.toString();
    }
}
