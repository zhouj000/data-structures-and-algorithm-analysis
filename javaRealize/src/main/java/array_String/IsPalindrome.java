package array_String;

/**
 * 验证回文串
 **/
public class IsPalindrome {

    /**
     * 执行用时： 2 ms , 在所有 Java 提交中击败了 99.85% 的用户
     * 内存消耗： 38.1 MB , 在所有 Java 提交中击败了 98.11% 的用户
     */
    public boolean isPalindrome(String s) {
        char[] arr = s.toCharArray();
        if (arr.length < 2) {
            return true;
        }
        int p = 0, q = arr.length - 1;
        int n1, n2;
        while (p <= q) {
            n1 = -1;
            n2 = -1;
            while (n1 < 0 && p <= q) {
                n1 = getChar(arr[p++]);
            }
            // 由于p最后又加了1，所以判断要-1
            while (n2 < 0 && p - 1 <= q) {
                n2 = getChar(arr[q--]);
            }
            if (n1 != n2) {
                return false;
            }
        }
        return true;
    }

    private int getChar(char c) {
        if (97 <= c && c <= 122) {
            return c - 87;
        }
        if (65 <= c && c <= 90) {
            return c - 55;
        }
        if (48 <= c && c <= 57) {
            return c - 48;
        }
        return -1;
    }




    //  1 ms
    class BestSolution {
        public boolean isPalindrome(String s) {
            if (s.length() == 0) {
                return true;
            }
            int left = 0, right = s.length() - 1;
            while (left < right) {
                char l = s.charAt(left), r = s.charAt(right);
                if (l < 48 || l > 122 || ((l < 65) && (l > 57)) || ((l < 97) && (l > 90))) {
                    left++;
                    continue;
                }
                if (r < 48 || r > 122 || ((r < 65) && (r > 57)) || ((r < 97) && (r > 90))) {
                    right--;
                    continue;
                }
                if (l >= 65 && l <= 90) {
                    l = (char) (l + 32);
                }
                if (r >= 65 && r <= 90) {
                    r = (char) (r + 32);
                }
                if (l != r) {
                    return false;
                }
                left++;
                right--;
            }
            return true;
        }
    }

    //  2 ms
    class Solution2 {
        public boolean isPalindrome(String s) {
            int start = 0;
            int end = s.length() - 1;
            while (start < end) {
                while (start < s.length() && !isValid(s.charAt(start))) {
                    start++;
                }
                while (end >= 0 && !isValid(s.charAt(end))) {
                    end--;
                }
                if (start >= end) {
                    break;
                }
                char l = s.charAt(start);
                char r = s.charAt(end);

                boolean bothLetters = isLetter(l) && isLetter(r);
                boolean bothNumeric = isNumeric(l) && isNumeric(r);

                if (!bothLetters && !bothNumeric) {
                    return false;
                }
                if (bothLetters
                        && l - 'a' != r - 'a'
                        && l - 'A' != r - 'A'
                        && l - 'a' != r - 'A'
                        && l - 'A' != r - 'a') {
                    return false;
                }
                if (bothNumeric
                        && l - '0' != r - '0') {
                    return false;
                }
                start++;
                end--;
            }
            return true;
        }

        private boolean isValid(char c) {
            return isLetter(c) || isNumeric(c);
        }

        private boolean isLetter(char c) {
            return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
        }

        private boolean isNumeric(char c) {
            return c >= '0' && c <= '9';
        }
    }


    //  38592 kb
    class Solution3 {
        public boolean isPalindrome(String s) {
            if (s.length() == 0) {
                return true;
            }
            int left = 0, right = s.length() - 1;
            char l, r;
            while (left < right) {
                l = Character.toLowerCase(s.charAt(left));
                r = Character.toLowerCase(s.charAt(right));
                if (!isFit(l)) {
                    left++;
                    continue;
                }
                if (!isFit(r)) {
                    right--;
                    continue;
                }
                if (l != r) {
                    return false;
                }
                left++;
                right--;
            }
            return true;
        }

        public boolean isFit(char ch) {
            return Character.isAlphabetic(ch) || Character.isDigit(ch);
        }
    }

}
