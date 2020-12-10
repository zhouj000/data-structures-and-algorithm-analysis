package hash_list_map;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 四数相加 II
 * 执行用时： 178 ms , 在所有 Java 提交中击败了 5.12% 的用户
 * 内存消耗： 82.2 MB , 在所有 Java 提交中击败了 5.01% 的用户
 *
 *
 *  new HashMap<>(len * len);  后节约的时间：
 * 执行用时： 136 ms , 在所有 Java 提交中击败了 7.70% 的用户
 * 内存消耗： 78.5 MB , 在所有 Java 提交中击败了 6.08% 的用户
 **/
public class FourSumCount {

    public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
        int len = A.length;
        if (len == 0) {
            return 0;
        }
        Map<Integer, Integer> map1 = new HashMap<>();
        Map<Integer, Integer> map2 = new HashMap<>();
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                int c1 = A[i] + B[j];
                map1.put(c1, map1.getOrDefault(c1, 0) + 1);
                int c2 = C[i] + D[j];
                map2.put(c2, map2.getOrDefault(c2, 0) + 1);
            }
        }
        int count = 0;
        for (Map.Entry<Integer, Integer> entry : map1.entrySet()) {
            Integer v = map2.get(0 - entry.getKey());
            if (v != null) {
                count += entry.getValue() * v;
            }
        }
        return count;
    }




    // 25ms   思路和我一致，不过自定义了数据结构
    class BestSolution {
        private class Map {
            Node[] table;

            public Map(int initalCapacity) {
                if (initalCapacity < 16) {
                    initalCapacity = 16;
                } else {
                    initalCapacity = Integer.highestOneBit(initalCapacity - 1) << 1;
                }
                table = new Node[initalCapacity];
            }

            // 拷贝的HashMap的hash方法
            private int hash(int value) {
                if (value < 0) {
                    value = -value;
                }
                int h;
                return (value == 0) ? 0 : (h = value) ^ (h >>> 16);
            }

            public void put(int value) {
                int tableIndex = hash(value) & table.length - 1;
                Node head = table[tableIndex];
                if (head == null) {
                    table[tableIndex] = new Node(value);
                    return;
                }
                Node cur = head;
                while (cur != null) {
                    if (cur.value == value) {
                        cur.count++;
                        return;
                    }
                    cur = cur.next;
                }
                // 头插法
                table[tableIndex] = new Node(value, head);
            }

            public int getCount(int value) {
                int tableIndex = hash(value) & table.length - 1;
                Node head = table[tableIndex];
                if (head == null) {
                    return 0;
                }
                Node cur = head;
                while (cur != null) {
                    if (cur.value == value) {
                        return cur.count;
                    }
                    cur = cur.next;
                }
                return 0;
            }
        }

        private class Node {
            int value;
            int count;
            Node next;

            public Node(int value) {
                this.value = value;
                this.count = 1;
            }
            public Node(int value, Node next) {
                this.value = value;
                this.count = 1;
                this.next = next;
            }
        }

        public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
            // 避免扩容, 初始化一个最大初始容量
            Map abMap = new Map(A.length * B.length);

            for (int a : A) {
                for (int b : B) {
                    abMap.put(a + b);
                }
            }
            int res = 0;
            for (int c : C) {
                for (int d : D) {
                    res += abMap.getCount(-c - d);
                }
            }
            return res;
        }
    }

    // 49ms  空间优
    class Solution2 {
        public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
            int res = 0;
            Arrays.sort(A);
            Arrays.sort(B);
            Arrays.sort(C);
            Arrays.sort(D);
            Map<Long, Integer> map = new HashMap<>();
            for (int i = 0; i < A.length; i++) {
                int countA = 1;
                int a = i;
                while (a + 1 < A.length && A[a + 1] == A[a]) {
                    a++;
                    countA++;
                }
                i = a;
                for (int j = 0; j < B.length; j++) {
                    int countB = 1;
                    int b = j;
                    while (b + 1 < B.length && B[b + 1] == B[b]) {
                        b++;
                        countB++;
                    }
                    j = b;
                    long sum = (long) A[a] + (long) B[b];
                    if (map.containsKey(sum)) {
                        map.put(sum, map.get(sum) + countA * countB);
                    } else {
                        map.put(sum, countA * countB);
                    }
                }
            }
            for (int k = 0; k < C.length; k++) {
                int countC = 1;
                int c = k;
                while (c + 1 < C.length && C[c + 1] == C[c]) {
                    c++;
                    countC++;
                }
                k = c;
                for (int l = 0; l < D.length; l++) {
                    int countD = 1;
                    int d = l;
                    while (d + 1 < D.length && D[d + 1] == D[d]) {
                        d++;
                        countD++;
                    }
                    l = d;
                    long sum = (long) C[k] + (long) D[l];
                    sum *= -1;
                    if (map.containsKey(sum)) {
                        res += map.get(sum) * countC * countD;
                    }
                }
            }
            return res;
        }
    }

    // 50ms
    class Solution3 {
        // 尽量避免Integer与int的自动转换
        private class IntHolder {
            public int value;

            public IntHolder(int value) {
                this.value = value;
            }

            public void increase() {
                value++;
            }
        }

        public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {

            // 避免扩容, 初始化一个最大初始容量
            HashMap<Integer, IntHolder> abMap = new HashMap<>(A.length * B.length, 1);

            for (int a : A) {
                for (int b : B) {
                    int abSum = a + b;
                    IntHolder curValue = abMap.get(abSum);
                    if (curValue == null) {
                        abMap.put(abSum, new IntHolder(1));
                        continue;
                    }
                    curValue.increase();
                }
            }

            int res = 0;
            for (int c : C) {
                for (int d : D) {
                    Integer exceptedAbSum = -c - d;
                    IntHolder abSumCount = abMap.get(exceptedAbSum);
                    if (abSumCount == null) {
                        continue;
                    }
                    res += abSumCount.value;
                }
            }
            return res;
        }
    }

    // 54ms  没用自定义结构时
    class Solution4 {
        public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
            Map<Integer, Integer> map = new HashMap<>((int)(A.length * B.length * 1.5));
            for (int a : A) {
                for (int b : B) {
                    map.put(a + b, map.getOrDefault(a + b, 0) + 1);
                }
            }
            int count = 0;
            for (int c : C) {
                for (int d : D) {
                    count += map.getOrDefault(-(c + d), 0);
                }
            }
            return count;
        }
    }

}
