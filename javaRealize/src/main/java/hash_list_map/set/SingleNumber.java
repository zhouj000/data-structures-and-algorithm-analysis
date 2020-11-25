package hash_list_map.set;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 只出现一次的数字
 * 执行用时： 12 ms , 在所有 Java 提交中击败了 17.29% 的用户
 * 内存消耗： 38.8 MB , 在所有 Java 提交中击败了 73.73% 的用户
 **/
public class SingleNumber {

    public int singleNumber(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            if (!set.add(nums[i])) {
                set.remove(nums[i]);
            }
        }
        return set.iterator().next();
    }



    // 后注： 用异或来判断，相同的数字会变为0
    class BestSolution {
        public int singleNumber(int[] nums) {
            int res = 0;
            for (int n : nums) {
                res ^= n;
            }
            return res;
        }
    }

    // 后注：我有想到这个方案，但是觉得比我用set会时间效率低，结果出乎意料
    class Solution2 {
        final int N = 3 * 10000 + 5;

        public int singleNumber(int[] nums) {
            int counter[] = new int[2 * N];
            for (int i = 0; i < nums.length; ++i) {
                counter[nums[i] + N]++;
            }
            for (int i = 0; i < 2 * N; ++i) {
                if (counter[i] == 1) {
                    return i - N;
                }
            }
            return -1;
        }
    }

    class Solution3 {
        public int singleNumber(int[] nums) {
            return Arrays.stream(nums).reduce(0, (acc, x) -> acc ^= x);
        }
    }

    class Solution4 {
        public int singleNumber(int[] nums) {
            Arrays.sort(nums);
            if (nums.length == 1) {
                return nums[0];
            }
            for (int i = 0; i < nums.length - 1; i++) {
                if (nums[i] != nums[++i]) {
                    return nums[--i];
                }
            }
            if (nums[nums.length - 1] != nums[nums.length - 2]) {
                return nums[nums.length - 1];
            }
            return 0;
        }
    }

}
