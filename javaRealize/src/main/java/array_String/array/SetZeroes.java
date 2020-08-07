package array_String.array;

/**
 * 零矩阵
 * 问题： 1. 大部分都是先找，再设的解法，唯一就是best解法和我类似，相对提供一种新的思路
 *       PS. 我的解法 100%用时，85%空间，基本也算最优了
 **/
public class SetZeroes {

    // 有两种方式，一直是这个用标记的方式
    // 另一种就是分卫2步，先找到0的位置，然后进行填充
    public void setZeroes(int[][] matrix) {
        int len = matrix.length;
        int wit = matrix[0].length;
        int[][] mark = new int[len][wit];
        for (int i = 0; i < len; i ++) {
            for (int j = 0; j < wit; j++) {
                if (matrix[i][j] != 0 || mark[i][j] == 1) {
                    continue;
                }
                for (int k = 0; k < wit; k++) {
                    if (matrix[i][k] != 0) {
                        matrix[i][k] = 0;
                        mark[i][k] = 1;
                    }
                }
                for (int k = 0; k < len; k++) {
                    if (matrix[k][j] != 0) {
                        matrix[k][j] = 0;
                        mark[k][j] = 1;
                    }
                }
            }
        }
    }

    // 后注：这是讲第一行与第一列作为达标缓存，有0则表明这行/列需要设为0
    //       最后判断第一行和第一列是否需要设0，来完成整个矩阵的设0
    //       这是应该是先查找（也是打标），后设0思路的一种变化
    class BestSolution {
        public void setZeroes(int[][] matrix) {
            int m = matrix.length;
            if (m == 0) {
                return;
            }
            int n = matrix[0].length;
            if (n == 0) {
                return;
            }

            boolean firstRow = false;
            boolean firstColumn = false;
            for (int i = 0; i < n; i++) {
                if (matrix[0][i] == 0) {
                    firstRow = true;
                    break;
                }
            }
            for (int i = 0; i < m; i++) {
                if (matrix[i][0] == 0) {
                    firstColumn = true;
                    break;
                }
            }

            for (int y = 1; y < m ; y++) {
                for (int x = 1; x < n; x++ ){
                    if (matrix[y][x] == 0) {
                        matrix[y][0] = 0;
                        matrix[0][x] = 0;
                    }
                }
            }

            for (int y = 1; y < m; y++) {
                if (matrix[y][0] == 0) {
                    for (int x = 1; x < n; x++) {
                        matrix[y][x] = 0;
                    }
                }
            }
            for (int x = 1; x < n; x++) {
                if (matrix[0][x] == 0) {
                    for (int y = 1; y < m; y++) {
                        matrix[y][x] = 0;
                    }
                }
            }

            if (firstColumn) {
                for (int y = 0; y < m; y++) {
                    matrix[y][0] = 0;
                }
            }
            if (firstRow) {
                for (int x = 0; x < n; x++) {
                    matrix[0][x] = 0;
                }
            }
        }
    }


    class Solution2 {
        public void  setZeroes(int[][] matrix) {
            boolean[] line = new boolean[matrix.length];
            boolean[] column = new boolean[matrix[0].length];
            // 找出要清零的行列
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[0].length; j++) {
                    if (matrix[i][j] == 0) {
                        line[i] = true;
                        column[j] = true;
                    }
                }
            }

            // 开始对行清零
            for (int i = 0; i < matrix.length; i++) {
                if (line[i]) {
                    for (int j = 0; j < matrix[0].length; j++) {
                        matrix[i][j] = 0;
                    }
                }
            }

            // 开始对列清零
            for (int i = 0; i < matrix[0].length; i++) {
                if (column[i]) {
                    for (int j = 0; j < matrix.length; j++) {
                        matrix[j][i] = 0;
                    }
                }
            }
        }
    }

}
