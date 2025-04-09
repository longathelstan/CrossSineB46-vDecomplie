/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_8to1_9.packet;

import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;

public enum ServerboundPackets1_8 implements ServerboundPacketType
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
    CUSTOM_PAYLOAD,
    TELEPORT_TO_ENTITY,
    RESOURCE_PACK;


    @Override
    public int getId() {
        return this.ordinal();
    }

    @Override
    public String getName() {
        return this.name();
    }
}

