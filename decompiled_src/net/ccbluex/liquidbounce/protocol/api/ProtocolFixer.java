/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.protocol.api;

import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import net.ccbluex.liquidbounce.protocol.ProtocolBase;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.MovingObjectPosition;

public class ProtocolFixer {
    private static final Minecraft mc = MinecraftInstance.mc;

    public static void sendConditionalSwing(MovingObjectPosition mop) {
        if (mop != null && mop.field_72313_a != MovingObjectPosition.MovingObjectType.ENTITY) {
            ProtocolFixer.mc.field_71439_g.func_71038_i();
        }
    }

    public static void sendFixedAttack(EntityPlayer entityIn, Entity target, Boolean packetSwing) {
        if (ProtocolFixer.newerThan1_8()) {
            ProtocolFixer.mc.field_71442_b.func_78764_a(entityIn, target);
            if (packetSwing.booleanValue()) {
                mc.func_147114_u().func_147297_a((Packet)new C0APacketAnimation());
            } else {
                ProtocolFixer.mc.field_71439_g.func_71038_i();
            }
        } else {
            if (packetSwing.booleanValue()) {
                mc.func_147114_u().func_147297_a((Packet)new C0APacketAnimation());
            } else {
                ProtocolFixer.mc.field_71439_g.func_71038_i();
            }
            ProtocolFixer.mc.field_71442_b.func_78764_a(entityIn, target);
        }
    }

    public static boolean newerThanOrEqualsTo1_8() {
        return ProtocolBase.getManager().getTargetVersion().newerThanOrEqualTo(ProtocolVersion.v1_8) && !MinecraftInstance.mc.func_71387_A() || MinecraftInstance.mc.func_71387_A();
    }

    public static boolean newerThan1_8() {
        return ProtocolBase.getManager().getTargetVersion().newerThan(ProtocolVersion.v1_8) && !MinecraftInstance.mc.func_71387_A();
    }

    public static boolean newerThanOrEqualsTo1_9() {
        return ProtocolBase.getManager().getTargetVersion().newerThanOrEqualTo(ProtocolVersion.v1_9) && !MinecraftInstance.mc.func_71387_A();
    }

    public static boolean newerThanOrEqualsTo1_13() {
        return ProtocolBase.getManager().getTargetVersion().newerThanOrEqualTo(ProtocolVersion.v1_13) && !MinecraftInstance.mc.func_71387_A();
    }

    public static boolean olderThanOrEqualsTo1_13_2() {
        return ProtocolBase.getManager().getTargetVersion().olderThanOrEqualTo(ProtocolVersion.v1_13_2) && !MinecraftInstance.mc.func_71387_A();
    }

    public static boolean newerThanOrEqualsTo1_14() {
        return ProtocolBase.getManager().getTargetVersion().newerThanOrEqualTo(ProtocolVersion.v1_14) && !MinecraftInstance.mc.func_71387_A();
    }

    public static boolean newerThanOrEqualsTo1_16() {
        return ProtocolBase.getManager().getTargetVersion().newerThanOrEqualTo(ProtocolVersion.v1_14) && !MinecraftInstance.mc.func_71387_A();
    }

    public static boolean newerThanOrEqualsTo1_17() {
        return ProtocolBase.getManager().getTargetVersion().newerThanOrEqualTo(ProtocolVersion.v1_17) && !MinecraftInstance.mc.func_71387_A();
    }
}

