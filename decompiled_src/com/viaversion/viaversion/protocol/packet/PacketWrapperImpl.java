/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocol.packet;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.ProtocolInfo;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.Direction;
import com.viaversion.viaversion.api.protocol.packet.PacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.TypeConverter;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.exception.CancelException;
import com.viaversion.viaversion.exception.InformativeException;
import com.viaversion.viaversion.util.PipelineUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

public class PacketWrapperImpl
implements PacketWrapper {
    final Deque<PacketValue<?>> readableObjects = new ArrayDeque();
    final List<PacketValue<?>> packetValues = new ArrayList();
    final ByteBuf inputBuffer;
    final UserConnection userConnection;
    boolean send = true;
    PacketType packetType;
    int id;

    public PacketWrapperImpl(int packetId, @Nullable ByteBuf inputBuffer, UserConnection userConnection) {
        this.id = packetId;
        this.inputBuffer = inputBuffer;
        this.userConnection = userConnection;
    }

    public PacketWrapperImpl(@Nullable PacketType packetType, @Nullable ByteBuf inputBuffer, UserConnection userConnection) {
        this.packetType = packetType;
        this.id = packetType != null ? packetType.getId() : -1;
        this.inputBuffer = inputBuffer;
        this.userConnection = userConnection;
    }

    @Override
    public <T> T get(Type<T> type, int index2) throws InformativeException {
        int currentIndex = 0;
        for (PacketValue<?> packetValue : this.packetValues) {
            if (packetValue.type() != type) continue;
            if (currentIndex == index2) {
                return (T)packetValue.value();
            }
            ++currentIndex;
        }
        int n = index2;
        String string = type.getTypeName();
        throw this.createInformativeException(new ArrayIndexOutOfBoundsException("Could not find type " + string + " at " + n), type, index2);
    }

    @Override
    public boolean is(Type type, int index2) {
        int currentIndex = 0;
        for (PacketValue<?> packetValue : this.packetValues) {
            if (packetValue.type() != type) continue;
            if (currentIndex == index2) {
                return true;
            }
            ++currentIndex;
        }
        return false;
    }

    @Override
    public boolean isReadable(Type type, int index2) {
        int currentIndex = 0;
        for (PacketValue<?> packetValue : this.readableObjects) {
            if (packetValue.type().getBaseClass() != type.getBaseClass()) continue;
            if (currentIndex == index2) {
                return true;
            }
            ++currentIndex;
        }
        return false;
    }

    @Override
    public <T> void set(Type<T> type, int index2, @Nullable T value) throws InformativeException {
        int currentIndex = 0;
        for (PacketValue<?> packetValue : this.packetValues) {
            if (packetValue.type() != type) continue;
            if (currentIndex == index2) {
                packetValue.setValue(value);
                return;
            }
            ++currentIndex;
        }
        int n = index2;
        String string = type.getTypeName();
        throw this.createInformativeException(new ArrayIndexOutOfBoundsException("Could not find type " + string + " at " + n), type, index2);
    }

    @Override
    public <T> T read(Type<T> type) {
        return this.readableObjects.isEmpty() ? this.readFromBuffer(type) : this.pollReadableObject(type).value;
    }

    <T> T readFromBuffer(Type<T> type) {
        Preconditions.checkNotNull((Object)this.inputBuffer, (Object)"This packet does not have an input buffer.");
        try {
            return type.read(this.inputBuffer);
        }
        catch (Exception e) {
            throw this.createInformativeException(e, type, this.packetValues.size() + 1);
        }
    }

    <T> PacketValue<T> pollReadableObject(Type<T> type) {
        PacketValue<?> readValue = this.readableObjects.poll();
        Type<?> readType = readValue.type();
        if (readType == type || type.getBaseClass() == readType.getBaseClass() && type.getOutputClass() == readType.getOutputClass()) {
            return readValue;
        }
        String string = readValue.type().getTypeName();
        String string2 = type.getTypeName();
        throw this.createInformativeException(new IOException("Unable to read type " + string2 + ", found " + string), type, this.readableObjects.size());
    }

    @Override
    public <T> void write(Type<T> type, T value) {
        this.packetValues.add(new PacketValue<T>(type, value));
    }

    <T> @Nullable T attemptTransform(Type<T> expectedType, @Nullable Object value) {
        if (value != null && !expectedType.getOutputClass().isAssignableFrom(value.getClass())) {
            if (expectedType instanceof TypeConverter) {
                return ((TypeConverter)((Object)expectedType)).from(value);
            }
            Class<T> clazz = expectedType.getOutputClass();
            String string = value.getClass().getName();
            Via.getPlatform().getLogger().warning("Possible type mismatch: " + string + " -> " + clazz);
        }
        return (T)value;
    }

    @Override
    public <T> T passthrough(Type<T> type) throws InformativeException {
        if (this.readableObjects.isEmpty()) {
            T value = this.readFromBuffer(type);
            this.packetValues.add(new PacketValue<T>(type, value));
            return value;
        }
        PacketValue<T> value = this.pollReadableObject(type);
        this.packetValues.add(value);
        return value.value;
    }

    @Override
    public <T> T passthroughAndMap(Type<?> type, Type<T> mappedType) throws InformativeException {
        if (type == mappedType) {
            return this.passthrough(mappedType);
        }
        Object value = this.read(type);
        T mappedValue = this.attemptTransform(mappedType, value);
        this.write(mappedType, mappedValue);
        return mappedValue;
    }

    @Override
    public void passthroughAll() throws InformativeException {
        this.packetValues.addAll(this.readableObjects);
        this.readableObjects.clear();
        if (this.inputBuffer.isReadable()) {
            this.passthrough(Types.REMAINING_BYTES);
        }
    }

    @Override
    public void writeToBuffer(ByteBuf buffer) throws InformativeException {
        if (this.id != -1) {
            Types.VAR_INT.writePrimitive(buffer, this.id);
        }
        if (!this.readableObjects.isEmpty()) {
            this.packetValues.addAll(this.readableObjects);
            this.readableObjects.clear();
        }
        for (int i = 0; i < this.packetValues.size(); ++i) {
            PacketValue<?> packetValue = this.packetValues.get(i);
            try {
                packetValue.write(buffer);
                continue;
            }
            catch (Exception e) {
                throw this.createInformativeException(e, packetValue.type(), i);
            }
        }
        this.writeRemaining(buffer);
    }

    InformativeException createInformativeException(Exception cause, Type<?> type, int index2) {
        return new InformativeException(cause).set("Index", index2).set("Type", type.getTypeName()).set("Packet ID", this.id).set("Packet Type", this.packetType).set("Data", this.packetValues);
    }

    @Override
    public void clearInputBuffer() {
        if (this.inputBuffer != null) {
            this.inputBuffer.clear();
        }
        this.readableObjects.clear();
    }

    @Override
    public void clearPacket() {
        this.clearInputBuffer();
        this.packetValues.clear();
    }

    void writeRemaining(ByteBuf output) {
        if (this.inputBuffer != null) {
            output.writeBytes(this.inputBuffer);
        }
    }

    @Override
    public void send(Class<? extends Protocol> protocol, boolean skipCurrentPipeline) throws InformativeException {
        this.send0(protocol, skipCurrentPipeline, true);
    }

    @Override
    public void scheduleSend(Class<? extends Protocol> protocol, boolean skipCurrentPipeline) throws InformativeException {
        this.send0(protocol, skipCurrentPipeline, false);
    }

    void send0(Class<? extends Protocol> protocol, boolean skipCurrentPipeline, boolean currentThread) throws InformativeException {
        if (this.isCancelled()) {
            return;
        }
        UserConnection connection = this.user();
        if (currentThread) {
            this.sendNow(protocol, skipCurrentPipeline);
        } else {
            connection.getChannel().eventLoop().submit(() -> this.sendNow(protocol, skipCurrentPipeline));
        }
    }

    void sendNow(Class<? extends Protocol> protocol, boolean skipCurrentPipeline) throws InformativeException {
        block4: {
            try {
                ByteBuf output = this.constructPacket(protocol, skipCurrentPipeline, Direction.CLIENTBOUND);
                this.user().sendRawPacket(output);
            }
            catch (InformativeException e) {
                throw e;
            }
            catch (CancelException e) {
            }
            catch (Exception e) {
                if (PipelineUtil.containsCause(e, CancelException.class)) break block4;
                throw new InformativeException(e);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    ByteBuf constructPacket(@Nullable Class<? extends Protocol> protocolClass, boolean skipCurrentPipeline, Direction direction) throws InformativeException, CancelException {
        this.resetReader();
        ProtocolInfo protocolInfo = this.user().getProtocolInfo();
        List<Protocol> protocols = protocolInfo.getPipeline().pipes(protocolClass, skipCurrentPipeline, direction);
        this.apply(direction, protocolInfo.getState(direction), protocols);
        ByteBuf output = this.inputBuffer == null ? this.user().getChannel().alloc().buffer() : this.inputBuffer.alloc().buffer();
        try {
            this.writeToBuffer(output);
            ByteBuf byteBuf = output.retain();
            return byteBuf;
        }
        finally {
            output.release();
        }
    }

    @Override
    public ChannelFuture sendFuture(Class<? extends Protocol> protocolClass) throws InformativeException {
        if (!this.isCancelled()) {
            ByteBuf output;
            try {
                output = this.constructPacket(protocolClass, true, Direction.CLIENTBOUND);
            }
            catch (CancelException e) {
                return this.user().getChannel().newFailedFuture((Throwable)new RuntimeException("Cancelled packet"));
            }
            return this.user().sendRawPacketFuture(output);
        }
        return this.cancelledFuture();
    }

    @Override
    public void sendRaw() throws InformativeException {
        this.sendRaw(true);
    }

    @Override
    public ChannelFuture sendFutureRaw() throws InformativeException {
        if (this.isCancelled()) {
            return this.cancelledFuture();
        }
        ByteBuf output = this.inputBuffer == null ? this.user().getChannel().alloc().buffer() : this.inputBuffer.alloc().buffer();
        try {
            this.writeToBuffer(output);
            ChannelFuture channelFuture = this.user().sendRawPacketFuture(output.retain());
            return channelFuture;
        }
        finally {
            output.release();
        }
    }

    @Override
    public void scheduleSendRaw() throws InformativeException {
        this.sendRaw(false);
    }

    void sendRaw(boolean currentThread) throws InformativeException {
        if (this.isCancelled()) {
            return;
        }
        ByteBuf output = this.inputBuffer == null ? this.user().getChannel().alloc().buffer() : this.inputBuffer.alloc().buffer();
        try {
            this.writeToBuffer(output);
            if (currentThread) {
                this.user().sendRawPacket(output.retain());
            } else {
                this.user().scheduleSendRawPacket(output.retain());
            }
        }
        finally {
            output.release();
        }
    }

    ChannelFuture cancelledFuture() {
        return this.user().getChannel().newFailedFuture((Throwable)new RuntimeException("Tried to send cancelled packet"));
    }

    @Override
    public PacketWrapperImpl create(int packetId) {
        return new PacketWrapperImpl(packetId, null, this.user());
    }

    @Override
    public PacketWrapperImpl create(int packetId, PacketHandler handler) throws InformativeException {
        PacketWrapperImpl wrapper = this.create(packetId);
        handler.handle(wrapper);
        return wrapper;
    }

    @Override
    public void apply(Direction direction, State state, List<Protocol> pipeline) throws InformativeException, CancelException {
        int size = pipeline.size();
        for (int i = 0; i < size; ++i) {
            Protocol protocol = pipeline.get(i);
            protocol.transform(direction, state, this);
            this.resetReader();
            if (this.packetType == null) continue;
            state = this.packetType.state();
        }
    }

    @Override
    public boolean isCancelled() {
        return !this.send;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.send = !cancel;
    }

    @Override
    public UserConnection user() {
        return this.userConnection;
    }

    @Override
    public void resetReader() {
        for (int i = this.packetValues.size() - 1; i >= 0; --i) {
            this.readableObjects.addFirst(this.packetValues.get(i));
        }
        this.packetValues.clear();
    }

    @Override
    public void sendToServerRaw() throws InformativeException {
        this.sendToServerRaw(true);
    }

    @Override
    public void scheduleSendToServerRaw() throws InformativeException {
        this.sendToServerRaw(false);
    }

    void sendToServerRaw(boolean currentThread) throws InformativeException {
        if (this.isCancelled()) {
            return;
        }
        ByteBuf output = this.inputBuffer == null ? this.user().getChannel().alloc().buffer() : this.inputBuffer.alloc().buffer();
        try {
            this.writeToBuffer(output);
            if (currentThread) {
                this.user().sendRawPacketToServer(output.retain());
            } else {
                this.user().scheduleSendRawPacketToServer(output.retain());
            }
        }
        finally {
            output.release();
        }
    }

    @Override
    public void sendToServer(Class<? extends Protocol> protocol, boolean skipCurrentPipeline) throws InformativeException {
        this.sendToServer0(protocol, skipCurrentPipeline, true);
    }

    @Override
    public void scheduleSendToServer(Class<? extends Protocol> protocol, boolean skipCurrentPipeline) throws InformativeException {
        this.sendToServer0(protocol, skipCurrentPipeline, false);
    }

    void sendToServer0(Class<? extends Protocol> protocol, boolean skipCurrentPipeline, boolean currentThread) throws InformativeException {
        if (this.isCancelled()) {
            return;
        }
        UserConnection connection = this.user();
        if (currentThread) {
            block6: {
                try {
                    ByteBuf output = this.constructPacket(protocol, skipCurrentPipeline, Direction.SERVERBOUND);
                    connection.sendRawPacketToServer(output);
                }
                catch (InformativeException e) {
                    throw e;
                }
                catch (CancelException e) {
                }
                catch (Exception e) {
                    if (PipelineUtil.containsCause(e, CancelException.class)) break block6;
                    throw new InformativeException(e);
                }
            }
            return;
        }
        connection.getChannel().eventLoop().submit(() -> {
            block4: {
                try {
                    ByteBuf output = this.constructPacket(protocol, skipCurrentPipeline, Direction.SERVERBOUND);
                    connection.sendRawPacketToServer(output);
                }
                catch (InformativeException e) {
                    throw e;
                }
                catch (CancelException e) {
                }
                catch (Exception e) {
                    if (PipelineUtil.containsCause(e, CancelException.class)) break block4;
                    throw new InformativeException(e);
                }
            }
        });
    }

    @Override
    public @Nullable PacketType getPacketType() {
        return this.packetType;
    }

    @Override
    public void setPacketType(PacketType packetType) {
        this.packetType = packetType;
        this.id = packetType != null ? packetType.getId() : -1;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    @Deprecated
    public void setId(int id) {
        this.packetType = null;
        this.id = id;
    }

    public @Nullable ByteBuf getInputBuffer() {
        return this.inputBuffer;
    }

    public String toString() {
        Deque<PacketValue<?>> deque = this.readableObjects;
        List<PacketValue<?>> list = this.packetValues;
        int n = this.id;
        PacketType packetType = this.packetType;
        return "PacketWrapper{type=" + packetType + ", id=" + n + ", values=" + list + ", readable=" + deque + "}";
    }

    public static final class PacketValue<T> {
        final Type<T> type;
        T value;

        PacketValue(Type<T> type, @Nullable T value) {
            this.type = type;
            this.value = value;
        }

        public Type<T> type() {
            return this.type;
        }

        public @Nullable Object value() {
            return this.value;
        }

        public void write(ByteBuf buffer) throws Exception {
            this.type.write(buffer, this.value);
        }

        public void setValue(@Nullable T value) {
            this.value = value;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            PacketValue that = (PacketValue)o;
            if (!this.type.equals(that.type)) {
                return false;
            }
            return Objects.equals(this.value, that.value);
        }

        public int hashCode() {
            int result = this.type.hashCode();
            result = 31 * result + (this.value != null ? this.value.hashCode() : 0);
            return result;
        }

        public String toString() {
            T t = this.value;
            Type<T> type = this.type;
            return "{" + type + ": " + t + "}";
        }
    }
}

