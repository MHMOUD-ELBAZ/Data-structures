import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MyBST<E extends Comparable<E>> {
    protected static class TreeNode<T> {
        protected TreeNode<T> left;
        protected TreeNode<T> right;
        protected T data;

        public TreeNode(T data) {
            this.data = data;
        }
    }
    protected TreeNode<E> root;
    private int size = 0;
    private List<E> path = new LinkedList<>();
    public MyBST() {

    }
    public boolean insert(E element) {

        TreeNode<E> newNode = new TreeNode<>(element);
        if (root == null) {
            root = newNode;
            size++;
            return true;
        }

        TreeNode<E> current = root;
        while (current != null) {
            //compare the new element with the current node's data
            if (element.compareTo(current.data) >= 0) {
                // Insert to the right if the new element is greater than or equal to the current node's data
                if (current.right == null) {
                    current.right = newNode;
                    size++;
                    return true;
                } else {
                    current = current.right;
                }
            } else {
                //insert to the left if the new element is less than the current node's data
                if (current.left == null) {
                    current.left = newNode;
                    size++;
                    return true;
                } else {
                    current = current.left;
                }
            }
        }

        return false;
    }
    public boolean search(E element){
        TreeNode<E> current = root;

        while(current != null){
            if(current.data.compareTo(element)==0)
                return true;
            else if(current.data.compareTo(element)>0)
                current  = current.left;
            else current = current.right;
        }

        return false;
    }
    public boolean delete(E element){
        if(root == null) return false;

        TreeNode<E> current = root; //the current is node to be deleted
        TreeNode<E> parent = null; //parent of the node to be deleted

        //loop to make current points to the node to be deleted
        while(current != null){
            if(current.data.compareTo(element)==0)
                break;
            else if(current.data.compareTo(element)>0) {
                parent = current;
                current = current.left;
            }
            else {
                parent = current;
                current = current.right;
            }
        }

        //check if the node is found or not
        if(current==null || current.data.compareTo(element)!=0) return false;

        //case 1 : no children --> make the current's parent points to null
        if(current.left == null && current.right == null){
            if(parent == null) root = null; //it's the root node
            else if(parent.left == current) parent.left = null;
            else parent.right = null;
        }
        //case 2 : one right child --> make the parent point to the right child of the current
        else if(current.left == null ){ //
            if(parent == null) root = current.right;
            else if(parent.left == current) parent.left = current.right;
            else if(parent.right == current)  parent.right = current.right;
        }
        //case 2 : one left child --> make the parent point to the left child of the current
        else if(current.right == null ){
            if(parent == null) root = current.left;
            else if(parent.left == current) parent.left = current.left;
            else if(parent.right == current)  parent.right = current.left;
        }
        //case 3 : two children --> swap successor's data with current and delete the successor
        else{
            TreeNode<E> successor = deleteSuccessor(current);
            current.data = successor.data;
        }

        size--;
        return true;
    }
    public void inOrder(){
        // left --> root --> right
        TreeNode<E> r = root;
        inOrder(r);
        System.out.println(path);
    }

    /**
     *  Visits nodes level by level. First the root is visited,
     *  then all the children of the root from left to right,
     *  then the grandchildren of the root from left to right, and so on.
     */
    public void BFS(){
        if(root == null) return;

        //to hold the children of the current until traversing the other children of current's parent
        Queue<TreeNode<E>> queue = new LinkedList<>();
        queue.offer(root);
        TreeNode<E> current ;//to reference the first node in the queue

        while(!queue.isEmpty()){
            current = queue.poll();
            System.out.print(current.data+" ");

            if(current.left != null) queue.offer(current.left);
            if(current.right != null) queue.offer(current.right);
        }

        System.out.println();
    }
    public void preOrder() {
        if (root == null) return;

        // stack to hold nodes for pre-order traversal
        Deque<TreeNode<E>> stack = new LinkedList<>();
        stack.addFirst(root);
        TreeNode<E> current; // reference to the current node that polled from the stack

        while (!stack.isEmpty()) {
            current = stack.removeFirst();
            System.out.print(current.data + " "); // visit the node

            // push right child first so that left child is processed first
            if (current.right != null) stack.addFirst(current.right);
            if (current.left != null) stack.addFirst(current.left);
        }
        System.out.println(); // move to the next line after traversal
    }

    /**
    * returns in-order traversal path as list of elements
    * */
    public List<E> inOrederList(){
        if(path.size() == size)
            return path;

        inOrder(root);
        return path;
    }
    public int getSize(){ return size; }

    /* helper methods */
    private void inOrder(TreeNode<E> root){
        if(root==null) return;

        inOrder(root.left);
        path.add(root.data);
        inOrder(root.right);
    }
    private TreeNode<E> deleteSuccessor(TreeNode<E> root){
        TreeNode<E> parent = root;
        TreeNode<E> successor = root.right;

        //locate the successor and it's parent
        while(successor.left != null){
            parent = successor;
            successor = parent.left;
        }

        //connect the parent of the successor to successor's right child
        if(parent.left == successor)
            parent.left = successor.right;
        else
            parent.right = successor.right;

        return successor;
    }

}
