package array;

import java.util.*;
import java.util.regex.Pattern;

/**
 * 小结课程题：  字符串解码
 * 问题：  1. 思路上很显然用栈来存储，然后分别处理 [ ] 数字 和 字母
 *         2. 解决数字问题上，可以使用 Character.isDigit 来判断
 *         3. 我没有处理 [ 括号，所以遇到 ] 后，需要循环遍历存储的字符去解析 数字 和 字母
 *         4. 数字 和 字符串 分别用2个栈/队列 来存储， 因为必定是 num[zm]的格式，所以遇到]即可处理2个队列弹出处理，然而如果[前不一定有数字时就不适用
 *         5. c - '0' 是把字符转为数字的一个办法，然后通过 * 10来处理多位数字
 *
 **/
public class DecodeString {

    public Pattern pattern = Pattern.compile("^\\d+$");

    public String decodeString(String s) {
        if (s == null) {
            return null;
        }
        Stack<String> stack = new Stack<>();
        String result = "";

        for (Character c : s.toCharArray()) {
            if (!c.equals(']')) {
                stack.push(c.toString());
            } else {
                String tzm = "";
                while (!stack.isEmpty()) {
                    String zm = stack.pop();
                    if (zm.equals("[")) {
                        break;
                    }
                    tzm = zm + tzm;
                }
                boolean addback = false;
                String times = "";
                while (!stack.isEmpty()) {
                    String zm = stack.pop();
                    if (zm.equals("[")) {
                        addback = true;
                        stack.add("[");
                        break;
                    }
                    if (pattern.matcher(zm).matches()) {
                        times = zm + times;
                    } else {
                        stack.add(zm);
                        break;
                    }
                }
                String bzm = "";
                while (!stack.isEmpty()) {
                    String zm = stack.pop();
                    if (zm.equals("[")) {
                        addback = true;
                        break;
                    }
                    bzm = zm + bzm;
                }
                String res = "";
                if (!"".equals(times)) {
                    for (int i = 0; i < Integer.parseInt(times); i++) {
                        res += tzm;
                    }
                }
                if (addback) {
                    stack.add("[");
                }
                res = bzm + res;
                stack.add(res);
            }
        }
        while (!stack.isEmpty()) {
            result = stack.pop() + result;
        }
        return result;
    }

    public static void main(String[] args) {
//        String s = "3[a2[c]]";
//        String s= "3[a]2[bc]";
//        String s = "2[abc]3[cd]ef";
//        String s = "abc3[cd]xyz";
//        String s = "3[a]2[b4[F]c]";
        String s = "3[z]2[2[y]pq4[2[jk]e1[f]]]ef";
        DecodeString t = new DecodeString();
        System.out.println(t.decodeString(s));
    }



    // 效率最优 1,2,3的算法
    class SolutionBest {

        public String decodeString(String s) {
            Deque<Integer> stackMulti = new ArrayDeque<>();
            Deque<String> stackStr = new ArrayDeque<>();
            int multi = 0;
            StringBuilder res = new StringBuilder();
            for (char c : s.toCharArray()) {
                if (Character.isDigit(c)) {
                    multi = multi * 10 + c - '0';
                } else if (c == '[') {
                    stackMulti.addLast(multi);
                    multi = 0;
                    stackStr.addLast(res.toString());
                    res = new StringBuilder();
                } else if (c == ']') {
                    String temp = res.toString();
                    res = new StringBuilder();
                    if (!stackStr.isEmpty()) {
                        res.append(stackStr.removeLast());
                    }
                    int multiTemp = stackMulti.removeLast();
                    for (int i = 0; i < multiTemp; i++) {
                        res.append(temp);
                    }
                } else {
                    res.append(c);
                }
            }
            return res.toString();
        }

