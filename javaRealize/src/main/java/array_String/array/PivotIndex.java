package array_String.array;

/**
 * 寻找数组的中心索引
 *
 * 问题： 1. 比较死板的用左边等于右边之和，而用总和就可以简单计算
 *        PS. 类似这种数组加法问题，应该都能用到总和来简便计算
 **/
public class PivotIndex {

    public int pivotIndex(int[] nums) {
        if (nums.length == 0) {
            return -1;
        }
        for (int i = 0; i < nums.length; i++) {
            int left = 0;
            for (int j = 0; j < i; j++) {
                left += nums[j];
            }
            int right = 0;
            for (int j = i + 1; j < nums.length; j++) {
                right += nums[j];
            }
            if (left == right) {
                return i;
            }
        }
        return -1;
    }


    // 用总和的方式比较
    class BestSolution {
        public int pivotIndex(int[] nums) {
            int sum = 0;
            for (int num : nums) {
                sum += num;
            }
            int acc = 0;
            for (int i = 0; i < nums.length; i++) {
                if (acc * 2 + nums[i] == sum) {
                    return i;
                }
                acc += nums[i];
            }
            return -1;
        }
    }

}
