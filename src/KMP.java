import java.util.Objects;

/**
 * KMP算法实现
 * <p>
 * 关于KMP算法，参考：
 * http://www.ruanyifeng.com/blog/2013/05/Knuth%E2%80%93Morris%E2%80%
 * 93Pratt_algorithm.html
 * 
 * @author JiangJiafu
 *
 */
public class KMP {

    /**
     * 判断pattern是否在s中
     * @param s
     * @param pattern
     * @return
     */
    public static boolean contains(String s, String pattern) {
        Objects.requireNonNull(s);
        Objects.requireNonNull(pattern);
        int[] partialMatchTable = generatePartialMatchTable(pattern);
        for (int i = 0, j = 0; i < s.length(); i++) {
            // 匹配发生错误时，在 next 数组中向前迭代找到匹配的结果。这是整个算法的核心位置！
            while (j > 0 && s.charAt(i) != pattern.charAt(j)) {
                j = partialMatchTable[j - 1];
            }
            // 如果相等，pattern 中的索引往后移动一位
            if (s.charAt(i) == pattern.charAt(j))
                j++;
            // pattern数组所有位置均连续的匹配完成
            if (j == pattern.length()) {
                System.out.println(i - pattern.length() + 1);
                return true;
            }
        }
        return false;
    }

    /**
     * 返回部分匹配表数组，数组中的每个元素a[i]表示 pattern[0:i+1]中的前缀和后缀的最长共有元素的长度。
     * "前缀"指除了最后一个字符以外，一个字符串的全部头部组合；
     * "后缀"指除了第一个字符以外，一个字符串的全部尾部组合。
     * 
     * @param pattern
     *            待匹配的字符串
     * @return 部分匹配表
     */
    public static int[] generatePartialMatchTable(String pattern) {
        int[] partialMatchTable = new int[pattern.length()];
        partialMatchTable[0] = 0; // 第一个元素的 next 值为 0
        for (int i = 1, j = 0; i < pattern.length(); i++) {
            // 每次循环开始时，j表示字符串pattern[0:i]的前缀和后缀的最长共有元素的长度
            while (j > 0 && pattern.charAt(i) != pattern.charAt(j)) {
                j = partialMatchTable[j - 1];
            }
            if (pattern.charAt(i) == pattern.charAt(j)) {
                j++;
            }
            partialMatchTable[i] = j;
        }
        return partialMatchTable;
    }

    public static void main(String[] args) {
        String str = "BBC ABCDAB ABCDABCDABDE";
        String pattern = "ABCDABD";
        System.out.println(contains(str, pattern));
    }
}
