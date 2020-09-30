package linklist.twopoint;

/**
 * 设计链表
 *      问题： 1. 没有加默认头部
 */
class MyLinkedList {

    class Node {
        int val;
        Node next;

        public Node(int val) {
            this.val = val;
        }
    }

    private Node head;
    private int size;

    /**
     * Initialize your data structure here.
     */
    public MyLinkedList() {
    }

    /**
     * Get the value of the index-th node in the linked list. If the index is invalid, return -1.
     */
    public int get(int index) {
        if (index < 0 || index >= size) {
            return -1;
        }
        Node cur = head;
        while (index-- > 0) {
            cur = cur.next;
        }
        return cur.val;
    }

    /**
     * Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list.
     */
    public void addAtHead(int val) {
        Node cur = head;
        head = new Node(val);
        head.next = cur;
        size++;
    }

    /**
     * Append a node of value val to the last element of the linked list.
     */
    public void addAtTail(int val) {
        Node newNode = new Node(val);
        if (head == null) {
            head = newNode;
        } else {
            Node cur = head;
            while (cur.next != null) {
                cur = cur.next;
            }
            cur.next = newNode;
        }
        size++;
    }

    /**
     * Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted.
     */
    public void addAtIndex(int index, int val) {
        if (index > size) {
            return;
        }
        if (index <= 0) {
            addAtHead(val);
            return;
        }
        Node cur = head;
        while (--index > 0) {
            cur = cur.next;
        }
        Node newNode = new Node(val);
        newNode.next = cur.next;
        cur.next = newNode;
        size++;
    }

    /**
     * Delete the index-th node in the linked list, if the index is valid.
     */
    public void deleteAtIndex(int index) {
        if (index < 0 || index >= size) {
            return;
        }
        size--;
        if (index == 0) {
            head = head.next;
            return;
        }
        Node cur = head;
        while (--index > 0) {
            cur = cur.next;
        }
        cur.next = cur.next.next;
    }




    /**
     * 后注： 1. 使用next, prev，双向链表
     *        2. 我也用head, tail(后来出错去掉了,还没找到原因)，这里将head和tail作为固定节点，非新增点
     *           这样避免了一些麻烦，只是作为锚定，这个做法挺好的，能避免一些错误
     */
    public class ListNode {
        int val;
        ListNode next;
        ListNode prev;
        ListNode(int x) { val = x; }
    }

    class BestMyLinkedList {
        int size;
        // sentinel nodes as pseudo-head and pseudo-tail
        ListNode head, tail;
        public BestMyLinkedList() {
            size = 0;
            head = new ListNode(0);
            tail = new ListNode(0);
            head.next = tail;
            tail.prev = head;
        }

        /** Get the value of the index-th node in the linked list. If the index is invalid, return -1. */
        public int get(int index) {
            // if index is invalid
            if (index < 0 || index >= size) {
                return -1;
            }

            // choose the fastest way: to move from the head
            // or to move from the tail
            ListNode curr = head;
            if (index + 1 < size - index) {
                for(int i = 0; i < index + 1; ++i) {
                    curr = curr.next;
                }
            } else {
                curr = tail;
                for(int i = 0; i < size - index; ++i) {
                    curr = curr.prev;
                }
            }
            return curr.val;
        }

        /** Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list. */
        public void addAtHead(int val) {
            ListNode pred = head, succ = head.next;

            ++size;
            ListNode toAdd = new ListNode(val);
            toAdd.prev = pred;
            toAdd.next = succ;
            pred.next = toAdd;
            succ.prev = toAdd;
        }

        /** Append a node of value val to the last element of the linked list. */
        public void addAtTail(int val) {
            ListNode succ = tail, pred = tail.prev;

            ++size;
            ListNode toAdd = new ListNode(val);
            toAdd.prev = pred;
            toAdd.next = succ;
            pred.next = toAdd;
            succ.prev = toAdd;
        }

        /** Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted. */
        public void addAtIndex(int index, int val) {
            // If index is greater than the length,
            // the node will not be inserted.
            if (index > size) {
                return;
            }

            // [so weird] If index is negative,
            // the node will be inserted at the head of the list.
            if (index < 0) {
                index = 0;
            }

            // find predecessor and successor of the node to be added
            ListNode pred, succ;
            if (index < size - index) {
                pred = head;
                for(int i = 0; i < index; ++i) {
                    pred = pred.next;
                }
                succ = pred.next;
            } else {
                succ = tail;
                for (int i = 0; i < size - index; ++i) {
                    succ = succ.prev;
                }
                pred = succ.prev;
            }

            // insertion itself
            ++size;
            ListNode toAdd = new ListNode(val);
            toAdd.prev = pred;
            toAdd.next = succ;
            pred.next = toAdd;
            succ.prev = toAdd;
        }

        /** Delete the index-th node in the linked list, if the index is valid. */
        public void deleteAtIndex(int index) {
            // if the index is invalid, do nothing
            if (index < 0 || index >= size) {
                return;
            }

            // find predecessor and successor of the node to be deleted
            ListNode pred, succ;
            if (index < size - index) {
                pred = head;
                for(int i = 0; i < index; ++i) {
                    pred = pred.next;
                }
                succ = pred.next.next;
            } else {
                succ = tail;
                for (int i = 0; i < size - index - 1; ++i) {
                    succ = succ.prev;
                }
                pred = succ.prev.prev;
            }

            // delete pred.next
            --size;
            pred.next = succ;
            succ.prev = pred;
        }
    }





    class ListNode2 {
        public int val;
        public ListNode2 next;

        public ListNode2() {
            this(0);
        }

        public ListNode2(int val) {
            this(val, null);
        }

        public ListNode2(int val, ListNode2 next) {
            this.val = val;
            this.next = next;
        }
    }

    class MyLinkedList2 {
        ListNode2 head = new ListNode2();
        ListNode2 tail = head;
        int size = 0;

        /**
         * Initialize your data structure here.
         */
        public MyLinkedList2() {
        }

        /**
         * Get the value of the index-th node in the linked list. If the index is invalid, return -1.
         */
        public int get(int index) {
            if (index >= size) {
                return -1;
            }
            ListNode2 node = head;
            for (int i = 0; i <= index; i++) {
                node = node.next;
            }

            return node.val;
        }

        /**
         * Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list.
         */
        public void addAtHead(int val) {
            ListNode2 node = new ListNode2(val);
            node.next = head.next;
            head.next = node;
            if(tail == head){
                tail = node;
            }
            ++size;
        }

        /**
         * Append a node of value val to the last element of the linked list.
         */
        public void addAtTail(int val) {
            ListNode2 node = new ListNode2(val);
            tail.next = node;
            tail = node;
            ++size;
        }

        /**
         * Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted.
         */
        public void addAtIndex(int index, int val) {
            if (index > size) {
                return;
            }
            if (index == 0) {
                addAtHead(val);
                return;
            }
            if (index == size) {
                addAtTail(val);
                return;
            }
            ListNode2 node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            ListNode2 n = new ListNode2(val);
            n.next = node.next;
            node.next = n;
            ++size;
        }

        /**
         * Delete the index-th node in the linked list, if the index is valid.
         */
        public void deleteAtIndex(int index) {
            if(index >= size){
                return;
            }
            ListNode2 node = head;
            for (int i = 0; i < index; i++) {
                node = node.next;
            }
            if(tail == node.next){
                tail = node;
            }
            node.next = node.next.next;
            --size;
        }
    }
}
