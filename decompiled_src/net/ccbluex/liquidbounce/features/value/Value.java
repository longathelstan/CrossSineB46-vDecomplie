/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.value;

import com.google.gson.JsonElement;
import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000@\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0002\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\b&\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00028\u0000\u00a2\u0006\u0002\u0010\u0006J\u0018\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u001dH\u0016J\u0015\u0010\u001e\u001a\u00020\u001c2\u0006\u0010\u0005\u001a\u00028\u0000H\u0016\u00a2\u0006\u0002\u0010\u001aJ\u000e\u0010\u001f\u001a\u00020\b2\u0006\u0010 \u001a\u00020\u0004J\u001a\u0010\r\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\b0\u0011J\u0013\u0010\"\u001a\u00020\b2\b\u0010#\u001a\u0004\u0018\u00010\u0002H\u0096\u0002J\u0010\u0010$\u001a\u00020\u001c2\u0006\u0010%\u001a\u00020&H&J\u000b\u0010'\u001a\u00028\u0000\u00a2\u0006\u0002\u0010\u000bJ\b\u0010(\u001a\u00020)H\u0016J\b\u0010*\u001a\u00020\bH\u0016J\b\u0010+\u001a\u00020\bH\u0016J\u001a\u0010,\u001a\u00020\b2\b\u0010#\u001a\u0004\u0018\u00010\u00042\b\b\u0002\u0010-\u001a\u00020\bJ\u001d\u0010.\u001a\u00020\u001c2\u0006\u0010/\u001a\u00028\u00002\u0006\u00100\u001a\u00028\u0000H\u0014\u00a2\u0006\u0002\u00101J\u001d\u00102\u001a\u00020\u001c2\u0006\u0010/\u001a\u00028\u00002\u0006\u00100\u001a\u00028\u0000H\u0014\u00a2\u0006\u0002\u00101J\u0013\u00103\u001a\u00020\u001c2\u0006\u00100\u001a\u00028\u0000\u00a2\u0006\u0002\u0010\u001aJ\u0006\u00104\u001a\u00020\u001cJ\u0010\u00105\u001a\u00020\u001c2\u0006\u00106\u001a\u00020\bH\u0016J\n\u00107\u001a\u0004\u0018\u00010&H&R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0013\u0010\t\u001a\u00028\u0000\u00a2\u0006\n\n\u0002\u0010\f\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\r\u001a\u00020\b8F\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\b0\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u000f\"\u0004\b\u0016\u0010\u0017R\u001c\u0010\u0005\u001a\u00028\u0000X\u0086\u000e\u00a2\u0006\u0010\n\u0002\u0010\f\u001a\u0004\b\u0018\u0010\u000b\"\u0004\b\u0019\u0010\u001a\u00a8\u00068"}, d2={"Lnet/ccbluex/liquidbounce/features/value/Value;", "T", "", "name", "", "value", "(Ljava/lang/String;Ljava/lang/Object;)V", "Expanded", "", "default", "getDefault", "()Ljava/lang/Object;", "Ljava/lang/Object;", "displayable", "getDisplayable", "()Z", "displayableFunc", "Lkotlin/Function0;", "getName", "()Ljava/lang/String;", "textHovered", "getTextHovered", "setTextHovered", "(Z)V", "getValue", "setValue", "(Ljava/lang/Object;)V", "ColorValue", "", "", "changeValue", "contains", "text", "func", "equals", "other", "fromJson", "element", "Lcom/google/gson/JsonElement;", "get", "getAwtColor", "Ljava/awt/Color;", "getExpanded", "isExpanded", "nah", "ignoreCase", "onChange", "oldValue", "newValue", "(Ljava/lang/Object;Ljava/lang/Object;)V", "onChanged", "set", "setDefault", "setExpanded", "b", "toJson", "CrossSine"})
public abstract class Value<T> {
    @NotNull
    private final String name;
    private T value;
    private final T default;
    private boolean textHovered;
    @NotNull
    private Function0<Boolean> displayableFunc;
    private boolean Expanded;

    public Value(@NotNull String name, T value) {
        Intrinsics.checkNotNullParameter(name, "name");
        this.name = name;
        this.value = value;
        this.default = this.value;
        this.displayableFunc = displayableFunc.1.INSTANCE;
    }

    @NotNull
    public final String getName() {
        return this.name;
    }

    public final T getValue() {
        return this.value;
    }

    public final void setValue(T t) {
        this.value = t;
    }

    public final T getDefault() {
        return this.default;
    }

    public final boolean getTextHovered() {
        return this.textHovered;
    }

    public final void setTextHovered(boolean bl) {
        this.textHovered = bl;
    }

    @NotNull
    public final Value<T> displayable(@NotNull Function0<Boolean> func) {
        Intrinsics.checkNotNullParameter(func, "func");
        this.displayableFunc = func;
        return this;
    }

    public final boolean getDisplayable() {
        return this.displayableFunc.invoke();
    }

    public final void set(T newValue) {
        if (Intrinsics.areEqual(newValue, this.value)) {
            return;
        }
        T oldValue = this.get();
        try {
            this.onChange(oldValue, newValue);
            this.changeValue(newValue);
            this.onChanged(oldValue, newValue);
            CrossSine.INSTANCE.getConfigManager().smartSave();
        }
        catch (Exception e) {
            ClientUtils.INSTANCE.logError("[ValueSystem (" + this.name + ")]: " + e.getClass().getName() + " (" + e.getMessage() + ") [" + oldValue + " >> " + newValue + ']');
        }
    }

    public final T get() {
        return this.value;
    }

    public final void setDefault() {
        this.value = this.default;
    }

    public void changeValue(T value) {
        this.value = value;
    }

    @Nullable
    public abstract JsonElement toJson();

    public abstract void fromJson(@NotNull JsonElement var1);

    protected void onChange(T oldValue, T newValue) {
    }

    protected void onChanged(T oldValue, T newValue) {
    }

    public final boolean nah(@Nullable String other, boolean ignoreCase) {
        if (this == null) {
            return other == null;
        }
        return !ignoreCase ? ((String)((Object)this)).equals(other) : ((String)((Object)this)).equalsIgnoreCase(other);
    }

    public static /* synthetic */ boolean nah$default(Value value, String string, boolean bl, int n, Object object) {
        if (object != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: nah");
        }
        if ((n & 2) != 0) {
            bl = false;
        }
        return value.nah(string, bl);
    }

    public boolean equals(@Nullable Object other) {
        boolean bl;
        if (other == null) {
            return false;
        }
        if (this.value instanceof String && other instanceof String) {
            T t = this.value;
            if (t == null) {
                throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
            }
            return StringsKt.equals((String)t, (String)other, true);
        }
        T t = this.value;
        return t == null ? false : (bl = t.equals(other));
    }

    public final boolean contains(@NotNull String text) {
        boolean bl;
        Intrinsics.checkNotNullParameter(text, "text");
        if (this.value instanceof String) {
            T t = this.value;
            if (t == null) {
                throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
            }
            bl = StringsKt.contains((CharSequence)((String)t), text, true);
        } else {
            bl = false;
        }
        return bl;
    }

    public boolean getExpanded() {
        return this.Expanded;
    }

    public void setExpanded(boolean b) {
    }

    public boolean isExpanded() {
        return this.Expanded;
    }

    @NotNull
    public Color getAwtColor() {
        return new Color(((Number)this.value).intValue(), true);
    }

    public void ColorValue(@NotNull String name, int value) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkParameterIsNotNull(name, "name");
        this.ColorValue(name, value);
    }
}

