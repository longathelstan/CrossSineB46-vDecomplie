/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viarewind.api.data;

import com.viaversion.nbt.tag.CompoundTag;
import com.viaversion.viabackwards.api.data.BackwardsMappingData;
import com.viaversion.viarewind.ViaRewind;
import com.viaversion.viarewind.api.data.RewindMappingDataLoader;
import java.util.logging.Logger;

public class RewindMappingData
extends BackwardsMappingData {
    public RewindMappingData(String unmappedVersion, String mappedVersion) {
        super(unmappedVersion, mappedVersion);
    }

    @Override
    protected Logger getLogger() {
        return ViaRewind.getPlatform().getLogger();
    }

    @Override
    protected CompoundTag readMappingsFile(String name) {
        return RewindMappingDataLoader.INSTANCE.loadNBTFromDir(name);
    }
}

