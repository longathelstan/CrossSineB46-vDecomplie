/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialoader.impl.viaversion;

import com.viaversion.viaversion.configuration.AbstractViaConfig;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class VLViaConfig
extends AbstractViaConfig {
    protected final List<String> UNSUPPORTED = new ArrayList<String>();

    public VLViaConfig(File configFile, Logger logger) {
        super(configFile, logger);
        this.UNSUPPORTED.addAll(BUKKIT_ONLY_OPTIONS);
        this.UNSUPPORTED.addAll(VELOCITY_ONLY_OPTIONS);
        this.UNSUPPORTED.add("check-for-updates");
    }

    @Override
    protected void handleConfig(Map<String, Object> config) {
    }

    @Override
    public List<String> getUnsupportedOptions() {
        return Collections.unmodifiableList(this.UNSUPPORTED);
    }

    @Override
    public boolean isCheckForUpdates() {
        return false;
    }
}

