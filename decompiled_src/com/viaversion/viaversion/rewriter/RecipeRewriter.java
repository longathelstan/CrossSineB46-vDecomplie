/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.rewriter;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.util.Key;
import java.util.HashMap;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;

public class RecipeRewriter<C extends ClientboundPacketType> {
    protected final Protocol<C, ?, ?, ?> protocol;
    protected final Map<String, RecipeConsumer> recipeHandlers = new HashMap<String, RecipeConsumer>();

    public RecipeRewriter(Protocol<C, ?, ?, ?> protocol) {
        this.protocol = protocol;
        this.recipeHandlers.put("crafting_shapeless", this::handleCraftingShapeless);
        this.recipeHandlers.put("crafting_shaped", this::handleCraftingShaped);
        this.recipeHandlers.put("smelting", this::handleSmelting);
        this.recipeHandlers.put("blasting", this::handleSmelting);
        this.recipeHandlers.put("smoking", this::handleSmelting);
        this.recipeHandlers.put("campfire_cooking", this::handleSmelting);
        this.recipeHandlers.put("stonecutting", this::handleStonecutting);
        this.recipeHandlers.put("smithing", this::handleSmithing);
        this.recipeHandlers.put("smithing_transform", this::handleSmithingTransform);
        this.recipeHandlers.put("smithing_trim", this::handleSmithingTrim);
        this.recipeHandlers.put("crafting_decorated_pot", this::handleSimpleRecipe);
    }

    public void handleRecipeType(PacketWrapper wrapper, String type) {
        RecipeConsumer handler = this.recipeHandlers.get(Key.stripMinecraftNamespace(type));
        if (handler != null) {
            handler.accept(wrapper);
        }
    }

    public void register(C packetType) {
        this.protocol.registerClientbound(packetType, wrapper -> {
            int size = wrapper.passthrough(Types.VAR_INT);
            for (int i = 0; i < size; ++i) {
                String type = wrapper.passthrough(Types.STRING);
                wrapper.passthrough(Types.STRING);
                this.handleRecipeType(wrapper, type);
            }
        });
    }

    public void register1_20_5(C packetType) {
        this.protocol.registerClientbound(packetType, wrapper -> {
            int size = wrapper.passthrough(Types.VAR_INT);
            for (int i = 0; i < size; ++i) {
                wrapper.passthrough(Types.STRING);
                int typeId = wrapper.passthrough(Types.VAR_INT);
                String type = this.protocol.getMappingData().getRecipeSerializerMappings().identifier(typeId);
                this.handleRecipeType(wrapper, type);
            }
        });
    }

    public void handleCraftingShaped(PacketWrapper wrapper) {
        int ingredientsNo = wrapper.passthrough(Types.VAR_INT) * wrapper.passthrough(Types.VAR_INT);
        wrapper.passthrough(Types.STRING);
        for (int i = 0; i < ingredientsNo; ++i) {
            this.handleIngredient(wrapper);
        }
        Item result = this.rewrite(wrapper.user(), wrapper.read(this.itemType()));
        wrapper.write(this.mappedItemType(), result);
    }

    public void handleCraftingShapeless(PacketWrapper wrapper) {
        wrapper.passthrough(Types.STRING);
        this.handleIngredients(wrapper);
        Item result = this.rewrite(wrapper.user(), wrapper.read(this.itemType()));
        wrapper.write(this.mappedItemType(), result);
    }

    public void handleSmelting(PacketWrapper wrapper) {
        wrapper.passthrough(Types.STRING);
        this.handleIngredient(wrapper);
        Item result = this.rewrite(wrapper.user(), wrapper.read(this.itemType()));
        wrapper.write(this.mappedItemType(), result);
        wrapper.passthrough(Types.FLOAT);
        wrapper.passthrough(Types.VAR_INT);
    }

    public void handleStonecutting(PacketWrapper wrapper) {
        wrapper.passthrough(Types.STRING);
        this.handleIngredient(wrapper);
        Item result = this.rewrite(wrapper.user(), wrapper.read(this.itemType()));
        wrapper.write(this.mappedItemType(), result);
    }

    public void handleSmithing(PacketWrapper wrapper) {
        this.handleIngredient(wrapper);
        this.handleIngredient(wrapper);
        Item result = this.rewrite(wrapper.user(), wrapper.read(this.itemType()));
        wrapper.write(this.mappedItemType(), result);
    }

    public void handleSimpleRecipe(PacketWrapper wrapper) {
        wrapper.passthrough(Types.VAR_INT);
    }

    public void handleSmithingTransform(PacketWrapper wrapper) {
        this.handleIngredient(wrapper);
        this.handleIngredient(wrapper);
        this.handleIngredient(wrapper);
        Item result = this.rewrite(wrapper.user(), wrapper.read(this.itemType()));
        wrapper.write(this.mappedItemType(), result);
    }

    public void handleSmithingTrim(PacketWrapper wrapper) {
        this.handleIngredient(wrapper);
        this.handleIngredient(wrapper);
        this.handleIngredient(wrapper);
    }

    protected @Nullable Item rewrite(UserConnection connection, @Nullable Item item) {
        if (this.protocol.getItemRewriter() != null) {
            return this.protocol.getItemRewriter().handleItemToClient(connection, item);
        }
        return item;
    }

    protected void handleIngredient(PacketWrapper wrapper) {
        Item[] items = wrapper.passthroughAndMap(this.itemArrayType(), this.mappedItemArrayType());
        for (int i = 0; i < items.length; ++i) {
            Item item = items[i];
            items[i] = this.rewrite(wrapper.user(), item);
        }
    }

    protected void handleIngredients(PacketWrapper wrapper) {
        int ingredients = wrapper.passthrough(Types.VAR_INT);
        for (int i = 0; i < ingredients; ++i) {
            this.handleIngredient(wrapper);
        }
    }

    protected Type<Item> itemType() {
        return Types.ITEM1_13_2;
    }

    protected Type<Item[]> itemArrayType() {
        return Types.ITEM1_13_2_ARRAY;
    }

    protected Type<Item> mappedItemType() {
        return this.itemType();
    }

    protected Type<Item[]> mappedItemArrayType() {
        return this.itemArrayType();
    }

    @FunctionalInterface
    public static interface RecipeConsumer {
        public void accept(PacketWrapper var1);
    }
}

