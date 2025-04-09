/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.file.configs;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.file.FileConfig;
import org.jetbrains.annotations.NotNull;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001:\u0001\u001bB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001a\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u00122\b\b\u0002\u0010\u0013\u001a\u00020\u0012H\u0007J\u0006\u0010\u0014\u001a\u00020\u0015J\u000e\u0010\u0016\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u0012J\u0010\u0010\u0017\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u0012H\u0016J\u000e\u0010\u0019\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u0012J\b\u0010\u001a\u001a\u00020\u0012H\u0016R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006\u001c"}, d2={"Lnet/ccbluex/liquidbounce/file/configs/FriendsConfig;", "Lnet/ccbluex/liquidbounce/file/FileConfig;", "file", "Ljava/io/File;", "(Ljava/io/File;)V", "aura", "", "getAura", "()Z", "setAura", "(Z)V", "friends", "", "Lnet/ccbluex/liquidbounce/file/configs/FriendsConfig$Friend;", "getFriends", "()Ljava/util/List;", "addFriend", "playerName", "", "alias", "clearFriends", "", "isFriend", "loadConfig", "config", "removeFriend", "saveConfig", "Friend", "CrossSine"})
public final class FriendsConfig
extends FileConfig {
    @NotNull
    private final List<Friend> friends;
    private boolean aura;

    public FriendsConfig(@NotNull File file) {
        Intrinsics.checkNotNullParameter(file, "file");
        super(file);
        this.friends = new ArrayList();
    }

    @NotNull
    public final List<Friend> getFriends() {
        return this.friends;
    }

    public final boolean getAura() {
        return this.aura;
    }

    public final void setAura(boolean bl) {
        this.aura = bl;
    }

    @Override
    public void loadConfig(@NotNull String config) {
        Intrinsics.checkNotNullParameter(config, "config");
        this.clearFriends();
        String[] stringArray = new String[]{"\n"};
        Iterable $this$forEach$iv = StringsKt.split$default((CharSequence)config, stringArray, false, 0, 6, null);
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            String line = (String)element$iv;
            boolean bl = false;
            if (StringsKt.contains$default((CharSequence)line, ":", false, 2, null)) {
                String[] data;
                String[] stringArray2 = new String[]{":"};
                Collection $this$toTypedArray$iv = StringsKt.split$default((CharSequence)line, stringArray2, false, 0, 6, null);
                boolean $i$f$toTypedArray = false;
                Collection thisCollection$iv = $this$toTypedArray$iv;
                if (thisCollection$iv.toArray(new String[0]) == null) {
                    throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
                }
                this.addFriend(data[0], data[1]);
                continue;
            }
            FriendsConfig.addFriend$default(this, line, null, 2, null);
        }
    }

    @Override
    @NotNull
    public String saveConfig() {
        StringBuilder builder = new StringBuilder();
        for (Friend friend : this.friends) {
            builder.append(friend.getPlayerName()).append(":").append(friend.getAlias()).append("\n");
        }
        String string = builder.toString();
        Intrinsics.checkNotNullExpressionValue(string, "builder.toString()");
        return string;
    }

    @JvmOverloads
    public final boolean addFriend(@NotNull String playerName, @NotNull String alias) {
        Intrinsics.checkNotNullParameter(playerName, "playerName");
        Intrinsics.checkNotNullParameter(alias, "alias");
        if (this.isFriend(playerName)) {
            return false;
        }
        this.friends.add(new Friend(playerName, alias));
        return true;
    }

    public static /* synthetic */ boolean addFriend$default(FriendsConfig friendsConfig, String string, String string2, int n, Object object) {
        if ((n & 2) != 0) {
            string2 = string;
        }
        return friendsConfig.addFriend(string, string2);
    }

    public final boolean removeFriend(@NotNull String playerName) {
        Intrinsics.checkNotNullParameter(playerName, "playerName");
        if (!this.isFriend(playerName)) {
            return false;
        }
        this.friends.removeIf(arg_0 -> FriendsConfig.removeFriend$lambda-1(playerName, arg_0));
        return true;
    }

    public final boolean isFriend(@NotNull String playerName) {
        Intrinsics.checkNotNullParameter(playerName, "playerName");
        for (Friend friend : this.friends) {
            if (!Intrinsics.areEqual(friend.getPlayerName(), playerName)) continue;
            return true;
        }
        return false;
    }

    public final void clearFriends() {
        this.friends.clear();
    }

    @JvmOverloads
    public final boolean addFriend(@NotNull String playerName) {
        Intrinsics.checkNotNullParameter(playerName, "playerName");
        return FriendsConfig.addFriend$default(this, playerName, null, 2, null);
    }

    private static final boolean removeFriend$lambda-1(String $playerName, Friend friend) {
        Intrinsics.checkNotNullParameter($playerName, "$playerName");
        Intrinsics.checkNotNullParameter(friend, "friend");
        return Intrinsics.areEqual(friend.getPlayerName(), $playerName);
    }

    @Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007\u00a8\u0006\t"}, d2={"Lnet/ccbluex/liquidbounce/file/configs/FriendsConfig$Friend;", "", "playerName", "", "alias", "(Ljava/lang/String;Ljava/lang/String;)V", "getAlias", "()Ljava/lang/String;", "getPlayerName", "CrossSine"})
    public static final class Friend {
        @NotNull
        private final String playerName;
        @NotNull
        private final String alias;

        public Friend(@NotNull String playerName, @NotNull String alias) {
            Intrinsics.checkNotNullParameter(playerName, "playerName");
            Intrinsics.checkNotNullParameter(alias, "alias");
            this.playerName = playerName;
            this.alias = alias;
        }

        @NotNull
        public final String getPlayerName() {
            return this.playerName;
        }

        @NotNull
        public final String getAlias() {
            return this.alias;
        }
    }
}

