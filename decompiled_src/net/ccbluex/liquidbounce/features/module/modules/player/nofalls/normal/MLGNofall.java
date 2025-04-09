/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.player.nofalls.normal;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.player.nofalls.NoFallMode;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.SlotUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\f\u001a\u00020\rH\u0002J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\u0010\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/player/nofalls/normal/MLGNofall;", "Lnet/ccbluex/liquidbounce/features/module/modules/player/nofalls/NoFallMode;", "()V", "fallDistance", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "placed", "", "rotation", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "takeBack", "turnSpeed", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "findBuket", "", "onDisable", "", "onUpdate", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class MLGNofall
extends NoFallMode {
    @NotNull
    private final BoolValue rotation = new BoolValue("SilentRotation", false);
    @NotNull
    private final IntegerValue turnSpeed = new IntegerValue("TurnSpeed", 90, 1, 90);
    @NotNull
    private final FloatValue fallDistance = new FloatValue("FallDistance", 3.0f, 3.0f, 8.0f);
    private boolean placed;
    private boolean takeBack;

    public MLGNofall() {
        super("MLG");
    }

    @Override
    public void onDisable() {
        this.placed = false;
        this.takeBack = false;
    }

    @Override
    public void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (!this.takeBack) {
            if (this.findBuket() != -1) {
                ClientUtils.INSTANCE.displayAlert("Failed to find WaterBuket");
                return;
            }
            SlotUtils.INSTANCE.setSlot(this.findBuket(), true, this.getNofall().getName());
            if (MinecraftInstance.mc.field_71439_g.field_70143_R >= ((Number)this.fallDistance.get()).floatValue()) {
                Rotation rotation = RotationUtils.limitAngleChange(RotationUtils.serverRotation, new Rotation(MovementUtils.INSTANCE.getMovingYaw(), 90.0f), ((Number)this.turnSpeed.get()).intValue());
                Intrinsics.checkNotNullExpressionValue(rotation, "limitAngleChange(Rotatio\u2026urnSpeed.get().toFloat())");
                Rotation limitAngle = rotation;
                if (((Boolean)this.rotation.get()).booleanValue()) {
                    RotationUtils.setTargetRotation(limitAngle, 100);
                } else {
                    rotation = MinecraftInstance.mc.field_71439_g;
                    Intrinsics.checkNotNullExpressionValue(rotation, "mc.thePlayer");
                    limitAngle.toPlayer((EntityPlayer)rotation);
                }
                if (MinecraftInstance.mc.field_71476_x.field_72313_a == MovingObjectPosition.MovingObjectType.BLOCK) {
                    if (!this.placed) {
                        MinecraftInstance.mc.func_147121_ag();
                        this.placed = true;
                    }
                    if (MinecraftInstance.mc.field_71439_g.func_70090_H()) {
                        MinecraftInstance.mc.func_147121_ag();
                        this.takeBack = true;
                        SlotUtils.INSTANCE.stopSet();
                    }
                }
            }
        }
    }

    private final int findBuket() {
        int slot = -1;
        int n = 36;
        while (n < 45) {
            int i;
            ItemStack itemStack;
            if ((itemStack = MinecraftInstance.mc.field_71439_g.field_71069_bz.func_75139_a(i = n++).func_75211_c()) == null) continue;
            if (!Intrinsics.areEqual(itemStack.func_77973_b(), Items.field_151131_as)) {
                if (!(itemStack.func_77973_b() instanceof ItemBlock)) continue;
                Item item = itemStack.func_77973_b();
                if (item == null) {
                    throw new NullPointerException("null cannot be cast to non-null type net.minecraft.item.ItemBlock");
                }
                if (!Intrinsics.areEqual(((ItemBlock)item).field_150939_a, Blocks.field_150321_G)) continue;
            }
            slot = i - 36;
        }
        return slot;
    }
}

