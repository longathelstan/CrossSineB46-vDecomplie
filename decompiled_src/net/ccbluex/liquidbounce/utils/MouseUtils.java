/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.utils;

import java.nio.ByteBuffer;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Mouse;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J8\u0010\f\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u0011H\u0007J\u0016\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u0004R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\b\u00a8\u0006\u0019"}, d2={"Lnet/ccbluex/liquidbounce/utils/MouseUtils;", "", "()V", "leftClicked", "", "getLeftClicked", "()Z", "setLeftClicked", "(Z)V", "rightClicked", "getRightClicked", "setRightClicked", "mouseWithinBounds", "mouseX", "", "mouseY", "x", "", "y", "x2", "y2", "setMouseButtonState", "", "mouseButton", "held", "CrossSine"})
public final class MouseUtils {
    @NotNull
    public static final MouseUtils INSTANCE = new MouseUtils();
    private static boolean leftClicked;
    private static boolean rightClicked;

    private MouseUtils() {
    }

    public final boolean getLeftClicked() {
        return leftClicked;
    }

    public final void setLeftClicked(boolean bl) {
        leftClicked = bl;
    }

    public final boolean getRightClicked() {
        return rightClicked;
    }

    public final void setRightClicked(boolean bl) {
        rightClicked = bl;
    }

    @JvmStatic
    public static final boolean mouseWithinBounds(int mouseX, int mouseY, float x, float y, float x2, float y2) {
        return (float)mouseX >= x && (float)mouseX < x2 && (float)mouseY >= y && (float)mouseY < y2;
    }

    public final void setMouseButtonState(int mouseButton, boolean held) {
        MouseEvent m2 = new MouseEvent();
        String[] stringArray = new String[]{"button"};
        ObfuscationReflectionHelper.setPrivateValue(MouseEvent.class, (Object)m2, (Object)mouseButton, (String[])stringArray);
        stringArray = new String[]{"buttonstate"};
        ObfuscationReflectionHelper.setPrivateValue(MouseEvent.class, (Object)m2, (Object)held, (String[])stringArray);
        MinecraftForge.EVENT_BUS.post((Event)m2);
        String[] stringArray2 = new String[]{"buttons"};
        ByteBuffer buttons = (ByteBuffer)ObfuscationReflectionHelper.getPrivateValue(Mouse.class, null, (String[])stringArray2);
        buttons.put(mouseButton, (byte)(held ? 1 : 0));
        stringArray2 = new String[]{"buttons"};
        ObfuscationReflectionHelper.setPrivateValue(Mouse.class, null, (Object)buttons, (String[])stringArray2);
    }
}

