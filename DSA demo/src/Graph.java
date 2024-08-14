import java.util.*;


public class Graph<T> {
    //private List<T> vertices ;
    private class Vertex<E> {

    }
    private HashMap<T,Integer> vertexNumber;
    private List<List<Integer>> adjList; //fast index retrieval
    private boolean[] visited;
    public Graph(){
        adjList = new ArrayList<>();
        vertexNumber = new HashMap<>();
    }

    public void addVertex(T v){
        if(!vertexNumber.containsKey(v)) {
            vertexNumber.put(v , vertexNumber.size());
            adjList.add(new ArrayList<>());
        }
    }
    public void addEdge(T u , T v){
        Integer uIndex = vertexNumber.get(u);
        Integer vIndex = vertexNumber.get(v);

        if(uIndex == null || vIndex == null) return ;

        adjList.get(uIndex).add(vIndex);
        adjList.get(vIndex).add(uIndex);
    }
    public void removeEdge(T u , T v){
        Integer uIndex = vertexNumber.get(u);
        Integer vIndex = vertexNumber.get(v);

        if(uIndex == null || vIndex == null) return ;

        adjList.get(uIndex).remove(vIndex);
        adjList.get(vIndex).remove(uIndex);
    }
    public void DFS(T u){
        Integer uIndex = vertexNumber.get(u);
        if(uIndex == null) return ;


        visited = new boolean[vertexNumber.size()];
        Deque<Integer> stack = new LinkedList<>();

        stack.push(uIndex);
        visited[uIndex] = true;
        int vertex;

        while(!stack.isEmpty()){
            vertex = stack.removeFirst();

            System.out.print(vertex + " ");

            for(Integer neighbor : adjList.get(vertex)){
                if(!visited[neighbor]) {
                    stack.addFirst(neighbor);
                    visited[neighbor] = true;
                }
            }
        }
        System.out.println();
    }
    public void BFS(T u){
        Integer uIndex = vertexNumber.get(u);
        if(uIndex == null) return ;


        visited = new boolean[vertexNumber.size()];
        Queue<Integer> q = new LinkedList<>();

        q.offer(uIndex);
        visited[uIndex] = true;
        int vertex;

        while(!q.isEmpty()){
            vertex = q.poll();
            System.out.println(vertex +" ");
            for(Integer neighbor : adjList.get(vertex)){
                if(!visited[neighbor]) {
                    q.offer(neighbor);
                    visited[neighbor] = true;
                }
            }
        }

        System.out.println();
    }

    /**
     * check if there is a path between source and target*/
    public boolean isAccessible(T source ,T target){
        Integer uIndex = vertexNumber.get(source);
        Integer vIndex = vertexNumber.get(target);
        if(uIndex == null || vIndex == null) return false;

        Queue<Integer> q =new LinkedList<>();
        visited = new boolean[vertexNumber.size()];
        q.add(uIndex);
        visited[uIndex] = true;


        while(!q.isEmpty()){
            uIndex = q.poll();

            for(int neighbor : adjList.get(uIndex) ){
                if(neighbor == vIndex) return true;
                if(!visited[neighbor]){
                    q.add(neighbor);
                    visited[neighbor] = true;
                }
            }
        }

        return false;
    }

    /**
     * uses modified recursive DFS to check if it's cyclic or not.
     * any graph is cyclic if any vertex has any visited neighbor other than it's parent */
    public boolean isCyclic(){
        visited = new boolean[vertexNumber.size()];

        //to handle disconnected graphs
        for (int i = 0; i < vertexNumber.size(); i++) {
            if (!visited[i]) {
                if (isCyclic(i, -1)) {
                    return true;
                }
            }
        }

        return false;
    }
    private boolean isCyclic(int u,int parent){
        visited[u] = true;

        for(int neighbor : adjList.get(u))
        {
            if(visited[neighbor] && neighbor != parent)
                return true;
            else if(!visited[neighbor])
                if(isCyclic(neighbor,u)) return true;
        }

        return false;
    }

}
