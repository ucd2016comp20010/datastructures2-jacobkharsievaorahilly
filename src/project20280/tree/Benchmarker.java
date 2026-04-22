package project20280.tree;

import java.util.*;
import project20280.interfaces.Map;
import project20280.interfaces.Entry;

public class Benchmarker {

    public static void main(String[] args) {

        int[] sizes = {100, 500, 1000, 5000, 10000};

        // Q2 map
        System.out.println("Q2");
        System.out.println("Tree\tN\tType\tInsert\tSearch\tDelete\tInOrder");

        for (int i = 0; i < sizes.length; i++) {
            int n = sizes[i];

            ArrayList<Integer> random = makeRandom(n);
            ArrayList<Integer> sorted = makeSorted(n);
            ArrayList<Integer> reversed = makeReversed(n);
            ArrayList<Integer> partial = makePartial(n);

            runMap(new TreapMap<>(), "Treap", random, "Random");
            runMap(new AVLTreeMap<>(), "AVL", random, "Random");
            runJava(random, "Random");

            runMap(new TreapMap<>(), "Treap", sorted, "Sorted");
            runMap(new AVLTreeMap<>(), "AVL", sorted, "Sorted");
            runJava(sorted, "Sorted");

            runMap(new TreapMap<>(), "Treap", reversed, "Reversed");
            runMap(new AVLTreeMap<>(), "AVL", reversed, "Reversed");
            runJava(reversed, "Reversed");

            runMap(new TreapMap<>(), "Treap", partial, "Partial");
            runMap(new AVLTreeMap<>(), "AVL", partial, "Partial");
            runJava(partial, "Partial");

            System.out.println();
        }

        // Q3 sorting
        System.out.println("Q3");
        System.out.println("Algorithm\tN\tType\tTime(ms)");

        for (int i = 0; i < sizes.length; i++) {
            int n = sizes[i];

            ArrayList<Integer> random = makeRandom(n);
            ArrayList<Integer> sorted = makeSorted(n);
            ArrayList<Integer> reversed = makeReversed(n);


            runSort(new ArrayList<>(random), "TreapSort", n, "Random");
            runSort(new ArrayList<>(random), "PQSort", n, "Random");
            runSort(new ArrayList<>(random), "TimSort", n, "Random");
            runSort(new ArrayList<>(random), "QuickSort", n, "Random");
            runSort(new ArrayList<>(random), "MergeSort", n, "Random");

            runSort(new ArrayList<>(sorted), "TreapSort", n, "Sorted");
            runSort(new ArrayList<>(sorted), "PQSort", n, "Sorted");
            runSort(new ArrayList<>(sorted), "TimSort", n, "Sorted");
            runSort(new ArrayList<>(sorted), "QuickSort", n, "Sorted");
            runSort(new ArrayList<>(sorted), "MergeSort", n, "Sorted");

            runSort(new ArrayList<>(reversed), "TreapSort", n, "Reversed");
            runSort(new ArrayList<>(reversed), "PQSort", n, "Reversed");
            runSort(new ArrayList<>(reversed), "TimSort", n, "Reversed");
            runSort(new ArrayList<>(reversed), "QuickSort", n, "Reversed");
            runSort(new ArrayList<>(reversed), "MergeSort", n, "Reversed");

            System.out.println();
        }
    }

    // run treap and avl
    public static void runMap(Map<Integer, Integer> map, String name, List<Integer> data, String type) {
        int n = data.size();

        long s1 = System.nanoTime();
        for (int i = 0; i < n; i++) {
            map.put(data.get(i), data.get(i));
        }
        double insertTime = (System.nanoTime() - s1) / 1000000.0;

        long s2 = System.nanoTime();
        for (int i = 0; i < n; i++) {
            map.get(data.get(i));
        }
        double searchTime = (System.nanoTime() - s2) / 1000000.0;

        long s3 = System.nanoTime();
        for (Entry<Integer, Integer> e : map.entrySet()) {
        }
        double inorderTime = (System.nanoTime() - s3) / 1000000.0;

        long s4 = System.nanoTime();
        for (int i = 0; i < n; i++) {
            map.remove(data.get(i));
        }
        double deleteTime = (System.nanoTime() - s4) / 1000000.0;

        System.out.println(name + "\t" + n + "\t" + type + "\t" + insertTime + "\t" + searchTime + "\t" + deleteTime + "\t" + inorderTime);
    }

