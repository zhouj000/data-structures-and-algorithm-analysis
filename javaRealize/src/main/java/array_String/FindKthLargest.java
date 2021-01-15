package array_String;

import java.util.Arrays;
import java.util.Random;

/**
 * 数组类算法： 数组中的第K个最大元素
 *
 * 利用快速排序的思想，从数组 S 中随机找出一个元素 X，把数组分为两部分 Sa 和 Sb。
 * Sa 中的元素大于等于 X，Sb 中元素小于 X
 * 这时有两种情况：
 *      Sa 中元素的个数小于 k，则 Sb 中的第 k-|Sa| 个元素即为第k大数；
 *      Sa 中元素的个数大于等于 k，则返回 Sa 中的第 k 大数。时间复杂度近似为 O(n)
 *
 **/
public class FindKthLargest {

    /**
     * 执行用时： 2 ms , 在所有 Java 提交中击败了 90.18% 的用户
     * 内存消耗： 38.7 MB , 在所有 Java 提交中击败了 71.27% 的用户
     */
    public int findKthLargest(int[] nums, int k) {
        // 先排序
        Arrays.sort(nums);
        // 后获取
        return nums[nums.length - k];
    }




    // 0 ms
    class BestSolution {
        public int findKthLargest(int[] nums, int k) {
            return quickSort(nums, 0, nums.length - 1, k - 1);
        }

        public int quickSort(int[] nums, int l, int r, int k) {
            if (l == r) {
                return nums[k];
            }
            int pivot = nums[(l + r) >> 1], i = l - 1, j = r + 1;
            while (i < j) {
                do {
                    i++;
                } while (nums[i] > pivot);
                do {
                    j--;
                } while (nums[j] < pivot);
                if (i < j) {
                    int tmp = nums[i];
                    nums[i] = nums[j];
                    nums[j] = tmp;
                }
            }
            return (k <= j) ? quickSort(nums, l, j, k) : quickSort(nums, j + 1, r, k);
        }
    }

    //  1 ms
    class Solution2 {
        private Random random = new Random(System.currentTimeMillis());

        public int findKthLargest(int[] nums, int k) {
            int len = nums.length;
            int left = 0;
            int right = len - 1;

            // 转换一下，第 len-k 小的元素
            int target = len - k;

            while (true) {
                int index = partition(nums, left, right);//找中间位置数
                if (index == target) {//刚好是第几小的
                    return nums[index];
                } else if (index < target) {//找到的数比要找的要小，说明要找的在右边区间
                    left = index + 1;
                } else {//要找的在左边区间
                    right = index - 1;
                }
            }
        }

        public int partition(int[] nums, int left, int right) {
            // 在区间随机选择一个元素作为标定点
            if (right > left) {
                int randomIndex = left + 1 + random.nextInt(right - left);
                swap(nums, left, randomIndex);
            }
            int pivot = nums[left];

            // 将等于 pivot 的元素分散到两边
            // [left, lt) <= pivot
            // (rt, right] >= pivot
            int lt = left + 1;
            int rt = right;

            while (true) {
                while (lt <= rt && nums[lt] < pivot) {
                    lt++;
                }
                while (lt <= rt && nums[rt] > pivot) {
                    rt--;
                }
                if (lt > rt) {
                    break;
                }
                swap(nums, lt, rt);
                lt++;
                rt--;
            }
            swap(nums, left, rt);
            return rt;
        }

        private void swap(int[] nums, int index1, int index2) {
            int temp = nums[index1];
            nums[index1] = nums[index2];
            nums[index2] = temp;
        }
    }




    // 2 ms   38680 kb
    class Solution3 {
        public int findKthLargest(int[] nums, int k) {
            int heapSize = nums.length;
            buildMaxHeap(nums, heapSize);
            for (int i = nums.length - 1; i >= nums.length - k + 1; --i) {
                swap(nums, 0, i);
                --heapSize;
                maxHeapify(nums, 0, heapSize);
            }
            return nums[0];
        }

