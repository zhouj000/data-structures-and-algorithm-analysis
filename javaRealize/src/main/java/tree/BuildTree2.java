package tree;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * 从前序与中序遍历序列构造二叉树
 *
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 *
 * 前序遍历 preorder = [3,9,20,15,7]
 * 中序遍历 inorder =  [9,3,15,20,7]
 **/
public class BuildTree2 {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    int[] preorder;
    int[] inorder;
    int pre_index;
    Map<Integer, Integer> inorderMap = new HashMap<>();

    /**
     * 按照BuildTree中的递归改的
     *
     * 执行用时： 2 ms , 在所有 Java 提交中击败了 97.30% 的用户
     * 内存消耗： 39 MB , 在所有 Java 提交中击败了 36.29% 的用户
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        this.preorder = preorder;
        this.inorder = inorder;
        pre_index = 0;
        for (int i = 0; i < inorder.length; i++) {
            inorderMap.put(inorder[i], i);
        }
        return recc(0, inorder.length - 1);
    }

    private TreeNode recc(int left, int right) {
        if (left > right) {
            return null;
        }
        TreeNode root = new TreeNode(preorder[pre_index++]);
        int in_idx = inorderMap.get(root.val);
        root.left = recc(left, in_idx - 1);
        root.right = recc(in_idx + 1, right);
        return root;
    }




    // 1 ms     38360 kb
    class BestSolution {
        public TreeNode buildTree(int[] preorder, int[] inorder) {
            if (preorder == null || preorder.length == 0) {
                return null;
            }
            TreeNode root = new TreeNode(preorder[0]);
            Deque<TreeNode> stack = new LinkedList<>();
            stack.push(root);
            int inorderIndex = 0;
            for (int i = 1; i < preorder.length; i++) {
                int preorderVal = preorder[i];
                TreeNode node = stack.peek();
                if (node.val != inorder[inorderIndex]) {
                    node.left = new TreeNode(preorderVal);
                    stack.push(node.left);
                } else {
                    while (!stack.isEmpty() && stack.peek().val == inorder[inorderIndex]) {
                        node = stack.pop();
                        inorderIndex++;
                    }
                    node.right = new TreeNode(preorderVal);
                    stack.push(node.right);
                }
            }
            return root;
        }
    }

    // 3 ms
    class Solution2 {
        Map<Integer, Integer> map = new HashMap();

        private TreeNode buildTree(int[] preorder, int[] inorder, int preleft, int preright,
                                   int inleft, int inright) {
            if (preleft > preright) {
                return null;
            }
            int rootVal = preorder[preleft];
            int rootIndex = map.get(rootVal);
            int leftLength = rootIndex - inleft;
            TreeNode root = new TreeNode(rootVal);
            TreeNode left = buildTree(preorder, inorder, preleft + 1, preleft + leftLength, inleft, rootIndex - 1);
            TreeNode right = buildTree(preorder, inorder, preleft + leftLength + 1, preright, rootIndex + 1, inright);
            root.left = left;
            root.right = right;
            return root;
        }

        public TreeNode buildTree(int[] preorder, int[] inorder) {
            for (int i = 0; i < inorder.length; i++) {
                map.put(inorder[i], i);
            }
            return buildTree(preorder, inorder, 0, preorder.length - 1, 0, inorder.length - 1);
        }
    }

    // 38484 kb
    class Solution3 {

        public TreeNode buildTree(int[] preorder, int[] inorder) {
            int p = preorder.length;
            int i = inorder.length;
            if (p != i || p < 0) {
                return null;
            }
            return createTree(preorder, 0, preorder.length, inorder, 0, inorder.length);
        }


        public TreeNode createTree(int[] preorder, int pre_start, int pre_end, int[] inorder, int in_start, int in_end) {
            if (pre_start == pre_end) {
                return null;
            }
            int root_val = preorder[pre_start];
            TreeNode root = new TreeNode(root_val);
            // 中序遍历中根节点的位置
            int root1 = 0;
            for (int i = in_start; i < in_end; i++) {
                if (inorder[i] == root_val) {
                    root1 = i;
                    break;
                }
            }
            // 左子树数量
            int leftNum = root1 - in_start;

            root.left = createTree(preorder, pre_start + 1, pre_start + leftNum + 1, inorder, in_start, root1);
            root.right = createTree(preorder, pre_start + leftNum + 1, pre_end, inorder, root1 + 1, in_end);
            return root;
        }
    }
}