    // java treemap
    public static void runJava(List<Integer> data, String type) {
        int n = data.size();
        java.util.TreeMap<Integer, Integer> jm = new java.util.TreeMap<>();

        long s1 = System.nanoTime();
        for (int i = 0; i < n; i++) {
            jm.put(data.get(i), data.get(i));
        }
        double insertTime = (System.nanoTime() - s1) / 1000000.0;

        long s2 = System.nanoTime();
        for (int i = 0; i < n; i++) {
            jm.get(data.get(i));
        }
        double searchTime = (System.nanoTime() - s2) / 1000000.0;

        long s3 = System.nanoTime();
        for (java.util.Map.Entry<Integer, Integer> e : jm.entrySet()) {
        }
        double inorderTime = (System.nanoTime() - s3) / 1000000.0;

        long s4 = System.nanoTime();
        for (int i = 0; i < n; i++) {
            jm.remove(data.get(i));
        }
        double deleteTime = (System.nanoTime() - s4) / 1000000.0;

        System.out.println("JavaTM\t" + n + "\t" + type + "\t" + insertTime + "\t" + searchTime + "\t" + deleteTime + "\t" + inorderTime);
    }

    public static void runSort(ArrayList<Integer> data, String algo, int n, String type) {
        long start = System.nanoTime();

        if (algo.equals("TreapSort")) {
            TreapMap<Integer, Integer> t = new TreapMap<>();
            for (int i = 0; i < data.size(); i++) {
                t.put(data.get(i), data.get(i));
            }
            // inorder traversal gets sorted output
            for (Entry<Integer, Integer> e : t.entrySet()) {
            }

        } else if (algo.equals("PQSort")) {
            PriorityQueue<Integer> pq = new PriorityQueue<>(data);
            ArrayList<Integer> out = new ArrayList<>();
            while (!pq.isEmpty()) {
                out.add(pq.poll());
            }

        } else if (algo.equals("TimSort")) {
            Collections.sort(data);

        } else if (algo.equals("QuickSort")) {
            int[] arr = new int[data.size()];
            for (int i = 0; i < data.size(); i++) arr[i] = data.get(i);
            quickSort(arr, 0, arr.length - 1);

        } else if (algo.equals("MergeSort")) {
            int[] arr = new int[data.size()];
            for (int i = 0; i < data.size(); i++) arr[i] = data.get(i);
            mergeSort(arr, 0, arr.length - 1);
        }

        double time = (System.nanoTime() - start) / 1000000.0;
        System.out.println(algo + "\t" + n + "\t" + type + "\t" + time);
    }

    // QuickSort from https://www.geeksforgeeks.org/dsa/java-program-for-quicksort/
    static int partition(int a[], int low, int high) {
        int pivot = a[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (a[j] <= pivot) {
                i++;
                int temp = a[i];
                a[i] = a[j];
                a[j] = temp;
            }
        }
        int temp = a[i+1];
        a[i+1] = a[high];
        a[high] = temp;
        return i+1;
    }

    static void quickSort(int a[], int l, int h) {
        if (l < h) {
            int pi = partition(a, l, h);
            quickSort(a, l, pi-1);
            quickSort(a, pi+1, h);
        }
    }

    // MergeSort from https://www.geeksforgeeks.org/java/java-program-for-merge-sort/
    static void merge(int a[], int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;
        int L[] = new int[n1];
        int R[] = new int[n2];
        for (int i = 0; i < n1; ++i)
            L[i] = a[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = a[m + 1 + j];
        int i = 0, j = 0;
        int k = l;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                a[k] = L[i];
                i++;
            } else {
                a[k] = R[j];
                j++;
            }
            k++;
        }
        while (i < n1) {
            a[k] = L[i];
            i++;
            k++;
        }
        while (j < n2) {
            a[k] = R[j];
            j++;
            k++;
        }
    }

    static void mergeSort(int a[], int l, int r) {
        if (l < r) {
            int m = (l + r) / 2;
            mergeSort(a, l, m);
            mergeSort(a, m + 1, r);
            merge(a, l, m, r);
        }
    }

    // data generators
    static ArrayList<Integer> makeRandom(int n) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < n; i++) list.add(i);
        Collections.shuffle(list);
        return list;
    }

    static ArrayList<Integer> makeSorted(int n) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < n; i++) list.add(i);
        return list;
    }

    static ArrayList<Integer> makeReversed(int n) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = n - 1; i >= 0; i--) list.add(i);
        return list;
    }

    // 80% sorted 20% random
    static ArrayList<Integer> makePartial(int n) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < n; i++) list.add(i);
        int cutoff = (int)(n * 0.8);
        List<Integer> tail = list.subList(cutoff, n);
        Collections.shuffle(tail);
        return list;
    }
}