        public void buildMaxHeap(int[] a, int heapSize) {
            for (int i = heapSize / 2 - 1; i >= 0; --i) {
                maxHeapify(a, i, heapSize);
            }
        }

        public void maxHeapify(int[] a, int i, int heapSize) {
            int l = i * 2 + 1, r = i * 2 + 2, largest = i;
            if (l < heapSize && a[l] > a[largest]) {
                largest = l;
            }
            if (r < heapSize && a[r] > a[largest]) {
                largest = r;
            }
            if (largest != i) {
                swap(a, i, largest);
                maxHeapify(a, largest, heapSize);
            }
        }

        public void swap(int[] a, int i, int j) {
            int temp = a[i];
            a[i] = a[j];
            a[j] = temp;
        }
    }

    // 38808 kb
    class Solution4 {
        public int findKthLargest(int[] nums, int k) {
            int len = nums.length;
            int left = 0;
            int right = len - 1;

            // 转换一下，第 k 大元素的索引是 len - k
            int target = len - k;

            while (true) {
                int index = partition(nums, left, right);
                if (index == target) {
                    return nums[index];
                } else if (index < target) {
                    left = index + 1;
                } else {
                    right = index - 1;
                }
            }
        }

        /**
         * 在数组 nums 的子区间 [left, right] 执行 partition 操作，返回 nums[left] 排序以后应该在的位置
         * 在遍历过程中保持循环不变量的语义
         * 1、[left + 1, j] < nums[left]
         * 2、(j, i] >= nums[left]
         *
         * @param nums
         * @param left
         * @param right
         * @return
         */
        public int partition(int[] nums, int left, int right) {
            int pivot = nums[left];
            int j = left;
            for (int i = left + 1; i <= right; i++) {
                if (nums[i] < pivot) {
                    j++;
                    swap(nums, j, i);
                }
            }
            swap(nums, j, left);
            return j;
        }

        private void swap(int[] nums, int index1, int index2) {
            int temp = nums[index1];
            nums[index1] = nums[index2];
            nums[index2] = temp;
        }
    }

    // 38816 kb
    class Solution5 {
        public int findKthLargest(int[] nums, int k) {
            if (nums == null || k > nums.length || k == 0) {
                return 0;
            }
            int position = BFPRT(0, nums.length - 1, nums.length - k + 1, nums);
            return nums[position];
        }

        //算法主入口
        private int BFPRT(int left, int right, int k, int[] array) {
            if (left >= right) {
                return left;
            }
            int pivotIndex = getPivotIndex(left, right, array);
            int mid = Partition(left, right, pivotIndex, array);
            int count = mid - left + 1;
            if (count == k) {
                return mid;
            } else if (count > k) {
                return BFPRT(left, mid - 1, k, array);
            } else {
                return BFPRT(mid + 1, right, k - count, array);
            }
        }

        //得到中位数
        private int getPivotIndex(int left, int right, int[] array) {
            if ((right - left) < 5) {
                return insertSort(left, right, array);
            }
            int back = left - 1;
            for (int i = left; i + 4 < right; i += 5) {
                int index = insertSort(i, i + 4, array);
                Swap(array, ++back, index);
            }
            return BFPRT(left, back, ((back + left) >> 1) + 1, array);
        }

        //插入排序
        private int insertSort(int left, int right, int[] array) {
            for (int i = left + 1; i < right + 1; i++) {
                int temp = array[i];
                int j;
                for (j = i; j > left && array[j - 1] > temp; j--) {
                    array[j] = array[j - 1];
                }
                array[j] = temp;
            }
            return (right + left) >> 1;
        }

        //交换
        private void Swap(int[] nums, int a, int b) {
            int temp = nums[a];
            nums[a] = nums[b];
            nums[b] = temp;
            return;
        }

        //划分
        private int Partition(int left, int right, int index, int[] array) {
            Swap(array, right, index);
            int position = left;
            for (int i = left; i < right; i++) {
                if (array[i] < array[right]) {
                    Swap(array, i, position++);
                }
            }
            Swap(array, right, position);
            return position;
        }
    }

}
