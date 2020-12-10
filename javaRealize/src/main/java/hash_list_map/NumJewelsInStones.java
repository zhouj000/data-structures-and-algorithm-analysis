package hash_list_map;

/**
 * 宝石与石头
 * 执行用时： 1 ms , 在所有 Java 提交中击败了 98.19% 的用户
 * 内存消耗： 37 MB , 在所有 Java 提交中击败了 77.94% 的用户
 **/
public class NumJewelsInStones {

    public int numJewelsInStones(String J, String S) {
        int[] stones = new int[58];
        char[] sary = S.toCharArray();
        for (char s : sary) {
            stones[s - 'A'] += 1;
        }
        char[] jary = J.toCharArray();
        int count = 0;
        for (char j : jary) {
            count += stones[j - 'A'];
        }
        return count;
    }



    class BestSolution {
        public int numJewelsInStones(String J, String S) {
            int jewelsCount = 0;
            int jewelsLength = J.length(), stonesLength = S.length();
            for (int i = 0; i < stonesLength; i++) {
                char stone = S.charAt(i);
                for (int j = 0; j < jewelsLength; j++) {
                    char jewel = J.charAt(j);
                    if (stone == jewel) {
                        jewelsCount++;
                        break;
                    }
                }
            }
            return jewelsCount;
        }
    }

    // 空间最优
    class Solution2 {
        public int numJewelsInStones(String J, String S) {
            if (S == null || S.isEmpty()) {
                return 0;
            }
            if (J == null || J.isEmpty()) {
                return 0;
            }

            byte[] arr = new byte[58];
            int count = 0;
            for (char ch : J.toCharArray()) {
                arr[ch - 65] = 1;
            }
            for (char ch : S.toCharArray()) {
                if(arr[ch -65] == 1) {
                    count++;
                };
            }
            return count;
        }
    }
}
