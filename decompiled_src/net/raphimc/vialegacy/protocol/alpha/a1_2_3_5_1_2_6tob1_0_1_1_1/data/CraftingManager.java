/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.alpha.a1_2_3_5_1_2_6tob1_0_1_1_1.data;

import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.raphimc.vialegacy.api.data.BlockList1_6;
import net.raphimc.vialegacy.api.data.ItemList1_6;
import net.raphimc.vialegacy.protocol.alpha.a1_2_3_5_1_2_6tob1_0_1_1_1.model.CraftingRecipe;

public class CraftingManager {
    private static final List<CraftingRecipe> recipes;
    private static final String[][] tools_pattern;
    private static final int[][] tools_ingredients;
    private static final String[][] weapons_pattern;
    private static final int[][] weapons_ingredients;
    private static final int[][] ingots_ingredients;
    private static final String[][] armor_pattern;
    private static final int[][] armor_ingredients;

    private static void addRecipe(Item resultItem, Object ... objects) {
        StringBuilder var3 = new StringBuilder();
        int pos = 0;
        int width = 0;
        int height = 0;
        if (objects[pos] instanceof String[]) {
            String[] var11;
            for (String var9 : var11 = (String[])objects[pos++]) {
                ++height;
                width = var9.length();
                var3.append(var9);
            }
        } else {
            while (objects[pos] instanceof String) {
                String var7 = (String)objects[pos++];
                ++height;
                width = var7.length();
                var3.append(var7);
            }
        }
        HashMap<Character, Integer> lookup = new HashMap<Character, Integer>();
        while (pos < objects.length) {
            lookup.put(Character.valueOf(((Character)objects[pos]).charValue()), (Integer)objects[pos + 1]);
            pos += 2;
        }
        int[] ingredientMap = new int[width * height];
        for (int i = 0; i < ingredientMap.length; ++i) {
            ingredientMap[i] = lookup.getOrDefault(Character.valueOf(var3.charAt(i)), -1);
        }
        recipes.add(new CraftingRecipe(width, height, ingredientMap, resultItem));
    }

    public static Item getResult(Item[] craftingGrid) {
        int gridSize = (int)Math.sqrt(craftingGrid.length);
        int[] ingredientMap = new int[9];
        for (int x = 0; x < 3; ++x) {
            for (int y = 0; y < 3; ++y) {
                Item item;
                int ingredient = -1;
                if (x < gridSize && y < gridSize && (item = craftingGrid[x + y * gridSize]) != null) {
                    ingredient = item.identifier();
                }
                ingredientMap[x + y * 3] = ingredient;
            }
        }
        return CraftingManager.getResult(ingredientMap);
    }

    public static Item getResult(int[] ingredientMap) {
        for (CraftingRecipe recipe : recipes) {
            if (!recipe.matches(ingredientMap)) continue;
            return recipe.createResult();
        }
        return null;
    }

