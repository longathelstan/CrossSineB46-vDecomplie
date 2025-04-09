/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.utils.login;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import me.liuli.elixir.account.CrackedAccount;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.SessionEvent;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0006\u0010\u0007\u001a\u00020\u0004\u00a8\u0006\b"}, d2={"Lnet/ccbluex/liquidbounce/utils/login/LoginUtils;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "loginCracked", "", "username", "", "randomCracked", "CrossSine"})
public final class LoginUtils
extends MinecraftInstance {
    @NotNull
    public static final LoginUtils INSTANCE = new LoginUtils();

    private LoginUtils() {
    }

    public final void loginCracked(@NotNull String username) {
        me.liuli.elixir.compat.Session it;
        CrackedAccount crackedAccount;
        Intrinsics.checkNotNullParameter(username, "username");
        CrackedAccount crackedAccount2 = crackedAccount = new CrackedAccount();
        Minecraft minecraft = MinecraftInstance.mc;
        boolean bl = false;
        ((CrackedAccount)((Object)it)).setName(username);
        it = crackedAccount.getSession();
        boolean bl2 = false;
        minecraft.field_71449_j = new Session(it.getUsername(), it.getUuid(), it.getToken(), it.getType());
        CrossSine.INSTANCE.getEventManager().callEvent(new SessionEvent());
    }

    public final void randomCracked() {
        this.loginCracked(RandomUtils.randomUsername$default(RandomUtils.INSTANCE, 0, false, 3, null));
    }
}

