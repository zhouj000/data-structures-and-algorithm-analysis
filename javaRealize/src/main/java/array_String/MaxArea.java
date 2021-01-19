package array_String;

/**
 * 盛最多水的容器
 **/
public class MaxArea {

    /**
     * 执行用时： 3 ms , 在所有 Java 提交中击败了 91.56% 的用户
     * 内存消耗： 39.9 MB , 在所有 Java 提交中击败了 72.49% 的用户
     *
     * 后注：当时想的时候有考虑一直移动到大于 最小高度的时候再计算area，结果后面写代码的时候忘记了，这块影响时效很多
     */
    public int maxArea(int[] height) {
        if (height == null || height.length < 2) {
            return 0;
        }
        int li = 0, ri = height.length - 1;
        int maxArea = 0;
        while (li < ri) {
            int left = height[li];
            int right = height[ri];
            boolean leftMin = left < right;
            int area = (ri - li) * (leftMin ? left : right);
            maxArea = area > maxArea ? area : maxArea;
            if (leftMin) {
                li++;
            } else {
                ri--;
            }
        }
        return maxArea;
    }



    //  1 ms
    class BestSolution {
        public int maxArea(int[] height) {
            //特例
            if (height == null || height.length == 0) {
                return -1;
            }
            //定义双指针
            int i = 0;
            int j = height.length - 1;
            //定义面积
            int maxArea = 0;
            //定义临时变量
            int temp = 0;
            int tempArea = 0;
            //开始遍历
            while (i < j) {
                tempArea = (j - i) * Math.min(height[i], height[j]);
                if (tempArea > maxArea) {
                    maxArea = tempArea;
                }
                if (height[i] < height[j]) {
                    //如果左边的楼矮一点，那么就往右找下一个较高的楼
                    temp = height[i];
                    while (i < j && height[i] <= temp) {
                        i++;
                    }
                } else {
                    //如果右边的楼矮一点，那么就往左找下一个较高的楼
                    temp = height[j];
                    while (i < j && height[j] <= temp) {
                        j--;
                    }
                }
            }
            return maxArea;
        }
    }

    // 2 ms
    class Solution2 {
        public int maxArea(int[] height) {
            int l = 0;
            int r = height.length - 1;
            int max = 0;
            while (l < r) {
                if (height[l] > height[r]) {
                    if (max < height[r] * (r - l)) {
                        max = height[r] * (r - l);
                    }
                    r--;
                } else {
                    if (max < height[l] * (r - l)) {
                        max = height[l] * (r - l);
                    }
                    l++;
                }
            }
            return max;
        }
    }



    // 39144 kb
    class Solution3 {
        public int maxArea(int[] height) {
            if (height == null) {
                return 0;
            }
            int maxHeight = 0;
            int temp = 0;

            for (int i = 0; i < height.length - 1; i++) {
                for (int j = i + 1; j < height.length; j++) {
                    temp = Math.min(height[i], height[j]) * (j - i);
                    if (temp > maxHeight) {
                        maxHeight = temp;
                    }
                }
            }
            return maxHeight;
        }
    }

}
