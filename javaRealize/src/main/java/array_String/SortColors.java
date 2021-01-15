package array_String;


import java.util.Arrays;

/**
 * 数组类算法： 颜色分类
 *
 **/
public class SortColors {

    /**
     * 执行用时： 0 ms , 在所有 Java 提交中击败了 100.00% 的用户
     * 内存消耗： 36.8 MB , 在所有 Java 提交中击败了 94.34% 的用户
     */
    public void sortColors(int[] nums) {
        int index0 = 0;
        int index2 = nums.length - 1;
        int tmp;
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if (num == 0) {
                tmp = nums[index0];
                nums[index0] = num;
                nums[i] = tmp;
                index0++;
            } else if (num == 2 && index2 > i) {
                tmp = nums[index2];
                nums[index2] = num;
                nums[i] = tmp;
                index2--;
                i--;
            }
        }
    }




    //  0 ms
    class BestSolution {
        public void sortColors(int[] nums) {
            Arrays.sort(nums);
        }
    }

    //  36992 kb
    // 后注：我的思路是0放最前面，2放最后面;  这里的思路是0和1放前面，0放最前面
    class Solution2 {
        public void sortColors(int[] nums) {
            int r1 = -1;
            int r2 = -1;
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] < 2) {
                    r2++;
                    swap(nums, i, r2);
                    if (nums[r2] < 1) {
                        r1++;
                        swap(nums, r1, r2);
                    }
                }
            }
        }

        void swap(int[] nums, int i, int j) {
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
    }

    // 37140 kb
    class Solution3 {
        public void sortColors(int[] nums) {
            int zero = -1;          // [0...zero] == 0
            int two = nums.length;  // [two...n-1] == 2
            for (int i = 0; i < two; ) {
                if (nums[i] == 1) {
                    i++;
                } else if (nums[i] == 2) {
                    swap(nums, i, --two);
                } else { // nums[i] == 0
                    //assert nums[i] == 0;
                    swap(nums, ++zero, i++);
                }
            }
        }

        private void swap(int[] nums, int i, int j) {
            int t = nums[i];
            nums[i] = nums[j];
            nums[j] = t;
        }
    }

}
