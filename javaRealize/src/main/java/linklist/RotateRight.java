package linklist;

/**
 * 旋转链表
 *
 **/
public class RotateRight {

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    /**
     执行用时： 1 ms , 在所有 Java 提交中击败了 89.53% 的用户
     内存消耗： 37.5 MB , 在所有 Java 提交中击败了 99.97% 的用户
     */
    public ListNode rotateRight(ListNode head, int k) {
        if (head == null) {
            return null;
        }
        int count = 0;
        ListNode newHead = new ListNode(0);
        ListNode tmpN = newHead;
        ListNode tmp = head;
        while (tmp != null) {
            ListNode node = new ListNode(tmp.val);
            tmpN.next = node;
            tmpN = tmpN.next;
            tmp = tmp.next;
            count++;
        }
        tmpN.next = head;
        // 查找起点
        int rd = count - k % count;
        ListNode first = newHead.next;
        while (rd-- > 0) {
            first = first.next;
        }
        tmp = first;
        while (--count > 0) {
            tmp = tmp.next;
        }
        tmp.next = null;
        return first;
    }



    // 后注：我是写了2个，这个是自己内循环了，思路是一样的
    class BestSolution {
        public ListNode rotateRight(ListNode head, int k) {
            if (head == null || head.next == null) {
                return head;
            }
            int count = 1;
            ListNode p = head;
            while (p.next != null) {
                p = p.next;
                count++;
            }
            k = k % count;
            if (k == 0) {
                return head;
            }
            p.next = head;
            while (k < count) {
                p = p.next;
                k++;
            }
            head = p.next;
            p.next = null;
            return head;
        }
    }

    class Solution2 {
        public ListNode rotateRight(ListNode head, int k) {
            if (head == null || k == 0 || head.next == null) {
                return head;
            }
            ListNode head1 = head;
            ListNode fastNode = head;
            ListNode slowNode = head;
            int len = 0;
            while (head != null) {
                head = head.next;
                len++;
            }
            k = k % len;
            if (k == 0) {
                return head1;
            }
            while (k > 0) {
                fastNode = fastNode.next;
                k--;
            }
            while (fastNode.next != null) {
                fastNode = fastNode.next;
                slowNode = slowNode.next;
            }
            head = slowNode.next;
            fastNode.next = head1;
            slowNode.next = null;
            return head;
        }
    }
}
