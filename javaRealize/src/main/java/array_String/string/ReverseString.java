package array_String.string;

/**
 * 反转字符串
 *
 **/
public class ReverseString {

    public void reverseString(char[] s) {
        int i = 0, j = s.length - 1;
        while (i < j) {
            char t = s[i];
            s[i] = s[j];
            s[j] = t;
            i++;
            j--;
        }
    }

}
