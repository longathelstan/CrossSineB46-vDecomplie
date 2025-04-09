/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\r\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0002\b\u0016\n\u0002\u0010!\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J/\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00040\u00072\u0012\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00070\u00072\u0006\u0010\t\u001a\u00020\u0004H\u0007\u00a2\u0006\u0002\u0010\nJ'\u0010\u000b\u001a\u00020\u00042\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\u00072\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00040\u0007\u00a2\u0006\u0002\u0010\u000eJ5\u0010\u000f\u001a\u00020\u00042\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00040\u00072\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00040\u00072\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00040\u0007\u00a2\u0006\u0002\u0010\u0013J\u0016\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0015J&\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u00042\u0006\u0010\u001c\u001a\u00020\u00042\u0006\u0010\u001d\u001a\u00020\u0004J5\u0010\u001e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00070\u00072\u0012\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00070\u00072\u0006\u0010\u001f\u001a\u00020\u0017H\u0007\u00a2\u0006\u0002\u0010 J\u001e\u0010!\u001a\u00020\u00042\u0006\u0010\"\u001a\u00020\u00042\u0006\u0010#\u001a\u00020\u00042\u0006\u0010$\u001a\u00020\u0004J5\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00040\u00072\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\u00072\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00040\u00072\u0006\u0010\t\u001a\u00020\u0004\u00a2\u0006\u0002\u0010&J\u000e\u0010'\u001a\u00020\u00042\u0006\u0010(\u001a\u00020\u0004J_\u0010)\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00070\u00072\u0012\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00070\u00072\u0006\u0010*\u001a\u00020\u00042\b\b\u0002\u0010+\u001a\u00020\u00172\b\b\u0002\u0010,\u001a\u00020\u00172\u0014\b\u0002\u0010-\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00070.H\u0007\u00a2\u0006\u0002\u0010/J\u001a\u00100\u001a\u000201*\u00020\u00042\u0006\u00102\u001a\u00020\u00042\u0006\u00103\u001a\u00020\u0004J\u001a\u00100\u001a\u000201*\u00020\u00152\u0006\u00102\u001a\u00020\u00152\u0006\u00103\u001a\u00020\u0015J\u001a\u00100\u001a\u000201*\u00020\u00172\u0006\u00102\u001a\u00020\u00172\u0006\u00103\u001a\u00020\u0017J\u001a\u00104\u001a\u000205*\u0002052\u0006\u00106\u001a\u0002072\u0006\u00108\u001a\u00020\u0004J\n\u00109\u001a\u00020\u0004*\u00020\u0004J\n\u0010:\u001a\u00020\u0004*\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006;"}, d2={"Lnet/ccbluex/liquidbounce/utils/MathUtils;", "", "()V", "DEGREES_TO_RADIANS", "", "RADIANS_TO_DEGREES", "calcCurvePoint", "", "points", "t", "([[Ljava/lang/Double;D)[Ljava/lang/Double;", "distanceSq", "a", "b", "([Ljava/lang/Double;[Ljava/lang/Double;)D", "distanceToSegmentSq", "p", "v", "w", "([Ljava/lang/Double;[Ljava/lang/Double;[Ljava/lang/Double;)D", "gaussian", "", "x", "", "sigma", "getDistance", "x1", "y1", "x2", "y2", "getPointsOnCurve", "num", "([[Ljava/lang/Double;I)[[Ljava/lang/Double;", "interpolate", "old", "current", "partialTicks", "lerp", "([Ljava/lang/Double;[Ljava/lang/Double;D)[Ljava/lang/Double;", "radians", "degrees", "simplifyPoints", "epsilon", "start", "end", "outPoints", "", "([[Ljava/lang/Double;DIILjava/util/List;)[[Ljava/lang/Double;", "inRange", "", "base", "range", "offset", "Lnet/minecraft/util/Vec3;", "direction", "Lnet/minecraft/util/EnumFacing;", "value", "toDegrees", "toRadians", "CrossSine"})
public final class MathUtils {
    @NotNull
    public static final MathUtils INSTANCE = new MathUtils();
    public static final double DEGREES_TO_RADIANS = Math.PI / 180;
    public static final double RADIANS_TO_DEGREES = 57.29577951308232;

    private MathUtils() {
    }

    public final double radians(double degrees) {
        return degrees * Math.PI / (double)180;
    }

    public final double interpolate(double old, double current, double partialTicks) {
        return old + (current - old) * partialTicks;
    }

    public final double getDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2.0) + Math.pow(y1 - y2, 2.0));
    }

    @NotNull
    public final Double[] lerp(@NotNull Double[] a, @NotNull Double[] b, double t) {
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        Double[] doubleArray = new Double[]{a[0] + (b[0] - a[0]) * t, a[1] + (b[1] - a[1]) * t};
        return doubleArray;
    }

    public final double distanceSq(@NotNull Double[] a, @NotNull Double[] b) {
        Intrinsics.checkNotNullParameter(a, "a");
        Intrinsics.checkNotNullParameter(b, "b");
        return Math.pow(a[0] - b[0], 2) + Math.pow(a[1] - b[1], 2);
    }

    public final double distanceToSegmentSq(@NotNull Double[] p, @NotNull Double[] v, @NotNull Double[] w) {
        Intrinsics.checkNotNullParameter(p, "p");
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(w, "w");
        double l2 = this.distanceSq(v, w);
        if (l2 == 0.0) {
            return this.distanceSq(p, v);
        }
        return this.distanceSq(p, this.lerp(v, w, RangesKt.coerceAtLeast(RangesKt.coerceAtMost(((p[0] - v[0]) * (w[0] - v[0]) + (p[1] - v[1]) * (w[1] - v[1])) / l2, 1.0), 0.0)));
    }

    public final boolean inRange(float $this$inRange, float base, float range) {
        float f = base - range;
        return $this$inRange <= base + range ? f <= $this$inRange : false;
    }

    public final boolean inRange(int $this$inRange, int base, int range) {
        int n = base - range;
        return $this$inRange <= base + range ? n <= $this$inRange : false;
    }

    public final boolean inRange(double $this$inRange, double base, double range) {
        double d = base - range;
        return $this$inRange <= base + range ? d <= $this$inRange : false;
    }

    @JvmStatic
    @NotNull
    public static final Double[] calcCurvePoint(@NotNull Double[][] points, double t) {
        Double[] doubleArray;
        Intrinsics.checkNotNullParameter(points, "points");
        List cpoints = new ArrayList();
        int n = 0;
        int n2 = ((Object[])points).length - 1;
        while (n < n2) {
            int i = n++;
            cpoints.add(INSTANCE.lerp(points[i], points[i + 1], t));
        }
        if (cpoints.size() == 1) {
            doubleArray = (Double[])cpoints.get(0);
        } else {
            Collection $this$toTypedArray$iv = cpoints;
            boolean $i$f$toTypedArray = false;
            Collection thisCollection$iv = $this$toTypedArray$iv;
            T[] TArray = thisCollection$iv.toArray((T[])new Double[0][]);
            if (TArray == null) {
                throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
            }
            doubleArray = MathUtils.calcCurvePoint((Double[][])TArray, t);
        }
        return doubleArray;
    }

    @JvmStatic
    @NotNull
    public static final Double[][] getPointsOnCurve(@NotNull Double[][] points, int num) {
        Intrinsics.checkNotNullParameter(points, "points");
        List cpoints = new ArrayList();
        int n = 0;
        while (n < num) {
            int i = n++;
            double t = (double)i / ((double)num - 1.0);
            cpoints.add(MathUtils.calcCurvePoint(points, t));
        }
        Collection $this$toTypedArray$iv = cpoints;
        boolean $i$f$toTypedArray = false;
        Collection thisCollection$iv = $this$toTypedArray$iv;
        T[] TArray = thisCollection$iv.toArray((T[])new Double[0][]);
        if (TArray == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
        }
        return (Double[][])TArray;
    }

    public final double toRadians(double $this$toRadians) {
        return $this$toRadians * (Math.PI / 180);
    }

    @NotNull
    public final Vec3 offset(@NotNull Vec3 $this$offset, @NotNull EnumFacing direction, double value) {
        Intrinsics.checkNotNullParameter($this$offset, "<this>");
        Intrinsics.checkNotNullParameter(direction, "direction");
        Vec3i vec3i = direction.func_176730_m();
        return new Vec3($this$offset.field_72450_a + value * (double)vec3i.func_177958_n(), $this$offset.field_72448_b + value * (double)vec3i.func_177956_o(), $this$offset.field_72449_c + value * (double)vec3i.func_177952_p());
    }

    public final double toDegrees(double $this$toDegrees) {
        return $this$toDegrees * 57.29577951308232;
    }

    public final float gaussian(int x, float sigma) {
        float s = sigma * sigma * (float)2;
        return 1.0f / (float)Math.sqrt((float)Math.PI * s) * (float)Math.exp((float)(-(x * x)) / s);
    }

    @JvmStatic
    @JvmOverloads
    @NotNull
    public static final Double[][] simplifyPoints(@NotNull Double[][] points, double epsilon, int start, int end, @NotNull List<Double[]> outPoints) {
        Intrinsics.checkNotNullParameter(points, "points");
        Intrinsics.checkNotNullParameter(outPoints, "outPoints");
        Double[] s = points[start];
        Double[] e = points[end - 1];
        double maxDistSq = 0.0;
        int maxNdx = 1;
        int n = start + 1;
        int n2 = end - 1;
        while (n < n2) {
            int i;
            double distSq;
            if (!((distSq = INSTANCE.distanceToSegmentSq(points[i = n++], s, e)) > maxDistSq)) continue;
            maxDistSq = distSq;
            maxNdx = i;
        }
        if (Math.sqrt(maxDistSq) > epsilon) {
            MathUtils.simplifyPoints(points, epsilon, start, maxNdx + 1, outPoints);
            MathUtils.simplifyPoints(points, epsilon, maxNdx, end, outPoints);
        } else {
            outPoints.add(s);
            outPoints.add(e);
        }
        Collection $this$toTypedArray$iv = outPoints;
        boolean $i$f$toTypedArray = false;
        Collection thisCollection$iv = $this$toTypedArray$iv;
        T[] TArray = thisCollection$iv.toArray((T[])new Double[0][]);
        if (TArray == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
        }
        return (Double[][])TArray;
    }

    public static /* synthetic */ Double[][] simplifyPoints$default(Double[][] doubleArray, double d, int n, int n2, List list, int n3, Object object) {
        if ((n3 & 4) != 0) {
            n = 0;
        }
        if ((n3 & 8) != 0) {
            n2 = ((Object[])doubleArray).length;
        }
        if ((n3 & 0x10) != 0) {
            list = new ArrayList();
        }
        return MathUtils.simplifyPoints(doubleArray, d, n, n2, list);
    }

    @JvmStatic
    @JvmOverloads
    @NotNull
    public static final Double[][] simplifyPoints(@NotNull Double[][] points, double epsilon, int start, int end) {
        Intrinsics.checkNotNullParameter(points, "points");
        return MathUtils.simplifyPoints$default(points, epsilon, start, end, null, 16, null);
    }

    @JvmStatic
    @JvmOverloads
    @NotNull
    public static final Double[][] simplifyPoints(@NotNull Double[][] points, double epsilon, int start) {
        Intrinsics.checkNotNullParameter(points, "points");
        return MathUtils.simplifyPoints$default(points, epsilon, start, 0, null, 24, null);
    }

    @JvmStatic
    @JvmOverloads
    @NotNull
    public static final Double[][] simplifyPoints(@NotNull Double[][] points, double epsilon) {
        Intrinsics.checkNotNullParameter(points, "points");
        return MathUtils.simplifyPoints$default(points, epsilon, 0, 0, null, 28, null);
    }
}

