package array_String;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 杨辉三角 II
 *      问题： 1, 不知道关系算法，所以得不出 n(k) 的解法
 *             2, 第二个解法，不错，在不知道关系时，这个解法很巧妙
 **/
public class GetRow {

    /**
                   1
     1            1 1
     2           1 2 1
     3          1 3 3 1
     4         1 4 6 4 1        1 ((5-1)*1)/1 ((5-2)*4)/2 ((5-3)*6)/3 ((5-4)*4)/4
     5       1 5 10 10 5 1      1 ((6-1)*1)/1 ((6-2)*5)/2 ((6-3)*10)/3 ((6-4)*10)/4 ((6-5)*5)/5
     6      1 6 15 20 15 6 1
     7     1 7 21 35 35 21 7 1

     1 ms, 在所有 Java 提交中击败了85.09%的用户
     37.3 MB, 在所有 Java 提交中击败了37.78%的用户

     期望：可以优化你的算法到 O(k) 空间复杂度
     */
    public List<Integer> getRow(int rowIndex) {
        List<Integer> list = new ArrayList<>();
        int[][] nums = new int[rowIndex + 1][rowIndex + 1];
        nums[0][0] = 1;
        for (int i = 1; i < rowIndex + 1; i++) {
            nums[i][0] = 1;
            for (int j = 1; j < i; j++) {
                nums[i][j] = nums[i - 1][j - 1] + nums[i - 1][j];
            }
            nums[i][i] = 1;
        }
        for (int num : nums[rowIndex]) {
            list.add(num);
        }
        return list;
    }


    class BestSolution {
        public List<Integer> getRow(int rowIndex) {
            int row = rowIndex + 1, i = 1;
            List<Integer> list = new ArrayList<>();
            list.add(1);
            while (i < row) {
                int now = list.get(i - 1);
                long value = (long) (row - i) * (long) now / (long) i;
                list.add((int) value);
                i++;
            }
            return list;
        }
    }

    /**
     5 ->   1, 0, 0,  0,  0, 0
            1, 1, 0,  0,  0, 0      0
            1, 2, 1,  0,  0, 0      1
            1, 3, 3,  1,  0, 0      2
            1, 4, 6,  4,  1, 0      3
            1, 5, 10, 10, 5, 1      4
     */
    class Solution2 {
        public List<Integer> getRow(int rowIndex) {
            Integer[] res = new Integer[rowIndex + 1];
            Arrays.fill(res, 1);
            for (int i = 0; i < rowIndex - 1; i++) {
                for (int j = i + 1; j > 0; j--) {
                    res[j] += res[j - 1];
                }
            }
            return Arrays.asList(res);
        }
    }

    // 空间最优
    class Solution3 {
        public List<Integer> getRow(int rowIndex) {
            List<Integer> ans = new ArrayList<>();
            int N = rowIndex;
            long pre = 1;
            ans.add(1);
            for (int k = 1; k <= N; k++) {
                long cur = pre * (N - k + 1) / k;
                ans.add((int) cur);
                pre = cur;
            }
            return ans;
        }
    }


}
