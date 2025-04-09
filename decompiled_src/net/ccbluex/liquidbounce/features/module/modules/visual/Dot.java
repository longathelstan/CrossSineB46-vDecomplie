/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.visual;

import java.util.List;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.ui.client.gui.colortheme.ClientTheme;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

@ModuleInfo(name="Dot", category=ModuleCategory.VISUAL)
public class Dot
extends Module {
    private static final BoolValue onlyTarget = new BoolValue("OnlyRotation", false);

    @EventTarget
    public void onRender3D(Render3DEvent event) {
        double maxDistance2 = 3.0;
        Vec3 startVec = Dot.mc.field_71439_g.func_174824_e(event.getPartialTicks());
        Vec3 lookVec = Dot.mc.field_71439_g.func_174806_f(RotationUtils.targetRotation != null ? (float)RotationUtils.smoothPitch : Dot.mc.field_71439_g.field_70125_A, RotationUtils.fakeRotation != null ? RotationUtils.fakeRotation.getYaw() : (RotationUtils.targetRotation != null ? (float)RotationUtils.smoothYaw : Dot.mc.field_71439_g.field_70177_z));
        Vec3 endVec = startVec.func_72441_c(lookVec.field_72450_a * maxDistance2, lookVec.field_72448_b * maxDistance2, lookVec.field_72449_c * maxDistance2);
        MovingObjectPosition mop = Dot.mc.field_71441_e.func_147447_a(startVec, endVec, false, true, false);
        if (mop != null && mop.field_72313_a == MovingObjectPosition.MovingObjectType.BLOCK) {
            maxDistance2 = mop.field_72307_f.func_72438_d(startVec);
        } else {
            Entity pointedEntity = this.getPointedEntity((Entity)Dot.mc.field_71439_g, maxDistance2, event.getPartialTicks());
            if (pointedEntity != null) {
                maxDistance2 = Dot.mc.field_71439_g.func_70032_d(pointedEntity);
            }
        }
        Vec3 dotVec = startVec.func_72441_c(lookVec.field_72450_a * maxDistance2, lookVec.field_72448_b * maxDistance2, lookVec.field_72449_c * maxDistance2);
        if (!((Boolean)onlyTarget.get()).booleanValue() || RotationUtils.targetRotation != null || RotationUtils.fakeRotation != null) {
            GlStateManager.func_179094_E();
            GlStateManager.func_179147_l();
            this.renderDot(dotVec.field_72450_a - Dot.mc.func_175598_ae().field_78730_l, dotVec.field_72448_b - Dot.mc.func_175598_ae().field_78731_m, dotVec.field_72449_c - Dot.mc.func_175598_ae().field_78728_n);
            GlStateManager.func_179084_k();
            GlStateManager.func_179121_F();
        }
    }

    private Entity getPointedEntity(Entity entity, double range, float partialTicks) {
        Entity pointedEntity = null;
        Vec3 startVec = entity.func_174824_e(partialTicks);
        Vec3 lookVec = entity.func_70676_i(partialTicks);
        Vec3 endVec = startVec.func_72441_c(lookVec.field_72450_a * range, lookVec.field_72448_b * range, lookVec.field_72449_c * range);
        float f1 = 1.0f;
        List list = entity.field_70170_p.func_72839_b(entity, entity.func_174813_aQ().func_72321_a(lookVec.field_72450_a * range, lookVec.field_72448_b * range, lookVec.field_72449_c * range).func_72314_b((double)f1, (double)f1, (double)f1));
        double d2 = 0.0;
        for (Entity entity1 : list) {
            double d3;
            if (!entity1.func_70067_L()) continue;
            float f2 = entity1.func_70111_Y();
            AxisAlignedBB axisalignedbb = entity1.func_174813_aQ().func_72314_b((double)f2, (double)f2, (double)f2);
            MovingObjectPosition movingobjectposition = axisalignedbb.func_72327_a(startVec, endVec);
            if (axisalignedbb.func_72318_a(startVec)) {
                if (!(0.0 < d2) && d2 != 0.0) continue;
                pointedEntity = entity1;
                d2 = 0.0;
                continue;
            }
            if (movingobjectposition == null || !((d3 = startVec.func_72438_d(movingobjectposition.field_72307_f)) < d2) && d2 != 0.0) continue;
            if (entity1 == entity.field_70154_o && !entity.canRiderInteract()) {
                if (d2 != 0.0) continue;
                pointedEntity = entity1;
                continue;
            }
            pointedEntity = entity1;
            d2 = d3;
        }
        return pointedEntity;
    }

    private void renderDot(double x, double y, double z) {
        GlStateManager.func_179094_E();
        GlStateManager.func_179137_b((double)x, (double)y, (double)z);
        float size = 0.06f;
        AxisAlignedBB box = new AxisAlignedBB((double)(-size), (double)(-size), (double)(-size), (double)size, (double)size, (double)size);
        RenderUtils.drawAxisAlignedBB(box, ClientTheme.INSTANCE.getColorWithAlpha(0, 120, true), true, true, 2.0f);
        GlStateManager.func_179121_F();
        GlStateManager.func_179117_G();
    }
}

