/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.utils.render;

import java.io.IOException;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
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
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u000bJ*\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u000b2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00150\u001b2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00150\u001bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010\r\u001a\u0004\u0018\u00010\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001d"}, d2={"Lnet/ccbluex/liquidbounce/utils/render/ShadowUtils;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "blurDirectory", "Lnet/minecraft/util/ResourceLocation;", "frameBuffer", "Lnet/minecraft/client/shader/Framebuffer;", "initFramebuffer", "lastHeight", "", "lastStrength", "", "lastWidth", "resultBuffer", "getResultBuffer", "()Lnet/minecraft/client/shader/Framebuffer;", "setResultBuffer", "(Lnet/minecraft/client/shader/Framebuffer;)V", "shaderGroup", "Lnet/minecraft/client/shader/ShaderGroup;", "initShaderIfRequired", "", "sc", "Lnet/minecraft/client/gui/ScaledResolution;", "strength", "shadow", "drawMethod", "Lkotlin/Function0;", "cutMethod", "CrossSine"})
public final class ShadowUtils
extends MinecraftInstance {
    @NotNull
    public static final ShadowUtils INSTANCE = new ShadowUtils();
    @Nullable
    private static Framebuffer initFramebuffer;
    @Nullable
    private static Framebuffer frameBuffer;
    @Nullable
    private static Framebuffer resultBuffer;
    @Nullable
    private static ShaderGroup shaderGroup;
    private static int lastWidth;
    private static int lastHeight;
    private static float lastStrength;
    @NotNull
    private static final ResourceLocation blurDirectory;

    private ShadowUtils() {
    }

    @Nullable
    public final Framebuffer getResultBuffer() {
        return resultBuffer;
    }

    public final void setResultBuffer(@Nullable Framebuffer framebuffer) {
        resultBuffer = framebuffer;
    }

    public final void initShaderIfRequired(@NotNull ScaledResolution sc, float strength) throws IOException {
        int i;
        int n;
        Intrinsics.checkNotNullParameter(sc, "sc");
        int width = sc.func_78326_a();
        int height = sc.func_78328_b();
        int factor = sc.func_78325_e();
        if (lastWidth != width || lastHeight != height || initFramebuffer == null || frameBuffer == null || shaderGroup == null) {
            Framebuffer framebuffer = initFramebuffer = new Framebuffer(width * factor, height * factor, true);
            Intrinsics.checkNotNull(framebuffer);
            framebuffer.func_147604_a(0.0f, 0.0f, 0.0f, 0.0f);
            Framebuffer framebuffer2 = initFramebuffer;
            Intrinsics.checkNotNull(framebuffer2);
            framebuffer2.func_147607_a(9729);
            ShaderGroup shaderGroup = ShadowUtils.shaderGroup = new ShaderGroup(MinecraftInstance.mc.func_110434_K(), MinecraftInstance.mc.func_110442_L(), initFramebuffer, blurDirectory);
            Intrinsics.checkNotNull(shaderGroup);
            shaderGroup.func_148026_a(width * factor, height * factor);
            ShaderGroup shaderGroup2 = ShadowUtils.shaderGroup;
            Intrinsics.checkNotNull(shaderGroup2);
            frameBuffer = shaderGroup2.field_148035_a;
            ShaderGroup shaderGroup3 = ShadowUtils.shaderGroup;
            Intrinsics.checkNotNull(shaderGroup3);
            resultBuffer = shaderGroup3.func_177066_a("braindead");
            lastWidth = width;
            lastHeight = height;
            lastStrength = strength;
            n = 0;
            while (n < 2) {
                i = n++;
                ShaderGroup shaderGroup4 = ShadowUtils.shaderGroup;
                Intrinsics.checkNotNull(shaderGroup4);
                ((Shader)shaderGroup4.field_148031_d.get(i)).func_148043_c().func_147991_a("Radius").func_148090_a(strength);
            }
        }
        if (!(lastStrength == strength)) {
            lastStrength = strength;
            n = 0;
            while (n < 2) {
                i = n++;
                ShaderGroup shaderGroup = ShadowUtils.shaderGroup;
                Intrinsics.checkNotNull(shaderGroup);
                ((Shader)shaderGroup.field_148031_d.get(i)).func_148043_c().func_147991_a("Radius").func_148090_a(strength);
            }
        }
    }

    public final void shadow(float strength, @NotNull Function0<Unit> drawMethod, @NotNull Function0<Unit> cutMethod) {
        Intrinsics.checkNotNullParameter(drawMethod, "drawMethod");
        Intrinsics.checkNotNullParameter(cutMethod, "cutMethod");
        if (!OpenGlHelper.func_148822_b()) {
            return;
        }
        ScaledResolution sc = new ScaledResolution(MinecraftInstance.mc);
        int width = sc.func_78326_a();
        int height = sc.func_78328_b();
        this.initShaderIfRequired(sc, strength);
        if (initFramebuffer == null) {
            return;
        }
        if (resultBuffer == null) {
            return;
        }
        if (frameBuffer == null) {
            return;
        }
        MinecraftInstance.mc.func_147110_a().func_147609_e();
        Framebuffer framebuffer = initFramebuffer;
        Intrinsics.checkNotNull(framebuffer);
        framebuffer.func_147614_f();
        Framebuffer framebuffer2 = resultBuffer;
        Intrinsics.checkNotNull(framebuffer2);
        framebuffer2.func_147614_f();
        Framebuffer framebuffer3 = initFramebuffer;
        Intrinsics.checkNotNull(framebuffer3);
        framebuffer3.func_147610_a(true);
        drawMethod.invoke();
        Framebuffer framebuffer4 = frameBuffer;
        Intrinsics.checkNotNull(framebuffer4);
        framebuffer4.func_147610_a(true);
        ShaderGroup shaderGroup = ShadowUtils.shaderGroup;
        Intrinsics.checkNotNull(shaderGroup);
        shaderGroup.func_148018_a(MinecraftInstance.mc.field_71428_T.field_74281_c);
        MinecraftInstance.mc.func_147110_a().func_147610_a(true);
        Framebuffer framebuffer5 = resultBuffer;
        Intrinsics.checkNotNull(framebuffer5);
        double d = framebuffer5.field_147621_c;
        Framebuffer framebuffer6 = resultBuffer;
        Intrinsics.checkNotNull(framebuffer6);
        double fr_width = d / (double)framebuffer6.field_147622_a;
        Framebuffer framebuffer7 = resultBuffer;
        Intrinsics.checkNotNull(framebuffer7);
        double d2 = framebuffer7.field_147618_d;
        Framebuffer framebuffer8 = resultBuffer;
        Intrinsics.checkNotNull(framebuffer8);
        double fr_height = d2 / (double)framebuffer8.field_147620_b;
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldrenderer = tessellator.func_178180_c();
        GL11.glPushMatrix();
        GlStateManager.func_179140_f();
        GlStateManager.func_179118_c();
        GlStateManager.func_179098_w();
        GlStateManager.func_179097_i();
        GlStateManager.func_179132_a((boolean)false);
        GlStateManager.func_179135_a((boolean)true, (boolean)true, (boolean)true, (boolean)true);
        Stencil.write(false);
        cutMethod.invoke();
        Stencil.erase(false);
        GlStateManager.func_179147_l();
        GlStateManager.func_179112_b((int)770, (int)771);
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        Framebuffer framebuffer9 = resultBuffer;
        Intrinsics.checkNotNull(framebuffer9);
        framebuffer9.func_147612_c();
        GL11.glTexParameteri((int)3553, (int)10242, (int)33071);
        GL11.glTexParameteri((int)3553, (int)10243, (int)33071);
        worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181709_i);
        worldrenderer.func_181662_b(0.0, (double)height, 0.0).func_181673_a(0.0, 0.0).func_181669_b(255, 255, 255, 255).func_181675_d();
        worldrenderer.func_181662_b((double)width, (double)height, 0.0).func_181673_a(fr_width, 0.0).func_181669_b(255, 255, 255, 255).func_181675_d();
        worldrenderer.func_181662_b((double)width, 0.0, 0.0).func_181673_a(fr_width, fr_height).func_181669_b(255, 255, 255, 255).func_181675_d();
        worldrenderer.func_181662_b(0.0, 0.0, 0.0).func_181673_a(0.0, fr_height).func_181669_b(255, 255, 255, 255).func_181675_d();
        tessellator.func_78381_a();
        Framebuffer framebuffer10 = resultBuffer;
        Intrinsics.checkNotNull(framebuffer10);
        framebuffer10.func_147606_d();
        GlStateManager.func_179084_k();
        GlStateManager.func_179141_d();
        GlStateManager.func_179126_j();
        GlStateManager.func_179132_a((boolean)true);
        Stencil.dispose();
        GL11.glPopMatrix();
        GlStateManager.func_179117_G();
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.func_179147_l();
        GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
    }

    static {
        blurDirectory = new ResourceLocation("crosssine/shadow.json");
    }
}

