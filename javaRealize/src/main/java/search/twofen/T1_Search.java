package search.twofen;

/**
 *  标准的二分查找模板
 *  搜索旋转排序数组
 *
 **/
public class T1_Search {

    /**
     * 执行用时： 0 ms , 在所有 Java 提交中击败了 100.00% 的用户
     * 内存消耗： 37.7 MB , 在所有 Java 提交中击败了 77.01% 的用户
     *
     * if里面的第二层if写了好久，反复过单测才找到 边界值要与target和mid比较找出反向二分的情况
     */
    public int search(int[] nums, int target) {
        if (nums == null) {
            return -1;
        }
        int len = nums.length;
        int left = 0, right = len - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            }
            if (target > nums[mid]) {
                if (nums[right] < target && nums[left] > nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else {
                if (nums[left] > target && nums[right] < nums[mid]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        return -1;
    }




    //  0 ms
    class BestSolution {
        public int search(int[] nums, int target) {
            if (nums.length == 1) {
                return target == nums[0] ? 0 : -1;
            }
            int right = nums.length - 1;
            int left = 0;
            while (left < right) {
                if (nums[left] == target) {
                    return left;
                }
                if (nums[right] == target) {
                    return right;
                }
                int mid = (left + right) / 2;
                if (nums[mid] == target) {
                    return mid;
                }
                if (nums[left] < nums[mid]) {
                    if (nums[mid] > target && nums[left] < target) {
                        right = mid - 1;
                    } else {
                        left = mid + 1;

                    }
                } else {
                    if (nums[mid] < target && target < nums[right]) {
                        left = mid + 1;
                    } else {
                        right = mid - 1;
                    }
                }
            }
            return -1;
        }
    }

    // 37788 kb
    class Solution2 {
        public int search(int[] nums, int target) {
            int left = 0;
            int right = nums.length - 1;
            while (left <= right) {
                int mid = (left + right) / 2;
                if (nums[mid] == target) {
                    return mid;
                }
                if (nums[mid] < nums[right]) {
                    if (target > nums[mid] && target <= nums[right]) {
                        left = mid + 1;
                    } else {
                        right = mid - 1;
                    }
                } else {
                    if (target < nums[mid] && target >= nums[left]) {
                        right = mid - 1;
                    } else {
                        left = mid + 1;
                    }
                }
            }
            return -1;
        }
    }

}
