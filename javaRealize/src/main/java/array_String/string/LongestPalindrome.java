package array_String.string;

/**
 * 最长回文子串
 * 问题：  1. 自己的方法就是考虑反转匹配的想法，但是走测试流程会因为长字符串超时
 *         2.
 **/
public class LongestPalindrome {

    public String longestPalindrome(String s) {
        if (s == null) {
            return null;
        }
        char[] sa = s.toCharArray();
        String result = "";
        for (int i = 0; i < sa.length; i++) {
            for (int j = i + 1; j <= sa.length; j++) {
                String tmp = s.substring(i, j);
                String re = new StringBuilder(tmp).reverse().toString();
                if (re.equals(tmp)) {
                    if (tmp.length() > result.length()) {
                        result = tmp;
                    }
                }
            }
        }
        return result;
    }

    // ================================================================================
    // by web: 中心扩散法
    public String longestPalindrome2(String s) {
        int len = s.length();
        if (len < 2) {
            return s;
        }
        int maxLen = 1;
        String res = s.substring(0, 1);
        // 中心位置枚举到 len - 2 即可
        for (int i = 0; i < len - 1; i++) {
            String oddStr = centerSpread(s, i, i);
            String evenStr = centerSpread(s, i, i + 1);
            String maxLenStr = oddStr.length() > evenStr.length() ? oddStr : evenStr;
            if (maxLenStr.length() > maxLen) {
                maxLen = maxLenStr.length();
                res = maxLenStr;
            }
        }
        return res;
    }

    private String centerSpread(String s, int left, int right) {
        // left = right 的时候，此时回文中心是一个空隙，回文串的长度是奇数
        // right = left + 1 的时候，此时回文中心是任意一个字符，回文串的长度是偶数
        int len = s.length();
        int i = left;
        int j = right;
        while (i >= 0 && j < len) {
            if (s.charAt(i) == s.charAt(j)) {
                i--;
                j++;
            } else {
                break;
            }
        }
        // 这里要小心，跳出 while 循环时，恰好满足 s.charAt(i) != s.charAt(j)，因此不能取 i，不能取 j
        return s.substring(i + 1, j);
    }

    // ================================================================================
    // by web: Manacher 算法

    /**
     1、分隔符是一个字符，种类也只有一个，并且这个字符一定不能是原始字符串中出现过的字符；
     2、加入了分隔符以后，使得“间隙”有了具体的位置，方便后续的讨论，并且新字符串中的任意一个回文子串在原始字符串中的一定能找到唯一的一个回文子串与之对应，因此对新字符串的回文子串的研究就能得到原始字符串的回文子串；
     3、新字符串的回文子串的长度一定是奇数；
     4、新字符串的回文子串一定以分隔符作为两边的边界，因此分隔符起到“哨兵”的作用。
     */
    public String longestPalindrome3(String s) {
        int len = s.length();
        if (len < 2) {
            return s;
        }
        String str = addBoundaries(s, '#');
        int sLen = 2 * len + 1;
        int maxLen = 1;

        int start = 0;
        for (int i = 0; i < sLen; i++) {
            int curLen = centerSpread(str, i);
            if (curLen > maxLen) {
                maxLen = curLen;
                start = (i - maxLen) / 2;
            }
        }
        return s.substring(start, start + maxLen);
    }

    private int centerSpread(String s, int center) {
        int len = s.length();
        int i = center - 1;
        int j = center + 1;
        int step = 0;
        while (i >= 0 && j < len && s.charAt(i) == s.charAt(j)) {
            i--;
            j++;
            step++;
        }
        return step;
    }

    /**
     * 创建预处理字符串
     *
     * @param s      原始字符串
     * @param divide 分隔字符
     * @return 使用分隔字符处理以后得到的字符串
     */
    private String addBoundaries(String s, char divide) {
        int len = s.length();
        if (len == 0) {
            return "";
        }
        if (s.indexOf(divide) != -1) {
            throw new IllegalArgumentException("参数错误，您传递的分割字符，在输入字符串中存在！");
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            stringBuilder.append(divide);
            stringBuilder.append(s.charAt(i));
        }
        stringBuilder.append(divide);
        return stringBuilder.toString();
    }

    // 在3的基础上，充分利用新字符串的回文性质，计算辅助数组 p
    // 比较复杂，没具体看，详见  https://www.cxyxiaowu.com/2869.html
    public String longestPalindrome4(String s) {
        // 特判
        int len = s.length();
        if (len < 2) {
            return s;
        }

        // 得到预处理字符串
        String str = addBoundaries(s, '#');
        // 新字符串的长度
        int sLen = 2 * len + 1;

        // 数组 p 记录了扫描过的回文子串的信息
        int[] p = new int[sLen];

        // 双指针，它们是一一对应的，须同时更新
        int maxRight = 0;
        int center = 0;

        // 当前遍历的中心最大扩散步数，其值等于原始字符串的最长回文子串的长度
        int maxLen = 1;
        // 原始字符串的最长回文子串的起始位置，与 maxLen 必须同时更新
        int start = 0;

        for (int i = 0; i < sLen; i++) {
            if (i < maxRight) {
                int mirror = 2 * center - i;
                // 这一行代码是 Manacher 算法的关键所在，要结合图形来理解
                p[i] = Math.min(maxRight - i, p[mirror]);
            }

            // 下一次尝试扩散的左右起点，能扩散的步数直接加到 p[i] 中
            int left = i - (1 + p[i]);
            int right = i + (1 + p[i]);

            // left >= 0 && right < sLen 保证不越界
            // str.charAt(left) == str.charAt(right) 表示可以扩散 1 次
            while (left >= 0 && right < sLen && str.charAt(left) == str.charAt(right)) {
                p[i]++;
                left--;
                right++;

            }
            // 根据 maxRight 的定义，它是遍历过的 i 的 i + p[i] 的最大者
            // 如果 maxRight 的值越大，进入上面 i < maxRight 的判断的可能性就越大，这样就可以重复利用之前判断过的回文信息了
            if (i + p[i] > maxRight) {
                // maxRight 和 center 需要同时更新
                maxRight = i + p[i];
                center = i;
            }
            if (p[i] > maxLen) {
                // 记录最长回文子串的长度和相应它在原始字符串中的起点
                maxLen = p[i];
                start = (i - maxLen) / 2;
            }
        }
        return s.substring(start, start + maxLen);
    }




    // 最佳，递归方式，也是用中心算法
    class BestSolution {
        private int offset = 0, end = 0, count = 0;

        public String longestPalindrome(String s) {
            if (s == null || s.length() < 2) {
                return s;
            }
            find(s.toCharArray(), 0);
            return s.substring(offset, end + 1);
        }

        private void find(char[] ch, int index) {
            if (index > ch.length - 1 || ((ch.length - 1 - index) << 1) + 1 < count) {
                return;
            }
            int curStart = index;
            int cueEnd = index;
            // 后注： 解决间隙情况
            while (cueEnd + 1 < ch.length && ch[cueEnd + 1] == ch[index]) {
                cueEnd++;
            }
            // 后注：下次递归时，直接跳过重复元素的地方，判断之后的字符即可
            index = cueEnd;
            // 后注： 对称情况
            while (curStart - 1 >= 0 && cueEnd + 1 < ch.length && ch[curStart - 1] == ch[cueEnd + 1]) {
                cueEnd++;
                curStart--;
            }
            if (cueEnd - curStart > count) {
                offset = curStart;
                end = cueEnd;
                count = cueEnd - curStart;
            }
            find(ch, index + 1);
        }
    }





}
