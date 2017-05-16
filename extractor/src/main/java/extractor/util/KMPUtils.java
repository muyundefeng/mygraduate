package extractor.util;

/**
 * Created by lisheng on 17-5-10.
 */
public class KMPUtils {
    /**
     * Text中是否包含word，如果查找到，则返回匹配最开始的位置，否则返回-1
     * @param text
     * @param word
     * @return
     */
    public static int getFirstMatchPos(String text, String word) {
        char[] textC = text.toCharArray();
        char[] wordC = word.toCharArray();
        int result = kmp(textC, wordC);
        return result;
    }

    // W := word
    public static Integer[] createTable(char[] W) {
        Integer[] table = new Integer[W.length];
        int pos = 2; // cur pos to compute in T
        int cnd = 0; // index of W of next character of cur candidate substr

        // first few values are fixed
        table[0] = -1;  // table[0] := -1
        table[1] = 0;   // table[1] := 0

        while (pos < W.length) {
            // first case: substring is still good
            if (W[pos - 1] == W[cnd]) {
                table[pos] = cnd;
                cnd += 1;
                pos += 1;
            } else if (cnd > 0)
                cnd = table[cnd];
            else {
                table[pos] = 0;
                pos += 1;
            }
        }
        return table;
    }

    // S := text string
    // W := word
    public static int kmp(char[] S, char[] W) {
        if (W.length == 0) // substr is empty string
            return 0;
        if (S.length == 0) // text is empty, can't be found
            return -1;
        int m = 0; // index of beg. of current match in S
        int i = 0; // pos. of cur char in W
        Integer[] T = createTable(S);

        while (m + i < S.length) {
            if (W[i] == S[m + i]) {
                if (i == W.length - 1)
                    return m;
                i += 1;
            } else {
                m = (m + i - T[i]);
                if (T[i] > -1)
                    i = T[i];
                else
                    i = 0;
            }
        }
        return -1;
    }
}