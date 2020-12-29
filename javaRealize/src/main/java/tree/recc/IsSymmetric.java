package tree.recc;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 对称二叉树
 *
 **/
public class IsSymmetric {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
    }

    /**
     * 执行用时： 2 ms , 在所有 Java 提交中击败了 7.84% 的用户
     * 内存消耗： 37.8 MB , 在所有 Java 提交中击败了 15.54% 的用户
     */
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        List<TreeNode> list = new ArrayList<>();
        list.add(root.left);
        list.add(root.right);
        while (!list.isEmpty()) {
            int size = list.size();
            // 后注： 应该也不用判断，都是成对的
            if (size % 2 != 0) {
                return false;
            }
            for (int i = 0; i < size; i += 2) {
                TreeNode left = list.get(i);
                TreeNode right = list.get(i + 1);
                if (left == null && right == null) {
                    continue;
                }
                if (left == null || right == null) {
                    return false;
                }
                if (left.val != right.val) {
                    return false;
                }
                list.add(left.left);
                list.add(right.right);
                list.add(left.right);
                list.add(right.left);
            }
            // 后注： 是不是不需要截取，毕竟都是成对的
            list = list.subList(size, list.size());
        }
        return true;
    }




    // 0 ms
    class BestSolution {
        public boolean isSymmetric(TreeNode root) {
            if(root == null) {
                return true;
            }
            return isSample(root.left,root.right);
        }

        public boolean isSample(TreeNode left, TreeNode right) {
            if (left == null && right == null) {
                return true;
            }
            if (left == null || right == null) {
                return false;
            }
            return left.val == right.val
                    && isSample(left.left, right.right)
                    && isSample(right.left, left.right);
        }
    }

    // 1 ms  思路和我一眼的，非递归解法
    class Solution2 {
        //解法2：迭代解法
        public boolean isSymmetric(TreeNode root) {
            return check(root, root);
        }

        public boolean check(TreeNode root1, TreeNode root2) {
            // 1.引入队列,这是把递归程序改写成迭代程序的常用方法
            Queue<TreeNode> q = new LinkedList<TreeNode>();
            // 2.队列中每两个连续的结点应该是相等的，而且它们的子树互为镜像,初始化时把根节点入队两次,每次提取两个结点并比较它们的值
            q.offer(root1);
            q.offer(root2);
            while (!q.isEmpty()) {
                root1 = q.poll();
                root2 = q.poll();
                if (root1 == null && root2 == null) {
                    continue;
                }
                if ((root1 == null || root2 == null) || (root1.val != root2.val)) {
                    return false;
                }
                // 3.然后将两个结点的左右子结点按相反的顺序插入队列中
                q.offer(root1.left);
                q.offer(root2.right);

                q.offer(root1.right);
                q.offer(root2.left);
            }
            // 4.当队列为空时，或者检测到树不对称（即从队列中取出两个不相等的连续结点）时，该算法结束。
            return true;
        }
    }

    // 36580 kb
    class Solution3 {
        public boolean isSymmetric(TreeNode root) {
            if (root == null) {
                return true;
            } else {
                return isSymmetric(root.left, root.right);
            }
        }

        private boolean isSymmetric(TreeNode p, TreeNode q) {
            if (p == null && q == null) {
                return true;
            } else if (p == null || q == null) {
                return false;
            } else if (p.val != q.val) {
                return false;
            } else {
                return isSymmetric(p.left, q.right) && isSymmetric(p.right, q.left);
            }
        }
    }
}
