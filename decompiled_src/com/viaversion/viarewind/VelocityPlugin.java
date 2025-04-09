/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viarewind;

import com.google.inject.Inject;
import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.viaversion.viarewind.api.ViaRewindPlatform;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.velocity.util.LoggerWrapper;
import java.io.File;
import java.nio.file.Path;
import org.slf4j.Logger;

@Plugin(id="viarewind", name="ViaRewind", version="4.0.3-SNAPSHOT", authors={"FlorianMichael/EnZaXD", "Gerrygames", "creeper123123321"}, description="ViaBackwards addon to allow 1.8.x and 1.7.x clients on newer server versions.", dependencies={@Dependency(id="viaversion"), @Dependency(id="viabackwards")}, url="https://viaversion.com/rewind")
public class VelocityPlugin
implements ViaRewindPlatform {
    private java.util.logging.Logger logger;
    @Inject
    private Logger loggerSlf4j;
    @Inject
    @DataDirectory
    private Path configPath;

    @Subscribe(order=PostOrder.LATE)
    public void onProxyStart(ProxyInitializeEvent e) {
        this.logger = new LoggerWrapper(this.loggerSlf4j);
        Via.getManager().addEnableListener(() -> this.init(new File(this.getDataFolder(), "config.yml")));
    }

    @Override
    public File getDataFolder() {
        return this.configPath.toFile();
    }

    @Override
    public java.util.logging.Logger getLogger() {
        return this.logger;
    }
}

