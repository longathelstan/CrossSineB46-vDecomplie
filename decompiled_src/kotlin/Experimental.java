/*
 * Decompiled with CFR 0.152.
 */
package kotlin;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.AnnotationTarget;
import kotlin.annotation.Retention;
import kotlin.annotation.Target;

@Target(allowedTargets={AnnotationTarget.ANNOTATION_CLASS})
@Retention(value=AnnotationRetention.BINARY)
@Deprecated(message="Please use RequiresOptIn instead.")
@java.lang.annotation.Retention(value=RetentionPolicy.CLASS)
@java.lang.annotation.Target(value={ElementType.ANNOTATION_TYPE})
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\u0002\u0018\u00002\u00020\u0001:\u0001\u0005B\n\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003R\u000f\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0006\u001a\u0004\b\u0002\u0010\u0004\u00f8\u0001\u0000\u0082\u0002\u0007\n\u0005\b\u009120\u0001\u00a8\u0006\u0006"}, d2={"Lkotlin/Experimental;", "", "level", "Lkotlin/Experimental$Level;", "()Lkotlin/Experimental$Level;", "Level", "kotlin-stdlib"})
@SinceKotlin(version="1.2")
public @interface Experimental {
    public Level level() default Level.ERROR;

    @Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004\u00a8\u0006\u0005"}, d2={"Lkotlin/Experimental$Level;", "", "(Ljava/lang/String;I)V", "WARNING", "ERROR", "kotlin-stdlib"})
    public static final class Level
    extends Enum<Level> {
        public static final /* enum */ Level WARNING = new Level();
        public static final /* enum */ Level ERROR = new Level();
        private static final /* synthetic */ Level[] $VALUES;

        public static Level[] values() {
            return (Level[])$VALUES.clone();
        }

        public static Level valueOf(String value) {
            return Enum.valueOf(Level.class, value);
        }

        static {
            $VALUES = levelArray = new Level[]{Level.WARNING, Level.ERROR};
        }
    }
}

