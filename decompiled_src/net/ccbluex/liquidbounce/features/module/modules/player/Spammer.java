/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.player;

import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.player.KillSay;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.TextValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.entity.EntityLivingBase;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(name="Spammer", category=ModuleCategory.PLAYER)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0013\u001a\u00020\u0014H\u0016J\u0010\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u0017H\u0007J\u0010\u0010\u0018\u001a\u00020\t2\u0006\u0010\u0019\u001a\u00020\tH\u0002J\u0010\u0010\u001a\u001a\u00020\t2\u0006\u0010\u0019\u001a\u00020\tH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001b"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/player/Spammer;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "delay", "", "endingCharsValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "insultMessageValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "lastIndex", "", "maxDelayValue", "messageValue", "minDelayValue", "modeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "msTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "onEnable", "", "onUpdate", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "replace", "str", "replaceAbuse", "CrossSine"})
public final class Spammer
extends Module {
    @NotNull
    private final IntegerValue maxDelayValue = new IntegerValue(this){
        final /* synthetic */ Spammer this$0;
        {
            this.this$0 = $receiver;
            super("MaxDelay", 1000, 0, 5000);
        }

        protected void onChanged(int oldValue, int newValue) {
            int minDelayValueObject = ((Number)Spammer.access$getMinDelayValue$p(this.this$0).get()).intValue();
            if (minDelayValueObject > newValue) {
                this.set(minDelayValueObject);
            }
            Spammer.access$setDelay$p(this.this$0, TimeUtils.INSTANCE.randomDelay(((Number)Spammer.access$getMinDelayValue$p(this.this$0).get()).intValue(), ((Number)this.get()).intValue()));
        }
    };
    @NotNull
    private final IntegerValue minDelayValue = new IntegerValue(this){
        final /* synthetic */ Spammer this$0;
        {
            this.this$0 = $receiver;
            super("MinDelay", 500, 0, 5000);
        }

        protected void onChanged(int oldValue, int newValue) {
            int maxDelayValueObject = ((Number)Spammer.access$getMaxDelayValue$p(this.this$0).get()).intValue();
            if (maxDelayValueObject < newValue) {
                this.set(maxDelayValueObject);
            }
            Spammer.access$setDelay$p(this.this$0, TimeUtils.INSTANCE.randomDelay(((Number)this.get()).intValue(), ((Number)Spammer.access$getMaxDelayValue$p(this.this$0).get()).intValue()));
        }
    };
    @NotNull
    private final ListValue modeValue;
    @NotNull
    private final IntegerValue endingCharsValue;
    @NotNull
    private final Value<String> messageValue;
    @NotNull
    private final Value<String> insultMessageValue;
    @NotNull
    private final MSTimer msTimer;
    private long delay;
    private int lastIndex;

    public Spammer() {
        String[] stringArray = new String[]{"Single", "Insult", "OrderInsult"};
        this.modeValue = new ListValue("Mode", stringArray, "Single");
        this.endingCharsValue = new IntegerValue("EndingRandomChars", 5, 0, 30);
        this.messageValue = new TextValue("Message", "Buy %r Minecraft %r Legit %r and %r stop %r using %r cracked %r servers %r%r").displayable(new Function0<Boolean>(this){
            final /* synthetic */ Spammer this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return !Spammer.access$getModeValue$p(this.this$0).contains("insult");
            }
        });
        this.insultMessageValue = new TextValue("InsultMessage", "[%s] %w [%s]").displayable(new Function0<Boolean>(this){
            final /* synthetic */ Spammer this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return Spammer.access$getModeValue$p(this.this$0).contains("insult");
            }
        });
        this.msTimer = new MSTimer();
        this.delay = TimeUtils.INSTANCE.randomDelay(((Number)this.minDelayValue.get()).intValue(), ((Number)this.maxDelayValue.get()).intValue());
        this.lastIndex = -1;
    }

    @Override
    public void onEnable() {
        this.lastIndex = -1;
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (MinecraftInstance.mc.field_71462_r != null && MinecraftInstance.mc.field_71462_r instanceof GuiChat) {
            return;
        }
        if (this.modeValue.equals("Single") && StringsKt.startsWith$default(this.messageValue.get(), ".", false, 2, null)) {
            CrossSine.INSTANCE.getCommandManager().executeCommands(this.messageValue.get());
            return;
        }
        if (this.msTimer.hasTimePassed(this.delay)) {
            String string;
            EntityPlayerSP entityPlayerSP = MinecraftInstance.mc.field_71439_g;
            String string2 = ((String)this.modeValue.get()).toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(string2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            String string3 = string2;
            if (Intrinsics.areEqual(string3, "insult")) {
                string = this.replaceAbuse(KillSay.INSTANCE.getRandomOne());
            } else if (Intrinsics.areEqual(string3, "orderinsult")) {
                int n = this.lastIndex;
                this.lastIndex = n + 1;
                if (this.lastIndex >= KillSay.INSTANCE.getInsultWords().size() - 1) {
                    this.lastIndex = 0;
                }
                string = this.replaceAbuse(KillSay.INSTANCE.getInsultWords().get(this.lastIndex));
            } else {
                string = this.replace(this.messageValue.get());
            }
            entityPlayerSP.func_71165_d(string);
            this.msTimer.reset();
            this.delay = TimeUtils.INSTANCE.randomDelay(((Number)this.minDelayValue.get()).intValue(), ((Number)this.maxDelayValue.get()).intValue());
        }
    }

    private final String replaceAbuse(String str) {
        return this.replace(StringsKt.replace$default(this.insultMessageValue.get(), "%w", str, false, 4, null));
    }

    private final String replace(String str) {
        String string;
        String string2 = StringsKt.replace$default(StringsKt.replace$default(StringsKt.replace$default(str, "%r", String.valueOf(RandomUtils.nextInt(0, 99)), false, 4, null), "%s", RandomUtils.INSTANCE.randomString(3), false, 4, null), "%c", RandomUtils.INSTANCE.randomString(1), false, 4, null);
        if (CrossSine.INSTANCE.getCombatManager().getTarget() != null) {
            EntityLivingBase entityLivingBase = CrossSine.INSTANCE.getCombatManager().getTarget();
            Intrinsics.checkNotNull(entityLivingBase);
            string = entityLivingBase.func_70005_c_();
        } else {
            string = "You";
        }
        String string3 = string;
        Intrinsics.checkNotNullExpressionValue(string3, "if (CrossSine.combatMana\u2026t!!.name } else { \"You\" }");
        return Intrinsics.stringPlus(StringsKt.replace$default(string2, "%name%", string3, false, 4, null), RandomUtils.INSTANCE.randomString(((Number)this.endingCharsValue.get()).intValue()));
    }

    public static final /* synthetic */ IntegerValue access$getMinDelayValue$p(Spammer $this) {
        return $this.minDelayValue;
    }

    public static final /* synthetic */ void access$setDelay$p(Spammer $this, long l) {
        $this.delay = l;
    }

    public static final /* synthetic */ IntegerValue access$getMaxDelayValue$p(Spammer $this) {
        return $this.maxDelayValue;
    }

    public static final /* synthetic */ ListValue access$getModeValue$p(Spammer $this) {
        return $this.modeValue;
    }
}

