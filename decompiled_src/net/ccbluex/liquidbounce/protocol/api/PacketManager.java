/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.protocol.api;

import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.protocol.api.ProtocolFixer;
import net.ccbluex.liquidbounce.utils.AnimationUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;

public class PacketManager
extends MinecraftInstance
implements Listenable {
    public static float eyeHeight;
    public static float lastEyeHeight;

    @EventTarget
    public void onMotion(MotionEvent event) {
        PacketManager.mc.field_71429_W = 0;
        float START_HEIGHT = 1.62f;
        lastEyeHeight = eyeHeight;
        float END_HEIGHT = ProtocolFixer.newerThanOrEqualsTo1_9() && ProtocolFixer.olderThanOrEqualsTo1_13_2() ? 1.47f : (ProtocolFixer.newerThanOrEqualsTo1_14() ? 1.32f : 1.54f);
        float delta = ProtocolFixer.newerThanOrEqualsTo1_9() && ProtocolFixer.olderThanOrEqualsTo1_13_2() ? 0.147f : (ProtocolFixer.newerThanOrEqualsTo1_14() ? 0.132f : 0.154f);
        if (PacketManager.mc.field_71439_g.func_70093_af()) {
            eyeHeight = AnimationUtils.animate(END_HEIGHT, eyeHeight, 4.0f * delta);
        } else if (eyeHeight < START_HEIGHT) {
            eyeHeight = AnimationUtils.animate(START_HEIGHT, eyeHeight, 4.0f * delta);
        }
    }

    @Override
    public boolean handleEvents() {
        return true;
    }
}

