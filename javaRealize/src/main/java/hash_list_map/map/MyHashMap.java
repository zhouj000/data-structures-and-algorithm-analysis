package hash_list_map.map;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * 设计哈希映射
 *      执行用时： 25 ms , 在所有 Java 提交中击败了 73.76% 的用户
 *      内存消耗： 40.7 MB , 在所有 Java 提交中击败了 99.15% 的用户
 **/
public class MyHashMap {

    class Node {
        private int key;
        private int value;
        private Node next;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }

    private Node[] nodeArray;

    /** Initialize your data structure here. */
    public MyHashMap() {
        nodeArray = new Node[100];
    }

    private int hash(int key) {
        return key % 100;
    }

    /** value will always be non-negative. */
    public void put(int key, int value) {
        Node head = nodeArray[hash(key)];
        if (head == null) {
            nodeArray[hash(key)] = new Node(key, value);
        } else {
            // 是否更新
            Node tmp = head;
            while (tmp != null) {
                if (tmp.getKey() == key) {
                    tmp.setValue(value);
                    return;
                }
                tmp = tmp.next;
            }
            // 没有则找到最后的node
            while (head.next != null) {
                head = head.next;
            }
            Node tail = new Node(key, value);
            head.next = tail;
        }
    }

    /** Returns the value to which the specified key is mapped, or -1 if this map contains no mapping for the key */
    public int get(int key) {
        Node head = nodeArray[hash(key)];
        if (head == null) {
            return -1;
        }
        while (head != null) {
            if (head.getKey() == key) {
                return head.value;
            }
            head = head.next;
        }
        return -1;
    }

    /** Removes the mapping of the specified value key if this map contains a mapping for the key */
    public void remove(int key) {
        Node head = nodeArray[hash(key)];
        if (head == null) {
            return;
        }
        if (head.getKey() == key) {
            nodeArray[hash(key)] = head.next;
        }
        Node tmp = head;
        while (tmp.next != null) {
            if (tmp.next.getKey() == key) {
                tmp.next = tmp.next.next;
                return;
            }
            tmp = tmp.next;
        }
    }



    // 后注： 这个put方法，将新节点永远加到第一位，这样就不用像我一眼要去查找最后一个节点位
    class BestMyHashMap {

        BestNode[] table;

        /**
         * Initialize your data structure here.
         */
        public BestMyHashMap() {
            table = new BestNode[100];
        }

        /**
         * value will always be non-negative.
         */
        public void put(int key, int value) {
            int code = hashcode(key);
            BestNode temp = table[code];
            if (temp == null) {
                table[code] = new BestNode(key, value);
            } else {
                while (temp != null) {
                    if (temp.key == key) {
                        temp.value = value;
                        return;
                    }
                    temp = temp.next;
                }
                BestNode t = new BestNode(key, value);
                t.next = table[code];
                table[code] = t;
            }
        }

        /**
         * Returns the value to which the specified key is mapped, or -1 if this map contains no mapping for the key
         */
        public int get(int key) {
            int code = hashcode(key);
            BestNode temp = table[code];
            if (temp == null) {
                return -1;
            }
            while (temp != null) {
                if (temp.key == key) {
                    return temp.value;
                }
                temp = temp.next;
            }
            return -1;
        }

        /**
         * Removes the mapping of the specified value key if this map contains a mapping for the key
         */
        public void remove(int key) {
            int code = hashcode(key);
            BestNode dummy = new BestNode(-1, -1);
            dummy.next = table[code];
            BestNode cur = dummy;
            while (cur.next != null) {
                if (cur.next.key == key) {
                    cur.next = cur.next.next;
                    break;
                }
                cur = cur.next;
            }
            table[code] = dummy.next;
        }

        public int hashcode(int key) {
            return key % 100;
        }

        class BestNode {
            public int key;
            public int value;
            public BestNode next;

            public BestNode(int k, int v) {
                key = k;
                value = v;
            }
        }
    }




    // 给出的解决方案
    class MyHashMapExp {
        private final int MAX_LEN = 100000;             // the amount of buckets
        private List<Pair<Integer, Integer>>[] map;     // hash map implemented by array

        /** Returns the corresponding bucket index. */
        private int getIndex(int key) {
            return key % MAX_LEN;
        }

        /** Search the key in a specific bucket. Returns -1 if the key does not existed. */
        private int getPos(int key, int index) {
            // Each bucket contains a list.
            List<Pair<Integer, Integer>> temp = map[index];
            if (temp == null) {
                return -1;
            }
            // Iterate all the elements in the bucket to find the target key.
            for (int i = 0; i < temp.size(); ++i) {
                if (temp.get(i).getKey() == key) {
                    return i;
                }
            }
            return -1;
        }

        /** Initialize your data structure here. */
        public MyHashMapExp() {
            map = (List<Pair<Integer, Integer>>[])new ArrayList[MAX_LEN];
        }

        /** value will always be positive. */
        public void put(int key, int value) {
            int index = getIndex(key);
            int pos = getPos(key, index);
            if (pos < 0) {
                // Add new (key, value) pair if key is not existed.
                if (map[index] == null) {
                    map[index] = new ArrayList<Pair<Integer, Integer>>();
                }
                map[index].add(new Pair(key, value));
            } else {
                // Update the value if key is existed.
                map[index].set(pos, new Pair(key, value));
            }
        }

        /** Returns the value to which the specified key is mapped, or -1 if this map contains no mapping for the key */
        public int get(int key) {
            int index = getIndex(key);
            int pos = getPos(key, index);
            if (pos < 0) {
                return -1;
            } else {
                return map[index].get(pos).getValue();
            }
        }

        /** Removes the mapping of the specified value key if this map contains a mapping for the key */
        public void remove(int key) {
            int index = getIndex(key);
            int pos = getPos(key, index);
            if (pos >= 0) {
                map[index].remove(pos);
            }
        }
    }

}
