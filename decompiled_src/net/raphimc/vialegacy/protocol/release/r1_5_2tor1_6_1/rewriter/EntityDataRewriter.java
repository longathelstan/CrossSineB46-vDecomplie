/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialegacy.protocol.release.r1_5_2tor1_6_1.rewriter;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_8;
import com.viaversion.viaversion.api.minecraft.entitydata.EntityData;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import net.raphimc.vialegacy.ViaLegacy;
import net.raphimc.vialegacy.protocol.release.r1_5_2tor1_6_1.data.EntityDataIndex1_5_2;
import net.raphimc.vialegacy.protocol.release.r1_6_4tor1_7_2_5.types.EntityDataTypes1_6_4;

public class EntityDataRewriter {
    public static void transform(EntityTypes1_8.EntityType type, List<EntityData> list) {
        for (EntityData entry : new ArrayList<EntityData>(list)) {
            EntityDataIndex1_5_2 entityDataIndex = EntityDataIndex1_5_2.searchIndex(type, entry.id());
            try {
                if (entityDataIndex == null) continue;
                Object value = entry.getValue();
                entry.setTypeAndValue(entityDataIndex.getOldType(), value);
                entry.setDataTypeUnsafe(entityDataIndex.getNewType());
                entry.setId(entityDataIndex.getNewIndex());
                switch (entityDataIndex.getNewType()) {
                    case BYTE: {
                        entry.setValue(((Number)value).byteValue());
                        break;
                    }
                    case SHORT: {
                        entry.setValue(((Number)value).shortValue());
                        break;
                    }
                    case INT: {
                        entry.setValue(((Number)value).intValue());
                        break;
                    }
                    case FLOAT: {
                        entry.setValue(Float.valueOf(((Number)value).floatValue()));
                        break;
                    }
                    case ITEM: 
                    case STRING: 
                    case BLOCK_POSITION: {
                        break;
                    }
                    default: {
                        if (!Via.getConfig().isSuppressConversionWarnings()) {
                            EntityData entityData = entry;
                            EntityDataTypes1_6_4 entityDataTypes1_6_4 = entityDataIndex.getNewType();
                            ViaLegacy.getPlatform().getLogger().warning("1.5.2 EntityDataRewriter: Unhandled Type: " + entityDataTypes1_6_4 + " " + entityData);
                        }
                        list.remove(entry);
                        break;
                    }
                }
            }
            catch (Throwable e) {
                if (!Via.getConfig().isSuppressConversionWarnings()) {
                    EntityData entityData = entry;
                    String string = type.name();
                    ViaLegacy.getPlatform().getLogger().log(Level.WARNING, "Error rewriting entity data entry for " + string + ": " + entityData, e);
                }
                list.remove(entry);
            }
        }
    }
}

