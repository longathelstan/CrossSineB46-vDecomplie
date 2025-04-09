/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.snakeyaml.tokens;

import com.viaversion.viaversion.libs.snakeyaml.comments.CommentType;
import com.viaversion.viaversion.libs.snakeyaml.error.Mark;
import com.viaversion.viaversion.libs.snakeyaml.tokens.Token;
import java.util.Objects;

public final class CommentToken
extends Token {
    private final CommentType type;
    private final String value;

    public CommentToken(CommentType type, String value, Mark startMark, Mark endMark) {
        super(startMark, endMark);
        Objects.requireNonNull(type);
        this.type = type;
        Objects.requireNonNull(value);
        this.value = value;
    }

    public CommentType getCommentType() {
        return this.type;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public Token.ID getTokenId() {
        return Token.ID.Comment;
    }
}

