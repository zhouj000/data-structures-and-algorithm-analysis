package linklist;

import java.util.Stack;

/**
 * 扁平化多级双向链表
 *
 **/
public class Flatten {

    class Node {
        public int val;
        public Node prev;
        public Node next;
        public Node child;
    };

    /**
     执行用时： 1 ms , 在所有 Java 提交中击败了 20.74% 的用户
     内存消耗： 36.6 MB , 在所有 Java 提交中击败了 92.02% 的用户
     */
    public Node flatten(Node head) {
        if (head == null) {
            return head;
        }
        Node top = new Node();
        Node node = top;
        Stack<Node> stack = new Stack<>();
        stack.add(head);
        while (!stack.isEmpty()) {
            Node tmp = stack.pop();
            if (tmp == null) {
                continue;
            }
            stack.push(tmp.next);
            stack.push(tmp.child);
            tmp.child = null;
            node.next = tmp;
            tmp.prev = node;
            node = node.next;
        }
        node = top.next;
        node.prev = null;
        return node;
    }


    class BestSolution {

        public Node flatten(Node head) {
            Node p = head;
            while (p != null) {
                if (p.child != null) {
                    Node next = p.next;
                    Node child = p.child;
                    p.next = child;

                    p.child = null;
                    child.prev = p;

                    while (child.next != null) {
                        child = child.next;
                    }
                    child.next = next;
                    if (next != null) {
                        next.prev = child;
                    }
                }
                p = p.next;
            }
            return head;
        }
    }
}
