/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.rewriter;

import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.rewriter.IdRewriteFunction;
import org.checkerframework.checker.nullness.qual.Nullable;

public class StatisticsRewriter<C extends ClientboundPacketType> {
    static final int CUSTOM_STATS_CATEGORY = 8;
    final Protocol<C, ?, ?, ?> protocol;

    public StatisticsRewriter(Protocol<C, ?, ?, ?> protocol) {
        this.protocol = protocol;
    }

    public void register(C packetType) {
        this.protocol.registerClientbound(packetType, wrapper -> {
            int size;
            int newSize = size = wrapper.passthrough(Types.VAR_INT).intValue();
            for (int i = 0; i < size; ++i) {
                int categoryId = wrapper.read(Types.VAR_INT);
                int statisticId = wrapper.read(Types.VAR_INT);
                int value = wrapper.read(Types.VAR_INT);
                if (categoryId == 8 && this.protocol.getMappingData().getStatisticsMappings() != null) {
                    statisticId = this.protocol.getMappingData().getStatisticsMappings().getNewId(statisticId);
                    if (statisticId == -1) {
                        --newSize;
                        continue;
                    }
                } else {
                    IdRewriteFunction statisticsRewriter;
                    RegistryType type = this.getRegistryTypeForStatistic(categoryId);
                    if (type != null && (statisticsRewriter = this.getRewriter(type)) != null) {
                        statisticId = statisticsRewriter.rewrite(statisticId);
                    }
                }
                wrapper.write(Types.VAR_INT, categoryId);
                wrapper.write(Types.VAR_INT, statisticId);
                wrapper.write(Types.VAR_INT, value);
            }
            if (newSize != size) {
                wrapper.set(Types.VAR_INT, 0, newSize);
            }
        });
    }

    protected @Nullable IdRewriteFunction getRewriter(RegistryType type) {
        IdRewriteFunction idRewriteFunction;
        switch (type) {
            case BLOCK: {
                if (this.protocol.getMappingData().getBlockMappings() != null) {
                    idRewriteFunction = id -> this.protocol.getMappingData().getNewBlockId(id);
                    break;
                }
                idRewriteFunction = null;
                break;
            }
            case ITEM: {
                if (this.protocol.getMappingData().getItemMappings() != null) {
                    idRewriteFunction = id -> this.protocol.getMappingData().getNewItemId(id);
                    break;
                }
                idRewriteFunction = null;
                break;
            }
            case ENTITY: {
                if (this.protocol.getEntityRewriter() != null) {
                    idRewriteFunction = id -> this.protocol.getEntityRewriter().newEntityId(id);
                    break;
                }
                idRewriteFunction = null;
                break;
            }
            default: {
                RegistryType registryType = type;
                throw new IllegalArgumentException("Unknown registry type in statistics packet: " + (Object)((Object)registryType));
            }
        }
        return idRewriteFunction;
    }

    public @Nullable RegistryType getRegistryTypeForStatistic(int statisticsId) {
        RegistryType registryType;
        switch (statisticsId) {
            case 0: {
                registryType = RegistryType.BLOCK;
                break;
            }
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 5: {
                registryType = RegistryType.ITEM;
                break;
            }
            case 6: 
            case 7: {
                registryType = RegistryType.ENTITY;
                break;
            }
            default: {
                registryType = null;
            }
        }
        return registryType;
    }
}

