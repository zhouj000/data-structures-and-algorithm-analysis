package linklist.twopoint;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 删除链表的倒数第N个节点
 *      问题： 1. 没什么特别好的思路，就是将最后N位放到队列中，然后处理
 *             2. 最佳解直接用1个指针量好了距离，然后2个指针一起走，找到位置节点处理
 **/
public class RemoveNthFromEnd {

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    /**
     * 执行用时： 2 ms , 在所有 Java 提交中击败了 21.61% 的用户
     * 内存消耗： 37.3 MB , 在所有 Java 提交中击败了 11.17% 的用户
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null || head.next == null) {
            return null;
        }
        Queue<ListNode> queue = new ArrayDeque<>(n + 1);
        ListNode node = head;
        while (node != null) {
            if (queue.size() > n) {
                queue.poll();
            }
            queue.offer(node);
            node = node.next;
        }
        int count = queue.size();
        ListNode before = queue.poll();
        ListNode now = queue.poll();
        if (count < n + 1) {
            return now;
        }
        ListNode after = queue.poll();
        before.next = after;
        return head;
    }


    class BestSolution {
        public ListNode removeNthFromEnd(ListNode head, int n) {
            if (head == null || n == 0) {
                return head;
            }
            ListNode p = head, q = head;
            while (n-- > 0) {
                p = p.next;
            }
            if (p == null) {
                return q.next;
            }
            while (p.next != null) {
                p = p.next;
                q = q.next;
            }
            q.next = q.next.next;
            return head;
        }
    }

    // 空间最优
    class Solution2 {
        public ListNode removeNthFromEnd(ListNode head, int n) {
            if (head == null || head.next == null) {
                return null;
            }
            int a = 0;
            ListNode ans = new ListNode(-1);
            ans.next = head;
            ListNode delnode = ans;
            while (head != null) {
                if (a > n - 1) {
                    delnode = delnode.next;
                }
                head = head.next;
                a++;
            }
            delnode.next = delnode.next.next;

            return ans.next;
        }
    }

}
