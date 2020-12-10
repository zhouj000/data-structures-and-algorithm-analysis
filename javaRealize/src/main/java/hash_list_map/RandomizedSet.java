package hash_list_map;

import java.util.*;

/**
 * 常数时间插入、删除和获取随机元素
 *
 * 执行用时： 316 ms , 在所有 Java 提交中击败了 5.03% 的用户
 * 内存消耗： 45.3 MB , 在所有 Java 提交中击败了 5.05% 的用户
 **/
public class RandomizedSet {

    HashSet<Integer> set;

    /**
     * Initialize your data structure here.
     */
    public RandomizedSet() {
        set = new HashSet<>();
    }

    /**
     * Inserts a value to the set. Returns true if the set did not already contain the specified element.
     */
    public boolean insert(int val) {
        return set.add(val);
    }

    /**
     * Removes a value from the set. Returns true if the set contained the specified element.
     */
    public boolean remove(int val) {
        return set.remove(val);
    }

    /**
     * Get a random element from the set.
     */
    public int getRandom() {
        Object[] list = set.toArray();
        int length = list.length;
        int index = (int) (Math.random() * length);
        return (Integer) list[index];
    }




    // 7 ms
    class BestRandomizedSet {

        Map<Integer, Integer> map = new HashMap<>();
        List<Integer> data = new ArrayList<>();

        public BestRandomizedSet() {
        }

        public boolean insert(int val) {
            if (map.containsKey(val)) {
                return false;
            }
            map.put(val, data.size());
            data.add(val);
            return true;
        }

        public boolean remove(int val) {
            Integer index = map.remove(val);
            if (index == null) {
                return false;
            }
            if (index != data.size() - 1) {
                int last = data.remove(data.size() - 1);
                data.set(index, last);
                map.put(last, index);
            } else {
                data.remove(data.size() - 1);
            }
            return true;
        }

        public int getRandom() {
            return data.get((int) (Math.random() * data.size()));
        }
    }




    class RandomizedSet2 {
        private final Random RANDOM = new Random();
        private final HashMap<Integer, Integer> indexMap = new HashMap<>();
        private final ArrayList<Integer> elements = new ArrayList<>();

        public boolean insert(int value) {
            Integer index = indexMap.putIfAbsent(value, elements.size());
            if (index != null) {
                return false;
            }
            elements.add(value);
            return true;
        }

        public boolean remove(int value) {
            Integer index = indexMap.get(value);
            if (index == null) {
                return false;
            }
            // 获取列表指定位置的元素
            int lastIndex = elements.size() - 1;
            // get value的那个key 是index
            int lastElement = elements.get(lastIndex);
            // 让last位置上的是（size（）-1）这个值
            indexMap.put(lastElement, index);
            // 出去val
            indexMap.remove(value);
            // 将last存在idx的位置上
            elements.set(index, lastElement);
            // 去掉list最后一个元素
            elements.remove(lastIndex);
            return true;
        }

        public int getRandom() {
            int randomIndex = RANDOM.nextInt(elements.size());
            return elements.get(randomIndex);
        }
    }




    // 42816 kb
    public class RandomizedSet3 {
        public int[] vals;
        private Map<Integer,Integer> map;
        private int idx;

        public RandomizedSet3() {
            vals = new int[10000];
            map = new HashMap<>();
            idx = 0;
        }

        public boolean insert(int val) {
            if (map.containsKey(val)) {
                return false;
            }
            this.vals[idx] = val;
            map.put(val, idx);
            idx++;
            return true;
        }

        public boolean remove(int val) {
            if (!map.containsKey(val)) {
                return false;
            }
            int pos = map.get(val);
            map.remove(val);
            if (pos == idx - 1) {
                vals[pos] = 0;
                idx--;
                return true;
            } else {
                vals[pos] = vals[idx - 1];
                map.put(vals[idx - 1], pos);
                idx--;
                return true;
            }
        }

        public int getRandom() {
            Random random = new Random();
            int pos = random.nextInt(idx);
            return vals[pos];
        }
    }

}
