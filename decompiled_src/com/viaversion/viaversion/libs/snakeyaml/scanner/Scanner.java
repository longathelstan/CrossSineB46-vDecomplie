/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.snakeyaml.scanner;

import com.viaversion.viaversion.libs.snakeyaml.tokens.Token;

public interface Scanner {
    public boolean checkToken(Token.ID ... var1);

    public Token peekToken();

    public Token getToken();

    public void resetDocumentIndex();
}

