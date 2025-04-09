/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.ui.font;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.ui.font.FontDetails;
import net.ccbluex.liquidbounce.ui.font.GameFontRenderer;
import net.ccbluex.liquidbounce.ui.font.TTFFontRenderer;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.FileUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;

public class Fonts {
    @FontDetails(fontName="Urbanist", fontSize=20)
    public static GameFontRenderer font20;
    @FontDetails(fontName="Urbanist", fontSize=24)
    public static GameFontRenderer font24;
    @FontDetails(fontName="Urbanist", fontSize=30)
    public static GameFontRenderer font30;
    @FontDetails(fontName="Urbanist", fontSize=32)
    public static GameFontRenderer font32;
    @FontDetails(fontName="Urbanist", fontSize=35)
    public static GameFontRenderer font35;
    @FontDetails(fontName="Urbanist", fontSize=40)
    public static GameFontRenderer font40;
    @FontDetails(fontName="UrbanistBold", fontSize=20)
    public static GameFontRenderer font20Bold;
    @FontDetails(fontName="UrbanistBold", fontSize=24)
    public static GameFontRenderer font24Bold;
    @FontDetails(fontName="UrbanistBold", fontSize=30)
    public static GameFontRenderer font30Bold;
    @FontDetails(fontName="UrbanistBold", fontSize=32)
    public static GameFontRenderer font32Bold;
    @FontDetails(fontName="UrbanistBold", fontSize=35)
    public static GameFontRenderer font35Bold;
    @FontDetails(fontName="UrbanistBold", fontSize=40)
    public static GameFontRenderer font40Bold;
    @FontDetails(fontName="UrbanistSemiBold", fontSize=20)
    public static GameFontRenderer font20SemiBold;
    @FontDetails(fontName="UrbanistSemiBold", fontSize=24)
    public static GameFontRenderer font24SemiBold;
    @FontDetails(fontName="UrbanistSemiBold", fontSize=30)
    public static GameFontRenderer font30SemiBold;
    @FontDetails(fontName="UrbanistSemiBold", fontSize=32)
    public static GameFontRenderer font32SemiBold;
    @FontDetails(fontName="UrbanistSemiBold", fontSize=35)
    public static GameFontRenderer font35SemiBold;
    @FontDetails(fontName="UrbanistSemiBold", fontSize=40)
    public static GameFontRenderer font40SemiBold;
    @FontDetails(fontName="UrbanistSemiBold", fontSize=50)
    public static GameFontRenderer font50SemiBold;
    @FontDetails(fontName="SF", fontSize=35)
    public static GameFontRenderer fontSFUI35;
    @FontDetails(fontName="SF", fontSize=40)
    public static GameFontRenderer fontSFUI40;
    public static TTFFontRenderer fontTahomaSmall;
    @FontDetails(fontName="Bangers", fontSize=45)
    public static GameFontRenderer fontBangers;
    @FontDetails(fontName="Minecraft Font")
    public static final FontRenderer minecraftFont;
    @FontDetails(fontName="Tenacity35", fontSize=35)
    public static GameFontRenderer fontTenacity35;
    @FontDetails(fontName="TenacityBold35", fontSize=35)
    public static GameFontRenderer fontTenacityBold35;
    @FontDetails(fontName="tenacity40", fontSize=40)
    public static GameFontRenderer fontTenacity40;
    @FontDetails(fontName="tenacityBold40", fontSize=40)
    public static GameFontRenderer fontTenacityBold40;
    @FontDetails(fontName="TenacityIcon30", fontSize=30)
    public static GameFontRenderer fontTenacityIcon30;
    @FontDetails(fontName="Comfortaa35", fontSize=35)
    public static GameFontRenderer fontComfortaa35;
    @FontDetails(fontName="Comfortaa40", fontSize=40)
    public static GameFontRenderer fontComfortaa40;
    @FontDetails(fontName="RockoFLFBold35", fontSize=35)
    public static GameFontRenderer fontRockoFLF35;
    @FontDetails(fontName="RockoFLFBold40", fontSize=40)
    public static GameFontRenderer fontRockoFLF40;
    @FontDetails(fontName="Nunito24", fontSize=24)
    public static GameFontRenderer Nunito24;
    @FontDetails(fontName="Nunito30", fontSize=30)
    public static GameFontRenderer Nunito30;
    @FontDetails(fontName="Nunito35", fontSize=35)
    public static GameFontRenderer Nunito35;
    @FontDetails(fontName="Nunito40", fontSize=40)
    public static GameFontRenderer Nunito40;
    @FontDetails(fontName="Nunito50", fontSize=50)
    public static GameFontRenderer Nunito50;
    @FontDetails(fontName="Nunito60", fontSize=60)
    public static GameFontRenderer Nunito60;
    @FontDetails(fontName="SFApple24", fontSize=24)
    public static GameFontRenderer SFApple24;
    @FontDetails(fontName="SFApple30", fontSize=30)
    public static GameFontRenderer SFApple30;
    @FontDetails(fontName="SFApple35", fontSize=35)
    public static GameFontRenderer SFApple35;
    @FontDetails(fontName="SFApple40", fontSize=40)
    public static GameFontRenderer SFApple40;
    @FontDetails(fontName="SFApple50", fontSize=50)
    public static GameFontRenderer SFApple50;
    @FontDetails(fontName="Nova24", fontSize=24)
    public static GameFontRenderer Nova24;
    @FontDetails(fontName="Nova30", fontSize=30)
    public static GameFontRenderer Nova30;
    @FontDetails(fontName="Nova30", fontSize=32)
    public static GameFontRenderer Nova32;
    @FontDetails(fontName="Nova35", fontSize=35)
    public static GameFontRenderer Nova35;
    @FontDetails(fontName="Nova40", fontSize=40)
    public static GameFontRenderer Nova40;
    @FontDetails(fontName="Nova50", fontSize=50)
    public static GameFontRenderer Nova50;
    @FontDetails(fontName="SFBold24", fontSize=24)
    public static GameFontRenderer SFBold24;
    @FontDetails(fontName="SFBold30", fontSize=30)
    public static GameFontRenderer SFBold30;
    @FontDetails(fontName="SFBold35", fontSize=35)
    public static GameFontRenderer SFBold35;
    @FontDetails(fontName="SFBold40", fontSize=40)
    public static GameFontRenderer SFBold40;
    @FontDetails(fontName="SFBold50", fontSize=50)
    public static GameFontRenderer SFBold50;
    private static final List<GameFontRenderer> CUSTOM_FONT_RENDERERS;

