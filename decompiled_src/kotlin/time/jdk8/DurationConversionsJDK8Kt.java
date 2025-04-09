/*
 * Decompiled with CFR 0.152.
 */
package kotlin.time.jdk8;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.WasExperimental;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlin.time.ExperimentalTime;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(mv={1, 6, 0}, k=2, xi=48, d1={"\u0000\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a\u001a\u0010\u0000\u001a\u00020\u0001*\u00020\u0002H\u0087\b\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0003\u0010\u0004\u001a\u0015\u0010\u0005\u001a\u00020\u0002*\u00020\u0001H\u0087\b\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006\u0082\u0002\u000b\n\u0002\b\u0019\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u0007"}, d2={"toJavaDuration", "Ljava/time/Duration;", "Lkotlin/time/Duration;", "toJavaDuration-LRDsOJo", "(J)Ljava/time/Duration;", "toKotlinDuration", "(Ljava/time/Duration;)J", "kotlin-stdlib-jdk8"}, pn="kotlin.time")
@JvmName(name="DurationConversionsJDK8Kt")
public final class DurationConversionsJDK8Kt {
    @SinceKotlin(version="1.6")
    @WasExperimental(markerClass={ExperimentalTime.class})
    @InlineOnly
    private static final long toKotlinDuration(java.time.Duration $this$toKotlinDuration) {
        Intrinsics.checkNotNullParameter($this$toKotlinDuration, "<this>");
        return Duration.plus-LRDsOJo(DurationKt.toDuration($this$toKotlinDuration.getSeconds(), DurationUnit.SECONDS), DurationKt.toDuration($this$toKotlinDuration.getNano(), DurationUnit.NANOSECONDS));
    }

    /*
     * WARNING - void declaration
     */
    @SinceKotlin(version="1.6")
    @WasExperimental(markerClass={ExperimentalTime.class})
    @InlineOnly
    private static final java.time.Duration toJavaDuration-LRDsOJo(long $this$toJavaDuration) {
        void nanoseconds;
        boolean bl = false;
        int n = Duration.getNanosecondsComponent-impl($this$toJavaDuration);
        long seconds = Duration.getInWholeSeconds-impl($this$toJavaDuration);
        boolean bl2 = false;
        java.time.Duration duration = java.time.Duration.ofSeconds(seconds, (long)nanoseconds);
        Intrinsics.checkNotNullExpressionValue(duration, "toJavaDuration-LRDsOJo");
        return duration;
    }
}

