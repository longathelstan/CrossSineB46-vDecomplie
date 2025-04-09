/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.value;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.features.value.Value;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B#\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0002\u00a2\u0006\u0002\u0010\u0007J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0006\u001a\u00020\u0002H\u0016J\u000e\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u0002J\u0010\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u0012\u0010\u0014\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\u000e\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u0002J\u0010\u0010\u0019\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0018\u00010\u001aJ\u0006\u0010\u001b\u001a\u00020\u000eJ\u0006\u0010\u001c\u001a\u00020\u000eJ\b\u0010\u001d\u001a\u00020\u001eH\u0016R\u0012\u0010\b\u001a\u00020\t8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00020\u0005\u00a2\u0006\n\n\u0002\u0010\f\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u001f"}, d2={"Lnet/ccbluex/liquidbounce/features/value/ListValue;", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "name", "values", "", "value", "(Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)V", "openList", "", "getValues", "()[Ljava/lang/String;", "[Ljava/lang/String;", "changeValue", "", "containsValue", "string", "fromJson", "element", "Lcom/google/gson/JsonElement;", "getModeGet", "i", "", "getModeListNumber", "mode", "getModes", "", "nextValue", "prevValue", "toJson", "Lcom/google/gson/JsonPrimitive;", "CrossSine"})
public class ListValue
extends Value<String> {
    @NotNull
    private final String[] values;
    @JvmField
    public boolean openList;

    public ListValue(@NotNull String name, @NotNull String[] values2, @NotNull String value) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(values2, "values");
        Intrinsics.checkNotNullParameter(value, "value");
        super(name, value);
        this.values = values2;
        this.setValue(value);
    }

    @NotNull
    public final String[] getValues() {
        return this.values;
    }

    public final int getModeListNumber(@NotNull String mode2) {
        Intrinsics.checkNotNullParameter(mode2, "mode");
        return ArraysKt.indexOf(this.values, mode2);
    }

    public final boolean containsValue(@NotNull String string) {
        Intrinsics.checkNotNullParameter(string, "string");
        return Arrays.stream(this.values).anyMatch(arg_0 -> ListValue.containsValue$lambda-0(string, arg_0));
    }

    @Override
    public void changeValue(@NotNull String value) {
        Intrinsics.checkNotNullParameter(value, "value");
        for (String element : this.values) {
            if (!StringsKt.equals(element, value, true)) continue;
            this.setValue(element);
            break;
        }
    }

    public final void nextValue() {
        int index2 = ArraysKt.indexOf(this.values, this.getValue()) + 1;
        if (index2 > this.values.length - 1) {
            index2 = 0;
        }
        this.setValue(this.values[index2]);
    }

    public final void prevValue() {
        int index2 = ArraysKt.indexOf(this.values, this.getValue()) - 1;
        if (index2 < 0) {
            index2 = this.values.length - 1;
        }
        this.setValue(this.values[index2]);
    }

    @Nullable
    public final List<String> getModes() {
        return ArraysKt.toList(this.values);
    }

    @Nullable
    public String getModeGet(int i) {
        return this.values[i];
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
            this.changeValue(string);
        }
    }

    private static final boolean containsValue$lambda-0(String $string, String it) {
        Intrinsics.checkNotNullParameter($string, "$string");
        return StringsKt.equals(it, $string, true);
    }
}

