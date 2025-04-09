/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.visual;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@ModuleInfo(name="Projectiles", category=ModuleCategory.VISUAL)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0007J\b\u0010\r\u001a\u00020\u000eH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/visual/Projectiles;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "dynamicBowPower", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "textBlueValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "textGreenValue", "textRedValue", "onRender3D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "textColor", "Ljava/awt/Color;", "CrossSine"})
public final class Projectiles
extends Module {
    @NotNull
    private final BoolValue dynamicBowPower = new BoolValue("DynamicBowPower", true);
    @NotNull
    private final IntegerValue textRedValue = new IntegerValue("Text-R", 255, 0, 255);
    @NotNull
    private final IntegerValue textGreenValue = new IntegerValue("Text-G", 255, 0, 255);
    @NotNull
    private final IntegerValue textBlueValue = new IntegerValue("Text-B", 255, 0, 255);

    @EventTarget
    public final void onRender3D(@NotNull Render3DEvent event) {
        Object posBefore;
        Intrinsics.checkNotNullParameter(event, "event");
        if (MinecraftInstance.mc.field_71439_g.func_70694_bm() == null) {
            return;
        }
        Item item = MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b();
        RenderManager renderManager = MinecraftInstance.mc.func_175598_ae();
        boolean isBow = false;
        float motionFactor = 1.5f;
        float motionSlowdown = 0.99f;
        float gravity = 0.0f;
        float size = 0.0f;
        if (item instanceof ItemBow) {
            if (((Boolean)this.dynamicBowPower.get()).booleanValue() && !MinecraftInstance.mc.field_71439_g.func_71039_bw()) {
                return;
            }
            isBow = true;
            gravity = 0.05f;
            size = 0.3f;
            float power = (float)((Boolean)this.dynamicBowPower.get() != false ? MinecraftInstance.mc.field_71439_g.func_71057_bx() : item.func_77626_a(new ItemStack(item))) / 20.0f;
            if ((power = (power * power + power * 2.0f) / 3.0f) < 0.1f) {
                return;
            }
            if (power > 1.0f) {
                power = 1.0f;
            }
            motionFactor = power * 3.0f;
        } else if (item instanceof ItemFishingRod) {
            gravity = 0.04f;
            size = 0.25f;
            motionSlowdown = 0.92f;
        } else if (item instanceof ItemPotion && ItemPotion.func_77831_g((int)MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77952_i())) {
            gravity = 0.05f;
            size = 0.25f;
            motionFactor = 0.5f;
        } else {
            if (!(item instanceof ItemSnowball || item instanceof ItemEnderPearl || item instanceof ItemEgg)) {
                return;
            }
            gravity = 0.03f;
            size = 0.25f;
        }
        float yaw = RotationUtils.targetRotation != null ? RotationUtils.targetRotation.getYaw() : MinecraftInstance.mc.field_71439_g.field_70177_z;
        float pitch = RotationUtils.targetRotation != null ? RotationUtils.targetRotation.getPitch() : MinecraftInstance.mc.field_71439_g.field_70125_A;
        double posX = renderManager.field_78725_b - (double)(MathHelper.func_76134_b((float)(yaw / 180.0f * (float)Math.PI)) * 0.16f);
        double posY = renderManager.field_78726_c + (double)MinecraftInstance.mc.field_71439_g.func_70047_e() - (double)0.1f;
        double posZ = renderManager.field_78723_d - (double)(MathHelper.func_76126_a((float)(yaw / 180.0f * (float)Math.PI)) * 0.16f);
        double motionX = (double)(-MathHelper.func_76126_a((float)(yaw / 180.0f * (float)Math.PI)) * MathHelper.func_76134_b((float)(pitch / 180.0f * (float)Math.PI))) * (isBow ? 1.0 : 0.4);
        double motionY = (double)(-MathHelper.func_76126_a((float)((pitch + (float)(item instanceof ItemPotion && ItemPotion.func_77831_g((int)MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77952_i()) ? -20 : 0)) / 180.0f * (float)Math.PI))) * (isBow ? 1.0 : 0.4);
        double motionZ = (double)(MathHelper.func_76134_b((float)(yaw / 180.0f * (float)Math.PI)) * MathHelper.func_76134_b((float)(pitch / 180.0f * (float)Math.PI))) * (isBow ? 1.0 : 0.4);
        float distance = MathHelper.func_76133_a((double)(motionX * motionX + motionY * motionY + motionZ * motionZ));
        motionX /= (double)distance;
        motionY /= (double)distance;
        motionZ /= (double)distance;
        motionX *= (double)motionFactor;
        motionY *= (double)motionFactor;
        motionZ *= (double)motionFactor;
        MovingObjectPosition landingPosition = null;
        boolean hasLanded = false;
        boolean hitEntity = false;
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldRenderer = tessellator.func_178180_c();
        List pos = new ArrayList();
        while (!hasLanded && posY > 0.0) {
            posBefore = new Vec3(posX, posY, posZ);
            Vec3 posAfter = new Vec3(posX + motionX, posY + motionY, posZ + motionZ);
            landingPosition = MinecraftInstance.mc.field_71441_e.func_147447_a((Vec3)posBefore, posAfter, false, true, false);
            posBefore = new Vec3(posX, posY, posZ);
            posAfter = new Vec3(posX + motionX, posY + motionY, posZ + motionZ);
            if (landingPosition != null) {
                hasLanded = true;
                posAfter = new Vec3(landingPosition.field_72307_f.field_72450_a, landingPosition.field_72307_f.field_72448_b, landingPosition.field_72307_f.field_72449_c);
            }
            AxisAlignedBB arrowBox = new AxisAlignedBB(posX - (double)size, posY - (double)size, posZ - (double)size, posX + (double)size, posY + (double)size, posZ + (double)size).func_72321_a(motionX, motionY, motionZ).func_72314_b(1.0, 1.0, 1.0);
            int chunkMinX = MathHelper.func_76128_c((double)((arrowBox.field_72340_a - 2.0) / 16.0));
            int chunkMaxX = MathHelper.func_76128_c((double)((arrowBox.field_72336_d + 2.0) / 16.0));
            int chunkMinZ = MathHelper.func_76128_c((double)((arrowBox.field_72339_c - 2.0) / 16.0));
            int chunkMaxZ = MathHelper.func_76128_c((double)((arrowBox.field_72334_f + 2.0) / 16.0));
            List collidedEntities = new ArrayList();
            int n = chunkMinX;
            if (n <= chunkMaxX) {
                int x;
                do {
                    int z;
                    x = n++;
                    int n2 = chunkMinZ;
                    if (n2 > chunkMaxZ) continue;
                    do {
                        z = n2++;
                        MinecraftInstance.mc.field_71441_e.func_72964_e(x, z).func_177414_a((Entity)MinecraftInstance.mc.field_71439_g, arrowBox, collidedEntities, null);
                    } while (z != chunkMaxZ);
                } while (x != chunkMaxX);
            }
            for (Entity possibleEntity : collidedEntities) {
                AxisAlignedBB possibleEntityBoundingBox;
                MovingObjectPosition movingObjectPosition;
                if (!possibleEntity.func_70067_L() || possibleEntity == MinecraftInstance.mc.field_71439_g || (movingObjectPosition = (possibleEntityBoundingBox = possibleEntity.func_174813_aQ().func_72314_b((double)size, (double)size, (double)size)).func_72327_a((Vec3)posBefore, posAfter)) == null) continue;
                MovingObjectPosition possibleEntityLanding = movingObjectPosition;
                hitEntity = true;
                hasLanded = true;
                landingPosition = possibleEntityLanding;
            }
            if (MinecraftInstance.mc.field_71441_e.func_180495_p(new BlockPos(posX += motionX, posY += motionY, posZ += motionZ)).func_177230_c().func_149688_o() == Material.field_151586_h) {
                motionX *= 0.6;
                motionY *= 0.6;
                motionZ *= 0.6;
            } else {
                motionX *= (double)motionSlowdown;
                motionY *= (double)motionSlowdown;
                motionZ *= (double)motionSlowdown;
            }
            motionY -= (double)gravity;
            pos.add(new Vec3(posX - renderManager.field_78725_b, posY - renderManager.field_78726_c, posZ - renderManager.field_78723_d));
        }
        GL11.glDepthMask((boolean)false);
        posBefore = new int[]{3042, 2848};
        RenderUtils.enableGlCap(posBefore);
        posBefore = new int[]{2929, 3008, 3553};
        RenderUtils.disableGlCap(posBefore);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glHint((int)3154, (int)4354);
        RenderUtils.glColor(hitEntity ? new Color(this.textColor().getRGB()) : new Color(this.textColor().getRGB()));
        GL11.glLineWidth((float)2.0f);
        worldRenderer.func_181668_a(3, DefaultVertexFormats.field_181705_e);
        Iterable $this$forEach$iv = pos;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Vec3 it = (Vec3)element$iv;
            boolean bl = false;
            worldRenderer.func_181662_b(it.field_72450_a, it.field_72448_b, it.field_72449_c).func_181675_d();
        }
        tessellator.func_78381_a();
        GL11.glPushMatrix();
        GL11.glTranslated((double)(posX - renderManager.field_78725_b), (double)(posY - renderManager.field_78726_c), (double)(posZ - renderManager.field_78723_d));
        if (landingPosition != null) {
            switch (landingPosition.field_178784_b.func_176740_k().ordinal()) {
                case 0: {
                    GL11.glRotatef((float)90.0f, (float)0.0f, (float)0.0f, (float)1.0f);
                    break;
                }
                case 2: {
                    GL11.glRotatef((float)90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                }
            }
            RenderUtils.drawAxisAlignedBB(new AxisAlignedBB(-0.5, 0.0, -0.5, 0.5, 0.1, 0.5), hitEntity ? new Color(this.textColor().getRGB()) : new Color(this.textColor().getRGB()), true, true, 3.0f);
        }
        GL11.glPopMatrix();
        GL11.glDepthMask((boolean)true);
        RenderUtils.resetCaps();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    private final Color textColor() {
        return new Color(((Number)this.textRedValue.get()).intValue(), ((Number)this.textGreenValue.get()).intValue(), ((Number)this.textBlueValue.get()).intValue());
    }
}

