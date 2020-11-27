package hash_list_map.map;

import java.util.HashMap;
import java.util.Map;

/**
 * 两数之和
 * 执行用时： 0 ms , 在所有 Java 提交中击败了 100.00% 的用户
 * 内存消耗： 38.5 MB , 在所有 Java 提交中击败了 87.17% 的用户
 **/
public class TwoSum {

    public int[] twoSum(int[] nums, int target) {
        int i = 0, j;
        int length = nums.length;
        for (; i < length; i++) {
            for (j = i + 1; j < length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[0];
    }


    // 后注：这个效率竟然还不如上面的双循环，估计是测试集太小的缘故
    class Solution2 {
        public int[] twoSum(int[] nums, int target) {
            Map<Integer, Integer> map = new HashMap<>();
            for (int i = 0; i < nums.length; i++) {
                if (map.containsKey(target - nums[i])) {
                    return new int[]{i, map.get(target - nums[i])};
                }
                map.put(nums[i], i);
            }
            return new int[0];
        }
    }

}
