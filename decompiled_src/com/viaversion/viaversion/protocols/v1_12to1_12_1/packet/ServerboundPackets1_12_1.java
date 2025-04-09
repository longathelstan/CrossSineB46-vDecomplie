/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_12to1_12_1.packet;

import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;

public enum ServerboundPackets1_12_1 implements ServerboundPacketType
{
    ACCEPT_TELEPORTATION,
    COMMAND_SUGGESTION,
    CHAT,
    CLIENT_COMMAND,
    CLIENT_INFORMATION,
    CONTAINER_ACK,
    CONTAINER_BUTTON_CLICK,
    CONTAINER_CLICK,
    CONTAINER_CLOSE,
    CUSTOM_PAYLOAD,
    INTERACT,
    KEEP_ALIVE,
    MOVE_PLAYER_STATUS_ONLY,
    MOVE_PLAYER_POS,
    MOVE_PLAYER_POS_ROT,
    MOVE_PLAYER_ROT,
    MOVE_VEHICLE,
    PADDLE_BOAT,
    PLACE_RECIPE,
    PLAYER_ABILITIES,
    PLAYER_ACTION,
    PLAYER_COMMAND,
    PLAYER_INPUT,
    RECIPE_BOOK_UPDATE,
    RESOURCE_PACK,
    SEEN_ADVANCEMENTS,
    SET_CARRIED_ITEM,
    SET_CREATIVE_MODE_SLOT,
    SIGN_UPDATE,
    SWING,
    TELEPORT_TO_ENTITY,
    USE_ITEM_ON,
    USE_ITEM;


    @Override
    public int getId() {
        return this.ordinal();
    }

    @Override
    public String getName() {
        return this.name();
    }
}

