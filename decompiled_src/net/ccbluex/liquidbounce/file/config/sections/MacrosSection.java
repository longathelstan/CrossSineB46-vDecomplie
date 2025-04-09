/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.file.config.sections;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.features.macro.Macro;
import net.ccbluex.liquidbounce.file.config.ConfigSection;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\b\u0010\u0007\u001a\u00020\u0006H\u0016\u00a8\u0006\b"}, d2={"Lnet/ccbluex/liquidbounce/file/config/sections/MacrosSection;", "Lnet/ccbluex/liquidbounce/file/config/ConfigSection;", "()V", "load", "", "json", "Lcom/google/gson/JsonObject;", "save", "CrossSine"})
public final class MacrosSection
extends ConfigSection {
    public MacrosSection() {
        super("macros");
    }

    @Override
    public void load(@NotNull JsonObject json) {
        Intrinsics.checkNotNullParameter(json, "json");
        CrossSine.INSTANCE.getMacroManager().getMacros().clear();
        JsonArray jsonArray = json.getAsJsonArray("macros");
        if (jsonArray == null) {
            return;
        }
        JsonArray jsonArray2 = jsonArray;
        for (JsonElement jsonElement : jsonArray2) {
            JsonObject macroJson = jsonElement.getAsJsonObject();
            ArrayList<Macro> arrayList = CrossSine.INSTANCE.getMacroManager().getMacros();
            int n = macroJson.get("key").getAsInt();
            String string = macroJson.get("command").getAsString();
            Intrinsics.checkNotNullExpressionValue(string, "macroJson.get(\"command\").asString");
            arrayList.add(new Macro(n, string));
        }
    }

    @Override
    @NotNull
    public JsonObject save() {
        JsonArray jsonArray = new JsonArray();
        for (Macro macro : CrossSine.INSTANCE.getMacroManager().getMacros()) {
            JsonObject macroJson = new JsonObject();
            macroJson.addProperty("key", (Number)macro.getKey());
            macroJson.addProperty("command", macro.getCommand());
            jsonArray.add((JsonElement)macroJson);
        }
        JsonObject json = new JsonObject();
        json.add("macros", (JsonElement)jsonArray);
        return json;
    }
}

