/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.viaaprilfools.api.data;

import com.viaversion.viabackwards.api.data.BackwardsMappingDataLoader;
import java.io.File;
import java.util.logging.Logger;
import net.raphimc.viaaprilfools.ViaAprilFools;

public class AprilFoolsMappingDataLoader
extends BackwardsMappingDataLoader {
    public static final AprilFoolsMappingDataLoader INSTANCE = new AprilFoolsMappingDataLoader();

    public AprilFoolsMappingDataLoader() {
        super(AprilFoolsMappingDataLoader.class, "assets/viaaprilfools/data/");
    }

    @Override
    public File getDataFolder() {
        return ViaAprilFools.getPlatform().getDataFolder();
    }

    @Override
    public Logger getLogger() {
        return ViaAprilFools.getPlatform().getLogger();
    }
}

