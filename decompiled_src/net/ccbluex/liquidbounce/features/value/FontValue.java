/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.value;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.FontUtils;
import net.minecraft.client.gui.FontRenderer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\u000e\u0010\u0015\u001a\u00020\b2\u0006\u0010\u0016\u001a\u00020\u0004J\b\u0010\u0017\u001a\u00020\u0014H\u0016R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00020\u000e8F\u00a2\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0018"}, d2={"Lnet/ccbluex/liquidbounce/features/value/FontValue;", "Lnet/ccbluex/liquidbounce/features/value/Value;", "Lnet/minecraft/client/gui/FontRenderer;", "valueName", "", "value", "(Ljava/lang/String;Lnet/minecraft/client/gui/FontRenderer;)V", "openList", "", "getOpenList", "()Z", "setOpenList", "(Z)V", "values", "", "getValues", "()Ljava/util/List;", "fromJson", "", "element", "Lcom/google/gson/JsonElement;", "set", "name", "toJson", "CrossSine"})
public final class FontValue
extends Value<FontRenderer> {
    private boolean openList;

    public FontValue(@NotNull String valueName, @NotNull FontRenderer value) {
        Intrinsics.checkNotNullParameter(valueName, "valueName");
        Intrinsics.checkNotNullParameter(value, "value");
        super(valueName, value);
    }

    public final boolean getOpenList() {
        return this.openList;
    }

    public final void setOpenList(boolean bl) {
        this.openList = bl;
    }

    @Override
    @NotNull
    public JsonElement toJson() {
        Object[] fontDetails = Fonts.getFontDetails((FontRenderer)this.getValue());
        JsonObject valueObject = new JsonObject();
        Object object = fontDetails[0];
        if (object == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.String");
        }
        valueObject.addProperty("fontName", (String)object);
        Object object2 = fontDetails[1];
        if (object2 == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Int");
        }
        valueObject.addProperty("fontSize", (Number)((Integer)object2));
        return (JsonElement)valueObject;
    }

    @Override
    public void fromJson(@NotNull JsonElement element) {
        Intrinsics.checkNotNullParameter(element, "element");
        if (!element.isJsonObject()) {
            return;
        }
        JsonObject valueObject = element.getAsJsonObject();
        FontRenderer fontRenderer = Fonts.getFontRenderer(valueObject.get("fontName").getAsString(), valueObject.get("fontSize").getAsInt());
        Intrinsics.checkNotNullExpressionValue(fontRenderer, "getFontRenderer(valueObj\u2026Object[\"fontSize\"].asInt)");
        this.setValue(fontRenderer);
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final List<FontRenderer> getValues() {
        void $this$mapTo$iv$iv;
        Pair<String, FontRenderer>[] $this$map$iv = FontUtils.INSTANCE.getAllFontDetails();
        boolean $i$f$map = false;
        Pair<String, FontRenderer>[] pairArray = $this$map$iv;
        Collection destination$iv$iv = new ArrayList($this$map$iv.length);
        boolean $i$f$mapTo = false;
        for (void item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            void var10_10 = item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            collection.add((FontRenderer)it.getSecond());
        }
        return (List)destination$iv$iv;
    }

    public final boolean set(@NotNull String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        if (StringsKt.equals(name, "Minecraft", true)) {
            FontRenderer fontRenderer = Fonts.minecraftFont;
            Intrinsics.checkNotNullExpressionValue(fontRenderer, "minecraftFont");
            this.set(fontRenderer);
            return true;
        }
        if (StringsKt.contains$default((CharSequence)name, " - ", false, 2, null)) {
            String[] stringArray = new String[]{" - "};
            List spiced = StringsKt.split$default((CharSequence)name, stringArray, false, 0, 6, null);
            FontRenderer fontRenderer = Fonts.getFontRenderer((String)spiced.get(0), Integer.parseInt((String)spiced.get(1)));
            if (fontRenderer == null) {
                return false;
            }
            this.set(fontRenderer);
            return true;
        }
        return false;
    }
}

