package search.twofen;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 二分查找
 **/
public class Search {

    /**
     * 执行用时： 0 ms , 在所有 Java 提交中击败了 100.00% 的用户
     * 内存消耗： 39.6 MB , 在所有 Java 提交中击败了 20.89% 的用户
     */
    public int search(int[] nums, int target) {
        int left = 0, right = nums.length - 1, middle;
        while (left <= right) {
            middle =  (left + right) / 2;
            int mid = nums[middle];
            if (mid == target) {
                return middle;
            } else if (mid < target) {
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }
        return -1;
    }




    // 0 ms
    class BestSolution {
        public int search(int[] nums, int target) {
            if (nums == null || nums.length < 1) {
                return -1;
            }

            int left = 0;
            int right = nums.length - 1;
            int mid;
            while (left <= right) {
                mid = left + (right - left) / 2;
                if (nums[mid] > target) {
                    right = mid - 1;
                } else if (nums[mid] < target) {
                    left = mid + 1;
                } else {
                    return mid;
                }
            }
            return -1;
        }
    }


    //  39356 kb
    class Solution2 {
        public int search(int[] nums, int target) {
            List<Integer> list = Arrays.stream(nums).boxed().collect(Collectors.toList());
            return list.indexOf(target);
        }
    }

    // 39412 kb
    class Solution3 {
        public int search(int[] nums, int target) {
            int left = 0;
            int right = nums.length - 1;

            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (nums[mid] == target) {
                    return mid;
                } else if (nums[mid] < target) {
                    left = mid + 1;
                } else if (nums[mid] > target) {
                    right = mid - 1;
                }
            }
            return -1;
        }
    }

}
