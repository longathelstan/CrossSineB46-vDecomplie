/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.script.remapper;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\n\u001a\u00020\u000bJ\u0016\u0010\f\u001a\u00020\u000b2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00050\u000eH\u0002J\u001a\u0010\u000f\u001a\u00020\u00052\n\u0010\u0010\u001a\u0006\u0012\u0002\b\u00030\u00112\u0006\u0010\u0012\u001a\u00020\u0005J\"\u0010\u0013\u001a\u00020\u00052\n\u0010\u0010\u001a\u0006\u0012\u0002\b\u00030\u00112\u0006\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\u0005RR\u0010\u0003\u001aF\u0012\u0004\u0012\u00020\u0005\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u00040\u0004j*\u0012\u0004\u0012\u00020\u0005\u0012 \u0012\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005`\u0006`\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000RR\u0010\u0007\u001aF\u0012\u0004\u0012\u00020\u0005\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u00040\u0004j*\u0012\u0004\u0012\u00020\u0005\u0012 \u0012\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0005`\u0006`\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2={"Lnet/ccbluex/liquidbounce/script/remapper/Remapper;", "", "()V", "fields", "Ljava/util/HashMap;", "", "Lkotlin/collections/HashMap;", "methods", "srgLoaded", "", "loadSrg", "", "parseSrg", "srgData", "", "remapField", "clazz", "Ljava/lang/Class;", "name", "remapMethod", "desc", "CrossSine"})
public final class Remapper {
    @NotNull
    public static final Remapper INSTANCE = new Remapper();
    private static boolean srgLoaded;
    @NotNull
    private static final HashMap<String, HashMap<String, String>> fields;
    @NotNull
    private static final HashMap<String, HashMap<String, String>> methods;

    private Remapper() {
    }

    public final void loadSrg() {
        if (srgLoaded) {
            return;
        }
        ClientUtils.INSTANCE.logInfo("[Remapper] Loading srg...");
        List list = IOUtils.readLines((InputStream)Remapper.class.getClassLoader().getResourceAsStream("assets/minecraft/crosssine/scriptapi/mcp-stable_22.srg"));
        Intrinsics.checkNotNullExpressionValue(list, "readLines(Remapper::clas\u2026tapi/mcp-stable_22.srg\"))");
        this.parseSrg(list);
        srgLoaded = true;
        ClientUtils.INSTANCE.logInfo("[Remapper] Loaded srg.");
    }

    private final void parseSrg(List<String> srgData) {
        Iterable $this$forEach$iv = srgData;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            String fieldSrg;
            String string;
            HashMap<String, String> hashMap;
            String name;
            String it = (String)element$iv;
            boolean bl = false;
            String[] stringArray = new String[]{" "};
            List args2 = StringsKt.split$default((CharSequence)it, stringArray, false, 0, 6, null);
            if (StringsKt.startsWith$default(it, "FD:", false, 2, null)) {
                name = (String)args2.get(1);
                String srg = (String)args2.get(2);
                String string2 = name;
                int n = 0;
                int n2 = StringsKt.lastIndexOf$default((CharSequence)name, '/', 0, false, 6, null);
                String string3 = string2.substring(n, n2);
                Intrinsics.checkNotNullExpressionValue(string3, "this as java.lang.String\u2026ing(startIndex, endIndex)");
                String className = StringsKt.replace$default(string3, '/', '.', false, 4, null);
                String string4 = name;
                n2 = StringsKt.lastIndexOf$default((CharSequence)name, '/', 0, false, 6, null) + 1;
                string3 = string4.substring(n2);
                Intrinsics.checkNotNullExpressionValue(string3, "this as java.lang.String).substring(startIndex)");
                String fieldName = string3;
                hashMap = srg;
                int n3 = StringsKt.lastIndexOf$default((CharSequence)srg, '/', 0, false, 6, null) + 1;
                string = ((String)((Object)hashMap)).substring(n3);
                Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).substring(startIndex)");
                fieldSrg = string;
                if (!((Map)fields).containsKey(className)) {
                    hashMap = fields;
                    HashMap hashMap2 = new HashMap();
                    hashMap.put(className, (String)((Object)hashMap2));
                }
                HashMap<String, String> hashMap3 = fields.get(className);
                Intrinsics.checkNotNull(hashMap3);
                hashMap = hashMap3;
                Intrinsics.checkNotNullExpressionValue(hashMap, "fields[className]!!");
                ((Map)hashMap).put(fieldSrg, fieldName);
                continue;
            }
            if (!StringsKt.startsWith$default(it, "MD:", false, 2, null)) continue;
            name = (String)args2.get(1);
            String desc = (String)args2.get(2);
            String srg = (String)args2.get(3);
            fieldSrg = name;
            int n = 0;
            int n4 = StringsKt.lastIndexOf$default((CharSequence)name, '/', 0, false, 6, null);
            string = fieldSrg.substring(n, n4);
            Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String\u2026ing(startIndex, endIndex)");
            String className = StringsKt.replace$default(string, '/', '.', false, 4, null);
            hashMap = name;
            n4 = StringsKt.lastIndexOf$default((CharSequence)name, '/', 0, false, 6, null) + 1;
            string = ((String)((Object)hashMap)).substring(n4);
            Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).substring(startIndex)");
            String methodName = string;
            Map<String, String> map = srg;
            int n5 = StringsKt.lastIndexOf$default((CharSequence)srg, '/', 0, false, 6, null) + 1;
            String string5 = ((String)((Object)map)).substring(n5);
            Intrinsics.checkNotNullExpressionValue(string5, "this as java.lang.String).substring(startIndex)");
            String methodSrg = string5;
            if (!((Map)methods).containsKey(className)) {
                map = methods;
                HashMap hashMap4 = new HashMap();
                map.put(className, (String)((Object)hashMap4));
            }
            HashMap<String, String> hashMap5 = methods.get(className);
            Intrinsics.checkNotNull(hashMap5);
            map = hashMap5;
            Intrinsics.checkNotNullExpressionValue(map, "methods[className]!!");
            map = map;
            string = Intrinsics.stringPlus(methodSrg, desc);
            map.put(string, methodName);
        }
    }

    @NotNull
    public final String remapField(@NotNull Class<?> clazz, @NotNull String name) {
        String string;
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        Intrinsics.checkNotNullParameter(name, "name");
        HashMap<String, String> hashMap = fields.get(clazz.getName());
        if (hashMap == null) {
            string = name;
        } else {
            string = hashMap.getOrDefault(name, name);
            if (string == null) {
                string = name;
            }
        }
        return string;
    }

    @NotNull
    public final String remapMethod(@NotNull Class<?> clazz, @NotNull String name, @NotNull String desc) {
        String string;
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(desc, "desc");
        HashMap<String, String> hashMap = methods.get(clazz.getName());
        if (hashMap == null) {
            string = name;
        } else {
            string = hashMap.getOrDefault(Intrinsics.stringPlus(name, desc), name);
            if (string == null) {
                string = name;
            }
        }
        return string;
    }

    static {
        fields = new HashMap();
        methods = new HashMap();
    }
}

