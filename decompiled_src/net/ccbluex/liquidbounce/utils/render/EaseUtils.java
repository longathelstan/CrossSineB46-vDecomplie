/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.utils.render;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0002+,B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0004H\u0007J\u000e\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010\f\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010\r\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u0010\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0007J\u000e\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u0010\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0007J\u000e\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u0010\u0010\u0016\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0007J\u0010\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0007J\u000e\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010\u001b\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010\u001c\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u0010\u0010\u001d\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0007J\u000e\u0010\u001e\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010\u001f\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010 \u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010!\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010\"\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u0010\u0010#\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0007J\u0010\u0010$\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004H\u0007J\u000e\u0010%\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020)J\u000e\u0010*\u001a\u00020'2\u0006\u0010(\u001a\u00020)\u00a8\u0006-"}, d2={"Lnet/ccbluex/liquidbounce/utils/render/EaseUtils;", "", "()V", "apply", "", "type", "Lnet/ccbluex/liquidbounce/utils/render/EaseUtils$EnumEasingType;", "order", "Lnet/ccbluex/liquidbounce/utils/render/EaseUtils$EnumEasingOrder;", "value", "easeInBack", "x", "easeInCirc", "easeInCubic", "easeInElastic", "easeInExpo", "easeInOutBack", "easeInOutCirc", "easeInOutCubic", "easeInOutElastic", "easeInOutExpo", "easeInOutQuad", "easeInOutQuart", "easeInOutQuint", "easeInOutSine", "easeInQuad", "easeInQuart", "easeInQuint", "easeInSine", "easeOutBack", "easeOutCirc", "easeOutCubic", "easeOutElastic", "easeOutExpo", "easeOutQuad", "easeOutQuart", "easeOutQuint", "easeOutSine", "getEnumEasingList", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "name", "", "getEnumEasingOrderList", "EnumEasingOrder", "EnumEasingType", "CrossSine"})
public final class EaseUtils {
    @NotNull
    public static final EaseUtils INSTANCE = new EaseUtils();

    private EaseUtils() {
    }

    public final double easeInSine(double x) {
        return 1.0 - Math.cos(x * Math.PI / (double)2);
    }

    public final double easeOutSine(double x) {
        return Math.sin(x * Math.PI / (double)2);
    }

    public final double easeInOutSine(double x) {
        return -(Math.cos(Math.PI * x) - 1.0) / (double)2;
    }

    public final double easeInQuad(double x) {
        return x * x;
    }

    public final double easeOutQuad(double x) {
        return 1.0 - (1.0 - x) * (1.0 - x);
    }

    public final double easeInOutQuad(double x) {
        return x < 0.5 ? (double)2 * x * x : 1.0 - Math.pow((double)-2 * x + (double)2, 2) / (double)2;
    }

    public final double easeInCubic(double x) {
        return x * x * x;
    }

    public final double easeOutCubic(double x) {
        return 1.0 - Math.pow(1.0 - x, 3);
    }

    public final double easeInOutCubic(double x) {
        return x < 0.5 ? (double)4 * x * x * x : 1.0 - Math.pow((double)-2 * x + (double)2, 3) / (double)2;
    }

    public final double easeInQuart(double x) {
        return x * x * x * x;
    }

    @JvmStatic
    public static final double easeOutQuart(double x) {
        return 1.0 - Math.pow(1.0 - x, 4);
    }

    @JvmStatic
    public static final double easeInOutQuart(double x) {
        return x < 0.5 ? (double)8 * x * x * x * x : 1.0 - Math.pow((double)-2 * x + (double)2, 4) / (double)2;
    }

    public final double easeInQuint(double x) {
        return x * x * x * x * x;
    }

    @JvmStatic
    public static final double easeOutQuint(double x) {
        return 1.0 - Math.pow(1.0 - x, 5);
    }

    @JvmStatic
    public static final double easeInOutQuint(double x) {
        return x < 0.5 ? (double)16 * x * x * x * x * x : 1.0 - Math.pow((double)-2 * x + (double)2, 5) / (double)2;
    }

    @JvmStatic
    public static final double easeInExpo(double x) {
        return x == 0.0 ? 0.0 : Math.pow(2.0, (double)10 * x - (double)10);
    }

    public final double easeOutExpo(double x) {
        return x == 1.0 ? 1.0 : 1.0 - Math.pow(2.0, (double)-10 * x);
    }

    @JvmStatic
    public static final double easeInOutExpo(double x) {
        return x == 0.0 ? 0.0 : (x == 1.0 ? 1.0 : (x < 0.5 ? Math.pow(2.0, (double)20 * x - (double)10) / (double)2 : ((double)2 - Math.pow(2.0, (double)-20 * x + (double)10)) / (double)2));
    }

    public final double easeInCirc(double x) {
        return 1.0 - Math.sqrt(1.0 - Math.pow(x, 2));
    }

    public final double easeOutCirc(double x) {
        return Math.sqrt(1.0 - Math.pow(x - 1.0, 2));
    }

