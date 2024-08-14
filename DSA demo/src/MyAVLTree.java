import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class MyAVLTree <E extends Comparable<E>> {
    private static class AVLNode<T> {
        protected AVLNode<T> left;
        protected AVLNode<T> right;
        protected T data;
        protected int height = 0 ;
        public AVLNode(T data) {
            this.data = data;
        }
    }
    private int size =0;
    private AVLNode<E> root ;

    /*insert a new node and rebalance the path from the root to that new node*/
    public void insert(E element){
        AVLNode<E> newNode = new AVLNode<>(element);
        if (root == null) {
            root = newNode;
            size++;
            return ;
        }

        //get the path from the root until the new Inserted node
        Deque<AVLNode<E>> path = new LinkedList<>();
        AVLNode<E> current = root;
        path.addLast(root);
        while (true) {

            //compare the new element with the current node's data
            if (element.compareTo(current.data) >= 0) {
                // Insert to the right if the new element is greater than or equal to the current node's data
                if (current.right == null) {
                    current.right = newNode;
                    size++;
                    break;
                } else {current = current.right;

                }
            }
            else {
                //insert to the left if the new element is less than the current node's data
                if (current.left == null) {
                    current.left = newNode;
                    size++;
                    break;
                } else {
                    current = current.left;
                }
            }

            //add the current to the path
            path.addLast(current);
        }
        path.addLast(newNode);

        balancePath(path);
    }
    public boolean search(E element){
        AVLNode<E> current = root;

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
        if(root == null ) return false;
        else if (root.left == null && root.right ==null) {
            root = null;
            size--;
            return true;
        }


        AVLNode<E> current = root; //the current is the node to be deleted
        AVLNode<E> parent = null; //parent of the node to be deleted
        Deque<AVLNode<E>> path = new LinkedList<>();

        //locate the node to be deleted, it's parent, and update the path along the way
        while(current != null){
            path.addLast(current);
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
        path.removeLast();

        //check if the node is found or not
        if(current==null || current.data.compareTo(element)!=0) return false;


        //case 1 : no children --> make the current's parent points to null
        //and case 2 : one right child --> make the parent point to the right child of the current
        if(current.left == null ){ //
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
            AVLNode<E> successor = deleteSuccessor(current,path);
            current.data = successor.data;
        }

        balancePath(path);
        size--;
        return true;

    }
    //iterative in-order
    public void inOrder(){
        if(root == null) return;

        //stack to hold nodes for (left-root-right) traversal
        Deque<AVLNode<E>> stack = new LinkedList<>();
        AVLNode<E> current = root;

        while(!stack.isEmpty() || current != null){
            //add all the path from the current down to current's leftmost node
            while(current != null){
                stack.addFirst(current);
                current = current.left;
            }

            //visit leftmost node
            current = stack.pop();
            System.out.print( current.data + " ");

            //redirect current to it's right subtree and repeat
            current = current.right;
        }
        System.out.println();
    }
    //iterative pre-order
    public void preOrder(){
        if(root == null) return;

        //stack to hold nodes for (root-left-right) traversal
        Deque<AVLNode<E>> stack = new LinkedList<>();
        stack.addFirst(root);
        AVLNode<E> current = root;

        while(!stack.isEmpty()){
            //visit the root then add it's right then it's left
            current = stack.poll();
            System.out.print( current.data + " ");

            //add right first to poll it last
            if(current.right != null) stack.addFirst(current.right);
            if(current.left != null) stack.addFirst(current.left);
        }
        System.out.println();
    }
    public void postOrder() {
        if (root == null) return;

        //used to push nodes onto it in the (root-left-right) order
        Deque<AVLNode<E>> stack1 = new LinkedList<>();
        //that will store the post-order in reverse order -> (root-right-left)
        Deque<AVLNode<E>> stack2 = new LinkedList<>();
        AVLNode<E> current = null;
        stack1.push(root);

        while (!stack1.isEmpty()) {
            current = stack1.pop();
            stack2.push(current);

            if (current.left != null) stack1.push(current.left);
            if (current.right != null) stack1.push(current.right);
        }

        while (!stack2.isEmpty()) {
            current = stack2.pop();
            System.out.print(current.data + " ");
        }
        System.out.println();
    }
    public void BFS(){
        if(root == null) return;

        //to hold the children of the current until traversing the other children of current's parent
        Queue<AVLNode<E>> queue = new LinkedList<>();
        queue.offer(root);
        AVLNode<E> current ;//to reference the first node in the queue

        while(!queue.isEmpty()){
            current = queue.poll();
            System.out.print(current.data+" ");

            if(current.left != null) queue.offer(current.left);
            if(current.right != null) queue.offer(current.right);
        }

        System.out.println();
    }


     /** check the nodes along the path from the new leaf node up to the root.
     If an unbalanced node is found, perform an appropriate rotation using a rotation algorithm
     */
    private void balancePath(Deque<AVLNode<E>> path){

        //check all nodes from the leaf up to the root, update height for each node
        //and balance any unbalanced node based on the balance factor
        AVLNode<E> tmp = null , tmpParent = null;
        while(!path.isEmpty()) {
            tmp = path.removeLast();
            updateHeight(tmp);

            if(!path.isEmpty()) tmpParent = path.getLast();
            else tmpParent = null;

            short BF = balanceFactor(tmp);
            switch (BF){
                case -2: // left-heavy
                    //LL imbalance
                    if(balanceFactor(tmp.left) <= 0 )
                        balanceLL(tmpParent, tmp);
                    else //LR imbalance
                        balanceLR(tmpParent , tmp);
                    break;
                case 2: // right-heavy
                    //RR imbalance
                    if(balanceFactor(tmp.right) >= 0 )
                        balanceRR(tmpParent, tmp);
                    else //RL imbalance
                        balanceRL(tmpParent , tmp);
                    break;
            }
        }

    }

    private void balanceLL( AVLNode<E> parentOfRoot,AVLNode<E> root){
        //make a left-left rotation
        AVLNode<E> newRoot = root.left;
        root.left = newRoot.right;
        newRoot.right = root;

        //Update the height of the old root and the new root
        updateHeight(root);
        updateHeight(newRoot);

        //update the parent to point to the new root
        if(parentOfRoot != null){
            if(parentOfRoot.left == root)
                parentOfRoot.left = newRoot;
            else
                parentOfRoot.right = newRoot;
        }
        else
            this.root = newRoot;
    }
    private void balanceRR( AVLNode<E> parentOfRoot,AVLNode<E> root){
        //make right-right balance
        AVLNode<E> newRoot = root.right;
        root.right = newRoot.left;
        newRoot.left = root;

        //Update the height of the old root and the new root
        updateHeight(root);
        updateHeight(newRoot);

        //update the parent to point to the new root
        if(parentOfRoot != null){
            if(parentOfRoot.left == root)
                parentOfRoot.left = newRoot;
            else
                parentOfRoot.right = newRoot;
        }
        else
            this.root = newRoot;
    }
    private void balanceLR( AVLNode<E> parentOfRoot,AVLNode<E> root){
        AVLNode<E> middle = root.left;             //        root
        AVLNode<E> newRoot = middle.right;         //   middle     T4
                                                   //T1         newRoot
        root.left = newRoot.right;                  //         T2        T3
        middle.right = newRoot.left;
        newRoot.left = middle;
        newRoot.right = root;

        //Update the height of the old root and the new root
        updateHeight(root);
        updateHeight(middle);
        updateHeight(newRoot);

        //update the parent to point to the new root
        if(parentOfRoot != null){
            if(parentOfRoot.left == root)
                parentOfRoot.left = newRoot;
            else
                parentOfRoot.right = newRoot;
        }
        else
            this.root = newRoot;
    }
    private void balanceRL( AVLNode<E> parentOfRoot,AVLNode<E> root){
        AVLNode<E> middle = root.right;
        AVLNode<E> newRoot = middle.left;

        root.right = newRoot.left;
        middle.left = newRoot.right;
        newRoot.right = middle;
        newRoot.left = root;

        //Update the height of the old root and the new root
        updateHeight( root);
        updateHeight(middle);
        updateHeight(newRoot);

        //update the parent to point to the new root
        if(parentOfRoot != null){
            if(parentOfRoot.left == root)
                parentOfRoot.left = newRoot;
            else
                parentOfRoot.right = newRoot;
        }
        else
            this.root = newRoot;
    }

    /**
     * any new inserted node start with height zero, the parent of it is with height 1
     * the parent of parent is with height 2 and so on, this function is called in context of
     * traversing from a leaf node up to the root so the actual height of any node is more than
     * the child with max height ,of the two children, by one.
     * */
    private void updateHeight(AVLNode<E> node){
        if(node.left == null && node.right == null)
            node.height = 0;
        else if(node.left == null)
            node.height = node.right.height + 1;
        else if(node.right == null)
            node.height = node.left.height + 1;
        else node.height =
                    Math.max(node.right.height, node.left.height)+ 1;
    }

    /**
     * return the balance factor of any node which is [node.right.height - node.left.height]
     * */
    private short balanceFactor(AVLNode<E> node){
        if(node.left == null && node.right == null) return 0;
        else if(node.left == null)
            return (short) node.height;
        else if(node.right == null)
            return (short) -node.height;

        return (short) (node.right.height - node.left.height);
    }

    private AVLNode<E> deleteSuccessor(AVLNode<E> root, Deque<AVLNode<E>> pathToSuccessor){
        AVLNode<E> parent = root;
        AVLNode<E> successor = root.right;
        pathToSuccessor.addLast(parent);

        //locate the successor and it's parent
        while(successor.left != null){
            pathToSuccessor.addLast(successor);
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