    static {
        int i1;
        int i;
        recipes = new ArrayList<CraftingRecipe>();
        tools_pattern = new String[][]{{"XXX", " # ", " # "}, {"X", "#", "#"}, {"XX", "X#", " #"}, {"XX", " #", " #"}};
        tools_ingredients = new int[][]{{BlockList1_6.planks.blockId(), BlockList1_6.cobblestone.blockId(), ItemList1_6.ingotIron.itemId(), ItemList1_6.diamond.itemId(), ItemList1_6.ingotGold.itemId()}, {ItemList1_6.pickaxeWood.itemId(), ItemList1_6.pickaxeStone.itemId(), ItemList1_6.pickaxeIron.itemId(), ItemList1_6.pickaxeDiamond.itemId(), ItemList1_6.pickaxeGold.itemId()}, {ItemList1_6.shovelWood.itemId(), ItemList1_6.shovelStone.itemId(), ItemList1_6.shovelIron.itemId(), ItemList1_6.shovelDiamond.itemId(), ItemList1_6.shovelGold.itemId()}, {ItemList1_6.axeWood.itemId(), ItemList1_6.axeStone.itemId(), ItemList1_6.axeIron.itemId(), ItemList1_6.axeDiamond.itemId(), ItemList1_6.axeGold.itemId()}, {ItemList1_6.hoeWood.itemId(), ItemList1_6.hoeStone.itemId(), ItemList1_6.hoeIron.itemId(), ItemList1_6.hoeDiamond.itemId(), ItemList1_6.hoeGold.itemId()}};
        weapons_pattern = new String[][]{{"X", "X", "#"}};
        weapons_ingredients = new int[][]{{BlockList1_6.planks.blockId(), BlockList1_6.cobblestone.blockId(), ItemList1_6.ingotIron.itemId(), ItemList1_6.diamond.itemId(), ItemList1_6.ingotGold.itemId()}, {ItemList1_6.swordWood.itemId(), ItemList1_6.swordStone.itemId(), ItemList1_6.swordIron.itemId(), ItemList1_6.swordDiamond.itemId(), ItemList1_6.swordGold.itemId()}};
        ingots_ingredients = new int[][]{{BlockList1_6.blockGold.blockId(), ItemList1_6.ingotGold.itemId()}, {BlockList1_6.blockIron.blockId(), ItemList1_6.ingotIron.itemId()}, {BlockList1_6.blockDiamond.blockId(), ItemList1_6.diamond.itemId()}};
        armor_pattern = new String[][]{{"XXX", "X X"}, {"X X", "XXX", "XXX"}, {"XXX", "X X", "X X"}, {"X X", "X X"}};
        armor_ingredients = new int[][]{{ItemList1_6.leather.itemId(), BlockList1_6.fire.blockId(), ItemList1_6.ingotIron.itemId(), ItemList1_6.diamond.itemId(), ItemList1_6.ingotGold.itemId()}, {ItemList1_6.helmetLeather.itemId(), ItemList1_6.helmetChain.itemId(), ItemList1_6.helmetIron.itemId(), ItemList1_6.helmetDiamond.itemId(), ItemList1_6.helmetGold.itemId()}, {ItemList1_6.plateLeather.itemId(), ItemList1_6.plateChain.itemId(), ItemList1_6.plateIron.itemId(), ItemList1_6.plateDiamond.itemId(), ItemList1_6.plateGold.itemId()}, {ItemList1_6.legsLeather.itemId(), ItemList1_6.legsChain.itemId(), ItemList1_6.legsIron.itemId(), ItemList1_6.legsDiamond.itemId(), ItemList1_6.legsGold.itemId()}, {ItemList1_6.bootsLeather.itemId(), ItemList1_6.bootsChain.itemId(), ItemList1_6.bootsIron.itemId(), ItemList1_6.bootsDiamond.itemId(), ItemList1_6.bootsGold.itemId()}};
        for (i = 0; i < tools_ingredients[0].length; ++i) {
            for (i1 = 0; i1 < tools_ingredients.length - 1; ++i1) {
                CraftingManager.addRecipe(new DataItem(tools_ingredients[i1 + 1][i], 1, 0, null), tools_pattern[i1], Character.valueOf('#'), ItemList1_6.stick.itemId(), Character.valueOf('X'), tools_ingredients[0][i]);
            }
        }
        for (i = 0; i < weapons_ingredients[0].length; ++i) {
            for (i1 = 0; i1 < weapons_ingredients.length - 1; ++i1) {
                CraftingManager.addRecipe(new DataItem(weapons_ingredients[i1 + 1][i], 1, 0, null), weapons_pattern[i1], Character.valueOf('#'), ItemList1_6.stick.itemId(), Character.valueOf('X'), weapons_ingredients[0][i]);
            }
        }
        CraftingManager.addRecipe(new DataItem(ItemList1_6.bow.itemId(), 1, 0, null), " #X", "# X", " #X", Character.valueOf('X'), ItemList1_6.silk.itemId(), Character.valueOf('#'), ItemList1_6.stick.itemId());
        CraftingManager.addRecipe(new DataItem(ItemList1_6.arrow.itemId(), 4, 0, null), "X", "#", "Y", Character.valueOf('Y'), ItemList1_6.feather.itemId(), Character.valueOf('X'), ItemList1_6.flint.itemId(), Character.valueOf('#'), ItemList1_6.stick.itemId());
        int[][] i2 = ingots_ingredients;
        i1 = i2.length;
        for (int j = 0; j < i1; ++j) {
            int[] ingots_ingredient = i2[j];
            CraftingManager.addRecipe(new DataItem(ingots_ingredient[0], 1, 0, null), "###", "###", "###", Character.valueOf('#'), ingots_ingredient[1]);
            CraftingManager.addRecipe(new DataItem(ingots_ingredient[1], 9, 0, null), "#", Character.valueOf('#'), ingots_ingredient[0]);
        }
        CraftingManager.addRecipe(new DataItem(ItemList1_6.bowlSoup.itemId(), 1, 0, null), "Y", "X", "#", Character.valueOf('X'), BlockList1_6.mushroomBrown.blockId(), Character.valueOf('Y'), BlockList1_6.mushroomRed.blockId(), Character.valueOf('#'), ItemList1_6.bowlEmpty.itemId());
        CraftingManager.addRecipe(new DataItem(ItemList1_6.bowlSoup.itemId(), 1, 0, null), "Y", "X", "#", Character.valueOf('X'), BlockList1_6.mushroomRed.blockId(), Character.valueOf('Y'), BlockList1_6.mushroomBrown.blockId(), Character.valueOf('#'), ItemList1_6.bowlEmpty.itemId());
        CraftingManager.addRecipe(new DataItem(BlockList1_6.chest.blockId(), 1, 0, null), "###", "# #", "###", Character.valueOf('#'), BlockList1_6.planks.blockId());
        CraftingManager.addRecipe(new DataItem(BlockList1_6.furnaceIdle.blockId(), 1, 0, null), "###", "# #", "###", Character.valueOf('#'), BlockList1_6.cobblestone.blockId());
        CraftingManager.addRecipe(new DataItem(BlockList1_6.workbench.blockId(), 1, 0, null), "##", "##", Character.valueOf('#'), BlockList1_6.planks.blockId());
        for (int i3 = 0; i3 < armor_ingredients[0].length; ++i3) {
            for (i1 = 0; i1 < armor_ingredients.length - 1; ++i1) {
                CraftingManager.addRecipe(new DataItem(armor_ingredients[i1 + 1][i3], 1, 0, null), armor_pattern[i1], Character.valueOf('X'), armor_ingredients[0][i3]);
            }
        }
        CraftingManager.addRecipe(new DataItem(ItemList1_6.paper.itemId(), 3, 0, null), "###", Character.valueOf('#'), ItemList1_6.reed.itemId());
        CraftingManager.addRecipe(new DataItem(ItemList1_6.book.itemId(), 1, 0, null), "#", "#", "#", Character.valueOf('#'), ItemList1_6.paper.itemId());
        CraftingManager.addRecipe(new DataItem(BlockList1_6.fence.blockId(), 2, 0, null), "###", "###", Character.valueOf('#'), ItemList1_6.stick.itemId());
        CraftingManager.addRecipe(new DataItem(BlockList1_6.jukebox.blockId(), 1, 0, null), "###", "#X#", "###", Character.valueOf('#'), BlockList1_6.planks.blockId(), Character.valueOf('X'), ItemList1_6.diamond.itemId());
        CraftingManager.addRecipe(new DataItem(BlockList1_6.bookShelf.blockId(), 1, 0, null), "###", "XXX", "###", Character.valueOf('#'), BlockList1_6.planks.blockId(), Character.valueOf('X'), ItemList1_6.book.itemId());
        CraftingManager.addRecipe(new DataItem(BlockList1_6.blockSnow.blockId(), 1, 0, null), "##", "##", Character.valueOf('#'), ItemList1_6.snowball.itemId());
        CraftingManager.addRecipe(new DataItem(BlockList1_6.blockClay.blockId(), 1, 0, null), "##", "##", Character.valueOf('#'), ItemList1_6.clay.itemId());
        CraftingManager.addRecipe(new DataItem(BlockList1_6.brick.blockId(), 1, 0, null), "##", "##", Character.valueOf('#'), ItemList1_6.brick.itemId());
        CraftingManager.addRecipe(new DataItem(BlockList1_6.glowStone.blockId(), 1, 0, null), "###", "###", "###", Character.valueOf('#'), ItemList1_6.glowstone.itemId());
        CraftingManager.addRecipe(new DataItem(BlockList1_6.cloth.blockId(), 1, 0, null), "###", "###", "###", Character.valueOf('#'), ItemList1_6.silk.itemId());
        CraftingManager.addRecipe(new DataItem(BlockList1_6.tnt.blockId(), 1, 0, null), "X#X", "#X#", "X#X", Character.valueOf('X'), ItemList1_6.gunpowder.itemId(), Character.valueOf('#'), BlockList1_6.sand.blockId());
        CraftingManager.addRecipe(new DataItem(BlockList1_6.stoneSingleSlab.blockId(), 3, 0, null), "###", Character.valueOf('#'), BlockList1_6.cobblestone.blockId());
        CraftingManager.addRecipe(new DataItem(BlockList1_6.ladder.blockId(), 1, 0, null), "# #", "###", "# #", Character.valueOf('#'), ItemList1_6.stick.itemId());
        CraftingManager.addRecipe(new DataItem(ItemList1_6.doorWood.itemId(), 1, 0, null), "##", "##", "##", Character.valueOf('#'), BlockList1_6.planks.blockId());
        CraftingManager.addRecipe(new DataItem(ItemList1_6.doorIron.itemId(), 1, 0, null), "##", "##", "##", Character.valueOf('#'), ItemList1_6.ingotIron.itemId());
        CraftingManager.addRecipe(new DataItem(ItemList1_6.sign.itemId(), 1, 0, null), "###", "###", " X ", Character.valueOf('#'), BlockList1_6.planks.blockId(), Character.valueOf('X'), ItemList1_6.stick.itemId());
        CraftingManager.addRecipe(new DataItem(BlockList1_6.planks.blockId(), 4, 0, null), "#", Character.valueOf('#'), BlockList1_6.wood.blockId());
        CraftingManager.addRecipe(new DataItem(ItemList1_6.stick.itemId(), 4, 0, null), "#", "#", Character.valueOf('#'), BlockList1_6.planks.blockId());
        CraftingManager.addRecipe(new DataItem(BlockList1_6.torchWood.blockId(), 4, 0, null), "X", "#", Character.valueOf('X'), ItemList1_6.coal.itemId(), Character.valueOf('#'), ItemList1_6.stick.itemId());
        CraftingManager.addRecipe(new DataItem(ItemList1_6.bowlEmpty.itemId(), 4, 0, null), "# #", " # ", Character.valueOf('#'), BlockList1_6.planks.blockId());
        CraftingManager.addRecipe(new DataItem(BlockList1_6.rail.blockId(), 16, 0, null), "X X", "X#X", "X X", Character.valueOf('X'), ItemList1_6.ingotIron.itemId(), Character.valueOf('#'), ItemList1_6.stick.itemId());
        CraftingManager.addRecipe(new DataItem(ItemList1_6.minecartEmpty.itemId(), 1, 0, null), "# #", "###", Character.valueOf('#'), ItemList1_6.ingotIron.itemId());
        CraftingManager.addRecipe(new DataItem(BlockList1_6.pumpkinLantern.blockId(), 1, 0, null), "A", "B", Character.valueOf('A'), BlockList1_6.pumpkin.blockId(), Character.valueOf('B'), BlockList1_6.torchWood.blockId());
        CraftingManager.addRecipe(new DataItem(ItemList1_6.minecartCrate.itemId(), 1, 0, null), "A", "B", Character.valueOf('A'), BlockList1_6.chest.blockId(), Character.valueOf('B'), ItemList1_6.minecartEmpty.itemId());
        CraftingManager.addRecipe(new DataItem(ItemList1_6.minecartPowered.itemId(), 1, 0, null), "A", "B", Character.valueOf('A'), BlockList1_6.furnaceIdle.blockId(), Character.valueOf('B'), ItemList1_6.minecartEmpty.itemId());
        CraftingManager.addRecipe(new DataItem(ItemList1_6.boat.itemId(), 1, 0, null), "# #", "###", Character.valueOf('#'), BlockList1_6.planks.blockId());
        CraftingManager.addRecipe(new DataItem(ItemList1_6.bucketEmpty.itemId(), 1, 0, null), "# #", " # ", Character.valueOf('#'), ItemList1_6.ingotIron.itemId());
        CraftingManager.addRecipe(new DataItem(ItemList1_6.flintAndSteel.itemId(), 1, 0, null), "A ", " B", Character.valueOf('A'), ItemList1_6.ingotIron.itemId(), Character.valueOf('B'), ItemList1_6.flint.itemId());
        CraftingManager.addRecipe(new DataItem(ItemList1_6.bread.itemId(), 1, 0, null), "###", Character.valueOf('#'), ItemList1_6.wheat.itemId());
        CraftingManager.addRecipe(new DataItem(BlockList1_6.stairsWoodOak.blockId(), 4, 0, null), "#  ", "## ", "###", Character.valueOf('#'), BlockList1_6.planks.blockId());
        CraftingManager.addRecipe(new DataItem(ItemList1_6.fishingRod.itemId(), 1, 0, null), "  #", " #X", "# X", Character.valueOf('#'), ItemList1_6.stick.itemId(), Character.valueOf('X'), ItemList1_6.silk.itemId());
        CraftingManager.addRecipe(new DataItem(BlockList1_6.stairsCobblestone.blockId(), 4, 0, null), "#  ", "## ", "###", Character.valueOf('#'), BlockList1_6.cobblestone.blockId());
        CraftingManager.addRecipe(new DataItem(ItemList1_6.painting.itemId(), 1, 0, null), "###", "#X#", "###", Character.valueOf('#'), ItemList1_6.stick.itemId(), Character.valueOf('X'), BlockList1_6.cloth.blockId());
        CraftingManager.addRecipe(new DataItem(ItemList1_6.appleGold.itemId(), 1, 0, null), "###", "#X#", "###", Character.valueOf('#'), BlockList1_6.blockGold.blockId(), Character.valueOf('X'), ItemList1_6.appleRed.itemId());
        CraftingManager.addRecipe(new DataItem(BlockList1_6.lever.blockId(), 1, 0, null), "X", "#", Character.valueOf('#'), BlockList1_6.cobblestone.blockId(), Character.valueOf('X'), ItemList1_6.stick.itemId());
        CraftingManager.addRecipe(new DataItem(BlockList1_6.torchRedstoneActive.blockId(), 1, 0, null), "X", "#", Character.valueOf('#'), ItemList1_6.stick.itemId(), Character.valueOf('X'), ItemList1_6.redstone.itemId());
        CraftingManager.addRecipe(new DataItem(ItemList1_6.pocketSundial.itemId(), 1, 0, null), " # ", "#X#", " # ", Character.valueOf('#'), ItemList1_6.ingotGold.itemId(), Character.valueOf('X'), ItemList1_6.redstone.itemId());
        CraftingManager.addRecipe(new DataItem(ItemList1_6.compass.itemId(), 1, 0, null), " # ", "#X#", " # ", Character.valueOf('#'), ItemList1_6.ingotIron.itemId(), Character.valueOf('X'), ItemList1_6.redstone.itemId());
        CraftingManager.addRecipe(new DataItem(BlockList1_6.stoneButton.blockId(), 1, 0, null), "#", "#", Character.valueOf('#'), BlockList1_6.stone.blockId());
        CraftingManager.addRecipe(new DataItem(BlockList1_6.pressurePlateStone.blockId(), 1, 0, null), "###", Character.valueOf('#'), BlockList1_6.stone.blockId());
        CraftingManager.addRecipe(new DataItem(BlockList1_6.pressurePlatePlanks.blockId(), 1, 0, null), "###", Character.valueOf('#'), BlockList1_6.planks.blockId());
        recipes.sort((o1, o2) -> Integer.compare(o2.getRecipeSize(), o1.getRecipeSize()));
    }
}

