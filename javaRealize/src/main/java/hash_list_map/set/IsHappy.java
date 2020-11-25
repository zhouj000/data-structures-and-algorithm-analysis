package hash_list_map.set;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 快乐数
 * 执行用时： 4 ms , 在所有 Java 提交中击败了 7.34% 的用户
 * 内存消耗： 36 MB , 在所有 Java 提交中击败了 32.39% 的用户
 **/
public class IsHappy {

    public boolean isHappy(int n) {
        Set<Integer> set = new HashSet<>();
        int result = n;
        while (result != 1) {
            if (!set.add(result)) {
                return false;
            }
            List<Integer> list = getNumList(result);
            result = 0;
            for (int num : list) {
                result += num * num;
            }
        }
        return true;
    }

    private List<Integer> getNumList(int n) {
        List<Integer> list = new ArrayList<>();
        // 后注：好像直接  n > 0即可
        while (n % 10 != 0 || n / 10 != 0) {
            list.add(n % 10);
            n = n / 10;
        }
        return list;
    }



    // 后注： 计算数字和拆分数字应该合在一起，这样只要一个循环
    class BestSolution {
        public boolean isHappy(int n) {
            HashSet<Integer> nums = new HashSet<>();
            while (n != 1) {
                boolean flag = nums.add(n);
                if (flag) {
                    n = getNext(n);
                } else {
                    return false;
                }
            }
            return true;
        }

        private int getNext(int n) {
            int sum = 0;
            while (n > 9) {
                int temp = n % 10;
                sum += temp * temp;
                n = n / 10;
            }
            sum += n * n;
            return sum;
        }
    }


    // 空间最优：  快慢匹配，多了循环次数，节约了set的空间，明显的时间换空间
    class Solution2 {
        public boolean isHappy(int n) {
            int slowRunner = n;
            int fastRunner = getNext(n);
            while (fastRunner != 1 && slowRunner != fastRunner) {
                slowRunner = getNext(slowRunner);
                fastRunner = getNext(getNext(fastRunner));
            }
            return fastRunner == 1;
        }

        public int getNext(int n) {
            int totalSum = 0;
            while (n > 0) {
                int d = n % 10;
                n = n / 10;
                totalSum += d * d;
            }
            return totalSum;
        }
    }

}
