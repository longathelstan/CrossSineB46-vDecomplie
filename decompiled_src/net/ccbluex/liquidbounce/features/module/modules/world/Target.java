/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.world;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.entity.EntityLivingBase;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(name="Target", category=ModuleCategory.WORLD, canEnable=false)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\u000e\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u0015R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006R\u0011\u0010\u000b\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0006R\u0011\u0010\r\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0006R\u0011\u0010\u000f\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0006\u00a8\u0006\u0016"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/world/Target;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "animalValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "getAnimalValue", "()Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "deadValue", "getDeadValue", "friendValue", "getFriendValue", "invisibleValue", "getInvisibleValue", "mobValue", "getMobValue", "playerValue", "getPlayerValue", "handleEvents", "", "isInYourTeam", "entity", "Lnet/minecraft/entity/EntityLivingBase;", "CrossSine"})
public final class Target
extends Module {
    @NotNull
    public static final Target INSTANCE = new Target();
    @NotNull
    private static final BoolValue playerValue = new BoolValue("Player", true);
    @NotNull
    private static final BoolValue animalValue = new BoolValue("Animal", false);
    @NotNull
    private static final BoolValue mobValue = new BoolValue("Mob", true);
    @NotNull
    private static final BoolValue invisibleValue = new BoolValue("Invisible", false);
    @NotNull
    private static final BoolValue deadValue = new BoolValue("Dead", false);
    @NotNull
    private static final BoolValue friendValue = new BoolValue("NoFriend", false);

    private Target() {
    }

    @NotNull
    public final BoolValue getPlayerValue() {
        return playerValue;
    }

    @NotNull
    public final BoolValue getAnimalValue() {
        return animalValue;
    }

    @NotNull
    public final BoolValue getMobValue() {
        return mobValue;
    }

    @NotNull
    public final BoolValue getInvisibleValue() {
        return invisibleValue;
    }

    @NotNull
    public final BoolValue getDeadValue() {
        return deadValue;
    }

    @NotNull
    public final BoolValue getFriendValue() {
        return friendValue;
    }

    public final boolean isInYourTeam(@NotNull EntityLivingBase entity) {
        Intrinsics.checkNotNullParameter(entity, "entity");
        if (((Boolean)friendValue.get()).booleanValue()) {
            if (MinecraftInstance.mc.field_71439_g == null) {
                return false;
            }
            if (MinecraftInstance.mc.field_71439_g.func_96124_cp() != null && entity.func_96124_cp() != null && MinecraftInstance.mc.field_71439_g.func_96124_cp().func_142054_a(entity.func_96124_cp())) {
                return true;
            }
            if (MinecraftInstance.mc.field_71439_g.func_145748_c_() != null && entity.func_145748_c_() != null) {
                String string = entity.func_145748_c_().func_150254_d();
                Intrinsics.checkNotNullExpressionValue(string, "entity.displayName.formattedText");
                String targetName = StringsKt.replace$default(string, "\u00a7r", "", false, 4, null);
                String string2 = MinecraftInstance.mc.field_71439_g.func_145748_c_().func_150254_d();
                Intrinsics.checkNotNullExpressionValue(string2, "mc.thePlayer.displayName.formattedText");
                String clientName = StringsKt.replace$default(string2, "\u00a7r", "", false, 4, null);
                return StringsKt.startsWith$default(targetName, Intrinsics.stringPlus("\u00a7", Character.valueOf(clientName.charAt(1))), false, 2, null);
            }
        }
        return false;
    }

    @Override
    public boolean handleEvents() {
        return true;
    }
}

