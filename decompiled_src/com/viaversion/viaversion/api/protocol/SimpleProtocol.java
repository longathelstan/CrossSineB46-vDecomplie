/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.protocol;

import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.Direction;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;

public interface SimpleProtocol
extends Protocol<DummyPacketTypes, DummyPacketTypes, DummyPacketTypes, DummyPacketTypes> {

    public static final class DummyPacketTypes
    extends Enum<DummyPacketTypes>
    implements ClientboundPacketType,
    ServerboundPacketType {
        static final /* synthetic */ DummyPacketTypes[] $VALUES;

        public static DummyPacketTypes[] values() {
            return (DummyPacketTypes[])$VALUES.clone();
        }

        public static DummyPacketTypes valueOf(String name) {
            return Enum.valueOf(DummyPacketTypes.class, name);
        }

        @Override
        public int getId() {
            return 0;
        }

        @Override
        public String getName() {
            return this.name();
        }

        @Override
        public Direction direction() {
            throw new UnsupportedOperationException();
        }

        static /* synthetic */ DummyPacketTypes[] $values() {
            return new DummyPacketTypes[0];
        }

        static {
            $VALUES = DummyPacketTypes.$values();
        }
    }
}