    public final double easeInOutCirc(double x) {
        return x < 0.5 ? (1.0 - Math.sqrt(1.0 - Math.pow((double)2 * x, 2))) / (double)2 : (Math.sqrt(1.0 - Math.pow((double)-2 * x + (double)2, 2)) + 1.0) / (double)2;
    }

    public final double easeInBack(double x) {
        double c1 = 1.70158;
        double c3 = c1 + 1.0;
        return c3 * x * x * x - c1 * x * x;
    }

    @JvmStatic
    public static final double easeOutBack(double x) {
        double c1 = 1.70158;
        double c3 = c1 + 1.0;
        return 1.0 + c3 * Math.pow(x - 1.0, 3) + c1 * Math.pow(x - 1.0, 2);
    }

    public final double easeInOutBack(double x) {
        double c1 = 1.70158;
        double c2 = c1 * 1.525;
        return x < 0.5 ? Math.pow((double)2 * x, 2) * ((c2 + 1.0) * (double)2 * x - c2) / (double)2 : (Math.pow((double)2 * x - (double)2, 2) * ((c2 + 1.0) * (x * (double)2 - (double)2) + c2) + (double)2) / (double)2;
    }

    public final double easeInElastic(double x) {
        double c4 = 2.0943951023931953;
        return x == 0.0 ? 0.0 : (x == 1.0 ? 1.0 : Math.pow(-2.0, (double)10 * x - (double)10) * Math.sin((x * (double)10 - 10.75) * c4));
    }

    public final double easeOutElastic(double x) {
        double c4 = 2.0943951023931953;
        return x == 0.0 ? 0.0 : (x == 1.0 ? 1.0 : Math.pow(2.0, (double)-10 * x) * Math.sin((x * (double)10 - 0.75) * c4) + 1.0);
    }

