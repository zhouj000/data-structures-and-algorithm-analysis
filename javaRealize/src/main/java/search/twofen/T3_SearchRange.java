package search.twofen;

import java.util.ArrayList;
import java.util.List;

/**
 * T3_二分查找的另一种独特形式
 * 在排序数组中查找元素的第一个和最后一个位置
 *
 **/
public class T3_SearchRange {

    /**
     * 执行用时： 0 ms , 在所有 Java 提交中击败了 100.00% 的用户
     * 内存消耗： 41.6 MB , 在所有 Java 提交中击败了 55.69% 的用户
     */
    public int[] searchRange(int[] nums, int target) {
        int[] result = new int[]{-1, -1};
        if (nums == null || nums.length == 0) {
            return result;
        }
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                int mlef = mid;
                while (left < mlef) {
                    int mid2 = left + (mlef - left) / 2;
                    if (nums[mid2] == target) {
                        mlef = mid2;
                    } else {
                        left = mid2 + 1;
                    }
                }
                mlef = left;
                int mrig = mid;
                while (mrig + 1 < right) {
                    int mid3 = mrig + (right - mrig) / 2;
                    if (nums[mid3] != target) {
                        right = mid3;
                    } else {
                        mrig = mid3;
                    }
                }
                result[0] = mlef;
                result[1] = nums[right] == target ? right : mrig;
                return result;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return result;
    }






    // 0 ms
    class BestSolution {
        public int[] searchRange(int[] nums, int target) {
            int n = nums.length;
            if (n == 0) {
                return new int[]{-1, -1};
            }

            int leftIdx = searchLeftMostIdx(nums, target); //左侧找不到
            if (leftIdx == -1) {
                return new int[]{-1, -1};
            }

            int rightIdx = searchRightMostIdx(nums, target); //此时右侧一定找的到。最坏情况下：rightIdx == leftIdx
            return new int[]{leftIdx, rightIdx};
        }

        public int searchLeftMostIdx(int[] nums, int target) {
            int start = 0, end = nums.length - 1;
            while (start < end) {
                int mid = start + (end - start) / 2; // start <= mid < end !
                if (nums[mid] < target) {
                    start = mid + 1; //赋值后，start的值变大。整个搜索区间变小
                } else if (nums[mid] == target) {
                    //要search符合条件的最左侧index
                    end = mid; //赋值后，end的值变小。整个搜索区间变小
                } else { //nums[mid] > target
                    end = mid; //赋值后，end的值变小。整个搜索区间变小
                }
            }
            if (nums[start] == target) {
                return start;
            }
            return -1;
        }

        public int searchRightMostIdx(int[] nums, int target) {
            int start = 0, end = nums.length - 1;
            while (start < end) {
                int mid = start + (end - start) / 2; // start <= mid < end !
                if (nums[mid] < target) {
                    start = mid + 1; //赋值后，start的值变大。整个搜索区间变小
                } else if (nums[mid] == target) {
                    //要search符合条件的最右侧index
                    if (start < mid) {
                        start = mid; //赋值后，start的值变大。整个搜索区间变小
                    } else {
                        //此时start=mid<end, start和end的差值为1。如果不做处理，进入无限循环
                        return nums[end] == target ? end : start;
                    }
                } else { //nums[mid] > target
                    end = mid; //赋值后，end的值变小。整个搜索区间变小
                }
            }
            if (nums[start] == target) {
                return start;
            }
            return -1;
        }
    }



    // 40836 kb
    class Solution2 {
        public int[] searchRange(int[] nums, int target) {
            int[] result = new int[2];
            result[0] = -1;
            result[1] = -1;
            List<Integer> mid = new ArrayList<>();
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] == target) {
                    mid.add(i);
                    Integer[] mids = new Integer[mid.size()];
                    mid.toArray(mids);
                    result[0] = mids[0];
                    result[1] = mids[mids.length - 1];
                }
            }
            return result;
        }
    }

    //  41668 kb
    class Solution3 {
        public int[] searchRange(int[] nums, int target) {
            int len = nums.length;
            if (len == 0) {
                int[] position = new int[2];
                position[0] = -1;
                position[1] = -1;
                return position;
            }
            return check(nums, 0, len - 1, target);
        }

        public int[] check(int[] nums, int left, int right, int target) {
            int[] position = new int[2];
            position[0] = -1;
            position[1] = -1;
            if (left == right) {
                if (nums[left] == target) {
                    position[0] = left;
                    position[1] = left;
                }
                return position;
            }
            int mid = (left + right) / 2;
            System.out.println(mid + " left:" + left + " right:" + right);
            int[] p1 = check(nums, left, mid, target);
            int[] p2 = check(nums, mid + 1, right, target);
            int[] p3 = merge(p1, p2);
            return p3;
        }

        public int[] merge(int[] p1, int[] p2) {
            int[] p3 = new int[2];
            if (p1[1] + 1 == p2[0]) {
                if (p1[0] != -1) {
                    p3[0] = p1[0];
                }
                if (p2[1] != -1) {
                    p3[1] = p2[1];
                }
                if (p1[0] == -1) {
                    p3[0] = p1[1];
                }
                if (p2[1] == -1) {
                    p3[1] = p2[0];
                }
            }
            if (p1[1] == -1) {
                p3[0] = p2[0];
                p3[1] = p2[1];
            }
            if (p2[0] == -1) {
                p3[0] = p1[0];
                p3[1] = p1[1];
            }
            return p3;
        }
    }

    // 41752 kb
    class Solution4 {
        public int[] searchRange(int[] nums, int target) {
            int i = 0;
            int j = nums.length - 1;
            int[] res = new int[]{-1, -1};

            if (nums.length == 0) {
                return res;
            }

            if (nums.length == 1 && nums[0] == target) {
                return new int[]{0, 0};
            }

            while (i <= nums.length - 1 && (res[0] == -1 || res[1] == -1)) {
                if (nums[i] != target) {
                    System.out.println("Branch 001 i=" + i);
                }
                if (nums[j] != target) {
                    System.out.println("Branch 002 j=" + j);
                }
                if (res[0] == -1 && nums[i] == target) {
                    System.out.println("Branch 003 i=" + i);
                    res[0] = i;
                    System.out.println("Branch 003 res[0]" + res[0]);
                }
                if (res[1] == -1 && nums[j] == target) {
                    System.out.println("Branch 004 j=" + j);
                    res[1] = j;
                    System.out.println("Branch 004 res[1]" + res[1]);
                }
                i++;
                j--;
            }
            return res;
        }
    }

    // 41892 kb
    class Solution5 {
        public int[] searchRange(int[] nums, int target) {
            int left = 0;
            int right = nums.length - 1;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (nums[mid] > target) {
                    right = mid - 1;
                } else if (nums[mid] < target) {
                    left = mid + 1;
                } else if (nums[mid] == target) {
                    left = mid;
                    right = mid;
                    while (right + 1 < nums.length && nums[right + 1] == target) {
                        right++;
                    }
                    while (left - 1 >= 0 && nums[left - 1] == target) {
                        left--;
                    }
                    return new int[]{left, right};
                }
            }
            return new int[]{-1, -1};
        }
    }
}
