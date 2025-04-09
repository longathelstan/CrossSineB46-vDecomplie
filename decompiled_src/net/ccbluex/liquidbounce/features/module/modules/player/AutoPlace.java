/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.player;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MouseUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.input.Mouse;

@ModuleInfo(name="AutoPlace", category=ModuleCategory.PLAYER)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001c"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/player/AutoPlace;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "dl", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "down", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "f", "", "fakeMouseDown", "l", "", "lm", "Lnet/minecraft/util/MovingObjectPosition;", "lp", "Lnet/minecraft/util/BlockPos;", "md", "nofly", "pitchLimit", "pitchMax", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "pitchMin", "side", "up", "onRender3D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "CrossSine"})
public final class AutoPlace
extends Module {
    @NotNull
    private final FloatValue dl = new FloatValue("Delay", 0.0f, 0.0f, 10.0f);
    @NotNull
    private final BoolValue md = new BoolValue("MouseDown", false);
    @NotNull
    private final BoolValue fakeMouseDown = new BoolValue("Fake-Mouse-Down", false);
    @NotNull
    private final BoolValue up = new BoolValue("Up", false);
    @NotNull
    private final BoolValue down = new BoolValue("Down", false);
    @NotNull
    private final BoolValue side = new BoolValue("Side", true);
    @NotNull
    private final BoolValue nofly = new BoolValue("NoFly", false);
    @NotNull
    private final BoolValue pitchLimit = new BoolValue("Pitch", false);
    @NotNull
    private final IntegerValue pitchMax = (IntegerValue)new IntegerValue(this){
        final /* synthetic */ AutoPlace this$0;
        {
            this.this$0 = $receiver;
            super("Pitch-MAX", 90, 0, 90);
        }

        protected void onChanged(int oldValue, int newValue) {
            if (newValue < ((Number)AutoPlace.access$getPitchMin$p(this.this$0).get()).intValue()) {
                this.set(AutoPlace.access$getPitchMin$p(this.this$0).get());
            }
        }
    }.displayable(new Function0<Boolean>(this){
        final /* synthetic */ AutoPlace this$0;
        {
            this.this$0 = $receiver;
            super(0);
        }

        @NotNull
        public final Boolean invoke() {
            return (Boolean)AutoPlace.access$getPitchLimit$p(this.this$0).get();
        }
    });
    @NotNull
    private final IntegerValue pitchMin = (IntegerValue)new IntegerValue(this){
        final /* synthetic */ AutoPlace this$0;
        {
            this.this$0 = $receiver;
            super("Pitch-MIN", 75, 0, 90);
        }

        protected void onChanged(int oldValue, int newValue) {
            if (newValue > ((Number)AutoPlace.access$getPitchMax$p(this.this$0).get()).intValue()) {
                this.set(AutoPlace.access$getPitchMax$p(this.this$0).get());
            }
        }
    }.displayable(new Function0<Boolean>(this){
        final /* synthetic */ AutoPlace this$0;
        {
            this.this$0 = $receiver;
            super(0);
        }

        @NotNull
        public final Boolean invoke() {
            return (Boolean)AutoPlace.access$getPitchLimit$p(this.this$0).get();
        }
    });
    private long l;
    private int f;
    @Nullable
    private MovingObjectPosition lm;
    @Nullable
    private BlockPos lp;

    @EventTarget
    public final void onRender3D(@Nullable Render3DEvent event) {
        block6: {
            long n;
            Block b;
            BlockPos pos;
            MovingObjectPosition m2;
            ItemStack i;
            block9: {
                block8: {
                    block7: {
                        if (((Boolean)this.fakeMouseDown.get()).booleanValue() && !Mouse.isButtonDown((int)1)) {
                            boolean bl = MinecraftInstance.mc.field_71474_y.field_74313_G.field_74513_e = MinecraftInstance.mc.field_71476_x != null && MinecraftInstance.mc.field_71476_x.field_72313_a == MovingObjectPosition.MovingObjectType.BLOCK && ((Boolean)this.up.get() != false || MinecraftInstance.mc.field_71476_x.field_178784_b != EnumFacing.UP) && ((Boolean)this.down.get() != false || MinecraftInstance.mc.field_71476_x.field_178784_b != EnumFacing.DOWN) || (Boolean)this.side.get() == false || MinecraftInstance.mc.field_71476_x.field_178784_b == EnumFacing.NORTH || MinecraftInstance.mc.field_71476_x.field_178784_b == EnumFacing.EAST || MinecraftInstance.mc.field_71476_x.field_178784_b == EnumFacing.SOUTH || MinecraftInstance.mc.field_71476_x.field_178784_b == EnumFacing.WEST;
                        }
                        if (MinecraftInstance.mc.field_71462_r != null || ((Boolean)this.nofly.get()).booleanValue() && MinecraftInstance.mc.field_71439_g.field_71075_bZ.field_75100_b || ((Boolean)this.pitchLimit.get()).booleanValue() && (!(MinecraftInstance.mc.field_71439_g.field_70125_A < (float)((Number)this.pitchMax.get()).intValue()) || !(MinecraftInstance.mc.field_71439_g.field_70125_A > (float)((Number)this.pitchMin.get()).intValue())) || (i = MinecraftInstance.mc.field_71439_g.func_70694_bm()) == null || !(i.func_77973_b() instanceof ItemBlock)) break block6;
                        m2 = MinecraftInstance.mc.field_71476_x;
                        if (m2 != null && m2.field_72313_a == MovingObjectPosition.MovingObjectType.BLOCK && (((Boolean)this.up.get()).booleanValue() || m2.field_178784_b != EnumFacing.UP) && (((Boolean)this.down.get()).booleanValue() || m2.field_178784_b != EnumFacing.DOWN) || !((Boolean)this.side.get()).booleanValue()) break block7;
                        MovingObjectPosition movingObjectPosition = m2;
                        Intrinsics.checkNotNull(movingObjectPosition);
                        if (movingObjectPosition.field_178784_b != EnumFacing.NORTH && m2.field_178784_b != EnumFacing.EAST && m2.field_178784_b != EnumFacing.SOUTH && m2.field_178784_b != EnumFacing.WEST) break block6;
                    }
                    if (this.lm == null || !((double)this.f < (double)((Number)this.dl.get()).floatValue())) break block8;
                    ++this.f;
                    break block6;
                }
                this.lm = m2;
                pos = m2.func_178782_a();
                if (this.lp == null) break block9;
                int n2 = pos.func_177958_n();
                BlockPos blockPos = this.lp;
                Intrinsics.checkNotNull(blockPos);
                if (n2 != blockPos.func_177958_n()) break block9;
                int n3 = pos.func_177956_o();
                BlockPos blockPos2 = this.lp;
                Intrinsics.checkNotNull(blockPos2);
                if (n3 != blockPos2.func_177956_o()) break block9;
                int n4 = pos.func_177952_p();
                BlockPos blockPos3 = this.lp;
                Intrinsics.checkNotNull(blockPos3);
                if (n4 == blockPos3.func_177952_p()) break block6;
            }
            if (!((b = MinecraftInstance.mc.field_71441_e.func_180495_p(pos).func_177230_c()) == null || b == Blocks.field_150350_a || b instanceof BlockLiquid || ((Boolean)this.md.get()).booleanValue() && !MinecraftInstance.mc.field_71474_y.field_74313_G.func_151470_d() || (n = System.currentTimeMillis()) - this.l < 25L)) {
                this.l = n;
                if (MinecraftInstance.mc.field_71442_b.func_178890_a(MinecraftInstance.mc.field_71439_g, MinecraftInstance.mc.field_71441_e, i, pos, m2.field_178784_b, m2.field_72307_f)) {
                    MouseUtils.INSTANCE.setMouseButtonState(1, true);
                    MinecraftInstance.mc.field_71439_g.func_71038_i();
                    MinecraftInstance.mc.func_175597_ag().func_78444_b();
                    MouseUtils.INSTANCE.setMouseButtonState(1, false);
                    this.lp = pos;
                    this.f = 0;
                }
            }
        }
    }

    public static final /* synthetic */ IntegerValue access$getPitchMin$p(AutoPlace $this) {
        return $this.pitchMin;
    }

    public static final /* synthetic */ BoolValue access$getPitchLimit$p(AutoPlace $this) {
        return $this.pitchLimit;
    }

    public static final /* synthetic */ IntegerValue access$getPitchMax$p(AutoPlace $this) {
        return $this.pitchMax;
    }
}

