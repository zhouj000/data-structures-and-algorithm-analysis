package search.twofen;

/**
 * 模板 - 3种方式
 *
 **/
public class Template {

    /**
     * 模板 #1 和 #3 是最常用的，几乎所有二分查找问题都可以用其中之一轻松实现。
     * 模板 #2 更 高级一些，用于解决某些类型的问题
     */

    /**
     * T1_标准的二分查找模板
     *      1. 查找条件可以在不与元素的两侧进行比较的情况下确定（或使用它周围的特定元素）
     *      2. 不需要后处理，因为每一步中，你都在检查是否找到了元素。如果到达末尾，则知道未找到该元素
     *
     *
     * 初始条件：    left = 0, right = length - 1
     * 终止：        left > right
     * 向左查找：    right = mid - 1
     * 向右查找：    left = mid + 1
     *
     */
    int binarySearch1(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            // Prevent (left + right) overflow
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        // End Condition: left > right
        return -1;
    }


    /**
     * T2_二分查找的高级模板： 用于查找需要访问数组中当前索引及其直接右邻居索引的元素或条件
     *      1. 查找条件需要访问元素的直接右邻居
     *      2. 使用元素的右邻居来确定是否满足条件，并决定是向左还是向右
     *      3. 保证查找空间在每一步中至少有 2个元素
     *      4. 需要进行后处理。当你剩下 1个元素时，循环/递归结束。需要评估剩余元素是否符合条件
     *
     *
     * 初始条件：        left = 0, right = length
     * 终止：            left == right
     * 向左查找：        right = mid
     * 向右查找：        left = mid + 1
     *
     */
    int binarySearch2(int[] nums, int target){
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int left = 0, right = nums.length;
        while (left < right) {
            // Prevent (left + right) overflow
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        // Post-processing:
        // End Condition: left == right
        if (left != nums.length && nums[left] == target) {
            return left;
        }
        return -1;
    }


    /**
     *  T3_二分查找的另一种独特形式：用于搜索需要访问当前索引及其在数组中的直接左右邻居索引的元素或条件
     *      1. 搜索条件需要访问元素的直接左右邻居
     *      2. 使用元素的邻居来确定它是向右还是向左
     *      3. 保证查找空间在每个步骤中至少有 3个元素
     *      4. 需要进行后处理。当剩下 2个元素时，循环/递归结束。需要评估其余元素是否符合条件
     *
     *
     * 初始条件：        left = 0, right = length-1
     * 终止：            left + 1 == right
     * 向左查找：        right = mid
     * 向右查找：        left = mid
     *
     */
    int binarySearch(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int left = 0, right = nums.length - 1;
        while (left + 1 < right) {
            // Prevent (left + right) overflow
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid;
            } else {
                right = mid;
            }
        }

        // Post-processing:
        // End Condition: left + 1 == right
        if (nums[left] == target) {
            return left;
        }
        if (nums[right] == target) {
            return right;
        }
        return -1;
    }

}
