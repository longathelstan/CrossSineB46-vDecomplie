/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.value;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.features.value.Value;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u00a2\u0006\u0002\u0010\u0005J\u000e\u0010\u0006\u001a\u00020\u00002\u0006\u0010\u0007\u001a\u00020\bJ\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\r\u001a\u00020\u000eH\u0016\u00a8\u0006\u000f"}, d2={"Lnet/ccbluex/liquidbounce/features/value/TextValue;", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "name", "value", "(Ljava/lang/String;Ljava/lang/String;)V", "append", "o", "", "fromJson", "", "element", "Lcom/google/gson/JsonElement;", "toJson", "Lcom/google/gson/JsonPrimitive;", "CrossSine"})
public class TextValue
extends Value<String> {
    public TextValue(@NotNull String name, @NotNull String value) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(value, "value");
        super(name, value);
    }

    @NotNull
    public JsonPrimitive toJson() {
        return new JsonPrimitive((String)this.getValue());
    }

    @Override
    public void fromJson(@NotNull JsonElement element) {
        Intrinsics.checkNotNullParameter(element, "element");
        if (element.isJsonPrimitive()) {
            String string = element.getAsString();
            Intrinsics.checkNotNullExpressionValue(string, "element.asString");
            this.setValue(string);
        }
    }

    @NotNull
    public final TextValue append(@NotNull Object o) {
        Intrinsics.checkNotNullParameter(o, "o");
        this.set(Intrinsics.stringPlus((String)this.get(), o));
        return this;
    }
}

