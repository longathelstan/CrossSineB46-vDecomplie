/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_20_3to1_20_5.data;

import com.viaversion.viaversion.util.KeyMappings;

public final class MapDecorations1_20_5 {
    private static final KeyMappings MAP_DECORATIONS = new KeyMappings("player", "frame", "red_marker", "blue_marker", "target_x", "target_point", "player_off_map", "player_off_limits", "mansion", "monument", "banner_white", "banner_orange", "banner_magenta", "banner_light_blue", "banner_yellow", "banner_lime", "banner_pink", "banner_gray", "banner_light_gray", "banner_cyan", "banner_purple", "banner_blue", "banner_brown", "banner_green", "banner_red", "banner_black", "red_x", "village_desert", "village_plains", "village_savanna", "village_snowy", "village_taiga", "jungle_temple", "swamp_hut", "trial_chambers");

    public static String idToKey(int index2) {
        return index2 < 0 || index2 >= MAP_DECORATIONS.size() ? "player" : MAP_DECORATIONS.idToKey(index2);
    }

    public static int keyToId(String key) {
        return MAP_DECORATIONS.keyToId(key);
    }
}

