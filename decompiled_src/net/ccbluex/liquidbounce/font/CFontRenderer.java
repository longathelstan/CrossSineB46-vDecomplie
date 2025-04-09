/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.font;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.ccbluex.liquidbounce.font.CFont;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import org.lwjgl.opengl.GL11;

public class CFontRenderer
extends CFont {
    protected final CFont.CharData[] boldChars = new CFont.CharData[256];
    protected final CFont.CharData[] italicChars = new CFont.CharData[256];
    protected final CFont.CharData[] boldItalicChars = new CFont.CharData[256];
    private final int[] colorCode = new int[32];
    protected DynamicTexture texBold;
    protected DynamicTexture texItalic;
    protected DynamicTexture texItalicBold;

    public CFontRenderer(Font font, boolean antiAlias, boolean fractionalMetrics) {
        super(font, antiAlias, fractionalMetrics);
        this.setupMinecraftColorcodes();
        this.setupBoldItalicIDs();
    }

    public float drawStringWithShadow(String text, double x, double y, int color) {
        return Math.max(this.drawString(text, x + 0.5, y + 0.5, color, true), this.drawString(text, x, y, color, false));
    }

    public float drawString(String text, float x, float y, int color) {
        GlStateManager.func_179124_c((float)1.0f, (float)1.0f, (float)1.0f);
        RenderUtils.glColor(color);
        return this.drawString(text, (double)x, (double)y, color, false);
    }

    public float drawCenteredString(String text, double x, double y, int color) {
        GlStateManager.func_179124_c((float)1.0f, (float)1.0f, (float)1.0f);
        return this.drawString(text, (float)(x - (double)(this.getStringWidth(text) / 2)), (float)y, color);
    }

    public float drawCenteredStringWithShadow(String text, float x, float y, int color) {
        return this.drawStringWithShadow(text, x - (float)(this.getStringWidth(text) / 2), y, color);
    }

    public float drawCenteredStringWithShadow(String text, double x, double y, int color) {
        return this.drawStringWithShadow(text, x - (double)(this.getStringWidth(text) / 2), y, color);
    }

    public static boolean isChinese(char c) {
        String s = String.valueOf(c);
        return "1234567890abcdefghijklmnopqrstuvwxyz!<>@#$%^&*()-_=+[]{}|\\/'\",.~`".contains(s.toLowerCase());
    }

    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m2 = p.matcher(str);
        return m2.find();
    }

    public static char validateLegalString(String content) {
        String illegal = "`~!#%^&*=+\\|{};:'\",<>/?\u25cb\u25cf\u2605\u2606\u2609\u2640\u2642\u203b\u00a4\u256c\u306e\u3006";
        char isLegalChar = 't';
        for (int i = 0; i < content.length(); ++i) {
            for (int j = 0; j < illegal.length(); ++j) {
                if (content.charAt(i) != illegal.charAt(j)) continue;
                isLegalChar = content.charAt(i);
                return isLegalChar;
            }
        }
        return isLegalChar;
    }

    public static int DisplayFontWidth(String str, CFontRenderer font) {
        int x = 0;
        for (int iF = 0; iF < str.length(); ++iF) {
            String s = String.valueOf(str.toCharArray()[iF]);
            if (s.contains("\u00a7") && iF + 1 <= str.length()) {
                ++iF;
                continue;
            }
            x = CFontRenderer.isChinese(s.charAt(0)) ? (int)((float)x + (float)font.getStringWidth(s)) : (int)((float)x + (float)Fonts.font35.func_78256_a(s));
        }
        return x + 5;
    }

    public int DisplayFontWidths(CFontRenderer font, String str) {
        return this.DisplayFontWidths(str, font);
    }

    public int DisplayFontWidths(String str, CFontRenderer font) {
        int x = 0;
        for (int iF = 0; iF < str.length(); ++iF) {
            String s = String.valueOf(str.toCharArray()[iF]);
            if (s.contains("\u00a7") && iF + 1 <= str.length()) {
                ++iF;
                continue;
            }
            x = CFontRenderer.isChinese(s.charAt(0)) ? (int)((float)x + (float)font.getStringWidth(s)) : (int)((float)x + (float)Fonts.font35.func_78256_a(s));
        }
        return x + 5;
    }

    public static void DisplayFont(CFontRenderer font, String str, float x, float y, int color) {
        CFontRenderer.DisplayFont(str, x, y, color, font);
    }

    public static void DisplayFonts(CFontRenderer font, String str, float x, float y, int color) {
        CFontRenderer.DisplayFont(str, x, y, color, font);
    }

    public float DisplayFont2(CFontRenderer font, String str, float x, float y, int color, boolean shadow) {
        if (shadow) {
            return CFontRenderer.DisplayFont(str, x, y, color, true, font);
        }
        return CFontRenderer.DisplayFont(str, x, y, color, font);
    }

    public static float DisplayFont(String str, float x, float y, int color, boolean shadow, CFontRenderer font) {
        str = " " + str;
        for (int iF = 0; iF < str.length(); ++iF) {
            String s = String.valueOf(str.toCharArray()[iF]);
            if (s.contains("\u00a7") && iF + 1 <= str.length()) {
                color = CFontRenderer.getColor(String.valueOf(str.toCharArray()[iF + 1]));
                ++iF;
                continue;
            }
            if (CFontRenderer.isChinese(s.charAt(0))) {
                font.drawString(s, x + 0.5f, y + 1.5f, new Color(0, 0, 0, 100).getRGB());
                font.drawString(s, x - 0.5f, y + 0.5f, color);
                x += (float)font.getStringWidth(s);
                continue;
            }
            Fonts.font35.drawString(s, x + 1.5f, y + 2.0f, new Color(0, 0, 0, 50).getRGB());
            Fonts.font35.drawString(s, x + 0.5f, y + 1.0f, color);
            x += (float)Fonts.font35.func_78256_a(s);
        }
        return x;
    }

    public static float DisplayFont(String str, float x, float y, int color, CFontRenderer font) {
        str = " " + str;
        for (int iF = 0; iF < str.length(); ++iF) {
            String s = String.valueOf(str.toCharArray()[iF]);
            if (s.contains("\u00a7") && iF + 1 <= str.length()) {
                color = CFontRenderer.getColor(String.valueOf(str.toCharArray()[iF + 1]));
                ++iF;
                continue;
            }
            if (CFontRenderer.isChinese(s.charAt(0))) {
                font.drawString(s, x - 0.5f, y + 1.0f, color);
                x += (float)font.getStringWidth(s);
                continue;
            }
            Fonts.font35.drawString(s, x + 0.5f, y + 1.0f, color);
            x += (float)Fonts.font35.func_78256_a(s);
        }
        return x;
    }

    public float DisplayFonts(String str, float x, float y, int color, CFontRenderer font) {
        str = " " + str;
        for (int iF = 0; iF < str.length(); ++iF) {
            String s = String.valueOf(str.toCharArray()[iF]);
            if (s.contains("\u00a7") && iF + 1 <= str.length()) {
                color = CFontRenderer.getColor(String.valueOf(str.toCharArray()[iF + 1]));
                ++iF;
                continue;
            }
            if (CFontRenderer.isChinese(s.charAt(0))) {
                font.drawString(s, x - 0.5f, y + 1.0f, color);
                x += (float)font.getStringWidth(s);
                continue;
            }
            Fonts.font35.drawString(s, x + 0.5f, y + 1.0f, color);
            x += (float)Fonts.font35.func_78256_a(s);
        }
        return x;
    }

    public static int getColor(String str) {
        switch (str.hashCode()) {
            case 48: {
                if (!str.equals("0")) break;
                return new Color(0, 0, 0).getRGB();
            }
            case 49: {
                if (!str.equals("1")) break;
                return new Color(0, 0, 189).getRGB();
            }
            case 50: {
                if (!str.equals("2")) break;
                return new Color(0, 192, 0).getRGB();
            }
            case 51: {
                if (!str.equals("3")) break;
                return new Color(0, 190, 190).getRGB();
            }
            case 52: {
                if (!str.equals("4")) break;
                return new Color(190, 0, 0).getRGB();
            }
            case 53: {
                if (!str.equals("5")) break;
                return new Color(189, 0, 188).getRGB();
            }
            case 54: {
                if (!str.equals("6")) break;
                return new Color(218, 163, 47).getRGB();
            }
            case 55: {
                if (!str.equals("7")) break;
                return new Color(190, 190, 190).getRGB();
            }
            case 56: {
                if (!str.equals("8")) break;
                return new Color(63, 63, 63).getRGB();
            }
            case 57: {
                if (!str.equals("9")) break;
                return new Color(63, 64, 253).getRGB();
            }
            case 97: {
                if (!str.equals("a")) break;
                return new Color(63, 254, 63).getRGB();
            }
            case 98: {
                if (!str.equals("b")) break;
                return new Color(62, 255, 254).getRGB();
            }
            case 99: {
                if (!str.equals("c")) break;
                return new Color(254, 61, 62).getRGB();
            }
            case 100: {
                if (!str.equals("d")) break;
                return new Color(255, 64, 255).getRGB();
            }
            case 101: {
                if (!str.equals("e")) break;
                return new Color(254, 254, 62).getRGB();
            }
            case 102: {
                if (!str.equals("f")) break;
                return new Color(255, 255, 255).getRGB();
            }
        }
        return new Color(255, 255, 255).getRGB();
    }

    public float drawString(String text, float x, float y, int color, boolean shadow) {
        return this.drawString(text, (double)x, (double)y, color, shadow);
    }

    public float drawString(String text, double x, double y, int color, boolean shadow) {
        GlStateManager.func_179147_l();
        GlStateManager.func_179084_k();
        double x2 = x - 1.0;
        if (text == null) {
            return 0.0f;
        }
        if (color == 0x20FFFFFF) {
            color = 0xFFFFFF;
        }
        if ((color & 0xFC000000) == 0) {
            color |= 0xFF000000;
        }
        if (shadow) {
            color = new Color(0, 0, 0).getRGB();
        }
        CFont.CharData[] currentData = this.charData;
        float alpha = (float)(color >> 24 & 0xFF) / 255.0f;
        boolean bold = false;
        boolean italic = false;
        boolean strikethrough = false;
        boolean underline = false;
        char c = (char)(x2 * 2.0);
        double y2 = (y - 3.0) * 2.0;
        GL11.glPushMatrix();
        GlStateManager.func_179139_a((double)0.5, (double)0.5, (double)0.5);
        GlStateManager.func_179147_l();
        GlStateManager.func_179112_b((int)770, (int)771);
        GlStateManager.func_179131_c((float)((float)(color >> 16 & 0xFF) / 255.0f), (float)((float)(color >> 8 & 0xFF) / 255.0f), (float)((float)(color & 0xFF) / 255.0f), (float)alpha);
        int size = text.length();
        GlStateManager.func_179098_w();
        GlStateManager.func_179144_i((int)this.tex.func_110552_b());
        GL11.glBindTexture((int)3553, (int)this.tex.func_110552_b());
        for (int i = 0; i < size; ++i) {
            char character = text.charAt(i);
            if (character == '\u00a7') {
                int colorIndex = 21;
                try {
                    colorIndex = "0123456789abcdefklmnor".indexOf(text.charAt(i + 1));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                if (colorIndex < 16) {
                    bold = false;
                    italic = false;
                    underline = false;
                    strikethrough = false;
                    GlStateManager.func_179144_i((int)this.tex.func_110552_b());
                    currentData = this.charData;
                    if (colorIndex < 0) {
                        colorIndex = 15;
                    }
                    if (shadow) {
                        colorIndex += 16;
                    }
                    int colorcode = this.colorCode[colorIndex];
                    GlStateManager.func_179131_c((float)((float)(colorcode >> 16 & 0xFF) / 255.0f), (float)((float)(colorcode >> 8 & 0xFF) / 255.0f), (float)((float)(colorcode & 0xFF) / 255.0f), (float)alpha);
                } else if (colorIndex != 16) {
                    if (colorIndex == 17) {
                        bold = true;
                        if (italic) {
                            GlStateManager.func_179144_i((int)this.texItalicBold.func_110552_b());
                            currentData = this.boldItalicChars;
                        } else {
                            GlStateManager.func_179144_i((int)this.texBold.func_110552_b());
                            currentData = this.boldChars;
                        }
                    } else if (colorIndex == 18) {
                        strikethrough = true;
                    } else if (colorIndex == 19) {
                        underline = true;
                    } else if (colorIndex == 20) {
                        italic = true;
                        if (bold) {
                            GlStateManager.func_179144_i((int)this.texItalicBold.func_110552_b());
                            currentData = this.boldItalicChars;
                        } else {
                            GlStateManager.func_179144_i((int)this.texItalic.func_110552_b());
                            currentData = this.italicChars;
                        }
                    } else {
                        bold = false;
                        italic = false;
                        underline = false;
                        strikethrough = false;
                        GlStateManager.func_179131_c((float)((float)(color >> 16 & 0xFF) / 255.0f), (float)((float)(color >> 8 & 0xFF) / 255.0f), (float)((float)(color & 0xFF) / 255.0f), (float)alpha);
                        GlStateManager.func_179144_i((int)this.tex.func_110552_b());
                        currentData = this.charData;
                    }
                }
                ++i;
                continue;
            }
            if (character >= currentData.length) continue;
            GL11.glBegin((int)4);
            this.drawChar(currentData, character, c, (float)y2);
            GL11.glEnd();
            if (strikethrough) {
                this.drawLine(c, y2 + (double)(currentData[character].height / 2), (double)c + (double)currentData[character].width - 8.0, y2 + (double)(currentData[character].height / 2));
            }
            if (underline) {
                this.drawLine(c, y2 + (double)currentData[character].height - 2.0, (double)c + (double)currentData[character].width - 8.0, y2 + (double)currentData[character].height - 2.0);
            }
            double d = c;
            int n = currentData[character].width - 8;
            this.getClass();
            c = (char)(d + (double)(n + 0));
        }
        GL11.glHint((int)3155, (int)4352);
        GL11.glPopMatrix();
        return (float)c / 2.0f;
    }

    public int drawStringi(String text, double x, double y, int color, boolean shadow) {
        GlStateManager.func_179147_l();
        GlStateManager.func_179084_k();
        double x2 = x - 1.0;
        if (text == null) {
            return 0;
        }
        if (color == 0x20FFFFFF) {
            color = 0xFFFFFF;
        }
        if ((color & 0xFC000000) == 0) {
            color |= 0xFF000000;
        }
        if (shadow) {
            color = new Color(0, 0, 0).getRGB();
        }
        CFont.CharData[] currentData = this.charData;
        float alpha = (float)(color >> 24 & 0xFF) / 255.0f;
        boolean bold = false;
        boolean italic = false;
        boolean strikethrough = false;
        boolean underline = false;
        char c = (char)(x2 * 2.0);
        double y2 = (y - 3.0) * 2.0;
        GL11.glPushMatrix();
        GlStateManager.func_179139_a((double)0.5, (double)0.5, (double)0.5);
        GlStateManager.func_179147_l();
        GlStateManager.func_179112_b((int)770, (int)771);
        GlStateManager.func_179131_c((float)((float)(color >> 16 & 0xFF) / 255.0f), (float)((float)(color >> 8 & 0xFF) / 255.0f), (float)((float)(color & 0xFF) / 255.0f), (float)alpha);
        int size = text.length();
        GlStateManager.func_179098_w();
        GlStateManager.func_179144_i((int)this.tex.func_110552_b());
        GL11.glBindTexture((int)3553, (int)this.tex.func_110552_b());
        for (int i = 0; i < size; ++i) {
            char character = text.charAt(i);
            if (character == '\u00a7') {
                int colorIndex = 21;
                try {
                    colorIndex = "0123456789abcdefklmnor".indexOf(text.charAt(i + 1));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                if (colorIndex < 16) {
                    bold = false;
                    italic = false;
                    underline = false;
                    strikethrough = false;
                    GlStateManager.func_179144_i((int)this.tex.func_110552_b());
                    currentData = this.charData;
                    if (colorIndex < 0) {
                        colorIndex = 15;
                    }
                    if (shadow) {
                        colorIndex += 16;
                    }
                    int colorcode = this.colorCode[colorIndex];
                    GlStateManager.func_179131_c((float)((float)(colorcode >> 16 & 0xFF) / 255.0f), (float)((float)(colorcode >> 8 & 0xFF) / 255.0f), (float)((float)(colorcode & 0xFF) / 255.0f), (float)alpha);
                } else if (colorIndex != 16) {
                    if (colorIndex == 17) {
                        bold = true;
                        if (italic) {
                            GlStateManager.func_179144_i((int)this.texItalicBold.func_110552_b());
                            currentData = this.boldItalicChars;
                        } else {
                            GlStateManager.func_179144_i((int)this.texBold.func_110552_b());
                            currentData = this.boldChars;
                        }
                    } else if (colorIndex == 18) {
                        strikethrough = true;
                    } else if (colorIndex == 19) {
                        underline = true;
                    } else if (colorIndex == 20) {
                        italic = true;
                        if (bold) {
                            GlStateManager.func_179144_i((int)this.texItalicBold.func_110552_b());
                            currentData = this.boldItalicChars;
                        } else {
                            GlStateManager.func_179144_i((int)this.texItalic.func_110552_b());
                            currentData = this.italicChars;
                        }
                    } else {
                        bold = false;
                        italic = false;
                        underline = false;
                        strikethrough = false;
                        GlStateManager.func_179131_c((float)((float)(color >> 16 & 0xFF) / 255.0f), (float)((float)(color >> 8 & 0xFF) / 255.0f), (float)((float)(color & 0xFF) / 255.0f), (float)alpha);
                        GlStateManager.func_179144_i((int)this.tex.func_110552_b());
                        currentData = this.charData;
                    }
                }
                ++i;
                continue;
            }
            if (character >= currentData.length) continue;
            GL11.glBegin((int)4);
            this.drawChar(currentData, character, c, (float)y2);
            GL11.glEnd();
            if (strikethrough) {
                this.drawLine(c, y2 + (double)(currentData[character].height / 2), (double)c + (double)currentData[character].width - 8.0, y2 + (double)(currentData[character].height / 2));
            }
            if (underline) {
                this.drawLine(c, y2 + (double)currentData[character].height - 2.0, (double)c + (double)currentData[character].width - 8.0, y2 + (double)currentData[character].height - 2.0);
            }
            double d = c;
            int n = currentData[character].width - 8;
            this.getClass();
            c = (char)(d + (double)(n + 0));
        }
        GL11.glHint((int)3155, (int)4352);
        GL11.glPopMatrix();
        return c / 2;
    }

    @Override
    public int getStringWidth(String text) {
        if (text == null) {
            return 0;
        }
        int width = 0;
        CFont.CharData[] currentData = this.charData;
        boolean bold = false;
        boolean italic = false;
        int size = text.length();
        for (int i = 0; i < size; ++i) {
            char character = text.charAt(i);
            if (character == '\u00a7') {
                int colorIndex = "0123456789abcdefklmnor".indexOf(character);
                bold = false;
                italic = false;
                ++i;
                continue;
            }
            if (character >= currentData.length) continue;
            int n = currentData[character].width - 8;
            this.getClass();
            width += n + 0;
        }
        return width / 2;
    }

    @Override
    public void setFont(Font font) {
        this.setFont(font);
        this.setupBoldItalicIDs();
    }

    @Override
    public void setAntiAlias(boolean antiAlias) {
        this.setAntiAlias(antiAlias);
        this.setupBoldItalicIDs();
    }

    @Override
    public void setFractionalMetrics(boolean fractionalMetrics) {
        this.setFractionalMetrics(fractionalMetrics);
        this.setupBoldItalicIDs();
    }

    private void setupBoldItalicIDs() {
        this.texBold = this.setupTexture(this.font.deriveFont(1), this.antiAlias, this.fractionalMetrics, this.boldChars);
        this.texItalic = this.setupTexture(this.font.deriveFont(2), this.antiAlias, this.fractionalMetrics, this.italicChars);
    }

    private void drawLine(double x, double y, double x1, double y1) {
        GL11.glDisable((int)3553);
        GL11.glLineWidth((float)1.0f);
        GL11.glBegin((int)1);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glVertex2d((double)x1, (double)y1);
        GL11.glEnd();
        GL11.glEnable((int)3553);
    }

    public List<String> wrapWords(String text, double width) {
        ArrayList<String> finalWords = new ArrayList<String>();
        if ((double)this.getStringWidth(text) > width) {
            String[] words = text.split(" ");
            String currentWord = "";
            int c = 65535;
            for (String word : words) {
                for (int i = 0; i < word.toCharArray().length; ++i) {
                    if (word.toCharArray()[i] != '\u00a7' || i >= word.toCharArray().length - 1) continue;
                    c = word.toCharArray()[i + 1];
                }
                StringBuilder stringBuilder = new StringBuilder();
                if ((double)this.getStringWidth(stringBuilder.append(currentWord).append(word).append(" ").toString()) < width) {
                    currentWord = currentWord + word + " ";
                    continue;
                }
                finalWords.add(currentWord);
                currentWord = 167 + c + word + " ";
            }
            if (currentWord.length() > 0) {
                if ((double)this.getStringWidth(currentWord) < width) {
                    finalWords.add(167 + c + currentWord + " ");
                } else {
                    finalWords.addAll(this.formatString(currentWord, width));
                }
            }
        } else {
            finalWords.add(text);
        }
        return finalWords;
    }

    public List<String> formatString(String string, double width) {
        ArrayList<String> finalWords = new ArrayList<String>();
        String currentWord = "";
        int lastColorCode = 65535;
        char[] chars = string.toCharArray();
        for (int i = 0; i < chars.length; ++i) {
            char c = chars[i];
            if (c == '\u00a7' && i < chars.length - 1) {
                lastColorCode = chars[i + 1];
            }
            StringBuilder stringBuilder = new StringBuilder();
            if ((double)this.getStringWidth(stringBuilder.append(currentWord).append(c).toString()) < width) {
                currentWord = currentWord + c;
                continue;
            }
            finalWords.add(currentWord);
            currentWord = String.valueOf(167 + lastColorCode) + c;
        }
        if (currentWord.length() > 0) {
            finalWords.add(currentWord);
        }
        return finalWords;
    }

    private void setupMinecraftColorcodes() {
        for (int index2 = 0; index2 < 32; ++index2) {
            int noClue = (index2 >> 3 & 1) * 85;
            int red2 = (index2 >> 2 & 1) * 170 + noClue;
            int green2 = (index2 >> 1 & 1) * 170 + noClue;
            int blue2 = (index2 & 1) * 170 + noClue;
            if (index2 == 6) {
                red2 += 85;
            }
            if (index2 >= 16) {
                red2 /= 4;
                green2 /= 4;
                blue2 /= 4;
            }
            this.colorCode[index2] = (red2 & 0xFF) << 16 | (green2 & 0xFF) << 8 | blue2 & 0xFF;
        }
    }
}

