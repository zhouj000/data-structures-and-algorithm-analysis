package array_String.string;

/**
 * 最长公共前缀
 *  问题： 1. 其实只要一次性判断就可以，用第一个数组元素作为标准，截取字符串得到公共前缀
 *
 **/
public class LongestCommonPrefix {

    // 转为二维数组，然后比较列来判断
    public String longestCommonPrefix(String[] strs) {
        int length = strs.length;
        if (length == 0) {
            return "";
        }
        char[][] common = new char[length][];
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < length; i++) {
            String str = strs[i];
            common[i] = str.toCharArray();
            if (min > common[i].length) {
                min = common[i].length;
            }
        }
        StringBuilder prefix = new StringBuilder();
        for (int i = 0; i < min; i++) {
            char letter = common[0][i];
            boolean flag = false;
            for (int j = 1; j < length; j++) {
                if (letter != common[j][i]) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                break;
            }
            prefix.append(letter);
        }
        return prefix.toString();
    }


    class BestSolution {
        public String longestCommonPrefix(String[] strs) {
            if (strs == null || strs.length == 0) {
                return "";
            }
            for (int i = 1; i < strs.length; i++) {
                // 单次求取每个元素的公共前缀，将第一个元素作为参照，循环将第一个元素长度从后缩短一个判断
                while (strs[i].indexOf(strs[0]) != 0) {
                    strs[0] = strs[0].substring(0, strs[0].length() - 1);
                    if (strs[0].isEmpty()) {
                        return "";
                    }
                }
            }
            return strs[0];
        }
    }

    class Solution2 {
        public String longestCommonPrefix(String[] strs) {
            if (strs.length == 0) {
                return "";
            }
            if (strs.length == 1) {
                return strs[0];
            }
            String s = strs[0];
            for (int i = 1; i < strs.length; i++) {
                int j = 0;
                String checkStr = strs[i];

                while (j < s.length() && j < checkStr.length()) {
                    if (checkStr.charAt(j) != s.charAt(j)) {
                        break;
                    }
                    j++;
                }
                s = s.substring(0, j);
                if (s.equals("")) {
                    return s;
                }
            }
            return s;
        }
    }

}
