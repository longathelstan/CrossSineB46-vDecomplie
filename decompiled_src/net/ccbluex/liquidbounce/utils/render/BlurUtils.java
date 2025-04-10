/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.utils.render;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.features.module.modules.client.Interface;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.Stencil;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.Shader;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0011\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002JF\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\n2\u0006\u0010\u0017\u001a\u00020\n2\u0006\u0010\u0018\u001a\u00020\n2\u0006\u0010\u0019\u001a\u00020\n2\u0006\u0010\u001a\u001a\u00020\n2\u0006\u0010\u001b\u001a\u00020\u001c2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00150\u001eH\u0007J8\u0010\u001f\u001a\u00020\u00152\u0006\u0010 \u001a\u00020\n2\u0006\u0010!\u001a\u00020\n2\u0006\u0010\"\u001a\u00020\n2\u0006\u0010#\u001a\u00020\n2\u0006\u0010$\u001a\u00020\n2\u0006\u0010\u001a\u001a\u00020\nH\u0007JJ\u0010%\u001a\u00020\u00152\u0006\u0010&\u001a\u00020\n2\u0006\u0010 \u001a\u00020\n2\u0006\u0010!\u001a\u00020\n2\u0006\u0010'\u001a\u00020\n2\u0006\u0010(\u001a\u00020\n2\u0006\u0010)\u001a\u00020\n2\u0006\u0010*\u001a\u00020\n2\b\b\u0002\u0010+\u001a\u00020\u001cH\u0002J\b\u0010,\u001a\u00020\u0015H\u0002J\u001e\u0010-\u001a\u00020\u001c2\u0006\u0010.\u001a\u00020\b2\u0006\u0010)\u001a\u00020\b2\u0006\u0010*\u001a\u00020\bR\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0006\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006/"}, d2={"Lnet/ccbluex/liquidbounce/utils/render/BlurUtils;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "framebuffer", "Lnet/minecraft/client/shader/Framebuffer;", "kotlin.jvm.PlatformType", "frbuffer", "lastFactor", "", "lastH", "", "lastHeight", "lastStrength", "lastW", "lastWeight", "lastWidth", "lastX", "lastY", "shaderGroup", "Lnet/minecraft/client/shader/ShaderGroup;", "blur", "", "posX", "posY", "posXEnd", "posYEnd", "blurStrength", "displayClipMask", "", "triggerMethod", "Lkotlin/Function0;", "blurAreaRounded", "x", "y", "x2", "y2", "rad", "setValues", "strength", "w", "h", "width", "height", "force", "setupFramebuffers", "sizeHasChanged", "scaleFactor", "CrossSine"})
public final class BlurUtils
extends MinecraftInstance {
    @NotNull
    public static final BlurUtils INSTANCE = new BlurUtils();
    @NotNull
    private static final ShaderGroup shaderGroup = new ShaderGroup(MinecraftInstance.mc.func_110434_K(), MinecraftInstance.mc.func_110442_L(), MinecraftInstance.mc.func_147110_a(), new ResourceLocation("shaders/post/blurArea.json"));
    private static final Framebuffer framebuffer = BlurUtils.shaderGroup.field_148035_a;
    private static final Framebuffer frbuffer = shaderGroup.func_177066_a("result");
    private static int lastFactor;
    private static int lastWidth;
    private static int lastHeight;
    private static int lastWeight;
    private static float lastX;
    private static float lastY;
    private static float lastW;
    private static float lastH;
    private static float lastStrength;

    private BlurUtils() {
    }

    private final void setupFramebuffers() {
        try {
            shaderGroup.func_148026_a(MinecraftInstance.mc.field_71443_c, MinecraftInstance.mc.field_71440_d);
        }
        catch (Exception e) {
            ClientUtils.INSTANCE.logError("Exception caught while setting up shader group", e);
        }
    }

    private final void setValues(float strength, float x, float y, float w, float h, float width, float height, boolean force) {
        if (!force && strength == lastStrength && lastX == x && lastY == y && lastW == w && lastH == h) {
            return;
        }
        lastStrength = strength;
        lastX = x;
        lastY = y;
        lastW = w;
        lastH = h;
        int n = 0;
        while (n < 2) {
            int i = n++;
            ((Shader)BlurUtils.shaderGroup.field_148031_d.get(i)).func_148043_c().func_147991_a("Radius").func_148090_a(strength);
            ((Shader)BlurUtils.shaderGroup.field_148031_d.get(i)).func_148043_c().func_147991_a("BlurXY").func_148087_a(x, height - y - h);
            ((Shader)BlurUtils.shaderGroup.field_148031_d.get(i)).func_148043_c().func_147991_a("BlurCoord").func_148087_a(w, h);
        }
    }

    static /* synthetic */ void setValues$default(BlurUtils blurUtils, float f, float f2, float f3, float f4, float f5, float f6, float f7, boolean bl, int n, Object object) {
        if ((n & 0x80) != 0) {
            bl = false;
        }
        blurUtils.setValues(f, f2, f3, f4, f5, f6, f7, bl);
    }

    @JvmStatic
    public static final void blur(float posX, float posY, float posXEnd, float posYEnd, float blurStrength, boolean displayClipMask, @NotNull Function0<Unit> triggerMethod) {
        int height;
        int width;
        ScaledResolution sc;
        int scaleFactor;
        float z;
        Intrinsics.checkNotNullParameter(triggerMethod, "triggerMethod");
        if (!OpenGlHelper.func_148822_b()) {
            return;
        }
        if (!((Boolean)Interface.INSTANCE.getShaderValue().get()).booleanValue() || !Interface.INSTANCE.getBlurValue().get().booleanValue()) {
            return;
        }
        float x = posX;
        float y = posY;
        float x2 = posXEnd;
        float y2 = posYEnd;
        if (x > x2) {
            z = x;
            x = x2;
            x2 = z;
        }
        if (y > y2) {
            z = y;
            y2 = y = y2;
        }
        if (INSTANCE.sizeHasChanged(scaleFactor = (sc = new ScaledResolution(MinecraftInstance.mc)).func_78325_e(), width = sc.func_78326_a(), height = sc.func_78328_b())) {
            INSTANCE.setupFramebuffers();
            INSTANCE.setValues(blurStrength, x, y, x2 - x, y2 - y, width, height, true);
        }
        lastFactor = scaleFactor;
        lastWidth = width;
        lastHeight = height;
        BlurUtils.setValues$default(INSTANCE, blurStrength, x, y, x2 - x, y2 - y, width, height, false, 128, null);
        framebuffer.func_147610_a(true);
        shaderGroup.func_148018_a(MinecraftInstance.mc.field_71428_T.field_74281_c);
        MinecraftInstance.mc.func_147110_a().func_147610_a(true);
        Stencil.write(displayClipMask);
        triggerMethod.invoke();
        Stencil.erase(true);
        GlStateManager.func_179147_l();
        GlStateManager.func_179112_b((int)770, (int)771);
        GlStateManager.func_179094_E();
        GlStateManager.func_179135_a((boolean)true, (boolean)true, (boolean)true, (boolean)false);
        GlStateManager.func_179097_i();
        GlStateManager.func_179132_a((boolean)false);
        GlStateManager.func_179098_w();
        GlStateManager.func_179140_f();
        GlStateManager.func_179118_c();
        frbuffer.func_147612_c();
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        double f2 = (double)BlurUtils.frbuffer.field_147621_c / (double)BlurUtils.frbuffer.field_147622_a;
        double f3 = (double)BlurUtils.frbuffer.field_147618_d / (double)BlurUtils.frbuffer.field_147620_b;
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldrenderer = tessellator.func_178180_c();
        worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181709_i);
        worldrenderer.func_181662_b(0.0, (double)height, 0.0).func_181673_a(0.0, 0.0).func_181669_b(255, 255, 255, 255).func_181675_d();
        worldrenderer.func_181662_b((double)width, (double)height, 0.0).func_181673_a(f2, 0.0).func_181669_b(255, 255, 255, 255).func_181675_d();
        worldrenderer.func_181662_b((double)width, 0.0, 0.0).func_181673_a(f2, f3).func_181669_b(255, 255, 255, 255).func_181675_d();
        worldrenderer.func_181662_b(0.0, 0.0, 0.0).func_181673_a(0.0, f3).func_181669_b(255, 255, 255, 255).func_181675_d();
        tessellator.func_78381_a();
        frbuffer.func_147606_d();
        GlStateManager.func_179126_j();
        GlStateManager.func_179132_a((boolean)true);
        GlStateManager.func_179135_a((boolean)true, (boolean)true, (boolean)true, (boolean)true);
        GlStateManager.func_179121_F();
        GlStateManager.func_179084_k();
        Stencil.dispose();
        GlStateManager.func_179141_d();
    }

    @JvmStatic
    public static final void blurAreaRounded(float x, float y, float x2, float y2, float rad, float blurStrength) {
        if (!((Boolean)Interface.INSTANCE.getShaderValue().get()).booleanValue() || !Interface.INSTANCE.getBlurValue().get().booleanValue()) {
            return;
        }
        BlurUtils.blur(x, y, x2, y2, blurStrength, false, new Function0<Unit>(x, y, x2, y2, rad){
            final /* synthetic */ float $x;
            final /* synthetic */ float $y;
            final /* synthetic */ float $x2;
            final /* synthetic */ float $y2;
            final /* synthetic */ float $rad;
            {
                this.$x = $x;
                this.$y = $y;
                this.$x2 = $x2;
                this.$y2 = $y2;
                this.$rad = $rad;
                super(0);
            }

            public final void invoke() {
                GlStateManager.func_179147_l();
                GlStateManager.func_179090_x();
                GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
                RenderUtils.fastRoundedRect(this.$x, this.$y, this.$x2, this.$y2, this.$rad);
                GlStateManager.func_179098_w();
                GlStateManager.func_179084_k();
            }
        });
    }

    public final boolean sizeHasChanged(int scaleFactor, int width, int height) {
        return lastFactor != scaleFactor || lastWidth != width || lastHeight != height;
    }

    static {
        lastStrength = 5.0f;
    }
}

