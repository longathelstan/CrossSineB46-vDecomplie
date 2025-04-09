/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.discordrpc;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.DiscordBuild;
import com.jagrosh.discordipc.entities.RichPresence;
import com.jagrosh.discordipc.entities.pipe.PipeStatus;
import java.time.OffsetDateTime;
import kotlin.Metadata;
import kotlin.concurrent.ThreadsKt;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.discordrpc.CrossSineRPC;
import net.ccbluex.liquidbounce.ui.client.gui.GuiMainMenu;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.ServerUtils;
import net.minecraft.client.gui.GuiMultiplayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\n\u001a\u00020\u000bJ\u0006\u0010\f\u001a\u00020\u000bJ\b\u0010\r\u001a\u00020\u000bH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\n \t*\u0004\u0018\u00010\b0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2={"Lnet/ccbluex/liquidbounce/discordrpc/CrossSineRPC;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "ipcClient", "Lcom/jagrosh/discordipc/IPCClient;", "running", "", "timestamp", "Ljava/time/OffsetDateTime;", "kotlin.jvm.PlatformType", "run", "", "stop", "update", "CrossSine"})
public final class CrossSineRPC
extends MinecraftInstance {
    @NotNull
    public static final CrossSineRPC INSTANCE = new CrossSineRPC();
    @NotNull
    private static final IPCClient ipcClient = new IPCClient(1218820754552918096L);
    private static final OffsetDateTime timestamp = OffsetDateTime.now();
    private static boolean running;

    private CrossSineRPC() {
    }

    public final void run() {
        ipcClient.setListener(new IPCListener(){

            public void onReady(@Nullable IPCClient client) {
                CrossSineRPC.access$setRunning$p(true);
                ThreadsKt.thread$default(false, false, null, null, 0, run.onReady.1.INSTANCE, 31, null);
            }

            public void onClose(@Nullable IPCClient client, @Nullable JSONObject json) {
                CrossSineRPC.access$setRunning$p(false);
            }
        });
        try {
            ipcClient.connect(new DiscordBuild[0]);
        }
        catch (Exception e) {
            ClientUtils.INSTANCE.logError("DiscordRPC failed to start");
        }
        catch (RuntimeException e) {
            ClientUtils.INSTANCE.logError("DiscordRPC failed to start");
        }
    }

    private final void update() {
        RichPresence.Builder builder = new RichPresence.Builder();
        builder.setStartTimestamp(timestamp);
        builder.setLargeImage("https://crosssine.github.io/file/CrossSinelogo.png", "CrossSine Client");
        if (MinecraftInstance.mc.field_71462_r instanceof GuiMainMenu) {
            builder.setDetails("MainMenu");
        } else if (MinecraftInstance.mc.field_71462_r instanceof GuiMultiplayer) {
            builder.setDetails("Selecting Server");
        } else if (MinecraftInstance.mc.field_71441_e != null && MinecraftInstance.mc.field_71441_e.field_72995_K) {
            builder.setDetails(Intrinsics.stringPlus("Playing : ", ServerUtils.getRemoteIp()));
        } else {
            builder.setDetails(Intrinsics.stringPlus(MinecraftInstance.mc.field_71449_j.func_111285_a(), " is best player"));
        }
        builder.setState("Version : Build 46");
        if (ipcClient.getStatus() == PipeStatus.CONNECTED) {
            ipcClient.sendRichPresence(builder.build());
        }
    }

    public final void stop() {
        if (ipcClient.getStatus() == PipeStatus.CONNECTED) {
            ipcClient.close();
        }
    }

    public static final /* synthetic */ void access$setRunning$p(boolean bl) {
        running = bl;
    }

    public static final /* synthetic */ boolean access$getRunning$p() {
        return running;
    }

    public static final /* synthetic */ void access$update(CrossSineRPC $this) {
        $this.update();
    }
}

