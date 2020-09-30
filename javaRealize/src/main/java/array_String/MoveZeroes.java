package array_String;

/**
 * 移动零
 *
 **/
public class MoveZeroes {

    /**
     执行用时： 1 ms , 在所有 Java 提交中击败了 16.74% 的用户
     内存消耗： 39.2 MB , 在所有 Java 提交中击败了 42.66% 的用户
     */
    public void moveZeroes(int[] nums) {
        int len = nums.length;
        if (len <= 1) {
            return;
        }
        int j = 0;
        for (int i = 0; i < len && j < len; i++) {
            j = j > i ? j : i;
            while (j < len && nums[j] == 0) {
                j++;
            }
            int k = j < len ? j : len - 1;
            if (nums[i] == 0 && nums[k] != 0) {
                int tmp = nums[i];
                nums[i] = nums[k];
                nums[k] = tmp;
            }
        }
    }




    // 后注：i相当于我的j作为快指针一直向前移动，k作为慢指针来交换元素，且必为与0才进入if交换
    class BestSolution {
        public void moveZeroes(int[] nums) {
            int k = 0;
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] != 0) {
                    nums[k] = nums[i];
                    if (i != k) {
                        nums[i] = 0;
                    }
                    k++;
                }
            }
        }
    }

    class Solution2 {
        public void moveZeroes(int[] nums) {
            int n = nums.length;
            if (n < 2) {
                return;
            }
            int i = 0, j = 1;
            while (j < n) {
                if (nums[i] != 0 && nums[j] != 0) {
                    i++;
                    j++;
                } else if (nums[i] == 0 && nums[j] == 0) {
                    j++;
                } else if (nums[i] != 0 && nums[j] == 0) {
                    i++;
                    j++;
                } else if (nums[i] == 0 && nums[j] != 0) {
                    int tmp = nums[i];
                    nums[i] = nums[j];
                    nums[j] = tmp;
                    i++;
                    j++;
                }
            }
        }
    }

    // 空间最优
    class Solution3 {
        public void moveZeroes(int[] nums) {
            if (nums == null || nums.length == 0) {
                return;
            }
            int slow, fast;
            slow = fast = 0;
            while (fast < nums.length) {
                if (nums[fast] == 0) {
                    ++fast;
                } else {
                    nums[slow++] = nums[fast++];
                }
            }
            while (slow < nums.length) {
                nums[slow++] = 0;
            }
            return;
        }
    }

}
