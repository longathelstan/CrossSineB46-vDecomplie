/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_21to1_20_5.rewriter;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.IntArrayTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viabackwards.protocol.v1_21to1_20_5.Protocol1_21To1_20_5;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.protocols.v1_20_3to1_20_5.data.Attributes1_20_5;
import com.viaversion.viaversion.protocols.v1_20_5to1_21.Protocol1_20_5To1_21;
import com.viaversion.viaversion.protocols.v1_20_5to1_21.data.AttributeModifierMappings1_21;
import com.viaversion.viaversion.protocols.v1_20_5to1_21.packet.ClientboundPacket1_21;
import com.viaversion.viaversion.rewriter.ComponentRewriter;
import com.viaversion.viaversion.util.Key;
import com.viaversion.viaversion.util.SerializerVersion;
import com.viaversion.viaversion.util.TagUtil;
import com.viaversion.viaversion.util.UUIDUtil;
import java.util.UUID;

public final class ComponentRewriter1_21
extends TranslatableRewriter<ClientboundPacket1_21> {
    public ComponentRewriter1_21(Protocol1_21To1_20_5 protocol) {
        super(protocol, ComponentRewriter.ReadType.NBT);
    }

    private void convertAttributeModifiersComponent(CompoundTag tag) {
        CompoundTag attributeModifiers = TagUtil.getNamespacedCompoundTag(tag, "attribute_modifiers");
        if (attributeModifiers == null) {
            return;
        }
        ListTag<CompoundTag> modifiers = attributeModifiers.getListTag("modifiers", CompoundTag.class);
        int size = modifiers.size();
        for (int i = 0; i < size; ++i) {
            CompoundTag modifier = modifiers.get(i);
            String type = Key.stripMinecraftNamespace(modifier.getString("type"));
            if (Attributes1_20_5.keyToId(type) == -1) {
                modifiers.remove(i--);
                --size;
                continue;
            }
            String id = modifier.getString("id");
            UUID uuid = Protocol1_20_5To1_21.mapAttributeId(id);
            String name = AttributeModifierMappings1_21.idToName(id);
            modifier.put("uuid", new IntArrayTag(UUIDUtil.toIntArray(uuid)));
            modifier.putString("name", name != null ? name : id);
        }
    }

    @Override
    protected void handleShowItem(UserConnection connection, CompoundTag itemTag, CompoundTag componentsTag) {
        super.handleShowItem(connection, itemTag, componentsTag);
        if (componentsTag != null) {
            TagUtil.removeNamespaced(componentsTag, "jukebox_playable");
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

