/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.utils.render.shader.shaders;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.utils.render.shader.Shader;
import net.minecraft.client.renderer.GlStateManager;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002JI\u0010\u0003\u001a\u00020\u00002\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\fH\u0086\bJ\b\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\u000fH\u0016\u00a8\u0006\u0011"}, d2={"Lnet/ccbluex/liquidbounce/utils/render/shader/shaders/RoundedRectGradientShader;", "Lnet/ccbluex/liquidbounce/utils/render/shader/Shader;", "()V", "draw", "x", "", "y", "x2", "y2", "radius", "shadow", "colortop", "Ljava/awt/Color;", "colorbottom", "setupUniforms", "", "updateUniforms", "CrossSine"})
public final class RoundedRectGradientShader
extends Shader {
    @NotNull
    public static final RoundedRectGradientShader INSTANCE = new RoundedRectGradientShader();

    private RoundedRectGradientShader() {
        super("roundedrectgradient.frag");
    }

    @Override
    public void setupUniforms() {
        this.setupUniform("u_size");
        this.setupUniform("u_radius");
        this.setupUniform("u_color");
    }

    @Override
    public void updateUniforms() {
    }

    @NotNull
    public final RoundedRectGradientShader draw(float x, float y, float x2, float y2, float radius, float shadow, @NotNull Color colortop, @NotNull Color colorbottom) {
        Intrinsics.checkNotNullParameter(colortop, "colortop");
        Intrinsics.checkNotNullParameter(colorbottom, "colorbottom");
        boolean $i$f$draw = false;
        float width = Math.abs(x2 - x);
        float height = Math.abs(y2 - y);
        INSTANCE.startShader();
        float[] fArray = new float[]{width, height};
        INSTANCE.setUniformf("u_size", fArray);
        fArray = new float[]{radius};
        INSTANCE.setUniformf("u_radius", fArray);
        fArray = new float[]{(float)colortop.getRed() / 255.0f, (float)colortop.getGreen() / 255.0f, (float)colortop.getBlue() / 255.0f, (float)colortop.getAlpha() / 255.0f};
        INSTANCE.setUniformf("u_colorTop", fArray);
        fArray = new float[]{(float)colorbottom.getRed() / 255.0f, (float)colorbottom.getGreen() / 255.0f, (float)colorbottom.getBlue() / 255.0f, (float)colorbottom.getAlpha() / 255.0f};
        INSTANCE.setUniformf("u_colorBottom", fArray);
        fArray = new float[]{shadow};
        INSTANCE.setUniformf("u_shadow", fArray);
        GlStateManager.func_179147_l();
        GlStateManager.func_179112_b((int)770, (int)771);
        GlStateManager.func_179141_d();
        GlStateManager.func_179092_a((int)516, (float)0.0f);
        Shader.drawQuad(x, y, width, height);
        GlStateManager.func_179084_k();
        INSTANCE.stopShader();
        return INSTANCE;
    }
}

