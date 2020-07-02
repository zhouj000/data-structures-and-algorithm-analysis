package array;

import java.util.Queue;

/**
 * 小结课程题：   矩阵
 * 问题：  1. 首先向用DFS方式，但是遇到递归循环问题，然后想使用BFS，需要创建一个NODE对象放到queue中，没去使用这种方式
 *         2. 等于0的点其实不用赋值，因为本身初始化就是0
 **/
public class UpdateMatrix {

    int[][] result;
    int R;
    int C;

    public int[][] updateMatrix(int[][] matrix) {
        R = matrix.length;
        C = matrix[0].length;
        result = new int[R][C];
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                bfs(matrix, i, j);
            }
        }
        return result;
    }

    private void bfs(int[][] matrix, int x, int y) {
        if (matrix[x][y] == 0) {
            result[x][y] = 0;
            return;
        }
        // 最大角距
        int maxR = x > (R - 1 - x) ? x : (R - 1 - x);
        int maxC = y > (C - 1 - y) ? y : (C - 1 - y);
        for (int i = 1; i <= maxR + maxC; i++) {
            for (int j = 0; j <= i; j++) {
                // 四个象限判断
                boolean a = x + j < R && y + (i - j) < C && matrix[x + j][y + (i - j)] == 0;
                boolean b = x + j < R && y - (i - j) >= 0 && matrix[x + j][y - (i - j)] == 0;
                boolean c = x - j >= 0 && y + (i - j) < C && matrix[x - j][y + (i - j)] == 0;
                boolean d = x - j >= 0 && y - (i - j) >= 0 && matrix[x - j][y - (i - j)] == 0;
                if (a || b || c || d) {
                    result[x][y] = i;
                    return;
                }
            }
        }
    }

    public static void main(String[] args) {
        UpdateMatrix test = new UpdateMatrix();
        int[][] image = {{0, 0, 0}, {0, 1, 0}, {1, 1, 1}};

        for (int[] mg : image) {
            for (int g : mg) {
                System.out.print(g + " ");
            }
            System.out.println();
        }
        System.out.println("=====================");

        int[][] newImg = test.updateMatrix(image);
        for (int[] mg : newImg) {
            for (int g : mg) {
                System.out.print(g + " ");
            }
            System.out.println();
        }
    }



    // 速度最优，思路一样，就是处理最短路径的方式不同，他是一层层处理，我是用最大角距组合的方式处理的
    class SolutionBest {

        public int[][] updateMatrix(int[][] matrix) {
            int row = matrix.length;
            int column = matrix[0].length;

            // 遍历矩阵
            for (int rowIndex = 0; rowIndex < row; rowIndex++) {
                for (int columnIndex = 0; columnIndex < column; columnIndex++) {
                    if (matrix[rowIndex][columnIndex] == 0) {
                        continue;
                    } else {
                        findMinLength(matrix, rowIndex, columnIndex);
                    }
                }
            }
            return matrix;
        }

        /**
         * 找出距离最短的0
         *
         * @param matrix
         * @param rowIndex
         * @param columnIndex
         */
        private void findMinLength(int[][] matrix, int rowIndex, int columnIndex) {
            int xcount = 1;
            int ycount = 1;
            int start = 1;
            while (true) {
                // 中心行
                if (xcount == 1) {
                    // 后注：左
                    if (columnIndex - ycount >= 0 && (matrix[rowIndex][columnIndex - ycount] == 0)) {
                        matrix[rowIndex][columnIndex] = ycount;
                        return;
                    }
                    // 后注：右
                    if (columnIndex + ycount < matrix[0].length && (matrix[rowIndex][columnIndex + ycount] == 0)) {
                        matrix[rowIndex][columnIndex] = ycount;
                        return;
                    }
                    ycount--;
                }

                // 向下 每下一层 rowIndex ++
                if (rowIndex + xcount < matrix.length) {
                    // 左
                    if (columnIndex - ycount >= 0 && matrix[rowIndex + xcount][columnIndex - ycount] == 0) {
                        matrix[rowIndex][columnIndex] = xcount + ycount;
                        return;
                    }

                    // 右
                    if (columnIndex + ycount < matrix[0].length && matrix[rowIndex + xcount][columnIndex + ycount] == 0) {
                        matrix[rowIndex][columnIndex] = xcount + ycount;
                        return;
                    }
                }

                // 向上 每上一层 rowIndex--
                if (rowIndex - xcount >= 0) {
                    // 左
                    if (columnIndex - ycount >= 0 && matrix[rowIndex - xcount][columnIndex - ycount] == 0) {
                        matrix[rowIndex][columnIndex] = xcount + ycount;
                        return;
                    }

                    // 右
                    if (columnIndex + ycount < matrix[0].length && matrix[rowIndex - xcount][columnIndex + ycount] == 0) {
                        matrix[rowIndex][columnIndex] = xcount + ycount;
                        return;
                    }
                }

                // 一轮结束
                if (ycount == 0) {
                    start++;
                    ycount = start;
                    xcount = 1;
                } else {
                    xcount++;
                    ycount--;
                }
            }
        }
    }

    // 和我同思路，算法有点点区别，我其实不用考虑最大角距，因为此题必有0，则不会无限找下去
    class Solution2 {
        private int[][] matrix;
        private int[][] res;
        private int x;
        private int y;

        public int[][] updateMatrix(int[][] matrix) {
            this.matrix = matrix;
            this.x = matrix.length;
            this.y = matrix[0].length;
            this.res = new int[x][y];
            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    if (matrix[i][j] != 0) {
                        int dis = 1;
                        this.getDis(i, j, dis);
                    }
                }
            }
            return res;
        }

        private void getDis(int i, int j, int dis) {
            for (int n = 0; n <= dis; n++) {
                int x1 = i + n; //x2 = 2
                int x2 = i - n; // x3 = 3
                int y1 = j + dis - n;//2
                int y2 = j - dis + n;
                if (x1 < x && y1 < y && matrix[x1][y1] == 0) {
                    res[i][j] = dis;
                    break;
                }
                if (x1 < x && y2 >= 0 && matrix[x1][y2] == 0) {
                    res[i][j] = dis;
                    break;
                }
                if (x2 >= 0 && y1 < y && matrix[x2][y1] == 0) {
                    res[i][j] = dis;
                    break;
                }
                if (x2 >= 0 && y2 >= 0 && matrix[x2][y2] == 0) {
                    res[i][j] = dis;
                    break;
                }
            }
            if (res[i][j] == 0) {
                dis++;
                getDis(i, j, dis);
            }
        }
    }

    class Solution3 {
        public int[][] updateMatrix(int[][] matrix) {
            int[][] m = matrix;
            if (m == null || m.length == 0 || m[0] == null || m[0].length == 0){
                return matrix;
            }

            int row = m.length - 1;
            int col = m[0].length - 1;

            int count = 0;
            int pre = 0;
            int curr = pre + 1;
            int next = curr + 1;
            int preCount = -1;

            do {
                count = 0;
                for (int r = 0; r <= row; r++) {
                    for (int c = 0; c <= col; c++) {
                        if (m[r][c] != curr) continue;

                        int top    = r > 0 ? m[r - 1][c] : curr;
                        int right  = c < col ? m[r][c + 1] : curr;
                        int bottom = r < row ? m[r + 1][c] : curr;
                        int left   = c > 0 ? m[r][c -1] : curr;

                        if (top == pre || right == pre ||
                                bottom == pre || left == pre)  {
                            if (left == next) {
                                count--;
                            }
                            int topRight = r > 0 && c < col ? m[r - 1][c + 1] : curr;
                            if (top == next && topRight != curr) {
                                count--;
                            }
                        } else {
                            m[r][c] = next;
                            count++;
                            if (preCount != -1) {
                                preCount--;
                                if (preCount == 0) break;
                            }
                        }
                    }
                    if (preCount == 0) break;
                }

                pre = curr;
                curr = next;
                next = next + 1;
                preCount = count;

            } while (count != 0);

            return m;
        }
    }

    // 之前想过类似填充后依赖周边节点的值+1取最小，但是会循环依赖，这里用了从左上角和右下角都遍历一边的方案，这样就可以保证正确了么？
    class Solution4 {
        public int[][] updateMatrix(int[][] matrix) {
            int m = matrix.length, n = matrix[0].length;
            int[][] dp = new int[m][n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    dp[i][j] = matrix[i][j] == 0 ? 0 : 10000;
                }
            }

            // 从左上角开始
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (i - 1 >= 0) {
                        dp[i][j] = Math.min(dp[i][j], dp[i - 1][j] + 1);
                    }
                    if (j - 1 >= 0) {
                        dp[i][j] = Math.min(dp[i][j], dp[i][j - 1] + 1);
                    }
                }
            }
            // 从右下角开始
            for (int i = m - 1; i >= 0; i--) {
                for (int j = n - 1; j >= 0; j--) {
                    if (i + 1 < m) {
                        dp[i][j] = Math.min(dp[i][j], dp[i + 1][j] + 1);
                    }
                    if (j + 1 < n) {
                        dp[i][j] = Math.min(dp[i][j], dp[i][j + 1] + 1);
                    }
                }
            }
            return dp;

        }
    }

    // 动态规划，和上面一样，感觉有点理解了：
    // 第一遍遇到0开始发散，即使用动态规划向右下角计算，第二遍反方向即可到发散点方向将之前的点全部算出
    class Solution5 {
        public int[][] updateMatrix(int[][] matrix) {
            int m = matrix.length;
            int n = matrix[0].length;
            int[][] space = new int[m][n];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (matrix[i][j] == 0) {
                        space[i][j] = 0;
                    }
                    else {
                        space[i][j] = m + n;
                    }
                }
            }

            //动态规划
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (i > 0) {
                        space[i][j] = Math.min(space[i][j], space[i - 1][j] +1);
                    }
                    if (j > 0) {
                        space[i][j] = Math.min(space[i][j], space[i][j - 1] +1);
                    }
                }
            }

            for (int i = m - 1; i >= 0; i--) {
                for (int j = n - 1; j >= 0; j--) {
                    if (i < m - 1) {
                        space[i][j] = Math.min(space[i][j], space[i + 1][j] +1);
                    }
                    if (j < n - 1) {
                        space[i][j] = Math.min(space[i][j], space[i][j + 1] +1);
                    }
                }
            }
            return space;
        }
    }


    // 空间最优
    class Solution6 {
        public int[][] updateMatrix(int[][] matrix) {
            int row = matrix.length;
            int column = matrix[0].length;
            int all = row * column;
            int ct = 0;
            int lastColor = 0;
            int[][] res = new int[row][column];
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < column; j++) {
                    if (matrix[i][j] != 0) {
                        res[i][j] = -1;
                        ct++;
                    }
                }
            }
            ct = all - ct;  // 后注：算出是0的点个数，即完成的点个数

            // 从0开始向上下左右扩散，即可得出周边点的值，然后处理间距为1的点，依次直到全部点处理完成
            // 空间最优，但是时间效率有点差
            while (ct != all) {
                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < column; j++) {
                        // 从0开始处理，未处理的都是-1，这样可以逐步计算出不同步长的点
                        if (res[i][j] == lastColor) {
                            if (i - 1 >= 0 && res[i - 1][j] == -1) {
                                res[i - 1][j] = lastColor + 1;
                                ct++;
                            }
                            if (j - 1 >= 0 && res[i][j - 1] == -1) {
                                res[i][j - 1] = lastColor + 1;
                                ct++;
                            }
                            if (i + 1 < row && res[i + 1][j] == -1) {
                                res[i + 1][j] = lastColor + 1;
                                ct++;
                            }
                            if (j + 1 < column && res[i][j + 1] == -1) {
                                res[i][j + 1] = lastColor + 1;
                                ct++;
                            }
                        }
                    }
                }
                lastColor++;
            }
            return res;
        }
    }

}
