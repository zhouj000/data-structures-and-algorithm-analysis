package queue_stack.queue;

/**
 * 课程题：岛屿的数量
 *
 * 自己的设计思路：（没有实现）
 * 1.从0,0根节点开始向右和下广度BFS遍历，类似一棵树，找到1的放入used队列
 * 2.判断1的节点，之后找下一个根节点查找下一棵树的岛屿
 * 3.卡在怎么找下一个根节点，还不会重复计算
 *
 * 最后网上找算法，没考虑到的点：
 * 1.可以把找到的陆地修改为0，避免重复的方法
 * 2.按树的方式思路遍历，虽然想到四个方向，但没按图的思路遍历
 */
public class Class2_BFS {

    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        int rows = grid.length;
        int cols = grid[0].length;
        int count = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // 注意char
                if (grid[i][j] == '1') {
                    count++;
                    bfsSearch(grid, i, j, rows, cols);
                }
            }
        }
        return count++;
    }

    // 每遇到'1'后, 开始向四个方向 递归搜索. 搜到后变为'0',
    // 因为相邻的属于一个island. 然后开始继续找下一个'1'.
    private void bfsSearch(char[][] grid,
                           int i, int j, int rows, int cols) {
        if (i < 0 || i >= rows || j < 0 || j >= cols) {
            return;
        }
        if (grid[i][j] != '1') {
            return;
        }
        // 也可以才用一个visited数组，标记遍历过的岛屿
        grid[i][j] = '0';
        bfsSearch(grid, i + 1, j, rows, cols);
        bfsSearch(grid, i - 1, j, rows, cols);
        bfsSearch(grid, i, j + 1, rows, cols);
        bfsSearch(grid, i, j - 1, rows, cols);
    }


/*
    int BFS(Node root, Node target) {
        Queue<Node> queue;  // store all nodes which are waiting to be processed
        Set<Node> used;     // store all the used nodes
        int step = 0;       // number of steps neeeded from root to current node
        // initialize
        add root to queue;
        add root to used;
        // BFS
        while (queue is not empty) {
            step = step + 1;
            // iterate the nodes which are already in the queue
            int size = queue.size();
            for (int i = 0; i < size; ++i) {
                Node cur = the first node in queue;
                return step if cur is target;
                for (Node next : the neighbors of cur) {
                    if (next is not in used) {
                        add next to queue;
                        add next to used;
                    }
                }
                remove the first node from queue;
            }
        }
        return -1;          // there is no path from root to target
    }
*/


    class BFSBest {

        public int numIslands(char[][] grid) {
            if (grid.length == 0) {
                return 0;
            }
            int count = 0;
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {
                    if (grid[i][j] == '1') {
                        bfs(grid, i, j);
                        count++;
                    }
                }
            }
            return count;
        }

        public void bfs(char[][] grid, int i, int j) {
            grid[i][j] = '0';
            if (i != 0 && grid[i - 1][j] == '1') {
                bfs(grid, i - 1, j);
            }
            if (i < grid.length - 1 && grid[i + 1][j] == '1') {
                bfs(grid, i + 1, j);
            }
            if (j != 0 && grid[i][j - 1] == '1') {
                bfs(grid, i, j - 1);
            }
            if (j < grid[0].length - 1 && grid[i][j + 1] == '1') {
                bfs(grid, i, j + 1);
            }
        }
    }

}
