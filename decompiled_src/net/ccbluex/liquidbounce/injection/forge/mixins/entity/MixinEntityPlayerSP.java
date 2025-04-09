/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.injection.forge.mixins.entity;

import java.util.List;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PostUpdateEvent;
import net.ccbluex.liquidbounce.event.PreUpdateEvent;
import net.ccbluex.liquidbounce.event.PushOutEvent;
import net.ccbluex.liquidbounce.event.SlowDownEvent;
import net.ccbluex.liquidbounce.event.SprintEvent;
import net.ccbluex.liquidbounce.event.StepEvent;
import net.ccbluex.liquidbounce.event.SwingEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.features.module.modules.movement.Inventory;
import net.ccbluex.liquidbounce.features.module.modules.movement.MovementFix;
import net.ccbluex.liquidbounce.features.module.modules.movement.NoSlow;
import net.ccbluex.liquidbounce.features.module.modules.movement.Sprint;
import net.ccbluex.liquidbounce.injection.forge.mixins.entity.MixinAbstractClientPlayer;
import net.ccbluex.liquidbounce.protocol.api.ProtocolFixer;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.PlayerUtils;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInput;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={EntityPlayerSP.class})
public abstract class MixinEntityPlayerSP
extends MixinAbstractClientPlayer {
    @Shadow
    private boolean field_175170_bN;
    @Shadow
    public boolean field_175171_bO;
    @Shadow
    public int field_71157_e;
    @Shadow
    protected int field_71156_d;
    @Shadow
    public float field_71086_bY;
    @Shadow
    public float field_71080_cy;
    @Shadow
    protected Minecraft field_71159_c;
    @Shadow
    public MovementInput field_71158_b;
    @Shadow
    public float field_110321_bQ;
    @Shadow
    public int field_110320_a;
    @Unique
    private boolean prevOnGround;
    @Shadow
    @Final
    public NetHandlerPlayClient field_71174_a;
    @Shadow
    private double field_175172_bI;
    @Shadow
    private int field_175168_bP;
    @Shadow
    private double field_175166_bJ;
    @Shadow
    private double field_175167_bK;
    @Shadow
    private float field_175164_bL;
    @Shadow
    private float field_175165_bM;
    private boolean debug_AttemptSprint = false;
    @Unique
    private boolean lastOnGround;

    @Shadow
    public abstract void func_85030_a(String var1, float var2, float var3);

    @Override
    @Shadow
    public abstract void func_70031_b(boolean var1);

    @Shadow
    protected abstract boolean func_145771_j(double var1, double var3, double var5);

    @Shadow
    public abstract void func_71016_p();

    @Shadow
    protected abstract void func_110318_g();

    @Shadow
    public abstract boolean func_110317_t();

    @Override
    @Shadow
    public abstract boolean func_70093_af();

    @Shadow
    protected abstract boolean func_175160_A();

    @Overwrite
    public void func_175161_p() {
        try {
            MotionEvent event = new MotionEvent(this.field_70165_t, this.func_174813_aQ().field_72338_b, this.field_70161_v, this.field_70177_z, this.field_70125_A, this.field_70122_E, this.func_70051_ag(), this.func_70093_af());
            CrossSine.eventManager.callEvent(event);
            boolean sprinting = event.getSprint();
            boolean sneaking = event.getSneak();
            if (sprinting != this.field_175171_bO) {
                if (sprinting) {
                    this.field_71174_a.func_147297_a((Packet)new C0BPacketEntityAction((Entity)((EntityPlayerSP)this), C0BPacketEntityAction.Action.START_SPRINTING));
                } else {
                    this.field_71174_a.func_147297_a((Packet)new C0BPacketEntityAction((Entity)((EntityPlayerSP)this), C0BPacketEntityAction.Action.STOP_SPRINTING));
                }
                this.field_175171_bO = sprinting;
            }
            if (sneaking != this.field_175170_bN) {
                if (sneaking) {
                    this.field_71174_a.func_147297_a((Packet)new C0BPacketEntityAction((Entity)((EntityPlayerSP)this), C0BPacketEntityAction.Action.START_SNEAKING));
                } else {
                    this.field_71174_a.func_147297_a((Packet)new C0BPacketEntityAction((Entity)((EntityPlayerSP)this), C0BPacketEntityAction.Action.STOP_SNEAKING));
                }
                this.field_175170_bN = sneaking;
            }
            if (event.getOnGround()) {
                PlayerUtils.setGroundTicks(PlayerUtils.getGroundTicks() + 1);
                PlayerUtils.setLastGroundTicks(PlayerUtils.getGroundTicks());
                PlayerUtils.setOffGroundTicks(0);
            } else {
                PlayerUtils.setOffGroundTicks(PlayerUtils.getOffGroundTicks() + 1);
                PlayerUtils.setLastOffGroundTicks(PlayerUtils.getOffGroundTicks());
                PlayerUtils.setGroundTicks(0);
            }
            PlayerUtils.setSinceTeleportTicks(PlayerUtils.getSinceTeleportTicks() + 1);
            if (!MovementFix.INSTANCE.getState() && MovementFix.INSTANCE.getTick() > 0) {
                MovementFix.INSTANCE.setTick(MovementFix.INSTANCE.getTick() - 1);
            }
            if (!MovementFix.INSTANCE.getState() && MovementFix.INSTANCE.getTick() == 0) {
                MovementFix.INSTANCE.setDoFix(false);
            }
            if (this.func_175160_A()) {
                boolean rotated;
                float yaw = event.getYaw();
                float pitch = event.getPitch();
                float lastReportedYaw = RotationUtils.serverRotation.getYaw();
                float lastReportedPitch = RotationUtils.serverRotation.getPitch();
                if (RotationUtils.targetRotation != null) {
                    yaw = RotationUtils.targetRotation.getYaw();
                    pitch = RotationUtils.targetRotation.getPitch();
                }
                double xDiff = event.getX() - this.field_175172_bI;
                double yDiff = event.getY() - this.field_175166_bJ;
                double zDiff = event.getZ() - this.field_175167_bK;
                double yawDiff = yaw - lastReportedYaw;
                double pitchDiff = pitch - lastReportedPitch;
                boolean moved = xDiff * xDiff + yDiff * yDiff + zDiff * zDiff > 0.0 || this.field_175168_bP >= 20;
                boolean bl = rotated = yawDiff != 0.0 || pitchDiff != 0.0;
                if (this.field_70154_o == null) {
                    if (moved && rotated) {
                        this.field_71174_a.func_147297_a((Packet)new C03PacketPlayer.C06PacketPlayerPosLook(event.getX(), event.getY(), event.getZ(), yaw, pitch, event.getOnGround()));
                    } else if (moved) {
                        this.field_71174_a.func_147297_a((Packet)new C03PacketPlayer.C04PacketPlayerPosition(event.getX(), event.getY(), event.getZ(), event.getOnGround()));
                    } else if (rotated) {
                        this.field_71174_a.func_147297_a((Packet)new C03PacketPlayer.C05PacketPlayerLook(yaw, pitch, event.getOnGround()));
                    } else {
                        this.field_71174_a.func_147297_a((Packet)new C03PacketPlayer(event.getOnGround()));
                    }
                } else {
                    this.field_71174_a.func_147297_a((Packet)new C03PacketPlayer.C06PacketPlayerPosLook(this.field_70159_w, -999.0, this.field_70179_y, yaw, pitch, event.getOnGround()));
                    moved = false;
                }
                ++this.field_175168_bP;
                if (moved) {
                    this.field_175172_bI = event.getX();
                    this.field_175166_bJ = event.getY();
                    this.field_175167_bK = event.getZ();
                    this.field_175168_bP = 0;
                }
                if (rotated) {
                    this.field_175164_bL = yaw;
                    this.field_175165_bM = pitch;
                }
            }
            if (this.func_175160_A()) {
                this.lastOnGround = event.getOnGround();
            }
            event.setEventState(EventState.POST);
            CrossSine.eventManager.callEvent(event);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Inject(method={"onUpdate"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/entity/AbstractClientPlayer;onUpdate()V", shift=At.Shift.BEFORE, ordinal=0)}, cancellable=true)
    private void preTickEvent(CallbackInfo ci) {
        PreUpdateEvent preUpdateEvent = new PreUpdateEvent();
        CrossSine.eventManager.callEvent(preUpdateEvent);
        if (preUpdateEvent.isCancelled()) {
            ci.cancel();
        }
    }

    @Inject(method={"onUpdate"}, at={@At(value="RETURN")})
    public void onPostUpdate(CallbackInfo ci) {
        if (this.field_70170_p.func_175667_e(new BlockPos(this.field_70165_t, 0.0, this.field_70161_v))) {
            CrossSine.eventManager.callEvent(new PostUpdateEvent());
        }
    }

    @Inject(method={"pushOutOfBlocks"}, at={@At(value="HEAD")}, cancellable=true)
    private void onPushOutOfBlocks(CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        PushOutEvent event = new PushOutEvent();
        if (this.field_70145_X) {
            event.cancelEvent();
        }
        CrossSine.eventManager.callEvent(event);
        if (event.isCancelled()) {
            callbackInfoReturnable.setReturnValue(false);
        }
    }

    @Redirect(method={"onUpdateWalkingPlayer"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/network/NetHandlerPlayClient;addToSendQueue(Lnet/minecraft/network/Packet;)V", ordinal=7))
    public void emulateIdlePacket(NetHandlerPlayClient instance, Packet<?> p_addToSendQueue_1_) {
        if (ProtocolFixer.newerThan1_8() && this.prevOnGround == this.field_70122_E) {
            return;
        }
        instance.func_147297_a(p_addToSendQueue_1_);
    }

    @Override
    @Overwrite
    public void func_70636_d() {
        boolean noStrafe;
        boolean lastForwardToggleState = this.field_71158_b.field_78900_b > 0.05f;
        boolean lastJumpToggleState = this.field_71158_b.field_78901_c;
        this.field_71158_b.func_78898_a();
        Sprint sprint = CrossSine.moduleManager.getModule(Sprint.class);
        NoSlow noSlow = CrossSine.moduleManager.getModule(NoSlow.class);
        KillAura killAura = CrossSine.moduleManager.getModule(KillAura.class);
        Inventory inventoryMove = CrossSine.moduleManager.getModule(Inventory.class);
        MovementFix movementFix = CrossSine.moduleManager.getModule(MovementFix.class);
        if (this.field_71157_e > 0) {
            --this.field_71157_e;
            if (this.field_71157_e == 0) {
                this.func_70031_b(false);
            }
        }
        if (this.field_71156_d > 0) {
            --this.field_71156_d;
        }
        boolean isSprintDirection = false;
        boolean movingStat = Math.abs(this.field_71158_b.field_78900_b) > 0.05f || Math.abs(this.field_71158_b.field_78902_a) > 0.05f;
        boolean runStrictStrafe = movementFix.getDoFix() && !movementFix.getSilentFix();
        boolean bl = noStrafe = RotationUtils.targetRotation == null || !movementFix.getDoFix();
        if (!movingStat || runStrictStrafe || noStrafe) {
            isSprintDirection = this.field_71158_b.field_78900_b > 0.05f;
        } else {
            boolean bl2 = isSprintDirection = Math.abs(RotationUtils.getAngleDifference(MovementUtils.INSTANCE.getMovingYaw(), RotationUtils.targetRotation.getYaw())) < 67.0f;
        }
        if (!movingStat) {
            isSprintDirection = false;
        }
        boolean attemptToggle = sprint.getState() || this.func_70051_ag() || this.field_71159_c.field_71474_y.field_151444_V.func_151470_d();
        boolean baseIsMoving = sprint.getState() && (Boolean)sprint.getAllDirectionsValue().get() != false && (Math.abs(this.field_71158_b.field_78900_b) > 0.05f || Math.abs(this.field_71158_b.field_78902_a) > 0.05f) || isSprintDirection;
        boolean baseSprintState = !(!((Boolean)sprint.getHungryValue().get() == false && sprint.getState() || (float)this.func_71024_bL().func_75116_a() > 6.0f) && !this.field_71075_bZ.field_75101_c || !baseIsMoving || this.field_70123_F && (Boolean)sprint.getCollideValue().get() == false || this.func_70093_af() && (Boolean)sprint.getSneakValue().get() == false || this.func_70644_a(Potion.field_76440_q));
        boolean canToggleSprint = this.field_70122_E && !this.field_71158_b.field_78901_c && !this.field_71158_b.field_78899_d && !this.func_70644_a(Potion.field_76440_q);
        boolean isCurrentUsingItem = this.func_70694_bm() != null && (this.func_71039_bw() || this.func_70694_bm().func_77973_b() instanceof ItemSword && killAura.getBlockingStatus()) && !this.func_70115_ae();
        SprintEvent sprintEvent = new SprintEvent(sprint.getForceSprint() || baseSprintState && (!isCurrentUsingItem || noSlow.getState() && noSlow.getShouldSprint()) && attemptToggle);
        boolean bl3 = baseSprintState = baseSprintState && (!inventoryMove.getNoSprintValue().equals("Real") || !inventoryMove.getInvOpen());
        if (!(attemptToggle || lastForwardToggleState || !baseSprintState || this.func_70051_ag() || !canToggleSprint || isCurrentUsingItem || this.func_70644_a(Potion.field_76440_q))) {
            if (this.field_71156_d <= 0 && !this.field_71159_c.field_71474_y.field_151444_V.func_151470_d()) {
                this.field_71156_d = 7;
            } else {
                attemptToggle = true;
            }
        }
        CrossSine.eventManager.callEvent(sprintEvent);
        this.func_70031_b(sprintEvent.getSprint());
        if (sprintEvent.isCancelled()) {
            this.func_70031_b(false);
        }
        CrossSine.eventManager.callEvent(new UpdateEvent());
        this.field_71080_cy = this.field_71086_bY;
        if (this.field_71087_bX) {
            if (this.field_71086_bY == 0.0f) {
                this.field_71159_c.func_147118_V().func_147682_a((ISound)PositionedSoundRecord.func_147674_a((ResourceLocation)new ResourceLocation("portal.trigger"), (float)(this.field_70146_Z.nextFloat() * 0.4f + 0.8f)));
            }
            this.field_71086_bY += 0.0125f;
            if (this.field_71086_bY >= 1.0f) {
                this.field_71086_bY = 1.0f;
            }
            this.field_71087_bX = false;
        } else if (this.func_70644_a(Potion.field_76431_k) && this.func_70660_b(Potion.field_76431_k).func_76459_b() > 60) {
            this.field_71086_bY += 0.006666667f;
            if (this.field_71086_bY > 1.0f) {
                this.field_71086_bY = 1.0f;
            }
        } else {
            if (this.field_71086_bY > 0.0f) {
                this.field_71086_bY -= 0.05f;
            }
            if (this.field_71086_bY < 0.0f) {
                this.field_71086_bY = 0.0f;
            }
        }
        if (this.field_71088_bW > 0) {
            --this.field_71088_bW;
        }
        this.field_71158_b.func_78898_a();
        movingStat = Math.abs(this.field_71158_b.field_78900_b) > 0.05f || Math.abs(this.field_71158_b.field_78902_a) > 0.05f;
        runStrictStrafe = movementFix.getDoFix() && !movementFix.getSilentFix();
        noStrafe = RotationUtils.targetRotation == null || !movementFix.getDoFix();
        boolean bl4 = isCurrentUsingItem = this.func_70694_bm() != null && (this.func_71039_bw() || this.func_70694_bm().func_77973_b() instanceof ItemSword && killAura.getBlockingStatus()) && !this.func_70115_ae();
        if (isCurrentUsingItem) {
            SlowDownEvent slowDownEvent = new SlowDownEvent(0.2f, 0.2f);
            CrossSine.eventManager.callEvent(slowDownEvent);
            this.field_71158_b.field_78902_a *= slowDownEvent.getStrafe();
            this.field_71158_b.field_78900_b *= slowDownEvent.getForward();
        }
        this.func_145771_j(this.field_70165_t - (double)this.field_70130_N * 0.35, this.func_174813_aQ().field_72338_b + 0.5, this.field_70161_v + (double)this.field_70130_N * 0.35);
        this.func_145771_j(this.field_70165_t - (double)this.field_70130_N * 0.35, this.func_174813_aQ().field_72338_b + 0.5, this.field_70161_v - (double)this.field_70130_N * 0.35);
        this.func_145771_j(this.field_70165_t + (double)this.field_70130_N * 0.35, this.func_174813_aQ().field_72338_b + 0.5, this.field_70161_v - (double)this.field_70130_N * 0.35);
        this.func_145771_j(this.field_70165_t + (double)this.field_70130_N * 0.35, this.func_174813_aQ().field_72338_b + 0.5, this.field_70161_v + (double)this.field_70130_N * 0.35);
        isSprintDirection = !movingStat || runStrictStrafe || noStrafe ? this.field_71158_b.field_78900_b > 0.05f : Math.abs(RotationUtils.getAngleDifference(MovementUtils.INSTANCE.getMovingYaw(), RotationUtils.targetRotation.getYaw())) < 67.0f;
        baseIsMoving = sprint.getState() && (Boolean)sprint.getAllDirectionsValue().get() != false && (Math.abs(this.field_71158_b.field_78900_b) > 0.05f || Math.abs(this.field_71158_b.field_78902_a) > 0.05f) || isSprintDirection;
        baseSprintState = !(!((Boolean)sprint.getHungryValue().get() == false && sprint.getState() || (float)this.func_71024_bL().func_75116_a() > 6.0f) && !this.field_71075_bZ.field_75101_c || !baseIsMoving || this.field_70123_F && (Boolean)sprint.getCollideValue().get() == false || this.func_70093_af() && (Boolean)sprint.getSneakValue().get() == false || this.func_70644_a(Potion.field_76440_q));
        this.func_70031_b(sprintEvent.getSprint());
        if (sprintEvent.isCancelled()) {
            this.func_70031_b(false);
        }
        this.debug_AttemptSprint = this.func_70051_ag();
        if (this.field_71075_bZ.field_75101_c) {
            if (this.field_71159_c.field_71442_b.func_178887_k()) {
                if (!this.field_71075_bZ.field_75100_b) {
                    this.field_71075_bZ.field_75100_b = true;
                    this.func_71016_p();
                }
            } else if (!lastJumpToggleState && this.field_71158_b.field_78901_c) {
                if (this.field_71101_bC == 0) {
                    this.field_71101_bC = 7;
                } else {
                    this.field_71075_bZ.field_75100_b = !this.field_71075_bZ.field_75100_b;
                    this.func_71016_p();
                    this.field_71101_bC = 0;
                }
            }
        }
        if (this.field_71075_bZ.field_75100_b && this.func_175160_A()) {
            if (this.field_71158_b.field_78899_d) {
                this.field_70181_x -= (double)(this.field_71075_bZ.func_75093_a() * 3.0f);
            }
            if (this.field_71158_b.field_78901_c) {
                this.field_70181_x += (double)(this.field_71075_bZ.func_75093_a() * 3.0f);
            }
        }
        if (this.func_110317_t()) {
            if (this.field_110320_a < 0) {
                ++this.field_110320_a;
                if (this.field_110320_a == 0) {
                    this.field_110321_bQ = 0.0f;
                }
            }
            if (lastJumpToggleState && !this.field_71158_b.field_78901_c) {
                this.field_110320_a = -10;
                this.func_110318_g();
            } else if (!lastJumpToggleState && this.field_71158_b.field_78901_c) {
                this.field_110320_a = 0;
                this.field_110321_bQ = 0.0f;
            } else if (lastJumpToggleState) {
                ++this.field_110320_a;
                this.field_110321_bQ = this.field_110320_a < 10 ? (float)this.field_110320_a * 0.1f : 0.8f + 2.0f / (float)(this.field_110320_a - 9) * 0.1f;
            }
        } else {
            this.field_110321_bQ = 0.0f;
        }
        super.func_70636_d();
        if (this.field_70122_E && this.field_71075_bZ.field_75100_b && !this.field_71159_c.field_71442_b.func_178887_k()) {
            this.field_71075_bZ.field_75100_b = false;
            this.func_71016_p();
        }
    }

    @Override
    @Overwrite
    public void func_71038_i() {
        SwingEvent event = new SwingEvent();
        CrossSine.eventManager.callEvent(event);
        if (!event.isCancelled()) {
            super.func_71038_i();
        } else {
            this.field_71159_c.field_71439_g.field_82175_bq = false;
        }
        this.field_71174_a.func_147297_a((Packet)new C0APacketAnimation());
    }

    @Override
    public void func_70091_d(double x, double y, double z) {
        MoveEvent moveEvent = new MoveEvent(x, y, z);
        CrossSine.eventManager.callEvent(moveEvent);
        if (moveEvent.isCancelled()) {
            return;
        }
        x = moveEvent.getX();
        y = moveEvent.getY();
        z = moveEvent.getZ();
        if (this.field_70145_X) {
            this.func_174826_a(this.func_174813_aQ().func_72317_d(x, y, z));
            this.field_70165_t = (this.func_174813_aQ().field_72340_a + this.func_174813_aQ().field_72336_d) / 2.0;
            this.field_70163_u = this.func_174813_aQ().field_72338_b;
            this.field_70161_v = (this.func_174813_aQ().field_72339_c + this.func_174813_aQ().field_72334_f) / 2.0;
        } else {
            Block block;
            boolean flag;
            this.field_70170_p.field_72984_F.func_76320_a("move");
            double d0 = this.field_70165_t;
            double d1 = this.field_70163_u;
            double d2 = this.field_70161_v;
            if (this.field_70134_J) {
                this.field_70134_J = false;
                x *= 0.25;
                y *= (double)0.05f;
                z *= 0.25;
                this.field_70159_w = 0.0;
                this.field_70181_x = 0.0;
                this.field_70179_y = 0.0;
            }
            double d3 = x;
            double d4 = y;
            double d5 = z;
            boolean bl = flag = this.field_70122_E && this.func_70093_af();
            if (flag || moveEvent.isSafeWalk()) {
                double d6 = 0.05;
                while (x != 0.0 && this.field_70170_p.func_72945_a((Entity)this, this.func_174813_aQ().func_72317_d(x, -1.0, 0.0)).isEmpty()) {
                    x = x < d6 && x >= -d6 ? 0.0 : (x > 0.0 ? (x -= d6) : (x += d6));
                    d3 = x;
                }
                while (z != 0.0 && this.field_70170_p.func_72945_a((Entity)this, this.func_174813_aQ().func_72317_d(0.0, -1.0, z)).isEmpty()) {
                    z = z < d6 && z >= -d6 ? 0.0 : (z > 0.0 ? (z -= d6) : (z += d6));
                    d5 = z;
                }
                while (x != 0.0 && z != 0.0 && this.field_70170_p.func_72945_a((Entity)this, this.func_174813_aQ().func_72317_d(x, -1.0, z)).isEmpty()) {
                    x = x < d6 && x >= -d6 ? 0.0 : (x > 0.0 ? (x -= d6) : (x += d6));
                    d3 = x;
                    z = z < d6 && z >= -d6 ? 0.0 : (z > 0.0 ? (z -= d6) : (z += d6));
                    d5 = z;
                }
            }
            List list1 = this.field_70170_p.func_72945_a((Entity)this, this.func_174813_aQ().func_72321_a(x, y, z));
            AxisAlignedBB axisalignedbb = this.func_174813_aQ();
            for (Object axisalignedbb1 : list1) {
                y = axisalignedbb1.func_72323_b(this.func_174813_aQ(), y);
            }
            this.func_174826_a(this.func_174813_aQ().func_72317_d(0.0, y, 0.0));
            boolean flag1 = this.field_70122_E || d4 != y && d4 < 0.0;
            for (AxisAlignedBB axisalignedbb2 : list1) {
                x = axisalignedbb2.func_72316_a(this.func_174813_aQ(), x);
            }
            this.func_174826_a(this.func_174813_aQ().func_72317_d(x, 0.0, 0.0));
            for (AxisAlignedBB axisalignedbb13 : list1) {
                z = axisalignedbb13.func_72322_c(this.func_174813_aQ(), z);
            }
            this.func_174826_a(this.func_174813_aQ().func_72317_d(0.0, 0.0, z));
            if (this.field_70138_W > 0.0f && flag1 && (d3 != x || d5 != z)) {
                StepEvent stepEvent = new StepEvent(this.field_70138_W, EventState.PRE);
                CrossSine.eventManager.callEvent(stepEvent);
                double d11 = x;
                double d7 = y;
                double d8 = z;
                AxisAlignedBB axisalignedbb3 = this.func_174813_aQ();
                this.func_174826_a(axisalignedbb);
                y = stepEvent.getStepHeight();
                List list = this.field_70170_p.func_72945_a((Entity)this, this.func_174813_aQ().func_72321_a(d3, y, d5));
                AxisAlignedBB axisalignedbb4 = this.func_174813_aQ();
                AxisAlignedBB axisalignedbb5 = axisalignedbb4.func_72321_a(d3, 0.0, d5);
                double d9 = y;
                for (AxisAlignedBB axisalignedbb6 : list) {
                    d9 = axisalignedbb6.func_72323_b(axisalignedbb5, d9);
                }
                axisalignedbb4 = axisalignedbb4.func_72317_d(0.0, d9, 0.0);
                double d15 = d3;
                for (AxisAlignedBB axisalignedbb7 : list) {
                    d15 = axisalignedbb7.func_72316_a(axisalignedbb4, d15);
                }
                axisalignedbb4 = axisalignedbb4.func_72317_d(d15, 0.0, 0.0);
                double d16 = d5;
                for (AxisAlignedBB axisalignedbb8 : list) {
                    d16 = axisalignedbb8.func_72322_c(axisalignedbb4, d16);
                }
                axisalignedbb4 = axisalignedbb4.func_72317_d(0.0, 0.0, d16);
                AxisAlignedBB axisalignedbb14 = this.func_174813_aQ();
                double d17 = y;
                for (AxisAlignedBB axisalignedbb9 : list) {
                    d17 = axisalignedbb9.func_72323_b(axisalignedbb14, d17);
                }
                axisalignedbb14 = axisalignedbb14.func_72317_d(0.0, d17, 0.0);
                double d18 = d3;
                for (AxisAlignedBB axisalignedbb10 : list) {
                    d18 = axisalignedbb10.func_72316_a(axisalignedbb14, d18);
                }
                axisalignedbb14 = axisalignedbb14.func_72317_d(d18, 0.0, 0.0);
                double d19 = d5;
                for (AxisAlignedBB axisalignedbb11 : list) {
                    d19 = axisalignedbb11.func_72322_c(axisalignedbb14, d19);
                }
                axisalignedbb14 = axisalignedbb14.func_72317_d(0.0, 0.0, d19);
                double d20 = d15 * d15 + d16 * d16;
                double d10 = d18 * d18 + d19 * d19;
                if (d20 > d10) {
                    x = d15;
                    z = d16;
                    y = -d9;
                    this.func_174826_a(axisalignedbb4);
                } else {
                    x = d18;
                    z = d19;
                    y = -d17;
                    this.func_174826_a(axisalignedbb14);
                }
                for (AxisAlignedBB axisalignedbb12 : list) {
                    y = axisalignedbb12.func_72323_b(this.func_174813_aQ(), y);
                }
                this.func_174826_a(this.func_174813_aQ().func_72317_d(0.0, y, 0.0));
                if (d11 * d11 + d8 * d8 >= x * x + z * z) {
                    x = d11;
                    y = d7;
                    z = d8;
                    this.func_174826_a(axisalignedbb3);
                } else {
                    CrossSine.eventManager.callEvent(new StepEvent(-1.0f, EventState.POST));
                }
            }
            this.field_70170_p.field_72984_F.func_76319_b();
            this.field_70170_p.field_72984_F.func_76320_a("rest");
            this.field_70165_t = (this.func_174813_aQ().field_72340_a + this.func_174813_aQ().field_72336_d) / 2.0;
            this.field_70163_u = this.func_174813_aQ().field_72338_b;
            this.field_70161_v = (this.func_174813_aQ().field_72339_c + this.func_174813_aQ().field_72334_f) / 2.0;
            this.field_70123_F = d3 != x || d5 != z;
            this.field_70124_G = d4 != y;
            this.field_70122_E = this.field_70124_G && d4 < 0.0;
            this.field_70132_H = this.field_70123_F || this.field_70124_G;
            int i = MathHelper.func_76128_c((double)this.field_70165_t);
            int j = MathHelper.func_76128_c((double)(this.field_70163_u - (double)0.2f));
            int k = MathHelper.func_76128_c((double)this.field_70161_v);
            BlockPos blockpos = new BlockPos(i, j, k);
            Block block1 = this.field_70170_p.func_180495_p(blockpos).func_177230_c();
            if (block1.func_149688_o() == Material.field_151579_a && ((block = this.field_70170_p.func_180495_p(blockpos.func_177977_b()).func_177230_c()) instanceof BlockFence || block instanceof BlockWall || block instanceof BlockFenceGate)) {
                block1 = block;
                blockpos = blockpos.func_177977_b();
            }
            this.func_180433_a(y, this.field_70122_E, block1, blockpos);
            if (d3 != x) {
                this.field_70159_w = 0.0;
            }
            if (d5 != z) {
                this.field_70179_y = 0.0;
            }
            if (d4 != y) {
                block1.func_176216_a(this.field_70170_p, (Entity)this);
            }
            if (this.func_70041_e_() && !flag && this.field_70154_o == null) {
                double d12 = this.field_70165_t - d0;
                double d13 = this.field_70163_u - d1;
                double d14 = this.field_70161_v - d2;
                if (block1 != Blocks.field_150468_ap) {
                    d13 = 0.0;
                }
                if (block1 != null && this.field_70122_E) {
                    block1.func_176199_a(this.field_70170_p, blockpos, (Entity)this);
                }
                this.field_70140_Q = (float)((double)this.field_70140_Q + (double)MathHelper.func_76133_a((double)(d12 * d12 + d14 * d14)) * 0.6);
                this.field_82151_R = (float)((double)this.field_82151_R + (double)MathHelper.func_76133_a((double)(d12 * d12 + d13 * d13 + d14 * d14)) * 0.6);
                if (this.field_82151_R > (float)this.getNextStepDistance() && block1.func_149688_o() != Material.field_151579_a) {
                    this.setNextStepDistance((int)this.field_82151_R + 1);
                    if (this.func_70090_H()) {
                        float f = MathHelper.func_76133_a((double)(this.field_70159_w * this.field_70159_w * (double)0.2f + this.field_70181_x * this.field_70181_x + this.field_70179_y * this.field_70179_y * (double)0.2f)) * 0.35f;
                        if (f > 1.0f) {
                            f = 1.0f;
                        }
                        this.func_85030_a(this.func_145776_H(), f, 1.0f + (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.4f);
                    }
                    this.func_180429_a(blockpos, block1);
                }
            }
            try {
                this.func_145775_I();
            }
            catch (Throwable throwable) {
                CrashReport crashreport = CrashReport.func_85055_a((Throwable)throwable, (String)"Checking entity block collision");
                CrashReportCategory crashreportcategory = crashreport.func_85058_a("Entity being checked for collision");
                this.func_85029_a(crashreportcategory);
                throw new ReportedException(crashreport);
            }
            boolean flag2 = this.func_70026_G();
            if (this.field_70170_p.func_147470_e(this.func_174813_aQ().func_72331_e(0.001, 0.001, 0.001))) {
                this.func_70081_e(1);
                if (!flag2) {
                    this.func_70015_d(this.getFire() + 1);
                    if (this.getFire() == 0) {
                        this.func_70015_d(8);
                    }
                }
            } else if (this.getFire() <= 0) {
                this.func_70015_d(-this.field_70174_ab);
            }
            if (flag2 && this.getFire() > 0) {
                this.func_85030_a("random.fizz", 0.7f, 1.6f + (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.4f);
                this.func_70015_d(-this.field_70174_ab);
            }
            this.field_70170_p.field_72984_F.func_76319_b();
        }
    }
}

