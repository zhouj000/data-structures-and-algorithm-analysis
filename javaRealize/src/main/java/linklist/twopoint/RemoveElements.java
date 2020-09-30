package linklist.twopoint;

/**
 * 移除链表元素
 *
 **/
public class RemoveElements {

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    /**
     执行用时： 1 ms , 在所有 Java 提交中击败了 99.58% 的用户
     内存消耗： 39.8 MB , 在所有 Java 提交中击败了 50.59% 的用户
     */
    public ListNode removeElements(ListNode head, int val) {
        ListNode node = new ListNode(-1);
        ListNode top = node;
        node.next = head;
        while (node.next != null) {
            if (node.next.val == val) {
                node.next = node.next.next;
            } else {
                node = node.next;
            }
        }
        return top.next;
    }



    class BestSolution {
        public ListNode removeElements(ListNode head, int val) {
            if (head == null) {
                return null;
            }
            if (head.val == val) {
                return removeElements(head.next, val);
            }

            ListNode cur = head;
            while (cur.next != null) {
                if (cur.next.val == val) {
                    cur.next = cur.next.next;
                } else {
                    cur = cur.next;
                }
            }
            return head;
        }
    }
}
