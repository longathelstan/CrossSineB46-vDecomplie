/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.beta.b1_7_0_3tob1_8_0_1.storage;

import com.viaversion.viaversion.api.connection.StorableObject;

public class PlayerHealthTracker
implements StorableObject {
    private short health = (short)20;

    public void setHealth(short health) {
        this.health = health;
    }

    public short getHealth() {
        return this.health;
    }
}

