/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.player;

import java.util.Timer;
import java.util.TimerTask;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.TextValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S45PacketTitle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(name="AutoLogin", category=ModuleCategory.PLAYER)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u000fH\u0002J\b\u0010\u0016\u001a\u00020\u0014H\u0016J\u0010\u0010\u0017\u001a\u00020\u00142\u0006\u0010\u0018\u001a\u00020\u0019H\u0007J\u0010\u0010\u001a\u001a\u00020\u00142\u0006\u0010\u0018\u001a\u00020\u001bH\u0007J\u0010\u0010\u001c\u001a\u00020\u00142\u0006\u0010\u001d\u001a\u00020\u000fH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u000e\u001a\u0004\u0018\u00010\u000f8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0012\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001e"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/player/AutoLogin;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "chatValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "delayValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "loginCommandValue", "Lnet/ccbluex/liquidbounce/features/value/TextValue;", "logined", "", "passwordValue", "registerCommandValue", "showPassword", "tag", "", "getTag", "()Ljava/lang/String;", "titleValue", "delayedMessage", "", "message", "onEnable", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "processMessage", "msg", "CrossSine"})
public final class AutoLogin
extends Module {
    @NotNull
    private final TextValue registerCommandValue = new TextValue("Register", "/register %p %p");
    @NotNull
    private final TextValue loginCommandValue = new TextValue("Login", "/login %p");
    @NotNull
    private final TextValue passwordValue = new TextValue("Password", "password");
    @NotNull
    private final IntegerValue delayValue = new IntegerValue("Delay", 1500, 100, 5000);
    @NotNull
    private final BoolValue titleValue = new BoolValue("Title", true);
    @NotNull
    private final BoolValue chatValue = new BoolValue("Chat", true);
    @NotNull
    private final BoolValue showPassword = new BoolValue("ShowPassword", false);
    private boolean logined;

    @Override
    public void onEnable() {
        this.logined = false;
    }

    @EventTarget
    public final void onWorld(@NotNull WorldEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.logined = false;
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        String string;
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.logined) {
            return;
        }
        Packet<?> packet = event.getPacket();
        if (((Boolean)this.titleValue.get()).booleanValue() && packet instanceof S45PacketTitle) {
            if (((S45PacketTitle)packet).func_179805_b() == null) {
                return;
            }
            string = ((S45PacketTitle)packet).func_179805_b().func_150260_c();
            Intrinsics.checkNotNullExpressionValue(string, "packet.message.unformattedText");
            this.processMessage(string);
        }
        if (((Boolean)this.chatValue.get()).booleanValue() && packet instanceof S02PacketChat) {
            string = ((S02PacketChat)packet).func_148915_c().func_150260_c();
            Intrinsics.checkNotNullExpressionValue(string, "packet.chatComponent.unformattedText");
            this.processMessage(string);
        }
    }

    private final void processMessage(String msg) {
        String[] stringArray;
        if (!StringsKt.isBlank((CharSequence)this.registerCommandValue.get())) {
            stringArray = new String[]{" "};
            String regCommand = (String)StringsKt.split$default((CharSequence)this.registerCommandValue.get(), stringArray, false, 0, 6, null).get(0);
            if (((CharSequence)regCommand).length() > 0 && StringsKt.contains((CharSequence)msg, regCommand, true)) {
                this.delayedMessage(StringsKt.replace$default((String)this.registerCommandValue.get(), "%p", (String)this.passwordValue.get(), false, 4, null));
            }
        }
        if (!StringsKt.isBlank((CharSequence)this.loginCommandValue.get())) {
            stringArray = new String[]{" "};
            String logCommand = (String)StringsKt.split$default((CharSequence)this.loginCommandValue.get(), stringArray, false, 0, 6, null).get(0);
            if (((CharSequence)logCommand).length() > 0 && StringsKt.contains((CharSequence)msg, logCommand, true)) {
                this.delayedMessage(StringsKt.replace$default((String)this.loginCommandValue.get(), "%p", (String)this.passwordValue.get(), false, 4, null));
            }
        }
    }

    private final void delayedMessage(String message) {
        this.logined = true;
        Timer timer = new Timer();
        long l = ((Number)this.delayValue.get()).intValue();
        TimerTask timerTask2 = new TimerTask(this, message){
            final /* synthetic */ AutoLogin this$0;
            final /* synthetic */ String $message$inlined;
            {
                this.this$0 = autoLogin;
                this.$message$inlined = string;
            }

            public void run() {
                TimerTask $this$delayedMessage_u24lambda_u2d0 = this;
                boolean bl = false;
                if (this.this$0.getState() && MinecraftInstance.mc.field_71439_g != null) {
                    MinecraftInstance.mc.field_71439_g.func_71165_d(this.$message$inlined);
                }
            }
        };
        timer.schedule(timerTask2, l);
    }

    @Override
    @Nullable
    public String getTag() {
        return (Boolean)this.showPassword.get() != false ? (String)this.passwordValue.get() : null;
    }
}

