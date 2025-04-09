/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.viaaprilfools;

import com.viaversion.viaversion.util.Config;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class ViaAprilFoolsConfig
extends Config
implements net.raphimc.viaaprilfools.platform.ViaAprilFoolsConfig {
    public ViaAprilFoolsConfig(File configFile, Logger logger) {
        super(configFile, logger);
    }

    @Override
    public void reload() {
        super.reload();
        this.loadFields();
    }

    private void loadFields() {
    }

    @Override
    public URL getDefaultConfigURL() {
        return this.getClass().getClassLoader().getResource("assets/viaaprilfools/viaaprilfools.yml");
    }

    @Override
    public InputStream getDefaultConfigInputStream() {
        return this.getClass().getClassLoader().getResourceAsStream("assets/viaaprilfools/viaaprilfools.yml");
    }

    @Override
    protected void handleConfig(Map<String, Object> map) {
    }

    @Override
    public List<String> getUnsupportedOptions() {
        return Collections.emptyList();
    }
}

