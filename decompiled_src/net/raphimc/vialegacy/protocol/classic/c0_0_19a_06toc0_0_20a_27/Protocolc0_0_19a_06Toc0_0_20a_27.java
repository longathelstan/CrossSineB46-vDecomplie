/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.classic.c0_0_19a_06toc0_0_20a_27;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Types;
import net.raphimc.vialegacy.api.LegacyProtocolVersion;
import net.raphimc.vialegacy.api.protocol.StatelessProtocol;
import net.raphimc.vialegacy.api.splitter.PreNettySplitter;
import net.raphimc.vialegacy.protocol.classic.c0_0_19a_06toc0_0_20a_27.packet.ClientboundPacketsc0_19a;
import net.raphimc.vialegacy.protocol.classic.c0_0_19a_06toc0_0_20a_27.packet.ServerboundPacketsc0_19a;
import net.raphimc.vialegacy.protocol.classic.c0_0_20a_27toc0_28_30.packet.ClientboundPacketsc0_20a;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.packet.ServerboundPacketsc0_28;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.storage.ClassicBlockRemapper;
import net.raphimc.vialegacy.protocol.classic.c0_28_30toa1_0_15.types.Typesc0_30;

public class Protocolc0_0_19a_06Toc0_0_20a_27
extends StatelessProtocol<ClientboundPacketsc0_19a, ClientboundPacketsc0_20a, ServerboundPacketsc0_19a, ServerboundPacketsc0_28> {
    public Protocolc0_0_19a_06Toc0_0_20a_27() {
        super(ClientboundPacketsc0_19a.class, ClientboundPacketsc0_20a.class, ServerboundPacketsc0_19a.class, ServerboundPacketsc0_28.class);
    }

    @Override
    protected void registerPackets() {
        this.registerClientbound(ClientboundPacketsc0_19a.LOGIN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BYTE);
                this.map(Typesc0_30.STRING);
                this.map(Typesc0_30.STRING);
                this.create(Types.BYTE, (byte)0);
            }
        });
        this.registerServerbound(ServerboundPacketsc0_28.LOGIN, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Types.BYTE);
                this.map(Typesc0_30.STRING);
                this.map(Typesc0_30.STRING);
                this.read(Types.BYTE);
            }
        });
    }

    @Override
    public void init(UserConnection userConnection) {
        userConnection.put(new PreNettySplitter(Protocolc0_0_19a_06Toc0_0_20a_27.class, ClientboundPacketsc0_19a::getPacket));
        ClassicBlockRemapper previousRemapper = userConnection.get(ClassicBlockRemapper.class);
        userConnection.put(new ClassicBlockRemapper(previousRemapper.mapper(), o -> {
            int block = previousRemapper.reverseMapper().getInt(o);
            if (userConnection.getProtocolInfo().serverProtocolVersion().equals(LegacyProtocolVersion.c0_0_19a_06) && block != 1 && block != 3 && block != 5 && block != 6 && block != 13 && block != 17 && block != 18 && block != 19 && block != 20) {
                block = 1;
            }
            return block;
        }));
    }
}

