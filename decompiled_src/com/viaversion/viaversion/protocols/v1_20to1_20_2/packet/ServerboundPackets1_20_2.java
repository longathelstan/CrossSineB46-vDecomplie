/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_20to1_20_2.packet;

import com.viaversion.viaversion.protocols.v1_20to1_20_2.packet.ServerboundPacket1_20_2;

public enum ServerboundPackets1_20_2 implements ServerboundPacket1_20_2
{
    ACCEPT_TELEPORTATION,
    BLOCK_ENTITY_TAG_QUERY,
    CHANGE_DIFFICULTY,
    CHAT_ACK,
    CHAT_COMMAND,
    CHAT,
    CHAT_SESSION_UPDATE,
    CHUNK_BATCH_RECEIVED,
    CLIENT_COMMAND,
    CLIENT_INFORMATION,
    COMMAND_SUGGESTION,
    CONFIGURATION_ACKNOWLEDGED,
    CONTAINER_BUTTON_CLICK,
    CONTAINER_CLICK,
    CONTAINER_CLOSE,
    CUSTOM_PAYLOAD,
    EDIT_BOOK,
    ENTITY_TAG_QUERY,
    INTERACT,
    JIGSAW_GENERATE,
    KEEP_ALIVE,
    LOCK_DIFFICULTY,
    MOVE_PLAYER_POS,
    MOVE_PLAYER_POS_ROT,
    MOVE_PLAYER_ROT,
    MOVE_PLAYER_STATUS_ONLY,
    MOVE_VEHICLE,
    PADDLE_BOAT,
    PICK_ITEM,
    PING_REQUEST,
    PLACE_RECIPE,
    PLAYER_ABILITIES,
    PLAYER_ACTION,
    PLAYER_COMMAND,
    PLAYER_INPUT,
    PONG,
    RECIPE_BOOK_CHANGE_SETTINGS,
    RECIPE_BOOK_SEEN_RECIPE,
    RENAME_ITEM,
    RESOURCE_PACK,
    SEEN_ADVANCEMENTS,
    SELECT_TRADE,
    SET_BEACON,
    SET_CARRIED_ITEM,
    SET_COMMAND_BLOCK,
    SET_COMMAND_MINECART,
    SET_CREATIVE_MODE_SLOT,
    SET_JIGSAW_BLOCK,
    SET_STRUCTURE_BLOCK,
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

