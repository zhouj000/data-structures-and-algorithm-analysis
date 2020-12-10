package hash_list_map;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * 无重复字符的最长子串
 * 执行用时： 10 ms , 在所有 Java 提交中击败了 36.17% 的用户
 * 内存消耗： 38.6 MB , 在所有 Java 提交中击败了 77.30% 的用户
 **/
public class LengthOfLongestSubstring {

    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        Queue<Character> queue = new LinkedList<>();
        int count = 0;
        for (Character c : s.toCharArray()) {
            if (queue.contains(c)) {
                while (!c.equals(queue.poll())) {
                }
            }
            queue.offer(c);
            if (queue.size() > count) {
                count = queue.size();
            }
        }
        return count;
    }



    // ?
    static class BestSolution {
        public int lengthOfLongestSubstring(String s) {
            char[] ss = s.toCharArray();
            int[] map = new int[256];
            int res = 0;
            for (int i = 0, j = 0; i < ss.length && j < ss.length; ) {
                while (j < ss.length && map[ss[j]] == 0) {
                    ++map[ss[j++]];
                }
                res = res > j - i ? res : j - i;
                while (j < ss.length && map[ss[j]] > 0) {
                    --map[ss[i++]];
                }
            }
            return res;
        }
    }

    // 1ms
    class BestSolution2 {
        public int lengthOfLongestSubstring(String s) {
            //记录字符上一次出现的位置
            int[] last = new int[128];
            for (int i = 0; i < 128; i++) {
                last[i] = -1;
            }
            int n = s.length();
            int start = 0;
            int res = 0;
            for (int i = 0; i < n; i++) {
                int index = s.charAt(i);
                start = Math.max(start, last[index] + 1);
                res = Math.max(res, i + 1 - start);
                last[index] = i;
            }
            return res;
        }
    }

    class Solution2 {
        public int lengthOfLongestSubstring(String s) {
            int maxNotDup = 0;
            int[] index = new int[128];
            int lastNotDup = 0;
            for (int i = 0; i < s.length(); i++) {
                lastNotDup = Math.max(index[s.charAt(i)], lastNotDup);
                maxNotDup = Math.max(maxNotDup, i - lastNotDup + 1);
                index[s.charAt(i)] = i + 1;
            }
            return maxNotDup;
        }
    }

    class Solution3 {
        public int lengthOfLongestSubstring(String s) {
            int[] table = new int[128];
            int left = 0;
            int right = 0;
            int max = 0;
            char[] chars = s.toCharArray();
            while (right < chars.length) {
                int index = chars[right++];
                table[index]++;
                while (table[index] > 1) {
                    table[chars[left++]]--;
                }
                max = Math.max(max, right - left);
            }
            return max;
        }
    }

    // 3ms
    class Solution33 {
        public int lengthOfLongestSubstring(String s) {
            int pos = 0;     // 看现在的字符在窗口的位置
            int left = 0;   // 滑动窗口的左侧
            int max = 0;     // 代表最大值
            int len = s.length();
            int l = 0;       // 表示长度
            for (int i = 0; i < len; i++) {
                pos = s.indexOf(s.charAt(i), left);
                if (pos < i) {
                    left = pos + 1;
                    if (l > max) {
                        max = l;
                    }
                    if (max >= len - pos - 1) {
                        return max;
                    }
                    l = i - pos - 1;
                }
                l++;
            }
            return l;
        }
    }

    class Solution4 {
        public int lengthOfLongestSubstring(String s) {
            int ans = 0;
            int size = s.length();
            Map<Character, Integer> map = new HashMap<>();
            for (int end = 0, start = 0; end < size; end++) {
                // 提取当前字符
                char a = s.charAt(end);
                // 更新start到重复字符的下个位置，从0开始，前面字符不会遗漏
                if (map.containsKey(a)) {
                    start = Math.max(map.get(a), start);
                }
                ans = Math.max(ans, end - start + 1);
                map.put(s.charAt(end), end + 1);
            }
            return ans;
        }
    }

    // 4ms
    class Solution5 {
        public int lengthOfLongestSubstring(String s) {
            if (s == null || s.length() < 1) {
                return 0;
            }
            int[] chs = new int[128];
            int res = 0, left = 0;
            for (int i = 0; i < s.length(); i++) {
                char ch = s.charAt(i);
                if (chs[ch] == 0) {
                    chs[ch]++;
                    res = Math.max(res, i - left + 1);
                } else {
                    chs[ch]++;
                    while (chs[ch] > 1) {
                        chs[s.charAt(left)]--;
                        left++;
                    }
                }
            }
            return res;
        }
    }

    // 5ms
    class Solution6 {
        public int lengthOfLongestSubstring(String s) {
            if (s.length() == 0) {
                return 0;
            }
            int p0 = 0, p1 = 0;
            int length = 0;
            char[] ch = s.toCharArray();
            while (p1 < ch.length - 1) {
                p1++;
                for (int i = p0; i < p1; i++) {
                    if (ch[p1] == ch[i]) {
                        p0 = i + 1;
                    }
                }
                length = Math.max(length, p1 - p0);
            }
            return length + 1;
        }
    }

    // 6ms
    class Solution7 {
        public int lengthOfLongestSubstring(String s) {
            int sec = 0;
            int max = 0;
            Map<Character, Integer> map = new HashMap<>();
            for (int i = 0; i < s.length(); i++) {
                Integer put = map.put(s.charAt(i), i);
                if (put != null && sec <= put) {
                    //说明出现重复
                    sec = put + 1;
                }
                max = Math.max(max, i - sec + 1);
            }
            return max;
        }
    }

    // 7ms
    class Solution {
        public int lengthOfLongestSubstring(String s) {
            int start = 0, end = 0, result = 0;
            Map<Character, Integer> hash = new HashMap<>();
            while (end < s.length()) {
                char point = s.charAt(end);
                if (hash.containsKey(point) && hash.get(point) >= start) {
                    start = hash.get(point) + 1;
                }
                hash.put(point, end);
                /*
                    HashMap.put(K,V)方法，将K,V对添加入实例成员hash中，如果有对应的K，则替换V。
                 */
                end++;
                result = Math.max(result, end - start);
            }
            return result;
        }
    }

}
