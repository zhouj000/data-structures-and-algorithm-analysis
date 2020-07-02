package queue_stack.stack;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

/**
 * 课程题： 逆波兰表达式求值
 *
 * 问题： 1. 最后一个一定是符号位
 *        2. DFS从后往前计算，类似动态规划的解法，求最后2步的结果
 *       3. 其他优化版本思路类似，优化更好，不及时间最优算法
 *
 *       栈的DFS是 树的深度算法      列表的BFS是 树的广度算法
 **/
public class Class4_EvalRPN {

    public int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];
            if ("+".equals(token)) {
                stack.push(stack.pop() + stack.pop());
            } else if ("-".equals(token)) {
                int num2 = stack.pop();
                int num1 = stack.pop();
                stack.push(num1 - num2);
            } else if ("*".equals(token)) {
                stack.push(stack.pop() * stack.pop());
            } else if ("/".equals(token)) {
                int num2 = stack.pop();
                int num1 = stack.pop();
                stack.push(num1 / num2);
            } else {
                stack.push(Integer.parseInt(token));
            }
        }
        return stack.peek();
    }

    public static void main(String[] args) {
        Class4_EvalRPN t = new Class4_EvalRPN();
        String[] a = new String[]{"4","13","5","/","+"};

        System.out.println(t.evalRPN(a));
    }






    // 时间最优
    class SolutionBest {
        int i;
        String[] str;

        public int evalRPN(String[] tokens) {
            i = tokens.length - 1;
            str = tokens;
            return dfs();
        }

        public int dfs() {
            String tmp = str[i--];
            int a, b;
            switch (tmp) {
                case "+":
                    b = dfs();
                    a = dfs();
                    return a + b;
                case "-":
                    b = dfs();
                    a = dfs();
                    return a - b;
                case "*":
                    b = dfs();
                    a = dfs();
                    return a * b;
                case "/":
                    b = dfs();
                    a = dfs();
                    return a / b;
                default:
                    return Integer.parseInt(tmp);
            }
        }
    }

    // 同思路解法，优化更好
    class Solution2 {
        public int evalRPN(String[] tokens) {
            int[] numStack = new int[tokens.length / 2 + 1];
            int index = 0;
            for (String s : tokens) {
                switch (s) {
                    case "+":
                        numStack[index - 2] += numStack[--index];
                        break;
                    case "-":
                        numStack[index - 2] -= numStack[--index];
                        break;
                    case "*":
                        numStack[index - 2] *= numStack[--index];
                        break;
                    case "/":
                        numStack[index - 2] /= numStack[--index];
                        break;
                    default:
                        // numStack[index++] = Integer.valueOf(s);
                        // valueOf改为parseInt，减少自动拆箱装箱操作
                        numStack[index++] = Integer.parseInt(s);
                        break;
                }
            }
            return numStack[0];
        }
    }

    // 空间最优
    class Solution {
        public int evalRPN(String[] tokens) {
            Deque<Integer> stack = new ArrayDeque<>();
            for (int i = 0; i < tokens.length; i++) {
                String s = tokens[i];
                if (s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/")) {
                    stack.push(process(stack.pop(), stack.pop(), s));
                } else {
                    stack.push(Integer.parseInt(tokens[i]));
                }
            }
            return stack.peek();
        }

        public int process(int v1, int v2, String operator) {
            if (operator.equals("+")) return v2 + v1;
            if (operator.equals("-")) return v2 - v1;
            if (operator.equals("*")) return v2 * v1;
            if (operator.equals("/")) return v2 / v1;
            return 0;
        }
    }
}
