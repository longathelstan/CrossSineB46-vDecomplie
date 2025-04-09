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
import org.lwjgl.input.Keyboard;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0016\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\u0006\u0010\u000b\u001a\u00020\fJ\u000e\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u0002J\b\u0010\u000f\u001a\u00020\nH\u0016\u00a8\u0006\u0010"}, d2={"Lnet/ccbluex/liquidbounce/features/value/KeyBindValue;", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "name", "", "value", "(Ljava/lang/String;I)V", "fromJson", "", "element", "Lcom/google/gson/JsonElement;", "isPressed", "", "setKey", "newKey", "toJson", "CrossSine"})
public class KeyBindValue
extends Value<Integer> {
    public KeyBindValue(@NotNull String name, int value) {
        Intrinsics.checkNotNullParameter(name, "name");
        super(name, value);
    }

    @Override
    @NotNull
    public JsonElement toJson() {
        return (JsonElement)new JsonPrimitive((Number)this.getValue());
    }

    @Override
    public void fromJson(@NotNull JsonElement element) {
        Intrinsics.checkNotNullParameter(element, "element");
        if (element.isJsonPrimitive()) {
            int key = element.getAsInt();
            this.setValue((0 <= key ? key < 257 : false) ? key : 0);
        }
    }

    public final void setKey(int newKey) {
        this.setValue(newKey);
    }

    public final boolean isPressed() {
        return Keyboard.isKeyDown((int)((Number)this.getValue()).intValue());
    }
}

