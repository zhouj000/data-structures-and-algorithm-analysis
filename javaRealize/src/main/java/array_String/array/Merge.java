package array_String.array;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * 合并区间
 * 问题：   1. 最佳方案思路与我一样，不过他用了 标记数组来记录被合并元素
 *             而我是通过把合并后元素放入LIST
 *
 **/
public class Merge {

    // 这个思路有问题， [1,4][5,6] 就会连在一起
    // 而且空间占用较多
    /*public int[][] merge(int[][] intervals) {
        if (intervals.length == 0) {
            return intervals;
        }
        int max = intervals[intervals.length - 1][1];
        int[] nums = new int[max + 1];
        for (int[] val : intervals) {
            for (int i = val[0]; i <= val[1]; i++) {
                nums[i] = 1;
            }
        }
        List<int[]> list = new ArrayList<>();
        int left = -1;
        for (int i = 0; i <= max; i++) {
            if (i + 1 > max) {
                int[] val = {left, i};
                list.add(val);
                break;
            }
            if (nums[i] == 0) {
                if (left != -1) {
                    int[] val = {left, i - 1};
                    list.add(val);
                    left = -1;
                }
                continue;
            }
            if(left == -1) {
                left = i;
                continue;
            }
        }
        int[][] newVals = new int[list.size()][];
        for (int i = 0; i < list.size(); i++) {
            newVals[i] = list.get(i);
        }
        return newVals;
    }*/

    public int[][] merge(int[][] intervals) {
        if (intervals.length == 0) {
            return intervals;
        }
        List<int[]> list = new ArrayList<>();
        for (int i = 0; i < intervals.length; i++) {
            boolean flag = false;
            int[] left = intervals[i];
            for (int j = i + 1; j < intervals.length; j++) {
                int[] right = intervals[j];
                if (!(left[0] > right[1]) && !(left[1] < right[0])) {
                    int lmin = left[0] < right[0] ? left[0] : right[0];
                    int rmax = left[1] > right[1] ? left[1] : right[1];
                    int[] comb = {lmin, rmax};
                    intervals[j] = comb;
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                list.add(left);
            }
        }
        int[][] newVals = new int[list.size()][];
        for (int k = 0; k < list.size(); k++) {
            newVals[k] = list.get(k);
        }
        return newVals;
    }

    public static void main(String[] args) {
        Merge t = new Merge();
//        int[][] arr = {{1,3}, {2,6}, {8,10}, {15,18}};
//        int[][] arr = {{1,4}, {0,1}};
//        int[][] arr = {{1,4}, {0,0}};
//        int[][] arr = {{1,4}, {0,2}, {3,5}};
        int[][] arr = {{2,3}, {4,5}, {6,7}, {8,9}, {1,10}};
        int[][] arr2 = t.merge(arr);
        for (int[] a : arr2) {
            for (int b : a) {
                System.out.print(b + ",");
            }
            System.out.println();
        }
    }


    class BestSolution {
        public int[][] merge(int[][] intervals) {
            int n = intervals.length;
            int[] marks = new int[n];
            for (int i = 0; i < intervals.length; i++) {
                for (int j = i + 1; j < intervals.length; j++) {
                    if (intervals[i][0] > intervals[j][1] || intervals[i][1] < intervals[j][0]) {
                        continue;
                    } else {
                        intervals[j][0] = Math.min(intervals[i][0], intervals[j][0]);
                        intervals[j][1] = Math.max(intervals[i][1], intervals[j][1]);
                        // 后注：意味着被融合了，后面可以排除
                        marks[i] = 1;
                        n--;
                        break;
                    }
                }
            }
            int[][] res = new int[n][2];
            for (int i = 0, j = 0; i < marks.length; i++) {
                if (marks[i] == 0) {
                    res[j++] = intervals[i];
                }
            }
            return res;
        }
    }

    // 后注：这个用了我第一次注释的尝试，用打标方式判断
    // 当时因为邻居元素会导致一起合并，这里是对元素数字 X 2 来解决这个问题
    // 打标后求每一块连续ture的开始与结束，即合并结果
    // 另外这里用了BitSet，当时用二维数组时间效率和空间效率都不行
    class Solution2 {
        public int[][] merge(int[][] intervals) {
            BitSet bitSet = new BitSet();
            int max = 0;
            for (int[] interval : intervals) {
                int temp = interval[1] * 2 + 1;
                bitSet.set(interval[0] * 2, temp, true);
                max = temp >= max ? temp : max;
            }
            int index = 0, count = 0;
            while (index < max) {
                int start = bitSet.nextSetBit(index);
                int end = bitSet.nextClearBit(start);

                int[] item = {start / 2, (end - 1) / 2};
                intervals[count++] = item;

                index = end;
            }
            int[][] ret = new int[count][2];
            for (int i = 0; i < count; i++) {
                ret[i] = intervals[i];
            }
            return ret;
        }
    }

    // 后注：先排序，然后再依次判断
    class Solution3 {
        public int[][] merge(int[][] intervals) {
            if (intervals.length == 0) {
                return new int[0][2];
            }
            quickSort(intervals, 0, intervals.length - 1);

            int index = 0;
            for (int i = 1; i < intervals.length; ++i) {
                if (intervals[i][0] <= intervals[index][1]) {
                    intervals[index][1] = Math.max(intervals[index][1], intervals[i][1]);
                } else if (intervals[i][0] > intervals[index][1]) {
                    index++;
                    intervals[index][0] = intervals[i][0];
                    intervals[index][1] = intervals[i][1];
                }
            }
            int[][] ans = new int[index + 1][2];
            for (int i = 0; i < index + 1; ++i) {
                ans[i] = intervals[i];
            }
            return ans;
        }

        private void quickSort(int[][] nums, int left, int right) {
            if (left >= right) {
                return;
            }

            int key = nums[left][0];
            int l = left, r = right;
            while (l < r) {
                while (l < r && nums[r][0] >= key) {
                    r--;
                }
                while (l < r && nums[l][0] <= key) {
                    l++;
                }

                if (l < r) {
                    int[] temp = nums[l];
                    nums[l] = nums[r];
                    nums[r] = temp;
                }
            }

            int[] temp = nums[left];
            nums[left] = nums[l];
            nums[l] = temp;
            quickSort(nums, left, l -1);
            quickSort(nums, l + 1, right);
        }
    }

}
