package project20280.tree;

import project20280.interfaces.Entry;
import project20280.interfaces.Position;

import java.util.Comparator;
import java.util.Random;

public class TreapMap<K, V> extends TreeMap<K, V> {

    Random rand = new Random();

    public TreapMap() {
        super();
    }

    public TreapMap(Comparator<K> comp) {
        super(comp);
    }

    protected int priority(Position<Entry<K, V>> p) {
        return tree.getAux(p);
    }

    @Override
    protected void rebalanceInsert(Position<Entry<K, V>> p) {
        int pri = rand.nextInt(Integer.MAX_VALUE);
        tree.setAux(p, pri);

        while (!isRoot(p)) {
            Position<Entry<K, V>> par = parent(p);
            if (priority(p) > priority(par)) {
                rotate(p);
            } else {
                break;
            }
        }
    }

    @Override
    protected void rebalanceDelete(Position<Entry<K, V>> p) {
        // uses treemap delete
    }

    public String toString() {
        java.util.ArrayList<K> keys = new java.util.ArrayList<>();
        for (Entry<K, V> e : entrySet()) {
            keys.add(e.getKey());
        }
        return keys.toString();
    }

    public String toBinaryTreeString() {
        BinaryTreePrinter<Entry<K, V>> btp = new BinaryTreePrinter<>(this.tree);
        return btp.print();
    }

    public static void main(String[] args) {
        TreapMap<Integer, Integer> treap = new TreapMap<>();

        Integer[] arr = new Integer[]{5, 3, 10, 2, 4, 7, 11, 1, 6, 9, 12, 8};

        for (Integer i : arr) {
            treap.put(i, i);
        }

        System.out.println(treap.toBinaryTreeString());
        System.out.println(treap);
    }
}