/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.world;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.concurrent.ThreadsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.TextValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.ServerUtils;
import net.ccbluex.liquidbounce.utils.misc.HttpUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S01PacketJoinGame;
import net.minecraft.network.play.server.S04PacketEntityEquipment;
import net.minecraft.network.play.server.S0BPacketAnimation;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.network.play.server.S19PacketEntityHeadLook;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.network.play.server.S1CPacketEntityMetadata;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.network.play.server.S1EPacketRemoveEntityEffect;
import net.minecraft.network.play.server.S20PacketEntityProperties;
import net.minecraft.network.play.server.S49PacketUpdateEntityNBT;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(name="StaffChecker", category=ModuleCategory.WORLD)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J\b\u0010\u001b\u001a\u00020\u001cH\u0016J\b\u0010\u001d\u001a\u00020\u001cH\u0016J\u0010\u0010\u001e\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020 H\u0007J\u0010\u0010!\u001a\u00020\u001c2\u0006\u0010\u001f\u001a\u00020\"H\u0007J\u0010\u0010#\u001a\u00020\u001c2\u0006\u0010$\u001a\u00020%H\u0007J\b\u0010&\u001a\u00020\u001cH\u0002J\u0010\u0010'\u001a\u00020\u001c2\u0006\u0010(\u001a\u00020\nH\u0002R\u0014\u0010\u0003\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\n0\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\n0\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0015\u0010\u0006R\u0014\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\n0\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\n0\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006)"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/world/StaffChecker;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "Custom", "", "getCustom", "()Z", "antiV", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "bmcStaffList", "", "chat", "csstaffs", "", "customName", "Lnet/ccbluex/liquidbounce/features/value/Value;", "customValue", "leave", "leavemsg", "Lnet/ccbluex/liquidbounce/features/value/TextValue;", "onBMC", "getOnBMC", "staffs", "staffsInWorld", "isStaff", "entity", "Lnet/minecraft/entity/Entity;", "onEnable", "", "onInitialize", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "onWorld", "e", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "vanish", "warn", "name", "CrossSine"})
public final class StaffChecker
extends Module {
    @NotNull
    private final BoolValue antiV = new BoolValue("AntiVanish", false);
    @NotNull
    private final BoolValue chat = new BoolValue("AlertChat", true);
    @NotNull
    private final BoolValue leave = new BoolValue("Leave", true);
    @NotNull
    private final TextValue leavemsg = new TextValue("LeaveMessage", "leave");
    @NotNull
    private final BoolValue customValue = new BoolValue("CustomName", false);
    @NotNull
    private final Value<String> customName = new TextValue("Name-of-staff", "").displayable(new Function0<Boolean>(this){
        final /* synthetic */ StaffChecker this$0;
        {
            this.this$0 = $receiver;
            super(0);
        }

        @NotNull
        public final Boolean invoke() {
            return (Boolean)StaffChecker.access$getCustomValue$p(this.this$0).get();
        }
    });
    @NotNull
    private List<String> staffs = new ArrayList();
    @NotNull
    private List<String> csstaffs = new ArrayList();
    @NotNull
    private List<String> staffsInWorld = new ArrayList();
    @NotNull
    private String bmcStaffList = "https://crosssine.github.io/cloud/StaffList/bmcstaff.txt";

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final boolean getOnBMC() {
        if (MinecraftInstance.mc.func_71356_B()) return false;
        if (ServerUtils.serverData == null) return false;
        String string = ServerUtils.serverData.field_78845_b;
        Intrinsics.checkNotNullExpressionValue(string, "serverData.serverIP");
        if (!StringsKt.contains$default((CharSequence)string, "blocksmc.com", false, 2, null)) return false;
        return true;
    }

    private final boolean getCustom() {
        return !MinecraftInstance.mc.func_71356_B() && ServerUtils.serverData != null && (Boolean)this.customValue.get() != false;
    }

    @Override
    public void onInitialize() {
        ThreadsKt.thread$default(false, false, null, null, 0, new Function0<Unit>(this){
            final /* synthetic */ StaffChecker this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            public final void invoke() {
                List list;
                String[] stringArray = new String[]{","};
                StaffChecker.access$getStaffs$p(this.this$0).addAll(StringsKt.split$default((CharSequence)HttpUtils.INSTANCE.get(StaffChecker.access$getBmcStaffList$p(this.this$0)), stringArray, false, 0, 6, null));
                List list2 = StaffChecker.access$getCsstaffs$p(this.this$0);
                String string = ((String)StaffChecker.access$getCustomName$p(this.this$0).get()).toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                if (StringsKt.contains$default((CharSequence)string, "https", false, 2, null)) {
                    stringArray = new String[]{","};
                    list = StringsKt.split$default((CharSequence)HttpUtils.INSTANCE.get((String)StaffChecker.access$getCustomName$p(this.this$0).get()), stringArray, false, 0, 6, null);
                } else {
                    stringArray = new String[]{","};
                    list = StringsKt.split$default((CharSequence)StaffChecker.access$getCustomName$p(this.this$0).get(), stringArray, false, 0, 6, null);
                }
                list2.addAll(list);
            }
        }, 31, null);
    }

    @Override
    public void onEnable() {
        this.staffsInWorld.clear();
    }

    @EventTarget
    public final void onWorld(@NotNull WorldEvent e) {
        Intrinsics.checkNotNullParameter(e, "e");
        this.staffsInWorld.clear();
    }

    private final void warn(String name) {
        if (this.staffsInWorld.contains(name)) {
            return;
        }
        if (((Boolean)this.chat.get()).booleanValue()) {
            this.chat(Intrinsics.stringPlus("[\u00a7CAntiStaff\u00a7F] Detected staff: \u00a7C", name));
        }
        if (((Boolean)this.leave.get()).booleanValue()) {
            MinecraftInstance.mc.field_71439_g.func_71165_d(Intrinsics.stringPlus("/", this.leavemsg.get()));
        }
        this.staffsInWorld.add(name);
    }

    private final void vanish() {
        if (((Boolean)this.chat.get()).booleanValue()) {
            this.chat("[\u00a7CAntiStaff\u00a7F] Detected someone vanished!");
        }
        if (((Boolean)this.leave.get()).booleanValue()) {
            MinecraftInstance.mc.field_71439_g.func_71165_d(Intrinsics.stringPlus("/", this.leavemsg.get()));
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final boolean isStaff(Entity entity) {
        if (this.getCustom()) {
            if (this.csstaffs.contains(entity.func_70005_c_())) return true;
            if (this.csstaffs.contains(entity.func_145748_c_().func_150260_c())) return true;
            String string = entity.func_70005_c_();
            Intrinsics.checkNotNullExpressionValue(string, "entity.name");
            if (StringsKt.contains$default((CharSequence)string, this.csstaffs.toString(), false, 2, null)) return true;
            String string2 = this.csstaffs.toString().toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(string2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            CharSequence charSequence = string2;
            string = entity.func_70005_c_();
            Intrinsics.checkNotNullExpressionValue(string, "entity.name");
            string2 = string.toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(string2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            if (StringsKt.contains$default(charSequence, string2, false, 2, null)) return true;
            string2 = this.csstaffs.toString().toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(string2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            CharSequence charSequence2 = string2;
            string = entity.func_145748_c_().func_150260_c();
            Intrinsics.checkNotNullExpressionValue(string, "entity.displayName.unformattedText");
            string2 = string.toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(string2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            if (StringsKt.contains$default(charSequence2, string2, false, 2, null)) return true;
            string = entity.func_70005_c_();
            Intrinsics.checkNotNullExpressionValue(string, "entity.name");
            string2 = string.toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(string2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            CharSequence charSequence3 = string2;
            string2 = this.csstaffs.toString().toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(string2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            if (!StringsKt.contains$default(charSequence3, string2, false, 2, null)) return false;
            return true;
        }
        if (!this.getOnBMC()) return false;
        if (this.staffs.contains(entity.func_70005_c_())) return true;
        if (this.staffs.contains(entity.func_145748_c_().func_150260_c())) return true;
        String string = entity.func_70005_c_();
        Intrinsics.checkNotNullExpressionValue(string, "entity.name");
        if (StringsKt.contains$default((CharSequence)string, this.staffs.toString(), false, 2, null)) return true;
        String string3 = this.staffs.toString().toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(string3, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        CharSequence charSequence = string3;
        string = entity.func_70005_c_();
        Intrinsics.checkNotNullExpressionValue(string, "entity.name");
        string3 = string.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(string3, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        if (StringsKt.contains$default(charSequence, string3, false, 2, null)) return true;
        string3 = this.staffs.toString().toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(string3, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        CharSequence charSequence4 = string3;
        string = entity.func_145748_c_().func_150260_c();
        Intrinsics.checkNotNullExpressionValue(string, "entity.displayName.unformattedText");
        string3 = string.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(string3, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        if (StringsKt.contains$default(charSequence4, string3, false, 2, null)) return true;
        string = entity.func_70005_c_();
        Intrinsics.checkNotNullExpressionValue(string, "entity.name");
        string3 = string.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(string3, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        CharSequence charSequence5 = string3;
        string3 = this.staffs.toString().toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(string3, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        if (!StringsKt.contains$default(charSequence5, string3, false, 2, null)) return false;
        return true;
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (MinecraftInstance.mc.field_71441_e == null || MinecraftInstance.mc.field_71439_g == null) {
            return;
        }
        if (this.getCustom() || this.getOnBMC()) {
            Packet<?> packet = event.getPacket();
            if (packet instanceof S0CPacketSpawnPlayer) {
                Entity entity = MinecraftInstance.mc.field_71441_e.func_73045_a(((S0CPacketSpawnPlayer)packet).func_148943_d());
                if (entity == null) {
                    return;
                }
                Entity entity2 = entity;
                if (this.isStaff(entity2)) {
                    String string = entity2.func_70005_c_();
                    Intrinsics.checkNotNullExpressionValue(string, "entity.name");
                    this.warn(string);
                }
            } else if (packet instanceof S1EPacketRemoveEntityEffect) {
                Entity entity = MinecraftInstance.mc.field_71441_e.func_73045_a(((S1EPacketRemoveEntityEffect)packet).func_149076_c());
                if (entity == null) {
                    return;
                }
                Entity entity3 = entity;
                if (this.isStaff(entity3)) {
                    String string = entity3.func_70005_c_();
                    Intrinsics.checkNotNullExpressionValue(string, "entity.name");
                    this.warn(string);
                }
            } else if (packet instanceof S12PacketEntityVelocity) {
                Entity entity = MinecraftInstance.mc.field_71441_e.func_73045_a(((S12PacketEntityVelocity)packet).func_149412_c());
                if (entity == null) {
                    return;
                }
                Entity entity4 = entity;
                if (this.isStaff(entity4)) {
                    String string = entity4.func_70005_c_();
                    Intrinsics.checkNotNullExpressionValue(string, "entity.name");
                    this.warn(string);
                }
            } else if (packet instanceof S01PacketJoinGame) {
                Entity entity = MinecraftInstance.mc.field_71441_e.func_73045_a(((S01PacketJoinGame)packet).func_149197_c());
                if (entity == null) {
                    return;
                }
                Entity entity5 = entity;
                if (this.isStaff(entity5)) {
                    String string = entity5.func_70005_c_();
                    Intrinsics.checkNotNullExpressionValue(string, "entity.name");
                    this.warn(string);
                }
            } else if (packet instanceof S04PacketEntityEquipment) {
                Entity entity = MinecraftInstance.mc.field_71441_e.func_73045_a(((S04PacketEntityEquipment)packet).func_149389_d());
                if (entity == null) {
                    return;
                }
                Entity entity6 = entity;
                if (this.isStaff(entity6)) {
                    String string = entity6.func_70005_c_();
                    Intrinsics.checkNotNullExpressionValue(string, "entity.name");
                    this.warn(string);
                }
            } else if (packet instanceof S1CPacketEntityMetadata) {
                Entity entity = MinecraftInstance.mc.field_71441_e.func_73045_a(((S1CPacketEntityMetadata)packet).func_149375_d());
                if (entity == null) {
                    return;
                }
                Entity entity7 = entity;
                if (this.isStaff(entity7)) {
                    String string = entity7.func_70005_c_();
                    Intrinsics.checkNotNullExpressionValue(string, "entity.name");
                    this.warn(string);
                }
            } else if (packet instanceof S1DPacketEntityEffect) {
                if (((Boolean)this.antiV.get()).booleanValue() && MinecraftInstance.mc.field_71441_e.func_73045_a(((S1DPacketEntityEffect)packet).func_149426_d()) == null) {
                    this.vanish();
                }
                Entity entity = MinecraftInstance.mc.field_71441_e.func_73045_a(((S1DPacketEntityEffect)packet).func_149426_d());
                if (entity == null) {
                    return;
                }
                Entity entity8 = entity;
                if (this.isStaff(entity8)) {
                    String string = entity8.func_70005_c_();
                    Intrinsics.checkNotNullExpressionValue(string, "entity.name");
                    this.warn(string);
                }
            } else if (packet instanceof S18PacketEntityTeleport) {
                Entity entity = MinecraftInstance.mc.field_71441_e.func_73045_a(((S18PacketEntityTeleport)packet).func_149451_c());
                if (entity == null) {
                    return;
                }
                Entity entity9 = entity;
                if (this.isStaff(entity9)) {
                    String string = entity9.func_70005_c_();
                    Intrinsics.checkNotNullExpressionValue(string, "entity.name");
                    this.warn(string);
                }
            } else if (packet instanceof S20PacketEntityProperties) {
                Entity entity = MinecraftInstance.mc.field_71441_e.func_73045_a(((S20PacketEntityProperties)packet).func_149442_c());
                if (entity == null) {
                    return;
                }
                Entity entity10 = entity;
                if (this.isStaff(entity10)) {
                    String string = entity10.func_70005_c_();
                    Intrinsics.checkNotNullExpressionValue(string, "entity.name");
                    this.warn(string);
                }
            } else if (packet instanceof S0BPacketAnimation) {
                Entity entity = MinecraftInstance.mc.field_71441_e.func_73045_a(((S0BPacketAnimation)packet).func_148978_c());
                if (entity == null) {
                    return;
                }
                Entity entity11 = entity;
                if (this.isStaff(entity11)) {
                    String string = entity11.func_70005_c_();
                    Intrinsics.checkNotNullExpressionValue(string, "entity.name");
                    this.warn(string);
                }
            } else if (packet instanceof S14PacketEntity) {
                if (((S14PacketEntity)packet).func_149065_a((World)MinecraftInstance.mc.field_71441_e) == null) {
                    this.vanish();
                }
                Entity entity = ((S14PacketEntity)packet).func_149065_a((World)MinecraftInstance.mc.field_71441_e);
                if (entity == null) {
                    return;
                }
                Entity entity12 = entity;
                if (this.isStaff(entity12)) {
                    String string = entity12.func_70005_c_();
                    Intrinsics.checkNotNullExpressionValue(string, "entity.name");
                    this.warn(string);
                }
            } else if (packet instanceof S19PacketEntityStatus) {
                Entity entity = ((S19PacketEntityStatus)packet).func_149161_a((World)MinecraftInstance.mc.field_71441_e);
                if (entity == null) {
                    return;
                }
                Entity entity13 = entity;
                if (this.isStaff(entity13)) {
                    String string = entity13.func_70005_c_();
                    Intrinsics.checkNotNullExpressionValue(string, "entity.name");
                    this.warn(string);
                }
            } else if (packet instanceof S19PacketEntityHeadLook) {
                Entity entity = ((S19PacketEntityHeadLook)packet).func_149381_a((World)MinecraftInstance.mc.field_71441_e);
                if (entity == null) {
                    return;
                }
                Entity entity14 = entity;
                if (this.isStaff(entity14)) {
                    String string = entity14.func_70005_c_();
                    Intrinsics.checkNotNullExpressionValue(string, "entity.name");
                    this.warn(string);
                }
            } else if (packet instanceof S49PacketUpdateEntityNBT) {
                Entity entity = ((S49PacketUpdateEntityNBT)packet).func_179764_a((World)MinecraftInstance.mc.field_71441_e);
                if (entity == null) {
                    return;
                }
                Entity entity15 = entity;
                if (this.isStaff(entity15)) {
                    String string = entity15.func_70005_c_();
                    Intrinsics.checkNotNullExpressionValue(string, "entity.name");
                    this.warn(string);
                }
            }
        }
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Object object;
        NetworkPlayerInfo it;
        Intrinsics.checkNotNullParameter(event, "event");
        if (MinecraftInstance.mc.field_71441_e == null || MinecraftInstance.mc.field_71439_g == null || !this.getOnBMC()) {
            return;
        }
        Collection collection = MinecraftInstance.mc.func_147114_u().func_175106_d();
        Intrinsics.checkNotNullExpressionValue(collection, "mc.netHandler.playerInfoMap");
        Iterable $this$forEach$iv = collection;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            it = (NetworkPlayerInfo)element$iv;
            boolean bl = false;
            Intrinsics.checkNotNullExpressionValue(it, "it");
            object = new String[]{" "};
            String networkName = (String)StringsKt.split$default((CharSequence)ColorUtils.stripColor(EntityUtils.INSTANCE.getName(it)), object, false, 0, 6, null).get(0);
            if (!this.staffs.contains(networkName)) continue;
            this.warn(networkName);
        }
        $this$forEach$iv = MinecraftInstance.mc.field_71441_e.field_72996_f;
        Intrinsics.checkNotNullExpressionValue($this$forEach$iv, "mc.theWorld.loadedEntityList");
        $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            it = (Entity)element$iv;
            boolean bl = false;
            if (!this.staffs.contains(it.func_70005_c_())) continue;
            object = it.func_70005_c_();
            Intrinsics.checkNotNullExpressionValue(object, "it.name");
            this.warn((String)object);
        }
    }

    public static final /* synthetic */ List access$getStaffs$p(StaffChecker $this) {
        return $this.staffs;
    }

    public static final /* synthetic */ String access$getBmcStaffList$p(StaffChecker $this) {
        return $this.bmcStaffList;
    }

    public static final /* synthetic */ List access$getCsstaffs$p(StaffChecker $this) {
        return $this.csstaffs;
    }

    public static final /* synthetic */ Value access$getCustomName$p(StaffChecker $this) {
        return $this.customName;
    }

    public static final /* synthetic */ BoolValue access$getCustomValue$p(StaffChecker $this) {
        return $this.customValue;
    }
}

