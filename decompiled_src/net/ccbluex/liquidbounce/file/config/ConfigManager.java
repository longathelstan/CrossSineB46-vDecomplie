/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.file.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.modules.world.Target;
import net.ccbluex.liquidbounce.features.special.NotificationUtil;
import net.ccbluex.liquidbounce.features.special.TYPE;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.TextValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.file.FileManager;
import net.ccbluex.liquidbounce.file.config.ConfigSection;
import net.ccbluex.liquidbounce.utils.ClassUtils;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.misc.HttpUtils;
import net.ccbluex.liquidbounce.utils.misc.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.input.Keyboard;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001:\u0001&B\u0005\u00a2\u0006\u0002\u0010\u0002J\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013J\u0018\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\t2\b\b\u0002\u0010\u0018\u001a\u00020\u0007J\u0006\u0010\u0019\u001a\u00020\u0016J\u0006\u0010\u001a\u001a\u00020\u0016J\u000e\u0010\u001b\u001a\u00020\u00072\u0006\u0010\u001c\u001a\u00020\u0014J\u0018\u0010\u001d\u001a\u00020\u00162\u000e\u0010\u001e\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00110\u001fH\u0002J\u0010\u0010\u001d\u001a\u00020\u00072\u0006\u0010 \u001a\u00020\u0011H\u0002J$\u0010\u0018\u001a\u00020\u00162\b\b\u0002\u0010!\u001a\u00020\u00072\b\b\u0002\u0010\"\u001a\u00020\u00072\b\b\u0002\u0010#\u001a\u00020\u0007J\u0006\u0010\"\u001a\u00020\u0016J\b\u0010$\u001a\u00020\u0016H\u0002J\u0006\u0010%\u001a\u00020\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u00020\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u000e\u0010\u000e\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00110\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006'"}, d2={"Lnet/ccbluex/liquidbounce/file/config/ConfigManager;", "", "()V", "configFile", "Ljava/io/File;", "configSetFile", "needSave", "", "nowConfig", "", "getNowConfig", "()Ljava/lang/String;", "setNowConfig", "(Ljava/lang/String;)V", "nowConfigInFile", "sections", "", "Lnet/ccbluex/liquidbounce/file/config/ConfigSection;", "fetchOnlineConfigs", "", "Lnet/ccbluex/liquidbounce/file/config/ConfigManager$OnlineConfig;", "load", "", "name", "save", "loadConfigSet", "loadLegacySupport", "loadOnlineConfig", "config", "registerSection", "sectionClass", "Ljava/lang/Class;", "section", "autoSave", "saveConfigSet", "forceSave", "saveTicker", "smartSave", "OnlineConfig", "CrossSine"})
public final class ConfigManager {
    @NotNull
    private final File configSetFile = new File(CrossSine.INSTANCE.getFileManager().getDir(), "config-settings.json");
    @NotNull
    private final List<ConfigSection> sections = new ArrayList();
    @NotNull
    private String nowConfig = "default";
    @NotNull
    private String nowConfigInFile = "default";
    @NotNull
    private File configFile = new File(CrossSine.INSTANCE.getFileManager().getConfigsDir(), Intrinsics.stringPlus(this.nowConfig, ".json"));
    private boolean needSave;

