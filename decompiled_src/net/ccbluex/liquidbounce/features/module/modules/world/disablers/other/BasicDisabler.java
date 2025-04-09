/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.world.disablers.other;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.module.modules.world.disablers.DisablerMode;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/world/disablers/other/BasicDisabler;", "Lnet/ccbluex/liquidbounce/features/module/modules/world/disablers/DisablerMode;", "()V", "c03NoMoveValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "cancelC00Value", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "cancelC03Value", "cancelC0AValue", "cancelC0BValue", "cancelC0FValue", "onPacket", "", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "CrossSine"})
public final class BasicDisabler
extends DisablerMode {
    @NotNull
    private final BoolValue cancelC00Value = new BoolValue(Intrinsics.stringPlus(this.getValuePrefix(), "CancelC00"), true);
    @NotNull
    private final BoolValue cancelC0FValue = new BoolValue(Intrinsics.stringPlus(this.getValuePrefix(), "CancelC0F"), true);
    @NotNull
    private final BoolValue cancelC0AValue = new BoolValue(Intrinsics.stringPlus(this.getValuePrefix(), "CancelC0A"), true);
    @NotNull
    private final BoolValue cancelC0BValue = new BoolValue(Intrinsics.stringPlus(this.getValuePrefix(), "CancelC0B"), true);
    @NotNull
    private final BoolValue cancelC03Value = new BoolValue(Intrinsics.stringPlus(this.getValuePrefix(), "CancelC03"), true);
    @NotNull
    private final Value<Boolean> c03NoMoveValue = new BoolValue(Intrinsics.stringPlus(this.getValuePrefix(), "C03-NoMove"), true).displayable(new Function0<Boolean>(this){
        final /* synthetic */ BasicDisabler this$0;
        {
            this.this$0 = $receiver;
            super(0);
        }

        @NotNull
        public final Boolean invoke() {
            return (Boolean)BasicDisabler.access$getCancelC03Value$p(this.this$0).get();
        }
    });

    public BasicDisabler() {
        super("Basic");
    }

    @Override
    public void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Packet<?> packet = event.getPacket();
        if (packet instanceof C0FPacketConfirmTransaction && ((Boolean)this.cancelC0FValue.get()).booleanValue()) {
            event.cancelEvent();
            this.getDisabler().debugMessage("Cancel C0F-Transaction");
        }
        if (packet instanceof C00PacketKeepAlive && ((Boolean)this.cancelC00Value.get()).booleanValue()) {
            event.cancelEvent();
            this.getDisabler().debugMessage("Cancel C00-KeepAlive");
        }
        if (packet instanceof C0APacketAnimation && ((Boolean)this.cancelC0AValue.get()).booleanValue()) {
            event.cancelEvent();
            this.getDisabler().debugMessage("Cancel C0A-Swing");
        }
        if (packet instanceof C0BPacketEntityAction && ((Boolean)this.cancelC0BValue.get()).booleanValue()) {
            event.cancelEvent();
            this.getDisabler().debugMessage("Cancel C0B-Action");
        }
        if (packet instanceof C03PacketPlayer && !(packet instanceof C03PacketPlayer.C04PacketPlayerPosition) && !(packet instanceof C03PacketPlayer.C05PacketPlayerLook) && !(packet instanceof C03PacketPlayer.C06PacketPlayerPosLook) && ((Boolean)this.cancelC03Value.get()).booleanValue()) {
            if (this.c03NoMoveValue.get().booleanValue() && MovementUtils.INSTANCE.isMoving()) {
                return;
            }
            event.cancelEvent();
            this.getDisabler().debugMessage("Cancel C03-Flying");
        }
    }

    public static final /* synthetic */ BoolValue access$getCancelC03Value$p(BasicDisabler $this) {
        return $this.cancelC03Value;
    }
}

