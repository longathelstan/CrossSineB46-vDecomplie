/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.features.module.modules.visual.Chams;
import net.ccbluex.liquidbounce.features.module.modules.visual.ItemPhysics;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={RenderEntityItem.class})
public abstract class MixinRenderEntityItem
extends Render<EntityItem> {
    protected MixinRenderEntityItem(RenderManager p_i46179_1_) {
        super(p_i46179_1_);
    }

    @Shadow
    protected abstract int func_177078_a(ItemStack var1);

    @Inject(method={"doRender"}, at={@At(value="HEAD")})
    private void injectChamsPre(CallbackInfo callbackInfo) {
        Chams chams = CrossSine.moduleManager.getModule(Chams.class);
        if (chams.getState() && ((Boolean)chams.getItemsValue().get()).booleanValue()) {
            GL11.glEnable((int)32823);
            GL11.glPolygonOffset((float)1.0f, (float)-1000000.0f);
        }
    }

    @Inject(method={"doRender"}, at={@At(value="RETURN")})
    private void injectChamsPost(CallbackInfo callbackInfo) {
        Chams chams = CrossSine.moduleManager.getModule(Chams.class);
        if (chams.getState() && ((Boolean)chams.getItemsValue().get()).booleanValue()) {
            GL11.glPolygonOffset((float)1.0f, (float)1000000.0f);
            GL11.glDisable((int)32823);
        }
    }

    @Overwrite
    private int func_177077_a(EntityItem itemIn, double x, double y, double z, float p_177077_8_, IBakedModel ibakedmodel) {
        ItemStack itemStack = itemIn.func_92059_d();
        Item item = itemStack.func_77973_b();
        if (item == null || itemStack == null) {
            return 0;
        }
        boolean isGui3d = ibakedmodel.func_177556_c();
        int count = this.getItemCount(itemStack);
        float yOffset = 0.25f;
        float age = (float)itemIn.func_174872_o() + p_177077_8_;
        float hoverStart = itemIn.field_70290_d;
        boolean isPhysicsState = ItemPhysics.INSTANCE.getState();
        float weight = isPhysicsState ? 0.5f : 0.0f;
        float sinValue = (float)(Math.sin(age / 10.0f + hoverStart) * (double)0.1f + (double)0.1f);
        if (isPhysicsState) {
            sinValue = 0.0f;
        }
        float scaleY = ibakedmodel.func_177552_f().func_181688_b((ItemCameraTransforms.TransformType)ItemCameraTransforms.TransformType.GROUND).field_178363_d.y;
        if (isPhysicsState) {
            GlStateManager.func_179109_b((float)((float)x), (float)((float)y), (float)((float)z));
        } else {
            GlStateManager.func_179109_b((float)((float)x), (float)((float)y + sinValue + yOffset * scaleY), (float)((float)z));
        }
        if (isGui3d) {
            GlStateManager.func_179137_b((double)0.0, (double)0.0, (double)-0.08);
        } else {
            GlStateManager.func_179137_b((double)0.0, (double)0.0, (double)-0.04);
        }
        if (isGui3d || this.field_76990_c.field_78733_k != null) {
            float rotationYaw = (age / 20.0f + hoverStart) * 57.295776f;
            rotationYaw *= ((Float)ItemPhysics.INSTANCE.getRotationSpeed().get()).floatValue() * (1.0f + Math.min(age / 360.0f, 1.0f));
            if (isPhysicsState) {
                if (itemIn.field_70122_E) {
                    GL11.glRotatef((float)90.0f, (float)1.0f, (float)0.0f, (float)0.0f);
                    GL11.glRotatef((float)itemIn.field_70177_z, (float)0.0f, (float)0.0f, (float)1.0f);
                } else {
                    for (int a = 0; a < 10; ++a) {
                        GL11.glRotatef((float)rotationYaw, (float)weight, (float)weight, (float)1.0f);
                    }
                }
            } else {
                GlStateManager.func_179114_b((float)rotationYaw, (float)0.0f, (float)1.0f, (float)0.0f);
            }
        }
        if (!isGui3d) {
            float offsetX = -0.0f * (float)(count - 1) * 0.5f;
            float offsetY = -0.0f * (float)(count - 1) * 0.5f;
            float offsetZ = -0.09375f * (float)(count - 1) * 0.5f;
            GlStateManager.func_179109_b((float)offsetX, (float)offsetY, (float)offsetZ);
        }
        RenderUtils.color(1.0, 1.0, 1.0, 1.0);
        return count;
    }

    private int getItemCount(ItemStack stack) {
        int size = stack.field_77994_a;
        if (size > 48) {
            return 5;
        }
        if (size > 32) {
            return 4;
        }
        if (size > 16) {
            return 3;
        }
        if (size > 1) {
            return 2;
        }
        return 1;
    }
}

