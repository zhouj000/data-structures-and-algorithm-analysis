package hash_list_map.set;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 两个数组的交集
 * 执行用时： 3 ms , 在所有 Java 提交中击败了 86.86% 的用户
 * 内存消耗： 38.8 MB , 在所有 Java 提交中击败了 42.42% 的用户
 *
 **/
public class Intersection {

    public int[] intersection(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        int i = 0, j = 0;
        Set<Integer> set = new HashSet<>();
        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] == nums2[j]) {
                set.add(nums1[i]);
                i++;
                j++;
            } else if (nums1[i] > nums2[j]) {
                j++;
            } else {
                i++;
            }
        }
        int[] array = new int[set.size()];
        int k = 0;
        for (Integer num : set) {
            array[k++] = num;
        }
        return array;
    }


    // 后注：和我思路一样，不过因为创建数组太大，返回会有0，且有重复，所以用了set作为容器，最后转为数组输出
    class BestSolution {
        public int[] intersection(int[] nums1, int[] nums2) {
            Arrays.sort(nums1);
            Arrays.sort(nums2);
            int len1 = nums1.length, len2 = nums2.length;
            int[] res = new int[len1 + len2];
            int idx = 0, idx1 = 0, idx2 = 0;
            while (idx1 < len1 && idx2 < len2) {
                int num1 = nums1[idx1], num2 = nums2[idx2];
                if (num1 == num2) {
                    if (idx == 0 || num1 != res[idx - 1]) {
                        res[idx++] = num1;
                    }
                    idx1++;
                    idx2++;
                } else if (num1 < num2) {
                    idx1++;
                } else {
                    idx2++;
                }
            }
            return Arrays.copyOfRange(res, 0, idx);
        }
    }


    class Solution2 {
        public int[] intersection(int[] nums1, int[] nums2) {
            if (nums1 == null && nums2 == null) {
                return new int[0];
            }
            Set<Integer> set = new HashSet<>();
            Set<Integer> set1 = new HashSet<>();
            //1.首先将数组num1的数据全部放到set中
            for (int i = 0; i < nums1.length; i++) {
                set.add(nums1[i]);
            }
            for (Integer i : nums2) {
                if (set.contains(i)) {
                    set1.add(i);
                }
            }
            int sum = 0;
            int[] ret = new int[set1.size()];
            for (Integer e : set1) {
                ret[sum++] = e;
            }
            return ret;
        }
    }


    // 空间最优...?
    class Solution {
        public int[] intersection(int[] nums1, int[] nums2) {
            HashSet<Integer> set1 = new HashSet<Integer>();
            for (Integer n : nums1) {
                set1.add(n);
            }
            HashSet<Integer> set2 = new HashSet<Integer>();
            for (Integer n : nums2) {
                set2.add(n);
            }

            set1.retainAll(set2);

            int [] output = new int[set1.size()];
            int idx = 0;
            for (int s : set1) {
                output[idx++] = s;
            }
            return output;
        }
    }

}
