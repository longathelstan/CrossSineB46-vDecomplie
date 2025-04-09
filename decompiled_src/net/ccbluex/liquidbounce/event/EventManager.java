/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.event;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.Event;
import net.ccbluex.liquidbounce.event.EventHook;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0006J\u000e\u0010\r\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u000fJ\u000e\u0010\u0010\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u000fRF\u0010\u0003\u001a:\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0004j\u001c\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u0007`\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2={"Lnet/ccbluex/liquidbounce/event/EventManager;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "registry", "Ljava/util/HashMap;", "Ljava/lang/Class;", "Lnet/ccbluex/liquidbounce/event/Event;", "", "Lnet/ccbluex/liquidbounce/event/EventHook;", "Lkotlin/collections/HashMap;", "callEvent", "", "event", "registerListener", "listener", "Lnet/ccbluex/liquidbounce/event/Listenable;", "unregisterListener", "listenable", "CrossSine"})
public final class EventManager
extends MinecraftInstance {
    @NotNull
    private final HashMap<Class<? extends Event>, List<EventHook>> registry = new HashMap();

    public final void registerListener(@NotNull Listenable listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        if (CrossSine.INSTANCE.getDestruced()) {
            return;
        }
        Method[] methodArray = listener.getClass().getDeclaredMethods();
        Intrinsics.checkNotNullExpressionValue(methodArray, "listener.javaClass.declaredMethods");
        Method[] methodArray2 = methodArray;
        int n = 0;
        int n2 = methodArray2.length;
        while (n < n2) {
            Method method = methodArray2[n];
            ++n;
            if (!method.isAnnotationPresent(EventTarget.class) || method.getParameterTypes().length != 1) continue;
            try {
                Object object;
                Class<?> eventClass;
                if (!method.isAccessible()) {
                    method.setAccessible(true);
                }
                if (method.getParameterTypes()[0] == null) {
                    throw new NullPointerException("null cannot be cast to non-null type java.lang.Class<out net.ccbluex.liquidbounce.event.Event>");
                }
                EventTarget eventTarget = method.getAnnotation(EventTarget.class);
                Map $this$getOrPut$iv = this.registry;
                boolean $i$f$getOrPut = false;
                Object value$iv = $this$getOrPut$iv.get(eventClass);
                if (value$iv == null) {
                    boolean bl = false;
                    List answer$iv = new ArrayList();
                    $this$getOrPut$iv.put(eventClass, answer$iv);
                    object = answer$iv;
                } else {
                    object = value$iv;
                }
                List invokableEventTargets = (List)object;
                Intrinsics.checkNotNullExpressionValue(method, "method");
                Intrinsics.checkNotNullExpressionValue(eventTarget, "eventTarget");
                invokableEventTargets.add(new EventHook(listener, method, eventTarget));
                ((Map)this.registry).put(eventClass, invokableEventTargets);
            }
            catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    public final void unregisterListener(@NotNull Listenable listenable) {
        Intrinsics.checkNotNullParameter(listenable, "listenable");
        for (Map.Entry entry : ((Map)this.registry).entrySet()) {
            Class key = (Class)entry.getKey();
            List targets = (List)entry.getValue();
            targets.removeIf(arg_0 -> EventManager.unregisterListener$lambda-1(listenable, arg_0));
            ((Map)this.registry).put(key, targets);
        }
    }

    public final void callEvent(@NotNull Event event) {
        Intrinsics.checkNotNullParameter(event, "event");
        if (CrossSine.INSTANCE.getDestruced()) {
            return;
        }
        List<EventHook> list = this.registry.get(event.getClass());
        if (list == null) {
            return;
        }
        List<EventHook> targets = list;
        try {
            for (EventHook invokableEventTarget : targets) {
                try {
                    if (!invokableEventTarget.getEventClass().handleEvents() && !invokableEventTarget.isIgnoreCondition()) continue;
                    Object[] objectArray = new Object[]{event};
                    invokableEventTarget.getMethod().invoke((Object)invokableEventTarget.getEventClass(), objectArray);
                }
                catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final boolean unregisterListener$lambda-1(Listenable $listenable, EventHook it) {
        Intrinsics.checkNotNullParameter($listenable, "$listenable");
        Intrinsics.checkNotNullParameter(it, "it");
        return Intrinsics.areEqual(it.getEventClass(), $listenable);
    }
}

