package tree.print;

import java.util.*;

/**
 * 二叉树的中序遍历
 **/
public class InorderTraversal {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    // 非递归
    /**
     * 执行用时： 1 ms , 在所有 Java 提交中击败了 42.83% 的用户
     * 内存消耗： 36.7 MB , 在所有 Java 提交中击败了 79.59% 的用户
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        if (root == null) {
            return list;
        }
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            if (node.right != null) {
                stack.push(node.right);
                node.right = null;
            }
            if (node.left != null) {
                stack.push(node);
                stack.push(node.left);
                node.left = null;
            } else {
                list.add(node.val);
            }
        }
        return list;
    }

    // 递归
    /**
     * 执行用时： 0 ms , 在所有 Java 提交中击败了 100.00% 的用户
     * 内存消耗： 36.6 MB , 在所有 Java 提交中击败了 86.00% 的用户
     */
    public List<Integer> inorderTraversal2(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        recc(root, list);
        return list;
    }

    private void recc(TreeNode node, List<Integer> list) {
        if (node == null) {
            return;
        }
        recc(node.left, list);
        list.add(node.val);
        recc(node.right, list);
    }




    // 空间最优 36592 kb
    class Solution1 {
        public List<Integer> inorderTraversal(TreeNode root) {
            List<Integer> list = new ArrayList<>();
            Stack<TreeNode> stack = new Stack<>();
            TreeNode cur = root;
            while (cur != null || !stack.isEmpty()) {
                if (cur != null) {
                    stack.push(cur);
                    cur = cur.left;
                } else {
                    cur = stack.pop();
                    list.add(cur.val);
                    cur = cur.right;
                }
            }
            return list;
        }
    }

}
