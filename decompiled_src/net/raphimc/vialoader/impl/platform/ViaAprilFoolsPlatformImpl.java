/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialoader.impl.platform;

import com.viaversion.viaversion.api.Via;
import java.io.File;
import java.util.logging.Logger;
import net.raphimc.viaaprilfools.platform.ViaAprilFoolsPlatform;
import net.raphimc.vialoader.util.JLoggerToSLF4J;
import org.slf4j.LoggerFactory;

public class ViaAprilFoolsPlatformImpl
implements ViaAprilFoolsPlatform {
    private static final Logger LOGGER = new JLoggerToSLF4J(LoggerFactory.getLogger("ViaAprilFools"));

    public ViaAprilFoolsPlatformImpl() {
        this.init(new File(this.getDataFolder(), "viaaprilfools.yml"));
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

