/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.protocol.api;

import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.PushOutEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.protocol.api.ProtocolFixer;
import net.ccbluex.liquidbounce.utils.AnimationUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.block.material.Material;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class McUpdatesHandler
extends MinecraftInstance
implements Listenable {
    public static boolean isSwimmingOrCrawling = false;
    public static boolean doingEyeRot = false;
    public static float eyeHeight;
    public static float lastEyeHeight;

    private static boolean underWater() {
        double eyeBlock;
        BlockPos blockPos;
        World world = McUpdatesHandler.mc.field_71439_g.func_130014_f_();
        return world.func_180495_p(blockPos = new BlockPos(McUpdatesHandler.mc.field_71439_g.field_70165_t, eyeBlock = McUpdatesHandler.mc.field_71439_g.field_70163_u + (double)McUpdatesHandler.mc.field_71439_g.func_70047_e() - 0.25, McUpdatesHandler.mc.field_71439_g.field_70161_v)).func_177230_c().func_149688_o() == Material.field_151586_h && !(McUpdatesHandler.mc.field_71439_g.field_70154_o instanceof EntityBoat);
    }

    private static boolean isSwimming() {
        return !McUpdatesHandler.mc.field_71439_g.field_70145_X && McUpdatesHandler.mc.field_71439_g.func_70090_H() && McUpdatesHandler.mc.field_71439_g.func_70051_ag();
    }

    public static boolean shouldAnimation() {
        AxisAlignedBB box = McUpdatesHandler.mc.field_71439_g.func_174813_aQ();
        AxisAlignedBB crawl = new AxisAlignedBB(box.field_72340_a, box.field_72338_b + 0.9, box.field_72339_c, box.field_72340_a + 0.6, box.field_72338_b + 1.5, box.field_72339_c + 0.6);
        return !McUpdatesHandler.mc.field_71439_g.field_70145_X && (isSwimmingOrCrawling && McUpdatesHandler.mc.field_71439_g.func_70051_ag() && McUpdatesHandler.mc.field_71439_g.func_70090_H() || isSwimmingOrCrawling && !McUpdatesHandler.mc.field_71441_e.func_147461_a(crawl).isEmpty());
    }

    @EventTarget
    public void onPushOut(PushOutEvent event) {
        if (ProtocolFixer.newerThanOrEqualsTo1_13() && (McUpdatesHandler.shouldAnimation() || McUpdatesHandler.mc.field_71439_g.func_70093_af())) {
            event.cancelEvent();
        }
    }

    @EventTarget
    public void onMotion(MotionEvent event) {
        if (ProtocolFixer.newerThanOrEqualsTo1_13()) {
            float START_HEIGHT = 1.62f;
            lastEyeHeight = eyeHeight;
            float END_HEIGHT = 0.45f;
            float delta = 0.085f;
            if (McUpdatesHandler.shouldAnimation()) {
                eyeHeight = AnimationUtils.animate(END_HEIGHT, eyeHeight, 4.0f * delta);
                doingEyeRot = true;
            } else if (eyeHeight < START_HEIGHT) {
                eyeHeight = AnimationUtils.animate(START_HEIGHT, eyeHeight, 4.0f * delta);
            }
            if (eyeHeight >= START_HEIGHT && doingEyeRot) {
                doingEyeRot = false;
            }
        }
    }

    @EventTarget
    public void onUpdate(UpdateEvent event) {
        float newWidth;
        float newHeight;
        if (ProtocolFixer.newerThanOrEqualsTo1_13() && McUpdatesHandler.isSwimming()) {
            if (McUpdatesHandler.mc.field_71439_g.field_70159_w < -0.4) {
                McUpdatesHandler.mc.field_71439_g.field_70159_w = -0.39f;
            }
            if (McUpdatesHandler.mc.field_71439_g.field_70159_w > 0.4) {
                McUpdatesHandler.mc.field_71439_g.field_70159_w = 0.39f;
            }
            if (McUpdatesHandler.mc.field_71439_g.field_70181_x < -0.4) {
                McUpdatesHandler.mc.field_71439_g.field_70181_x = -0.39f;
            }
            if (McUpdatesHandler.mc.field_71439_g.field_70181_x > 0.4) {
                McUpdatesHandler.mc.field_71439_g.field_70181_x = 0.39f;
            }
            if (McUpdatesHandler.mc.field_71439_g.field_70179_y < -0.4) {
                McUpdatesHandler.mc.field_71439_g.field_70179_y = -0.39f;
            }
            if (McUpdatesHandler.mc.field_71439_g.field_70179_y > 0.4) {
                McUpdatesHandler.mc.field_71439_g.field_70179_y = 0.39f;
            }
            double d3 = McUpdatesHandler.mc.field_71439_g.func_70040_Z().field_72448_b;
            double d4 = 0.025;
            if (d3 <= 0.0 || McUpdatesHandler.mc.field_71439_g.field_70170_p.func_180495_p(new BlockPos(McUpdatesHandler.mc.field_71439_g.field_70165_t, McUpdatesHandler.mc.field_71439_g.field_70163_u + 1.0 - 0.64, McUpdatesHandler.mc.field_71439_g.field_70161_v)).func_177230_c().func_149688_o() == Material.field_151586_h) {
                McUpdatesHandler.mc.field_71439_g.field_70181_x += (d3 - McUpdatesHandler.mc.field_71439_g.field_70181_x) * d4;
            }
            McUpdatesHandler.mc.field_71439_g.field_70181_x += 0.018;
            if (McUpdatesHandler.shouldAnimation()) {
                McUpdatesHandler.mc.field_71439_g.field_70159_w *= (double)1.09f;
                McUpdatesHandler.mc.field_71439_g.field_70179_y *= (double)1.09f;
            }
        }
        float sneakLength = ProtocolFixer.newerThanOrEqualsTo1_9() && ProtocolFixer.olderThanOrEqualsTo1_13_2() ? 1.65f : (ProtocolFixer.newerThanOrEqualsTo1_14() ? 1.5f : 1.8f);
        double d0 = (double)McUpdatesHandler.mc.field_71439_g.field_70130_N / 2.0;
        AxisAlignedBB box = McUpdatesHandler.mc.field_71439_g.func_174813_aQ();
        AxisAlignedBB setThrough = new AxisAlignedBB(McUpdatesHandler.mc.field_71439_g.field_70165_t - d0, box.field_72338_b, McUpdatesHandler.mc.field_71439_g.field_70161_v - d0, McUpdatesHandler.mc.field_71439_g.field_70165_t + d0, box.field_72338_b + (double)McUpdatesHandler.mc.field_71439_g.field_70131_O, McUpdatesHandler.mc.field_71439_g.field_70161_v + d0);
        AxisAlignedBB sneak = new AxisAlignedBB(box.field_72340_a, box.field_72338_b + 0.9, box.field_72339_c, box.field_72340_a + 0.6, box.field_72338_b + 1.8, box.field_72339_c + 0.6);
        AxisAlignedBB crawl = new AxisAlignedBB(box.field_72340_a, box.field_72338_b + 0.9, box.field_72339_c, box.field_72340_a + 0.6, box.field_72338_b + 1.5, box.field_72339_c + 0.6);
        if (ProtocolFixer.newerThanOrEqualsTo1_13() && isSwimmingOrCrawling && McUpdatesHandler.underWater() && (double)McUpdatesHandler.mc.field_71439_g.field_70125_A >= 0.0) {
            newHeight = 0.6f;
            newWidth = 0.6f;
            isSwimmingOrCrawling = true;
            McUpdatesHandler.mc.field_71439_g.func_174826_a(setThrough);
        } else if (ProtocolFixer.newerThanOrEqualsTo1_13() && (McUpdatesHandler.isSwimming() && McUpdatesHandler.underWater() || !McUpdatesHandler.mc.field_71441_e.func_147461_a(crawl).isEmpty())) {
            newHeight = 0.6f;
            newWidth = 0.6f;
            isSwimmingOrCrawling = true;
            McUpdatesHandler.mc.field_71439_g.func_174826_a(setThrough);
        } else if (McUpdatesHandler.mc.field_71439_g.func_70093_af() && !McUpdatesHandler.underWater()) {
            newHeight = sneakLength;
            newWidth = 0.6f;
            McUpdatesHandler.mc.field_71439_g.func_174826_a(setThrough);
        } else {
            if (isSwimmingOrCrawling) {
                isSwimmingOrCrawling = false;
            }
            newHeight = 1.8f;
            newWidth = 0.6f;
            McUpdatesHandler.mc.field_71439_g.func_174826_a(setThrough);
        }
        if (!(!ProtocolFixer.newerThanOrEqualsTo1_9() || !McUpdatesHandler.mc.field_71439_g.field_70122_E || McUpdatesHandler.mc.field_71439_g.func_70093_af() || McUpdatesHandler.underWater() || McUpdatesHandler.mc.field_71439_g.field_70131_O != sneakLength && McUpdatesHandler.mc.field_71439_g.field_70131_O != 0.6f || McUpdatesHandler.mc.field_71441_e.func_147461_a(sneak).isEmpty())) {
            McUpdatesHandler.mc.field_71474_y.field_74311_E.field_74513_e = true;
        } else if (!GameSettings.func_100015_a((KeyBinding)McUpdatesHandler.mc.field_71474_y.field_74311_E) && McUpdatesHandler.mc.field_71441_e.func_147461_a(sneak).isEmpty()) {
            McUpdatesHandler.mc.field_71474_y.field_74311_E.field_74513_e = false;
        }
        try {
            McUpdatesHandler.mc.field_71439_g.field_70131_O = newHeight;
            McUpdatesHandler.mc.field_71439_g.field_70130_N = newWidth;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            // empty catch block
        }
    }

    @Override
    public boolean handleEvents() {
        return true;
    }
}

