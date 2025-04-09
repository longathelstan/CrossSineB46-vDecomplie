/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.protocol.api;

import net.raphimc.vialoader.impl.viaversion.VLInjector;

public class ProtocolVLInjector
extends VLInjector {
    @Override
    public String getDecoderName() {
        return "via-decoder";
    }

    @Override
    public String getEncoderName() {
        return "via-encoder";
    }
}

