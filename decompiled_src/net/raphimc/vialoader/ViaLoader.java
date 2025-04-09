/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialoader;

import com.viaversion.viaversion.ViaManagerImpl;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.platform.ViaInjector;
import com.viaversion.viaversion.api.platform.ViaPlatform;
import com.viaversion.viaversion.api.platform.ViaPlatformLoader;
import com.viaversion.viaversion.commands.ViaCommandHandler;
import com.viaversion.viaversion.protocol.ProtocolManagerImpl;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.raphimc.vialoader.impl.platform.ViaVersionPlatformImpl;
import net.raphimc.vialoader.impl.viaversion.VLCommandHandler;
import net.raphimc.vialoader.impl.viaversion.VLInjector;
import net.raphimc.vialoader.impl.viaversion.VLLoader;
import net.raphimc.vialoader.util.JLoggerToSLF4J;
import org.slf4j.LoggerFactory;

public class ViaLoader {
    public static final String VERSION = "3.0.3-SNAPSHOT";
    private static final Logger LOGGER = new JLoggerToSLF4J(LoggerFactory.getLogger("ViaLoader"));

    public static void init(ViaPlatform<?> platform, ViaPlatformLoader loader, ViaInjector injector, ViaCommandHandler commandHandler, Supplier<?> ... platformSuppliers) {
        if (platform == null) {
            platform = new ViaVersionPlatformImpl(null);
        }
        if (loader == null) {
            loader = new VLLoader();
        }
        if (injector == null) {
            injector = new VLInjector();
        }
        if (commandHandler == null) {
            commandHandler = new VLCommandHandler();
        }
        Via.init(ViaManagerImpl.builder().platform(platform).loader(loader).injector(injector).commandHandler(commandHandler).build());
        Via.getConfig().reload();
        if (platformSuppliers != null) {
            Via.getManager().addEnableListener(() -> {
                for (Supplier additionalPlatformSupplier : platformSuppliers) {
                    try {
                        additionalPlatformSupplier.get();
                    }
                    catch (Throwable e) {
                        LOGGER.log(Level.SEVERE, "Platform failed to load", e);
                    }
                }
            });
        }
        ViaManagerImpl viaManager = (ViaManagerImpl)Via.getManager();
        viaManager.init();
        viaManager.onServerLoaded();
        Via.getManager().getProtocolManager().setMaxProtocolPathSize(Integer.MAX_VALUE);
        Via.getManager().getProtocolManager().setMaxPathDeltaIncrease(-1);
        ((ProtocolManagerImpl)Via.getManager().getProtocolManager()).refreshVersions();
    }
}