    public final double easeInOutElastic(double x) {
        double c5 = 1.3962634015954636;
        return x == 0.0 ? 0.0 : (x == 1.0 ? 1.0 : (x < 0.5 ? -(Math.pow(2.0, (double)20 * x - (double)10) * Math.sin(((double)20 * x - 11.125) * c5)) / (double)2 : Math.pow(2.0, (double)-20 * x + (double)10) * Math.sin(((double)20 * x - 11.125) * c5) / (double)2 + 1.0));
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final ListValue getEnumEasingList(@NotNull String name) {
        void $this$mapTo$iv$iv;
        void $this$map$iv;
        Intrinsics.checkNotNullParameter(name, "name");
        EnumEasingType[] enumEasingTypeArray = EnumEasingType.values();
        String string = name;
        boolean $i$f$map = false;
        void var4_5 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(((void)$this$map$iv).length);
        boolean $i$f$mapTo = false;
        for (void item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            void var11_12 = item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            collection.add(it.toString());
        }
        Collection $this$toTypedArray$iv = (List)destination$iv$iv;
        boolean $i$f$toTypedArray = false;
        Collection thisCollection$iv = $this$toTypedArray$iv;
        String[] stringArray = thisCollection$iv.toArray(new String[0]);
        if (stringArray == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
        }
        String string2 = EnumEasingType.SINE.toString();
        String[] stringArray2 = stringArray;
        String string3 = string;
        return new ListValue(string3, stringArray2, string2);
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final ListValue getEnumEasingOrderList(@NotNull String name) {
        void $this$mapTo$iv$iv;
        void $this$map$iv;
        Intrinsics.checkNotNullParameter(name, "name");
        EnumEasingOrder[] enumEasingOrderArray = EnumEasingOrder.values();
        String string = name;
        boolean $i$f$map = false;
        void var4_5 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(((void)$this$map$iv).length);
        boolean $i$f$mapTo = false;
        for (void item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            void var11_12 = item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            collection.add(it.toString());
        }
        Collection $this$toTypedArray$iv = (List)destination$iv$iv;
        boolean $i$f$toTypedArray = false;
        Collection thisCollection$iv = $this$toTypedArray$iv;
        String[] stringArray = thisCollection$iv.toArray(new String[0]);
        if (stringArray == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
        }
        String string2 = EnumEasingOrder.FAST_AT_START.toString();
        String[] stringArray2 = stringArray;
        String string3 = string;
        return new ListValue(string3, stringArray2, string2);
    }

    @JvmStatic
    public static final double apply(@NotNull EnumEasingType type, @NotNull EnumEasingOrder order, double value) {
        double d;
        Object object;
        String methodName;
        block5: {
            Intrinsics.checkNotNullParameter((Object)type, "type");
            Intrinsics.checkNotNullParameter((Object)order, "order");
            if (type == EnumEasingType.NONE) {
                return value;
            }
            methodName = "ease" + order.getMethodName() + type.getFriendlyName();
            Method[] methodArray = INSTANCE.getClass().getDeclaredMethods();
            Intrinsics.checkNotNullExpressionValue(methodArray, "this.javaClass.declaredMethods");
            for (Object object2 : (Object[])methodArray) {
                Method it = (Method)object2;
                boolean bl = false;
                if (!it.getName().equals(methodName)) continue;
                object = object2;
                break block5;
            }
            object = null;
        }
        Method it = (Method)object;
        boolean bl = false;
        if (it != null) {
            Object[] objectArray = new Object[]{value};
            Object object3 = it.invoke((Object)INSTANCE, objectArray);
            if (object3 == null) {
                throw new NullPointerException("null cannot be cast to non-null type kotlin.Double");
            }
            d = (Double)object3;
        } else {
            ClientUtils.INSTANCE.logError(Intrinsics.stringPlus("Cannot found easing method: ", methodName));
            d = value;
        }
        return d;
    }

    @Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\r\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010\u00a8\u0006\u0011"}, d2={"Lnet/ccbluex/liquidbounce/utils/render/EaseUtils$EnumEasingType;", "", "(Ljava/lang/String;I)V", "friendlyName", "", "getFriendlyName", "()Ljava/lang/String;", "NONE", "SINE", "QUAD", "CUBIC", "QUART", "QUINT", "EXPO", "CIRC", "BACK", "ELASTIC", "CrossSine"})
    public static final class EnumEasingType
    extends Enum<EnumEasingType> {
        @NotNull
        private final String friendlyName;
        public static final /* enum */ EnumEasingType NONE = new EnumEasingType();
        public static final /* enum */ EnumEasingType SINE = new EnumEasingType();
        public static final /* enum */ EnumEasingType QUAD = new EnumEasingType();
        public static final /* enum */ EnumEasingType CUBIC = new EnumEasingType();
        public static final /* enum */ EnumEasingType QUART = new EnumEasingType();
        public static final /* enum */ EnumEasingType QUINT = new EnumEasingType();
        public static final /* enum */ EnumEasingType EXPO = new EnumEasingType();
        public static final /* enum */ EnumEasingType CIRC = new EnumEasingType();
        public static final /* enum */ EnumEasingType BACK = new EnumEasingType();
        public static final /* enum */ EnumEasingType ELASTIC = new EnumEasingType();
        private static final /* synthetic */ EnumEasingType[] $VALUES;

        private EnumEasingType() {
            String string = this.name().substring(0, 1);
            Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String\u2026ing(startIndex, endIndex)");
            String string2 = string.toUpperCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(string2, "this as java.lang.String).toUpperCase(Locale.ROOT)");
            String string3 = string2;
            string = this.name().substring(1, this.name().length());
            Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String\u2026ing(startIndex, endIndex)");
            string2 = string.toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(string2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            this.friendlyName = Intrinsics.stringPlus(string3, string2);
        }

        @NotNull
        public final String getFriendlyName() {
            return this.friendlyName;
        }

        public static EnumEasingType[] values() {
            return (EnumEasingType[])$VALUES.clone();
        }

        public static EnumEasingType valueOf(String value) {
            return Enum.valueOf(EnumEasingType.class, value);
        }

        static {
            $VALUES = enumEasingTypeArray = new EnumEasingType[]{EnumEasingType.NONE, EnumEasingType.SINE, EnumEasingType.QUAD, EnumEasingType.CUBIC, EnumEasingType.QUART, EnumEasingType.QUINT, EnumEasingType.EXPO, EnumEasingType.CIRC, EnumEasingType.BACK, EnumEasingType.ELASTIC};
        }
    }

    @Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\t\u00a8\u0006\n"}, d2={"Lnet/ccbluex/liquidbounce/utils/render/EaseUtils$EnumEasingOrder;", "", "methodName", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getMethodName", "()Ljava/lang/String;", "FAST_AT_START", "FAST_AT_END", "FAST_AT_START_AND_END", "CrossSine"})
    public static final class EnumEasingOrder
    extends Enum<EnumEasingOrder> {
        @NotNull
        private final String methodName;
        public static final /* enum */ EnumEasingOrder FAST_AT_START = new EnumEasingOrder("Out");
        public static final /* enum */ EnumEasingOrder FAST_AT_END = new EnumEasingOrder("In");
        public static final /* enum */ EnumEasingOrder FAST_AT_START_AND_END = new EnumEasingOrder("InOut");
        private static final /* synthetic */ EnumEasingOrder[] $VALUES;

        private EnumEasingOrder(String methodName) {
            this.methodName = methodName;
        }

        @NotNull
        public final String getMethodName() {
            return this.methodName;
        }

        public static EnumEasingOrder[] values() {
            return (EnumEasingOrder[])$VALUES.clone();
        }

        public static EnumEasingOrder valueOf(String value) {
            return Enum.valueOf(EnumEasingOrder.class, value);
        }

        static {
            $VALUES = enumEasingOrderArray = new EnumEasingOrder[]{EnumEasingOrder.FAST_AT_START, EnumEasingOrder.FAST_AT_END, EnumEasingOrder.FAST_AT_START_AND_END};
        }
    }
}

