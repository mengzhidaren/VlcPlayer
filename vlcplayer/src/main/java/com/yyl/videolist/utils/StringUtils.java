package com.yyl.videolist.utils;

import android.content.Context;
import android.text.SpannableStringBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @author WeiShiming
 */
public class StringUtils {
    public static final String CHARSET_UTF_8 = "UTF-8";

    /**
     * 解决自动换行
     *
     * @param input
     * @return
     */
    public static String toSBC(String input) {
        if (isNull(input)) {
            return input;
        }
        char c[] = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == ' ') {
                c[i] = '\u3000';
            } else if (c[i] < '\177') {
                c[i] = (char) (c[i] + 65248);
            }
        }
        return new String(c);
    }

    public static String fixLastSlash(String str) {
        String res = str == null ? "/" : str.trim() + "/";
        if (res.length() > 2 && res.charAt(res.length() - 2) == '/')
            res = res.substring(0, res.length() - 1);
        return res;
    }

    public static int convertToInt(String str) throws NumberFormatException {
        int s, e;
        for (s = 0; s < str.length(); s++)
            if (Character.isDigit(str.charAt(s)))
                break;
        for (e = str.length(); e > 0; e--)
            if (Character.isDigit(str.charAt(e - 1)))
                break;
        if (e > s) {
            try {
                return Integer.parseInt(str.substring(s, e));
            } catch (NumberFormatException ex) {
                throw new NumberFormatException();
            }
        } else {
            throw new NumberFormatException();
        }
    }

    public static String generateTime(long time) {
        int totalSeconds = (int) (time / 1000);
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;

        return hours > 0 ? String.format("%02d:%02d:%02d", hours, minutes, seconds) : String.format("%02d:%02d", minutes, seconds);
    }

    /**
     * 创建一条带独占图标的字符串
     *
     * @param context
     * @param title
     * @return
     */
    public static CharSequence buildExclusiveTitle(Context context, String title) {
        SpannableStringBuilder ssb = new SpannableStringBuilder("0 ");
        ssb.append(title);

//        ssb.setSpan(new ImageSpan(context, R.drawable.ic_exclusive,
//                        DynamicDrawableSpan.ALIGN_BASELINE), 0, 1,
//                Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        return ssb;
    }

    public static String getOmitChar(int count) {
        StringBuffer buffer = new StringBuffer(count + "");
        if (count >= 1000000) {
            buffer.delete(buffer.length() - 4, buffer.length());
            buffer.append("w");
        } else if (count >= 10000) {
            buffer.delete(buffer.length() - 3, buffer.length());
            buffer.insert(buffer.length() - 1, ".");
            buffer.append("w");
        } else if (count >= 1000) {
            buffer.delete(buffer.length() - 2, buffer.length());
            buffer.insert(buffer.length() - 1, ".");
            buffer.append("k");
        }
        System.out.println(buffer);
        return buffer.toString();
    }

    public static String getOmitChar2(String count) {
        if (StringUtils.isNull(count)) {
            return "0";
        }
        StringBuffer buffer = new StringBuffer(count + "");
        int nubm = 0;
        try {
            nubm = Integer.valueOf(count);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (nubm >= 10000) {
            buffer.delete(buffer.length() - 3, buffer.length());
            buffer.insert(buffer.length() - 1, ".");
            buffer.append("万");
        }
        System.out.println(buffer);
        return buffer.toString();
    }

    /**
     * @param str 有空字符就返回 true
     * @return
     */
    public static boolean isNull(String str) {
        if (str == null || str.trim().equals("")) {
            return true;
        }
        return false;
    }

    /**
     * @param str 有空字符就返回 true
     * @return
     */
    public static boolean isNull(CharSequence str) {
        if (str == null || str.toString() == null || str.toString().trim().equals("")) {
            return true;
        }
        return false;
    }

    /**
     * @param str 数组中有空字符就返回 true
     * @return
     */
    public static boolean isNulls(String... str) {
        for (int i = 0; i < str.length; i++) {
            if (str[i] == null || str[i].trim().equals("")) {
                return true;
            }
        }
        return false;
    }

    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern
                .compile("1([\\d]{10})|((\\+[0-9]{2,4})?\\(?[0-9]+\\)?-?)?[0-9]{7,8}");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    public static String toUtf8(String str) {
        try {
            return URLEncoder.encode(str, CHARSET_UTF_8);
        } catch (UnsupportedEncodingException e) {
            try {
                return new String(str.getBytes("UTF-8"), "UTF-8");
            } catch (UnsupportedEncodingException e1) {
            }
        }
        return str;
    }

    public static boolean matchesNmb(String str) {
        if (isNull(str)) return false;
        return str.matches(".*\\d+.*");
    }

    public static String covertUtf8(String str) {
        try {
            return URLEncoder.encode(str, CHARSET_UTF_8);
        } catch (UnsupportedEncodingException e) {
            try {
                return new String(str.getBytes("UTF-8"), "UTF-8");
            } catch (UnsupportedEncodingException e1) {
            }
        }
        return str;
    }

    public static boolean isPassword(String password) {
        Pattern p = Pattern.compile("^[0-9a-zA-Z _-]+$");
        Matcher m = p.matcher(password);
        return m.matches();
    }

    public static boolean isUserNiCheng(String niCheng) {
        String all1 = "^[a-zA-Z0-9_\u4e00-\u9fa5]+$";
        Pattern p = Pattern.compile(all1);
        Matcher m = p.matcher(niCheng);
        return m.matches();
    }



    /**
     * Judge whether a string is whitespace, empty ("") or null.
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0 || str.equalsIgnoreCase("null")) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }
}
