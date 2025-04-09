/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_7_2_5tor1_7_6_10.packet;

import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;

public enum ServerboundPackets1_7_2 implements ServerboundPacketType
{
    KEEP_ALIVE,
    CHAT,
    INTERACT,
    MOVE_PLAYER_STATUS_ONLY,
    MOVE_PLAYER_POS,
    MOVE_PLAYER_ROT,
    MOVE_PLAYER_POS_ROT,
    PLAYER_ACTION,
    USE_ITEM_ON,
    SET_CARRIED_ITEM,
    SWING,
    PLAYER_COMMAND,
    PLAYER_INPUT,
    CONTAINER_CLOSE,
    CONTAINER_CLICK,
    CONTAINER_ACK,
    SET_CREATIVE_MODE_SLOT,
    CONTAINER_BUTTON_CLICK,
    SIGN_UPDATE,
    PLAYER_ABILITIES,
    COMMAND_SUGGESTION,
    CLIENT_INFORMATION,
    CLIENT_COMMAND,
    CUSTOM_PAYLOAD;


    @Override
    public int getId() {
        return this.ordinal();
    }

    @Override
    public String getName() {
        return this.name();
    }
}

