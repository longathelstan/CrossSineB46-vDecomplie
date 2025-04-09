/*
 * Decompiled with CFR 0.152.
 */
package jdk.nashorn.api.scripting;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class Formatter {
    private static final String formatSpecifier = "%(\\d+\\$)?([-#+ 0,(\\<]*)?(\\d+)?(\\.\\d+)?([tT])?([a-zA-Z%])";
    private static final Pattern FS_PATTERN = Pattern.compile("%(\\d+\\$)?([-#+ 0,(\\<]*)?(\\d+)?(\\.\\d+)?([tT])?([a-zA-Z%])");

    private Formatter() {
    }

    static String format(String format, Object[] args2) {
        Matcher m2 = FS_PATTERN.matcher(format);
        int positionalParameter = 1;
        while (m2.find()) {
            int index2 = Formatter.index(m2.group(1));
            boolean previous = Formatter.isPreviousArgument(m2.group(2));
            char conversion = m2.group(6).charAt(0);
            if (index2 < 0 || previous || conversion == 'n' || conversion == '%') continue;
            if (index2 == 0) {
                index2 = positionalParameter++;
            }
            if (index2 > args2.length) continue;
            Object arg = args2[index2 - 1];
            if (m2.group(5) != null) {
                if (!(arg instanceof Double)) continue;
                args2[index2 - 1] = ((Double)arg).longValue();
                continue;
            }
            switch (conversion) {
                case 'X': 
                case 'd': 
                case 'o': 
                case 'x': {
                    if (arg instanceof Double) {
                        args2[index2 - 1] = ((Double)arg).longValue();
                        break;
                    }
                    if (!(arg instanceof String) || ((String)arg).length() <= 0) break;
                    args2[index2 - 1] = (int)((String)arg).charAt(0);
                    break;
                }
                case 'A': 
                case 'E': 
                case 'G': 
                case 'a': 
                case 'e': 
                case 'f': 
                case 'g': {
                    if (!(arg instanceof Integer)) break;
                    args2[index2 - 1] = ((Integer)arg).doubleValue();
                    break;
                }
                case 'c': {
                    if (arg instanceof Double) {
                        args2[index2 - 1] = ((Double)arg).intValue();
                        break;
                    }
                    if (!(arg instanceof String) || ((String)arg).length() <= 0) break;
                    args2[index2 - 1] = (int)((String)arg).charAt(0);
                    break;
                }
            }
        }
        return String.format(format, args2);
    }

    private static int index(String s) {
        int index2 = -1;
        if (s != null) {
            try {
                index2 = Integer.parseInt(s.substring(0, s.length() - 1));
            }
            catch (NumberFormatException numberFormatException) {}
        } else {
            index2 = 0;
        }
        return index2;
    }

    private static boolean isPreviousArgument(String s) {
        return s != null && s.indexOf(60) >= 0;
    }
}

