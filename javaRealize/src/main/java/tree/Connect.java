package tree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 填充每个节点的下一个右侧节点指针
 *
 **/
public class Connect {

    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {}
        public Node(int val) {this.val = val;}
    };

    /**
     * 执行用时： 3 ms , 在所有 Java 提交中击败了 42.24% 的用户
     * 内存消耗： 39 MB , 在所有 Java 提交中击败了 11.82% 的用户
     */
    public Node connect(Node root) {
        if (root == null) {
            return root;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Node node = queue.poll();
                if (node.left != null) {
                    queue.offer(node.left);
                    queue.offer(node.right);
                }
                if (i < size - 1) {
                    Node next = queue.peek();
                    node.next = next;
                }
            }
        }
        return root;
    }




    //  0 ms
    class BestSolution {
        public Node connect(Node root) {
            if (root == null) {
                return null;
            }
            if (root.left != null && root.right != null) {
                root.left.next = root.right;
                Node p = root.left, q = root.right;
                while (p.right != null && q.left != null) {
                    p = p.right;
                    q = q.left;
                    p.next = q;
                }
                connect(root.left);
                connect(root.right);
            }
            return root;
        }
    }

    // 1 ms
    class Solution2 {
        public Node connect(Node root) {
            if (root == null) {
                return null;
            }
            if (root.left == null) {
                return root;
            }
            connectBetweenTwo(root.left, root.right);
            return root;
        }

            private void connectBetweenTwo(Node node1, Node node2) {
            // for node1 and node2 are in the same layer, if node1 is leaf node, node2 too.
            if (node1.left == null) {
                node1.next = node2;
                return;
            }
            node1.next = node2;

            connectBetweenTwo(node1.left, node1.right);
            connectBetweenTwo(node2.left, node2.right);
            connectBetweenTwo(node1.right, node2.left);
        }
    }

    // 38652 kb
    class Solution3 {
        public Node connect(Node root) {
            if (root == null) {
                return root;
            }
            //cur我们可以把它看做是每一层的链表
            Node cur = root;
            while (cur != null) {
                //遍历当前层的时候，为了方便操作在下一
                //层前面添加一个哑结点（注意这里是访问
                //当前层的节点，然后把下一层的节点串起来）
                Node dummy = new Node(0);
                //pre表示访下一层节点的前一个节点
                Node pre = dummy;
                //然后开始遍历当前层的链表
                while (cur != null) {
                    if (cur.left != null) {
                        //如果当前节点的左子节点不为空，就让pre节点
                        //的next指向他，也就是把它串起来
                        pre.next = cur.left;
                        //然后再更新pre
                        pre = pre.next;
                    }
                    //同理参照左子树
                    if (cur.right != null) {
                        pre.next = cur.right;
                        pre = pre.next;
                    }
                    //继续访问这一行的下一个节点
                    cur = cur.next;
                }
                //把下一层串联成一个链表之后，让他赋值给cur，
                //后续继续循环，直到cur为空为止
                cur = dummy.next;
            }
            return root;
        }
    }

    //  38732 kb
    class Solution4 {
        public Node connect(Node root) {
            Node dump = new Node(0);
            dump.next = null;
            Node pre = dump;
            Node cur = root;
            while (cur != null) {
                if (cur.left != null) {
                    pre.next = cur.left;
                    pre = pre.next;
                }
                if (cur.right != null) {
                    pre.next = cur.right;
                    pre = pre.next;
                }
                if (cur.next != null) {
                    cur = cur.next;
                } else {
                    cur = dump.next;
                    dump.next = null;
                    pre = dump;
                }
            }
            return root;
        }
    }

    //  38796 kb
    class Solution5 {
        public Node connect(Node root) {
            if (root == null) {
                return null;
            }
            if (root.left != null) {
                root.left.next = root.right;
                if (root.next != null) {
                    root.right.next = root.next.left;
                }
                connect(root.left);
                connect(root.right);
            }
            return root;
        }
    }

}
