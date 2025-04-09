/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.beta.b1_2_0_2tob1_3_0_1.storage;

import com.viaversion.viaversion.api.connection.StoredObject;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import net.raphimc.vialegacy.protocol.beta.b1_2_0_2tob1_3_0_1.Protocolb1_2_0_2Tob1_3_0_1;

public class BlockDigStorage
extends StoredObject {
    public int tick = 1;
    private final BlockPosition position;
    private final short facing;

    public BlockDigStorage(UserConnection user, BlockPosition position, short facing) {
        super(user);
        this.position = position;
        this.facing = facing;
    }

    public void tick() {
        if (this.tick >= 5) {
            Protocolb1_2_0_2Tob1_3_0_1.sendBlockDigPacket(this.user(), (short)0, this.position, this.facing);
            this.tick = 0;
        } else {
            ++this.tick;
        }
        Protocolb1_2_0_2Tob1_3_0_1.sendBlockDigPacket(this.user(), (short)1, this.position, this.facing);
    }
}

