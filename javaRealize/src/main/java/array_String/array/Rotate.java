package array_String.array;

/**
 * 旋转矩阵
 * 问题： 1. 作弊使用新增空间完成的
 * 2. 看到解法后发现，其实就是向量的思路，分解为2步，因为只能靠元素替换
 **/
public class Rotate {

    public void rotate(int[][] matrix) {
        int len = matrix.length;
        int[][] re = new int[len][len];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                int y = Math.abs(i - len + 1);
                re[j][y] = matrix[i][j];
            }
        }
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                matrix[i][j] = re[i][j];
            }
        }
    }


    class BestSolution {
        public void rotate(int[][] matrix) {
            int n = matrix.length;
            int mid = n >> 1;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < mid; j++) {
                    int tmp = matrix[i][j];
                    matrix[i][j] = matrix[i][n - 1 - j];
                    matrix[i][n - 1 - j] = tmp;
                }
            }
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n - i; j++) {
                    int tmp = matrix[i][j];
                    matrix[i][j] = matrix[n - 1 - j][n - 1 - i];
                    matrix[n - 1 - j][n - 1 - i] = tmp;
                }
            }
        }
    }

    class Solution2 {
        public void rotate(int[][] matrix) {
            int n = matrix.length;
            //先水平翻转
            for (int row = 0; row < n / 2; ++row) {
                for (int col = 0; col < n; ++col) {
                    int temp = matrix[row][col];
                    matrix[row][col] = matrix[n - row - 1][col];
                    matrix[n - row - 1][col] = temp;
                }
            }
            //再对角线反转
            for (int row = 0; row < n; ++row) {
                for (int col = 0; col < row; ++col) {
                    int temp = matrix[row][col];
                    matrix[row][col] = matrix[col][row];
                    matrix[col][row] = temp;
                }
            }
        }
    }

    // 后注：一次性4个点同时翻转，排除掉重复即可
    class Solution3 {
        public void rotate(int[][] matrix) {
            int n = matrix.length;
            for (int i = 0; i < n >> 1; i++) {
                for (int j = 0; j < (n + 1) / 2; j++) {
                    int temp = matrix[i][j];
                    matrix[i][j] = matrix[n - j - 1][i];
                    matrix[n - j - 1][i] = matrix[n - i - 1][n - j - 1];
                    matrix[n - i - 1][n - j - 1] = matrix[j][n - i - 1];
                    matrix[j][n - i - 1] = temp;
                }
            }
        }
    }

}
