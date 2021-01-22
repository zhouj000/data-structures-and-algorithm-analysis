package search.twofen;

/**
 * 标准的二分查找模板
 * 猜数字大小
 *
 **/
public class T1_GuessNumber {

    int guess(int num){
        return 1702766719 - num;
    };

    /**
     * 执行用时： 0 ms , 在所有 Java 提交中击败了 100.00% 的用户
     * 内存消耗： 35.3 MB , 在所有 Java 提交中击败了 24.27% 的用户
     */
    public int guessNumber(int n) {
        int left = 1, right = n;
        while (left <= right) {
            int middle = left + (right - left ) / 2;
            int guess = guess(middle);
            if (guess == 0) {
                return middle;
            } else if (guess < 0) {
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }
        return 1;
    }



    // 34932 kb
    public class BestSolution {
        public int guessNumber(int n) {
            int left = 1;
            int right = n;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                int flag = guess(mid);
                if (flag == 0) {
                    return mid;
                } else if (flag == 1) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
            return 0;
        }
    }

}
