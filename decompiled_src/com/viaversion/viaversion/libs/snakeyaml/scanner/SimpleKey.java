/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.snakeyaml.scanner;

import com.viaversion.viaversion.libs.snakeyaml.error.Mark;

final class SimpleKey {
    private final int tokenNumber;
    private final boolean required;
    private final int index;
    private final int line;
    private final int column;
    private final Mark mark;

    public SimpleKey(int tokenNumber, boolean required, int index2, int line, int column, Mark mark) {
        this.tokenNumber = tokenNumber;
        this.required = required;
        this.index = index2;
        this.line = line;
        this.column = column;
        this.mark = mark;
    }

    public int getTokenNumber() {
        return this.tokenNumber;
    }

    public int getColumn() {
        return this.column;
    }

    public Mark getMark() {
        return this.mark;
    }

    public int getIndex() {
        return this.index;
    }

    public int getLine() {
        return this.line;
    }

    public boolean isRequired() {
        return this.required;
    }

    public String toString() {
        return "SimpleKey - tokenNumber=" + this.tokenNumber + " required=" + this.required + " index=" + this.index + " line=" + this.line + " column=" + this.column;
    }
}

