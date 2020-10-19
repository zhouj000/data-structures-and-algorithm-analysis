package linklist;

/**
 * 对照
 * 链表和其他数据结构（包括数组，队列和栈）之间时间复杂度的比较
 **/
public class AllOn {

    /**
                                    Array   singly Linked List   doubly Linked List   Queue   Stack
     Access   by index              O(1)    O(n)                 O(n)                 O(n)    O(n)
     Add      before first node     O(n)    O(1)                 O(1)                 O(n)    O(n)
              after given node      O(n)    O(1)                 O(1)                 O(n)    O(n)
              after last node       O(1)    O(1)                 O(1)                 O(1)    O(1)
     Delete   the first node        O(n)    O(1)                 O(1)                 O(1)    O(n)
              a given node          O(n)    O(n)                 O(1)                 O(n)    O(n)
              the last node         O(1)    O(n)                 O(1)                 O(n)    O(1)
     Search   a given node          O(n)    O(n)                 O(n)                 O(n)    O(n)


     如果你需要经常 [添加] 或 [删除] 结点，链表 可能是一个不错的选择。

     如果你需要经常按 [索引] [访问] 元素，数组 可能是比链表更好的选择。

     */
}