    public static void loadFonts() {
        long l = System.currentTimeMillis();
        ClientUtils.INSTANCE.logInfo("Loading Fonts.");
        fontSFUI35 = new GameFontRenderer(Fonts.getFont("SF.ttf", 35));
        fontSFUI40 = new GameFontRenderer(Fonts.getFont("SF.ttf", 40));
        fontSFUI35 = new GameFontRenderer(Fonts.getFont("SF.ttf", 35));
        fontSFUI40 = new GameFontRenderer(Fonts.getFont("SF.ttf", 40));
        fontTahomaSmall = new TTFFontRenderer(Fonts.getFont("Tahoma.ttf", 11));
        fontBangers = new GameFontRenderer(Fonts.getFontcustom(45, "Bangers"));
        fontTenacity35 = new GameFontRenderer(Fonts.getFontcustom(35, "tenacity"));
        fontTenacityBold35 = new GameFontRenderer(Fonts.getFontcustom(35, "tenacity-bold"));
        fontTenacityIcon30 = new GameFontRenderer(Fonts.getFontcustom(30, "Tenacityicon"));
        fontTenacity40 = new GameFontRenderer(Fonts.getFontcustom(40, "tenacity"));
        fontTenacityBold40 = new GameFontRenderer(Fonts.getFontcustom(40, "tenacity-bold"));
        fontComfortaa35 = new GameFontRenderer(Fonts.getFontcustom(35, "Comfortaa"));
        fontComfortaa40 = new GameFontRenderer(Fonts.getFontcustom(40, "Comfortaa"));
        fontRockoFLF35 = new GameFontRenderer(Fonts.getFontcustom(35, "RockoFLF-Bold"));
        fontRockoFLF40 = new GameFontRenderer(Fonts.getFontcustom(40, "RockoFLF-Bold"));
        Nunito24 = new GameFontRenderer(Fonts.getFontcustom(24, "Nunito"));
        Nunito30 = new GameFontRenderer(Fonts.getFontcustom(30, "Nunito"));
        Nunito35 = new GameFontRenderer(Fonts.getFontcustom(35, "Nunito"));
        Nunito40 = new GameFontRenderer(Fonts.getFontcustom(40, "Nunito"));
        Nunito50 = new GameFontRenderer(Fonts.getFontcustom(50, "Nunito"));
        Nunito60 = new GameFontRenderer(Fonts.getFontcustom(60, "Nunito"));
        SFApple40 = new GameFontRenderer(Fonts.getFontcustom(40, "SFApple"));
        SFApple30 = new GameFontRenderer(Fonts.getFontcustom(30, "SFApple"));
        SFApple35 = new GameFontRenderer(Fonts.getFontcustom(35, "SFApple"));
        SFApple50 = new GameFontRenderer(Fonts.getFontcustom(50, "SFApple"));
        SFApple24 = new GameFontRenderer(Fonts.getFontcustom(24, "SFApple"));
        Nova40 = new GameFontRenderer(Fonts.getFontcustom(40, "ProximaNova"));
        Nova30 = new GameFontRenderer(Fonts.getFontcustom(30, "ProximaNova"));
        Nova35 = new GameFontRenderer(Fonts.getFontcustom(35, "ProximaNova"));
        Nova50 = new GameFontRenderer(Fonts.getFontcustom(50, "ProximaNova"));
        Nova24 = new GameFontRenderer(Fonts.getFontcustom(24, "ProximaNova"));
        SFBold24 = new GameFontRenderer(Fonts.getFontcustom(24, "SFSemiBold"));
        SFBold30 = new GameFontRenderer(Fonts.getFontcustom(30, "SFSemiBold"));
        SFBold30 = new GameFontRenderer(Fonts.getFontcustom(32, "SFSemiBold"));
        SFBold35 = new GameFontRenderer(Fonts.getFontcustom(35, "SFSemiBold"));
        SFBold40 = new GameFontRenderer(Fonts.getFontcustom(40, "SFSemiBold"));
        SFBold50 = new GameFontRenderer(Fonts.getFontcustom(50, "SFSemiBold"));
        font20 = new GameFontRenderer(Fonts.getFontcustom(20, "Urbanist-Medium"));
        font24 = new GameFontRenderer(Fonts.getFontcustom(24, "Urbanist-Medium"));
        font30 = new GameFontRenderer(Fonts.getFontcustom(30, "Urbanist-Medium"));
        font32 = new GameFontRenderer(Fonts.getFontcustom(32, "Urbanist-Medium"));
        font35 = new GameFontRenderer(Fonts.getFontcustom(35, "Urbanist-Medium"));
        font40 = new GameFontRenderer(Fonts.getFontcustom(40, "Urbanist-Medium"));
        font20Bold = new GameFontRenderer(Fonts.getFontcustom(20, "Urbanist-Bold"));
        font24Bold = new GameFontRenderer(Fonts.getFontcustom(24, "Urbanist-Bold"));
        font30Bold = new GameFontRenderer(Fonts.getFontcustom(30, "Urbanist-Bold"));
        font32Bold = new GameFontRenderer(Fonts.getFontcustom(32, "Urbanist-Bold"));
        font35Bold = new GameFontRenderer(Fonts.getFontcustom(35, "Urbanist-Bold"));
        font40Bold = new GameFontRenderer(Fonts.getFontcustom(40, "Urbanist-Bold"));
        font20SemiBold = new GameFontRenderer(Fonts.getFontcustom(20, "Urbanist-SemiBold"));
        font24SemiBold = new GameFontRenderer(Fonts.getFontcustom(24, "Urbanist-SemiBold"));
        font30SemiBold = new GameFontRenderer(Fonts.getFontcustom(30, "Urbanist-SemiBold"));
        font32SemiBold = new GameFontRenderer(Fonts.getFontcustom(32, "Urbanist-SemiBold"));
        font35SemiBold = new GameFontRenderer(Fonts.getFontcustom(35, "Urbanist-SemiBold"));
        font40SemiBold = new GameFontRenderer(Fonts.getFontcustom(40, "Urbanist-SemiBold"));
        font50SemiBold = new GameFontRenderer(Fonts.getFontcustom(50, "Urbanist-SemiBold"));
        Fonts.getCustomFonts();
        Fonts.initFonts();
        for (Field field : Fonts.class.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                FontDetails fontDetails = field.getAnnotation(FontDetails.class);
                if (fontDetails == null || fontDetails.fileName().isEmpty()) continue;
                field.set(null, (Object)new GameFontRenderer(Fonts.getFont(fontDetails.fileName(), fontDetails.fontSize())));
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        try {
            CUSTOM_FONT_RENDERERS.clear();
            File fontsFile = new File(CrossSine.fileManager.getFontsDir(), "fonts.json");
            if (fontsFile.exists()) {
                JsonElement jsonElement = new JsonParser().parse((Reader)new BufferedReader(new FileReader(fontsFile)));
                if (jsonElement instanceof JsonNull) {
                    return;
                }
                JsonArray jsonArray = (JsonArray)jsonElement;
                for (JsonElement element : jsonArray) {
                    if (element instanceof JsonNull) {
                        return;
                    }
                    JsonObject fontObject = (JsonObject)element;
                    CUSTOM_FONT_RENDERERS.add(new GameFontRenderer(Fonts.getFont(fontObject.get("fontFile").getAsString(), fontObject.get("fontSize").getAsInt())));
                }
            } else {
                fontsFile.createNewFile();
                PrintWriter printWriter = new PrintWriter(new FileWriter(fontsFile));
                printWriter.println(new GsonBuilder().setPrettyPrinting().create().toJson((JsonElement)new JsonArray()));
                printWriter.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        ClientUtils.INSTANCE.logInfo("Loaded Fonts. (" + (System.currentTimeMillis() - l) + "ms)");
    }

    private static void initFonts() {
        try {
            Fonts.initSingleFont("Urbanist-Medium.ttf", "assets/minecraft/crosssine/font/Urbanist-Medium.ttf");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Font getFontcustom(int size, String fontname) {
        Font font;
        try {
            InputStream is = Minecraft.func_71410_x().func_110442_L().func_110536_a(new ResourceLocation("crosssine/font/" + fontname + ".ttf")).func_110527_b();
            font = Font.createFont(0, is);
            font = font.deriveFont(0, size);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error loading font: " + fontname);
            font = new Font("default", 0, size);
        }
        return font;
    }

    private static void initSingleFont(String name, String resourcePath) throws IOException {
        File file = new File(CrossSine.fileManager.getFontsDir(), name);
        if (!file.exists()) {
            FileUtils.INSTANCE.unpackFile(file, resourcePath);
        }
    }

    public static FontRenderer getFontRenderer(String name, int size) {
        for (Field field : Fonts.class.getDeclaredFields()) {
            try {
                FontDetails fontDetails;
                field.setAccessible(true);
                Object o = field.get(null);
                if (!(o instanceof FontRenderer) || !(fontDetails = field.getAnnotation(FontDetails.class)).fontName().equals(name) || fontDetails.fontSize() != size) continue;
                return (FontRenderer)o;
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        for (GameFontRenderer liquidFontRenderer : CUSTOM_FONT_RENDERERS) {
            Font font = liquidFontRenderer.getDefaultFont().getFont();
            if (!font.getName().equals(name) || font.getSize() != size) continue;
            return liquidFontRenderer;
        }
        return minecraftFont;
    }

    public static Object[] getFontDetails(FontRenderer fontRenderer) {
        for (Field field : Fonts.class.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Object o = field.get(null);
                if (!o.equals(fontRenderer)) continue;
                FontDetails fontDetails = field.getAnnotation(FontDetails.class);
                return new Object[]{fontDetails.fontName(), fontDetails.fontSize()};
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (fontRenderer instanceof GameFontRenderer) {
            Font font = ((GameFontRenderer)fontRenderer).getDefaultFont().getFont();
            return new Object[]{font.getName(), font.getSize()};
        }
        return null;
    }

    public static List<FontRenderer> getFonts() {
        ArrayList<FontRenderer> fonts = new ArrayList<FontRenderer>();
        for (Field fontField : Fonts.class.getDeclaredFields()) {
            try {
                fontField.setAccessible(true);
                Object fontObj = fontField.get(null);
                if (!(fontObj instanceof FontRenderer)) continue;
                fonts.add((FontRenderer)fontObj);
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        fonts.addAll(CUSTOM_FONT_RENDERERS);
        return fonts;
    }

    public static List<GameFontRenderer> getCustomFonts() {
        ArrayList<GameFontRenderer> fonts = new ArrayList<GameFontRenderer>();
        for (Field fontField : Fonts.class.getDeclaredFields()) {
            try {
                fontField.setAccessible(true);
                Object fontObj = fontField.get(null);
                if (!(fontObj instanceof GameFontRenderer)) continue;
                fonts.add((GameFontRenderer)((Object)fontObj));
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        fonts.addAll(CUSTOM_FONT_RENDERERS);
        return fonts;
    }

    private static Font getFont(String fontName, int size) {
        try {
            FileInputStream inputStream = new FileInputStream(new File(CrossSine.fileManager.getFontsDir(), fontName));
            Font awtClientFont = Font.createFont(0, inputStream);
            awtClientFont = awtClientFont.deriveFont(0, size);
            ((InputStream)inputStream).close();
            return awtClientFont;
        }
        catch (Exception e) {
            e.printStackTrace();
            return new Font("default", 0, size);
        }
    }

    static {
        minecraftFont = Minecraft.func_71410_x().field_71466_p;
        CUSTOM_FONT_RENDERERS = new ArrayList<GameFontRenderer>();
    }
}

