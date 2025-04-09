/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce;

import kotlin.Metadata;
import kotlin.concurrent.ThreadsKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.discordrpc.CrossSineRPC;
import net.ccbluex.liquidbounce.event.ClientShutdownEvent;
import net.ccbluex.liquidbounce.event.EventManager;
import net.ccbluex.liquidbounce.features.command.CommandManager;
import net.ccbluex.liquidbounce.features.macro.MacroManager;
import net.ccbluex.liquidbounce.features.module.ModuleManager;
import net.ccbluex.liquidbounce.features.module.modules.client.Interface;
import net.ccbluex.liquidbounce.features.module.modules.client.SelfDestructCommand;
import net.ccbluex.liquidbounce.features.special.ClientSpoof;
import net.ccbluex.liquidbounce.features.special.CombatManager;
import net.ccbluex.liquidbounce.features.special.NotificationManager;
import net.ccbluex.liquidbounce.features.special.NotificationUtil;
import net.ccbluex.liquidbounce.features.special.TYPE;
import net.ccbluex.liquidbounce.file.FileConfig;
import net.ccbluex.liquidbounce.file.FileManager;
import net.ccbluex.liquidbounce.file.config.ConfigManager;
import net.ccbluex.liquidbounce.protocol.api.McUpdatesHandler;
import net.ccbluex.liquidbounce.protocol.api.PacketManager;
import net.ccbluex.liquidbounce.script.ScriptManager;
import net.ccbluex.liquidbounce.ui.client.gui.ClickGUIModule;
import net.ccbluex.liquidbounce.ui.client.gui.GuiMainMenu;
import net.ccbluex.liquidbounce.ui.client.keybind.KeyBindManager;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.InventoryUtils;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.SessionUtils;
import net.ccbluex.liquidbounce.utils.SlotUtils;
import net.ccbluex.liquidbounce.utils.StatisticsUtils;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.command.ICommand;
import net.minecraftforge.client.ClientCommandHandler;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.Display;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0084\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010^\u001a\u00020_J\u0006\u0010`\u001a\u00020_R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u00020\u000eX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0013\u001a\u00020\u0014X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u001a\u0010\u0019\u001a\u00020\u001aX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u001a\u0010\u001f\u001a\u00020 X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R\u001a\u0010%\u001a\u00020\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b&\u0010'\"\u0004\b(\u0010)R\u001a\u0010*\u001a\u00020+X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b,\u0010-\"\u0004\b.\u0010/R\u001a\u00100\u001a\u000201X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b2\u00103\"\u0004\b4\u00105R\u001a\u00106\u001a\u00020\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b6\u0010'\"\u0004\b7\u0010)R\u001a\u00108\u001a\u00020\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b8\u0010'\"\u0004\b9\u0010)R\u001a\u0010:\u001a\u00020;X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b<\u0010=\"\u0004\b>\u0010?R\u001a\u0010@\u001a\u00020AX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\bB\u0010C\"\u0004\bD\u0010ER\u001a\u0010F\u001a\u00020GX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\bH\u0010I\"\u0004\bJ\u0010KR\u001a\u0010L\u001a\u00020MX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\bN\u0010O\"\u0004\bP\u0010QR\u001a\u0010R\u001a\u00020SX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\bT\u0010U\"\u0004\bV\u0010WR\u001a\u0010X\u001a\u00020YX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\bZ\u0010[\"\u0004\b\\\u0010]\u00a8\u0006a"}, d2={"Lnet/ccbluex/liquidbounce/CrossSine;", "", "()V", "CLIENT_CLOUD", "", "CLIENT_CREATOR", "CLIENT_LOADING", "CLIENT_NAME", "CLIENT_STATUS", "", "CLIENT_TITLE", "CLIENT_VERSION", "COLORED_NAME", "clientRPC", "Lnet/ccbluex/liquidbounce/discordrpc/CrossSineRPC;", "getClientRPC", "()Lnet/ccbluex/liquidbounce/discordrpc/CrossSineRPC;", "setClientRPC", "(Lnet/ccbluex/liquidbounce/discordrpc/CrossSineRPC;)V", "combatManager", "Lnet/ccbluex/liquidbounce/features/special/CombatManager;", "getCombatManager", "()Lnet/ccbluex/liquidbounce/features/special/CombatManager;", "setCombatManager", "(Lnet/ccbluex/liquidbounce/features/special/CombatManager;)V", "commandManager", "Lnet/ccbluex/liquidbounce/features/command/CommandManager;", "getCommandManager", "()Lnet/ccbluex/liquidbounce/features/command/CommandManager;", "setCommandManager", "(Lnet/ccbluex/liquidbounce/features/command/CommandManager;)V", "configManager", "Lnet/ccbluex/liquidbounce/file/config/ConfigManager;", "getConfigManager", "()Lnet/ccbluex/liquidbounce/file/config/ConfigManager;", "setConfigManager", "(Lnet/ccbluex/liquidbounce/file/config/ConfigManager;)V", "destruced", "getDestruced", "()Z", "setDestruced", "(Z)V", "eventManager", "Lnet/ccbluex/liquidbounce/event/EventManager;", "getEventManager", "()Lnet/ccbluex/liquidbounce/event/EventManager;", "setEventManager", "(Lnet/ccbluex/liquidbounce/event/EventManager;)V", "fileManager", "Lnet/ccbluex/liquidbounce/file/FileManager;", "getFileManager", "()Lnet/ccbluex/liquidbounce/file/FileManager;", "setFileManager", "(Lnet/ccbluex/liquidbounce/file/FileManager;)V", "isLoadingConfig", "setLoadingConfig", "isStarting", "setStarting", "keyBindManager", "Lnet/ccbluex/liquidbounce/ui/client/keybind/KeyBindManager;", "getKeyBindManager", "()Lnet/ccbluex/liquidbounce/ui/client/keybind/KeyBindManager;", "setKeyBindManager", "(Lnet/ccbluex/liquidbounce/ui/client/keybind/KeyBindManager;)V", "macroManager", "Lnet/ccbluex/liquidbounce/features/macro/MacroManager;", "getMacroManager", "()Lnet/ccbluex/liquidbounce/features/macro/MacroManager;", "setMacroManager", "(Lnet/ccbluex/liquidbounce/features/macro/MacroManager;)V", "mainMenu", "Lnet/minecraft/client/gui/GuiScreen;", "getMainMenu", "()Lnet/minecraft/client/gui/GuiScreen;", "setMainMenu", "(Lnet/minecraft/client/gui/GuiScreen;)V", "moduleManager", "Lnet/ccbluex/liquidbounce/features/module/ModuleManager;", "getModuleManager", "()Lnet/ccbluex/liquidbounce/features/module/ModuleManager;", "setModuleManager", "(Lnet/ccbluex/liquidbounce/features/module/ModuleManager;)V", "notification", "Lnet/ccbluex/liquidbounce/features/special/NotificationManager;", "getNotification", "()Lnet/ccbluex/liquidbounce/features/special/NotificationManager;", "setNotification", "(Lnet/ccbluex/liquidbounce/features/special/NotificationManager;)V", "scriptManager", "Lnet/ccbluex/liquidbounce/script/ScriptManager;", "getScriptManager", "()Lnet/ccbluex/liquidbounce/script/ScriptManager;", "setScriptManager", "(Lnet/ccbluex/liquidbounce/script/ScriptManager;)V", "initClient", "", "stopClient", "CrossSine"})
public final class CrossSine {
    @NotNull
    public static final CrossSine INSTANCE = new CrossSine();
    @NotNull
    public static final String CLIENT_NAME = "CrossSine";
    @NotNull
    public static final String CLIENT_CLOUD = "https://crosssine.github.io/cloud";
    private static boolean CLIENT_STATUS;
    @NotNull
    public static final String COLORED_NAME = "\u00a7CC\u00a7FrossSine";
    @NotNull
    public static final String CLIENT_CREATOR = "Shape";
    @NotNull
    public static final String CLIENT_VERSION = "46";
    private static boolean destruced;
    @NotNull
    public static final String CLIENT_LOADING = "Initialzing Minecraft";
    @JvmField
    @NotNull
    public static final String CLIENT_TITLE;
    private static boolean isStarting;
    private static boolean isLoadingConfig;
    public static ModuleManager moduleManager;
    public static CommandManager commandManager;
    public static EventManager eventManager;
    public static FileManager fileManager;
    public static ScriptManager scriptManager;
    public static CombatManager combatManager;
    public static MacroManager macroManager;
    public static ConfigManager configManager;
    public static NotificationManager notification;
    public static GuiScreen mainMenu;
    public static KeyBindManager keyBindManager;
    public static CrossSineRPC clientRPC;

    private CrossSine() {
    }

    public final boolean getDestruced() {
        return destruced;
    }

    public final void setDestruced(boolean bl) {
        destruced = bl;
    }

    public final boolean isStarting() {
        return isStarting;
    }

    public final void setStarting(boolean bl) {
        isStarting = bl;
    }

    public final boolean isLoadingConfig() {
        return isLoadingConfig;
    }

    public final void setLoadingConfig(boolean bl) {
        isLoadingConfig = bl;
    }

    @NotNull
    public final ModuleManager getModuleManager() {
        ModuleManager moduleManager = CrossSine.moduleManager;
        if (moduleManager != null) {
            return moduleManager;
        }
        Intrinsics.throwUninitializedPropertyAccessException("moduleManager");
        return null;
    }

    public final void setModuleManager(@NotNull ModuleManager moduleManager) {
        Intrinsics.checkNotNullParameter(moduleManager, "<set-?>");
        CrossSine.moduleManager = moduleManager;
    }

    @NotNull
    public final CommandManager getCommandManager() {
        CommandManager commandManager = CrossSine.commandManager;
        if (commandManager != null) {
            return commandManager;
        }
        Intrinsics.throwUninitializedPropertyAccessException("commandManager");
        return null;
    }

    public final void setCommandManager(@NotNull CommandManager commandManager) {
        Intrinsics.checkNotNullParameter(commandManager, "<set-?>");
        CrossSine.commandManager = commandManager;
    }

    @NotNull
    public final EventManager getEventManager() {
        EventManager eventManager = CrossSine.eventManager;
        if (eventManager != null) {
            return eventManager;
        }
        Intrinsics.throwUninitializedPropertyAccessException("eventManager");
        return null;
    }

    public final void setEventManager(@NotNull EventManager eventManager) {
        Intrinsics.checkNotNullParameter(eventManager, "<set-?>");
        CrossSine.eventManager = eventManager;
    }

    @NotNull
    public final FileManager getFileManager() {
        FileManager fileManager = CrossSine.fileManager;
        if (fileManager != null) {
            return fileManager;
        }
        Intrinsics.throwUninitializedPropertyAccessException("fileManager");
        return null;
    }

    public final void setFileManager(@NotNull FileManager fileManager) {
        Intrinsics.checkNotNullParameter(fileManager, "<set-?>");
        CrossSine.fileManager = fileManager;
    }

    @NotNull
    public final ScriptManager getScriptManager() {
        ScriptManager scriptManager = CrossSine.scriptManager;
        if (scriptManager != null) {
            return scriptManager;
        }
        Intrinsics.throwUninitializedPropertyAccessException("scriptManager");
        return null;
    }

    public final void setScriptManager(@NotNull ScriptManager scriptManager) {
        Intrinsics.checkNotNullParameter(scriptManager, "<set-?>");
        CrossSine.scriptManager = scriptManager;
    }

    @NotNull
    public final CombatManager getCombatManager() {
        CombatManager combatManager = CrossSine.combatManager;
        if (combatManager != null) {
            return combatManager;
        }
        Intrinsics.throwUninitializedPropertyAccessException("combatManager");
        return null;
    }

    public final void setCombatManager(@NotNull CombatManager combatManager) {
        Intrinsics.checkNotNullParameter(combatManager, "<set-?>");
        CrossSine.combatManager = combatManager;
    }

    @NotNull
    public final MacroManager getMacroManager() {
        MacroManager macroManager = CrossSine.macroManager;
        if (macroManager != null) {
            return macroManager;
        }
        Intrinsics.throwUninitializedPropertyAccessException("macroManager");
        return null;
    }

    public final void setMacroManager(@NotNull MacroManager macroManager) {
        Intrinsics.checkNotNullParameter(macroManager, "<set-?>");
        CrossSine.macroManager = macroManager;
    }

    @NotNull
    public final ConfigManager getConfigManager() {
        ConfigManager configManager = CrossSine.configManager;
        if (configManager != null) {
            return configManager;
        }
        Intrinsics.throwUninitializedPropertyAccessException("configManager");
        return null;
    }

    public final void setConfigManager(@NotNull ConfigManager configManager) {
        Intrinsics.checkNotNullParameter(configManager, "<set-?>");
        CrossSine.configManager = configManager;
    }

    @NotNull
    public final NotificationManager getNotification() {
        NotificationManager notificationManager = notification;
        if (notificationManager != null) {
            return notificationManager;
        }
        Intrinsics.throwUninitializedPropertyAccessException("notification");
        return null;
    }

    public final void setNotification(@NotNull NotificationManager notificationManager) {
        Intrinsics.checkNotNullParameter(notificationManager, "<set-?>");
        notification = notificationManager;
    }

    @NotNull
    public final GuiScreen getMainMenu() {
        GuiScreen guiScreen = mainMenu;
        if (guiScreen != null) {
            return guiScreen;
        }
        Intrinsics.throwUninitializedPropertyAccessException("mainMenu");
        return null;
    }

    public final void setMainMenu(@NotNull GuiScreen guiScreen) {
        Intrinsics.checkNotNullParameter(guiScreen, "<set-?>");
        mainMenu = guiScreen;
    }

    @NotNull
    public final KeyBindManager getKeyBindManager() {
        KeyBindManager keyBindManager = CrossSine.keyBindManager;
        if (keyBindManager != null) {
            return keyBindManager;
        }
        Intrinsics.throwUninitializedPropertyAccessException("keyBindManager");
        return null;
    }

    public final void setKeyBindManager(@NotNull KeyBindManager keyBindManager) {
        Intrinsics.checkNotNullParameter((Object)keyBindManager, "<set-?>");
        CrossSine.keyBindManager = keyBindManager;
    }

    @NotNull
    public final CrossSineRPC getClientRPC() {
        CrossSineRPC crossSineRPC = clientRPC;
        if (crossSineRPC != null) {
            return crossSineRPC;
        }
        Intrinsics.throwUninitializedPropertyAccessException("clientRPC");
        return null;
    }

    public final void setClientRPC(@NotNull CrossSineRPC crossSineRPC) {
        Intrinsics.checkNotNullParameter(crossSineRPC, "<set-?>");
        clientRPC = crossSineRPC;
    }

    public final void initClient() {
        ClientUtils.INSTANCE.logInfo("Loading CrossSine 46, by Shape");
        ClientUtils.INSTANCE.logInfo("Initialzing...");
        Display.setTitle((String)"Initialzing CrossSine...");
        long startTime = System.currentTimeMillis();
        this.setFileManager(new FileManager());
        this.setConfigManager(new ConfigManager());
        ClientCommandHandler.instance.func_71560_a((ICommand)new SelfDestructCommand());
        this.setEventManager(new EventManager());
        Display.setTitle((String)"Loading event");
        this.getEventManager().registerListener(new RotationUtils());
        this.getEventManager().registerListener(new ClientSpoof());
        this.getEventManager().registerListener(InventoryUtils.INSTANCE);
        this.getEventManager().registerListener(new SessionUtils());
        this.getEventManager().registerListener(new StatisticsUtils());
        this.getEventManager().registerListener(SlotUtils.INSTANCE);
        this.getEventManager().registerListener(new McUpdatesHandler());
        this.getEventManager().registerListener(new PacketManager());
        this.getEventManager().registerListener(SlotUtils.INSTANCE);
        this.setCommandManager(new CommandManager());
        this.setNotification(new NotificationManager());
        this.setClientRPC(CrossSineRPC.INSTANCE);
        Display.setTitle((String)"Load config");
        FileConfig[] fileConfigArray = new FileConfig[]{this.getFileManager().getAccountsConfig(), this.getFileManager().getFriendsConfig(), this.getFileManager().getSpecialConfig(), this.getFileManager().getThemeConfig(), this.getFileManager().getClienthudConfig(), this.getFileManager().getSubscriptsConfig()};
        this.getFileManager().loadConfigs(fileConfigArray);
        Display.setTitle((String)"Load Fonts");
        Fonts.loadFonts();
        this.setMacroManager(new MacroManager());
        this.getEventManager().registerListener(this.getMacroManager());
        Display.setTitle((String)"Load Module");
        this.setModuleManager(new ModuleManager());
        this.getModuleManager().registerModules();
        try {
            this.setScriptManager(new ScriptManager());
            this.getScriptManager().loadScripts();
            this.getScriptManager().enableScripts();
        }
        catch (Throwable throwable) {
            ClientUtils.INSTANCE.logError("Failed to load scripts.", throwable);
        }
        this.getCommandManager().registerCommands();
        this.setKeyBindManager(new KeyBindManager());
        this.setCombatManager(new CombatManager());
        this.getEventManager().registerListener(this.getCombatManager());
        this.setMainMenu(new GuiMainMenu());
        this.getModuleManager().registerModule(new ClickGUIModule());
        this.getConfigManager().loadLegacySupport();
        this.getConfigManager().loadConfigSet();
        isStarting = false;
        isLoadingConfig = false;
        ThreadsKt.thread$default(false, false, null, null, 0, initClient.1.INSTANCE, 31, null);
        Interface.INSTANCE.setState(true);
        FileConfig[] throwable = new FileConfig[]{this.getFileManager().getXrayConfig()};
        this.getFileManager().loadConfigs(throwable);
        ClientUtils.INSTANCE.logInfo("Loading Script Subscripts...");
        Display.setTitle((String)"Loading Script");
        try {
            this.setScriptManager(new ScriptManager());
            this.getScriptManager().loadScripts();
            this.getScriptManager().enableScripts();
        }
        catch (Throwable throwable2) {
            ClientUtils.INSTANCE.logError("Failed to load scripts.", throwable2);
        }
        Display.setTitle((String)CLIENT_TITLE);
        ClientUtils.INSTANCE.logInfo("CrossSine 46 started!");
        ClientUtils.INSTANCE.logInfo("CrossSine 46 loaded in " + (System.currentTimeMillis() - startTime) + "ms!");
        this.getNotification().getList().add(new NotificationUtil(CLIENT_NAME, "CrossSine 46 loaded in " + (System.currentTimeMillis() - startTime) + "ms!", TYPE.SUCCESS, System.currentTimeMillis(), 3500));
    }

    public final void stopClient() {
        if (!isStarting && !isLoadingConfig) {
            ClientUtils.INSTANCE.logInfo("Shutting down CrossSine 46!");
            this.getEventManager().callEvent(new ClientShutdownEvent());
            ConfigManager.save$default(this.getConfigManager(), true, true, false, 4, null);
            this.getFileManager().saveAllConfigs();
        }
        this.getClientRPC().stop();
    }

    static {
        CLIENT_TITLE = Intrinsics.stringPlus("CrossSine B46", CLIENT_STATUS ? " (Beta)" : "");
        isStarting = true;
        isLoadingConfig = true;
    }
}

