package array_String.string;

/**
 * 字符串匹配算法：KMP
 *
 **/
public class KMP {

    /**
     * 求出一个字符数组的next数组
     * @param t 字符数组
     * @return next数组
     */
    public static int[] getNextArray(char[] t) {
        int[] next = new int[t.length];
        next[0] = -1;
        int k;
        for (int j = 2; j < t.length; j++) {
            k = next[j - 1];
            while (k != -1) {
                if (t[j - 1] == t[k]) {
                    next[j] = k + 1;
                    break;
                } else {
                    k = next[k];
                }
                next[j] = 0;  //当k==-1而跳出循环时，next[j] = 0，否则next[j]会在break之前被赋值
            }
        }
        return next;
    }

    /**
     * 对主串s和模式串t进行KMP模式匹配
     * @param s 主串
     * @param t 模式串
     * @return 若匹配成功，返回t在s中的位置（第一个相同字符对应的位置），若匹配失败，返回-1
     */
    public static int kmpMatch(String s, String t){
        char[] s_arr = s.toCharArray();
        char[] t_arr = t.toCharArray();
        int[] next = getNextArray(t_arr);
        int i = 0, j = 0;
        while (i < s_arr.length && j < t_arr.length) {
            if (j == -1 || s_arr[i] == t_arr[j]) {
                i++;
                j++;
            } else {
                j = next[j];
            }
        }
        if (j == t_arr.length) {
            return i - j;
        } else {
            return -1;
        }
    }

    public static void main(String[] args) {
        System.out.println(kmpMatch("abcabaabaabcacb", "abaabcac"));
        System.out.println(kmpMatch2("abcabaabaabcacb", "abaabcac"));
    }

    // 按力扣C++方法改写的
    /**
      A B C D A B D
      0 0 0 0 1 2 0

     ”部分匹配值”就是 ”前缀” 和 ”后缀” 的最长的共有元素的长度。以 ”ABCDABD” 为例
     － ”A”的前缀和后缀都为空集，共有元素的长度为0；
     － ”AB”的前缀为 [A]，后缀为 [B]，共有元素的长度为 0
     － ”ABC”的前缀为 [A, AB]，后缀为 [BC, C]，共有元素的长度 0
     － ”ABCD”的前缀为 [A, AB, ABC]，后缀为 [BCD, CD, D]，共有元素的长度为 0
     － ”ABCDA”的前缀为 [A, AB, ABC, ABCD]，后缀为 [BCDA, CDA, DA, A]，共有元素为 ”A”，长度为 1
     － ”ABCDAB”的前缀为 [A, AB, ABC, ABCD, ABCDA]，后缀为 [BCDAB, CDAB, DAB, AB, B]，共有元素为 ”AB”，长度为 2
     － ”ABCDABD”的前缀为 [A, AB, ABC, ABCD, ABCDA, ABCDAB]，后缀为 [BCDABD, CDABD, DABD, ABD, BD, D]，共有元素的长度为 0

     ”部分匹配”的实质是，有时候，字符串头部和尾部会有重复。
     比如， ”ABCDAB” 之中有两个 ”AB” ，那么它的”部分匹配值”就是2 ( ”AB” 的长度)
     搜索词移动的时候，第一个 ”AB” 向后移动 4 位（字符串长度-部分匹配值 6-2），就可以来到第二个”AB”的位置

     */
    public static int[] getNextArray2(char[] p) {
        int m = p.length, j = 0;
        int[] N = new int[m];   // next 表
        int t = N[0] = -1;  // 模式串指针
        while (j < m - 1) {
            if (0 > t || p[j] == p[t]) { // 匹配
                j++; t++;
                N[j] = t; // 此句可改进为 N[j] = (P[j] != P[t] ? t : N[t]);
            } else { // 失配
                t = N[t];
            }
        }
        return N;
    }

    public static int kmpMatch2(String s, String p) {
        char[] s_arr = s.toCharArray();
        char[] p_arr = p.toCharArray();
        int[] next = getNextArray2(p_arr); // 构造 next 表
        int m = s.length(), i = 0; // 文本串指针
        int n = p.length(), j = 0; //模式串指针
        while (j < n && i < m) { // 自左向右逐个比对字符
            if (0 > j || s_arr[i] == p_arr[j]) { // 若匹配，或 P 已移除最左侧
                i++; j++;   // 则转到下一字符
            } else {
                j = next[j]; // 模式串右移（注意：文本串不用回退）
            }
        }
        return i - j;
    }

}
