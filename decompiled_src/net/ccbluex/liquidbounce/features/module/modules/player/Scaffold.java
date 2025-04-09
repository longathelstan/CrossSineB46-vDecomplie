/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.player;

import java.util.Iterator;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.PostUpdateEvent;
import net.ccbluex.liquidbounce.event.PreUpdateEvent;
import net.ccbluex.liquidbounce.event.SprintEvent;
import net.ccbluex.liquidbounce.event.StrafeEvent;
import net.ccbluex.liquidbounce.event.TickEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed;
import net.ccbluex.liquidbounce.features.module.modules.player.Scaffold;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.FloatValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.CPSCounter;
import net.ccbluex.liquidbounce.utils.InventoryUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MouseUtils;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.PlaceRotation;
import net.ccbluex.liquidbounce.utils.PlayerUtils;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.SlotUtils;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.block.PlaceInfo;
import net.ccbluex.liquidbounce.utils.extensions.EntityExtensionKt;
import net.ccbluex.liquidbounce.utils.extensions.OtherExtensionKt;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.ccbluex.liquidbounce.utils.timer.TimerMS;
import net.minecraft.block.BlockAir;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(name="Scaffold", category=ModuleCategory.PLAYER)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u00d8\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0013\n\u0002\b+\n\u0002\u0010\u000e\n\u0002\b\u0018\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0006\n\u0002\b\r\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\n\u0010\u0085\u0001\u001a\u00030\u0086\u0001H\u0002J\u0013\u0010\u0087\u0001\u001a\u00030\u0086\u00012\u0007\u0010\u0088\u0001\u001a\u00020\u0005H\u0002JP\u0010\u0089\u0001\u001a\u0005\u0018\u00010\u008a\u00012\b\u0010\u008b\u0001\u001a\u00030\u008c\u00012\b\u0010\u008d\u0001\u001a\u00030\u008c\u00012\b\u0010\u008e\u0001\u001a\u00030\u008f\u00012\b\u0010\u0090\u0001\u001a\u00030\u0091\u00012\b\u0010\u0092\u0001\u001a\u00030\u008f\u00012\u0007\u0010\u0093\u0001\u001a\u00020\"2\u0007\u0010\u0094\u0001\u001a\u00020\u0005H\u0002J\u0013\u0010\u0095\u0001\u001a\u00030\u0096\u00012\u0007\u0010\u0097\u0001\u001a\u00020\tH\u0002J\t\u0010\u0098\u0001\u001a\u00020\tH\u0002J\u0013\u0010\u0099\u0001\u001a\u00030\u0096\u00012\u0007\u0010\u0097\u0001\u001a\u00020\tH\u0002J1\u0010\u009a\u0001\u001a\u00030\u008f\u00012\b\u0010\u009b\u0001\u001a\u00030\u008f\u00012\b\u0010\u009c\u0001\u001a\u00030\u0091\u00012\b\u0010\u008b\u0001\u001a\u00030\u008f\u00012\u0007\u0010\u009d\u0001\u001a\u00020\u0005H\u0002J\n\u0010\u009e\u0001\u001a\u00030\u0086\u0001H\u0002J\n\u0010\u009f\u0001\u001a\u00030\u0086\u0001H\u0016J\n\u0010\u00a0\u0001\u001a\u00030\u0086\u0001H\u0016J\t\u0010\u00a1\u0001\u001a\u00020\u0005H\u0002J\u0014\u0010\u00a2\u0001\u001a\u00030\u0086\u00012\b\u0010\u00a3\u0001\u001a\u00030\u00a4\u0001H\u0007J\u0014\u0010\u00a5\u0001\u001a\u00030\u0086\u00012\b\u0010\u00a3\u0001\u001a\u00030\u00a6\u0001H\u0007J\u0014\u0010\u00a7\u0001\u001a\u00030\u0086\u00012\b\u0010\u00a3\u0001\u001a\u00030\u00a8\u0001H\u0007J\u0014\u0010\u00a9\u0001\u001a\u00030\u0086\u00012\b\u0010\u00a3\u0001\u001a\u00030\u00aa\u0001H\u0007J\u0014\u0010\u00ab\u0001\u001a\u00030\u0086\u00012\b\u0010\u00a3\u0001\u001a\u00030\u00ac\u0001H\u0007J\u0014\u0010\u00ad\u0001\u001a\u00030\u0086\u00012\b\u0010\u00a3\u0001\u001a\u00030\u00ae\u0001H\u0007J\u0014\u0010\u00af\u0001\u001a\u00030\u0086\u00012\b\u0010\u00a3\u0001\u001a\u00030\u00b0\u0001H\u0007J\u0014\u0010\u00b1\u0001\u001a\u00030\u0086\u00012\b\u0010\u00a3\u0001\u001a\u00030\u00b2\u0001H\u0007J\u0014\u0010\u00b3\u0001\u001a\u00030\u0086\u00012\b\u0010\u00a3\u0001\u001a\u00030\u00b2\u0001H\u0007J\u001e\u0010\u00b4\u0001\u001a\u0005\u0018\u00010\u00b5\u00012\u0007\u0010\u00b6\u0001\u001a\u00020\u00152\u0007\u0010\u0093\u0001\u001a\u00020\"H\u0002J\n\u0010\u00b7\u0001\u001a\u00030\u0086\u0001H\u0002J\n\u0010\u00b8\u0001\u001a\u00030\u0086\u0001H\u0002J\u001c\u0010\u00b9\u0001\u001a\u00020\u00052\b\u0010\u00ba\u0001\u001a\u00030\u008c\u00012\u0007\u0010\u0094\u0001\u001a\u00020\u0005H\u0002J\t\u0010\u00bb\u0001\u001a\u00020\u0005H\u0002J\t\u0010\u00bc\u0001\u001a\u00020\u0005H\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u00020\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\t0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\u00020\u00158BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u001e\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0014\u0010!\u001a\b\u0012\u0004\u0012\u00020\"0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020$X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020&X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010'\u001a\u00020\u00198BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b(\u0010)R\u000e\u0010*\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010,\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010-\u001a\u00020\u00058BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b-\u0010.R\u001e\u0010/\u001a\u0004\u0018\u00010\tX\u0086\u000e\u00a2\u0006\u0010\n\u0002\u00104\u001a\u0004\b0\u00101\"\u0004\b2\u00103R\u000e\u00105\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u00106\u001a\u0004\u0018\u00010\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u00107\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u00108\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b9\u0010.\"\u0004\b:\u0010;R\u000e\u0010<\u001a\u00020$X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010=\u001a\u00020$X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010>\u001a\u00020$X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010?\u001a\u00020$X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010@\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010A\u001a\b\u0012\u0004\u0012\u00020\"0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010B\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010C\u001a\b\u0012\u0004\u0012\u00020\"0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010D\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010E\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010F\u001a\u00020\tX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bG\u0010\u000b\"\u0004\bH\u0010\rR\u000e\u0010I\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010J\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010K\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010L\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010M\u001a\u00020\"8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\bN\u0010OR\u000e\u0010P\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010Q\u001a\b\u0012\u0004\u0012\u00020R0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010S\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010T\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010U\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010V\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010W\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010X\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010Y\u001a\b\u0012\u0004\u0012\u00020\"0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010Z\u001a\b\u0012\u0004\u0012\u00020\"0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010[\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\\\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b]\u0010 R\u0010\u0010^\u001a\u0004\u0018\u00010\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010_\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010`\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010a\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010b\u001a\b\u0012\u0004\u0012\u00020\"0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010c\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010d\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010e\u001a\b\u0012\u0004\u0012\u00020\t0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010f\u001a\u00020R8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\bg\u0010hR\u000e\u0010i\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010j\u001a\u0004\u0018\u00010kX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010l\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010m\u001a\b\u0012\u0004\u0012\u00020\t0\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010n\u001a\u00020oX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010p\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010q\u001a\u00020&X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010r\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bs\u0010.\"\u0004\bt\u0010;R\u000e\u0010u\u001a\u00020oX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010v\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010w\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010x\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010y\u001a\u00020\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bz\u0010.\"\u0004\b{\u0010;R\u000e\u0010|\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010}\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001f\u0010~\u001a\u0004\u0018\u00010\tX\u0086\u000e\u00a2\u0006\u0011\n\u0002\u00104\u001a\u0004\b\u007f\u00101\"\u0005\b\u0080\u0001\u00103R\u000f\u0010\u0081\u0001\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000f\u0010\u0082\u0001\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0083\u0001\u001a\u00030\u0084\u0001X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u00bd\u0001"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/player/Scaffold;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "andJump", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "autoBlockValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "blockAmount", "", "getBlockAmount", "()I", "setBlockAmount", "(I)V", "blocksToEagleValue", "bridgeMode", "canSameY", "cancelSprint", "cancelSprintCustom", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "currRotation", "Lnet/ccbluex/liquidbounce/utils/Rotation;", "getCurrRotation", "()Lnet/ccbluex/liquidbounce/utils/Rotation;", "delay", "", "delayTimer", "Lnet/ccbluex/liquidbounce/utils/timer/TimerMS;", "downValue", "eagleSneaking", "eagleValue", "getEagleValue", "()Lnet/ccbluex/liquidbounce/features/value/ListValue;", "edgeDistanceValue", "", "expandLengthValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "floatSpeedLevels", "", "getDelay", "getGetDelay", "()J", "highBlock", "highBlockMode", "hitableCheckValue", "isLookingDiagonally", "()Z", "lastGroundY", "getLastGroundY", "()Ljava/lang/Integer;", "setLastGroundY", "(Ljava/lang/Integer;)V", "Ljava/lang/Integer;", "lastPlace", "lockRotation", "lowHopblocksmc", "lowhopState", "getLowhopState", "setLowhopState", "(Z)V", "maxPlaceDelay", "maxRotationSpeedValue", "minPlaceDelay", "minRotationSpeedValue", "motionCustom", "motionSpeedCustom", "motionSpeedEffectCustom", "motionSpeedSpeedEffectCustom", "omniDirectionalExpand", "placeMethod", "placeTick", "getPlaceTick", "setPlaceTick", "placedBlocksWithoutEagle", "prevItem", "prevTowered", "rightSide", "rotationSpeed", "getRotationSpeed", "()F", "rotationSpeedValue", "rotationsValue", "", "safeWalkValue", "sameYSpeed", "searchValue", "shouldGoDown", "shouldJump", "slot", "speedDiagonallyVanilla", "speedVanilla", "sprintCustom", "sprintModeValue", "getSprintModeValue", "staticRotation", "strafeCustom", "strafeFix", "strafeSpeedCustom", "strafeSpeedCustomValue", "swingValue", "switchPlaceTick", "switchTickValue", "tag", "getTag", "()Ljava/lang/String;", "takeVelo", "targetPlace", "Lnet/ccbluex/liquidbounce/utils/block/PlaceInfo;", "tellyPlaceTicks", "tellyTicks", "timerValue", "Lnet/ccbluex/liquidbounce/features/value/FloatValue;", "towerModeValue", "towerSpeedLevels", "towerStatus", "getTowerStatus", "setTowerStatus", "towerTimerValue", "waitRotation", "watchdogJumped", "watchdogSpeed", "watchdogStarted", "getWatchdogStarted", "setWatchdogStarted", "watchdogTower", "watchdogWasEnabled", "y", "getY", "setY", "zitterDirection", "zitterModeValue", "zitterTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "calculateSide", "", "findBlock", "expand", "findTargetPlace", "Lnet/ccbluex/liquidbounce/utils/PlaceRotation;", "pos", "Lnet/minecraft/util/BlockPos;", "offsetPos", "vec3", "Lnet/minecraft/util/Vec3;", "side", "Lnet/minecraft/util/EnumFacing;", "eyes", "maxReach", "raycast", "getFloatSpeed", "", "speedLevel", "getSpeedLevel", "getTowerSpeed", "modifyVec", "original", "direction", "shouldModify", "move", "onDisable", "onEnable", "onGround", "onMotion", "event", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onPostUpdate", "Lnet/ccbluex/liquidbounce/event/PostUpdateEvent;", "onPreUpdate", "Lnet/ccbluex/liquidbounce/event/PreUpdateEvent;", "onSprint", "Lnet/ccbluex/liquidbounce/event/SprintEvent;", "onStrafe", "Lnet/ccbluex/liquidbounce/event/StrafeEvent;", "onTick", "Lnet/ccbluex/liquidbounce/event/TickEvent;", "onTick2", "performBlockRaytrace", "Lnet/minecraft/util/MovingObjectPosition;", "rotation", "place", "rotationStatic", "search", "blockPos", "shouldPlace", "sprint", "CrossSine"})
public final class Scaffold
extends Module {
    @NotNull
    public static final Scaffold INSTANCE = new Scaffold();
    @NotNull
    private static final Value<String> rotationsValue;
    @NotNull
    private static final ListValue towerModeValue;
    @NotNull
    private static final Value<Float> speedVanilla;
    @NotNull
    private static final Value<Float> speedDiagonallyVanilla;
    @NotNull
    private static final ListValue placeMethod;
    @NotNull
    private static final ListValue autoBlockValue;
    @NotNull
    private static final BoolValue highBlock;
    @NotNull
    private static final Value<Boolean> highBlockMode;
    @NotNull
    private static final Value<Integer> switchTickValue;
    @NotNull
    private static final ListValue sprintModeValue;
    @NotNull
    private static final Value<Boolean> lowHopblocksmc;
    @NotNull
    private static final Value<Boolean> sprintCustom;
    @NotNull
    private static final BoolValue cancelSprintCustom;
    @NotNull
    private static final Value<Boolean> motionCustom;
    @NotNull
    private static final Value<Float> motionSpeedCustom;
    @NotNull
    private static final Value<Boolean> motionSpeedEffectCustom;
    @NotNull
    private static final Value<Float> motionSpeedSpeedEffectCustom;
    @NotNull
    private static final Value<Boolean> strafeCustom;
    @NotNull
    private static final Value<Boolean> strafeSpeedCustom;
    @NotNull
    private static final Value<Float> strafeSpeedCustomValue;
    @NotNull
    private static final ListValue bridgeMode;
    @NotNull
    private static final Value<Boolean> waitRotation;
    @NotNull
    private static final Value<Integer> tellyTicks;
    @NotNull
    private static final Value<Boolean> sameYSpeed;
    @NotNull
    private static final Value<Boolean> andJump;
    @NotNull
    private static final BoolValue strafeFix;
    @NotNull
    private static final BoolValue swingValue;
    @NotNull
    private static final BoolValue searchValue;
    @NotNull
    private static final BoolValue downValue;
    @NotNull
    private static final BoolValue safeWalkValue;
    @NotNull
    private static final BoolValue zitterModeValue;
    @NotNull
    private static final BoolValue rotationSpeedValue;
    @NotNull
    private static final IntegerValue maxRotationSpeedValue;
    @NotNull
    private static final IntegerValue minRotationSpeedValue;
    @NotNull
    private static final IntegerValue maxPlaceDelay;
    @NotNull
    private static final IntegerValue minPlaceDelay;
    @NotNull
    private static final IntegerValue expandLengthValue;
    @NotNull
    private static final Value<Boolean> omniDirectionalExpand;
    @NotNull
    private static final FloatValue timerValue;
    @NotNull
    private static final FloatValue towerTimerValue;
    @NotNull
    private static final ListValue eagleValue;
    @NotNull
    private static final Value<Integer> blocksToEagleValue;
    @NotNull
    private static final Value<Float> edgeDistanceValue;
    @NotNull
    private static final ListValue hitableCheckValue;
    @Nullable
    private static PlaceInfo targetPlace;
    @Nullable
    private static Integer lastGroundY;
    @Nullable
    private static Integer y;
    @Nullable
    private static Rotation lockRotation;
    @Nullable
    private static Rotation staticRotation;
    private static int prevItem;
    private static int slot;
    private static boolean cancelSprint;
    private static boolean zitterDirection;
    private static boolean watchdogJumped;
    private static boolean watchdogStarted;
    private static boolean watchdogTower;
    private static boolean watchdogSpeed;
    private static boolean watchdogWasEnabled;
    @NotNull
    private static final MSTimer zitterTimer;
    @NotNull
    private static final TimerMS delayTimer;
    private static int lastPlace;
    private static long delay;
    private static boolean rightSide;
    private static int placedBlocksWithoutEagle;
    private static boolean eagleSneaking;
    private static boolean shouldGoDown;
    private static boolean towerStatus;
    private static boolean canSameY;
    private static boolean prevTowered;
    private static boolean shouldJump;
    private static boolean takeVelo;
    private static int tellyPlaceTicks;
    private static int switchPlaceTick;
    private static int placeTick;
    private static int blockAmount;
    private static boolean lowhopState;
    @NotNull
    private static double[] floatSpeedLevels;
    @NotNull
    private static final double[] towerSpeedLevels;

    private Scaffold() {
    }

    @NotNull
    public final ListValue getSprintModeValue() {
        return sprintModeValue;
    }

    @NotNull
    public final ListValue getEagleValue() {
        return eagleValue;
    }

    @Nullable
    public final Integer getLastGroundY() {
        return lastGroundY;
    }

    public final void setLastGroundY(@Nullable Integer n) {
        lastGroundY = n;
    }

    @Nullable
    public final Integer getY() {
        return y;
    }

    public final void setY(@Nullable Integer n) {
        y = n;
    }

    public final boolean getWatchdogStarted() {
        return watchdogStarted;
    }

    public final void setWatchdogStarted(boolean bl) {
        watchdogStarted = bl;
    }

    private final Rotation getCurrRotation() {
        Rotation rotation = RotationUtils.targetRotation;
        if (rotation == null) {
            rotation = new Rotation(MinecraftInstance.mc.field_71439_g.field_70177_z, MinecraftInstance.mc.field_71439_g.field_70125_A);
        }
        return rotation;
    }

    public final boolean getTowerStatus() {
        return towerStatus;
    }

    public final void setTowerStatus(boolean bl) {
        towerStatus = bl;
    }

    public final int getPlaceTick() {
        return placeTick;
    }

    public final void setPlaceTick(int n) {
        placeTick = n;
    }

    public final int getBlockAmount() {
        return blockAmount;
    }

    public final void setBlockAmount(int n) {
        blockAmount = n;
    }

    public final boolean getLowhopState() {
        return lowhopState;
    }

    public final void setLowhopState(boolean bl) {
        lowhopState = bl;
    }

    private final boolean isLookingDiagonally() {
        float directionDegree = (float)(MovementUtils.INSTANCE.getDirection() * 57.295779513);
        float yaw = (float)Math.rint(Math.abs(MathHelper.func_76142_g((float)directionDegree)) / 45.0f) * 45.0f;
        boolean isYawDiagonal = !(yaw % (float)90 == 0.0f);
        return isYawDiagonal;
    }

    @Override
    public void onEnable() {
        prevTowered = false;
        rightSide = false;
        shouldJump = false;
        watchdogStarted = false;
        watchdogJumped = false;
        watchdogWasEnabled = false;
        if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
            y = (int)MinecraftInstance.mc.field_71439_g.field_70163_u;
        }
        if (((Boolean)cancelSprintCustom.get()).booleanValue() && sprintModeValue.equals("Custom") || sprintModeValue.equals("BlocksMC")) {
            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C0BPacketEntityAction((Entity)MinecraftInstance.mc.field_71439_g, C0BPacketEntityAction.Action.STOP_SPRINTING));
            cancelSprint = true;
        }
        prevItem = MinecraftInstance.mc.field_71439_g.field_71071_by.field_70461_c;
        slot = MinecraftInstance.mc.field_71439_g.field_71071_by.field_70461_c;
        if (MinecraftInstance.mc.field_71439_g == null) {
            return;
        }
        lastGroundY = (int)MinecraftInstance.mc.field_71439_g.field_70163_u;
        zitterTimer.reset();
        tellyPlaceTicks = 0;
    }

    @EventTarget
    public final void onSprint(@NotNull SprintEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        event.setSprint(this.sprint());
    }

    @EventTarget
    public final void onTick2(@NotNull TickEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (InventoryUtils.INSTANCE.findAutoBlockBlock((Boolean)highBlock.get()) != -1) {
            this.findBlock(((Number)expandLengthValue.get()).intValue() > 1);
            if (towerStatus) {
                MinecraftInstance.mc.field_71428_T.field_74278_d = ((Number)towerTimerValue.get()).floatValue();
                this.move();
                prevTowered = true;
                canSameY = false;
                lastGroundY = (int)MinecraftInstance.mc.field_71439_g.field_70163_u;
                y = (int)MinecraftInstance.mc.field_71439_g.field_70163_u;
            }
        }
        if (takeVelo && MinecraftInstance.mc.field_71439_g.field_70737_aN <= 0) {
            takeVelo = false;
        }
        if (bridgeMode.equals("GodBridge") && waitRotation.get().booleanValue()) {
            MinecraftInstance.mc.field_71474_y.field_74311_E.field_74513_e = placeTick == 0;
        }
        this.rotationStatic();
        if (placeMethod.equals("GameTick")) {
            this.place();
        }
    }

    @EventTarget
    public final void onPreUpdate(@NotNull PreUpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.calculateSide();
        if (towerModeValue.equals("WatchDog") && towerStatus) {
            if (MovementUtils.INSTANCE.isMoving()) {
                watchdogSpeed = false;
                int simpleY = (int)Math.round(MinecraftInstance.mc.field_71439_g.field_70163_u % 1.0 * 100.0);
                if (MinecraftInstance.mc.field_71439_g.field_70163_u % 1.0 == 0.0 && MinecraftInstance.mc.field_71439_g.field_70122_E) {
                    watchdogTower = true;
                }
                if (watchdogTower) {
                    switch (simpleY) {
                        case 0: {
                            MinecraftInstance.mc.field_71439_g.field_70181_x = 0.42;
                            if (PlayerUtils.getOffGroundTicks() == 6) {
                                MinecraftInstance.mc.field_71439_g.field_70181_x = -0.078400001525879;
                            }
                            MovementUtils.INSTANCE.strafe(this.getTowerSpeed(this.getSpeedLevel()));
                            watchdogSpeed = true;
                            break;
                        }
                        case 42: {
                            MinecraftInstance.mc.field_71439_g.field_70181_x = 0.33;
                            MovementUtils.INSTANCE.strafe(this.getTowerSpeed(this.getSpeedLevel()));
                            watchdogSpeed = true;
                            break;
                        }
                        case 75: {
                            MinecraftInstance.mc.field_71439_g.field_70181_x = 1.0 - MinecraftInstance.mc.field_71439_g.field_70163_u % (double)1.0f;
                        }
                        default: {
                            break;
                        }
                    }
                }
            } else {
                watchdogTower = false;
            }
        }
        if (placeMethod.equals("Pre")) {
            this.place();
        }
    }

    @EventTarget
    public final void onPostUpdate(@NotNull PostUpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (placeMethod.equals("Post")) {
            this.place();
        }
    }

    @EventTarget
    public final void onTick(@NotNull TickEvent event) {
        block46: {
            block45: {
                block44: {
                    Intrinsics.checkNotNullParameter(event, "event");
                    if (eagleValue.equals("Silent") && !MinecraftInstance.mc.field_71439_g.field_70122_E) {
                        y = null;
                    }
                    if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
                        if (y == null) {
                            y = (int)MinecraftInstance.mc.field_71439_g.field_70163_u;
                        }
                        if (lastGroundY == null) {
                            lastGroundY = (int)MinecraftInstance.mc.field_71439_g.field_70163_u;
                        }
                    }
                    if (MinecraftInstance.mc.field_71439_g.func_70694_bm() != null && MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemBlock) {
                        if (!((Boolean)highBlock.get()).booleanValue()) {
                            if (blockAmount == 0) {
                                blockAmount = MinecraftInstance.mc.field_71439_g.func_70694_bm().field_77994_a;
                            }
                        } else {
                            blockAmount = MinecraftInstance.mc.field_71439_g.func_70694_bm().field_77994_a;
                        }
                    }
                    if (bridgeMode.equals("GodBridge") && PlayerUtils.INSTANCE.BlockUnderPlayerIsEmpty() && !towerStatus && MinecraftInstance.mc.field_71439_g.field_70122_E) {
                        MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, 0.0, 4, null);
                    }
                    if (lastGroundY == null) break block44;
                    double d = MinecraftInstance.mc.field_71439_g.field_70163_u;
                    Integer n = lastGroundY;
                    Intrinsics.checkNotNull(n);
                    if (d < (double)n.intValue()) break block45;
                }
                if (y == null) break block46;
                double d = MinecraftInstance.mc.field_71439_g.field_70163_u;
                Integer n = y;
                Intrinsics.checkNotNull(n);
                if (!(d < (double)n.intValue())) break block46;
            }
            y = null;
            lastGroundY = null;
        }
        if (lastPlace == 1) {
            delayTimer.reset();
            delay = this.getGetDelay();
            MouseUtils.INSTANCE.setRightClicked(false);
            lastPlace = 0;
        }
        if (!towerStatus) {
            if (bridgeMode.equals("AutoJump")) {
                canSameY = true;
                if (MovementUtils.INSTANCE.isMoving() && this.onGround()) {
                    MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, 0.0, 6, null);
                }
            }
            if (sprintModeValue.equals("WatchDog")) {
                canSameY = true;
                if (GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74313_G)) {
                    MinecraftInstance.mc.field_71474_y.field_74313_G.field_74513_e = false;
                    if (MovementUtils.INSTANCE.isMoving() && this.onGround()) {
                        MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, 0.0, 6, null);
                    }
                }
            }
            if (bridgeMode.equals("SameY") && (!sameYSpeed.get().booleanValue() || Speed.INSTANCE.getState())) {
                canSameY = true;
                if (MovementUtils.INSTANCE.isMoving() && this.onGround()) {
                    MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, 0.0, 6, null);
                }
            }
            if (bridgeMode.equals("Telly") && this.onGround() && MovementUtils.INSTANCE.isMoving()) {
                MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, 0.0, 6, null);
            }
            if (bridgeMode.equals("KeepUP")) {
                canSameY = false;
                if (MovementUtils.INSTANCE.isMoving() && this.onGround()) {
                    MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, 0.0, 6, null);
                }
            }
        }
        if (bridgeMode.equals("Andromeda") && !(BlockUtils.getBlock(new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v).func_177977_b()) instanceof BlockAir) && !(BlockUtils.getBlock(new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + (double)2, MinecraftInstance.mc.field_71439_g.field_70161_v)) instanceof BlockAir)) {
            if (andJump.get().booleanValue() && MinecraftInstance.mc.field_71439_g.field_70122_E) {
                MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, 0.0, 6, null);
            }
            lockRotation = null;
        }
        if (!towerStatus) {
            MinecraftInstance.mc.field_71428_T.field_74278_d = ((Number)timerValue.get()).floatValue();
        }
        boolean bl = shouldGoDown = (Boolean)downValue.get() != false && GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74311_E);
        if (shouldGoDown) {
            MinecraftInstance.mc.field_71474_y.field_74311_E.field_74513_e = false;
        }
        if (MinecraftInstance.mc.field_71439_g.field_70122_E) {
            if (((Boolean)zitterModeValue.get()).booleanValue()) {
                if (!GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74366_z)) {
                    MinecraftInstance.mc.field_71474_y.field_74366_z.field_74513_e = false;
                }
                if (!GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74370_x)) {
                    MinecraftInstance.mc.field_71474_y.field_74370_x.field_74513_e = false;
                }
                if (zitterTimer.hasTimePassed(100L)) {
                    zitterDirection = !zitterDirection;
                    zitterTimer.reset();
                }
                if (zitterDirection) {
                    MinecraftInstance.mc.field_71474_y.field_74366_z.field_74513_e = true;
                    MinecraftInstance.mc.field_71474_y.field_74370_x.field_74513_e = false;
                } else {
                    MinecraftInstance.mc.field_71474_y.field_74366_z.field_74513_e = false;
                    MinecraftInstance.mc.field_71474_y.field_74370_x.field_74513_e = true;
                }
            }
            if (!StringsKt.equals((String)eagleValue.get(), "Off", true) && !shouldGoDown) {
                double dif = 0.5;
                BlockPos blockPos = new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u - 1.0, MinecraftInstance.mc.field_71439_g.field_70161_v);
                if (((Number)edgeDistanceValue.get()).floatValue() > 0.0f) {
                    for (EnumFacing facingType : EnumFacing.values()) {
                        BlockPos neighbor;
                        if (facingType == EnumFacing.UP || facingType == EnumFacing.DOWN || !BlockUtils.isReplaceable(neighbor = blockPos.func_177972_a(facingType))) continue;
                        double d = facingType == EnumFacing.NORTH || facingType == EnumFacing.SOUTH ? Math.abs((double)neighbor.func_177952_p() + 0.5 - MinecraftInstance.mc.field_71439_g.field_70161_v) : Math.abs((double)neighbor.func_177958_n() + 0.5 - MinecraftInstance.mc.field_71439_g.field_70165_t);
                        double calcDif = d - 0.5;
                        if (!(calcDif < dif)) continue;
                        dif = calcDif;
                    }
                }
                if (placedBlocksWithoutEagle >= ((Number)blocksToEagleValue.get()).intValue()) {
                    boolean shouldEagle;
                    boolean bl2 = shouldEagle = BlockUtils.isReplaceable(blockPos) || ((Number)edgeDistanceValue.get()).floatValue() > 0.0f && dif < (double)((Number)edgeDistanceValue.get()).floatValue();
                    if (StringsKt.equals((String)eagleValue.get(), "Packet", true)) {
                        if (eagleSneaking != shouldEagle) {
                            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C0BPacketEntityAction((Entity)MinecraftInstance.mc.field_71439_g, shouldEagle ? C0BPacketEntityAction.Action.START_SNEAKING : C0BPacketEntityAction.Action.STOP_SNEAKING));
                        }
                        eagleSneaking = shouldEagle;
                    } else {
                        MinecraftInstance.mc.field_71474_y.field_74311_E.field_74513_e = shouldEagle;
                    }
                    placedBlocksWithoutEagle = 0;
                } else {
                    int n = placedBlocksWithoutEagle;
                    placedBlocksWithoutEagle = n + 1;
                }
            }
        }
    }

    @EventTarget
    public final void onStrafe(@NotNull StrafeEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (((Boolean)strafeFix.get()).booleanValue()) {
            float f;
            Float yaw = RotationUtils.playerYaw;
            float f2 = MinecraftInstance.mc.field_71439_g.field_70177_z;
            Intrinsics.checkNotNullExpressionValue(yaw, "yaw");
            int dif = (int)((MathHelper.func_76142_g((float)(f2 - yaw.floatValue() - 23.5f - (float)135)) + (float)180) / (float)45);
            float strafe = event.getStrafe();
            float forward = event.getForward();
            float friction = event.getFriction();
            float calcForward = 0.0f;
            float calcStrafe = 0.0f;
            switch (dif) {
                case 0: {
                    calcForward = forward;
                    calcStrafe = strafe;
                    break;
                }
                case 1: {
                    calcForward += forward;
                    calcStrafe -= forward;
                    calcForward += strafe;
                    calcStrafe += strafe;
                    break;
                }
                case 2: {
                    calcForward = strafe;
                    calcStrafe = -forward;
                    break;
                }
                case 3: {
                    calcForward -= forward;
                    calcStrafe -= forward;
                    calcForward += strafe;
                    calcStrafe -= strafe;
                    break;
                }
                case 4: {
                    calcForward = -forward;
                    calcStrafe = -strafe;
                    break;
                }
                case 5: {
                    calcForward -= forward;
                    calcStrafe += forward;
                    calcForward -= strafe;
                    calcStrafe -= strafe;
                    break;
                }
                case 6: {
                    calcForward = -strafe;
                    calcStrafe = forward;
                    break;
                }
                case 7: {
                    calcForward += forward;
                    calcStrafe += forward;
                    calcForward -= strafe;
                    calcStrafe += strafe;
                }
            }
            if (calcForward > 1.0f || calcForward < 0.9f && calcForward > 0.3f || calcForward < -1.0f || calcForward > -0.9f && calcForward < -0.3f) {
                calcForward *= 0.5f;
            }
            if (calcStrafe > 1.0f || calcStrafe < 0.9f && calcStrafe > 0.3f || calcStrafe < -1.0f || calcStrafe > -0.9f && calcStrafe < -0.3f) {
                calcStrafe *= 0.5f;
            }
            if ((f = calcStrafe * calcStrafe + calcForward * calcForward) >= 1.0E-4f) {
                if ((f = MathHelper.func_76129_c((float)f)) < 1.0f) {
                    f = 1.0f;
                }
                f = friction / f;
                float yawSin = MathHelper.func_76126_a((float)((float)((double)yaw.floatValue() * Math.PI / (double)180.0f)));
                float yawCos = MathHelper.func_76134_b((float)((float)((double)yaw.floatValue() * Math.PI / (double)180.0f)));
                EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                entityPlayerSP.field_70159_w += (double)((calcStrafe *= f) * yawCos - (calcForward *= f) * yawSin);
                entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                entityPlayerSP.field_70179_y += (double)(calcForward * yawCos + calcStrafe * yawSin);
            }
            event.cancelEvent();
        }
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (MinecraftInstance.mc.field_71439_g == null) {
            return;
        }
        Packet<?> packet = event.getPacket();
        if (packet instanceof C08PacketPlayerBlockPlacement) {
            ((C08PacketPlayerBlockPlacement)packet).field_149577_f = RangesKt.coerceIn(((C08PacketPlayerBlockPlacement)packet).field_149577_f, -1.0f, 1.0f);
            ((C08PacketPlayerBlockPlacement)packet).field_149578_g = RangesKt.coerceIn(((C08PacketPlayerBlockPlacement)packet).field_149578_g, -1.0f, 1.0f);
            ((C08PacketPlayerBlockPlacement)packet).field_149584_h = RangesKt.coerceIn(((C08PacketPlayerBlockPlacement)packet).field_149584_h, -1.0f, 1.0f);
        }
        if (packet instanceof S12PacketEntityVelocity && ((S12PacketEntityVelocity)packet).func_149412_c() == MinecraftInstance.mc.field_71439_g.func_145782_y()) {
            takeVelo = true;
        }
        if ((sprintModeValue.equals("Custom") || sprintModeValue.equals("BlocksMC")) && ((Boolean)cancelSprintCustom.get()).booleanValue() && packet instanceof C0BPacketEntityAction && cancelSprint) {
            event.cancelEvent();
        }
        if (towerModeValue.equals("BlocksMC") && MovementUtils.INSTANCE.isMoving() && MinecraftInstance.mc.field_71439_g.field_70181_x > -0.09800000190734864 && packet instanceof C08PacketPlayerBlockPlacement && Intrinsics.areEqual(((C08PacketPlayerBlockPlacement)packet).func_179724_a(), new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u - 1.4, MinecraftInstance.mc.field_71439_g.field_70161_v))) {
            MinecraftInstance.mc.field_71439_g.field_70181_x = -0.09800000190734864;
        }
    }

    private final void rotationStatic() {
        String string = rotationsValue.get().toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        switch (string) {
            case "stabilized": {
                staticRotation = lockRotation == null ? new Rotation(MovementUtils.INSTANCE.getMovingYaw() - (float)180, 80.0f) : lockRotation;
                break;
            }
            case "normal": {
                staticRotation = lockRotation == null ? new Rotation(MovementUtils.INSTANCE.getMovingYaw() - 180.0f, 80.0f) : lockRotation;
                break;
            }
            case "vanilla": {
                PlaceInfo placeInfo = targetPlace;
                Intrinsics.checkNotNull(placeInfo);
                EnumFacing enumFacing = placeInfo.getEnumFacing();
                PlaceInfo placeInfo2 = targetPlace;
                Intrinsics.checkNotNull(placeInfo2);
                staticRotation = RotationUtils.getFaceRotation(enumFacing, placeInfo2.getBlockPos());
                break;
            }
            case "watchdog": {
                staticRotation = new Rotation((float)((double)MinecraftInstance.mc.field_71439_g.field_70177_z - 99.99999999999999 + (Math.random() - 0.5) * (double)3), 86.0f);
                break;
            }
            case "telly": {
                float f;
                float rotationYaw;
                float f2;
                if (prevTowered) {
                    if (lockRotation == null) {
                        f2 = MovementUtils.INSTANCE.getMovingYaw() - (float)180;
                    } else {
                        Rotation rotation = lockRotation;
                        Intrinsics.checkNotNull(rotation);
                        f2 = rotation.getYaw();
                    }
                } else if (!this.shouldPlace()) {
                    f2 = MovementUtils.INSTANCE.getMovingYaw();
                } else if (lockRotation == null) {
                    f2 = MovementUtils.INSTANCE.getMovingYaw() - (float)180;
                } else {
                    Rotation rotation = lockRotation;
                    Intrinsics.checkNotNull(rotation);
                    f2 = rotationYaw = rotation.getYaw();
                }
                if (lockRotation == null) {
                    f = 85.0f;
                } else {
                    Rotation rotation = lockRotation;
                    Intrinsics.checkNotNull(rotation);
                    f = rotation.getPitch();
                }
                staticRotation = new Rotation(rotationYaw, f);
            }
        }
        if (bridgeMode.equals("GodBridge")) {
            Rotation rotation;
            if (prevTowered || takeVelo) {
                PlaceInfo placeInfo = targetPlace;
                Intrinsics.checkNotNull(placeInfo);
                double d = (double)placeInfo.getBlockPos().func_177958_n() + 0.5;
                PlaceInfo placeInfo3 = targetPlace;
                Intrinsics.checkNotNull(placeInfo3);
                double d2 = (double)placeInfo3.getBlockPos().func_177956_o() + 0.5;
                PlaceInfo placeInfo4 = targetPlace;
                Intrinsics.checkNotNull(placeInfo4);
                rotation = RotationUtils.getRotations(d, d2, (double)placeInfo4.getBlockPos().func_177952_p() + 0.5);
            } else {
                rotation = staticRotation = new Rotation(this.isLookingDiagonally() ? MovementUtils.INSTANCE.getMovingYaw() - (float)180 : MovementUtils.INSTANCE.getMovingYaw() + (rightSide ? 135.0f : -135.0f), 75.5f);
            }
        }
        if (staticRotation != null) {
            RotationUtils.setTargetRotation(RotationUtils.limitAngleChange(RotationUtils.serverRotation, staticRotation, this.getRotationSpeed()), 20);
        }
    }

    @EventTarget
    public final void onMotion(@NotNull MotionEvent event) {
        block20: {
            block21: {
                Intrinsics.checkNotNullParameter(event, "event");
                if (InventoryUtils.INSTANCE.findAutoBlockBlock((Boolean)highBlock.get()) == -1) break block20;
                if (MinecraftInstance.mc.field_71439_g.func_70694_bm() == null || !(MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b() instanceof ItemBlock)) break block21;
                Item item = MinecraftInstance.mc.field_71439_g.func_70694_bm().func_77973_b();
                if (item == null) {
                    throw new NullPointerException("null cannot be cast to non-null type net.minecraft.item.ItemBlock");
                }
                if (!(InventoryUtils.INSTANCE.isBlockListBlock((ItemBlock)item) || (Boolean)highBlock.get() != false && highBlockMode.get() == false || (Boolean)highBlock.get() == false && placeTick >= blockAmount) && (!((Boolean)highBlock.get()).booleanValue() || !highBlockMode.get().booleanValue() || switchPlaceTick < ((Number)switchTickValue.get()).intValue())) break block20;
            }
            SlotUtils.INSTANCE.setSlot(InventoryUtils.INSTANCE.findAutoBlockBlock((Boolean)highBlock.get() != false && highBlockMode.get() == false || (Boolean)highBlock.get() == false && placeTick >= blockAmount || (Boolean)highBlock.get() != false && highBlockMode.get() != false && switchPlaceTick >= ((Number)switchTickValue.get()).intValue()) - 36, autoBlockValue.equals("Spoof"), this.getName());
            if (!((Boolean)highBlock.get()).booleanValue()) {
                blockAmount = 0;
            }
            placeTick = 0;
            switchPlaceTick = 0;
            MinecraftInstance.mc.field_71442_b.func_78765_e();
        }
        if (event.isPre()) {
            if (sprintModeValue.equals("WatchDog") && !Speed.INSTANCE.getState() && !towerStatus) {
                watchdogWasEnabled = true;
                if (!watchdogStarted) {
                    if (PlayerUtils.getGroundTicks() > 8 && MinecraftInstance.mc.field_71439_g.field_70122_E) {
                        MinecraftInstance.mc.field_71439_g.func_70664_aZ();
                        MovementUtils.INSTANCE.strafe((double)MovementUtils.INSTANCE.getSpeed() - 0.1);
                        watchdogJumped = true;
                    } else if (PlayerUtils.getGroundTicks() <= 8 && MinecraftInstance.mc.field_71439_g.field_70122_E) {
                        watchdogStarted = true;
                    }
                    if (watchdogJumped && !MinecraftInstance.mc.field_71439_g.field_70122_E) {
                        watchdogStarted = true;
                    }
                }
                if (watchdogStarted && MinecraftInstance.mc.field_71439_g.field_70122_E) {
                    event.setY(event.getY() + (double)1.0E-12f);
                    if (MovementUtils.INSTANCE.isMoving()) {
                        MovementUtils.INSTANCE.strafe(this.getFloatSpeed(this.getSpeedLevel()));
                    }
                }
            } else if (watchdogWasEnabled) {
                watchdogStarted = false;
                watchdogJumped = false;
                watchdogWasEnabled = false;
            }
            if (towerModeValue.equals("WatchDogA") && towerStatus && PlayerUtils.getOffGroundTicks() == 6) {
                event.setY(event.getY() + 3.83527E-4);
            }
        }
        if (PlayerUtils.getOffGroundTicks() <= 3 && !towerStatus) {
            towerStatus = MinecraftInstance.mc.field_71474_y.field_74314_A.func_151470_d();
        }
        if (!MinecraftInstance.mc.field_71474_y.field_74314_A.func_151470_d()) {
            towerStatus = false;
        }
    }

    private final int getSpeedLevel() {
        Iterator iterator2 = MinecraftInstance.mc.field_71439_g.func_70651_bq().iterator();
        if (iterator2.hasNext()) {
            PotionEffect potionEffect = (PotionEffect)iterator2.next();
            if (Intrinsics.areEqual(potionEffect.func_76453_d(), "potion.moveSpeed")) {
                return potionEffect.func_76458_c() + 1;
            }
            return 0;
        }
        return 0;
    }

    private final double getFloatSpeed(int speedLevel) {
        if (speedLevel >= 0) {
            return floatSpeedLevels[speedLevel];
        }
        return floatSpeedLevels[0];
    }

    private final void move() {
        String string = ((String)towerModeValue.get()).toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        switch (string) {
            case "ncp": {
                MovementUtils.INSTANCE.strafe();
                if (!PlayerUtils.INSTANCE.blockNear(2)) break;
                if (MinecraftInstance.mc.field_71439_g.field_70163_u % 1.0 <= 0.00153598) {
                    MinecraftInstance.mc.field_71439_g.func_70107_b(MinecraftInstance.mc.field_71439_g.field_70165_t, Math.floor(MinecraftInstance.mc.field_71439_g.field_70163_u), MinecraftInstance.mc.field_71439_g.field_70161_v);
                    MinecraftInstance.mc.field_71439_g.field_70181_x = 0.42;
                    break;
                }
                if (!(MinecraftInstance.mc.field_71439_g.field_70163_u % 1.0 < 0.1) || PlayerUtils.getOffGroundTicks() == 0) break;
                MinecraftInstance.mc.field_71439_g.field_70181_x = 0.0;
                MinecraftInstance.mc.field_71439_g.func_70107_b(MinecraftInstance.mc.field_71439_g.field_70165_t, Math.floor(MinecraftInstance.mc.field_71439_g.field_70163_u), MinecraftInstance.mc.field_71439_g.field_70161_v);
                break;
            }
            case "blocksmc": {
                if (PlayerUtils.INSTANCE.blockNear(2) && !MovementUtils.INSTANCE.isMoving()) {
                    if (MinecraftInstance.mc.field_71439_g.field_70163_u % 1.0 <= 0.00153598) {
                        MinecraftInstance.mc.field_71439_g.func_70107_b(MinecraftInstance.mc.field_71439_g.field_70165_t, Math.floor(MinecraftInstance.mc.field_71439_g.field_70163_u), MinecraftInstance.mc.field_71439_g.field_70161_v);
                        MinecraftInstance.mc.field_71439_g.field_70181_x = 0.42;
                    } else if (MinecraftInstance.mc.field_71439_g.field_70163_u % 1.0 < 0.1 && PlayerUtils.getOffGroundTicks() != 0) {
                        MinecraftInstance.mc.field_71439_g.field_70181_x = 0.0;
                        MinecraftInstance.mc.field_71439_g.func_70107_b(MinecraftInstance.mc.field_71439_g.field_70165_t, Math.floor(MinecraftInstance.mc.field_71439_g.field_70163_u), MinecraftInstance.mc.field_71439_g.field_70161_v);
                    }
                    MovementUtils.INSTANCE.resetMotion(false);
                }
                if (!MovementUtils.INSTANCE.isMoving() || !MinecraftInstance.mc.field_71439_g.field_70122_E) break;
                MovementUtils.jump$default(MovementUtils.INSTANCE, true, false, 0.0, 6, null);
                break;
            }
            case "vanilla": {
                MinecraftInstance.mc.field_71439_g.field_70181_x = 0.41965;
            }
        }
    }

    private final double getTowerSpeed(int speedLevel) {
        if (speedLevel >= 0) {
            return towerSpeedLevels[speedLevel];
        }
        return towerSpeedLevels[0];
    }

    private final void findBlock(boolean expand) {
        BlockPos blockPosition;
        block24: {
            block25: {
                BlockPos blockPos;
                if (!this.shouldPlace()) {
                    return;
                }
                if (shouldGoDown) {
                    blockPos = MinecraftInstance.mc.field_71439_g.field_70163_u == (double)((int)MinecraftInstance.mc.field_71439_g.field_70163_u) + 0.5 ? new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u - 0.6, MinecraftInstance.mc.field_71439_g.field_70161_v) : new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u - 0.6, MinecraftInstance.mc.field_71439_g.field_70161_v).func_177977_b();
                } else if (bridgeMode.equals("Telly") && !towerStatus) {
                    double d = MinecraftInstance.mc.field_71439_g.field_70165_t;
                    Integer n = lastGroundY;
                    Intrinsics.checkNotNull(n);
                    blockPos = new BlockPos(d, (double)n.intValue() - 1.0, MinecraftInstance.mc.field_71439_g.field_70161_v);
                } else if (bridgeMode.equals("UpSideDown") && !towerStatus) {
                    blockPos = new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + (double)2, MinecraftInstance.mc.field_71439_g.field_70161_v);
                } else if (bridgeMode.equals("Andromeda") && !towerStatus) {
                    blockPos = BlockUtils.getBlock(new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v).func_177977_b()) instanceof BlockAir ? new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v).func_177977_b() : new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u + 2.5, MinecraftInstance.mc.field_71439_g.field_70161_v);
                } else if (MinecraftInstance.mc.field_71439_g.field_70163_u == (double)((int)MinecraftInstance.mc.field_71439_g.field_70163_u) + 0.5 && !canSameY) {
                    blockPos = new BlockPos((Entity)MinecraftInstance.mc.field_71439_g);
                } else if (canSameY) {
                    double d = MinecraftInstance.mc.field_71439_g.field_70165_t;
                    Integer n = lastGroundY;
                    Intrinsics.checkNotNull(n);
                    blockPos = new BlockPos(d, (double)n.intValue() - 1.0, MinecraftInstance.mc.field_71439_g.field_70161_v);
                } else {
                    blockPos = blockPosition = new BlockPos(MinecraftInstance.mc.field_71439_g.field_70165_t, MinecraftInstance.mc.field_71439_g.field_70163_u, MinecraftInstance.mc.field_71439_g.field_70161_v).func_177977_b();
                }
                if (expand) break block24;
                if (!BlockUtils.isReplaceable(blockPosition)) break block25;
                Intrinsics.checkNotNullExpressionValue(blockPosition, "blockPosition");
                if (!this.search(blockPosition, !shouldGoDown)) break block24;
            }
            return;
        }
        if (expand) {
            double yaw = OtherExtensionKt.toRadiansD(MinecraftInstance.mc.field_71439_g.field_70177_z);
            int x = omniDirectionalExpand.get() != false ? -MathKt.roundToInt(Math.sin(yaw)) : MinecraftInstance.mc.field_71439_g.func_174811_aO().func_176730_m().func_177958_n();
            int z = omniDirectionalExpand.get() != false ? MathKt.roundToInt(Math.cos(yaw)) : MinecraftInstance.mc.field_71439_g.func_174811_aO().func_176730_m().func_177952_p();
            int n = 0;
            int n2 = ((Number)expandLengthValue.get()).intValue();
            while (n < n2) {
                int i = n++;
                BlockPos blockPos = blockPosition.func_177982_a(x * i, 0, z * i);
                Intrinsics.checkNotNullExpressionValue(blockPos, "blockPosition.add(x * i, 0, z * i)");
                if (!this.search(blockPos, false)) continue;
                return;
            }
        } else if (((Boolean)searchValue.get()).booleanValue()) {
            Pair<Integer, Integer> pair = bridgeMode.equals("Telly") ? TuplesKt.to(2, 2) : TuplesKt.to(1, 1);
            int horizontal2 = ((Number)pair.component1()).intValue();
            int vertical2 = ((Number)pair.component2()).intValue();
            int n = -horizontal2;
            if (n <= horizontal2) {
                int x;
                do {
                    int y;
                    x = n++;
                    int n3 = 0;
                    int n4 = -vertical2;
                    if (n4 > 0) continue;
                    do {
                        int z;
                        y = n3--;
                        int n5 = -horizontal2;
                        if (n5 > horizontal2) continue;
                        do {
                            z = n5++;
                            BlockPos blockPos = blockPosition.func_177982_a(x, y, z);
                            Intrinsics.checkNotNullExpressionValue(blockPos, "blockPosition.add(x, y, z)");
                            if (!this.search(blockPos, !shouldGoDown)) continue;
                            return;
                        } while (z != horizontal2);
                    } while (y != n4);
                } while (x != horizontal2);
            }
        }
    }

    private final boolean shouldPlace() {
        if (!delayTimer.hasTimePassed(delay) && !towerStatus) {
            return false;
        }
        return prevTowered || !bridgeMode.equals("Telly") || PlayerUtils.getOffGroundTicks() >= ((Number)tellyTicks.get()).intValue() && PlayerUtils.getOffGroundTicks() < 11;
    }

    private final void place() {
        block22: {
            if (!this.shouldPlace()) {
                return;
            }
            if (rotationsValue.equals("None")) break block22;
            Object object = MinecraftInstance.mc.field_71439_g;
            Intrinsics.checkNotNullExpressionValue(object, "mc.thePlayer");
            MovingObjectPosition rayTraceInfo = EntityExtensionKt.rayTraceWithServerSideRotation((Entity)object, MinecraftInstance.mc.field_71442_b.func_78757_d());
            String string = ((String)hitableCheckValue.get()).toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            switch (string) {
                case "simple": {
                    if (rayTraceInfo == null) break;
                    BlockPos blockPos = rayTraceInfo.func_178782_a();
                    PlaceInfo placeInfo = targetPlace;
                    Intrinsics.checkNotNull(placeInfo);
                    if (blockPos.equals((Object)placeInfo.getBlockPos())) break;
                    return;
                }
                case "strict": {
                    if (rayTraceInfo == null) break;
                    BlockPos blockPos = rayTraceInfo.func_178782_a();
                    PlaceInfo placeInfo = targetPlace;
                    Intrinsics.checkNotNull(placeInfo);
                    if (blockPos.equals((Object)placeInfo.getBlockPos())) {
                        EnumFacing enumFacing = rayTraceInfo.field_178784_b;
                        PlaceInfo placeInfo2 = targetPlace;
                        Intrinsics.checkNotNull(placeInfo2);
                        if (enumFacing == placeInfo2.getEnumFacing()) break;
                    }
                    return;
                }
                case "legit": {
                    if (MinecraftInstance.mc.field_71476_x == null) break;
                    BlockPos blockPos = MinecraftInstance.mc.field_71476_x.func_178782_a();
                    PlaceInfo placeInfo = targetPlace;
                    Intrinsics.checkNotNull(placeInfo);
                    if (Intrinsics.areEqual(blockPos, placeInfo.getBlockPos())) {
                        EnumFacing enumFacing = MinecraftInstance.mc.field_71476_x.field_178784_b;
                        PlaceInfo placeInfo3 = targetPlace;
                        Intrinsics.checkNotNull(placeInfo3);
                        if (enumFacing == placeInfo3.getEnumFacing()) break;
                    }
                    return;
                }
            }
        }
        if (InventoryUtils.INSTANCE.findAutoBlockBlock((Boolean)highBlock.get()) != -1 && targetPlace != null) {
            PlayerControllerMP playerControllerMP = MinecraftInstance.mc.field_71442_b;
            EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
            WorldClient worldClient = MinecraftInstance.mc.field_71441_e;
            ItemStack itemStack = MinecraftInstance.mc.field_71439_g.func_70694_bm();
            PlaceInfo placeInfo = targetPlace;
            Intrinsics.checkNotNull(placeInfo);
            BlockPos blockPos = placeInfo.getBlockPos();
            PlaceInfo placeInfo4 = targetPlace;
            Intrinsics.checkNotNull(placeInfo4);
            EnumFacing enumFacing = placeInfo4.getEnumFacing();
            PlaceInfo placeInfo5 = targetPlace;
            Intrinsics.checkNotNull(placeInfo5);
            if (playerControllerMP.func_178890_a(entityPlayerSP, worldClient, itemStack, blockPos, enumFacing, placeInfo5.getVec3())) {
                if (((Boolean)swingValue.get()).booleanValue()) {
                    MinecraftInstance.mc.field_71439_g.func_71038_i();
                } else {
                    MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C0APacketAnimation());
                }
                int n = tellyPlaceTicks;
                tellyPlaceTicks = n + 1;
                n = lastPlace;
                lastPlace = n + 1;
                MouseUtils.INSTANCE.setRightClicked(true);
                CPSCounter.registerClick(CPSCounter.MouseButton.RIGHT);
                if (highBlockMode.get().booleanValue() && ((Boolean)highBlock.get()).booleanValue()) {
                    n = switchPlaceTick;
                    switchPlaceTick = n + 1;
                }
                if (!((Boolean)highBlock.get()).booleanValue() && MinecraftInstance.mc.field_71442_b.func_78762_g()) {
                    n = placeTick;
                    placeTick = n + 1;
                }
            }
        }
        targetPlace = null;
    }

    @Override
    public void onDisable() {
        MouseUtils.INSTANCE.setRightClicked(GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74313_G));
        takeVelo = false;
        y = null;
        tellyPlaceTicks = 0;
        cancelSprint = false;
        if (MinecraftInstance.mc.field_71439_g == null) {
            return;
        }
        if (!GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74311_E)) {
            MinecraftInstance.mc.field_71474_y.field_74311_E.field_74513_e = false;
            if (eagleSneaking) {
                MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C0BPacketEntityAction((Entity)MinecraftInstance.mc.field_71439_g, C0BPacketEntityAction.Action.STOP_SNEAKING));
            }
        }
        if (sprintModeValue.equals("WatchDog")) {
            if (this.onGround()) {
                EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                entityPlayerSP.field_70159_w *= 0.15;
                entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                entityPlayerSP.field_70179_y *= 0.15;
            } else {
                EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                entityPlayerSP.field_70159_w *= 0.5;
                entityPlayerSP = MinecraftInstance.mc.field_71439_g;
                entityPlayerSP.field_70179_y *= 0.5;
            }
        }
        canSameY = false;
        if (!GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74366_z)) {
            MinecraftInstance.mc.field_71474_y.field_74366_z.field_74513_e = false;
        }
        if (!GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74370_x)) {
            MinecraftInstance.mc.field_71474_y.field_74370_x.field_74513_e = false;
        }
        lockRotation = null;
        staticRotation = null;
        MinecraftInstance.mc.field_71428_T.field_74278_d = 1.0f;
        shouldGoDown = false;
        RotationUtils.reset();
        SlotUtils.INSTANCE.stopSet();
        placeTick = 0;
        switchPlaceTick = 0;
        blockAmount = 0;
        towerStatus = false;
    }

    @EventTarget
    public final void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (!this.shouldPlace()) {
            return;
        }
        if (towerModeValue.equals("BlocksMC") && towerStatus) {
            if (!MinecraftInstance.mc.field_71439_g.func_70644_a(Potion.field_76424_c)) {
                event.setX(event.getX() * 0.75);
                event.setZ(event.getZ() * 0.75);
            } else {
                event.setX(event.getX() * 0.7);
                event.setZ(event.getZ() * 0.7);
            }
        }
        if (towerModeValue.equals("WatchDog") && towerStatus) {
            event.setX(event.getX() * (double)(this.isLookingDiagonally() ? ((Number)speedDiagonallyVanilla.get()).floatValue() : ((Number)speedVanilla.get()).floatValue()));
            event.setZ(event.getZ() * (double)(this.isLookingDiagonally() ? ((Number)speedDiagonallyVanilla.get()).floatValue() : ((Number)speedVanilla.get()).floatValue()));
        }
        if (sprintModeValue.equals("BlocksMC") && bridgeMode.equals("AutoJump") && lowHopblocksmc.get().booleanValue() && !towerStatus && PlayerUtils.getOffGroundTicks() == 4) {
            MinecraftInstance.mc.field_71439_g.field_70181_x = -0.098;
        }
        if (sprintModeValue.equals("BlocksMC") && !towerStatus) {
            MovementUtils.INSTANCE.strafe();
            if (bridgeMode.equals("AutoJump")) {
                if (!MinecraftInstance.mc.field_71439_g.func_70644_a(Potion.field_76424_c)) {
                    if (lowHopblocksmc.get().booleanValue()) {
                        event.setX(event.getX() * 0.867);
                        event.setZ(event.getZ() * 0.867);
                    } else {
                        event.setX(event.getX() * 0.941);
                        event.setZ(event.getZ() * 0.941);
                    }
                } else if (lowHopblocksmc.get().booleanValue()) {
                    event.setX(event.getX() * 1.1);
                    event.setZ(event.getZ() * 1.1);
                } else {
                    event.setX(event.getX() * 1.21);
                    event.setZ(event.getZ() * 1.21);
                }
            }
        }
        if (sprintModeValue.equals("Custom")) {
            if (!(!motionCustom.get().booleanValue() || motionSpeedEffectCustom.get().booleanValue() && MinecraftInstance.mc.field_71439_g.func_70644_a(Potion.field_76424_c))) {
                event.setX(event.getX() * ((Number)motionSpeedCustom.get()).doubleValue());
                event.setZ(event.getZ() * ((Number)motionSpeedCustom.get()).doubleValue());
            } else if (motionSpeedEffectCustom.get().booleanValue() && MinecraftInstance.mc.field_71439_g.func_70644_a(Potion.field_76424_c)) {
                event.setX(event.getX() * ((Number)motionSpeedSpeedEffectCustom.get()).doubleValue());
                event.setZ(event.getZ() * ((Number)motionSpeedSpeedEffectCustom.get()).doubleValue());
            }
        }
        if (((Boolean)safeWalkValue.get()).booleanValue() && MinecraftInstance.mc.field_71439_g.field_70122_E) {
            event.setSafeWalk(true);
        }
        if (!towerStatus && prevTowered && MinecraftInstance.mc.field_71439_g.field_70122_E) {
            prevTowered = false;
        }
    }

    private final boolean search(BlockPos blockPos, boolean raycast) {
        EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
        if (entityPlayerSP == null) {
            return false;
        }
        EntityPlayerSP player = entityPlayerSP;
        if (!BlockUtils.isReplaceable(blockPos)) {
            return false;
        }
        float maxReach = MinecraftInstance.mc.field_71442_b.func_78757_d();
        Vec3 eyes = EntityExtensionKt.getEyesLoc((Entity)player);
        PlaceRotation currPlaceRotation = null;
        PlaceRotation placeRotation = null;
        Object object = EnumFacing.values();
        int n = 0;
        int n2 = ((EnumFacing[])object).length;
        while (n < n2) {
            EnumFacing side = object[n];
            ++n;
            BlockPos neighbor = blockPos.func_177972_a(side);
            if (!BlockUtils.canBeClicked(neighbor)) continue;
            for (double x = 0.1; x < 0.9; x += 0.1) {
                for (double y = 0.1; y < 0.9; y += 0.1) {
                    double z = 0.1;
                    while (z < 0.9) {
                        Intrinsics.checkNotNullExpressionValue(neighbor, "neighbor");
                        currPlaceRotation = this.findTargetPlace(blockPos, neighbor, new Vec3(x, y, z), side, eyes, maxReach, raycast);
                        if (currPlaceRotation == null) {
                            z += 0.1;
                            continue;
                        }
                        if (placeRotation == null || RotationUtils.getRotationDifference(currPlaceRotation.getRotation(), this.getCurrRotation()) < RotationUtils.getRotationDifference(placeRotation.getRotation(), this.getCurrRotation())) {
                            placeRotation = currPlaceRotation;
                        }
                        z += 0.1;
                    }
                }
            }
        }
        if (placeRotation == null) {
            return false;
        }
        String string = rotationsValue.get().toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        switch (string) {
            case "stabilized": 
            case "normal": 
            case "snap": {
                Rotation rotation = placeRotation.getRotation();
                break;
            }
            case "vanilla": {
                Rotation rotation = RotationUtils.getFaceRotation(placeRotation.getPlaceInfo().getEnumFacing(), placeRotation.getPlaceInfo().getBlockPos());
                break;
            }
            case "watchdog": {
                Rotation rotation = new Rotation((float)((double)MinecraftInstance.mc.field_71439_g.field_70177_z - 150.0 + (Math.random() - 0.5) * 1.0), towerStatus ? 90.0f : 88.0f);
                break;
            }
            case "telly": {
                Rotation rotation;
                if (PlayerUtils.getOffGroundTicks() < ((Number)tellyTicks.get()).intValue() || PlayerUtils.getOffGroundTicks() >= 11) {
                    rotation = new Rotation(MovementUtils.INSTANCE.getMovingYaw(), placeRotation.getRotation().getPitch());
                    break;
                }
                rotation = new Rotation(RotationUtils.getRotations((double)blockPos.func_177958_n() + 0.5, (double)blockPos.func_177956_o() + 0.5, (double)blockPos.func_177952_p() + 0.5).getYaw(), placeRotation.getRotation().getPitch());
                break;
            }
            default: {
                Rotation rotation = lockRotation = null;
            }
        }
        if (bridgeMode.equals("GodBridge")) {
            lockRotation = prevTowered || takeVelo ? RotationUtils.getRotations((double)placeRotation.getPlaceInfo().getBlockPos().func_177958_n() + 0.5, (double)placeRotation.getPlaceInfo().getBlockPos().func_177956_o() + 0.5, (double)placeRotation.getPlaceInfo().getBlockPos().func_177952_p() + 0.5) : new Rotation(this.isLookingDiagonally() ? MovementUtils.INSTANCE.getMovingYaw() - (float)180 : MovementUtils.INSTANCE.getMovingYaw() + (rightSide ? 135.0f : -135.0f), 75.5f);
        }
        RotationUtils.setTargetRotation(lockRotation, rotationsValue.equals("Snap") ? 0 : 20);
        targetPlace = placeRotation.getPlaceInfo();
        return true;
    }

    /*
     * Unable to fully structure code
     */
    private final void calculateSide() {
        block2: {
            if (!MinecraftInstance.mc.field_71439_g.field_70122_E) break block2;
            if (!(Math.floor(MinecraftInstance.mc.field_71439_g.field_70165_t + (double)((float)Math.cos(OtherExtensionKt.toRadians(MovementUtils.INSTANCE.getMovingYaw()))) * 0.5) == Math.floor(MinecraftInstance.mc.field_71439_g.field_70165_t))) ** GOTO lbl-1000
            if (!(Math.floor(MinecraftInstance.mc.field_71439_g.field_70161_v + (double)((float)Math.sin(OtherExtensionKt.toRadians(MovementUtils.INSTANCE.getMovingYaw()))) * 0.5) == Math.floor(MinecraftInstance.mc.field_71439_g.field_70161_v))) lbl-1000:
            // 2 sources

            {
                v0 = true;
            } else {
                v0 = false;
            }
            Scaffold.rightSide = v0;
        }
    }

    private final boolean sprint() {
        ListValue sprint = sprintModeValue;
        if (!MovementUtils.INSTANCE.isMoving()) {
            return false;
        }
        if (!(!sprint.equals("Normal") || towerStatus && towerModeValue.equals("BlocksMC"))) {
            return true;
        }
        if (sprint.equals("WatchDog")) {
            return towerStatus || GameSettings.func_100015_a((KeyBinding)MinecraftInstance.mc.field_71474_y.field_74313_G);
        }
        if (sprint.equals("Air") && !this.onGround()) {
            return true;
        }
        if (sprint.equals("BlocksMC")) {
            return true;
        }
        if (sprint.equals("Ground") && this.onGround()) {
            return true;
        }
        if (sprint.equals("Telly") && !prevTowered && bridgeMode.equals("Telly")) {
            return PlayerUtils.getOffGroundTicks() >= ((Number)tellyTicks.get()).intValue() && PlayerUtils.getOffGroundTicks() < 11;
        }
        if (sprint.equals("Legit")) {
            if (Math.abs((double)(MathHelper.func_76142_g((float)MinecraftInstance.mc.field_71439_g.field_70177_z) - MathHelper.func_76142_g((float)RotationUtils.serverRotation.getYaw()))) < 90.0) {
                return true;
            }
        }
        if (sprint.equals("Custom")) {
            if (strafeCustom.get().booleanValue()) {
                MovementUtils.INSTANCE.strafe(strafeSpeedCustom.get() != false ? ((Number)strafeSpeedCustomValue.get()).floatValue() : MovementUtils.INSTANCE.getSpeed());
            }
            return sprintCustom.get();
        }
        return false;
    }

    private final Vec3 modifyVec(Vec3 original, EnumFacing direction, Vec3 pos, boolean shouldModify) {
        Vec3 vec3;
        if (!shouldModify) {
            return original;
        }
        double x = original.field_72450_a;
        double y = original.field_72448_b;
        double z = original.field_72449_c;
        EnumFacing side = direction.func_176734_d();
        EnumFacing.Axis axis = side.func_176740_k();
        if (axis == null) {
            return original;
        }
        switch (WhenMappings.$EnumSwitchMapping$0[axis.ordinal()]) {
            case 1: {
                vec3 = new Vec3(x, pos.field_72448_b + (double)RangesKt.coerceAtLeast(side.func_176730_m().func_177956_o(), 0), z);
                break;
            }
            case 2: {
                vec3 = new Vec3(pos.field_72450_a + (double)RangesKt.coerceAtLeast(side.func_176730_m().func_177958_n(), 0), y, z);
                break;
            }
            case 3: {
                vec3 = new Vec3(x, y, pos.field_72449_c + (double)RangesKt.coerceAtLeast(side.func_176730_m().func_177952_p(), 0));
                break;
            }
            default: {
                throw new NoWhenBranchMatchedException();
            }
        }
        return vec3;
    }

    private final PlaceRotation findTargetPlace(BlockPos pos, BlockPos offsetPos, Vec3 vec3, EnumFacing side, Vec3 eyes, float maxReach, boolean raycast) {
        double dist;
        WorldClient worldClient = MinecraftInstance.mc.field_71441_e;
        if (worldClient == null) {
            return null;
        }
        WorldClient world = worldClient;
        Vec3 vec = new Vec3((Vec3i)pos).func_178787_e(vec3).func_72441_c((double)side.func_176730_m().func_177958_n() * vec3.field_72450_a, (double)side.func_176730_m().func_177956_o() * vec3.field_72448_b, (double)side.func_176730_m().func_177952_p() * vec3.field_72449_c);
        double distance = eyes.func_72438_d(vec);
        if (raycast && (distance > (double)maxReach || world.func_147447_a(eyes, vec, false, true, false) != null)) {
            return null;
        }
        Vec3 diff = vec.func_178788_d(eyes);
        if (side.func_176740_k() != EnumFacing.Axis.Y && (dist = Math.abs(side.func_176740_k() == EnumFacing.Axis.Z ? diff.field_72449_c : diff.field_72450_a)) < 0.0) {
            return null;
        }
        Rotation rotation = RotationUtils.toRotation(vec, false);
        rotation = rotationsValue.equals("Stabilized") ? new Rotation((float)Math.rint(rotation.getYaw() / 45.0f) * 45.0f, rotation.getPitch()) : rotation;
        MovingObjectPosition movingObjectPosition = this.performBlockRaytrace(this.getCurrRotation(), maxReach);
        if (movingObjectPosition != null) {
            MovingObjectPosition raytrace = movingObjectPosition;
            boolean bl = false;
            if (raytrace.field_72313_a == MovingObjectPosition.MovingObjectType.BLOCK && Intrinsics.areEqual(raytrace.func_178782_a(), offsetPos) && (!raycast || raytrace.field_178784_b == side.func_176734_d())) {
                BlockPos blockPos = raytrace.func_178782_a();
                Intrinsics.checkNotNullExpressionValue(blockPos, "raytrace.blockPos");
                BlockPos blockPos2 = blockPos;
                blockPos = side.func_176734_d();
                Intrinsics.checkNotNullExpressionValue(blockPos, "side.opposite");
                BlockPos blockPos3 = blockPos;
                blockPos = raytrace.field_72307_f;
                Intrinsics.checkNotNullExpressionValue(blockPos, "raytrace.hitVec");
                return new PlaceRotation(new PlaceInfo(blockPos2, (EnumFacing)blockPos3, INSTANCE.modifyVec((Vec3)blockPos, side, new Vec3((Vec3i)offsetPos), !raycast)), INSTANCE.getCurrRotation());
            }
        }
        Rotation rotation2 = rotation;
        Intrinsics.checkNotNullExpressionValue(rotation2, "rotation");
        MovingObjectPosition movingObjectPosition2 = this.performBlockRaytrace(rotation2, maxReach);
        if (movingObjectPosition2 == null) {
            return null;
        }
        MovingObjectPosition raytrace = movingObjectPosition2;
        if (raytrace.field_72313_a == MovingObjectPosition.MovingObjectType.BLOCK && Intrinsics.areEqual(raytrace.func_178782_a(), offsetPos) && (!raycast || raytrace.field_178784_b == side.func_176734_d())) {
            Object object = raytrace.func_178782_a();
            Intrinsics.checkNotNullExpressionValue(object, "raytrace.blockPos");
            BlockPos blockPos = object;
            object = side.func_176734_d();
            Intrinsics.checkNotNullExpressionValue(object, "side.opposite");
            BlockPos blockPos4 = object;
            object = raytrace.field_72307_f;
            Intrinsics.checkNotNullExpressionValue(object, "raytrace.hitVec");
            PlaceInfo placeInfo = new PlaceInfo(blockPos, (EnumFacing)blockPos4, this.modifyVec((Vec3)object, side, new Vec3((Vec3i)offsetPos), !raycast));
            object = rotation;
            Intrinsics.checkNotNullExpressionValue(object, "rotation");
            return new PlaceRotation(placeInfo, (Rotation)object);
        }
        return null;
    }

    private final MovingObjectPosition performBlockRaytrace(Rotation rotation, float maxReach) {
        EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
        if (entityPlayerSP == null) {
            return null;
        }
        EntityPlayerSP player = entityPlayerSP;
        WorldClient worldClient = MinecraftInstance.mc.field_71441_e;
        if (worldClient == null) {
            return null;
        }
        WorldClient world = worldClient;
        Vec3 eyes = EntityExtensionKt.getEyesLoc((Entity)player);
        Vec3 rotationVec = RotationUtils.getVectorForRotation(rotation);
        Vec3 reach = eyes.func_72441_c(rotationVec.field_72450_a * (double)maxReach, rotationVec.field_72448_b * (double)maxReach, rotationVec.field_72449_c * (double)maxReach);
        return world.func_147447_a(eyes, reach, false, false, true);
    }

    private final float getRotationSpeed() {
        return (Boolean)rotationSpeedValue.get() != false ? (float)(Math.random() * (double)(((Number)maxRotationSpeedValue.get()).intValue() - ((Number)minRotationSpeedValue.get()).intValue()) + ((Number)minRotationSpeedValue.get()).doubleValue()) : Float.MAX_VALUE;
    }

    private final long getGetDelay() {
        return TimeUtils.INSTANCE.randomDelay(((Number)minPlaceDelay.get()).intValue(), ((Number)maxPlaceDelay.get()).intValue());
    }

    @Override
    @NotNull
    public String getTag() {
        return (String)bridgeMode.get();
    }

    private final boolean onGround() {
        return MinecraftInstance.mc.field_71439_g.field_70122_E || PlayerUtils.getOffGroundTicks() == 0;
    }

    public static final /* synthetic */ ListValue access$getBridgeMode$p() {
        return bridgeMode;
    }

    public static final /* synthetic */ ListValue access$getTowerModeValue$p() {
        return towerModeValue;
    }

    public static final /* synthetic */ BoolValue access$getHighBlock$p() {
        return highBlock;
    }

    public static final /* synthetic */ Value access$getHighBlockMode$p() {
        return highBlockMode;
    }

    public static final /* synthetic */ void access$setCancelSprint$p(boolean bl) {
        cancelSprint = bl;
    }

    public static final /* synthetic */ Value access$getSprintCustom$p() {
        return sprintCustom;
    }

    public static final /* synthetic */ Value access$getMotionCustom$p() {
        return motionCustom;
    }

    public static final /* synthetic */ Value access$getMotionSpeedEffectCustom$p() {
        return motionSpeedEffectCustom;
    }

    public static final /* synthetic */ Value access$getStrafeCustom$p() {
        return strafeCustom;
    }

    public static final /* synthetic */ Value access$getStrafeSpeedCustom$p() {
        return strafeSpeedCustom;
    }

    public static final /* synthetic */ IntegerValue access$getMinRotationSpeedValue$p() {
        return minRotationSpeedValue;
    }

    public static final /* synthetic */ Value access$getRotationsValue$p() {
        return rotationsValue;
    }

    public static final /* synthetic */ BoolValue access$getRotationSpeedValue$p() {
        return rotationSpeedValue;
    }

    public static final /* synthetic */ IntegerValue access$getMaxRotationSpeedValue$p() {
        return maxRotationSpeedValue;
    }

    public static final /* synthetic */ IntegerValue access$getMinPlaceDelay$p() {
        return minPlaceDelay;
    }

    public static final /* synthetic */ IntegerValue access$getMaxPlaceDelay$p() {
        return maxPlaceDelay;
    }

    public static final /* synthetic */ IntegerValue access$getExpandLengthValue$p() {
        return expandLengthValue;
    }

    static {
        Object[] objectArray = new String[]{"Normal", "Stabilized", "Vanilla", "WatchDog", "Telly", "Snap", "None"};
        rotationsValue = new ListValue("Rotations", (String[])objectArray, "Normal").displayable(rotationsValue.1.INSTANCE);
        objectArray = new String[]{"None", "NCP", "BlocksMC", "WatchDog", "Vanilla"};
        towerModeValue = new ListValue("TowerMode", (String[])objectArray, "None");
        speedVanilla = new FloatValue("Speed", 1.0f, 0.1f, 1.0f).displayable(speedVanilla.1.INSTANCE);
        speedDiagonallyVanilla = new FloatValue("Speed-Diagonally", 1.0f, 0.1f, 1.0f).displayable(speedDiagonallyVanilla.1.INSTANCE);
        objectArray = new String[]{"Post", "Pre", "GameTick"};
        placeMethod = new ListValue("PlaceEvent", (String[])objectArray, "GameTick");
        objectArray = new String[]{"Spoof", "Switch"};
        autoBlockValue = new ListValue("AutoBlock", (String[])objectArray, "Switch");
        highBlock = new BoolValue("BiggestStack", false);
        highBlockMode = new BoolValue("BiggestStackSwitchTick", false).displayable(highBlockMode.1.INSTANCE);
        switchTickValue = new IntegerValue("SwitchPlaceTick", 0, 0, 10).displayable(switchTickValue.1.INSTANCE);
        String[] stringArray = new String[]{"Normal", "Air", "Ground", "WatchDog", "BlocksMC", "Telly", "Legit", "Custom", "None"};
        objectArray = stringArray;
        sprintModeValue = new ListValue((String[])objectArray){

            protected void onChanged(@NotNull String oldValue, @NotNull String newValue) {
                Intrinsics.checkNotNullParameter(oldValue, "oldValue");
                Intrinsics.checkNotNullParameter(newValue, "newValue");
                if (Intrinsics.areEqual(newValue, "BlocksMC")) {
                    Scaffold.access$setCancelSprint$p(true);
                    MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C0BPacketEntityAction((Entity)MinecraftInstance.mc.field_71439_g, C0BPacketEntityAction.Action.STOP_SPRINTING));
                }
            }
        };
        lowHopblocksmc = new BoolValue("BlocksMC-LowHop", false).displayable(lowHopblocksmc.1.INSTANCE);
        sprintCustom = new BoolValue("CustomSprint", true).displayable(sprintCustom.1.INSTANCE);
        cancelSprintCustom = (BoolValue)new BoolValue(){

            protected void onChanged(boolean oldValue, boolean newValue) {
                if (((Boolean)Scaffold.access$getSprintCustom$p().get()).booleanValue()) {
                    Scaffold.access$setCancelSprint$p(true);
                    MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C0BPacketEntityAction((Entity)MinecraftInstance.mc.field_71439_g, C0BPacketEntityAction.Action.STOP_SPRINTING));
                }
            }
        }.displayable(cancelSprintCustom.2.INSTANCE);
        motionCustom = new BoolValue("CustomMotion", false).displayable(motionCustom.1.INSTANCE);
        motionSpeedCustom = new FloatValue("CustomMotionSpeed", 1.0f, 0.1f, 2.0f).displayable(motionSpeedCustom.1.INSTANCE);
        motionSpeedEffectCustom = new BoolValue("CustomMotion-SpeedEffect", false).displayable(motionSpeedEffectCustom.1.INSTANCE);
        motionSpeedSpeedEffectCustom = new FloatValue("CustomMotionSpeed-SpeedEffect", 1.0f, 0.1f, 2.0f).displayable(motionSpeedSpeedEffectCustom.1.INSTANCE);
        strafeCustom = new BoolValue("CustomStrafe", false).displayable(strafeCustom.1.INSTANCE);
        strafeSpeedCustom = new BoolValue("CustomStrafeSpeed", false).displayable(strafeSpeedCustom.1.INSTANCE);
        strafeSpeedCustomValue = new FloatValue("CustomStrafeSpeed", 0.1f, 0.1f, 1.0f).displayable(strafeSpeedCustomValue.1.INSTANCE);
        objectArray = new String[]{"UpSideDown", "Andromeda", "Normal", "Telly", "GodBridge", "AutoJump", "KeepUP", "SameY"};
        bridgeMode = new ListValue("BridgeMode", (String[])objectArray, "Normal");
        waitRotation = new BoolValue("WaitRotation", false).displayable(waitRotation.1.INSTANCE);
        tellyTicks = new IntegerValue("TellyTicks", 0, 0, 10).displayable(tellyTicks.1.INSTANCE);
        sameYSpeed = new BoolValue("SameY-OnlySpeed", false).displayable(sameYSpeed.1.INSTANCE);
        andJump = new BoolValue("Andromeda-Jump", false).displayable(andJump.1.INSTANCE);
        strafeFix = new BoolValue("StrafeFix", false);
        swingValue = new BoolValue("Swing", false);
        searchValue = new BoolValue("Search", true);
        downValue = new BoolValue("Downward", false);
        safeWalkValue = new BoolValue("SafeWalk", false);
        zitterModeValue = new BoolValue("Zitter", false);
        rotationSpeedValue = new BoolValue("RotationSpeed", true);
        maxRotationSpeedValue = (IntegerValue)new IntegerValue(){

            protected void onChanged(int oldValue, int newValue) {
                int v = ((Number)Scaffold.access$getMinRotationSpeedValue$p().get()).intValue();
                if (v > newValue) {
                    this.set(v);
                }
            }
        }.displayable(maxRotationSpeedValue.2.INSTANCE);
        minRotationSpeedValue = (IntegerValue)new IntegerValue(){

            protected void onChanged(int oldValue, int newValue) {
                int v = ((Number)Scaffold.access$getMaxRotationSpeedValue$p().get()).intValue();
                if (v < newValue) {
                    this.set(v);
                }
            }
        }.displayable(minRotationSpeedValue.2.INSTANCE);
        maxPlaceDelay = new IntegerValue(){

            protected void onChanged(int oldValue, int newValue) {
                int v = ((Number)Scaffold.access$getMinPlaceDelay$p().get()).intValue();
                if (v > newValue) {
                    this.set(v);
                }
            }
        };
        minPlaceDelay = new IntegerValue(){

            protected void onChanged(int oldValue, int newValue) {
                int v = ((Number)Scaffold.access$getMaxPlaceDelay$p().get()).intValue();
                if (v < newValue) {
                    this.set(v);
                }
            }
        };
        expandLengthValue = new IntegerValue("ExpandLength", 1, 1, 6);
        omniDirectionalExpand = new BoolValue("OmniDirectionalExpand", false).displayable(omniDirectionalExpand.1.INSTANCE);
        timerValue = new FloatValue("Timer", 1.0f, 0.1f, 5.0f);
        towerTimerValue = new FloatValue("TowerTimer", 1.0f, 0.1f, 5.0f);
        objectArray = new String[]{"Packet", "Silent", "Normal", "Off"};
        eagleValue = new ListValue("Eagle", (String[])objectArray, "Off");
        blocksToEagleValue = new IntegerValue("BlocksToEagle", 0, 0, 10).displayable(blocksToEagleValue.1.INSTANCE);
        edgeDistanceValue = new FloatValue("EagleEdgeDistance", 0.0f, 0.0f, 0.5f).displayable(edgeDistanceValue.1.INSTANCE);
        objectArray = new String[]{"Simple", "Strict", "Legit", "Off"};
        hitableCheckValue = new ListValue("HitableCheck", (String[])objectArray, "Simple");
        zitterTimer = new MSTimer();
        delayTimer = new TimerMS();
        objectArray = new double[5];
        objectArray[0] = (String)0.2;
        objectArray[1] = (String)0.22;
        objectArray[2] = (String)0.28;
        objectArray[3] = (String)0.29;
        objectArray[4] = (String)0.3;
        floatSpeedLevels = (double[])objectArray;
        objectArray = new double[5];
        objectArray[0] = (String)0.3;
        objectArray[1] = (String)0.34;
        objectArray[2] = (String)0.38;
        objectArray[3] = (String)0.42;
        objectArray[4] = (String)0.42;
        towerSpeedLevels = (double[])objectArray;
    }

    @Metadata(mv={1, 6, 0}, k=3, xi=48)
    public final class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] nArray = new int[EnumFacing.Axis.values().length];
            nArray[EnumFacing.Axis.Y.ordinal()] = 1;
            nArray[EnumFacing.Axis.X.ordinal()] = 2;
            nArray[EnumFacing.Axis.Z.ordinal()] = 3;
            $EnumSwitchMapping$0 = nArray;
        }
    }
}

