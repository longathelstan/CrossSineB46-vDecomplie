/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.util;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.util.ProtocolUtil;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProtocolLogger {
    private final Logger logger;
    private final String name;

    public ProtocolLogger(Class<? extends Protocol> protocol) {
        this(Via.getPlatform().getLogger(), protocol);
    }

    public ProtocolLogger(Logger logger, Class<? extends Protocol> protocol) {
        this.logger = logger;
        this.name = ProtocolUtil.toNiceName(protocol);
    }

    public void log(Level level, String msg) {
        this.logger.log(level, this.formatMessage(msg));
    }

    public void log(Level level, String msg, Throwable thrown) {
        this.logger.log(level, this.formatMessage(msg), thrown);
    }

    public void warning(String msg) {
        this.logger.warning(this.formatMessage(msg));
    }

    public void severe(String msg) {
        this.logger.severe(this.formatMessage(msg));
    }

    private String formatMessage(String msg) {
        String string = msg;
        String string2 = this.name;
        return "(" + string2 + ") " + string;
    }
}

