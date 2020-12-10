package hash_list_map.key;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 有效的数独 （一个有效的数独（部分已被填充）不一定是可解的）
 * 执行用时： 16 ms , 在所有 Java 提交中击败了 5.17% 的用户
 * 内存消耗： 39.3 MB , 在所有 Java 提交中击败了 5.05% 的用户
 **/
public class IsValidSudoku {

    public boolean isValidSudoku(char[][] board) {
        Map<String, List<Integer>> listMap = new HashMap<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                char c = board[i][j];
                if (c == '.') {
                    continue;
                }
                Integer num = Integer.parseInt(String.valueOf(c));
                String xKey = i + "x";
                String ykey = j + "y";
                String cellKey = i / 3 + "-" + j / 3;
                if (check(listMap, xKey, num) || check(listMap, ykey, num) || check(listMap, cellKey, num)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean check(Map<String, List<Integer>> listMap, String key, Integer num) {
        List<Integer> list = listMap.get(key);
        if (list == null) {
            list = new ArrayList<>();
            list.add(num);
            listMap.put(key, list);
        } else {
            if (list.contains(num)) {
                return true;
            }
            list.add(num);
        }
        return false;
    }

    public static void main(String[] args) {
        char[][] a = {{'8','3','.','.','7','.','.','.','.'},
                      {'6','.','.','1','9','5','.','.','.'},
                      {'.','9','8','.','.','.','.','6','.'},
                      {'8','.','.','.','6','.','.','.','3'},
                      {'4','.','.','8','.','3','.','.','1'},
                      {'7','.','.','.','2','.','.','.','6'},
                      {'.','6','.','.','.','.','2','8','.'},
                      {'.','.','.','4','1','9','.','.','5'},
                      {'.','.','.','.','8','.','.','7','9'}};
        IsValidSudoku t = new IsValidSudoku();
        System.out.println(t.isValidSudoku(a));
    }



    class BestSolution {
        private final int N = 9;

        public boolean isValidSudoku(char[][] board) {
            int[] rows = new int[N];
            int[] cols = new int[N];
            int[] boxes = new int[N];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (board[i][j] == '.') {
                        continue;
                    }
                    int temp = board[i][j] - '0';
                    int boxIndex = i / 3 * 3 + j / 3;
                    if ((rows[i] >> temp & 1) == 1 || (cols[j] >> temp & 1) == 1 || (boxes[boxIndex] >> temp & 1) == 1) {
                        return false;
                    }
                    rows[i] = rows[i] | 1 << temp;
                    cols[j] = cols[j] | 1 << temp;
                    boxes[boxIndex] = boxes[boxIndex] | (1 << temp);
                }
            }
            return true;
        }
    }

    class Solution2 {
        private void initial(int[] a) {
            int i = 0;
            for (; i < a.length; i++) {
                a[i] = 0;
            }
        }
        public boolean isValidSudoku(char[][] board) {
            int[] a = new int[10];
            int i = 0, j = 0;
            for (; i < 9; i++) {
                initial(a);
                for (j = 0; j < 9; j++) {
                    if (board[i][j] != '.') {
                        int n = board[i][j] - '0';
                        a[n]++;
                        if (a[n] > 1) {
                            return false;
                        }
                    }
                }
            }
            for (i = 0; i < 9; i++) {
                initial(a);
                for (j = 0; j < 9; j++) {
                    if (board[j][i] != '.') {
                        int n = board[j][i] - '0';
                        a[n]++;
                        if (a[n] > 1) {
                            return false;
                        }
                    }
                }
            }
            int k = 0;
            for (; k < 9; k++) {
                initial(a);
                for (i = k / 3 * 3; i < k / 3 * 3 + 3; i++) {
                    for (j = k % 3 * 3; j < k % 3 * 3 + 3; j++) {
                        if (board[i][j] != '.') {
                            int n = board[i][j] - '0';
                            a[n]++;
                            if (a[n] > 1) {
                                return false;
                            }
                        }
                    }
                }
            }
            return true;
        }
    }

    // 后注：和我的思路一样，直接用 map<c, boolean>即可
    class Solution3 {
        public boolean isValidSudoku(char[][] board) {
            Map<Character, Boolean>[] rows = new HashMap[9];
            Map<Character, Boolean>[] cols = new HashMap[9];
            Map<Character, Boolean>[] boxes = new HashMap[9];
            for (int i = 0; i < 9; i++) {
                rows[i] = new HashMap<>();
                cols[i] = new HashMap<>();
                boxes[i] = new HashMap<>();
            }
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    char c = board[row][col];
                    if (c == '.') {
                        continue;
                    }
                    if (rows[row].get(c) != null) {
                        return false;
                    } else {
                        rows[row].put(c, true);
                    }
                    if (cols[col].get(c) != null) {
                        return false;
                    } else {
                        cols[col].put(c, true);
                    }
                    int boxIndex = (row / 3) * 3 + col / 3;
                    if (boxes[boxIndex].get(c) != null) {
                        return false;
                    } else {
                        boxes[boxIndex].put(c, true);
                    }
                }
            }
            return true;
        }
    }


    // 空间最优
    class Solution4 {
        public boolean isValidSudoku(char[][] board) {
            if (board == null || board.length > 9 || board[0].length > 9) {
                return false;
            }
            return backtrack(board, 0, 0);
        }

        public boolean backtrack(char[][] board, int r, int c) {
            int m = 9, n = 9;
            if (c == n) {
                return backtrack(board, r + 1, 0);
            }
            if (r == m) {
                return true;
            }
            if (board[r][c] == '.') {
                return backtrack(board, r, c + 1);
            }
            if (board[r][c] != '.') {
                if (!isValid(board, r, c)) {
                    return false;
                }
                return backtrack(board, r, c + 1);
            }
            return true;
        }

        public boolean isValid(char[][] board, int r, int c) {
            char ch = board[r][c];
            for (int k = 0; k < 9; k++) {
                if (board[r][k] == ch) {
                    if (k != c) {
                        return false;
                    }
                }
                if (board[k][c] == ch) {
                    if (k != r) {
                        return false;
                    }
                }
                if (board[(r / 3) * 3 + k / 3][(c / 3) * 3 + k % 3] == ch) {
                    if ((r / 3) * 3 + k / 3 != r && (c / 3) * 3 + k % 3 != c) {
                        return false;
                    }
                }
            }
            return true;
        }
    }

}
