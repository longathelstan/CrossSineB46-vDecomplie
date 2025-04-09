/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.storage;

import com.viaversion.viaversion.api.connection.StoredObject;
import com.viaversion.viaversion.api.connection.UserConnection;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.storage.ClassicOpLevelStorage;

public class ClassicServerTitleStorage
extends StoredObject {
    private final String title;
    private final String motd;

    public ClassicServerTitleStorage(UserConnection user, String title2, String motd) {
        super(user);
        this.title = title2;
        this.motd = motd;
    }

    public String getTitle() {
        return this.title;
    }

    public String getMotd() {
        return this.motd;
    }

    public boolean isHaxEnabled() {
        return this.motd.contains("+hax");
    }

    public boolean isHaxDisabled() {
        return this.motd.contains("-hax");
    }

    public boolean isFlyEnabled() {
        return this.motd.contains("+fly");
    }

    public boolean isFlyDisabled() {
        return this.motd.contains("-fly");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean isFlyEffectivelyEnabled() {
        boolean isOp;
        boolean bl = isOp = this.user().get(ClassicOpLevelStorage.class).getOpLevel() >= 100;
        if (this.isHaxDisabled()) {
            if (this.isFlyEnabled()) {
                return true;
            }
        } else if (!this.isFlyDisabled()) return true;
        if (!isOp) return false;
        if (!this.isOphaxEnabled()) return false;
        return true;
    }

    public boolean isNoclipEnabled() {
        return this.motd.contains("+noclip");
    }

    public boolean isNoclipDisabled() {
        return this.motd.contains("-noclip");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean isNoclipEffectivelyEnabled() {
        boolean isOp;
        boolean bl = isOp = this.user().get(ClassicOpLevelStorage.class).getOpLevel() >= 100;
        if (this.isHaxDisabled()) {
            if (this.isNoclipEnabled()) {
                return true;
            }
        } else if (!this.isNoclipDisabled()) return true;
        if (!isOp) return false;
        if (!this.isOphaxEnabled()) return false;
        return true;
    }

    public boolean isRespawnEnabled() {
        return this.motd.contains("+respawn");
    }

    public boolean isRespawnDisabled() {
        return this.motd.contains("-respawn");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean isRespawnEffectivelyEnabled() {
        boolean isOp;
        boolean bl = isOp = this.user().get(ClassicOpLevelStorage.class).getOpLevel() >= 100;
        if (this.isHaxDisabled()) {
            if (this.isRespawnEnabled()) {
                return true;
            }
        } else if (!this.isRespawnDisabled()) return true;
        if (!isOp) return false;
        if (!this.isOphaxEnabled()) return false;
        return true;
    }

    public boolean isSpeedEnabled() {
        return this.motd.contains("+speed");
    }

    public boolean isSpeedDisabled() {
        return this.motd.contains("-speed");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean isSpeedEffectivelyEnabled() {
        boolean isOp;
        boolean bl = isOp = this.user().get(ClassicOpLevelStorage.class).getOpLevel() >= 100;
        if (this.isHaxDisabled()) {
            if (this.isSpeedEnabled()) {
                return true;
            }
        } else if (!this.isSpeedDisabled()) return true;
        if (!isOp) return false;
        if (!this.isOphaxEnabled()) return false;
        return true;
    }

    public boolean isOphaxEnabled() {
        return this.motd.contains("+ophax");
    }
}

