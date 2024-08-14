import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MyBinaryTree <E> {
    private static class TreeNode<T> {
        private TreeNode<T> left;
        private TreeNode<T> right;
        private T data;

        public TreeNode(T data) {
            this.data = data;
        }
    }
    private TreeNode<E> root;
    private List<E> traversalResult = new LinkedList<>();

    public MyBinaryTree(){

    }
    public void insert(E object){
        TreeNode<E> newNode = new TreeNode<>(object);
        if(root==null) {
            root = newNode;
            return;
        }

        //level order traversal
        Queue<TreeNode<E>> queue = new LinkedList<>();
        queue.add(root);
        TreeNode<E> tmp ;

        while(!queue.isEmpty()){
             tmp = queue.poll();
             if(tmp.left == null){
                 tmp.left = newNode;
                 return;
             }
             else
                 queue.add(tmp.left);


            if(tmp.right == null){
                tmp.right = newNode;
                return;
            }
            else
                queue.add(tmp.right);

         }

    }
    public void remove(E key){
        //handle edge case if key is the root
        if(root == null) return;
        if(root.data.equals(key) && root.left == null && root.right == null){ root = null; return; }

        TreeNode<E> toBeDeleted = null;
        TreeNode<E> deepestRightMostParent ;

        Queue<TreeNode<E>> queue = new LinkedList<>();
        Queue<TreeNode<E>> parents = new LinkedList<>();
        int traversed = 0; //to know when to remove a parent
        queue.add(root);
        TreeNode<E> tmp = null; //at the end of the loop it will point to the deepest right most node

        while(!queue.isEmpty()){
            tmp = queue.poll();
            traversed++;

            if(tmp.data.equals(key))
                toBeDeleted = tmp;

            if(tmp.left != null ||tmp.right != null)
                parents.add(tmp);

            if(tmp.left != null)
                queue.add(tmp.left);

            if(tmp.right != null)
                queue.add(tmp.right);

            if(traversed%3 == 0)
                parents.poll();
        }

        if(toBeDeleted != null){
            toBeDeleted.data = tmp.data; //swap the data

            while(!parents.isEmpty()){ //delete the 'tmp' by making its parent points to null
                deepestRightMostParent = parents.poll();
                if(deepestRightMostParent.right == tmp)
                    deepestRightMostParent.right = null;
                else if(deepestRightMostParent.left == tmp)
                    deepestRightMostParent.left = null;

            }

        }
    }
    public List<E> preOrder(){
        if(!traversalResult.isEmpty())
            traversalResult.clear();

        TreeNode<E> rootCopy= root;
        preOrderRec(root);
        return traversalResult;
    }
    public List<E> levelOrder(){
        if(!traversalResult.isEmpty())
            traversalResult.clear();

        if(root == null) return traversalResult;

        Queue<TreeNode<E>> queue = new LinkedList<>();
        queue.add(root);
        TreeNode<E> tmp ;

        while(!queue.isEmpty()){
            tmp = queue.poll();
            traversalResult.add(tmp.data);

            if(tmp.left != null)
                queue.add(tmp.left);

            if(tmp.right != null)
                queue.add(tmp.right);
        }
        return traversalResult;
    }
    private void preOrderRec(TreeNode<E> root){
        if(root == null) return;

        traversalResult.add(root.data);
        preOrderRec(root.left);
        preOrderRec(root.right);
    }





}