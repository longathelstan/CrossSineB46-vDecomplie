/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_7_2_5tor1_7_6_10;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.BlockPosition;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.base.ClientboundLoginPackets;
import com.viaversion.viaversion.protocols.base.v1_7.ClientboundBaseProtocol1_7;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import net.raphimc.vialegacy.ViaLegacy;
import net.raphimc.vialegacy.api.util.UuidUtil;
import net.raphimc.vialegacy.protocol.release.r1_7_2_5tor1_7_6_10.packet.ClientboundPackets1_7_2;
import net.raphimc.vialegacy.protocol.release.r1_7_2_5tor1_7_6_10.packet.ServerboundPackets1_7_2;
import net.raphimc.vialegacy.protocol.release.r1_7_2_5tor1_7_6_10.rewriter.TextRewriter;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.model.GameProfile;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.provider.GameProfileFetcher;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types.Types1_7_6;

public class Protocolr1_7_2_5Tor1_7_6_10
extends AbstractProtocol<ClientboundPackets1_7_2, ClientboundPackets1_7_2, ServerboundPackets1_7_2, ServerboundPackets1_7_2> {
    static final String UUID_PATTERN = "[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}";

    public Protocolr1_7_2_5Tor1_7_6_10() {
        super(ClientboundPackets1_7_2.class, ClientboundPackets1_7_2.class, ServerboundPackets1_7_2.class, ServerboundPackets1_7_2.class);
    }

    @Override
    protected void registerPackets() {
        this.registerClientbound(State.LOGIN, ClientboundLoginPackets.GAME_PROFILE, (PacketHandler)new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.STRING);
                this.map(Types.STRING);
                this.handler(wrapper -> wrapper.set(Types.STRING, 0, Protocolr1_7_2_5Tor1_7_6_10.fixGameProfileUuid(wrapper.get(Types.STRING, 0), wrapper.get(Types.STRING, 1))));
            }
        });
        this.registerClientbound(ClientboundPackets1_7_2.ADD_PLAYER, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.STRING);
                this.map(Types.STRING);
                this.create(Types.VAR_INT, 0);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.SHORT);
                this.map(Types1_7_6.ENTITY_DATA_LIST);
                this.handler(wrapper -> wrapper.set(Types.STRING, 0, Protocolr1_7_2_5Tor1_7_6_10.fixGameProfileUuid(wrapper.get(Types.STRING, 0), wrapper.get(Types.STRING, 1))));
            }
        });
        this.registerClientbound(ClientboundPackets1_7_2.CHAT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.STRING, Types.STRING, TextRewriter::toClient);
            }
        });
        this.registerClientbound(ClientboundPackets1_7_2.BLOCK_ENTITY_DATA, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types1_7_6.BLOCK_POSITION_SHORT);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types1_7_6.NBT);
                this.handler(wrapper -> {
                    BlockPosition pos = wrapper.get(Types1_7_6.BLOCK_POSITION_SHORT, 0);
                    short type = wrapper.get(Types.UNSIGNED_BYTE, 0);
                    CompoundTag tag = wrapper.get(Types1_7_6.NBT, 0);
                    if (type != 4) {
                        return;
                    }
                    byte skullType = tag.getByte("SkullType");
                    if (skullType != 3) {
                        return;
                    }
                    StringTag extraType = (StringTag)tag.removeUnchecked("ExtraType");
                    if (extraType == null || extraType.getValue().isEmpty()) {
                        return;
                    }
                    if (ViaLegacy.getConfig().isLegacySkullLoading()) {
                        UUID uuid2;
                        GameProfileFetcher gameProfileFetcher = Via.getManager().getProviders().get(GameProfileFetcher.class);
                        String skullName = extraType.getValue();
                        CompoundTag newTag = tag.copy();
                        if (gameProfileFetcher.isUUIDLoaded(skullName) && gameProfileFetcher.isGameProfileLoaded(uuid2 = gameProfileFetcher.getMojangUUID(skullName))) {
                            GameProfile skullProfile = gameProfileFetcher.getGameProfile(uuid2);
                            if (skullProfile == null || skullProfile.isOffline()) {
                                return;
                            }
                            newTag.put("Owner", Protocolr1_7_2_5Tor1_7_6_10.writeGameProfileToTag(skullProfile));
                            wrapper.set(Types1_7_6.NBT, 0, newTag);
                            return;
                        }
                        gameProfileFetcher.getMojangUUIDAsync(skullName).thenAccept(uuid -> {
                            GameProfile skullProfile = gameProfileFetcher.getGameProfile((UUID)uuid);
                            if (skullProfile == null || skullProfile.isOffline()) {
                                return;
                            }
                            newTag.put("Owner", Protocolr1_7_2_5Tor1_7_6_10.writeGameProfileToTag(skullProfile));
                            try {
                                PacketWrapper updateSkull = PacketWrapper.create(ClientboundPackets1_7_2.BLOCK_ENTITY_DATA, wrapper.user());
                                updateSkull.write(Types1_7_6.BLOCK_POSITION_SHORT, pos);
                                updateSkull.write(Types.UNSIGNED_BYTE, type);
                                updateSkull.write(Types1_7_6.NBT, newTag);
                                updateSkull.send(Protocolr1_7_2_5Tor1_7_6_10.class);
                            }
                            catch (Throwable e) {
                                String string = skullName;
                                ViaLegacy.getPlatform().getLogger().log(Level.WARNING, "Failed to update skull block entity data for " + string, e);
                            }
                        });
                    }
                });
            }
        });
    }

    public static CompoundTag writeGameProfileToTag(GameProfile gameProfile) {
        CompoundTag ownerTag = new CompoundTag();
        if (gameProfile.userName != null && !gameProfile.userName.isEmpty()) {
            ownerTag.putString("Name", gameProfile.userName);
        }
        if (gameProfile.uuid != null) {
            ownerTag.putString("Id", gameProfile.uuid.toString());
        }
        if (!gameProfile.properties.isEmpty()) {
            CompoundTag propertiesTag = new CompoundTag();
            for (Map.Entry<String, List<GameProfile.Property>> entry : gameProfile.properties.entrySet()) {
                ListTag<CompoundTag> propertiesList = new ListTag<CompoundTag>(CompoundTag.class);
                for (GameProfile.Property property : entry.getValue()) {
                    CompoundTag propertyTag = new CompoundTag();
                    propertyTag.putString("Value", property.value);
                    if (property.signature != null) {
                        propertyTag.putString("Signature", property.signature);
                    }
                    propertiesList.add(propertyTag);
                }
                propertiesTag.put(entry.getKey(), propertiesList);
            }
            ownerTag.put("Properties", propertiesTag);
        }
        return ownerTag;
    }

    static String fixGameProfileUuid(String uuid, String name) {
        String dashedUuid;
        if (uuid.matches(UUID_PATTERN)) {
            return uuid;
        }
        if (uuid.length() == 32 && (dashedUuid = ClientboundBaseProtocol1_7.addDashes(uuid)).matches(UUID_PATTERN)) {
            return dashedUuid;
        }
        return UuidUtil.createOfflinePlayerUuid(name).toString();
    }
}

