package array_String.string;

/**
 * 长度最小的子数组
 *
 **/
public class MinSubArrayLen {

    // 时间 在所有 Java 提交中击败了87.51%的用户
    // 空间 在所有 Java 提交中击败了27.34%的用户
    public int minSubArrayLen(int s, int[] nums) {
        int min = nums.length + 1;
        if (min == 0) {
            return 0;
        }
        int slow = -1, fast = -1;
        int sum = 0;
        int len = 0;
        while (slow < nums.length) {
            if (sum < s) {
                fast++;
                if (fast == nums.length) {
                    min = min > nums.length ? 0 : min;
                    break;
                }
                len++;
                sum += nums[fast];
            } else {
                if (min > len) {
                    min = len;
                }
                slow++;
                len--;
                sum -= nums[slow];
            }
        }
        return min;
    }

    public static void main(String[] args) {
        MinSubArrayLen t = new MinSubArrayLen();
        int[] nums = {1,2,3,4,5};
        int n = t.minSubArrayLen(11, nums);
        System.out.println(n);
    }




    class BestSolution {
        public int minSubArrayLen(int s, int[] nums) {
            if (nums.length == 0) {
                return 0;
            }
            int len = Integer.MAX_VALUE;
            int start = 0;
            int end = 0;
            int sum = 0;
            while (end < nums.length) {
                sum += nums[end];
                end++;
                while (sum >= s) {
                    sum -= nums[start];
                    len = Math.min(len, end - start);
                    start++;
                }
            }

            if (len == Integer.MAX_VALUE) {
                return 0;
            } else {
                return len;
            }
        }
    }


    class Solution2 {
        public int minSubArrayLen(int s, int[] nums) {
            int left = 0, right = 0;
            int n = nums.length;
            if (n == 0) {
                return 0;
            }
            int sum = 0;
            int minLen = Integer.MAX_VALUE;

            while (right < n) {
                int c = nums[right];
                sum += c;
                right++;
                while (sum >= s) {
                    int d = nums[left];
                    minLen = Math.min(minLen, right - left);
                    sum -= d;
                    left++;
                }
            }
            return minLen == Integer.MAX_VALUE ? 0 : minLen;
        }
    }

}
