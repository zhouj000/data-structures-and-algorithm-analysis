package queue_stack.queue;

/**
 * 课程题：设计循环队列
 *
 * 自己的设计问题：
 * 1.没有使用%取余技巧获取位置
 * 2.没有count统计已有大小，示范中的size我认为不是必须的
 * 3.空的时放入第一个元素的处理较复杂
 *
 * java类似实现：java.util.concurrent.ArrayBlockingQueue
 */
class Class1_MyCircularQueue {

    private int[] array;
    private int head = -1;
    private int tail = -1;

    /**
     * Initialize your data structure here. Set the size of the queue to be k.
     */
    public Class1_MyCircularQueue(int k) {
        array = new int[k];
    }

    /**
     * Insert an element into the circular queue. Return true if the operation is successful.
     */
    public boolean enQueue(int value) {
        if (isEmpty()) {
            array[0] = value;
            head = 0;
            tail = 0;
            return true;
        }
        if (isFull()) {
            return false;
        }
        tail = getNext(tail);
        array[tail] = value;
        return true;
    }

    /**
     * Delete an element from the circular queue. Return true if the operation is successful.
     */
    public boolean deQueue() {
        if (isEmpty()) {
            return false;
        }
        if (head == tail) {
            head = -1;
            tail = -1;
            return true;
        }
        head = getNext(head);
        return true;
    }

    /**
     * Get the front item from the queue.
     */
    public int Front() {
        if (isEmpty()) {
            return -1;
        }
        return array[head];
    }

    /**
     * Get the last item from the queue.
     */
    public int Rear() {
        if (isEmpty()) {
            return -1;
        }
        return array[tail];
    }

    /**
     * Checks whether the circular queue is empty or not.
     */
    public boolean isEmpty() {
        return head == -1 && tail == -1;
    }

    /**
     * Checks whether the circular queue is full or not.
     */
    public boolean isFull() {
        if (isEmpty()) {
            return false;
        }
        return getNext(tail) == head;
    }

    private int getNext(int now) {
        return (now + 1 == array.length) ? 0 : now + 1;
    }


    /**
     * 课程题设计示范
     */
    class MyCircularQueueExample {

        private int[] data;
        private int head;
        private int tail;
        private int size;

        /**
         * Initialize your data structure here. Set the size of the queue to be k.
         */
        public MyCircularQueueExample(int k) {
            data = new int[k];
            head = -1;
            tail = -1;
            size = k;
        }

        /**
         * Insert an element into the circular queue. Return true if the operation is successful.
         */
        public boolean enQueue(int value) {
            if (isFull() == true) {
                return false;
            }
            if (isEmpty() == true) {
                head = 0;
            }
            tail = (tail + 1) % size;
            data[tail] = value;
            return true;
        }

        /**
         * Delete an element from the circular queue. Return true if the operation is successful.
         */
        public boolean deQueue() {
            if (isEmpty() == true) {
                return false;
            }
            if (head == tail) {
                head = -1;
                tail = -1;
                return true;
            }
            head = (head + 1) % size;
            return true;
        }

        /**
         * Get the front item from the queue.
         */
        public int Front() {
            if (isEmpty() == true) {
                return -1;
            }
            return data[head];
        }

        /**
         * Get the last item from the queue.
         */
        public int Rear() {
            if (isEmpty() == true) {
                return -1;
            }
            return data[tail];
        }

        /**
         * Checks whether the circular queue is empty or not.
         */
        public boolean isEmpty() {
            return head == -1;
        }

        /**
         * Checks whether the circular queue is full or not.
         */
        public boolean isFull() {
            return ((tail + 1) % size) == head;
        }
    }


    /**
     * 提交库中最优的设计
     */
    class MyCircularQueueBest {
        private int header = 0;
        private int tail = 0;
        private int count = 0;
        private int[] data;

        /**
         * Initialize your data structure here. Set the size of the queue to be k.
         */
        public MyCircularQueueBest(int k) {
            data = new int[k];
        }

        /**
         * Insert an element into the circular queue. Return true if the operation is successful.
         */
        public boolean enQueue(int value) {
            if (this.isFull()) {
                return false;
            }
            data[tail] = value;
            tail = ++tail % data.length;
            ++count;
            return true;
        }

        /**
         * Delete an element from the circular queue. Return true if the operation is successful.
         */
        public boolean deQueue() {
            if (this.isEmpty()) {
                return false;
            }
            header = ++header % data.length;
            --count;
            return true;
        }

        /**
         * Get the front item from the queue.
         */
        public int Front() {
            if (this.isEmpty()) {
                return -1;
            }
            return data[header];
        }

        /**
         * Get the last item from the queue.
         */
        public int Rear() {
            if (this.isEmpty()) {
                return -1;
            }
            return data[(tail + data.length - 1) % data.length];
        }

        /**
         * Checks whether the circular queue is empty or not.
         */
        public boolean isEmpty() {
            return count == 0;
        }

        /**
         * Checks whether the circular queue is full or not.
         */
        public boolean isFull() {
            return count == data.length;
        }
    }

}
