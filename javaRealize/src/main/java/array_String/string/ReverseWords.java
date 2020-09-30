package array_String.string;

import java.util.ArrayList;
import java.util.List;

/**
 * 翻转字符串里的单词
 * 问题：  1. 正序查询，可以用sb的insert方法插到前面，我用了麻烦的list
 *         2. 显然可以反向查询，只要确认好 截取字段的开始和结束即可
 *         3. 总体而言，就是倒着获取非空格字符串，然后拼接，我解决方法复杂了
 *
 **/
public class ReverseWords {

    public String reverseWords(String s) {
        char[] arr = s.toCharArray();
        int start = -1;
        boolean zf = false;
        List<String> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            char c = arr[i];
            if (c == ' ' && !zf) {
                continue;
            } else if (c == ' ' && zf) {
                list.add(s.substring(start, i));
                start = -1;
                zf = false;
                continue;
            }
            zf = true;
            if (start == -1) {
                start = i;
            }
            if (i == arr.length - 1) {
                list.add(s.substring(start, i + 1));
                break;
            }
        }
        String res = "";
        if (list.size() == 0) {
            return res;
        }
        for (int j = list.size() - 1; j >= 0; j--) {
            res = res + " " + list.get(j);
        }
        return res.substring(1);
    }

    public static void main(String[] args) {
        String s = " hello world ";
        ReverseWords t = new ReverseWords();
        System.out.println(t.reverseWords(s));
    }



    // 最优
    public String BestreverseWords(String s) {
        /*
        if(s.length() == null) return "";
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == ' ')
                continue;
            int j = i;
            while(j < s.length() && s.charAt(j) != ' ')
                j++;

            String curr = s.substring(i, j);
            //System.out.println(i + " " + j + " " + curr);
            sb.insert(0, curr + " ");
            i = j;
        }

        if(sb.length() == 0) return "";
        return sb.toString().substring(0, sb.length() - 1);
        */

        if (s == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == ' ') {
                continue;
            }
            int j = i;
            i = s.lastIndexOf(' ', i);
            sb.append(s.substring(i + 1, j + 1)).append(" ");
        }
        if (sb.length() == 0) {
            return "";
        }
        return sb.toString().substring(0, sb.length() - 1);

        /*
        String[] words = s.trim().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for(int i = words.length - 1; i > 0; i--) {
            sb.append(words[i]).append(" ");
        }
        return sb.append(words[0]).toString();
        */
    }




    private int pos;
    private String input;

    public String reverseWords2(String s) {
        if (s == null || s.isEmpty()) {
            return s;
        }

        input = s;
        pos = s.length() - 1;

        StringBuffer buf = new StringBuffer();
        do {
            String word = readWord();
            if (word.isEmpty()) {
                break;
            } else {
                buf.append(word);
                buf.append(' ');
            }
        } while (true);

        if (buf.length() > 0) {
            buf.deleteCharAt(buf.length() - 1);
        }
        return buf.toString();
    }

    private String readWord() {
        while (pos >= 0 && input.charAt(pos) == ' ') {
            pos--;
        } //删掉单词前的空格, pos=-1表示读完字符串
        if (pos < 0) {
            return "";
        }

        int mark = pos;
        while (pos >= 0 && input.charAt(pos) != ' ') {
            pos--;
        }

        if (pos < 0) {
            return input.substring(0, mark + 1);
        } else {
            return input.substring(pos + 1, mark + 1);
        }
    }

    // 后注：split也是一种方法
    public String reverseWords3(String s) {
        if (s == null) {
            return s;
        }
        if (s.trim().length() == 0) {
            return s.trim();
        }
        String[] strings = s.trim().split(" ");
        StringBuilder sb = new StringBuilder();
        for (int i = strings.length - 1; i >= 0; i--) {
            if (!"".equals(strings[i])) {
                sb.append(strings[i] + " ");
            }
        }
        return sb.toString().trim();
    }

}
