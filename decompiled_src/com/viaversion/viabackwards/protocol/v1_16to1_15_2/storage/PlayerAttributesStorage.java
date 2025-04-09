/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.protocol.v1_16to1_15_2.storage;

import com.viaversion.viabackwards.protocol.v1_16to1_15_2.Protocol1_16To1_15_2;
import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_14_4to1_15.packet.ClientboundPackets1_15;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public final class PlayerAttributesStorage
implements StorableObject {
    final Map<String, Attribute> attributes = new HashMap<String, Attribute>();

    public void sendAttributes(UserConnection connection, int entityId) {
        PacketWrapper updateAttributes = PacketWrapper.create(ClientboundPackets1_15.UPDATE_ATTRIBUTES, connection);
        updateAttributes.write(Types.VAR_INT, entityId);
        updateAttributes.write(Types.INT, this.attributes.size());
        for (Map.Entry<String, Attribute> attributeEntry : this.attributes.entrySet()) {
            Attribute attribute = attributeEntry.getValue();
            updateAttributes.write(Types.STRING, attributeEntry.getKey());
            updateAttributes.write(Types.DOUBLE, attribute.value());
            updateAttributes.write(Types.VAR_INT, attribute.modifiers().length);
            for (AttributeModifier modifier : attribute.modifiers()) {
                updateAttributes.write(Types.UUID, modifier.uuid());
                updateAttributes.write(Types.DOUBLE, modifier.amount());
                updateAttributes.write(Types.BYTE, modifier.operation());
            }
        }
        updateAttributes.send(Protocol1_16To1_15_2.class);
    }

    public void clearAttributes() {
        this.attributes.clear();
    }

    public void addAttribute(String key, Attribute attribute) {
        this.attributes.put(key, attribute);
    }

    public static final class Attribute {
        final double value;
        final AttributeModifier[] modifiers;

        public Attribute(double value, AttributeModifier[] modifiers) {
            this.value = value;
            this.modifiers = modifiers;
        }

        public double value() {
            return this.value;
        }

        public AttributeModifier[] modifiers() {
            return this.modifiers;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof Attribute)) {
                return false;
            }
            Attribute attribute = (Attribute)object;
            return Double.compare(this.value, attribute.value) == 0 && Objects.equals(this.modifiers, attribute.modifiers);
        }

        public int hashCode() {
            return (0 * 31 + Double.hashCode(this.value)) * 31 + Objects.hashCode(this.modifiers);
        }

        public String toString() {
            return String.format("%s[value=%s, modifiers=%s]", this.getClass().getSimpleName(), Double.toString(this.value), Objects.toString(this.modifiers));
        }
    }

    public static final class AttributeModifier {
        final UUID uuid;
        final double amount;
        final byte operation;

        public AttributeModifier(UUID uuid, double amount, byte operation) {
            this.uuid = uuid;
            this.amount = amount;
            this.operation = operation;
        }

        public UUID uuid() {
            return this.uuid;
        }

        public double amount() {
            return this.amount;
        }

        public byte operation() {
            return this.operation;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof AttributeModifier)) {
                return false;
            }
            AttributeModifier attributeModifier = (AttributeModifier)object;
            return Objects.equals(this.uuid, attributeModifier.uuid) && Double.compare(this.amount, attributeModifier.amount) == 0 && this.operation == attributeModifier.operation;
        }

        public int hashCode() {
            return ((0 * 31 + Objects.hashCode(this.uuid)) * 31 + Double.hashCode(this.amount)) * 31 + Byte.hashCode(this.operation);
        }

        public String toString() {
            return String.format("%s[uuid=%s, amount=%s, operation=%s]", this.getClass().getSimpleName(), Objects.toString(this.uuid), Double.toString(this.amount), Byte.toString(this.operation));
        }
    }
}

