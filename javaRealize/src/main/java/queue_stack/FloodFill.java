package queue_stack;

import java.util.*;

/**
 * 小结课程题：   图像渲染
 * 问题：      1. DFS方式，不过使用递归更好，不用NODE直接传参x,y坐标即可， 坐标边缘和同颜色判断起来也更方便
 *             2. 判重可以用 boolean[][]的方式，其实改色后就不需要判断重复了，通过颜色比较即可中断传播
 *
 **/
public class FloodFill {

    public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        List<Node> useNodes = new ArrayList<>();
        Deque<Node> nodeQueue = new ArrayDeque<>();
        int oldColor = image[sr][sc];
        int xl = image.length;
        int yl = image[0].length;

        Node root = new Node(sr, sc);
        nodeQueue.add(root);
        useNodes.add(root);
        while (!nodeQueue.isEmpty()) {
            Node base = nodeQueue.pop();
            if (image[base.getX()][base.getY()] == oldColor) {
                image[base.getX()][base.getY()] = newColor;
            }
            if (base.getX() + 1 < xl && image[base.getX() + 1][base.getY()] == oldColor) {
                Node d = new Node(base.getX() + 1, base.getY());
                if (!useNodes.contains(d)) {
                    nodeQueue.add(d);
                    useNodes.add(d);
                }
            }
            if (base.getX() - 1 >= 0 && image[base.getX() - 1][base.getY()] == oldColor) {
                Node a = new Node(base.getX() - 1, base.getY());
                if (!useNodes.contains(a)) {
                    nodeQueue.add(a);
                    useNodes.add(a);
                }
            }
            if (base.getY() + 1 < yl && image[base.getX()][base.getY() + 1] == oldColor) {
                Node w = new Node(base.getX(), base.getY() + 1);
                if (!useNodes.contains(w)) {
                    nodeQueue.add(w);
                    useNodes.add(w);
                }
            }
            if (base.getY() - 1 >= 0 && image[base.getX()][base.getY() - 1] == oldColor) {
                Node s = new Node(base.getX(), base.getY() - 1);
                if (!useNodes.contains(s)) {
                    nodeQueue.add(s);
                    useNodes.add(s);
                }
            }
        }
        return image;
    }

    class Node {
        int x, y;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return x == node.x &&
                    y == node.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    public static void main(String[] args) {
        FloodFill test = new FloodFill();
        int[][] image = {{1,1,1}, {1,1,0}, {1,0,1}};

        int[][] newImg = test.floodFill(image, 1, 1, 2);
        for (int[] mg : newImg) {
            for (int g : mg) {
                System.out.print(g + " ");
            }
            System.out.println();
        }
    }




    class SolutionBest {

        public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
            helper(image, sr, sc, image[sr][sc], newColor);
            return image;
        }

        public void helper(int[][] image, int row, int col, int oriColor, int newColor) {
            // 不合法直接退出
            if (row >= image.length || row < 0 || col >= image[0].length || col < 0) {
                return;
            }
            if (image[row][col] != oriColor) {
                return;
            }
            image[row][col] = -1;
            helper(image, row + 1, col, oriColor, newColor);
            helper(image, row - 1, col, oriColor, newColor);
            helper(image, row, col + 1, oriColor, newColor);
            helper(image, row, col - 1, oriColor, newColor);
            image[row][col] = newColor;
        }
    }

    class Solution2 {
        public boolean[][] flag;// 记录是否访问过

        public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
            int origColor = image[sr][sc];// 记录初始颜色
            flag = new boolean[image.length][image[0].length];
            fill1(image, sr, sc, origColor, newColor);
            return image;
        }

        public int fill1(int[][] image, int sr, int sc, int origColor, int newColor) {
            // 出界：超出边界索引
            if (!inArea1(image, sr, sc)) {
                return 0;
            }
            // 碰壁：遇到其他颜色直接返回，因为只找和初始颜色origColor相同的
            if (image[sr][sc] != origColor) {
                return 0;
            }
            if (flag[sr][sc]) {// 已访问过，直接返回
                return 1;
            }
            flag[sr][sc] = true;// 标记已访问

            // 四个方向递归
            int surround = fill1(image, sr - 1, sc, origColor, newColor)// 上
                         + fill1(image, sr + 1, sc, origColor, newColor)// 下
                         + fill1(image, sr, sc - 1, origColor, newColor)// 左
                         + fill1(image, sr, sc + 1, origColor, newColor);// 右
            if (surround < 4) {
                image[sr][sc] = newColor;// 颜色替换
            }
            return 1;
        }

        public boolean inArea1(int[][] image, int i, int j) {
            return i >= 0 && i < image.length
                    && j >= 0 && j < image[0].length;
        }
    }

    // 后注：和我同思路，一个是不需要用NODE，第二改颜色后可以区分，不需要判重
    class Solution3 {
        public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {

            if (image[sr][sc] == newColor){
                return image;
            }

            Queue<int[]> queue = new LinkedList();
            int num = image[sr][sc];
            queue.add(new int[]{sr,sc});
            int row = image.length;
            int clomn = image[0].length;

            while (!queue.isEmpty()){
                int[] array = queue.poll();
                image[array[0]][array[1]] = newColor;
                if (array[0] + 1 < row && image[array[0]+1][array[1]] == num){
                    queue.add(new int[]{array[0] + 1, array[1]});
                }
                if (array[0] - 1 >= 0 && image[array[0]-1][array[1]] == num){
                    queue.add(new int[]{array[0] - 1, array[1]});
                }
                if (array[1] + 1 < clomn && image[array[0]][array[1]+1] == num){
                    queue.add(new int[]{array[0], array[1]+1});
                }
                if (array[1] - 1 >= 0 && image[array[0]][array[1]-1] == num){
                    queue.add(new int[]{array[0], array[1]-1});
                }
            }
            return image;
        }
    }

    // 内存最优, 使用2个数组来对 x,y的上下左右进行计算
    class Solution4 {
        int[] dr = {0, 0, 1, -1};
        int[] dc = {1, -1, 0, 0};
        int R, C;

        private void dfs(int[][] image, int row, int col, int newColor, int original) {
            //int original = image[row][col];
            if (original == newColor) {
                return;
            }
            image[row][col] = newColor;
            for (int i = 0; i < 4; i++) {
                int new_row = row + dr[i];
                int new_col = col + dc[i];
                if (new_row >= 0 && new_row < R && new_col >= 0 && new_col < C && image[new_row][new_col] == original) {
                    dfs(image, new_row, new_col, newColor, original);
                }
            }
        }

        public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
            R = image.length;
            C = image[0].length;
            int original = image[sr][sc];
            dfs(image, sr, sc, newColor, original);
            return image;
        }
    }

}
