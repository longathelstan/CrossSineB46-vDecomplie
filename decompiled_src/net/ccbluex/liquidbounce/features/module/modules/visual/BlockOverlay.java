/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.visual;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.world.BedAura;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.ui.client.gui.colortheme.ClientTheme;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.animation.Animation;
import net.ccbluex.liquidbounce.utils.animation.Easing;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(name="BlockOverlay", category=ModuleCategory.VISUAL)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 H\u0007R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0012\u001a\u0004\u0018\u00010\u00138BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0016\u001a\u00020\u00178BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u000e\u0010\u001a\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006!"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/visual/BlockOverlay;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "animation", "Lnet/ccbluex/liquidbounce/utils/animation/Animation;", "clientTheme", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "colorBlockAlphaValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "colorBlockAlphaValue2", "colorBlockBlueValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "colorBlockBlueValue2", "colorBlockGreenValue", "colorBlockGreenValue2", "colorBlockRedValue", "colorBlockRedValue2", "currentBlock", "Lnet/minecraft/util/BlockPos;", "getCurrentBlock", "()Lnet/minecraft/util/BlockPos;", "currentDamage", "", "getCurrentDamage", "()D", "outlineValue", "widthValue", "", "onRender3D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "CrossSine"})
public final class BlockOverlay
extends Module {
    @NotNull
    private final BoolValue clientTheme = new BoolValue("ClientTheme", false);
    @NotNull
    private final BoolValue outlineValue = new BoolValue("Outline", false);
    @NotNull
    private final Value<Float> widthValue = new FloatValue("LineWidth", 2.0f, 0.0f, 10.0f).displayable(new Function0<Boolean>(this){
        final /* synthetic */ BlockOverlay this$0;
        {
            this.this$0 = $receiver;
            super(0);
        }

        @NotNull
        public final Boolean invoke() {
            return (Boolean)BlockOverlay.access$getOutlineValue$p(this.this$0).get();
        }
    });
    @NotNull
    private final Value<Integer> colorBlockRedValue = new IntegerValue("Block-Red", 255, 0, 255).displayable(new Function0<Boolean>(this){
        final /* synthetic */ BlockOverlay this$0;
        {
            this.this$0 = $receiver;
            super(0);
        }

        @NotNull
        public final Boolean invoke() {
            return (Boolean)BlockOverlay.access$getClientTheme$p(this.this$0).get() == false;
        }
    });
    @NotNull
    private final Value<Integer> colorBlockGreenValue = new IntegerValue("Block-Green", 255, 0, 255).displayable(new Function0<Boolean>(this){
        final /* synthetic */ BlockOverlay this$0;
        {
            this.this$0 = $receiver;
            super(0);
        }

        @NotNull
        public final Boolean invoke() {
            return (Boolean)BlockOverlay.access$getClientTheme$p(this.this$0).get() == false;
        }
    });
    @NotNull
    private final Value<Integer> colorBlockBlueValue = new IntegerValue("Block-Blue", 255, 0, 255).displayable(new Function0<Boolean>(this){
        final /* synthetic */ BlockOverlay this$0;
        {
            this.this$0 = $receiver;
            super(0);
        }

        @NotNull
        public final Boolean invoke() {
            return (Boolean)BlockOverlay.access$getClientTheme$p(this.this$0).get() == false;
        }
    });
    @NotNull
    private final IntegerValue colorBlockAlphaValue = new IntegerValue("Block-Alpha", 255, 0, 255);
    @NotNull
    private final Value<Integer> colorBlockRedValue2 = new IntegerValue("Block-Red2", 255, 0, 255).displayable(new Function0<Boolean>(this){
        final /* synthetic */ BlockOverlay this$0;
        {
            this.this$0 = $receiver;
            super(0);
        }

        @NotNull
        public final Boolean invoke() {
            return (Boolean)BlockOverlay.access$getClientTheme$p(this.this$0).get() == false;
        }
    });
    @NotNull
    private final Value<Integer> colorBlockGreenValue2 = new IntegerValue("Block-Green2", 255, 0, 255).displayable(new Function0<Boolean>(this){
        final /* synthetic */ BlockOverlay this$0;
        {
            this.this$0 = $receiver;
            super(0);
        }

        @NotNull
        public final Boolean invoke() {
            return (Boolean)BlockOverlay.access$getClientTheme$p(this.this$0).get() == false;
        }
    });
    @NotNull
    private final Value<Integer> colorBlockBlueValue2 = new IntegerValue("Block-Blue2", 255, 0, 255).displayable(new Function0<Boolean>(this){
        final /* synthetic */ BlockOverlay this$0;
        {
            this.this$0 = $receiver;
            super(0);
        }

        @NotNull
        public final Boolean invoke() {
            return (Boolean)BlockOverlay.access$getClientTheme$p(this.this$0).get() == false;
        }
    });
    @NotNull
    private final IntegerValue colorBlockAlphaValue2 = new IntegerValue("Block-Alpha2", 255, 0, 255);
    @Nullable
    private Animation animation;

    private final BlockPos getCurrentBlock() {
        MovingObjectPosition movingObjectPosition = MinecraftInstance.mc.field_71476_x;
        Object object = movingObjectPosition == null ? null : movingObjectPosition.func_178782_a();
        if (object == null) {
            return null;
        }
        BlockPos blockPos = object;
        if (BedAura.INSTANCE.getState() && BedAura.INSTANCE.getPos() != null) {
            return BedAura.INSTANCE.getPos();
        }
        if (BlockUtils.canBeClicked(blockPos) && MinecraftInstance.mc.field_71441_e.func_175723_af().func_177746_a(blockPos)) {
            return blockPos;
        }
        return null;
    }

    private final double getCurrentDamage() {
        if (MinecraftInstance.mc.field_71442_b.field_78770_f == 0.0f) {
            return 1.0;
        }
        return MinecraftInstance.mc.field_71442_b.field_78770_f;
    }

    @EventTarget
    public final void onRender3D(@NotNull Render3DEvent event) {
        Color color2;
        Intrinsics.checkNotNullParameter(event, "event");
        BlockPos blockPos = this.getCurrentBlock();
        if (blockPos == null) {
            return;
        }
        BlockPos blockPos2 = blockPos;
        Color color = (Boolean)this.clientTheme.get() != false ? ClientTheme.getColorWithAlpha$default(ClientTheme.INSTANCE, 0, ((Number)this.colorBlockAlphaValue.get()).intValue(), false, 4, null) : new Color(((Number)this.colorBlockRedValue.get()).intValue(), ((Number)this.colorBlockGreenValue.get()).intValue(), ((Number)this.colorBlockBlueValue.get()).intValue(), ((Number)this.colorBlockAlphaValue.get()).intValue());
        Color color3 = color2 = (Boolean)this.clientTheme.get() != false ? ClientTheme.getColorWithAlpha$default(ClientTheme.INSTANCE, 180, ((Number)this.colorBlockAlphaValue.get()).intValue(), false, 4, null) : new Color(((Number)this.colorBlockRedValue2.get()).intValue(), ((Number)this.colorBlockGreenValue2.get()).intValue(), ((Number)this.colorBlockBlueValue2.get()).intValue(), ((Number)this.colorBlockAlphaValue2.get()).intValue());
        if (this.animation == null) {
            this.animation = new Animation(Easing.LINEAR, 40L);
            Intrinsics.checkNotNull(this.animation);
            this.animation.value = this.getCurrentDamage();
        }
        Animation animation = this.animation;
        Intrinsics.checkNotNull(animation);
        animation.run(this.getCurrentDamage());
        GlStateManager.func_179094_E();
        boolean bl = (Boolean)this.outlineValue.get();
        boolean bl2 = (Boolean)this.outlineValue.get() == false;
        float f = ((Number)this.widthValue.get()).floatValue();
        Animation animation2 = this.animation;
        Intrinsics.checkNotNull(animation2);
        RenderUtils.drawBlockBoxGradient(blockPos2, color, color2, bl, bl2, f, (float)animation2.value);
        GlStateManager.func_179121_F();
        GlStateManager.func_179117_G();
    }

    public static final /* synthetic */ BoolValue access$getOutlineValue$p(BlockOverlay $this) {
        return $this.outlineValue;
    }

    public static final /* synthetic */ BoolValue access$getClientTheme$p(BlockOverlay $this) {
        return $this.clientTheme;
    }
}

