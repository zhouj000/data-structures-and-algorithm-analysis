package array_String;

/**
 * 反转字符串中的单词 III
 *      问题： 1. 效率与空间都很差，只击败5%
 *
 *
 **/
public class ReverseWords {

    public String reverseWords(String s) {
        String res = "";
        char[] array = s.toCharArray();
        char[] words = new char[array.length];
        int len = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == ' ') {
                for (int j = len - 1; j >= 0; j--) {
                    res += words[j];
                }
                res += ' ';
                len = 0;
                continue;
            }
            words[len++] = array[i];
            if (i == array.length - 1) {
                for (int j = len - 1; j >= 0; j--) {
                    res += words[j];
                }
            }
        }
        return res;
    }




    class BestSolution {
        public String reverseWords(String s) {
            if (s.length() == 0) {
                return "";
            }
            s = s.trim();
            char[] cs = s.toCharArray();
            int low = 0;
            int high = 0;

            while (high < cs.length) {
                while (high < cs.length && cs[high] != ' ') {
                    high++;
                }
                if (high > cs.length) {
                    break;
                }

                int highIndex = high - 1;
                high++;
                // 后注： 交换位置
                while (low < highIndex) {
                    char c = cs[low];
                    cs[low] = cs[highIndex];
                    cs[highIndex] = c;
                    low++;
                    highIndex--;
                }
                // 后注： 这里是不是直接low == high就行了?
                while (low < cs.length && cs[low] != ' ') {
                    low++;
                }
                low++;
            }
            return String.copyValueOf(cs);
        }
    }

    // 后注：  切割后反转，都是用了java自带方法，实战中应该这么做
    class Solution2 {
        public String reverseWords(String s) {
            if (s == null || s == "") {
                return "";
            }
            String[] str = s.split(" ");
            StringBuilder string = new StringBuilder();
            for (int i = 0; i < str.length; i++) {
                string.append(reverse(str[i])).append(" ");
            }
            return string.toString().trim();
        }

        private String reverse(String s) {
            StringBuilder string = new StringBuilder(s);
            return string.reverse().toString();
        }
    }

}
