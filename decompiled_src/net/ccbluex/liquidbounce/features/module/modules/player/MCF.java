/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.player;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.file.configs.FriendsConfig;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Mouse;

@ModuleInfo(name="MCF", category=ModuleCategory.PLAYER)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/player/MCF;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "wasDown", "", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "CrossSine"})
public final class MCF
extends Module {
    private boolean wasDown;

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (MinecraftInstance.mc.field_71462_r != null) {
            return;
        }
        if (!this.wasDown && Mouse.isButtonDown((int)2)) {
            Entity entity = MinecraftInstance.mc.field_71476_x.field_72308_g;
            if (entity instanceof EntityPlayer) {
                String string = entity.func_70005_c_();
                Intrinsics.checkNotNullExpressionValue(string, "entity.getName()");
                String playerName = ColorUtils.stripColor(string);
                FriendsConfig friendsConfig = CrossSine.INSTANCE.getFileManager().getFriendsConfig();
                if (!friendsConfig.isFriend(playerName)) {
                    FriendsConfig.addFriend$default(friendsConfig, playerName, null, 2, null);
                    CrossSine.INSTANCE.getFileManager().saveConfig(friendsConfig);
                    ClientUtils.INSTANCE.displayChatMessage("\u00a7a\u00a7l" + playerName + "\u00a7c was added to your friends.");
                } else {
                    friendsConfig.removeFriend(playerName);
                    CrossSine.INSTANCE.getFileManager().saveConfig(friendsConfig);
                    ClientUtils.INSTANCE.displayChatMessage("\u00a7a\u00a7l" + playerName + "\u00a7c was removed from your friends.");
                }
            } else {
                ClientUtils.INSTANCE.displayChatMessage("\u00a7c\u00a7lError: \u00a7aYou need to select a player.");
            }
        }
        this.wasDown = Mouse.isButtonDown((int)2);
    }
}

