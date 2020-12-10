package hash_list_map;

import sun.reflect.annotation.ExceptionProxy;

import java.util.*;

/**
 * 前 K 个高频元素
 * 执行用时： 31 ms , 在所有 Java 提交中击败了 6.65% 的用户
 * 内存消耗： 41.2 MB , 在所有 Java 提交中击败了 68.65% 的用户
 **/
public class TopKFrequent {

    public int[] topKFrequent(int[] nums, int k) {
        if (nums.length == 1) {
            return nums;
        }
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        List<Integer> list = new ArrayList<>();
        map.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getValue)).forEach(
                o -> list.add(o.getKey())
        );
        Collections.reverse(list);
        int[] res = new int[k];
        for (int i = 0; i < k; i++) {
            res[i] = list.get(i);
        }
        return res;
    }



    // 5 ms
    class BestSolution {
        int[] res;

        public int[] topKFrequent(int[] nums, int k) {
            MyHashMap map = new MyHashMap(nums.length);
            for (int num : nums) {
                map.increase(num);
            }

            // 题目没有求有序输出，因此可选择平均速度更快的快排思路
            List<int[]> list = new ArrayList();
            for (MyEntry entry : map.entries) {
                int[] tmp = new int[2];
                tmp[0] = entry.key;
                tmp[1] = entry.value;
                list.add(tmp);
            }
            res = new int[k];
            quickSort(list, k, 0, list.size() - 1, 0);
            return res;
        }

        private void quickSort(List<int[]> list, int k, int start, int end, int resInd) {
            if (start > end) {
                return;
            }
            int p = new Random().nextInt(end - start + 1) + start;
            Collections.swap(list, p, end);
            int ind = start;
            for (int i = start; i < end; i++) {
                if (list.get(i)[1] > list.get(end)[1]) {
                    Collections.swap(list, ind++, i);
                }
            }
            Collections.swap(list, ind, end);

            int sortSize = ind - start + 1;
            if (k < sortSize) {
                quickSort(list, k, start, ind - 1, resInd);
            } else {
                for (int i = start; i <= ind; i++) {
                    res[resInd++] = list.get(i)[0];
                }
                if (k > sortSize) {
                    quickSort(list, k - sortSize, ind + 1, end, resInd);
                }
            }
        }

        /**
         * 基于桶和二叉搜索树的HashMap
         */
        class MyHashMap {
            public MyEntry[] buckets;
            public List<MyEntry> entries;

            public MyHashMap(int size) {
                buckets = new MyEntry[size < 17 ? 17 : size];
                entries = new ArrayList<>(size);
            }

            public void increase(int key) {
                int hash = key < 0 ? -key : key;// 使用key的绝对值作为哈希值
                int bucketIndex = hash % buckets.length;//哈希函数：哈希值 % 桶的数量
                MyEntry entry = buckets[bucketIndex];
                if (entry == null) {
                    buckets[bucketIndex] = new MyEntry(key);
                    entries.add(buckets[bucketIndex]);
                    return;
                }

                MyEntry parent = null;
                while (entry != null) {
                    if (entry.key == key) {
                        // 指定的key已经在MyHashMap中了，直接将其值加一
                        entry.value += 1;
                        return;
                    }
                    parent = entry;
                    entry = hash < entry.hash ? entry.left : entry.right;
                }

                if (hash < parent.hash) {
                    parent.left = new MyEntry(key);
                    entries.add(parent.left);
                } else {
                    parent.right = new MyEntry(key);
                    entries.add(parent.right);
                }
            }
        }

        class MyEntry {
            public int key;     // 数字
            public int value;   // 数字出现的次数
            public int hash;
            public MyEntry left;
            public MyEntry right;

            public MyEntry(int key) {
                this.key = key;
                this.hash = key < 0 ? -key : key;
                this.value = 1;
            }
        }
    }

    // 7 ms
    class Solution2 {
        public int[] topKFrequent(int[] nums, int k) {
            List[] bucket = new List[nums.length + 1];
            Map<Integer, Integer> hashMap = new HashMap<>();
            for (int num : nums) {
                hashMap.put(num, hashMap.getOrDefault(num, 0) + 1);
            }
            for (int key : hashMap.keySet()) {
                int frequency = hashMap.get(key);
                if (bucket[frequency] == null) {
                    bucket[frequency] = new ArrayList<>();
                }
                bucket[frequency].add(key);
            }
            List<Integer> res = new ArrayList<>();
            for (int pos = bucket.length - 1; pos >= 0 && res.size() < k; pos--) {
                if (bucket[pos] != null) {
                    res.addAll(bucket[pos]);
                }
            }
            int[] result = new int[res.size()];
            for (int i = 0; i < result.length; i++) {
                result[i] = res.get(i);
            }
            return result;
        }
    }

    // 9ms
    class Solution3 {
        public int[] topKFrequent(int[] nums, int k) {
            ArrayList<int[]> rec = new ArrayList<>();
            Arrays.sort(nums);
            int count = 1;
            for (int i = 1; i < nums.length; i++) {
                if (nums[i] != nums[i - 1]) {
                    rec.add(new int[]{nums[i - 1], count});
                    count = 1;
                } else {
                    count++;
                }
            }
            rec.add(new int[]{nums[nums.length - 1], count});
            rec.sort((a, b) -> b[1] - a[1]);
            int[] ans = new int[k];
            for (int i = 0; i < k; i++) {
                ans[i] = rec.get(i)[0];
            }
            return ans;
        }
    }


    //  41164 kb
    class Solution4 {
        public int[] topKFrequent(int[] nums, int k) {
            if (nums == null || nums.length == 0 || k == 0) {
                return null;
            }
            HashMap<Integer, int[]> map = new HashMap<>();
            ArrayList<int[]> topK = new ArrayList<>(k);
            for (int n : nums) {
                if (map.containsKey(n)) {
                    int[] value = map.get(n);
                    int t = value[1] + 1;
                    int[] new_value = new int[]{n, t};
                    map.put(n, new_value);
                    //如果topK里面包含了老值，删掉老值，换新值
                    int index = topK.indexOf(value);
                    if (index != -1) {
                        topK.remove(index);
                        topK.add(new_value);
                    } else {
                        topK.sort(new Comparator<int[]>() {
                            @Override
                            public int compare(int[] o1, int[] o2) {
                                if (o1[1] == o2[1]) {
                                    return 0;
                                }
                                return o1[1] > o2[1] ? 1 : -1;
                            }
                        });
                        //不存在老值，判断这个值是否有资格入列
                        if (t > topK.get(0)[1]) {
                            topK.remove(0);
                            topK.add(new_value);
                        }
                    }
                } else {
                    int[] new_value = new int[]{n, 1};
                    map.put(n, new_value);
                    if (topK.size() < k) {
                        topK.add(new_value);
                    }
                }
            }
            int[] res = new int[k];
            for (int i = 0; i < k; i++) {
                res[i] = topK.get(i)[0];
            }
            return res;
        }
    }

    // 41188 kb
    class Solution5 {
        public int[] topKFrequent(int[] nums, int k) {
            Map<Integer, Integer> map = new HashMap<>();
            for (int i = 0; i < nums.length; i++) {
                if (map.containsKey(nums[i])) {
                    map.put(nums[i], map.get(nums[i]) + 1);
                } else {
                    map.put(nums[i], 1);
                }
            }
            PriorityQueue<int[]> queue = new PriorityQueue(new Comparator<int[]>() {
                @Override
                public int compare(int[] o1, int[] o2) {
                    return o1[1] - o2[1];
                }
            });

            for (Map.Entry entry : map.entrySet()) {
                if (queue.size() != k) {
                    queue.offer(new int[]{(int) entry.getKey(), (int) entry.getValue()});
                } else {
                    if (queue.peek()[1] < (int) entry.getValue()) {
                        queue.poll();
                        queue.offer(new int[]{(int) entry.getKey(), (int) entry.getValue()});
                    }
                }
            }
            int[] res = new int[k];
            int i = 0;
            while (!queue.isEmpty()) {
                int[] poll = queue.poll();
                res[i] = poll[0];
                i++;
            }
            return res;
        }
    }

}
