package linklist.twopoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 回文链表
 *      问题： 1.用了最笨的办法，直接查出来后判断
 **/
public class IsPalindrome {

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    /**
     执行用时： 4 ms , 在所有 Java 提交中击败了 27.75% 的用户
     内存消耗： 42.9 MB , 在所有 Java 提交中击败了 11.30% 的用户
     */
    public boolean isPalindrome(ListNode head) {
        if (head == null) {
            return true;
        }
        List<Integer> list = new ArrayList<>();
        while (head != null) {
            list.add(head.val);
            head = head.next;
        }
        int size = list.size();
        for (int i = 0, j = size - 1; i < size / 2; i++, j--) {
            if (!list.get(i).equals(list.get(j))) {
                return false;
            }
        }
        return true;
    }



    // 后注： 取到中间节点，然后反转后半部分，再进行比较
    //        惊讶于竟然效率最高...
    class BestSolution {
        public boolean isPalindrome(ListNode head) {
            if (head == null) {
                return true;
            }
            ListNode p = head;
            ListNode halfhead = helpzhong(head);
            ListNode halfstart = helpveverse(halfhead.next);
            ListNode q = halfstart;
            boolean result = true;
            while (result && q != null) {
                if (p.val != q.val) {
                    result = false;
                }
                p = p.next;
                q = q.next;
            }
            return result;
        }

        public ListNode helpzhong(ListNode head) {
            ListNode f = head;
            ListNode l = head;
            while (f.next != null && f.next.next != null) {
                f = f.next.next;
                l = l.next;
            }
            return l;
        }

        public ListNode helpveverse(ListNode head) {
            if (head == null) {
                return null;
            }
            ListNode pre = null;
            ListNode temp = head;
            ListNode next = null;
            while (temp != null) {
                next = temp.next;
                temp.next = pre;
                pre = temp;
                temp = next;
            }
            return pre;
        }
    }

    // 比我的好一点，我之前也想用Stack结构，后来感觉没啥必要，算同一个思路的解法
    class Solution2 {
        public boolean isPalindrome(ListNode head) {
            if (head == null) {
                return true;
            }
            Stack<ListNode> stack = new Stack<>();
            ListNode cur = head;
            while (cur != null) {
                stack.push(cur);
                cur = cur.next;
            }

            cur = head;
            while (!stack.isEmpty()) {
                ListNode pop = stack.pop();
                if (cur.val != pop.val) {
                    return false;
                }
                cur = cur.next;
            }
            return true;
        }
    }




    // 空间最优
    class Solution3 {
        public boolean isPalindrome(ListNode head) {
            ListNode slow = head;
            ListNode fast = head;
            ListNode pre = null;
            // 后注： 在第一次找中间节点时，顺便将前半部分反转了
            while (fast != null && fast.next != null) {
                ListNode next = slow.next;
                fast = fast.next.next;
                slow.next = pre;
                pre = slow;
                slow = next;
            }
            if (fast != null) {
                slow = slow.next;
            }
            while (pre != null && slow != null) {
                if (pre.val == slow.val) {
                    pre = pre.next;
                    slow = slow.next;
                } else {
                    return false;
                }
            }
            return true;
        }
    }
}
