/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.utils.misc;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.imageio.ImageIO;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;

public class YouTubeThumbnailRenderer {
    private static final Minecraft mc = Minecraft.func_71410_x();
    private static final String THUMBNAIL_URL = "https://img.youtube.com/vi/%s/maxresdefault.jpg";
    private ResourceLocation textureResource;
    private boolean loading = false;
    private boolean failed = false;

    public void loadThumbnailAsync(String videoId) {
        if (this.loading || this.textureResource != null) {
            return;
        }
        this.loading = true;
        this.failed = false;
        new Thread(() -> {
            try {
                this.loadImageFromUrl(String.format(THUMBNAIL_URL, videoId));
            }
            catch (Exception e) {
                this.failed = true;
            }
            finally {
                this.loading = false;
            }
        }).start();
    }

    private void loadImageFromUrl(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        InputStream inputStream = url.openStream();
        BufferedImage bufferedImage = ImageIO.read(inputStream);
        IOUtils.closeQuietly((InputStream)inputStream);
        if (bufferedImage == null) {
            throw new IOException("Failed to load image");
        }
        mc.func_152344_a(() -> {
            if (this.textureResource != null) {
                mc.func_110434_K().func_147645_c(this.textureResource);
            }
            DynamicTexture dynamicTexture = new DynamicTexture(bufferedImage);
            this.textureResource = mc.func_110434_K().func_110578_a("yt_thumb_" + System.currentTimeMillis(), dynamicTexture);
        });
    }

    public void drawThumbnail(int x, int y, int width, int height) {
        if (this.textureResource != null) {
            RenderUtils.drawImage(this.textureResource, x, y, width, height);
        } else if (this.loading) {
            Fonts.font32SemiBold.drawCenteredString("Loading...", (float)x + (float)width / 2.0f, (float)y + (float)height / 2.0f - 4.0f, 0xFFFFFF);
        } else if (this.failed) {
            Fonts.font32SemiBold.drawCenteredString("No HD Thumbnail", (float)x + (float)width / 2.0f, (float)y + (float)height / 2.0f - 4.0f, 0xFF0000);
        }
    }

    public void cleanup() {
        if (this.textureResource != null) {
            mc.func_110434_K().func_147645_c(this.textureResource);
            this.textureResource = null;
        }
    }

    public boolean isReady() {
        return this.textureResource != null;
    }
}

