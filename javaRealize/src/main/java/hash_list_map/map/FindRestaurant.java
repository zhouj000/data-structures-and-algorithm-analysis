package hash_list_map.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 两个列表的最小索引总和
 * 执行用时： 10 ms , 在所有 Java 提交中击败了 78.03% 的用户
 * 内存消耗： 39.4 MB , 在所有 Java 提交中击败了 33.23% 的用户
 **/
public class FindRestaurant {

    public String[] findRestaurant(String[] list1, String[] list2) {
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < list1.length; i++) {
            map.put(list1[i], i);
        }
        int min = Integer.MAX_VALUE;
        List<String> list = new ArrayList<>();
        for (int j = 0; j < list2.length; j++) {
            String str = list2[j];
            if (map.containsKey(str)) {
                if (min == map.get(str) + j) {
                    list.add(str);
                } else if (min > map.get(str) + j) {
                    min = map.get(str) + j;
                    list.clear();
                    list.add(str);
                }
            }
        }
        String[] array = new String[list.size()];
        // 后注：这里的入参数组大小可以不设，只是确定类型
        array = list.toArray(array);
        return array;
    }




    class BestSolution {
        public String[] findRestaurant(String[] list1, String[] list2) {
            if (list1 == null || list1.length == 0 || list2 == null || list2.length == 0) {
                return new String[]{};
            }
            // 后注：这里让第一个list的长度是小于第二个的，有什么目的么？仅仅为了map小一点？
            if (list1.length > list2.length) {
                return findRestaurant(list2, list1);
            }

            Map<String, Integer> hash = new HashMap<>(list1.length, 1);
            List<String> list = new ArrayList<>();

            for (int i = 0; i < list1.length; i++) {
                hash.put(list1[i], i);
            }
            int minIndexSum = Integer.MAX_VALUE;
            for (int j = 0; j < list2.length; j++) {
                String restaurant = list2[j];
                Integer i = hash.get(restaurant);
                if (i != null) {
                    int sum = i + j;
                    if (sum <= minIndexSum) {
                        if (sum < minIndexSum) {
                            minIndexSum = sum;
                            // 找到有更小的 要把之前的清掉
                            list.clear();
                        }
                        list.add(restaurant);
                    }
                }
                // 后注： minIndexSum <= j  这个可以省略后面的遍历
                if (list.size() > 0 && minIndexSum <= j) {
                    break;
                }
            }
            return list.toArray(new String[0]);
        }
    }

    class Solution2 {
        public String[] findRestaurant(String[] list1, String[] list2) {
            if (list1.length > list2.length) {
                return findRestaurant(list2, list1);
            }
            Map<String, Integer> map = new HashMap();
            for (int i = 0; i < list1.length; i++) {
                map.put(list1[i], i);
            }
            int sum;
            int min_index = Integer.MAX_VALUE;
            List<String> comm = new ArrayList();
            for (int i = 0; i < list2.length && i <= min_index; i++) {
                if (map.containsKey(list2[i])) {
                    sum = map.get(list2[i]) + i;
                    if (sum < min_index) {
                        comm.clear();
                        comm.add(list2[i]);
                        min_index = sum;
                    } else if (sum == min_index) {
                        comm.add(list2[i]);
                    }
                }
            }
            return comm.toArray(new String[comm.size()]);
        }
    }

    // 空间最优
    class Solution3 {
        public String[] findRestaurant(String[] list1, String[] list2) {

            List<String> ans = new ArrayList<>() ;

            for (int cnt = 0; cnt < list1.length + list2.length - 1; cnt++) {
                for (int i = 0; i <= cnt; i++) {
                    if (i < list1.length && cnt - i < list2.length && list1[i].equals(list2[cnt - i])) {
                        ans.add(list1[i]);
                    }
                }
                if (ans.size() > 0) {
                    break;
                }
            }
            return ans.toArray(new String[ans.size()]);
        }
    }

}
