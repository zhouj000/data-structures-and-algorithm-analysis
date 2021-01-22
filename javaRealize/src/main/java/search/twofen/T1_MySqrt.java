package search.twofen;

/**
 * 标准的二分查找模板
 * x 的平方根
 *
 **/
public class T1_MySqrt {

    /**
     * int乘法后越界 有点烦
     *
     * 执行用时： 2 ms , 在所有 Java 提交中击败了 45.97% 的用户
     * 内存消耗： 35.3 MB , 在所有 Java 提交中击败了 92.11% 的用户
     */
    public int mySqrt(int x) {
        int left = 0, right = x > 46340 ? 46340 : x;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (mid == 46340) {
                return 46340;
            }
            int d = mid * mid;
            if (d == x) {
                return mid;
            } else if (d < x) {
                left = mid + 1;
                if (left * left > x) {
                    return mid;
                }
            } else {
                right = mid - 1;
                if (right * right < x) {
                    return right;
                }
            }
        }
        return 0;
    }




    // 2 ms     最优的是 return (int)Math.sqrt(x);
    class Solution2 {
        public int mySqrt(int x) {
            long left = 0;
            long right = x;
            while (left < right) {
                long mid = (left + right) / 2 + 1;
                if (mid * mid > x) {
                    right = mid - 1;
                } else {
                    left = mid;
                }
            }
            return (int) left;
        }
    }

    //  35560 kb
    class Solution3 {
        public int mySqrt(int x) {
            //二分查找 [0, x] 之间
            int left = 0;
            int right = x;
            while (left < right) {
            /*
            这里为什么使用的是 (left + right + 1) >>> 1 ， 而不是跟之前一样使用 (left + right) >>> 1 ？
            因为我们下面需要的是 right = mid - 1 而不是 left = mid + 1，如果 (left + right) >>> 1 的话 left = mid 可能会造成死循环

            为什么使用 right = mid - 1？
            在不存在 一个整数平方等于 x 的情况下，比如 x = 8，有以下情况：
            因为我们要求的是 相邻的两个数 x1 和 x2 ，x1 的平方比 x 小， x2 的平方比 x 大
            比如 x = 8，那么 x1 = 2, x2 = 3
            而我们需要返回的是 x1 = 2，即较小的值，因此，当我们遇到大值的时候，需要跳过该大值，往小值方向找
            与以往的跳过小值，找大值不同
            如果是以往的二分查找模板，那么当 break 找到的就会是 x2 = 3
            */
                int mid = (left + right + 1) >>> 1;
                //注意：有案例是 2147395599，求得 mid 平方会超过 int ，因此需要将平方结果转为 long
                if ((long) mid * mid > x) {
                    right = mid - 1;
                } else {
                    left = mid;
                }
            }
            return left;
        }
    }

    //  35564 kb
    class Solution4 {
        public int mySqrt(int x) {
            long low = 0;
            long high = x;
            long mid = 0;
            while (low < high) {
                mid = (low + high) / 2;
                if (mid * mid < x) {
                    low = mid + 1;
                } else {
                    high = mid;
                }
            }
            return (low * low > x ? -1 : 0) + (int) low;
        }
    }

    //  35600 kb
    class Solution5 {
        public int mySqrt(int x) {
            int left = 0;
            int right = x;
            int result = -1;
            while (left <= right) {
                int mid = (left + right) / 2;
                if ((long) mid * mid <= x) {
                    left = mid + 1;
                    result = mid;
                } else {
                    right = mid - 1;
                }
            }
            return result;
        }
    }

}
