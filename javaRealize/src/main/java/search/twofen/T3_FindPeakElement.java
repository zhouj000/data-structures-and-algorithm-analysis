package search.twofen;

/**
 * T3_二分查找的另一种独特形式
 * 寻找峰值
 *
 **/
public class T3_FindPeakElement {

    /**
     * 执行用时： 0 ms , 在所有 Java 提交中击败了 100.00% 的用户
     * 内存消耗： 37.9 MB , 在所有 Java 提交中击败了 84.26% 的用户
     */
    public int findPeakElement(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        if (nums.length == 1) {
            return 0;
        }
        int l = 0, r = nums.length - 1;
        while (l < r) {
            if (nums[l + 1] < nums[l]) {
                return l;
            } else {
                l++;
            }
            if (nums[r] > nums[r - 1]) {
                return r;
            } else {
                r--;
            }
        }
        if (nums[l - 1] < nums[l] && nums[l] > nums[l + 1]) {
            return l;
        }
        return -1;
    }




    // 38184 kb
    // 后注：这不和T2一样么，感觉T3就是硬掰一个
    public class BestSolution {
        public int findPeakElement(int[] nums) {
            int l = 0, r = nums.length - 1;
            while (l < r) {
                int mid = (l + r) / 2;
                if (nums[mid] > nums[mid + 1])
                    r = mid;
                else
                    l = mid + 1;
            }
            return l;
        }
    }


}
