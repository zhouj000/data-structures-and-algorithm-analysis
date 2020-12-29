package tree.print;

import java.util.*;

/**
 * 二叉树的层序遍历
 * BFS  queue   广度遍历
 * DFS  stack   深度遍历
 **/
public class LevelOrder {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
    }

    List<List<Integer>> resultList = new ArrayList<>();

    /**
     * 执行用时： 1 ms , 在所有 Java 提交中击败了 93.34% 的用户
     * 内存消耗： 38.6 MB , 在所有 Java 提交中击败了 81.41% 的用户
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null) {
            return resultList;
        }
        int i = 0;
        Queue<TreeNode> queue1 = new LinkedList<>();
        Queue<TreeNode> queue2 = new LinkedList<>();
        queue1.add(root);
        while (!queue1.isEmpty() || !queue2.isEmpty()) {
            if (i % 2 == 0) {
                fillNode(queue1, queue2);
            } else {
                fillNode(queue2, queue1);
            }
            i++;
        }
        return resultList;
    }

    private void fillNode(Queue<TreeNode> queue1, Queue<TreeNode> queue2) {
        List<Integer> list = new ArrayList<>();
        while (!queue1.isEmpty()) {
            TreeNode node = queue1.poll();
            list.add(node.val);
            if (node.left != null) {
                queue2.add(node.left);
            }
            if (node.right != null) {
                queue2.add(node.right);
            }
        }
        resultList.add(list);
    }


    // 0 ms
    class BestSolution {
        public List<List<Integer>> levelOrder(TreeNode root) {
            List<List<Integer>> ans = new ArrayList<>();
            dfs(ans, 0, root);
            return ans;
        }

        private void dfs(List<List<Integer>> ans, int level, TreeNode node) {
            if (node == null) {
                return;
            }
            if (ans.size() < level + 1) {
                ans.add(new ArrayList<>());
            }
            ans.get(level).add(node.val);
            dfs(ans, level + 1, node.left);
            dfs(ans, level + 1, node.right);
        }
    }

    // 38652 kb
    class Solution2 {
        public List<List<Integer>> levelOrder(TreeNode root) {
            List<List<Integer>> ans = new ArrayList<>();
            if (root == null) {
                return ans;
            }
            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);
            while (!queue.isEmpty()) {
                List<Integer> path = new ArrayList<>();
                int size = queue.size();
                while (size-- > 0) {
                    TreeNode node = queue.poll();
                    path.add(node.val);
                    if (node.left != null) {
                        queue.offer(node.left);
                    }
                    if (node.right != null) {
                        queue.offer(node.right);
                    }
                }
                ans.add(path);
            }
            return ans;
        }
    }

}