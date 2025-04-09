/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_4_6_7tor1_5_0_1;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_8;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import net.raphimc.vialegacy.api.protocol.StatelessProtocol;
import net.raphimc.vialegacy.api.splitter.PreNettySplitter;
import net.raphimc.vialegacy.protocol.release.r1_4_6_7tor1_5_0_1.packet.ClientboundPackets1_4_6;
import net.raphimc.vialegacy.protocol.release.r1_4_6_7tor1_5_0_1.rewriter.ItemRewriter;
import net.raphimc.vialegacy.protocol.release.r1_5_2tor1_6_1.packet.ClientboundPackets1_5_2;
import net.raphimc.vialegacy.protocol.release.r1_5_2tor1_6_1.packet.ServerboundPackets1_5_2;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.types.Types1_6_4;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types.Types1_7_6;

public class Protocolr1_4_6_7Tor1_5_0_1
extends StatelessProtocol<ClientboundPackets1_4_6, ClientboundPackets1_5_2, ServerboundPackets1_5_2, ServerboundPackets1_5_2> {
    final ItemRewriter itemRewriter = new ItemRewriter(this);

    public Protocolr1_4_6_7Tor1_5_0_1() {
        super(ClientboundPackets1_4_6.class, ClientboundPackets1_5_2.class, ServerboundPackets1_5_2.class, ServerboundPackets1_5_2.class);
    }

    @Override
    protected void registerPackets() {
        super.registerPackets();
        this.registerClientbound(ClientboundPackets1_4_6.ADD_ENTITY, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types.BYTE);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.INT);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.INT);
                this.handler(wrapper -> {
                    byte typeId = wrapper.get(Types.BYTE, 0);
                    if (typeId == 10 || typeId == 11 || typeId == 12) {
                        wrapper.set(Types.BYTE, 0, (byte)EntityTypes1_8.ObjectType.MINECART.getId());
                    }
                    int throwerEntityId = wrapper.get(Types.INT, 4);
                    short speedX = 0;
                    short speedY = 0;
                    short speedZ = 0;
                    if (throwerEntityId > 0) {
                        speedX = wrapper.read(Types.SHORT);
                        speedY = wrapper.read(Types.SHORT);
                        speedZ = wrapper.read(Types.SHORT);
                    }
                    if (typeId == 10) {
                        throwerEntityId = 0;
                    }
                    if (typeId == 11) {
                        throwerEntityId = 1;
                    }
                    if (typeId == 12) {
                        throwerEntityId = 2;
                    }
                    wrapper.set(Types.INT, 4, throwerEntityId);
                    if (throwerEntityId > 0) {
                        wrapper.write(Types.SHORT, speedX);
                        wrapper.write(Types.SHORT, speedY);
                        wrapper.write(Types.SHORT, speedZ);
                    }
                });
            }
        });
        this.registerClientbound(ClientboundPackets1_4_6.OPEN_SCREEN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types.UNSIGNED_BYTE);
                this.map(Types1_6_4.STRING);
                this.map(Types.UNSIGNED_BYTE);
                this.create(Types.BOOLEAN, false);
            }
        });
        this.registerServerbound(ServerboundPackets1_5_2.CONTAINER_CLICK, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BYTE);
                this.map(Types.SHORT);
                this.map(Types.BYTE);
                this.map(Types.SHORT);
                this.map(Types.BYTE);
                this.map(Types1_7_6.ITEM);
                this.handler(wrapper -> {
                    short slot = wrapper.get(Types.SHORT, 0);
                    byte button = wrapper.get(Types.BYTE, 1);
                    byte mode2 = wrapper.get(Types.BYTE, 2);
                    if (mode2 > 3) {
                        boolean mouseClick;
                        boolean startDragging = false;
                        boolean endDragging = false;
                        boolean droppingUsingQ = false;
                        boolean addSlot = false;
                        switch (mode2) {
                            case 4: {
                                droppingUsingQ = button + (slot != -999 ? 2 : 0) == 2;
                                break;
                            }
                            case 5: {
                                startDragging = button == 0;
                                endDragging = button == 2;
                                addSlot = button == 1;
                            }
                        }
                        boolean leftClick = startDragging || addSlot || endDragging;
                        boolean clickingOutside = slot == -999 && mode2 != 5;
                        boolean bl = mouseClick = !leftClick;
                        if (droppingUsingQ) {
                            PacketWrapper closeWindow = PacketWrapper.create(ClientboundPackets1_5_2.CONTAINER_CLOSE, wrapper.user());
                            closeWindow.write(Types.BYTE, (byte)0);
                            closeWindow.send(Protocolr1_4_6_7Tor1_5_0_1.class);
                            wrapper.cancel();
                            return;
                        }
                        if (slot < 0 && !clickingOutside) {
                            wrapper.cancel();
                            return;
                        }
                        wrapper.set(Types.BYTE, 1, (byte)(mouseClick ? 1 : 0));
                        wrapper.set(Types.BYTE, 2, (byte)0);
                        wrapper.set(Types1_7_6.ITEM, 0, new DataItem(34, 0, 0, null));
                    }
                });
            }
        });
    }

    @Override
    public void init(UserConnection userConnection) {
        userConnection.put(new PreNettySplitter(Protocolr1_4_6_7Tor1_5_0_1.class, ClientboundPackets1_4_6::getPacket));
    }

    public ItemRewriter getItemRewriter() {
        return this.itemRewriter;
    }
}

