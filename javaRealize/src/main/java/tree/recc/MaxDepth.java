package tree.recc;

/**
 * 二叉树的最大深度
 *
 **/
public class MaxDepth {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
    }

    /**
     * 执行用时： 0 ms , 在所有 Java 提交中击败了 100.00% 的用户
     * 内存消耗： 38.5 MB , 在所有 Java 提交中击败了 66.58% 的用户
     */
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left_depth = maxDepth(root.left);
        int right_depth = maxDepth(root.right);
        return Math.max(left_depth, right_depth) + 1;
    }



    // 0ms
    class Solution {
        public int maxDepth(TreeNode root) {
            return helper(root, 0);
        }

        public int helper(TreeNode node, int current) {
            if (node == null) {
                return current;
            }
            return Math.max(helper(node.left, current + 1), helper(node.right, current + 1));
        }
    }

    // 38404 kb
    class Solution2 {
        public int maxDepth(TreeNode root) {
            if (root == null) {
                return 0;
            }
            return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
        }
    }

}
