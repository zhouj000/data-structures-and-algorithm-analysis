package search.twofen;

/**
 * T2_二分查找的高级模板
 * 第一个错误的版本
 *
 **/
public class T2_FirstBadVersion {

    boolean isBadVersion(int version) { return version >= 4;}

    /**
     * 执行用时： 17 ms , 在所有 Java 提交中击败了 39.31% 的用户
     * 内存消耗： 35 MB , 在所有 Java 提交中击败了 87.81% 的用户
     */
    public int firstBadVersion(int n) {
        int left = 1, right = n;
        while (left < right) {
            int mid = left + (right - left) / 2;
            boolean bad = isBadVersion(mid);
            if (bad) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        // 后注：这个不用判断，   返回left和right都可以
        if (left != n && isBadVersion(left)) {
            return left;
        }
        return right;
    }




    // 12 ms    35164 kb
    public class BestSolution {
        public int firstBadVersion(int n) {
            int left = 0, right = n, mid = 1;
            while (left < right) {
                mid = left + (right - left) / 2;
                if (!isBadVersion(mid)) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }
            return left;
        }
    }

}
