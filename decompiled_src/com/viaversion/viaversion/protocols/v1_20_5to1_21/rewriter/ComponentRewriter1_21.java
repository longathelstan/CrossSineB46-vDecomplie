/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_20_5to1_21.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.packet.ClientboundPacket1_20_5;
import com.viaversion.viaversion.protocols.v1_20_5to1_21.Protocol1_20_5To1_21;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import com.viaversion.viaversion.util.SerializerVersion;
import com.viaversion.viaversion.util.TagUtil;
import com.viaversion.viaversion.util.UUIDUtil;
import java.util.UUID;

public final class ComponentRewriter1_21
extends ComponentRewriter<ClientboundPacket1_20_5> {
    public ComponentRewriter1_21(Protocol1_20_5To1_21 protocol) {
        super(protocol, ComponentRewriter.ReadType.NBT);
    }

    private void convertAttributeModifiersComponent(CompoundTag tag) {
        CompoundTag attributeModifiers = TagUtil.getNamespacedCompoundTag(tag, "minecraft:attribute_modifiers");
        if (attributeModifiers == null) {
            return;
        }
        ListTag<CompoundTag> modifiers = attributeModifiers.getListTag("modifiers", CompoundTag.class);
        for (CompoundTag modifier : modifiers) {
            String name = modifier.getString("name");
            UUID uuid = UUIDUtil.fromIntArray(modifier.getIntArrayTag("uuid").getValue());
            String id = Protocol1_20_5To1_21.mapAttributeUUID(uuid, name);
            modifier.putString("id", id);
        }
    }

    @Override
    protected void handleShowItem(UserConnection connection, CompoundTag itemTag, CompoundTag componentsTag) {
        super.handleShowItem(connection, itemTag, componentsTag);
        if (componentsTag != null) {
            this.convertAttributeModifiersComponent(componentsTag);
        }
    }

    @Override
    protected SerializerVersion inputSerializerVersion() {
        return SerializerVersion.V1_20_5;
    }

    @Override
    protected SerializerVersion outputSerializerVersion() {
        return SerializerVersion.V1_20_5;
    }
}

