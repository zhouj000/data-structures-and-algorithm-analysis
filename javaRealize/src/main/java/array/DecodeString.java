package array;

import java.util.*;
import java.util.regex.Pattern;

/**
 * 小结课程题：  字符串解码
 *      问题：  1.
 *
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
        String s = "3[a]2[b4[F]c]";
        DecodeString t = new DecodeString();
        System.out.println(t.decodeString(s));
    }





}
