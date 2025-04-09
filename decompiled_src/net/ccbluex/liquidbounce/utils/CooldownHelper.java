/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.utils;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.utils.MinecraftInstanceKt;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0007\u001a\u00020\u0004J\u0006\u0010\b\u001a\u00020\u0004J\u0006\u0010\t\u001a\u00020\nJ\u0006\u0010\u000b\u001a\u00020\nJ\u0010\u0010\f\u001a\u00020\n2\b\u0010\r\u001a\u0004\u0018\u00010\u000eR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2={"Lnet/ccbluex/liquidbounce/utils/CooldownHelper;", "", "()V", "genericAttackSpeed", "", "lastAttackedTicks", "", "getAttackCooldownProgress", "getAttackCooldownProgressPerTick", "incrementLastAttackedTicks", "", "resetLastAttackedTicks", "updateGenericAttackSpeed", "itemStack", "Lnet/minecraft/item/ItemStack;", "CrossSine"})
public final class CooldownHelper {
    @NotNull
    public static final CooldownHelper INSTANCE = new CooldownHelper();
    private static int lastAttackedTicks;
    private static double genericAttackSpeed;

    private CooldownHelper() {
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    public final void updateGenericAttackSpeed(@Nullable ItemStack itemStack) {
        block22: {
            block26: {
                block25: {
                    block24: {
                        block23: {
                            block21: {
                                v0 = itemStack;
                                v1 /* !! */  = var2_2 = v0 == null ? null : v0.func_77973_b();
                                if (!(var2_2 instanceof ItemSword)) break block21;
                                v2 = 1.6;
                                break block22;
                            }
                            if (!(var2_2 instanceof ItemAxe)) break block23;
                            v3 = itemStack.func_77973_b();
                            if (v3 == null) {
                                throw new NullPointerException("null cannot be cast to non-null type net.minecraft.item.ItemAxe");
                            }
                            axe = (ItemAxe)v3;
                            v4 = axe.func_150913_i();
                            switch (v4 == null ? -1 : WhenMappings.$EnumSwitchMapping$0[v4.ordinal()]) {
                                case 1: {
                                    v2 = 0.9;
                                    break;
                                }
                                case 2: 
                                case 3: {
                                    v2 = 0.8;
                                    break;
                                }
                                default: {
                                    v2 = 1.0;
                                    break;
                                }
                            }
                            break block22;
                        }
                        if (!(var2_2 instanceof ItemPickaxe)) break block24;
                        v2 = 1.2;
                        break block22;
                    }
                    if (!(var2_2 instanceof ItemSpade)) break block25;
                    v2 = 1.0;
                    break block22;
                }
                if (!(var2_2 instanceof ItemHoe)) break block26;
                v5 = itemStack.func_77973_b();
                if (v5 == null) {
                    throw new NullPointerException("null cannot be cast to non-null type net.minecraft.item.ItemHoe");
                }
                hoe = (ItemHoe)v5;
                var4_5 = hoe.func_77842_f();
                if (var4_5 == null) ** GOTO lbl-1000
                tmp = -1;
                switch (var4_5.hashCode()) {
                    case -1921929932: {
                        if (var4_5.equals("DIAMOND")) {
                            tmp = 1;
                        }
                        break;
                    }
                    case 2256072: {
                        if (var4_5.equals("IRON")) {
                            tmp = 2;
                        }
                        break;
                    }
                    case 79233093: {
                        if (var4_5.equals("STONE")) {
                            tmp = 3;
                        }
                        break;
                    }
                }
                switch (tmp) {
                    case 3: {
                        v2 = 2.0;
                        break;
                    }
                    case 2: {
                        v2 = 3.0;
                        break;
                    }
                    case 1: {
                        v2 = 4.0;
                        break;
                    }
                    default: lbl-1000:
                    // 2 sources

                    {
                        v2 = 1.0;
                        break;
                    }
                }
                break block22;
            }
            v2 = CooldownHelper.genericAttackSpeed = 4.0;
        }
        if (MinecraftInstanceKt.getMc().field_71439_g.func_70644_a(Potion.field_76419_f)) {
            CooldownHelper.genericAttackSpeed *= 1.0 - Math.min(1.0, 0.1 * (double)MinecraftInstanceKt.getMc().field_71439_g.func_70660_b(Potion.field_76419_f).func_76458_c() + (double)true);
        }
        if (MinecraftInstanceKt.getMc().field_71439_g.func_70644_a(Potion.field_76422_e)) {
            CooldownHelper.genericAttackSpeed *= 1.0 + (0.1 * (double)MinecraftInstanceKt.getMc().field_71439_g.func_70660_b(Potion.field_76422_e).func_76458_c() + (double)true);
        }
    }

    public final double getAttackCooldownProgressPerTick() {
        return 1.0 / genericAttackSpeed * 20.0;
    }

    public final double getAttackCooldownProgress() {
        return MathHelper.func_151237_a((double)((double)lastAttackedTicks / this.getAttackCooldownProgressPerTick()), (double)0.0, (double)1.0);
    }

    public final void resetLastAttackedTicks() {
        lastAttackedTicks = 0;
    }

    public final void incrementLastAttackedTicks() {
        int n = lastAttackedTicks;
        lastAttackedTicks = n + 1;
    }

    @Metadata(mv={1, 6, 0}, k=3, xi=48)
    public final class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] nArray = new int[Item.ToolMaterial.values().length];
            nArray[Item.ToolMaterial.IRON.ordinal()] = 1;
            nArray[Item.ToolMaterial.WOOD.ordinal()] = 2;
            nArray[Item.ToolMaterial.STONE.ordinal()] = 3;
            $EnumSwitchMapping$0 = nArray;
        }
    }
}

