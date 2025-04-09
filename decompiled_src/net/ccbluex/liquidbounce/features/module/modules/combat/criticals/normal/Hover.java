/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.combat.criticals.normal;

import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.module.modules.combat.criticals.CriticalMode;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\f\u001a\u00020\rH\u0016J\u0010\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/combat/criticals/normal/Hover;", "Lnet/ccbluex/liquidbounce/features/module/modules/combat/criticals/CriticalMode;", "()V", "aacLastState", "", "hoverCombat", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "hoverNoFall", "hoverValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "jState", "", "onEnable", "", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "CrossSine"})
public final class Hover
extends CriticalMode {
    @NotNull
    private final ListValue hoverValue;
    @NotNull
    private final BoolValue hoverNoFall;
    @NotNull
    private final BoolValue hoverCombat;
    private int jState;
    private boolean aacLastState;

    public Hover() {
        super("Hover");
        String[] stringArray = new String[]{"AAC4", "AAC4Other", "OldRedesky", "Normal1", "Normal2", "Minis", "Minis2", "TPCollide", "2b2t"};
        this.hoverValue = new ListValue("HoverMode", stringArray, "AAC4");
        this.hoverNoFall = new BoolValue("Hover-NoFall", true);
        this.hoverCombat = new BoolValue("Hover-OnlyCombat", true);
    }

    @Override
    public void onEnable() {
        this.jState = 0;
    }

    @Override
    public void onPacket(@NotNull PacketEvent event) {
        block77: {
            Intrinsics.checkNotNullParameter(event, "event");
            Packet<?> packet = event.getPacket();
            if (packet instanceof S08PacketPlayerPosLook && ((Boolean)this.getCritical().getS08FlagValue().get()).booleanValue()) {
                this.jState = 0;
            }
            if (!(packet instanceof C03PacketPlayer)) break block77;
            if (((Boolean)this.hoverCombat.get()).booleanValue() && !CrossSine.INSTANCE.getCombatManager().getInCombat()) {
                return;
            }
            ((C03PacketPlayer)packet).func_149469_a(true);
            String string = ((String)this.hoverValue.get()).toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            block11 : switch (string) {
                case "2b2t": {
                    if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
                        ((C03PacketPlayer)packet).field_149474_g = false;
                        int n = this.jState;
                        this.jState = n + 1;
                        switch (this.jState) {
                            case 2: {
                                ((C03PacketPlayer)packet).field_149477_b += 0.02;
                                break block11;
                            }
                            case 3: {
                                ((C03PacketPlayer)packet).field_149477_b += 0.01;
                                break block11;
                            }
                            case 4: {
                                if (((Boolean)this.hoverNoFall.get()).booleanValue()) {
                                    ((C03PacketPlayer)packet).field_149474_g = true;
                                }
                                this.jState = 1;
                                break block11;
                            }
                        }
                        this.jState = 1;
                        break;
                    }
                    this.jState = 0;
                    break;
                }
                case "minis2": {
                    if (MinecraftInstance.mc.field_71439_g.field_70122_E && !this.aacLastState) {
                        ((C03PacketPlayer)packet).field_149474_g = MinecraftInstance.mc.field_71439_g.field_70122_E;
                        this.aacLastState = MinecraftInstance.mc.field_71439_g.field_70122_E;
                        return;
                    }
                    this.aacLastState = MinecraftInstance.mc.field_71439_g.field_70122_E;
                    if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
                        ((C03PacketPlayer)packet).field_149474_g = false;
                        int n = this.jState;
                        this.jState = n + 1;
                        if (this.jState % 2 == 0) {
                            ((C03PacketPlayer)packet).field_149477_b += 0.015625;
                            break;
                        }
                        if (this.jState <= 100) break;
                        if (((Boolean)this.hoverNoFall.get()).booleanValue()) {
                            ((C03PacketPlayer)packet).field_149474_g = true;
                        }
                        this.jState = 1;
                        break;
                    }
                    this.jState = 0;
                    break;
                }
                case "tpcollide": {
                    if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
                        ((C03PacketPlayer)packet).field_149474_g = false;
                        int n = this.jState;
                        this.jState = n + 1;
                        switch (this.jState) {
                            case 2: {
                                ((C03PacketPlayer)packet).field_149477_b += 0.20000004768372;
                                break block11;
                            }
                            case 3: {
                                ((C03PacketPlayer)packet).field_149477_b += 0.12160004615784;
                                break block11;
                            }
                            case 4: {
                                if (((Boolean)this.hoverNoFall.get()).booleanValue()) {
                                    ((C03PacketPlayer)packet).field_149474_g = true;
                                }
                                this.jState = 1;
                                break block11;
                            }
                        }
                        this.jState = 1;
                        break;
                    }
                    this.jState = 0;
                    break;
                }
                case "minis": {
                    if (MinecraftInstance.mc.field_71439_g.field_70122_E && !this.aacLastState) {
                        ((C03PacketPlayer)packet).field_149474_g = MinecraftInstance.mc.field_71439_g.field_70122_E;
                        this.aacLastState = MinecraftInstance.mc.field_71439_g.field_70122_E;
                        return;
                    }
                    this.aacLastState = MinecraftInstance.mc.field_71439_g.field_70122_E;
                    if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
                        ((C03PacketPlayer)packet).field_149474_g = false;
                        int n = this.jState;
                        this.jState = n + 1;
                        if (this.jState % 2 == 0) {
                            ((C03PacketPlayer)packet).field_149477_b += 0.0625;
                            break;
                        }
                        if (this.jState <= 50) break;
                        if (((Boolean)this.hoverNoFall.get()).booleanValue()) {
                            ((C03PacketPlayer)packet).field_149474_g = true;
                        }
                        this.jState = 1;
                        break;
                    }
                    this.jState = 0;
                    break;
                }
                case "normal1": {
                    if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
                        if (!((Boolean)this.hoverNoFall.get()).booleanValue() || this.jState != 0) {
                            ((C03PacketPlayer)packet).field_149474_g = false;
                        }
                        int n = this.jState;
                        this.jState = n + 1;
                        switch (this.jState) {
                            case 2: {
                                ((C03PacketPlayer)packet).field_149477_b += 0.001335979112147;
                                break block11;
                            }
                            case 3: {
                                ((C03PacketPlayer)packet).field_149477_b += 1.31132E-8;
                                break block11;
                            }
                            case 4: {
                                ((C03PacketPlayer)packet).field_149477_b += 1.94788E-8;
                                break block11;
                            }
                            case 5: {
                                ((C03PacketPlayer)packet).field_149477_b += 1.304E-11;
                                break block11;
                            }
                            case 6: {
                                if (((Boolean)this.hoverNoFall.get()).booleanValue()) {
                                    ((C03PacketPlayer)packet).field_149474_g = true;
                                }
                                this.jState = 1;
                                break block11;
                            }
                        }
                        this.jState = 1;
                        break;
                    }
                    this.jState = 0;
                    break;
                }
                case "aac4other": {
                    if (MinecraftInstance.mc.field_71439_g.field_70122_E && !this.aacLastState && ((Boolean)this.hoverNoFall.get()).booleanValue()) {
                        ((C03PacketPlayer)packet).field_149474_g = MinecraftInstance.mc.field_71439_g.field_70122_E;
                        this.aacLastState = MinecraftInstance.mc.field_71439_g.field_70122_E;
                        ((C03PacketPlayer)packet).field_149477_b += 0.00101;
                        return;
                    }
                    this.aacLastState = MinecraftInstance.mc.field_71439_g.field_70122_E;
                    ((C03PacketPlayer)packet).field_149477_b += 0.001;
                    if (!MinecraftInstance.mc.field_71439_g.field_70122_E && ((Boolean)this.hoverNoFall.get()).booleanValue()) break;
                    ((C03PacketPlayer)packet).field_149474_g = false;
                    break;
                }
                case "aac4": {
                    if (MinecraftInstance.mc.field_71439_g.field_70122_E && !this.aacLastState && ((Boolean)this.hoverNoFall.get()).booleanValue()) {
                        ((C03PacketPlayer)packet).field_149474_g = MinecraftInstance.mc.field_71439_g.field_70122_E;
                        this.aacLastState = MinecraftInstance.mc.field_71439_g.field_70122_E;
                        ((C03PacketPlayer)packet).field_149477_b += 1.36E-13;
                        return;
                    }
                    this.aacLastState = MinecraftInstance.mc.field_71439_g.field_70122_E;
                    ((C03PacketPlayer)packet).field_149477_b += 3.6E-14;
                    if (!MinecraftInstance.mc.field_71439_g.field_70122_E && ((Boolean)this.hoverNoFall.get()).booleanValue()) break;
                    ((C03PacketPlayer)packet).field_149474_g = false;
                    break;
                }
                case "normal2": {
                    if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
                        if (!((Boolean)this.hoverNoFall.get()).booleanValue() || this.jState != 0) {
                            ((C03PacketPlayer)packet).field_149474_g = false;
                        }
                        int n = this.jState;
                        this.jState = n + 1;
                        switch (this.jState) {
                            case 2: {
                                ((C03PacketPlayer)packet).field_149477_b += 6.67547E-12;
                                break block11;
                            }
                            case 3: {
                                ((C03PacketPlayer)packet).field_149477_b += 4.5413E-13;
                                break block11;
                            }
                            case 4: {
                                ((C03PacketPlayer)packet).field_149477_b += 3.6E-14;
                                break block11;
                            }
                            case 5: {
                                if (((Boolean)this.hoverNoFall.get()).booleanValue()) {
                                    ((C03PacketPlayer)packet).field_149474_g = true;
                                }
                                this.jState = 1;
                                break block11;
                            }
                        }
                        this.jState = 1;
                        break;
                    }
                    this.jState = 0;
                    break;
                }
                case "oldredesky": {
                    if (((Boolean)this.hoverNoFall.get()).booleanValue() && MinecraftInstance.mc.field_71439_g.field_70143_R > 0.0f) {
                        ((C03PacketPlayer)packet).field_149474_g = true;
                        return;
                    }
                    if (!MinecraftInstance.mc.field_71439_g.field_70122_E) break;
                    ((C03PacketPlayer)packet).field_149474_g = false;
                }
            }
        }
    }
}

