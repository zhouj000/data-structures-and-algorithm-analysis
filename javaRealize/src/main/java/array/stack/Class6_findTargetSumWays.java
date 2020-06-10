package array.stack;

import java.util.Arrays;

/**
 * 课程题：目标和
 *
 * 问题：   1. 没考虑不成功的情况，测试用例没有覆盖这个
 *          2. 我的解法是最简单的递归思路，问题是时间复杂度太高，O(2^n)
 *          3.
 **/
public class Class6_findTargetSumWays {

    int count = 0;

    /**
     * 非负整数数组，数组非空，且长度不会超过20
     * 初始的数组的和不会超过1000
     * 保证返回的最终结果能被32位整数存下
     */
    public int findTargetSumWays(int[] nums, int S) {
        sum(nums.length-1, nums, S);
        return count;
    }

    private void sum(int index, int[] nums, int res) {
        int de1 = res + nums[index];
        int de2 = res - nums[index];
        if (index == 0) {
            count += de1 == 0 ? 1 : 0;
            count += de2 == 0 ? 1 : 0;
        } else {
            sum(index - 1, nums, de1);
            sum(index - 1, nums, de2);
        }
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1, 1, 1, 1, 1};
        Class6_findTargetSumWays t = new Class6_findTargetSumWays();
        System.out.println(t.findTargetSumWays(nums, 3));
    }








    /**
     * 时间最优   动态规划
     *
     * 状态转移方程是前i个数字，和等于j的数量等于前i-1个数字，
     * 和等于j-当前数字的数量 + 前i-1个数字，和等于j+当前数字的数量
     */
    class SolutionBest {
        public int findTargetSumWays(int[] nums, int S) {
            int sum = 0;
            for (int n : nums) {
                sum += n;
            }
            int res = sum < S || (sum + S) % 2 > 0 ? 0 : subsetSum(nums, (sum + S) >> 1);
            return res;
        }

        public int subsetSum(int[] nums, int target) {
            int[] dp = new int[target + 1];
            dp[0] = 1;
            for (int n : nums) {
                for (int i = target; i >= n; i--) {
                    dp[i] += dp[i - n];
                }
            }
            return dp[target];
        }
    }

    /**
     * 时间最优2 + 空间最优
     * 动态规划 + 找规律
     *
     * 我们可以将这组数看成两部分，P 和 N，其中 P 使用正号，N 使用负号，那么可以推导出一下关系：
     * 1、target = sum(P) - sum(N)           目标 = 一些数集合 - 一些数集合
     * 2、sum(nums) = sum(P) + sum(N)        总和 = 一些数集合 + 一些数集合
     * 由1可以得出：sum(P) = target + sum(N)
     * 由2可以得出：sum(N) = sum(nums) - sum(P)
     * 综合以上，可以得出：
     * sum(P) = (target + sum(nums)) / 2     只要看正数集合达成指定sum的可能性数量即可
     *
     * 因此只要找到一个子集，令它们都取正号，并且和等于 (target + sum(nums))/2
     * 就证明存在解，这就属于正常的0-1背包问题的范畴了
     * 需要注意target + sum(nums)必须为偶数，否则 sum(P) 就是小数了，这和题目要求的所有数都是非负整数不符
     *
     */
    class Solution2 {
        public int findTargetSumWays(int[] nums, int S) {
            int sum = 0;
            for (int num : nums) {
                sum += num;
            }
            // 如果是奇数，那么无法被2整除，不符合规则
            if (sum < S || (sum + S) % 2 == 1) {
                return 0;
            }
            // 新的目标和(sumNums + S) / 2
            int w = (sum + S) / 2;
            // 正常的背包问题，在nums中找几个数，满足和为newTarget
            // dp[i]表示从数组nums找出和为i的组合情况
            int[] dp = new int[w + 1];
            dp[0] = 1;
            for (int num : nums) {
                for (int i = w; i >= num; i--) {
                    // 更新dp
                    dp[i] = dp[i] + dp[i - num];
                }
            }
            return dp[w];
        }


        // 类似思路的不同写法    背包算法
        public int findTargetSumWays2(int[] nums, int S) {
            int sum = 0;
            for (int num : nums) {
                sum += num;
            }
            // 总和+S = 奇数 ->找不到组合 返回0个
            if ((sum + S) % 2 == 1 || sum < S) {
                return 0;
            }

            int target = (sum + S) / 2, length = nums.length;
            // 都多申请了一位0
            int[][] dp = new int[length + 1][target + 1];

            //初始化 0个数组成0的组合有1种。（什么都不选 默认就是0）
            dp[0][0] = 1;

            for (int i = 1; i < length + 1; i++) {
                for (int j = 0; j < target + 1; j++) {
                    //dp多加了一位0，dp的1对应num的0
                    if (j - nums[i - 1] < 0) {
                        dp[i][j] = dp[i - 1][j];
                    } else {
                        // 不选 or 选
                        dp[i][j] = dp[i - 1][j] + dp[i - 1][j - nums[i - 1]];
                    }
                }
            }
            return dp[length][target];
        }
    }

    /**
     * 简单动态规划
     *
     * 二维数组dp[i][j]表示用数组中的前i个元素，组成和为j的方案数
     * 考虑第i个数nums[i]，它可以被添加 + 或 -, 因此状态转移方程如下：
     * dp[i][j] = dp[i - 1][j - nums[i]] + dp[i - 1][j + nums[i]]  ===>  i-1时2种算法方案数之和
     * 也可以写成递推的形式：
     * dp[i][j + nums[i]] += dp[i - 1][j]
     * dp[i][j - nums[i]] += dp[i - 1][j]
     * 因为题目中提到所有数的和不超过 1000，那么 j 的最小值可以达到 -1000。在 java 中，是不允许数组的下标为负数的
     * 因此我们需要给dp[i][j]的第二维预先增加 1000，那么新的递推关系如下：
     * dp[i][j + nums[i] + 1000] += dp[i - 1][j + 1000]
     * dp[i][j - nums[i] + 1000] += dp[i - 1][j + 1000]
     */
    class Solution3 {
        public int findTargetSumWays(int[] nums, int S) {
            int total = 0;
            // 求和的最大值
            for (int num : nums) {
                total += num;
            }
            // 初始的数组的和不会超过total，因此最大为total，最小为-total
            int[][] dp = new int[nums.length][2 * total + 1];
            // 针对nums[0]，先求出第一步，这里+total只是为了防止负数的坐标
            dp[0][total + nums[0]] = 1;
            dp[0][total - nums[0]] += 1;

            for (int i = 1; i < nums.length; i++) {
                for (int j = -total; j <= total; j++) {
                    // 如果上一步有求出和为sum，那么可以继续计算下一步
                    if (dp[i - 1][j + total] > 0) {
                        dp[i][j + total + nums[i]] += dp[i - 1][j + total];
                        dp[i][j + total - nums[i]] += dp[i - 1][j + total];
                    }
                }
            }
            return S > total ? 0 : dp[nums.length - 1][S + total];
        }

        // 同思路
        public int findTargetSumWays2(int[] nums, int S) {
            int[] dp = new int[2001];
            dp[nums[0] + 1000] = 1;
            dp[-nums[0] + 1000] += 1;
            for (int i = 1; i < nums.length; i++) {
                int[] next = new int[2001];
                for (int sum = -1000; sum <= 1000; sum++) {
                    if (dp[sum + 1000] > 0) {
                        next[sum + nums[i] + 1000] += dp[sum + 1000];
                        next[sum - nums[i] + 1000] += dp[sum + 1000];
                    }
                }
                dp = next;
            }
            return S > 1000 ? 0 : dp[S + 1000];
        }
    }

    // 类似动态规划 ，但是用DFS实现，混合种
    class Solution4 {
        public int findTargetSumWays(int[] nums, int S) {
            int zero = 0;
            int right = 0;
            for (int num : nums) {
                right += num;
                if (num == 0) {
                    zero++;
                }
            }
            int[][] cache = new int[nums.length][2000];
            for (int[] ints : cache) {
                Arrays.fill(ints, Integer.MIN_VALUE);
            }
            return dfs(nums, S, 0, 0, cache, right, zero);
        }

        private int dfs(int[] nums, int S, int cur, int sum, int[][] cache, int right, int zero) {
            if (cur == nums.length) {
                return S == sum ? 1 : 0;
            }
            if (sum + right == S || sum - right == S) {
                return (int) Math.pow(2, zero);
            }
            if (sum + right < S || sum - right > S) {
                return 0;
            }
            if (cache[cur][sum + 1000] != Integer.MIN_VALUE) {
                return cache[cur][sum + 1000];
            }
            if (nums[cur] == 0) {
                return cache[cur][sum + 1000] = 2 * dfs(nums, S, cur + 1, sum + nums[cur], cache, right - nums[cur], zero - 1);
            } else {
                return cache[cur][sum + 1000] = dfs(nums, S, cur + 1, sum + nums[cur], cache, right - nums[cur], zero) +
                        dfs(nums, S, cur + 1, sum - nums[cur], cache, right - nums[cur], zero);
            }
        }
    }

}
