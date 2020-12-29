package tree;

import javafx.util.Pair;

import java.util.*;

/**
 * 二叉树的最近公共祖先
 **/
public class LowestCommonAncestor {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    /**
     * 思路，最近公共节点应该是左数有一个值，右树也有另一个值的那个节点
     *       第一次提交，漏了自己就是根节点的情况
     *
     * 执行用时： 7 ms , 在所有 Java 提交中击败了 98.96% 的用户
     * 内存消耗： 40.2 MB , 在所有 Java 提交中击败了 92.04% 的用户
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        }
        findNode(root, p.val, q.val);
        return node;
    }

    TreeNode node = null;
    boolean flag = false;

    private boolean findNode(TreeNode root, int val1, int val2) {
        if (root == null || flag) {
            return false;
        }
        boolean left = findNode(root.left, val1, val2);
        boolean right = findNode(root.right, val1, val2);
        boolean now = root.val == val1 || root.val == val2;
        if ((left && right) || (now && (left || right))) {
            node = root;
            flag = true;
        }
        return left || right || now;
    }



    // 4 ms
    class BestSolution {
        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            // 递归终止条件：当 root 为空时返回 null
            // 或者遍历遇到了要找的 p 或者 q，则返回对应的 node
            if (root == null || root == p || root == q) {
                return root;
            }
            /** Divide */
            TreeNode left = lowestCommonAncestor(root.left, p, q);
            TreeNode right = lowestCommonAncestor(root.right, p, q);
            /**
             * Conquer
             * 这里利用了 三目运算 使得代码更加简洁，思路就是：
             * 如果 left 为空，则说明在左子树中没找到，因此应该返回右子树；
             * 同理如果 right 为空，则返回 left。
             * 如果 left 和 right 均不为空，说明 p, q 分布在当前节点的左右子树中。
             * 因此当前节点就是要求的 LCA。
             */
            return left == null ? right : right == null ? left : root;
        }
    }

    // 7 ms
    class Solution2 {
        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            return commonAncestor( root,  p,  q);
        }

        public TreeNode commonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            if (root == null) {
                return null;
            }
            if (root == p || root == q) {
                return root;
            }
            TreeNode left = commonAncestor(root.left, p, q);
            TreeNode right = commonAncestor(root.right, p, q);
            if (left != null & right != null) {
                return root;
            }
            if (left == null & right == null) {
                return null;
            }
            return left == null ? right : left;
        }
    }

    // 39276 kb
    class Solution3 {
        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            Map<TreeNode, TreeNode> parentNodeMap = new HashMap<>();
            Deque<TreeNode> treeNodeDeque = new LinkedList<>();
            treeNodeDeque.push(root);
            int curSize = 1;
            int tempSize = 0;
            TreeNode temp;
            while (!treeNodeDeque.isEmpty()) {
                while (curSize-- > 0) {
                    temp = treeNodeDeque.poll();
                    if (temp.left != null) {
                        parentNodeMap.put(temp.left, temp);
                        treeNodeDeque.push(temp.left);
                        tempSize++;
                    }
                    if (temp.right != null) {
                        parentNodeMap.put(temp.right, temp);
                        treeNodeDeque.push(temp.right);
                        tempSize++;
                    }
                }
                curSize = tempSize;
                tempSize = 0;
            }
            Set<TreeNode> pParentNodeSet = new HashSet<>();
            pParentNodeSet.add(p);
            while (parentNodeMap.containsKey(p)) {
                p = parentNodeMap.get(p);
                pParentNodeSet.add(p);
            }
            while (q != null && !pParentNodeSet.contains(q)) {
                q = parentNodeMap.get(q);
            }
            return q;
        }

        // 39324 kb
        public TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
            //记录遍历到的每个节点的父节点。
            Map<TreeNode, TreeNode> parent = new HashMap<>();
            Queue<TreeNode> queue = new LinkedList<>();
            parent.put(root, null);//根节点没有父节点，所以为空
            queue.add(root);
            //直到两个节点都找到为止。
            while (!parent.containsKey(p) || !parent.containsKey(q)) {
                //队列是一边进一边出，这里poll方法是出队，
                TreeNode node = queue.poll();
                if (node.left != null) {
                    //左子节点不为空，记录下他的父节点
                    parent.put(node.left, node);
                    //左子节点不为空，把它加入到队列中
                    queue.add(node.left);
                }
                //右节点同上
                if (node.right != null) {
                    parent.put(node.right, node);
                    queue.add(node.right);
                }
            }
            Set<TreeNode> ancestors = new HashSet<>();
            //记录下p和他的祖先节点，从p节点开始一直到根节点。
            while (p != null) {
                ancestors.add(p);
                p = parent.get(p);
            }
            //查看p和他的祖先节点是否包含q节点，如果不包含再看是否包含q的父节点……
            while (!ancestors.contains(q)) {
                q = parent.get(q);
            }
            return q;
        }
    }

    // 39712 kb
    static class Solution4 {

        //他的两个子节点都访问过了
        private static int BOTH_PENDING = 2;

        //只有左节点被访问过了
        private static int LEFT_DONE = 1;

        //两个节点都还没有被访问
        private static int BOTH_DONE = 0;

        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            //Pair<>（）
            Stack<Pair<TreeNode, Integer>> stack = new Stack<Pair<TreeNode, Integer>>();

            //头节点push
            stack.push(new Pair<TreeNode, Integer>(root, Solution4.BOTH_PENDING));

            // This flag is set when either one of p or q is found.
            boolean one_node_found = false;

            //祖先节点初始化
            TreeNode LCA = null;

            //孩子节点初始化
            TreeNode child_node = null;

            // We do a post order traversal of the binary tree using stack
            while (!stack.isEmpty()) {
                //先看栈顶元素
                Pair<TreeNode, Integer> top = stack.peek();
                //pair用法 getKey（），栈顶元素就是父节点
                TreeNode parent_node = top.getKey();
                //父节点的状态
                int parent_state = top.getValue();

                //说明他的子节点还有没被访问的
                if (parent_state != Solution4.BOTH_DONE) {

                    // 都没被访问
                    if (parent_state == Solution4.BOTH_PENDING) {

                        // Check if the current parent_node is either p or q.
                        if (parent_node == p || parent_node == q) {

                            // If one_node_found was set already, this means we have found
                            // both the nodes.
                            if (one_node_found) {
                                return LCA;
                            } else {
                                // Otherwise, set one_node_found to True,
                                // to mark one of p and q is found.
                                one_node_found = true;

                                // Save the current top element of stack as the LCA.
                                LCA = stack.peek().getKey();
                            }
                        }

                        // If both pending, traverse the left child first
                        child_node = parent_node.left;
                    } else {
                        // traverse right child
                        child_node = parent_node.right;
                    }

                    // Update the node state at the top of the stack
                    // Since we have visited one more child.
                    stack.pop();
                    stack.push(new Pair<TreeNode, Integer>(parent_node, parent_state - 1));

                    // Add the child node to the stack for traversal.
                    if (child_node != null) {
                        stack.push(new Pair<TreeNode, Integer>(child_node, Solution4.BOTH_PENDING));
                    }
                } else {

                    // If the parent_state of the node is both done,
                    // the top node could be popped off the stack.
                    // Update the LCA node to be the next top node.
                    if (LCA == stack.pop().getKey()) {
                        LCA = stack.peek().getKey();
                    }

                }
            }
            return null;
        }
    }


    // 40000 kb
    class Solution5 {
        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            ArrayList<TreeNode> path1 = null;
            ArrayList<TreeNode> path2 = null;
            /**
             * 利用栈作二叉树的后序非递归遍历
             * 1 先把根一路向左入栈。
             * 2 取栈顶元素peek。
             * 3 peek有右孩子且未被访问，把右孩子一路向左全入栈。否则出栈peek并访问。
             * 4 重复2,3直至栈空。
             * 5 期间栈顶元素是p和q就记录路径。
             */
            LinkedList<TreeNode> stack = new LinkedList<>();
            pushAllLeft(root, stack);
            TreeNode pre = null;
            while (!stack.isEmpty()) {
                TreeNode peek = stack.peekLast();
                if (peek == p) {
                    path1 = getPath(stack);
                }
                if (peek == q) {
                    path2 = getPath(stack);
                }
                if (path1 != null && path2 != null) {
                    break;
                }
                if (peek.right != null && peek.right != pre) {
                    pushAllLeft(peek.right, stack);
                } else {
                    pre = stack.pollLast();
                }
            }
            //寻找最近的祖先节点
            return findLowestCommonAncestor(path1, path2);
        }

        //一路向左全入栈
        private void pushAllLeft(TreeNode p, LinkedList<TreeNode> stack) {
            while (p != null) {
                stack.add(p);
                p = p.left;
            }
        }

        //获取一条路径
        private ArrayList<TreeNode> getPath(LinkedList<TreeNode> stack) {
            ArrayList<TreeNode> path = new ArrayList<>();
            for (TreeNode node : stack) {
                path.add(node);
            }
            return path;
        }

        //寻找最近的祖先
        private TreeNode findLowestCommonAncestor(ArrayList<TreeNode> path1, ArrayList<TreeNode> path2) {
            int i = 0;
            int j = 0;
            while (i < path1.size() && j < path2.size() && path1.get(i) == path2.get(j)) {
                i++;
                j++;
            }
            return path1.get(i - 1);
        }
    }

}
