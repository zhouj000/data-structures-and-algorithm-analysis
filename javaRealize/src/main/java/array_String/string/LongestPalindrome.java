package array_String.string;

/**
 * 最长回文子串
 *
 **/
public class LongestPalindrome {

    public String longestPalindrome(String s) {
        if (s == null) {
            return null;
        }
        String re = "";
        char[] sa = s.toCharArray();
        for (int i = sa.length - 1; i >= 0 ; i--) {
            re += sa[i];
        }
        String result = "";
        String tmp;
        for (int i = 0; i < sa.length; i++) {
            for (int j = i + 1; j <= sa.length; j++) {
                tmp = s.substring(i, j);
                if (re.contains(tmp)) {
                    if (tmp.length() > result.length()) {
                        result = tmp;
                    }
                } else {
                    break;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        LongestPalindrome t = new LongestPalindrome();
        System.out.println(t.longestPalindrome("a"));
    }

}
