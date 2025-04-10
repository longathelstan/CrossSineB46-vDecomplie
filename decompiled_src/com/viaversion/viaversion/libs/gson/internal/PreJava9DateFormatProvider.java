/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.gson.internal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class PreJava9DateFormatProvider {
    private PreJava9DateFormatProvider() {
    }

    public static DateFormat getUsDateTimeFormat(int dateStyle, int timeStyle) {
        String pattern = PreJava9DateFormatProvider.getDatePartOfDateTimePattern(dateStyle) + " " + PreJava9DateFormatProvider.getTimePartOfDateTimePattern(timeStyle);
        return new SimpleDateFormat(pattern, Locale.US);
    }

    private static String getDatePartOfDateTimePattern(int dateStyle) {
        switch (dateStyle) {
            case 3: {
                return "M/d/yy";
            }
            case 2: {
                return "MMM d, yyyy";
            }
            case 1: {
                return "MMMM d, yyyy";
            }
            case 0: {
                return "EEEE, MMMM d, yyyy";
            }
        }
        throw new IllegalArgumentException("Unknown DateFormat style: " + dateStyle);
    }

    private static String getTimePartOfDateTimePattern(int timeStyle) {
        switch (timeStyle) {
            case 3: {
                return "h:mm a";
            }
            case 2: {
                return "h:mm:ss a";
            }
            case 0: 
            case 1: {
                return "h:mm:ss a z";
            }
        }
        throw new IllegalArgumentException("Unknown DateFormat style: " + timeStyle);
    }
}

