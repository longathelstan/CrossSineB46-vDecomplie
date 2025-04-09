/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.file.configs;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.File;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.features.module.modules.visual.XRay;
import net.ccbluex.liquidbounce.file.FileConfig;
import net.ccbluex.liquidbounce.file.FileManager;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.minecraft.block.Block;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\bH\u0016\u00a8\u0006\n"}, d2={"Lnet/ccbluex/liquidbounce/file/configs/XRayConfig;", "Lnet/ccbluex/liquidbounce/file/FileConfig;", "file", "Ljava/io/File;", "(Ljava/io/File;)V", "loadConfig", "", "config", "", "saveConfig", "CrossSine"})
public final class XRayConfig
extends FileConfig {
    public XRayConfig(@NotNull File file) {
        Intrinsics.checkNotNullParameter(file, "file");
        super(file);
    }

    @Override
    public void loadConfig(@NotNull String config) {
        Intrinsics.checkNotNullParameter(config, "config");
        XRay xRay = CrossSine.INSTANCE.getModuleManager().get(XRay.class);
        Intrinsics.checkNotNull(xRay);
        XRay xRay2 = xRay;
        JsonArray jsonArray = new JsonParser().parse(config).getAsJsonArray();
        xRay2.getXrayBlocks().clear();
        for (JsonElement jsonElement : jsonArray) {
            try {
                Block block = Block.func_149684_b((String)jsonElement.getAsString());
                if (xRay2.getXrayBlocks().contains(block)) {
                    ClientUtils.INSTANCE.logError("[FileManager] Skipped xray block '" + block.getRegistryName() + "' because the block is already added.");
                    continue;
                }
                List<Block> list = xRay2.getXrayBlocks();
                Intrinsics.checkNotNullExpressionValue(block, "block");
                list.add(block);
            }
            catch (Throwable throwable) {
                ClientUtils.INSTANCE.logError("[FileManager] Failed to add block to xray.", throwable);
            }
        }
    }

    @Override
    @NotNull
    public String saveConfig() {
        XRay xRay = CrossSine.INSTANCE.getModuleManager().get(XRay.class);
        Intrinsics.checkNotNull(xRay);
        XRay xRay2 = xRay;
        JsonArray jsonArray = new JsonArray();
        for (Block block : xRay2.getXrayBlocks()) {
            jsonArray.add(FileManager.Companion.getPRETTY_GSON().toJsonTree((Object)Block.func_149682_b((Block)block)));
        }
        String string = FileManager.Companion.getPRETTY_GSON().toJson((JsonElement)jsonArray);
        Intrinsics.checkNotNullExpressionValue(string, "FileManager.PRETTY_GSON.toJson(jsonArray)");
        return string;
    }
}

