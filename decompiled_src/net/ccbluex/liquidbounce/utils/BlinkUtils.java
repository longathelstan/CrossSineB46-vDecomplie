/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.utils;

import java.math.BigInteger;
import java.util.LinkedList;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.PacketUtils;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayServer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0018\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0014\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u001a\u001a\u00020\u000e2\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u001cJ&\u0010\u001d\u001a\u00020\u001e2\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\b\b\u0002\u0010\u001f\u001a\u00020\u00042\b\b\u0002\u0010 \u001a\u00020\u000eJ\u0012\u0010!\u001a\u00020\u00042\b\b\u0002\u0010\u001b\u001a\u00020\u001cH\u0002J\u0012\u0010\"\u001a\u00020\u00042\n\u0010#\u001a\u0006\u0012\u0002\b\u00030\u0015J0\u0010$\u001a\u00020\u001e2\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\b\b\u0002\u0010\u001f\u001a\u00020\u00042\b\b\u0002\u0010 \u001a\u00020\u000e2\b\b\u0002\u0010%\u001a\u00020\u000eJt\u0010&\u001a\u00020\u001e2\b\b\u0002\u0010'\u001a\u00020\u00042\b\b\u0002\u0010(\u001a\u00020\u00042\b\b\u0002\u0010)\u001a\u00020\u00042\b\b\u0002\u0010*\u001a\u00020\u00042\b\b\u0002\u0010+\u001a\u00020\u00042\b\b\u0002\u0010,\u001a\u00020\u00042\b\b\u0002\u0010-\u001a\u00020\u00042\b\b\u0002\u0010.\u001a\u00020\u00042\b\b\u0002\u0010/\u001a\u00020\u00042\b\b\u0002\u00100\u001a\u00020\u00042\b\b\u0002\u00101\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u000e\u0010\r\u001a\u00020\u000eX\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00160\u00150\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0017\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\n\"\u0004\b\u0019\u0010\f\u00a8\u00062"}, d2={"Lnet/ccbluex/liquidbounce/utils/BlinkUtils;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "abilitiesStat", "", "actionStat", "interactStat", "invStat", "keepAliveStat", "getKeepAliveStat", "()Z", "setKeepAliveStat", "(Z)V", "misMatch_Type", "", "movingPacketStat", "otherPacket", "packetToggleStat", "", "playerBuffer", "Ljava/util/LinkedList;", "Lnet/minecraft/network/Packet;", "Lnet/minecraft/network/play/INetHandlerPlayServer;", "transactionStat", "getTransactionStat", "setTransactionStat", "bufferSize", "packetType", "", "clearPacket", "", "onlySelected", "amount", "isBlacklisted", "pushPacket", "packets", "releasePacket", "minBuff", "setBlinkState", "off", "release", "all", "packetMoving", "packetTransaction", "packetKeepAlive", "packetAction", "packetAbilities", "packetInventory", "packetInteract", "other", "CrossSine"})
public final class BlinkUtils
extends MinecraftInstance {
    @NotNull
    public static final BlinkUtils INSTANCE = new BlinkUtils();
    @NotNull
    private static final LinkedList<Packet<INetHandlerPlayServer>> playerBuffer = new LinkedList();
    private static final int misMatch_Type = -302;
    private static boolean movingPacketStat;
    private static boolean transactionStat;
    private static boolean keepAliveStat;
    private static boolean actionStat;
    private static boolean abilitiesStat;
    private static boolean invStat;
    private static boolean interactStat;
    private static boolean otherPacket;
    @NotNull
    private static boolean[] packetToggleStat;

    private BlinkUtils() {
    }

    public final boolean getTransactionStat() {
        return transactionStat;
    }

    public final void setTransactionStat(boolean bl) {
        transactionStat = bl;
    }

    public final boolean getKeepAliveStat() {
        return keepAliveStat;
    }

    public final void setKeepAliveStat(boolean bl) {
        keepAliveStat = bl;
    }

    public final void releasePacket(@Nullable String packetType, boolean onlySelected, int amount, int minBuff) {
        int count = 0;
        if (packetType == null) {
            count = -1;
            for (Packet packet : playerBuffer) {
                String string = packet.getClass().getSimpleName();
                Intrinsics.checkNotNullExpressionValue(string, "packets.javaClass.simpleName");
                int n = new BigInteger(StringsKt.substring(string, new IntRange(1, 2)), 16).intValue();
                if (!packetToggleStat[n] && onlySelected) continue;
                Intrinsics.checkNotNullExpressionValue(packet, "packets");
                PacketUtils.sendPacketNoEvent((Packet<INetHandlerPlayServer>)packet);
            }
        } else {
            LinkedList<Packet> tempBuffer = new LinkedList<Packet>();
            for (Packet packet : playerBuffer) {
                String className = packet.getClass().getSimpleName();
                if (!StringsKt.equals(className, packetType, true)) continue;
                tempBuffer.add(packet);
            }
            while (tempBuffer.size() > minBuff && (count < amount || amount <= 0)) {
                Object e = tempBuffer.pop();
                Intrinsics.checkNotNullExpressionValue(e, "tempBuffer.pop()");
                PacketUtils.sendPacketNoEvent((Packet<INetHandlerPlayServer>)((Packet)e));
                int n = count;
                count = n + 1;
            }
        }
        this.clearPacket(packetType, onlySelected, count);
    }

    public static /* synthetic */ void releasePacket$default(BlinkUtils blinkUtils, String string, boolean bl, int n, int n2, int n3, Object object) {
        if ((n3 & 1) != 0) {
            string = null;
        }
        if ((n3 & 2) != 0) {
            bl = false;
        }
        if ((n3 & 4) != 0) {
            n = -1;
        }
        if ((n3 & 8) != 0) {
            n2 = 0;
        }
        blinkUtils.releasePacket(string, bl, n, n2);
    }

    public final void clearPacket(@Nullable String packetType, boolean onlySelected, int amount) {
        if (packetType == null) {
            LinkedList<Packet> tempBuffer = new LinkedList<Packet>();
            for (Packet packet : playerBuffer) {
                String string = packet.getClass().getSimpleName();
                Intrinsics.checkNotNullExpressionValue(string, "packets.javaClass.simpleName");
                int n = new BigInteger(StringsKt.substring(string, new IntRange(1, 2)), 16).intValue();
                if (packetToggleStat[n] || !onlySelected) continue;
                tempBuffer.add(packet);
            }
            playerBuffer.clear();
            for (Packet packet : tempBuffer) {
                playerBuffer.add((Packet<INetHandlerPlayServer>)packet);
            }
        } else {
            int count = 0;
            LinkedList<Packet> tempBuffer = new LinkedList<Packet>();
            for (Packet packet : playerBuffer) {
                String className = packet.getClass().getSimpleName();
                if (!StringsKt.equals(className, packetType, true)) {
                    tempBuffer.add(packet);
                    continue;
                }
                int n = count;
                if ((count = n + 1) <= amount) continue;
                tempBuffer.add(packet);
            }
            playerBuffer.clear();
            for (Packet packet : tempBuffer) {
                playerBuffer.add((Packet<INetHandlerPlayServer>)packet);
            }
        }
    }

    public static /* synthetic */ void clearPacket$default(BlinkUtils blinkUtils, String string, boolean bl, int n, int n2, Object object) {
        if ((n2 & 1) != 0) {
            string = null;
        }
        if ((n2 & 2) != 0) {
            bl = false;
        }
        if ((n2 & 4) != 0) {
            n = -1;
        }
        blinkUtils.clearPacket(string, bl, n);
    }

    public final boolean pushPacket(@NotNull Packet<?> packets) {
        Intrinsics.checkNotNullParameter(packets, "packets");
        String string = packets.getClass().getSimpleName();
        Intrinsics.checkNotNullExpressionValue(string, "packets.javaClass.simpleName");
        int packetID = new BigInteger(StringsKt.substring(string, new IntRange(1, 2)), 16).intValue();
        if (packetToggleStat[packetID]) {
            string = packets.getClass().getSimpleName();
            Intrinsics.checkNotNullExpressionValue(string, "packets.javaClass.simpleName");
            if (!this.isBlacklisted(string)) {
                playerBuffer.add(packets);
                return true;
            }
        }
        return false;
    }

    private final boolean isBlacklisted(String packetType) {
        boolean bl;
        switch (packetType) {
            case "C01PacketEncryptionResponse": 
            case "C00PacketLoginStart": 
            case "C00PacketServerQuery": 
            case "C00Handshake": 
            case "C01PacketPing": 
            case "C01PacketChatMessage": {
                bl = true;
                break;
            }
            default: {
                bl = false;
            }
        }
        return bl;
    }

    static /* synthetic */ boolean isBlacklisted$default(BlinkUtils blinkUtils, String string, int n, Object object) {
        if ((n & 1) != 0) {
            string = "";
        }
        return blinkUtils.isBlacklisted(string);
    }

    public final void setBlinkState(boolean off, boolean release, boolean all, boolean packetMoving, boolean packetTransaction, boolean packetKeepAlive, boolean packetAction, boolean packetAbilities, boolean packetInventory, boolean packetInteract, boolean other) {
        if (release) {
            BlinkUtils.releasePacket$default(this, null, false, 0, 0, 15, null);
        }
        movingPacketStat = packetMoving && !off || all;
        transactionStat = packetTransaction && !off || all;
        keepAliveStat = packetKeepAlive && !off || all;
        actionStat = packetAction && !off || all;
        abilitiesStat = packetAbilities && !off || all;
        invStat = packetInventory && !off || all;
        interactStat = packetInteract && !off || all;
        boolean bl = otherPacket = other && !off || all;
        if (all) {
            int n = 0;
            int n2 = packetToggleStat.length;
            while (n < n2) {
                int i = n++;
                BlinkUtils.packetToggleStat[i] = true;
            }
        } else {
            int n = 0;
            int n3 = packetToggleStat.length;
            while (n < n3) {
                int i = n++;
                switch (i) {
                    case 0: {
                        BlinkUtils.packetToggleStat[i] = keepAliveStat;
                        break;
                    }
                    case 1: 
                    case 17: 
                    case 18: 
                    case 20: 
                    case 21: 
                    case 23: 
                    case 24: 
                    case 25: {
                        BlinkUtils.packetToggleStat[i] = otherPacket;
                        break;
                    }
                    case 3: 
                    case 4: 
                    case 5: 
                    case 6: {
                        BlinkUtils.packetToggleStat[i] = movingPacketStat;
                        break;
                    }
                    case 15: {
                        BlinkUtils.packetToggleStat[i] = transactionStat;
                        break;
                    }
                    case 2: 
                    case 9: 
                    case 10: 
                    case 11: {
                        BlinkUtils.packetToggleStat[i] = actionStat;
                        break;
                    }
                    case 12: 
                    case 19: {
                        BlinkUtils.packetToggleStat[i] = abilitiesStat;
                        break;
                    }
                    case 13: 
                    case 14: 
                    case 16: 
                    case 22: {
                        BlinkUtils.packetToggleStat[i] = invStat;
                        break;
                    }
                    case 7: 
                    case 8: {
                        BlinkUtils.packetToggleStat[i] = interactStat;
                    }
                }
            }
        }
    }

    public static /* synthetic */ void setBlinkState$default(BlinkUtils blinkUtils, boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5, boolean bl6, boolean bl7, boolean bl8, boolean bl9, boolean bl10, boolean bl11, int n, Object object) {
        if ((n & 1) != 0) {
            bl = false;
        }
        if ((n & 2) != 0) {
            bl2 = false;
        }
        if ((n & 4) != 0) {
            bl3 = false;
        }
        if ((n & 8) != 0) {
            bl4 = movingPacketStat;
        }
        if ((n & 0x10) != 0) {
            bl5 = transactionStat;
        }
        if ((n & 0x20) != 0) {
            bl6 = keepAliveStat;
        }
        if ((n & 0x40) != 0) {
            bl7 = actionStat;
        }
        if ((n & 0x80) != 0) {
            bl8 = abilitiesStat;
        }
        if ((n & 0x100) != 0) {
            bl9 = invStat;
        }
        if ((n & 0x200) != 0) {
            bl10 = interactStat;
        }
        if ((n & 0x400) != 0) {
            bl11 = otherPacket;
        }
        blinkUtils.setBlinkState(bl, bl2, bl3, bl4, bl5, bl6, bl7, bl8, bl9, bl10, bl11);
    }

    public final int bufferSize(@Nullable String packetType) {
        int n;
        if (packetType == null) {
            n = playerBuffer.size();
        } else {
            int packetCount = 0;
            boolean flag = false;
            for (Packet packet : playerBuffer) {
                String className = packet.getClass().getSimpleName();
                if (!StringsKt.equals(className, packetType, true)) continue;
                flag = true;
                int n2 = packetCount;
                packetCount = n2 + 1;
            }
            n = flag ? packetCount : -302;
        }
        return n;
    }

    public static /* synthetic */ int bufferSize$default(BlinkUtils blinkUtils, String string, int n, Object object) {
        if ((n & 1) != 0) {
            string = null;
        }
        return blinkUtils.bufferSize(string);
    }

    static {
        boolean[] blArray = new boolean[]{false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
        packetToggleStat = blArray;
        BlinkUtils.setBlinkState$default(INSTANCE, true, true, false, false, false, false, false, false, false, false, false, 2044, null);
        BlinkUtils.clearPacket$default(INSTANCE, null, false, 0, 7, null);
    }
}