    public ConfigManager() {
        Iterable $this$forEach$iv = ClassUtils.INSTANCE.resolvePackage(Intrinsics.stringPlus(this.getClass().getPackage().getName(), ".sections"), ConfigSection.class);
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Class p0 = (Class)element$iv;
            boolean bl = false;
            this.registerSection(p0);
        }
        Timer timer = new Timer();
        long l = 30000L;
        long l2 = 30000L;
        TimerTask timerTask2 = new TimerTask(this){
            final /* synthetic */ ConfigManager this$0;
            {
                this.this$0 = configManager;
            }

            public void run() {
                TimerTask $this$_init__u24lambda_u2d0 = this;
                boolean bl = false;
                ConfigManager.access$saveTicker(this.this$0);
            }
        };
        timer.schedule(timerTask2, l, l2);
    }

    @NotNull
    public final String getNowConfig() {
        return this.nowConfig;
    }

    public final void setNowConfig(@NotNull String string) {
        Intrinsics.checkNotNullParameter(string, "<set-?>");
        this.nowConfig = string;
    }

    public final void load(@NotNull String name, boolean save) {
        Object object;
        Object object2;
        Intrinsics.checkNotNullParameter(name, "name");
        CrossSine.INSTANCE.setLoadingConfig(true);
        if (save && !Intrinsics.areEqual(this.nowConfig, name)) {
            ConfigManager.save$default(this, false, true, true, 1, null);
        }
        this.nowConfig = name;
        this.configFile = new File(CrossSine.INSTANCE.getFileManager().getConfigsDir(), Intrinsics.stringPlus(this.nowConfig, ".json"));
        try {
            JsonObject jsonObject;
            if (this.configFile.exists()) {
                object2 = this.configFile;
                Charset charset = Charsets.UTF_8;
                object = object2;
                object = new FileInputStream((File)object);
                jsonObject = new JsonParser().parse((Reader)new InputStreamReader((InputStream)object, charset)).getAsJsonObject();
            } else {
                jsonObject = new JsonObject();
            }
            object2 = jsonObject;
        }
        catch (Exception e) {
            ClientUtils.INSTANCE.logError("Error parsing config file '" + name + "': " + e.getMessage());
            object2 = new JsonObject();
        }
        Object json = object2;
        try {
            for (ConfigSection section : this.sections) {
                object = json.has(section.getSectionName()) ? json.getAsJsonObject(section.getSectionName()) : new JsonObject();
                Intrinsics.checkNotNullExpressionValue(object, "if (json.has(section.sec\u2026) } else { JsonObject() }");
                section.load((JsonObject)object);
            }
        }
        catch (Exception e) {
            ClientUtils.INSTANCE.logError("Error loading sections for config '" + name + "': " + e.getMessage());
        }
        if (!this.configFile.exists()) {
            ConfigManager.save$default(this, false, false, true, 3, null);
        }
        if (save) {
            this.saveConfigSet();
        }
        ClientUtils.INSTANCE.logInfo("Config " + this.nowConfig + ".json loaded.");
        CrossSine.INSTANCE.getNotification().getList().add(new NotificationUtil("Config", "Config " + this.nowConfig + " loaded.", TYPE.SUCCESS, System.currentTimeMillis(), 1000));
        CrossSine.INSTANCE.setLoadingConfig(false);
    }

    public static /* synthetic */ void load$default(ConfigManager configManager, String string, boolean bl, int n, Object object) {
        if ((n & 2) != 0) {
            bl = true;
        }
        configManager.load(string, bl);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NotNull
    public final List<OnlineConfig> fetchOnlineConfigs() {
        List configs;
        block11: {
            String apiUrl = "https://api.github.com/repos/crosssine/crosssine.github.io/contents/file/config";
            configs = new ArrayList();
            try {
                URL url = new URL(apiUrl);
                URLConnection uRLConnection = url.openConnection();
                if (uRLConnection == null) {
                    throw new NullPointerException("null cannot be cast to non-null type java.net.HttpURLConnection");
                }
                HttpURLConnection connection = (HttpURLConnection)uRLConnection;
                connection.setRequestProperty("User-Agent", "CrossSineClient");
                if (connection.getResponseCode() == 200) {
                    Closeable closeable = connection.getInputStream();
                    Intrinsics.checkNotNullExpressionValue(closeable, "connection.inputStream");
                    Object object = Charsets.UTF_8;
                    Closeable closeable2 = closeable;
                    closeable2 = new InputStreamReader((InputStream)closeable2, (Charset)object);
                    int n = 8192;
                    closeable = closeable2 instanceof BufferedReader ? (BufferedReader)closeable2 : new BufferedReader((Reader)closeable2, n);
                    object = null;
                    try {
                        Object it = (BufferedReader)closeable;
                        boolean bl = false;
                        it = TextStreamsKt.readText((Reader)it);
                    }
                    catch (Throwable it) {
                        object = it;
                        throw it;
                    }
                    finally {
                        CloseableKt.closeFinally(closeable, (Throwable)object);
                    }
                    Object response = it;
                    JsonArray jsonArray = new JsonParser().parse((String)response).getAsJsonArray();
                    for (JsonElement jsonElement : jsonArray) {
                        JsonObject jsonObject = jsonElement.getAsJsonObject();
                        String name = jsonObject.get("name").getAsString();
                        String downloadUrl = jsonObject.get("download_url").getAsString();
                        Intrinsics.checkNotNullExpressionValue(name, "name");
                        if (!StringsKt.endsWith$default(name, ".json", false, 2, null)) continue;
                        String string = StringsKt.removeSuffix(name, (CharSequence)".json");
                        Intrinsics.checkNotNullExpressionValue(downloadUrl, "downloadUrl");
                        configs.add(new OnlineConfig(string, downloadUrl));
                    }
                    break block11;
                }
                CrossSine.INSTANCE.getNotification().getList().add(new NotificationUtil("Config", Intrinsics.stringPlus("Failed to fetch configs: ", connection.getResponseCode()), TYPE.ERROR, System.currentTimeMillis(), 5000));
                System.out.println((Object)Intrinsics.stringPlus("Failed to fetch configs: ", connection.getResponseCode()));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return configs;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final boolean loadOnlineConfig(@NotNull OnlineConfig config) {
        boolean bl;
        Intrinsics.checkNotNullParameter(config, "config");
        try {
            URL url = new URL(config.getDownloadUrl());
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("User-Agent", "CrossSineClient");
            InputStream input = connection.getInputStream();
            File outputFile = new File(CrossSine.INSTANCE.getFileManager().getConfigsDir(), Intrinsics.stringPlus(config.getName(), ".json"));
            CrossSine.INSTANCE.getNotification().getList().add(new NotificationUtil("Config", Intrinsics.stringPlus("Downloading ", config.getName()), TYPE.INFO, System.currentTimeMillis(), 2000));
            Closeable closeable = input;
            Throwable throwable = null;
            try {
                long l;
                InputStream in = (InputStream)closeable;
                boolean bl2 = false;
                Closeable closeable2 = new FileOutputStream(outputFile);
                Throwable throwable2 = null;
                try {
                    FileOutputStream out = (FileOutputStream)closeable2;
                    boolean bl3 = false;
                    Intrinsics.checkNotNullExpressionValue(in, "`in`");
                    l = ByteStreamsKt.copyTo$default(in, out, 0, 2, null);
                }
                catch (Throwable throwable3) {
                    throwable2 = throwable3;
                    throw throwable3;
                }
                finally {
                    CloseableKt.closeFinally(closeable2, throwable2);
                }
                long l2 = l;
            }
            catch (Throwable throwable4) {
                throwable = throwable4;
                throw throwable4;
            }
            finally {
                CloseableKt.closeFinally(closeable, throwable);
            }
            CrossSine.INSTANCE.getConfigManager().nowConfig = config.getName();
            ConfigManager.load$default(CrossSine.INSTANCE.getConfigManager(), config.getName(), false, 2, null);
            bl = true;
        }
        catch (Exception e) {
            e.printStackTrace();
            bl = false;
        }
        return bl;
    }

    public final void save(boolean autoSave, boolean saveConfigSet, boolean forceSave) {
        if (CrossSine.INSTANCE.isLoadingConfig() && !forceSave) {
            return;
        }
        JsonObject config = new JsonObject();
        for (ConfigSection section : this.sections) {
            config.add(section.getSectionName(), (JsonElement)section.save());
        }
        String string = FileManager.Companion.getPRETTY_GSON().toJson((JsonElement)config);
        Intrinsics.checkNotNullExpressionValue(string, "FileManager.PRETTY_GSON.toJson(config)");
        FilesKt.writeText(this.configFile, string, Charsets.UTF_8);
        if (saveConfigSet || forceSave) {
            this.saveConfigSet();
        }
        this.needSave = false;
        ClientUtils.INSTANCE.logInfo("Config " + this.nowConfig + ".json saved.");
        if (!autoSave) {
            CrossSine.INSTANCE.getNotification().getList().add(new NotificationUtil("Config", "Config " + this.nowConfig + " saved.", TYPE.SUCCESS, System.currentTimeMillis(), 1000));
        }
    }

    public static /* synthetic */ void save$default(ConfigManager configManager, boolean bl, boolean bl2, boolean bl3, int n, Object object) {
        if ((n & 1) != 0) {
            bl = false;
        }
        if ((n & 2) != 0) {
            boolean bl4 = bl2 = !Intrinsics.areEqual(configManager.nowConfigInFile, configManager.nowConfig);
        }
        if ((n & 4) != 0) {
            bl3 = false;
        }
        configManager.save(bl, bl2, bl3);
    }

    private final void saveTicker() {
        if (!this.needSave) {
            return;
        }
        ConfigManager.save$default(this, true, false, false, 6, null);
    }

    public final void smartSave() {
        this.needSave = true;
    }

    public final void loadConfigSet() {
        String string;
        JsonObject configSet;
        JsonObject jsonObject;
        Object object;
        if (this.configSetFile.exists()) {
            try {
                object = this.configSetFile;
                Charset charset = Charsets.UTF_8;
                Object object2 = object;
                object2 = new FileInputStream((File)object2);
                object = new JsonParser().parse((Reader)new InputStreamReader((InputStream)object2, charset)).getAsJsonObject();
            }
            catch (Exception e) {
                object = new JsonObject();
            }
            jsonObject = object;
        } else {
            jsonObject = configSet = new JsonObject();
        }
        if (configSet.has("file")) {
            object = configSet.get("file").getAsString();
            Intrinsics.checkNotNullExpressionValue(object, "{\n            configSet.\u2026file\").asString\n        }");
            string = object;
        } else {
            string = "default";
        }
        this.nowConfigInFile = string;
        try {
            this.load(this.nowConfigInFile, false);
        }
        catch (Exception e) {
            ClientUtils.INSTANCE.logError("Error loading config '" + this.nowConfigInFile + "', falling back to default: " + e.getMessage());
            this.load("default", false);
        }
    }

    public final void saveConfigSet() {
        JsonObject configSet = new JsonObject();
        configSet.addProperty("file", this.nowConfig);
        String string = FileManager.Companion.getPRETTY_GSON().toJson((JsonElement)configSet);
        Intrinsics.checkNotNullExpressionValue(string, "FileManager.PRETTY_GSON.toJson(configSet)");
        FilesKt.writeText(this.configSetFile, string, Charsets.UTF_8);
    }

    public final void loadLegacySupport() {
        File oldSettingDir;
        if (CrossSine.INSTANCE.getFileManager().loadLegacy()) {
            if (new File(CrossSine.INSTANCE.getFileManager().getConfigsDir(), Intrinsics.stringPlus(this.nowConfig, ".json")).exists()) {
                this.nowConfig = "legacy";
                this.configFile = new File(CrossSine.INSTANCE.getFileManager().getConfigsDir(), Intrinsics.stringPlus(this.nowConfig, ".json"));
                ConfigManager.save$default(this, false, false, true, 3, null);
            } else {
                ConfigManager.save$default(this, false, false, true, 3, null);
            }
            ClientUtils.INSTANCE.logWarn("Converted legacy config");
        }
        if ((oldSettingDir = new File(CrossSine.INSTANCE.getFileManager().getDir(), "settings")).exists()) {
            File[] fileArray = oldSettingDir.listFiles();
            if (fileArray != null) {
                File[] $this$forEach$iv = fileArray;
                boolean $i$f$forEach = false;
                File[] fileArray2 = $this$forEach$iv;
                int n = 0;
                int n2 = fileArray2.length;
                while (n < n2) {
                    File element$iv = fileArray2[n];
                    ++n;
                    File it = element$iv;
                    boolean bl = false;
                    if (it.isFile()) {
                        String name = this.getNowConfig();
                        ClientUtils.INSTANCE.logWarn("Converting legacy setting \"" + it.getName() + '\"');
                        this.load("default", false);
                        Object object = it.getName();
                        Intrinsics.checkNotNullExpressionValue(object, "it.name");
                        this.setNowConfig((String)object);
                        this.configFile = new File(CrossSine.INSTANCE.getFileManager().getConfigsDir(), Intrinsics.stringPlus(this.getNowConfig(), ".json"));
                        object = Files.readAllBytes(it.toPath());
                        Intrinsics.checkNotNullExpressionValue(object, "readAllBytes(it.toPath())");
                        ConfigManager.loadLegacySupport$executeScript(new String((byte[])object, Charsets.UTF_8));
                        ConfigManager.save$default(this, false, false, true, 1, null);
                        this.setNowConfig(name);
                        this.configFile = new File(CrossSine.INSTANCE.getFileManager().getConfigsDir(), Intrinsics.stringPlus(this.getNowConfig(), ".json"));
                        this.saveConfigSet();
                    }
                    if (!CrossSine.INSTANCE.getFileManager().getLegacySettingsDir().exists()) {
                        CrossSine.INSTANCE.getFileManager().getLegacySettingsDir().mkdir();
                    }
                    it.renameTo(new File(CrossSine.INSTANCE.getFileManager().getLegacySettingsDir(), it.getName()));
                }
            }
            oldSettingDir.delete();
        }
    }

    private final boolean registerSection(ConfigSection section) {
        return this.sections.add(section);
    }

    private final void registerSection(Class<? extends ConfigSection> sectionClass) {
        try {
            ConfigSection configSection = sectionClass.newInstance();
            Intrinsics.checkNotNullExpressionValue(configSection, "sectionClass.newInstance()");
            this.registerSection(configSection);
        }
        catch (Throwable e) {
            ClientUtils.INSTANCE.logError("Failed to load config section: " + sectionClass.getName() + " (" + e.getClass().getName() + ": " + e.getMessage() + ')');
        }
    }

    /*
     * WARNING - void declaration
     */
    private static final void loadLegacySupport$executeScript(String script) {
        void $this$filterTo$iv$iv;
        Iterable $this$filter$iv = StringsKt.lines(script);
        boolean $i$f$filter = false;
        Iterable iterable = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            String it = (String)element$iv$iv;
            boolean bl = false;
            if (!(((CharSequence)it).length() > 0 && !StringsKt.startsWith$default((CharSequence)it, '#', false, 2, null))) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        Iterable $this$forEachIndexed$iv = (List)destination$iv$iv;
        boolean $i$f$forEachIndexed = false;
        int index$iv = 0;
        block22: for (Object item$iv : $this$forEachIndexed$iv) {
            String[] args2;
            void s;
            Object element$iv$iv;
            int n = index$iv;
            index$iv = n + 1;
            if (n < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            element$iv$iv = (String)item$iv;
            int $noName_0 = n;
            boolean bl = false;
            Object object = new String[]{" "};
            Collection $this$toTypedArray$iv = StringsKt.split$default((CharSequence)s, (String[])object, false, 0, 6, null);
            boolean $i$f$toTypedArray = false;
            Collection thisCollection$iv = $this$toTypedArray$iv;
            if (thisCollection$iv.toArray(new String[0]) == null) {
                throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
            }
            if (args2.length <= 1) continue;
            switch (args2[0]) {
                case "load": {
                    String url = StringUtils.toCompleteString(args2, 1);
                    try {
                        Intrinsics.checkNotNullExpressionValue(url, "url");
                        ConfigManager.loadLegacySupport$executeScript(HttpUtils.INSTANCE.get(url));
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case "targetPlayer": 
                case "targetPlayers": {
                    Target.INSTANCE.getPlayerValue().set(StringsKt.equals(args2[1], "true", true));
                    break;
                }
                case "targetMobs": {
                    Target.INSTANCE.getMobValue().set(StringsKt.equals(args2[1], "true", true));
                    break;
                }
                case "targetAnimals": {
                    Target.INSTANCE.getAnimalValue().set(StringsKt.equals(args2[1], "true", true));
                    break;
                }
                case "targetInvisible": {
                    Target.INSTANCE.getInvisibleValue().set(StringsKt.equals(args2[1], "true", true));
                    break;
                }
                case "targetDead": {
                    Target.INSTANCE.getDeadValue().set(StringsKt.equals(args2[1], "true", true));
                    break;
                }
                default: {
                    Module module;
                    if (args2.length != 3) continue block22;
                    String moduleName = args2[0];
                    String valueName = args2[1];
                    String value = args2[2];
                    if (CrossSine.INSTANCE.getModuleManager().getModule(moduleName) == null) continue block22;
                    if (StringsKt.equals(valueName, "toggle", true)) {
                        module.setState(StringsKt.equals(value, "true", true));
                        continue block22;
                    }
                    if (StringsKt.equals(valueName, "bind", true)) {
                        module.setKeyBind(Keyboard.getKeyIndex((String)value));
                        continue block22;
                    }
                    if (module.getValue(valueName) == null) continue block22;
                    try {
                        Value<?> moduleValue;
                        Value<?> value2 = moduleValue;
                        if (value2 instanceof BoolValue) {
                            ((BoolValue)moduleValue).changeValue(Boolean.parseBoolean(value));
                            break;
                        }
                        if (value2 instanceof FloatValue) {
                            ((FloatValue)moduleValue).changeValue(Float.valueOf(Float.parseFloat(value)));
                            break;
                        }
                        if (value2 instanceof IntegerValue) {
                            ((IntegerValue)moduleValue).changeValue(Integer.parseInt(value));
                            break;
                        }
                        if (value2 instanceof TextValue) {
                            ((TextValue)moduleValue).changeValue(value);
                            break;
                        }
                        if (!(value2 instanceof ListValue)) continue block22;
                        ((ListValue)moduleValue).changeValue(value);
                        break;
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static final /* synthetic */ void access$saveTicker(ConfigManager $this) {
        $this.saveTicker();
    }

    @Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\n\u001a\u00020\u0003H\u00c6\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u000f\u001a\u00020\u0010H\u00d6\u0001J\t\u0010\u0011\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007\u00a8\u0006\u0012"}, d2={"Lnet/ccbluex/liquidbounce/file/config/ConfigManager$OnlineConfig;", "", "name", "", "downloadUrl", "(Ljava/lang/String;Ljava/lang/String;)V", "getDownloadUrl", "()Ljava/lang/String;", "getName", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "CrossSine"})
    public static final class OnlineConfig {
        @NotNull
        private final String name;
        @NotNull
        private final String downloadUrl;

        public OnlineConfig(@NotNull String name, @NotNull String downloadUrl) {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(downloadUrl, "downloadUrl");
            this.name = name;
            this.downloadUrl = downloadUrl;
        }

        @NotNull
        public final String getName() {
            return this.name;
        }

        @NotNull
        public final String getDownloadUrl() {
            return this.downloadUrl;
        }

        @NotNull
        public final String component1() {
            return this.name;
        }

        @NotNull
        public final String component2() {
            return this.downloadUrl;
        }

        @NotNull
        public final OnlineConfig copy(@NotNull String name, @NotNull String downloadUrl) {
            Intrinsics.checkNotNullParameter(name, "name");
            Intrinsics.checkNotNullParameter(downloadUrl, "downloadUrl");
            return new OnlineConfig(name, downloadUrl);
        }

        public static /* synthetic */ OnlineConfig copy$default(OnlineConfig onlineConfig, String string, String string2, int n, Object object) {
            if ((n & 1) != 0) {
                string = onlineConfig.name;
            }
            if ((n & 2) != 0) {
                string2 = onlineConfig.downloadUrl;
            }
            return onlineConfig.copy(string, string2);
        }

        @NotNull
        public String toString() {
            return "OnlineConfig(name=" + this.name + ", downloadUrl=" + this.downloadUrl + ')';
        }

        public int hashCode() {
            int result = this.name.hashCode();
            result = result * 31 + this.downloadUrl.hashCode();
            return result;
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof OnlineConfig)) {
                return false;
            }
            OnlineConfig onlineConfig = (OnlineConfig)other;
            if (!Intrinsics.areEqual(this.name, onlineConfig.name)) {
                return false;
            }
            return Intrinsics.areEqual(this.downloadUrl, onlineConfig.downloadUrl);
        }
    }
}

