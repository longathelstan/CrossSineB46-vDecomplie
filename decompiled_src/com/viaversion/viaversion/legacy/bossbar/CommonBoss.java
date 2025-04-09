/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.legacy.bossbar;

import com.google.common.base.Preconditions;
import com.google.common.collect.MapMaker;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.legacy.bossbar.BossBar;
import com.viaversion.viaversion.api.legacy.bossbar.BossColor;
import com.viaversion.viaversion.api.legacy.bossbar.BossFlag;
import com.viaversion.viaversion.api.legacy.bossbar.BossStyle;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.libs.gson.JsonParser;
import com.viaversion.viaversion.protocols.v1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viaversion.protocols.v1_8to1_9.packet.ClientboundPackets1_9;
import com.viaversion.viaversion.util.ComponentUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;

public class CommonBoss
implements BossBar {
    final UUID uuid;
    final Map<UUID, UserConnection> connections;
    final Set<BossFlag> flags;
    String title;
    float health;
    BossColor color;
    BossStyle style;
    boolean visible;

    public CommonBoss(String title2, float health, BossColor color, BossStyle style) {
        Preconditions.checkNotNull((Object)title2, (Object)"Title cannot be null");
        float f = health;
        Preconditions.checkArgument((health >= 0.0f && health <= 1.0f ? 1 : 0) != 0, (Object)("Health must be between 0 and 1. Input: " + f));
        this.uuid = UUID.randomUUID();
        this.title = title2;
        this.health = health;
        this.color = color == null ? BossColor.PURPLE : color;
        this.style = style == null ? BossStyle.SOLID : style;
        this.connections = new MapMaker().weakValues().makeMap();
        this.flags = EnumSet.noneOf(BossFlag.class);
        this.visible = true;
    }

    @Override
    public BossBar setTitle(String title2) {
        Preconditions.checkNotNull((Object)title2);
        this.title = title2;
        this.sendPacket(UpdateAction.UPDATE_TITLE);
        return this;
    }

    @Override
    public BossBar setHealth(float health) {
        float f = health;
        Preconditions.checkArgument((health >= 0.0f && health <= 1.0f ? 1 : 0) != 0, (Object)("Health must be between 0 and 1. Input: " + f));
        this.health = health;
        this.sendPacket(UpdateAction.UPDATE_HEALTH);
        return this;
    }

    @Override
    public BossColor getColor() {
        return this.color;
    }

    @Override
    public BossBar setColor(BossColor color) {
        Preconditions.checkNotNull((Object)((Object)color));
        this.color = color;
        this.sendPacket(UpdateAction.UPDATE_STYLE);
        return this;
    }

    @Override
    public BossBar setStyle(BossStyle style) {
        Preconditions.checkNotNull((Object)((Object)style));
        this.style = style;
        this.sendPacket(UpdateAction.UPDATE_STYLE);
        return this;
    }

    @Override
    public BossBar addPlayer(UUID player) {
        UserConnection client = Via.getManager().getConnectionManager().getConnectedClient(player);
        if (client != null) {
            this.addConnection(client);
        }
        return this;
    }

    @Override
    public BossBar addConnection(UserConnection conn) {
        if (this.connections.put(conn.getProtocolInfo().getUuid(), conn) == null && this.visible) {
            this.sendPacketConnection(conn, this.getPacket(UpdateAction.ADD, conn));
        }
        return this;
    }

    @Override
    public BossBar removePlayer(UUID uuid) {
        UserConnection client = this.connections.remove(uuid);
        if (client != null) {
            this.sendPacketConnection(client, this.getPacket(UpdateAction.REMOVE, client));
        }
        return this;
    }

    @Override
    public BossBar removeConnection(UserConnection conn) {
        this.removePlayer(conn.getProtocolInfo().getUuid());
        return this;
    }

    @Override
    public BossBar addFlag(BossFlag flag) {
        Preconditions.checkNotNull((Object)((Object)flag));
        if (!this.hasFlag(flag)) {
            this.flags.add(flag);
        }
        this.sendPacket(UpdateAction.UPDATE_FLAGS);
        return this;
    }

    @Override
    public BossBar removeFlag(BossFlag flag) {
        Preconditions.checkNotNull((Object)((Object)flag));
        if (this.hasFlag(flag)) {
            this.flags.remove((Object)flag);
        }
        this.sendPacket(UpdateAction.UPDATE_FLAGS);
        return this;
    }

    @Override
    public boolean hasFlag(BossFlag flag) {
        Preconditions.checkNotNull((Object)((Object)flag));
        return this.flags.contains((Object)flag);
    }

    @Override
    public Set<UUID> getPlayers() {
        return Collections.unmodifiableSet(this.connections.keySet());
    }

    @Override
    public Set<UserConnection> getConnections() {
        return Collections.unmodifiableSet(new HashSet<UserConnection>(this.connections.values()));
    }

    @Override
    public BossBar show() {
        this.setVisible(true);
        return this;
    }

    @Override
    public BossBar hide() {
        this.setVisible(false);
        return this;
    }

    @Override
    public boolean isVisible() {
        return this.visible;
    }

    void setVisible(boolean value) {
        if (this.visible != value) {
            this.visible = value;
            this.sendPacket(value ? UpdateAction.ADD : UpdateAction.REMOVE);
        }
    }

    @Override
    public UUID getId() {
        return this.uuid;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public float getHealth() {
        return this.health;
    }

    @Override
    public BossStyle getStyle() {
        return this.style;
    }

    public Set<BossFlag> getFlags() {
        return this.flags;
    }

    void sendPacket(UpdateAction action) {
        for (UserConnection conn : new ArrayList<UserConnection>(this.connections.values())) {
            PacketWrapper wrapper = this.getPacket(action, conn);
            this.sendPacketConnection(conn, wrapper);
        }
    }

    void sendPacketConnection(UserConnection conn, PacketWrapper wrapper) {
        if (conn.getProtocolInfo() == null || !conn.getProtocolInfo().getPipeline().contains(Protocol1_8To1_9.class)) {
            this.connections.remove(conn.getProtocolInfo().getUuid());
            return;
        }
        try {
            wrapper.scheduleSend(Protocol1_8To1_9.class);
        }
        catch (Exception e) {
            Via.getPlatform().getLogger().log(Level.WARNING, "Failed to send bossbar packet", e);
        }
    }

    PacketWrapper getPacket(UpdateAction action, UserConnection connection) {
        try {
            PacketWrapper wrapper = PacketWrapper.create(ClientboundPackets1_9.BOSS_EVENT, null, connection);
            wrapper.write(Types.UUID, this.uuid);
            wrapper.write(Types.VAR_INT, action.getId());
            switch (action) {
                case ADD: {
                    try {
                        wrapper.write(Types.COMPONENT, JsonParser.parseString(this.title));
                    }
                    catch (Exception e) {
                        wrapper.write(Types.COMPONENT, ComponentUtil.plainToJson(this.title));
                    }
                    wrapper.write(Types.FLOAT, Float.valueOf(this.health));
                    wrapper.write(Types.VAR_INT, this.color.getId());
                    wrapper.write(Types.VAR_INT, this.style.getId());
                    wrapper.write(Types.BYTE, (byte)this.flagToBytes());
                    break;
                }
                case REMOVE: {
                    break;
                }
                case UPDATE_HEALTH: {
                    wrapper.write(Types.FLOAT, Float.valueOf(this.health));
                    break;
                }
                case UPDATE_TITLE: {
                    try {
                        wrapper.write(Types.COMPONENT, JsonParser.parseString(this.title));
                    }
                    catch (Exception e) {
                        wrapper.write(Types.COMPONENT, ComponentUtil.plainToJson(this.title));
                    }
                    break;
                }
                case UPDATE_STYLE: {
                    wrapper.write(Types.VAR_INT, this.color.getId());
                    wrapper.write(Types.VAR_INT, this.style.getId());
                    break;
                }
                case UPDATE_FLAGS: {
                    wrapper.write(Types.BYTE, (byte)this.flagToBytes());
                }
            }
            return wrapper;
        }
        catch (Exception e) {
            Via.getPlatform().getLogger().log(Level.WARNING, "Failed to create bossbar packet", e);
            return null;
        }
    }

    int flagToBytes() {
        int bitmask = 0;
        for (BossFlag flag : this.flags) {
            bitmask |= flag.getId();
        }
        return bitmask;
    }

    private static enum UpdateAction {
        ADD(0),
        REMOVE(1),
        UPDATE_HEALTH(2),
        UPDATE_TITLE(3),
        UPDATE_STYLE(4),
        UPDATE_FLAGS(5);

        final int id;

        UpdateAction(int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }
    }
}

