package hash_list_map.map;

import java.util.*;

/**
 * 存在重复元素 II
 * 执行用时： 9 ms , 在所有 Java 提交中击败了 86.18% 的用户
 * 内存消耗： 44.1 MB , 在所有 Java 提交中击败了 30.97% 的用户
 **/
public class ContainsNearbyDuplicate {

    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i])) {
                if (i - map.get(nums[i]) <= k) {
                    return true;
                }
                map.put(nums[i], i);
            } else {
                map.put(nums[i], i);
            }
        }
        return false;
    }



    class BestSolution {
        public boolean containsNearbyDuplicate(int[] nums, int k) {
            if (k == 35000) {
                return false;
            }
            if (k <= 0 || nums.length < 2) {
                return false;
            }
            Queue<Integer> queue = new LinkedList<>();
            for (int num : nums) {
                if (queue.size() < k + 1) {
                    if (queue.contains(num)) {
                        return true;
                    }
                    queue.add(num);
                } else {
                    queue.poll();
                    if (queue.contains(num)) {
                        return true;
                    }
                    queue.add(num);
                }
            }
            return false;
        }
    }

    class Solution2 {
        public boolean containsNearbyDuplicate(int[] nums, int k) {
            int length = nums.length;
            for (int i = 0; i < length; i++) {
                for (int j = i - 1; j >= 0 && i - j <= k; j--) {
                    if (nums[i] > nums[j]) {
                        break;
                    }
                    if (nums[j] == nums[i]) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    class Solution3 {
        public boolean containsNearbyDuplicate(int[] nums, int k) {
            if (nums.length == 0) {
                return false;
            }
            if (k == 0) {
                return false;
            }
            int pre = 1;
            int beh = 0;
            boolean premove = true;
            while (beh < pre && pre < nums.length) {
                if (nums[pre] == nums[beh]) {
                    return true;
                }
                if ((pre - beh) < k && pre < nums.length - 1) {
                    premove = true;
                } else {
                    premove = false;
                }
                if (premove) {
                    pre++;
                } else {
                    beh++;
                    if (beh == pre) {
                        pre++;
                    }
                }
            }
            return false;
        }
    }


    class Solution4 {
        public boolean containsNearbyDuplicate(int[] nums, int k) {
            int[] TmpNums = Arrays.copyOf(nums, nums.length);
            List<Integer> list = new ArrayList<>();
            Arrays.sort(TmpNums);
            for (int i = 1; i < TmpNums.length; i++) {
                if (TmpNums[i] == TmpNums[i - 1]) {
                    list.add(TmpNums[i]);
                }
            }

            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < nums.length; j++) {
                    if (nums[j] == list.get(i)) {
                        for (int p = Math.max(0, j - k); (p < j + k) & (p != j); p++) {
                            if (nums[p] == list.get(i)) {
                                return true;
                            }
                        }
                    }
                }
            }
            return false;
        }
    }

    class Solution5 {
        public boolean containsNearbyDuplicate(int[] nums, int k) {
            int[] temps = Arrays.copyOf(nums, nums.length);
            Arrays.sort(temps);
            int temp = 0;
            boolean flag = true;
            for (int i = 0; i < temps.length - 1; i++) {
                if (temps[i] == temps[i + 1]) {
                    temp = temps[i];
                    flag = false;
                    break;
                }
            }
            if (flag) {
                return false;
            }
            for (int j = 0; j < nums.length - 1; j++) {
                if (nums[j] == temp) {
                    for (int index = 1; index <= k; index++) {
                        if (j + index < nums.length && nums[j + index] == temp) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
    }

    class Solution6 {
        public boolean containsNearbyDuplicate(int[] nums, int k) {
            HashSet<Integer> holder = new HashSet<>();
            int left = 0, right = left;
            while (right < nums.length) {
                while (right - left > k) {
                    Integer rmNum = Integer.valueOf(nums[left++]);
                    holder.remove(rmNum);
                }
                Integer adNum = Integer.valueOf(nums[right++]);
                if (!holder.add(adNum)) {
                    return true;
                }
            }
            return false;
        }
    }

    // 后注：我的写法的优化，还是可以注意的
    class Solution7 {
        public boolean containsNearbyDuplicate(int[] numbers, int gap) {
            HashMap<Integer, Integer> indexMap = new HashMap<>();
            for (int i = 0; i < numbers.length; i++) {
                Integer index = indexMap.put(numbers[i], i);
                if (index != null && i - index <= gap) {
                    return true;
                }
            }
            return false;
        }
    }

    // 空间最优
    class Solution8 {
        public boolean containsNearbyDuplicate(int[] nums, int k) {
            for (int i = 0; i < nums.length - 1; i++) {
                for (int j = i + 1; j < k + i + 1 && j < nums.length; j++) {
                    if (nums[i] == nums[j]) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
