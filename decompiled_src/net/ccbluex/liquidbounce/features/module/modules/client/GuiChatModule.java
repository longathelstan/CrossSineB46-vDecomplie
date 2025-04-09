/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.client;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(name="ChatManager", category=ModuleCategory.CLIENT, array=false, defaultOn=true)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006R\u0011\u0010\u000b\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0006\u00a8\u0006\r"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/client/GuiChatModule;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "chatAnimValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "getChatAnimValue", "()Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "chatCombine", "getChatCombine", "chatLimitValue", "getChatLimitValue", "chatRectValue", "getChatRectValue", "CrossSine"})
public final class GuiChatModule
extends Module {
    @NotNull
    private final BoolValue chatRectValue = new BoolValue("ChatRect", false);
    @NotNull
    private final BoolValue chatLimitValue = new BoolValue("NoChatLimit", true);
    @NotNull
    private final BoolValue chatCombine = new BoolValue("ChatCombine", true);
    @NotNull
    private final BoolValue chatAnimValue = new BoolValue("ChatAnimation", true);

    @NotNull
    public final BoolValue getChatRectValue() {
        return this.chatRectValue;
    }

    @NotNull
    public final BoolValue getChatLimitValue() {
        return this.chatLimitValue;
    }

    @NotNull
    public final BoolValue getChatCombine() {
        return this.chatCombine;
    }

    @NotNull
    public final BoolValue getChatAnimValue() {
        return this.chatAnimValue;
    }
}

