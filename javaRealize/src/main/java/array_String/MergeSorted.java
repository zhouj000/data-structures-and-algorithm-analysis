package array_String;

/**
 * 合并两个有序数组
 **/
public class MergeSorted {

    /**
     * 执行用时： 0 ms , 在所有 Java 提交中击败了 100.00% 的用户
     * 内存消耗： 38.5 MB , 在所有 Java 提交中击败了 58.35% 的用户
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int index1 = m - 1, index2 = n - 1, last = m + n - 1;
        while (index1 >= 0 && index2 >= 0) {
            if (nums2[index2] > nums1[index1]) {
                nums1[last--] = nums2[index2--];
            } else {
                nums1[last--] = nums1[index1--];
            }
        }
        if (index2 >= 0) {  // 后注：这个if判断不需要，直接用while例判断即可
            while (index2 >= 0) {
                nums1[last--] = nums2[index2--];
            }
        }
    }





    // 0 ms
    class BesrSolution {
        public void merge(int[] nums1, int m, int[] nums2, int n) {
            int[] result = new int[m + n];
            int p1 = 0, p2 = 0;
            for (int i = 0; i < m + n; i++) {
                if (p1 < m) {
                    if (p2 < n) {
                        if (nums1[p1] < nums2[p2]) {
                            result[i] = nums1[p1];
                            p1++;
                        } else {
                            result[i] = nums2[p2];
                            p2++;
                        }
                    } else {
                        result[i] = nums1[p1];
                        p1++;
                    }
                } else {
                    result[i] = nums2[p2];
                    p2++;
                }
            }
            System.arraycopy(result, 0, nums1, 0, m + n);
        }
    }


    //  38476 kb
    // 后注：和我一模一样，就是判断用三元运算代替了，而且把index2的情况一起算进去了，我写的优化一下就是这个
    class Solution2 {
        public void merge(int[] nums1, int m, int[] nums2, int n) {
            int index = m + n - 1;
            int mIndex = m - 1;
            int nIndex = n - 1;
            while (nIndex >= 0) {
                nums1[index--] = mIndex >= 0 && nums1[mIndex] > nums2[nIndex] ? nums1[mIndex--] : nums2[nIndex--];
            }
        }
    }

}
