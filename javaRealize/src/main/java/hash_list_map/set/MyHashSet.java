package hash_list_map.set;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 设计哈希集合
 * 执行用时： 15 ms , 在所有 Java 提交中击败了 99.41% 的用户
 * 内存消耗： 46.9 MB , 在所有 Java 提交中击败了 32.43% 的用户
 **/
public class MyHashSet {

    private boolean[] array;

    private int size;

    /** Initialize your data structure here. */
    public MyHashSet() {
        size = 10;
        array = new boolean[size];
    }

    public void add(int key) {
        if (key + 1 > size) {
            size = key + 1;
            array = Arrays.copyOf(array, size);
        }
        array[key] = true;
    }

    public void remove(int key) {
        if (key + 1 > size) {
            return;
        }
        array[key] = false;
    }

    /** Returns true if this set contains the specified element */
    public boolean contains(int key) {
        if (key + 1 > size) {
            return false;
        }
        return array[key];
    }




    class MyHashSet2 {
        boolean[] arr = new boolean[100];

        public MyHashSet2() {
        }

        public void add(int key) {
            if (key >= arr.length) {
                extend(key);
            }
            arr[key] = true;
        }

        public void remove(int key) {
            if (key >= arr.length) {
                extend(key);
            }
            arr[key] = false;
        }

        /**
         * Returns true if this set contains the specified element
         */
        public boolean contains(int key) {
            if (key >= arr.length) {
                return false;
            }
            return arr[key] == true;
        }

        public void extend(int key) {
            arr = Arrays.copyOf(arr, key + 2);
        }
    }

    // 空间最优
    class MyHashSet3 {
        int[][] values;

        public MyHashSet3() {
            values = new int[100][100];
            for (int[] v : values) {
                Arrays.fill(v, -1);
            }
        }

        public void add(int key) {
            int h = hash(key);
            int[] bucket = values[h];
            for (int i = 0; i < bucket.length; i++) {
                if (key == bucket[i]) {
                    return;
                } else if (bucket[i] == -1) {
                    bucket[i] = key;
                    return;
                }
            }
            System.out.println("桶爆了！");
        }

        public void remove(int key) {
            int h = hash(key);
            int[] bucket = values[h];
            for (int i = 0; i < bucket.length; i++) {
                if (key == bucket[i]) {
                    bucket[i] = -1;
                    return;
                }
            }
        }

        public boolean contains(int key) {
            int h = hash(key);
            int[] bucket = values[h];
            for (int v : bucket) {
                if (key == v) {
                    return true;
                }
            }
            return false;
        }

        private int hash(int key) {
            return key % 100;
        }
    }




    // 给出的解决方案： 就是用了map的数据结构，这里没有扩容
    class MyHashSetExp {
        private final int MAX_LEN = 100000; // the amount of buckets
        private List<Integer>[] set;      // hash set implemented by array

        /** Returns the corresponding bucket index. */
        private int getIndex(int key) {
            return key % MAX_LEN;
        }

        /** Search the key in a specific bucket. Returns -1 if the key does not existed. */
        private int getPos(int key, int index) {
            // Each bucket contains a list.
            List<Integer> temp = set[index];
            if (temp == null) {
                return -1;
            }
            // Iterate all the elements in the bucket to find the target key.
            for (int i = 0; i < temp.size(); ++i) {
                if (temp.get(i) == key) {
                    return i;
                }
            }
            return -1;
        }

        /** Initialize your data structure here. */
        public MyHashSetExp() {
            set = (List<Integer>[]) new ArrayList[MAX_LEN];
        }

        public void add(int key) {
            int index = getIndex(key);
            int pos = getPos(key, index);
            if (pos < 0) {
                // Add new key if key does not exist.
                if (set[index] == null) {
                    set[index] = new ArrayList<>();
                }
                set[index].add(key);
            }
        }

        public void remove(int key) {
            int index = getIndex(key);
            int pos = getPos(key, index);
            if (pos >= 0) {
                // Remove the key if key exists.
                set[index].remove(pos);
            }
        }

        /** Returns true if this set did not already contain the specified element */
        public boolean contains(int key) {
            int index = getIndex(key);
            int pos = getPos(key, index);
            return pos >= 0;
        }
    }
}
