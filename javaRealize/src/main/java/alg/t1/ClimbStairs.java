package alg.t1;

/**
 * 动态规划
 * 爬楼梯
 *
 * 迭代：只要有可能，就应用记忆化
 **/
public class ClimbStairs {

    /**
     * 第一版解法，就是100楼=到99楼走1步 + 到98楼走2步，
     * 所以用climbStairs(n - 1) + climbStairs(n - 2)去计算，最后超时了，需要记录历史步的缓存才行
     *
     * 后查了下网上，发现其实也等于 走1步后走99楼 + 走2步后走98楼到98楼，
     * 依次就相当于 f3 = f2 + f1,  f4 = f3 + f2....   因此可以使用递归滚动的方法
     *
     *
     * 执行用时： 0 ms , 在所有 Java 提交中击败了 100.00% 的用户
     * 内存消耗： 35.5 MB , 在所有 Java 提交中击败了 8.07% 的用户
     */
    public int climbStairs(int n) {
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        int pre1 = 2, pre2 = 1;
        for (int i = 2; i < n; i++) {
            int cur = pre1 + pre2;
            pre2 = pre1;
            pre1 = cur;
        }
        return pre1;
    }



    // 35148 kb
    class BestSolution {
        public int climbStairs(int n) {
            int p = 0, q = 0, r = 1;
            for (int i = 1; i <= n; i++) {
                p = q;
                q = r;
                r = p + q;
            }
            return r;
        }
    }

}
