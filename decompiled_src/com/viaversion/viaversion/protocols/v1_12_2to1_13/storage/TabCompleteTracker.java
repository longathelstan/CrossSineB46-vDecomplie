/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_12_2to1_13.storage;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.provider.PlayerLookTargetProvider;
import com.viaversion.viaversion.protocols.v1_12to1_12_1.packet.ServerboundPackets1_12_1;

public class TabCompleteTracker
implements StorableObject {
    private int transactionId;
    private String input;
    private String lastTabComplete;
    private long timeToSend;

    public void sendPacketToServer(UserConnection connection) {
        if (this.lastTabComplete == null || this.timeToSend > System.currentTimeMillis()) {
            return;
        }
        PacketWrapper wrapper = PacketWrapper.create(ServerboundPackets1_12_1.COMMAND_SUGGESTION, null, connection);
        wrapper.write(Types.STRING, this.lastTabComplete);
        wrapper.write(Types.BOOLEAN, false);
        BlockPosition playerLookTarget = Via.getManager().getProviders().get(PlayerLookTargetProvider.class).getPlayerLookTarget(connection);
        wrapper.write(Types.OPTIONAL_POSITION1_8, playerLookTarget);
        wrapper.scheduleSendToServer(Protocol1_12_2To1_13.class);
        this.lastTabComplete = null;
    }

    public int getTransactionId() {
        return this.transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getInput() {
        return this.input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getLastTabComplete() {
        return this.lastTabComplete;
    }

    public void setLastTabComplete(String lastTabComplete) {
        this.lastTabComplete = lastTabComplete;
    }

    public long getTimeToSend() {
        return this.timeToSend;
    }

    public void setTimeToSend(long timeToSend) {
        this.timeToSend = timeToSend;
    }
}

