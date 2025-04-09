/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.utils;

import com.google.gson.JsonObject;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.features.command.CommandManager;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.modules.player.KillSay;
import net.ccbluex.liquidbounce.ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.util.IChatComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0003\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0001\u001aB\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010J\u000e\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010J\u000e\u0010\u0012\u001a\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\u0010J\u000e\u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\u0010J\u0016\u0010\u0014\u001a\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u00020\u0016J\u000e\u0010\u0017\u001a\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\u0010J\u000e\u0010\u0018\u001a\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\u0010J\u0006\u0010\u0019\u001a\u00020\u000eR$\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u00048\u0006X\u0087\u0004\u00a2\u0006\u000e\n\u0000\u0012\u0004\b\u0006\u0010\u0002\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u001b"}, d2={"Lnet/ccbluex/liquidbounce/utils/ClientUtils;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "logger", "Lorg/apache/logging/log4j/Logger;", "kotlin.jvm.PlatformType", "getLogger$annotations", "getLogger", "()Lorg/apache/logging/log4j/Logger;", "mc", "Lnet/minecraft/client/Minecraft;", "getMc", "()Lnet/minecraft/client/Minecraft;", "displayAlert", "", "message", "", "displayChatMessage", "logDebug", "msg", "logError", "t", "", "logInfo", "logWarn", "reloadClient", "EnumOSType", "CrossSine"})
public final class ClientUtils
extends MinecraftInstance {
    @NotNull
    public static final ClientUtils INSTANCE = new ClientUtils();
    private static final Logger logger = LogManager.getLogger((String)"CrossSine");
    @NotNull
    private static final Minecraft mc;

    private ClientUtils() {
    }

    public static final Logger getLogger() {
        return logger;
    }

    @JvmStatic
    public static /* synthetic */ void getLogger$annotations() {
    }

    public final void logInfo(@NotNull String msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        logger.info(msg);
    }

    public final void logWarn(@NotNull String msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        logger.warn(msg);
    }

    public final void logError(@NotNull String msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        logger.error(msg);
    }

    public final void logError(@NotNull String msg, @NotNull Throwable t) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        Intrinsics.checkNotNullParameter(t, "t");
        logger.error(msg, t);
    }

    public final void logDebug(@NotNull String msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        logger.debug(msg);
    }

    public final void displayAlert(@NotNull String message) {
        Intrinsics.checkNotNullParameter(message, "message");
        this.displayChatMessage(Intrinsics.stringPlus("[\u00a7CC\u00a7FrossSine] ", message));
    }

    public final void displayChatMessage(@NotNull String message) {
        Intrinsics.checkNotNullParameter(message, "message");
        if (ClientUtils.mc.field_71439_g == null) {
            logger.info(Intrinsics.stringPlus("(MCChat) ", message));
            return;
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("text", message);
        ClientUtils.mc.field_71439_g.func_145747_a(IChatComponent.Serializer.func_150699_a((String)jsonObject.toString()));
    }

    public final void reloadClient() {
        CrossSine.INSTANCE.setCommandManager(new CommandManager());
        CrossSine.INSTANCE.getCommandManager().registerCommands();
        CrossSine.INSTANCE.setStarting(true);
        CrossSine.INSTANCE.setLoadingConfig(true);
        CrossSine.INSTANCE.getScriptManager().disableScripts();
        CrossSine.INSTANCE.getScriptManager().unloadScripts();
        for (Module module : CrossSine.INSTANCE.getModuleManager().getModules()) {
            CrossSine.INSTANCE.getModuleManager().generateCommand$CrossSine(module);
        }
        CrossSine.INSTANCE.getScriptManager().loadScripts();
        CrossSine.INSTANCE.getScriptManager().enableScripts();
        Fonts.loadFonts();
        CrossSine.INSTANCE.getConfigManager().load(CrossSine.INSTANCE.getConfigManager().getNowConfig(), false);
        KillSay.INSTANCE.loadFile();
        CrossSine.INSTANCE.getFileManager().loadConfig(CrossSine.INSTANCE.getFileManager().getAccountsConfig());
        CrossSine.INSTANCE.getFileManager().loadConfig(CrossSine.INSTANCE.getFileManager().getFriendsConfig());
        CrossSine.INSTANCE.getFileManager().loadConfig(CrossSine.INSTANCE.getFileManager().getXrayConfig());
        CrossSine.INSTANCE.getFileManager().loadConfig(CrossSine.INSTANCE.getFileManager().getThemeConfig());
        CrossSine.INSTANCE.getFileManager().loadConfig(CrossSine.INSTANCE.getFileManager().getThemeConfig());
        CrossSine.INSTANCE.setStarting(false);
        CrossSine.INSTANCE.setLoadingConfig(false);
        System.gc();
    }

    @NotNull
    public final Minecraft getMc() {
        return mc;
    }

    static {
        Minecraft minecraft = Minecraft.func_71410_x();
        Intrinsics.checkNotNull(minecraft);
        mc = minecraft;
    }

    @Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\n\u00a8\u0006\u000b"}, d2={"Lnet/ccbluex/liquidbounce/utils/ClientUtils$EnumOSType;", "", "friendlyName", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getFriendlyName", "()Ljava/lang/String;", "WINDOWS", "LINUX", "MACOS", "UNKNOWN", "CrossSine"})
    public static final class EnumOSType
    extends Enum<EnumOSType> {
        @NotNull
        private final String friendlyName;
        public static final /* enum */ EnumOSType WINDOWS = new EnumOSType("win");
        public static final /* enum */ EnumOSType LINUX = new EnumOSType("linux");
        public static final /* enum */ EnumOSType MACOS = new EnumOSType("mac");
        public static final /* enum */ EnumOSType UNKNOWN = new EnumOSType("unk");
        private static final /* synthetic */ EnumOSType[] $VALUES;

        private EnumOSType(String friendlyName) {
            this.friendlyName = friendlyName;
        }

        @NotNull
        public final String getFriendlyName() {
            return this.friendlyName;
        }

        public static EnumOSType[] values() {
            return (EnumOSType[])$VALUES.clone();
        }

        public static EnumOSType valueOf(String value) {
            return Enum.valueOf(EnumOSType.class, value);
        }

        static {
            $VALUES = enumOSTypeArray = new EnumOSType[]{EnumOSType.WINDOWS, EnumOSType.LINUX, EnumOSType.MACOS, EnumOSType.UNKNOWN};
        }
    }
}

