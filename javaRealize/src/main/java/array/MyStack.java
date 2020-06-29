package array;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 小结课程题：  用队列实现栈
 * 问题：  1. 提交后参考是ArrayDeque，使用from和to，互相倒水poll方法（改为这种方式）
 *         2. 原解法使用ArrayDeque的pop和peek不需要这样
 **/
public class MyStack {

    Queue<Integer> queue1;
    Queue<Integer> queue2;

    /**
     * Initialize your data structure here.
     */
    public MyStack() {
        queue1 = new ArrayDeque<>();
        queue2 = new ArrayDeque<>();
    }

    /**
     * Push element x onto stack.
     */
    public void push(int x) {
        if (queue1.size() != 0) {
            queue1.add(x);
        } else {
            queue2.add(x);
        }
    }

    /**
     * Removes the element on top of the stack and returns that element.
     */
    public int pop() {
        Queue<Integer> from;
        Queue<Integer> to;
        if (queue1.size() != 0) {
            from = queue1;
            to = queue2;
        } else {
            from = queue2;
            to = queue1;
        }

        while (from.size() != 1) {
            to.add(from.poll());
        }
        return from.poll();
    }

    /**
     * Get the top element.
     */
    public int top() {
        Queue<Integer> from;
        Queue<Integer> to;
        if (queue1.size() != 0) {
            from = queue1;
            to = queue2;
        } else {
            from = queue2;
            to = queue1;
        }

        while (from.size() != 1) {
            to.add(from.poll());
        }
        int x = from.peek();
        to.add(from.poll());
        return x;
    }

    /**
     * Returns whether the stack is empty.
     */
    public boolean empty() {
        return queue1.isEmpty() && queue2.isEmpty();
    }
}
