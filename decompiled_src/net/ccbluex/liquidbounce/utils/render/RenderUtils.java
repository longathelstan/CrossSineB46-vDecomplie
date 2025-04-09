/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.utils.render;

import akka.actor.Nobody;
import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import net.ccbluex.liquidbounce.features.module.modules.client.Interface;
import net.ccbluex.liquidbounce.injection.access.StaticStorage;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MathUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.geom.Rectangle;
import net.ccbluex.liquidbounce.utils.render.AnimationUnit;
import net.ccbluex.liquidbounce.utils.render.AnimationUtil;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.ImageUtils;
import net.ccbluex.liquidbounce.utils.render.ShaderUtil;
import net.ccbluex.liquidbounce.utils.render.Stencil;
import net.ccbluex.liquidbounce.utils.render.glu.DirectTessCallback;
import net.ccbluex.liquidbounce.utils.render.glu.VertexData;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.Shader;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Timer;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.GLUtessellator;
import org.lwjgl.util.glu.GLUtessellatorCallback;
import org.lwjgl.util.glu.GLUtessellatorCallbackAdapter;
import org.lwjgl.util.glu.Project;

public final class RenderUtils
extends MinecraftInstance {
    private static final Map<String, Map<Integer, Boolean>> glCapMap = new HashMap<String, Map<Integer, Boolean>>();
    private static Framebuffer initialFB;
    private static Framebuffer frameBuffer;
    private static Framebuffer blackBuffer;
    private static ShaderGroup mainShader;
    private static float lastWidth;
    private static float lastHeight;
    private static float lastStrength;
    private static final Minecraft mc;
    private static final ResourceLocation blurDirectory;
    public static int deltaTime;
    private static final int[] DISPLAY_LISTS_2D;
    private static long startTime;
    private static int animationDuration;
    protected static float zLevel;
    private static final Frustum frustrum;

    public static double safeDivD(double numerator, int denominator) {
        return (double)denominator == 0.0 ? 0.0 : numerator / (double)denominator;
    }

    public static void drawAxisAlignedBB(AxisAlignedBB axisAlignedBB, Color color) {
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3042);
        GL11.glLineWidth((float)2.0f);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        RenderUtils.glColor(color);
        RenderUtils.drawFilledBox(axisAlignedBB);
        GlStateManager.func_179117_G();
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
    }

    public static float[] worldToScreen(double x, double y, double z) {
        Minecraft mc = Minecraft.func_71410_x();
        RenderManager renderManager = mc.func_175598_ae();
        double relativeX = x - renderManager.field_78730_l;
        double relativeY = y - renderManager.field_78731_m;
        double relativeZ = z - renderManager.field_78728_n;
        FloatBuffer modelView = BufferUtils.createFloatBuffer((int)16);
        FloatBuffer projection = BufferUtils.createFloatBuffer((int)16);
        IntBuffer viewport = BufferUtils.createIntBuffer((int)16);
        GL11.glGetFloat((int)2982, (FloatBuffer)modelView);
        GL11.glGetFloat((int)2983, (FloatBuffer)projection);
        GL11.glGetInteger((int)2978, (IntBuffer)viewport);
        FloatBuffer screenCoords = BufferUtils.createFloatBuffer((int)3);
        if (Project.gluProject((float)((float)relativeX), (float)((float)relativeY), (float)((float)relativeZ), (FloatBuffer)modelView, (FloatBuffer)projection, (IntBuffer)viewport, (FloatBuffer)screenCoords)) {
            float screenX = screenCoords.get(0);
            float screenY = (float)mc.field_71440_d - screenCoords.get(1);
            float screenZ = screenCoords.get(2);
            return new float[]{screenX, screenY, screenZ};
        }
        return null;
    }

    public static void drawClickGuiArrow(float x, float y, float size, AnimationUnit animation, int color) {
        GL11.glTranslatef((float)x, (float)y, (float)0.0f);
        double[] interpolation = new double[1];
        RenderUtils.setup2DRendering(() -> RenderUtils.render(5, () -> {
            RenderUtils.color(color);
            interpolation[0] = RenderUtils.interpolate(0.0, (double)size / 2.0, animation.getOutput());
            if (animation.getOutput() >= 0.48) {
                GL11.glVertex2d((double)(size / 2.0f), (double)RenderUtils.interpolate((double)size / 2.0, 0.0, animation.getOutput()));
            }
            GL11.glVertex2d((double)0.0, (double)interpolation[0]);
            if (animation.getOutput() < 0.48) {
                GL11.glVertex2d((double)(size / 2.0f), (double)RenderUtils.interpolate((double)size / 2.0, 0.0, animation.getOutput()));
            }
            GL11.glVertex2d((double)size, (double)interpolation[0]);
        }));
        GL11.glTranslatef((float)(-x), (float)(-y), (float)0.0f);
    }

    public static Color getGradientOffset(Color color1, Color color2, double offset) {
        int redPart;
        double inverse_percent;
        if (offset > 1.0) {
            inverse_percent = offset % 1.0;
            redPart = (int)offset;
            offset = redPart % 2 == 0 ? inverse_percent : 1.0 - inverse_percent;
        }
        inverse_percent = 1.0 - offset;
        redPart = (int)((double)color1.getRed() * inverse_percent + (double)color2.getRed() * offset);
        int greenPart = (int)((double)color1.getGreen() * inverse_percent + (double)color2.getGreen() * offset);
        int bluePart = (int)((double)color1.getBlue() * inverse_percent + (double)color2.getBlue() * offset);
        return new Color(redPart, greenPart, bluePart);
    }

    public static void drawRectBordered(double x, double y, double x1, double y1, double width, int internalColor, int borderColor) {
        RenderUtils.rectangle(x + width, y + width, x1 - width, y1 - width, internalColor);
        RenderUtils.rectangle(x + width, y, x1 - width, y + width, borderColor);
        RenderUtils.rectangle(x, y, x + width, y1, borderColor);
        RenderUtils.rectangle(x1 - width, y, x1, y1, borderColor);
        RenderUtils.rectangle(x + width, y1 - width, x1 - width, y1, borderColor);
    }

    public static void connectPoints(float xOne, float yOne, float xTwo, float yTwo) {
        GL11.glPushMatrix();
        GL11.glEnable((int)2848);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)0.8f);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3042);
        GL11.glLineWidth((float)0.5f);
        GL11.glBegin((int)1);
        GL11.glVertex2f((float)xOne, (float)yOne);
        GL11.glVertex2f((float)xTwo, (float)yTwo);
        GL11.glEnd();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glDisable((int)2848);
        GL11.glEnable((int)3553);
        GL11.glPopMatrix();
    }

    public static void drawGradientRect(double left, double top, double right, double bottom, boolean sideways, int startColor, int endColor) {
        GL11.glDisable((int)3553);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glShadeModel((int)7425);
        GL11.glBegin((int)7);
        RenderUtils.color(startColor);
        if (sideways) {
            GL11.glVertex2d((double)left, (double)top);
            GL11.glVertex2d((double)left, (double)bottom);
            RenderUtils.color(endColor);
            GL11.glVertex2d((double)right, (double)bottom);
            GL11.glVertex2d((double)right, (double)top);
        } else {
            GL11.glVertex2d((double)left, (double)top);
            RenderUtils.color(endColor);
            GL11.glVertex2d((double)left, (double)bottom);
            GL11.glVertex2d((double)right, (double)bottom);
            RenderUtils.color(startColor);
            GL11.glVertex2d((double)right, (double)top);
        }
        GL11.glEnd();
        GL11.glDisable((int)3042);
        GL11.glShadeModel((int)7424);
        GL11.glEnable((int)3553);
    }

    public static int darker(int hexColor, int factor) {
        float alpha = hexColor >> 24 & 0xFF;
        float red2 = Math.max((float)(hexColor >> 16 & 0xFF) - (float)(hexColor >> 16 & 0xFF) / (100.0f / (float)factor), 0.0f);
        float green2 = Math.max((float)(hexColor >> 8 & 0xFF) - (float)(hexColor >> 8 & 0xFF) / (100.0f / (float)factor), 0.0f);
        float blue2 = Math.max((float)(hexColor & 0xFF) - (float)(hexColor & 0xFF) / (100.0f / (float)factor), 0.0f);
        return (int)((float)(((int)alpha << 24) + ((int)red2 << 16) + ((int)green2 << 8)) + blue2);
    }

    public static int darker(int color, float factor) {
        int r = (int)((float)(color >> 16 & 0xFF) * factor);
        int g = (int)((float)(color >> 8 & 0xFF) * factor);
        int b = (int)((float)(color & 0xFF) * factor);
        int a = color >> 24 & 0xFF;
        return (r & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF | (a & 0xFF) << 24;
    }

    public static void targetHudRect(double x, double y, double x1, double y1, double size) {
        RenderUtils.rectangleBordered(x, y + -4.0, x1 + size, y1 + size, 0.5, new Color(60, 60, 60).getRGB(), new Color(10, 10, 10).getRGB());
        RenderUtils.rectangleBordered(x + 1.0, y + -3.0, x1 + size - 1.0, y1 + size - 1.0, 1.0, new Color(40, 40, 40).getRGB(), new Color(40, 40, 40).getRGB());
        RenderUtils.rectangleBordered(x + 2.5, y - 1.5, x1 + size - 2.5, y1 + size - 2.5, 0.5, new Color(40, 40, 40).getRGB(), new Color(60, 60, 60).getRGB());
        RenderUtils.rectangleBordered(x + 2.5, y - 1.5, x1 + size - 2.5, y1 + size - 2.5, 0.5, new Color(22, 22, 22).getRGB(), new Color(255, 255, 255, 0).getRGB());
    }

    public static void targetHudRect1(double x, double y, double x1, double y1, double size) {
        RenderUtils.rectangleBordered(x + 4.35, y + 0.5, x1 + size - 84.5, y1 + size - 4.35, 0.5, new Color(48, 48, 48).getRGB(), new Color(10, 10, 10).getRGB());
        RenderUtils.rectangleBordered(x + 5.0, y + 1.0, x1 + size - 85.0, y1 + size - 5.0, 0.5, new Color(17, 17, 17).getRGB(), new Color(255, 255, 255, 0).getRGB());
    }

    public static int reAlpha(int color, float alpha) {
        Color c = new Color(color);
        float r = 0.003921569f * (float)c.getRed();
        float g = 0.003921569f * (float)c.getGreen();
        float b = 0.003921569f * (float)c.getBlue();
        return new Color(r, g, b, alpha).getRGB();
    }

    public static void drawRoundedRect(float x, float y, float width, float height, float edgeRadius, int color, float borderWidth, int borderColor) {
        double angleRadians;
        int i;
        if (color == 0xFFFFFF) {
            color = -65794;
        }
        if (borderColor == 0xFFFFFF) {
            borderColor = -65794;
        }
        if (edgeRadius < 0.0f) {
            edgeRadius = 0.0f;
        }
        if (edgeRadius > width / 2.0f) {
            edgeRadius = width / 2.0f;
        }
        if (edgeRadius > height / 2.0f) {
            edgeRadius = height / 2.0f;
        }
        RenderUtils.drawRDRect(x + edgeRadius, y + edgeRadius, width - edgeRadius * 2.0f, height - edgeRadius * 2.0f, color);
        RenderUtils.drawRDRect(x + edgeRadius, y, width - edgeRadius * 2.0f, edgeRadius, color);
        RenderUtils.drawRDRect(x + edgeRadius, y + height - edgeRadius, width - edgeRadius * 2.0f, edgeRadius, color);
        RenderUtils.drawRDRect(x, y + edgeRadius, edgeRadius, height - edgeRadius * 2.0f, color);
        RenderUtils.drawRDRect(x + width - edgeRadius, y + edgeRadius, edgeRadius, height - edgeRadius * 2.0f, color);
        RenderUtils.enableRender2D();
        RenderUtils.color(color);
        GL11.glBegin((int)6);
        float centerX = x + edgeRadius;
        float centerY = y + edgeRadius;
        GL11.glVertex2d((double)centerX, (double)centerY);
        int vertices = (int)Math.min(Math.max(edgeRadius, 10.0f), 90.0f);
        for (i = 0; i < vertices + 1; ++i) {
            angleRadians = Math.PI * 2 * (double)(i + 180) / (double)(vertices * 4);
            GL11.glVertex2d((double)((double)centerX + Math.sin(angleRadians) * (double)edgeRadius), (double)((double)centerY + Math.cos(angleRadians) * (double)edgeRadius));
        }
        GL11.glEnd();
        GL11.glBegin((int)6);
        centerX = x + width - edgeRadius;
        centerY = y + edgeRadius;
        GL11.glVertex2d((double)centerX, (double)centerY);
        vertices = (int)Math.min(Math.max(edgeRadius, 10.0f), 90.0f);
        for (i = 0; i < vertices + 1; ++i) {
            angleRadians = Math.PI * 2 * (double)(i + 90) / (double)(vertices * 4);
            GL11.glVertex2d((double)((double)centerX + Math.sin(angleRadians) * (double)edgeRadius), (double)((double)centerY + Math.cos(angleRadians) * (double)edgeRadius));
        }
        GL11.glEnd();
        GL11.glBegin((int)6);
        centerX = x + edgeRadius;
        centerY = y + height - edgeRadius;
        GL11.glVertex2d((double)centerX, (double)centerY);
        vertices = (int)Math.min(Math.max(edgeRadius, 10.0f), 90.0f);
        for (i = 0; i < vertices + 1; ++i) {
            angleRadians = Math.PI * 2 * (double)(i + 270) / (double)(vertices * 4);
            GL11.glVertex2d((double)((double)centerX + Math.sin(angleRadians) * (double)edgeRadius), (double)((double)centerY + Math.cos(angleRadians) * (double)edgeRadius));
        }
        GL11.glEnd();
        GL11.glBegin((int)6);
        centerX = x + width - edgeRadius;
        centerY = y + height - edgeRadius;
        GL11.glVertex2d((double)centerX, (double)centerY);
        vertices = (int)Math.min(Math.max(edgeRadius, 10.0f), 90.0f);
        for (i = 0; i < vertices + 1; ++i) {
            angleRadians = Math.PI * 2 * (double)i / (double)(vertices * 4);
            GL11.glVertex2d((double)((double)centerX + Math.sin(angleRadians) * (double)edgeRadius), (double)((double)centerY + Math.cos(angleRadians) * (double)edgeRadius));
        }
        GL11.glEnd();
        RenderUtils.color(borderColor);
        GL11.glLineWidth((float)borderWidth);
        GL11.glBegin((int)3);
        centerX = x + edgeRadius;
        centerY = y + edgeRadius;
        vertices = i = (int)Math.min(Math.max(edgeRadius, 10.0f), 90.0f);
        while (i >= 0) {
            angleRadians = Math.PI * 2 * (double)(i + 180) / (double)(vertices * 4);
            GL11.glVertex2d((double)((double)centerX + Math.sin(angleRadians) * (double)edgeRadius), (double)((double)centerY + Math.cos(angleRadians) * (double)edgeRadius));
            --i;
        }
        GL11.glVertex2d((double)(x + edgeRadius), (double)y);
        GL11.glVertex2d((double)(x + width - edgeRadius), (double)y);
        centerX = x + width - edgeRadius;
        centerY = y + edgeRadius;
        for (i = vertices; i >= 0; --i) {
            angleRadians = Math.PI * 2 * (double)(i + 90) / (double)(vertices * 4);
            GL11.glVertex2d((double)((double)centerX + Math.sin(angleRadians) * (double)edgeRadius), (double)((double)centerY + Math.cos(angleRadians) * (double)edgeRadius));
        }
        GL11.glVertex2d((double)(x + width), (double)(y + edgeRadius));
        GL11.glVertex2d((double)(x + width), (double)(y + height - edgeRadius));
        centerX = x + width - edgeRadius;
        centerY = y + height - edgeRadius;
        for (i = vertices; i >= 0; --i) {
            angleRadians = Math.PI * 2 * (double)i / (double)(vertices * 4);
            GL11.glVertex2d((double)((double)centerX + Math.sin(angleRadians) * (double)edgeRadius), (double)((double)centerY + Math.cos(angleRadians) * (double)edgeRadius));
        }
        GL11.glVertex2d((double)(x + width - edgeRadius), (double)(y + height));
        GL11.glVertex2d((double)(x + edgeRadius), (double)(y + height));
        centerX = x + edgeRadius;
        centerY = y + height - edgeRadius;
        for (i = vertices; i >= 0; --i) {
            angleRadians = Math.PI * 2 * (double)(i + 270) / (double)(vertices * 4);
            GL11.glVertex2d((double)((double)centerX + Math.sin(angleRadians) * (double)edgeRadius), (double)((double)centerY + Math.cos(angleRadians) * (double)edgeRadius));
        }
        GL11.glVertex2d((double)x, (double)(y + height - edgeRadius));
        GL11.glVertex2d((double)x, (double)(y + edgeRadius));
        GL11.glEnd();
        RenderUtils.disableRender2D();
    }

    public static void drawRDRect(float left, float top, float width, float height, int color) {
        float f3 = (float)(color >> 24 & 0xFF) / 255.0f;
        float f4 = (float)(color >> 16 & 0xFF) / 255.0f;
        float f5 = (float)(color >> 8 & 0xFF) / 255.0f;
        float f6 = (float)(color & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldrenderer = tessellator.func_178180_c();
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
        GlStateManager.func_179131_c((float)f4, (float)f5, (float)f6, (float)f3);
        worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
        worldrenderer.func_181662_b((double)left, (double)(top + height), 0.0).func_181675_d();
        worldrenderer.func_181662_b((double)(left + width), (double)(top + height), 0.0).func_181675_d();
        worldrenderer.func_181662_b((double)(left + width), (double)top, 0.0).func_181675_d();
        worldrenderer.func_181662_b((double)left, (double)top, 0.0).func_181675_d();
        tessellator.func_78381_a();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
    }

    public static void enableRender2D() {
        GL11.glEnable((int)3042);
        GL11.glDisable((int)2884);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)2848);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glLineWidth((float)1.0f);
    }

    public static void disableRender2D() {
        GL11.glDisable((int)3042);
        GL11.glEnable((int)2884);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)2848);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.func_179103_j((int)7424);
        GlStateManager.func_179084_k();
        GlStateManager.func_179098_w();
    }

    public static void prepareGL() {
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
    }

    public static void releaseGL() {
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)2848);
    }

    public static void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height, float zLevel) {
        float f = 0.00390625f;
        float f1 = 0.00390625f;
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldrenderer = tessellator.func_178180_c();
        worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
        worldrenderer.func_181662_b((double)x, (double)(y + height), (double)zLevel).func_181673_a((double)((float)textureX * f), (double)((float)(textureY + height) * f1)).func_181675_d();
        worldrenderer.func_181662_b((double)(x + width), (double)(y + height), (double)zLevel).func_181673_a((double)((float)(textureX + width) * f), (double)((float)(textureY + height) * f1)).func_181675_d();
        worldrenderer.func_181662_b((double)(x + width), (double)y, (double)zLevel).func_181673_a((double)((float)(textureX + width) * f), (double)((float)textureY * f1)).func_181675_d();
        worldrenderer.func_181662_b((double)x, (double)y, (double)zLevel).func_181673_a((double)((float)textureX * f), (double)((float)textureY * f1)).func_181675_d();
        tessellator.func_78381_a();
    }

    public static double getAnimationStateSmooth(double target, double current, double speed) {
        boolean larger = target > current;
        boolean bl = larger;
        if (speed < 0.0) {
            speed = 0.0;
        } else if (speed > 1.0) {
            speed = 1.0;
        }
        if (target == current) {
            return target;
        }
        double dif = Math.max(target, current) - Math.min(target, current);
        double factor = Math.max(dif * speed, 1.0);
        if (factor < 0.1) {
            factor = 0.1;
        }
        current = larger ? (current + factor > target ? target : (current += factor)) : (current - factor < target ? target : (current -= factor));
        return current;
    }

    public static void prepareScissorBox(float x, float y, float x2, float y2) {
        ScaledResolution scale = new ScaledResolution(mc);
        int factor = scale.func_78325_e();
        GL11.glScissor((int)((int)(x * (float)factor)), (int)((int)(((float)scale.func_78328_b() - y2) * (float)factor)), (int)((int)((x2 - x) * (float)factor)), (int)((int)((y2 - y) * (float)factor)));
    }

    public static void drawOutlinedRect(float x, float y, float width, float height, float lineSize, int lineColor) {
        RenderUtils.drawRect(x, y, width, y + lineSize, lineColor);
        RenderUtils.drawRect(x, height - lineSize, width, height, lineColor);
        RenderUtils.drawRect(x, y + lineSize, x + lineSize, height - lineSize, lineColor);
        RenderUtils.drawRect(width - lineSize, y + lineSize, width, height - lineSize, lineColor);
    }

    public static void drawCircleRect(float x, float y, float x1, float y1, float radius, int color) {
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
        RenderUtils.glColor(color);
        RenderUtils.quickRenderCircle(x1 - radius, y1 - radius, 0.0, 90.0, radius, radius);
        RenderUtils.quickRenderCircle(x + radius, y1 - radius, 90.0, 180.0, radius, radius);
        RenderUtils.quickRenderCircle(x + radius, y + radius, 180.0, 270.0, radius, radius);
        RenderUtils.quickRenderCircle(x1 - radius, y + radius, 270.0, 360.0, radius, radius);
        RenderUtils.quickDrawRect(x + radius, y + radius, x1 - radius, y1 - radius);
        RenderUtils.quickDrawRect(x, y + radius, x + radius, y1 - radius);
        RenderUtils.quickDrawRect(x1 - radius, y + radius, x1, y1 - radius);
        RenderUtils.quickDrawRect(x + radius, y, x1 - radius, y + radius);
        RenderUtils.quickDrawRect(x + radius, y1 - radius, x1 - radius, y1);
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
    }

    public static int SkyRainbow(int var2, float st, float bright) {
        double d;
        double v1 = Math.ceil(System.currentTimeMillis() + (long)(var2 * 109)) / 5.0;
        return Color.getHSBColor((double)((float)(d / 360.0)) < 0.5 ? -((float)(v1 / 360.0)) : (float)((v1 %= 360.0) / 360.0), st, bright).getRGB();
    }

    public static Color skyRainbow(int index2, float st, float bright, float speed) {
        double d;
        double v1 = Math.ceil(System.currentTimeMillis() + (long)((float)(index2 * 109) * speed)) / 5.0;
        return Color.getHSBColor((double)((float)(d / 360.0)) < 0.5 ? -((float)(v1 / 360.0)) : (float)((v1 %= 360.0) / 360.0), st, bright);
    }

    public static void drawCircle(float x, float y, float radius, int color) {
        RenderUtils.glColor(color);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glPushMatrix();
        GL11.glLineWidth((float)1.0f);
        GL11.glBegin((int)9);
        for (int i = 0; i <= 360; ++i) {
            GL11.glVertex2d((double)((double)x + Math.sin((double)i * Math.PI / 180.0) * (double)radius), (double)((double)y + Math.cos((double)i * Math.PI / 180.0) * (double)radius));
        }
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)2848);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    public static void drawCheck(double x, double y, int lineWidth, int color) {
        RenderUtils.start2D();
        GL11.glPushMatrix();
        GL11.glLineWidth((float)lineWidth);
        RenderUtils.setColor(new Color(color));
        GL11.glBegin((int)3);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glVertex2d((double)(x + 2.0), (double)(y + 3.0));
        GL11.glVertex2d((double)(x + 6.0), (double)(y - 2.0));
        GL11.glEnd();
        GL11.glPopMatrix();
        RenderUtils.stop2D();
    }

    public static void setColor(Color color) {
        float alpha = (float)(color.getRGB() >> 24 & 0xFF) / 255.0f;
        float red2 = (float)(color.getRGB() >> 16 & 0xFF) / 255.0f;
        float green2 = (float)(color.getRGB() >> 8 & 0xFF) / 255.0f;
        float blue2 = (float)(color.getRGB() & 0xFF) / 255.0f;
        GL11.glColor4f((float)red2, (float)green2, (float)blue2, (float)alpha);
    }

    public static void start2D() {
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
    }

    public static void stop2D() {
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    public static void renderCircle(double x, double y, double radius, int color) {
        RenderUtils.renderCircle(x, y, 0.0, 360.0, radius - 1.0, color);
    }

    public static void renderCircle(double x, double y, double start, double end, double radius, int color) {
        RenderUtils.renderCircle(x, y, start, end, radius, radius, color);
    }

    public static void renderCircle(double x, double y, double start, double end, double w, double h, int color) {
        GlStateManager.func_179124_c((float)0.0f, (float)0.0f, (float)0.0f);
        GL11.glColor4f((float)0.0f, (float)0.0f, (float)0.0f, (float)0.0f);
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
        RenderUtils.glColor(color);
        RenderUtils.quickRenderCircle(x, y, start, end, w, h);
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
    }

    public static void quickRenderCircle(double x, double y, double start, double end, double w, double h) {
        if (start > end) {
            double temp = end;
            end = start;
            start = temp;
        }
        GL11.glBegin((int)6);
        GL11.glVertex2d((double)x, (double)y);
        for (double i = end; i >= start; i -= 4.0) {
            double ldx = Math.cos(i * Math.PI / 180.0) * w;
            double ldy = Math.sin(i * Math.PI / 180.0) * h;
            GL11.glVertex2d((double)(x + ldx), (double)(y + ldy));
        }
        GL11.glVertex2d((double)x, (double)y);
        GL11.glEnd();
    }

    private static void quickPolygonCircle(float x, float y, float xRadius, float yRadius, int start, int end) {
        for (int i = end; i >= start; i -= 4) {
            GL11.glVertex2d((double)((double)x + Math.sin((double)i * Math.PI / 180.0) * (double)xRadius), (double)((double)y + Math.cos((double)i * Math.PI / 180.0) * (double)yRadius));
        }
    }

    public static int getRainbowOpaque(int seconds, float saturation, float brightness, int index2) {
        float hue = (float)((System.currentTimeMillis() + (long)index2) % (long)(seconds * 1000)) / (float)(seconds * 1000);
        int color = Color.HSBtoRGB(hue, saturation, brightness);
        return color;
    }

    public static float smoothAnimation(float ani, float finalState, float speed, float scale) {
        return AnimationUtil.getAnimationState(ani, finalState, Math.max(10.0f, Math.abs(ani - finalState) * speed) * scale);
    }

    public static boolean isHovering(int mouseX, int mouseY, float xLeft, float yUp, float xRight, float yBottom) {
        return (float)mouseX > xLeft && (float)mouseX < xRight && (float)mouseY > yUp && (float)mouseY < yBottom;
    }

    public static void drawRoundedCornerRect(float x, float y, float x1, float y1, float radius, int color) {
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDisable((int)3553);
        boolean hasCull = GL11.glIsEnabled((int)2884);
        GL11.glDisable((int)2884);
        RenderUtils.glColor(color);
        RenderUtils.drawRoundedCornerRect(x, y, x1, y1, radius);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        RenderUtils.setGlState(2884, hasCull);
    }

    public static void fastShadowRoundedRect(float x, float y, float x2, float y2, float rad, float width, float r, float g, float b, float al) {
        Stencil.write(true);
        RenderUtils.drawRoundedRect(x, y, x2, y2, rad, new Color(r, g, b, al).getRGB());
        Stencil.erase(false);
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glShadeModel((int)7425);
        GL11.glColor4f((float)r, (float)g, (float)b, (float)al);
        GL11.glBegin((int)8);
        GL11.glVertex2f((float)(x + width / 2.0f), (float)(y + width / 2.0f));
        GL11.glColor4f((float)r, (float)g, (float)b, (float)0.0f);
        GL11.glVertex2f((float)(x - width), (float)(y - width));
        GL11.glColor4f((float)r, (float)g, (float)b, (float)al);
        GL11.glVertex2f((float)(x2 - width / 2.0f), (float)(y + width / 2.0f));
        GL11.glColor4f((float)r, (float)g, (float)b, (float)0.0f);
        GL11.glVertex2f((float)(x2 + width), (float)(y - width));
        GL11.glColor4f((float)r, (float)g, (float)b, (float)al);
        GL11.glVertex2f((float)(x2 - width / 2.0f), (float)(y2 - width / 2.0f));
        GL11.glColor4f((float)r, (float)g, (float)b, (float)0.0f);
        GL11.glVertex2f((float)(x2 + width), (float)(y2 + width));
        GL11.glColor4f((float)r, (float)g, (float)b, (float)al);
        GL11.glVertex2f((float)(x + width / 2.0f), (float)(y2 - width / 2.0f));
        GL11.glColor4f((float)r, (float)g, (float)b, (float)0.0f);
        GL11.glVertex2f((float)(x - width), (float)(y2 + width));
        GL11.glColor4f((float)r, (float)g, (float)b, (float)al);
        GL11.glVertex2f((float)(x + width / 2.0f), (float)(y + width / 2.0f));
        GL11.glColor4f((float)r, (float)g, (float)b, (float)0.0f);
        GL11.glVertex2f((float)(x - width), (float)(y - width));
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
        GL11.glShadeModel((int)7424);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glPopMatrix();
        Stencil.dispose();
    }

    public static void fastShadowRoundedRect(float x, float y, float x2, float y2, float rad, float width, Color color) {
        RenderUtils.fastShadowRoundedRect(x, y, x2, y2, rad, width, (float)color.getRed() / 255.0f, (float)color.getGreen() / 255.0f, (float)color.getBlue() / 255.0f, (float)color.getAlpha() / 255.0f);
    }

    public static void initBlur(ScaledResolution sc, float strength) throws IOException {
        int w = sc.func_78326_a();
        int h = sc.func_78328_b();
        int f = sc.func_78325_e();
        if (lastWidth != (float)w || lastHeight != (float)h || initialFB == null || frameBuffer == null || mainShader == null) {
            initialFB = new Framebuffer(w * f, h * f, true);
            initialFB.func_147604_a(0.0f, 0.0f, 0.0f, 0.0f);
            initialFB.func_147607_a(9729);
            mainShader = new ShaderGroup(mc.func_110434_K(), mc.func_110442_L(), initialFB, blurDirectory);
            mainShader.func_148026_a(w * f, h * f);
            frameBuffer = RenderUtils.mainShader.field_148035_a;
            blackBuffer = mainShader.func_177066_a("braindead");
        }
        lastWidth = w;
        lastHeight = h;
        if (strength != lastStrength) {
            lastStrength = strength;
            for (int i = 0; i < 2; ++i) {
                ((Shader)RenderUtils.mainShader.field_148031_d.get(i)).func_148043_c().func_147991_a("Radius").func_148090_a(strength);
            }
        }
    }

    public static void processShadow(boolean begin, float strength) throws IOException {
        if (!OpenGlHelper.func_148822_b()) {
            return;
        }
        ScaledResolution sc = new ScaledResolution(mc);
        RenderUtils.initBlur(sc, strength);
        if (begin) {
            mc.func_147110_a().func_147609_e();
            initialFB.func_147614_f();
            blackBuffer.func_147614_f();
            initialFB.func_147610_a(true);
        } else {
            frameBuffer.func_147610_a(true);
            mainShader.func_148018_a(RenderUtils.mc.field_71428_T.field_74281_c);
            mc.func_147110_a().func_147610_a(true);
            float f = sc.func_78326_a();
            float f1 = sc.func_78328_b();
            float f2 = (float)RenderUtils.blackBuffer.field_147621_c / (float)RenderUtils.blackBuffer.field_147622_a;
            float f3 = (float)RenderUtils.blackBuffer.field_147618_d / (float)RenderUtils.blackBuffer.field_147620_b;
            GlStateManager.func_179094_E();
            GlStateManager.func_179140_f();
            GlStateManager.func_179118_c();
            GlStateManager.func_179098_w();
            GlStateManager.func_179097_i();
            GlStateManager.func_179132_a((boolean)false);
            GlStateManager.func_179135_a((boolean)true, (boolean)true, (boolean)true, (boolean)true);
            GlStateManager.func_179147_l();
            GlStateManager.func_179112_b((int)770, (int)771);
            GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            blackBuffer.func_147612_c();
            GL11.glTexParameteri((int)3553, (int)10242, (int)33071);
            GL11.glTexParameteri((int)3553, (int)10243, (int)33071);
            Tessellator tessellator = Tessellator.func_178181_a();
            WorldRenderer worldrenderer = tessellator.func_178180_c();
            worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181709_i);
            worldrenderer.func_181662_b(0.0, (double)f1, 0.0).func_181673_a(0.0, 0.0).func_181669_b(255, 255, 255, 255).func_181675_d();
            worldrenderer.func_181662_b((double)f, (double)f1, 0.0).func_181673_a((double)f2, 0.0).func_181669_b(255, 255, 255, 255).func_181675_d();
            worldrenderer.func_181662_b((double)f, 0.0, 0.0).func_181673_a((double)f2, (double)f3).func_181669_b(255, 255, 255, 255).func_181675_d();
            worldrenderer.func_181662_b(0.0, 0.0, 0.0).func_181673_a(0.0, (double)f3).func_181669_b(255, 255, 255, 255).func_181675_d();
            tessellator.func_78381_a();
            blackBuffer.func_147606_d();
            GlStateManager.func_179084_k();
            GlStateManager.func_179141_d();
            GlStateManager.func_179126_j();
            GlStateManager.func_179132_a((boolean)true);
            GlStateManager.func_179098_w();
            GlStateManager.func_179121_F();
            GlStateManager.func_179117_G();
            GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GlStateManager.func_179147_l();
            GlStateManager.func_179112_b((int)770, (int)771);
        }
    }

    public static void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor) {
        float f = (float)(startColor >> 24 & 0xFF) / 255.0f;
        float f1 = (float)(startColor >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(startColor >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(startColor & 0xFF) / 255.0f;
        float f4 = (float)(endColor >> 24 & 0xFF) / 255.0f;
        float f5 = (float)(endColor >> 16 & 0xFF) / 255.0f;
        float f6 = (float)(endColor >> 8 & 0xFF) / 255.0f;
        float f7 = (float)(endColor & 0xFF) / 255.0f;
        GlStateManager.func_179094_E();
        GlStateManager.func_179090_x();
        GlStateManager.func_179147_l();
        GlStateManager.func_179118_c();
        GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
        GlStateManager.func_179103_j((int)7425);
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldrenderer = tessellator.func_178180_c();
        worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181706_f);
        worldrenderer.func_181662_b((double)right, (double)top, (double)zLevel).func_181666_a(f1, f2, f3, f).func_181675_d();
        worldrenderer.func_181662_b((double)left, (double)top, (double)zLevel).func_181666_a(f1, f2, f3, f).func_181675_d();
        worldrenderer.func_181662_b((double)left, (double)bottom, (double)zLevel).func_181666_a(f5, f6, f7, f4).func_181675_d();
        worldrenderer.func_181662_b((double)right, (double)bottom, (double)zLevel).func_181666_a(f5, f6, f7, f4).func_181675_d();
        tessellator.func_78381_a();
        GlStateManager.func_179103_j((int)7424);
        GlStateManager.func_179084_k();
        GlStateManager.func_179141_d();
        GlStateManager.func_179098_w();
        GlStateManager.func_179121_F();
    }

    public static void drawGradientRoundedRect(int left, int top, int right, int bottom, int radius, int startColor, int endColor) {
        Stencil.write(false);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        RenderUtils.fastRoundedRect(left, top, right, bottom, radius);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3553);
        Stencil.erase(true);
        RenderUtils.drawGradientRect(left, top, right, bottom, startColor, endColor);
        Stencil.dispose();
    }

    public static void drawGradientRoundedRect(float left, float top, float right, float bottom, int radius, int startColor, int endColor) {
        Stencil.write(false);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        RenderUtils.fastRoundedRect(left, top, right, bottom, radius);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3553);
        Stencil.erase(true);
        RenderUtils.drawGradientRect(left, top, right, bottom, startColor, endColor);
        Stencil.dispose();
    }

    public static void drawGradientSideways(double left, double top, double right, double bottom, int col1, int col2) {
        float f = (float)(col1 >> 24 & 0xFF) / 255.0f;
        float f1 = (float)(col1 >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(col1 >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(col1 & 0xFF) / 255.0f;
        float f4 = (float)(col2 >> 24 & 0xFF) / 255.0f;
        float f5 = (float)(col2 >> 16 & 0xFF) / 255.0f;
        float f6 = (float)(col2 >> 8 & 0xFF) / 255.0f;
        float f7 = (float)(col2 & 0xFF) / 255.0f;
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glShadeModel((int)7425);
        GL11.glPushMatrix();
        GL11.glBegin((int)7);
        GL11.glColor4f((float)f1, (float)f2, (float)f3, (float)f);
        GL11.glVertex2d((double)left, (double)top);
        GL11.glVertex2d((double)left, (double)bottom);
        GL11.glColor4f((float)f5, (float)f6, (float)f7, (float)f4);
        GL11.glVertex2d((double)right, (double)bottom);
        GL11.glVertex2d((double)right, (double)top);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
        GL11.glShadeModel((int)7424);
    }

    public static void drawAnimatedGradient(double left, double top, double right, double bottom, int col1, int col2) {
        int color2;
        int color1;
        long currentTime = System.currentTimeMillis();
        if (startTime == 0L) {
            startTime = currentTime;
        }
        long elapsedTime = currentTime - startTime;
        float progress = (float)(elapsedTime % (long)animationDuration) / (float)animationDuration;
        if (elapsedTime / (long)animationDuration % 2L == 0L) {
            color1 = RenderUtils.interpolateColors(col1, col2, progress);
            color2 = RenderUtils.interpolateColors(col2, col1, progress);
        } else {
            color1 = RenderUtils.interpolateColors(col2, col1, progress);
            color2 = RenderUtils.interpolateColors(col1, col2, progress);
        }
        RenderUtils.drawGradientSideways(left, top, right, bottom, color1, color2);
        if (elapsedTime >= (long)(2 * animationDuration)) {
            startTime = currentTime;
        }
    }

    public static int interpolateColors(int color1, int color2, float progress) {
        int alpha = (int)((1.0 - (double)progress) * (double)(color1 >>> 24) + (double)(progress * (float)(color2 >>> 24)));
        int red2 = (int)((1.0 - (double)progress) * (double)(color1 >> 16 & 0xFF) + (double)(progress * (float)(color2 >> 16 & 0xFF)));
        int green2 = (int)((1.0 - (double)progress) * (double)(color1 >> 8 & 0xFF) + (double)(progress * (float)(color2 >> 8 & 0xFF)));
        int blue2 = (int)((1.0 - (double)progress) * (double)(color1 & 0xFF) + (double)(progress * (float)(color2 & 0xFF)));
        return alpha << 24 | red2 << 16 | green2 << 8 | blue2;
    }

    public static void drawShadow(float x, float y, float width, float height) {
        RenderUtils.drawTexturedRect(x - 9.0f, y - 9.0f, 9.0f, 9.0f, "paneltopleft");
        RenderUtils.drawTexturedRect(x - 9.0f, y + height, 9.0f, 9.0f, "panelbottomleft");
        RenderUtils.drawTexturedRect(x + width, y + height, 9.0f, 9.0f, "panelbottomright");
        RenderUtils.drawTexturedRect(x + width, y - 9.0f, 9.0f, 9.0f, "paneltopright");
        RenderUtils.drawTexturedRect(x - 9.0f, y, 9.0f, height, "panelleft");
        RenderUtils.drawTexturedRect(x + width, y, 9.0f, height, "panelright");
        RenderUtils.drawTexturedRect(x, y - 9.0f, width, 9.0f, "paneltop");
        RenderUtils.drawTexturedRect(x, y + height, width, 9.0f, "panelbottom");
    }

    public static void drawTexturedRect(float x, float y, float width, float height, String image) {
        boolean disableAlpha;
        GL11.glPushMatrix();
        boolean enableBlend = GL11.glIsEnabled((int)3042);
        boolean bl = disableAlpha = !GL11.glIsEnabled((int)3008);
        if (!enableBlend) {
            GL11.glEnable((int)3042);
        }
        if (!disableAlpha) {
            GL11.glDisable((int)3008);
        }
        mc.func_110434_K().func_110577_a(new ResourceLocation("crosssine/ui/shadow/" + image + ".png"));
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderUtils.drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, width, height, width, height);
        if (!enableBlend) {
            GL11.glDisable((int)3042);
        }
        if (!disableAlpha) {
            GL11.glEnable((int)3008);
        }
        GL11.glPopMatrix();
    }

    public static void drawModalRectWithCustomSizedTexture(float x, float y, float u, float v, float width, float height, float textureWidth, float textureHeight) {
        float f = 1.0f / textureWidth;
        float f1 = 1.0f / textureHeight;
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldrenderer = tessellator.func_178180_c();
        worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
        worldrenderer.func_181662_b((double)x, (double)(y + height), 0.0).func_181673_a((double)(u * f), (double)((v + height) * f1)).func_181675_d();
        worldrenderer.func_181662_b((double)(x + width), (double)(y + height), 0.0).func_181673_a((double)((u + width) * f), (double)((v + height) * f1)).func_181675_d();
        worldrenderer.func_181662_b((double)(x + width), (double)y, 0.0).func_181673_a((double)((u + width) * f), (double)(v * f1)).func_181675_d();
        worldrenderer.func_181662_b((double)x, (double)y, 0.0).func_181673_a((double)(u * f), (double)(v * f1)).func_181675_d();
        tessellator.func_78381_a();
    }

    public static void drawExhiEnchants(ItemStack stack, float x, float y) {
        int unb;
        RenderHelper.func_74518_a();
        GlStateManager.func_179097_i();
        GlStateManager.func_179084_k();
        GlStateManager.func_179117_G();
        int darkBorder = -16777216;
        if (stack.func_77973_b() instanceof ItemArmor) {
            int prot = EnchantmentHelper.func_77506_a((int)Enchantment.field_180310_c.field_77352_x, (ItemStack)stack);
            int unb2 = EnchantmentHelper.func_77506_a((int)Enchantment.field_77347_r.field_77352_x, (ItemStack)stack);
            int thorn = EnchantmentHelper.func_77506_a((int)Enchantment.field_92091_k.field_77352_x, (ItemStack)stack);
            if (prot > 0) {
                RenderUtils.drawExhiOutlined(prot + "", RenderUtils.drawExhiOutlined("P", x, y, 0.35f, -16777216, -1, true), y, 0.35f, RenderUtils.getBorderColor(prot), RenderUtils.getMainColor(prot), true);
                y += 4.0f;
            }
            if (unb2 > 0) {
                RenderUtils.drawExhiOutlined(unb2 + "", RenderUtils.drawExhiOutlined("U", x, y, 0.35f, -16777216, -1, true), y, 0.35f, RenderUtils.getBorderColor(unb2), RenderUtils.getMainColor(unb2), true);
                y += 4.0f;
            }
            if (thorn > 0) {
                RenderUtils.drawExhiOutlined(thorn + "", RenderUtils.drawExhiOutlined("T", x, y, 0.35f, -16777216, -1, true), y, 0.35f, RenderUtils.getBorderColor(thorn), RenderUtils.getMainColor(thorn), true);
                y += 4.0f;
            }
        }
        if (stack.func_77973_b() instanceof ItemBow) {
            int power = EnchantmentHelper.func_77506_a((int)Enchantment.field_77345_t.field_77352_x, (ItemStack)stack);
            int punch = EnchantmentHelper.func_77506_a((int)Enchantment.field_77344_u.field_77352_x, (ItemStack)stack);
            int flame = EnchantmentHelper.func_77506_a((int)Enchantment.field_77343_v.field_77352_x, (ItemStack)stack);
            unb = EnchantmentHelper.func_77506_a((int)Enchantment.field_77347_r.field_77352_x, (ItemStack)stack);
            if (power > 0) {
                RenderUtils.drawExhiOutlined(power + "", RenderUtils.drawExhiOutlined("Pow", x, y, 0.35f, -16777216, -1, true), y, 0.35f, RenderUtils.getBorderColor(power), RenderUtils.getMainColor(power), true);
                y += 4.0f;
            }
            if (punch > 0) {
                RenderUtils.drawExhiOutlined(punch + "", RenderUtils.drawExhiOutlined("Pun", x, y, 0.35f, -16777216, -1, true), y, 0.35f, RenderUtils.getBorderColor(punch), RenderUtils.getMainColor(punch), true);
                y += 4.0f;
            }
            if (flame > 0) {
                RenderUtils.drawExhiOutlined(flame + "", RenderUtils.drawExhiOutlined("F", x, y, 0.35f, -16777216, -1, true), y, 0.35f, RenderUtils.getBorderColor(flame), RenderUtils.getMainColor(flame), true);
                y += 4.0f;
            }
            if (unb > 0) {
                RenderUtils.drawExhiOutlined(unb + "", RenderUtils.drawExhiOutlined("U", x, y, 0.35f, -16777216, -1, true), y, 0.35f, RenderUtils.getBorderColor(unb), RenderUtils.getMainColor(unb), true);
                y += 4.0f;
            }
        }
        if (stack.func_77973_b() instanceof ItemSword) {
            int sharp = EnchantmentHelper.func_77506_a((int)Enchantment.field_180314_l.field_77352_x, (ItemStack)stack);
            int kb = EnchantmentHelper.func_77506_a((int)Enchantment.field_180313_o.field_77352_x, (ItemStack)stack);
            int fire = EnchantmentHelper.func_77506_a((int)Enchantment.field_77334_n.field_77352_x, (ItemStack)stack);
            unb = EnchantmentHelper.func_77506_a((int)Enchantment.field_77347_r.field_77352_x, (ItemStack)stack);
            if (sharp > 0) {
                RenderUtils.drawExhiOutlined(sharp + "", RenderUtils.drawExhiOutlined("S", x, y, 0.35f, -16777216, -1, true), y, 0.35f, RenderUtils.getBorderColor(sharp), RenderUtils.getMainColor(sharp), true);
                y += 4.0f;
            }
            if (kb > 0) {
                RenderUtils.drawExhiOutlined(kb + "", RenderUtils.drawExhiOutlined("K", x, y, 0.35f, -16777216, -1, true), y, 0.35f, RenderUtils.getBorderColor(kb), RenderUtils.getMainColor(kb), true);
                y += 4.0f;
            }
            if (fire > 0) {
                RenderUtils.drawExhiOutlined(fire + "", RenderUtils.drawExhiOutlined("F", x, y, 0.35f, -16777216, -1, true), y, 0.35f, RenderUtils.getBorderColor(fire), RenderUtils.getMainColor(fire), true);
                y += 4.0f;
            }
            if (unb > 0) {
                RenderUtils.drawExhiOutlined(unb + "", RenderUtils.drawExhiOutlined("U", x, y, 0.35f, -16777216, -1, true), y, 0.35f, RenderUtils.getBorderColor(unb), RenderUtils.getMainColor(unb), true);
                y += 4.0f;
            }
        }
        GlStateManager.func_179126_j();
        RenderHelper.func_74520_c();
    }

    private static float drawExhiOutlined(String text, float x, float y, float borderWidth, int borderColor, int mainColor, boolean drawText) {
        Fonts.fontTahomaSmall.drawString(text, x, y - borderWidth, borderColor);
        Fonts.fontTahomaSmall.drawString(text, x, y + borderWidth, borderColor);
        Fonts.fontTahomaSmall.drawString(text, x - borderWidth, y, borderColor);
        Fonts.fontTahomaSmall.drawString(text, x + borderWidth, y, borderColor);
        if (drawText) {
            Fonts.fontTahomaSmall.drawString(text, x, y, mainColor);
        }
        return x + Fonts.fontTahomaSmall.getWidth(text) - 2.0f;
    }

    public static void drawFilledCircle(int xx, int yy, float radius, int col) {
        float f = (float)(col >> 24 & 0xFF) / 255.0f;
        float f1 = (float)(col >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(col >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(col & 0xFF) / 255.0f;
        int sections = 50;
        double dAngle = Math.PI * 2 / (double)sections;
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)2848);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glBegin((int)6);
        for (int i = 0; i < sections; ++i) {
            float x = (float)((double)radius * Math.sin((double)i * dAngle));
            float y = (float)((double)radius * Math.cos((double)i * dAngle));
            GL11.glColor4f((float)f1, (float)f2, (float)f3, (float)f);
            GL11.glVertex2f((float)((float)xx + x), (float)((float)yy + y));
        }
        GlStateManager.func_179124_c((float)0.0f, (float)0.0f, (float)0.0f);
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
        GL11.glPopMatrix();
    }

    public static void drawGradientRect(float left, float top, float right, float bottom, int startColor, int endColor) {
        float f = (float)(startColor >> 24 & 0xFF) / 255.0f;
        float f1 = (float)(startColor >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(startColor >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(startColor & 0xFF) / 255.0f;
        float f4 = (float)(endColor >> 24 & 0xFF) / 255.0f;
        float f5 = (float)(endColor >> 16 & 0xFF) / 255.0f;
        float f6 = (float)(endColor >> 8 & 0xFF) / 255.0f;
        float f7 = (float)(endColor & 0xFF) / 255.0f;
        GlStateManager.func_179090_x();
        GlStateManager.func_179147_l();
        GlStateManager.func_179118_c();
        GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
        GlStateManager.func_179103_j((int)7425);
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldrenderer = tessellator.func_178180_c();
        worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181706_f);
        worldrenderer.func_181662_b((double)right, (double)top, 0.0).func_181666_a(f1, f2, f3, f).func_181675_d();
        worldrenderer.func_181662_b((double)left, (double)top, 0.0).func_181666_a(f1, f2, f3, f).func_181675_d();
        worldrenderer.func_181662_b((double)left, (double)bottom, 0.0).func_181666_a(f5, f6, f7, f4).func_181675_d();
        worldrenderer.func_181662_b((double)right, (double)bottom, 0.0).func_181666_a(f5, f6, f7, f4).func_181675_d();
        tessellator.func_78381_a();
        GlStateManager.func_179103_j((int)7424);
        GlStateManager.func_179084_k();
        GlStateManager.func_179141_d();
        GlStateManager.func_179098_w();
    }

    private static int getMainColor(int level) {
        if (level == 4) {
            return -5636096;
        }
        return -1;
    }

    private static int getBorderColor(int level) {
        if (level == 2) {
            return 1884684117;
        }
        if (level == 3) {
            return 0x7000AAAA;
        }
        if (level == 4) {
            return 0x70AA0000;
        }
        if (level >= 5) {
            return 1895803392;
        }
        return 0x70FFFFFF;
    }

    public static void customRounded(float paramXStart, float paramYStart, float paramXEnd, float paramYEnd, float rTL, float rTR, float rBR, float rBL, int color) {
        double i;
        float z;
        float alpha = (float)(color >> 24 & 0xFF) / 255.0f;
        float red2 = (float)(color >> 16 & 0xFF) / 255.0f;
        float green2 = (float)(color >> 8 & 0xFF) / 255.0f;
        float blue2 = (float)(color & 0xFF) / 255.0f;
        if (paramXStart > paramXEnd) {
            z = paramXStart;
            paramXStart = paramXEnd;
            paramXEnd = z;
        }
        if (paramYStart > paramYEnd) {
            z = paramYStart;
            paramYStart = paramYEnd;
            paramYEnd = z;
        }
        double xTL = paramXStart + rTL;
        double yTL = paramYStart + rTL;
        double xTR = paramXEnd - rTR;
        double yTR = paramYStart + rTR;
        double xBR = paramXEnd - rBR;
        double yBR = paramYEnd - rBR;
        double xBL = paramXStart + rBL;
        double yBL = paramYEnd - rBL;
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glLineWidth((float)1.0f);
        GL11.glColor4f((float)red2, (float)green2, (float)blue2, (float)alpha);
        GL11.glBegin((int)9);
        double degree = Math.PI / 180;
        for (i = 0.0; i <= 90.0; i += 0.25) {
            GL11.glVertex2d((double)(xBR + Math.sin(i * degree) * (double)rBR), (double)(yBR + Math.cos(i * degree) * (double)rBR));
        }
        for (i = 90.0; i <= 180.0; i += 0.25) {
            GL11.glVertex2d((double)(xTR + Math.sin(i * degree) * (double)rTR), (double)(yTR + Math.cos(i * degree) * (double)rTR));
        }
        for (i = 180.0; i <= 270.0; i += 0.25) {
            GL11.glVertex2d((double)(xTL + Math.sin(i * degree) * (double)rTL), (double)(yTL + Math.cos(i * degree) * (double)rTL));
        }
        for (i = 270.0; i <= 360.0; i += 0.25) {
            GL11.glVertex2d((double)(xBL + Math.sin(i * degree) * (double)rBL), (double)(yBL + Math.cos(i * degree) * (double)rBL));
        }
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
        GL11.glPopMatrix();
    }

    public static void drawRoundedCornerRect(float x, float y, float x1, float y1, float radius) {
        GL11.glBegin((int)9);
        float xRadius = (float)Math.min((double)(x1 - x) * 0.5, (double)radius);
        float yRadius = (float)Math.min((double)(y1 - y) * 0.5, (double)radius);
        RenderUtils.quickPolygonCircle(x + xRadius, y + yRadius, xRadius, yRadius, 180, 270);
        RenderUtils.quickPolygonCircle(x1 - xRadius, y + yRadius, xRadius, yRadius, 90, 180);
        RenderUtils.quickPolygonCircle(x1 - xRadius, y1 - yRadius, xRadius, yRadius, 0, 90);
        RenderUtils.quickPolygonCircle(x + xRadius, y1 - yRadius, xRadius, yRadius, 270, 360);
        GL11.glEnd();
    }

    public static void renderItemIcon(int x, int y, ItemStack itemStack) {
        if (itemStack != null && itemStack.func_77973_b() != null) {
            GlStateManager.func_179094_E();
            GlStateManager.func_179091_B();
            GlStateManager.func_179147_l();
            GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
            RenderHelper.func_74520_c();
            mc.func_175599_af().func_175042_a(itemStack, x, y);
            GlStateManager.func_179101_C();
            GlStateManager.func_179084_k();
            RenderHelper.func_74518_a();
            GlStateManager.func_179121_F();
        }
    }

    public static void customRoundedinf(float paramXStart, float paramYStart, float paramXEnd, float paramYEnd, float rTL, float rTR, float rBR, float rBL, int color) {
        double i;
        float alpha = (float)(color >> 24 & 0xFF) / 255.0f;
        float red2 = (float)(color >> 16 & 0xFF) / 255.0f;
        float green2 = (float)(color >> 8 & 0xFF) / 255.0f;
        float blue2 = (float)(color & 0xFF) / 255.0f;
        float z = 0.0f;
        if (paramXStart > paramXEnd) {
            z = paramXStart;
            paramXStart = paramXEnd;
            paramXEnd = z;
        }
        if (paramYStart > paramYEnd) {
            z = paramYStart;
            paramYStart = paramYEnd;
            paramYEnd = z;
        }
        double xTL = paramXStart + rTL;
        double yTL = paramYStart + rTL;
        double xTR = paramXEnd - rTR;
        double yTR = paramYStart + rTR;
        double xBR = paramXEnd - rBR;
        double yBR = paramYEnd - rBR;
        double xBL = paramXStart + rBL;
        double yBL = paramYEnd - rBL;
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glLineWidth((float)1.0f);
        GL11.glColor4f((float)red2, (float)green2, (float)blue2, (float)alpha);
        GL11.glBegin((int)9);
        double degree = Math.PI / 180;
        if (rBR <= 0.0f) {
            GL11.glVertex2d((double)xBR, (double)yBR);
        } else {
            for (i = 0.0; i <= 90.0; i += 1.0) {
                GL11.glVertex2d((double)(xBR + Math.sin(i * degree) * (double)rBR), (double)(yBR + Math.cos(i * degree) * (double)rBR));
            }
        }
        if (rTR <= 0.0f) {
            GL11.glVertex2d((double)xTR, (double)yTR);
        } else {
            for (i = 90.0; i <= 180.0; i += 1.0) {
                GL11.glVertex2d((double)(xTR + Math.sin(i * degree) * (double)rTR), (double)(yTR + Math.cos(i * degree) * (double)rTR));
            }
        }
        if (rTL <= 0.0f) {
            GL11.glVertex2d((double)xTL, (double)yTL);
        } else {
            for (i = 180.0; i <= 270.0; i += 1.0) {
                GL11.glVertex2d((double)(xTL + Math.sin(i * degree) * (double)rTL), (double)(yTL + Math.cos(i * degree) * (double)rTL));
            }
        }
        if (rBL <= 0.0f) {
            GL11.glVertex2d((double)xBL, (double)yBL);
        } else {
            for (i = 270.0; i <= 360.0; i += 1.0) {
                GL11.glVertex2d((double)(xBL + Math.sin(i * degree) * (double)rBL), (double)(yBL + Math.cos(i * degree) * (double)rBL));
            }
        }
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
        GL11.glPopMatrix();
    }

    public static void drawRoundedRect2(float x, float y, float x2, float y2, float round, int color) {
        RenderUtils.drawRect((float)((int)(x += (float)((double)(round / 2.0f) + 0.5))), (float)((int)(y += (float)((double)(round / 2.0f) + 0.5))), (float)((int)(x2 -= (float)((double)(round / 2.0f) + 0.5))), (float)((int)(y2 -= (float)((double)(round / 2.0f) + 0.5))), color);
        RenderUtils.circle(x2 - round / 2.0f, y + round / 2.0f, round, color);
        RenderUtils.circle(x + round / 2.0f, y2 - round / 2.0f, round, color);
        RenderUtils.circle(x + round / 2.0f, y + round / 2.0f, round, color);
        RenderUtils.circle(x2 - round / 2.0f, y2 - round / 2.0f, round, color);
        RenderUtils.drawRect((float)((int)(x - round / 2.0f - 0.5f)), (float)((int)(y + round / 2.0f)), (float)((int)x2), (float)((int)(y2 - round / 2.0f)), color);
        RenderUtils.drawRect((float)((int)x), (float)((int)(y + round / 2.0f)), (float)((int)(x2 + round / 2.0f + 0.5f)), (float)((int)(y2 - round / 2.0f)), color);
        RenderUtils.drawRect((float)((int)(x + round / 2.0f)), (float)((int)(y - round / 2.0f - 0.5f)), (float)((int)(x2 - round / 2.0f)), (float)((int)(y2 - round / 2.0f)), color);
        RenderUtils.drawRect((float)((int)(x + round / 2.0f)), (float)((int)y), (float)((int)(x2 - round / 2.0f)), (float)((int)(y2 + round / 2.0f + 0.5f)), color);
    }

    public static void setupRender(boolean start) {
        if (start) {
            GlStateManager.func_179147_l();
            GL11.glEnable((int)2848);
            GlStateManager.func_179097_i();
            GlStateManager.func_179090_x();
            GlStateManager.func_179112_b((int)770, (int)771);
            GL11.glHint((int)3154, (int)4354);
        } else {
            GlStateManager.func_179084_k();
            GlStateManager.func_179098_w();
            GL11.glDisable((int)2848);
            GlStateManager.func_179126_j();
        }
        GlStateManager.func_179132_a((!start ? 1 : 0) != 0);
    }

    public static void drawSuperCircle(float x, float y, float radius, int color) {
        int i;
        float alpha = (float)(color >> 24 & 0xFF) / 255.0f;
        float red2 = (float)(color >> 16 & 0xFF) / 255.0f;
        float green2 = (float)(color >> 8 & 0xFF) / 255.0f;
        float blue2 = (float)(color & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.func_178181_a();
        tessellator.func_178180_c();
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
        GlStateManager.func_179131_c((float)red2, (float)green2, (float)blue2, (float)alpha);
        if (alpha > 0.5f) {
            GL11.glEnable((int)2881);
            GL11.glEnable((int)2848);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glBegin((int)3);
            for (i = 0; i <= 180; ++i) {
                GL11.glVertex2d((double)((double)x + Math.sin((double)i * 3.141526 / 180.0) * (double)radius), (double)((double)y + Math.cos((double)i * 3.141526 / 180.0) * (double)radius));
                GL11.glVertex2d((double)((double)x + Math.sin((double)i * 3.141526 / 180.0) * (double)radius), (double)((double)y + Math.cos((double)i * 3.141526 / 180.0) * (double)radius));
            }
            GL11.glEnd();
            GL11.glDisable((int)2848);
            GL11.glDisable((int)2881);
        }
        GL11.glBegin((int)6);
        for (i = 0; i <= 180; ++i) {
            GL11.glVertex2d((double)((double)x + Math.sin((double)i * 3.141526 / 180.0) * (double)radius), (double)((double)y + Math.cos((double)i * 3.141526 / 180.0) * (double)radius));
        }
        GL11.glEnd();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
    }

    public static void circle(float x, float y, float radius, int fill) {
        RenderUtils.arc(x, y, 0.0f, 360.0f, radius, fill);
    }

    public static void circle(float x, float y, float radius, Color fill) {
        RenderUtils.arc(x, y, 0.0f, 360.0f, radius, fill);
    }

    public static void arc(float x, float y, float start, float end, float radius, int color) {
        RenderUtils.arcEllipse(x, y, start, end, radius, radius, color);
    }

    public static void arc(float x, float y, float start, float end, float radius, Color color) {
        RenderUtils.arcEllipse(x, y, start, end, radius, radius, color);
    }

    public static void arcEllipse(float x, float y, float start, float end, float w, float h, int color) {
        float ldy;
        float ldx;
        float i;
        GlStateManager.func_179124_c((float)0.0f, (float)0.0f, (float)0.0f);
        GL11.glColor4f((float)0.0f, (float)0.0f, (float)0.0f, (float)0.0f);
        if (start > end) {
            float temp = end;
            end = start;
            start = temp;
        }
        float alpha = (float)(color >> 24 & 0xFF) / 255.0f;
        float red2 = (float)(color >> 16 & 0xFF) / 255.0f;
        float green2 = (float)(color >> 8 & 0xFF) / 255.0f;
        float blue2 = (float)(color & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.func_178181_a();
        tessellator.func_178180_c();
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
        GlStateManager.func_179131_c((float)red2, (float)green2, (float)blue2, (float)alpha);
        if (alpha > 0.5f) {
            GL11.glEnable((int)2881);
            GL11.glEnable((int)2848);
            GL11.glLineWidth((float)2.0f);
            GL11.glBegin((int)3);
            for (i = end; i >= start; i -= 4.0f) {
                ldx = (float)Math.cos((double)i * Math.PI / 180.0) * w * 1.001f;
                ldy = (float)Math.sin((double)i * Math.PI / 180.0) * h * 1.001f;
                GL11.glVertex2f((float)(x + ldx), (float)(y + ldy));
            }
            GL11.glEnd();
            GL11.glDisable((int)2848);
            GL11.glDisable((int)2881);
        }
        GL11.glBegin((int)6);
        for (i = end; i >= start; i -= 4.0f) {
            ldx = (float)Math.cos((double)i * Math.PI / 180.0) * w;
            ldy = (float)Math.sin((double)i * Math.PI / 180.0) * h;
            GL11.glVertex2f((float)(x + ldx), (float)(y + ldy));
        }
        GL11.glEnd();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
    }

    public static void arcEllipse(float x, float y, float start, float end, float w, float h, Color color) {
        float ldy;
        float ldx;
        float i;
        GlStateManager.func_179124_c((float)0.0f, (float)0.0f, (float)0.0f);
        GL11.glColor4f((float)0.0f, (float)0.0f, (float)0.0f, (float)0.0f);
        float temp = 0.0f;
        if (start > end) {
            temp = end;
            end = start;
            start = temp;
        }
        Tessellator var9 = Tessellator.func_178181_a();
        var9.func_178180_c();
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
        GlStateManager.func_179131_c((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
        if ((float)color.getAlpha() > 0.5f) {
            GL11.glEnable((int)2848);
            GL11.glLineWidth((float)2.0f);
            GL11.glBegin((int)3);
            for (i = end; i >= start; i -= 4.0f) {
                ldx = (float)Math.cos((double)i * Math.PI / 180.0) * w * 1.001f;
                ldy = (float)Math.sin((double)i * Math.PI / 180.0) * h * 1.001f;
                GL11.glVertex2f((float)(x + ldx), (float)(y + ldy));
            }
            GL11.glEnd();
            GL11.glDisable((int)2848);
        }
        GL11.glBegin((int)6);
        for (i = end; i >= start; i -= 4.0f) {
            ldx = (float)Math.cos((double)i * Math.PI / 180.0) * w;
            ldy = (float)Math.sin((double)i * Math.PI / 180.0) * h;
            GL11.glVertex2f((float)(x + ldx), (float)(y + ldy));
        }
        GL11.glEnd();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
    }

    public static void fastRoundedRect(float paramXStart, float paramYStart, float paramXEnd, float paramYEnd, float radius) {
        double i;
        float z = 0.0f;
        if (paramXStart > paramXEnd) {
            z = paramXStart;
            paramXStart = paramXEnd;
            paramXEnd = z;
        }
        if (paramYStart > paramYEnd) {
            z = paramYStart;
            paramYStart = paramYEnd;
            paramYEnd = z;
        }
        double x1 = paramXStart + radius;
        double y1 = paramYStart + radius;
        double x2 = paramXEnd - radius;
        double y2 = paramYEnd - radius;
        GL11.glEnable((int)2848);
        GL11.glLineWidth((float)1.0f);
        GL11.glBegin((int)9);
        double degree = Math.PI / 180;
        for (i = 0.0; i <= 90.0; i += 1.0) {
            GL11.glVertex2d((double)(x2 + Math.sin(i * degree) * (double)radius), (double)(y2 + Math.cos(i * degree) * (double)radius));
        }
        for (i = 90.0; i <= 180.0; i += 1.0) {
            GL11.glVertex2d((double)(x2 + Math.sin(i * degree) * (double)radius), (double)(y1 + Math.cos(i * degree) * (double)radius));
        }
        for (i = 180.0; i <= 270.0; i += 1.0) {
            GL11.glVertex2d((double)(x1 + Math.sin(i * degree) * (double)radius), (double)(y1 + Math.cos(i * degree) * (double)radius));
        }
        for (i = 270.0; i <= 360.0; i += 1.0) {
            GL11.glVertex2d((double)(x1 + Math.sin(i * degree) * (double)radius), (double)(y2 + Math.cos(i * degree) * (double)radius));
        }
        GL11.glEnd();
        GL11.glDisable((int)2848);
    }

    public static double interpolate(double current, double old, double scale) {
        return old + (current - old) * scale;
    }

    public static boolean isInViewFrustrum(Entity entity) {
        return RenderUtils.isInViewFrustrum(entity.func_174813_aQ()) || entity.field_70158_ak;
    }

    private static boolean isInViewFrustrum(AxisAlignedBB bb) {
        Entity current = mc.func_175606_aa();
        frustrum.func_78547_a(current.field_70165_t, current.field_70163_u, current.field_70161_v);
        return frustrum.func_78546_a(bb);
    }

    public static void drawFilledCircleNoGL(int x, int y, double r, int c, int quality) {
        float f = (float)(c >> 24 & 0xFF) / 255.0f;
        float f1 = (float)(c >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(c >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(c & 0xFF) / 255.0f;
        GL11.glColor4f((float)f1, (float)f2, (float)f3, (float)f);
        GL11.glBegin((int)6);
        for (int i = 0; i <= 360 / quality; ++i) {
            double x2 = Math.sin((double)(i * quality) * Math.PI / 180.0) * r;
            double y2 = Math.cos((double)(i * quality) * Math.PI / 180.0) * r;
            GL11.glVertex2d((double)((double)x + x2), (double)((double)y + y2));
        }
        GL11.glEnd();
    }

    public static Framebuffer createFrameBuffer(Framebuffer framebuffer) {
        if (framebuffer == null || framebuffer.field_147621_c != RenderUtils.mc.field_71443_c || framebuffer.field_147618_d != RenderUtils.mc.field_71440_d) {
            if (framebuffer != null) {
                framebuffer.func_147608_a();
            }
            return new Framebuffer(RenderUtils.mc.field_71443_c, RenderUtils.mc.field_71440_d, true);
        }
        return framebuffer;
    }

    public static void bindTexture(int texture) {
        GL11.glBindTexture((int)3553, (int)texture);
    }

    public static void drawEntityBox(Entity entity, Color color, boolean outline) {
        RenderManager renderManager = mc.func_175598_ae();
        Timer timer = RenderUtils.mc.field_71428_T;
        GL11.glBlendFunc((int)770, (int)771);
        RenderUtils.enableGlCap(3042);
        RenderUtils.disableGlCap(3553, 2929);
        GL11.glDepthMask((boolean)false);
        double x = entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * (double)timer.field_74281_c - renderManager.field_78725_b;
        double y = entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * (double)timer.field_74281_c - renderManager.field_78726_c;
        double z = entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * (double)timer.field_74281_c - renderManager.field_78723_d;
        AxisAlignedBB entityBox = entity.func_174813_aQ();
        AxisAlignedBB axisAlignedBB = new AxisAlignedBB(entityBox.field_72340_a - entity.field_70165_t + x - 0.05, entityBox.field_72338_b - entity.field_70163_u + y, entityBox.field_72339_c - entity.field_70161_v + z - 0.05, entityBox.field_72336_d - entity.field_70165_t + x + 0.05, entityBox.field_72337_e - entity.field_70163_u + y + 0.15, entityBox.field_72334_f - entity.field_70161_v + z + 0.05);
        if (outline) {
            GL11.glLineWidth((float)1.0f);
            RenderUtils.enableGlCap(2848);
            RenderUtils.glColor(color.getRed(), color.getGreen(), color.getBlue(), 95);
            RenderUtils.drawSelectionBoundingBox(axisAlignedBB);
        }
        RenderUtils.glColor(color.getRed(), color.getGreen(), color.getBlue(), outline ? 26 : 35);
        RenderUtils.drawFilledBox(axisAlignedBB);
        GlStateManager.func_179117_G();
        GL11.glDepthMask((boolean)true);
        RenderUtils.resetCaps();
    }

    public static void drawFilledCircle(double x, double y, double r, int c, int id) {
        float f = (float)(c >> 24 & 0xFF) / 255.0f;
        float f1 = (float)(c >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(c >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(c & 0xFF) / 255.0f;
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glColor4f((float)f1, (float)f2, (float)f3, (float)f);
        GL11.glBegin((int)9);
        if (id == 1) {
            GL11.glVertex2d((double)x, (double)y);
            for (int i = 0; i <= 90; ++i) {
                double x2 = Math.sin((double)i * 3.141526 / 180.0) * r;
                double y2 = Math.cos((double)i * 3.141526 / 180.0) * r;
                GL11.glVertex2d((double)(x - x2), (double)(y - y2));
            }
        } else if (id == 2) {
            GL11.glVertex2d((double)x, (double)y);
            for (int i = 90; i <= 180; ++i) {
                double x2 = Math.sin((double)i * 3.141526 / 180.0) * r;
                double y2 = Math.cos((double)i * 3.141526 / 180.0) * r;
                GL11.glVertex2d((double)(x - x2), (double)(y - y2));
            }
        } else if (id == 3) {
            GL11.glVertex2d((double)x, (double)y);
            for (int i = 270; i <= 360; ++i) {
                double x2 = Math.sin((double)i * 3.141526 / 180.0) * r;
                double y2 = Math.cos((double)i * 3.141526 / 180.0) * r;
                GL11.glVertex2d((double)(x - x2), (double)(y - y2));
            }
        } else if (id == 4) {
            GL11.glVertex2d((double)x, (double)y);
            for (int i = 180; i <= 270; ++i) {
                double x2 = Math.sin((double)i * 3.141526 / 180.0) * r;
                double y2 = Math.cos((double)i * 3.141526 / 180.0) * r;
                GL11.glVertex2d((double)(x - x2), (double)(y - y2));
            }
        } else {
            for (int i = 0; i <= 360; ++i) {
                double x2 = Math.sin((double)i * 3.141526 / 180.0) * r;
                double y2 = Math.cos((double)i * 3.141526 / 180.0) * r;
                GL11.glVertex2f((float)((float)(x - x2)), (float)((float)(y - y2)));
            }
        }
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
    }

    public static void drawGradientSidewaysH(double left, double top, double right, double bottom, int col1, int col2) {
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glShadeModel((int)7425);
        RenderUtils.quickDrawGradientSidewaysH(left, top, right, bottom, col1, col2);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
        GL11.glShadeModel((int)7424);
    }

    public static void quickDrawGradientSidewaysH(double left, double top, double right, double bottom, int col1, int col2) {
        GL11.glBegin((int)7);
        RenderUtils.glColor(col1);
        GL11.glVertex2d((double)left, (double)top);
        GL11.glVertex2d((double)left, (double)bottom);
        RenderUtils.glColor(col2);
        GL11.glVertex2d((double)right, (double)bottom);
        GL11.glVertex2d((double)right, (double)top);
        GL11.glEnd();
    }

    public static void drawGradientSidewaysV(double left, double top, double right, double bottom, int col1, int col2) {
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glShadeModel((int)7425);
        RenderUtils.quickDrawGradientSidewaysV(left, top, right, bottom, col1, col2);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
        GL11.glShadeModel((int)7424);
    }

    public static void quickDrawGradientSidewaysV(double left, double top, double right, double bottom, int col1, int col2) {
        GL11.glBegin((int)7);
        RenderUtils.glColor(col1);
        GL11.glVertex2d((double)right, (double)top);
        GL11.glVertex2d((double)left, (double)top);
        RenderUtils.glColor(col2);
        GL11.glVertex2d((double)left, (double)bottom);
        GL11.glVertex2d((double)right, (double)bottom);
        GL11.glEnd();
    }

    public static void drawBlockBox(BlockPos blockPos, Color color, boolean outline, boolean box, float outlineWidth2, float animation) {
        RenderManager renderManager = mc.func_175598_ae();
        Timer timer = RenderUtils.mc.field_71428_T;
        double x = (double)blockPos.func_177958_n() - renderManager.field_78725_b;
        double y = (double)blockPos.func_177956_o() - renderManager.field_78726_c;
        double z = (double)blockPos.func_177952_p() - renderManager.field_78723_d;
        double adjustedHeight = animation;
        AxisAlignedBB axisAlignedBB = new AxisAlignedBB(x, y, z, x + 1.0, y + adjustedHeight, z + 1.0);
        Block block = BlockUtils.getBlock(blockPos);
        if (block != null) {
            EntityPlayerSP player = RenderUtils.mc.field_71439_g;
            double posX = player.field_70142_S + (player.field_70165_t - player.field_70142_S) * (double)timer.field_74281_c;
            double posY = player.field_70137_T + (player.field_70163_u - player.field_70137_T) * (double)timer.field_74281_c;
            double posZ = player.field_70136_U + (player.field_70161_v - player.field_70136_U) * (double)timer.field_74281_c;
            axisAlignedBB = block.func_180646_a((World)RenderUtils.mc.field_71441_e, blockPos).func_72314_b((double)0.002f, (double)0.002f, (double)0.002f).func_72317_d(-posX, -posY, -posZ);
            axisAlignedBB = new AxisAlignedBB(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c, axisAlignedBB.field_72336_d, Math.min(axisAlignedBB.field_72337_e, axisAlignedBB.field_72338_b + adjustedHeight), axisAlignedBB.field_72334_f);
        }
        GL11.glBlendFunc((int)770, (int)771);
        RenderUtils.enableGlCap(3042);
        RenderUtils.disableGlCap(3553, 2929);
        GL11.glDepthMask((boolean)false);
        if (box) {
            RenderUtils.glColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha() != 255 ? color.getAlpha() : (outline ? 26 : 35));
            RenderUtils.drawFilledBox(axisAlignedBB);
        }
        if (outline) {
            GL11.glLineWidth((float)outlineWidth2);
            RenderUtils.enableGlCap(2848);
            RenderUtils.glColor(color);
            RenderUtils.drawSelectionBoundingBox(axisAlignedBB);
        }
        GlStateManager.func_179117_G();
        GL11.glDepthMask((boolean)true);
        RenderUtils.resetCaps();
    }

    public static void drawBlockBoxGradient(BlockPos blockPos, Color color1, Color color2, boolean outline, boolean box, float outlineWidth2, float animation) {
        RenderManager renderManager = mc.func_175598_ae();
        Timer timer = RenderUtils.mc.field_71428_T;
        double x = (double)blockPos.func_177958_n() - renderManager.field_78725_b;
        double y = (double)blockPos.func_177956_o() - renderManager.field_78726_c;
        double z = (double)blockPos.func_177952_p() - renderManager.field_78723_d;
        double adjustedHeight = animation;
        AxisAlignedBB axisAlignedBB = new AxisAlignedBB(x, y, z, x + 1.0, y + adjustedHeight, z + 1.0);
        Block block = BlockUtils.getBlock(blockPos);
        if (block != null) {
            EntityPlayerSP player = RenderUtils.mc.field_71439_g;
            double posX = player.field_70142_S + (player.field_70165_t - player.field_70142_S) * (double)timer.field_74281_c;
            double posY = player.field_70137_T + (player.field_70163_u - player.field_70137_T) * (double)timer.field_74281_c;
            double posZ = player.field_70136_U + (player.field_70161_v - player.field_70136_U) * (double)timer.field_74281_c;
            axisAlignedBB = block.func_180646_a((World)RenderUtils.mc.field_71441_e, blockPos).func_72314_b((double)0.002f, (double)0.002f, (double)0.002f).func_72317_d(-posX, -posY, -posZ);
            axisAlignedBB = new AxisAlignedBB(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c, axisAlignedBB.field_72336_d, Math.min(axisAlignedBB.field_72337_e, axisAlignedBB.field_72338_b + adjustedHeight), axisAlignedBB.field_72334_f);
        }
        GL11.glBlendFunc((int)770, (int)771);
        RenderUtils.enableGlCap(3042);
        RenderUtils.disableGlCap(3553, 2929);
        GL11.glDepthMask((boolean)false);
        if (box) {
            GL11.glShadeModel((int)7425);
            GlStateManager.func_179147_l();
            GlStateManager.func_179112_b((int)770, (int)771);
            GlStateManager.func_179090_x();
            GlStateManager.func_179129_p();
            Tessellator tessellator = Tessellator.func_178181_a();
            WorldRenderer buffer = tessellator.func_178180_c();
            buffer.func_181668_a(7, DefaultVertexFormats.field_181706_f);
            float r1 = (float)color1.getRed() / 255.0f;
            float g1 = (float)color1.getGreen() / 255.0f;
            float b1 = (float)color1.getBlue() / 255.0f;
            float a1 = (float)color1.getAlpha() / 255.0f;
            float r2 = (float)color2.getRed() / 255.0f;
            float g2 = (float)color2.getGreen() / 255.0f;
            float b2 = (float)color2.getBlue() / 255.0f;
            float a2 = (float)color2.getAlpha() / 255.0f;
            double minX = axisAlignedBB.field_72340_a;
            double minY = axisAlignedBB.field_72338_b;
            double minZ = axisAlignedBB.field_72339_c;
            double maxX = axisAlignedBB.field_72336_d;
            double maxY = axisAlignedBB.field_72337_e;
            double maxZ = axisAlignedBB.field_72334_f;
            buffer.func_181662_b(minX, maxY, minZ).func_181666_a(r1, g1, b1, a1).func_181675_d();
            buffer.func_181662_b(maxX, maxY, minZ).func_181666_a(r2, g2, b2, a2).func_181675_d();
            buffer.func_181662_b(maxX, maxY, maxZ).func_181666_a(r1, g1, b1, a1).func_181675_d();
            buffer.func_181662_b(minX, maxY, maxZ).func_181666_a(r2, g2, b2, a2).func_181675_d();
            buffer.func_181662_b(minX, minY, minZ).func_181666_a(r2, g2, b2, a2).func_181675_d();
            buffer.func_181662_b(maxX, minY, minZ).func_181666_a(r1, g1, b1, a1).func_181675_d();
            buffer.func_181662_b(maxX, minY, maxZ).func_181666_a(r2, g2, b2, a2).func_181675_d();
            buffer.func_181662_b(minX, minY, maxZ).func_181666_a(r1, g1, b1, a1).func_181675_d();
            buffer.func_181662_b(minX, minY, maxZ).func_181666_a(r1, g1, b1, a1).func_181675_d();
            buffer.func_181662_b(maxX, minY, maxZ).func_181666_a(r2, g2, b2, a2).func_181675_d();
            buffer.func_181662_b(maxX, maxY, maxZ).func_181666_a(r1, g1, b1, a1).func_181675_d();
            buffer.func_181662_b(minX, maxY, maxZ).func_181666_a(r2, g2, b2, a2).func_181675_d();
            buffer.func_181662_b(minX, minY, minZ).func_181666_a(r2, g2, b2, a2).func_181675_d();
            buffer.func_181662_b(maxX, minY, minZ).func_181666_a(r1, g1, b1, a1).func_181675_d();
            buffer.func_181662_b(maxX, maxY, minZ).func_181666_a(r2, g2, b2, a2).func_181675_d();
            buffer.func_181662_b(minX, maxY, minZ).func_181666_a(r1, g1, b1, a1).func_181675_d();
            buffer.func_181662_b(minX, minY, minZ).func_181666_a(r1, g1, b1, a1).func_181675_d();
            buffer.func_181662_b(minX, minY, maxZ).func_181666_a(r2, g2, b2, a2).func_181675_d();
            buffer.func_181662_b(minX, maxY, maxZ).func_181666_a(r1, g1, b1, a1).func_181675_d();
            buffer.func_181662_b(minX, maxY, minZ).func_181666_a(r2, g2, b2, a2).func_181675_d();
            buffer.func_181662_b(maxX, minY, minZ).func_181666_a(r2, g2, b2, a2).func_181675_d();
            buffer.func_181662_b(maxX, minY, maxZ).func_181666_a(r1, g1, b1, a1).func_181675_d();
            buffer.func_181662_b(maxX, maxY, maxZ).func_181666_a(r2, g2, b2, a2).func_181675_d();
            buffer.func_181662_b(maxX, maxY, minZ).func_181666_a(r1, g1, b1, a1).func_181675_d();
            tessellator.func_78381_a();
            GL11.glShadeModel((int)7424);
            GlStateManager.func_179098_w();
            GlStateManager.func_179089_o();
        }
        if (outline) {
            GL11.glLineWidth((float)outlineWidth2);
            RenderUtils.enableGlCap(2848);
            GL11.glShadeModel((int)7425);
            float r1 = (float)color1.getRed() / 255.0f;
            float g1 = (float)color1.getGreen() / 255.0f;
            float b1 = (float)color1.getBlue() / 255.0f;
            float a1 = (float)color1.getAlpha() / 255.0f;
            float r2 = (float)color2.getRed() / 255.0f;
            float g2 = (float)color2.getGreen() / 255.0f;
            float b2 = (float)color2.getBlue() / 255.0f;
            float a2 = (float)color2.getAlpha() / 255.0f;
            double minX = axisAlignedBB.field_72340_a;
            double minY = axisAlignedBB.field_72338_b;
            double minZ = axisAlignedBB.field_72339_c;
            double maxX = axisAlignedBB.field_72336_d;
            double maxY = axisAlignedBB.field_72337_e;
            double maxZ = axisAlignedBB.field_72334_f;
            GL11.glBegin((int)1);
            RenderUtils.drawGradientLine(minX, minY, minZ, maxX, minY, minZ, r1, g1, b1, a1, r2, g2, b2, a2);
            RenderUtils.drawGradientLine(maxX, minY, minZ, maxX, minY, maxZ, r2, g2, b2, a2, r1, g1, b1, a1);
            RenderUtils.drawGradientLine(maxX, minY, maxZ, minX, minY, maxZ, r1, g1, b1, a1, r2, g2, b2, a2);
            RenderUtils.drawGradientLine(minX, minY, maxZ, minX, minY, minZ, r2, g2, b2, a2, r1, g1, b1, a1);
            RenderUtils.drawGradientLine(minX, maxY, minZ, maxX, maxY, minZ, r1, g1, b1, a1, r2, g2, b2, a2);
            RenderUtils.drawGradientLine(maxX, maxY, minZ, maxX, maxY, maxZ, r2, g2, b2, a2, r1, g1, b1, a1);
            RenderUtils.drawGradientLine(maxX, maxY, maxZ, minX, maxY, maxZ, r1, g1, b1, a1, r2, g2, b2, a2);
            RenderUtils.drawGradientLine(minX, maxY, maxZ, minX, maxY, minZ, r2, g2, b2, a2, r1, g1, b1, a1);
            RenderUtils.drawGradientLine(minX, minY, minZ, minX, maxY, minZ, r1, g1, b1, a1, r2, g2, b2, a2);
            RenderUtils.drawGradientLine(maxX, minY, minZ, maxX, maxY, minZ, r2, g2, b2, a2, r1, g1, b1, a1);
            RenderUtils.drawGradientLine(maxX, minY, maxZ, maxX, maxY, maxZ, r1, g1, b1, a1, r2, g2, b2, a2);
            RenderUtils.drawGradientLine(minX, minY, maxZ, minX, maxY, maxZ, r2, g2, b2, a2, r1, g1, b1, a1);
            GL11.glEnd();
            GL11.glShadeModel((int)7424);
        }
        GlStateManager.func_179117_G();
        GL11.glDepthMask((boolean)true);
        RenderUtils.resetCaps();
    }

    private static void drawGradientLine(double x1, double y1, double z1, double x2, double y2, double z2, float r1, float g1, float b1, float a1, float r2, float g2, float b2, float a2) {
        GL11.glColor4f((float)r1, (float)g1, (float)b1, (float)a1);
        GL11.glVertex3d((double)x1, (double)y1, (double)z1);
        GL11.glColor4f((float)r2, (float)g2, (float)b2, (float)a2);
        GL11.glVertex3d((double)x2, (double)y2, (double)z2);
    }

    private static void drawBox(AxisAlignedBB box, Color color) {
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179129_p();
        GlStateManager.func_179112_b((int)770, (int)771);
        GlStateManager.func_179103_j((int)7425);
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer buffer = tessellator.func_178180_c();
        float r = (float)color.getRed() / 255.0f;
        float g = (float)color.getGreen() / 255.0f;
        float b = (float)color.getBlue() / 255.0f;
        float a = (float)color.getAlpha() / 255.0f;
        buffer.func_181668_a(7, DefaultVertexFormats.field_181706_f);
        double minX = box.field_72340_a;
        double minY = box.field_72338_b;
        double minZ = box.field_72339_c;
        double maxX = box.field_72336_d;
        double maxY = box.field_72337_e;
        double maxZ = box.field_72334_f;
        buffer.func_181662_b(minX, maxY, minZ).func_181666_a(r, g, b, a).func_181675_d();
        buffer.func_181662_b(maxX, maxY, minZ).func_181666_a(r, g, b, a).func_181675_d();
        buffer.func_181662_b(maxX, maxY, maxZ).func_181666_a(r, g, b, a).func_181675_d();
        buffer.func_181662_b(minX, maxY, maxZ).func_181666_a(r, g, b, a).func_181675_d();
        buffer.func_181662_b(minX, minY, minZ).func_181666_a(r, g, b, a).func_181675_d();
        buffer.func_181662_b(maxX, minY, minZ).func_181666_a(r, g, b, a).func_181675_d();
        buffer.func_181662_b(maxX, minY, maxZ).func_181666_a(r, g, b, a).func_181675_d();
        buffer.func_181662_b(minX, minY, maxZ).func_181666_a(r, g, b, a).func_181675_d();
        buffer.func_181662_b(minX, minY, maxZ).func_181666_a(r, g, b, a).func_181675_d();
        buffer.func_181662_b(maxX, minY, maxZ).func_181666_a(r, g, b, a).func_181675_d();
        buffer.func_181662_b(maxX, maxY, maxZ).func_181666_a(r, g, b, a).func_181675_d();
        buffer.func_181662_b(minX, maxY, maxZ).func_181666_a(r, g, b, a).func_181675_d();
        buffer.func_181662_b(minX, minY, minZ).func_181666_a(r, g, b, a).func_181675_d();
        buffer.func_181662_b(maxX, minY, minZ).func_181666_a(r, g, b, a).func_181675_d();
        buffer.func_181662_b(maxX, maxY, minZ).func_181666_a(r, g, b, a).func_181675_d();
        buffer.func_181662_b(minX, maxY, minZ).func_181666_a(r, g, b, a).func_181675_d();
        buffer.func_181662_b(minX, minY, minZ).func_181666_a(r, g, b, a).func_181675_d();
        buffer.func_181662_b(minX, minY, maxZ).func_181666_a(r, g, b, a).func_181675_d();
        buffer.func_181662_b(minX, maxY, maxZ).func_181666_a(r, g, b, a).func_181675_d();
        buffer.func_181662_b(minX, maxY, minZ).func_181666_a(r, g, b, a).func_181675_d();
        buffer.func_181662_b(maxX, minY, minZ).func_181666_a(r, g, b, a).func_181675_d();
        buffer.func_181662_b(maxX, minY, maxZ).func_181666_a(r, g, b, a).func_181675_d();
        buffer.func_181662_b(maxX, maxY, maxZ).func_181666_a(r, g, b, a).func_181675_d();
        buffer.func_181662_b(maxX, maxY, minZ).func_181666_a(r, g, b, a).func_181675_d();
        tessellator.func_78381_a();
        GlStateManager.func_179089_o();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
        GlStateManager.func_179103_j((int)7424);
    }

    public static void drawSelectionBoundingBox(AxisAlignedBB boundingBox) {
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldrenderer = tessellator.func_178180_c();
        worldrenderer.func_181668_a(3, DefaultVertexFormats.field_181705_e);
        worldrenderer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181675_d();
        worldrenderer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72334_f).func_181675_d();
        worldrenderer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72334_f).func_181675_d();
        worldrenderer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181675_d();
        worldrenderer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181675_d();
        worldrenderer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181675_d();
        worldrenderer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72334_f).func_181675_d();
        worldrenderer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72337_e, boundingBox.field_72334_f).func_181675_d();
        worldrenderer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181675_d();
        worldrenderer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181675_d();
        worldrenderer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72334_f).func_181675_d();
        worldrenderer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72334_f).func_181675_d();
        worldrenderer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72334_f).func_181675_d();
        worldrenderer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72337_e, boundingBox.field_72334_f).func_181675_d();
        worldrenderer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181675_d();
        worldrenderer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181675_d();
        tessellator.func_78381_a();
    }

    public static void drawEntityBox(Entity entity, Color color, boolean outline, boolean box, float outlineWidth2) {
        RenderManager renderManager = mc.func_175598_ae();
        Timer timer = RenderUtils.mc.field_71428_T;
        GL11.glBlendFunc((int)770, (int)771);
        RenderUtils.enableGlCap(3042);
        RenderUtils.disableGlCap(3553, 2929);
        GL11.glDepthMask((boolean)false);
        double x = entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * (double)timer.field_74281_c - renderManager.field_78725_b;
        double y = entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * (double)timer.field_74281_c - renderManager.field_78726_c;
        double z = entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * (double)timer.field_74281_c - renderManager.field_78723_d;
        AxisAlignedBB entityBox = entity.func_174813_aQ();
        AxisAlignedBB axisAlignedBB = new AxisAlignedBB(entityBox.field_72340_a - entity.field_70165_t + x - 0.05, entityBox.field_72338_b - entity.field_70163_u + y, entityBox.field_72339_c - entity.field_70161_v + z - 0.05, entityBox.field_72336_d - entity.field_70165_t + x + 0.05, entityBox.field_72337_e - entity.field_70163_u + y + 0.15, entityBox.field_72334_f - entity.field_70161_v + z + 0.05);
        GlStateManager.func_179094_E();
        GlStateManager.func_179137_b((double)x, (double)y, (double)z);
        GlStateManager.func_179114_b((float)(-(entity.field_70126_B + (entity.field_70177_z - entity.field_70126_B) * timer.field_74281_c)), (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.func_179137_b((double)(-x), (double)(-y), (double)(-z));
        if (outline) {
            GL11.glLineWidth((float)outlineWidth2);
            RenderUtils.enableGlCap(2848);
            RenderUtils.glColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
            RenderUtils.drawSelectionBoundingBox(axisAlignedBB);
        }
        if (box) {
            RenderUtils.glColor(color.getRed(), color.getGreen(), color.getBlue(), outline ? 26 : 50);
            RenderUtils.drawFilledBox(axisAlignedBB);
        }
        GlStateManager.func_179121_F();
        GlStateManager.func_179117_G();
        GL11.glDepthMask((boolean)true);
        RenderUtils.resetCaps();
    }

    public static void drawAxisAlignedBB(AxisAlignedBB axisAlignedBB, Color color, boolean outline, boolean box, float outlineWidth2) {
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3042);
        GL11.glLineWidth((float)outlineWidth2);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        RenderUtils.glColor(color);
        if (outline) {
            GL11.glLineWidth((float)outlineWidth2);
            RenderUtils.enableGlCap(2848);
            RenderUtils.glColor(color.getRed(), color.getGreen(), color.getBlue(), 255);
            RenderUtils.drawSelectionBoundingBox(axisAlignedBB);
        }
        if (box) {
            RenderUtils.glColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
            RenderUtils.drawFilledBox(axisAlignedBB);
        }
        GlStateManager.func_179117_G();
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
    }

    public static void drawPlatform(double y, Color color, double size) {
        RenderManager renderManager = mc.func_175598_ae();
        double renderY = y - renderManager.field_78726_c;
        RenderUtils.drawAxisAlignedBB(new AxisAlignedBB(size, renderY + 0.02, size, -size, renderY, -size), color, false, true, 2.0f);
    }

    public static void drawPlatform(Entity entity, Color color) {
        RenderManager renderManager = mc.func_175598_ae();
        Timer timer = RenderUtils.mc.field_71428_T;
        double x = entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * (double)timer.field_74281_c - renderManager.field_78725_b;
        double y = entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * (double)timer.field_74281_c - renderManager.field_78726_c;
        double z = entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * (double)timer.field_74281_c - renderManager.field_78723_d;
        AxisAlignedBB axisAlignedBB = entity.func_174813_aQ().func_72317_d(-entity.field_70165_t, -entity.field_70163_u, -entity.field_70161_v).func_72317_d(x, y, z);
        RenderUtils.drawAxisAlignedBB(new AxisAlignedBB(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e + 0.2, axisAlignedBB.field_72339_c, axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e + 0.26, axisAlignedBB.field_72334_f), color, false, true, 2.0f);
    }

    public static void renderBox(AxisAlignedBB axisAlignedBB, Color c) {
        GlStateManager.func_179094_E();
        GlStateManager.func_179132_a((boolean)false);
        GlStateManager.func_179090_x();
        GlStateManager.func_179140_f();
        GlStateManager.func_179129_p();
        GlStateManager.func_179084_k();
        GlStateManager.func_179097_i();
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldRenderer = tessellator.func_178180_c();
        GlStateManager.func_179147_l();
        GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
        worldRenderer.func_181668_a(7, DefaultVertexFormats.field_181706_f);
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        tessellator.func_78381_a();
        GlStateManager.func_179126_j();
        GlStateManager.func_179132_a((boolean)true);
        GlStateManager.func_179098_w();
        GlStateManager.func_179145_e();
        GlStateManager.func_179089_o();
        GlStateManager.func_179147_l();
        GlStateManager.func_179117_G();
        GlStateManager.func_179121_F();
    }

    public static void drawFilledBox(AxisAlignedBB axisAlignedBB) {
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldRenderer = tessellator.func_178180_c();
        worldRenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72340_a, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72337_e, axisAlignedBB.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(axisAlignedBB.field_72336_d, axisAlignedBB.field_72338_b, axisAlignedBB.field_72334_f).func_181675_d();
        tessellator.func_78381_a();
    }

    public static void renderOutlines(double x, double y, double z, float width, float height, float heightScale, Color c, float outlineWidth2) {
        float halfWidth = width / 2.0f;
        float adjustedHeight = height * heightScale;
        double bottomY = y;
        double topY = bottomY + (double)adjustedHeight;
        GlStateManager.func_179094_E();
        GlStateManager.func_179132_a((boolean)false);
        GlStateManager.func_179090_x();
        GlStateManager.func_179140_f();
        GlStateManager.func_179129_p();
        GlStateManager.func_179084_k();
        GlStateManager.func_179097_i();
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldRenderer = tessellator.func_178180_c();
        worldRenderer.func_181668_a(1, DefaultVertexFormats.field_181706_f);
        GL11.glLineWidth((float)outlineWidth2);
        worldRenderer.func_181662_b(x - (double)halfWidth, bottomY, z - (double)halfWidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
        worldRenderer.func_181662_b(x - (double)halfWidth, topY, z - (double)halfWidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
        worldRenderer.func_181662_b(x + (double)halfWidth, bottomY, z + (double)halfWidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
        worldRenderer.func_181662_b(x + (double)halfWidth, topY, z + (double)halfWidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
        worldRenderer.func_181662_b(x + (double)halfWidth, bottomY, z - (double)halfWidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
        worldRenderer.func_181662_b(x + (double)halfWidth, topY, z - (double)halfWidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
        worldRenderer.func_181662_b(x - (double)halfWidth, bottomY, z + (double)halfWidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
        worldRenderer.func_181662_b(x - (double)halfWidth, topY, z + (double)halfWidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
        worldRenderer.func_181662_b(x - (double)halfWidth, bottomY, z - (double)halfWidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
        worldRenderer.func_181662_b(x - (double)halfWidth, bottomY, z + (double)halfWidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
        worldRenderer.func_181662_b(x - (double)halfWidth, bottomY, z - (double)halfWidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
        worldRenderer.func_181662_b(x + (double)halfWidth, bottomY, z - (double)halfWidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
        worldRenderer.func_181662_b(x + (double)halfWidth, bottomY, z - (double)halfWidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
        worldRenderer.func_181662_b(x + (double)halfWidth, bottomY, z + (double)halfWidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
        worldRenderer.func_181662_b(x - (double)halfWidth, bottomY, z + (double)halfWidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
        worldRenderer.func_181662_b(x + (double)halfWidth, bottomY, z + (double)halfWidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
        worldRenderer.func_181662_b(x - (double)halfWidth, topY, z - (double)halfWidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
        worldRenderer.func_181662_b(x - (double)halfWidth, topY, z + (double)halfWidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
        worldRenderer.func_181662_b(x - (double)halfWidth, topY, z - (double)halfWidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
        worldRenderer.func_181662_b(x + (double)halfWidth, topY, z - (double)halfWidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
        worldRenderer.func_181662_b(x + (double)halfWidth, topY, z - (double)halfWidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
        worldRenderer.func_181662_b(x + (double)halfWidth, topY, z + (double)halfWidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
        worldRenderer.func_181662_b(x - (double)halfWidth, topY, z + (double)halfWidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
        worldRenderer.func_181662_b(x + (double)halfWidth, topY, z + (double)halfWidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
        tessellator.func_78381_a();
        GlStateManager.func_179126_j();
        GlStateManager.func_179132_a((boolean)true);
        GlStateManager.func_179098_w();
        GlStateManager.func_179145_e();
        GlStateManager.func_179089_o();
        GlStateManager.func_179147_l();
        GlStateManager.func_179121_F();
    }

    public static void renderBox(double x, double y, double z, float width, float height, float animation, Color c) {
        float baseHeight;
        float halfwidth = width / 2.0f;
        float adjustedHeight = baseHeight = height * animation;
        GlStateManager.func_179094_E();
        GlStateManager.func_179132_a((boolean)false);
        GlStateManager.func_179090_x();
        GlStateManager.func_179140_f();
        GlStateManager.func_179129_p();
        GlStateManager.func_179084_k();
        GlStateManager.func_179097_i();
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldRenderer = tessellator.func_178180_c();
        GlStateManager.func_179147_l();
        GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
        worldRenderer.func_181668_a(7, DefaultVertexFormats.field_181706_f);
        worldRenderer.func_181662_b(x - (double)halfwidth, y, z + (double)halfwidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
        worldRenderer.func_181662_b(x - (double)halfwidth, y + (double)adjustedHeight, z + (double)halfwidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
        worldRenderer.func_181662_b(x + (double)halfwidth, y + (double)adjustedHeight, z + (double)halfwidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
        worldRenderer.func_181662_b(x + (double)halfwidth, y, z + (double)halfwidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
        worldRenderer.func_181662_b(x - (double)halfwidth, y, z - (double)halfwidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
        worldRenderer.func_181662_b(x - (double)halfwidth, y + (double)adjustedHeight, z - (double)halfwidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
        worldRenderer.func_181662_b(x + (double)halfwidth, y + (double)adjustedHeight, z - (double)halfwidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
        worldRenderer.func_181662_b(x + (double)halfwidth, y, z - (double)halfwidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
        worldRenderer.func_181662_b(x - (double)halfwidth, y, z - (double)halfwidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
        worldRenderer.func_181662_b(x - (double)halfwidth, y + (double)adjustedHeight, z - (double)halfwidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
        worldRenderer.func_181662_b(x - (double)halfwidth, y + (double)adjustedHeight, z + (double)halfwidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
        worldRenderer.func_181662_b(x - (double)halfwidth, y, z + (double)halfwidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
        worldRenderer.func_181662_b(x + (double)halfwidth, y, z - (double)halfwidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
        worldRenderer.func_181662_b(x + (double)halfwidth, y + (double)adjustedHeight, z - (double)halfwidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
        worldRenderer.func_181662_b(x + (double)halfwidth, y + (double)adjustedHeight, z + (double)halfwidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
        worldRenderer.func_181662_b(x + (double)halfwidth, y, z + (double)halfwidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
        worldRenderer.func_181662_b(x - (double)halfwidth, y + (double)adjustedHeight, z - (double)halfwidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
        worldRenderer.func_181662_b(x + (double)halfwidth, y + (double)adjustedHeight, z - (double)halfwidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
        worldRenderer.func_181662_b(x + (double)halfwidth, y + (double)adjustedHeight, z + (double)halfwidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
        worldRenderer.func_181662_b(x - (double)halfwidth, y + (double)adjustedHeight, z + (double)halfwidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
        worldRenderer.func_181662_b(x - (double)halfwidth, y, z - (double)halfwidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
        worldRenderer.func_181662_b(x + (double)halfwidth, y, z - (double)halfwidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
        worldRenderer.func_181662_b(x + (double)halfwidth, y, z + (double)halfwidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
        worldRenderer.func_181662_b(x - (double)halfwidth, y, z + (double)halfwidth).func_181669_b(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).func_181675_d();
        tessellator.func_78381_a();
        GlStateManager.func_179126_j();
        GlStateManager.func_179132_a((boolean)true);
        GlStateManager.func_179098_w();
        GlStateManager.func_179145_e();
        GlStateManager.func_179089_o();
        GlStateManager.func_179147_l();
        GlStateManager.func_179117_G();
        GlStateManager.func_179121_F();
    }

    public static void drawBlockBox(BlockPos blockPos, Color color, boolean outline) {
        RenderManager renderManager = mc.func_175598_ae();
        Timer timer = RenderUtils.mc.field_71428_T;
        double x = (double)blockPos.func_177958_n() - renderManager.field_78725_b;
        double y = (double)blockPos.func_177956_o() - renderManager.field_78726_c;
        double z = (double)blockPos.func_177952_p() - renderManager.field_78723_d;
        AxisAlignedBB axisAlignedBB = new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0);
        Block block = BlockUtils.getBlock(blockPos);
        if (block != null) {
            EntityPlayerSP player = RenderUtils.mc.field_71439_g;
            double posX = player.field_70142_S + (player.field_70165_t - player.field_70142_S) * (double)timer.field_74281_c;
            double posY = player.field_70137_T + (player.field_70163_u - player.field_70137_T) * (double)timer.field_74281_c;
            double posZ = player.field_70136_U + (player.field_70161_v - player.field_70136_U) * (double)timer.field_74281_c;
            axisAlignedBB = block.func_180646_a((World)RenderUtils.mc.field_71441_e, blockPos).func_72314_b((double)0.002f, (double)0.002f, (double)0.002f).func_72317_d(-posX, -posY, -posZ);
        }
        GL11.glBlendFunc((int)770, (int)771);
        RenderUtils.enableGlCap(3042);
        RenderUtils.disableGlCap(3553, 2929);
        GL11.glDepthMask((boolean)false);
        RenderUtils.glColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha() != 255 ? color.getAlpha() : (outline ? 26 : 35));
        RenderUtils.drawFilledBox(axisAlignedBB);
        if (outline) {
            GL11.glLineWidth((float)1.0f);
            RenderUtils.enableGlCap(2848);
            RenderUtils.glColor(color);
            RenderUtils.drawSelectionBoundingBox(axisAlignedBB);
        }
        GlStateManager.func_179117_G();
        GL11.glDepthMask((boolean)true);
        RenderUtils.resetCaps();
    }

    public static void drawBlockBox(BlockPos blockPos, BlockPos blockPos2, Color color, boolean outline) {
        RenderManager renderManager = mc.func_175598_ae();
        Timer timer = RenderUtils.mc.field_71428_T;
        double x = (double)blockPos.func_177958_n() - renderManager.field_78725_b;
        double y = (double)blockPos.func_177956_o() - renderManager.field_78726_c;
        double z = (double)blockPos.func_177952_p() - renderManager.field_78723_d;
        AxisAlignedBB axisAlignedBB = new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0);
        Block block = BlockUtils.getBlock(blockPos);
        if (block != null) {
            EntityPlayerSP player = RenderUtils.mc.field_71439_g;
            double posX = player.field_70142_S + (player.field_70165_t - player.field_70142_S) * (double)timer.field_74281_c;
            double posY = player.field_70137_T + (player.field_70163_u - player.field_70137_T) * (double)timer.field_74281_c;
            double posZ = player.field_70136_U + (player.field_70161_v - player.field_70136_U) * (double)timer.field_74281_c;
            AxisAlignedBB axisAlignedBB2 = block.func_180646_a((World)RenderUtils.mc.field_71441_e, blockPos2).func_72314_b((double)0.002f, (double)0.002f, (double)0.002f).func_72317_d(-posX, -posY, -posZ);
            axisAlignedBB = block.func_180646_a((World)RenderUtils.mc.field_71441_e, blockPos).func_72314_b((double)0.002f, (double)0.002f, (double)0.002f).func_72317_d(-posX, -posY, -posZ).func_111270_a(axisAlignedBB2);
        }
        GL11.glBlendFunc((int)770, (int)771);
        RenderUtils.enableGlCap(3042);
        RenderUtils.disableGlCap(3553, 2929);
        GL11.glDepthMask((boolean)false);
        RenderUtils.glColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha() != 255 ? color.getAlpha() : (outline ? 26 : 35));
        RenderUtils.drawFilledBox(axisAlignedBB);
        if (outline) {
            GL11.glLineWidth((float)1.0f);
            RenderUtils.enableGlCap(2848);
            RenderUtils.glColor(color);
            RenderUtils.drawSelectionBoundingBox(axisAlignedBB);
        }
        GlStateManager.func_179117_G();
        GL11.glDepthMask((boolean)true);
        RenderUtils.resetCaps();
    }

    public static void quickDrawRect(float x, float y, float x2, float y2) {
        GL11.glBegin((int)7);
        GL11.glVertex2d((double)x2, (double)y);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glVertex2d((double)x, (double)y2);
        GL11.glVertex2d((double)x2, (double)y2);
        GL11.glEnd();
    }

    public static void quickDrawRect(float x, float y, float x2, float y2, int color) {
        RenderUtils.glColor(color);
        RenderUtils.quickDrawRect(x, y, x2, y2);
    }

    public static void quickDrawRect(float x, float y, float x2, float y2, Color color) {
        RenderUtils.quickDrawRect(x, y, x2, y2, color.getRGB());
    }

    public static void drawRect(float left, float top, float right, float bottom, int color) {
        if (left < right) {
            float i = left;
            left = right;
            right = i;
        }
        if (top < bottom) {
            float j = top;
            top = bottom;
            bottom = j;
        }
        float f3 = (float)(color >> 24 & 0xFF) / 255.0f;
        float f = (float)(color >> 16 & 0xFF) / 255.0f;
        float f1 = (float)(color >> 8 & 0xFF) / 255.0f;
        float f2 = (float)(color & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldrenderer = tessellator.func_178180_c();
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
        GlStateManager.func_179131_c((float)f, (float)f1, (float)f2, (float)f3);
        worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
        worldrenderer.func_181662_b((double)left, (double)bottom, 0.0).func_181675_d();
        worldrenderer.func_181662_b((double)right, (double)bottom, 0.0).func_181675_d();
        worldrenderer.func_181662_b((double)right, (double)top, 0.0).func_181675_d();
        worldrenderer.func_181662_b((double)left, (double)top, 0.0).func_181675_d();
        tessellator.func_78381_a();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
    }

    public static void drawRect(double x, double y, double x2, double y2, int color) {
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        RenderUtils.glColor(color);
        GL11.glBegin((int)7);
        GL11.glVertex2d((double)x2, (double)y);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glVertex2d((double)x, (double)y2);
        GL11.glVertex2d((double)x2, (double)y2);
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
    }

    public static void drawRect(Rectangle rect, int color) {
        RenderUtils.drawRect(rect.getX(), rect.getY(), rect.getX() + rect.getWidth(), rect.getY() + rect.getHeight(), color);
    }

    public static void drawRect(float x, float y, float x2, float y2, Color color) {
        RenderUtils.drawRect(x, y, x2, y2, color.getRGB());
    }

    public static void drawBorderedRect(double x, double y, double x2, double y2, double width, int color1, int color2) {
        RenderUtils.drawBorderedRect((float)x, (float)y, (float)x2, (float)y2, (float)width, color1, color2);
    }

    public static void drawBorderedRect(float x, float y, float x2, float y2, float width, int color1, int color2) {
        RenderUtils.drawRect(x, y, x2, y2, color2);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        RenderUtils.glColor(color1);
        GL11.glLineWidth((float)width);
        GL11.glBegin((int)1);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glVertex2d((double)x, (double)y2);
        GL11.glVertex2d((double)x2, (double)y2);
        GL11.glVertex2d((double)x2, (double)y);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glVertex2d((double)x2, (double)y);
        GL11.glVertex2d((double)x, (double)y2);
        GL11.glVertex2d((double)x2, (double)y2);
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
    }

    public static void newDrawRect(float left, float top, float right, float bottom, int color) {
        if (left < right) {
            float i = left;
            left = right;
            right = i;
        }
        if (top < bottom) {
            float j = top;
            top = bottom;
            bottom = j;
        }
        float f3 = (float)(color >> 24 & 0xFF) / 255.0f;
        float f = (float)(color >> 16 & 0xFF) / 255.0f;
        float f1 = (float)(color >> 8 & 0xFF) / 255.0f;
        float f2 = (float)(color & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldrenderer = tessellator.func_178180_c();
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
        GlStateManager.func_179131_c((float)f, (float)f1, (float)f2, (float)f3);
        worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
        worldrenderer.func_181662_b((double)left, (double)bottom, 0.0).func_181675_d();
        worldrenderer.func_181662_b((double)right, (double)bottom, 0.0).func_181675_d();
        worldrenderer.func_181662_b((double)right, (double)top, 0.0).func_181675_d();
        worldrenderer.func_181662_b((double)left, (double)top, 0.0).func_181675_d();
        tessellator.func_78381_a();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
    }

    public static void newDrawRect(double left, double top, double right, double bottom, int color) {
        if (left < right) {
            double i = left;
            left = right;
            right = i;
        }
        if (top < bottom) {
            double j = top;
            top = bottom;
            bottom = j;
        }
        float f3 = (float)(color >> 24 & 0xFF) / 255.0f;
        float f = (float)(color >> 16 & 0xFF) / 255.0f;
        float f1 = (float)(color >> 8 & 0xFF) / 255.0f;
        float f2 = (float)(color & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldrenderer = tessellator.func_178180_c();
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
        GlStateManager.func_179131_c((float)f, (float)f1, (float)f2, (float)f3);
        worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
        worldrenderer.func_181662_b(left, bottom, 0.0).func_181675_d();
        worldrenderer.func_181662_b(right, bottom, 0.0).func_181675_d();
        worldrenderer.func_181662_b(right, top, 0.0).func_181675_d();
        worldrenderer.func_181662_b(left, top, 0.0).func_181675_d();
        tessellator.func_78381_a();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
    }

    public static void drawRoundedRect(float paramXStart, float paramYStart, float paramXEnd, float paramYEnd, float radius, int color, boolean popPush) {
        double i;
        float alpha = (float)(color >> 24 & 0xFF) / 255.0f;
        float red2 = (float)(color >> 16 & 0xFF) / 255.0f;
        float green2 = (float)(color >> 8 & 0xFF) / 255.0f;
        float blue2 = (float)(color & 0xFF) / 255.0f;
        float z = 0.0f;
        if (paramXStart > paramXEnd) {
            z = paramXStart;
            paramXStart = paramXEnd;
            paramXEnd = z;
        }
        if (paramYStart > paramYEnd) {
            z = paramYStart;
            paramYStart = paramYEnd;
            paramYEnd = z;
        }
        double x1 = paramXStart + radius;
        double y1 = paramYStart + radius;
        double x2 = paramXEnd - radius;
        double y2 = paramYEnd - radius;
        if (popPush) {
            GL11.glPushMatrix();
        }
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glLineWidth((float)1.0f);
        GL11.glColor4f((float)red2, (float)green2, (float)blue2, (float)alpha);
        GL11.glBegin((int)9);
        double degree = Math.PI / 180;
        for (i = 0.0; i <= 90.0; i += 1.0) {
            GL11.glVertex2d((double)(x2 + Math.sin(i * degree) * (double)radius), (double)(y2 + Math.cos(i * degree) * (double)radius));
        }
        for (i = 90.0; i <= 180.0; i += 1.0) {
            GL11.glVertex2d((double)(x2 + Math.sin(i * degree) * (double)radius), (double)(y1 + Math.cos(i * degree) * (double)radius));
        }
        for (i = 180.0; i <= 270.0; i += 1.0) {
            GL11.glVertex2d((double)(x1 + Math.sin(i * degree) * (double)radius), (double)(y1 + Math.cos(i * degree) * (double)radius));
        }
        for (i = 270.0; i <= 360.0; i += 1.0) {
            GL11.glVertex2d((double)(x1 + Math.sin(i * degree) * (double)radius), (double)(y2 + Math.cos(i * degree) * (double)radius));
        }
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
        if (popPush) {
            GL11.glPopMatrix();
        }
    }

    public static void drawRoundedRect(float paramXStart, float paramYStart, float paramXEnd, float paramYEnd, float radius, int color, boolean popPush, boolean roundTopLeft, boolean roundTopRight, boolean roundBottomLeft, boolean roundBottomRight) {
        double i;
        float alpha = (float)(color >> 24 & 0xFF) / 255.0f;
        float red2 = (float)(color >> 16 & 0xFF) / 255.0f;
        float green2 = (float)(color >> 8 & 0xFF) / 255.0f;
        float blue2 = (float)(color & 0xFF) / 255.0f;
        float z = 0.0f;
        if (paramXStart > paramXEnd) {
            z = paramXStart;
            paramXStart = paramXEnd;
            paramXEnd = z;
        }
        if (paramYStart > paramYEnd) {
            z = paramYStart;
            paramYStart = paramYEnd;
            paramYEnd = z;
        }
        double x1 = paramXStart;
        double y1 = paramYStart;
        double x2 = paramXEnd;
        double y2 = paramYEnd;
        if (popPush) {
            GL11.glPushMatrix();
        }
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glLineWidth((float)1.0f);
        GL11.glColor4f((float)red2, (float)green2, (float)blue2, (float)alpha);
        GL11.glBegin((int)9);
        double degree = Math.PI / 180;
        if (roundTopLeft) {
            for (i = 0.0; i <= 90.0; i += 1.0) {
                GL11.glVertex2d((double)(x1 + Math.sin(i * degree) * (double)radius), (double)(y1 + Math.cos(i * degree) * (double)radius));
            }
        } else {
            GL11.glVertex2d((double)x1, (double)y1);
        }
        if (roundTopRight) {
            for (i = 90.0; i <= 180.0; i += 1.0) {
                GL11.glVertex2d((double)(x2 + Math.sin(i * degree) * (double)radius), (double)(y1 + Math.cos(i * degree) * (double)radius));
            }
        } else {
            GL11.glVertex2d((double)x2, (double)y1);
        }
        if (roundBottomRight) {
            for (i = 180.0; i <= 270.0; i += 1.0) {
                GL11.glVertex2d((double)(x2 + Math.sin(i * degree) * (double)radius), (double)(y2 + Math.cos(i * degree) * (double)radius));
            }
        } else {
            GL11.glVertex2d((double)x2, (double)y2);
        }
        if (roundBottomLeft) {
            for (i = 270.0; i <= 360.0; i += 1.0) {
                GL11.glVertex2d((double)(x1 + Math.sin(i * degree) * (double)radius), (double)(y2 + Math.cos(i * degree) * (double)radius));
            }
        } else {
            GL11.glVertex2d((double)x1, (double)y2);
        }
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
        if (popPush) {
            GL11.glPopMatrix();
        }
    }

    public static void drawLoadingCircle(float x, float y) {
        for (int i = 0; i < 4; ++i) {
            int rot = (int)(System.nanoTime() / 5000000L * (long)i % 360L);
            RenderUtils.drawCircle(x, y, (float)(i * 10), rot - 180, rot, Color.WHITE);
        }
    }

    public static void drawCircle(float x, float y, float radius, int start, int end, Color color) {
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
        RenderUtils.glColor(color.getRGB());
        GL11.glEnable((int)2848);
        GL11.glLineWidth((float)2.0f);
        GL11.glBegin((int)3);
        for (float i = (float)end; i >= (float)start; i -= 4.0f) {
            GL11.glVertex2f((float)((float)((double)x + Math.cos((double)i * Math.PI / 180.0) * (double)(radius * 1.001f))), (float)((float)((double)y + Math.sin((double)i * Math.PI / 180.0) * (double)(radius * 1.001f))));
        }
        GL11.glEnd();
        GL11.glDisable((int)2848);
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
        GlStateManager.func_179117_G();
    }

    public static void drawLimitedCircle(float lx, float ly, float x2, float y2, int xx, int yy, float radius, Color color) {
        int sections = 50;
        double dAngle = Math.PI * 2 / (double)sections;
        GL11.glPushAttrib((int)8192);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glBegin((int)6);
        RenderUtils.glColor(color);
        for (int i = 0; i < sections; ++i) {
            float x = (float)((double)radius * Math.sin((double)i * dAngle));
            float y = (float)((double)radius * Math.cos((double)i * dAngle));
            GL11.glVertex2f((float)Math.min(x2, Math.max((float)xx + x, lx)), (float)Math.min(y2, Math.max((float)yy + y, ly)));
        }
        GlStateManager.func_179124_c((float)0.0f, (float)0.0f, (float)0.0f);
        GL11.glEnd();
        GL11.glPopAttrib();
    }

    public static void drawImage(ResourceLocation image, int x, int y, int width, int height) {
        GL11.glDisable((int)2929);
        GL11.glEnable((int)3042);
        GL11.glDepthMask((boolean)false);
        OpenGlHelper.func_148821_a((int)770, (int)771, (int)1, (int)0);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        mc.func_110434_K().func_110577_a(image);
        Gui.func_146110_a((int)x, (int)y, (float)0.0f, (float)0.0f, (int)width, (int)height, (float)width, (float)height);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)2929);
    }

    public static void drawSquareTriangle(float cx, float cy, float dirX, float dirY, Color color, boolean filled) {
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldrenderer = tessellator.func_178180_c();
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
        GlStateManager.func_179117_G();
        RenderUtils.glColor(color);
        worldrenderer.func_181668_a(filled ? 5 : 2, DefaultVertexFormats.field_181705_e);
        worldrenderer.func_181662_b((double)(cx + dirX), (double)cy, 0.0).func_181675_d();
        worldrenderer.func_181662_b((double)cx, (double)cy, 0.0).func_181675_d();
        worldrenderer.func_181662_b((double)cx, (double)(cy + dirY), 0.0).func_181675_d();
        worldrenderer.func_181662_b((double)(cx + dirX), (double)cy, 0.0).func_181675_d();
        tessellator.func_78381_a();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    public static void drawImage(BufferedImage image, int x, int y, int width, int height) {
        GL11.glDisable((int)2929);
        GL11.glEnable((int)3042);
        GL11.glDepthMask((boolean)false);
        OpenGlHelper.func_148821_a((int)770, (int)771, (int)1, (int)0);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        ByteBuffer buffer = ByteBuffer.allocateDirect(4 * image.getWidth() * image.getHeight());
        int[] pixels = new int[image.getWidth() * image.getHeight()];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
        for (int i = 0; i < pixels.length; ++i) {
            int pixel = pixels[i];
            buffer.put((byte)(pixel >> 16 & 0xFF));
            buffer.put((byte)(pixel >> 8 & 0xFF));
            buffer.put((byte)(pixel & 0xFF));
            buffer.put((byte)(pixel >> 24 & 0xFF));
        }
        buffer.flip();
        int textureId = GL11.glGenTextures();
        GL11.glBindTexture((int)3553, (int)textureId);
        GL11.glTexParameteri((int)3553, (int)10241, (int)9729);
        GL11.glTexParameteri((int)3553, (int)10240, (int)9729);
        GL11.glTexImage2D((int)3553, (int)0, (int)32856, (int)image.getWidth(), (int)image.getHeight(), (int)0, (int)6408, (int)5121, (ByteBuffer)buffer);
        GL11.glEnable((int)3553);
        GL11.glBindTexture((int)3553, (int)textureId);
        GL11.glBegin((int)7);
        GL11.glTexCoord2f((float)0.0f, (float)0.0f);
        GL11.glVertex2i((int)x, (int)y);
        GL11.glTexCoord2f((float)0.0f, (float)1.0f);
        GL11.glVertex2i((int)x, (int)(y + height));
        GL11.glTexCoord2f((float)1.0f, (float)1.0f);
        GL11.glVertex2i((int)(x + width), (int)(y + height));
        GL11.glTexCoord2f((float)1.0f, (float)0.0f);
        GL11.glVertex2i((int)(x + width), (int)y);
        GL11.glEnd();
        GL11.glDisable((int)3553);
        GL11.glDeleteTextures((int)textureId);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)2929);
    }

    public static void drawImage(ResourceLocation image, int x, int y, int width, int height, float alpha) {
        GL11.glDisable((int)2929);
        GL11.glEnable((int)3042);
        GL11.glDepthMask((boolean)false);
        OpenGlHelper.func_148821_a((int)770, (int)771, (int)1, (int)0);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)alpha);
        mc.func_110434_K().func_110577_a(image);
        Gui.func_146110_a((int)x, (int)y, (float)0.0f, (float)0.0f, (int)width, (int)height, (float)width, (float)height);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)2929);
    }

    public static void drawImage2(ResourceLocation image, float x, float y, int width, int height) {
        GL11.glDisable((int)2929);
        GL11.glEnable((int)3042);
        GL11.glDepthMask((boolean)false);
        OpenGlHelper.func_148821_a((int)770, (int)771, (int)1, (int)0);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glTranslatef((float)x, (float)y, (float)x);
        mc.func_110434_K().func_110577_a(image);
        Gui.func_146110_a((int)0, (int)0, (float)0.0f, (float)0.0f, (int)width, (int)height, (float)width, (float)height);
        GL11.glTranslatef((float)(-x), (float)(-y), (float)(-x));
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)2929);
    }

    public static void drawImage3(ResourceLocation image, float x, float y, int width, int height, float r, float g, float b, float al) {
        GL11.glDisable((int)2929);
        GL11.glEnable((int)3042);
        GL11.glDepthMask((boolean)false);
        OpenGlHelper.func_148821_a((int)770, (int)771, (int)1, (int)0);
        GL11.glColor4f((float)r, (float)g, (float)b, (float)al);
        GL11.glTranslatef((float)x, (float)y, (float)x);
        mc.func_110434_K().func_110577_a(image);
        Gui.func_146110_a((int)0, (int)0, (float)0.0f, (float)0.0f, (int)width, (int)height, (float)width, (float)height);
        GL11.glTranslatef((float)(-x), (float)(-y), (float)(-x));
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)2929);
    }

    public static void drawRectBasedBorder(float x, float y, float x2, float y2, float width, int color1) {
        RenderUtils.drawRect(x - width / 2.0f, y - width / 2.0f, x2 + width / 2.0f, y + width / 2.0f, color1);
        RenderUtils.drawRect(x - width / 2.0f, y + width / 2.0f, x + width / 2.0f, y2 + width / 2.0f, color1);
        RenderUtils.drawRect(x2 - width / 2.0f, y + width / 2.0f, x2 + width / 2.0f, y2 + width / 2.0f, color1);
        RenderUtils.drawRect(x + width / 2.0f, y2 - width / 2.0f, x2 - width / 2.0f, y2 + width / 2.0f, color1);
    }

    public static void drawRectBasedBorder(double x, double y, double x2, double y2, double width, int color1) {
        RenderUtils.newDrawRect(x - width / 2.0, y - width / 2.0, x2 + width / 2.0, y + width / 2.0, color1);
        RenderUtils.newDrawRect(x - width / 2.0, y + width / 2.0, x + width / 2.0, y2 + width / 2.0, color1);
        RenderUtils.newDrawRect(x2 - width / 2.0, y + width / 2.0, x2 + width / 2.0, y2 + width / 2.0, color1);
        RenderUtils.newDrawRect(x + width / 2.0, y2 - width / 2.0, x2 - width / 2.0, y2 + width / 2.0, color1);
    }

    public static void lineWidth(float width) {
        GL11.glLineWidth((float)width);
    }

    public static void begin(int glMode) {
        GL11.glBegin((int)glMode);
    }

    public static void vertex(double x, double y) {
        GL11.glVertex2d((double)x, (double)y);
    }

    public static void end() {
        GL11.glEnd();
    }

    public static void enable(int glTarget) {
        GL11.glEnable((int)glTarget);
    }

    public static void color(Color color) {
        if (color == null) {
            color = Color.white;
        }
        RenderUtils.color((float)color.getRed() / 255.0f, (float)color.getGreen() / 255.0f, (float)color.getBlue() / 255.0f, (float)color.getAlpha() / 255.0f);
    }

    public static void color(double red2, double green2, double blue2, double alpha) {
        GL11.glColor4d((double)red2, (double)green2, (double)blue2, (double)alpha);
    }

    public static void color(int color, float alpha) {
        float r = (float)(color >> 16 & 0xFF) / 255.0f;
        float g = (float)(color >> 8 & 0xFF) / 255.0f;
        float b = (float)(color & 0xFF) / 255.0f;
        GlStateManager.func_179131_c((float)r, (float)g, (float)b, (float)alpha);
    }

    public static void color(int color) {
        RenderUtils.color(color, (float)(color >> 24 & 0xFF) / 255.0f);
    }

    public static void disable(int glTarget) {
        GL11.glDisable((int)glTarget);
    }

    public static void lineNoGl(double firstX, double firstY, double secondX, double secondY, Color color) {
        Nobody.start();
        if (color != null) {
            RenderUtils.color(color);
        }
        RenderUtils.lineWidth(1.0f);
        GL11.glEnable((int)2848);
        RenderUtils.begin(1);
        RenderUtils.vertex(firstX, firstY);
        RenderUtils.vertex(secondX, secondY);
        RenderUtils.end();
        GL11.glDisable((int)2848);
        Nobody.stop();
    }

    public static void glColor(int red2, int green2, int blue2, int alpha) {
        GlStateManager.func_179131_c((float)((float)red2 / 255.0f), (float)((float)green2 / 255.0f), (float)((float)blue2 / 255.0f), (float)((float)alpha / 255.0f));
    }

    public static void glColor(Color color) {
        float red2 = (float)color.getRed() / 255.0f;
        float green2 = (float)color.getGreen() / 255.0f;
        float blue2 = (float)color.getBlue() / 255.0f;
        float alpha = (float)color.getAlpha() / 255.0f;
        GlStateManager.func_179131_c((float)red2, (float)green2, (float)blue2, (float)alpha);
    }

    public static void glColor(Color color, int alpha) {
        RenderUtils.glColor(color, (float)alpha / 255.0f);
    }

    public static void glColor(Color color, float alpha) {
        float red2 = (float)color.getRed() / 255.0f;
        float green2 = (float)color.getGreen() / 255.0f;
        float blue2 = (float)color.getBlue() / 255.0f;
        GlStateManager.func_179131_c((float)red2, (float)green2, (float)blue2, (float)alpha);
    }

    public static void glColor(int hex) {
        float alpha = (float)(hex >> 24 & 0xFF) / 255.0f;
        float red2 = (float)(hex >> 16 & 0xFF) / 255.0f;
        float green2 = (float)(hex >> 8 & 0xFF) / 255.0f;
        float blue2 = (float)(hex & 0xFF) / 255.0f;
        GlStateManager.func_179131_c((float)red2, (float)green2, (float)blue2, (float)alpha);
    }

    public static void glColor(int hex, int alpha) {
        float red2 = (float)(hex >> 16 & 0xFF) / 255.0f;
        float green2 = (float)(hex >> 8 & 0xFF) / 255.0f;
        float blue2 = (float)(hex & 0xFF) / 255.0f;
        GlStateManager.func_179131_c((float)red2, (float)green2, (float)blue2, (float)((float)alpha / 255.0f));
    }

    public static void glColor(int hex, float alpha) {
        float red2 = (float)(hex >> 16 & 0xFF) / 255.0f;
        float green2 = (float)(hex >> 8 & 0xFF) / 255.0f;
        float blue2 = (float)(hex & 0xFF) / 255.0f;
        GlStateManager.func_179131_c((float)red2, (float)green2, (float)blue2, (float)alpha);
    }

    public static void drawTriAngle(float cx, float cy, float r, float n, Color color, boolean polygon) {
        cx = (float)((double)cx * 2.0);
        cy = (float)((double)cy * 2.0);
        double b = 6.2831852 / (double)n;
        double p = Math.cos(b);
        double s = Math.sin(b);
        r = (float)((double)r * 2.0);
        double x = r;
        double y = 0.0;
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldrenderer = tessellator.func_178180_c();
        GL11.glLineWidth((float)1.0f);
        RenderUtils.enableGlCap(2848);
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
        GlStateManager.func_179117_G();
        RenderUtils.glColor(color);
        GlStateManager.func_179152_a((float)0.5f, (float)0.5f, (float)0.5f);
        worldrenderer.func_181668_a(polygon ? 9 : 2, DefaultVertexFormats.field_181705_e);
        int ii = 0;
        while ((float)ii < n) {
            worldrenderer.func_181662_b(x + (double)cx, y + (double)cy, 0.0).func_181675_d();
            double t = x;
            x = p * x - s * y;
            y = s * t + p * y;
            ++ii;
        }
        tessellator.func_78381_a();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
        GlStateManager.func_179152_a((float)2.0f, (float)2.0f, (float)2.0f);
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    public static void drawCircle(float x, float y, float radius, float lineWidth, int start, int end, Color color) {
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
        RenderUtils.glColor(color);
        GL11.glEnable((int)2848);
        GL11.glLineWidth((float)lineWidth);
        GL11.glBegin((int)3);
        for (float i = (float)end; i >= (float)start; i -= 4.0f) {
            GL11.glVertex2f((float)((float)((double)x + Math.cos((double)i * Math.PI / 180.0) * (double)(radius * 1.001f))), (float)((float)((double)y + Math.sin((double)i * Math.PI / 180.0) * (double)(radius * 1.001f))));
        }
        GL11.glEnd();
        GL11.glDisable((int)2848);
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
    }

    public static void drawCircle(float cx, float cy, float r, int segments, int color) {
        float red2 = (float)(color >> 16 & 0xFF) / 255.0f;
        float green2 = (float)(color >> 8 & 0xFF) / 255.0f;
        float blue2 = (float)(color & 0xFF) / 255.0f;
        float alpha = (float)(color >> 24 & 0xFF) / 255.0f;
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDisable((int)3553);
        GL11.glColor4f((float)red2, (float)green2, (float)blue2, (float)alpha);
        GL11.glBegin((int)6);
        GL11.glVertex2f((float)cx, (float)cy);
        for (int i = 0; i <= segments; ++i) {
            double angle = Math.PI * 2 * (double)i / (double)segments;
            float x = (float)((double)cx + (double)r * Math.cos(angle));
            float y = (float)((double)cy + (double)r * Math.sin(angle));
            GL11.glVertex2f((float)x, (float)y);
        }
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
    }

    public static void drawCircle(float x, float y, float radius, float lineWidth, int start, int end) {
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
        RenderUtils.glColor(Color.WHITE);
        GL11.glEnable((int)2848);
        GL11.glLineWidth((float)lineWidth);
        GL11.glBegin((int)3);
        for (float i = (float)end; i >= (float)start; i -= 4.0f) {
            GL11.glVertex2f((float)((float)((double)x + Math.cos((double)i * Math.PI / 180.0) * (double)(radius * 1.001f))), (float)((float)((double)y + Math.sin((double)i * Math.PI / 180.0) * (double)(radius * 1.001f))));
        }
        GL11.glEnd();
        GL11.glDisable((int)2848);
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
    }

    public static void drawArrow(double x, double y, int lineWidth, int color, double length) {
        RenderUtils.start2D();
        GL11.glPushMatrix();
        GL11.glLineWidth((float)lineWidth);
        RenderUtils.setColor(new Color(color));
        GL11.glBegin((int)3);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glVertex2d((double)(x + 3.0), (double)(y + length));
        GL11.glVertex2d((double)(x + 6.0), (double)y);
        GL11.glEnd();
        GL11.glPopMatrix();
        RenderUtils.stop2D();
    }

    public static void draw2D(EntityLivingBase entity, double posX, double posY, double posZ, int color, int backgroundColor) {
        GlStateManager.func_179094_E();
        GlStateManager.func_179137_b((double)posX, (double)posY, (double)posZ);
        GlStateManager.func_179114_b((float)(-RenderUtils.mc.func_175598_ae().field_78735_i), (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.func_179139_a((double)-0.1, (double)-0.1, (double)0.1);
        GL11.glDisable((int)2929);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GlStateManager.func_179132_a((boolean)true);
        RenderUtils.glColor(color);
        GL11.glCallList((int)DISPLAY_LISTS_2D[0]);
        RenderUtils.glColor(backgroundColor);
        GL11.glCallList((int)DISPLAY_LISTS_2D[1]);
        GlStateManager.func_179137_b((double)0.0, (double)(21.0 + -(entity.func_174813_aQ().field_72337_e - entity.func_174813_aQ().field_72338_b) * 12.0), (double)0.0);
        RenderUtils.glColor(color);
        GL11.glCallList((int)DISPLAY_LISTS_2D[2]);
        RenderUtils.glColor(backgroundColor);
        GL11.glCallList((int)DISPLAY_LISTS_2D[3]);
        GL11.glEnable((int)2929);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GlStateManager.func_179121_F();
    }

    public static void draw2D(BlockPos blockPos, int color, int backgroundColor) {
        RenderManager renderManager = mc.func_175598_ae();
        double posX = (double)blockPos.func_177958_n() + 0.5 - renderManager.field_78725_b;
        double posY = (double)blockPos.func_177956_o() - renderManager.field_78726_c;
        double posZ = (double)blockPos.func_177952_p() + 0.5 - renderManager.field_78723_d;
        GlStateManager.func_179094_E();
        GlStateManager.func_179137_b((double)posX, (double)posY, (double)posZ);
        GlStateManager.func_179114_b((float)(-RenderUtils.mc.func_175598_ae().field_78735_i), (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.func_179139_a((double)-0.1, (double)-0.1, (double)0.1);
        GL11.glDisable((int)2929);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GlStateManager.func_179132_a((boolean)true);
        RenderUtils.glColor(color);
        GL11.glCallList((int)DISPLAY_LISTS_2D[0]);
        RenderUtils.glColor(backgroundColor);
        GL11.glCallList((int)DISPLAY_LISTS_2D[1]);
        GlStateManager.func_179109_b((float)0.0f, (float)9.0f, (float)0.0f);
        RenderUtils.glColor(color);
        GL11.glCallList((int)DISPLAY_LISTS_2D[2]);
        RenderUtils.glColor(backgroundColor);
        GL11.glCallList((int)DISPLAY_LISTS_2D[3]);
        GL11.glEnable((int)2929);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GlStateManager.func_179121_F();
    }

    public static void renderNameTag(String string, double x, double y, double z) {
        RenderManager renderManager = mc.func_175598_ae();
        GL11.glPushMatrix();
        GL11.glTranslated((double)(x - renderManager.field_78725_b), (double)(y - renderManager.field_78726_c), (double)(z - renderManager.field_78723_d));
        GL11.glNormal3f((float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)(-RenderUtils.mc.func_175598_ae().field_78735_i), (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glRotatef((float)RenderUtils.mc.func_175598_ae().field_78732_j, (float)1.0f, (float)0.0f, (float)0.0f);
        GL11.glScalef((float)-0.05f, (float)-0.05f, (float)0.05f);
        RenderUtils.setGlCap(2896, false);
        RenderUtils.setGlCap(2929, false);
        RenderUtils.setGlCap(3042, true);
        GL11.glBlendFunc((int)770, (int)771);
        int width = Fonts.font35.func_78256_a(string) / 2;
        Gui.func_73734_a((int)(-width - 1), (int)-1, (int)(width + 1), (int)Fonts.font35.field_78288_b, (int)Integer.MIN_VALUE);
        Fonts.font35.func_175065_a(string, -width, 1.5f, Color.WHITE.getRGB(), true);
        RenderUtils.resetCaps();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glPopMatrix();
    }

    public static void makeScissorBox(float x, float y, float x2, float y2) {
        RenderUtils.makeScissorBox(x, y, x2, y2, 1.0f);
    }

    public static void makeScissorBox(float x, float y, float x2, float y2, float scaleOffset) {
        ScaledResolution scaledResolution = StaticStorage.scaledResolution;
        float factor = (float)scaledResolution.func_78325_e() * scaleOffset;
        GL11.glScissor((int)((int)(x * factor)), (int)((int)(((float)scaledResolution.func_78328_b() - y2) * factor)), (int)((int)((x2 - x) * factor)), (int)((int)((y2 - y) * factor)));
    }

    public static void resetCaps(String scale) {
        if (!glCapMap.containsKey(scale)) {
            return;
        }
        Map<Integer, Boolean> map = glCapMap.get(scale);
        map.forEach(RenderUtils::setGlState);
        map.clear();
    }

    public static void resetCaps() {
        RenderUtils.resetCaps("COMMON");
    }

    public static void clearCaps(String scale) {
        if (!glCapMap.containsKey(scale)) {
            return;
        }
        Map<Integer, Boolean> map = glCapMap.get(scale);
        if (!map.isEmpty()) {
            ClientUtils.INSTANCE.logWarn("Cap map is not empty! [" + map.size() + "]");
        }
        map.clear();
    }

    public static void clearCaps() {
        RenderUtils.clearCaps("COMMON");
    }

    public static void enableGlCap(int cap, String scale) {
        RenderUtils.setGlCap(cap, true, scale);
    }

    public static void enableGlCap(int cap) {
        RenderUtils.enableGlCap(cap, "COMMON");
    }

    public static void disableGlCap(int cap, String scale) {
        RenderUtils.setGlCap(cap, false, scale);
    }

    public static void disableGlCap(int cap) {
        RenderUtils.disableGlCap(cap, "COMMON");
    }

    public static void enableGlCap(int ... caps) {
        for (int cap : caps) {
            RenderUtils.setGlCap(cap, true, "COMMON");
        }
    }

    public static void disableGlCap(int ... caps) {
        for (int cap : caps) {
            RenderUtils.setGlCap(cap, false, "COMMON");
        }
    }

    public static void setGlCap(int cap, boolean state, String scale) {
        if (!glCapMap.containsKey(scale)) {
            glCapMap.put(scale, new HashMap());
        }
        glCapMap.get(scale).put(cap, GL11.glGetBoolean((int)cap));
        RenderUtils.setGlState(cap, state);
    }

    public static void setGlCap(int cap, boolean state) {
        RenderUtils.setGlCap(cap, state, "COMMON");
    }

    public static void setGlState(int cap, boolean state) {
        if (state) {
            GL11.glEnable((int)cap);
        } else {
            GL11.glDisable((int)cap);
        }
    }

    public static double[] convertTo2D(double x, double y, double z) {
        double[] dArray;
        FloatBuffer screenCoords = BufferUtils.createFloatBuffer((int)3);
        IntBuffer viewport = BufferUtils.createIntBuffer((int)16);
        FloatBuffer modelView = BufferUtils.createFloatBuffer((int)16);
        FloatBuffer projection = BufferUtils.createFloatBuffer((int)16);
        GL11.glGetFloat((int)2982, (FloatBuffer)modelView);
        GL11.glGetFloat((int)2983, (FloatBuffer)projection);
        GL11.glGetInteger((int)2978, (IntBuffer)viewport);
        boolean result = GLU.gluProject((float)((float)x), (float)((float)y), (float)((float)z), (FloatBuffer)modelView, (FloatBuffer)projection, (IntBuffer)viewport, (FloatBuffer)screenCoords);
        if (result) {
            double[] dArray2 = new double[3];
            dArray2[0] = screenCoords.get(0);
            dArray2[1] = (float)Display.getHeight() - screenCoords.get(1);
            dArray = dArray2;
            dArray2[2] = screenCoords.get(2);
        } else {
            dArray = null;
        }
        return dArray;
    }

    public static void rectangle(double left, double top, double right, double bottom, int color) {
        double var5;
        if (left < right) {
            var5 = left;
            left = right;
            right = var5;
        }
        if (top < bottom) {
            var5 = top;
            top = bottom;
            bottom = var5;
        }
        float var11 = (float)(color >> 24 & 0xFF) / 255.0f;
        float var6 = (float)(color >> 16 & 0xFF) / 255.0f;
        float var7 = (float)(color >> 8 & 0xFF) / 255.0f;
        float var8 = (float)(color & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldRenderer = tessellator.func_178180_c();
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
        GlStateManager.func_179131_c((float)var6, (float)var7, (float)var8, (float)var11);
        worldRenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
        worldRenderer.func_181662_b(left, bottom, 0.0).func_181675_d();
        worldRenderer.func_181662_b(right, bottom, 0.0).func_181675_d();
        worldRenderer.func_181662_b(right, top, 0.0).func_181675_d();
        worldRenderer.func_181662_b(left, top, 0.0).func_181675_d();
        tessellator.func_78381_a();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    public static Color reAlpha(Color cIn, float alpha) {
        return new Color((float)cIn.getRed() / 255.0f, (float)cIn.getGreen() / 255.0f, (float)cIn.getBlue() / 255.0f, (float)cIn.getAlpha() / 255.0f * alpha);
    }

    public static void rectangleBordered(double x, double y, double x1, double y1, double width, int internalColor, int borderColor) {
        RenderUtils.rectangle(x + width, y + width, x1 - width, y1 - width, internalColor);
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderUtils.rectangle(x + width, y, x1 - width, y + width, borderColor);
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderUtils.rectangle(x, y, x + width, y1, borderColor);
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderUtils.rectangle(x1 - width, y, x1, y1, borderColor);
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderUtils.rectangle(x + width, y1 - width, x1 - width, y1, borderColor);
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    public static void skeetRect(double x, double y, double x1, double y1, double size) {
        RenderUtils.rectangleBordered(x, y - 4.0, x1 + size, y1 + size, 0.5, new Color(60, 60, 60).getRGB(), new Color(10, 10, 10).getRGB());
        RenderUtils.rectangleBordered(x + 1.0, y - 3.0, x1 + size - 1.0, y1 + size - 1.0, 1.0, new Color(40, 40, 40).getRGB(), new Color(40, 40, 40).getRGB());
        RenderUtils.rectangleBordered(x + 2.5, y - 1.5, x1 + size - 2.5, y1 + size - 2.5, 0.5, new Color(40, 40, 40).getRGB(), new Color(60, 60, 60).getRGB());
        RenderUtils.rectangleBordered(x + 2.5, y - 1.5, x1 + size - 2.5, y1 + size - 2.5, 0.5, new Color(22, 22, 22).getRGB(), new Color(255, 255, 255, 0).getRGB());
    }

    public static void skeetRectSmall(double x, double y, double x1, double y1, double size) {
        RenderUtils.rectangleBordered(x + 4.35, y + 0.5, x1 + size - 84.5, y1 + size - 4.35, 0.5, new Color(48, 48, 48).getRGB(), new Color(10, 10, 10).getRGB());
        RenderUtils.rectangleBordered(x + 5.0, y + 1.0, x1 + size - 85.0, y1 + size - 5.0, 0.5, new Color(17, 17, 17).getRGB(), new Color(255, 255, 255, 0).getRGB());
    }

    public static void drawOutlinedBoundingBox(AxisAlignedBB aa) {
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldRenderer = tessellator.func_178180_c();
        worldRenderer.func_181668_a(3, DefaultVertexFormats.field_181705_e);
        worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72338_b, aa.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72338_b, aa.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72339_c).func_181675_d();
        tessellator.func_78381_a();
        worldRenderer.func_181668_a(3, DefaultVertexFormats.field_181705_e);
        worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72337_e, aa.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72337_e, aa.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72339_c).func_181675_d();
        tessellator.func_78381_a();
        worldRenderer.func_181668_a(1, DefaultVertexFormats.field_181705_e);
        worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72338_b, aa.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72337_e, aa.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72338_b, aa.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72337_e, aa.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72334_f).func_181675_d();
        tessellator.func_78381_a();
    }

    public static void drawBoundingBox(AxisAlignedBB aa) {
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldRenderer = tessellator.func_178180_c();
        worldRenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
        worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72338_b, aa.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72337_e, aa.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72338_b, aa.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72337_e, aa.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72334_f).func_181675_d();
        tessellator.func_78381_a();
        worldRenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
        worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72337_e, aa.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72338_b, aa.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72337_e, aa.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72338_b, aa.field_72334_f).func_181675_d();
        tessellator.func_78381_a();
        worldRenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
        worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72337_e, aa.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72337_e, aa.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72337_e, aa.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72337_e, aa.field_72339_c).func_181675_d();
        tessellator.func_78381_a();
        worldRenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
        worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72338_b, aa.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72338_b, aa.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72338_b, aa.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72338_b, aa.field_72339_c).func_181675_d();
        tessellator.func_78381_a();
        worldRenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
        worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72338_b, aa.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72337_e, aa.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72338_b, aa.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72337_e, aa.field_72339_c).func_181675_d();
        tessellator.func_78381_a();
        worldRenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
        worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72337_e, aa.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72340_a, aa.field_72338_b, aa.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72337_e, aa.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72338_b, aa.field_72339_c).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72337_e, aa.field_72334_f).func_181675_d();
        worldRenderer.func_181662_b(aa.field_72336_d, aa.field_72338_b, aa.field_72334_f).func_181675_d();
        tessellator.func_78381_a();
    }

    public static void originalRoundedRect(float paramXStart, float paramYStart, float paramXEnd, float paramYEnd, float radius, int color) {
        double i;
        float alpha = (float)(color >> 24 & 0xFF) / 255.0f;
        float red2 = (float)(color >> 16 & 0xFF) / 255.0f;
        float green2 = (float)(color >> 8 & 0xFF) / 255.0f;
        float blue2 = (float)(color & 0xFF) / 255.0f;
        float z = 0.0f;
        if (paramXStart > paramXEnd) {
            z = paramXStart;
            paramXStart = paramXEnd;
            paramXEnd = z;
        }
        if (paramYStart > paramYEnd) {
            z = paramYStart;
            paramYStart = paramYEnd;
            paramYEnd = z;
        }
        double x1 = paramXStart + radius;
        double y1 = paramYStart + radius;
        double x2 = paramXEnd - radius;
        double y2 = paramYEnd - radius;
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldrenderer = tessellator.func_178180_c();
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179120_a((int)770, (int)771, (int)1, (int)0);
        GlStateManager.func_179131_c((float)red2, (float)green2, (float)blue2, (float)alpha);
        worldrenderer.func_181668_a(9, DefaultVertexFormats.field_181705_e);
        double degree = Math.PI / 180;
        for (i = 0.0; i <= 90.0; i += 1.0) {
            worldrenderer.func_181662_b(x2 + Math.sin(i * degree) * (double)radius, y2 + Math.cos(i * degree) * (double)radius, 0.0).func_181675_d();
        }
        for (i = 90.0; i <= 180.0; i += 1.0) {
            worldrenderer.func_181662_b(x2 + Math.sin(i * degree) * (double)radius, y1 + Math.cos(i * degree) * (double)radius, 0.0).func_181675_d();
        }
        for (i = 180.0; i <= 270.0; i += 1.0) {
            worldrenderer.func_181662_b(x1 + Math.sin(i * degree) * (double)radius, y1 + Math.cos(i * degree) * (double)radius, 0.0).func_181675_d();
        }
        for (i = 270.0; i <= 360.0; i += 1.0) {
            worldrenderer.func_181662_b(x1 + Math.sin(i * degree) * (double)radius, y2 + Math.cos(i * degree) * (double)radius, 0.0).func_181675_d();
        }
        tessellator.func_78381_a();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
    }

    public static void drawFilledCircle(int xx, int yy, float radius, Color color) {
        int sections = 50;
        double dAngle = Math.PI * 2 / (double)sections;
        GL11.glPushAttrib((int)8192);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glBegin((int)6);
        for (int i = 0; i < sections; ++i) {
            float x = (float)((double)radius * Math.sin((double)i * dAngle));
            float y = (float)((double)radius * Math.cos((double)i * dAngle));
            GL11.glColor4f((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
            GL11.glVertex2f((float)((float)xx + x), (float)((float)yy + y));
        }
        GlStateManager.func_179124_c((float)0.0f, (float)0.0f, (float)0.0f);
        GL11.glEnd();
        GL11.glPopAttrib();
    }

    public static void drawFilledCircle(float xx, float yy, float radius, Color color) {
        int sections = 50;
        double dAngle = Math.PI * 2 / (double)sections;
        GL11.glPushAttrib((int)8192);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glBegin((int)6);
        for (int i = 0; i < sections; ++i) {
            float x = (float)((double)radius * Math.sin((double)i * dAngle));
            float y = (float)((double)radius * Math.cos((double)i * dAngle));
            GL11.glColor4f((float)((float)color.getRed() / 255.0f), (float)((float)color.getGreen() / 255.0f), (float)((float)color.getBlue() / 255.0f), (float)((float)color.getAlpha() / 255.0f));
            GL11.glVertex2f((float)(xx + x), (float)(yy + y));
        }
        GlStateManager.func_179124_c((float)0.0f, (float)0.0f, (float)0.0f);
        GL11.glEnd();
        GL11.glPopAttrib();
    }

    public static void drawLine(double x, double y, double x1, double y1, float width) {
        GL11.glDisable((int)3553);
        GL11.glLineWidth((float)width);
        GL11.glBegin((int)1);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glVertex2d((double)x1, (double)y1);
        GL11.glEnd();
        GL11.glEnable((int)3553);
    }

    public static void startDrawing() {
        GL11.glEnable((int)3042);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        Minecraft.func_71410_x().field_71460_t.func_78479_a(Minecraft.func_71410_x().field_71428_T.field_74281_c, 0);
    }

    public static void stopDrawing() {
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)2848);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)2929);
    }

    public static void drawRoundedRect(float paramXStart, float paramYStart, float paramXEnd, float paramYEnd, float radius, int color) {
        RenderUtils.drawRoundedRect(paramXStart, paramYStart, paramXEnd, paramYEnd, radius, color, true);
    }

    public static void drawBloomRoundedRect(float paramXStart, float paramYStart, float paramXEnd, float paramYEnd, float radius, float radius2, float shadow, Color color, ShaderBloom bloom) {
        Color color1 = color;
        if (shadow > 8.0f) {
            shadow = 8.0f;
        }
        if (bloom == ShaderBloom.BLOOMONLY && color.getAlpha() == 255 && !Interface.INSTANCE.getBloomValue().get().booleanValue()) {
            color1 = new Color(color.getRed(), color.getGreen(), color.getBlue(), 127);
        }
        if (!((Boolean)Interface.INSTANCE.getShaderValue().get()).booleanValue() || !Interface.INSTANCE.getBloomValue().get().booleanValue()) {
            RenderUtils.drawRoundedRect(paramXStart, paramYStart, paramXEnd, paramYEnd, radius, color1.getRGB(), true);
            return;
        }
        switch (bloom) {
            case BLOOMONLY: {
                ShaderUtil.drawRoundedRect(paramXStart, paramYStart, paramXEnd, paramYEnd, radius2, shadow, color);
                break;
            }
            case ROUNDONLY: {
                RenderUtils.drawRoundedRect(paramXStart, paramYStart, paramXEnd, paramYEnd, radius, color.getRGB(), true);
                break;
            }
            case BOTH: {
                ShaderUtil.drawRoundedRect(paramXStart - shadow, paramYStart - shadow, paramXEnd + shadow, paramYEnd + shadow, radius2, shadow, color1);
                RenderUtils.drawRoundedRect(paramXStart, paramYStart, paramXEnd, paramYEnd, radius, color1.getRGB(), true);
            }
        }
    }

    public static void drawBloomGradientRoundedRect(float paramXStart, float paramYStart, float paramXEnd, float paramYEnd, float radius, float shadow, Color colorTop, Color colorBottom, ShaderBloom bloom) {
        Color color1 = colorTop;
        Color color2 = colorBottom;
        if (bloom == ShaderBloom.BLOOMONLY && colorTop.getAlpha() == 255 && !Interface.INSTANCE.getBloomValue().get().booleanValue()) {
            color1 = new Color(colorTop.getRed(), colorTop.getGreen(), colorTop.getBlue(), 127);
        }
        if (bloom == ShaderBloom.BLOOMONLY && colorBottom.getAlpha() == 255 && !Interface.INSTANCE.getBloomValue().get().booleanValue()) {
            color2 = new Color(colorBottom.getRed(), colorBottom.getGreen(), colorBottom.getBlue(), 127);
        }
        if (!((Boolean)Interface.INSTANCE.getShaderValue().get()).booleanValue() || !Interface.INSTANCE.getBloomValue().get().booleanValue()) {
            RenderUtils.drawRoundedRect(paramXStart, paramYStart, paramXEnd, paramYEnd, radius, color1.getRGB(), true);
            return;
        }
        if (shadow > 8.0f) {
            shadow = 8.0f;
        }
        if (!((Boolean)Interface.INSTANCE.getShaderValue().get()).booleanValue() || !Interface.INSTANCE.getBloomValue().get().booleanValue()) {
            RenderUtils.drawGradientRoundedRect(paramXStart, paramYStart, paramXEnd, paramYEnd, (int)radius, color1.getRGB(), color2.getRGB());
            return;
        }
        switch (bloom) {
            case BLOOMONLY: {
                ShaderUtil.drawGradientRoundedRect(paramXStart, paramYStart, paramXEnd, paramYEnd, radius, shadow, colorTop, colorBottom);
                break;
            }
            case ROUNDONLY: {
                RenderUtils.drawGradientRoundedRect(paramXStart, paramYStart, paramXEnd, paramYEnd, (int)radius, color1.getRGB(), color2.getRGB());
                break;
            }
            case BOTH: {
                ShaderUtil.drawGradientRoundedRect(paramXStart - shadow, paramYStart - shadow, paramXEnd + shadow, paramYEnd + shadow, radius, shadow, color1, color2);
                RenderUtils.drawGradientRoundedRect(paramXStart, paramYStart, paramXEnd, paramYEnd, (int)radius, color1.getRGB(), color2.getRGB());
            }
        }
    }

    public static void drawBloomRoundedRect(float paramXStart, float paramYStart, float paramXEnd, float paramYEnd, float radius, float shadow, Color color, ShaderBloom bloom) {
        Color color1 = color;
        if (shadow > 8.0f) {
            shadow = 8.0f;
        }
        if (bloom == ShaderBloom.BLOOMONLY && color.getAlpha() == 255 && !Interface.INSTANCE.getBloomValue().get().booleanValue()) {
            color1 = new Color(color.getRed(), color.getGreen(), color.getBlue(), 127);
        }
        if (!((Boolean)Interface.INSTANCE.getShaderValue().get()).booleanValue() || !Interface.INSTANCE.getBloomValue().get().booleanValue()) {
            RenderUtils.drawRoundedRect(paramXStart, paramYStart, paramXEnd, paramYEnd, radius, color1.getRGB(), true);
            return;
        }
        switch (bloom) {
            case BLOOMONLY: {
                ShaderUtil.drawRoundedRect(paramXStart, paramYStart, paramXEnd, paramYEnd, radius, shadow, color1);
                break;
            }
            case ROUNDONLY: {
                RenderUtils.drawRoundedRect(paramXStart, paramYStart, paramXEnd, paramYEnd, radius, color1.getRGB(), true);
                break;
            }
            case BOTH: {
                ShaderUtil.drawRoundedRect(paramXStart - shadow, paramYStart - shadow, paramXEnd + shadow, paramYEnd + shadow, radius, shadow, color1);
                RenderUtils.drawRoundedRect(paramXStart, paramYStart, paramXEnd, paramYEnd, radius, color1.getRGB(), true);
            }
        }
    }

    public static void drawEntityOnScreen(double posX, double posY, float scale, EntityLivingBase entity) {
        GlStateManager.func_179094_E();
        GlStateManager.func_179142_g();
        GlStateManager.func_179137_b((double)posX, (double)posY, (double)50.0);
        GlStateManager.func_179152_a((float)(-scale), (float)scale, (float)scale);
        GlStateManager.func_179114_b((float)180.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        GlStateManager.func_179114_b((float)135.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        RenderHelper.func_74519_b();
        GlStateManager.func_179114_b((float)-135.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.func_179137_b((double)0.0, (double)0.0, (double)0.0);
        RenderManager rendermanager = mc.func_175598_ae();
        rendermanager.func_178631_a(180.0f);
        rendermanager.func_178633_a(false);
        rendermanager.func_147940_a((Entity)entity, 0.0, 0.0, 0.0, 0.0f, 1.0f);
        rendermanager.func_178633_a(true);
        GlStateManager.func_179121_F();
        RenderHelper.func_74518_a();
        GlStateManager.func_179101_C();
        GlStateManager.func_179138_g((int)OpenGlHelper.field_77476_b);
        GlStateManager.func_179090_x();
        GlStateManager.func_179138_g((int)OpenGlHelper.field_77478_a);
    }

    public static void drawEntityOnScreen(int posX, int posY, int scale, EntityLivingBase entity) {
        RenderUtils.drawEntityOnScreen((double)posX, (double)posY, (float)scale, entity);
    }

    public static void drawScaledCustomSizeModalRect(int x, int y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight) {
        float f = 1.0f / tileWidth;
        float f1 = 1.0f / tileHeight;
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldrenderer = tessellator.func_178180_c();
        worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
        worldrenderer.func_181662_b((double)x, (double)(y + height), 0.0).func_181673_a((double)(u * f), (double)((v + (float)vHeight) * f1)).func_181675_d();
        worldrenderer.func_181662_b((double)(x + width), (double)(y + height), 0.0).func_181673_a((double)((u + (float)uWidth) * f), (double)((v + (float)vHeight) * f1)).func_181675_d();
        worldrenderer.func_181662_b((double)(x + width), (double)y, 0.0).func_181673_a((double)((u + (float)uWidth) * f), (double)(v * f1)).func_181675_d();
        worldrenderer.func_181662_b((double)x, (double)y, 0.0).func_181673_a((double)(u * f), (double)(v * f1)).func_181675_d();
        tessellator.func_78381_a();
    }

    public static void drawScaledCustomSizeModalCircle(int x, int y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight) {
        float f = 1.0f / tileWidth;
        float f1 = 1.0f / tileHeight;
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldrenderer = tessellator.func_178180_c();
        worldrenderer.func_181668_a(9, DefaultVertexFormats.field_181707_g);
        float xRadius = (float)width / 2.0f;
        float yRadius = (float)height / 2.0f;
        float uRadius = ((u + (float)uWidth) * f - u * f) / 2.0f;
        float vRadius = ((v + (float)vHeight) * f1 - v * f1) / 2.0f;
        for (int i = 0; i <= 360; i += 10) {
            double xPosOffset = Math.sin((double)i * Math.PI / 180.0);
            double yPosOffset = Math.cos((double)i * Math.PI / 180.0);
            worldrenderer.func_181662_b((double)((float)x + xRadius) + xPosOffset * (double)xRadius, (double)((float)y + yRadius) + yPosOffset * (double)yRadius, 0.0).func_181673_a((double)(u * f + uRadius) + xPosOffset * (double)uRadius, (double)(v * f1 + vRadius) + yPosOffset * (double)vRadius).func_181675_d();
        }
        tessellator.func_78381_a();
    }

    public static void drawHead(ResourceLocation skin, int x, int y, int width, int height, int color) {
        float f3 = (float)(color >> 24 & 0xFF) / 255.0f;
        float f = (float)(color >> 16 & 0xFF) / 255.0f;
        float f1 = (float)(color >> 8 & 0xFF) / 255.0f;
        float f2 = (float)(color & 0xFF) / 255.0f;
        GlStateManager.func_179131_c((float)f, (float)f1, (float)f2, (float)f3);
        mc.func_110434_K().func_110577_a(skin);
        RenderUtils.drawScaledCustomSizeModalRect(x, y, 8.0f, 8.0f, 8, 8, width, height, 64.0f, 64.0f);
        RenderUtils.drawScaledCustomSizeModalRect(x, y, 40.0f, 8.0f, 8, 8, width, height, 64.0f, 64.0f);
    }

    public static void quickDrawBorderedRect(float x, float y, float x2, float y2, float width, int color1, int color2) {
        RenderUtils.quickDrawRect(x, y, x2, y2, color2);
        RenderUtils.glColor(color1);
        GL11.glLineWidth((float)width);
        GL11.glBegin((int)2);
        GL11.glVertex2d((double)x2, (double)y);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glVertex2d((double)x, (double)y2);
        GL11.glVertex2d((double)x2, (double)y2);
        GL11.glEnd();
    }

    public static void quickDrawHead(ResourceLocation skin, int x, int y, int width, int height) {
        mc.func_110434_K().func_110577_a(skin);
        RenderUtils.drawScaledCustomSizeModalRect(x, y, 8.0f, 8.0f, 8, 8, width, height, 64.0f, 64.0f);
        RenderUtils.drawScaledCustomSizeModalRect(x, y, 40.0f, 8.0f, 8, 8, width, height, 64.0f, 64.0f);
    }

    public static void drawBorder(float x, float y, float x2, float y2, float width, int color1) {
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        RenderUtils.glColor(color1);
        GL11.glLineWidth((float)width);
        GL11.glBegin((int)2);
        GL11.glVertex2d((double)(x2 + 1.0f), (double)(y - 1.0f));
        GL11.glVertex2d((double)(x - 1.0f), (double)(y - 1.0f));
        GL11.glVertex2d((double)(x - 1.0f), (double)(y2 + 1.0f));
        GL11.glVertex2d((double)(x2 + 1.0f), (double)(y2 + 1.0f));
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
    }

    public static void drawOutLineRect(double x, double y, double x1, double y1, double width, int internalColor, int borderColor) {
        RenderUtils.drawRect(x + width, y + width, x1 - width, y1 - width, internalColor);
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderUtils.drawRect(x + width, y, x1 - width, y + width, borderColor);
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderUtils.drawRect(x, y, x + width, y1, borderColor);
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderUtils.drawRect(x1 - width, y, x1, y1, borderColor);
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        RenderUtils.drawRect(x + width, y1 - width, x1 - width, y1, borderColor);
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    public static void enableSmoothLine(float width) {
        GL11.glDisable((int)3008);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glEnable((int)2884);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        GL11.glHint((int)3155, (int)4354);
        GL11.glLineWidth((float)width);
    }

    public static void disableSmoothLine() {
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3008);
        GL11.glDepthMask((boolean)true);
        GL11.glCullFace((int)1029);
        GL11.glDisable((int)2848);
        GL11.glHint((int)3154, (int)4352);
        GL11.glHint((int)3155, (int)4352);
    }

    public static void directDrawAWTShape(Shape shape, double epsilon) {
        RenderUtils.drawAWTShape(shape, epsilon, DirectTessCallback.INSTANCE);
    }

    public static void startSmooth() {
        GL11.glEnable((int)2848);
        GL11.glEnable((int)2881);
        GL11.glEnable((int)2832);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glHint((int)3154, (int)4354);
        GL11.glHint((int)3155, (int)4354);
        GL11.glHint((int)3153, (int)4354);
    }

    public static void endSmooth() {
        GL11.glDisable((int)2848);
        GL11.glDisable((int)2881);
        GL11.glEnable((int)2832);
    }

    public static void drawAWTShape(Shape shape, double epsilon, GLUtessellatorCallbackAdapter gluTessCallback) {
        PathIterator path = shape.getPathIterator(new AffineTransform());
        Double[] cp = new Double[2];
        GLUtessellator tess = GLU.gluNewTess();
        tess.gluTessCallback(100100, (GLUtessellatorCallback)gluTessCallback);
        tess.gluTessCallback(100102, (GLUtessellatorCallback)gluTessCallback);
        tess.gluTessCallback(100101, (GLUtessellatorCallback)gluTessCallback);
        tess.gluTessCallback(100105, (GLUtessellatorCallback)gluTessCallback);
        switch (path.getWindingRule()) {
            case 0: {
                tess.gluTessProperty(100140, 100130.0);
                break;
            }
            case 1: {
                tess.gluTessProperty(100140, 100131.0);
            }
        }
        ArrayList<Object> pointsCache = new ArrayList<Object>();
        tess.gluTessBeginPolygon(null);
        while (!path.isDone()) {
            double[] segment = new double[6];
            int type = path.currentSegment(segment);
            switch (type) {
                case 0: {
                    tess.gluTessBeginContour();
                    pointsCache.add(new Double[]{segment[0], segment[1]});
                    cp[0] = segment[0];
                    cp[1] = segment[1];
                    break;
                }
                case 1: {
                    pointsCache.add(new Double[]{segment[0], segment[1]});
                    cp[0] = segment[0];
                    cp[1] = segment[1];
                    break;
                }
                case 2: {
                    Double[][] points = MathUtils.getPointsOnCurve(new Double[][]{{cp[0], cp[1]}, {segment[0], segment[1]}, {segment[2], segment[3]}}, 10);
                    pointsCache.addAll(Arrays.asList(points));
                    cp[0] = segment[2];
                    cp[1] = segment[3];
                    break;
                }
                case 3: {
                    Double[][] points = MathUtils.getPointsOnCurve(new Double[][]{{cp[0], cp[1]}, {segment[0], segment[1]}, {segment[2], segment[3]}, {segment[4], segment[5]}}, 10);
                    pointsCache.addAll(Arrays.asList(points));
                    cp[0] = segment[4];
                    cp[1] = segment[5];
                    break;
                }
                case 4: {
                    for (Double[] point : MathUtils.simplifyPoints((Double[][])pointsCache.toArray((T[])new Double[0][0]), epsilon)) {
                        RenderUtils.tessVertex(tess, new double[]{point[0], point[1], 0.0, 0.0, 0.0, 0.0});
                    }
                    pointsCache.clear();
                    tess.gluTessEndContour();
                }
            }
            path.next();
        }
        tess.gluEndPolygon();
        tess.gluDeleteTess();
    }

    public static void drawNewRect(double left, double top, double right, double bottom, int color) {
        if (left < right) {
            double i = left;
            left = right;
            right = i;
        }
        if (top < bottom) {
            double j = top;
            top = bottom;
            bottom = j;
        }
        float f3 = (float)(color >> 24 & 0xFF) / 255.0f;
        float f = (float)(color >> 16 & 0xFF) / 255.0f;
        float f1 = (float)(color >> 8 & 0xFF) / 255.0f;
        float f2 = (float)(color & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer vertexbuffer = tessellator.func_178180_c();
        GlStateManager.func_179147_l();
        GlStateManager.func_179090_x();
        GlStateManager.func_179120_a((int)770, (int)771, (int)0, (int)1);
        GlStateManager.func_179131_c((float)f, (float)f1, (float)f2, (float)f3);
        vertexbuffer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
        vertexbuffer.func_181662_b(left, bottom, 0.0).func_181675_d();
        vertexbuffer.func_181662_b(right, bottom, 0.0).func_181675_d();
        vertexbuffer.func_181662_b(right, top, 0.0).func_181675_d();
        vertexbuffer.func_181662_b(left, top, 0.0).func_181675_d();
        tessellator.func_78381_a();
        GlStateManager.func_179098_w();
        GlStateManager.func_179084_k();
    }

    public static void drawCheckeredBackground(float x, float y, float x2, float y2) {
        RenderUtils.drawRect(x, y, x2, y2, RenderUtils.getColor(0xFFFFFF));
        boolean offset = false;
        while (y < y2) {
            offset = !offset;
            for (float x3 = x + (float)(offset ? true : false); x3 < x2; x3 += 2.0f) {
                if (!(x3 <= x2 - 1.0f)) continue;
                RenderUtils.drawRect(x3, y, x3 + 1.0f, y + 1.0f, RenderUtils.getColor(0x808080));
            }
            y += 1.0f;
        }
    }

    public static int getColor(int color) {
        int r = color >> 16 & 0xFF;
        int g = color >> 8 & 0xFF;
        int b = color & 0xFF;
        int a = 255;
        return (r & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF | 0xFF000000;
    }

    public static void scissor(double x, double y, double width, double height) {
        int scaleFactor;
        for (scaleFactor = new ScaledResolution(Minecraft.func_71410_x()).func_78325_e(); scaleFactor < 2 && Minecraft.func_71410_x().field_71443_c / (scaleFactor + 1) >= 320 && Minecraft.func_71410_x().field_71440_d / (scaleFactor + 1) >= 240; ++scaleFactor) {
        }
        GL11.glScissor((int)((int)(x * (double)scaleFactor)), (int)((int)((double)Minecraft.func_71410_x().field_71440_d - (y + height) * (double)scaleFactor)), (int)((int)(width * (double)scaleFactor)), (int)((int)(height * (double)scaleFactor)));
    }

    public static void drawRoundedRectd(float paramXStart, float paramYStart, float paramXEnd, float paramYEnd, float radius, int color) {
        RenderUtils.drawRoundedRect(paramXStart, paramYStart, paramXEnd, paramYEnd, radius, color, true);
    }

    public static void drawGradientRect(double d, double e, double e2, double g, int startColor, int endColor) {
        float f = (float)(startColor >> 24 & 0xFF) / 255.0f;
        float f2 = (float)(startColor >> 16 & 0xFF) / 255.0f;
        float f3 = (float)(startColor >> 8 & 0xFF) / 255.0f;
        float f4 = (float)(startColor & 0xFF) / 255.0f;
        float f5 = (float)(endColor >> 24 & 0xFF) / 255.0f;
        float f6 = (float)(endColor >> 16 & 0xFF) / 255.0f;
        float f7 = (float)(endColor >> 8 & 0xFF) / 255.0f;
        float f8 = (float)(endColor & 0xFF) / 255.0f;
        GlStateManager.func_179090_x();
        GlStateManager.func_179147_l();
        GlStateManager.func_179118_c();
        GlStateManager.func_179120_a((int)770, (int)771, (int)0, (int)1);
        GlStateManager.func_179103_j((int)7425);
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer bufferbuilder = tessellator.func_178180_c();
        bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181706_f);
        bufferbuilder.func_181662_b(e2, e, (double)zLevel).func_181666_a(f2, f3, f4, f).func_181675_d();
        bufferbuilder.func_181662_b(d, e, (double)zLevel).func_181666_a(f2, f3, f4, f).func_181675_d();
        bufferbuilder.func_181662_b(d, g, (double)zLevel).func_181666_a(f6, f7, f8, f5).func_181675_d();
        bufferbuilder.func_181662_b(e2, g, (double)zLevel).func_181666_a(f6, f7, f8, f5).func_181675_d();
        tessellator.func_78381_a();
        GlStateManager.func_179103_j((int)7424);
        GlStateManager.func_179084_k();
        GlStateManager.func_179141_d();
        GlStateManager.func_179098_w();
    }

    public static void render(int mode2, Runnable render) {
        GL11.glBegin((int)mode2);
        render.run();
        GL11.glEnd();
    }

    public static void setup2DRendering(Runnable f) {
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDisable((int)3553);
        f.run();
        GL11.glEnable((int)3553);
        GlStateManager.func_179084_k();
    }

    public static void tessVertex(GLUtessellator tessellator, double[] coords) {
        tessellator.gluTessVertex(coords, 0, (Object)new VertexData(coords));
    }

    public static boolean inArea(float x, float y, float ax1, float ay1, float ax2, float ay2) {
        return x >= ax1 && x <= ax2 && y >= ay1 && y <= ay2;
    }

    public static boolean inArea(int x, int y, int ax1, int ay1, int ax2, int ay2) {
        return x >= ax1 && x <= ax2 && y >= ay1 && y <= ay2;
    }

    public static int loadGlTexture(BufferedImage bufferedImage) {
        int textureId = GL11.glGenTextures();
        GL11.glBindTexture((int)3553, (int)textureId);
        GL11.glTexParameteri((int)3553, (int)10242, (int)10497);
        GL11.glTexParameteri((int)3553, (int)10243, (int)10497);
        GL11.glTexParameteri((int)3553, (int)10241, (int)9729);
        GL11.glTexParameteri((int)3553, (int)10240, (int)9729);
        GL11.glTexImage2D((int)3553, (int)0, (int)6408, (int)bufferedImage.getWidth(), (int)bufferedImage.getHeight(), (int)0, (int)6408, (int)5121, (ByteBuffer)ImageUtils.readImageToBuffer(bufferedImage));
        GL11.glBindTexture((int)3553, (int)0);
        return textureId;
    }

    public static void drawModel(float yaw, float pitch, EntityLivingBase entityLivingBase) {
        GlStateManager.func_179117_G();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.func_179142_g();
        GlStateManager.func_179094_E();
        GlStateManager.func_179109_b((float)0.0f, (float)0.0f, (float)50.0f);
        GlStateManager.func_179152_a((float)-50.0f, (float)50.0f, (float)50.0f);
        GlStateManager.func_179114_b((float)180.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        float renderYawOffset = entityLivingBase.field_70761_aq;
        float rotationYaw = entityLivingBase.field_70177_z;
        float rotationPitch = entityLivingBase.field_70125_A;
        float prevRotationYawHead = entityLivingBase.field_70758_at;
        float rotationYawHead = entityLivingBase.field_70759_as;
        GlStateManager.func_179114_b((float)135.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        RenderHelper.func_74519_b();
        GlStateManager.func_179114_b((float)-135.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.func_179114_b((float)((float)(-Math.atan(pitch / 40.0f) * 20.0)), (float)1.0f, (float)0.0f, (float)0.0f);
        entityLivingBase.field_70761_aq = yaw - yaw / yaw * 0.4f;
        entityLivingBase.field_70177_z = yaw - yaw / yaw * 0.2f;
        entityLivingBase.field_70125_A = pitch;
        entityLivingBase.field_70759_as = entityLivingBase.field_70177_z;
        entityLivingBase.field_70758_at = entityLivingBase.field_70177_z;
        GlStateManager.func_179109_b((float)0.0f, (float)0.0f, (float)0.0f);
        RenderManager renderManager = mc.func_175598_ae();
        renderManager.func_178631_a(180.0f);
        renderManager.func_178633_a(false);
        entityLivingBase.func_174833_aM();
        renderManager.func_147940_a((Entity)entityLivingBase, 0.0, 0.0, 0.0, 0.0f, 1.0f);
        renderManager.func_178633_a(true);
        entityLivingBase.field_70761_aq = renderYawOffset;
        entityLivingBase.field_70177_z = rotationYaw;
        entityLivingBase.field_70125_A = rotationPitch;
        entityLivingBase.field_70758_at = prevRotationYawHead;
        entityLivingBase.field_70759_as = rotationYawHead;
        GlStateManager.func_179121_F();
        RenderHelper.func_74518_a();
        GlStateManager.func_179101_C();
        GlStateManager.func_179138_g((int)OpenGlHelper.field_77476_b);
        GlStateManager.func_179090_x();
        GlStateManager.func_179138_g((int)OpenGlHelper.field_77478_a);
        GlStateManager.func_179117_G();
    }

    public static void drawOutlinedStringCock(FontRenderer fr, String s, float x, float y, int color, int outlineColor) {
        fr.func_78276_b(ColorUtils.stripColor(s), (int)(x - 1.0f), (int)y, outlineColor);
        fr.func_78276_b(ColorUtils.stripColor(s), (int)x, (int)(y - 1.0f), outlineColor);
        fr.func_78276_b(ColorUtils.stripColor(s), (int)(x + 1.0f), (int)y, outlineColor);
        fr.func_78276_b(ColorUtils.stripColor(s), (int)x, (int)(y + 1.0f), outlineColor);
        fr.func_78276_b(s, (int)x, (int)y, color);
    }

    public static void drawSmoothRoundedRect(float x, float y, float x1, float y1, float radius, Color colour) {
        int i;
        GL11.glPushAttrib((int)0);
        GL11.glScaled((double)0.5, (double)0.5, (double)0.5);
        x = (float)((double)x * 2.0);
        y = (float)((double)y * 2.0);
        x1 = (float)((double)x1 * 2.0);
        y1 = (float)((double)y1 * 2.0);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)2848);
        ColorUtils.setColour(colour.getRGB());
        GL11.glEnable((int)2848);
        GL11.glBegin((int)9);
        for (i = 0; i <= 90; i += 3) {
            GL11.glVertex2d((double)((double)(x + radius) + Math.sin((double)i * Math.PI / 180.0) * (double)radius * -1.0), (double)((double)(y + radius) + Math.cos((double)i * Math.PI / 180.0) * (double)radius * -1.0));
        }
        for (i = 90; i <= 180; i += 3) {
            GL11.glVertex2d((double)((double)(x + radius) + Math.sin((double)i * Math.PI / 180.0) * (double)radius * -1.0), (double)((double)(y1 - radius) + Math.cos((double)i * Math.PI / 180.0) * (double)radius * -1.0));
        }
        for (i = 0; i <= 90; i += 3) {
            GL11.glVertex2d((double)((double)(x1 - radius) + Math.sin((double)i * Math.PI / 180.0) * (double)radius), (double)((double)(y1 - radius) + Math.cos((double)i * Math.PI / 180.0) * (double)radius));
        }
        for (i = 90; i <= 180; i += 3) {
            GL11.glVertex2d((double)((double)(x1 - radius) + Math.sin((double)i * Math.PI / 180.0) * (double)radius), (double)((double)(y + radius) + Math.cos((double)i * Math.PI / 180.0) * (double)radius));
        }
        GL11.glEnd();
        GL11.glBegin((int)2);
        for (i = 0; i <= 90; i += 3) {
            GL11.glVertex2d((double)((double)(x + radius) + Math.sin((double)i * Math.PI / 180.0) * (double)radius * -1.0), (double)((double)(y + radius) + Math.cos((double)i * Math.PI / 180.0) * (double)radius * -1.0));
        }
        for (i = 90; i <= 180; i += 3) {
            GL11.glVertex2d((double)((double)(x + radius) + Math.sin((double)i * Math.PI / 180.0) * (double)radius * -1.0), (double)((double)(y1 - radius) + Math.cos((double)i * Math.PI / 180.0) * (double)radius * -1.0));
        }
        for (i = 0; i <= 90; i += 3) {
            GL11.glVertex2d((double)((double)(x1 - radius) + Math.sin((double)i * Math.PI / 180.0) * (double)radius), (double)((double)(y1 - radius) + Math.cos((double)i * Math.PI / 180.0) * (double)radius));
        }
        for (i = 90; i <= 180; i += 3) {
            GL11.glVertex2d((double)((double)(x1 - radius) + Math.sin((double)i * Math.PI / 180.0) * (double)radius), (double)((double)(y + radius) + Math.cos((double)i * Math.PI / 180.0) * (double)radius));
        }
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
        GL11.glDisable((int)2848);
        GL11.glEnable((int)3553);
        GL11.glScaled((double)2.0, (double)2.0, (double)2.0);
        GL11.glPopAttrib();
        GL11.glLineWidth((float)1.0f);
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    public static void drawRoundedOutline(float x, float y, float x1, float y1, float radius, float lineWidth, int colour) {
        int i;
        GL11.glPushAttrib((int)0);
        GL11.glScaled((double)0.5, (double)0.5, (double)0.5);
        x = (float)((double)x * 2.0);
        y = (float)((double)y * 2.0);
        x1 = (float)((double)x1 * 2.0);
        y1 = (float)((double)y1 * 2.0);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        ColorUtils.setColour(colour);
        GL11.glEnable((int)2848);
        GL11.glLineWidth((float)lineWidth);
        GL11.glBegin((int)2);
        for (i = 0; i <= 90; i += 3) {
            GL11.glVertex2d((double)((double)(x + radius) + Math.sin((double)i * Math.PI / 180.0) * (double)radius * -1.0), (double)((double)(y + radius) + Math.cos((double)i * Math.PI / 180.0) * (double)radius * -1.0));
        }
        for (i = 90; i <= 180; i += 3) {
            GL11.glVertex2d((double)((double)(x + radius) + Math.sin((double)i * Math.PI / 180.0) * (double)radius * -1.0), (double)((double)(y1 - radius) + Math.cos((double)i * Math.PI / 180.0) * (double)radius * -1.0));
        }
        for (i = 0; i <= 90; i += 3) {
            GL11.glVertex2d((double)((double)(x1 - radius) + Math.sin((double)i * Math.PI / 180.0) * (double)radius), (double)((double)(y1 - radius) + Math.cos((double)i * Math.PI / 180.0) * (double)radius));
        }
        for (i = 90; i <= 180; i += 3) {
            GL11.glVertex2d((double)((double)(x1 - radius) + Math.sin((double)i * Math.PI / 180.0) * (double)radius), (double)((double)(y + radius) + Math.cos((double)i * Math.PI / 180.0) * (double)radius));
        }
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3553);
        GL11.glScaled((double)2.0, (double)2.0, (double)2.0);
        GL11.glPopAttrib();
        GL11.glLineWidth((float)1.0f);
        GlStateManager.func_179131_c((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    public static void drawRoundedGradientRectCorner(float x, float y, float x1, float y1, float radius, int color, int color2, int color3, int color4) {
        int i;
        ColorUtils.setColour(-1);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glShadeModel((int)7425);
        GL11.glPushAttrib((int)0);
        GL11.glScaled((double)0.5, (double)0.5, (double)0.5);
        x = (float)((double)x * 2.0);
        y = (float)((double)y * 2.0);
        x1 = (float)((double)x1 * 2.0);
        y1 = (float)((double)y1 * 2.0);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        ColorUtils.setColour(color);
        GL11.glEnable((int)2848);
        GL11.glShadeModel((int)7425);
        GL11.glBegin((int)6);
        for (i = 0; i <= 90; i += 3) {
            GL11.glVertex2d((double)((double)(x + radius) + Math.sin((double)i * Math.PI / 180.0) * (double)radius * -1.0), (double)((double)(y + radius) + Math.cos((double)i * Math.PI / 180.0) * (double)radius * -1.0));
        }
        ColorUtils.setColour(color2);
        for (i = 90; i <= 180; i += 3) {
            GL11.glVertex2d((double)((double)(x + radius) + Math.sin((double)i * Math.PI / 180.0) * (double)radius * -1.0), (double)((double)(y1 - radius) + Math.cos((double)i * Math.PI / 180.0) * (double)radius * -1.0));
        }
        ColorUtils.setColour(color3);
        for (i = 0; i <= 90; i += 3) {
            GL11.glVertex2d((double)((double)(x1 - radius) + Math.sin((double)i * Math.PI / 180.0) * (double)radius), (double)((double)(y1 - radius) + Math.cos((double)i * Math.PI / 180.0) * (double)radius));
        }
        ColorUtils.setColour(color4);
        for (i = 90; i <= 180; i += 3) {
            GL11.glVertex2d((double)((double)(x1 - radius) + Math.sin((double)i * Math.PI / 180.0) * (double)radius), (double)((double)(y + radius) + Math.cos((double)i * Math.PI / 180.0) * (double)radius));
        }
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3553);
        GL11.glScaled((double)2.0, (double)2.0, (double)2.0);
        GL11.glPopAttrib();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
        GL11.glShadeModel((int)7424);
        ColorUtils.setColour(-1);
    }

    public static void drawRoundedGradientRectCorner(float x, float y, float x1, float y1, float radius, int color, int color2) {
        int i;
        ColorUtils.setColour(-1);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glShadeModel((int)7425);
        GL11.glPushAttrib((int)0);
        GL11.glScaled((double)0.5, (double)0.5, (double)0.5);
        x = (float)((double)x * 2.0);
        y = (float)((double)y * 2.0);
        x1 = (float)((double)x1 * 2.0);
        y1 = (float)((double)y1 * 2.0);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        ColorUtils.setColour(color);
        GL11.glEnable((int)2848);
        GL11.glShadeModel((int)7425);
        GL11.glBegin((int)6);
        for (i = 0; i <= 90; i += 3) {
            GL11.glVertex2d((double)((double)(x + radius) + Math.sin((double)i * Math.PI / 180.0) * (double)radius * -1.0), (double)((double)(y + radius) + Math.cos((double)i * Math.PI / 180.0) * (double)radius * -1.0));
        }
        ColorUtils.setColour(color);
        for (i = 90; i <= 180; i += 3) {
            GL11.glVertex2d((double)((double)(x + radius) + Math.sin((double)i * Math.PI / 180.0) * (double)radius * -1.0), (double)((double)(y1 - radius) + Math.cos((double)i * Math.PI / 180.0) * (double)radius * -1.0));
        }
        ColorUtils.setColour(color2);
        for (i = 0; i <= 90; i += 3) {
            GL11.glVertex2d((double)((double)(x1 - radius) + Math.sin((double)i * Math.PI / 180.0) * (double)radius), (double)((double)(y1 - radius) + Math.cos((double)i * Math.PI / 180.0) * (double)radius));
        }
        ColorUtils.setColour(color2);
        for (i = 90; i <= 180; i += 3) {
            GL11.glVertex2d((double)((double)(x1 - radius) + Math.sin((double)i * Math.PI / 180.0) * (double)radius), (double)((double)(y + radius) + Math.cos((double)i * Math.PI / 180.0) * (double)radius));
        }
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3553);
        GL11.glScaled((double)2.0, (double)2.0, (double)2.0);
        GL11.glPopAttrib();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
        GL11.glShadeModel((int)7424);
        ColorUtils.setColour(-1);
    }

    public static void drawRoundedGradientOutlineCorner(float x, float y, float x1, float y1, float width, float radius, int color, int color2, int color3, int color4) {
        int i;
        ColorUtils.setColour(-1);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glShadeModel((int)7425);
        GL11.glPushAttrib((int)0);
        GL11.glScaled((double)0.5, (double)0.5, (double)0.5);
        x = (float)((double)x * 2.0);
        y = (float)((double)y * 2.0);
        x1 = (float)((double)x1 * 2.0);
        y1 = (float)((double)y1 * 2.0);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        ColorUtils.setColour(color);
        GL11.glEnable((int)2848);
        GL11.glShadeModel((int)7425);
        GL11.glLineWidth((float)width);
        GL11.glBegin((int)2);
        for (i = 0; i <= 90; i += 3) {
            GL11.glVertex2d((double)((double)(x + radius) + Math.sin((double)i * Math.PI / 180.0) * (double)radius * -1.0), (double)((double)(y + radius) + Math.cos((double)i * Math.PI / 180.0) * (double)radius * -1.0));
        }
        ColorUtils.setColour(color2);
        for (i = 90; i <= 180; i += 3) {
            GL11.glVertex2d((double)((double)(x + radius) + Math.sin((double)i * Math.PI / 180.0) * (double)radius * -1.0), (double)((double)(y1 - radius) + Math.cos((double)i * Math.PI / 180.0) * (double)radius * -1.0));
        }
        ColorUtils.setColour(color3);
        for (i = 0; i <= 90; i += 3) {
            GL11.glVertex2d((double)((double)(x1 - radius) + Math.sin((double)i * Math.PI / 180.0) * (double)radius), (double)((double)(y1 - radius) + Math.cos((double)i * Math.PI / 180.0) * (double)radius));
        }
        ColorUtils.setColour(color4);
        for (i = 90; i <= 180; i += 3) {
            GL11.glVertex2d((double)((double)(x1 - radius) + Math.sin((double)i * Math.PI / 180.0) * (double)radius), (double)((double)(y + radius) + Math.cos((double)i * Math.PI / 180.0) * (double)radius));
        }
        GL11.glEnd();
        GL11.glLineWidth((float)1.0f);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3553);
        GL11.glScaled((double)2.0, (double)2.0, (double)2.0);
        GL11.glPopAttrib();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
        GL11.glShadeModel((int)7424);
        ColorUtils.setColour(-1);
    }

    public static void drawRoundedGradientOutlineCorner(float x, float y, float x1, float y1, float width, float radius, int color, int color2) {
        int i;
        ColorUtils.setColour(-1);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glShadeModel((int)7425);
        GL11.glPushAttrib((int)0);
        GL11.glScaled((double)0.5, (double)0.5, (double)0.5);
        x *= 2.0f;
        y *= 2.0f;
        x1 *= 2.0f;
        y1 *= 2.0f;
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        ColorUtils.setColour(color);
        GL11.glEnable((int)2848);
        GL11.glShadeModel((int)7425);
        GL11.glLineWidth((float)width);
        GL11.glBegin((int)2);
        for (i = 0; i <= 90; i += 3) {
            GL11.glVertex2d((double)((double)(x + radius) + Math.sin((double)i * Math.PI / 180.0) * (double)radius * -1.0), (double)((double)(y + radius) + Math.cos((double)i * Math.PI / 180.0) * (double)radius * -1.0));
        }
        ColorUtils.setColour(color);
        for (i = 90; i <= 180; i += 3) {
            GL11.glVertex2d((double)((double)(x + radius) + Math.sin((double)i * Math.PI / 180.0) * (double)radius * -1.0), (double)((double)(y1 - radius) + Math.cos((double)i * Math.PI / 180.0) * (double)radius * -1.0));
        }
        ColorUtils.setColour(color2);
        for (i = 0; i <= 90; i += 3) {
            GL11.glVertex2d((double)((double)(x1 - radius) + Math.sin((double)i * Math.PI / 180.0) * (double)radius), (double)((double)(y1 - radius) + Math.cos((double)i * Math.PI / 180.0) * (double)radius));
        }
        ColorUtils.setColour(color2);
        for (i = 90; i <= 180; i += 3) {
            GL11.glVertex2d((double)((double)(x1 - radius) + Math.sin((double)i * Math.PI / 180.0) * (double)radius), (double)((double)(y + radius) + Math.cos((double)i * Math.PI / 180.0) * (double)radius));
        }
        GL11.glEnd();
        GL11.glLineWidth((float)1.0f);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3553);
        GL11.glScaled((double)2.0, (double)2.0, (double)2.0);
        GL11.glPopAttrib();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
        GL11.glShadeModel((int)7424);
        ColorUtils.setColour(-1);
    }

    public static void otherDrawOutlinedBoundingBoundingBox(float yaw, double x, double y, double z, double width, double height) {
        float yaw4;
        float yaw3;
        float yaw2;
        float yaw1;
        width *= 1.5;
        if ((yaw = MathHelper.func_76142_g((float)yaw) + 45.0f) < 0.0f) {
            yaw1 = 0.0f;
            yaw1 += 360.0f - Math.abs(yaw);
        } else {
            yaw1 = yaw;
        }
        yaw1 *= -1.0f;
        yaw1 = (float)((double)yaw1 * (Math.PI / 180));
        yaw += 90.0f;
        if (yaw < 0.0f) {
            yaw2 = 0.0f;
            yaw2 += 360.0f - Math.abs(yaw);
        } else {
            yaw2 = yaw;
        }
        yaw2 *= -1.0f;
        yaw2 = (float)((double)yaw2 * (Math.PI / 180));
        yaw += 90.0f;
        if (yaw < 0.0f) {
            yaw3 = 0.0f;
            yaw3 += 360.0f - Math.abs(yaw);
        } else {
            yaw3 = yaw;
        }
        yaw3 *= -1.0f;
        yaw3 = (float)((double)yaw3 * (Math.PI / 180));
        yaw += 90.0f;
        if (yaw < 0.0f) {
            yaw4 = 0.0f;
            yaw4 += 360.0f - Math.abs(yaw);
        } else {
            yaw4 = yaw;
        }
        yaw4 *= -1.0f;
        yaw4 = (float)((double)yaw4 * (Math.PI / 180));
        float x1 = (float)(Math.sin(yaw1) * width + x);
        float z1 = (float)(Math.cos(yaw1) * width + z);
        float x2 = (float)(Math.sin(yaw2) * width + x);
        float z2 = (float)(Math.cos(yaw2) * width + z);
        float x3 = (float)(Math.sin(yaw3) * width + x);
        float z3 = (float)(Math.cos(yaw3) * width + z);
        float x4 = (float)(Math.sin(yaw4) * width + x);
        float z4 = (float)(Math.cos(yaw4) * width + z);
        float y2 = (float)(y + height);
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldrenderer = tessellator.func_178180_c();
        worldrenderer.func_181668_a(3, DefaultVertexFormats.field_181705_e);
        worldrenderer.func_181662_b((double)x1, y, (double)z1).func_181675_d();
        worldrenderer.func_181662_b((double)x1, (double)y2, (double)z1).func_181675_d();
        worldrenderer.func_181662_b((double)x2, (double)y2, (double)z2).func_181675_d();
        worldrenderer.func_181662_b((double)x2, y, (double)z2).func_181675_d();
        worldrenderer.func_181662_b((double)x1, y, (double)z1).func_181675_d();
        worldrenderer.func_181662_b((double)x4, y, (double)z4).func_181675_d();
        worldrenderer.func_181662_b((double)x3, y, (double)z3).func_181675_d();
        worldrenderer.func_181662_b((double)x3, (double)y2, (double)z3).func_181675_d();
        worldrenderer.func_181662_b((double)x4, (double)y2, (double)z4).func_181675_d();
        worldrenderer.func_181662_b((double)x4, y, (double)z4).func_181675_d();
        worldrenderer.func_181662_b((double)x4, (double)y2, (double)z4).func_181675_d();
        worldrenderer.func_181662_b((double)x3, (double)y2, (double)z3).func_181675_d();
        worldrenderer.func_181662_b((double)x2, (double)y2, (double)z2).func_181675_d();
        worldrenderer.func_181662_b((double)x2, y, (double)z2).func_181675_d();
        worldrenderer.func_181662_b((double)x3, y, (double)z3).func_181675_d();
        worldrenderer.func_181662_b((double)x4, y, (double)z4).func_181675_d();
        worldrenderer.func_181662_b((double)x4, (double)y2, (double)z4).func_181675_d();
        worldrenderer.func_181662_b((double)x1, (double)y2, (double)z1).func_181675_d();
        worldrenderer.func_181662_b((double)x1, y, (double)z1).func_181675_d();
        tessellator.func_78381_a();
    }

    public static void otherDrawBoundingBox(float yaw, double x, double y, double z, double width, double height) {
        float yaw4;
        float yaw3;
        float yaw2;
        float yaw1;
        width *= 1.5;
        if ((yaw = MathHelper.func_76142_g((float)yaw) + 45.0f) < 0.0f) {
            yaw1 = 0.0f;
            yaw1 += 360.0f - Math.abs(yaw);
        } else {
            yaw1 = yaw;
        }
        yaw1 *= -1.0f;
        yaw1 = (float)((double)yaw1 * (Math.PI / 180));
        yaw += 90.0f;
        if (yaw < 0.0f) {
            yaw2 = 0.0f;
            yaw2 += 360.0f - Math.abs(yaw);
        } else {
            yaw2 = yaw;
        }
        yaw2 *= -1.0f;
        yaw2 = (float)((double)yaw2 * (Math.PI / 180));
        yaw += 90.0f;
        if (yaw < 0.0f) {
            yaw3 = 0.0f;
            yaw3 += 360.0f - Math.abs(yaw);
        } else {
            yaw3 = yaw;
        }
        yaw3 *= -1.0f;
        yaw3 = (float)((double)yaw3 * (Math.PI / 180));
        yaw += 90.0f;
        if (yaw < 0.0f) {
            yaw4 = 0.0f;
            yaw4 += 360.0f - Math.abs(yaw);
        } else {
            yaw4 = yaw;
        }
        yaw4 *= -1.0f;
        yaw4 = (float)((double)yaw4 * (Math.PI / 180));
        float x1 = (float)(Math.sin(yaw1) * width + x);
        float z1 = (float)(Math.cos(yaw1) * width + z);
        float x2 = (float)(Math.sin(yaw2) * width + x);
        float z2 = (float)(Math.cos(yaw2) * width + z);
        float x3 = (float)(Math.sin(yaw3) * width + x);
        float z3 = (float)(Math.cos(yaw3) * width + z);
        float x4 = (float)(Math.sin(yaw4) * width + x);
        float z4 = (float)(Math.cos(yaw4) * width + z);
        float y1 = (float)y;
        float y2 = (float)(y + height);
        Tessellator tessellator = Tessellator.func_178181_a();
        WorldRenderer worldrenderer = tessellator.func_178180_c();
        worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
        worldrenderer.func_181662_b((double)x1, (double)y1, (double)z1).func_181675_d();
        worldrenderer.func_181662_b((double)x1, (double)y2, (double)z1).func_181675_d();
        worldrenderer.func_181662_b((double)x2, (double)y2, (double)z2).func_181675_d();
        worldrenderer.func_181662_b((double)x2, (double)y1, (double)z2).func_181675_d();
        worldrenderer.func_181662_b((double)x2, (double)y1, (double)z2).func_181675_d();
        worldrenderer.func_181662_b((double)x2, (double)y2, (double)z2).func_181675_d();
        worldrenderer.func_181662_b((double)x3, (double)y2, (double)z3).func_181675_d();
        worldrenderer.func_181662_b((double)x3, (double)y1, (double)z3).func_181675_d();
        worldrenderer.func_181662_b((double)x3, (double)y1, (double)z3).func_181675_d();
        worldrenderer.func_181662_b((double)x3, (double)y2, (double)z3).func_181675_d();
        worldrenderer.func_181662_b((double)x4, (double)y2, (double)z4).func_181675_d();
        worldrenderer.func_181662_b((double)x4, (double)y1, (double)z4).func_181675_d();
        worldrenderer.func_181662_b((double)x4, (double)y1, (double)z4).func_181675_d();
        worldrenderer.func_181662_b((double)x4, (double)y2, (double)z4).func_181675_d();
        worldrenderer.func_181662_b((double)x1, (double)y2, (double)z1).func_181675_d();
        worldrenderer.func_181662_b((double)x1, (double)y1, (double)z1).func_181675_d();
        worldrenderer.func_181662_b((double)x1, (double)y1, (double)z1).func_181675_d();
        worldrenderer.func_181662_b((double)x2, (double)y1, (double)z2).func_181675_d();
        worldrenderer.func_181662_b((double)x3, (double)y1, (double)z3).func_181675_d();
        worldrenderer.func_181662_b((double)x4, (double)y1, (double)z4).func_181675_d();
        worldrenderer.func_181662_b((double)x1, (double)y2, (double)z1).func_181675_d();
        worldrenderer.func_181662_b((double)x2, (double)y2, (double)z2).func_181675_d();
        worldrenderer.func_181662_b((double)x3, (double)y2, (double)z3).func_181675_d();
        worldrenderer.func_181662_b((double)x4, (double)y2, (double)z4).func_181675_d();
        tessellator.func_78381_a();
    }

    private static void drawEnchantTag(String text, int x, float y) {
        GlStateManager.func_179094_E();
        GlStateManager.func_179097_i();
        GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
        RenderUtils.drawOutlinedStringCock(Minecraft.func_71410_x().field_71466_p, text, x, y, -1, new Color(0, 0, 0, 220).darker().getRGB());
        GL11.glScalef((float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.func_179126_j();
        GlStateManager.func_179121_F();
    }

    public static void renderEnchantText(ItemStack stack, int x, float y) {
        int unbreakingLevel;
        RenderHelper.func_74518_a();
        float enchantmentY = y + 24.0f;
        if (stack.func_77973_b() instanceof ItemArmor) {
            int protectionLevel = EnchantmentHelper.func_77506_a((int)Enchantment.field_180310_c.field_77352_x, (ItemStack)stack);
            int unbreakingLevel2 = EnchantmentHelper.func_77506_a((int)Enchantment.field_77347_r.field_77352_x, (ItemStack)stack);
            int thornLevel = EnchantmentHelper.func_77506_a((int)Enchantment.field_92091_k.field_77352_x, (ItemStack)stack);
            if (protectionLevel > 0) {
                RenderUtils.drawEnchantTag("P" + ColorUtils.getColor(protectionLevel) + protectionLevel, x * 2, enchantmentY);
                enchantmentY += 8.0f;
            }
            if (unbreakingLevel2 > 0) {
                RenderUtils.drawEnchantTag("U" + ColorUtils.getColor(unbreakingLevel2) + unbreakingLevel2, x * 2, enchantmentY);
                enchantmentY += 8.0f;
            }
            if (thornLevel > 0) {
                RenderUtils.drawEnchantTag("T" + ColorUtils.getColor(thornLevel) + thornLevel, x * 2, enchantmentY);
                enchantmentY += 8.0f;
            }
        }
        if (stack.func_77973_b() instanceof ItemBow) {
            int powerLevel = EnchantmentHelper.func_77506_a((int)Enchantment.field_77345_t.field_77352_x, (ItemStack)stack);
            int punchLevel = EnchantmentHelper.func_77506_a((int)Enchantment.field_77344_u.field_77352_x, (ItemStack)stack);
            int flameLevel = EnchantmentHelper.func_77506_a((int)Enchantment.field_77343_v.field_77352_x, (ItemStack)stack);
            unbreakingLevel = EnchantmentHelper.func_77506_a((int)Enchantment.field_77347_r.field_77352_x, (ItemStack)stack);
            if (powerLevel > 0) {
                RenderUtils.drawEnchantTag("Pow" + ColorUtils.getColor(powerLevel) + powerLevel, x * 2, enchantmentY);
                enchantmentY += 8.0f;
            }
            if (punchLevel > 0) {
                RenderUtils.drawEnchantTag("Pun" + ColorUtils.getColor(punchLevel) + punchLevel, x * 2, enchantmentY);
                enchantmentY += 8.0f;
            }
            if (flameLevel > 0) {
                RenderUtils.drawEnchantTag("F" + ColorUtils.getColor(flameLevel) + flameLevel, x * 2, enchantmentY);
                enchantmentY += 8.0f;
            }
            if (unbreakingLevel > 0) {
                RenderUtils.drawEnchantTag("U" + ColorUtils.getColor(unbreakingLevel) + unbreakingLevel, x * 2, enchantmentY);
                enchantmentY += 8.0f;
            }
        }
        if (stack.func_77973_b() instanceof ItemSword) {
            int sharpnessLevel = EnchantmentHelper.func_77506_a((int)Enchantment.field_180314_l.field_77352_x, (ItemStack)stack);
            int knockbackLevel = EnchantmentHelper.func_77506_a((int)Enchantment.field_180313_o.field_77352_x, (ItemStack)stack);
            int fireAspectLevel = EnchantmentHelper.func_77506_a((int)Enchantment.field_77334_n.field_77352_x, (ItemStack)stack);
            unbreakingLevel = EnchantmentHelper.func_77506_a((int)Enchantment.field_77347_r.field_77352_x, (ItemStack)stack);
            if (sharpnessLevel > 0) {
                RenderUtils.drawEnchantTag("S" + ColorUtils.getColor(sharpnessLevel) + sharpnessLevel, x * 2, enchantmentY);
                enchantmentY += 8.0f;
            }
            if (knockbackLevel > 0) {
                RenderUtils.drawEnchantTag("K" + ColorUtils.getColor(knockbackLevel) + knockbackLevel, x * 2, enchantmentY);
                enchantmentY += 8.0f;
            }
            if (fireAspectLevel > 0) {
                RenderUtils.drawEnchantTag("F" + ColorUtils.getColor(fireAspectLevel) + fireAspectLevel, x * 2, enchantmentY);
                enchantmentY += 8.0f;
            }
            if (unbreakingLevel > 0) {
                RenderUtils.drawEnchantTag("U" + ColorUtils.getColor(unbreakingLevel) + unbreakingLevel, x * 2, enchantmentY);
                enchantmentY += 8.0f;
            }
        }
        if (stack.func_77953_t() == EnumRarity.EPIC) {
            GlStateManager.func_179094_E();
            GlStateManager.func_179097_i();
            GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
            RenderUtils.drawOutlinedStringCock(Minecraft.func_71410_x().field_71466_p, "God", x * 2, enchantmentY, new Color(255, 255, 0).getRGB(), new Color(100, 100, 0, 200).getRGB());
            GL11.glScalef((float)1.0f, (float)1.0f, (float)1.0f);
            GlStateManager.func_179126_j();
            GlStateManager.func_179121_F();
        }
    }

    public static Vec3 getRenderPos(double x, double y, double z) {
        return new Vec3(x -= RenderUtils.mc.func_175598_ae().field_78725_b, y -= RenderUtils.mc.func_175598_ae().field_78726_c, z -= RenderUtils.mc.func_175598_ae().field_78723_d);
    }

    public static void glVertex3D(Vec3 vector3d) {
        GL11.glVertex3d((double)vector3d.field_72450_a, (double)vector3d.field_72448_b, (double)vector3d.field_72449_c);
    }

    public static void drawBoundingBlock(AxisAlignedBB aa) {
        GL11.glBegin((int)7);
        RenderUtils.glVertex3D(RenderUtils.getRenderPos(aa.field_72340_a, aa.field_72338_b, aa.field_72339_c));
        RenderUtils.glVertex3D(RenderUtils.getRenderPos(aa.field_72340_a, aa.field_72337_e, aa.field_72339_c));
        RenderUtils.glVertex3D(RenderUtils.getRenderPos(aa.field_72336_d, aa.field_72338_b, aa.field_72339_c));
        RenderUtils.glVertex3D(RenderUtils.getRenderPos(aa.field_72336_d, aa.field_72337_e, aa.field_72339_c));
        RenderUtils.glVertex3D(RenderUtils.getRenderPos(aa.field_72336_d, aa.field_72338_b, aa.field_72334_f));
        RenderUtils.glVertex3D(RenderUtils.getRenderPos(aa.field_72336_d, aa.field_72337_e, aa.field_72334_f));
        RenderUtils.glVertex3D(RenderUtils.getRenderPos(aa.field_72340_a, aa.field_72338_b, aa.field_72334_f));
        RenderUtils.glVertex3D(RenderUtils.getRenderPos(aa.field_72340_a, aa.field_72337_e, aa.field_72334_f));
        RenderUtils.end();
        GL11.glBegin((int)7);
        RenderUtils.glVertex3D(RenderUtils.getRenderPos(aa.field_72336_d, aa.field_72337_e, aa.field_72339_c));
        RenderUtils.glVertex3D(RenderUtils.getRenderPos(aa.field_72336_d, aa.field_72338_b, aa.field_72339_c));
        RenderUtils.glVertex3D(RenderUtils.getRenderPos(aa.field_72340_a, aa.field_72337_e, aa.field_72339_c));
        RenderUtils.glVertex3D(RenderUtils.getRenderPos(aa.field_72340_a, aa.field_72338_b, aa.field_72339_c));
        RenderUtils.glVertex3D(RenderUtils.getRenderPos(aa.field_72340_a, aa.field_72337_e, aa.field_72334_f));
        RenderUtils.glVertex3D(RenderUtils.getRenderPos(aa.field_72340_a, aa.field_72338_b, aa.field_72334_f));
        RenderUtils.glVertex3D(RenderUtils.getRenderPos(aa.field_72336_d, aa.field_72337_e, aa.field_72334_f));
        RenderUtils.glVertex3D(RenderUtils.getRenderPos(aa.field_72336_d, aa.field_72338_b, aa.field_72334_f));
        RenderUtils.end();
        GL11.glBegin((int)7);
        RenderUtils.glVertex3D(RenderUtils.getRenderPos(aa.field_72340_a, aa.field_72337_e, aa.field_72339_c));
        RenderUtils.glVertex3D(RenderUtils.getRenderPos(aa.field_72336_d, aa.field_72337_e, aa.field_72339_c));
        RenderUtils.glVertex3D(RenderUtils.getRenderPos(aa.field_72336_d, aa.field_72337_e, aa.field_72334_f));
        RenderUtils.glVertex3D(RenderUtils.getRenderPos(aa.field_72340_a, aa.field_72337_e, aa.field_72334_f));
        RenderUtils.glVertex3D(RenderUtils.getRenderPos(aa.field_72340_a, aa.field_72337_e, aa.field_72339_c));
        RenderUtils.glVertex3D(RenderUtils.getRenderPos(aa.field_72340_a, aa.field_72337_e, aa.field_72334_f));
        RenderUtils.glVertex3D(RenderUtils.getRenderPos(aa.field_72336_d, aa.field_72337_e, aa.field_72334_f));
        RenderUtils.glVertex3D(RenderUtils.getRenderPos(aa.field_72336_d, aa.field_72337_e, aa.field_72339_c));
        RenderUtils.end();
        GL11.glBegin((int)7);
        RenderUtils.glVertex3D(RenderUtils.getRenderPos(aa.field_72340_a, aa.field_72338_b, aa.field_72339_c));
        RenderUtils.glVertex3D(RenderUtils.getRenderPos(aa.field_72336_d, aa.field_72338_b, aa.field_72339_c));
        RenderUtils.glVertex3D(RenderUtils.getRenderPos(aa.field_72336_d, aa.field_72338_b, aa.field_72334_f));
        RenderUtils.glVertex3D(RenderUtils.getRenderPos(aa.field_72340_a, aa.field_72338_b, aa.field_72334_f));
        RenderUtils.glVertex3D(RenderUtils.getRenderPos(aa.field_72340_a, aa.field_72338_b, aa.field_72339_c));
        RenderUtils.glVertex3D(RenderUtils.getRenderPos(aa.field_72340_a, aa.field_72338_b, aa.field_72334_f));
        RenderUtils.glVertex3D(RenderUtils.getRenderPos(aa.field_72336_d, aa.field_72338_b, aa.field_72334_f));
        RenderUtils.glVertex3D(RenderUtils.getRenderPos(aa.field_72336_d, aa.field_72338_b, aa.field_72339_c));
        RenderUtils.end();
        GL11.glBegin((int)7);
        RenderUtils.glVertex3D(RenderUtils.getRenderPos(aa.field_72340_a, aa.field_72338_b, aa.field_72339_c));
        RenderUtils.glVertex3D(RenderUtils.getRenderPos(aa.field_72340_a, aa.field_72337_e, aa.field_72339_c));
        RenderUtils.glVertex3D(RenderUtils.getRenderPos(aa.field_72340_a, aa.field_72338_b, aa.field_72334_f));
        RenderUtils.glVertex3D(RenderUtils.getRenderPos(aa.field_72340_a, aa.field_72337_e, aa.field_72334_f));
        RenderUtils.glVertex3D(RenderUtils.getRenderPos(aa.field_72336_d, aa.field_72338_b, aa.field_72334_f));
        RenderUtils.glVertex3D(RenderUtils.getRenderPos(aa.field_72336_d, aa.field_72337_e, aa.field_72334_f));
        RenderUtils.glVertex3D(RenderUtils.getRenderPos(aa.field_72336_d, aa.field_72338_b, aa.field_72339_c));
        RenderUtils.glVertex3D(RenderUtils.getRenderPos(aa.field_72336_d, aa.field_72337_e, aa.field_72339_c));
        RenderUtils.end();
        GL11.glBegin((int)7);
        RenderUtils.glVertex3D(RenderUtils.getRenderPos(aa.field_72340_a, aa.field_72337_e, aa.field_72334_f));
        RenderUtils.glVertex3D(RenderUtils.getRenderPos(aa.field_72340_a, aa.field_72338_b, aa.field_72334_f));
        RenderUtils.glVertex3D(RenderUtils.getRenderPos(aa.field_72340_a, aa.field_72337_e, aa.field_72339_c));
        RenderUtils.glVertex3D(RenderUtils.getRenderPos(aa.field_72340_a, aa.field_72338_b, aa.field_72339_c));
        RenderUtils.glVertex3D(RenderUtils.getRenderPos(aa.field_72336_d, aa.field_72337_e, aa.field_72339_c));
        RenderUtils.glVertex3D(RenderUtils.getRenderPos(aa.field_72336_d, aa.field_72338_b, aa.field_72339_c));
        RenderUtils.glVertex3D(RenderUtils.getRenderPos(aa.field_72336_d, aa.field_72337_e, aa.field_72334_f));
        RenderUtils.glVertex3D(RenderUtils.getRenderPos(aa.field_72336_d, aa.field_72338_b, aa.field_72334_f));
        RenderUtils.end();
    }

    public static boolean SupportBloom() {
        return Interface.INSTANCE.getBloomValue().get();
    }

    static {
        mainShader = null;
        lastWidth = 0.0f;
        lastHeight = 0.0f;
        lastStrength = 0.0f;
        mc = Minecraft.func_71410_x();
        blurDirectory = new ResourceLocation("crosssine/shadow.json");
        DISPLAY_LISTS_2D = new int[4];
        animationDuration = 500;
        for (int i = 0; i < DISPLAY_LISTS_2D.length; ++i) {
            RenderUtils.DISPLAY_LISTS_2D[i] = GL11.glGenLists((int)1);
        }
        GL11.glNewList((int)DISPLAY_LISTS_2D[0], (int)4864);
        RenderUtils.quickDrawRect(-7.0f, 2.0f, -4.0f, 3.0f);
        RenderUtils.quickDrawRect(4.0f, 2.0f, 7.0f, 3.0f);
        RenderUtils.quickDrawRect(-7.0f, 0.5f, -6.0f, 3.0f);
        RenderUtils.quickDrawRect(6.0f, 0.5f, 7.0f, 3.0f);
        GL11.glEndList();
        GL11.glNewList((int)DISPLAY_LISTS_2D[1], (int)4864);
        RenderUtils.quickDrawRect(-7.0f, 3.0f, -4.0f, 3.3f);
        RenderUtils.quickDrawRect(4.0f, 3.0f, 7.0f, 3.3f);
        RenderUtils.quickDrawRect(-7.3f, 0.5f, -7.0f, 3.3f);
        RenderUtils.quickDrawRect(7.0f, 0.5f, 7.3f, 3.3f);
        GL11.glEndList();
        GL11.glNewList((int)DISPLAY_LISTS_2D[2], (int)4864);
        RenderUtils.quickDrawRect(4.0f, -20.0f, 7.0f, -19.0f);
        RenderUtils.quickDrawRect(-7.0f, -20.0f, -4.0f, -19.0f);
        RenderUtils.quickDrawRect(6.0f, -20.0f, 7.0f, -17.5f);
        RenderUtils.quickDrawRect(-7.0f, -20.0f, -6.0f, -17.5f);
        GL11.glEndList();
        GL11.glNewList((int)DISPLAY_LISTS_2D[3], (int)4864);
        RenderUtils.quickDrawRect(7.0f, -20.0f, 7.3f, -17.5f);
        RenderUtils.quickDrawRect(-7.3f, -20.0f, -7.0f, -17.5f);
        RenderUtils.quickDrawRect(4.0f, -20.3f, 7.3f, -20.0f);
        RenderUtils.quickDrawRect(-7.3f, -20.3f, -4.0f, -20.0f);
        GL11.glEndList();
        zLevel = 0.0f;
        frustrum = new Frustum();
    }

    public static enum ShaderBloom {
        BLOOMONLY,
        ROUNDONLY,
        BOTH;

    }
}

