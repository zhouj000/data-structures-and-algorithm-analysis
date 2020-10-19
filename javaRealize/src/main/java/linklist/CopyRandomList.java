package linklist;

import java.util.HashMap;
import java.util.Map;

/**
 * 复制带随机指针的链表
 *
 **/
public class CopyRandomList {

    static class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    public Node copyRandomList(Node head) {
        return null;
    }


    // 网上找的，双100%
    class BestSolution {
        public Node copyRandomList(Node head) {
            if(head == null){
                return head;
            }
            // 空间复杂度O(1)，将克隆结点放在原结点后面
            Node node = head;
            // 1->2->3  ==>  1->1'->2->2'->3->3'
            while(node != null){
                Node clone = new Node(node.val);
                clone.next = node.next;
                Node temp = node.next;
                node.next = clone;
                node = temp;
            }
            // 处理random指针
            node = head;
            while(node != null){
                // ！！
                node.next.random = node.random == null ? null : node.random.next;
                node = node.next.next;
            }
            // 还原原始链表，即分离原链表和克隆链表
            node = head;
            Node cloneHead = head.next;
            while(node.next != null){
                Node temp = node.next;
                node.next = node.next.next;
                node = temp;
            }
            return cloneHead;
        }
    }


    class Solution2 {
        public Node copyRandomList(Node head) {
            if (head == null) {
                return null;
            }
            Node p = head;
            Map<Node, Node> map = new HashMap<>();
            while (p != null) {
                map.put(p, new Node(p.val));
                p = p.next;
            }
            p = head;
            while (p != null) {
                map.get(p).next = map.get(p.next);
                map.get(p).random = map.get(p.random);
                p = p.next;
            }
            return map.get(head);
        }
    }

}
