package search.twofen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * T3_二分查找的另一种独特形式
 * 找到 K 个最接近的元素
 *
 * 整数 a 比整数 b 更接近 x 需要满足：
 * |a - x| < |b - x| 或者
 * |a - x| == |b - x| 且 a < b
 *
 **/
public class T3_FindClosestElements {

    /**
     * 执行用时： 6 ms , 在所有 Java 提交中击败了 51.02% 的用户
     * 内存消耗： 40.2 MB , 在所有 Java 提交中击败了 58.60% 的用户
     *
     * 原思路：先二分查询到最接近x的值，然后在其左右用双指针查询，最后直接用双指针查询了
     */
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        List<Integer> list = new ArrayList<>();
        if (arr == null || arr.length < k) {
            return list;
        }
        int left = 0, right = arr.length - 1;
        while ((right - left) > k - 1) {
            if (Math.abs(arr[left] - x) > Math.abs(arr[right] - x)) {
                left++;
            } else {
                right--;
            }
        }
        for (int i = left; i < left + k; i++) {
            list.add(arr[i]);
        }
        return list;
    }




    // 2 ms
    // 后注：忘记了 index + k 的范围查询了，之前做过这种思路，忘记了。。。
   static class BestSolution {
        public List<Integer> findClosestElements(int[] arr, int k, int x) {
            int lo = 0, hi = arr.length - k;
            while (lo < hi) {
                int mid = (lo + hi) / 2;
                if (x - arr[mid] > arr[mid + k] - x) {
                    lo = mid + 1;
                } else {
                    hi = mid;
                }
            }
            List<Integer> res = new ArrayList(k);
            for (int i = 0; i < k; i++) {
                res.add(arr[lo + i]);
            }
            return res;
        }
    }

    // 3ms  40480 kb
    class Solution2 {
        public List<Integer> findClosestElements(int[] arr, int k, int x) {
            int l = 0, r = arr.length - 1;
            int removecount = arr.length - k;
            while (removecount > 0) {
                if (x - arr[l] <= arr[r] - x) {
                    r--;
                } else {
                    l++;
                }
                removecount--;
            }
            List<Integer> res = new ArrayList<>();
            for (int i = l; i <= r; i++) {
                res.add(arr[i]);
            }
            return res;
        }
    }

    //  4 ms
    class Solution3 {
        public List<Integer> findClosestElements(int[] arr, int k, int x) {
            //1.双指针
            int n = arr.length;
            List<Integer> result = new ArrayList<>();
            if (arr == null || arr.length == 0) {
                return result;
            }
            int left = 0, right = n - 1;
            int numDel = n - k;
            while (numDel > 0 && left <= right) {
                if (x - arr[left] <= arr[right] - x) {
                    right--;
                } else {
                    left++;
                }
                numDel--;
            }
            for (int i = left; i < left + k; i++) {
                result.add(arr[i]);
            }
            return result;
        }
    }




    // 40328 kb
    class Solution4 {
        public List<Integer> findClosestElements(int[] arr, int k, int x) {
            if (arr == null || arr.length < k) {
                return Collections.emptyList();
            }
            int i = -1, min = Integer.MAX_VALUE;
            int l = 0, r = arr.length - 1;
            while (l <= r) {
                int m = l + (r - l) / 2;
                int tmp = Math.abs(arr[m] - x);
                if (tmp < min) {
                    i = m;
                    min = tmp;
                }
                if (arr[m] == x) {
                    break;
                } else if (arr[m] > x) {
                    r = m - 1;
                } else {
                    l = m + 1;
                }
            }
            System.out.println(i);
            List<Integer> res = new ArrayList<>(k);
            l = i;
            r = i + 1;
            while (k > 0 && (l >= 0 || r < arr.length)) {
                int ld = Integer.MAX_VALUE;
                if (l >= 0) {
                    ld = x - arr[l];
                }
                int rd = Integer.MAX_VALUE;
                if (r < arr.length) {
                    rd = arr[r] - x;
                }
                if (ld <= rd) {
                    res.add(arr[l]);
                    l--;
                } else {
                    res.add(arr[r]);
                    r++;
                }
                k--;
            }
            Collections.sort(res);
            return res;
        }
    }

}
