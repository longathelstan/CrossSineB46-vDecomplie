/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.script;

import com.sun.jna.Native;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import jdk.nashorn.api.scripting.ClassFilter;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.misc.HttpUtils;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0001\u0019B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0011\u001a\u00020\u00122\n\u0010\u0013\u001a\u0006\u0012\u0002\b\u00030\u000eJ\u001a\u0010\u0011\u001a\u00020\u00122\n\u0010\u0013\u001a\u0006\u0012\u0002\b\u00030\u000e2\u0006\u0010\u0014\u001a\u00020\u0005J\u0012\u0010\u0015\u001a\u00020\u00122\n\u0010\u0016\u001a\u0006\u0012\u0002\b\u00030\u000eJ\u001a\u0010\u0015\u001a\u00020\u00122\n\u0010\u0016\u001a\u0006\u0012\u0002\b\u00030\u000e2\u0006\u0010\u0014\u001a\u00020\u0005J\u001a\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0016\u001a\u00020\u00052\b\b\u0002\u0010\u0014\u001a\u00020\u0005H\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R*\u0010\f\u001a\u001e\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000e\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000b0\u000f0\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u0010\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000e\u0012\u0004\u0012\u00020\u000b0\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001a"}, d2={"Lnet/ccbluex/liquidbounce/script/ScriptSafetyManager;", "", "()V", "alerted", "", "", "classFilter", "Ljdk/nashorn/api/scripting/ClassFilter;", "getClassFilter", "()Ljdk/nashorn/api/scripting/ClassFilter;", "level", "", "restrictedChilds", "", "Ljava/lang/Class;", "Lkotlin/Pair;", "restrictedClasses", "isRestricted", "", "classIn", "child", "isRestrictedSimple", "klass", "warnRestricted", "", "ProtectionLevel", "CrossSine"})
public final class ScriptSafetyManager {
    @NotNull
    public static final ScriptSafetyManager INSTANCE;
    private static final int level;
    @NotNull
    private static final Map<Class<?>, Integer> restrictedClasses;
    @NotNull
    private static final Map<Class<?>, Pair<String, Integer>> restrictedChilds;
    @NotNull
    private static final ClassFilter classFilter;
    @NotNull
    private static final List<String> alerted;

    private ScriptSafetyManager() {
    }

    @NotNull
    public final ClassFilter getClassFilter() {
        return classFilter;
    }

    public final boolean isRestricted(@NotNull Class<?> classIn) {
        Intrinsics.checkNotNullParameter(classIn, "classIn");
        Class<?> klass = classIn;
        while (!Intrinsics.areEqual(klass, Object.class)) {
            if (this.isRestrictedSimple(klass)) {
                return true;
            }
            if (klass.getSuperclass() == null) break;
            Class<?> clazz = klass.getSuperclass();
            Intrinsics.checkNotNullExpressionValue(clazz, "klass.superclass");
            klass = clazz;
        }
        return false;
    }

