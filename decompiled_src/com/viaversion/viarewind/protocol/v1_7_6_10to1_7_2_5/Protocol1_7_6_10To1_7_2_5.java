/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viarewind.protocol.v1_7_6_10to1_7_2_5;

import com.viaversion.viarewind.api.type.version.Types1_7_6_10;
import com.viaversion.viarewind.protocol.v1_7_6_10to1_7_2_5.packet.ClientboundPackets1_7_2_5;
import com.viaversion.viarewind.protocol.v1_7_6_10to1_7_2_5.packet.ServerboundPackets1_7_2_5;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.base.ClientboundLoginPackets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Protocol1_7_6_10To1_7_2_5
extends AbstractProtocol<ClientboundPackets1_7_2_5, ClientboundPackets1_7_2_5, ServerboundPackets1_7_2_5, ServerboundPackets1_7_2_5> {
    public static final ValueTransformer<String, String> REMOVE_DASHES = new ValueTransformer<String, String>(Types.STRING){

        @Override
        public String transform(PacketWrapper wrapper, String s) {
            return s.replace("-", "");
        }
    };

    public Protocol1_7_6_10To1_7_2_5() {
        super(ClientboundPackets1_7_2_5.class, ClientboundPackets1_7_2_5.class, ServerboundPackets1_7_2_5.class, ServerboundPackets1_7_2_5.class);
    }

    @Override
    protected void registerPackets() {
        this.registerClientbound(State.LOGIN, ClientboundLoginPackets.GAME_PROFILE, (PacketHandler)new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.STRING, REMOVE_DASHES);
                this.map(Types.STRING);
            }
        });
        this.registerClientbound(ClientboundPackets1_7_2_5.ADD_PLAYER, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.VAR_INT);
                this.map(Types.STRING, REMOVE_DASHES);
                this.map(Types.STRING);
                this.handler(wrapper -> {
                    int size = wrapper.read(Types.VAR_INT);
                    for (int i = 0; i < size; ++i) {
                        wrapper.read(Types.STRING);
                        wrapper.read(Types.STRING);
                        wrapper.read(Types.STRING);
                    }
                });
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.SHORT);
                this.map(Types1_7_6_10.ENTITY_DATA_LIST);
            }
        });
        this.registerClientbound(ClientboundPackets1_7_2_5.SET_PLAYER_TEAM, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.STRING);
                this.map(Types.BYTE);
                this.handler(wrapper -> {
                    byte mode2 = wrapper.get(Types.BYTE, 0);
                    if (mode2 == 0 || mode2 == 2) {
                        wrapper.passthrough(Types.STRING);
                        wrapper.passthrough(Types.STRING);
                        wrapper.passthrough(Types.STRING);
                        wrapper.passthrough(Types.BYTE);
                    }
                    if (mode2 == 0 || mode2 == 3 || mode2 == 4) {
                        List<Object> entryList = new ArrayList<String>();
                        int size = wrapper.read(Types.SHORT).shortValue();
                        for (int i = 0; i < size; ++i) {
                            entryList.add(wrapper.read(Types.STRING));
                        }
                        entryList = entryList.stream().map((? super T it) -> it.length() > 16 ? it.substring(0, 16) : it).distinct().collect(Collectors.toList());
                        wrapper.write(Types.SHORT, (short)entryList.size());
                        for (String string : entryList) {
                            wrapper.write(Types.STRING, string);
                        }
                    }
                });
            }
        });
    }
}

