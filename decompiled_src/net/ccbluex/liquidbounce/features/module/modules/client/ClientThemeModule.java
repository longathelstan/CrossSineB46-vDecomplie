/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module.modules.client;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.value.IntegerValue;
import net.ccbluex.liquidbounce.features.value.ListValue;
import net.ccbluex.liquidbounce.ui.client.gui.colortheme.ClientTheme;
import org.jetbrains.annotations.NotNull;

@ModuleInfo(name="ClientTheme", category=ModuleCategory.CLIENT, canEnable=false)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\r"}, d2={"Lnet/ccbluex/liquidbounce/features/module/modules/client/ClientThemeModule;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "fadespeed", "Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "getFadespeed", "()Lnet/ccbluex/liquidbounce/features/value/IntegerValue;", "index", "getIndex", "mode", "Lnet/ccbluex/liquidbounce/features/value/ListValue;", "getMode", "()Lnet/ccbluex/liquidbounce/features/value/ListValue;", "CrossSine"})
public final class ClientThemeModule
extends Module {
    @NotNull
    public static final ClientThemeModule INSTANCE = new ClientThemeModule();
    @NotNull
    private static final ListValue mode;
    @NotNull
    private static final IntegerValue fadespeed;
    @NotNull
    private static final IntegerValue index;

    private ClientThemeModule() {
    }

    @NotNull
    public final ListValue getMode() {
        return mode;
    }

    @NotNull
    public final IntegerValue getFadespeed() {
        return fadespeed;
    }

    @NotNull
    public final IntegerValue getIndex() {
        return index;
    }

    static {
        String[] stringArray = ClientTheme.INSTANCE.getMode();
        mode = new ListValue(stringArray){

            protected void onChanged(@NotNull String oldValue, @NotNull String newValue) {
                Intrinsics.checkNotNullParameter(oldValue, "oldValue");
                Intrinsics.checkNotNullParameter(newValue, "newValue");
                ClientTheme.INSTANCE.getClientColorMode().set(newValue);
            }
        };
        fadespeed = new IntegerValue(){

            protected void onChanged(int oldValue, int newValue) {
                ClientTheme.INSTANCE.getFadespeed().set(newValue);
            }
        };
        index = new IntegerValue(){

            protected void onChanged(int oldValue, int newValue) {
                ClientTheme.INSTANCE.getIndex().set(newValue);
            }
        };
    }
}

