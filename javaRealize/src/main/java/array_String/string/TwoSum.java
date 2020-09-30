package array_String.string;

import java.util.HashMap;

/**
 * 两数之和 II - 输入有序数组
 *  问题： 主要方案就是减少for循环范围
 *         -> 双指针I，头尾互相集中
 *
 **/
public class TwoSum {

    public int[] twoSum(int[] numbers, int target) {
        int[] res = new int[2];
        for (int i = 0; i < numbers.length; i++) {
            int a = numbers[i];
            if (a > target) {
                break;
            }
            for (int j = i + 1; j < numbers.length; j++) {
                int b = numbers[j];
                if (a + b > target) {
                    break;
                }
                if (a + b == target) {
                    res[0] = i + 1;
                    res[1] = j + 1;
                }
            }
        }
        return res;
    }


    // 后注： 有个0ms的无法查看
    class BestSolution {
        public int[] twoSum(int[] numbers, int target) {
            int left = 0, right = numbers.length - 1;
            while (left <= right) {
                int sum = numbers[left] + numbers[right];
                if (sum > target) {
                    right--;
                } else if (sum < target) {
                    left++;
                } else {
                    return new int[]{++left, ++right};
                }
            }
            return new int[]{};
        }
    }

    class Solution3 {
        public int[] twoSum(int[] numbers, int target) {
            HashMap<Integer, Integer> map = new HashMap<>();
            int[] res = new int[2];
            int length = numbers.length;
            int middle = searchInsert(numbers, target / 2);
            for (int i = 0; i <= middle; i++) {
                for (int j = middle; j < length; j++) {
                    int addition = numbers[i] + numbers[j];
                    if (addition == target && i != j) {
                        res[0] = i + 1;
                        res[1] = j + 1;
                    } else if (addition > target) {
                        break;
                    }
                }
            }
            return res;
        }

        public int searchInsert(int[] nums, int target) {
            int length = nums.length;
            if (length == 0) {
                target = 0;
            }
            int left = 0, right = length - 1, middle = 0;
            while (left <= right) {
                middle = (left + right) / 2;
                int value = nums[middle];
                if (target > value) {
                    left = middle + 1;
                } else if (target < value) {
                    right = middle - 1;
                } else {
                    return middle;
                }
            }
            return left;
        }
    }

}
