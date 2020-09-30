package array_String.string;

/**
 * 实现 strStr()
 *  问题： 想复杂了
 **/
public class StrStr {

    public int strStr(String haystack, String needle) {
        if (haystack == null || needle == null || needle.length() == 0) {
            return 0;
        }
        char[] ha = haystack.toCharArray();
        char[] na = needle.toCharArray();
        int[] next = getNext(na);
        int i = 0, j = 0;
        while (i < ha.length && j < na.length) {
            if (j < 0 || ha[i] == na[j]) {
                i++;
                j++;
            } else {
                j = next[j];
            }
        }
        if (j == na.length) {
            return i - j;
        }
        return -1;
    }

    private int[] getNext(char[] na) {
        int len = na.length;
        int[] next = new int[len];
        int i = 0;
        int j = next[0] = -1;
        while (i < len - 1) {
            if (j == -1 || na[i] == na[j]) {
                i++;
                j++;
                next[i] = j;
            } else {
                j = next[j];
            }
        }
        return next;
    }

    public static void main(String[] args) {
        StrStr t = new StrStr();
        t.strStr("", "ll");
    }





    public int bestStrStr(String haystack, String needle) {
        return haystack.indexOf(needle);
    }

    public int strStr2(String haystack, String needle) {
        int slen = haystack.length();
        int nlen = needle.length();
        for (int i = 0; i < slen - nlen + 1; i++) {
            if (haystack.substring(i, i + nlen).equals(needle)) {
                return i;
            }
        }
        return -1;
    }

    public int strStr3(String haystack, String needle) {
        int sourceLen = haystack.length();
        int targetLen = needle.length();

        if (targetLen == 0) {
            return 0;
        }

        if (sourceLen < targetLen) {
            return -1;
        }

        for (int i = 0; i < sourceLen - targetLen + 1; i++) {
            int k = i;
            for (int j = 0; j < targetLen; j++) {
                if (haystack.charAt(k) == needle.charAt(j)) {
                    if (j == targetLen - 1) {
                        return i;
                    }
                    k++;
                } else {
                    break;
                }
            }
        }
        return -1;
    }

}
