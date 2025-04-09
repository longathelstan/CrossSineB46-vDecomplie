/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.snakeyaml.tokens;

import com.viaversion.viaversion.libs.snakeyaml.error.Mark;
import com.viaversion.viaversion.libs.snakeyaml.tokens.Token;

public final class AliasToken
extends Token {
    private final String value;

    public AliasToken(String value, Mark startMark, Mark endMark) {
        super(startMark, endMark);
        if (value == null) {
            throw new NullPointerException("alias is expected");
        }
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public Token.ID getTokenId() {
        return Token.ID.Alias;
    }
}

