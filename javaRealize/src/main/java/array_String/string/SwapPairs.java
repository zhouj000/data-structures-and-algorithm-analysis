package array_String.string;

/**
 * 递归
 * 两两交换链表中的节点
 *
 **/
public class SwapPairs {

    public class ListNode {
        int val;
        ListNode next;
        public ListNode(int val) {
            this.val = val;
        }
    }

    /**
     * 执行用时： 0 ms , 在所有 Java 提交中击败了 100.00% 的用户
     * 内存消耗： 36 MB , 在所有 Java 提交中击败了 65.74% 的用户
     */
    public ListNode swapPairs(ListNode head) {
        return help(head);
    }

    public ListNode help(ListNode before){
        if(before == null || before.next == null){
            return before;
        }
        ListNode after = before.next;
        before.next = help(after.next);
        after.next = before;
        return after;
    }




    // 36056 kb
    class BestSolution {
        public ListNode swapPairs(ListNode head) {
            if (head == null) {
                return null;
            }
            ListNode h = new ListNode(0), tem = h;
            h.next = head;
            ListNode p1 = head, p2 = p1.next;
            while (p1 != null && p2 != null) {
                ListNode next = p2.next, nextNext = null;
                if (next != null) {
                    nextNext = next.next;
                }
                tem.next = p2;
                p2.next = p1;
                p1.next = next;
                tem = p1;
                p1 = next;
                p2 = nextNext;
            }
            return h.next;
        }
    }

}
