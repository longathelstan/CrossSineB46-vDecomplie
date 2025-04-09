/*
 * Decompiled with CFR 0.152.
 */
package jdk.nashorn.internal.objects;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.nio.ByteBuffer;
import java.util.Collections;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.objects.ArrayBufferView;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.objects.NativeArrayBuffer;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.arrays.ArrayData;
import jdk.nashorn.internal.runtime.arrays.TypedArrayData;

public final class NativeUint8Array
extends ArrayBufferView {
    public static final int BYTES_PER_ELEMENT = 1;
    private static PropertyMap $nasgenmap$;
    private static final ArrayBufferView.Factory FACTORY;

    public static NativeUint8Array constructor(boolean newObj, Object self, Object ... args2) {
        return (NativeUint8Array)NativeUint8Array.constructorImpl(newObj, args2, FACTORY);
    }

    NativeUint8Array(NativeArrayBuffer buffer, int byteOffset, int length) {
        super(buffer, byteOffset, length);
    }

    @Override
    protected ArrayBufferView.Factory factory() {
        return FACTORY;
    }

    protected static Object set(Object self, Object array, Object offset) {
        return ArrayBufferView.setImpl(self, array, offset);
    }

    protected static NativeUint8Array subarray(Object self, Object begin, Object end) {
        return (NativeUint8Array)ArrayBufferView.subarrayImpl(self, begin, end);
    }

    @Override
    protected ScriptObject getPrototype(Global global) {
        return global.getUint8ArrayPrototype();
    }

    static {
        FACTORY = new ArrayBufferView.Factory(1){

            @Override
            public ArrayBufferView construct(NativeArrayBuffer buffer, int byteOffset, int length) {
                return new NativeUint8Array(buffer, byteOffset, length);
            }

            public Uint8ArrayData createArrayData(ByteBuffer nb, int start, int end) {
                return new Uint8ArrayData(nb, start, end);
            }

            @Override
            public String getClassName() {
                return "Uint8Array";
            }
        };
        NativeUint8Array.$clinit$();
    }

    public static void $clinit$() {
        $nasgenmap$ = PropertyMap.newMap(Collections.EMPTY_LIST);
    }

    private static final class Uint8ArrayData
    extends TypedArrayData<ByteBuffer> {
        private static final MethodHandle GET_ELEM = CompilerConstants.specialCall(MethodHandles.lookup(), Uint8ArrayData.class, "getElem", Integer.TYPE, Integer.TYPE).methodHandle();
        private static final MethodHandle SET_ELEM = CompilerConstants.specialCall(MethodHandles.lookup(), Uint8ArrayData.class, "setElem", Void.TYPE, Integer.TYPE, Integer.TYPE).methodHandle();

        private Uint8ArrayData(ByteBuffer nb, int start, int end) {
            super(((ByteBuffer)nb.position(start).limit(end)).slice(), end - start);
        }

        @Override
        protected MethodHandle getGetElem() {
            return GET_ELEM;
        }

        @Override
        protected MethodHandle getSetElem() {
            return SET_ELEM;
        }

        private int getElem(int index2) {
            try {
                return ((ByteBuffer)this.nb).get(index2) & 0xFF;
            }
            catch (IndexOutOfBoundsException e) {
                throw new ClassCastException();
            }
        }

        private void setElem(int index2, int elem) {
            try {
                if (index2 < ((ByteBuffer)this.nb).limit()) {
                    ((ByteBuffer)this.nb).put(index2, (byte)elem);
                }
            }
            catch (IndexOutOfBoundsException e) {
                throw new ClassCastException();
            }
        }

        @Override
        public boolean isUnsigned() {
            return true;
        }

        @Override
        public Class<?> getElementType() {
            return Integer.TYPE;
        }

        @Override
        public Class<?> getBoxedElementType() {
            return Integer.class;
        }

        @Override
        public int getInt(int index2) {
            return this.getElem(index2);
        }

        @Override
        public int getIntOptimistic(int index2, int programPoint) {
            return this.getElem(index2);
        }

        @Override
        public double getDouble(int index2) {
            return this.getInt(index2);
        }

        @Override
        public double getDoubleOptimistic(int index2, int programPoint) {
            return this.getElem(index2);
        }

        @Override
        public Object getObject(int index2) {
            return this.getInt(index2);
        }

        @Override
        public ArrayData set(int index2, Object value, boolean strict) {
            return this.set(index2, JSType.toInt32(value), strict);
        }

        @Override
        public ArrayData set(int index2, int value, boolean strict) {
            this.setElem(index2, value);
            return this;
        }

        @Override
        public ArrayData set(int index2, double value, boolean strict) {
            return this.set(index2, (int)value, strict);
        }
    }
}

