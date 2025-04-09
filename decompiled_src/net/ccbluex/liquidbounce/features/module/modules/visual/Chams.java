/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.visual;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(name="Chams", category=ModuleCategory.VISUAL)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0007R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0007R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0007R\u0011\u0010\u0010\u001a\u00020\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\t0\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0007R\u0017\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0007R\u0011\u0010\u0018\u001a\u00020\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0013R\u0011\u0010\u001a\u001a\u00020\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0013R\u0011\u0010\u001c\u001a\u00020\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0013R\u0017\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0007R\u0017\u0010 \u001a\b\u0012\u0004\u0012\u00020\u000e0\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0007R\u0011\u0010\"\u001a\u00020\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0013R\u0017\u0010$\u001a\b\u0012\u0004\u0012\u00020%0\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\u0007\u00a8\u0006'"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/visual/Chams;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "alphaValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "getAlphaValue", "()Lnet/ccbluex/liquidbounce/features/value/Value;", "behindColorModeValue", "", "getBehindColorModeValue", "blueValue", "getBlueValue", "brightnessValue", "", "getBrightnessValue", "chestsValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "getChestsValue", "()Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "colorModeValue", "getColorModeValue", "greenValue", "getGreenValue", "itemsValue", "getItemsValue", "legacyMode", "getLegacyMode", "localPlayerValue", "getLocalPlayerValue", "redValue", "getRedValue", "saturationValue", "getSaturationValue", "targetsValue", "getTargetsValue", "texturedValue", "", "getTexturedValue", "CrossSine"})
public final class Chams
extends Module {
    @NotNull
    private final BoolValue targetsValue = new BoolValue("Targets", true);
    @NotNull
    private final BoolValue chestsValue = new BoolValue("Chests", true);
    @NotNull
    private final BoolValue itemsValue = new BoolValue("Items", true);
    @NotNull
    private final BoolValue localPlayerValue = new BoolValue("LocalPlayer", true);
    @NotNull
    private final BoolValue legacyMode = new BoolValue("Legacy-Mode", false);
    @NotNull
    private final Value<Boolean> texturedValue = new BoolValue("Textured", false).displayable(new Function0<Boolean>(this){
        final /* synthetic */ Chams this$0;
        {
            this.this$0 = $receiver;
            super(0);
        }

        @NotNull
        public final Boolean invoke() {
            return (Boolean)this.this$0.getLegacyMode().get() == false;
        }
    });
    @NotNull
    private final Value<String> colorModeValue;
    @NotNull
    private final Value<String> behindColorModeValue;
    @NotNull
    private final Value<Integer> redValue;
    @NotNull
    private final Value<Integer> greenValue;
    @NotNull
    private final Value<Integer> blueValue;
    @NotNull
    private final Value<Integer> alphaValue;
    @NotNull
    private final Value<Float> saturationValue;
    @NotNull
    private final Value<Float> brightnessValue;

    public Chams() {
        String[] stringArray = new String[]{"Custom", "Slowly", "Fade"};
        this.colorModeValue = new ListValue("Color", stringArray, "Custom").displayable(new Function0<Boolean>(this){
            final /* synthetic */ Chams this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return (Boolean)this.this$0.getLegacyMode().get() == false;
            }
        });
        stringArray = new String[]{"Same", "Opposite", "Red"};
        this.behindColorModeValue = new ListValue("Behind-Color", stringArray, "Red").displayable(new Function0<Boolean>(this){
            final /* synthetic */ Chams this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return (Boolean)this.this$0.getLegacyMode().get() == false;
            }
        });
        this.redValue = new IntegerValue("Red", 0, 0, 255).displayable(new Function0<Boolean>(this){
            final /* synthetic */ Chams this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return (Boolean)this.this$0.getLegacyMode().get() == false;
            }
        });
        this.greenValue = new IntegerValue("Green", 200, 0, 255).displayable(new Function0<Boolean>(this){
            final /* synthetic */ Chams this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return (Boolean)this.this$0.getLegacyMode().get() == false;
            }
        });
        this.blueValue = new IntegerValue("Blue", 0, 0, 255).displayable(new Function0<Boolean>(this){
            final /* synthetic */ Chams this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return (Boolean)this.this$0.getLegacyMode().get() == false;
            }
        });
        this.alphaValue = new IntegerValue("Alpha", 255, 0, 255).displayable(new Function0<Boolean>(this){
            final /* synthetic */ Chams this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return (Boolean)this.this$0.getLegacyMode().get() == false;
            }
        });
        this.saturationValue = new FloatValue("Saturation", 1.0f, 0.0f, 1.0f).displayable(new Function0<Boolean>(this){
            final /* synthetic */ Chams this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return (Boolean)this.this$0.getLegacyMode().get() == false;
            }
        });
        this.brightnessValue = new FloatValue("Brightness", 1.0f, 0.0f, 1.0f).displayable(new Function0<Boolean>(this){
            final /* synthetic */ Chams this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return (Boolean)this.this$0.getLegacyMode().get() == false;
            }
        });
    }

    @NotNull
    public final BoolValue getTargetsValue() {
        return this.targetsValue;
    }

    @NotNull
    public final BoolValue getChestsValue() {
        return this.chestsValue;
    }

    @NotNull
    public final BoolValue getItemsValue() {
        return this.itemsValue;
    }

    @NotNull
    public final BoolValue getLocalPlayerValue() {
        return this.localPlayerValue;
    }

    @NotNull
    public final BoolValue getLegacyMode() {
        return this.legacyMode;
    }

    @NotNull
    public final Value<Boolean> getTexturedValue() {
        return this.texturedValue;
    }

    @NotNull
    public final Value<String> getColorModeValue() {
        return this.colorModeValue;
    }

    @NotNull
    public final Value<String> getBehindColorModeValue() {
        return this.behindColorModeValue;
    }

    @NotNull
    public final Value<Integer> getRedValue() {
        return this.redValue;
    }

    @NotNull
    public final Value<Integer> getGreenValue() {
        return this.greenValue;
    }

    @NotNull
    public final Value<Integer> getBlueValue() {
        return this.blueValue;
    }

    @NotNull
    public final Value<Integer> getAlphaValue() {
        return this.alphaValue;
    }

    @NotNull
    public final Value<Float> getSaturationValue() {
        return this.saturationValue;
    }

    @NotNull
    public final Value<Float> getBrightnessValue() {
        return this.brightnessValue;
    }
}

