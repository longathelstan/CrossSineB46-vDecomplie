/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.file.config.sections;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.features.module.EnumAutoDisableType;
import net.ccbluex.liquidbounce.features.module.EnumTriggerType;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.file.config.ConfigSection;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\b\u0010\u0007\u001a\u00020\u0006H\u0016\u00a8\u0006\b"}, d2={"Lnet/ccbluex/liquidbounce/file/config/sections/ModulesSection;", "Lnet/ccbluex/liquidbounce/file/config/ConfigSection;", "()V", "load", "", "json", "Lcom/google/gson/JsonObject;", "save", "CrossSine"})
public final class ModulesSection
extends ConfigSection {
    public ModulesSection() {
        super("modules");
    }

    @Override
    public void load(@NotNull JsonObject json) {
        Object it;
        Intrinsics.checkNotNullParameter(json, "json");
        Iterable $this$forEach$iv = CrossSine.INSTANCE.getModuleManager().getModules();
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            it = (Module)element$iv;
            boolean bl = false;
            if (!((Module)it).getLoadConfig()) continue;
            ModuleInfo moduleInfo = ((Module)it).getModuleInfo();
            ((Module)it).setState(moduleInfo.defaultOn());
            ((Module)it).setKeyBind(moduleInfo.keyBind());
            ((Module)it).setArray(moduleInfo.array());
            ((Module)it).setAutoDisable(moduleInfo.autoDisable());
            Iterable $this$forEach$iv2 = ((Module)it).getValues();
            boolean $i$f$forEach2 = false;
            for (Object element$iv2 : $this$forEach$iv2) {
                Value value = (Value)element$iv2;
                boolean bl2 = false;
                value.setDefault();
            }
        }
        for (Map.Entry entrySet : json.entrySet()) {
            Module module;
            if (CrossSine.INSTANCE.getModuleManager().getModule((String)entrySet.getKey()) == null) continue;
            JsonObject data = ((JsonElement)entrySet.getValue()).getAsJsonObject();
            if (data.has("state")) {
                module.setState(data.get("state").getAsBoolean());
            }
            if (data.has("keybind")) {
                module.setKeyBind(data.get("keybind").getAsInt());
            }
            if (data.has("array")) {
                module.setArray(data.get("array").getAsBoolean());
            }
            if (data.has("trigger")) {
                it = data.get("trigger").getAsString();
                Intrinsics.checkNotNullExpressionValue(it, "data.get(\"trigger\").asString");
                module.setTriggerType(EnumTriggerType.valueOf((String)it));
            }
            if (data.has("autodisable")) {
                it = data.get("autodisable").getAsString();
                Intrinsics.checkNotNullExpressionValue(it, "data.get(\"autodisable\").asString");
                module.setAutoDisable(EnumAutoDisableType.valueOf((String)it));
            }
            JsonObject values2 = data.getAsJsonObject("values");
            Iterable $this$forEach$iv3 = module.getValues();
            boolean $i$f$forEach3 = false;
            for (Object element$iv : $this$forEach$iv3) {
                Value it2 = (Value)element$iv;
                boolean bl = false;
                if (!values2.has(it2.getName())) continue;
                JsonElement jsonElement = values2.get(it2.getName());
                Intrinsics.checkNotNullExpressionValue(jsonElement, "values.get(it.name)");
                it2.fromJson(jsonElement);
            }
        }
    }

    @Override
    @NotNull
    public JsonObject save() {
        JsonObject json = new JsonObject();
        Iterable $this$forEach$iv = CrossSine.INSTANCE.getModuleManager().getModules();
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Module it = (Module)element$iv;
            boolean bl = false;
            JsonObject moduleJson = new JsonObject();
            if (it.getCanEnable() || it.getTriggerType() != EnumTriggerType.PRESS) {
                moduleJson.addProperty("state", Boolean.valueOf(it.getState()));
            }
            moduleJson.addProperty("keybind", (Number)it.getKeyBind());
            if (it.getCanEnable()) {
                moduleJson.addProperty("array", Boolean.valueOf(it.getArray()));
            }
            if (it.getCanEnable()) {
                moduleJson.addProperty("autodisable", it.getAutoDisable().toString());
            }
            moduleJson.addProperty("trigger", it.getTriggerType().toString());
            JsonObject valuesJson = new JsonObject();
            Iterable $this$forEach$iv2 = it.getValues();
            boolean $i$f$forEach2 = false;
            for (Object element$iv2 : $this$forEach$iv2) {
                Value value = (Value)element$iv2;
                boolean bl2 = false;
                valuesJson.add(value.getName(), value.toJson());
            }
            moduleJson.add("values", (JsonElement)valuesJson);
            json.add(it.getName(), (JsonElement)moduleJson);
        }
        return json;
    }
}

