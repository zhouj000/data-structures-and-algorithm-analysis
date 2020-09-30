package array_String.string;

import java.util.Arrays;

/**
 * 数组拆分 I
 *
 **/
public class ArrayPairSum {

    // 自注： 先排序，后计算，会导致时间上由于排序而超时，因此放弃这种解法
    public int arrayPairSum_old(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        // 归并排序
//        nums = sort(nums, 0, nums.length);
        // 然后取值
        int sum = 0;
        for (int i = 0; i < nums.length; i += 2) {
            sum += nums[i];
        }
        return sum;
    }

    /**
         n是正整数,范围在 [1, 10000]. （长度2n，即 2~20000个数）
        数组中的元素范围在 [-10000, 10000]

        自注： 明显的空间换时间方法，然而提交后
               时间上击败99.81%的用户，空间上击败了98.51%的用户
     */
    public int arrayPairSum(int[] nums) {
        int[] flags = new int[20001];
        for (int num : nums) {
            flags[num + 10000] = flags[num + 10000] + 1;
        }
        boolean next = false;
        int sum = 0;
        int i = 0;
        while (i < 20001) {
            if (flags[i] == 0) {
                i++;
                continue;
            }
            if (next) {
                flags[i] = flags[i] - 1;
                next = false;
                continue;
            }
            flags[i] = flags[i] - 1;
            sum = sum + i -  10000;
            next = true;
        }
        return sum;
    }

    public static void main(String[] args) {
//        int[] nums = {1, 4, 3, 2};
//        ArrayPairSum t = new ArrayPairSum();
//        int n = t.arrayPairSum(nums);
//        System.out.println(n);

        System.out.println(-1 & 1);
        System.out.println(0 & 1);
        System.out.println(1 & 1);
        System.out.println(2 & 1);
        System.out.println(5 & 1);
    }


    /**
     * 后注：我的解法与其效率一致，思路是差不多的
     *      使用min max让后面的循环减少轮次
     *
     */
    public class BestSolution {
        public int arrayPairSum(int[] nums) {
            int[] arr = new int[20001];
            int lim = 10000;
            int max = Integer.MIN_VALUE;
            int min = Integer.MAX_VALUE;
            for (int num : nums) {
                int temp = num + lim;
                arr[temp]++;
                if (temp > max) {
                    max = temp;
                }
                if (temp < min) {
                    min = temp;
                }
            }

            int sum = 0;
            int temp = 0;
            for (int i = min; i <= max; i++) {
                if (arr[i] != 0) {
                    // 后注： 他这里用temp代替我的boolean，然后同一个位置有多个值，
                    //        直接计算出次数（& 1 -> 偶数是0，奇数是1）
                    int low = arr[i] + (((temp & 1) == 1) ? -1 : 0);
                    if ((low & 1) == 1) {
                        // 后注： 奇数/2 + 1
                        sum += ((i - lim) * (low / 2 + 1));
                    } else {
                        sum += ((i - lim) * (low / 2));
                    }
                    temp += arr[i];
                }
            }
            return sum;
        }
    }

    class Solution2 {
        public int arrayPairSum(int[] nums) {
            boolean[] flag = new boolean[20001];
            int sum = 0;
            for (int n : nums) {
                // 如果相同的两个元素，则差值为0，不需要再循环里计算
                flag[n + 10000] = !flag[n + 10000];
                sum += n;
            }

            boolean odd = true;
            int nSum = 0;
            for (int i = 0; i < 20001; i++) {
                if (flag[i]) {
                    if (odd) {
                        nSum += -(i - 10000);
                    } else {
                        nSum += i - 10000;
                    }
                    odd = !odd;
                }
            }

            // 假设a=分组里小值之和，b=分组里的大值之和,a是所求的目标值
            // sum=a+b,nSum=b-a,故(sum+bSum)/2=(a+b-(b-a))/2=a
            return (sum - nSum) / 2;
        }
    }




    // 后注：用Arrays的排序，效率比自己写高，没有导致超时
    class Solution3 {
        public int arrayPairSum(int[] nums) {
            //最大的肯定是要被抛弃的，那么最大跟第二大肯定是要在一起的，不然保不住第二大的数。保住了第二大，那么最大的牺牲是值得的。
            Arrays.sort(nums);
            int total = 0;
            for (int i = 0; i < nums.length; i++) {
                if (i % 2 == 0) {
                    total = total + nums[i];
                }
            }
            return total;
        }
    }

    class Solution4 {
        public int arrayPairSum(int[] nums) {
            Arrays.sort(nums);
            int sum = 0;
            // 后注： 疑问： 循环+=2 比 ++后再if判断更慢(上例比此例快)
            for (int i = 0; i < nums.length; i += 2) {
                sum += nums[i];
            }
            return sum;
        }
    }

}
