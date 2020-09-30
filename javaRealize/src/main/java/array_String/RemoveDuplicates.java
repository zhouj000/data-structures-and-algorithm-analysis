package array_String;

/**
 * 删除排序数组中的重复项
 *
 **/
public class RemoveDuplicates {

    /**
     执行用时： 1 ms , 在所有 Java 提交中击败了 98.22% 的用户
     内存消耗： 41 MB , 在所有 Java 提交中击败了 5.30% 的用户
     */
    public int removeDuplicates(int[] nums) {
        int len = nums.length;
        if (len <= 1) {
            return len;
        }
        int i = 0, j = 1;
        // 后注：直接用for循环即可，还可以省略对最后元素的处理
        while (j < len) {
            if (nums[i] == nums[j]) {
                j++;
            } else {
                nums[++i] = nums[j];
            }
        }
        if (i < len - 1 && nums[len - 1] != nums[i + 1]) {
            nums[i + 1] = nums[len - 1];
        }
        return i + 1;
    }


    class BestSolution {
        public int removeDuplicates(int[] nums) {
            int i = 0;
            for (int j = 0; j < nums.length; j++) {//数组排好序的前提下
                if (nums[j] != nums[i]) {
                    i++;
                    nums[i] = nums[j];
                }
            }
            return i + 1;
        }
    }

    class Solution2 {
        public int removeDuplicates(int[] nums) {
            if (nums == null || nums.length == 0) {
                return 0;
            }
            int index = 0;
            for (int i = 1; i < nums.length; i++) {
                if (nums[i] != nums[index]) {
                    nums[++index] = nums[i];
                }
            }
            return index + 1;
        }
    }

}
