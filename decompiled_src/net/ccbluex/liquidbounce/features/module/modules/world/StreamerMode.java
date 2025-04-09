/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.world;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.TextEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.BoolValue;
import net.ccbluex.liquidbounce.features.value.TextValue;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.ccbluex.liquidbounce.utils.misc.StringUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.minecraft.client.network.NetworkPlayerInfo;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(name="StreamerMode", category=ModuleCategory.WORLD)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/world/StreamerMode;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "allPlayersValue", "Lnet/ccbluex/liquidbounce/features/value/BoolValue;", "nameDisplay", "Lnet/ccbluex/liquidbounce/features/value/TextValue;", "onText", "", "event", "Lnet/ccbluex/liquidbounce/event/TextEvent;", "CrossSine"})
public final class StreamerMode
extends Module {
    @NotNull
    public static final StreamerMode INSTANCE = new StreamerMode();
    @NotNull
    private static final TextValue nameDisplay = new TextValue("Name", "CrossSineUser");
    @NotNull
    private static final BoolValue allPlayersValue = new BoolValue("SensorPlayer", false);

    private StreamerMode() {
    }

    @EventTarget
    public final void onText(@NotNull TextEvent event) {
        block6: {
            block5: {
                Intrinsics.checkNotNullParameter(event, "event");
                if (MinecraftInstance.mc.field_71439_g == null) break block5;
                String string = event.getText();
                Intrinsics.checkNotNull(string);
                if (StringsKt.startsWith$default(string, "/", false, 2, null)) break block5;
                String string2 = event.getText();
                Intrinsics.checkNotNull(string2);
                char c = CrossSine.INSTANCE.getCommandManager().getPrefix();
                String string3 = "";
                if (!StringsKt.startsWith$default(string2, c + string3, false, 2, null)) break block6;
            }
            return;
        }
        event.setText(StringUtils.replace(event.getText(), MinecraftInstance.mc.field_71439_g.func_70005_c_(), Intrinsics.stringPlus(ColorUtils.translateAlternateColorCodes((String)nameDisplay.get()), "\u00a7r")));
        if (((Boolean)allPlayersValue.get()).booleanValue()) {
            for (NetworkPlayerInfo playerInfo : MinecraftInstance.mc.func_147114_u().func_175106_d()) {
                event.setText(StringUtils.replace(event.getText(), playerInfo.func_178845_a().getName(), Intrinsics.stringPlus(RandomUtils.INSTANCE.randomString(playerInfo.func_178845_a().getName().length()), "\u00a7f")));
            }
        }
    }
}

