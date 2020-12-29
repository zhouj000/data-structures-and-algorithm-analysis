package tree;

import java.util.*;

/**
 * 从中序与后序遍历序列构造二叉树
 *
 * 中序遍历 inorder =   [9,3,15,20,7]
 * 后序遍历 postorder = [9,15,7,20,3]
 *
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 *
 **/
public class BuildTree {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    /**
     * 执行用时： 8 ms , 在所有 Java 提交中击败了 16.27% 的用户
     * 内存消耗： 39.1 MB , 在所有 Java 提交中击败了 63.58% 的用户
     */
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        if (inorder == null || inorder.length <= 0) {
            return null;
        }
        TreeNode root = new TreeNode(postorder[postorder.length - 1]);
        int index = getIndexByVal(inorder, root.val);
        int[] iLeft = Arrays.copyOfRange(inorder, 0, index);
        int[] pLeft = Arrays.copyOfRange(postorder, 0, index);
        int[] iright = index >= inorder.length ? null : Arrays.copyOfRange(inorder, index + 1, inorder.length);
        int[] pright = index >= postorder.length ? null : Arrays.copyOfRange(postorder, index, inorder.length - 1);
        root.left = buildTree(iLeft, pLeft);
        root.right = buildTree(iright, pright);
        return root;
    }

    private int getIndexByVal(int[] array, int val) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == val) {
                return i;
            }
        }
        return -1;
    }




    //  1 ms   38668 kb(排第二)
    class BestSolution {
        public TreeNode buildTree(int[] inorder, int[] postorder) {
            if (postorder == null || postorder.length == 0) {
                return null;
            }
            TreeNode root = new TreeNode(postorder[postorder.length - 1]);
            Deque<TreeNode> stack = new LinkedList<>();
            stack.push(root);
            int inorderIndex = inorder.length - 1;
            for (int i = postorder.length - 2; i >= 0; i--) {
                int postorderVal = postorder[i];
                TreeNode node = stack.peek();
                if (node.val != inorder[inorderIndex]) {
                    node.right = new TreeNode(postorderVal);
                    stack.push(node.right);
                } else {
                    while (!stack.isEmpty() && stack.peek().val == inorder[inorderIndex]) {
                        node = stack.pop();
                        inorderIndex--;
                    }
                    node.left = new TreeNode(postorderVal);
                    stack.push(node.left);
                }
            }
            return root;
        }
    }

    //  2 ms  同递归思路  不过用map解决了我找index的方法，时间应该就节约在这里
    class Solution2 {
        int[] inorder;
        int[] postorder;
        int post_index;
        Map<Integer, Integer> inorderMap = new HashMap<>();

        private TreeNode helper(int in_left, int in_right) {
            if (in_left > in_right) {
                return null;
            }
            TreeNode root = new TreeNode(postorder[post_index--]);
            int in_idx = inorderMap.get(root.val);
            root.right = helper(in_idx + 1, in_right);
            root.left = helper(in_left, in_idx - 1);
            return root;
        }

        public TreeNode buildTree(int[] inorder, int[] postorder) {
            this.inorder = inorder;
            this.postorder = postorder;
            post_index = postorder.length - 1;
            for (int i = 0; i < inorder.length; i++) {
                inorderMap.put(inorder[i], i);
            }
            return helper(0, inorder.length - 1);
        }
    }

    //  3 ms
    class Solution3 {
        HashMap<Integer, Integer> inord = new HashMap<>();

        public TreeNode buildTree(int[] inorder, int[] postorder) {
            if (postorder.length == 0 || inorder.length == 0) {
                return null;
            }
            for (int k = 0; k <= inorder.length - 1; k++) {
                inord.put(inorder[k], k);
            }
            TreeNode temp = cal(postorder, 0, postorder.length - 1, inorder, 0, inorder.length - 1);
            return temp;

        }

        // 后注： inorder没用到, 这个方法里写的不好阅读，不如上面那个递归
        public TreeNode cal(int[] postorder, int postl, int postr, int[] inorder, int inl, int inr) {
            if (postl == postr) {
                TreeNode temp = new TreeNode(postorder[postr]);
                return temp;
            }
            TreeNode temp = new TreeNode(postorder[postr]);
            int index = inord.get(postorder[postr]);
            int leftnum = index - inl;
            int rightnum = inr - index;
            int inl2 = inl;
            int inr2 = index - 1;
            int postl2 = postl;
            int postr2 = postl + leftnum - 1;

            int inl3 = index + 1;
            int inr3 = inr;
            int postl3 = postl + leftnum;
            int postr3 = postr - 1;

            if (inr2 >= inl2 && postr2 >= postl2) {
                temp.left = cal(postorder, postl2, postr2, inorder, inl2, inr2);
            }
            if (inr3 >= inl3 && postr3 >= postl3) {
                temp.right = cal(postorder, postl3, postr3, inorder, inl3, inr3);
            }
            return temp;
        }
    }

    // 4 ms   这个和我写的应该是类似的，但是为什么时间效率比我高，可以后续关注下优化在哪
    class Solution4 {
        public TreeNode buildTree(int[] inorder, int[] postorder) {
            return helper(inorder, 0, inorder.length - 1, postorder, 0, postorder.length - 1);
        }

        private TreeNode helper(int[] in, int inL, int inR,
                                int[] post, int postL, int postR) {
            if (inL > inR) {
                return null;
            }
            int rootVal = post[postR], rootIdxIn = -1;
            for (int i = inL; i <= inR; ++i) {
                if (in[i] == rootVal) {
                    rootIdxIn = i;
                    break;
                }
            }

            int leftSubTreeSize = rootIdxIn - inL;
            TreeNode root = new TreeNode(rootVal);
            root.left = helper(in, inL, rootIdxIn - 1, post, postL, postL + leftSubTreeSize - 1);
            root.right = helper(in, rootIdxIn + 1, inR, post, postL + leftSubTreeSize, postR - 1);
            return root;
        }
    }



    //  38608 kb
    class Solution5 {
        public TreeNode buildTree(int[] inorder, int[] postorder) {
            return buildTree(inorder, 0, inorder.length - 1, postorder, 0, postorder.length - 1);
        }

        public TreeNode buildTree(int[] inorder, int inBegin, int inEnd, int[] postorder, int postBegin, int postEnd) {
            if (inBegin > inEnd) {
                return null;
            }
            TreeNode root = new TreeNode(postorder[postEnd]);
            int i = inEnd;
            for (; i >= inBegin; i--) {
                if (inorder[i] == root.val) {
                    break;
                }
            }
            root.left = buildTree(inorder, inBegin, i - 1, postorder, postBegin, postBegin + i - 1 - inBegin);
            root.right = buildTree(inorder, i + 1, inEnd, postorder, postEnd - inEnd + i, postEnd - 1);
            return root;
        }
    }

}
