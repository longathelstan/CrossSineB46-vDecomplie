/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialoader.impl.platform;

import com.viaversion.viabackwards.api.ViaBackwardsPlatform;
import com.viaversion.viaversion.api.Via;
import java.io.File;
import java.util.logging.Logger;
import net.raphimc.vialoader.util.JLoggerToSLF4J;
import org.slf4j.LoggerFactory;

public class ViaBackwardsPlatformImpl
implements ViaBackwardsPlatform {
    private static final Logger LOGGER = new JLoggerToSLF4J(LoggerFactory.getLogger("ViaBackwards"));

    public ViaBackwardsPlatformImpl() {
        this.init(new File(this.getDataFolder(), "viabackwards.yml"));
    }

    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    @Override
    public File getDataFolder() {
        return Via.getPlatform().getDataFolder();
    }

    @Override
    public boolean isOutdated() {
        return false;
    }

    @Override
    public void disable() {
    }
}

