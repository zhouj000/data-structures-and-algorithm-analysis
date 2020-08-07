package array_String.array;

import java.util.Arrays;
import java.util.Stack;

/**
 * 对角线遍历
 *  问题： 1. 最优解2的思路很好，边界反弹
 *         2. 最优解1也是用边界的思想，不过感觉没最优解2清晰
 **/
public class FindDiagonalOrder {

    public int[] findDiagonalOrder(int[][] matrix) {
        int len = matrix.length;
        if (len == 0) {
            return new int[0];
        }
        int wit = matrix[0].length;
        if (wit == 0) {
            return new int[0];
        }
        int size = len * wit;
        int[] result = new int[size];
        int count = 0, x = 0, y = 0, opc1 = 0, opc2 = 0;
        // 向右, 向下
        int[][] op1 = {{0, 1}, {1, 0}};
        // 左下斜, 右上斜
        int[][] op2 = {{-1, 1}, {1, -1}};
        while(count < size) {
            result[count++] = matrix[x][y];
            // 先尝试走斜角
            int[] o2 = op2[opc2 % 2];
            if (x + o2[0] >= 0 && x + o2[0] < len && y + o2[1] >= 0 && y + o2[1] < wit) {
                x = x + o2[0];
                y = y + o2[1];
                continue;
            }
            // 选择方向，撞墙后不改变下次趋势方向
            int[] o1 = op1[opc1 % 2];
            int[] o12 = op1[(opc1 + 1) % 2];
            if (x + o1[0] >= 0 && x + o1[0] < len && y + o1[1] >= 0 && y + o1[1] < wit) {
                x = x + o1[0];
                y = y + o1[1];
                opc1++;
                opc2++;
            } else if (x + o12[0] >= 0 && x + o12[0] < len && y + o12[1] >= 0 && y + o12[1] < wit) {
                x = x + o12[0];
                y = y + o12[1];
                opc1++;
                opc2++;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        FindDiagonalOrder t = new FindDiagonalOrder();
//        int[][] arr = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
//        int[][] arr = {{3}, {2}};
//        int[][] arr = {{6, 9, 7, 2}};
//        int[][] arr = {{1,2,3,4}, {5,6,7,8}, {9,10,11,12}, {13,14,15,16}};
        int[][] arr = {{2,3,4}, {5,6,7}, {8,9,10}, {11,12,13}, {14,15,16}};
        int[] res = t.findDiagonalOrder(arr);
        Arrays.stream(res).forEach(x -> System.out.print(x + ", "));
    }



    class BestSolution {
        public int[] findDiagonalOrder(int[][] matrix) {
            if (matrix == null) {
                return null;
            }
            if (matrix.length == 0) {
                return new int[]{};
            }
            if (matrix.length == 1) {
                return matrix[0];
            }
            int m = matrix.length;
            int n = matrix[0].length;
            int[] res = new int[n * m];
            int size = m * n;
            int x = 0, y = 0;
            boolean flag = true;
            for (int i = 0; i < size; i++) {
                res[i] = matrix[x][y];
                // 后注；向右上斜角运动
                if (flag) {
                    x--;
                    y++;
                    // 后注；超过右边界后，向下运动
                    if (y > n - 1) {
                        y = n - 1;
                        x += 2;
                        flag = false;
                    }
                    // 后注：超过上边界，返回向右运动
                    if (x < 0) {
                        x = 0;
                        flag = false;
                    }
                } else {
                    // 后注：向左下斜角运动
                    y--;
                    x++;
                    // 后注：超过下边界，向右运动
                    if (x > m - 1) {
                        x = m - 1;
                        y += 2;
                        flag = true;
                    }
                    // 后注：超过左边界，向下运动
                    if (y < 0) {
                        y = 0;
                        flag = true;
                    }
                }
            }
            return res;
        }
    }

    class Solution2 {
        public int[] findDiagonalOrder(int[][] matrix) {
            if (matrix == null) {
                return null;
            }
            int height = matrix.length;
            if (height == 0) {
                return new int[0];
            }
            int width = matrix[0].length;
            if (width == 0) {
                return new int[0];
            }
            int i = 0;
            int j = 0;
            int index = 0;
            int count = height * width;
            int[] ans = new int[count];
            while (index < count) {
                // 后注：边界判断反转，i或j为0了，就说明要向一边进行反转了
                // 然后while将斜边上全部取得后到达另一边界
                if (i == 0 || j == width - 1) {
                    if (index >= count) {
                        break;
                    }
                    ans[index++] = matrix[i][j];
                    // 后注：上下两边界，优先向右运动
                    if (j < width - 1) {
                        j++;
                    } else {
                        i++;
                    }
                    // 后注：上下两边界，向右下角进行斜边输出
                    while (j > 0 && i < height - 1) {
                        ans[index++] = matrix[i][j];
                        i++;
                        j--;
                    }
                }
                // 后注：左右边界反之
                if (j == 0 || i == height - 1) {
                    if (index >= count) {
                        break;
                    }
                    ans[index++] = matrix[i][j];
                    if (i < height - 1) {
                        i++;
                    } else {
                        j++;
                    }
                    while (i > 0 && j < width - 1) {
                        ans[index++] = matrix[i][j];
                        i--;
                        j++;
                    }
                }
            }
            return ans;
        }
    }


    class Solution3 {
        public int[] findDiagonalOrder(int[][] matrix) {
            // 矩阵  M行
            int rowLength = matrix.length;
            if (rowLength == 0) {
                return new int[0];
            }

            int xChange = 0;
            int yChange = 0;
            int colLength = matrix[0].length;
            int[] result = new int[rowLength * colLength];
            int xIndex = 0;
            int yIndex = 0;
            int index = 0;
            do {
                result[index] = matrix[xIndex][yIndex];
                // 后注： 处理单点矩阵
                if (rowLength == 1 && colLength == 1) {
                    break;
                }
                index++;
                int xTemp = xIndex;
                int yTemp = yIndex;
                // 后注： 处理单行 或 单列矩阵
                if (rowLength == 1) {
                    yIndex++;
                    continue;
                }
                if (colLength == 1) {
                    xIndex++;
                    continue;
                }
                // 根据方向 判断下次移动位置index
                // 后注：没看懂，要debug下，以后再看
                if (xChange == 0 && yChange == 0) {
                    yIndex = yIndex + 1;
                } else if (xChange > 0 && yChange == 0) {
                    // 向左移动
                    if (xTemp == 0) {
                        xIndex = xTemp + 1;
                        yIndex = yTemp - 1;
                    } else if (xTemp == rowLength - 1) {
                        xIndex = xTemp - 1;
                        yIndex = yTemp + 1;
                    }
                } else if (xChange < 0 && yChange > 0) {
                    if (xTemp == rowLength - 1) {
                        yIndex = yTemp + 1;
                    } else if (yTemp == 0) {
                        xIndex = xTemp + 1;
                    } else {
                        yIndex = yTemp - 1;
                        xIndex = xTemp + 1;
                    }
                } else if (xChange > 0 && yChange < 0) {
                    if (yTemp == colLength - 1) {
                        xIndex = xTemp + 1;
                    } else if (xTemp == 0 && yTemp != colLength - 1) {
                        yIndex = yTemp + 1;
                    } else {
                        xIndex = xTemp - 1;
                        yIndex = yTemp + 1;
                    }
                } else if (xChange == 0 && yChange > 0) {
                    if (yTemp == 0) {
                        xIndex = xTemp - 1;
                        yIndex = yIndex + 1;
                    } else if (yTemp == colLength - 1) {
                        xIndex = xTemp + 1;
                        yIndex = yTemp - 1;
                    }
                }

                xChange = yIndex - yTemp;
                yChange = xIndex - xTemp;
            } while (xIndex != rowLength - 1 || yIndex != colLength - 1);
            result[index] = matrix[xIndex][yIndex];

            return result;
        }
    }


    class Solution4 {
        public int[] findDiagonalOrder(int[][] matrix) {
            int m = matrix.length;
            if (m == 0) {
                return new int[0];
            }
            int n = matrix[0].length;

            int[] res = new int[m * n];
            boolean up = true;
            int idx = 0, dx = -1, dy = 1;
            for (int i = 1; i <= m + n - 1; i++) {
                int[] point = cal(i, up, m, n);
                while (point[0] >= 0 && point[0] < m && point[1] >= 0 && point[1] < n) {
                    res[idx++] = matrix[point[0]][point[1]];
                    // 后注：向右上运动， up
                    point[0] += dx;
                    point[1] += dy;
                }
                // 后注：向左下运动，!up
                up = !up;
                dx = -dx;
                dy = -dy;
            }
            return res;
        }

        // 后注：决定反向点
        public int[] cal(int i, boolean up, int m, int n) {
            if (up) {
                if (i <= m) {
                    return new int[]{i - 1, 0};
                } else {
                    return new int[]{m - 1, i - m};
                }
            } else {
                if (i <= n) {
                    return new int[]{0, i - 1};
                } else {
                    return new int[]{i - n, n - 1};
                }
            }
        }
    }


    class Solution5 {
        public int[] findDiagonalOrder(int[][] matrix) {
            int l1 = matrix.length;
            int l2 = 0;
            if (l1 > 0) {
                l2 = matrix[0].length;
            }
            Stack<Integer> stack = new Stack<>();
            int[] result = new int[l1 * l2];

            int temp = 0;
            int index = 0;
            // 后注： i，一层层处理， 矩阵左上半块
            for (int i = 0; i < l1; i++) {
                if (temp % 2 == 0) {
                    int k = i, j = 0;
                    // 后注：向右上移动
                    while (k >= 0 && j < l2) {
                        result[index] = matrix[k][j];
                        index++;
                        k--;
                        j++;
                    }
                } else {
                    int k = i, j = 0;
                    // 后注：向右上运动
                    while (k >= 0 && j < l2) {
                        stack.push(matrix[k][j]);
                        k--;
                        j++;
                    }
                    // 后注：反向输出，即向左下运动
                    while (!stack.isEmpty()) {
                        result[index] = stack.pop();
                        index++;
                    }
                }
                temp++;
            }

            // 后注： i，一层层处理， 矩阵右下半块，同样处理
            for (int i = 1; i < l2; i++) {
                if (temp % 2 == 0) {
                    int k = l1 - 1, j = i;
                    while (k >= 0 && j < l2) {
                        result[index] = matrix[k][j];
                        index++;
                        k--;
                        j++;
                    }
                } else {
                    int k = l1 - 1, j = i;
                    while (k >= 0 && j < l2) {
                        stack.push(matrix[k][j]);
                        k--;
                        j++;
                    }
                    while (!stack.isEmpty()) {
                        result[index] = stack.pop();
                        index++;
                    }
                }
                temp++;
            }
            return result;
        }
    }


    class Solution {
        private int R, C;

        public int[] findDiagonalOrder(int[][] matrix) {
            if (matrix.length == 0) {
                return new int[0];
            }
            R = matrix.length;
            C = matrix[0].length;
            int dir = 1;
            int[] res = new int[R * C];
            res[0] = matrix[0][0];
            int index = 1;

            int r = 0, c = 0;
            while (r != R - 1 || c != C - 1) {
                // 更新
                int newR = r - dir, newC = c + dir;
                if (rInRange(newR) && cInRange(newC)) {
                    r = newR;
                    c = newC;
                } else if (!rInRange(newR) && cInRange(newC)) {
                    c += 1;
                    dir = -dir;
                } else if (!rInRange(newR) && !cInRange(newC)) {
                    if (dir == 1) {
                        r += 1;
                    } else {
                        c += 1;
                    }
                    dir = -dir;
                } else {
                    r += 1;
                    dir = -dir;
                }
                res[index++] = matrix[r][c];
            }
            return res;
        }

        private boolean rInRange(int r) {
            return r >= 0 && r <= R - 1;
        }

        private boolean cInRange(int c) {
            return c >= 0 && c <= C - 1;
        }
    }

}
