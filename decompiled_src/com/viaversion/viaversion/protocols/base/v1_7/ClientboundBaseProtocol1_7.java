/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.base.v1_7;

import com.google.common.base.Joiner;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.ProtocolInfo;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.ProtocolPathEntry;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.packet.provider.PacketTypesProvider;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.protocol.version.VersionProvider;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonParseException;
import com.viaversion.viaversion.protocol.ProtocolManagerImpl;
import com.viaversion.viaversion.protocol.ServerProtocolVersionSingleton;
import com.viaversion.viaversion.protocols.base.ClientboundLoginPackets;
import com.viaversion.viaversion.protocols.base.ClientboundStatusPackets;
import com.viaversion.viaversion.protocols.base.packet.BaseClientboundPacket;
import com.viaversion.viaversion.protocols.base.packet.BasePacketTypesProvider;
import com.viaversion.viaversion.protocols.base.packet.BaseServerboundPacket;
import com.viaversion.viaversion.util.GsonUtil;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class ClientboundBaseProtocol1_7
extends AbstractProtocol<BaseClientboundPacket, BaseClientboundPacket, BaseServerboundPacket, BaseServerboundPacket> {
    public ClientboundBaseProtocol1_7() {
        super(BaseClientboundPacket.class, BaseClientboundPacket.class, BaseServerboundPacket.class, BaseServerboundPacket.class);
    }

    @Override
    protected void registerPackets() {
        this.registerClientbound(ClientboundStatusPackets.STATUS_RESPONSE, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.STRING);
                this.handler(wrapper -> {
                    ProtocolInfo info = wrapper.user().getProtocolInfo();
                    String originalStatus = wrapper.get(Types.STRING, 0);
                    try {
                        ProtocolVersion closestServerProtocol;
                        VersionProvider versionProvider;
                        JsonObject version;
                        JsonElement json = GsonUtil.getGson().fromJson(originalStatus, JsonElement.class);
                        int protocol = 0;
                        if (json.isJsonObject()) {
                            if (json.getAsJsonObject().has("version")) {
                                version = json.getAsJsonObject().get("version").getAsJsonObject();
                                if (version.has("protocol")) {
                                    protocol = Long.valueOf(version.get("protocol").getAsLong()).intValue();
                                }
                            } else {
                                version = new JsonObject();
                                json.getAsJsonObject().add("version", version);
                            }
                        } else {
                            json = new JsonObject();
                            version = new JsonObject();
                            json.getAsJsonObject().add("version", version);
                        }
                        ProtocolVersion protocolVersion = ProtocolVersion.getProtocol(protocol);
                        if (Via.getConfig().isSendSupportedVersions()) {
                            version.add("supportedVersions", GsonUtil.getGson().toJsonTree(Via.getAPI().getSupportedVersions()));
                        }
                        if (!Via.getAPI().getServerVersion().isKnown()) {
                            ProtocolManagerImpl protocolManager = (ProtocolManagerImpl)Via.getManager().getProtocolManager();
                            protocolManager.setServerProtocol(new ServerProtocolVersionSingleton(protocolVersion));
                        }
                        if ((versionProvider = Via.getManager().getProviders().get(VersionProvider.class)) == null) {
                            wrapper.user().setActive(false);
                            return;
                        }
                        try {
                            closestServerProtocol = versionProvider.getClosestServerProtocol(wrapper.user());
                        }
                        catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        List<ProtocolPathEntry> protocols = Via.getManager().getProtocolManager().getProtocolPath(info.protocolVersion(), closestServerProtocol);
                        if (protocols != null) {
                            if (protocolVersion.equalTo(closestServerProtocol) || protocolVersion.getVersion() == 0) {
                                version.addProperty("protocol", info.protocolVersion().getOriginalVersion());
                            }
                        } else {
                            wrapper.user().setActive(false);
                        }
                        if (Via.getConfig().blockedProtocolVersions().contains(info.protocolVersion())) {
                            version.addProperty("protocol", -1);
                        }
                        wrapper.set(Types.STRING, 0, GsonUtil.getGson().toJson(json));
                    }
                    catch (JsonParseException e) {
                        Via.getPlatform().getLogger().log(Level.SEVERE, "Error handling StatusResponse", e);
                    }
                });
            }
        });
        this.registerClientbound(ClientboundLoginPackets.GAME_PROFILE, wrapper -> {
            ProtocolInfo info = wrapper.user().getProtocolInfo();
            if (info.serverProtocolVersion().olderThan(ProtocolVersion.v1_16)) {
                String uuidString = wrapper.passthrough(Types.STRING);
                if (uuidString.length() == 32) {
                    uuidString = ClientboundBaseProtocol1_7.addDashes(uuidString);
                }
                info.setUuid(UUID.fromString(uuidString));
            } else {
                UUID uuid = wrapper.passthrough(Types.UUID);
                info.setUuid(uuid);
            }
            String username = wrapper.passthrough(Types.STRING);
            info.setUsername(username);
            ClientboundBaseProtocol1_7.onLoginSuccess(wrapper.user());
        });
    }

    @Override
    public boolean isBaseProtocol() {
        return true;
    }

    public static String addDashes(String trimmedUUID) {
        StringBuilder idBuff = new StringBuilder(trimmedUUID);
        idBuff.insert(20, '-');
        idBuff.insert(16, '-');
        idBuff.insert(12, '-');
        idBuff.insert(8, '-');
        return idBuff.toString();
    }

    public static void onLoginSuccess(UserConnection connection) {
        ProtocolInfo info = connection.getProtocolInfo();
        if (info.protocolVersion().olderThan(ProtocolVersion.v1_20_2)) {
            info.setState(State.PLAY);
        }
        Via.getManager().getConnectionManager().onLoginSuccess(connection);
        if (!info.getPipeline().hasNonBaseProtocols()) {
            connection.setActive(false);
        }
        if (Via.getManager().isDebug()) {
            Via.getPlatform().getLogger().log(Level.INFO, "{0} logged in with protocol {1}, Route: {2}", new Object[]{info.getUsername(), info.protocolVersion().getName(), Joiner.on((String)", ").join(info.getPipeline().pipes(), (Object)", ", new Object[0])});
        }
    }

    @Override
    protected PacketTypesProvider<BaseClientboundPacket, BaseClientboundPacket, BaseServerboundPacket, BaseServerboundPacket> createPacketTypesProvider() {
        return BasePacketTypesProvider.INSTANCE;
    }
}

