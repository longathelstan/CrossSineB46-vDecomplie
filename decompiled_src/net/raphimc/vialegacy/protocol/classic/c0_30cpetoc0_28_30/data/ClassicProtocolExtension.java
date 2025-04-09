/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.classic.c0_30cpetoc0_28_30.data;

import com.viaversion.viaversion.libs.fastutil.ints.IntIterator;
import com.viaversion.viaversion.libs.fastutil.ints.IntOpenHashSet;
import com.viaversion.viaversion.libs.fastutil.ints.IntSet;
import java.util.Arrays;

public enum ClassicProtocolExtension {
    CLICK_DISTANCE("ClickDistance", new int[0]),
    CUSTOM_BLOCKS("CustomBlocks", 1),
    HELD_BLOCK("HeldBlock", new int[0]),
    TEXT_HOT_KEY("TextHotKey", new int[0]),
    EXT_PLAYER_LIST("ExtPlayerList", new int[0]),
    ENV_COLORS("EnvColors", new int[0]),
    SELECTION_CUBOID("SelectionCuboid", new int[0]),
    BLOCK_PERMISSIONS("BlockPermissions", 1),
    CHANGE_MODEL("ChangeModel", new int[0]),
    ENV_MAP_APPEARANCE("EnvMapAppearance", new int[0]),
    ENV_WEATHER_TYPE("EnvWeatherType", new int[0]),
    HACK_CONTROL("HackControl", 1),
    EMOTE_FIX("EmoteFix", 1),
    MESSAGE_TYPES("MessageTypes", new int[0]),
    LONGER_MESSAGES("LongerMessages", 1),
    FULL_CP437("FullCP437", 1),
    BLOCK_DEFINITIONS("BlockDefinitions", new int[0]),
    BLOCK_DEFINITIONS_EXT("BlockDefinitionsExt", new int[0]),
    TEXT_COLORS("TextColors", new int[0]),
    BULK_BLOCK_UPDATE("BulkBlockUpdate", 1),
    ENV_MAP_ASPECT("EnvMapAspect", new int[0]),
    PLAYER_CLICK("PlayerClick", new int[0]),
    ENTITY_PROPERTY("EntityProperty", new int[0]),
    EXT_ENTITY_POSITIONS("ExtEntityPositions", new int[0]),
    TWO_WAY_PING("TwoWayPing", 1),
    INVENTORY_ORDER("InventoryOrder", new int[0]),
    INSTANT_MOTD("InstantMOTD", 1),
    EXTENDED_BLOCKS("ExtendedBlocks", new int[0]),
    FAST_MAP("FastMap", new int[0]),
    EXTENDED_TEXTURES("ExtendedTextures", new int[0]),
    SET_HOTBAR("SetHotbar", new int[0]),
    SET_SPAWNPOINT("SetSpawnpoint", new int[0]),
    VELOCITY_CONTROL("VelocityControl", new int[0]),
    CUSTOM_PARTICLES("CustomParticles", new int[0]),
    CUSTOM_MODELS("CustomModels", new int[0]),
    EXT_ENTITY_TELEPORT("ExtEntityTeleport", new int[0]);

    private final String name;
    private final IntSet supportedVersions;

    private ClassicProtocolExtension(String name, int ... supportedVersions) {
        this.name = name;
        this.supportedVersions = new IntOpenHashSet();
        for (int supportedVersion : supportedVersions) {
            this.supportedVersions.add(supportedVersion);
        }
    }

    public static ClassicProtocolExtension byName(String name) {
        return Arrays.stream(ClassicProtocolExtension.values()).filter(e -> e.name.equals(name)).findFirst().orElse(null);
    }

    public static ClassicProtocolExtension byNameAndVersion(String name, int version) {
        ClassicProtocolExtension extension = ClassicProtocolExtension.byName(name);
        if (extension == null || !extension.supportsVersion(version)) {
            return null;
        }
        return extension;
    }

    public boolean supportsVersion(int version) {
        return this.supportedVersions.contains(version);
    }

    public IntSet getSupportedVersions() {
        return this.supportedVersions;
    }

    public int getHighestSupportedVersion() {
        int highest = 0;
        IntIterator intIterator = this.supportedVersions.iterator();
        while (intIterator.hasNext()) {
            int supportedVersion = (Integer)intIterator.next();
            if (supportedVersion <= highest) continue;
            highest = supportedVersion;
        }
        return highest;
    }

    public boolean isSupported() {
        return !this.supportedVersions.isEmpty();
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return this.name;
    }
}

