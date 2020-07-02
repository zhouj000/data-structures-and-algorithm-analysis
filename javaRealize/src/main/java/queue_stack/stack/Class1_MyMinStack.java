package queue_stack.stack;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 课程题：最小栈
 *
 * 与最佳思路不同：
 * 1.不知道可以直接用Java现成的Stack数据结构，结果用了List作为代替
 * 2.min想到用一个变量存放，但是没考虑好pop的正好是min后面怎么更新min，用了时间复杂度N的遍历
 *   按它的思路就没问题了，老min被替换时候存了2个原值和新的min，这样新min弹出后面跟的就是次小的
 *
 **/
public class Class1_MyMinStack {

    private List<Integer> stack;

    public Class1_MyMinStack() {
        stack = new ArrayList<>();
    }

    public void push(int x) {
        stack.add(x);
    }

    public void pop() {
        if (!stack.isEmpty()) {
            stack.remove(stack.size() - 1);
        }
    }

    public int top() {
        if (stack.isEmpty()) {
            return 0;
        }
        return stack.get(stack.size() - 1);
    }

    public int getMin() {
        if (stack.isEmpty()) {
            return 0;
        }
        int min = stack.get(0);
        for (Integer num : stack) {
            if (min > num) {
                min = num;
            }
        }
        return min;
    }



    class MinStackBest {
        private int min = Integer.MAX_VALUE;
        private Stack<Integer> stack;

        public MinStackBest() {
            stack = new Stack<>();
        }

        public void push(int x) {
            if(min >= x){
                stack.push(min);
                min = x;
            }
            stack.push(x);
        }

        public void pop() {
            if(stack.pop() == min){
                min = stack.pop();
            }
        }

        public int top() {
            return stack.peek();
        }

        public int getMin() {
            return min;
        }
    }

}
