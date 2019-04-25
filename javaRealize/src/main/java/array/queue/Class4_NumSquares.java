package array.queue;

import java.util.*;

/**
 * 课程题：完全平方数
 *
 * 我的思路：
 * 依旧是一轮轮减去可能性，然后判断最短路径
 *
 * 与其他算法的区别：
 * 1.前面2个算法，设计思路看不透，要有好的数学基础吧，总体上用了4这个除了1以外最小的完全平方数做文章
 * 2.从倒数第2个算法来看，这个问题只有4种可能，而且是右规律的
 *
 * 3.可以使用动态规划的思想
 * 
 * 4.最后1个算法，可以看出used和head tail last的方式反而造成时间变长 
 **/
public class Class4_NumSquares {

    public int numSquares(int n) {
        if (Math.sqrt(n) % 1 == 0) {
            return 1;
        }
        Queue<Integer> task = new ArrayDeque<>();
        Set<Integer> used = new HashSet<>();
        int head, tail = n, last = n;
        task.offer(n);
        int round = 1;
        while (!task.isEmpty()) {
            head = task.poll();
            int max = (int) Math.sqrt(head);
            int min = 1; //(int)Math.sqrt(head / 2);
            for (int i = min; i <= max; i++) {
                int left = head - i * i;
                if (left == 0) {
                    return round;
                }
                if (!used.contains(left)) {
                    task.offer(left);
                    used.add(left);
                    last = left;
                }
            }
            if (head == tail) {
                tail = last;
                round++;
            }
        }
        return round;
    }

    class NumSquaresBest {

        public int numSquares(int n) {
            while (n % 4 == 0) n = n / 4;
            if (n % 8 == 7) return 4;

            else {
                int a = 0;
                while (a * a < n) {
                    int b = (int) Math.sqrt(n - a * a);
                    if (a * a + b * b == n) {
                        if (a == 0 && b == 0) return 0;
                        else if (a != 0 && b != 0) return 2;
                        else return 1;
                    }
                    a = a + 1;
                }
                return 3;
            }
        }
    }


    class OtherNumSquares {
        public boolean isSquare(int n) {
            int num = (int) Math.sqrt(n);
            return num * num == n;
        }

        public int numSquares(int n) {
            if (isSquare(n)) return 1;
            while ((n & 3) == 0) {
                //n是4的倍数
                n >>= 2;
            }
            if ((n & 7) == 7) {
                //n是8的倍数
                return 4;
            }
            int num = (int) Math.sqrt(n);
            for (int i = 0; i <= num; i++) {
                if (isSquare(n - i * i)) {
                    return 2;
                }
            }
            return 3;
        }
    }

    /**
     * 是用的动态规划？还是算递归拆分？
     */
    class OtherNumSquares2 {
        int min;

        public int numSquares(int n) {
            min = n;
            int k = nsqr(n, 0);
            return k < min ? k : min;
        }

        public int nsqr(int n, int cur) {
            if (cur + 1 >= min) return 1;
            int d = (int) Math.sqrt(n);
            int d2 = d * d;
            if (d2 == n) return 1;
            if (cur + 2 >= min) return 2;
            int result = 0;
            for (int i = d; i > 0; i--) {
                int r = nsqr(n - i * i, cur + 1);
                if (i == d || r + 1 < result) result = r + 1;
                if (cur + r + 1 < min) min = cur + r + 1;
            }
            return result;
        }
    }


    /**
     * 动态规划: 效率不知
     */
    class OtherNumSquares3 {

        public int numSquares(int n) {
            List<Integer> squareList = generateSquareList(n);
            int[] dp = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                dp[i] = i;//初始化，数i是由i个1组成，所以有i个平方数=》最多
                for (int num : squareList) {
                    if (num > i)
                        break;
                    //i-num所有的平方数个数，加上1
                    dp[i] = Math.min(dp[i], 1 + dp[i - num]);
                }
            }
            return dp[n];
        }

        private List<Integer> generateSquareList(int n) {
            List<Integer> squareList = new ArrayList<>();
            for (int i = 1; i * i <= n; i++) {
                squareList.add(i * i);
            }
            return squareList;
        }
    }

    class OtherNumSquares4 {
        public int[] dp = new int[10000];

        {
            dp[0] = 0;
            dp[1] = 1;
            dp[2] = 2;
            dp[3] = 3;
            dp[4] = 1;
            dp[5] = 2;
            dp[6] = 3;
            dp[7] = 4;
            dp[8] = 2;
            dp[9] = 1;
        }

        public int max = 10;

        public int numSquares(int n) {
            if (n < max)
                return dp[n];
            for (int k = max; k <= n; k++) {
                int min = 10000;
                for (int j = 1; j * j <= k; j++) {
                    min = Math.min(min, dp[k - j * j] + 1);
                }
                dp[k] = min;
            }
            max = n + 1;
            return dp[n];
        }
    }
	
	class OtherNumSquares5 {
		public int numSquares(int n) {
			Queue<Integer> queue = new LinkedList<Integer>();
			int step = 0;
			queue.offer(n);
			if (isSquare(n)){
				return 1;
			}
        
			while(!queue.isEmpty()){
				int size = queue.size();
				for (int j = 0; j < size; j++){
					int sum = queue.peek();
					for (int k = (int) Math.sqrt(sum); k > 0 ; k--){
						int result = sum - k * k;
						if (isSquare(result)){
							step = step + 2;
							return step;
						}
						queue.offer(result);
					}
					queue.remove();
				}
				step = step + 1;
			}
			return -1;
		}
    
		public boolean isSquare(int n){
			double temp = Math.sqrt(n);
			int m = (int) temp;
			return m * m == n;
		}
	}
}
