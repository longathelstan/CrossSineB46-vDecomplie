/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.visual;

import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.event.EntityKilledEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.server.S2CPacketSpawnGlobalEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(name="KillEffect", category=ModuleCategory.VISUAL)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0002J\u0010\u0010\u0010\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\u0012H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/visual/KillEffect;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "blockState", "", "lightingSoundValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "modeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "timesValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "displayEffectFor", "", "entity", "Lnet/minecraft/entity/EntityLivingBase;", "onKilled", "event", "Lnet/ccbluex/liquidbounce/event/EntityKilledEvent;", "CrossSine"})
public final class KillEffect
extends Module {
    @NotNull
    private final ListValue modeValue;
    @NotNull
    private final IntegerValue timesValue;
    @NotNull
    private final Value<Boolean> lightingSoundValue;
    private final int blockState;

    public KillEffect() {
        String[] stringArray = new String[]{"Lighting", "Blood", "Fire"};
        this.modeValue = new ListValue("Mode", stringArray, "Lighting");
        this.timesValue = new IntegerValue("Times", 1, 1, 10);
        this.lightingSoundValue = new BoolValue("LightingSound", true).displayable(new Function0<Boolean>(this){
            final /* synthetic */ KillEffect this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return KillEffect.access$getModeValue$p(this.this$0).equals("Lighting");
            }
        });
        this.blockState = Block.func_176210_f((IBlockState)Blocks.field_150451_bX.func_176223_P());
    }

    @EventTarget
    public final void onKilled(@NotNull EntityKilledEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.displayEffectFor(event.getTargetEntity());
    }

    private final void displayEffectFor(EntityLivingBase entity) {
        int n = ((Number)this.timesValue.get()).intValue();
        int n2 = 0;
        while (n2 < n) {
            int n3;
            int it = n3 = n2++;
            boolean bl = false;
            String string = ((String)this.modeValue.get()).toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            switch (string) {
                case "lighting": {
                    MinecraftInstance.mc.func_147114_u().func_147292_a(new S2CPacketSpawnGlobalEntity((Entity)new EntityLightningBolt((World)MinecraftInstance.mc.field_71441_e, entity.field_70165_t, entity.field_70163_u, entity.field_70161_v)));
                    if (!this.lightingSoundValue.get().booleanValue()) break;
                    MinecraftInstance.mc.func_147118_V().func_147682_a((ISound)PositionedSoundRecord.func_147674_a((ResourceLocation)new ResourceLocation("random.explode"), (float)1.0f));
                    MinecraftInstance.mc.func_147118_V().func_147682_a((ISound)PositionedSoundRecord.func_147674_a((ResourceLocation)new ResourceLocation("ambient.weather.thunder"), (float)1.0f));
                    break;
                }
                case "blood": {
                    int n4 = 10;
                    int n5 = 0;
                    while (n5 < n4) {
                        int n6;
                        int it2 = n6 = n5++;
                        boolean bl2 = false;
                        int[] nArray = new int[]{this.blockState};
                        MinecraftInstance.mc.field_71452_i.func_178927_a(EnumParticleTypes.BLOCK_CRACK.func_179348_c(), entity.field_70165_t, entity.field_70163_u + (double)(entity.field_70131_O / (float)2), entity.field_70161_v, entity.field_70159_w + (double)RandomUtils.INSTANCE.nextFloat(-0.5f, 0.5f), entity.field_70181_x + (double)RandomUtils.INSTANCE.nextFloat(-0.5f, 0.5f), entity.field_70179_y + (double)RandomUtils.INSTANCE.nextFloat(-0.5f, 0.5f), nArray);
                    }
                    break;
                }
                case "fire": {
                    MinecraftInstance.mc.field_71452_i.func_178926_a((Entity)entity, EnumParticleTypes.LAVA);
                }
            }
        }
    }

    public static final /* synthetic */ ListValue access$getModeValue$p(KillEffect $this) {
        return $this.modeValue;
    }
}

