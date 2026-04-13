package project20280.list;

import project20280.interfaces.List;

import java.util.Iterator;

public class DoublyLinkedList<E> implements List<E> {

    private static class Node<E> {
        private E data;
        private Node<E> next;
        private Node<E> prev;

        public Node(E e, Node<E> p, Node<E> n) {
            data = e;
            prev = p;
            next = n;
        }

        public void setNext(Node<E> n) {
            this.next = n;
        }

        public void setPrev(Node<E> p) {
            this.prev = p;
        }

        public E getData() {
            return data;
        }

        public Node<E> getNext() {
            return next;
        }

        public Node<E> getPrev() {
            return prev;
        }

    }

    private final Node<E> head;
    private final Node<E> tail;
    private int size = 0;

    public DoublyLinkedList() {
        head = new Node<E>(null, null, null);
        tail = new Node<E>(null, head, null);
        head.next = tail;
    }

    @Override
    public void addFirst(E e) {
        addBetween(e, head, head.getNext());
    }


    private void addBetween(E e, Node<E> pred, Node<E> succ) {
        Node<E> newest = new Node<>(e, pred, succ);

        pred.setNext(newest);
        succ.setPrev(newest);
        size++;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        if (head.getNext() == tail) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public E get(int i) {

        // base

        if (i < 0 || i >= size) {
            return null;
        }

        Node<E> curr = head.getNext();
        for (int j = 0; j < i;) {
            curr = curr.getNext();
            j++;
        }
        return curr.data;
    }

    @Override
    public void add(int i, E e) {
        // base
        if (i < 0 || i > size) return;

        Node<E> nNewest;
        if (i == size) {
            nNewest = tail;
        } else {
            nNewest = head.getNext();
            for (int j = 0; j < i;) {
                nNewest = nNewest.getNext();
                j++;
            }
        }

        Node<E> previous = nNewest.getPrev();
        Node<E> newest = new Node<>(e, previous, nNewest);

        previous.setNext(newest);
        nNewest.setPrev(newest);
        size++;
    }

    @Override
    public E remove(int i) {
        // base case
        if (i < 0 || i >= size) return null;

        // for loop through nodes
        Node<E> curr = head.getNext();
        for (int j = 0; j < i;) {
            curr = curr.getNext();
            j++;
        }

        return remove(curr);

    }

    private class DoublyLinkedListIterator<E> implements Iterator<E> {
        Node<E> curr = (Node<E>) head.next;

        @Override
        public boolean hasNext() {
            return curr != tail;
        }

        @Override
        public E next() {
            E res = curr.data;
            curr = curr.next;
            return res;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new DoublyLinkedListIterator<E>();
    }


    public E first() {
        if (isEmpty()) {
            return null;
        }
        return head.next.getData();
    }

    public E last() {
        // base
        if (isEmpty()) return null;
        return tail.getPrev().getData();
    }

    @Override
    public E removeFirst() {
        // base case
        if (isEmpty()) return null;

        return remove(head.getNext());
    }

    @Override
    public E removeLast() {
        // bc
        if (isEmpty()) return null;


        return remove(tail.getPrev());
    }

    @Override
    public void addLast(E e) {
        addBetween(e, tail.getPrev(), tail);
    }

    private E remove(Node<E> n) {
        Node<E> rPred = n.getPrev();
        Node<E> rSucc = n.getNext();

        rPred.setNext(rSucc);
        rSucc.setPrev(rPred);

        size--;
        return n.getData();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Node<E> curr = head.next;
        while (curr != tail) {
            sb.append(curr.data);
            curr = curr.next;
            if (curr != tail) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        DoublyLinkedList<Integer> ll = new DoublyLinkedList<Integer>();
        ll.addFirst(0);
        ll.addFirst(1);
        ll.addFirst(2);
        ll.addLast(-1);
        System.out.println(ll);

        ll.removeFirst();
        System.out.println(ll);

        ll.removeLast();
        System.out.println(ll);

        for (Integer e : ll) {
            System.out.println("value: " + e);
        }
    }
}