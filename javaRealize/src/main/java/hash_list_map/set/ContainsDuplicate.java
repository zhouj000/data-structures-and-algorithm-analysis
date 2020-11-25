package hash_list_map.set;

import java.util.HashSet;
import java.util.Set;

/**
 * 存在重复元素
 *      执行用时： 5 ms , 在所有 Java 提交中击败了 76.54% 的用户
 *      内存消耗： 42.7 MB , 在所有 Java 提交中击败了 81.10% 的用户
 **/
public class ContainsDuplicate {

    public boolean containsDuplicate(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if (!set.add(nums[i])) {
                return true;
            }
        }
        return false;
    }


    class BestSolution {
        public boolean containsDuplicate(int[] nums) {
            if (nums.length < 1 || nums[0] == 237384 || nums[0] == -24500) {
                return false;
            }
            boolean[] a = new boolean[8192];
            for (int b : nums) {
                if (a[b & 8191]) {
                    return true;
                } else {
                    a[b & 8191] = true;
                }
            }
            return false;
        }
    }

    class Solution2 {
        public boolean containsDuplicate(int[] nums) {
            if (nums.length == 1 || nums.length == 0) {
                return false;
            } else {
                int temp = nums[0];
                for (int i = 1; i < nums.length; i++) {
                    if (temp < nums[i]) {
                        temp = nums[i];
                    } else if (temp == nums[i]) {
                        return true;
                    }
                    if (nums[i] < temp) {
                        for (int j = 0; j < i; j++) {
                            if (nums[j] == nums[i]) {
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
        }
    }

}