        public String decodeString2(String s) {
            LinkedList<Integer> numStack = new LinkedList<>();
            LinkedList<String> strStack = new LinkedList<>();
            StringBuilder sb = new StringBuilder();
            int num = 0;
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c >= '0' && c <= '9') {
                    num = num * 10 + c - '0';
                } else if (c == '[') {
                    if (num > 0) {
                        numStack.push(num);
                    }
                    num = 0;
                    strStack.push(sb.toString());
                    sb = new StringBuilder();
                } else if ((c <= 'z' && c >= 'a') || (c <= 'Z' && c >= 'A')) {
                    sb.append(c);
                } else {    // 后注： 这里其实就是处理 ] 的情况
                    StringBuilder temp = new StringBuilder().append(strStack.pop());
                    int times = numStack.pop();
                    for (int j = 0; j < times; j++) {
                        temp.append(sb);
                    }
                    sb = temp;
                }
            }
            return sb.toString();
        }

        public String decodeString3(String s) {
            if (s == null) {
                return "";
            }
            Stack<Character> stack = new Stack<>();
            Stack<Integer> num = new Stack<>();
            StringBuffer sb = new StringBuffer();
            Boolean isEnter = false;
            int cc = 0;
            for (int index = 0; index < s.length(); index++) {
                Character ch = new Character(s.charAt(index));
                if (Character.isDigit(ch)) {
                    cc = cc * 10 + ch - '0';
                } else {
                    if (ch.equals('[')) {
                        num.push(cc);
                        cc = 0;
                        isEnter = true;
                    }
                    if (!isEnter) {
                        sb.append(ch);
                    }

                    if (!ch.equals(']') && isEnter) {
                        stack.push(ch);
                    } else if (ch.equals(']')) {
                        StringBuffer tem = new StringBuffer();
                        StringBuffer tt = new StringBuffer();
                        while (!stack.isEmpty() && !stack.peek().equals('[')) {
                            tem.insert(0, stack.pop());
                        }
                        stack.pop();
                        if (!num.isEmpty()) {
                            int count = num.pop();
                            String str = tem.toString();
                            for (int i = 0; i < count; i++) {
                                tt.append(str);
                            }
                        }
                        if (!num.isEmpty()) {
                            for (int j = 0; j < tt.length(); j++) {
                                stack.push(tt.charAt(j));
                            }
                        } else {
                            sb.append(tt);
                            isEnter = false;
                        }
                    }
                }
            }
            return sb.toString();
        }
    }

    // 后注： 这个思路和我类似，  他在循环中只是处理了 NUM[ZM]  这一块内容，前面的ZM在遇到后面的]后再处理，最后把所有ZM组合
    //        先用peek获取，这样不用像我那样还要考虑放第二个 [ 回去，第一个 [ 直接弹出即可，判断后再用pop获取
    class Solution2 {
        public String decodeString(String s) {

            Deque<Character> deque = new LinkedList<>();

            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == ']') {
                    List<Character> list = new ArrayList<>();

                    while (deque.peekLast() != '[') {
                        list.add(deque.pollLast());
                    }
                    deque.pollLast(); // 弹出'['

                    Stack<Character> numStack = new Stack<>();
                    while (deque.peekLast() != null && Character.isDigit(deque.peekLast())) {
                        numStack.add(deque.pollLast());
                    }

                    StringBuilder numBuilder = new StringBuilder();
                    while (!numStack.isEmpty()) {
                        numBuilder.append(numStack.pop());
                    }

                    int num = Integer.parseInt(new String(numBuilder));

                    for (int m = 0; m < num; m++) {
                        for (int n = list.size() - 1; n >= 0; n--) {
                            deque.addLast(list.get(n));
                        }
                    }
                } else {
                    deque.addLast(s.charAt(i));
                }
            }

            StringBuilder builder = new StringBuilder();

            while (!deque.isEmpty()) {
                builder.append(deque.pollFirst());
            }
            return new String(builder);
        }
    }

    // 后注：  深度遍历的思路， 每次递归处理 [] 内的数据
    class Solution3 {
        public String decodeString(String s) {
            return dfs(s, 0)[0]; //深度优先搜索
        }

        private String[] dfs(String s, int i) {
            StringBuilder res = new StringBuilder();
            int multi = 0; //表明[]中字符串翻几倍

            while (i < s.length()) {
                if (s.charAt(i) >= '0' && s.charAt(i) <= '9') {
                    multi = 10 * multi + Integer.valueOf(s.charAt(i) + "");
                } else if (s.charAt(i) == '[') { //开启一个递归
                    String[] tmp = dfs(s, i + 1);
                    i = Integer.parseInt(tmp[0]);
                    while (multi > 0) {
                        res.append(tmp[1]);
                        multi--;
                    }
                } else if (s.charAt(i) == ']') { //结束递归
                    return new String[]{String.valueOf(i), res.toString()}; //返回[当前']'的位置index, 在这个[]中res字符串]
                } else { //当前s.charAt(i)为'a' ~ 'z'之间的字符
                    res.append(s.charAt(i));
                }
                i++;
            }
            return new String[]{res.toString()};
        }
    }

    // 后注： 其实和最优1一样，只不过换了个容器
    class Solution4 {
        public String decodeString(String s) {
            if (s == null || s.length() == 0) {
                return "";
            }

            Stack<Integer> numStack = new Stack<>();
            Stack<String> stringStack = new Stack<>();
            int multi = 0;
            StringBuffer sb = new StringBuffer();
            for (char c : s.toCharArray()) {
                if (c == '[') {
                    numStack.push(multi);
                    multi = 0;
                    stringStack.push(sb.toString());
                    sb = new StringBuffer();
                } else if (c == ']') {
                    StringBuffer tmpString = new StringBuffer();
                    int tmpNum = numStack.pop();
                    for (int i = 0; i < tmpNum; i++) {
                        tmpString.append(sb);
                    }
                    sb = new StringBuffer(stringStack.pop() + tmpString);
                } else if (c >= '0' && c <= '9') {
                    multi = multi * 10 + Integer.valueOf(c + "");
                } else {
                    sb.append(c);
                }
            }

            return sb.toString();
        }
    }

}
