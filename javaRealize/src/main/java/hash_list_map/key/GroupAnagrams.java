package hash_list_map.key;

import java.util.*;

/**
 * 字母异位词分组
 * 执行用时： 8 ms , 在所有 Java 提交中击败了 77.02% 的用户
 * 内存消耗： 41.4 MB , 在所有 Java 提交中击败了 83.23% 的用户
 **/
public class GroupAnagrams {

    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> listMap = new HashMap<>();
        for (String str : strs) {
            String key = sortStr(str);
            List<String> list = listMap.get(key);
            if (list == null) {
                list = new ArrayList<>();
                listMap.put(key, list);
            }
            list.add(str);
        }
        return new ArrayList<>(listMap.values());
    }

    private String sortStr(String str) {
        char[] strArr = str.toCharArray();
        Arrays.sort(strArr);
        return new String(strArr);
    }



    // 后注：使用质数获得乘积，hash算法的方式避免重复，  如果是非常长字符串，有long越界风险，例如全是z
    class BestSolution {
        public List<List<String>> groupAnagrams(String[] strs) {
            // enumber the first 26 prime number
            int[] primes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101};
            Map<Long, List<String>> groups = new HashMap<>();
            for (int i = 0; i < strs.length; ++i) {
                long key = 1;
                for (int j = 0; j < strs[i].length(); ++j) {
                    key *= primes[strs[i].charAt(j) - 'a'];
                }

                if (groups.containsKey(key)) {
                    groups.get(key).add(strs[i]);
                } else {
                    List<String> temp = new ArrayList<>();
                    temp.add(strs[i]);
                    groups.put(key, temp);
                }
            }
            return new ArrayList<>(groups.values());
        }
    }

    // 后注：也是用计数法，个人觉得比上面的时间最佳好
    class Solution2 {
        public List<List<String>> groupAnagrams(String[] strs) {
            List<List<String>> res = new ArrayList<>();
            if (strs == null || strs.length == 0) {
                return res;
            }
            Map<String, List<String>> map = new HashMap<>();
            for (String str : strs) {
                char[] arr = new char[26];
                for (char ch : str.toCharArray()) {
                    arr[ch - 'a']++;
                }
                String s = String.valueOf(arr);
                if (!map.containsKey(s)) {
                    map.put(s, new ArrayList<>());
                }
                map.get(s).add(str);
            }
            return new ArrayList<>(map.values());
        }
    }

    class Solution3 {
        public List<List<String>> groupAnagrams(String[] strs) {
            if (strs.length == 0) {
                return new ArrayList();
            }
            Map<String, List> ans = new HashMap<String, List>();
            for (String s : strs) {
                char[] ca = s.toCharArray();
                Arrays.sort(ca);
                String key = String.valueOf(ca);
                if (!ans.containsKey(key)) {
                    ans.put(key, new ArrayList());
                }
                ans.get(key).add(s);
            }
            return new ArrayList(ans.values());
        }
    }

    // 空间最优
    class Solution4 {
        public List<List<String>> groupAnagrams(String[] strs) {
            Map<String, List<String>> map = new HashMap<>();
            for (int i = 0; i < strs.length; i++) {
                String str = strs[i];
                char[] cs = str.toCharArray();
                Arrays.sort(cs);
                String key = String.valueOf(cs);
                List<String> list = map.get(key);
                if (list != null) {
                    list.add(str);
                } else {
                    map.put(key, new ArrayList<>(Arrays.asList(str)));
                }
            }
            List<List<String>> res = new ArrayList<>(map.size());
            for (List<String> value : map.values()) {
                res.add(value);
            }
            return res;
        }
    }
}
