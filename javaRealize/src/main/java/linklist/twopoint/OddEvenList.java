package linklist.twopoint;

/**
 * 奇偶链表
 *
 **/
public class OddEvenList {

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    /**
     执行用时： 0 ms , 在所有 Java 提交中击败了 100.00% 的用户
     内存消耗： 38.8 MB , 在所有 Java 提交中击败了 6.96% 的用户
     */
    public ListNode oddEvenList(ListNode head) {
        if (head == null || head.next == null || head.next.next == null) {
            return head;
        }
        ListNode oddHead = new ListNode(-1);
        ListNode evenHead = new ListNode(-2);
        oddHead.next = head;
        ListNode node = oddHead;
        ListNode even = null;
        while (node.next != null) {
            node = node.next;
            ListNode tmp = node.next;
            if (even == null) {
                evenHead.next = tmp;
            } else {
                even.next = tmp;
            }
            node.next = tmp != null ? tmp.next : null;
            even = tmp;
        }
        node.next = evenHead.next;
        return head;
    }




    class BestSolution {
        public ListNode oddEvenList(ListNode head) {
            if (head == null || head.next == null) {
                return head;
            }
            ListNode oddHead = head;
            ListNode evenHead = head.next;
            ListNode evenTemp = evenHead;
            while (evenHead != null && evenHead.next != null) {
                oddHead.next = evenHead.next;
                oddHead = oddHead.next;
                evenHead.next = oddHead.next;
                evenHead = evenHead.next;
            }
            oddHead.next = evenTemp;
            return head;
        }
    }

    // 空间最优
    class Solution {
        public ListNode oddEvenList(ListNode head) {
            if (head == null) {
                return head;
            }
            ListNode tmp1;
            ListNode tmp2;
            ListNode change;
            ListNode change2;
            tmp1 = head;
            tmp2 = tmp1.next;
            if (tmp2 == null) {
                return head;
            }
            while (tmp2 != null) {
                change2 = tmp1.next;
                if (tmp2.next == null) {
                    return head;
                }
                tmp1.next = tmp2.next;
                tmp1 = tmp1.next;
                change = tmp1.next;
                tmp1.next = change2;
                tmp2.next = change;
                tmp2 = tmp2.next;
            }
            return head;
        }
    }
}
