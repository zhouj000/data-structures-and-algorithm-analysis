package array;

import java.util.Stack;

/**
 * 小结课程题：  用栈实现队列
 *  问题： 1. 没有好坏，提交后的参考中是使用了2个Stack，来模拟队列的，一个用来反向存储元素
 *         2. 漏题了，要求是 你只能使用标准的栈操作 -- 也就是只有 push to top, peek/pop from top, size, 和 is empty 操作是合法的
 **/
public class MyQueue {

    private Stack<Integer> stack;
    private Stack<Integer> tempStack;

    /** Initialize your data structure here. */
    public MyQueue() {
        stack = new Stack<>();
        tempStack = new Stack<>();
    }

    /** Push element x to the back of queue. */
    public void push(int x) {
        while (!stack.isEmpty()) {
            tempStack.push(stack.pop());
        }
        stack.push(x);
        while(!tempStack.isEmpty()) {
            stack.push(tempStack.pop());
        }
    }

    /** Removes the element from in front of queue and returns that element. */
    public int pop() {
//        int res = stack.elementAt(0);
//        stack.remove(0);
//        return res;
        return stack.pop();
    }

    /** Get the front element. */
    public int peek() {
//        return stack.elementAt(0);
        return stack.peek();
    }

    /** Returns whether the queue is empty. */
    public boolean empty() {
        return stack.isEmpty();
    }

}
