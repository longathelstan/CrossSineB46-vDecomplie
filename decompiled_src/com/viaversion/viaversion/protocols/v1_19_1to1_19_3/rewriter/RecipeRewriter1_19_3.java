/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_19_1to1_19_3.rewriter;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.rewriter.RecipeRewriter;

public class RecipeRewriter1_19_3<C extends ClientboundPacketType>
extends RecipeRewriter<C> {
    public RecipeRewriter1_19_3(Protocol<C, ?, ?, ?> protocol) {
        super(protocol);
        this.recipeHandlers.put("crafting_special_armordye", this::handleSimpleRecipe);
        this.recipeHandlers.put("crafting_special_bookcloning", this::handleSimpleRecipe);
        this.recipeHandlers.put("crafting_special_mapcloning", this::handleSimpleRecipe);
        this.recipeHandlers.put("crafting_special_mapextending", this::handleSimpleRecipe);
        this.recipeHandlers.put("crafting_special_firework_rocket", this::handleSimpleRecipe);
        this.recipeHandlers.put("crafting_special_firework_star", this::handleSimpleRecipe);
        this.recipeHandlers.put("crafting_special_firework_star_fade", this::handleSimpleRecipe);
        this.recipeHandlers.put("crafting_special_tippedarrow", this::handleSimpleRecipe);
        this.recipeHandlers.put("crafting_special_bannerduplicate", this::handleSimpleRecipe);
        this.recipeHandlers.put("crafting_special_shielddecoration", this::handleSimpleRecipe);
        this.recipeHandlers.put("crafting_special_shulkerboxcoloring", this::handleSimpleRecipe);
        this.recipeHandlers.put("crafting_special_suspiciousstew", this::handleSimpleRecipe);
        this.recipeHandlers.put("crafting_special_repairitem", this::handleSimpleRecipe);
    }

    @Override
    public void handleCraftingShapeless(PacketWrapper wrapper) {
        wrapper.passthrough(Types.STRING);
        wrapper.passthrough(Types.VAR_INT);
        this.handleIngredients(wrapper);
        Item result = this.rewrite(wrapper.user(), wrapper.read(this.itemType()));
        wrapper.write(this.mappedItemType(), result);
    }

    @Override
    public void handleCraftingShaped(PacketWrapper wrapper) {
        int ingredients = wrapper.passthrough(Types.VAR_INT) * wrapper.passthrough(Types.VAR_INT);
        wrapper.passthrough(Types.STRING);
        wrapper.passthrough(Types.VAR_INT);
        for (int i = 0; i < ingredients; ++i) {
            this.handleIngredient(wrapper);
        }
        Item result = this.rewrite(wrapper.user(), wrapper.read(this.itemType()));
        wrapper.write(this.mappedItemType(), result);
    }

    @Override
    public void handleSmelting(PacketWrapper wrapper) {
        wrapper.passthrough(Types.STRING);
        wrapper.passthrough(Types.VAR_INT);
        this.handleIngredient(wrapper);
        Item result = this.rewrite(wrapper.user(), wrapper.read(this.itemType()));
        wrapper.write(this.mappedItemType(), result);
        wrapper.passthrough(Types.FLOAT);
        wrapper.passthrough(Types.VAR_INT);
    }
}

