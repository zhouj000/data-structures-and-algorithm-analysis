package array_String;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * 反转字符串中的元音字母
 **/
public class ReverseVowels {

    /**
     * 执行用时： 4 ms , 在所有 Java 提交中击败了 63.35% 的用户
     * 内存消耗： 40.9 MB , 在所有 Java 提交中击败了 5.05% 的用户
     */
    public String reverseVowels(String s) {
        if (s == null) {
            return s;
        }
        char[] sArray = s.toCharArray();
        List<String> yl = new ArrayList<>();
        List<Integer> il = new ArrayList<>();
        for (int i = 0; i < sArray.length; i++) {
            char c = sArray[i];
            if (c - 97 == 0 || c - 101 == 0 || c - 105 == 0 || c - 111 == 0 || c - 117 == 0 ||
                c - 65 == 0 || c - 69 == 0 || c - 73 == 0 || c - 79 == 0 || c - 85 == 0) {
                yl.add(String.valueOf(c));
                il.add(i);
            }
        }
        for (int j = il.size() - 1, i = 0; j >= 0; j--, i++) {
            int index = il.get(j);
            sArray[index] = yl.get(i).charAt(0);
        }
        return String.valueOf(sArray);
    }




    //  2 ms
    class BestSolution {
        public String reverseVowels(String s) {
            if (s.length() == 1 || "".equals(s)) {
                return s;
            }
            char[] temp = s.toCharArray();
            char tem;
            int length = s.length();
            int a = 0, b = length - 1;
            while (true) {
                while (a < length && !judge(temp[a])) {
                    a++;
                }
                while (b >= 0 && !judge(temp[b])) {
                    b--;
                }
                if (a > b) {
                    break;
                }
                tem = temp[b];
                temp[b] = temp[a];
                temp[a] = tem;
                a++;
                b--;
            }
            return String.valueOf(temp);
        }

        public boolean judge(char a) {
            if (a == 'a' || a == 'e' || a == 'i' || a == 'o' || a == 'u'
                    || a == 'A' || a == 'E' || a == 'I' || a == 'O' || a == 'U') {
                return true;
            }
            return false;
        }
    }


    //  38652 kb
    class Solution2 {
        private final HashSet<Character> vowels = new HashSet<>(Arrays.asList('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U'));

        public String reverseVowels(String s) {
            if (s == null) {
                return null;
            }
            // 头指针赋0，尾指针为最后一个
            int i = 0, j = s.length() - 1;
            // 初始化结果数组
            char[] result = new char[s.length()];
            // 此处必须等于，否则怎有一位无法插入
            while (i <= j) {
                // 获取两个指针所指的字符元素
                char ci = s.charAt(i);
                char cj = s.charAt(j);
                // 若i不是元音字符，则将ci加入结果字符数组，然后前指针后移
                if (!vowels.contains(ci)) {
                    result[i++] = ci; //i++先使用，后赋值。
                }
                // 若j不是元音字符，则将cj加入结果字符数组，后指针前移
                else if (!vowels.contains(cj)) {
                    result[j--] = cj;
                } else {
                    // 若两个都为元音字符，加入结果数组，且前移后移
                    result[i++] = cj;
                    result[j--] = ci;
                }
            }
            return new String(result);
        }
    }
}
