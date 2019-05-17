package array.stack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * 课程题：有效的括号
 *
 * 问题：
 * 1.由于属于栈的题目，直接用Stack模仿队列的DFS思路写了，把问题复杂化了，直接用数组的前后位来判断会简单很多
 * 2.for循环直接遍历即可，不需要按stack是否为空来判断继续，反而多了长度判断
 * 3.看题目有空格的问题，做了replaceAll(耗时)来避免取下一个时判断空格，然而从其他实现中没有发现
 *
 **/
public class Class2_MySolution {

    public boolean isValid(String s) {
        if ("".equals(s)) {
            return true;
        }
        s.replaceAll(" ", "");
        Stack<Character> stack = new Stack<>();
        char[] array = s.toCharArray();
        int index = 0;
        stack.push(array[index]);
        while (!stack.empty()) {
            if (index + 1 >= array.length) {
                return false;
            }
            index = index + 1;
            char next = array[index];
            if (isLeft(next)) {
                stack.push(next);
                continue;
            }
            char left = stack.pop();
            if (!isEffective(left, next)) {
                return false;
            }
            if (index + 1 < array.length && stack.empty()) {
                index = index + 1;
                stack.push(array[index]);
            }
        }
        return true;
    }

    private boolean isLeft(char letter) {
        if (letter == '(' || letter == '{' || letter == '[') {
            return true;
        }
        return false;
    }

    private boolean isEffective(char left, char right) {
        if (left == '(' && right == ')' ||
                left == '{' && right == '}' ||
                left == '[' && right == ']') {
            return true;
        }
        return false;
    }




    class SolutionBest {

        public boolean isValid(String s) {
            char[] stack = new char[s.length() + 1];
            int top = 1;
            for (char c : s.toCharArray()) {
                if (c == '(' || c == '[' || c == '{') {
                    stack[top++] = c;
                } else if (c == ')' && stack[--top] != '(') {
                    return false;
                } else if (c == '}' && stack[--top] != '{') {
                    return false;
                } else if (c == ']' && stack[--top] != '[') {
                    return false;
                }
            }
            return top == 1;
        }
    }




    /**
     * 不优雅，思路和上面的一样，不如数组操作简便
     */
    class Solution2 {

        public boolean isValid(String s) {
            List<Character> list = new ArrayList<>();
            Boolean flag = true;
            for (int i = 0; i < s.length(); i++) {
                char charAt = s.charAt(i);
                if (charAt == '(' || charAt == '[' || charAt == '{') {
                    list.add(charAt);
                } else {
                    if (list.size() > 0) {
                        if (charAt == ')') {
                            if (list.get(list.size() - 1) == '(') {
                                list.remove(list.size() - 1);
                            } else {
                                flag = false;
                                break;
                            }
                        } else if (charAt == ']') {
                            if (list.get(list.size() - 1) == '[') {
                                list.remove(list.size() - 1);
                            } else {
                                flag = false;
                                break;
                            }
                        } else if (charAt == '}') {
                            if (list.get(list.size() - 1) == '{') {
                                list.remove(list.size() - 1);
                            } else {
                                flag = false;
                                break;
                            }
                        }
                    } else {
                        flag = false;
                    }
                }
            }
            if (list.size() != 0) {
                flag = false;
            }
            return flag;
        }
    }



    
    /**
     * 成对关系，用map也正好符合
     */
    class Solution4 {

        private  HashMap<Character,Character> map = new HashMap<>();

        // static
        {
            map.put('(',')');
            map.put('[',']');
            map.put('{','}');
        }

        // HashMap 方式实现
        public boolean isValid(String s) {

            Stack<Character> stack = new Stack();

            int len = s.length();
            for (int i = 0; i < len; i++) {
                char c = s.charAt(i);

                // 如果key包含左边括号
                // [{( )}]
                if (map.containsKey(c)){
                    stack.push(c);
                }else {
                    if (stack.isEmpty()) return false;

                    char  left = stack.pop();
                    if (c != map.get(left)) return false;
                }
            }
            return stack.isEmpty();
        }
    }

}
