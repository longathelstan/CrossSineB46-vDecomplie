/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.exception;

import java.util.HashMap;
import java.util.Map;

public class InformativeException
extends RuntimeException {
    private final Map<String, Object> info = new HashMap<String, Object>();
    private boolean shouldBePrinted = true;
    private int sources;

    public InformativeException(Throwable cause) {
        super(cause);
    }

    public InformativeException set(String key, Object value) {
        this.info.put(key, value);
        return this;
    }

    public InformativeException addSource(Class<?> sourceClazz) {
        int n = this.sources++;
        return this.set("Source " + n, this.getSource(sourceClazz));
    }

    private String getSource(Class<?> sourceClazz) {
        String string;
        if (sourceClazz.isAnonymousClass()) {
            String string2 = sourceClazz.getName();
            string = string2 + " (Anonymous)";
        } else {
            string = sourceClazz.getName();
        }
        return string;
    }

    public boolean shouldBePrinted() {
        return this.shouldBePrinted;
    }

    public void setShouldBePrinted(boolean shouldBePrinted) {
        this.shouldBePrinted = shouldBePrinted;
    }

    @Override
    public String getMessage() {
        StringBuilder builder = new StringBuilder("Please report this on the Via support Discord or open an issue on the relevant GitHub repository\n");
        boolean first = true;
        for (Map.Entry<String, Object> entry : this.info.entrySet()) {
            if (!first) {
                builder.append(", ");
            }
            builder.append(entry.getKey()).append(": ").append(entry.getValue());
            first = false;
        }
        return builder.toString();
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}

