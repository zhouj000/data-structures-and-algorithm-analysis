package array.stack;

import java.util.*;

/**
 * 课程题： 二叉树的中序遍历
 *   扩展： 二叉树的前序、中序、后序遍历，  递归 & for
 *
 * 问题：  1. 递归解法比较简单，自己写的和网上最优的一致
 *         2. 非递归算法写的比较复杂，想把左节点和右节点放到栈里，然后取出来再判断
 *            实际上可以直接将当前点替换为下一个节点
 *         3. 其实前序和中序 遍历的顺序是一致的，只是打印位置不同
 *         4. 后序遍历 使用了部分BFS思路+栈的后进先出的特性，刚好反向遍历，然后正向输出即可
 **/
public class Class7_InorderTraversal {

    // 课程要求， 中序 + 非递归
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> out = new ArrayList<>();
        if (root == null) {
            return out;
        }
        Set<TreeNode> visited = new HashSet<>();
        Stack<TreeNode> set = new Stack<>();
        set.push(root);

        while (!set.isEmpty()) {
            TreeNode node = set.peek();
            if (node.left == null || visited.contains(node.left)) {
                out.add(node.val);
                if (node.right != null && !visited.contains(node.right)) {
                    visited.add(node.right);
                    set.add(node.right);
                }
                set.remove(node);
            }
            if (node.left != null && !visited.contains(node.left)) {
                visited.add(node.left);
                set.add(node.left);
            }
        }
        return out;
    }

    // 递归方案
    public List<Integer> orderTraversal(TreeNode root) {
        List<Integer> out = new ArrayList<>();
        dgOrder(root, out);
        return out;
    }
    private void dgOrder(TreeNode node, List<Integer> list) {
        if (node == null) {
            return;
        }
        // 前序
        // list.add(node.val);
        dgOrder(node.left, list);
        // 中序
        list.add(node.val);
        dgOrder(node.right, list);
        // 后序
        // list.add(node.val);
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) {
            val = x;
        }
    }

    /**
     *           1
     *    2              5
     *      3          6   7
     *        4      8   9
     *
     *  前序： 1 2 3 4 5 6 8 9 7       根节点排最先，然后同级先左后右
     *  中序： 2 3 4 1 8 6 9 5 7       先左后根，最后右
     *  后续： 4 3 2 8 9 6 7 5 1       先左后右，最后根
     */
    public static void main(String[] args) {
        TreeNode a = new TreeNode(1);
        TreeNode b = new TreeNode(2);
        TreeNode c = new TreeNode(3);
        TreeNode d = new TreeNode(4);
        TreeNode e = new TreeNode(5);
        TreeNode f = new TreeNode(6);
        TreeNode g = new TreeNode(7);
        TreeNode h = new TreeNode(8);
        TreeNode i = new TreeNode(9);
        a.left = b;
        b.right = c;
        c.right = d;
        a.right = e;
        e.left = f;
        e.right = g;
        f.left = h;
        f.right = i;

        Class7_InorderTraversal t = new Class7_InorderTraversal();
        /*List<Integer> out = t.afterorderTraversal(a);
        for (Integer n : out) {
            System.out.print(n + " ");
        }*/

        System.out.println("===================== ");
        webSearch ws = t.new webSearch();
        ws.postOrder(a);

        System.out.println("\n===================== ");
        webSearch2 ws2 = t.new webSearch2();
        ws2.preorderTraversal(a);
    }

    // 前序 + 非递归
    public List<Integer> beforeorderTraversal(TreeNode root) {
        List<Integer> out = new ArrayList<>();
        if (root == null) {
            return out;
        }
        Set<TreeNode> visited = new HashSet<>();
        Stack<TreeNode> set = new Stack<>();
        set.push(root);

        while (!set.isEmpty()) {
            TreeNode node = set.peek();
            if (!visited.contains(node)) {
                visited.add(node);
                out.add(node.val);
            }
            if (node.left != null && !visited.contains(node.left)) {
                set.add(node.left);
                continue;
            }
            if (node.left == null || visited.contains(node.left)) {
                if (node.right != null && !visited.contains(node.right)) {
                    set.add(node.right);
                }
                set.remove(node);
            }
        }
        return out;
    }

    // 后序 + 非递归
    public List<Integer> afterorderTraversal(TreeNode root) {
        List<Integer> out = new ArrayList<>();
        if (root == null) {
            return out;
        }
        Set<TreeNode> visited = new HashSet<>();
        Stack<TreeNode> set = new Stack<>();
        set.push(root);

        while (!set.isEmpty()) {
            TreeNode node = set.peek();
            if ((node.left == null || visited.contains(node.left)) &&
                    (node.right == null || visited.contains(node.right))) {
                out.add(node.val);
                set.remove(node);
            }
            if (node.left == null || visited.contains(node.left)) {
                if (node.right != null && !visited.contains(node.right)) {
                    visited.add(node.right);
                    set.add(node.right);
                }
            }
            if (node.left != null && !visited.contains(node.left)) {
                visited.add(node.left);
                set.add(node.left);
            }
        }
        return out;
    }






    // 网上找的解法
    class webSearch {

        public void preOrder(TreeNode root) {
            Stack<TreeNode> stack = new Stack<>();
            TreeNode node = root;
            while (node != null || !stack.isEmpty()) {
                if (node != null) {
                    System.out.print(node.val + " ");
                    stack.push(node);
                    node = node.left;
                } else {
                    node = stack.pop();
                    node = node.right;
                }
            }
        }

        public void inOrder(TreeNode root) {
            Stack<TreeNode> stack = new Stack<>();
            TreeNode node = root;
            while (node != null || !stack.isEmpty()) {
                if (node != null) {
                    stack.push(node);
                    node = node.left;
                } else {
                    node = stack.pop();
                    System.out.print(node.val + " ");
                    node = node.right;
                }
            }
        }

        // 后序，反向放入栈，刚好是 根、右、左
        public void postOrder(TreeNode root) {
            Stack<TreeNode> s1 = new Stack();
            Stack<TreeNode> s2 = new Stack();
            TreeNode node = root;
            s1.push(node);
            while (!s1.empty()) {
                node = s1.pop();
                s2.push(node);
                if (node.left != null) {
                    s1.push(node.left);
                }
                if (node.right != null) {
                    s1.push(node.right);
                }
            }
            while (!s2.empty()) {
                node = s2.pop();
                System.out.print(node.val + " ");
            }
        }

        // 正向顺序，先尽量向左遍历，最左后向右，当叶子时打印
        public void postOrder2(TreeNode root) {
            if (root == null) {
                return;
            }
            Stack<TreeNode> s = new Stack<>();
            TreeNode last = null;
            while (!s.isEmpty() || root != null) {
                while (root != null) {
                    s.push(root);
                    root = root.left;
                }
                if (!s.isEmpty()) {
                    TreeNode t = s.pop();
                    // 在这里面打印t并处理last之后，并不用处理root，因为之所以能进入这里，
                    // 是因为root一定等于null，所以下一轮循环一定还能进入这里，然后弹出t的父结点做处理
                    if (t.right == null || last == t.right) {
                        System.out.print(t.val + " ");
                        last = t;
                    } else { // 右孩子还没有打印过
                        // 因为当前结点未打印，所以要重新放回去，等右孩子打印完之后回来打印
                        s.push(t);
                        root = t.right;
                    }
                }
            }
        }
    }

    class webSearch2 {

        public List<Integer> preorderTraversal(TreeNode root) {
            List<Integer> list = new ArrayList<>();
            if (root == null) {
                return list;
            }
            Stack<TreeNode> stack = new Stack<TreeNode>();
            stack.add(root);
            while (!stack.isEmpty()) {
                TreeNode node = stack.pop();
                list.add(node.val);
                if (node.right != null) { // 先压入右子树
                    stack.push(node.right);
                }
                if (node.left != null) { // 再压入左子树
                    stack.push(node.left);
                }
            }
            System.out.println(list);
            return list;
        }

        public List<Integer> inorderTraversal(TreeNode root) {
            List<Integer> list = new ArrayList<>();
            if (root == null) {
                return list;
            }
            Stack<TreeNode> s = new Stack<TreeNode>();
            TreeNode node = root;
            while (node != null || !s.isEmpty()) {
                while (node != null) {
                    s.push(node);
                    node = node.left;
                }
                node = s.pop();
                list.add(node.val);
                node = node.right;
            }
            System.out.println(list);
            return list;
        }

        public List<Object> postOrder(TreeNode root){
            List<Object> list = new ArrayList<Object>();
            if(root == null) {
                return list;
            }
            Stack<TreeNode> stack = new Stack<>();
            TreeNode node = root, prev = root; // pre记录上一个已经输出的结点
            while (node != null || stack.size() > 0) {
                while (node != null) {
                    stack.push(node);
                    node = node.left;
                }
                TreeNode temp = stack.peek().right; // 在出栈之前，先判断栈顶元素的右孩子结点
                if (temp == null || temp == prev) { // 当前节点无右子树或右子树已经输出
                    node = stack.pop();
                    list.add(node.val);
                    prev = node; // 记录上一个已输出结点
                    node = null;
                } else {
                    node = temp; // 处理右子树
                }
            }
            System.out.println(list);
            return list;
        }

    }

}
