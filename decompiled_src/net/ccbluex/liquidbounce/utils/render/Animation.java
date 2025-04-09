/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.utils.render;

import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.utils.render.EaseUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u00002\u00020\u0001:\u0001\u001fB-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\n\u00a2\u0006\u0002\u0010\u000bJ\u0006\u0010\u001e\u001a\u00020\u0000R\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0012\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0013\u001a\u00020\u0014X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u0011\u0010\b\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u000fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\u001c\u001a\u00020\u00078F\u00a2\u0006\u0006\u001a\u0004\b\u001d\u0010\u000f\u00a8\u0006 "}, d2={"Lnet/ccbluex/liquidbounce/utils/render/Animation;", "", "type", "Lnet/ccbluex/liquidbounce/utils/render/EaseUtils$EnumEasingType;", "order", "Lnet/ccbluex/liquidbounce/utils/render/EaseUtils$EnumEasingOrder;", "from", "", "to", "duration", "", "(Lnet/ccbluex/liquidbounce/utils/render/EaseUtils$EnumEasingType;Lnet/ccbluex/liquidbounce/utils/render/EaseUtils$EnumEasingOrder;DDJ)V", "getDuration", "()J", "getFrom", "()D", "getOrder", "()Lnet/ccbluex/liquidbounce/utils/render/EaseUtils$EnumEasingOrder;", "startTime", "state", "Lnet/ccbluex/liquidbounce/utils/render/Animation$EnumAnimationState;", "getState", "()Lnet/ccbluex/liquidbounce/utils/render/Animation$EnumAnimationState;", "setState", "(Lnet/ccbluex/liquidbounce/utils/render/Animation$EnumAnimationState;)V", "getTo", "getType", "()Lnet/ccbluex/liquidbounce/utils/render/EaseUtils$EnumEasingType;", "value", "getValue", "start", "EnumAnimationState", "CrossSine"})
public final class Animation {
    @NotNull
    private final EaseUtils.EnumEasingType type;
    @NotNull
    private final EaseUtils.EnumEasingOrder order;
    private final double from;
    private final double to;
    private final long duration;
    @NotNull
    private EnumAnimationState state;
    private long startTime;

    public Animation(@NotNull EaseUtils.EnumEasingType type, @NotNull EaseUtils.EnumEasingOrder order, double from, double to, long duration) {
        Intrinsics.checkNotNullParameter((Object)type, "type");
        Intrinsics.checkNotNullParameter((Object)order, "order");
        this.type = type;
        this.order = order;
        this.from = from;
        this.to = to;
        this.duration = duration;
        this.state = EnumAnimationState.NOT_STARTED;
    }

    @NotNull
    public final EaseUtils.EnumEasingType getType() {
        return this.type;
    }

    @NotNull
    public final EaseUtils.EnumEasingOrder getOrder() {
        return this.order;
    }

    public final double getFrom() {
        return this.from;
    }

    public final double getTo() {
        return this.to;
    }

    public final long getDuration() {
        return this.duration;
    }

    @NotNull
    public final EnumAnimationState getState() {
        return this.state;
    }

    public final void setState(@NotNull EnumAnimationState enumAnimationState) {
        Intrinsics.checkNotNullParameter((Object)enumAnimationState, "<set-?>");
        this.state = enumAnimationState;
    }

    @NotNull
    public final Animation start() {
        if (this.state != EnumAnimationState.NOT_STARTED) {
            throw new IllegalStateException("Animation already started!");
        }
        this.startTime = System.currentTimeMillis();
        this.state = EnumAnimationState.DURING;
        return this;
    }

    public final double getValue() {
        double d;
        switch (WhenMappings.$EnumSwitchMapping$0[this.state.ordinal()]) {
            case 1: {
                d = this.from;
                break;
            }
            case 2: {
                double percent = (double)(System.currentTimeMillis() - this.startTime) / (double)this.duration;
                if (percent > 1.0) {
                    this.state = EnumAnimationState.STOPPED;
                    d = this.to;
                    break;
                }
                d = this.from + (this.to - this.from) * EaseUtils.apply(this.type, this.order, percent);
                break;
            }
            case 3: {
                d = this.to;
                break;
            }
            default: {
                throw new NoWhenBranchMatchedException();
            }
        }
        return d;
    }

    @Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2={"Lnet/ccbluex/liquidbounce/utils/render/Animation$EnumAnimationState;", "", "(Ljava/lang/String;I)V", "NOT_STARTED", "DURING", "STOPPED", "CrossSine"})
    public static final class EnumAnimationState
    extends Enum<EnumAnimationState> {
        public static final /* enum */ EnumAnimationState NOT_STARTED = new EnumAnimationState();
        public static final /* enum */ EnumAnimationState DURING = new EnumAnimationState();
        public static final /* enum */ EnumAnimationState STOPPED = new EnumAnimationState();
        private static final /* synthetic */ EnumAnimationState[] $VALUES;

        public static EnumAnimationState[] values() {
            return (EnumAnimationState[])$VALUES.clone();
        }

        public static EnumAnimationState valueOf(String value) {
            return Enum.valueOf(EnumAnimationState.class, value);
        }

        static {
            $VALUES = enumAnimationStateArray = new EnumAnimationState[]{EnumAnimationState.NOT_STARTED, EnumAnimationState.DURING, EnumAnimationState.STOPPED};
        }
    }

    @Metadata(mv={1, 6, 0}, k=3, xi=48)
    public final class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] nArray = new int[EnumAnimationState.values().length];
            nArray[EnumAnimationState.NOT_STARTED.ordinal()] = 1;
            nArray[EnumAnimationState.DURING.ordinal()] = 2;
            nArray[EnumAnimationState.STOPPED.ordinal()] = 3;
            $EnumSwitchMapping$0 = nArray;
        }
    }
}

