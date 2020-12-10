package hash_list_map.map;

import java.util.*;

/**
 * 两个数组的交集 II
 * 执行用时： 4 ms , 在所有 Java 提交中击败了 49.10% 的用户
 * 内存消耗： 38.6 MB , 在所有 Java 提交中击败了 84.87% 的用户
 **/
public class Intersect {

    // 这个基本和空间最优算法类似
    public int[] intersect(int[] nums1, int[] nums2) {
        if (nums1.length == 0 || nums2.length == 0) {
            return new int[]{};
        }
        if (nums2.length < nums1.length) {
            return intersect(nums2, nums1);
        }

        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums1) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        List<Integer> list = new ArrayList<>();
        for (int num : nums2) {
            if (map.getOrDefault(num, 0) > 0) {
                list.add(num);
                map.put(num, map.get(num) - 1);
            }
        }
        // 后注：直接用 Arrays.copyOfRange(res, 0, index); 即可，不用list再转了
        int[] result = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }


    class BestSolution {
        public int[] intersect(int[] nums1, int[] nums2) {
            //1、先排序
            Arrays.sort(nums1);
            Arrays.sort(nums2);
            //2、设置指针及新的数组
            int index = 0, index1 = 0, index2 = 0;
            int copy[] = new int[nums1.length > nums2.length ? nums1.length : nums2.length];
            while (index1 < nums1.length && index2 < nums2.length) {
                if (nums1[index1] == nums2[index2]) {
                    copy[index] = nums1[index1];
                    index++;
                    index1++;
                    index2++;
                } else {
                    if (nums1[index1] > nums2[index2]) {
                        //往后找有没有更大的值来匹配
                        index2++;
                    } else {
                        index1++;
                    }
                }
            }
            return Arrays.copyOfRange(copy, 0, index);
        }
    }
}
