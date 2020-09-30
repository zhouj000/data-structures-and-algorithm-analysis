package array_String;

/**
 * 寻找旋转排序数组中的最小值
 *  问题： 1. 思路都是一样的，折半查找
 *        2. 折半写法可以优化一点 (我是用了快(即mid)慢指针的写法)
 *
 **/
public class FindMin {

    /**
     执行用时： 0 ms, 在所有 Java 提交中击败了 100.00% 的用户
     内存消耗：39.5 MB, 在所有 Java 提交中击败了 51.71% 的用户
     */
    public int findMin(int[] nums) {
        int len = nums.length;
        int i = 0, j = len / 2;
        while (i < len) {
            if (i == j) {
                i = i + 1 >= len ? 0 : i + 1;
                break;
            }
            if (nums[i] < nums[j]) {
                i = j;
                j = (j + len) / 2;
            } else {
                j = (i + j) / 2;
            }
        }
        return nums[i];
    }


    class SolutionBest {
        public int findMin(int[] n) {
            //if(n.length==0) return -1;
            int lo = 0, hi = n.length - 1;
            while (n[lo] != n[hi]) {
                int mid = lo + (hi - lo) / 2;
                //if(n[mid]==target)return mid;
                if (n[mid] < n[hi]) {
                    hi = mid;
                } else {
                    lo = mid + 1;
                }
            }
            return n[lo];
        }
    }

    // 空间最优
    class Solution2 {
        public int findMin(int[] nums) {
            int l = 0;
            int r = nums.length - 1;
            int mid;
            while (l <= r) {
                //查找范围内顺序直接返回
                if (nums[l] <= nums[r]) {
                    return nums[l];
                }
                mid = l + (r - l) / 2;
                if (nums[mid] <= nums[r]) {
                    r = mid;
                } else {
                    l = mid + 1;
                }
            }
            return 0;
        }
    }

}
