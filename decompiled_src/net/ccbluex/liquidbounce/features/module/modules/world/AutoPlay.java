/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.world;

import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.world.AutoPlay;
import net.ccbluex.liquidbounce.features.special.AutoDisable;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.event.ClickEvent;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S2DPacketOpenWindow;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraft.util.IChatComponent;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(name="AutoPlay", category=ModuleCategory.WORLD)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0016\u001a\u00020\u0005H\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\u0010\u0010\u0019\u001a\u00020\u00182\u0006\u0010\u001a\u001a\u00020\u001bH\u0007J\u0010\u0010\u001c\u001a\u00020\u00182\u0006\u0010\u001a\u001a\u00020\u001dH\u0007J \u0010\u001e\u001a\u00020\u00182\b\b\u0002\u0010\u001f\u001a\u00020 2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00180\"H\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\u00020\u00078VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0015\u001a\u00020\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006#"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/world/AutoPlay;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "autoStartValue", "Lnet/ccbluex/liquidbounce/features/value/Value;", "", "bwModeValue", "", "clickState", "", "clicking", "delayValue", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "modeValue", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "queued", "replayWhenKickedValue", "showGuiWhenFailedValue", "tag", "getTag", "()Ljava/lang/String;", "waitForLobby", "handleEvents", "onEnable", "", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", "queueAutoPlay", "delay", "", "runnable", "Lkotlin/Function0;", "CrossSine"})
public final class AutoPlay
extends Module {
    @NotNull
    private final ListValue modeValue;
    @NotNull
    private final Value<String> bwModeValue;
    @NotNull
    private final Value<Boolean> autoStartValue;
    @NotNull
    private final Value<Boolean> replayWhenKickedValue;
    @NotNull
    private final Value<Boolean> showGuiWhenFailedValue;
    @NotNull
    private final IntegerValue delayValue;
    private boolean clicking;
    private boolean queued;
    private int clickState;
    private boolean waitForLobby;

    public AutoPlay() {
        String[] stringArray = new String[]{"RedeSky", "BlocksMC", "Minemora", "Hypixel", "Jartex", "Pika", "Hydracraft", "HyCraft", "MineFC/HeroMC_Bedwars", "Supercraft"};
        this.modeValue = new ListValue("Server", stringArray, "RedeSky");
        stringArray = new String[]{"SOLO", "4v4v4v4"};
        this.bwModeValue = new ListValue("Mode", stringArray, "4v4v4v4").displayable(new Function0<Boolean>(this){
            final /* synthetic */ AutoPlay this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return AutoPlay.access$getModeValue$p(this.this$0).equals("MineFC/HeroMC_Bedwars");
            }
        });
        this.autoStartValue = new BoolValue("AutoStartAtLobby", true).displayable(new Function0<Boolean>(this){
            final /* synthetic */ AutoPlay this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return AutoPlay.access$getModeValue$p(this.this$0).equals("MineFC/HeroMC_Bedwars");
            }
        });
        this.replayWhenKickedValue = new BoolValue("ReplayWhenKicked", true).displayable(new Function0<Boolean>(this){
            final /* synthetic */ AutoPlay this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return AutoPlay.access$getModeValue$p(this.this$0).equals("MineFC/HeroMC_Bedwars");
            }
        });
        this.showGuiWhenFailedValue = new BoolValue("ShowGuiWhenFailed", true).displayable(new Function0<Boolean>(this){
            final /* synthetic */ AutoPlay this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            @NotNull
            public final Boolean invoke() {
                return AutoPlay.access$getModeValue$p(this.this$0).equals("MineFC/HeroMC_Bedwars");
            }
        });
        this.delayValue = new IntegerValue("JoinDelay", 3, 0, 7);
    }

    @Override
    public void onEnable() {
        this.clickState = 0;
        this.clicking = false;
        this.queued = false;
        this.waitForLobby = false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Packet<?> packet;
        block61: {
            Intrinsics.checkNotNullParameter(event, "event");
            packet = event.getPacket();
            String string = ((String)this.modeValue.get()).toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            String string2 = string;
            if (Intrinsics.areEqual(string2, "redesky")) {
                if (this.clicking && (packet instanceof C0EPacketClickWindow || packet instanceof C07PacketPlayerDigging)) {
                    event.cancelEvent();
                    return;
                }
                if (this.clickState == 2 && packet instanceof S2DPacketOpenWindow) {
                    event.cancelEvent();
                }
            } else if (Intrinsics.areEqual(string2, "hypixel") && this.clickState == 1 && packet instanceof S2DPacketOpenWindow) {
                event.cancelEvent();
            }
            if (!(packet instanceof S2FPacketSetSlot)) break block61;
            ItemStack itemStack = ((S2FPacketSetSlot)packet).func_149174_e();
            if (itemStack == null) {
                return;
            }
            ItemStack item = itemStack;
            int windowId = ((S2FPacketSetSlot)packet).func_149175_c();
            int slot = ((S2FPacketSetSlot)packet).func_149173_d();
            String itemName = item.func_77977_a();
            String displayName = item.func_82833_r();
            String string3 = ((String)this.modeValue.get()).toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(string3, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            switch (string3) {
                case "redesky": {
                    if (this.clickState == 0 && windowId == 0 && slot == 42) {
                        Intrinsics.checkNotNullExpressionValue(itemName, "itemName");
                        if (StringsKt.contains((CharSequence)itemName, "paper", true)) {
                            Intrinsics.checkNotNullExpressionValue(displayName, "displayName");
                            if (StringsKt.contains((CharSequence)displayName, "Jogar novamente", true)) {
                                this.clickState = 1;
                                this.clicking = true;
                                AutoPlay.queueAutoPlay$default(this, 0L, new Function0<Unit>(item, this){
                                    final /* synthetic */ ItemStack $item;
                                    final /* synthetic */ AutoPlay this$0;
                                    {
                                        this.$item = $item;
                                        this.this$0 = $receiver;
                                        super(0);
                                    }

                                    public final void invoke() {
                                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C09PacketHeldItemChange(6));
                                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C08PacketPlayerBlockPlacement(this.$item));
                                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C09PacketHeldItemChange(MinecraftInstance.mc.field_71439_g.field_71071_by.field_70461_c));
                                        AutoPlay.access$setClickState$p(this.this$0, 2);
                                    }
                                }, 1, null);
                                return;
                            }
                        }
                    }
                    if (this.clickState != 2 || windowId == 0 || slot != 11) return;
                    Intrinsics.checkNotNullExpressionValue(itemName, "itemName");
                    if (!StringsKt.contains((CharSequence)itemName, "enderPearl", true)) return;
                    Timer timer = new Timer();
                    long l = 500L;
                    TimerTask timerTask2 = new TimerTask(this, windowId, slot, item){
                        final /* synthetic */ AutoPlay this$0;
                        final /* synthetic */ int $windowId$inlined;
                        final /* synthetic */ int $slot$inlined;
                        final /* synthetic */ ItemStack $item$inlined;
                        {
                            this.this$0 = autoPlay;
                            this.$windowId$inlined = n;
                            this.$slot$inlined = n2;
                            this.$item$inlined = itemStack;
                        }

                        public void run() {
                            TimerTask $this$onPacket_u24lambda_u2d0 = this;
                            boolean bl = false;
                            AutoPlay.access$setClicking$p(this.this$0, false);
                            AutoPlay.access$setClickState$p(this.this$0, 0);
                            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C0EPacketClickWindow(this.$windowId$inlined, this.$slot$inlined, 0, 0, this.$item$inlined, 1919));
                        }
                    };
                    timer.schedule(timerTask2, l);
                    return;
                }
                case "hypixel": 
                case "blocksmc": {
                    if (this.clickState == 0 && windowId == 0 && slot == 43) {
                        Intrinsics.checkNotNullExpressionValue(itemName, "itemName");
                        if (StringsKt.contains((CharSequence)itemName, "paper", true)) {
                            AutoPlay.queueAutoPlay$default(this, 0L, new Function0<Unit>(item){
                                final /* synthetic */ ItemStack $item;
                                {
                                    this.$item = $item;
                                    super(0);
                                }

                                public final void invoke() {
                                    MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C09PacketHeldItemChange(7));
                                    int n = 2;
                                    ItemStack itemStack = this.$item;
                                    int n2 = 0;
                                    while (n2 < n) {
                                        int n3;
                                        int it = n3 = n2++;
                                        boolean bl = false;
                                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C08PacketPlayerBlockPlacement(itemStack));
                                    }
                                }
                            }, 1, null);
                            this.clickState = 1;
                        }
                    }
                    if (!this.modeValue.equals("hypixel") || this.clickState != 1 || windowId == 0 || !StringsKt.equals(itemName, "item.fireworks", true)) return;
                    MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C0EPacketClickWindow(windowId, slot, 0, 0, item, 1919));
                    MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C0DPacketCloseWindow(windowId));
                }
                default: {
                    return;
                }
            }
        }
        if (!(packet instanceof S02PacketChat)) return;
        String text = ((S02PacketChat)packet).func_148915_c().func_150260_c();
        IChatComponent component = ((S02PacketChat)packet).func_148915_c();
        String displayName = ((String)this.modeValue.get()).toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(displayName, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        switch (displayName) {
            case "supercraft": {
                Intrinsics.checkNotNullExpressionValue(text, "text");
                if (StringsKt.contains((CharSequence)text, Intrinsics.stringPlus("Ganador: ", MinecraftInstance.mc.field_71449_j.func_111285_a()), true) || StringsKt.contains((CharSequence)text, Intrinsics.stringPlus(MinecraftInstance.mc.field_71449_j.func_111285_a(), " fue asesinado"), true)) {
                    AutoPlay.queueAutoPlay$default(this, 0L, onPacket.4.INSTANCE, 1, null);
                }
                if (!StringsKt.contains((CharSequence)text, "El juego ya fue iniciado.", true)) return;
                AutoPlay.queueAutoPlay$default(this, 0L, onPacket.5.INSTANCE, 1, null);
                return;
            }
            case "minemora": {
                Intrinsics.checkNotNullExpressionValue(text, "text");
                if (!StringsKt.contains((CharSequence)text, "Has click en alguna de las siguientes opciones", true)) return;
                AutoPlay.queueAutoPlay$default(this, 0L, onPacket.6.INSTANCE, 1, null);
                return;
            }
            case "hydracraft": {
                Intrinsics.checkNotNullExpressionValue(text, "text");
                if (!StringsKt.contains((CharSequence)text, "Has ganado \u00bfQu\u00e9 quieres hacer?", true)) return;
                AutoPlay.queueAutoPlay$default(this, 0L, onPacket.7.INSTANCE, 1, null);
                return;
            }
            case "hycraft": {
                List itemName = component.func_150253_a();
                Intrinsics.checkNotNullExpressionValue(itemName, "component.siblings");
                Iterable $this$forEach$iv = itemName;
                boolean $i$f$forEach = false;
                for (Object element$iv : $this$forEach$iv) {
                    IChatComponent sib = (IChatComponent)element$iv;
                    boolean bl = false;
                    ClickEvent clickEvent = sib.func_150256_b().func_150235_h();
                    if (clickEvent == null || clickEvent.func_150669_a() != ClickEvent.Action.RUN_COMMAND) continue;
                    String string = clickEvent.func_150668_b();
                    Intrinsics.checkNotNullExpressionValue(string, "clickEvent.value");
                    if (!StringsKt.contains$default((CharSequence)string, "playagain", false, 2, null)) continue;
                    AutoPlay.queueAutoPlay$default(this, 0L, new Function0<Unit>(clickEvent){
                        final /* synthetic */ ClickEvent $clickEvent;
                        {
                            this.$clickEvent = $clickEvent;
                            super(0);
                        }

                        public final void invoke() {
                            MinecraftInstance.mc.field_71439_g.func_71165_d(this.$clickEvent.func_150668_b());
                        }
                    }, 1, null);
                }
                return;
            }
            case "blocksmc": {
                if (this.clickState != 1) return;
                Intrinsics.checkNotNullExpressionValue(text, "text");
                if (!StringsKt.contains((CharSequence)text, "Only VIP players can join full servers!", true)) return;
                Timer $this$forEach$iv = new Timer();
                long $i$f$forEach = 1500L;
                TimerTask element$iv = new TimerTask(){

                    public void run() {
                        TimerTask $this$onPacket_u24lambda_u2d3 = this;
                        boolean bl = false;
                        MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C09PacketHeldItemChange(7));
                        int n = 2;
                        int n2 = 0;
                        while (n2 < n) {
                            int n3;
                            int it = n3 = n2++;
                            boolean bl2 = false;
                            MinecraftInstance.mc.func_147114_u().func_147297_a((Packet)new C08PacketPlayerBlockPlacement(MinecraftInstance.mc.field_71439_g.field_71071_by.func_70448_g()));
                        }
                    }
                };
                $this$forEach$iv.schedule(element$iv, $i$f$forEach);
                return;
            }
            case "jartex": {
                Intrinsics.checkNotNullExpressionValue(text, "text");
                if (!StringsKt.contains((CharSequence)text, "Play Again?", true)) return;
                Iterable $this$forEach$iv = component.func_150253_a();
                Intrinsics.checkNotNullExpressionValue($this$forEach$iv, "component.siblings");
                $this$forEach$iv = $this$forEach$iv;
                boolean $i$f$forEach = false;
                for (Object element$iv : $this$forEach$iv) {
                    IChatComponent sib = (IChatComponent)element$iv;
                    boolean bl = false;
                    ClickEvent clickEvent = sib.func_150256_b().func_150235_h();
                    if (clickEvent == null || clickEvent.func_150669_a() != ClickEvent.Action.RUN_COMMAND) continue;
                    String string = clickEvent.func_150668_b();
                    Intrinsics.checkNotNullExpressionValue(string, "clickEvent.value");
                    if (!StringsKt.startsWith$default(string, "/", false, 2, null)) continue;
                    AutoPlay.queueAutoPlay$default(this, 0L, new Function0<Unit>(clickEvent){
                        final /* synthetic */ ClickEvent $clickEvent;
                        {
                            this.$clickEvent = $clickEvent;
                            super(0);
                        }

                        public final void invoke() {
                            MinecraftInstance.mc.field_71439_g.func_71165_d(this.$clickEvent.func_150668_b());
                        }
                    }, 1, null);
                }
                return;
            }
            case "pika": {
                Intrinsics.checkNotNullExpressionValue(text, "text");
                if (StringsKt.contains((CharSequence)text, "Click here to play again", true)) {
                    Iterable $this$forEach$iv = component.func_150253_a();
                    Intrinsics.checkNotNullExpressionValue($this$forEach$iv, "component.siblings");
                    $this$forEach$iv = $this$forEach$iv;
                    boolean $i$f$forEach = false;
                    for (Object element$iv : $this$forEach$iv) {
                        IChatComponent sib = (IChatComponent)element$iv;
                        boolean bl = false;
                        ClickEvent clickEvent = sib.func_150256_b().func_150235_h();
                        if (clickEvent == null || clickEvent.func_150669_a() != ClickEvent.Action.RUN_COMMAND) continue;
                        String string = clickEvent.func_150668_b();
                        Intrinsics.checkNotNullExpressionValue(string, "clickEvent.value");
                        if (!StringsKt.startsWith$default(string, "/", false, 2, null)) continue;
                        AutoPlay.queueAutoPlay$default(this, 0L, new Function0<Unit>(clickEvent){
                            final /* synthetic */ ClickEvent $clickEvent;
                            {
                                this.$clickEvent = $clickEvent;
                                super(0);
                            }

                            public final void invoke() {
                                MinecraftInstance.mc.field_71439_g.func_71165_d(this.$clickEvent.func_150668_b());
                            }
                        }, 1, null);
                    }
                }
                if (!StringsKt.contains$default((CharSequence)text, Intrinsics.stringPlus(MinecraftInstance.mc.func_110432_I().func_111285_a(), " has been"), false, 2, null) && !StringsKt.contains$default((CharSequence)text, Intrinsics.stringPlus(MinecraftInstance.mc.func_110432_I().func_111285_a(), " died."), false, 2, null)) return;
                AutoPlay.queueAutoPlay$default(this, 0L, onPacket.12.INSTANCE, 1, null);
                return;
            }
            case "hypixel": {
                IChatComponent iChatComponent = ((S02PacketChat)packet).func_148915_c();
                Intrinsics.checkNotNullExpressionValue(iChatComponent, "packet.chatComponent");
                AutoPlay.onPacket$process(this, iChatComponent);
                return;
            }
            case "minefc/heromc_bedwars": {
                Intrinsics.checkNotNullExpressionValue(text, "text");
                if (StringsKt.contains((CharSequence)text, "B\u1ea1n \u0111\u00e3 b\u1ecb lo\u1ea1i!", false) || StringsKt.contains((CharSequence)text, "\u0111\u00e3 th\u1eafng tr\u00f2 ch\u01a1i", false)) {
                    MinecraftInstance.mc.field_71439_g.func_71165_d("/bw leave");
                    this.waitForLobby = true;
                }
                if ((this.waitForLobby || this.autoStartValue.get().booleanValue()) && StringsKt.contains((CharSequence)text, "\u00a1Hi\u1ec3n th\u1ecb", false) || this.replayWhenKickedValue.get().booleanValue() && StringsKt.contains((CharSequence)text, "[Anticheat] You have been kicked from the server!", false)) {
                    AutoPlay.queueAutoPlay$default(this, 0L, new Function0<Unit>(this){
                        final /* synthetic */ AutoPlay this$0;
                        {
                            this.this$0 = $receiver;
                            super(0);
                        }

                        public final void invoke() {
                            MinecraftInstance.mc.field_71439_g.func_71165_d(Intrinsics.stringPlus("/bw join ", AutoPlay.access$getBwModeValue$p(this.this$0).get()));
                        }
                    }, 1, null);
                    this.waitForLobby = false;
                }
                if (!this.showGuiWhenFailedValue.get().booleanValue() || !StringsKt.contains((CharSequence)text, "gi\u00e2y", false) || !StringsKt.contains((CharSequence)text, "th\u1ea5t b\u1ea1i", false)) return;
                MinecraftInstance.mc.field_71439_g.func_71165_d(Intrinsics.stringPlus("/bw gui ", this.bwModeValue.get()));
            }
        }
    }

    private final void queueAutoPlay(long delay2, Function0<Unit> runnable) {
        if (this.queued) {
            return;
        }
        this.queued = true;
        AutoDisable.INSTANCE.handleGameEnd();
        if (this.getState()) {
            Timer timer = new Timer();
            TimerTask timerTask2 = new TimerTask(this, runnable){
                final /* synthetic */ AutoPlay this$0;
                final /* synthetic */ Function0 $runnable$inlined;
                {
                    this.this$0 = autoPlay;
                    this.$runnable$inlined = function0;
                }

                public void run() {
                    TimerTask $this$queueAutoPlay_u24lambda_u2d7 = this;
                    boolean bl = false;
                    AutoPlay.access$setQueued$p(this.this$0, false);
                    if (this.this$0.getState()) {
                        this.$runnable$inlined.invoke();
                    }
                }
            };
            timer.schedule(timerTask2, delay2);
        }
    }

    static /* synthetic */ void queueAutoPlay$default(AutoPlay autoPlay, long l, Function0 function0, int n, Object object) {
        if ((n & 1) != 0) {
            l = (long)((Number)autoPlay.delayValue.get()).intValue() * (long)1000;
        }
        autoPlay.queueAutoPlay(l, function0);
    }

    @EventTarget
    public final void onWorld(@NotNull WorldEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        this.clicking = false;
        this.clickState = 0;
        this.queued = false;
    }

    @Override
    @NotNull
    public String getTag() {
        return (String)this.modeValue.get();
    }

    @Override
    public boolean handleEvents() {
        return true;
    }

    private static final void onPacket$process(AutoPlay this$0, IChatComponent component) {
        String value;
        ClickEvent clickEvent = component.func_150256_b().func_150235_h();
        String string = value = clickEvent == null ? null : clickEvent.func_150668_b();
        if (value != null && StringsKt.startsWith(value, "/play", true)) {
            AutoPlay.queueAutoPlay$default(this$0, 0L, new Function0<Unit>(value){
                final /* synthetic */ String $value;
                {
                    this.$value = $value;
                    super(0);
                }

                public final void invoke() {
                    MinecraftInstance.mc.field_71439_g.func_71165_d(this.$value);
                }
            }, 1, null);
        }
        List list = component.func_150253_a();
        Intrinsics.checkNotNullExpressionValue(list, "component.siblings");
        Iterable $this$forEach$iv = list;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            IChatComponent it = (IChatComponent)element$iv;
            boolean bl = false;
            Intrinsics.checkNotNullExpressionValue(it, "it");
            AutoPlay.onPacket$process(this$0, it);
        }
    }

    public static final /* synthetic */ void access$setClickState$p(AutoPlay $this, int n) {
        $this.clickState = n;
    }

    public static final /* synthetic */ Value access$getBwModeValue$p(AutoPlay $this) {
        return $this.bwModeValue;
    }

    public static final /* synthetic */ void access$setClicking$p(AutoPlay $this, boolean bl) {
        $this.clicking = bl;
    }

    public static final /* synthetic */ void access$setQueued$p(AutoPlay $this, boolean bl) {
        $this.queued = bl;
    }

    public static final /* synthetic */ ListValue access$getModeValue$p(AutoPlay $this) {
        return $this.modeValue;
    }
}

