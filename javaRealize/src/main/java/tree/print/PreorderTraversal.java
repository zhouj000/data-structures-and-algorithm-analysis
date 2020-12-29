package tree.print;

import java.util.*;

/**
 * 二叉树的前序遍历
 **/
public class PreorderTraversal {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
    }

    // 非递归
    /**
     * 执行用时： 1 ms , 在所有 Java 提交中击败了 41.12% 的用户
     * 内存消耗： 36.8 MB , 在所有 Java 提交中击败了 32.29% 的用户
     */
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        Stack<TreeNode> queue = new Stack<>();
        queue.push(root);
        while(!queue.isEmpty()) {
            TreeNode node = queue.pop();
            if (node == null) {
                continue;
            }
            list.add(node.val);
            queue.push(node.right);
            queue.push(node.left);
        }
        return list;
    }

    // 递归
    /**
     * 执行用时： 0 ms , 在所有 Java 提交中击败了 100.00% 的用户
     * 内存消耗： 36.7 MB , 在所有 Java 提交中击败了 56.58% 的用户
     */
    public List<Integer> preorderTraversal2(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        recc(root, list);
        return list;
    }

    private void recc(TreeNode node, List<Integer> list) {
        if (node == null) {
            return;
        }
        list.add(node.val);
        recc(node.left, list);
        recc(node.right, list);
    }




    // 空间最优  36712 kb
    class Solution1 {
        public List<Integer> preorderTraversal(TreeNode root) {
            List<Integer> res = new ArrayList<>();
            Stack<TreeNode> stack = new Stack<>();
            if (root == null) {
                return res;
            }
            stack.push(root);
            while (!stack.isEmpty()) {
                TreeNode cur = stack.pop();
                res.add(cur.val);
                if (cur.right != null) {
                    stack.push(cur.right);
                }
                if (cur.left != null) {
                    stack.push(cur.left);
                }
            }
            return res;
        }
    }

}
