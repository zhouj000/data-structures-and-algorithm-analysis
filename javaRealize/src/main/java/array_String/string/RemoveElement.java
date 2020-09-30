package array_String.string;

/**
 *  移除元素
 *      问题： 就是双指针II， 快慢指针的实例
 *             区别就是用可以用foreach循环，也可以用普通for或while去遍历
 *
 **/
public class RemoveElement {

    public int removeElement(int[] nums, int val) {
        int slow = 0;
        for (int fast = 0; fast < nums.length; fast++) {
            if (nums[fast] != val) {
                nums[slow] = nums[fast];
                slow++;
            }
        }
        return slow;
    }

}
