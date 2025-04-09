/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viarewind.protocol.v1_7_6_10to1_7_2_5.packet;

import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;

public enum ClientboundPackets1_7_2_5 implements ClientboundPacketType
{
    KEEP_ALIVE,
    LOGIN,
    CHAT,
    SET_TIME,
    SET_EQUIPPED_ITEM,
    SET_DEFAULT_SPAWN_POSITION,
    SET_HEALTH,
    RESPAWN,
    PLAYER_POSITION,
    SET_CARRIED_ITEM,
    PLAYER_SLEEP,
    ANIMATE,
    ADD_PLAYER,
    TAKE_ITEM_ENTITY,
    ADD_ENTITY,
    ADD_MOB,
    ADD_PAINTING,
    ADD_EXPERIENCE_ORB,
    SET_ENTITY_MOTION,
    REMOVE_ENTITIES,
    MOVE_ENTITY,
    MOVE_ENTITY_POS,
    MOVE_ENTITY_ROT,
    MOVE_ENTITY_POS_ROT,
    TELEPORT_ENTITY,
    ROTATE_HEAD,
    ENTITY_EVENT,
    SET_ENTITY_LINK,
    SET_ENTITY_DATA,
    UPDATE_MOB_EFFECT,
    REMOVE_MOB_EFFECT,
    SET_EXPERIENCE,
    UPDATE_ATTRIBUTES,
    LEVEL_CHUNK,
    CHUNK_BLOCKS_UPDATE,
    BLOCK_UPDATE,
    BLOCK_EVENT,
    BLOCK_DESTRUCTION,
    MAP_BULK_CHUNK,
    EXPLODE,
    LEVEL_EVENT,
    CUSTOM_SOUND,
    LEVEL_PARTICLES,
    GAME_EVENT,
    ADD_GLOBAL_ENTITY,
    OPEN_SCREEN,
    CONTAINER_CLOSE,
    CONTAINER_SET_SLOT,
    CONTAINER_SET_CONTENT,
    CONTAINER_SET_DATA,
    CONTAINER_ACK,
    UPDATE_SIGN,
    MAP_ITEM_DATA,
    BLOCK_ENTITY_DATA,
    OPEN_SIGN_EDITOR,
    AWARD_STATS,
    PLAYER_INFO,
    PLAYER_ABILITIES,
    COMMAND_SUGGESTIONS,
    SET_OBJECTIVE,
    SET_SCORE,
    SET_DISPLAY_OBJECTIVE,
    SET_PLAYER_TEAM,
    CUSTOM_PAYLOAD,
    DISCONNECT;


    @Override
    public int getId() {
        return this.ordinal();
    }

    @Override
    public String getName() {
        return this.name();
    }
}

