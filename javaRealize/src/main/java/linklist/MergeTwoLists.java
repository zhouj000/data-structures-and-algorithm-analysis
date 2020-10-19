package linklist;

/**
 * 合并两个有序链表
 *      模板和我写的一样。。 但是不是击败100%用户。。。
 *
 **/
public class MergeTwoLists {

    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }
        ListNode(int val) {
            this.val = val;
        }
        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    /**
     执行用时： 1 ms , 在所有 Java 提交中击败了 62.63% 的用户
     内存消耗： 38.4 MB , 在所有 Java 提交中击败了 70.02% 的用户
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode node = new ListNode(0);
        ListNode tmp = node;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                tmp.next = l1;
                l1 = l1.next;
            } else {
                tmp.next = l2;
                l2 = l2.next;
            }
            tmp = tmp.next;
        }
        if (l1 != null) {
            tmp.next = l1;
        }   // 模板这里是 else if
        if (l2 != null) {
            tmp.next = l2;
        }
        return node.next;
    }

}
