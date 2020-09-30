package linklist.twopoint;

/**
 * 反转链表
 **/
public class ReverseList {

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    /**
     执行用时： 0 ms , 在所有 Java 提交中击败了 100.00% 的用户
     内存消耗： 38.7 MB , 在所有 Java 提交中击败了 58.36% 的用户
     */
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode top = head;
        while (head.next != null) {
            ListNode tmp = head.next;
            head.next = tmp.next;
            tmp.next = top;
            top = tmp;
        }
        return top;
    }



    // 空间最优
    class Solution {
        public ListNode reverseList(ListNode head) {
            ListNode header = null;

            while (head != null) {
                ListNode tmp = new ListNode(head.val);
                if (header == null) {
                    tmp.next = null;
                    header = tmp;
                } else {
                    tmp.next = header;
                    header = tmp;
                }
                head = head.next;
            }
            return header;
        }
    }

    // 空间最优2
    class Solution2 {
        public ListNode reverseList(ListNode head) {
            if (head == null || head.next == null) {
                return head;
            }
            ListNode prev = head;
            ListNode curr = prev.next;
            while (prev.next != null) {
                prev.next = curr.next;
                curr.next = head;

                head = curr;
                curr = prev.next;
            }
            return head;
        }
    }
}
