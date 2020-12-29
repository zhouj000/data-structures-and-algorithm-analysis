package tree.recc;

/**
 *
 * 当遇到树问题时，请先思考一下两个问题：
 * 1. 你能确定一些参数，从该节点自身解决出发寻找答案吗？
 * 2. 你可以使用这些参数和节点本身的值来决定什么应该是传递给它子节点的参数吗？
 * 如果答案都是肯定的，那么请尝试使用 “自顶向下” 的递归来解决此问题。
 *
 * 或者你可以这样思考：
 * 对于树中的任意一个节点，如果你知道它子节点的答案，你能计算出该节点的答案吗？
 * 如果答案是肯定的，那么 “自底向上” 的递归可能是一个不错的解决方法
 *
 **/
public class Example {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
    }

    // don't forget to initialize answer before call maximum_depth
    private int answer;

    // 自顶向下
    private void maximum_depth(TreeNode root, int depth) {
        if (root == null) {
            return;
        }
        if (root.left == null && root.right == null) {
            answer = Math.max(answer, depth);
        }
        maximum_depth(root.left, depth + 1);
        maximum_depth(root.right, depth + 1);
    }


    // 自底向上
    public int maximum_depth(TreeNode root) {
        if (root == null) {
            // return 0 for null node
            return 0;
        }
        int left_depth = maximum_depth(root.left);
        int right_depth = maximum_depth(root.right);
        // return depth of the subtree rooted at root
        return Math.max(left_depth, right_depth) + 1;
    }

}
