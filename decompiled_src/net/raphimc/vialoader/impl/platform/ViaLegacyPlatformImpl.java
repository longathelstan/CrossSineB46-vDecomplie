/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialoader.impl.platform;

import com.viaversion.viaversion.api.Via;
import java.io.File;
import java.util.logging.Logger;
import net.raphimc.vialegacy.platform.ViaLegacyPlatform;
import net.raphimc.vialoader.util.JLoggerToSLF4J;
import org.slf4j.LoggerFactory;

public class ViaLegacyPlatformImpl
implements ViaLegacyPlatform {
    private static final Logger LOGGER = new JLoggerToSLF4J(LoggerFactory.getLogger("ViaLegacy"));

    public ViaLegacyPlatformImpl() {
        this.init(new File(this.getDataFolder(), "vialegacy.yml"));
    }

    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    @Override
    public File getDataFolder() {
        return Via.getPlatform().getDataFolder();
    }
}

