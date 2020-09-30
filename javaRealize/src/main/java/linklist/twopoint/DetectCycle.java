package linklist.twopoint;

/**
 * 环形链表 II
 *
 **/
public class DetectCycle {

    class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    /**
     网上找的解法:
        a      b1   b2(a)
     0 ---- 0 --X----0
            |--------|

        (a + b1) * 2 = a + b1 + b2 + b1
            2a + 2b1 = a + 2b1 + b2
                   a = b2
        a + b1 + x = x
        if x == a   ->  交点
     */
    public ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }
        ListNode fast = head;
        ListNode slow = head;
        boolean cycle = false;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                cycle = true;
                break;
            }
        }
        if (!cycle) {
            return null;
        }
        slow = head;
        while (slow != fast) {
            fast = fast.next;
            slow = slow.next;
        }
        return fast;
    }




    public class BestSolution {
        public ListNode detectCycle(ListNode head) {
            ListNode slow = head;
            ListNode fast = head;
            while (true) {
                if (fast == null || fast.next == null) {
                    return null;
                }
                fast = fast.next.next;
                slow = slow.next;
                if (slow == fast) {
                    break;
                }
            }
            fast = head;
            while (fast != slow) {
                fast = fast.next;
                slow = slow.next;
            }
            return slow;
        }
    }
}
