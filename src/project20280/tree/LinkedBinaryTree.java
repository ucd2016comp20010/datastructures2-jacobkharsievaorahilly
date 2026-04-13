package project20280.tree;

import project20280.interfaces.Position;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Concrete implementation of a binary tree using a node-based, linked
 * structure.
 */
public class LinkedBinaryTree<E> extends AbstractBinaryTree<E> {

    static java.util.Random rnd = new java.util.Random();
    /**
     * The root of the binary tree
     */
    protected Node<E> root = null; // root of the tree

    // LinkedBinaryTree instance variables
    /**
     * The number of nodes in the binary tree
     */
    private int size = 0; // number of nodes in the tree

    @Override
    public Iterable<Position<E>> children(Position<E> p) {
        java.util.List<Position<E>> snapshot = new ArrayList<>();
        if (left(p) != null) snapshot.add(left(p));
        if (right(p) != null) snapshot.add(right(p));
        return snapshot;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Constructs an empty binary tree.
     */
    public LinkedBinaryTree() {
    } // constructs an empty binary tree

    // constructor

    public static LinkedBinaryTree<Integer> makeRandom(int n) {
        LinkedBinaryTree<Integer> bt = new LinkedBinaryTree<>();
        bt.root = randomTree(null, 1, n);
        return bt;
    }

    // nonpublic utility

    public static <T extends Integer> Node<T> randomTree(Node<T> parent, Integer first, Integer last) {
        if (first > last) return null;
        else {
            Integer treeSize = last - first + 1;
            Integer leftCount = rnd.nextInt(treeSize);
            Integer rightCount = treeSize - leftCount - 1;
            Node<T> root = new Node<T>((T) ((Integer) (first + leftCount)), parent, null, null);
            root.setLeft(randomTree(root, first, first + leftCount - 1));
            root.setRight(randomTree(root, first + leftCount + 1, last));
            return root;
        }
    }

    // accessor methods (not already implemented in AbstractBinaryTree)

    public static void main(String[] args) {
        LinkedBinaryTree<String> bt = new LinkedBinaryTree<>();
        ArrayList<String> arr = new ArrayList<>();
        arr.add("A"); arr.add("B"); arr.add("C"); arr.add("D");
        arr.add("E"); arr.add(null); arr.add("F"); arr.add(null);
        arr.add(null); arr.add("G"); arr.add("H"); arr.add(null);
        arr.add(null); arr.add(null); arr.add(null);
        bt.createLevelOrder(arr);
        System.out.println(bt.toBinaryTreeString());
    }

    /**
     * Factory function to create a new node storing element e.
     */
    protected Node<E> createNode(E e, Node<E> parent, Node<E> left, Node<E> right) {
        return new Node<E>(e, parent, left, right);
    }

    /**
     * Verifies that a Position belongs to the appropriate class, and is not one
     * that has been previously removed. Note that our current implementation does
     * not actually verify that the position belongs to this particular list
     * instance.
     *
     * @param p a Position (that should belong to this tree)
     * @return the underlying Node instance for the position
     * @throws IllegalArgumentException if an invalid position is detected
     */
    protected Node<E> validate(Position<E> p) throws IllegalArgumentException {
        if (!(p instanceof Node)) throw new IllegalArgumentException("Not valid position type");
        Node<E> node = (Node<E>) p; // safe cast
        if (node.getParent() == node) // our convention for defunct node
            throw new IllegalArgumentException("p is no longer in the tree");
        return node;
    }

    /**
     * Returns the number of nodes in the tree.
     *
     * @return number of nodes in the tree
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns the root Position of the tree (or null if tree is empty).
     *
     * @return root Position of the tree (or null if tree is empty)
     */
    @Override
    public Position<E> root() {
        return root;
    }

    // update methods supported by this class

    /**
     * Returns the Position of p's parent (or null if p is root).
     *
     * @param p A valid Position within the tree
     * @return Position of p's parent (or null if p is root)
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    @Override
    public Position<E> parent(Position<E> p) throws IllegalArgumentException {
        return ((Node<E>) p).getParent();
    }

    /**
     * Returns the Position of p's left child (or null if no child exists).
     *
     * @param p A valid Position within the tree
     * @return the Position of the left child (or null if no child exists)
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     */
    @Override
    public Position<E> left(Position<E> p) throws IllegalArgumentException {
        return ((Node<E>) p).getLeft();
    }

    /**
     * Returns the Position of p's right child (or null if no child exists).
     *
     * @param p A valid Position within the tree
     * @return the Position of the right child (or null if no child exists)
     * @throws IllegalArgumentException if p is not a valid Position for this tree
     */
    @Override
    public Position<E> right(Position<E> p) throws IllegalArgumentException {
        return ((Node<E>) p).getRight();
    }

    /**
     * Places element e at the root of an empty tree and returns its new Position.
     *
     * @param e the new element
     * @return the Position of the new element
     * @throws IllegalStateException if the tree is not empty
     */
    public Position<E> addRoot(E e) throws IllegalStateException {
        if (size != 0) throw new IllegalStateException("Tree is not empty");
        root = createNode(e, null, null, null);
        size++;
        return root;
    }

    public void insert(E e) {
        addRecursive(root, e);
    }

    @Override
    public Iterable<Position<E>> positions() {
        return inorder();
    }

    private Node<E> addRecursive(Node<E> p, E e) {
        if (p == null) {
            root = createNode(e, null, null, null);
            size++;
            return root;
        }
        if (p.getLeft() == null) {
            Node<E> newest = createNode(e, p, null, null);
            p.setLeft(newest);
            size++;
            return newest;
        } else if (p.getRight() == null) {
            Node<E> newest = createNode(e, p, null, null);
            p.setRight(newest);
            size++;
            return newest;
        } else {
            return addRecursive(p.getLeft(), e);
        }
    }

    public Position<E> addLeft(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> parent = validate(p);
        if (parent.getLeft() != null) throw new IllegalArgumentException("p already has a left child");
        Node<E> newest = createNode(e, parent, null, null);
        parent.setLeft(newest);
        size++;
        return newest;
    }

    public Position<E> addRight(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> parent = validate(p);
        if (parent.getRight() != null) throw new IllegalArgumentException("p already has a right child");
        Node<E> newest = createNode(e, parent, null, null);
        parent.setRight(newest);
        size++;
        return newest;
    }

    public E set(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> node = validate(p);
        E old = node.getElement();
        node.setElement(e);
        return old;
    }

    public void attach(Position<E> p, LinkedBinaryTree<E> t1, LinkedBinaryTree<E> t2) throws IllegalArgumentException {
        Node<E> node = validate(p);
        if (isInternal(p)) throw new IllegalArgumentException("p must be a leaf");
        size += t1.size() + t2.size();
        if (!t1.isEmpty()) {
            t1.root.setParent(node);
            node.setLeft(t1.root);
            t1.root = null;
            t1.size = 0;
        }
        if (!t2.isEmpty()) {
            t2.root.setParent(node);
            node.setRight(t2.root);
            t2.root = null;
            t2.size = 0;
        }
    }

    public E remove(Position<E> p) throws IllegalArgumentException {
        Node<E> node = validate(p);
        if (node.getLeft() != null && node.getRight() != null)
            throw new IllegalArgumentException("p has two children");

        Node<E> child;
        if (node.getLeft() != null) {
            child = node.getLeft();
        } else if (node.getRight() != null) {
            child = node.getRight();
        } else {
            child = null;
        }

        if (child != null) child.setParent(node.getParent());

        if (node == root) {
            root = child;
        } else {
            Node<E> par = node.getParent();
            if (node == par.getLeft()) {
                par.setLeft(child);
            } else {
                par.setRight(child);
            }
        }

        size--;
        node.setParent(node);
        return node.getElement();
    }

    public void createLevelOrder(ArrayList<E> l) {
        root = createLevelOrderHelper(l, root, 0);
        size = l.size();
    }

    private Node<E> createLevelOrderHelper(java.util.ArrayList<E> l, Node<E> p, int i) {
        if (i < l.size()) {
            Node<E> newest = createNode(l.get(i), p, null, null);
            newest.setLeft(createLevelOrderHelper(l, newest.getLeft(), 2 * i + 1));
            newest.setRight(createLevelOrderHelper(l, newest.getRight(), 2 * i + 2));
            size++;
            return newest;
        }
        return null;
    }

    public void createLevelOrder(E[] arr) {
        root = createLevelOrderHelper(arr, root, 0);
    }

    private Node<E> createLevelOrderHelper(E[] arr, Node<E> p, int i) {
        if (i < arr.length && arr[i] != null) {
            Node<E> newest = createNode(arr[i], p, null, null);
            newest.setLeft(createLevelOrderHelper(arr, newest.getLeft(), 2 * i + 1));
            newest.setRight(createLevelOrderHelper(arr, newest.getRight(), 2 * i + 2));
            size++;
            return newest;
        }
        return null;
    }

    public String toBinaryTreeString() {
        BinaryTreePrinter<E> btp = new BinaryTreePrinter<>(this);
        return btp.print();
    }

    public String toString() {
        return inorder().toString();
    }

    @Override
    public Iterator<E> iterator() {
        java.util.List<E> buf = new ArrayList<>();
        for (Position<E> p : positions()) {
            buf.add(p.getElement());
        }
        return buf.iterator();
    }

    public int height() {
        return height_recursive(root);
    }

    /**
     * Nested static class for a binary tree node.
     */
    public static class Node<E> implements Position<E> {
        private E element;
        private Node<E> left, right, parent;

        public Node(E e, Node<E> p, Node<E> l, Node<E> r) {
            element = e;
            left = l;
            right = r;
            parent = p;
        }

        // accessor
        public E getElement() {
            return element;
        }

        // modifiers
        public void setElement(E e) {
            element = e;
        }

        public Node<E> getLeft() {
            return left;
        }

        public void setLeft(Node<E> n) {
            left = n;
        }

        public Node<E> getRight() {
            return right;
        }

        public void setRight(Node<E> n) {
            right = n;
        }

        public Node<E> getParent() {
            return parent;
        }

        public void setParent(Node<E> n) {
            parent = n;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (element == null) {
                sb.append("\u29B0");
            } else {
                sb.append(element);
            }
            return sb.toString();
        }
    }
}