/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.client;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.font.FontLoaders;
import net.ccbluex.liquidbounce.ui.client.gui.GuiMainMenu;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.timer.TimerMS;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.Display;

@ModuleInfo(name="SelfDestruct", category=ModuleCategory.CLIENT)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/client/SelfDestruct;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "timerMS", "Lnet/ccbluex/liquidbounce/utils/timer/TimerMS;", "onEnable", "", "onRender2D", "event", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "CrossSine"})
public final class SelfDestruct
extends Module {
    @NotNull
    private final TimerMS timerMS = new TimerMS();

    @Override
    public void onEnable() {
        this.timerMS.reset();
        MinecraftInstance.mc.field_71462_r = null;
    }

    @EventTarget
    public final void onRender2D(@NotNull Render2DEvent event) {
        Intrinsics.checkNotNullParameter(event, "event");
        FontLoaders.F24.drawCenteredStringWithShadow("USE /stopdestruct to stop selfdestruct", event.getScaledResolution().func_78327_c() / (double)2, event.getScaledResolution().func_78324_d() / (double)2, Color.WHITE.getRGB());
        if (this.timerMS.hasTimePassed(3500L)) {
            Display.setTitle((String)"Minecraft 1.8.9");
            MinecraftInstance.mc.func_147108_a(null);
            CrossSine.INSTANCE.setMainMenu(new GuiMainMenu());
            CrossSine.INSTANCE.getFileManager().saveAllConfigs();
            CrossSine.INSTANCE.setDestruced(true);
            CrossSine.INSTANCE.getCommandManager().setPrefix(' ');
            this.setState(false);
        }
    }
}

