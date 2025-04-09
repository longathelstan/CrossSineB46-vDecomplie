/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.rewriter;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.data.FullMappings;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Types;
import java.util.HashMap;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;

public class CommandRewriter<C extends ClientboundPacketType> {
    protected final Protocol<C, ?, ?, ?> protocol;
    protected final Map<String, CommandArgumentConsumer> parserHandlers = new HashMap<String, CommandArgumentConsumer>();

    public CommandRewriter(Protocol<C, ?, ?, ?> protocol) {
        this.protocol = protocol;
        this.parserHandlers.put("brigadier:double", wrapper -> {
            byte propertyFlags = wrapper.passthrough(Types.BYTE);
            if ((propertyFlags & 1) != 0) {
                wrapper.passthrough(Types.DOUBLE);
            }
            if ((propertyFlags & 2) != 0) {
                wrapper.passthrough(Types.DOUBLE);
            }
        });
        this.parserHandlers.put("brigadier:float", wrapper -> {
            byte propertyFlags = wrapper.passthrough(Types.BYTE);
            if ((propertyFlags & 1) != 0) {
                wrapper.passthrough(Types.FLOAT);
            }
            if ((propertyFlags & 2) != 0) {
                wrapper.passthrough(Types.FLOAT);
            }
        });
        this.parserHandlers.put("brigadier:integer", wrapper -> {
            byte propertyFlags = wrapper.passthrough(Types.BYTE);
            if ((propertyFlags & 1) != 0) {
                wrapper.passthrough(Types.INT);
            }
            if ((propertyFlags & 2) != 0) {
                wrapper.passthrough(Types.INT);
            }
        });
        this.parserHandlers.put("brigadier:long", wrapper -> {
            byte propertyFlags = wrapper.passthrough(Types.BYTE);
            if ((propertyFlags & 1) != 0) {
                wrapper.passthrough(Types.LONG);
            }
            if ((propertyFlags & 2) != 0) {
                wrapper.passthrough(Types.LONG);
            }
        });
        this.parserHandlers.put("brigadier:string", wrapper -> wrapper.passthrough(Types.VAR_INT));
        this.parserHandlers.put("minecraft:entity", wrapper -> wrapper.passthrough(Types.BYTE));
        this.parserHandlers.put("minecraft:score_holder", wrapper -> wrapper.passthrough(Types.BYTE));
        this.parserHandlers.put("minecraft:resource", wrapper -> wrapper.passthrough(Types.STRING));
        this.parserHandlers.put("minecraft:resource_or_tag", wrapper -> wrapper.passthrough(Types.STRING));
        this.parserHandlers.put("minecraft:resource_or_tag_key", wrapper -> wrapper.passthrough(Types.STRING));
        this.parserHandlers.put("minecraft:resource_key", wrapper -> wrapper.passthrough(Types.STRING));
    }

    public void registerDeclareCommands(C packetType) {
        this.protocol.registerClientbound(packetType, wrapper -> {
            int size = wrapper.passthrough(Types.VAR_INT);
            for (int i = 0; i < size; ++i) {
                byte nodeType;
                byte flags = wrapper.passthrough(Types.BYTE);
                wrapper.passthrough(Types.VAR_INT_ARRAY_PRIMITIVE);
                if ((flags & 8) != 0) {
                    wrapper.passthrough(Types.VAR_INT);
                }
                if ((nodeType = (byte)(flags & 3)) == 1 || nodeType == 2) {
                    wrapper.passthrough(Types.STRING);
                }
                if (nodeType == 2) {
                    String argumentType = wrapper.read(Types.STRING);
                    String newArgumentType = this.handleArgumentType(argumentType);
                    if (newArgumentType != null) {
                        wrapper.write(Types.STRING, newArgumentType);
                    }
                    this.handleArgument(wrapper, argumentType);
                }
                if ((flags & 0x10) == 0) continue;
                wrapper.passthrough(Types.STRING);
            }
            wrapper.passthrough(Types.VAR_INT);
        });
    }

    public void registerDeclareCommands1_19(C packetType) {
        this.protocol.registerClientbound(packetType, wrapper -> {
            int size = wrapper.passthrough(Types.VAR_INT);
            for (int i = 0; i < size; ++i) {
                byte nodeType;
                byte flags = wrapper.passthrough(Types.BYTE);
                wrapper.passthrough(Types.VAR_INT_ARRAY_PRIMITIVE);
                if ((flags & 8) != 0) {
                    wrapper.passthrough(Types.VAR_INT);
                }
                if ((nodeType = (byte)(flags & 3)) == 1 || nodeType == 2) {
                    wrapper.passthrough(Types.STRING);
                }
                if (nodeType != 2) continue;
                int argumentTypeId = wrapper.read(Types.VAR_INT);
                String argumentType = this.argumentType(argumentTypeId);
                if (argumentType == null) {
                    wrapper.write(Types.VAR_INT, this.mapInvalidArgumentType(argumentTypeId));
                    continue;
                }
                String newArgumentType = this.handleArgumentType(argumentType);
                Preconditions.checkNotNull((Object)newArgumentType, (String)"No mapping for argument type %s", (Object[])new Object[]{argumentType});
                wrapper.write(Types.VAR_INT, this.mappedArgumentTypeId(newArgumentType));
                this.handleArgument(wrapper, argumentType);
                if ((flags & 0x10) == 0) continue;
                wrapper.passthrough(Types.STRING);
            }
            wrapper.passthrough(Types.VAR_INT);
        });
    }

    public void handleArgument(PacketWrapper wrapper, String argumentType) {
        CommandArgumentConsumer handler = this.parserHandlers.get(argumentType);
        if (handler != null) {
            handler.accept(wrapper);
        }
    }

    public String handleArgumentType(String argumentType) {
        if (this.protocol.getMappingData() != null && this.protocol.getMappingData().getArgumentTypeMappings() != null) {
            return this.protocol.getMappingData().getArgumentTypeMappings().mappedIdentifier(argumentType);
        }
        return argumentType;
    }

    protected @Nullable String argumentType(int argumentTypeId) {
        FullMappings mappings = this.protocol.getMappingData().getArgumentTypeMappings();
        String identifier = mappings.identifier(argumentTypeId);
        Preconditions.checkArgument((identifier != null || argumentTypeId >= mappings.size() ? 1 : 0) != 0, (String)"Unknown argument type id %s", (Object[])new Object[]{argumentTypeId});
        return identifier;
    }

    protected int mappedArgumentTypeId(String mappedArgumentType) {
        return this.protocol.getMappingData().getArgumentTypeMappings().mappedId(mappedArgumentType);
    }

    int mapInvalidArgumentType(int id) {
        if (id < 0) {
            return id;
        }
        FullMappings mappings = this.protocol.getMappingData().getArgumentTypeMappings();
        int idx = id - mappings.size();
        return mappings.mappedSize() + idx;
    }

    @FunctionalInterface
    public static interface CommandArgumentConsumer {
        public void accept(PacketWrapper var1);
    }
}

