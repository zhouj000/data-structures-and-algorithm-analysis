package array_String.string;

/**
 * 最大连续1的个数
 *
 **/
public class FindMaxConsecutiveOnes {

    public int findMaxConsecutiveOnes(int[] nums) {
        int slow = -1;
        int val = 1;
        int max = 0;
        for (int fast = 0; fast < nums.length; fast++) {
            if (nums[fast] != val) {
                if (fast > slow) {
                    max = max < (fast - slow - 1) ? (fast - slow - 1) : max;
                }
                slow = fast;
            }
        }
        max = max < (nums.length - slow -1) ? (nums.length - slow - 1) : max;
        return max;
    }

    public static void main(String[] args) {
        int[] nums = {1,0,1,1,0,1};
        FindMaxConsecutiveOnes t = new FindMaxConsecutiveOnes();
        System.out.println(t.findMaxConsecutiveOnes(nums));
    }




    class BestSolution {
        public int findMaxConsecutiveOnes(int[] nums) {
            int lastZero = -1;//上一个零的下标
            int doubleZeroDis = 0;//两个零相距
            int maxConsecutiveOnes = 0;//最大的连续零
            int max = nums.length - 1;

            for (int i = 0; i <= max; i++) {
                if (nums[i] == 0) {
                    doubleZeroDis = i - lastZero - 1;
                    lastZero = i;
                    if (doubleZeroDis > maxConsecutiveOnes) {
                        maxConsecutiveOnes = doubleZeroDis;
                    }
                }
            }
            if (nums[max] == 1) {
                doubleZeroDis = nums.length - lastZero - 1;
                if (doubleZeroDis > maxConsecutiveOnes) {
                    maxConsecutiveOnes = doubleZeroDis;
                }
            }
            return maxConsecutiveOnes;
        }
    }

    // 后注： 这个方法很直观，直接遇到1就++，挺好
    class Solution2 {
        public int findMaxConsecutiveOnes(int[] nums) {
            int i = 0;
            int res = 0;
            int count = 0;
            while (i < nums.length) {
                if (nums[i] == 1) {
                    count++;
                } else {
                    res = Math.max(res, count);
                    count = 0;
                }
                i++;
            }
            return Math.max(res, count);
        }
    }

    class Solution3 {
        public int findMaxConsecutiveOnes(int[] nums) {
            int max = 0;
            int count = 0;
            for (int num : nums) {
                if (num == 1) {
                    count++;
                    max = Math.max(count, max);
                } else {
                    count = 0;
                }
            }
            return max;
        }
    }

}
