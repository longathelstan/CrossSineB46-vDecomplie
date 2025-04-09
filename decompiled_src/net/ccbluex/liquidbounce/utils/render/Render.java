/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.utils.render;

import java.awt.Color;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

public class Render {
    public float alpha = 255.0f;
    public Vec3 vec3;
    public long time;
    public float d;
    public Color color;

    public Render(double x, double y, double z, long time, Color color) {
        this.vec3 = new Vec3(x, y, z);
        this.time = time;
        this.color = color;
    }

    public void draw() {
        double var15;
        int i;
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glShadeModel((int)7425);
        GL11.glDisable((int)3553);
        GlStateManager.func_179097_i();
        GL11.glEnable((int)2848);
        GL11.glDisable((int)2896);
        GL11.glDepthMask((boolean)false);
        GL11.glHint((int)3154, (int)4354);
        GL11.glLineWidth((float)3.0f);
        GL11.glBegin((int)3);
        double renderPosX = Minecraft.func_71410_x().func_175598_ae().field_78730_l;
        double renderPosY = Minecraft.func_71410_x().func_175598_ae().field_78731_m;
        double renderPosZ = Minecraft.func_71410_x().func_175598_ae().field_78728_n;
        RenderUtils.setColor(new Color(this.color.getRed(), this.color.getGreen(), this.color.getBlue(), (int)this.alpha));
        for (i = 0; i <= 360; ++i) {
            GL11.glVertex3d((double)(this.vec3.field_72450_a - renderPosX + Math.cos((double)i * Math.PI / 180.0) * 0.6 * (double)this.d), (double)(this.vec3.field_72448_b - renderPosY), (double)(this.vec3.field_72449_c - renderPosZ + Math.sin((double)i * Math.PI / 180.0) * 0.6 * (double)this.d));
        }
        GL11.glEnd();
        GL11.glBegin((int)5);
        for (i = 0; i <= 360; i += 10) {
            for (int var11 = 0; var11 <= 3; ++var11) {
                GL11.glVertex3d((double)(this.vec3.field_72450_a - renderPosX + -Math.sin(Math.toRadians(i)) * (double)this.d), (double)(this.vec3.field_72448_b - renderPosY), (double)(this.vec3.field_72449_c - renderPosZ + Math.cos(Math.toRadians(i)) * (double)this.d));
                GL11.glVertex3d((double)(this.vec3.field_72450_a - renderPosX + -Math.sin(Math.toRadians(i)) * ((double)this.d - (double)var11 / 10.0)), (double)(this.vec3.field_72448_b - renderPosY), (double)(this.vec3.field_72449_c - renderPosZ + Math.cos(Math.toRadians(i)) * ((double)this.d - (double)var11 / 10.0)));
            }
        }
        double var14 = 0.0;
        if (var14 < 361.0) {
            var14 += 5.0;
        }
        if ((var15 = 0.0) < 255.0) {
            var15 += 3.0;
        }
        GL11.glEnd();
        GL11.glDepthMask((boolean)true);
        GlStateManager.func_179126_j();
        GL11.glDisable((int)2848);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glPopMatrix();
        if (this.d == 1.5f) {
            this.alpha = (float)((double)this.alpha - 1.0 * var15);
            this.alpha = MathHelper.func_76131_a((float)this.alpha, (float)0.0f, (float)255.0f);
        }
        this.d = (float)((double)this.d + 0.005 * var14);
        this.d = MathHelper.func_76131_a((float)this.d, (float)0.0f, (float)1.5f);
    }

    public float alpha() {
        return this.alpha;
    }
}

