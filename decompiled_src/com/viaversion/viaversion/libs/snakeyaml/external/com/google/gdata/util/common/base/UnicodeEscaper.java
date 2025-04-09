/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.snakeyaml.external.com.google.gdata.util.common.base;

import com.viaversion.viaversion.libs.snakeyaml.external.com.google.gdata.util.common.base.Escaper;
import java.io.IOException;

public abstract class UnicodeEscaper
implements Escaper {
    private static final int DEST_PAD = 32;
    private static final ThreadLocal<char[]> DEST_TL = new ThreadLocal<char[]>(){

        @Override
        protected char[] initialValue() {
            return new char[1024];
        }
    };

    protected abstract char[] escape(int var1);

    protected int nextEscapeIndex(CharSequence csq, int start, int end) {
        int index2;
        int cp;
        for (index2 = start; index2 < end && (cp = UnicodeEscaper.codePointAt(csq, index2, end)) >= 0 && this.escape(cp) == null; index2 += Character.isSupplementaryCodePoint(cp) ? 2 : 1) {
        }
        return index2;
    }

    @Override
    public String escape(String string) {
        int end = string.length();
        int index2 = this.nextEscapeIndex(string, 0, end);
        return index2 == end ? string : this.escapeSlow(string, index2);
    }

    protected final String escapeSlow(String s, int index2) {
        int end = s.length();
        char[] dest = DEST_TL.get();
        int destIndex = 0;
        int unescapedChunkStart = 0;
        while (index2 < end) {
            int cp = UnicodeEscaper.codePointAt(s, index2, end);
            if (cp < 0) {
                throw new IllegalArgumentException("Trailing high surrogate at end of input");
            }
            char[] escaped = this.escape(cp);
            if (escaped != null) {
                int charsSkipped = index2 - unescapedChunkStart;
                int sizeNeeded = destIndex + charsSkipped + escaped.length;
                if (dest.length < sizeNeeded) {
                    int destLength = sizeNeeded + (end - index2) + 32;
                    dest = UnicodeEscaper.growBuffer(dest, destIndex, destLength);
                }
                if (charsSkipped > 0) {
                    s.getChars(unescapedChunkStart, index2, dest, destIndex);
                    destIndex += charsSkipped;
                }
                if (escaped.length > 0) {
                    System.arraycopy(escaped, 0, dest, destIndex, escaped.length);
                    destIndex += escaped.length;
                }
            }
            unescapedChunkStart = index2 + (Character.isSupplementaryCodePoint(cp) ? 2 : 1);
            index2 = this.nextEscapeIndex(s, unescapedChunkStart, end);
        }
        int charsSkipped = end - unescapedChunkStart;
        if (charsSkipped > 0) {
            int endIndex = destIndex + charsSkipped;
            if (dest.length < endIndex) {
                dest = UnicodeEscaper.growBuffer(dest, destIndex, endIndex);
            }
            s.getChars(unescapedChunkStart, end, dest, destIndex);
            destIndex = endIndex;
        }
        return new String(dest, 0, destIndex);
    }

    @Override
    public Appendable escape(final Appendable out) {
        assert (out != null);
        return new Appendable(){
            int pendingHighSurrogate = -1;
            final char[] decodedChars = new char[2];

            @Override
            public Appendable append(CharSequence csq) throws IOException {
                return this.append(csq, 0, csq.length());
            }

            @Override
            public Appendable append(CharSequence csq, int start, int end) throws IOException {
                int index2 = start;
                if (index2 < end) {
                    char[] escaped;
                    int unescapedChunkStart = index2;
                    if (this.pendingHighSurrogate != -1) {
                        char c;
                        if (!Character.isLowSurrogate(c = csq.charAt(index2++))) {
                            throw new IllegalArgumentException("Expected low surrogate character but got " + c);
                        }
                        escaped = UnicodeEscaper.this.escape(Character.toCodePoint((char)this.pendingHighSurrogate, c));
                        if (escaped != null) {
                            this.outputChars(escaped, escaped.length);
                            ++unescapedChunkStart;
                        } else {
                            out.append((char)this.pendingHighSurrogate);
                        }
                        this.pendingHighSurrogate = -1;
                    }
                    while (true) {
                        if ((index2 = UnicodeEscaper.this.nextEscapeIndex(csq, index2, end)) > unescapedChunkStart) {
                            out.append(csq, unescapedChunkStart, index2);
                        }
                        if (index2 == end) break;
                        int cp = UnicodeEscaper.codePointAt(csq, index2, end);
                        if (cp < 0) {
                            this.pendingHighSurrogate = -cp;
                            break;
                        }
                        escaped = UnicodeEscaper.this.escape(cp);
                        if (escaped != null) {
                            this.outputChars(escaped, escaped.length);
                        } else {
                            int len = Character.toChars(cp, this.decodedChars, 0);
                            this.outputChars(this.decodedChars, len);
                        }
                        unescapedChunkStart = index2 += Character.isSupplementaryCodePoint(cp) ? 2 : 1;
                    }
                }
                return this;
            }

            @Override
            public Appendable append(char c) throws IOException {
                if (this.pendingHighSurrogate != -1) {
                    if (!Character.isLowSurrogate(c)) {
                        throw new IllegalArgumentException("Expected low surrogate character but got '" + c + "' with value " + c);
                    }
                    char[] escaped = UnicodeEscaper.this.escape(Character.toCodePoint((char)this.pendingHighSurrogate, c));
                    if (escaped != null) {
                        this.outputChars(escaped, escaped.length);
                    } else {
                        out.append((char)this.pendingHighSurrogate);
                        out.append(c);
                    }
                    this.pendingHighSurrogate = -1;
                } else if (Character.isHighSurrogate(c)) {
                    this.pendingHighSurrogate = c;
                } else {
                    if (Character.isLowSurrogate(c)) {
                        throw new IllegalArgumentException("Unexpected low surrogate character '" + c + "' with value " + c);
                    }
                    char[] escaped = UnicodeEscaper.this.escape(c);
                    if (escaped != null) {
                        this.outputChars(escaped, escaped.length);
                    } else {
                        out.append(c);
                    }
                }
                return this;
            }

            private void outputChars(char[] chars, int len) throws IOException {
                for (int n = 0; n < len; ++n) {
                    out.append(chars[n]);
                }
            }
        };
    }

    protected static final int codePointAt(CharSequence seq, int index2, int end) {
        if (index2 < end) {
            char c1;
            if ((c1 = seq.charAt(index2++)) < '\ud800' || c1 > '\udfff') {
                return c1;
            }
            if (c1 <= '\udbff') {
                if (index2 == end) {
                    return -c1;
                }
                char c2 = seq.charAt(index2);
                if (Character.isLowSurrogate(c2)) {
                    return Character.toCodePoint(c1, c2);
                }
                throw new IllegalArgumentException("Expected low surrogate but got char '" + c2 + "' with value " + c2 + " at index " + index2);
            }
            throw new IllegalArgumentException("Unexpected low surrogate character '" + c1 + "' with value " + c1 + " at index " + (index2 - 1));
        }
        throw new IndexOutOfBoundsException("Index exceeds specified range");
    }

    private static final char[] growBuffer(char[] dest, int index2, int size) {
        char[] copy = new char[size];
        if (index2 > 0) {
            System.arraycopy(dest, 0, copy, 0, index2);
        }
        return copy;
    }
}

