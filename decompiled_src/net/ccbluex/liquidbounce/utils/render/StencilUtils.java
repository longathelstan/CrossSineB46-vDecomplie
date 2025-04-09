/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.utils.render;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.minecraft.client.shader.Framebuffer;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u000e\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0006\u0010\b\u001a\u00020\u0004J\u0010\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0006\u0010\n\u001a\u00020\u0004J\u0006\u0010\u000b\u001a\u00020\u0004\u00a8\u0006\f"}, d2={"Lnet/ccbluex/liquidbounce/utils/render/StencilUtils;", "", "()V", "checkSetupFBO", "", "fbo", "Lnet/minecraft/client/shader/Framebuffer;", "initStencil", "readFromStencil", "setupFBO", "uninitStencil", "writeToStencil", "CrossSine"})
public final class StencilUtils {
    @NotNull
    public static final StencilUtils INSTANCE = new StencilUtils();

    private StencilUtils() {
    }

    public final void initStencil(@NotNull Framebuffer fbo) {
        Intrinsics.checkNotNullParameter(fbo, "fbo");
        fbo.func_147610_a(false);
        this.checkSetupFBO(fbo);
        GL11.glClear((int)1024);
        GL11.glEnable((int)2960);
    }

    public final void uninitStencil() {
        GL11.glDisable((int)2960);
    }

    public final void writeToStencil() {
        GL11.glColorMask((boolean)false, (boolean)false, (boolean)false, (boolean)false);
        GL11.glStencilFunc((int)519, (int)1, (int)1);
        GL11.glStencilOp((int)7681, (int)7681, (int)7681);
    }

    public final void readFromStencil() {
        GL11.glColorMask((boolean)true, (boolean)true, (boolean)true, (boolean)true);
        GL11.glStencilFunc((int)514, (int)1, (int)1);
        GL11.glStencilOp((int)7680, (int)7680, (int)7680);
    }

    private final void checkSetupFBO(Framebuffer fbo) {
        if (fbo.field_147624_h > -1) {
            this.setupFBO(fbo);
            fbo.field_147624_h = -1;
        }
    }

    private final void setupFBO(Framebuffer fbo) {
        EXTFramebufferObject.glDeleteRenderbuffersEXT((int)fbo.field_147624_h);
        int depthBufferId = EXTFramebufferObject.glGenRenderbuffersEXT();
        EXTFramebufferObject.glBindRenderbufferEXT((int)36161, (int)depthBufferId);
        EXTFramebufferObject.glRenderbufferStorageEXT((int)36161, (int)34041, (int)ClientUtils.INSTANCE.getMc().field_71443_c, (int)ClientUtils.INSTANCE.getMc().field_71440_d);
        EXTFramebufferObject.glFramebufferRenderbufferEXT((int)36160, (int)36128, (int)36161, (int)depthBufferId);
        EXTFramebufferObject.glFramebufferRenderbufferEXT((int)36160, (int)36096, (int)36161, (int)depthBufferId);
    }
}

