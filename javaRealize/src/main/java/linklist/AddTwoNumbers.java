package linklist;

/**
 * 两数相加
 *      问题： 最优解的核心就是  将 进位extra 单独拿出来保存了下来，并且可以作为判断条件
 *             直接将整个流程优化了
 **/
public class AddTwoNumbers {

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
     执行用时： 2 ms , 在所有 Java 提交中击败了 99.92% 的用户
     内存消耗： 38.9 MB , 在所有 Java 提交中击败了 70.62% 的用户
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(0);
        ListNode tmp = head;
        while (true) {
            int value = tmp.val + l1.val + l2.val;
            ListNode next = new ListNode(value / 10);
            tmp.next = next;
            tmp.val = value % 10;
            // 最优解将这里的判断放到 while条件中: extra的作用
            if (l1.next == null && l2.next == null) {
                tmp.next = next.val == 0 ? null : next;
                break;
            }
            // 这里直接传null, 由取值处判断，可以省内存空间
            l1 = l1.next != null ? l1.next : new ListNode(0);
            l2 = l2.next != null ? l2.next : new ListNode(0);
            tmp = next;
        }
        return head;
    }




    class BestSolution {

        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            // 后注： 这里讲extra拎出来，作为判断while的条件，解决了数字不对齐的情况能继续计算
            int extra = 0;
            ListNode head = new ListNode(0);
            ListNode currentNode = head;
            while (l1 != null || l2 != null || extra == 1) {
                int v1 = l1 == null ? 0 : l1.val;
                int v2 = l2 == null ? 0 : l2.val;
                int val = (v1 + v2 + extra) % 10;
                extra = (v1 + v2 + extra) / 10;
                // 后注：这里是从head的下一节点作为计算开始，是将进位extra保存了下来
                currentNode.next = new ListNode(val);
                currentNode = currentNode.next;
                l1 = l1 == null ? null : l1.next;
                l2 = l2 == null ? null : l2.next;
            }
            return head.next;
        }
    }


    class Solution2 {

        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            ListNode dummy = new ListNode(-1);
            ListNode pre = dummy;
            int carry = 0;
            while (l1 != null || l2 != null || carry != 0) {
                int sum = 0;
                // 后注： 这里的判断省略了同步next，更改了sum的方式 (拆分2个if累加)
                if (l1 != null) {
                    sum += l1.val;
                    l1 = l1.next;
                }
                if (l2 != null) {
                    sum += l2.val;
                    l2 = l2.next;
                }

                sum += carry;

                pre.next = new ListNode(sum % 10);
                pre = pre.next;
                carry = sum / 10;
            }
            return dummy.next;
        }
    }

}
