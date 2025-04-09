/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.file;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.features.macro.Macro;
import net.ccbluex.liquidbounce.features.module.EnumAutoDisableType;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.file.FileConfig;
import net.ccbluex.liquidbounce.file.configs.AccountsConfig;
import net.ccbluex.liquidbounce.file.configs.ClientHUDConfig;
import net.ccbluex.liquidbounce.file.configs.FriendsConfig;
import net.ccbluex.liquidbounce.file.configs.ScriptConfig;
import net.ccbluex.liquidbounce.file.configs.SpecialConfig;
import net.ccbluex.liquidbounce.file.configs.ThemeConfig;
import net.ccbluex.liquidbounce.file.configs.XRayConfig;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\u0018\u0000 :2\u00020\u0001:\u0001:B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010+\u001a\u00020,2\u0006\u0010-\u001a\u00020.J\u001f\u0010/\u001a\u00020,2\u0012\u00100\u001a\n\u0012\u0006\b\u0001\u0012\u00020.01\"\u00020.\u00a2\u0006\u0002\u00102J\u0006\u00103\u001a\u000204J\u0006\u00105\u001a\u00020,J\u000e\u00106\u001a\u00020,2\u0006\u0010-\u001a\u00020.J\u0018\u00106\u001a\u00020,2\u0006\u0010-\u001a\u00020.2\u0006\u00107\u001a\u000204H\u0002J\u001f\u00108\u001a\u00020,2\u0012\u00100\u001a\n\u0012\u0006\b\u0001\u0012\u00020.01\"\u00020.\u00a2\u0006\u0002\u00102J\u0006\u00109\u001a\u00020,R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u000f\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000eR\u0011\u0010\u0011\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000eR\u001a\u0010\u0013\u001a\u00020\u0014X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0019\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u000eR\u0011\u0010\u001b\u001a\u00020\u001c\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\u001f\u001a\u00020 \u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\"R\u0011\u0010#\u001a\u00020$\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010&R\u0011\u0010'\u001a\u00020(\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010*\u00a8\u0006;"}, d2={"Lnet/ccbluex/liquidbounce/file/FileManager;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "accountsConfig", "Lnet/ccbluex/liquidbounce/file/configs/AccountsConfig;", "getAccountsConfig", "()Lnet/ccbluex/liquidbounce/file/configs/AccountsConfig;", "clienthudConfig", "Lnet/ccbluex/liquidbounce/file/configs/ClientHUDConfig;", "getClienthudConfig", "()Lnet/ccbluex/liquidbounce/file/configs/ClientHUDConfig;", "configsDir", "Ljava/io/File;", "getConfigsDir", "()Ljava/io/File;", "dir", "getDir", "fontsDir", "getFontsDir", "friendsConfig", "Lnet/ccbluex/liquidbounce/file/configs/FriendsConfig;", "getFriendsConfig", "()Lnet/ccbluex/liquidbounce/file/configs/FriendsConfig;", "setFriendsConfig", "(Lnet/ccbluex/liquidbounce/file/configs/FriendsConfig;)V", "legacySettingsDir", "getLegacySettingsDir", "specialConfig", "Lnet/ccbluex/liquidbounce/file/configs/SpecialConfig;", "getSpecialConfig", "()Lnet/ccbluex/liquidbounce/file/configs/SpecialConfig;", "subscriptsConfig", "Lnet/ccbluex/liquidbounce/file/configs/ScriptConfig;", "getSubscriptsConfig", "()Lnet/ccbluex/liquidbounce/file/configs/ScriptConfig;", "themeConfig", "Lnet/ccbluex/liquidbounce/file/configs/ThemeConfig;", "getThemeConfig", "()Lnet/ccbluex/liquidbounce/file/configs/ThemeConfig;", "xrayConfig", "Lnet/ccbluex/liquidbounce/file/configs/XRayConfig;", "getXrayConfig", "()Lnet/ccbluex/liquidbounce/file/configs/XRayConfig;", "loadConfig", "", "config", "Lnet/ccbluex/liquidbounce/file/FileConfig;", "loadConfigs", "configs", "", "([Lnet/ccbluex/liquidbounce/file/FileConfig;)V", "loadLegacy", "", "saveAllConfigs", "saveConfig", "ignoreStarting", "saveConfigs", "setupFolder", "Companion", "CrossSine"})
public final class FileManager
extends MinecraftInstance {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final File dir;
    @NotNull
    private final File fontsDir;
    @NotNull
    private final File configsDir;
    @NotNull
    private final File legacySettingsDir;
    @NotNull
    private final AccountsConfig accountsConfig;
    @NotNull
    private FriendsConfig friendsConfig;
    @NotNull
    private final XRayConfig xrayConfig;
    @NotNull
    private final ScriptConfig subscriptsConfig;
    @NotNull
    private final SpecialConfig specialConfig;
    @NotNull
    private final ThemeConfig themeConfig;
    @NotNull
    private final ClientHUDConfig clienthudConfig;
    private static final Gson PRETTY_GSON = new GsonBuilder().setPrettyPrinting().create();

    public FileManager() {
        this.dir = new File(MinecraftInstance.mc.field_71412_D, "CrossSine");
        this.fontsDir = new File(this.dir, "fonts");
        this.configsDir = new File(this.dir, "configs");
        this.legacySettingsDir = new File(this.dir, "legacy-settings.json");
        this.accountsConfig = new AccountsConfig(new File(this.dir, "accounts.json"));
        this.friendsConfig = new FriendsConfig(new File(this.dir, "friends.json"));
        this.xrayConfig = new XRayConfig(new File(this.dir, "xray-blocks.json"));
        this.subscriptsConfig = new ScriptConfig(new File(this.dir, "subscripts.json"));
        this.specialConfig = new SpecialConfig(new File(this.dir, "special.json"));
        this.themeConfig = new ThemeConfig(new File(this.dir, "themeColor.json"));
        this.clienthudConfig = new ClientHUDConfig(new File(this.dir, "clienthud.json"));
        this.setupFolder();
    }

    @NotNull
    public final File getDir() {
        return this.dir;
    }

    @NotNull
    public final File getFontsDir() {
        return this.fontsDir;
    }

    @NotNull
    public final File getConfigsDir() {
        return this.configsDir;
    }

    @NotNull
    public final File getLegacySettingsDir() {
        return this.legacySettingsDir;
    }

    @NotNull
    public final AccountsConfig getAccountsConfig() {
        return this.accountsConfig;
    }

    @NotNull
    public final FriendsConfig getFriendsConfig() {
        return this.friendsConfig;
    }

    public final void setFriendsConfig(@NotNull FriendsConfig friendsConfig) {
        Intrinsics.checkNotNullParameter(friendsConfig, "<set-?>");
        this.friendsConfig = friendsConfig;
    }

    @NotNull
    public final XRayConfig getXrayConfig() {
        return this.xrayConfig;
    }

    @NotNull
    public final ScriptConfig getSubscriptsConfig() {
        return this.subscriptsConfig;
    }

    @NotNull
    public final SpecialConfig getSpecialConfig() {
        return this.specialConfig;
    }

    @NotNull
    public final ThemeConfig getThemeConfig() {
        return this.themeConfig;
    }

    @NotNull
    public final ClientHUDConfig getClienthudConfig() {
        return this.clienthudConfig;
    }

    public final void setupFolder() {
        if (!this.dir.exists()) {
            this.dir.mkdir();
        }
        if (!this.fontsDir.exists()) {
            this.fontsDir.mkdir();
        }
        if (!this.configsDir.exists()) {
            this.configsDir.mkdir();
        }
    }

    public final void loadConfigs(FileConfig ... configs) {
        Intrinsics.checkNotNullParameter(configs, "configs");
        if (CrossSine.INSTANCE.getDestruced()) {
            return;
        }
        for (FileConfig fileConfig : configs) {
            this.loadConfig(fileConfig);
        }
    }

    public final void loadConfig(@NotNull FileConfig config) {
        Intrinsics.checkNotNullParameter(config, "config");
        if (CrossSine.INSTANCE.getDestruced()) {
            return;
        }
        if (!config.hasConfig()) {
            ClientUtils.INSTANCE.logInfo("[FileManager] Skipped loading config: " + config.getFile().getName() + '.');
            this.saveConfig(config, true);
            return;
        }
        try {
            config.loadConfig(config.loadConfigFile());
            ClientUtils.INSTANCE.logInfo("[FileManager] Loaded config: " + config.getFile().getName() + '.');
        }
        catch (Throwable t) {
            ClientUtils.INSTANCE.logError("[FileManager] Failed to load config file: " + config.getFile().getName() + '.', t);
        }
    }

    public final void saveAllConfigs() {
        if (CrossSine.INSTANCE.getDestruced()) {
            return;
        }
        Field[] fieldArray = this.getClass().getDeclaredFields();
        Intrinsics.checkNotNullExpressionValue(fieldArray, "javaClass.declaredFields");
        Field[] fieldArray2 = fieldArray;
        int n = 0;
        int n2 = fieldArray2.length;
        while (n < n2) {
            Field field = fieldArray2[n];
            ++n;
            try {
                field.setAccessible(true);
                Object obj = field.get(this);
                if (!(obj instanceof FileConfig)) continue;
                this.saveConfig((FileConfig)obj);
            }
            catch (IllegalAccessException e) {
                ClientUtils.INSTANCE.logError("[FileManager] Failed to save config file of field " + field.getName() + '.', e);
            }
        }
    }

    public final void saveConfigs(FileConfig ... configs) {
        Intrinsics.checkNotNullParameter(configs, "configs");
        if (CrossSine.INSTANCE.getDestruced()) {
            return;
        }
        for (FileConfig fileConfig : configs) {
            this.saveConfig(fileConfig);
        }
    }

    public final void saveConfig(@NotNull FileConfig config) {
        Intrinsics.checkNotNullParameter(config, "config");
        if (CrossSine.INSTANCE.getDestruced()) {
            return;
        }
        this.saveConfig(config, true);
    }

    private final void saveConfig(FileConfig config, boolean ignoreStarting) {
        if (CrossSine.INSTANCE.getDestruced()) {
            return;
        }
        if (!ignoreStarting && CrossSine.INSTANCE.isStarting()) {
            return;
        }
        try {
            if (!config.hasConfig()) {
                config.createConfig();
            }
            config.saveConfigFile(config.saveConfig());
            ClientUtils.INSTANCE.logInfo("[FileManager] Saved config: " + config.getFile().getName() + '.');
        }
        catch (Throwable t) {
            ClientUtils.INSTANCE.logError("[FileManager] Failed to save config file: " + config.getFile().getName() + '.', t);
        }
    }

    public final boolean loadLegacy() throws IOException {
        File shortcutsFile;
        File macrosFile;
        File valuesFile;
        if (CrossSine.INSTANCE.getDestruced()) {
            return false;
        }
        boolean modified = false;
        File modulesFile = new File(this.dir, "modules.json");
        if (modulesFile.exists()) {
            modified = true;
            FileReader fr = new FileReader(modulesFile);
            try {
                JsonElement jsonElement = new JsonParser().parse((Reader)new BufferedReader(fr));
                for (Object object : jsonElement.getAsJsonObject().entrySet()) {
                    Intrinsics.checkNotNullExpressionValue(object, "jsonElement.asJsonObject.entrySet()");
                    String key = (String)object.getKey();
                    JsonElement value = (JsonElement)object.getValue();
                    Module module = CrossSine.INSTANCE.getModuleManager().getModule(key);
                    if (module == null) continue;
                    JsonElement jsonElement2 = value;
                    if (jsonElement2 == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.google.gson.JsonObject");
                    }
                    JsonObject jsonModule = (JsonObject)jsonElement2;
                    module.setState(jsonModule.get("State").getAsBoolean());
                    module.setKeyBind(jsonModule.get("KeyBind").getAsInt());
                    if (jsonModule.has("Array")) {
                        module.setArray(jsonModule.get("Array").getAsBoolean());
                    }
                    if (!jsonModule.has("AutoDisable")) continue;
                    String string = jsonModule.get("AutoDisable").getAsString();
                    Intrinsics.checkNotNullExpressionValue(string, "jsonModule[\"AutoDisable\"].asString");
                    module.setAutoDisable(EnumAutoDisableType.valueOf(string));
                }
            }
            catch (Throwable t) {
                t.printStackTrace();
            }
            try {
                fr.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            ClientUtils.INSTANCE.logInfo("Deleted Legacy config " + modulesFile.getName() + ' ' + modulesFile.delete());
        }
        if ((valuesFile = new File(this.dir, "values.json")).exists()) {
            modified = true;
            FileReader fr = new FileReader(valuesFile);
            try {
                JsonObject jsonObject = new JsonParser().parse((Reader)new BufferedReader(fr)).getAsJsonObject();
                for (Object object : jsonObject.entrySet()) {
                    Intrinsics.checkNotNullExpressionValue(object, "jsonObject.entrySet()");
                    String key = (String)object.getKey();
                    JsonElement value = (JsonElement)object.getValue();
                    Module module = CrossSine.INSTANCE.getModuleManager().getModule(key);
                    if (module == null) continue;
                    JsonElement jsonElement = value;
                    if (jsonElement == null) {
                        throw new NullPointerException("null cannot be cast to non-null type com.google.gson.JsonObject");
                    }
                    JsonObject jsonModule = (JsonObject)jsonElement;
                    for (Value<?> moduleValue : module.getValues()) {
                        JsonElement element = jsonModule.get(moduleValue.getName());
                        if (element == null) continue;
                        moduleValue.fromJson(element);
                    }
                }
            }
            catch (Throwable t) {
                t.printStackTrace();
            }
            try {
                fr.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            ClientUtils.INSTANCE.logInfo("Deleted Legacy config " + valuesFile.getName() + ' ' + valuesFile.delete());
        }
        if ((macrosFile = new File(this.dir, "macros.json")).exists()) {
            modified = true;
            FileReader fr = new FileReader(macrosFile);
            try {
                JsonArray jsonArray = new JsonParser().parse((Reader)new BufferedReader(fr)).getAsJsonArray();
                for (JsonElement jsonElement : jsonArray) {
                    JsonObject macroJson = jsonElement.getAsJsonObject();
                    ArrayList<Macro> arrayList = CrossSine.INSTANCE.getMacroManager().getMacros();
                    int n = macroJson.get("key").getAsInt();
                    String string = macroJson.get("command").getAsString();
                    Intrinsics.checkNotNullExpressionValue(string, "macroJson[\"command\"].asString");
                    arrayList.add(new Macro(n, string));
                }
            }
            catch (Throwable t) {
                t.printStackTrace();
            }
            try {
                fr.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            ClientUtils.INSTANCE.logInfo("Deleted Legacy config " + macrosFile.getName() + ' ' + macrosFile.delete());
        }
        if ((shortcutsFile = new File(this.dir, "shortcuts.json")).exists()) {
            shortcutsFile.delete();
        }
        return modified;
    }

    @Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0019\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\b"}, d2={"Lnet/ccbluex/liquidbounce/file/FileManager$Companion;", "", "()V", "PRETTY_GSON", "Lcom/google/gson/Gson;", "kotlin.jvm.PlatformType", "getPRETTY_GSON", "()Lcom/google/gson/Gson;", "CrossSine"})
    public static final class Companion {
        private Companion() {
        }

        public final Gson getPRETTY_GSON() {
            return PRETTY_GSON;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

