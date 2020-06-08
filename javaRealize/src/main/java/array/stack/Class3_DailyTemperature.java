package array.stack;

import java.util.Stack;

/**
 * 课程题：每日温度
 *
 * 问题： 1. 没有使用栈结构，只是依次匹配，想到过用栈，但是对连续多个栈对象的天数计算没想好，
 *          其实可以用node结构保存了下标进行计算连续未匹配温度的相距天数，而且可以跳坐标存入
 *        2. 最后一位必定是0
 *        3. 倒序查询，之前也想到后否决了，没想到可以用后面的结果天数来进行跳步和直接判0
 *        4. 空间最优2的和我的类似，但是我的额外判断多了点，不够精简
 **/
public class Class3_DailyTemperature {

    /**
     * 列表长度的范围是 [1, 30000]
     * 华氏度在 [30, 100] 范围内的整数
     */
    public int[] dailyTemperatures(int[] T) {
        if (T.length == 1) {
            return new int[]{0};
        }
        int[] res = new int[T.length];
        for (int i = 0; i < T.length; i++) {
            int a = T[i];
            if (a == 100) {
                res[i] = 0;
                continue;
            }
            int step = 1;
            for (int j = i + step; j < T.length; j = i + ++step) {
                if (T[j] > a) {
                    res[i] = step;
                    break;
                }
            }
        }
        return res;
    }


    // 时间最优
    class SolutionBest {
        public int[] dailyTemperatures(int[] params) {
            int[] ret = new int[params.length];
            // 注： 此题没必要的判断，题目约定数组1~30000
            if (params.length == 0) {
                return ret;
            }
            ret[params.length - 1] = 0;
            for (int i = params.length - 2; i >= 0; i--) {
                // j += result[j]是利用已经有的结果进行跳跃
                for (int j = i + 1; ; j += ret[j]) {
                    if (params[j] > params[i]) {
                        ret[i] = j - i;
                        break;
                    }
                    // 遇到0表示后面不会有更大的值，那当然当前值就应该也为0
                    if (ret[j] == 0) {
                        break;
                    }
                }
            }
            return ret;
        }
    }

    // 空间最优
    class Solution2 {
        Stack<Node> stack = new Stack<Node>();

        public int[] dailyTemperatures(int[] T) {
            // 注： 此题没必要的判断，题目约定数组1~30000
            if (T == null || T.length == 0) {
                return null;
            }

            int[] dayCountRecord = new int[T.length];
            for (int indexOfTemp = 0; indexOfTemp < T.length; indexOfTemp++) {
                int todaysTemp = T[indexOfTemp];
                if (stack.empty()) {
                    stack.push(new Node(indexOfTemp, todaysTemp));
                    continue;
                }
                while (!stack.empty() && todaysTemp > stack.peek().temperature) {
                    Node latest = stack.pop();
                    dayCountRecord[latest.indexOfTemp] = indexOfTemp - latest.indexOfTemp;
                }

                stack.push(new Node(indexOfTemp, todaysTemp));
            }
            return dayCountRecord;
        }

        class Node {
            int indexOfTemp;
            int temperature;

            public Node(int indexOfTemp, int temperature) {
                this.indexOfTemp = indexOfTemp;
                this.temperature = temperature;
            }
        }
    }

    // 空间最优2
    class Solution3 {
        public int[] dailyTemperatures(int[] T) {
            int days = T.length;
            int[] output = new int[days];
            for (int i = 0; i < days - 1; i++) {
                for (int j = i + 1; j < days; j++) {
                    if (T[j] > T[i]) {
                        output[i] = j - i;
                        break;
                    }
                }
            }
            return output;
        }
    }

}
