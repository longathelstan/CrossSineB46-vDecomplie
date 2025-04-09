/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.value;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import net.ccbluex.liquidbounce.features.value.Value;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0004\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B1\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0002\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0002\u0012\u0006\u0010\b\u001a\u00020\u0002\u00a2\u0006\u0002\u0010\tJ\u000e\u0010\u000e\u001a\u00020\u00002\u0006\u0010\u000f\u001a\u00020\u0002J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u0002H\u0016J\u000e\u0010\u0015\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\u0017J\b\u0010\u0018\u001a\u00020\u0019H\u0016R\u0011\u0010\b\u001a\u00020\u0002\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0007\u001a\u00020\u0002\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0011\u0010\u0006\u001a\u00020\u0002\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000b\u00a8\u0006\u001a"}, d2={"Lnet/ccbluex/liquidbounce/features/value/NumberValue;", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "name", "", "value", "minimum", "maximum", "inc", "(Ljava/lang/String;DDDD)V", "getInc", "()D", "getMaximum", "getMinimum", "append", "o", "fromJson", "", "element", "Lcom/google/gson/JsonElement;", "getDouble", "set", "newValue", "", "toJson", "Lcom/google/gson/JsonPrimitive;", "CrossSine"})
public class NumberValue
extends Value<Double> {
    private final double minimum;
    private final double maximum;
    private final double inc;

    public NumberValue(@NotNull String name, double value, double minimum, double maximum, double inc) {
        Intrinsics.checkNotNullParameter(name, "name");
        super(name, value);
        this.minimum = minimum;
        this.maximum = maximum;
        this.inc = inc;
    }

    public /* synthetic */ NumberValue(String string, double d, double d2, double d3, double d4, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 4) != 0) {
            d2 = 0.0;
        }
        if ((n & 8) != 0) {
            d3 = Double.MAX_VALUE;
        }
        this(string, d, d2, d3, d4);
    }

    public final double getMinimum() {
        return this.minimum;
    }

    public final double getMaximum() {
        return this.maximum;
    }

    public final double getInc() {
        return this.inc;
    }

    @Override
    public final void set(@NotNull Number newValue) {
        Intrinsics.checkNotNullParameter(newValue, "newValue");
        this.set(newValue.doubleValue());
    }

    @NotNull
    public JsonPrimitive toJson() {
        return new JsonPrimitive((Number)this.getValue());
    }

    @Override
    public void fromJson(@NotNull JsonElement element) {
        Intrinsics.checkNotNullParameter(element, "element");
        if (element.isJsonPrimitive()) {
            this.setValue(element.getAsDouble());
        }
    }

    public double getDouble() {
        return (double)MathKt.roundToInt(((Number)this.get()).doubleValue() / this.inc) * this.inc;
    }

    @NotNull
    public final NumberValue append(double o) {
        this.set(((Number)this.get()).doubleValue() + o);
        return this;
    }
}

