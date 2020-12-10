package hash_list_map.map;

import java.util.*;

/**
 * 字符串中的第一个唯一字符
 * 执行用时： 87 ms , 在所有 Java 提交中击败了 5.03% 的用户
 * 内存消耗： 39 MB , 在所有 Java 提交中击败了 74.70% 的用户
 **/
public class FirstUniqChar {

    public int firstUniqChar(String s) {
        char[] sAray = s.toCharArray();
        Map<String, Integer> map = new HashMap<>();
        for (Character c : sAray) {
            Integer count = map.get(String.valueOf(c));
            if (count == null) {
                count = 0;
            } else {
                count ++;
            }
            map.put(String.valueOf(c), count);
        }
        for (int i = 0; i < s.length(); i++) {
            if (map.get(String.valueOf(sAray[i])) == 0) {
                return i;
            }
        }
        return -1;
    }


    class BestSolution {
        public int firstUniqChar(String s) {
            int index = -1;
            for (char ch = 'a'; ch <= 'z'; ch++) {
                int beginIndex = s.indexOf(ch);
                // 从头开始的位置是否等于结束位置，相等说明只有一个，
                if (beginIndex != -1 && beginIndex == s.lastIndexOf(ch)) {
                    // 取小的，越小代表越前。
                    index = (index == -1 || index > beginIndex) ? beginIndex : index;
                }
            }
            return index;
        }
    }

    class Solution2 {
        public int firstUniqChar(String s) {
            // 字符串长度不超过26，按照原先的方式遍历
            if (s.length() <= 20) {
                int[] chars = new int[26];
                for (char c : s.toCharArray()) {
                    chars[c - 'a'] += 1;
                }
                for (int i = 0; i < s.length(); ++i) {
                    if (chars[s.charAt(i) - 'a'] == 1) {
                        return i;
                    }
                }
            }

            // 只遍历26个字母，使用indexOf函数检查字符索引
            int result = -1;
            for (char c = 'a'; c <= 'z'; ++c) {
                int pre = s.indexOf(c);
                // s包含该字符并且只出现一次
                if (pre != -1 && pre == s.lastIndexOf(c)) {
                    // 取最前面的位置
                    result = (result == -1 || result > pre) ? pre : result;
                }
            }
            return result;
        }
    }


    // 空间最优
    class Solution3 {
        public int firstUniqChar(String s) {
            Map<Character, Integer> map = new HashMap<>();
            for (int i = 0; i < s.length(); i++) {
                map.put(s.charAt(i), map.getOrDefault(s.charAt(i), 0) + 1);
            }
            for (int i = 0; i < s.length(); i++) {
                if (map.get(s.charAt(i)) < 2) {
                    return i;
                }
            }
            return -1;
        }
    }
}
