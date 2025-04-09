/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.snakeyaml.tokens;

import com.viaversion.viaversion.libs.snakeyaml.error.Mark;
import com.viaversion.viaversion.libs.snakeyaml.tokens.TagTuple;
import com.viaversion.viaversion.libs.snakeyaml.tokens.Token;

public final class TagToken
extends Token {
    private final TagTuple value;

    public TagToken(TagTuple value, Mark startMark, Mark endMark) {
        super(startMark, endMark);
        this.value = value;
    }

    public TagTuple getValue() {
        return this.value;
    }

    @Override
    public Token.ID getTokenId() {
        return Token.ID.Tag;
    }
}

