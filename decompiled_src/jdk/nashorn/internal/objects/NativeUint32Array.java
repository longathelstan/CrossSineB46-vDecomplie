/*
 * Decompiled with CFR 0.152.
 */
package jdk.nashorn.internal.objects;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
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

public final class NativeUint32Array
extends ArrayBufferView {
    public static final int BYTES_PER_ELEMENT = 4;
    private static PropertyMap $nasgenmap$;
    private static final ArrayBufferView.Factory FACTORY;

    public static NativeUint32Array constructor(boolean newObj, Object self, Object ... args2) {
        return (NativeUint32Array)NativeUint32Array.constructorImpl(newObj, args2, FACTORY);
    }

    NativeUint32Array(NativeArrayBuffer buffer, int byteOffset, int length) {
        super(buffer, byteOffset, length);
    }

    @Override
    protected ArrayBufferView.Factory factory() {
        return FACTORY;
    }

    protected static Object set(Object self, Object array, Object offset) {
        return ArrayBufferView.setImpl(self, array, offset);
    }

    protected static NativeUint32Array subarray(Object self, Object begin, Object end) {
        return (NativeUint32Array)ArrayBufferView.subarrayImpl(self, begin, end);
    }

    @Override
    protected ScriptObject getPrototype(Global global) {
        return global.getUint32ArrayPrototype();
    }

    static {
        FACTORY = new ArrayBufferView.Factory(4){

            @Override
            public ArrayBufferView construct(NativeArrayBuffer buffer, int byteBegin, int length) {
                return new NativeUint32Array(buffer, byteBegin, length);
            }

            public Uint32ArrayData createArrayData(ByteBuffer nb, int start, int end) {
                return new Uint32ArrayData(nb.order(ByteOrder.nativeOrder()).asIntBuffer(), start, end);
            }

            @Override
            public String getClassName() {
                return "Uint32Array";
            }
        };
        NativeUint32Array.$clinit$();
    }

    public static void $clinit$() {
        $nasgenmap$ = PropertyMap.newMap(Collections.EMPTY_LIST);
    }

    private static final class Uint32ArrayData
    extends TypedArrayData<IntBuffer> {
        private static final MethodHandle GET_ELEM = CompilerConstants.specialCall(MethodHandles.lookup(), Uint32ArrayData.class, "getElem", Double.TYPE, Integer.TYPE).methodHandle();
        private static final MethodHandle SET_ELEM = CompilerConstants.specialCall(MethodHandles.lookup(), Uint32ArrayData.class, "setElem", Void.TYPE, Integer.TYPE, Integer.TYPE).methodHandle();

        private Uint32ArrayData(IntBuffer nb, int start, int end) {
            super(((IntBuffer)nb.position(start).limit(end)).slice(), end - start);
        }

        @Override
        protected MethodHandle getGetElem() {
            return GET_ELEM;
        }

        @Override
        protected MethodHandle getSetElem() {
            return SET_ELEM;
        }

        @Override
        public MethodHandle getElementGetter(Class<?> returnType, int programPoint) {
            if (returnType == Integer.TYPE) {
                return null;
            }
            return this.getContinuousElementGetter(this.getClass(), GET_ELEM, returnType, programPoint);
        }

        private int getRawElem(int index2) {
            try {
                return ((IntBuffer)this.nb).get(index2);
            }
            catch (IndexOutOfBoundsException e) {
                throw new ClassCastException();
            }
        }

        private double getElem(int index2) {
            return JSType.toUint32(this.getRawElem(index2));
        }

        private void setElem(int index2, int elem) {
            try {
                if (index2 < ((IntBuffer)this.nb).limit()) {
                    ((IntBuffer)this.nb).put(index2, elem);
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
            return Double.TYPE;
        }

        @Override
        public Class<?> getBoxedElementType() {
            return Double.class;
        }

        @Override
        public int getInt(int index2) {
            return this.getRawElem(index2);
        }

        @Override
        public int getIntOptimistic(int index2, int programPoint) {
            return JSType.toUint32Optimistic(this.getRawElem(index2), programPoint);
        }

        @Override
        public double getDouble(int index2) {
            return this.getElem(index2);
        }

        @Override
        public double getDoubleOptimistic(int index2, int programPoint) {
            return this.getElem(index2);
        }

        @Override
        public Object getObject(int index2) {
            return this.getElem(index2);
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

