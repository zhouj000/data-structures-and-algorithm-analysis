package tree.recc;

import java.util.Stack;

/**
 * 路径总和
 **/
public class HasPathSum {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
    }

    boolean pathSum = false;

    /**
     * 执行用时： 0 ms , 在所有 Java 提交中击败了 100.00% 的用户
     * 内存消耗： 38.4 MB , 在所有 Java 提交中击败了 75.72% 的用户
     */
    public boolean hasPathSum(TreeNode root, int sum) {
        if (root == null) {
            return false;
        }
        recc(root, sum);
        return pathSum;
    }

    private void recc(TreeNode node, int less) {
        if (node.left == null && node.right == null) {
            pathSum = pathSum || less - node.val == 0;
            return;
        }
        if (node.left != null) {
            recc(node.left, less - node.val);
        }
        if (node.right != null) {
            recc(node.right, less - node.val);
        }
    }




    // 0 ms, 38384 kb
    class Solution {
        public boolean hasPathSum(TreeNode root, int sum) {
            if (root == null) {
                return false;
            }
            if (root.left == null && root.right == null) {
                return sum - root.val == 0;
            }
            return hasPathSum(root.left, sum - root.val) || hasPathSum(root.right, sum - root.val);
        }
    }

    // 1 ms
    class Solution2 {
        public boolean hasPathSum(TreeNode root, int sum) {
            Stack<Integer> stack1 = new Stack<>();
            Stack<TreeNode> stack2 = new Stack<>();
            if (root == null) {
                return false;
            }
            stack1.push(root.val);
            stack2.push(root);
            while (!stack1.isEmpty()) {
                int count = stack1.size();
                while (count > 0) {
                    TreeNode p = stack2.pop();
                    int a = stack1.pop();
                    if (p.left != null) {
                        stack1.push(a + p.left.val);
                        stack2.push(p.left);
                    }
                    if (p.right != null) {
                        stack1.push(a + p.right.val);
                        stack2.push(p.right);
                    }
                    if (p.left == null && p.right == null && a == sum) {
                        return true;
                    }
                    count--;
                }
            }
            return false;
        }
    }

}
