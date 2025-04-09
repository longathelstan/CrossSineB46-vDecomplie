/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_11to1_10.rewriter;

import com.viaversion.viabackwards.protocol.v1_11to1_10.Protocol1_11To1_10;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.protocols.v1_9_1to1_9_3.packet.ClientboundPackets1_9_3;
import com.viaversion.viaversion.protocols.v1_9_1to1_9_3.packet.ServerboundPackets1_9_3;
import com.viaversion.viaversion.util.ComponentUtil;

public class PlayerPacketRewriter1_11 {
    static final ValueTransformer<Short, Float> TO_NEW_FLOAT = new ValueTransformer<Short, Float>((Type)Types.FLOAT){

        @Override
        public Float transform(PacketWrapper wrapper, Short inputValue) {
            return Float.valueOf((float)inputValue.shortValue() / 16.0f);
        }
    };

    public static void register(Protocol1_11To1_10 protocol) {
        protocol.registerClientbound(ClientboundPackets1_9_3.SET_TITLES, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.handler(wrapper -> {
                    int action = wrapper.get(Types.VAR_INT, 0);
                    if (action == 2) {
                        JsonElement message = wrapper.read(Types.COMPONENT);
                        wrapper.clearPacket();
                        wrapper.setPacketType(ClientboundPackets1_9_3.CHAT);
                        String legacy = ComponentUtil.jsonToLegacy(message);
                        message = new JsonObject();
                        message.getAsJsonObject().addProperty("text", legacy);
                        wrapper.write(Types.COMPONENT, message);
                        wrapper.write(Types.BYTE, (byte)2);
                    } else if (action > 2) {
                        wrapper.set(Types.VAR_INT, 0, action - 1);
                    }
                });
            }
        });
        protocol.registerClientbound(ClientboundPackets1_9_3.TAKE_ITEM_ENTITY, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.VAR_INT);
                this.handler(wrapper -> wrapper.read(Types.VAR_INT));
            }
        });
        protocol.registerServerbound(ServerboundPackets1_9_3.USE_ITEM_ON, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BLOCK_POSITION1_8);
                this.map(Types.VAR_INT);
                this.map(Types.VAR_INT);
                this.map(Types.UNSIGNED_BYTE, TO_NEW_FLOAT);
                this.map(Types.UNSIGNED_BYTE, TO_NEW_FLOAT);
                this.map(Types.UNSIGNED_BYTE, TO_NEW_FLOAT);
            }
        });
    }
}

