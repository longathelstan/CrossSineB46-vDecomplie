/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.font;

import java.awt.Font;
import java.util.ArrayList;
import net.ccbluex.liquidbounce.font.CFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public abstract class FontLoaders {
    public static final CFontRenderer F16 = new CFontRenderer(FontLoaders.getFont(16), true, true);
    public static final CFontRenderer F18 = new CFontRenderer(FontLoaders.getFont(18), true, true);
    public static final CFontRenderer F24 = new CFontRenderer(FontLoaders.getFont(24), true, true);
    public static final CFontRenderer SF16 = new CFontRenderer(FontLoaders.getSF(16), true, true);
    public static final CFontRenderer SF18 = new CFontRenderer(FontLoaders.getSF(18), true, true);
    public static final CFontRenderer SF24 = new CFontRenderer(FontLoaders.getSF(24), true, true);
    public static final CFontRenderer SF30 = new CFontRenderer(FontLoaders.getSF(30), true, true);
    public static final CFontRenderer SF35 = new CFontRenderer(FontLoaders.getSF(35), true, true);
    public static final ArrayList<CFontRenderer> fonts = new ArrayList();

    public static Font getFont(int size) {
        Font font;
        try {
            font = Font.createFont(0, Minecraft.func_71410_x().func_110442_L().func_110536_a(new ResourceLocation("crosssine/font/Urbanist-Medium.ttf")).func_110527_b()).deriveFont(0, size);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, size);
        }
        return font;
    }

    public static Font getSF(int size) {
        Font font;
        try {
            font = Font.createFont(0, Minecraft.func_71410_x().func_110442_L().func_110536_a(new ResourceLocation("crosssine/font/SFSemiBold.ttf")).func_110527_b()).deriveFont(0, size);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error loading font");
            font = new Font("default", 0, size);
        }
        return font;
    }
}

