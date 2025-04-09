/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.visual;

import java.awt.Color;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.ui.client.gui.colortheme.ClientTheme;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(name="HurtCam", category=ModuleCategory.VISUAL, canEnable=false)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0005H\u0002J\b\u0010\u0015\u001a\u00020\tH\u0016J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0007J\u0010\u0010\u001a\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u001bH\u0007R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0011\u0010\r\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001c"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/visual/HurtCam;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "colorBlueValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "colorGreenValue", "colorRedValue", "colorThmeme", "", "fpsHeightValue", "hurt", "", "modeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "getModeValue", "()Lnet/ccbluex/liquidbounce/features/value/ListValue;", "timeValue", "getColor", "Ljava/awt/Color;", "alpha", "handleEvents", "onPacket", "", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onRender2d", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "CrossSine"})
public final class HurtCam
extends Module {
    @NotNull
    private final ListValue modeValue;
    @NotNull
    private final Value<Integer> colorRedValue;
    @NotNull
    private final Value<Integer> colorGreenValue;
    @NotNull
    private final Value<Integer> colorBlueValue;
    @NotNull
    private final Value<Boolean> colorThmeme;
    @NotNull
    private final Value<Integer> timeValue;
    @NotNull
    private final Value<Integer> fpsHeightValue;
    private long hurt;

    public HurtCam() {
        String[] stringArray = new String[]{"Vanilla", "Cancel", "FPS"};
        this.modeValue = new ListValue("Mode", stringArray, "Vanilla");
        this.colorRedValue = new IntegerValue("R", 255, 0, 255).displayable(new Function0<Boolean>(this){
            final /* synthetic */ HurtCam this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return this.this$0.getModeValue().equals("FPS");
            }
        });
        this.colorGreenValue = new IntegerValue("G", 0, 0, 255).displayable(new Function0<Boolean>(this){
            final /* synthetic */ HurtCam this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return this.this$0.getModeValue().equals("FPS");
            }
        });
        this.colorBlueValue = new IntegerValue("B", 0, 0, 255).displayable(new Function0<Boolean>(this){
            final /* synthetic */ HurtCam this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return this.this$0.getModeValue().equals("FPS");
            }
        });
        this.colorThmeme = new BoolValue("ColorTheme", false).displayable(new Function0<Boolean>(this){
            final /* synthetic */ HurtCam this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return this.this$0.getModeValue().equals("FPS");
            }
        });
        this.timeValue = new IntegerValue("FPSTime", 1000, 0, 1500).displayable(new Function0<Boolean>(this){
            final /* synthetic */ HurtCam this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return this.this$0.getModeValue().equals("FPS");
            }
        });
        this.fpsHeightValue = new IntegerValue("FPSHeight", 25, 10, 50).displayable(new Function0<Boolean>(this){
            final /* synthetic */ HurtCam this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return this.this$0.getModeValue().equals("FPS");
            }
        });
    }

    @NotNull
    public final ListValue getModeValue() {
        return this.modeValue;
    }

    @EventTarget
    public final void onRender2d(@NotNull Render2DEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.hurt == 0L) {
            return;
        }
        long passedTime = System.currentTimeMillis() - this.hurt;
        if (passedTime > (long)((Number)this.timeValue.get()).intValue()) {
            this.hurt = 0L;
            return;
        }
        Color color = this.getColor((int)((float)(((Number)this.timeValue.get()).longValue() - passedTime) / (float)((Number)this.timeValue.get()).intValue() * (float)255));
        Color color1 = this.getColor(0);
        double width = event.getScaledResolution().func_78327_c();
        double height = event.getScaledResolution().func_78324_d();
        RenderUtils.drawGradientSidewaysV(0.0, 0.0, width, ((Number)this.fpsHeightValue.get()).intValue(), color.getRGB(), color1.getRGB());
        RenderUtils.drawGradientSidewaysV(0.0, height - ((Number)this.fpsHeightValue.get()).doubleValue(), width, height, color1.getRGB(), color.getRGB());
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Packet<?> packet = event.getPacket();
        String string = ((String)this.modeValue.get()).toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        if (Intrinsics.areEqual(string, "fps") && packet instanceof S19PacketEntityStatus && ((S19PacketEntityStatus)packet).func_149160_c() == 2 && MinecraftInstance.mc.field_71439_g.equals((Object)((S19PacketEntityStatus)packet).func_149161_a((World)MinecraftInstance.mc.field_71441_e))) {
            this.hurt = System.currentTimeMillis();
        }
    }

    private final Color getColor(int alpha) {
        return this.colorThmeme.get() != false ? ClientTheme.getColorWithAlpha$default(ClientTheme.INSTANCE, 1, alpha, false, 4, null) : new Color(((Number)this.colorRedValue.get()).intValue(), ((Number)this.colorGreenValue.get()).intValue(), ((Number)this.colorBlueValue.get()).intValue(), alpha);
    }

    @Override
    public boolean handleEvents() {
        return true;
    }
}

