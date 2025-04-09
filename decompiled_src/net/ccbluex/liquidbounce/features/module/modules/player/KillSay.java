/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.EntityKilledEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.file.FileManager;
import net.ccbluex.liquidbounce.utils.FileUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(name="KillSay", category=ModuleCategory.PLAYER)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0015\u001a\u00020\u0007J\u0006\u0010\u0016\u001a\u00020\u0017J\u0010\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u001aH\u0007J\u0018\u0010\u001b\u001a\u00020\u00172\u0006\u0010\u001c\u001a\u00020\u00072\u0006\u0010\u001d\u001a\u00020\u0007H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R \u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u0011\u0010\f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0010\u001a\u00020\u00078VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001e"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/player/KillSay;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "insultFile", "Ljava/io/File;", "insultWords", "", "", "getInsultWords", "()Ljava/util/List;", "setInsultWords", "(Ljava/util/List;)V", "modeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "getModeValue", "()Lnet/ccbluex/liquidbounce/features/value/ListValue;", "tag", "getTag", "()Ljava/lang/String;", "waterMarkValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "getRandomOne", "loadFile", "", "onKilled", "event", "Lnet/ccbluex/liquidbounce/event/EntityKilledEvent;", "sendInsultWords", "msg", "name", "CrossSine"})
public final class KillSay
extends Module {
    @NotNull
    public static final KillSay INSTANCE = new KillSay();
    @NotNull
    private static final ListValue modeValue;
    @NotNull
    private static final BoolValue waterMarkValue;
    @NotNull
    private static final File insultFile;
    @NotNull
    private static List<String> insultWords;

    private KillSay() {
    }

    @NotNull
    public final ListValue getModeValue() {
        return modeValue;
    }

    @NotNull
    public final List<String> getInsultWords() {
        return insultWords;
    }

    public final void setInsultWords(@NotNull List<String> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        insultWords = list;
    }

    public final void loadFile() {
        try {
            JsonElement json;
            if (!insultFile.exists()) {
                FileUtils.INSTANCE.unpackFile(insultFile, "assets/minecraft/crosssine/misc/insult.json");
            }
            if ((json = new JsonParser().parse(FilesKt.readText(insultFile, Charsets.UTF_8))).isJsonArray()) {
                insultWords.clear();
                JsonArray jsonArray = json.getAsJsonArray();
                Intrinsics.checkNotNullExpressionValue(jsonArray, "json.asJsonArray");
                Iterable $this$forEach$iv = (Iterable)jsonArray;
                boolean $i$f$forEach = false;
                for (Object element$iv : $this$forEach$iv) {
                    JsonElement it = (JsonElement)element$iv;
                    boolean bl = false;
                    List<String> list = INSTANCE.getInsultWords();
                    String string = it.getAsString();
                    Intrinsics.checkNotNullExpressionValue(string, "it.asString");
                    list.add(string);
                }
            } else {
                KillSay.loadFile$convertJson();
            }
        }
        catch (Throwable e) {
            e.printStackTrace();
            KillSay.loadFile$convertJson();
        }
    }

    @NotNull
    public final String getRandomOne() {
        return insultWords.get(RandomUtils.nextInt(0, insultWords.size() - 1));
    }

    @EventTarget
    public final void onKilled(@NotNull EntityKilledEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        EntityLivingBase target = event.getTargetEntity();
        if (!(target instanceof EntityPlayer)) {
            return;
        }
        String string = ((String)modeValue.get()).toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        switch (string) {
            case "clear": {
                String string2 = Intrinsics.stringPlus("L ", ((EntityPlayer)target).func_70005_c_());
                String string3 = ((EntityPlayer)target).func_70005_c_();
                Intrinsics.checkNotNullExpressionValue(string3, "target.name");
                this.sendInsultWords(string2, string3);
                break;
            }
            case "withwords": {
                String string4 = "L " + ((EntityPlayer)target).func_70005_c_() + ' ' + this.getRandomOne();
                String string5 = ((EntityPlayer)target).func_70005_c_();
                Intrinsics.checkNotNullExpressionValue(string5, "target.name");
                this.sendInsultWords(string4, string5);
                break;
            }
            case "rawwords": {
                String string6 = this.getRandomOne();
                String string7 = ((EntityPlayer)target).func_70005_c_();
                Intrinsics.checkNotNullExpressionValue(string7, "target.name");
                this.sendInsultWords(string6, string7);
            }
        }
    }

    private final void sendInsultWords(String msg, String name) {
        String message = StringsKt.replace$default(msg, "%name%", name, false, 4, null);
        if (((Boolean)waterMarkValue.get()).booleanValue()) {
            message = Intrinsics.stringPlus("[CrossSine] ", message);
        }
        MinecraftInstance.mc.field_71439_g.func_71165_d(message);
    }

    @Override
    @NotNull
    public String getTag() {
        return (String)modeValue.get();
    }

    /*
     * WARNING - void declaration
     */
    private static final void loadFile$convertJson() {
        void $this$mapTo$iv$iv;
        void $this$filterTo$iv$iv;
        void $this$filter$iv;
        insultWords.clear();
        Iterable iterable = FilesKt.readLines(insultFile, Charsets.UTF_8);
        Collection<String> collection = insultWords;
        boolean $i$f$filter = false;
        void var2_4 = $this$filter$iv;
        Iterable destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            String it = (String)element$iv$iv;
            boolean bl = false;
            boolean bl2 = !StringsKt.isBlank(it);
            if (!bl2) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        collection.addAll((List)destination$iv$iv);
        JsonArray json = new JsonArray();
        Iterable $this$map$iv = insultWords;
        boolean $i$f$map = false;
        destination$iv$iv = $this$map$iv;
        Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            String bl = (String)item$iv$iv;
            collection = destination$iv$iv2;
            boolean bl3 = false;
            collection.add((String)new JsonPrimitive((String)it));
        }
        Iterable $this$forEach$iv = (List)destination$iv$iv2;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            JsonElement p0 = (JsonElement)element$iv;
            boolean bl = false;
            json.add(p0);
        }
        String string = FileManager.Companion.getPRETTY_GSON().toJson((JsonElement)json);
        Intrinsics.checkNotNullExpressionValue(string, "FileManager.PRETTY_GSON.toJson(json)");
        FilesKt.writeText(insultFile, string, Charsets.UTF_8);
    }

    static {
        String[] stringArray = new String[]{"Clear", "WithWords", "RawWords"};
        modeValue = new ListValue("Mode", stringArray, "RawWords");
        waterMarkValue = new BoolValue("WaterMark", true);
        insultFile = new File(CrossSine.INSTANCE.getFileManager().getDir(), "insult.json");
        insultWords = new ArrayList();
        INSTANCE.loadFile();
    }
}

