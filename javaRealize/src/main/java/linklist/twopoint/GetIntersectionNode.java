package linklist.twopoint;

/**
 * 相交链表
 *     问题：   1. 我的思路是右端对齐，找到同样长度的点后开始一起移动，如果相交就会相等
 *              2. 最佳阶梯思路很巧妙，通过跳跃链表遍历2次，必定 a + b = b + a，即遍历长度相等，这样如果会相交，则第二轮时会相等
 **/
public class GetIntersectionNode {

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    /**
     执行用时： 1 ms , 在所有 Java 提交中击败了 100.00% 的用户
     内存消耗： 41.6 MB , 在所有 Java 提交中击败了 70.01% 的用户
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        int countA = 0, countB = 0;
        ListNode nodeA = headA;
        while (nodeA != null) {
            countA++;
            nodeA = nodeA.next;
        }
        nodeA = headA;
        ListNode nodeB = headB;
        while (nodeB != null) {
            countB++;
            nodeB = nodeB.next;
        }
        nodeB = headB;
        if (countA > countB) {
            for (int i = 0; i < countA - countB; i++) {
                nodeA = nodeA.next;
            }
        } else {
            for (int i = 0; i < countB - countA; i++) {
                nodeB = nodeB.next;
            }
        }
        while(nodeA != null || nodeB != null) {
            if (nodeA == nodeB) {
                return nodeA;
            }
            nodeA = nodeA.next;
            nodeB = nodeB.next;
        }
        return null;
    }




    public class BestSolution {
        public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
            if (headA == null || headB == null) {
                return null;
            }

            ListNode nodeA = headA;
            ListNode nodeB = headB;

            // 在这里第一轮体现在 pA 和 pB 第一次到达尾部会移向另一链表的表头,
            // 而第二轮体现在如果 pA 或 pB 相交就返回交点, 不相交最后就是 null == null
            while (nodeA != nodeB) {
                nodeA = nodeA == null ? headB : nodeA.next;
                nodeB = nodeB == null ? headA : nodeB.next;
            }
            return nodeA;
        }
    }

}
