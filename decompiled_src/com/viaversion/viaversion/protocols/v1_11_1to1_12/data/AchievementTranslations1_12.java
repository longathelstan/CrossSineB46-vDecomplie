/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.v1_11_1to1_12.data;

import com.viaversion.viaversion.libs.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.HashSet;
import java.util.Set;

public class AchievementTranslations1_12 {
    private static final Object2ObjectOpenHashMap<String, String> ACHIEVEMENTS = new Object2ObjectOpenHashMap(150);
    private static final Set<String> SPECIAL_ACHIEVEMENTS = new HashSet<String>(10);

    private static void add(String key, String value) {
        ACHIEVEMENTS.put(key, value);
    }

    private static void addSpecial(String key, String value) {
        AchievementTranslations1_12.add(key, value);
        SPECIAL_ACHIEVEMENTS.add(key);
    }

    public static String get(String key) {
        return ACHIEVEMENTS.get(key);
    }

    public static boolean isSpecial(String key) {
        return SPECIAL_ACHIEVEMENTS.contains(key);
    }

    static {
        AchievementTranslations1_12.add("chat.type.achievement", "%s has just earned the achievement %s");
        AchievementTranslations1_12.add("chat.type.achievement.taken", "%s has lost the achievement %s");
        AchievementTranslations1_12.add("stats.tooltip.type.achievement", "Achievement");
        AchievementTranslations1_12.add("stats.tooltip.type.statistic", "Statistic");
        AchievementTranslations1_12.add("stat.generalButton", "General");
        AchievementTranslations1_12.add("stat.blocksButton", "Blocks");
        AchievementTranslations1_12.add("stat.itemsButton", "Items");
        AchievementTranslations1_12.add("stat.mobsButton", "Mobs");
        AchievementTranslations1_12.add("stat.used", "Times Used");
        AchievementTranslations1_12.add("stat.mined", "Times Mined");
        AchievementTranslations1_12.add("stat.depleted", "Times Depleted");
        AchievementTranslations1_12.add("stat.crafted", "Times Crafted");
        AchievementTranslations1_12.add("stat.entityKills", "You killed %s %s");
        AchievementTranslations1_12.add("stat.entityKilledBy", "%s killed you %s time(s)");
        AchievementTranslations1_12.add("stat.entityKills.none", "You have never killed %s");
        AchievementTranslations1_12.add("stat.entityKilledBy.none", "You have never been killed by %s");
        AchievementTranslations1_12.add("stat.startGame", "Times Played");
        AchievementTranslations1_12.add("stat.createWorld", "Worlds Created");
        AchievementTranslations1_12.add("stat.loadWorld", "Saves Loaded");
        AchievementTranslations1_12.add("stat.joinMultiplayer", "Multiplayer Joins");
        AchievementTranslations1_12.add("stat.leaveGame", "Games Quit");
        AchievementTranslations1_12.add("stat.playOneMinute", "Minutes Played");
        AchievementTranslations1_12.add("stat.timeSinceDeath", "Since Last Death");
        AchievementTranslations1_12.add("stat.sneakTime", "Sneak Time");
        AchievementTranslations1_12.add("stat.walkOneCm", "Distance Walked");
        AchievementTranslations1_12.add("stat.crouchOneCm", "Distance Crouched");
        AchievementTranslations1_12.add("stat.sprintOneCm", "Distance Sprinted");
        AchievementTranslations1_12.add("stat.fallOneCm", "Distance Fallen");
        AchievementTranslations1_12.add("stat.swimOneCm", "Distance Swum");
        AchievementTranslations1_12.add("stat.flyOneCm", "Distance Flown");
        AchievementTranslations1_12.add("stat.climbOneCm", "Distance Climbed");
        AchievementTranslations1_12.add("stat.diveOneCm", "Distance Dove");
        AchievementTranslations1_12.add("stat.minecartOneCm", "Distance by Minecart");
        AchievementTranslations1_12.add("stat.boatOneCm", "Distance by Boat");
        AchievementTranslations1_12.add("stat.pigOneCm", "Distance by Pig");
        AchievementTranslations1_12.add("stat.horseOneCm", "Distance by Horse");
        AchievementTranslations1_12.add("stat.aviateOneCm", "Distance by Elytra");
        AchievementTranslations1_12.add("stat.jump", "Jumps");
        AchievementTranslations1_12.add("stat.drop", "Items Dropped");
        AchievementTranslations1_12.add("stat.dropped", "Dropped");
        AchievementTranslations1_12.add("stat.pickup", "Picked Up");
        AchievementTranslations1_12.add("stat.damageDealt", "Damage Dealt");
        AchievementTranslations1_12.add("stat.damageTaken", "Damage Taken");
        AchievementTranslations1_12.add("stat.deaths", "Number of Deaths");
        AchievementTranslations1_12.add("stat.mobKills", "Mob Kills");
        AchievementTranslations1_12.add("stat.animalsBred", "Animals Bred");
        AchievementTranslations1_12.add("stat.playerKills", "Player Kills");
        AchievementTranslations1_12.add("stat.fishCaught", "Fish Caught");
        AchievementTranslations1_12.add("stat.treasureFished", "Treasure Fished");
        AchievementTranslations1_12.add("stat.junkFished", "Junk Fished");
        AchievementTranslations1_12.add("stat.talkedToVillager", "Talked to Villagers");
        AchievementTranslations1_12.add("stat.tradedWithVillager", "Traded with Villagers");
        AchievementTranslations1_12.add("stat.cakeSlicesEaten", "Cake Slices Eaten");
        AchievementTranslations1_12.add("stat.cauldronFilled", "Cauldrons Filled");
        AchievementTranslations1_12.add("stat.cauldronUsed", "Water Taken from Cauldron");
        AchievementTranslations1_12.add("stat.armorCleaned", "Armor Pieces Cleaned");
        AchievementTranslations1_12.add("stat.bannerCleaned", "Banners Cleaned");
        AchievementTranslations1_12.add("stat.brewingstandInteraction", "Interactions with Brewing Stand");
        AchievementTranslations1_12.add("stat.beaconInteraction", "Interactions with Beacon");
        AchievementTranslations1_12.add("stat.dropperInspected", "Droppers Searched");
        AchievementTranslations1_12.add("stat.hopperInspected", "Hoppers Searched");
        AchievementTranslations1_12.add("stat.dispenserInspected", "Dispensers Searched");
        AchievementTranslations1_12.add("stat.noteblockPlayed", "Note Blocks Played");
        AchievementTranslations1_12.add("stat.noteblockTuned", "Note Blocks Tuned");
        AchievementTranslations1_12.add("stat.flowerPotted", "Plants Potted");
        AchievementTranslations1_12.add("stat.trappedChestTriggered", "Trapped Chests Triggered");
        AchievementTranslations1_12.add("stat.enderchestOpened", "Ender Chests Opened");
        AchievementTranslations1_12.add("stat.itemEnchanted", "Items Enchanted");
        AchievementTranslations1_12.add("stat.recordPlayed", "Records Played");
        AchievementTranslations1_12.add("stat.furnaceInteraction", "Interactions with Furnace");
        AchievementTranslations1_12.add("stat.workbenchInteraction", "Interactions with Crafting Table");
        AchievementTranslations1_12.add("stat.chestOpened", "Chests Opened");
        AchievementTranslations1_12.add("stat.shulkerBoxOpened", "Shulker Boxes Opened");
        AchievementTranslations1_12.add("stat.sleepInBed", "Times Slept in a Bed");
        AchievementTranslations1_12.add("stat.mineBlock", "%1$s Mined");
        AchievementTranslations1_12.add("stat.craftItem", "%1$s Crafted");
        AchievementTranslations1_12.add("stat.useItem", "%1$s Used");
        AchievementTranslations1_12.add("stat.breakItem", "%1$s Depleted");
        AchievementTranslations1_12.add("achievement.get", "Achievement get!");
        AchievementTranslations1_12.add("achievement.taken", "Taken!");
        AchievementTranslations1_12.add("achievement.unknown", "???");
        AchievementTranslations1_12.add("achievement.requires", "Requires '%1$s'");
        AchievementTranslations1_12.add("achievement.openInventory", "Taking Inventory");
        AchievementTranslations1_12.add("achievement.openInventory.desc", "Press 'E' to open your inventory");
        AchievementTranslations1_12.add("achievement.mineWood", "Getting Wood");
        AchievementTranslations1_12.add("achievement.mineWood.desc", "Attack a tree until a block of wood pops out");
        AchievementTranslations1_12.add("achievement.buildWorkBench", "Benchmarking");
        AchievementTranslations1_12.add("achievement.buildWorkBench.desc", "Craft a workbench with four blocks of planks");
        AchievementTranslations1_12.add("achievement.buildPickaxe", "Time to Mine!");
        AchievementTranslations1_12.add("achievement.buildPickaxe.desc", "Use planks and sticks to make a pickaxe");
        AchievementTranslations1_12.add("achievement.buildFurnace", "Hot Topic");
        AchievementTranslations1_12.add("achievement.buildFurnace.desc", "Construct a furnace out of eight cobblestone blocks");
        AchievementTranslations1_12.add("achievement.acquireIron", "Acquire Hardware");
        AchievementTranslations1_12.add("achievement.acquireIron.desc", "Smelt an iron ingot");
        AchievementTranslations1_12.add("achievement.buildHoe", "Time to Farm!");
        AchievementTranslations1_12.add("achievement.buildHoe.desc", "Use planks and sticks to make a hoe");
        AchievementTranslations1_12.add("achievement.makeBread", "Bake Bread");
        AchievementTranslations1_12.add("achievement.makeBread.desc", "Turn wheat into bread");
        AchievementTranslations1_12.add("achievement.bakeCake", "The Lie");
        AchievementTranslations1_12.add("achievement.bakeCake.desc", "Wheat, sugar, milk and eggs!");
        AchievementTranslations1_12.add("achievement.buildBetterPickaxe", "Getting an Upgrade");
        AchievementTranslations1_12.add("achievement.buildBetterPickaxe.desc", "Construct a better pickaxe");
        AchievementTranslations1_12.addSpecial("achievement.overpowered", "Overpowered");
        AchievementTranslations1_12.add("achievement.overpowered.desc", "Eat a Notch apple");
        AchievementTranslations1_12.add("achievement.cookFish", "Delicious Fish");
        AchievementTranslations1_12.add("achievement.cookFish.desc", "Catch and cook fish!");
        AchievementTranslations1_12.addSpecial("achievement.onARail", "On A Rail");
        AchievementTranslations1_12.add("achievement.onARail.desc", "Travel by minecart at least 1 km from where you started");
        AchievementTranslations1_12.add("achievement.buildSword", "Time to Strike!");
        AchievementTranslations1_12.add("achievement.buildSword.desc", "Use planks and sticks to make a sword");
        AchievementTranslations1_12.add("achievement.killEnemy", "Monster Hunter");
        AchievementTranslations1_12.add("achievement.killEnemy.desc", "Attack and destroy a monster");
        AchievementTranslations1_12.add("achievement.killCow", "Cow Tipper");
        AchievementTranslations1_12.add("achievement.killCow.desc", "Harvest some leather");
        AchievementTranslations1_12.add("achievement.breedCow", "Repopulation");
        AchievementTranslations1_12.add("achievement.breedCow.desc", "Breed two cows with wheat");
        AchievementTranslations1_12.addSpecial("achievement.flyPig", "When Pigs Fly");
        AchievementTranslations1_12.add("achievement.flyPig.desc", "Fly a pig off a cliff");
        AchievementTranslations1_12.addSpecial("achievement.snipeSkeleton", "Sniper Duel");
        AchievementTranslations1_12.add("achievement.snipeSkeleton.desc", "Kill a skeleton with an arrow from more than 50 meters");
        AchievementTranslations1_12.add("achievement.diamonds", "DIAMONDS!");
        AchievementTranslations1_12.add("achievement.diamonds.desc", "Acquire diamonds with your iron tools");
        AchievementTranslations1_12.add("achievement.diamondsToYou", "Diamonds to you!");
        AchievementTranslations1_12.add("achievement.diamondsToYou.desc", "Throw diamonds at another player");
        AchievementTranslations1_12.add("achievement.portal", "We Need to Go Deeper");
        AchievementTranslations1_12.add("achievement.portal.desc", "Build a portal to the Nether");
        AchievementTranslations1_12.addSpecial("achievement.ghast", "Return to Sender");
        AchievementTranslations1_12.add("achievement.ghast.desc", "Destroy a Ghast with a fireball");
        AchievementTranslations1_12.add("achievement.blazeRod", "Into Fire");
        AchievementTranslations1_12.add("achievement.blazeRod.desc", "Relieve a Blaze of its rod");
        AchievementTranslations1_12.add("achievement.potion", "Local Brewery");
        AchievementTranslations1_12.add("achievement.potion.desc", "Brew a potion");
        AchievementTranslations1_12.addSpecial("achievement.theEnd", "The End?");
        AchievementTranslations1_12.add("achievement.theEnd.desc", "Locate the End");
        AchievementTranslations1_12.addSpecial("achievement.theEnd2", "The End.");
        AchievementTranslations1_12.add("achievement.theEnd2.desc", "Defeat the Ender Dragon");
        AchievementTranslations1_12.add("achievement.spawnWither", "The Beginning?");
        AchievementTranslations1_12.add("achievement.spawnWither.desc", "Spawn the Wither");
        AchievementTranslations1_12.add("achievement.killWither", "The Beginning.");
        AchievementTranslations1_12.add("achievement.killWither.desc", "Kill the Wither");
        AchievementTranslations1_12.addSpecial("achievement.fullBeacon", "Beaconator");
        AchievementTranslations1_12.add("achievement.fullBeacon.desc", "Create a full beacon");
        AchievementTranslations1_12.addSpecial("achievement.exploreAllBiomes", "Adventuring Time");
        AchievementTranslations1_12.add("achievement.exploreAllBiomes.desc", "Discover all biomes");
        AchievementTranslations1_12.add("achievement.enchantments", "Enchanter");
        AchievementTranslations1_12.add("achievement.enchantments.desc", "Use a book, obsidian and diamonds to construct an enchantment table");
        AchievementTranslations1_12.addSpecial("achievement.overkill", "Overkill");
        AchievementTranslations1_12.add("achievement.overkill.desc", "Deal nine hearts of damage in a single hit");
        AchievementTranslations1_12.add("achievement.bookcase", "Librarian");
        AchievementTranslations1_12.add("achievement.bookcase.desc", "Build some bookshelves to improve your enchantment table");
    }
}

