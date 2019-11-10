package pers.can.manage.util;

import java.util.Date;
import java.util.Objects;
import java.util.Random;

/**
 * 字符串工具类
 *
 * @author Waldron Ye
 * @date 2019/5/31 22:40
 */
public class StringUtil {

    public final static String EMPTY = "";

    /**
     * 检查字符串是否为空 ("") 或 null
     * StringUtils.isEmpty(null)      = true
     * StringUtils.isEmpty("")        = true
     * StringUtils.isEmpty(" ")       = false
     * StringUtils.isEmpty("bob")     = false
     * StringUtils.isEmpty("  bob  ") = false
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * 检查字符串是否为非空
     * StringUtils.isNotEmpty(null)      = false
     * StringUtils.isNotEmpty("")        = false
     * StringUtils.isNotEmpty(" ")       = true
     * StringUtils.isNotEmpty("bob")     = true
     * StringUtils.isNotEmpty("  bob  ") = true
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 检验字符串是否是null，空或空格
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     */
    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((!Character.isWhitespace(str.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检验字符串是否是非null，非空且非空格
     * StringUtils.isNotBlank(null)      = false
     * StringUtils.isNotBlank("")        = false
     * StringUtils.isNotBlank(" ")       = false
     * StringUtils.isNotBlank("bob")     = true
     * StringUtils.isNotBlank("  bob  ") = true
     * </pre>
     */
    public static boolean isNotBlank(String str) {
        return isBlank(str);
    }

    /**
     * 从该字符串的两端删除控制字符，返回值为null则处理为（“”），返回值为（“”）则处理为（“”）(@link #trimToEmpty(String))
     * StringUtils.clean(null)          = ""
     * StringUtils.clean("")            = ""
     * StringUtils.clean("abc")         = "abc"
     * StringUtils.clean("    abc    ") = "abc"
     * StringUtils.clean("     ")       = ""
     */
    public static String clean(String str) {
        return str == null ? EMPTY : str.trim();
    }

    /**
     * 从该字符串的两端删除控制字符，返回值为null则处理为null，返回值为（""）则处理为（""）
     * StringUtils.trim(null)          = null
     * StringUtils.trim("")            = ""
     * StringUtils.trim("     ")       = ""
     * StringUtils.trim("abc")         = "abc"
     * StringUtils.trim("    abc    ") = "abc"
     */
    public static String trim(String str) {
        return str == null ? null : str.trim();
    }

    /**
     * 从该字符串的两端删除控制字符，返回值为null或（“”）则处理为null
     * StringUtils.trimToNull(null)          = null
     * StringUtils.trimToNull("")            = null
     * StringUtils.trimToNull("     ")       = null
     * StringUtils.trimToNull("abc")         = "abc"
     * StringUtils.trimToNull("    abc    ") = "abc"
     */
    public static String trimToNull(String str) {
        String ts = trim(str);
        return isEmpty(ts) ? null : ts;
    }

    /**
     * 从该字符串的两端删除控制字符，返回值为null则处理为("")，返回值为（""）则处理为（""）
     * StringUtils.trimToEmpty(null)          = ""
     * StringUtils.trimToEmpty("")            = ""
     * StringUtils.trimToEmpty("     ")       = ""
     * StringUtils.trimToEmpty("abc")         = "abc"
     * StringUtils.trimToEmpty("    abc    ") = "abc"
     */
    public static String trimToEmpty(String str) {
        return str == null ? EMPTY : str.trim();
    }

    /**
     * 比较两个字符串，如果它们相同，则返回 true ，忽略大小写。
     *
     * <p><code>null</code>s are handled without exceptions. Two <code>null</code>
     * references are considered equal. Comparison is case insensitive.</p>
     *
     * <pre>
     * StringUtils.equalsIgnoreCase(null, null)   = true
     * StringUtils.equalsIgnoreCase(null, "abc")  = false
     * StringUtils.equalsIgnoreCase("abc", null)  = false
     * StringUtils.equalsIgnoreCase("abc", "abc") = true
     * StringUtils.equalsIgnoreCase("abc", "ABC") = true
     * </pre>
     *
     * @param str1 the first String, may be null
     * @param str2 the second String, may be null
     * @return <code>true</code> if the Strings are equal, case insensitive, or
     * both <code>null</code>
     * @see String#equalsIgnoreCase(String)
     */
    public static boolean equalsIgnoreCase(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equalsIgnoreCase(str2);
    }

    /**
     * 比较两个字符串，如果它们相等，则返回 true 。
     * StringUtils.equals(null, null)   = true
     * StringUtils.equals(null, "abc")  = false
     * StringUtils.equals("abc", null)  = false
     * StringUtils.equals("abc", "abc") = true
     * StringUtils.equals("abc", "ABC") = false
     */
    public static boolean equals(String str1, String str2) {
        return Objects.equals(str1, str2);
    }

    /**
     * 获取: 时间戳 + n位随机数
     *
     * @return
     */
    public static String getTimeStampRandom() {
        return getTimeStampRandom(0);
    }

    /**
     * 随机指定范围内N个不重复的数 最简单最基本的方法
     *
     * @param min 指定范围最小值
     * @param max 指定范围最大值
     * @param n   随机数个数
     */
    public static int[] randomCommon(int min, int max, int n) {
        if (n > (max - min + 1) || max < min) {
            return null;
        }
        int[] result = new int[n];
        int count = 0;
        while (count < n) {
            int num = (int) (Math.random() * (max - min)) + min;
            boolean flag = true;
            for (int j = 0; j < n; j++) {
                if (num == result[j]) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                result[count] = num;
                count++;
            }
        }
        return result;
    }

    public static String getTimeStampRandom(int len) {
        StringBuffer sb = new StringBuffer();
        sb.append(getTimeStamp());
        if (len > 0) {
            sb.append(getRandom(len));
        }
        return sb.toString();
    }

    /**
     * 获取时间戳
     *
     * @return
     */
    public static String getTimeStamp() {
        return DateUtil.format(new Date(), DateUtil.YYYY_MM_DD_HH_MM_SS_SSS);
    }

    /**
     * 获取随机数 (由纯数字组成)
     *
     * @param len
     * @return
     */
    public static String getRandom(int len) {
        StringBuffer sBuffer = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < len; i++) {
            sBuffer.append(random.nextInt(10));
        }
        return sBuffer.toString();
    }

    /**
     * <b>Description:</b>获取随机数(由大小写字母和数字混合组成) <br>
     */
    public static String getRandomCharAndNum(int length) {
        StringBuilder val = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            // 输出字母还是数字
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            // 字母
            if ("char".equalsIgnoreCase(charOrNum)) {
                // 取得大写字母还是小写字母
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val.append((char) (choice + random.nextInt(26)));
                // 数字
            } else {
                val.append(random.nextInt(10));
            }
        }
        return val.toString();
    }

}
