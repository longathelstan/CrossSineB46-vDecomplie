/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft.item.data;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.ArrayType;
import io.netty.buffer.ByteBuf;
import java.util.Objects;
import java.util.UUID;

public final class AttributeModifiers1_20_5 {
    final AttributeModifier[] modifiers;
    final boolean showInTooltip;
    public static final Type<AttributeModifiers1_20_5> TYPE = new Type<AttributeModifiers1_20_5>(AttributeModifiers1_20_5.class){

        @Override
        public AttributeModifiers1_20_5 read(ByteBuf buffer) {
            AttributeModifier[] modifiers = (AttributeModifier[])AttributeModifier.ARRAY_TYPE.read(buffer);
            boolean showInTooltip = buffer.readBoolean();
            return new AttributeModifiers1_20_5(modifiers, showInTooltip);
        }

        @Override
        public void write(ByteBuf buffer, AttributeModifiers1_20_5 value) {
            AttributeModifier.ARRAY_TYPE.write(buffer, value.modifiers());
            buffer.writeBoolean(value.showInTooltip());
        }
    };

    public AttributeModifiers1_20_5(AttributeModifier[] modifiers, boolean showInTooltip) {
        this.modifiers = modifiers;
        this.showInTooltip = showInTooltip;
    }

    public AttributeModifier[] modifiers() {
        return this.modifiers;
    }

    public boolean showInTooltip() {
        return this.showInTooltip;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof AttributeModifiers1_20_5)) {
            return false;
        }
        AttributeModifiers1_20_5 attributeModifiers1_20_5 = (AttributeModifiers1_20_5)object;
        return Objects.equals(this.modifiers, attributeModifiers1_20_5.modifiers) && this.showInTooltip == attributeModifiers1_20_5.showInTooltip;
    }

    public int hashCode() {
        return (0 * 31 + Objects.hashCode(this.modifiers)) * 31 + Boolean.hashCode(this.showInTooltip);
    }

    public String toString() {
        return String.format("%s[modifiers=%s, showInTooltip=%s]", this.getClass().getSimpleName(), Objects.toString(this.modifiers), Boolean.toString(this.showInTooltip));
    }

    public static final class AttributeModifier {
        final int attribute;
        final ModifierData modifier;
        final int slotType;
        public static final Type<AttributeModifier> TYPE = new Type<AttributeModifier>(AttributeModifier.class){

            @Override
            public AttributeModifier read(ByteBuf buffer) {
                int attribute = Types.VAR_INT.readPrimitive(buffer);
                ModifierData modifier = (ModifierData)ModifierData.TYPE.read(buffer);
                int slot = Types.VAR_INT.readPrimitive(buffer);
                return new AttributeModifier(attribute, modifier, slot);
            }

            @Override
            public void write(ByteBuf buffer, AttributeModifier value) {
                Types.VAR_INT.writePrimitive(buffer, value.attribute);
                ModifierData.TYPE.write(buffer, value.modifier);
                Types.VAR_INT.writePrimitive(buffer, value.slotType);
            }
        };
        public static final Type<AttributeModifier[]> ARRAY_TYPE = new ArrayType<AttributeModifier>(TYPE);

        public AttributeModifier(int attribute, ModifierData modifier, int slotType) {
            this.attribute = attribute;
            this.modifier = modifier;
            this.slotType = slotType;
        }

        public int attribute() {
            return this.attribute;
        }

        public ModifierData modifier() {
            return this.modifier;
        }

        public int slotType() {
            return this.slotType;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof AttributeModifier)) {
                return false;
            }
            AttributeModifier attributeModifier = (AttributeModifier)object;
            return this.attribute == attributeModifier.attribute && Objects.equals(this.modifier, attributeModifier.modifier) && this.slotType == attributeModifier.slotType;
        }

        public int hashCode() {
            return ((0 * 31 + Integer.hashCode(this.attribute)) * 31 + Objects.hashCode(this.modifier)) * 31 + Integer.hashCode(this.slotType);
        }

        public String toString() {
            return String.format("%s[attribute=%s, modifier=%s, slotType=%s]", this.getClass().getSimpleName(), Integer.toString(this.attribute), Objects.toString(this.modifier), Integer.toString(this.slotType));
        }
    }

    public static final class ModifierData {
        final UUID uuid;
        final String name;
        final double amount;
        final int operation;
        public static final Type<ModifierData> TYPE = new Type<ModifierData>(ModifierData.class){

            @Override
            public ModifierData read(ByteBuf buffer) {
                UUID uuid = (UUID)Types.UUID.read(buffer);
                String name = (String)Types.STRING.read(buffer);
                double amount = buffer.readDouble();
                int operation = Types.VAR_INT.readPrimitive(buffer);
                return new ModifierData(uuid, name, amount, operation);
            }

            @Override
            public void write(ByteBuf buffer, ModifierData value) {
                Types.UUID.write(buffer, value.uuid);
                Types.STRING.write(buffer, value.name);
                buffer.writeDouble(value.amount);
                Types.VAR_INT.writePrimitive(buffer, value.operation);
            }
        };

        public ModifierData(UUID uuid, String name, double amount, int operation) {
            this.uuid = uuid;
            this.name = name;
            this.amount = amount;
            this.operation = operation;
        }

        public UUID uuid() {
            return this.uuid;
        }

        public String name() {
            return this.name;
        }

        public double amount() {
            return this.amount;
        }

        public int operation() {
            return this.operation;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof ModifierData)) {
                return false;
            }
            ModifierData modifierData = (ModifierData)object;
            return Objects.equals(this.uuid, modifierData.uuid) && Objects.equals(this.name, modifierData.name) && Double.compare(this.amount, modifierData.amount) == 0 && this.operation == modifierData.operation;
        }

        public int hashCode() {
            return (((0 * 31 + Objects.hashCode(this.uuid)) * 31 + Objects.hashCode(this.name)) * 31 + Double.hashCode(this.amount)) * 31 + Integer.hashCode(this.operation);
        }

        public String toString() {
            return String.format("%s[uuid=%s, name=%s, amount=%s, operation=%s]", this.getClass().getSimpleName(), Objects.toString(this.uuid), Objects.toString(this.name), Double.toString(this.amount), Integer.toString(this.operation));
        }
    }
}

