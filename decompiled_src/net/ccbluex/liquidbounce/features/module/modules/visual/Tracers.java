/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.visual;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.ui.client.gui.colortheme.ClientTheme;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@ModuleInfo(name="Tracers", category=ModuleCategory.VISUAL)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u001e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011J\u0010\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\u0014H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/visual/Tracers;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "colorAlphaValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "entityHeightValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "playerHeightValue", "thicknessValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "drawTraces", "", "entity", "Lnet/minecraft/entity/Entity;", "color", "Ljava/awt/Color;", "drawHeight", "", "onRender3D", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "CrossSine"})
public final class Tracers
extends Module {
    @NotNull
    private final FloatValue thicknessValue = new FloatValue("Thickness", 2.0f, 1.0f, 5.0f);
    @NotNull
    private final IntegerValue colorAlphaValue = new IntegerValue("Alpha", 150, 0, 255);
    @NotNull
    private final BoolValue playerHeightValue = new BoolValue("PlayerHeight", true);
    @NotNull
    private final BoolValue entityHeightValue = new BoolValue("EntityHeight", true);

    @EventTarget
    public final void onRender3D(@NotNull Render3DEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3042);
        GL11.glEnable((int)2848);
        GL11.glLineWidth((float)((Number)this.thicknessValue.get()).floatValue());
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        for (Entity entity : MinecraftInstance.mc.field_71441_e.field_72996_f) {
            if (entity == null || Intrinsics.areEqual(entity, MinecraftInstance.mc.field_71439_g) || !EntityUtils.INSTANCE.isSelected(entity, false)) continue;
            this.drawTraces(entity, ClientTheme.getColorWithAlpha$default(ClientTheme.INSTANCE, 1, ((Number)this.colorAlphaValue.get()).intValue(), false, 4, null));
        }
        GL11.glEnable((int)3553);
        GL11.glDisable((int)2848);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GlStateManager.func_179117_G();
    }

    private final void drawTraces(Entity entity, Color color) {
        double x = entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * (double)MinecraftInstance.mc.field_71428_T.field_74281_c - MinecraftInstance.mc.func_175598_ae().field_78725_b;
        double y = entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * (double)MinecraftInstance.mc.field_71428_T.field_74281_c - MinecraftInstance.mc.func_175598_ae().field_78726_c;
        double z = entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * (double)MinecraftInstance.mc.field_71428_T.field_74281_c - MinecraftInstance.mc.func_175598_ae().field_78723_d;
        Vec3 eyeVector = new Vec3(0.0, 0.0, 1.0).func_178789_a((float)(-Math.toRadians(MinecraftInstance.mc.field_71439_g.field_70125_A))).func_178785_b((float)(-Math.toRadians(MinecraftInstance.mc.field_71439_g.field_70177_z)));
        RenderUtils.glColor(color, ((Number)this.colorAlphaValue.get()).intValue());
        GL11.glBegin((int)3);
        GL11.glVertex3d((double)eyeVector.field_72450_a, (double)(((Boolean)this.playerHeightValue.get() != false ? (double)MinecraftInstance.mc.field_71439_g.func_70047_e() : 0.0) + eyeVector.field_72448_b), (double)eyeVector.field_72449_c);
        GL11.glVertex3d((double)x, (double)y, (double)z);
        if (((Boolean)this.entityHeightValue.get()).booleanValue()) {
            GL11.glVertex3d((double)x, (double)(y + (double)entity.field_70131_O), (double)z);
        }
        GL11.glEnd();
    }

    public final void drawTraces(@NotNull Entity entity, @NotNull Color color, boolean drawHeight) {
        Intrinsics.checkNotNullParameter(entity, "entity");
        Intrinsics.checkNotNullParameter(color, "color");
        double x = entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * (double)MinecraftInstance.mc.field_71428_T.field_74281_c - MinecraftInstance.mc.func_175598_ae().field_78725_b;
        double y = entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * (double)MinecraftInstance.mc.field_71428_T.field_74281_c - MinecraftInstance.mc.func_175598_ae().field_78726_c;
        double z = entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * (double)MinecraftInstance.mc.field_71428_T.field_74281_c - MinecraftInstance.mc.func_175598_ae().field_78723_d;
        Vec3 eyeVector = new Vec3(0.0, 0.0, 1.0).func_178789_a((float)(-Math.toRadians(MinecraftInstance.mc.field_71439_g.field_70125_A))).func_178785_b((float)(-Math.toRadians(MinecraftInstance.mc.field_71439_g.field_70177_z)));
        RenderUtils.glColor(color, ((Number)this.colorAlphaValue.get()).intValue());
        GL11.glBegin((int)3);
        GL11.glVertex3d((double)eyeVector.field_72450_a, (double)(((Boolean)this.playerHeightValue.get() != false ? (double)MinecraftInstance.mc.field_71439_g.func_70047_e() : 0.0) + eyeVector.field_72448_b), (double)eyeVector.field_72449_c);
        GL11.glVertex3d((double)x, (double)y, (double)z);
        if (((Boolean)this.entityHeightValue.get()).booleanValue()) {
            GL11.glVertex3d((double)x, (double)(y + (double)entity.field_70131_O), (double)z);
        }
        GL11.glEnd();
    }
}

