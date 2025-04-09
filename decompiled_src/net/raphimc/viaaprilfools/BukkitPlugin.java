/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.viaaprilfools;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.ViaManager;
import com.viaversion.viaversion.api.protocol.version.VersionProvider;
import java.io.File;
import net.raphimc.viaaprilfools.api.VAFServerVersionProvider;
import net.raphimc.viaaprilfools.platform.ViaAprilFoolsPlatform;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitPlugin
extends JavaPlugin
implements ViaAprilFoolsPlatform {
    public BukkitPlugin() {
        ViaManager manager = Via.getManager();
        manager.addEnableListener(() -> {
            this.init(new File(this.getDataFolder(), "config.yml"));
            VersionProvider delegate = manager.getProviders().get(VersionProvider.class);
            manager.getProviders().use(VersionProvider.class, new VAFServerVersionProvider(delegate));
        });
    }
}

