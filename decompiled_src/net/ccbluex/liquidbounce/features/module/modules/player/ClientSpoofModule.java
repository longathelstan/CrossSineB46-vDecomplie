/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.player;

import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.features.value.TextValue;
import org.jetbrains.annotations.Nullable;

@ModuleInfo(name="ClientSpoofer", category=ModuleCategory.PLAYER)
public final class ClientSpoofModule
extends Module {
    public final ListValue modeValue = new ListValue("Mode", new String[]{"Vanilla", "Forge", "Lunar", "LabyMod", "CheatBreaker", "PvPLounge", "Custom"}, "Vanilla");
    public final TextValue CustomClient = new TextValue("CustomClient", "Vanilla");

    @Override
    @Nullable
    public String getTag() {
        return (String)this.modeValue.get();
    }
}

