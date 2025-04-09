/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.combat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.internal.ProgressionUtilKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.PreUpdateEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.SprintEvent;
import net.ccbluex.liquidbounce.event.StrafeEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.features.module.modules.movement.Flight;
import net.ccbluex.liquidbounce.features.module.modules.movement.MovementFix;
import net.ccbluex.liquidbounce.features.module.modules.movement.TargetStrafe;
import net.ccbluex.liquidbounce.features.module.modules.player.Blink;
import net.ccbluex.liquidbounce.features.module.modules.player.FreeCam;
import net.ccbluex.liquidbounce.features.module.modules.player.Scaffold;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.TitleValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.protocol.api.ProtocolFixer;
import net.ccbluex.liquidbounce.ui.client.gui.colortheme.ClientTheme;
import net.ccbluex.liquidbounce.utils.BlinkUtils;
import net.ccbluex.liquidbounce.utils.CPSCounter;
import net.ccbluex.liquidbounce.utils.CooldownHelper;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.InventoryUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MouseUtils;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.RaycastUtils;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.VecRotation;
import net.ccbluex.liquidbounce.utils.extensions.EntityExtensionKt;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.ccbluex.liquidbounce.utils.render.EaseUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.WorldSettings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@ModuleInfo(name="KillAura", category=ModuleCategory.COMBAT, keyBind=19)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u00bf\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0019\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\b\u0013\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n*\u0001g\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010y\u001a\u00020z2\u0006\u0010{\u001a\u00020$2\u0006\u0010|\u001a\u00020\u0005H\u0002J\u0016\u0010}\u001a\u00020z2\u0006\u0010{\u001a\u00020$2\u0006\u0010~\u001a\u00020\u007fJ\u001b\u0010\u0080\u0001\u001a\u00020\t2\u0007\u0010\u0081\u0001\u001a\u00020\u00072\u0007\u0010\u0082\u0001\u001a\u00020\u0007H\u0002J\u0011\u0010\u0083\u0001\u001a\u00020\u00052\u0006\u0010{\u001a\u00020$H\u0002J\t\u0010\u0084\u0001\u001a\u00020\u0007H\u0002J\t\u0010\u0085\u0001\u001a\u00020\u0007H\u0002J\t\u0010\u0086\u0001\u001a\u00020zH\u0016J\t\u0010\u0087\u0001\u001a\u00020zH\u0016J\u0012\u0010\u0088\u0001\u001a\u00020z2\u0007\u0010~\u001a\u00030\u0089\u0001H\u0007J\u0012\u0010\u008a\u0001\u001a\u00020z2\u0007\u0010~\u001a\u00030\u008b\u0001H\u0007J\u0012\u0010\u008c\u0001\u001a\u00020z2\u0007\u0010~\u001a\u00030\u008d\u0001H\u0007J\u0011\u0010\u008e\u0001\u001a\u00020z2\u0006\u0010~\u001a\u00020\u007fH\u0007J\u0012\u0010\u008f\u0001\u001a\u00020z2\u0007\u0010~\u001a\u00030\u0090\u0001H\u0007J\u0012\u0010\u0091\u0001\u001a\u00020z2\u0007\u0010~\u001a\u00030\u0092\u0001H\u0007J\u0013\u0010\u0093\u0001\u001a\u00020z2\b\u0010\u0094\u0001\u001a\u00030\u0095\u0001H\u0007J\u0011\u0010\u0096\u0001\u001a\u00020z2\u0006\u0010|\u001a\u00020\u0005H\u0002J\u0011\u0010\u0097\u0001\u001a\u00020z2\u0006\u0010|\u001a\u00020\u0005H\u0002J\t\u0010\u0098\u0001\u001a\u00020zH\u0002J\t\u0010\u0099\u0001\u001a\u00020zH\u0002J\t\u0010\u009a\u0001\u001a\u00020zH\u0002J\t\u0010\u009b\u0001\u001a\u00020zH\u0002J\t\u0010\u009c\u0001\u001a\u00020zH\u0002J\u0011\u0010\u009d\u0001\u001a\u00020\u00052\u0006\u0010{\u001a\u000206H\u0002J\t\u0010\u009e\u0001\u001a\u00020zH\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u000e\u001a\u00020\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0016\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u0014\u0010\u001b\u001a\u00020\u00058BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001c\u0010\u0018R\u0014\u0010\u001d\u001a\u00020\u00058BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001e\u0010\u0018R\u000e\u0010\u001f\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010 \u001a\u00020\u00058BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b!\u0010\u0018R\u000e\u0010\"\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010#\u001a\u0004\u0018\u00010$X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b%\u0010&\"\u0004\b'\u0010(R\u000e\u0010)\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0011\u0010*\u001a\u00020+\u00a2\u0006\b\n\u0000\u001a\u0004\b,\u0010-R\u0014\u0010.\u001a\b\u0012\u0004\u0012\u00020$0/X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u00100\u001a\u00020\u00058F\u00a2\u0006\u0006\u001a\u0004\b1\u0010\u0018R\u0014\u00102\u001a\b\u0012\u0004\u0012\u00020\r0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u00103\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u00104\u001a\u000e\u0012\u0004\u0012\u000206\u0012\u0004\u0012\u00020705X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u00108\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u00109\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010:\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010;\u001a\b\u0012\u0004\u0012\u00020$0/X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010<\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010=\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010>\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010?\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010@\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010A\u001a\u00020BX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010C\u001a\u00020+X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010D\u001a\u00020\r8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\bE\u0010FR\u000e\u0010G\u001a\u00020BX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010H\u001a\u00020BX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010I\u001a\u00020+X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010J\u001a\u00020BX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010K\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010L\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010M\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010N\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010O\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010P\u001a\u00020\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010Q\u001a\u00020\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010R\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010S\u001a\b\u0012\u0004\u0012\u00020\u00070/X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010T\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010U\u001a\b\u0012\u0004\u0012\u00020\r0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010V\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010W\u001a\u00020+\u00a2\u0006\b\n\u0000\u001a\u0004\bX\u0010-R\u0014\u0010Y\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010Z\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010[\u001a\u00020\\X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010]\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010^\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010_\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010`\u001a\b\u0012\u0004\u0012\u00020a0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010b\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010c\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010d\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010e\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010f\u001a\u00020gX\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010hR\u000e\u0010i\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010j\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010k\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010l\u001a\u00020a8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\bm\u0010nR\u000e\u0010o\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010p\u001a\u00020\\X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010q\u001a\u00020\\X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010r\u001a\u00020\\X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010s\u001a\u00020\\X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010t\u001a\u00020\\X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010u\u001a\u00020\\X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010v\u001a\u00020\\X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010w\u001a\u00020\\X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010x\u001a\u00020\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u009f\u0001"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/combat/KillAura;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "CpsReduceValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "addCps", "", "attackDelay", "", "attackTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "autoBlockRangeValue", "", "autoBlockValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "getAutoBlockValue", "()Lnet/ccbluex/liquidbounce/features/value/ListValue;", "blinkCheck", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "blinkLag", "blinking", "blockingStatus", "getBlockingStatus", "()Z", "setBlockingStatus", "(Z)V", "canBlock", "getCanBlock", "canFakeBlock", "getCanFakeBlock", "canSwing", "cancelRun", "getCancelRun", "clicks", "currentTarget", "Lnet/minecraft/entity/EntityLivingBase;", "getCurrentTarget", "()Lnet/minecraft/entity/EntityLivingBase;", "setCurrentTarget", "(Lnet/minecraft/entity/EntityLivingBase;)V", "cycle", "discoverRangeValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "getDiscoverRangeValue", "()Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "discoveredTargets", "", "displayBlocking", "getDisplayBlocking", "fovDisValue", "fovValue", "getAABB", "Lkotlin/Function1;", "Lnet/minecraft/entity/Entity;", "Lnet/minecraft/util/AxisAlignedBB;", "hitAbleValue", "hitable", "hyTicks", "inRangeDiscoveredTargets", "keepDirectionTickValue", "keepDirectionValue", "lastCanBeSeen", "limitedMultiTargetsValue", "markValue", "maxCpsValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "maxPredictSizeValue", "maxRange", "getMaxRange", "()F", "maxTurnSpeedValue", "minCpsValue", "minPredictSizeValue", "minTurnSpeedValue", "nineCombat", "noEventBlocking", "noFlyValue", "noScaffValue", "onWeapon", "pitch", "predictAmount", "predictValue", "prevTargetEntities", "priorityValue", "randomCenRangeValue", "randomCenterModeValue", "rangeValue", "getRangeValue", "raycastTargetValue", "raycastValue", "render", "Lnet/ccbluex/liquidbounce/features/value/TitleValue;", "rotationModeValue", "rotationRevTickValue", "rotationRevValue", "rotationStrafeValue", "", "rotationTimer", "silentRotationValue", "spinRotation", "swapped", "swingRangeValue", "net/ccbluex/liquidbounce/features/module/modules/combat/KillAura$swingRangeValue$1", "Lnet/ccbluex/liquidbounce/features/module/modules/combat/KillAura$swingRangeValue$1;", "swingValue", "switchDelayValue", "switchTimer", "tag", "getTag", "()Ljava/lang/String;", "targetModeValue", "text1", "text11", "text13", "text15", "text3", "text5", "text7", "text9", "yaw", "attackEntity", "", "entity", "interact", "draw", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "getAttackDelay", "minCps", "maxCps", "isAlive", "maxSpeedRot", "minSpeedRot", "onDisable", "onEnable", "onJump", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onPreUpdate", "Lnet/ccbluex/liquidbounce/event/PreUpdateEvent;", "onRender3D", "onSprint", "Lnet/ccbluex/liquidbounce/event/SprintEvent;", "onStrafe", "Lnet/ccbluex/liquidbounce/event/StrafeEvent;", "onUpdate", "ignoredEvent", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "runAttack", "runAttackLoop", "startBlocking", "startBlockingNoEvent", "stopBlocking", "stopBlockingNoEvent", "updateHitable", "updateRotations", "updateTarget", "CrossSine"})
public final class KillAura
extends Module {
    @NotNull
    public static final KillAura INSTANCE = new KillAura();
    @NotNull
    private static final TitleValue text1 = new TitleValue("Range");
    @NotNull
    private static final FloatValue rangeValue = new FloatValue(){

        protected void onChanged(float oldValue, float newValue) {
            float i = ((Number)KillAura.INSTANCE.getDiscoverRangeValue().get()).floatValue();
            if (i < newValue) {
                this.set(Float.valueOf(i));
            }
        }
    };
    @NotNull
    private static final swingRangeValue.1 swingRangeValue = new FloatValue(){

        protected void onChanged(float oldValue, float newValue) {
            float i = ((Number)KillAura.INSTANCE.getDiscoverRangeValue().get()).floatValue();
            if (i < newValue) {
                this.set(Float.valueOf(i));
            }
            if (KillAura.access$getMaxRange(KillAura.INSTANCE) > newValue) {
                this.set(Float.valueOf(KillAura.access$getMaxRange(KillAura.INSTANCE)));
            }
        }
    };
    @NotNull
    private static final FloatValue discoverRangeValue = new FloatValue("Discover-Range", 6.0f, 0.0f, 8.0f);
    @NotNull
    private static final BoolValue fovValue = new BoolValue("Fov", false);
    @NotNull
    private static final Value<Float> fovDisValue = new FloatValue("FOV-Disttance", 180.0f, 0.0f, 180.0f).displayable(fovDisValue.1.INSTANCE);
    @NotNull
    private static final TitleValue text3 = new TitleValue("CPS");
    @NotNull
    private static final BoolValue nineCombat = new BoolValue("1.9-Combat-Check", false);
    @NotNull
    private static final Value<Boolean> CpsReduceValue = new BoolValue("CPS-Reduce", false).displayable(CpsReduceValue.1.INSTANCE);
    @NotNull
    private static final Value<Integer> addCps = new IntegerValue("Add-CPS", 1, 1, 20).displayable(addCps.1.INSTANCE);
    @NotNull
    private static final IntegerValue maxCpsValue = (IntegerValue)new IntegerValue(){

        protected void onChanged(int oldValue, int newValue) {
            int i = ((Number)KillAura.access$getMinCpsValue$p().get()).intValue();
            if (i > newValue) {
                this.set(i);
            }
            KillAura.access$setAttackDelay$p(KillAura.access$getAttackDelay(KillAura.INSTANCE, ((Number)KillAura.access$getMinCpsValue$p().get()).intValue(), ((Number)this.get()).intValue()));
        }
    }.displayable(maxCpsValue.2.INSTANCE);
    @NotNull
    private static final IntegerValue minCpsValue = (IntegerValue)new IntegerValue(){

        protected void onChanged(int oldValue, int newValue) {
            int i = ((Number)KillAura.access$getMaxCpsValue$p().get()).intValue();
            if (i < newValue) {
                this.set(i);
            }
            KillAura.access$setAttackDelay$p(KillAura.access$getAttackDelay(KillAura.INSTANCE, ((Number)this.get()).intValue(), ((Number)KillAura.access$getMaxCpsValue$p().get()).intValue()));
        }
    }.displayable(minCpsValue.2.INSTANCE);
    @NotNull
    private static final TitleValue text5 = new TitleValue("Target-Mode");
    @NotNull
    private static final ListValue priorityValue;
    @NotNull
    private static final ListValue targetModeValue;
    @NotNull
    private static final Value<Integer> switchDelayValue;
    @NotNull
    private static final Value<Integer> limitedMultiTargetsValue;
    @NotNull
    private static final TitleValue text7;
    @NotNull
    private static final BoolValue blinkCheck;
    @NotNull
    private static final BoolValue noScaffValue;
    @NotNull
    private static final BoolValue noFlyValue;
    @NotNull
    private static final BoolValue onWeapon;
    @NotNull
    private static final TitleValue text9;
    @NotNull
    private static final ListValue swingValue;
    @NotNull
    private static final Value<String> rotationStrafeValue;
    @NotNull
    private static final BoolValue hitAbleValue;
    @NotNull
    private static final TitleValue text11;
    @NotNull
    private static final ListValue autoBlockValue;
    @NotNull
    private static final Value<Float> autoBlockRangeValue;
    @NotNull
    private static final TitleValue text13;
    @NotNull
    private static final ListValue rotationModeValue;
    @NotNull
    private static final BoolValue spinRotation;
    @NotNull
    private static final Value<Boolean> silentRotationValue;
    @NotNull
    private static final IntegerValue maxTurnSpeedValue;
    @NotNull
    private static final IntegerValue minTurnSpeedValue;
    @NotNull
    private static final Value<Boolean> rotationRevValue;
    @NotNull
    private static final Value<Integer> rotationRevTickValue;
    @NotNull
    private static final Value<Boolean> keepDirectionValue;
    @NotNull
    private static final Value<Integer> keepDirectionTickValue;
    @NotNull
    private static final BoolValue randomCenterModeValue;
    @NotNull
    private static final Value<Float> randomCenRangeValue;
    @NotNull
    private static final TitleValue text15;
    @NotNull
    private static final BoolValue raycastValue;
    @NotNull
    private static final Value<Boolean> raycastTargetValue;
    @NotNull
    private static final Value<Boolean> predictValue;
    @NotNull
    private static final FloatValue maxPredictSizeValue;
    @NotNull
    private static final FloatValue minPredictSizeValue;
    @NotNull
    private static final TitleValue render;
    @NotNull
    private static final ListValue markValue;
    @Nullable
    private static EntityLivingBase currentTarget;
    private static boolean hitable;
    @NotNull
    private static final List<Integer> prevTargetEntities;
    @NotNull
    private static final List<EntityLivingBase> discoveredTargets;
    @NotNull
    private static final List<EntityLivingBase> inRangeDiscoveredTargets;
    @NotNull
    private static final MSTimer attackTimer;
    @NotNull
    private static final MSTimer switchTimer;
    @NotNull
    private static final MSTimer rotationTimer;
    private static long attackDelay;
    private static int clicks;
    private static int hyTicks;
    private static boolean blinkLag;
    private static boolean blinking;
    private static boolean cycle;
    private static boolean swapped;
    private static float yaw;
    private static float pitch;
    private static boolean canSwing;
    private static boolean lastCanBeSeen;
    private static boolean blockingStatus;
    private static boolean noEventBlocking;
    private static float predictAmount;
    @NotNull
    private static final Function1<Entity, AxisAlignedBB> getAABB;

    private KillAura() {
    }

    @NotNull
    public final FloatValue getRangeValue() {
        return rangeValue;
    }

    @NotNull
    public final FloatValue getDiscoverRangeValue() {
        return discoverRangeValue;
    }

    @NotNull
    public final ListValue getAutoBlockValue() {
        return autoBlockValue;
    }

    @Nullable
    public final EntityLivingBase getCurrentTarget() {
        return currentTarget;
    }

    public final void setCurrentTarget(@Nullable EntityLivingBase entityLivingBase) {
        currentTarget = entityLivingBase;
    }

    private final boolean getCanFakeBlock() {
        return !((Collection)inRangeDiscoveredTargets).isEmpty();
    }

    public final boolean getBlockingStatus() {
        return blockingStatus;
    }

    public final void setBlockingStatus(boolean bl) {
        blockingStatus = bl;
    }

    public final boolean getDisplayBlocking() {
        return blockingStatus || (autoBlockValue.equals("Fake") || autoBlockValue.contains("WatchDog")) && this.getCanFakeBlock() && this.getCanBlock();
    }

    @Override
    public void onEnable() {
        if (MinecraftInstance.mc.field_71439_g == null) {
            return;
        }
        if (MinecraftInstance.mc.field_71441_e == null) {
            return;
        }
        lastCanBeSeen = false;
        blinking = false;
        this.updateTarget();
    }

    @Override
    public void onDisable() {
        TargetStrafe targetStrafe = CrossSine.INSTANCE.getModuleManager().get(TargetStrafe.class);
        Intrinsics.checkNotNull(targetStrafe);
        targetStrafe.setDoStrafe(false);
        currentTarget = null;
        hitable = false;
        if (blinking) {
            BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, true, true, false, false, false, false, false, false, false, false, false, 2044, null);
            blinking = false;
        }
        blinkLag = false;
        prevTargetEntities.clear();
        discoveredTargets.clear();
        inRangeDiscoveredTargets.clear();
        attackTimer.reset();
        clicks = 0;
        canSwing = false;
        if (swapped) {
            MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C09PacketHeldItemChange(MinecraftInstance.mc.field_71439_g.field_71071_by.field_70461_c));
            swapped = false;
        }
        this.stopBlocking();
        this.stopBlockingNoEvent();
        RotationUtils.setTargetRotationReverse(RotationUtils.serverRotation, keepDirectionValue.get() != false ? ((Number)keepDirectionTickValue.get()).intValue() + 1 : 1, rotationRevValue.get() != false ? ((Number)rotationRevTickValue.get()).intValue() + 1 : 0);
        hitable = false;
    }

    @EventTarget
    public final void onJump(@NotNull JumpEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (rotationStrafeValue.equals("Off") || currentTarget != null) {
            return;
        }
        event.setYaw(RotationUtils.targetRotation.getYaw());
    }

    @EventTarget
    public final void onStrafe(@NotNull StrafeEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (rotationStrafeValue.equals("Off") || currentTarget != null) {
            return;
        }
        if (rotationStrafeValue.equals("FullStrafe")) {
            event.setYaw(RotationUtils.targetRotation.getYaw());
        } else {
            MovementFix.INSTANCE.applyForceStrafe(true, true);
        }
    }

    @EventTarget
    public final void onSprint(@NotNull SprintEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (!rotationStrafeValue.equals("LessStrafe") || currentTarget != null) {
            return;
        }
        event.setSprint(Math.abs((double)(MathHelper.func_76142_g((float)MinecraftInstance.mc.field_71439_g.field_70177_z) - MathHelper.func_76142_g((float)RotationUtils.serverRotation.getYaw()))) < 90.0 && MovementUtils.INSTANCE.isMoving());
    }

    /*
     * Unable to fully structure code
     */
    @EventTarget
    public final void onPreUpdate(@NotNull PreUpdateEvent event) {
        block38: {
            block36: {
                Intrinsics.checkNotNullParameter(event, "event");
                if (this.getCancelRun() || KillAura.currentTarget == null) break block36;
                var2_2 = MinecraftInstance.mc.field_71439_g;
                Intrinsics.checkNotNullExpressionValue(var2_2, "mc.thePlayer");
                v0 = (Entity)var2_2;
                v1 = KillAura.currentTarget;
                Intrinsics.checkNotNull(v1);
                if (!(EntityExtensionKt.getDistanceToEntityBox(v0, (Entity)v1) <= (double)((Number)KillAura.autoBlockRangeValue.get()).floatValue())) break block36;
                var4_3 = ((String)KillAura.autoBlockValue.get()).toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(var4_3, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                var2_2 = var4_3;
                tmp = -1;
                switch (var2_2.hashCode()) {
                    case -280172556: {
                        if (var2_2.equals("watchdoga")) {
                            tmp = 1;
                        }
                        break;
                    }
                    case -280172555: {
                        if (var2_2.equals("watchdogb")) {
                            tmp = 2;
                        }
                        break;
                    }
                    case -1498505952: {
                        if (var2_2.equals("watchdogswap")) {
                            tmp = 3;
                        }
                        break;
                    }
                    case 233102203: {
                        if (var2_2.equals("vanilla")) {
                            tmp = 4;
                        }
                        break;
                    }
                }
                block6 : switch (tmp) {
                    case 4: {
                        this.startBlocking();
                        this.runAttackLoop(false);
                        break;
                    }
                    case 1: {
                        if (KillAura.blinkLag) {
                            KillAura.blinking = true;
                            BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, false, false, true, false, false, false, false, false, false, false, false, 2043, null);
                            this.stopBlockingNoEvent();
                            KillAura.blinkLag = false;
                            break;
                        }
                        this.runAttackLoop(true);
                        KillAura.blinking = false;
                        BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, true, true, false, false, false, false, false, false, false, false, false, 2044, null);
                        this.startBlockingNoEvent();
                        KillAura.blinkLag = true;
                        break;
                    }
                    case 2: {
                        if (KillAura.hyTicks >= 3) {
                            KillAura.hyTicks = 0;
                        }
                        var3_4 = KillAura.hyTicks;
                        KillAura.hyTicks = var3_4 + 1;
                        if (!KillAura.cycle) ** GOTO lbl82
                        switch (KillAura.hyTicks) {
                            case 1: {
                                KillAura.blinking = true;
                                BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, false, false, true, false, false, false, false, false, false, false, false, 2043, null);
                                this.runAttackLoop(true);
                                this.startBlockingNoEvent();
                                KillAura.blinking = false;
                                BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, true, true, false, false, false, false, false, false, false, false, false, 2044, null);
                                KillAura.blinkLag = true;
                                break;
                            }
                            case 2: {
                                if (!KillAura.blinkLag) {
                                    this.runAttackLoop(true);
                                    this.startBlockingNoEvent();
                                    KillAura.blinking = false;
                                    BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, true, true, false, false, false, false, false, false, false, false, false, 2044, null);
                                    KillAura.blinkLag = true;
                                    break;
                                }
                                break block38;
                            }
                            case 3: {
                                KillAura.cycle = false;
                            }
                        }
                        break block38;
lbl82:
                        // 1 sources

                        switch (KillAura.hyTicks) {
                            case 1: {
                                KillAura.blinking = true;
                                BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, false, false, true, false, false, false, false, false, false, false, false, 2043, null);
                                this.runAttackLoop(true);
                                this.startBlockingNoEvent();
                                KillAura.blinking = false;
                                BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, true, true, false, false, false, false, false, false, false, false, false, 2044, null);
                                KillAura.blinkLag = true;
                                break block6;
                            }
                            case 2: {
                                if (!KillAura.blinkLag) {
                                    this.runAttackLoop(true);
                                    this.startBlockingNoEvent();
                                    KillAura.blinking = false;
                                    BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, true, true, false, false, false, false, false, false, false, false, false, 2044, null);
                                    KillAura.blinkLag = true;
                                }
                                KillAura.cycle = true;
                                KillAura.hyTicks = 0;
                            }
                        }
                        break;
                    }
                    case 3: {
                        if (KillAura.hyTicks >= 3) {
                            KillAura.hyTicks = 0;
                        }
                        var3_5 = KillAura.hyTicks;
                        KillAura.hyTicks = var3_5 + 1;
                        switch (KillAura.hyTicks) {
                            case 2: {
                                BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, false, false, true, false, false, false, false, false, false, false, false, 2043, null);
                                KillAura.blinking = true;
                                KillAura.swapped = true;
                                MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C09PacketHeldItemChange(InventoryUtils.INSTANCE.getBestSwapSlot()));
                                break block6;
                            }
                            case 3: {
                                MinecraftInstance.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C09PacketHeldItemChange(MinecraftInstance.mc.field_71439_g.field_71071_by.field_70461_c));
                                KillAura.swapped = false;
                                this.runAttackLoop(true);
                                this.startBlockingNoEvent();
                                BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, true, true, false, false, false, false, false, false, false, false, false, 2044, null);
                                KillAura.blinking = false;
                            }
                        }
                    }
                }
                break block38;
            }
            if (KillAura.blinking || KillAura.blinkLag) {
                KillAura.blinking = false;
                KillAura.blinkLag = false;
                KillAura.hyTicks = 0;
                this.stopBlockingNoEvent();
                BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, true, true, false, false, false, false, false, false, false, false, false, 2044, null);
            }
        }
        if (KillAura.autoBlockValue.equals("None") || KillAura.autoBlockValue.equals("Fake")) {
            this.runAttackLoop(false);
        }
        this.updateHitable();
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        Packet<?> packet = event.getPacket();
        if (packet instanceof C09PacketHeldItemChange) {
            this.stopBlocking();
            this.stopBlockingNoEvent();
        }
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent ignoredEvent) {
        Intrinsics.checkNotNullParameter(ignoredEvent, "ignoredEvent");
        if (this.getCancelRun()) {
            currentTarget = null;
            hitable = false;
            this.stopBlocking();
            this.stopBlockingNoEvent();
            discoveredTargets.clear();
            inRangeDiscoveredTargets.clear();
            return;
        }
        this.updateTarget();
        if (currentTarget == null) {
            this.stopBlocking();
            this.stopBlockingNoEvent();
        }
        if (discoveredTargets.isEmpty()) {
            this.stopBlocking();
            this.stopBlockingNoEvent();
            return;
        }
        TargetStrafe targetStrafe = CrossSine.INSTANCE.getModuleManager().get(TargetStrafe.class);
        Intrinsics.checkNotNull(targetStrafe);
        EntityLivingBase entityLivingBase = currentTarget;
        if (entityLivingBase == null) {
            return;
        }
        targetStrafe.setTargetEntity(entityLivingBase);
    }

    private final void runAttackLoop(boolean interact) {
        if (((Boolean)nineCombat.get()).booleanValue() && CooldownHelper.INSTANCE.getAttackCooldownProgress() < 1.0) {
            return;
        }
        if (((Boolean)nineCombat.get()).booleanValue() && clicks > 0) {
            clicks = 1;
        }
        if (CpsReduceValue.get().booleanValue() && MinecraftInstance.mc.field_71439_g.field_70737_aN > 8) {
            clicks += ((Number)addCps.get()).intValue();
        }
        try {
            while (clicks > 0) {
                this.runAttack(interact);
                int n = clicks;
                clicks = n + -1;
            }
        }
        catch (IllegalStateException e) {
            return;
        }
    }

    /*
     * WARNING - void declaration
     */
    private final void runAttack(boolean interact) {
        if (this.getCancelRun()) {
            return;
        }
        if (currentTarget == null) {
            return;
        }
        if (hitable) {
            if (!targetModeValue.equals("Multi")) {
                EntityLivingBase entityLivingBase;
                if (((Boolean)raycastValue.get()).booleanValue()) {
                    Entity entity;
                    Entity entity2 = RaycastUtils.raycastEntity(this.getMaxRange(), KillAura::runAttack$lambda-0);
                    if (entity2 == null) {
                        EntityLivingBase entityLivingBase2 = currentTarget;
                        Intrinsics.checkNotNull(entityLivingBase2);
                        entity = (Entity)entityLivingBase2;
                    } else {
                        entity = entity2;
                    }
                    entityLivingBase = (EntityLivingBase)entity;
                } else {
                    EntityLivingBase entityLivingBase3 = currentTarget;
                    entityLivingBase = entityLivingBase3;
                    Intrinsics.checkNotNull(entityLivingBase3);
                }
                this.attackEntity(entityLivingBase, interact);
            } else {
                Iterable $this$forEachIndexed$iv = inRangeDiscoveredTargets;
                boolean $i$f$forEachIndexed = false;
                int index$iv = 0;
                for (Object item$iv : $this$forEachIndexed$iv) {
                    void entity;
                    int n = index$iv;
                    index$iv = n + 1;
                    if (n < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    EntityLivingBase entityLivingBase = (EntityLivingBase)item$iv;
                    int index2 = n;
                    boolean bl = false;
                    if (((Number)limitedMultiTargetsValue.get()).intValue() != 0 && index2 >= ((Number)limitedMultiTargetsValue.get()).intValue()) continue;
                    INSTANCE.attackEntity((EntityLivingBase)entity, interact);
                }
            }
            if (targetModeValue.equals("Switch")) {
                if (switchTimer.hasTimePassed(((Number)switchDelayValue.get()).intValue())) {
                    EntityLivingBase entityLivingBase = currentTarget;
                    Intrinsics.checkNotNull(entityLivingBase);
                    prevTargetEntities.add(entityLivingBase.func_145782_y());
                    switchTimer.reset();
                }
            } else {
                EntityLivingBase entityLivingBase = currentTarget;
                Intrinsics.checkNotNull(entityLivingBase);
                prevTargetEntities.add(entityLivingBase.func_145782_y());
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    private final void updateTarget() {
        void $this$filterTo$iv$iv;
        void $this$filter$iv;
        float fov = (Boolean)fovValue.get() != false ? ((Number)fovDisValue.get()).floatValue() : 180.0f;
        boolean switchMode = targetModeValue.equals("Switch");
        discoveredTargets.clear();
        for (Entity entity : MinecraftInstance.mc.field_71441_e.field_72996_f) {
            if (!(entity instanceof EntityLivingBase) || !EntityUtils.INSTANCE.isSelected(entity, true) || switchMode && prevTargetEntities.contains(((EntityLivingBase)entity).func_145782_y())) continue;
            EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
            Intrinsics.checkNotNullExpressionValue(entityPlayerSP, "mc.thePlayer");
            double distance = EntityExtensionKt.getDistanceToEntityBox((Entity)entityPlayerSP, entity);
            double entityFov = RotationUtils.getRotationDifference(entity);
            if (!(distance <= (double)((Number)discoverRangeValue.get()).floatValue()) || !(fov == 180.0f) && !(entityFov <= (double)fov)) continue;
            discoveredTargets.add((EntityLivingBase)entity);
        }
        String distance = ((String)priorityValue.get()).toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(distance, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        switch (distance) {
            case "distance": {
                List<EntityLivingBase> $this$sortBy$iv = discoveredTargets;
                boolean $i$f$sortBy = false;
                if ($this$sortBy$iv.size() <= 1) break;
                CollectionsKt.sortWith($this$sortBy$iv, new Comparator(){

                    public final int compare(T a, T b) {
                        EntityLivingBase it = (EntityLivingBase)a;
                        boolean bl = false;
                        EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                        Intrinsics.checkNotNullExpressionValue(entityPlayerSP, "mc.thePlayer");
                        Comparable comparable = Double.valueOf(EntityExtensionKt.getDistanceToEntityBox((Entity)entityPlayerSP, (Entity)it));
                        it = (EntityLivingBase)b;
                        Comparable comparable2 = comparable;
                        bl = false;
                        entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                        Intrinsics.checkNotNullExpressionValue(entityPlayerSP, "mc.thePlayer");
                        return ComparisonsKt.compareValues(comparable2, EntityExtensionKt.getDistanceToEntityBox((Entity)entityPlayerSP, (Entity)it));
                    }
                });
                break;
            }
            case "health": {
                List<EntityLivingBase> $this$sortBy$iv = discoveredTargets;
                boolean $i$f$sortBy = false;
                if ($this$sortBy$iv.size() <= 1) break;
                CollectionsKt.sortWith($this$sortBy$iv, new Comparator(){

                    public final int compare(T a, T b) {
                        EntityLivingBase it = (EntityLivingBase)a;
                        boolean bl = false;
                        Comparable comparable = Float.valueOf(it.func_110143_aJ() + it.func_110139_bj());
                        it = (EntityLivingBase)b;
                        Comparable comparable2 = comparable;
                        bl = false;
                        return ComparisonsKt.compareValues(comparable2, (Comparable)Float.valueOf(it.func_110143_aJ() + it.func_110139_bj()));
                    }
                });
                break;
            }
            case "direction": {
                List<EntityLivingBase> $this$sortBy$iv = discoveredTargets;
                boolean $i$f$sortBy = false;
                if ($this$sortBy$iv.size() <= 1) break;
                CollectionsKt.sortWith($this$sortBy$iv, new Comparator(){

                    public final int compare(T a, T b) {
                        EntityLivingBase it = (EntityLivingBase)a;
                        boolean bl = false;
                        Comparable comparable = Double.valueOf(RotationUtils.getRotationDifference((Entity)it));
                        it = (EntityLivingBase)b;
                        Comparable comparable2 = comparable;
                        bl = false;
                        return ComparisonsKt.compareValues(comparable2, RotationUtils.getRotationDifference((Entity)it));
                    }
                });
                break;
            }
            case "livingtime": {
                List<EntityLivingBase> $this$sortBy$iv = discoveredTargets;
                boolean $i$f$sortBy = false;
                if ($this$sortBy$iv.size() <= 1) break;
                CollectionsKt.sortWith($this$sortBy$iv, new Comparator(){

                    public final int compare(T a, T b) {
                        EntityLivingBase it = (EntityLivingBase)a;
                        boolean bl = false;
                        it = (EntityLivingBase)b;
                        Comparable comparable = Integer.valueOf(-it.field_70173_aa);
                        bl = false;
                        return ComparisonsKt.compareValues(comparable, -it.field_70173_aa);
                    }
                });
                break;
            }
            case "armor": {
                List<EntityLivingBase> $this$sortBy$iv = discoveredTargets;
                boolean $i$f$sortBy = false;
                if ($this$sortBy$iv.size() <= 1) break;
                CollectionsKt.sortWith($this$sortBy$iv, new Comparator(){

                    public final int compare(T a, T b) {
                        EntityLivingBase it = (EntityLivingBase)a;
                        boolean bl = false;
                        Comparable comparable = Integer.valueOf(it.func_70658_aO());
                        it = (EntityLivingBase)b;
                        Comparable comparable2 = comparable;
                        bl = false;
                        return ComparisonsKt.compareValues(comparable2, it.func_70658_aO());
                    }
                });
                break;
            }
            case "hurtresistance": {
                List<EntityLivingBase> $this$sortBy$iv = discoveredTargets;
                boolean $i$f$sortBy = false;
                if ($this$sortBy$iv.size() <= 1) break;
                CollectionsKt.sortWith($this$sortBy$iv, new Comparator(){

                    public final int compare(T a, T b) {
                        EntityLivingBase it = (EntityLivingBase)a;
                        boolean bl = false;
                        it = (EntityLivingBase)b;
                        Comparable comparable = Integer.valueOf(it.field_70172_ad);
                        bl = false;
                        return ComparisonsKt.compareValues(comparable, it.field_70172_ad);
                    }
                });
                break;
            }
            case "hurttime": {
                List<EntityLivingBase> $this$sortBy$iv = discoveredTargets;
                boolean $i$f$sortBy = false;
                if ($this$sortBy$iv.size() <= 1) break;
                CollectionsKt.sortWith($this$sortBy$iv, new Comparator(){

                    public final int compare(T a, T b) {
                        EntityLivingBase it = (EntityLivingBase)a;
                        boolean bl = false;
                        it = (EntityLivingBase)b;
                        Comparable comparable = Integer.valueOf(it.field_70737_aN);
                        bl = false;
                        return ComparisonsKt.compareValues(comparable, it.field_70737_aN);
                    }
                });
                break;
            }
            case "healthabsorption": {
                List<EntityLivingBase> $this$sortBy$iv = discoveredTargets;
                boolean $i$f$sortBy = false;
                if ($this$sortBy$iv.size() <= 1) break;
                CollectionsKt.sortWith($this$sortBy$iv, new Comparator(){

                    public final int compare(T a, T b) {
                        EntityLivingBase it = (EntityLivingBase)a;
                        boolean bl = false;
                        Comparable comparable = Float.valueOf(it.func_110143_aJ() + it.func_110139_bj());
                        it = (EntityLivingBase)b;
                        Comparable comparable2 = comparable;
                        bl = false;
                        return ComparisonsKt.compareValues(comparable2, (Comparable)Float.valueOf(it.func_110143_aJ() + it.func_110139_bj()));
                    }
                });
                break;
            }
            case "regenamplifier": {
                List<EntityLivingBase> $this$sortBy$iv = discoveredTargets;
                boolean $i$f$sortBy = false;
                if ($this$sortBy$iv.size() <= 1) break;
                CollectionsKt.sortWith($this$sortBy$iv, new Comparator(){

                    public final int compare(T a, T b) {
                        EntityLivingBase it = (EntityLivingBase)a;
                        boolean bl = false;
                        int n = it.func_70644_a(Potion.field_76428_l) ? it.func_70660_b(Potion.field_76428_l).func_76458_c() : -1;
                        it = (EntityLivingBase)b;
                        Comparable comparable = Integer.valueOf(n);
                        bl = false;
                        return ComparisonsKt.compareValues(comparable, it.func_70644_a(Potion.field_76428_l) ? it.func_70660_b(Potion.field_76428_l).func_76458_c() : -1);
                    }
                });
            }
        }
        inRangeDiscoveredTargets.clear();
        Iterator<EntityLivingBase> iterator2 = discoveredTargets;
        List<EntityLivingBase> list = inRangeDiscoveredTargets;
        boolean $i$f$filter = false;
        void $i$f$sortBy = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            EntityLivingBase it = (EntityLivingBase)element$iv$iv;
            boolean bl = false;
            EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
            Intrinsics.checkNotNullExpressionValue(entityPlayerSP, "mc.thePlayer");
            if (!(EntityExtensionKt.getDistanceToEntityBox((Entity)entityPlayerSP, (Entity)it) < (double)((Number)INSTANCE.getDiscoverRangeValue().get()).floatValue())) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        list.addAll((List)destination$iv$iv);
        if (inRangeDiscoveredTargets.isEmpty() && !((Collection)prevTargetEntities).isEmpty()) {
            prevTargetEntities.clear();
            this.updateTarget();
            return;
        }
        for (EntityLivingBase entity : discoveredTargets) {
            if (!this.updateRotations((Entity)entity)) {
                boolean success = false;
                continue;
            }
            EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
            Intrinsics.checkNotNullExpressionValue(entityPlayerSP, "mc.thePlayer");
            if (!(EntityExtensionKt.getDistanceToEntityBox((Entity)entityPlayerSP, (Entity)entity) < (double)((Number)discoverRangeValue.get()).floatValue())) continue;
            currentTarget = entity;
            TargetStrafe targetStrafe = CrossSine.INSTANCE.getModuleManager().get(TargetStrafe.class);
            Intrinsics.checkNotNull(targetStrafe);
            EntityLivingBase entityLivingBase = currentTarget;
            if (entityLivingBase == null) {
                return;
            }
            targetStrafe.setTargetEntity(entityLivingBase);
            TargetStrafe targetStrafe2 = CrossSine.INSTANCE.getModuleManager().get(TargetStrafe.class);
            Intrinsics.checkNotNull(targetStrafe2);
            TargetStrafe targetStrafe3 = CrossSine.INSTANCE.getModuleManager().get(TargetStrafe.class);
            Intrinsics.checkNotNull(targetStrafe3);
            targetStrafe2.setDoStrafe(targetStrafe3.toggleStrafe());
            return;
        }
        currentTarget = null;
        TargetStrafe targetStrafe = CrossSine.INSTANCE.getModuleManager().get(TargetStrafe.class);
        Intrinsics.checkNotNull(targetStrafe);
        targetStrafe.setDoStrafe(false);
    }

    private final void attackEntity(EntityLivingBase entity, boolean interact) {
        AttackEvent event = new AttackEvent((Entity)entity);
        CrossSine.INSTANCE.getEventManager().callEvent(event);
        if (event.isCancelled()) {
            return;
        }
        ProtocolFixer.sendFixedAttack((EntityPlayer)MinecraftInstance.mc.field_71439_g, (Entity)entity, StringsKt.equals((String)swingValue.get(), "packet", true));
        if (interact) {
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C02PacketUseEntity((Entity)entity, C02PacketUseEntity.Action.INTERACT));
        }
        if (MinecraftInstance.mc.field_71442_b.field_78779_k != WorldSettings.GameType.SPECTATOR) {
            MinecraftInstance.mc.field_71439_g.func_71059_n((Entity)entity);
        }
        CooldownHelper.INSTANCE.resetLastAttackedTicks();
    }

    private final boolean updateRotations(Entity entity) {
        Rotation rotation;
        String string;
        if (rotationModeValue.equals("None")) {
            return true;
        }
        double entityFov = RotationUtils.getRotationDifference(RotationUtils.toRotation(RotationUtils.getCenter(EntityExtensionKt.getHitBox(entity)), true), RotationUtils.serverRotation);
        if (entityFov <= (double)MinecraftInstance.mc.field_71474_y.field_74334_X) {
            lastCanBeSeen = true;
        } else if (lastCanBeSeen) {
            rotationTimer.reset();
            lastCanBeSeen = false;
        }
        if (predictValue.get().booleanValue()) {
            predictAmount = RandomUtils.INSTANCE.nextFloat(((Number)maxPredictSizeValue.get()).floatValue(), ((Number)minPredictSizeValue.get()).floatValue());
        }
        AxisAlignedBB boundingBox = getAABB.invoke(entity);
        switch ((String)rotationModeValue.get()) {
            case "Smooth2": 
            case "Smooth": {
                string = "CenterLine";
                break;
            }
            case "SmoothCenter2": {
                string = "CenterBody";
                break;
            }
            case "Normal": {
                string = "HalfUp";
                break;
            }
            case "Center": 
            case "SmoothCenter": {
                string = "CenterHead";
                break;
            }
            default: {
                string = "HalfUp";
            }
        }
        String rModes = string;
        VecRotation vecRotation = RotationUtils.calculateCenter(rModes, (Boolean)randomCenterModeValue.get(), ((Number)randomCenRangeValue.get()).floatValue(), false, boundingBox, predictValue.get(), true);
        if (vecRotation == null) {
            return false;
        }
        Rotation directRotation = vecRotation.component2();
        double diffAngle = RotationUtils.getRotationDifference(RotationUtils.serverRotation, directRotation);
        if (diffAngle < 0.0) {
            diffAngle = -diffAngle;
        }
        if (diffAngle > 180.0) {
            diffAngle = 180.0;
        }
        double calculateSpeed = diffAngle / (double)360 * ((Number)maxTurnSpeedValue.get()).doubleValue() + (1.0 - diffAngle / (double)360) * ((Number)minTurnSpeedValue.get()).doubleValue();
        switch ((String)rotationModeValue.get()) {
            case "Center": {
                rotation = RotationUtils.limitAngleChange(RotationUtils.serverRotation, directRotation, (float)(Math.random() * (double)(this.maxSpeedRot() - this.minSpeedRot()) + (double)this.minSpeedRot()));
                break;
            }
            case "Smooth": {
                rotation = RotationUtils.limitAngleChange(RotationUtils.serverRotation, directRotation, (float)calculateSpeed);
                break;
            }
            case "Smooth2": {
                rotation = RotationUtils.limitAngleChange(RotationUtils.serverRotation, directRotation, (float)(diffAngle / 1.5));
                break;
            }
            case "SmoothCenter": {
                rotation = RotationUtils.limitAngleChange(RotationUtils.serverRotation, directRotation, (float)calculateSpeed);
                break;
            }
            case "SmoothCenter2": {
                rotation = RotationUtils.limitAngleChange(RotationUtils.serverRotation, directRotation, (float)calculateSpeed);
                break;
            }
            case "Normal": {
                rotation = RotationUtils.limitAngleChange(RotationUtils.serverRotation, directRotation, (float)diffAngle);
                break;
            }
            default: {
                return true;
            }
        }
        Rotation rotation2 = rotation;
        Intrinsics.checkNotNullExpressionValue(rotation2, "when (rotationModeValue.\u2026 -> return true\n        }");
        Rotation rotation3 = rotation2;
        if (silentRotationValue.get().booleanValue() && ((Boolean)spinRotation.get()).booleanValue()) {
            RotationUtils.setFakeRotationReverse(new Rotation(yaw, pitch), rotation3, keepDirectionValue.get() != false ? ((Number)keepDirectionTickValue.get()).intValue() : 1, rotationRevValue.get() != false ? ((Number)rotationRevTickValue.get()).intValue() : 0);
        } else if (silentRotationValue.get().booleanValue()) {
            RotationUtils.setTargetRotationReverse(rotation3, keepDirectionValue.get() != false ? ((Number)keepDirectionTickValue.get()).intValue() : 1, rotationRevValue.get() != false ? ((Number)rotationRevTickValue.get()).intValue() : 0);
        } else {
            rotation2 = MinecraftInstance.mc.field_71439_g;
            Intrinsics.checkNotNullExpressionValue(rotation2, "mc.thePlayer");
            rotation3.toPlayer((EntityPlayer)rotation2);
        }
        return true;
    }

    /*
     * Unable to fully structure code
     */
    private final void updateHitable() {
        if (KillAura.currentTarget == null) {
            KillAura.canSwing = false;
            KillAura.hitable = false;
            return;
        }
        var3_1 = MinecraftInstance.mc.field_71439_g;
        Intrinsics.checkNotNullExpressionValue(var3_1, "mc.thePlayer");
        v0 = KillAura.currentTarget;
        if (v0 == null) {
            throw new NullPointerException("null cannot be cast to non-null type net.minecraft.entity.Entity");
        }
        entityDist = EntityExtensionKt.getDistanceToEntityBox((Entity)var3_1, (Entity)v0);
        v1 = KillAura.canSwing = entityDist <= (double)((Number)KillAura.swingRangeValue.get()).floatValue();
        if (((Boolean)KillAura.hitAbleValue.get()).booleanValue()) {
            KillAura.hitable = entityDist <= (double)this.getMaxRange();
            return;
        }
        if ((float)this.maxSpeedRot() <= 0.0f) {
            KillAura.hitable = true;
            return;
        }
        var4_3 = MinecraftInstance.mc.field_71439_g;
        Intrinsics.checkNotNullExpressionValue(var4_3, "mc.thePlayer");
        wallTrace = EntityExtensionKt.rayTraceWithServerSideRotation((Entity)var4_3, entityDist);
        if (!RotationUtils.isFaced((Entity)KillAura.currentTarget, this.getMaxRange())) ** GOTO lbl-1000
        if (entityDist < (double)((Number)KillAura.discoverRangeValue.get()).floatValue()) ** GOTO lbl-1000
        v2 = wallTrace;
        if ((v2 == null ? null : v2.field_72313_a) != MovingObjectPosition.MovingObjectType.BLOCK) lbl-1000:
        // 2 sources

        {
            v3 = true;
        } else lbl-1000:
        // 2 sources

        {
            v3 = false;
        }
        KillAura.hitable = v3;
    }

    private final void startBlocking() {
        if (!blockingStatus) {
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C08PacketPlayerBlockPlacement(MinecraftInstance.mc.field_71439_g.field_71071_by.func_70448_g()));
            blockingStatus = true;
        }
    }

    private final void stopBlocking() {
        if (blockingStatus) {
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.field_177992_a, EnumFacing.DOWN));
            blockingStatus = false;
        }
    }

    private final void startBlockingNoEvent() {
        if (!noEventBlocking) {
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C08PacketPlayerBlockPlacement(MinecraftInstance.mc.field_71439_g.field_71071_by.func_70448_g()));
            noEventBlocking = true;
        }
    }

    private final void stopBlockingNoEvent() {
        if (noEventBlocking) {
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.field_177992_a, EnumFacing.DOWN));
            noEventBlocking = false;
        }
    }

    private final int maxSpeedRot() {
        return ((Number)maxTurnSpeedValue.get()).intValue();
    }

    private final int minSpeedRot() {
        return ((Number)minTurnSpeedValue.get()).intValue();
    }

    @EventTarget
    public final void onRender3D(@NotNull Render3DEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        yaw += (float)5;
        if (yaw > 180.0f) {
            yaw = -180.0f;
        } else if (yaw < -180.0f) {
            yaw = 180.0f;
        }
        pitch = 150.0f;
        if (currentTarget != null && attackTimer.hasTimePassed(attackDelay)) {
            int n = clicks;
            clicks = n + 1;
            attackTimer.reset();
            MouseUtils.INSTANCE.setLeftClicked(true);
            CPSCounter.registerClick(CPSCounter.MouseButton.LEFT);
            attackDelay = this.getAttackDelay(((Number)minCpsValue.get()).intValue(), ((Number)maxCpsValue.get()).intValue());
        } else if (attackTimer.hasTimePassed(30L)) {
            MouseUtils.INSTANCE.setLeftClicked(GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74312_F));
        }
        if (currentTarget != null) {
            String string = ((String)markValue.get()).toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            String string2 = string;
            if (Intrinsics.areEqual(string2, "modern")) {
                EntityLivingBase entityLivingBase = currentTarget;
                Intrinsics.checkNotNull(entityLivingBase);
                this.draw(entityLivingBase, event);
            } else if (Intrinsics.areEqual(string2, "box")) {
                EntityLivingBase entityLivingBase = currentTarget;
                Intrinsics.checkNotNull(entityLivingBase);
                RenderUtils.drawEntityBox((Entity)entityLivingBase, ClientTheme.getColorWithAlpha$default(ClientTheme.INSTANCE, 0, 120, false, 4, null), false, true, 1.0f);
            }
            GlStateManager.func_179117_G();
        }
    }

    private final long getAttackDelay(int minCps, int maxCps) {
        return TimeUtils.INSTANCE.randomClickDelay(RangesKt.coerceAtMost(minCps, maxCps), RangesKt.coerceAtLeast(minCps, maxCps));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final boolean getCancelRun() {
        if (MinecraftInstance.mc.field_71439_g.func_175149_v()) return true;
        EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
        Intrinsics.checkNotNullExpressionValue(entityPlayerSP, "mc.thePlayer");
        if (!this.isAlive((EntityLivingBase)entityPlayerSP)) return true;
        if (((Boolean)blinkCheck.get()).booleanValue()) {
            Blink blink = CrossSine.INSTANCE.getModuleManager().get(Blink.class);
            Intrinsics.checkNotNull(blink);
            if (blink.getState()) return true;
        }
        FreeCam freeCam = CrossSine.INSTANCE.getModuleManager().get(FreeCam.class);
        Intrinsics.checkNotNull(freeCam);
        if (freeCam.getState()) return true;
        if (((Boolean)noScaffValue.get()).booleanValue()) {
            Scaffold scaffold = CrossSine.INSTANCE.getModuleManager().get(Scaffold.class);
            Intrinsics.checkNotNull(scaffold);
            if (scaffold.getState()) return true;
        }
        if (((Boolean)noFlyValue.get()).booleanValue()) {
            Flight flight = CrossSine.INSTANCE.getModuleManager().get(Flight.class);
            Intrinsics.checkNotNull(flight);
            if (flight.getState()) return true;
        }
        if ((Boolean)onWeapon.get() == false) return false;
        if (MinecraftInstance.mc.field_71439_g.func_70694_bm() == null) return true;
        if (MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemSword) return false;
        if (MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemPickaxe) return false;
        if (MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemAxe) return false;
        return true;
    }

    public final void draw(@NotNull EntityLivingBase entity, @NotNull Render3DEvent event) {
        Intrinsics.checkNotNullParameter(entity, "entity");
        Intrinsics.checkNotNullParameter(event, "event");
        int everyTime = 3000;
        int drawTime = (int)(System.currentTimeMillis() % (long)everyTime);
        boolean drawMode = drawTime > everyTime / 2;
        double drawPercent = (double)drawTime / ((double)everyTime / 2.0);
        drawPercent = !drawMode ? 1.0 - drawPercent : (drawPercent -= 1.0);
        drawPercent = EaseUtils.INSTANCE.easeInOutQuad(drawPercent);
        MinecraftInstance.mc.field_71460_t.func_175072_h();
        GL11.glPushMatrix();
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)2929);
        GL11.glDisable((int)2884);
        GL11.glShadeModel((int)7425);
        MinecraftInstance.mc.field_71460_t.func_175072_h();
        AxisAlignedBB bb = entity.func_174813_aQ();
        double radius = (bb.field_72336_d - bb.field_72340_a + (bb.field_72334_f - bb.field_72339_c)) * (double)0.5f;
        double height = bb.field_72337_e - bb.field_72338_b;
        double x = entity.field_70142_S + (entity.field_70165_t - entity.field_70142_S) * (double)event.getPartialTicks() - MinecraftInstance.mc.func_175598_ae().field_78730_l;
        double y = entity.field_70137_T + (entity.field_70163_u - entity.field_70137_T) * (double)event.getPartialTicks() - MinecraftInstance.mc.func_175598_ae().field_78731_m + height * drawPercent;
        double z = entity.field_70136_U + (entity.field_70161_v - entity.field_70136_U) * (double)event.getPartialTicks() - MinecraftInstance.mc.func_175598_ae().field_78728_n;
        double eased = height / (double)3 * (drawPercent > 0.5 ? 1.0 - drawPercent : drawPercent) * (double)(drawMode ? -1 : 1);
        int n = 5;
        int n2 = ProgressionUtilKt.getProgressionLastElement(5, 360, 5);
        if (n <= n2) {
            int i;
            do {
                i = n;
                n += 5;
                double x1 = x - Math.sin((double)i * Math.PI / (double)180.0f) * radius;
                double z1 = z + Math.cos((double)i * Math.PI / (double)180.0f) * radius;
                double x2 = x - Math.sin((double)(i - 5) * Math.PI / (double)180.0f) * radius;
                double z2 = z + Math.cos((double)(i - 5) * Math.PI / (double)180.0f) * radius;
                GL11.glBegin((int)7);
                RenderUtils.glColor(ClientTheme.INSTANCE.getColorWithAlpha(0, 0, true));
                GL11.glVertex3d((double)x1, (double)(y + eased), (double)z1);
                GL11.glVertex3d((double)x2, (double)(y + eased), (double)z2);
                RenderUtils.glColor(ClientTheme.INSTANCE.getColorWithAlpha(0, 150, true));
                GL11.glVertex3d((double)x2, (double)y, (double)z2);
                GL11.glVertex3d((double)x1, (double)y, (double)z1);
                GL11.glEnd();
            } while (i != n2);
        }
        GL11.glEnable((int)2884);
        GL11.glShadeModel((int)7424);
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glEnable((int)2929);
        GL11.glDisable((int)2848);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3553);
        GL11.glPopMatrix();
    }

    private final boolean isAlive(EntityLivingBase entity) {
        return entity.func_70089_S() && entity.func_110143_aJ() > 0.0f;
    }

    private final boolean getCanBlock() {
        return MinecraftInstance.mc.field_71439_g.func_70694_bm() != null && MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemSword;
    }

    private final float getMaxRange() {
        return Math.max(((Number)rangeValue.get()).floatValue(), ((Number)rangeValue.get()).floatValue());
    }

    @Override
    @NotNull
    public String getTag() {
        return (String)targetModeValue.get();
    }

    private static final boolean runAttack$lambda-0(Entity it) {
        return it instanceof EntityLivingBase && !(it instanceof EntityArmorStand) && (raycastTargetValue.get() == false || EntityUtils.INSTANCE.canRayCast(it)) && !EntityUtils.INSTANCE.isFriend(it);
    }

    public static final /* synthetic */ float access$getMaxRange(KillAura $this) {
        return $this.getMaxRange();
    }

    public static final /* synthetic */ BoolValue access$getFovValue$p() {
        return fovValue;
    }

    public static final /* synthetic */ BoolValue access$getNineCombat$p() {
        return nineCombat;
    }

    public static final /* synthetic */ Value access$getCpsReduceValue$p() {
        return CpsReduceValue;
    }

    public static final /* synthetic */ IntegerValue access$getMinCpsValue$p() {
        return minCpsValue;
    }

    public static final /* synthetic */ void access$setAttackDelay$p(long l) {
        attackDelay = l;
    }

    public static final /* synthetic */ long access$getAttackDelay(KillAura $this, int minCps, int maxCps) {
        return $this.getAttackDelay(minCps, maxCps);
    }

    public static final /* synthetic */ IntegerValue access$getMaxCpsValue$p() {
        return maxCpsValue;
    }

    public static final /* synthetic */ ListValue access$getTargetModeValue$p() {
        return targetModeValue;
    }

    public static final /* synthetic */ Value access$getSilentRotationValue$p() {
        return silentRotationValue;
    }

    public static final /* synthetic */ ListValue access$getRotationModeValue$p() {
        return rotationModeValue;
    }

    public static final /* synthetic */ void access$setBlinking$p(boolean bl) {
        blinking = bl;
    }

    public static final /* synthetic */ void access$setBlinkLag$p(boolean bl) {
        blinkLag = bl;
    }

    public static final /* synthetic */ IntegerValue access$getMinTurnSpeedValue$p() {
        return minTurnSpeedValue;
    }

    public static final /* synthetic */ IntegerValue access$getMaxTurnSpeedValue$p() {
        return maxTurnSpeedValue;
    }

    public static final /* synthetic */ Value access$getRotationRevValue$p() {
        return rotationRevValue;
    }

    public static final /* synthetic */ Value access$getKeepDirectionValue$p() {
        return keepDirectionValue;
    }

    public static final /* synthetic */ BoolValue access$getRandomCenterModeValue$p() {
        return randomCenterModeValue;
    }

    public static final /* synthetic */ BoolValue access$getRaycastValue$p() {
        return raycastValue;
    }

    public static final /* synthetic */ FloatValue access$getMinPredictSizeValue$p() {
        return minPredictSizeValue;
    }

    public static final /* synthetic */ Value access$getPredictValue$p() {
        return predictValue;
    }

    public static final /* synthetic */ FloatValue access$getMaxPredictSizeValue$p() {
        return maxPredictSizeValue;
    }

    public static final /* synthetic */ float access$getPredictAmount$p() {
        return predictAmount;
    }

    static {
        String[] stringArray = new String[]{"Health", "Distance", "Direction", "LivingTime", "Armor", "HurtResistance", "HurtTime", "HealthAbsorption", "RegenAmplifier"};
        priorityValue = new ListValue("Priority", stringArray, "Distance");
        stringArray = new String[]{"Single", "Switch", "Multi"};
        targetModeValue = new ListValue("Target-Mode", stringArray, "Switch");
        switchDelayValue = new IntegerValue("Switch-Delay", 15, 1, 2000).displayable(switchDelayValue.1.INSTANCE);
        limitedMultiTargetsValue = new IntegerValue("Limited-Multi-Targets", 0, 0, 50).displayable(limitedMultiTargetsValue.1.INSTANCE);
        text7 = new TitleValue("Limit-Use");
        blinkCheck = new BoolValue("Blink-Check", true);
        noScaffValue = new BoolValue("No-Scaffold", true);
        noFlyValue = new BoolValue("No-Fly", false);
        onWeapon = new BoolValue("On-Weapon", false);
        text9 = new TitleValue("Bypass");
        stringArray = new String[]{"Normal", "Packet", "None"};
        swingValue = new ListValue("Swing", stringArray, "Normal");
        stringArray = new String[]{"Off", "FullStrafe", "LessStrafe"};
        rotationStrafeValue = new ListValue("Fix-Movement", stringArray, "Off").displayable(rotationStrafeValue.1.INSTANCE);
        hitAbleValue = new BoolValue("Always-Attack", true);
        text11 = new TitleValue("AutoBlock");
        String[] stringArray2 = new String[]{"Vanilla", "WatchDogA", "WatchDogB", "WatchDogSwap", "Fake", "None"};
        stringArray = stringArray2;
        autoBlockValue = new ListValue(stringArray){

            protected void onChanged(@NotNull String oldValue, @NotNull String newValue) {
                Intrinsics.checkNotNullParameter(oldValue, "oldValue");
                Intrinsics.checkNotNullParameter(newValue, "newValue");
                BlinkUtils.setBlinkState$default(BlinkUtils.INSTANCE, true, true, false, false, false, false, false, false, false, false, false, 2044, null);
                KillAura.access$setBlinking$p(false);
                KillAura.access$setBlinkLag$p(false);
            }
        };
        autoBlockRangeValue = new FloatValue(){

            protected void onChanged(float oldValue, float newValue) {
                float i = ((Number)KillAura.INSTANCE.getDiscoverRangeValue().get()).floatValue();
                if (i < newValue) {
                    this.set(Float.valueOf(i));
                }
            }
        }.displayable(autoBlockRangeValue.2.INSTANCE);
        text13 = new TitleValue("Rotation");
        stringArray = new String[]{"None", "Center", "Normal", "Smooth", "Smooth2", "SmoothCenter", "SmoothCenter2"};
        rotationModeValue = new ListValue("RotationMode", stringArray, "Smooth");
        spinRotation = new BoolValue("SpinRotation", false);
        silentRotationValue = new BoolValue("SilentRotation", true).displayable(silentRotationValue.1.INSTANCE);
        maxTurnSpeedValue = (IntegerValue)new IntegerValue(){

            protected void onChanged(int oldValue, int newValue) {
                int v = ((Number)KillAura.access$getMinTurnSpeedValue$p().get()).intValue();
                if (v > newValue) {
                    this.set(v);
                }
            }
        }.displayable(maxTurnSpeedValue.2.INSTANCE);
        minTurnSpeedValue = (IntegerValue)new IntegerValue(){

            protected void onChanged(int oldValue, int newValue) {
                int v = ((Number)KillAura.access$getMaxTurnSpeedValue$p().get()).intValue();
                if (v < newValue) {
                    this.set(v);
                }
            }
        }.displayable(minTurnSpeedValue.2.INSTANCE);
        rotationRevValue = new BoolValue("RotationReverse", false).displayable(rotationRevValue.1.INSTANCE);
        rotationRevTickValue = new IntegerValue("RotationReverseTick", 5, 1, 20).displayable(rotationRevTickValue.1.INSTANCE);
        keepDirectionValue = new BoolValue("KeepDirection", true).displayable(keepDirectionValue.1.INSTANCE);
        keepDirectionTickValue = new IntegerValue("KeepDirectionTick", 15, 1, 20).displayable(keepDirectionTickValue.1.INSTANCE);
        randomCenterModeValue = new BoolValue("RandomCenter", false);
        randomCenRangeValue = new FloatValue("RandomRange", 0.0f, 0.0f, 1.2f).displayable(randomCenRangeValue.1.INSTANCE);
        text15 = new TitleValue("MoreBypass");
        raycastValue = new BoolValue("RayCast", true);
        raycastTargetValue = new BoolValue("RaycastOnlyTarget", false).displayable(raycastTargetValue.1.INSTANCE);
        predictValue = new BoolValue("Predict", true).displayable(predictValue.1.INSTANCE);
        maxPredictSizeValue = (FloatValue)new FloatValue(){

            protected void onChanged(float oldValue, float newValue) {
                float v = ((Number)KillAura.access$getMinPredictSizeValue$p().get()).floatValue();
                if (v > newValue) {
                    this.set(Float.valueOf(v));
                }
            }
        }.displayable(maxPredictSizeValue.2.INSTANCE);
        minPredictSizeValue = (FloatValue)new FloatValue(){

            protected void onChanged(float oldValue, float newValue) {
                float v = ((Number)KillAura.access$getMaxPredictSizeValue$p().get()).floatValue();
                if (v < newValue) {
                    this.set(Float.valueOf(v));
                }
            }
        }.displayable(minPredictSizeValue.2.INSTANCE);
        render = new TitleValue("Render");
        stringArray = new String[]{"Modern", "Box", "None"};
        markValue = new ListValue("TargetESP", stringArray, "Box");
        prevTargetEntities = new ArrayList();
        discoveredTargets = new ArrayList();
        inRangeDiscoveredTargets = new ArrayList();
        attackTimer = new MSTimer();
        switchTimer = new MSTimer();
        rotationTimer = new MSTimer();
        predictAmount = 1.0f;
        getAABB = getAABB.1.INSTANCE;
    }
}

