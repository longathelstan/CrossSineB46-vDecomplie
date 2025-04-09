/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_0_0_1tor1_1;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import net.raphimc.vialegacy.api.protocol.StatelessProtocol;
import net.raphimc.vialegacy.api.splitter.PreNettySplitter;
import net.raphimc.vialegacy.protocol.release.r1_0_0_1tor1_1.packet.ClientboundPackets1_0_0;
import net.raphimc.vialegacy.protocol.release.r1_0_0_1tor1_1.packet.ServerboundPackets1_0_0;
import net.raphimc.vialegacy.protocol.release.r1_0_0_1tor1_1.rewriter.ChatFilter;
import net.raphimc.vialegacy.protocol.release.r1_0_0_1tor1_1.rewriter.ItemRewriter;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.packet.ClientboundPackets1_1;
import net.raphimc.vialegacy.protocol.release.r1_1tor1_2_1_3.packet.ServerboundPackets1_1;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.types.Types1_6_4;
import net.raphimc.vialegacy.protocol.release.r1_7_6_10tor1_8.types.Types1_7_6;

public class Protocolr1_0_0_1Tor1_1
extends StatelessProtocol<ClientboundPackets1_0_0, ClientboundPackets1_1, ServerboundPackets1_0_0, ServerboundPackets1_1> {
    final ItemRewriter itemRewriter = new ItemRewriter(this);

    public Protocolr1_0_0_1Tor1_1() {
        super(ClientboundPackets1_0_0.class, ClientboundPackets1_1.class, ServerboundPackets1_0_0.class, ServerboundPackets1_1.class);
    }

    @Override
    protected void registerPackets() {
        super.registerPackets();
        this.registerServerbound(ServerboundPackets1_1.LOGIN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types1_6_4.STRING);
                this.map(Types.LONG);
                this.read(Types1_6_4.STRING);
                this.map(Types.INT);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
            }
        });
        this.registerServerbound(ServerboundPackets1_1.CHAT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types1_6_4.STRING, Types1_6_4.STRING, ChatFilter::filter);
            }
        });
        this.registerServerbound(ServerboundPackets1_1.RESPAWN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.SHORT);
                this.map(Types.LONG);
                this.read(Types1_6_4.STRING);
            }
        });
        this.cancelServerbound(ServerboundPackets1_1.CUSTOM_PAYLOAD);
        this.registerClientbound(ClientboundPackets1_0_0.LOGIN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.INT);
                this.map(Types1_6_4.STRING);
                this.map(Types.LONG);
                this.create(Types1_6_4.STRING, "default_1_1");
                this.map(Types.INT);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
            }
        });
        this.registerClientbound(ClientboundPackets1_0_0.CHAT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types1_6_4.STRING, Types1_6_4.STRING, msg -> msg.replace("\u00c2", ""));
            }
        });
        this.registerClientbound(ClientboundPackets1_0_0.RESPAWN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.BYTE);
                this.map(Types.SHORT);
                this.map(Types.LONG);
                this.create(Types1_6_4.STRING, "default_1_1");
            }
        });
        this.registerClientbound(ClientboundPackets1_0_0.UPDATE_SIGN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types1_7_6.BLOCK_POSITION_SHORT);
                this.map(Types1_6_4.STRING, Types1_6_4.STRING, msg -> msg.replace("\u00c2", ""));
                this.map(Types1_6_4.STRING, Types1_6_4.STRING, msg -> msg.replace("\u00c2", ""));
                this.map(Types1_6_4.STRING, Types1_6_4.STRING, msg -> msg.replace("\u00c2", ""));
                this.map(Types1_6_4.STRING, Types1_6_4.STRING, msg -> msg.replace("\u00c2", ""));
            }
        });
        this.registerClientbound(ClientboundPackets1_0_0.DISCONNECT, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types1_6_4.STRING, Types1_6_4.STRING, reason -> reason.replace("\u00c2", ""));
            }
        });
    }

    @Override
    public void init(UserConnection userConnection) {
        userConnection.put(new PreNettySplitter(Protocolr1_0_0_1Tor1_1.class, ClientboundPackets1_0_0::getPacket));
    }

    public ItemRewriter getItemRewriter() {
        return this.itemRewriter;
    }
}

