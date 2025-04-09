/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viarewind;

import com.viaversion.viarewind.api.ViaRewindPlatform;
import com.viaversion.viarewind.fabric.util.LoggerWrapper;
import java.io.File;
import java.nio.file.Path;
import java.util.logging.Logger;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;

public class ViaFabricAddon
implements ViaRewindPlatform,
Runnable {
    private final Logger logger = new LoggerWrapper(LogManager.getLogger((String)"ViaRewind"));
    private File configDir;

    @Override
    public void run() {
        Path configDirPath = FabricLoader.getInstance().getConfigDir().resolve("ViaRewind");
        this.configDir = configDirPath.toFile();
        this.init(new File(this.getDataFolder(), "config.yml"));
    }

    @Override
    public File getDataFolder() {
        return this.configDir;
    }

    @Override
    public Logger getLogger() {
        return this.logger;
    }
}

