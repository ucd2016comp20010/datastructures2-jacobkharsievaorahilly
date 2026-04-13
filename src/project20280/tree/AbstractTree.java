package project20280.tree;

import project20280.interfaces.Position;
import project20280.interfaces.Tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * An abstract base class providing some functionality of the Tree interface.
 * <p>
 * The following three methods remain abstract, and must be
 * implemented by a concrete subclass: root, parent, children. Other
 * methods implemented in this class may be overridden to provide a
 * more direct and efficient implementation.
 */
public abstract class AbstractTree<E> implements Tree<E> {

    /**
     * Returns true if Position p has one or more children.
     *
     * @param p A valid Position within the tree
     * @return true if p has at least one child, false otherwise
     * @throws IllegalArgumentException if p is not a valid Position for this tree.
     */
    @Override
    public boolean isInternal(Position<E> p) {
        return numChildren(p) > 0;
    }

    @Override
    public boolean isExternal(Position<E> p) {
        return numChildren(p) == 0;
    }

    @Override
    public boolean isRoot(Position<E> p) {
        return p == root();
    }

    @Override
    public int numChildren(Position<E> p) {
        int count = 0;
        for (Position<E> c : children(p)) {
            count++;
        }
        return count;
    }

    public int depth(Position<E> p) throws IllegalArgumentException {
        if (isRoot(p)) return 0;
        return 1 + depth(parent(p));
    }

    public int height_recursive(Position<E> p) {
        if (isExternal(p)) return 0;
        int h = 0;
        for (Position<E> c : children(p)) {
            int childH = height_recursive(c);
            if (childH > h) h = childH;
        }
        return 1 + h;
    }

    private void preorderSubtree(Position<E> p, List<Position<E>> snapshot) {
        snapshot.add(p);
        for (Position<E> c : children(p)) {
            preorderSubtree(c, snapshot);
        }
    }

    public Iterable<Position<E>> preorder() {
        List<Position<E>> snapshot = new ArrayList<>();
        if (!isEmpty()) preorderSubtree(root(), snapshot);
        return snapshot;
    }

    private void postorderSubtree(Position<E> p, List<Position<E>> snapshot) {
        for (Position<E> c : children(p)) {
            postorderSubtree(c, snapshot);
        }
        snapshot.add(p);
    }

    public Iterable<Position<E>> breadthfirst() {
        List<Position<E>> snapshot = new ArrayList<>();
        if (!isEmpty()) {
            java.util.Queue<Position<E>> q = new java.util.LinkedList<>();
            q.add(root());
            while (!q.isEmpty()) {
                Position<E> p = q.poll();
                snapshot.add(p);
                for (Position<E> c : children(p)) {
                    q.add(c);
                }
            }
        }
        return snapshot;
    }

    @Override
    public int size() {
        int count = 0;
        for (Position p : positions()) count++;
        return count;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public Iterator<E> iterator() {
        java.util.List<E> buf = new ArrayList<>();
        for (Position<E> p : positions()) {
            buf.add(p.getElement());
        }
        return buf.iterator();
    }

    @Override
    public Iterable<Position<E>> positions() {
        return preorder();
    }
}