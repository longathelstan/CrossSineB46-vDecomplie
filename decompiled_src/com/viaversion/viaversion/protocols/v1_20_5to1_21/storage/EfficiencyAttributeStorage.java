/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_20_5to1_21.storage;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.protocols.v1_20_5to1_21.Protocol1_20_5To1_21;
import com.viaversion.viaversion.protocols.v1_20_5to1_21.packet.ClientboundPackets1_21;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class EfficiencyAttributeStorage
implements StorableObject {
    static final EnchantAttributeModifier EFFICIENCY = new EnchantAttributeModifier("minecraft:enchantment.efficiency/mainhand", 19, 0.0, level -> level * level + 1);
    static final EnchantAttributeModifier SOUL_SPEED = new EnchantAttributeModifier("minecraft:enchantment.soul_speed", 21, 0.1, level -> 0.04 + (double)(level - 1) * 0.01);
    static final EnchantAttributeModifier SWIFT_SNEAK = new EnchantAttributeModifier("minecraft:enchantment.swift_sneak", 25, 0.3, level -> (double)level * 0.15);
    static final EnchantAttributeModifier AQUA_AFFINITY = new EnchantAttributeModifier("minecraft:enchantment.aqua_affinity", 28, 0.2, level -> level * 4, 2);
    static final EnchantAttributeModifier DEPTH_STRIDER = new EnchantAttributeModifier("minecraft:enchantment.depth_strider", 30, 0.0, level -> (double)level / 3.0);
    static final ActiveEnchants DEFAULT = new ActiveEnchants(-1, new ActiveEnchant(EFFICIENCY, 0, 0), new ActiveEnchant(SOUL_SPEED, 0, 0), new ActiveEnchant(SWIFT_SNEAK, 0, 0), new ActiveEnchant(AQUA_AFFINITY, 0, 0), new ActiveEnchant(DEPTH_STRIDER, 0, 0));
    final Object lock = new Object();
    volatile boolean attributesSent = true;
    volatile boolean loginSent;
    ActiveEnchants activeEnchants = DEFAULT;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void setEnchants(int entityId, UserConnection connection, int efficiency, int soulSpeed, int swiftSneak, int aquaAffinity, int depthStrider) {
        if (efficiency == this.activeEnchants.efficiency.level && soulSpeed == this.activeEnchants.soulSpeed.level && swiftSneak == this.activeEnchants.swiftSneak.level && aquaAffinity == this.activeEnchants.aquaAffinity.level && depthStrider == this.activeEnchants.depthStrider.level) {
            return;
        }
        Object object = this.lock;
        synchronized (object) {
            this.activeEnchants = new ActiveEnchants(entityId, new ActiveEnchant(this.activeEnchants.efficiency, efficiency), new ActiveEnchant(this.activeEnchants.soulSpeed, soulSpeed), new ActiveEnchant(this.activeEnchants.swiftSneak, swiftSneak), new ActiveEnchant(this.activeEnchants.aquaAffinity, aquaAffinity), new ActiveEnchant(this.activeEnchants.depthStrider, depthStrider));
            this.attributesSent = false;
        }
        this.sendAttributesPacket(connection, false);
    }

    public ActiveEnchants activeEnchants() {
        return this.activeEnchants;
    }

    public void onLoginSent(UserConnection connection) {
        this.loginSent = true;
        this.sendAttributesPacket(connection, false);
    }

    public void onRespawn(UserConnection connection) {
        this.sendAttributesPacket(connection, true);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void sendAttributesPacket(UserConnection connection, boolean forceSendAll) {
        ActiveEnchants enchants;
        Object object = this.lock;
        synchronized (object) {
            if (!(forceSendAll || this.loginSent && !this.attributesSent)) {
                return;
            }
            enchants = this.activeEnchants;
            this.attributesSent = true;
        }
        PacketWrapper attributesPacket = PacketWrapper.create(ClientboundPackets1_21.UPDATE_ATTRIBUTES, connection);
        attributesPacket.write(Types.VAR_INT, enchants.entityId());
        List<ActiveEnchant> list = Collections.unmodifiableList(Stream.of(enchants.efficiency(), enchants.soulSpeed(), enchants.swiftSneak(), enchants.aquaAffinity(), enchants.depthStrider()).filter(enchant -> forceSendAll || enchant.previousLevel != enchant.level).collect(Collectors.toList()));
        attributesPacket.write(Types.VAR_INT, list.size());
        for (ActiveEnchant enchant2 : list) {
            EnchantAttributeModifier modifier = enchant2.modifier;
            attributesPacket.write(Types.VAR_INT, modifier.attributeId);
            attributesPacket.write(Types.DOUBLE, modifier.baseValue);
            if (enchant2.level > 0) {
                attributesPacket.write(Types.VAR_INT, 1);
                attributesPacket.write(Types.STRING, modifier.key);
                attributesPacket.write(Types.DOUBLE, enchant2.modifier.modifierFunction.get(enchant2.level));
                attributesPacket.write(Types.BYTE, modifier.operation);
                continue;
            }
            attributesPacket.write(Types.VAR_INT, 0);
        }
        attributesPacket.scheduleSend(Protocol1_20_5To1_21.class);
    }

    public static final class ActiveEnchants {
        final int entityId;
        final ActiveEnchant efficiency;
        final ActiveEnchant soulSpeed;
        final ActiveEnchant swiftSneak;
        final ActiveEnchant aquaAffinity;
        final ActiveEnchant depthStrider;

        public ActiveEnchants(int entityId, ActiveEnchant efficiency, ActiveEnchant soulSpeed, ActiveEnchant swiftSneak, ActiveEnchant aquaAffinity, ActiveEnchant depthStrider) {
            this.entityId = entityId;
            this.efficiency = efficiency;
            this.soulSpeed = soulSpeed;
            this.swiftSneak = swiftSneak;
            this.aquaAffinity = aquaAffinity;
            this.depthStrider = depthStrider;
        }

        public int entityId() {
            return this.entityId;
        }

        public ActiveEnchant efficiency() {
            return this.efficiency;
        }

        public ActiveEnchant soulSpeed() {
            return this.soulSpeed;
        }

        public ActiveEnchant swiftSneak() {
            return this.swiftSneak;
        }

        public ActiveEnchant aquaAffinity() {
            return this.aquaAffinity;
        }

        public ActiveEnchant depthStrider() {
            return this.depthStrider;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof ActiveEnchants)) {
                return false;
            }
            ActiveEnchants activeEnchants = (ActiveEnchants)object;
            return this.entityId == activeEnchants.entityId && Objects.equals(this.efficiency, activeEnchants.efficiency) && Objects.equals(this.soulSpeed, activeEnchants.soulSpeed) && Objects.equals(this.swiftSneak, activeEnchants.swiftSneak) && Objects.equals(this.aquaAffinity, activeEnchants.aquaAffinity) && Objects.equals(this.depthStrider, activeEnchants.depthStrider);
        }

        public int hashCode() {
            return (((((0 * 31 + Integer.hashCode(this.entityId)) * 31 + Objects.hashCode(this.efficiency)) * 31 + Objects.hashCode(this.soulSpeed)) * 31 + Objects.hashCode(this.swiftSneak)) * 31 + Objects.hashCode(this.aquaAffinity)) * 31 + Objects.hashCode(this.depthStrider);
        }

        public String toString() {
            return String.format("%s[entityId=%s, efficiency=%s, soulSpeed=%s, swiftSneak=%s, aquaAffinity=%s, depthStrider=%s]", this.getClass().getSimpleName(), Integer.toString(this.entityId), Objects.toString(this.efficiency), Objects.toString(this.soulSpeed), Objects.toString(this.swiftSneak), Objects.toString(this.aquaAffinity), Objects.toString(this.depthStrider));
        }
    }

    public static final class ActiveEnchant {
        final EnchantAttributeModifier modifier;
        final int previousLevel;
        final int level;

        public ActiveEnchant(ActiveEnchant from, int level) {
            this(from.modifier, from.level, level);
        }

        public ActiveEnchant(EnchantAttributeModifier modifier, int previousLevel, int level) {
            this.modifier = modifier;
            this.previousLevel = previousLevel;
            this.level = level;
        }

        public EnchantAttributeModifier modifier() {
            return this.modifier;
        }

        public int previousLevel() {
            return this.previousLevel;
        }

        public int level() {
            return this.level;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof ActiveEnchant)) {
                return false;
            }
            ActiveEnchant activeEnchant = (ActiveEnchant)object;
            return Objects.equals(this.modifier, activeEnchant.modifier) && this.previousLevel == activeEnchant.previousLevel && this.level == activeEnchant.level;
        }

        public int hashCode() {
            return ((0 * 31 + Objects.hashCode(this.modifier)) * 31 + Integer.hashCode(this.previousLevel)) * 31 + Integer.hashCode(this.level);
        }

        public String toString() {
            return String.format("%s[modifier=%s, previousLevel=%s, level=%s]", this.getClass().getSimpleName(), Objects.toString(this.modifier), Integer.toString(this.previousLevel), Integer.toString(this.level));
        }
    }

    public static final class EnchantAttributeModifier {
        final String key;
        final int attributeId;
        final double baseValue;
        final LevelToModifier modifierFunction;
        final byte operation;

        EnchantAttributeModifier(String key, int attributeId, double baseValue, LevelToModifier modifierFunction) {
            this(key, attributeId, baseValue, modifierFunction, 0);
        }

        public EnchantAttributeModifier(String key, int attributeId, double baseValue, LevelToModifier modifierFunction, byte operation) {
            this.key = key;
            this.attributeId = attributeId;
            this.baseValue = baseValue;
            this.modifierFunction = modifierFunction;
            this.operation = operation;
        }

        public String key() {
            return this.key;
        }

        public int attributeId() {
            return this.attributeId;
        }

        public double baseValue() {
            return this.baseValue;
        }

        public LevelToModifier modifierFunction() {
            return this.modifierFunction;
        }

        public byte operation() {
            return this.operation;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof EnchantAttributeModifier)) {
                return false;
            }
            EnchantAttributeModifier enchantAttributeModifier = (EnchantAttributeModifier)object;
            return Objects.equals(this.key, enchantAttributeModifier.key) && this.attributeId == enchantAttributeModifier.attributeId && Double.compare(this.baseValue, enchantAttributeModifier.baseValue) == 0 && Objects.equals(this.modifierFunction, enchantAttributeModifier.modifierFunction) && this.operation == enchantAttributeModifier.operation;
        }

        public int hashCode() {
            return ((((0 * 31 + Objects.hashCode(this.key)) * 31 + Integer.hashCode(this.attributeId)) * 31 + Double.hashCode(this.baseValue)) * 31 + Objects.hashCode(this.modifierFunction)) * 31 + Byte.hashCode(this.operation);
        }

        public String toString() {
            return String.format("%s[key=%s, attributeId=%s, baseValue=%s, modifierFunction=%s, operation=%s]", this.getClass().getSimpleName(), Objects.toString(this.key), Integer.toString(this.attributeId), Double.toString(this.baseValue), Objects.toString(this.modifierFunction), Byte.toString(this.operation));
        }
    }

    @FunctionalInterface
    private static interface LevelToModifier {
        public double get(int var1);
    }
}

