/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_13to1_12_2;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.data.BackwardsMappingData1_13;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.data.PaintingNames1_13;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.provider.BackwardsBlockEntityProvider;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.rewriter.BlockItemPacketRewriter1_13;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.rewriter.EntityPacketRewriter1_13;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.rewriter.PlayerPacketRewriter1_13;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.rewriter.SoundPacketRewriter1_13;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.storage.BackwardsBlockStorage;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.storage.NoteBlockStorage;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.storage.PlayerPositionStorage1_13;
import com.viaversion.viabackwards.protocol.v1_13to1_12_2.storage.TabCompleteStorage;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_13;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonParser;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.packet.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.v1_12_2to1_13.packet.ServerboundPackets1_13;
import com.viaversion.viaversion.protocols.v1_12to1_12_1.packet.ClientboundPackets1_12_1;
import com.viaversion.viaversion.protocols.v1_12to1_12_1.packet.ServerboundPackets1_12_1;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import com.viaversion.viaversion.util.ComponentUtil;
import com.viaversion.viaversion.util.ProtocolLogger;
import org.checkerframework.checker.nullness.qual.Nullable;

public class Protocol1_13To1_12_2
extends BackwardsProtocol<ClientboundPackets1_13, ClientboundPackets1_12_1, ServerboundPackets1_13, ServerboundPackets1_12_1> {
    public static final BackwardsMappingData1_13 MAPPINGS = new BackwardsMappingData1_13();
    public static final ProtocolLogger LOGGER = new ProtocolLogger(Protocol1_13To1_12_2.class);
    final EntityPacketRewriter1_13 entityRewriter = new EntityPacketRewriter1_13(this);
    final BlockItemPacketRewriter1_13 blockItemPackets = new BlockItemPacketRewriter1_13(this);
    final TranslatableRewriter<ClientboundPackets1_13> translatableRewriter = new TranslatableRewriter<ClientboundPackets1_13>((BackwardsProtocol)this, ComponentRewriter.ReadType.JSON){

        @Override
        protected void handleTranslate(JsonObject root, String translate) {
            String mappedKey = this.mappedTranslationKey(translate);
            if (mappedKey != null || (mappedKey = Protocol1_13To1_12_2.this.getMappingData().getTranslateMappings().get(translate)) != null) {
                root.addProperty("translate", mappedKey);
            }
        }
    };
    final TranslatableRewriter<ClientboundPackets1_13> translatableToLegacyRewriter = new TranslatableRewriter<ClientboundPackets1_13>((BackwardsProtocol)this, ComponentRewriter.ReadType.JSON){

        @Override
        protected void handleTranslate(JsonObject root, String translate) {
            String mappedKey = this.mappedTranslationKey(translate);
            if (mappedKey != null || (mappedKey = Protocol1_13To1_12_2.this.getMappingData().getTranslateMappings().get(translate)) != null) {
                root.addProperty("translate", Protocol1_12_2To1_13.MAPPINGS.getMojangTranslation().getOrDefault(mappedKey, mappedKey));
            }
        }
    };

    public Protocol1_13To1_12_2() {
        super(ClientboundPackets1_13.class, ClientboundPackets1_12_1.class, ServerboundPackets1_13.class, ServerboundPackets1_12_1.class);
    }

    @Override
    protected void registerPackets() {
        this.executeAsyncAfterLoaded(Protocol1_12_2To1_13.class, () -> {
            MAPPINGS.load();
            PaintingNames1_13.init();
            Via.getManager().getProviders().register(BackwardsBlockEntityProvider.class, new BackwardsBlockEntityProvider());
        });
        this.translatableRewriter.registerPing();
        this.translatableRewriter.registerBossEvent(ClientboundPackets1_13.BOSS_EVENT);
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_13.CHAT);
        this.translatableRewriter.registerLegacyOpenWindow(ClientboundPackets1_13.OPEN_SCREEN);
        this.translatableRewriter.registerComponentPacket(ClientboundPackets1_13.DISCONNECT);
        this.translatableRewriter.registerPlayerCombat(ClientboundPackets1_13.PLAYER_COMBAT);
        this.translatableRewriter.registerTitle(ClientboundPackets1_13.SET_TITLES);
        this.translatableRewriter.registerTabList(ClientboundPackets1_13.TAB_LIST);
        this.blockItemPackets.register();
        this.entityRewriter.register();
        new PlayerPacketRewriter1_13(this).register();
        new SoundPacketRewriter1_13(this).register();
        this.cancelClientbound(ClientboundPackets1_13.TAG_QUERY);
        this.cancelClientbound(ClientboundPackets1_13.PLACE_GHOST_RECIPE);
        this.cancelClientbound(ClientboundPackets1_13.RECIPE);
        this.cancelClientbound(ClientboundPackets1_13.UPDATE_ADVANCEMENTS);
        this.cancelClientbound(ClientboundPackets1_13.UPDATE_RECIPES);
        this.cancelClientbound(ClientboundPackets1_13.UPDATE_TAGS);
        this.cancelServerbound(ServerboundPackets1_12_1.PLACE_RECIPE);
        this.cancelServerbound(ServerboundPackets1_12_1.RECIPE_BOOK_UPDATE);
    }

    @Override
    public void init(UserConnection user) {
        user.addEntityTracker(this.getClass(), new EntityTrackerBase(user, EntityTypes1_13.EntityType.PLAYER));
        user.addClientWorld(this.getClass(), new ClientWorld());
        user.put(new BackwardsBlockStorage());
        user.put(new TabCompleteStorage());
        if (ViaBackwards.getConfig().isFix1_13FacePlayer() && !user.has(PlayerPositionStorage1_13.class)) {
            user.put(new PlayerPositionStorage1_13());
        }
        user.put(new NoteBlockStorage());
    }

    @Override
    public BackwardsMappingData1_13 getMappingData() {
        return MAPPINGS;
    }

    @Override
    public ProtocolLogger getLogger() {
        return LOGGER;
    }

    public EntityPacketRewriter1_13 getEntityRewriter() {
        return this.entityRewriter;
    }

    public BlockItemPacketRewriter1_13 getItemRewriter() {
        return this.blockItemPackets;
    }

    public TranslatableRewriter<ClientboundPackets1_13> translatableRewriter() {
        return this.translatableRewriter;
    }

    public String jsonToLegacy(UserConnection connection, String value) {
        if (value.isEmpty()) {
            return "";
        }
        try {
            return this.jsonToLegacy(connection, JsonParser.parseString(value));
        }
        catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public String jsonToLegacy(UserConnection connection, @Nullable JsonElement value) {
        if (value == null || value.isJsonNull()) {
            return "";
        }
        this.translatableToLegacyRewriter.processText(connection, value);
        return ComponentUtil.jsonToLegacy(value);
    }
}

