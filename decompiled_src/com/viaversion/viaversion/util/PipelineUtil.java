/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.util;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class PipelineUtil {
    private static final MethodHandle DECODE_METHOD = PipelineUtil.privateHandleUnchecked(ByteToMessageDecoder.class, "decode", ChannelHandlerContext.class, ByteBuf.class, List.class);
    private static final MethodHandle ENCODE_METHOD = PipelineUtil.privateHandleUnchecked(MessageToByteEncoder.class, "encode", ChannelHandlerContext.class, Object.class, ByteBuf.class);
    private static final MethodHandle MTM_DECODE = PipelineUtil.privateHandleUnchecked(MessageToMessageDecoder.class, "decode", ChannelHandlerContext.class, Object.class, List.class);

    public static List<Object> callDecode(ByteToMessageDecoder decoder, ChannelHandlerContext ctx, Object input) throws Exception {
        ArrayList<Object> output = new ArrayList<Object>();
        try {
            DECODE_METHOD.invoke(decoder, ctx, input, output);
        }
        catch (Error | Exception e) {
            throw e;
        }
        catch (Throwable t) {
            throw new InvocationTargetException(t);
        }
        return output;
    }

    public static void callEncode(MessageToByteEncoder encoder, ChannelHandlerContext ctx, Object msg, ByteBuf output) throws Exception {
        try {
            ENCODE_METHOD.invoke(encoder, ctx, msg, output);
        }
        catch (Error | Exception e) {
            throw e;
        }
        catch (Throwable t) {
            throw new InvocationTargetException(t);
        }
    }

    public static List<Object> callDecode(MessageToMessageDecoder decoder, ChannelHandlerContext ctx, Object msg) throws Exception {
        ArrayList<Object> output = new ArrayList<Object>();
        try {
            MTM_DECODE.invoke(decoder, ctx, msg, output);
        }
        catch (Error | Exception e) {
            throw e;
        }
        catch (Throwable t) {
            throw new InvocationTargetException(t);
        }
        return output;
    }

    public static boolean containsCause(Throwable t, Class<?> c) {
        do {
            if (!c.isAssignableFrom(t.getClass())) continue;
            return true;
        } while ((t = t.getCause()) != null);
        return false;
    }

    public static <T> @Nullable T getCause(Throwable t, Class<T> c) {
        while (t != null) {
            if (c.isAssignableFrom(t.getClass())) {
                return (T)t;
            }
            t = t.getCause();
        }
        return null;
    }

    public static @Nullable ChannelHandlerContext getContextBefore(String name, ChannelPipeline pipeline) {
        boolean mark = false;
        for (String s : pipeline.names()) {
            if (mark) {
                return pipeline.context(pipeline.get(s));
            }
            if (!s.equalsIgnoreCase(name)) continue;
            mark = true;
        }
        return null;
    }

    public static @Nullable ChannelHandlerContext getPreviousContext(String name, ChannelPipeline pipeline) {
        String previous = null;
        for (String entry : pipeline.toMap().keySet()) {
            if (entry.equals(name)) {
                return pipeline.context(previous);
            }
            previous = entry;
        }
        return null;
    }

    private static MethodHandle privateHandle(Class<?> clazz, String method, Class<?> ... parameterTypes) throws NoSuchMethodException, IllegalAccessException {
        Method decodeMethod = clazz.getDeclaredMethod(method, parameterTypes);
        decodeMethod.setAccessible(true);
        return MethodHandles.lookup().unreflect(decodeMethod);
    }

    private static MethodHandle privateHandleUnchecked(Class<?> clazz, String method, Class<?> ... args2) {
        try {
            return PipelineUtil.privateHandle(clazz, method, args2);
        }
        catch (IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}

