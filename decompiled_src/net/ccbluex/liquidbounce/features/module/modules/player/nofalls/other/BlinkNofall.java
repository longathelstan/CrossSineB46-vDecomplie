/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.player.nofalls.other;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.features.module.modules.combat.SilentAura;
import net.ccbluex.liquidbounce.features.module.modules.combat.Velocity;
import net.ccbluex.liquidbounce.features.module.modules.player.Scaffold;
import net.ccbluex.liquidbounce.features.module.modules.player.nofalls.NoFallMode;
import net.ccbluex.liquidbounce.features.module.modules.world.BedAura;
import net.ccbluex.liquidbounce.ui.client.gui.colortheme.ClientTheme;
import net.ccbluex.liquidbounce.utils.BlinkUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.PlayerUtils;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.extensions.RendererExtensionKt;
import net.minecraft.block.BlockAir;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\fH\u0002J\b\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u000eH\u0016J\u0010\u0010\u0010\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0010\u0010\u0013\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u0014H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/player/nofalls/other/BlinkNofall;", "Lnet/ccbluex/liquidbounce/features/module/modules/player/nofalls/NoFallMode;", "()V", "bdaState", "", "disable", "kaState", "laState", "start", "veloState", "getBP", "pos", "", "onDisable", "", "onEnable", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onRender2D", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "CrossSine"})
public final class BlinkNofall
extends NoFallMode {
    private boolean start;
    private boolean disable;
    private boolean kaState;
    private boolean laState;
    private boolean bdaState;
    private boolean veloState;

    public BlinkNofall() {
        super("Blink");
    }

    @Override
    public void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (PlayerUtils.INSTANCE.isOnEdge() && this.getBP(1) && this.getBP(2) && this.getBP(3) && !this.start && !Scaffold.INSTANCE.getState()) {
            this.start = true;
            if (KillAura.INSTANCE.getState()) {
                this.kaState = true;
                KillAura.INSTANCE.setState(false);
            }
            if (BedAura.INSTANCE.getState()) {
                this.bdaState = true;
                BedAura.INSTANCE.setState(false);
            }
            if (SilentAura.INSTANCE.getState()) {
                this.laState = true;
                SilentAura.INSTANCE.setState(false);
            }
            if (Velocity.INSTANCE.getState()) {
                this.veloState = true;
                Velocity.INSTANCE.setState(false);
            }
        }
        if (this.start) {
            BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, false, false, true, false, false, false, false, false, false, false, false, 2043, null);
            if (event.getPacket() instanceof C03PacketPlayer) {
                ((C03PacketPlayer)event.getPacket()).field_149474_g = true;
            }
            if ((double)MinecraftInstance.mc.field_71439_g.field_70143_R > 0.2) {
                this.disable = true;
            }
        } else {
            BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, true, true, false, false, false, false, false, false, false, false, false, 2044, null);
        }
        if (MinecraftInstance.mc.field_71439_g.field_70122_E && this.disable) {
            this.start = false;
            this.disable = false;
            if (this.kaState) {
                KillAura.INSTANCE.setState(true);
                this.kaState = false;
            }
            if (this.bdaState) {
                BedAura.INSTANCE.setState(true);
                this.bdaState = false;
            }
            if (this.laState) {
                SilentAura.INSTANCE.setState(true);
                this.laState = false;
            }
            if (this.veloState) {
                Velocity.INSTANCE.setState(true);
                this.veloState = false;
            }
        }
    }

    @Override
    public void onRender2D(@NotNull Render2DEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.start) {
            String string = Intrinsics.stringPlus("Blinking : ", BlinkUtils.bufferSize$default(BlinkUtils.INSTANCE, null, 1, null));
            FontRenderer fontRenderer = MinecraftInstance.mc.field_71466_p;
            Intrinsics.checkNotNullExpressionValue(fontRenderer, "mc.fontRendererObj");
            RendererExtensionKt.drawCenteredString(fontRenderer, string, (float)new ScaledResolution(MinecraftInstance.mc).func_78326_a() / 2.0f, (float)new ScaledResolution(MinecraftInstance.mc).func_78328_b() / 2.0f + 10.0f, ClientTheme.getColor$default(ClientTheme.INSTANCE, 1, false, 2, null).getRGB(), true);
        }
    }

    @Override
    public void onDisable() {
        if (this.start) {
            this.start = false;
            if (this.kaState) {
                KillAura.INSTANCE.setState(true);
                this.kaState = false;
            }
            if (this.bdaState) {
                BedAura.INSTANCE.setState(true);
                this.bdaState = false;
            }
            if (this.laState) {
                SilentAura.INSTANCE.setState(true);
                this.laState = false;
            }
            if (this.veloState) {
                Velocity.INSTANCE.setState(true);
                this.veloState = false;
            }
        }
        this.disable = false;
        BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, true, true, false, false, false, false, false, false, false, false, false, 2044, null);
    }

    @Override
    public void onEnable() {
        this.start = false;
        this.disable = false;
    }

    private final boolean getBP(int pos) {
        return BlockUtils.getBlock(new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u - (double)pos, MinecraftInstance.mc.field_71439_g.field_70161_v)) instanceof BlockAir;
    }
}