    public final boolean isRestricted(@NotNull Class<?> classIn, @NotNull String child) {
        Intrinsics.checkNotNullParameter(classIn, "classIn");
        Intrinsics.checkNotNullParameter(child, "child");
        Class<?> klass = classIn;
        while (!Intrinsics.areEqual(klass, Object.class)) {
            if (this.isRestrictedSimple(klass, child)) {
                return true;
            }
            if (klass.getSuperclass() == null) break;
            Class<?> clazz = klass.getSuperclass();
            Intrinsics.checkNotNullExpressionValue(clazz, "klass.superclass");
            klass = clazz;
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean isRestrictedSimple(@NotNull Class<?> klass) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        if (!restrictedClasses.containsKey(klass)) return false;
        Integer n = restrictedClasses.get(klass);
        Intrinsics.checkNotNull(n);
        if (((Number)n).intValue() <= level) return false;
        String string = klass.getName();
        Intrinsics.checkNotNullExpressionValue(string, "klass.name");
        this.warnRestricted(string, "");
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean isRestrictedSimple(@NotNull Class<?> klass, @NotNull String child) {
        Intrinsics.checkNotNullParameter(klass, "klass");
        Intrinsics.checkNotNullParameter(child, "child");
        if (this.isRestrictedSimple(klass)) {
            String string = klass.getName();
            Intrinsics.checkNotNullExpressionValue(string, "klass.name");
            this.warnRestricted(string, "");
            return true;
        }
        if (!restrictedChilds.containsKey(klass)) return false;
        Pair<String, Integer> pair = restrictedChilds.get(klass);
        Intrinsics.checkNotNull(pair);
        if (!Intrinsics.areEqual(pair.getFirst(), child)) return false;
        Pair<String, Integer> pair2 = restrictedChilds.get(klass);
        Intrinsics.checkNotNull(pair2);
        if (((Number)pair2.getSecond()).intValue() <= level) return false;
        String string = klass.getName();
        Intrinsics.checkNotNullExpressionValue(string, "klass.name");
        this.warnRestricted(string, child);
        return true;
    }

    private final void warnRestricted(String klass, String child) {
        String message = Intrinsics.stringPlus(klass, ((CharSequence)child).length() > 0 ? Intrinsics.stringPlus(".", child) : "");
        if (!alerted.contains(message)) {
            alerted.add(message);
            ClientUtils.INSTANCE.logWarn("[ScriptAPI] \n========= WARNING =========\nThe script tried to make a restricted call: " + message + ",\nplease add a jvm argument to disable this check: -Dcrosssine.script.safety=HARMFUL\n===========================");
        }
    }

    static /* synthetic */ void warnRestricted$default(ScriptSafetyManager scriptSafetyManager, String string, String string2, int n, Object object) {
        if ((n & 2) != 0) {
            string2 = "";
        }
        scriptSafetyManager.warnRestricted(string, string2);
    }

    private static final boolean classFilter$lambda-0(String name) {
        boolean bl;
        try {
            Class<?> clazz = Class.forName(name);
            Intrinsics.checkNotNullExpressionValue(clazz, "forName(name)");
            bl = !INSTANCE.isRestricted(clazz);
        }
        catch (ClassNotFoundException e) {
            bl = false;
        }
        return bl;
    }

    static {
        Enum enum_;
        Enum enum_2;
        ClientUtils clientUtils;
        StringBuilder stringBuilder;
        block2: {
            INSTANCE = new ScriptSafetyManager();
            String string = System.getProperty("crosssine.script.safety");
            if (string == null) {
                string = "safe";
            }
            ProtectionLevel[] protectionLevelArray = string.toUpperCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(protectionLevelArray, "this as java.lang.String).toUpperCase(Locale.ROOT)");
            level = ProtectionLevel.valueOf((String)protectionLevelArray).getLevel();
            classFilter = ScriptSafetyManager::classFilter$lambda-0;
            protectionLevelArray = ProtectionLevel.values();
            stringBuilder = new StringBuilder().append("[ScriptAPI] Script safety level: ");
            clientUtils = ClientUtils.INSTANCE;
            for (ProtectionLevel protectionLevel : protectionLevelArray) {
                ProtectionLevel it = protectionLevel;
                boolean bl = false;
                if (!(it.getLevel() == level)) continue;
                enum_2 = protectionLevel;
                break block2;
            }
            enum_2 = null;
        }
        Enum enum_3 = enum_ = enum_2;
        clientUtils.logInfo(stringBuilder.append(enum_3 == null ? null : enum_3.name()).append('(').append(level).append(')').toString());
        Map restrictedClasses = new LinkedHashMap();
        Map restrictedChilds = new LinkedHashMap();
        restrictedClasses.put(ScriptSafetyManager.class, ProtectionLevel.HARMFUL.getLevel());
        restrictedClasses.put(ClassLoader.class, ProtectionLevel.HARMFUL.getLevel());
        restrictedClasses.put(Native.class, ProtectionLevel.HARMFUL.getLevel());
        restrictedClasses.put(Runtime.class, ProtectionLevel.HARMFUL.getLevel());
        restrictedChilds.put(System.class, new Pair<String, Integer>("loadLibrary", ProtectionLevel.HARMFUL.getLevel()));
        restrictedChilds.put(System.class, new Pair<String, Integer>("load", ProtectionLevel.HARMFUL.getLevel()));
        restrictedChilds.put(Class.class, new Pair<String, Integer>("forName", ProtectionLevel.HARMFUL.getLevel()));
        restrictedChilds.put(Class.class, new Pair<String, Integer>("getDeclaredField", ProtectionLevel.HARMFUL.getLevel()));
        restrictedChilds.put(Class.class, new Pair<String, Integer>("getDeclaredMethod", ProtectionLevel.HARMFUL.getLevel()));
        restrictedChilds.put(Class.class, new Pair<String, Integer>("getField", ProtectionLevel.HARMFUL.getLevel()));
        restrictedChilds.put(Class.class, new Pair<String, Integer>("getMethod", ProtectionLevel.HARMFUL.getLevel()));
        restrictedChilds.put(Class.class, new Pair<String, Integer>("getDeclaredFields", ProtectionLevel.HARMFUL.getLevel()));
        restrictedChilds.put(Class.class, new Pair<String, Integer>("getDeclaredMethods", ProtectionLevel.HARMFUL.getLevel()));
        restrictedChilds.put(Class.class, new Pair<String, Integer>("getFields", ProtectionLevel.HARMFUL.getLevel()));
        restrictedChilds.put(Class.class, new Pair<String, Integer>("getMethods", ProtectionLevel.HARMFUL.getLevel()));
        restrictedChilds.put(Class.class, new Pair<String, Integer>("getConstructor", ProtectionLevel.HARMFUL.getLevel()));
        restrictedChilds.put(Class.class, new Pair<String, Integer>("getDeclaredConstructor", ProtectionLevel.HARMFUL.getLevel()));
        restrictedChilds.put(Class.class, new Pair<String, Integer>("getConstructors", ProtectionLevel.HARMFUL.getLevel()));
        restrictedChilds.put(Class.class, new Pair<String, Integer>("getDeclaredConstructors", ProtectionLevel.HARMFUL.getLevel()));
        restrictedChilds.put(Class.class, new Pair<String, Integer>("newInstance", ProtectionLevel.HARMFUL.getLevel()));
        restrictedClasses.put(URL.class, ProtectionLevel.DANGER.getLevel());
        restrictedClasses.put(Socket.class, ProtectionLevel.DANGER.getLevel());
        restrictedClasses.put(URLConnection.class, ProtectionLevel.DANGER.getLevel());
        restrictedClasses.put(HttpUtils.class, ProtectionLevel.DANGER.getLevel());
        restrictedClasses.put(File.class, ProtectionLevel.DANGER.getLevel());
        restrictedClasses.put(Path.class, ProtectionLevel.DANGER.getLevel());
        restrictedClasses.put(FileUtils.class, ProtectionLevel.DANGER.getLevel());
        restrictedClasses.put(Files.class, ProtectionLevel.DANGER.getLevel());
        restrictedClasses.put(InputStream.class, ProtectionLevel.DANGER.getLevel());
        restrictedClasses.put(OutputStream.class, ProtectionLevel.DANGER.getLevel());
        restrictedClasses.put(com.google.common.io.Files.class, ProtectionLevel.DANGER.getLevel());
        restrictedClasses.put(net.ccbluex.liquidbounce.utils.FileUtils.class, ProtectionLevel.DANGER.getLevel());
        Map map = Collections.unmodifiableMap(restrictedClasses);
        Intrinsics.checkNotNullExpressionValue(map, "unmodifiableMap(restrictedClasses)");
        ScriptSafetyManager.restrictedClasses = map;
        map = Collections.unmodifiableMap(restrictedChilds);
        Intrinsics.checkNotNullExpressionValue(map, "unmodifiableMap(restrictedChilds)");
        ScriptSafetyManager.restrictedChilds = map;
        alerted = new ArrayList();
    }

    @Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\t\u00a8\u0006\n"}, d2={"Lnet/ccbluex/liquidbounce/script/ScriptSafetyManager$ProtectionLevel;", "", "level", "", "(Ljava/lang/String;II)V", "getLevel", "()I", "SAFE", "DANGER", "HARMFUL", "CrossSine"})
    public static final class ProtectionLevel
    extends Enum<ProtectionLevel> {
        private final int level;
        public static final /* enum */ ProtectionLevel SAFE = new ProtectionLevel(0);
        public static final /* enum */ ProtectionLevel DANGER = new ProtectionLevel(1);
        public static final /* enum */ ProtectionLevel HARMFUL = new ProtectionLevel(2);
        private static final /* synthetic */ ProtectionLevel[] $VALUES;

        private ProtectionLevel(int level) {
            this.level = level;
        }

        public final int getLevel() {
            return this.level;
        }

        public static ProtectionLevel[] values() {
            return (ProtectionLevel[])$VALUES.clone();
        }

        public static ProtectionLevel valueOf(String value) {
            return Enum.valueOf(ProtectionLevel.class, value);
        }

        static {
            $VALUES = protectionLevelArray = new ProtectionLevel[]{ProtectionLevel.SAFE, ProtectionLevel.DANGER, ProtectionLevel.HARMFUL};
        }
    }
}

