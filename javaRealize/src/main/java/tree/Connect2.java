package tree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 填充每个节点的下一个右侧节点指针 II
 **/
public class Connect2 {

    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {}
    };


    /**
     * 执行用时： 0 ms , 在所有 Java 提交中击败了 100.00% 的用户
     * 内存消耗： 38.2 MB , 在所有 Java 提交中击败了 86.68% 的用户
     */
    public Node connect(Node root) {
        // Connect.Solution4#connect
        return null;
    }




    // 0ms
    class BestSolution {
        Node last = null, nextStart = null;

        public Node connect(Node root) {
            if (root == null) {
                return root;
            }
            Node start = root;
            Node cur = null;
            while (start != null) {
                last = null;
                nextStart = null;
                cur = start;
                while (cur != null) {
                    if (cur.left != null) {
                        handle(cur.left);
                    }
                    if (cur.right != null) {
                        handle(cur.right);
                    }
                    cur = cur.next;
                }
                start = nextStart;
            }
            return root;
        }

        public void handle(Node node) {
            if (last != null) {
                last.next = node;
            }
            if (nextStart == null) {
                nextStart = node;
            }
            last = node;
        }
    }

    // 1 ms
    class Solution2 {
        public Node connect(Node root) {
            if (root == null) {
                return null;
            }
            Queue<Node> queue = new LinkedList<>();
            queue.add(root);
            while (queue.size() > 0) {
                int len = queue.size();
                helper(queue, len);
            }
            return root;
        }

        private Node helper(Queue<Node> queue, int n){
            Node head = queue.remove();
            if (head.left != null) {
                queue.add(head.left);
            }
            if (head.right != null) {
                queue.add(head.right);
            }
            if (n == 1) {
                return head;
            }
            head.next = helper(queue, n - 1);
            return head;
        }
    }

    // 38136 kb
    class Solution3 {
        public Node connect(Node root) {
            if (root == null) {
                return null;
            }
            Node nextNode = root.next;
            while (nextNode != null) {
                if (nextNode.left != null) {
                    nextNode = nextNode.left;
                    break;
                }
                if (nextNode.right != null) {
                    nextNode = nextNode.right;
                    break;
                }
                nextNode = nextNode.next;
            }
            if (root.right != null) {
                root.right.next = nextNode;
            }
            if (root.left != null) {
                root.left.next = root.right != null ? root.right : nextNode;
            }
            connect(root.right);
            connect(root.left);
            return root;
        }
    }

    // 38220 kb
    class Solution4 {
        public Node connect(Node root) {
            if (root == null) {
                return root;
            }
            LinkedList<Node> queue = new LinkedList<>();
            root.next = null;
            queue.offer(root);
            while (!queue.isEmpty()) {
                int size = queue.size();
                Node first = queue.peek();
                for (int i = 0; i < size; i++) {
                    Node node = queue.poll();
                    node.next = queue.peek();
                    if (i == size - 1) {
                        node.next = null;
                    }
                    if (node.left != null) {
                        queue.offer(node.left);
                    }
                    if (node.right != null) {
                        queue.offer(node.right);
                    }
                }
            }
            return root;
        }
    }

    //  38288 kb
    class Solution5 {
        public Node connect(Node root) {
            if (root == null) {
                return null;
            }
            Node nextIndex = root.next;
            while (nextIndex != null) {
                if (nextIndex.left != null || nextIndex.right != null) {
                    break;
                }
                nextIndex = nextIndex.next;
            }
            if (root.left != null && root.right != null) {
                root.left.next = root.right;
            }
            if (root.left != null && root.right == null) {
                if (nextIndex != null && nextIndex.left != null) {
                    root.left.next = nextIndex.left;
                }
                if (nextIndex != null && nextIndex.left == null && nextIndex.right != null) {
                    root.left.next = nextIndex.right;
                }
            }
            if (root.right != null && nextIndex != null) {
                if (nextIndex.left != null) {
                    root.right.next = nextIndex.left;
                }
                if (nextIndex.left == null && nextIndex.right != null) {
                    root.right.next = nextIndex.right;
                }
            }
            if (root.right != null) {
                connect(root.right);
            }
            if (root.left != null) {
                connect(root.left);
            }
            return root;
        }
    }
}
