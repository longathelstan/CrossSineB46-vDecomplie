/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viabackwards.api.rewriters;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.nbt.tag.ListTag;
import com.viaversion.nbt.tag.NumberTag;
import com.viaversion.nbt.tag.StringTag;
import com.viaversion.viabackwards.api.rewriters.BackwardsItemRewriter;
import com.viaversion.viabackwards.utils.ChatUtil;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.util.Key;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EnchantmentRewriter {
    public static final String ENCHANTMENT_LEVEL_TRANSLATION = "enchantment.level.%s";
    protected final Map<String, String> enchantmentMappings = new HashMap<String, String>();
    protected final BackwardsItemRewriter<?, ?, ?> itemRewriter;
    private final boolean jsonFormat;

    public EnchantmentRewriter(BackwardsItemRewriter<?, ?, ?> itemRewriter, boolean jsonFormat) {
        this.itemRewriter = itemRewriter;
        this.jsonFormat = jsonFormat;
    }

    public EnchantmentRewriter(BackwardsItemRewriter<?, ?, ?> itemRewriter) {
        this(itemRewriter, true);
    }

    public void registerEnchantment(String key, String replacementLore) {
        this.enchantmentMappings.put(Key.stripMinecraftNamespace(key), replacementLore);
    }

    public void handleToClient(Item item) {
        CompoundTag tag = item.tag();
        if (tag == null) {
            return;
        }
        if (tag.getListTag("Enchantments") != null) {
            this.rewriteEnchantmentsToClient(tag, false);
        }
        if (tag.getListTag("StoredEnchantments") != null) {
            this.rewriteEnchantmentsToClient(tag, true);
        }
    }

    public void handleToServer(Item item) {
        CompoundTag tag = item.tag();
        if (tag == null) {
            return;
        }
        if (tag.contains(this.itemRewriter.nbtTagName("Enchantments"))) {
            this.rewriteEnchantmentsToServer(tag, false);
        }
        if (tag.contains(this.itemRewriter.nbtTagName("StoredEnchantments"))) {
            this.rewriteEnchantmentsToServer(tag, true);
        }
    }

    public void rewriteEnchantmentsToClient(CompoundTag tag, boolean storedEnchant) {
        String key = storedEnchant ? "StoredEnchantments" : "Enchantments";
        ListTag<CompoundTag> enchantments = tag.getListTag(key, CompoundTag.class);
        ArrayList<StringTag> loreToAdd = new ArrayList<StringTag>();
        boolean changed = false;
        Iterator<CompoundTag> iterator2 = enchantments.iterator();
        while (iterator2.hasNext()) {
            String loreValue;
            int level;
            String enchantmentId;
            String remappedName;
            CompoundTag enchantmentEntry = iterator2.next();
            StringTag idTag = enchantmentEntry.getStringTag("id");
            if (idTag == null || (remappedName = this.enchantmentMappings.get(enchantmentId = Key.stripMinecraftNamespace(idTag.getValue()))) == null) continue;
            if (!changed) {
                this.itemRewriter.saveListTag(tag, enchantments, key);
                changed = true;
            }
            iterator2.remove();
            NumberTag levelTag = enchantmentEntry.getNumberTag("lvl");
            int n = level = levelTag != null ? levelTag.asInt() : 1;
            if (this.jsonFormat) {
                loreValue = ChatUtil.legacyToJsonString(remappedName, ENCHANTMENT_LEVEL_TRANSLATION.formatted(level), true);
            } else {
                String string = EnchantmentRewriter.getRomanNumber(level);
                String string2 = remappedName;
                loreValue = string2 + " " + string;
            }
            loreToAdd.add(new StringTag(loreValue));
        }
        if (!loreToAdd.isEmpty()) {
            ListTag<StringTag> loreTag;
            CompoundTag display;
            if (!storedEnchant && enchantments.isEmpty()) {
                CompoundTag dummyEnchantment = new CompoundTag();
                dummyEnchantment.putString("id", "");
                dummyEnchantment.putShort("lvl", (short)0);
                enchantments.add(dummyEnchantment);
            }
            if ((display = tag.getCompoundTag("display")) == null) {
                display = new CompoundTag();
                tag.put("display", display);
            }
            if ((loreTag = display.getListTag("Lore", StringTag.class)) == null) {
                loreTag = new ListTag<StringTag>(StringTag.class);
                display.put("Lore", loreTag);
            } else {
                this.itemRewriter.saveListTag(display, loreTag, "Lore");
            }
            loreToAdd.addAll((Collection<StringTag>)loreTag.getValue());
            loreTag.setValue(loreToAdd);
        }
    }

    public void rewriteEnchantmentsToServer(CompoundTag tag, boolean storedEnchant) {
        String key = storedEnchant ? "StoredEnchantments" : "Enchantments";
        this.itemRewriter.restoreListTag(tag, key);
    }

    public static String getRomanNumber(int number) {
        String string;
        switch (number) {
            case 1: {
                string = "I";
                break;
            }
            case 2: {
                string = "II";
                break;
            }
            case 3: {
                string = "III";
                break;
            }
            case 4: {
                string = "IV";
                break;
            }
            case 5: {
                string = "V";
                break;
            }
            case 6: {
                string = "VI";
                break;
            }
            case 7: {
                string = "VII";
                break;
            }
            case 8: {
                string = "VIII";
                break;
            }
            case 9: {
                string = "IX";
                break;
            }
            case 10: {
                string = "X";
                break;
            }
            default: {
                string = ENCHANTMENT_LEVEL_TRANSLATION.formatted(number);
            }
        }
        return string;
    }
}

