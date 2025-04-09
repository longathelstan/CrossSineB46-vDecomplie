/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.value;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.AnimationHelper;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\fH\u0016R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0012"}, d2={"Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "name", "", "value", "(Ljava/lang/String;Z)V", "animation", "Lnet/ccbluex/liquidbounce/utils/AnimationHelper;", "getAnimation", "()Lnet/ccbluex/liquidbounce/utils/AnimationHelper;", "fromJson", "", "element", "Lcom/google/gson/JsonElement;", "toJson", "Lcom/google/gson/JsonPrimitive;", "toggle", "CrossSine"})
public class BoolValue
extends Value<Boolean> {
    @NotNull
    private final AnimationHelper animation;

    public BoolValue(@NotNull String name, boolean value) {
        Intrinsics.checkNotNullParameter(name, "name");
        super(name, value);
        this.animation = new AnimationHelper(this);
        this.animation.animationX = value ? 5.0f : -5.0f;
    }

    @NotNull
    public final AnimationHelper getAnimation() {
        return this.animation;
    }

    @NotNull
    public JsonPrimitive toJson() {
        return new JsonPrimitive((Boolean)this.getValue());
    }

    @Override
    public void fromJson(@NotNull JsonElement element) {
        Intrinsics.checkNotNullParameter(element, "element");
        if (element.isJsonPrimitive()) {
            this.setValue(element.getAsBoolean() || StringsKt.equals(element.getAsString(), "true", true));
        }
    }

    public void toggle() {
        this.setValue((Boolean)this.getValue() == false);
    }
}

