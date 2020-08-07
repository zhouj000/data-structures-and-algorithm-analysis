package array_String.array;

/**
 * 搜索插入位置
 *
 **/
public class SearchInsert {

    public int searchInsert(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= target) {
                return i;
            }
        }
        return nums.length;
    }

    // 用while更方便，直接条件判断
    class BestSolution {
        public int searchInsert(int[] nums, int target) {
            int i = 0;
            while (i < nums.length && nums[i] < target) {
                i++;
            }
            return i;
        }
    }


    // 折半查找
    class Solution {
        public int searchInsert(int[] nums, int target) {
            int len = nums.length;
            if (len == 0) {
                return 0;
            }
            if (nums[len - 1] < target) {
                return len;
            }
            int left = 0, right = len - 1, ans = len;
            while (left <= right) {
                int mid = ((right - left) >> 1) + left;
                if (target <= nums[mid]) {
                    right = mid - 1;
                    ans = mid;
                } else {
                    left = mid + 1;
                }
            }
            return ans;
        }
    }

}
