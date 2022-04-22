package com.mdnote.rym.util;

/**
 * @author: Rhythm-2019
 * @date: 2020/3/9
 * @description: 字符串工具类
 */
public class StringUtil {

    /**
     * 判断字符串是否为空
     *
     * @param s 源字符串
     * @return true不为空
     */
    public static boolean isNotBlank(String s) {
        return !(s == null || s.isEmpty());
    }


    /**
     * 判断字符串是否为空
     *
     * @param s 源字符串
     * @return true为空
     */
    public static boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }


    /**
     * 将下划线命名转换成驼峰
     * @param s 字符串
     * @param upperFirst 是否将第一个字符传换成大写
     * @return 驼峰命名的字符串
     */
    public static String getCamelSign(String s, Boolean upperFirst) {
        // 首字母大写
        String bigHump = toBigHump(s);
        if (!upperFirst || bigHump.length() == 0) {
            return bigHump;
        }
        return toUpperCase(bigHump.charAt(0)) + bigHump.substring(1);
    }

    private static char toUpperCase(char c) {
        if (Character.isUpperCase(c)) {
            return c;
        }
        if (Character.isLowerCase(c)) {
            return Character.toUpperCase(c);
        }
        return c;
    }

    /**
     * 是否为大写字幕
     *
     * @param c 字符
     * @return 是否为大写字幕
     */
    private static boolean isLowerCase(char c) {
        return c >= 'a' && c <= 'z';
    }

    public static String packageToPath(String packageName) {
        return packageName.replace(".", "//") + "//";
    }

    public static String pathToPackage(String path) {
        return path.replace("//", ".");
    }

    public static String toBigHump(String source) {
        char[] exclude = {'_', '-', ' '};
        char[] chars = source.toCharArray();

        // skip
        int fast = 0;
        for (; fast < chars.length && containsExcludeChar(chars[fast], exclude); fast++) {}
        int slow = 0, len = chars.length;

        while (fast != len) {
            if (containsExcludeChar(chars[fast], exclude)) {
                while (fast != len && containsExcludeChar(chars[fast], exclude)) {
                    fast++;
                }
                if (fast == len) {
                    break;
                }
                if (fast < len) {
                    chars[fast] = Character.toUpperCase(chars[fast]);
                }
            }
            chars[slow] = chars[fast];
            slow++;
            fast++;
        }

        return new String(chars, 0, slow);
    }

    private static boolean containsExcludeChar(char c, char[] excludeChars) {
        for (char excludeChar : excludeChars) {
            if (c == excludeChar) {
                return true;
            }
        }
        return false;
    }

    public static String toLittleHump(String source) {
        if (source.length() == 1) {
            return Character.isLowerCase(source.charAt(0)) + "";
        }
        String bigHump = toBigHump(source);
        return Character.toLowerCase(bigHump.charAt(0)) + bigHump.substring(1);
    }
}
