/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.alpha.a1_2_3_5_1_2_6tob1_0_1_1_1.model;

import com.viaversion.viaversion.api.minecraft.item.Item;

public class CraftingRecipe {
    private final int width;
    private final int height;
    private final int[] ingredientMap;
    private final Item resultItem;

    public CraftingRecipe(int width, int height, int[] ingredientMap, Item resultItem) {
        this.width = width;
        this.height = height;
        this.ingredientMap = ingredientMap;
        this.resultItem = resultItem;
    }

    public boolean matches(int[] ingredientMap) {
        for (int x = 0; x <= 3 - this.width; ++x) {
            for (int y = 0; y <= 3 - this.height; ++y) {
                if (this.matches(ingredientMap, x, y, true)) {
                    return true;
                }
                if (!this.matches(ingredientMap, x, y, false)) continue;
                return true;
            }
        }
        return false;
    }

    private boolean matches(int[] ingredientMap, int x, int y, boolean b) {
        for (int xx = 0; xx < 3; ++xx) {
            for (int yy = 0; yy < 3; ++yy) {
                int rx = xx - x;
                int ry = yy - y;
                int var9 = -1;
                if (rx >= 0 && ry >= 0 && rx < this.width && ry < this.height) {
                    var9 = b ? this.ingredientMap[this.width - rx - 1 + ry * this.width] : this.ingredientMap[rx + ry * this.width];
                }
                if (ingredientMap[xx + yy * 3] == var9) continue;
                return false;
            }
        }
        return true;
    }

    public Item createResult() {
        return this.resultItem.copy();
    }

    public int getRecipeSize() {
        return this.width * this.height;
    }
}

