/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.player;

import java.util.ArrayList;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.player.Scaffold;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.misc.FallingPlayer;
import net.minecraft.block.BlockAir;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(name="AntiVoid", category=ModuleCategory.PLAYER)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010,\u001a\u00020\u0005H\u0002J\b\u0010-\u001a\u00020.H\u0016J\b\u0010/\u001a\u00020.H\u0016J\u0010\u00100\u001a\u00020.2\u0006\u00101\u001a\u000202H\u0007J\u0010\u00103\u001a\u00020.2\u0006\u00101\u001a\u000204H\u0007J\u0010\u00105\u001a\u00020.2\u0006\u00101\u001a\u000206H\u0007R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u0016\u001a\u0012\u0012\u0004\u0012\u00020\u00180\u0017j\b\u0012\u0004\u0012\u00020\u0018`\u0019X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001a\u001a\u0004\u0018\u00010\u001bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00150\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\"\u001a\u00020#8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b$\u0010%R\u000e\u0010&\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020(X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010*\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010+\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u00067"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/player/AntiVoid;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "autoScaffoldValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "blink", "canBlink", "canCancel", "canSpoof", "flagged", "lastRecY", "", "maxFallDistValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "modeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "motionX", "motionY", "motionZ", "motionflagValue", "", "packetCache", "Ljava/util/ArrayList;", "Lnet/minecraft/network/play/client/C03PacketPlayer;", "Lkotlin/collections/ArrayList;", "pos", "Lnet/minecraft/util/BlockPos;", "posX", "posY", "posZ", "prediction", "resetMotionValue", "startFallDistValue", "tag", "", "getTag", "()Ljava/lang/String;", "tried", "voidOnlyValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "x", "y", "z", "checkVoid", "onDisable", "", "onEnable", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "CrossSine"})
public final class AntiVoid
extends Module {
    @NotNull
    private final ListValue modeValue;
    @NotNull
    private final FloatValue maxFallDistValue;
    @NotNull
    private final Value<Boolean> resetMotionValue;
    @NotNull
    private final Value<Float> startFallDistValue;
    @NotNull
    private final Value<Boolean> autoScaffoldValue;
    @NotNull
    private final Value<Float> motionflagValue;
    @NotNull
    private final BoolValue voidOnlyValue;
    @NotNull
    private final Value<Boolean> prediction;
    @NotNull
    private final ArrayList<C03PacketPlayer> packetCache;
    private boolean blink;
    private boolean canBlink;
    private boolean canCancel;
    private boolean canSpoof;
    private boolean tried;
    private boolean flagged;
    private double posX;
    private double posY;
    private double posZ;
    private double motionX;
    private double motionY;
    private double motionZ;
    private double lastRecY;
    private double x;
    private double y;
    private double z;
    @Nullable
    private BlockPos pos;

    public AntiVoid() {
        String[] stringArray = new String[]{"Blink", "TPBack", "MotionFlag", "PacketFlag", "GroundSpoof", "OldHypixel", "Jartex", "OldCubecraft", "Packet", "Watchdog", "Vulcan", "Freeze"};
        this.modeValue = new ListValue("Mode", stringArray, "Blink");
        this.maxFallDistValue = new FloatValue("MaxFallDistance", 3.0f, 1.0f, 20.0f);
        this.resetMotionValue = new BoolValue("ResetMotion", false).displayable(new Function0<Boolean>(this){
            final /* synthetic */ AntiVoid this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return AntiVoid.access$getModeValue$p(this.this$0).equals("Blink");
            }
        });
        this.startFallDistValue = new FloatValue("BlinkStartFallDistance", 2.0f, 0.0f, 5.0f).displayable(new Function0<Boolean>(this){
            final /* synthetic */ AntiVoid this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return AntiVoid.access$getModeValue$p(this.this$0).equals("Blink");
            }
        });
        this.autoScaffoldValue = new BoolValue("BlinkAutoScaffold", true).displayable(new Function0<Boolean>(this){
            final /* synthetic */ AntiVoid this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return AntiVoid.access$getModeValue$p(this.this$0).equals("Blink");
            }
        });
        this.motionflagValue = new FloatValue("MotionFlag-MotionY", 1.0f, 0.0f, 5.0f).displayable(new Function0<Boolean>(this){
            final /* synthetic */ AntiVoid this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return AntiVoid.access$getModeValue$p(this.this$0).equals("MotionFlag");
            }
        });
        this.voidOnlyValue = new BoolValue("OnlyVoid", true);
        this.prediction = new BoolValue("Prediction", false).displayable(new Function0<Boolean>(this){
            final /* synthetic */ AntiVoid this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return (Boolean)AntiVoid.access$getVoidOnlyValue$p(this.this$0).get();
            }
        });
        this.packetCache = new ArrayList();
    }

    @Override
    public void onEnable() {
        this.canCancel = false;
        this.pos = null;
        this.blink = false;
        this.canBlink = false;
        this.canSpoof = false;
        this.lastRecY = MinecraftInstance.mc.field_71439_g != null ? MinecraftInstance.mc.field_71439_g.field_70163_u : 0.0;
        this.tried = false;
        this.flagged = false;
        if (this.modeValue.equals("Freeze")) {
            if (MinecraftInstance.mc.field_71439_g == null) {
                return;
            }
            this.x = MinecraftInstance.mc.field_71439_g.field_70165_t;
            this.y = MinecraftInstance.mc.field_71439_g.field_70163_u;
            this.z = MinecraftInstance.mc.field_71439_g.field_70161_v;
            this.motionX = MinecraftInstance.mc.field_71439_g.field_70159_w;
            this.motionY = MinecraftInstance.mc.field_71439_g.field_70181_x;
            this.motionZ = MinecraftInstance.mc.field_71439_g.field_70179_y;
        }
    }

    @EventTarget
    public final void onWorld(@NotNull WorldEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (this.lastRecY == 0.0) {
            this.lastRecY = MinecraftInstance.mc.field_71439_g.field_70163_u;
        }
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Object object;
        Object collLoc;
        Intrinsics.checkNotNullParameter(event, "event");
        if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
            this.tried = false;
            this.flagged = false;
        }
        if (!MinecraftInstance.mc.field_71439_g.field_70122_E) {
            int n;
            EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
            Intrinsics.checkNotNullExpressionValue(entityPlayerSP, "mc.thePlayer");
            FallingPlayer fallingPlayer = new FallingPlayer((EntityPlayer)entityPlayerSP);
            collLoc = fallingPlayer.findCollision(60);
            BlockPos blockPos = collLoc;
            object = Math.abs((double)(blockPos == null ? 0 : (n = blockPos.func_177956_o())) - MinecraftInstance.mc.field_71439_g.field_70163_u) > (double)(((Number)this.maxFallDistValue.get()).floatValue() + 1.0f) ? collLoc : (BlockPos)null;
        } else {
            object = null;
        }
        this.pos = object;
        String string = ((String)this.modeValue.get()).toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        switch (string) {
            case "freeze": {
                MinecraftInstance.mc.field_71439_g.field_70159_w = 0.0;
                MinecraftInstance.mc.field_71439_g.field_70181_x = 0.0;
                MinecraftInstance.mc.field_71439_g.field_70179_y = 0.0;
                MinecraftInstance.mc.field_71439_g.func_70080_a(this.x, this.y, this.z, MinecraftInstance.mc.field_71439_g.field_70177_z, MinecraftInstance.mc.field_71439_g.field_70125_A);
                break;
            }
            case "groundspoof": {
                if (((Boolean)this.voidOnlyValue.get()).booleanValue() && !this.checkVoid()) break;
                this.canSpoof = MinecraftInstance.mc.field_71439_g.field_70143_R > ((Number)this.maxFallDistValue.get()).floatValue();
                break;
            }
            case "vulcan": {
                if (MinecraftInstance.mc.field_71439_g.field_70122_E && !(BlockUtils.getBlock(new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u - 1.0, MinecraftInstance.mc.field_71439_g.field_70161_v)) instanceof BlockAir)) {
                    this.posX = MinecraftInstance.mc.field_71439_g.field_70169_q;
                    this.posY = MinecraftInstance.mc.field_71439_g.field_70167_r;
                    this.posZ = MinecraftInstance.mc.field_71439_g.field_70166_s;
                }
                if (((Boolean)this.voidOnlyValue.get()).booleanValue() && !this.checkVoid() || !(MinecraftInstance.mc.field_71439_g.field_70143_R > ((Number)this.maxFallDistValue.get()).floatValue()) || this.tried) break;
                MinecraftInstance.mc.field_71439_g.func_70107_b(MinecraftInstance.mc.field_71439_g.field_70165_t, this.posY, MinecraftInstance.mc.field_71439_g.field_70161_v);
                MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v, false));
                MinecraftInstance.mc.field_71439_g.func_70107_b(this.posX, this.posY, this.posZ);
                MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v, true));
                MinecraftInstance.mc.field_71439_g.field_70143_R = 0.0f;
                MovementUtils.INSTANCE.resetMotion(true);
                this.tried = true;
                break;
            }
            case "motionflag": {
                if (((Boolean)this.voidOnlyValue.get()).booleanValue() && !this.checkVoid() || !(MinecraftInstance.mc.field_71439_g.field_70143_R > ((Number)this.maxFallDistValue.get()).floatValue()) || this.tried) break;
                collLoc = MinecraftInstance.mc.field_71439_g;
                ((EntityPlayerSP)collLoc).field_70181_x += ((Number)this.motionflagValue.get()).doubleValue();
                MinecraftInstance.mc.field_71439_g.field_70143_R = 0.0f;
                this.tried = true;
                break;
            }
            case "packetflag": {
                if (((Boolean)this.voidOnlyValue.get()).booleanValue() && !this.checkVoid() || !(MinecraftInstance.mc.field_71439_g.field_70143_R > ((Number)this.maxFallDistValue.get()).floatValue()) || this.tried) break;
                MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t + 1.0, MinecraftInstance.mc.field_71439_g.field_70163_u + 1.0, MinecraftInstance.mc.field_71439_g.field_70161_v + 1.0, false));
                this.tried = true;
                break;
            }
            case "tpback": {
                if (MinecraftInstance.mc.field_71439_g.field_70122_E && !(BlockUtils.getBlock(new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u - 1.0, MinecraftInstance.mc.field_71439_g.field_70161_v)) instanceof BlockAir)) {
                    this.posX = MinecraftInstance.mc.field_71439_g.field_70169_q;
                    this.posY = MinecraftInstance.mc.field_71439_g.field_70167_r;
                    this.posZ = MinecraftInstance.mc.field_71439_g.field_70166_s;
                }
                if (((Boolean)this.voidOnlyValue.get()).booleanValue() && !this.checkVoid() || !(MinecraftInstance.mc.field_71439_g.field_70143_R > ((Number)this.maxFallDistValue.get()).floatValue()) || this.tried) break;
                MinecraftInstance.mc.field_71439_g.func_70634_a(this.posX, this.posY, this.posZ);
                MinecraftInstance.mc.field_71439_g.field_70143_R = 0.0f;
                MinecraftInstance.mc.field_71439_g.field_70159_w = 0.0;
                MinecraftInstance.mc.field_71439_g.field_70181_x = 0.0;
                MinecraftInstance.mc.field_71439_g.field_70179_y = 0.0;
                this.tried = true;
                break;
            }
            case "jartex": {
                this.canSpoof = false;
                if ((!((Boolean)this.voidOnlyValue.get()).booleanValue() || this.checkVoid()) && MinecraftInstance.mc.field_71439_g.field_70143_R > ((Number)this.maxFallDistValue.get()).floatValue() && MinecraftInstance.mc.field_71439_g.field_70163_u < this.lastRecY + 0.01 && MinecraftInstance.mc.field_71439_g.field_70181_x <= 0.0 && !MinecraftInstance.mc.field_71439_g.field_70122_E && !this.flagged) {
                    MinecraftInstance.mc.field_71439_g.field_70181_x = 0.0;
                    collLoc = MinecraftInstance.mc.field_71439_g;
                    ((EntityPlayerSP)collLoc).field_70179_y *= 0.838;
                    collLoc = MinecraftInstance.mc.field_71439_g;
                    ((EntityPlayerSP)collLoc).field_70159_w *= 0.838;
                    this.canSpoof = true;
                }
                this.lastRecY = MinecraftInstance.mc.field_71439_g.field_70163_u;
                break;
            }
            case "oldcubecraft": {
                this.canSpoof = false;
                if ((!((Boolean)this.voidOnlyValue.get()).booleanValue() || this.checkVoid()) && MinecraftInstance.mc.field_71439_g.field_70143_R > ((Number)this.maxFallDistValue.get()).floatValue() && MinecraftInstance.mc.field_71439_g.field_70163_u < this.lastRecY + 0.01 && MinecraftInstance.mc.field_71439_g.field_70181_x <= 0.0 && !MinecraftInstance.mc.field_71439_g.field_70122_E && !this.flagged) {
                    MinecraftInstance.mc.field_71439_g.field_70181_x = 0.0;
                    MinecraftInstance.mc.field_71439_g.field_70179_y = 0.0;
                    MinecraftInstance.mc.field_71439_g.field_70159_w = 0.0;
                    MinecraftInstance.mc.field_71439_g.field_70747_aH = 0.0f;
                    this.canSpoof = true;
                    if (!this.tried) {
                        this.tried = true;
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(MinecraftInstance.mc.field_71439_g.field_70165_t, 32000.0, MinecraftInstance.mc.field_71439_g.field_70161_v, false));
                    }
                }
                this.lastRecY = MinecraftInstance.mc.field_71439_g.field_70163_u;
                break;
            }
            case "packet": {
                if (this.checkVoid()) {
                    this.canCancel = true;
                }
                if (!this.canCancel) break;
                if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
                    for (C03PacketPlayer packet : this.packetCache) {
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)packet);
                    }
                    this.packetCache.clear();
                }
                this.canCancel = false;
                break;
            }
            case "blink": {
                if (!this.blink) {
                    BlockPos collide = new FallingPlayer(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v, 0.0, 0.0, 0.0, 0.0f, 0.0f, 0.0f, 0.0f).findCollision(60);
                    if (this.canBlink && (collide == null || MinecraftInstance.mc.field_71439_g.field_70163_u - (double)collide.func_177956_o() > (double)((Number)this.startFallDistValue.get()).floatValue())) {
                        this.posX = MinecraftInstance.mc.field_71439_g.field_70165_t;
                        this.posY = MinecraftInstance.mc.field_71439_g.field_70163_u;
                        this.posZ = MinecraftInstance.mc.field_71439_g.field_70161_v;
                        this.motionX = MinecraftInstance.mc.field_71439_g.field_70159_w;
                        this.motionY = MinecraftInstance.mc.field_71439_g.field_70181_x;
                        this.motionZ = MinecraftInstance.mc.field_71439_g.field_70179_y;
                        this.packetCache.clear();
                        this.blink = true;
                    }
                    if (!MinecraftInstance.mc.field_71439_g.field_70122_E) break;
                    this.canBlink = true;
                    break;
                }
                if (MinecraftInstance.mc.field_71439_g.field_70143_R > ((Number)this.maxFallDistValue.get()).floatValue()) {
                    MinecraftInstance.mc.field_71439_g.func_70634_a(this.posX, this.posY, this.posZ);
                    if (this.resetMotionValue.get().booleanValue()) {
                        MinecraftInstance.mc.field_71439_g.field_70159_w = 0.0;
                        MinecraftInstance.mc.field_71439_g.field_70181_x = 0.0;
                        MinecraftInstance.mc.field_71439_g.field_70179_y = 0.0;
                        MinecraftInstance.mc.field_71439_g.field_70747_aH = 0.0f;
                    } else {
                        MinecraftInstance.mc.field_71439_g.field_70159_w = this.motionX;
                        MinecraftInstance.mc.field_71439_g.field_70181_x = this.motionY;
                        MinecraftInstance.mc.field_71439_g.field_70179_y = this.motionZ;
                        MinecraftInstance.mc.field_71439_g.field_70747_aH = 0.0f;
                    }
                    if (this.autoScaffoldValue.get().booleanValue()) {
                        Scaffold scaffold = CrossSine.INSTANCE.getModuleManager().get(Scaffold.class);
                        Intrinsics.checkNotNull(scaffold);
                        scaffold.setState(true);
                    }
                    this.packetCache.clear();
                    this.blink = false;
                    this.canBlink = false;
                    break;
                }
                if (!MinecraftInstance.mc.field_71439_g.field_70122_E) break;
                this.blink = false;
                for (C03PacketPlayer packet : this.packetCache) {
                    MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)packet);
                }
                break;
            }
        }
    }

    private final boolean checkVoid() {
        if (this.prediction.get().booleanValue() && MinecraftInstance.mc.field_71439_g.field_70143_R >= ((Number)this.maxFallDistValue.get()).floatValue() && this.pos == null) {
            return false;
        }
        int i = (int)(-(MinecraftInstance.mc.field_71439_g.field_70163_u - 1.4857625));
        boolean dangerous = true;
        while (i <= 0) {
            dangerous = MinecraftInstance.mc.field_71441_e.func_147461_a(MinecraftInstance.mc.field_71439_g.func_174813_aQ().func_72317_d(MinecraftInstance.mc.field_71439_g.field_70159_w * 0.5, (double)i, MinecraftInstance.mc.field_71439_g.field_70179_y * 0.5)).isEmpty();
            int n = i;
            i = n + 1;
            if (dangerous) continue;
            break;
        }
        return dangerous;
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Packet<?> packet = event.getPacket();
        String string = ((String)this.modeValue.get()).toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        switch (string) {
            case "blink": {
                if (!this.blink || !(packet instanceof C03PacketPlayer)) break;
                this.packetCache.add((C03PacketPlayer)packet);
                event.cancelEvent();
                break;
            }
            case "packet": {
                if (this.canCancel && packet instanceof C03PacketPlayer) {
                    this.packetCache.add((C03PacketPlayer)packet);
                    event.cancelEvent();
                }
                if (!(packet instanceof S08PacketPlayerPosLook)) break;
                this.packetCache.clear();
                this.canCancel = false;
                break;
            }
            case "groundspoof": {
                if (!this.canSpoof || !(packet instanceof C03PacketPlayer)) break;
                ((C03PacketPlayer)packet).field_149474_g = true;
                break;
            }
            case "jartex": {
                if (this.canSpoof && packet instanceof C03PacketPlayer) {
                    ((C03PacketPlayer)packet).field_149474_g = true;
                }
                if (!this.canSpoof || !(packet instanceof S08PacketPlayerPosLook)) break;
                this.flagged = true;
                break;
            }
            case "oldcubecraft": {
                if (this.canSpoof && packet instanceof C03PacketPlayer && ((C03PacketPlayer)packet).field_149477_b < 1145.14191981) {
                    event.cancelEvent();
                }
                if (!this.canSpoof || !(packet instanceof S08PacketPlayerPosLook)) break;
                this.flagged = true;
                break;
            }
            case "oldhypixel": {
                if (packet instanceof S08PacketPlayerPosLook && (double)MinecraftInstance.mc.field_71439_g.field_70143_R > 3.125) {
                    MinecraftInstance.mc.field_71439_g.field_70143_R = 3.125f;
                }
                if (!(packet instanceof C03PacketPlayer)) break;
                if (((Boolean)this.voidOnlyValue.get()).booleanValue() && MinecraftInstance.mc.field_71439_g.field_70143_R >= ((Number)this.maxFallDistValue.get()).floatValue() && MinecraftInstance.mc.field_71439_g.field_70181_x <= 0.0 && this.checkVoid()) {
                    ((C03PacketPlayer)packet).field_149477_b += 11.0;
                }
                if (((Boolean)this.voidOnlyValue.get()).booleanValue() || !(MinecraftInstance.mc.field_71439_g.field_70143_R >= ((Number)this.maxFallDistValue.get()).floatValue())) break;
                ((C03PacketPlayer)packet).field_149477_b += 11.0;
                break;
            }
            case "freeze": {
                if (event.getPacket() instanceof C03PacketPlayer) {
                    event.cancelEvent();
                }
                if (!(event.getPacket() instanceof S08PacketPlayerPosLook)) break;
                this.x = ((S08PacketPlayerPosLook)event.getPacket()).field_148940_a;
                this.y = ((S08PacketPlayerPosLook)event.getPacket()).field_148938_b;
                this.z = ((S08PacketPlayerPosLook)event.getPacket()).field_148939_c;
                this.motionX = 0.0;
                this.motionY = 0.0;
                this.motionZ = 0.0;
            }
        }
    }

    @Override
    public void onDisable() {
        if (this.modeValue.equals("Freeze")) {
            MinecraftInstance.mc.field_71439_g.field_70159_w = this.motionX;
            MinecraftInstance.mc.field_71439_g.field_70181_x = this.motionY;
            MinecraftInstance.mc.field_71439_g.field_70179_y = this.motionZ;
            MinecraftInstance.mc.field_71439_g.func_70080_a(this.x, this.y, this.z, MinecraftInstance.mc.field_71439_g.field_70177_z, MinecraftInstance.mc.field_71439_g.field_70125_A);
        }
    }

    @Override
    @NotNull
    public String getTag() {
        return (String)this.modeValue.get();
    }

    public static final /* synthetic */ ListValue access$getModeValue$p(AntiVoid $this) {
        return $this.modeValue;
    }

    public static final /* synthetic */ BoolValue access$getVoidOnlyValue$p(AntiVoid $this) {
        return $this.voidOnlyValue;
    }
}

