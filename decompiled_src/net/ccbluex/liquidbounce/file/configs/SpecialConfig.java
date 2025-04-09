/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.file.configs;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.features.special.AutoReconnect;
import net.ccbluex.liquidbounce.file.FileConfig;
import net.ccbluex.liquidbounce.file.FileManager;
import net.ccbluex.liquidbounce.ui.client.altmanager.GuiAltManager;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\bH\u0016\u00a8\u0006\n"}, d2={"Lnet/ccbluex/liquidbounce/file/configs/SpecialConfig;", "Lnet/ccbluex/liquidbounce/file/FileConfig;", "file", "Ljava/io/File;", "(Ljava/io/File;)V", "loadConfig", "", "config", "", "saveConfig", "CrossSine"})
public final class SpecialConfig
extends FileConfig {
    public SpecialConfig(@NotNull File file) {
        Intrinsics.checkNotNullParameter(file, "file");
        super(file);
    }

    @Override
    public void loadConfig(@NotNull String config) {
        Intrinsics.checkNotNullParameter(config, "config");
        JsonObject json = new JsonParser().parse(config).getAsJsonObject();
        AutoReconnect.INSTANCE.setDelay(5000);
        if (json.has("prefix")) {
            CrossSine.INSTANCE.getCommandManager().setPrefix(json.get("prefix").getAsCharacter());
        }
        if (json.has("auto-reconnect")) {
            AutoReconnect.INSTANCE.setDelay(json.get("auto-reconnect").getAsInt());
        }
        if (json.has("stylised")) {
            GuiAltManager.Companion.setStylisedAlts(json.get("stylised").getAsBoolean());
        }
        if (json.has("unformattedAlts")) {
            GuiAltManager.Companion.setUnformattedAlts(json.get("unformattedAlts").getAsBoolean());
        }
        if (json.has("unformattedAlts")) {
            GuiAltManager.Companion.setAltsLength(json.get("altsLength").getAsInt());
        }
    }

    @Override
    @NotNull
    public String saveConfig() {
        JsonObject json = new JsonObject();
        json.addProperty("auto-reconnect", (Number)AutoReconnect.INSTANCE.getDelay());
        json.addProperty("prefix", Character.valueOf(CrossSine.INSTANCE.getCommandManager().getPrefix()));
        json.addProperty("stylised", Boolean.valueOf(GuiAltManager.Companion.getStylisedAlts()));
        json.addProperty("unformattedAlts", Boolean.valueOf(GuiAltManager.Companion.getUnformattedAlts()));
        json.addProperty("altsLength", (Number)GuiAltManager.Companion.getAltsLength());
        String string = FileManager.Companion.getPRETTY_GSON().toJson((JsonElement)json);
        Intrinsics.checkNotNullExpressionValue(string, "FileManager.PRETTY_GSON.toJson(json)");
        return string;
    }
}

