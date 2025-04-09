/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.rewriter;

import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.Mappings;
import com.viaversion.viaversion.api.data.ParticleMappings;
import com.viaversion.viaversion.api.minecraft.Particle;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.rewriter.ComponentRewriter;
import com.viaversion.viaversion.api.rewriter.RewriterBase;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.rewriter.SoundRewriter;
import org.checkerframework.checker.nullness.qual.Nullable;

public class ItemRewriter<C extends ClientboundPacketType, S extends ServerboundPacketType, T extends Protocol<C, ?, ?, S>>
extends RewriterBase<T>
implements com.viaversion.viaversion.api.rewriter.ItemRewriter<T> {
    final Type<Item> itemType;
    final Type<Item> mappedItemType;
    final Type<Item[]> itemArrayType;
    final Type<Item[]> mappedItemArrayType;
    final Type<Item> itemCostType;
    final Type<Item> mappedItemCostType;
    final Type<Item> optionalItemCostType;
    final Type<Item> mappedOptionalItemCostType;
    final Type<Particle> particleType;
    final Type<Particle> mappedParticleType;

    public ItemRewriter(T protocol, Type<Item> itemType, Type<Item[]> itemArrayType, Type<Item> mappedItemType, Type<Item[]> mappedItemArrayType, Type<Item> itemCostType, Type<Item> optionalItemCostType, Type<Item> mappedItemCostType, Type<Item> mappedOptionalItemCostType, Type<Particle> particleType, Type<Particle> mappedParticleType) {
        super(protocol);
        this.itemType = itemType;
        this.itemArrayType = itemArrayType;
        this.mappedItemType = mappedItemType;
        this.mappedItemArrayType = mappedItemArrayType;
        this.itemCostType = itemCostType;
        this.mappedItemCostType = mappedItemCostType;
        this.optionalItemCostType = optionalItemCostType;
        this.mappedOptionalItemCostType = mappedOptionalItemCostType;
        this.particleType = particleType;
        this.mappedParticleType = mappedParticleType;
    }

    public ItemRewriter(T protocol, Type<Item> itemType, Type<Item[]> itemArrayType, Type<Item> mappedItemType, Type<Item[]> mappedItemArrayType) {
        this(protocol, itemType, itemArrayType, mappedItemType, mappedItemArrayType, null, null, null, null, null, null);
    }

    public ItemRewriter(T protocol, Type<Item> itemType, Type<Item[]> itemArrayType) {
        this(protocol, itemType, itemArrayType, itemType, itemArrayType);
    }

    @Override
    public @Nullable Item handleItemToClient(UserConnection connection, @Nullable Item item) {
        if (item == null) {
            return null;
        }
        if (this.protocol.getMappingData() != null && this.protocol.getMappingData().getItemMappings() != null) {
            item.setIdentifier(this.protocol.getMappingData().getNewItemId(item.identifier()));
        }
        return item;
    }

    @Override
    public @Nullable Item handleItemToServer(UserConnection connection, @Nullable Item item) {
        if (item == null) {
            return null;
        }
        if (this.protocol.getMappingData() != null && this.protocol.getMappingData().getItemMappings() != null) {
            item.setIdentifier(this.protocol.getMappingData().getOldItemId(item.identifier()));
        }
        return item;
    }

    public void registerSetContent(C packetType) {
        this.protocol.registerClientbound(packetType, wrapper -> {
            wrapper.passthrough(Types.UNSIGNED_BYTE);
            Item[] items = wrapper.passthroughAndMap(this.itemArrayType, this.mappedItemArrayType);
            for (int i = 0; i < items.length; ++i) {
                items[i] = this.handleItemToClient(wrapper.user(), items[i]);
            }
        });
    }

    public void registerSetContent1_17_1(C packetType) {
        this.protocol.registerClientbound(packetType, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.VAR_INT);
                this.handler(wrapper -> {
                    Item[] items = wrapper.passthroughAndMap(ItemRewriter.this.itemArrayType, ItemRewriter.this.mappedItemArrayType);
                    for (int i = 0; i < items.length; ++i) {
                        items[i] = ItemRewriter.this.handleItemToClient(wrapper.user(), items[i]);
                    }
                    ItemRewriter.this.handleClientboundItem(wrapper);
                });
            }
        });
    }

    public void registerOpenScreen(C packetType) {
        this.protocol.registerClientbound(packetType, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.handler(wrapper -> ItemRewriter.this.handleMenuType(wrapper));
            }
        });
    }

    public void handleMenuType(PacketWrapper wrapper) {
        int windowType = wrapper.read(Types.VAR_INT);
        int mappedId = this.protocol.getMappingData().getMenuMappings().getNewId(windowType);
        if (mappedId == -1) {
            wrapper.cancel();
            return;
        }
        wrapper.write(Types.VAR_INT, mappedId);
    }

    public void registerSetSlot(C packetType) {
        this.protocol.registerClientbound(packetType, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.SHORT);
                this.handler(wrapper -> ItemRewriter.this.handleClientboundItem(wrapper));
            }
        });
    }

    public void registerSetSlot1_17_1(C packetType) {
        this.protocol.registerClientbound(packetType, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.VAR_INT);
                this.map(Types.SHORT);
                this.handler(wrapper -> ItemRewriter.this.handleClientboundItem(wrapper));
            }
        });
    }

    public void registerSetEquippedItem(C packetType) {
        this.protocol.registerClientbound(packetType, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.VAR_INT);
                this.handler(wrapper -> ItemRewriter.this.handleClientboundItem(wrapper));
            }
        });
    }

    public void registerSetEquipment(C packetType) {
        this.protocol.registerClientbound(packetType, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.handler(wrapper -> {
                    byte slot;
                    do {
                        slot = wrapper.passthrough(Types.BYTE);
                        ItemRewriter.this.handleClientboundItem(wrapper);
                    } while (slot < 0);
                });
            }
        });
    }

    public void registerSetCreativeModeSlot(S packetType) {
        this.protocol.registerServerbound(packetType, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.SHORT);
                this.handler(wrapper -> ItemRewriter.this.handleServerboundItem(wrapper));
            }
        });
    }

    public void registerContainerClick(S packetType) {
        this.protocol.registerServerbound(packetType, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.SHORT);
                this.map(Types.BYTE);
                this.map(Types.SHORT);
                this.map(Types.VAR_INT);
                this.handler(wrapper -> ItemRewriter.this.handleServerboundItem(wrapper));
            }
        });
    }

    public void registerContainerClick1_17_1(S packetType) {
        this.protocol.registerServerbound(packetType, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.VAR_INT);
                this.map(Types.SHORT);
                this.map(Types.BYTE);
                this.map(Types.VAR_INT);
                this.handler(wrapper -> {
                    int length = wrapper.passthrough(Types.VAR_INT);
                    for (int i = 0; i < length; ++i) {
                        wrapper.passthrough(Types.SHORT);
                        ItemRewriter.this.handleServerboundItem(wrapper);
                    }
                    ItemRewriter.this.handleServerboundItem(wrapper);
                });
            }
        });
    }

    public void registerCooldown(C packetType) {
        this.protocol.registerClientbound(packetType, wrapper -> {
            int itemId = wrapper.read(Types.VAR_INT);
            wrapper.write(Types.VAR_INT, this.protocol.getMappingData().getNewItemId(itemId));
        });
    }

    public void registerCustomPayloadTradeList(C packetType) {
        this.protocol.registerClientbound(packetType, new PacketHandlers(){

            @Override
            protected void register() {
                this.map(Types.STRING);
                this.handlerSoftFail(wrapper -> {
                    String channel = wrapper.get(Types.STRING, 0);
                    if (channel.equals("MC|TrList")) {
                        ItemRewriter.this.handleTradeList(wrapper);
                    }
                });
            }
        });
    }

    public void handleTradeList(PacketWrapper wrapper) {
        wrapper.passthrough(Types.INT);
        int size = wrapper.passthrough(Types.UNSIGNED_BYTE).shortValue();
        for (int i = 0; i < size; ++i) {
            this.handleClientboundItem(wrapper);
            this.handleClientboundItem(wrapper);
            if (wrapper.passthrough(Types.BOOLEAN).booleanValue()) {
                this.handleClientboundItem(wrapper);
            }
            wrapper.passthrough(Types.BOOLEAN);
            wrapper.passthrough(Types.INT);
            wrapper.passthrough(Types.INT);
        }
    }

    public void registerMerchantOffers(C packetType) {
        this.protocol.registerClientbound(packetType, wrapper -> {
            wrapper.passthrough(Types.VAR_INT);
            int size = wrapper.passthrough(Types.UNSIGNED_BYTE).shortValue();
            for (int i = 0; i < size; ++i) {
                this.handleClientboundItem(wrapper);
                this.handleClientboundItem(wrapper);
                if (wrapper.passthrough(Types.BOOLEAN).booleanValue()) {
                    this.handleClientboundItem(wrapper);
                }
                wrapper.passthrough(Types.BOOLEAN);
                wrapper.passthrough(Types.INT);
                wrapper.passthrough(Types.INT);
                wrapper.passthrough(Types.INT);
                wrapper.passthrough(Types.INT);
                wrapper.passthrough(Types.FLOAT);
                wrapper.passthrough(Types.INT);
            }
        });
    }

    public void registerMerchantOffers1_19(C packetType) {
        this.protocol.registerClientbound(packetType, wrapper -> {
            wrapper.passthrough(Types.VAR_INT);
            int size = wrapper.passthrough(Types.VAR_INT);
            for (int i = 0; i < size; ++i) {
                this.handleClientboundItem(wrapper);
                this.handleClientboundItem(wrapper);
                this.handleClientboundItem(wrapper);
                wrapper.passthrough(Types.BOOLEAN);
                wrapper.passthrough(Types.INT);
                wrapper.passthrough(Types.INT);
                wrapper.passthrough(Types.INT);
                wrapper.passthrough(Types.INT);
                wrapper.passthrough(Types.FLOAT);
                wrapper.passthrough(Types.INT);
            }
        });
    }

    public void registerMerchantOffers1_20_5(C packetType) {
        this.protocol.registerClientbound(packetType, wrapper -> {
            wrapper.passthrough(Types.VAR_INT);
            int size = wrapper.passthrough(Types.VAR_INT);
            for (int i = 0; i < size; ++i) {
                Item input = wrapper.read(this.itemCostType);
                wrapper.write(this.mappedItemCostType, this.handleItemToClient(wrapper.user(), input));
                this.handleClientboundItem(wrapper);
                Item secondInput = wrapper.read(this.optionalItemCostType);
                if (secondInput != null) {
                    this.handleItemToClient(wrapper.user(), secondInput);
                }
                wrapper.write(this.mappedOptionalItemCostType, secondInput);
                wrapper.passthrough(Types.BOOLEAN);
                wrapper.passthrough(Types.INT);
                wrapper.passthrough(Types.INT);
                wrapper.passthrough(Types.INT);
                wrapper.passthrough(Types.INT);
                wrapper.passthrough(Types.FLOAT);
                wrapper.passthrough(Types.INT);
            }
        });
    }

    public void registerAdvancements(C packetType) {
        this.protocol.registerClientbound(packetType, wrapper -> {
            wrapper.passthrough(Types.BOOLEAN);
            int size = wrapper.passthrough(Types.VAR_INT);
            for (int i = 0; i < size; ++i) {
                wrapper.passthrough(Types.STRING);
                wrapper.passthrough(Types.OPTIONAL_STRING);
                if (wrapper.passthrough(Types.BOOLEAN).booleanValue()) {
                    JsonElement title2 = wrapper.passthrough(Types.COMPONENT);
                    JsonElement description = wrapper.passthrough(Types.COMPONENT);
                    ComponentRewriter componentRewriter = this.protocol.getComponentRewriter();
                    if (componentRewriter != null) {
                        componentRewriter.processText(wrapper.user(), title2);
                        componentRewriter.processText(wrapper.user(), description);
                    }
                    this.handleClientboundItem(wrapper);
                    wrapper.passthrough(Types.VAR_INT);
                    int flags = wrapper.passthrough(Types.INT);
                    if ((flags & 1) != 0) {
                        wrapper.passthrough(Types.STRING);
                    }
                    wrapper.passthrough(Types.FLOAT);
                    wrapper.passthrough(Types.FLOAT);
                }
                wrapper.passthrough(Types.STRING_ARRAY);
                int arrayLength = wrapper.passthrough(Types.VAR_INT);
                for (int array = 0; array < arrayLength; ++array) {
                    wrapper.passthrough(Types.STRING_ARRAY);
                }
            }
        });
    }

    public void registerAdvancements1_20_3(C packetType) {
        this.protocol.registerClientbound(packetType, wrapper -> {
            wrapper.passthrough(Types.BOOLEAN);
            int size = wrapper.passthrough(Types.VAR_INT);
            for (int i = 0; i < size; ++i) {
                wrapper.passthrough(Types.STRING);
                wrapper.passthrough(Types.OPTIONAL_STRING);
                if (wrapper.passthrough(Types.BOOLEAN).booleanValue()) {
                    Tag title2 = wrapper.passthrough(Types.TAG);
                    Tag description = wrapper.passthrough(Types.TAG);
                    ComponentRewriter componentRewriter = this.protocol.getComponentRewriter();
                    if (componentRewriter != null) {
                        componentRewriter.processTag(wrapper.user(), title2);
                        componentRewriter.processTag(wrapper.user(), description);
                    }
                    this.handleClientboundItem(wrapper);
                    wrapper.passthrough(Types.VAR_INT);
                    int flags = wrapper.passthrough(Types.INT);
                    if ((flags & 1) != 0) {
                        wrapper.passthrough(Types.STRING);
                    }
                    wrapper.passthrough(Types.FLOAT);
                    wrapper.passthrough(Types.FLOAT);
                }
                int requirements = wrapper.passthrough(Types.VAR_INT);
                for (int array = 0; array < requirements; ++array) {
                    wrapper.passthrough(Types.STRING_ARRAY);
                }
                wrapper.passthrough(Types.BOOLEAN);
            }
        });
    }

    public void registerContainerSetData(C packetType) {
        this.protocol.registerClientbound(packetType, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.UNSIGNED_BYTE);
                this.handler(wrapper -> {
                    Mappings mappings = ItemRewriter.this.protocol.getMappingData().getEnchantmentMappings();
                    if (mappings == null) {
                        return;
                    }
                    short property = wrapper.passthrough(Types.SHORT);
                    if (property >= 4 && property <= 6) {
                        short enchantmentId = (short)mappings.getNewId(wrapper.read(Types.SHORT).shortValue());
                        wrapper.write(Types.SHORT, enchantmentId);
                    }
                });
            }
        });
    }

    public void registerLevelParticles(C packetType, final Type<?> coordType) {
        this.protocol.registerClientbound(packetType, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.BOOLEAN);
                this.map(coordType);
                this.map(coordType);
                this.map(coordType);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.INT);
                this.handler(ItemRewriter.this.levelParticlesHandler());
            }
        });
    }

    public void registerLevelParticles1_19(C packetType) {
        this.protocol.registerClientbound(packetType, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.BOOLEAN);
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.INT);
                this.handler(ItemRewriter.this.levelParticlesHandler(Types.VAR_INT));
            }
        });
    }

    public void registerLevelParticles1_20_5(C packetType) {
        this.protocol.registerClientbound(packetType, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BOOLEAN);
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.DOUBLE);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.FLOAT);
                this.map(Types.INT);
                this.handler(wrapper -> {
                    Particle particle = wrapper.passthroughAndMap(ItemRewriter.this.particleType, ItemRewriter.this.mappedParticleType);
                    ItemRewriter.this.rewriteParticle(wrapper.user(), particle);
                });
            }
        });
    }

    public void registerExplosion(C packetType) {
        SoundRewriter soundRewriter = new SoundRewriter(this.protocol);
        this.protocol.registerClientbound(packetType, wrapper -> {
            wrapper.passthrough(Types.DOUBLE);
            wrapper.passthrough(Types.DOUBLE);
            wrapper.passthrough(Types.DOUBLE);
            wrapper.passthrough(Types.FLOAT);
            int blocks = wrapper.passthrough(Types.VAR_INT);
            for (int i = 0; i < blocks; ++i) {
                wrapper.passthrough(Types.BYTE);
                wrapper.passthrough(Types.BYTE);
                wrapper.passthrough(Types.BYTE);
            }
            wrapper.passthrough(Types.FLOAT);
            wrapper.passthrough(Types.FLOAT);
            wrapper.passthrough(Types.FLOAT);
            wrapper.passthrough(Types.VAR_INT);
            Particle smallExplosionParticle = wrapper.passthroughAndMap(this.particleType, this.mappedParticleType);
            Particle largeExplosionParticle = wrapper.passthroughAndMap(this.particleType, this.mappedParticleType);
            this.rewriteParticle(wrapper.user(), smallExplosionParticle);
            this.rewriteParticle(wrapper.user(), largeExplosionParticle);
            soundRewriter.soundHolderHandler().handle(wrapper);
        });
    }

    public PacketHandler levelParticlesHandler() {
        return this.levelParticlesHandler(Types.INT);
    }

    public PacketHandler levelParticlesHandler(Type<Integer> idType) {
        return wrapper -> {
            int id = (Integer)wrapper.get(idType, 0);
            if (id == -1) {
                return;
            }
            ParticleMappings mappings = this.protocol.getMappingData().getParticleMappings();
            if (mappings.isBlockParticle(id)) {
                int data = wrapper.read(Types.VAR_INT);
                wrapper.write(Types.VAR_INT, this.protocol.getMappingData().getNewBlockStateId(data));
            } else if (mappings.isItemParticle(id)) {
                this.handleClientboundItem(wrapper);
            }
            int mappedId = this.protocol.getMappingData().getNewParticleId(id);
            if (mappedId != id) {
                wrapper.set(idType, 0, mappedId);
            }
        };
    }

    void handleClientboundItem(PacketWrapper wrapper) {
        Item item = this.handleItemToClient(wrapper.user(), wrapper.read(this.itemType));
        wrapper.write(this.mappedItemType, item);
    }

    void handleServerboundItem(PacketWrapper wrapper) {
        Item item = this.handleItemToServer(wrapper.user(), wrapper.read(this.mappedItemType));
        wrapper.write(this.itemType, item);
    }

    protected void rewriteParticle(UserConnection connection, Particle particle) {
        int id;
        ParticleMappings mappings = this.protocol.getMappingData().getParticleMappings();
        if (mappings.isBlockParticle(id = particle.id())) {
            Particle.ParticleData<Integer> data = particle.getArgument(0);
            data.setValue(this.protocol.getMappingData().getNewBlockStateId((Integer)data.getValue()));
        } else if (mappings.isItemParticle(id)) {
            Particle.ParticleData<Item> data = particle.getArgument(0);
            Item item = this.handleItemToClient(connection, (Item)data.getValue());
            if (this.mappedItemType() != null && this.itemType() != this.mappedItemType()) {
                particle.set(0, this.mappedItemType(), item);
            } else {
                data.setValue(item);
            }
        }
        particle.setId(this.protocol.getMappingData().getNewParticleId(id));
    }

    @Override
    public Type<Item> itemType() {
        return this.itemType;
    }

    @Override
    public Type<Item[]> itemArrayType() {
        return this.itemArrayType;
    }

    @Override
    public Type<Item> mappedItemType() {
        return this.mappedItemType;
    }

    @Override
    public Type<Item[]> mappedItemArrayType() {
        return this.mappedItemArrayType;
    }
}

