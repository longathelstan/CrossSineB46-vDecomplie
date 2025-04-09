/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.utils.misc;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import kotlin.text.Charsets;
import org.apache.commons.io.IOUtils;

public final class StringUtils {
    private static final Map<String, String> pinyinMap = new HashMap<String, String>();
    private static HashMap<String, String> airCache = new HashMap();
    private static HashMap<String, String> stringCache = new HashMap();

    public static String toCompleteString(String[] args2) {
        return StringUtils.toCompleteString(args2, 0);
    }

    public static String toCompleteString(String[] args2, int start) {
        return StringUtils.toCompleteString(args2, start, " ");
    }

    public static String toCompleteString(String[] args2, int start, String join) {
        if (args2.length <= start) {
            return "";
        }
        return String.join((CharSequence)join, Arrays.copyOfRange(args2, start, args2.length));
    }

    public static String replace(String string, String searchChars, String replaceChars) {
        if (string.isEmpty() || searchChars.isEmpty() || searchChars.equals(replaceChars)) {
            return string;
        }
        if (replaceChars == null) {
            replaceChars = "";
        }
        int stringLength = string.length();
        int searchCharsLength = searchChars.length();
        StringBuilder stringBuilder = new StringBuilder(string);
        for (int i = 0; i < stringLength; ++i) {
            int start = stringBuilder.indexOf(searchChars, i);
            if (start == -1) {
                if (i == 0) {
                    return string;
                }
                return stringBuilder.toString();
            }
            stringBuilder.replace(start, start + searchCharsLength, replaceChars);
        }
        return stringBuilder.toString();
    }

    public static String toPinyin(String inString, String fill) {
        if (pinyinMap.isEmpty()) {
            try {
                String[] dict;
                for (String word : dict = IOUtils.toString((InputStream)StringUtils.class.getClassLoader().getResourceAsStream("assets/minecraft/crosssine/misc/pinyin"), (Charset)Charsets.UTF_8).split(";")) {
                    String[] wordData = word.split(",");
                    pinyinMap.put(wordData[0], wordData[1]);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        String[] strSections = inString.split("");
        StringBuilder result = new StringBuilder();
        boolean lastIsPinyin = false;
        for (String section : strSections) {
            if (pinyinMap.containsKey(section)) {
                result.append(fill);
                result.append(pinyinMap.get(section));
                lastIsPinyin = true;
                continue;
            }
            if (lastIsPinyin) {
                result.append(fill);
            }
            result.append(section);
            lastIsPinyin = false;
        }
        return result.toString();
    }

    public static String injectAirString(String str) {
        if (airCache.containsKey(str)) {
            return airCache.get(str);
        }
        StringBuilder stringBuilder = new StringBuilder();
        boolean hasAdded = false;
        for (char c : str.toCharArray()) {
            stringBuilder.append(c);
            if (!hasAdded) {
                stringBuilder.append('\uf8ff');
            }
            hasAdded = true;
        }
        String result = stringBuilder.toString();
        airCache.put(str, result);
        return result;
    }

    public static String fixString(String str) {
        if (stringCache.containsKey(str)) {
            return stringCache.get(str);
        }
        str = str.replaceAll("\uf8ff", "");
        StringBuilder sb = new StringBuilder();
        for (char c : str.toCharArray()) {
            if (c > '\uff01' && c < '\uff60') {
                sb.append(Character.toChars(c - 65248));
                continue;
            }
            sb.append(c);
        }
        String result = sb.toString();
        stringCache.put(str, result);
        return result;
    }
}

