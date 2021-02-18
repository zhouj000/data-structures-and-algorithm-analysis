package search.twofen;

/**
 *  T2_二分查找的高级模板
 *  寻找峰值
 *
 **/
public class T2_FindPeakElement {

    /**
     * 执行用时： 0 ms , 在所有 Java 提交中击败了 100.00% 的用户
     * 内存消耗： 38.4 MB , 在所有 Java 提交中击败了 5.19% 的用户
     */
    public int findPeakElement(int[] nums) {
        if (nums == null) {
            return -1;
        }
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            // 就是找升序后的降序，而且 left < right，不需要校验mid + 1越界
            if (nums[mid] > nums[mid + 1]) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }


    // 38184 kb
    public class BestSolution {
        public int findPeakElement(int[] nums) {
            int l = 0, r = nums.length - 1;
            while (l < r) {
                int mid = (l + r) / 2;
                if (nums[mid] > nums[mid + 1]) {
                    r = mid;
                } else {
                    l = mid + 1;
                }
            }
            return l;
        }
    }

}
