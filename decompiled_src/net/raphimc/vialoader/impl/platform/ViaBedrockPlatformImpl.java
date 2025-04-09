/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialoader.impl.platform;

import com.viaversion.viaversion.api.Via;
import java.io.File;
import java.util.logging.Logger;
import net.raphimc.viabedrock.platform.ViaBedrockPlatform;
import net.raphimc.vialoader.util.JLoggerToSLF4J;
import org.slf4j.LoggerFactory;

public class ViaBedrockPlatformImpl
implements ViaBedrockPlatform {
    private static final Logger LOGGER = new JLoggerToSLF4J(LoggerFactory.getLogger("ViaBedrock"));

    public ViaBedrockPlatformImpl() {
        this.init(new File(this.getDataFolder(), "viabedrock.yml"));
    }

    public Logger getLogger() {
        return LOGGER;
    }

    public File getDataFolder() {
        return Via.getPlatform().getDataFolder();
    }
}

