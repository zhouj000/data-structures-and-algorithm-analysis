package hash_list_map.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 同构字符串
 * 执行用时： 137 ms , 在所有 Java 提交中击败了 5.02% 的用户
 * 内存消耗： 41.3 MB , 在所有 Java 提交中击败了 5.03% 的用户
 **/
public class IsIsomorphic {

    public boolean isIsomorphic(String s, String t) {
        char[] sArray = s.toCharArray();
        char[] tArray = t.toCharArray();
        if (sArray.length != tArray.length) {
            return false;
        }
        // 后注：只要保存个数即可，不用保存list来判断每个位置
        Map<Character, List<Integer>> sMap = new HashMap<>();
        Map<Character, List<Integer>> tMap = new HashMap<>();
        for (int i = 0; i < sArray.length; i++) {
            List<Integer> sList = sMap.get(sArray[i]);
            List<Integer> tList = tMap.get(tArray[i]);
            if (sList == null) {
                sList = new ArrayList<>();
                sMap.put(sArray[i], sList);
            }
            if (tList == null) {
                tList = new ArrayList<>();
                tMap.put(tArray[i], tList);
            }
            if (!check(sList, tList)) {
                return false;
            }
            sList.add(i);
            tList.add(i);
        }
        return true;
    }

    private boolean check(List<Integer> l1, List<Integer> l2) {
        if (l1.size() != l2.size()) {
            return false;
        }
        for (int i = 0; i < l1.size(); i++) {
            if (!l1.get(i).equals(l2.get(i))) {
                return false;
            }
        }
        return true;
    }




    class BestSolution {
        public boolean isIsomorphic(String s, String t) {
            if (s.length() != t.length()) {
                return false;
            }
            int len = s.length();
            int posS[] = new int[128];
            int posT[] = new int[128];
            char[] charS = s.toCharArray();
            char[] charT = t.toCharArray();

            for (int i = 0; i < len; i++) {
                if (posS[charS[i]] == 0) {
                    posS[charS[i]] = i + 1;
                }
                if (posT[charT[i]] == 0) {
                    posT[charT[i]] = i + 1;
                }
                if (posS[charS[i]] != posT[charT[i]]) {
                    return false;
                }
            }
            return true;
        }
    }

    class Solution2 {
        public boolean isIsomorphic(String s, String t) {
            if (s == null || t == null || s.length() != t.length()) {
                return false;
            }
            int[] snums = new int[128];
            int[] tnums = new int[128];

            char[] schar = s.toCharArray();
            char[] tchar = t.toCharArray();

            for (int i = 0; i < s.length(); i++) {
                char a = schar[i];
                char b = tchar[i];
                if (snums[a] == 0 && tnums[b] == 0) {
                    snums[a] = b;
                    tnums[b] = a;
                } else {
                    if (snums[a] !=b || tnums[b] != a) {
                        return false;
                    }
                }
            }
            return true;
        }
    }

    // 空间最优
    class Solution3 {
        public boolean isIsomorphic(String s, String t) {
            if (s.length() <= 1) {
                return true;
            }
            char[] s2t = new char[256], t2s = new char[256];
            for (int i = 0; i < s.length(); ++i) {
                char c1 = s.charAt(i), c2 = t.charAt(i);
                if (s2t[c1] == 0 && t2s[c2] == 0) {
                    s2t[c1] = c2;
                    t2s[c2] = c1;
                } else if (s2t[c1] != 0 && t2s[c2] != 0) {
                    if (s2t[c1] != c2 || t2s[c2] != c1) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
            return true;
        }
    }
}
