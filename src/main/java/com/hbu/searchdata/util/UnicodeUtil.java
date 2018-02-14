package com.hbu.searchdata.util;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Unicode工具
 */
public class UnicodeUtil {
    /*
     * 中文转unicode编码
     */
    public static String gbEncoding(final String gbString) {
        char[] utfBytes = gbString.toCharArray();
        String unicodeBytes = "";
        for (int i = 0; i < utfBytes.length; i++) {
            String hexB = Integer.toHexString(utfBytes[i]);
            if (hexB.length() <= 2) {
                hexB = "00" + hexB;
            }
            unicodeBytes = unicodeBytes + "\\u" + hexB;
        }
        return unicodeBytes;
    }

    /*
     * unicode编码转中文
     */
    public static String decodeUnicode(final String dataStr) {
        char chars[] = filterEmoji(dataStr).toCharArray();
        final StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '\\' && chars[i + 1] == 'u'){
                String charStr = dataStr.substring(i + 2, i + 6);
                char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。
                buffer.append(Character.toString(letter));
                i += 5;
            }
            else
                buffer.append(chars[i]);
        }

        //去除emoji
        return filterEmoji(buffer.toString());
    }
    private static String filterEmoji(String source) {
        if(source != null)
        {
            Pattern emoji = Pattern.compile ("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",Pattern.UNICODE_CASE | Pattern . CASE_INSENSITIVE ) ;
            Matcher emojiMatcher = emoji.matcher(source);
            if ( emojiMatcher.find())
            {
                source = emojiMatcher.replaceAll("*");
                return source ;
            }
            return source;
        }
        return source;
    }
    public static void main(String args[])
    {
        String s="\\u5218\\u56fd\\u6881\\u7684\\u7092\\u4f5c\\u5df2\\u8ba9\\u81ea\\u5df1\\u4e07\\u52ab\\u4e0d\\u590d";
        System.out.println(UnicodeUtil.decodeUnicode(s));
        System.out.println(UnicodeUtil.decodeUnicode(123456+"哈哈哈"));
    }
}
