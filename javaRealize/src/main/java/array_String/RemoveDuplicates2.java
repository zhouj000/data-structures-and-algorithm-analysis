package array_String;

/**
 * 数组类算法： 删除排序数组中的重复项 II
 **/
public class RemoveDuplicates2 {

    /**
     * 执行用时： 1 ms , 在所有 Java 提交中击败了 82.97% 的用户
     * 内存消耗： 38.5 MB , 在所有 Java 提交中击败了 78.43% 的用户
     */
    public int removeDuplicates(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int slow = 1, fast = 1;
        int repNum = nums[0];
        int repLen = 1;
        while (fast < nums.length) {
            int fastNum = nums[fast];
            if (fastNum == repNum) {
                repLen++;
            } else {
                if (fast > slow) {
                    nums[slow] = fastNum;
                }
                repNum = fastNum;
                repLen = 1;
                fast++;
                slow++;
                continue;
            }
            if (repLen > 2) {
                fast++;
            } else {
                repNum = fastNum;
                nums[slow] = nums[fast];
                fast++;
                slow++;
            }
        }
        return slow;
    }




    //  0 ms
    class BestSolution {
        public int removeDuplicates(int[] nums) {
            int n = nums.length;
            if (n <= 2) {
                return n;
            }
            int fp = 1;
            for (int sp = 2; sp < n; sp++) {
                if (nums[fp - 1] != nums[sp]) {
                    fp++;
                    nums[fp] = nums[sp];
                }
            }
            return fp + 1;
        }
    }

    //  38584 kb
    class Solution3 {
        public int removeDuplicates(int[] nums) {
            int idx = 2;
            for (int i = 2; i < nums.length; i++) {
                if (nums[i] != nums[idx - 2]) {
                    nums[idx++] = nums[i];
                }
            }
            return idx;
        }
    }

    // 1 ms
    class Solution2 {
        public int removeDuplicates(int[] nums) {
            // Initialize the counter and the second pointer.
            int j = 1, count = 1;
            // Start from the second element of the array and process
            // elements one by one.
            for (int i = 1; i < nums.length; i++) {
                // If the current element is a duplicate, increment the count.
                if (nums[i] == nums[i - 1]) {
                    count++;
                } else {
                    // Reset the count since we encountered a different element
                    // than the previous one.
                    count = 1;
                }
                // For a count <= 2, we copy the element over thus
                // overwriting the element at index "j" in the array
                if (count <= 2) {
                    nums[j++] = nums[i];
                }
            }
            return j;
        }
    }

    // 38640 kb
    class Solution4 {
        public int removeDuplicates(int[] nums) {
            if (nums.length <= 2) {
                return nums.length;
            }
            int index = 2;
            int i = 2;
            while (i < nums.length) {
                if (nums[i] != nums[index - 2]) {
                    nums[index] = nums[i];
                    index++;
                    i++;
                } else {
                    i++;
                }
            }
            return index;
        }
    }

    //  38664 kb
    class Solution5 {
        public int removeDuplicates(int[] nums) {
            if (nums.length <= 2) {
                return nums.length;
            }
            int times = 1;
            int j = 0;
            for (int i = 1; i < nums.length; i++) {
                if (nums[j] == nums[i]) {
                    times++;
                } else {
                    times = 1;
                }
                if (times <= 2) {
                    j++;
                    nums[j] = nums[i];
                }
            }
            return j + 1;
        }
    }

}
