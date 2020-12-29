package tree.print;

import java.util.*;

/**
 *  二叉树的后序遍历
 *  非递归方案，使用倒排输出，最后翻转
 **/
public class PostorderTraversal {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
    }

    // 非递归
    /**
     * 执行用时： 1 ms , 在所有 Java 提交中击败了 49.37% 的用户
     * 内存消耗： 37 MB , 在所有 Java 提交中击败了 53.31% 的用户
     */
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        if (root == null) {
            return list;
        }
        Stack<TreeNode> stack = new Stack<>();
        HashSet<TreeNode> visited = new HashSet<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.peek();
            if ((node.left == null || visited.contains(node.left)) && (node.right == null || visited.contains(node.right))) {
                list.add(stack.pop().val);
            } else {
                if (node.right != null && !visited.contains(node.right)) {
                    stack.push(node.right);
                    visited.add(node.right);
                }
                if (node.left != null && !visited.contains(node.left)) {
                    stack.push(node.left);
                    visited.add(node.left);
                }
            }
        }
        return list;
    }

    // 递归
    /**
     * 执行用时： 0 ms , 在所有 Java 提交中击败了 100.00% 的用户
     * 内存消耗： 36.7 MB , 在所有 Java 提交中击败了 84.82% 的用户
     */
    public List<Integer> postorderTraversal2(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        recc(root, list);
        return list;
    }

    private void recc(TreeNode node, List<Integer> list) {
        if (node == null) {
            return;
        }
        recc(node.left, list);
        recc(node.right, list);
        list.add(node.val);
    }




    // 36852 kb
    class Solution1 {
        public List<Integer> postorderTraversal(TreeNode root) {
            List<Integer> res = new LinkedList<>();
            Stack<TreeNode> st = new Stack<>();
            while (root != null || !st.isEmpty()) {
                while (root != null) {
                    res.add(root.val);
                    st.push(root);
                    root = root.right;
                }
                root = st.pop();
                root = root.left;
            }
            Collections.reverse(res);
            return res;
        }
    }

    // 36920 kb
    class Solution2 {
        public List<Integer> postorderTraversal(TreeNode root) {
            List<Integer> ret = new ArrayList<>();
            Deque<TreeNode> dq = new ArrayDeque<>();
            if (root != null) {
                dq.push(root);
            }
            while (!dq.isEmpty()) {
                TreeNode tmp = dq.peek();
                ret.add(dq.pop().val);
                if (tmp.left != null) {
                    dq.push(tmp.left);
                }
                if (tmp.right != null) {
                    dq.push(tmp.right);
                }
            }
            Collections.reverse(ret);
            return ret;
        }
    }
}
