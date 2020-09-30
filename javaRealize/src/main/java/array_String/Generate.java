package array_String;

import java.util.ArrayList;
import java.util.List;

/**
 * 小结习题： 杨辉三角
 *     问题： 当前列可以直接用 1 for(直接遍历上层即可) 1的方式进行填充
 *
 **/
public class Generate {

    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> list = new ArrayList<>();
        for (int i = 1; i <= numRows; i++) {
           List<Integer> inner = new ArrayList<>();
           int pre = i - 2;
           for (int j = 0; j < i; j++) {
                if (j == 0 || j == i - 1) {
                    inner.add(1);
                    continue;
                }
                if (pre < 0) {
                    continue;
                }
                List<Integer> before = list.get(pre);
               int num = before.get(j - 1) + before.get(j);
               inner.add(num);
           }
           list.add(inner);
        }
        return list;
    }

    public static void main(String[] args) {
        Generate t = new Generate();
        List<List<Integer>> ar = t.generate(5);
        for (List<Integer> l : ar) {
            for (Integer i : l) {
                System.out.print(i + " ");
            }
            System.out.println("");
        }
    }




    class BestSolution {
        public List<List<Integer>> generate(int numRows) {
            List<Integer> rows = new ArrayList<>();
            List<List<Integer>> results = new ArrayList<>();

            if (numRows == 0) {
                return results;
            }

            List<Integer> lastLists = null;
            rows.add(1);
            results.add(rows);
            if (numRows == 1) {
                return results;
            }
            int count = 1;
            for (int i = 1; i < numRows; i++) {
                rows = new ArrayList<Integer>();
                //第一个1
                rows.add(1);

                //中间取上一个list的值来计算
                lastLists = results.get(results.size() - 1);
                for (int j = 0; j < lastLists.size() - 1; j++) {
                    rows.add(lastLists.get(j) + lastLists.get(j + 1));
                }

                //最后一个1
                rows.add(1);
                results.add(rows);
            }
            return results;
        }
    }

    // 空间最优
    class Solution2 {
        public List<List<Integer>> generate(int numRows) {
            // 第i行有i个数字
            // 每一行第一个和最后一个是1
            // 其他数字是上一行的 j 和 j-1 个
            List<List<Integer>> res = new ArrayList<>();
            for (int i = 1; i <= numRows; i++) {
                List<Integer> list = new ArrayList<>();
                list.add(1); // 第一个数
                for (int j = 1; j < i; j++) {
                    if (j == i - 1) {   // 后注： 最后一个1
                        list.add(1);
                    } else {
                        list.add(res.get(i - 2).get(j) + res.get(i - 2).get(j - 1));
                    }
                }
                res.add(list);
            }
            return res;
        }
    }

}
