/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.CrossSine;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.KeyEvent;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.EnumTriggerType;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCommand;
import net.ccbluex.liquidbounce.features.special.AutoDisable;
import net.ccbluex.liquidbounce.features.value.Value;
import net.ccbluex.liquidbounce.utils.ClassUtils;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.input.Keyboard;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010 \n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0015\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0006H\u0000\u00a2\u0006\u0002\b\u0014J(\u0010\u0015\u001a\u0004\u0018\u0001H\u0016\"\b\b\u0000\u0010\u0016*\u00020\u00062\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u00160\u0005H\u0086\u0002\u00a2\u0006\u0002\u0010\u0018J\u0014\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00060\u001a2\u0006\u0010\u001b\u001a\u00020\u001cJ%\u0010\u001d\u001a\u0004\u0018\u0001H\u0016\"\b\b\u0000\u0010\u0016*\u00020\u00062\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u0002H\u00160\u0005\u00a2\u0006\u0002\u0010\u0018J\u0012\u0010\u001d\u001a\u0004\u0018\u00010\u00062\b\u0010\u001f\u001a\u0004\u0018\u00010 J\u0014\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00060\u001a2\u0006\u0010\"\u001a\u00020 J\b\u0010#\u001a\u00020$H\u0016J\u0010\u0010%\u001a\u00020\u00122\u0006\u0010&\u001a\u00020'H\u0003J\u0010\u0010(\u001a\u00020\u00122\u0006\u0010&\u001a\u00020)H\u0003J\u0018\u0010*\u001a\u00020\u00122\u000e\u0010\u001e\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u0005H\u0002J\u000e\u0010*\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0006J\u0006\u0010+\u001a\u00020\u0012J\u000e\u0010,\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0006R2\u0010\u0003\u001a&\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0005\u0012\u0004\u0012\u00020\u00060\u0004j\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0005\u0012\u0004\u0012\u00020\u0006`\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00060\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010\u00a8\u0006-"}, d2={"Lnet/ccbluex/liquidbounce/features/module/ModuleManager;", "Lnet/ccbluex/liquidbounce/event/Listenable;", "()V", "moduleClassMap", "Ljava/util/HashMap;", "Ljava/lang/Class;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "Lkotlin/collections/HashMap;", "modules", "", "getModules", "()Ljava/util/List;", "pendingBindModule", "getPendingBindModule", "()Lnet/ccbluex/liquidbounce/features/module/Module;", "setPendingBindModule", "(Lnet/ccbluex/liquidbounce/features/module/Module;)V", "generateCommand", "", "module", "generateCommand$CrossSine", "get", "T", "clazz", "(Ljava/lang/Class;)Lnet/ccbluex/liquidbounce/features/module/Module;", "getKeyBind", "", "key", "", "getModule", "moduleClass", "moduleName", "", "getModulesByName", "name", "handleEvents", "", "onKey", "event", "Lnet/ccbluex/liquidbounce/event/KeyEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "registerModule", "registerModules", "unregisterModule", "CrossSine"})
public final class ModuleManager
implements Listenable {
    @NotNull
    private final List<Module> modules = new ArrayList();
    @NotNull
    private final HashMap<Class<?>, Module> moduleClassMap = new HashMap();
    @Nullable
    private Module pendingBindModule;

    public ModuleManager() {
        CrossSine.INSTANCE.getEventManager().registerListener(this);
    }

    @NotNull
    public final List<Module> getModules() {
        return this.modules;
    }

    @Nullable
    public final Module getPendingBindModule() {
        return this.pendingBindModule;
    }

    public final void setPendingBindModule(@Nullable Module module) {
        this.pendingBindModule = module;
    }

    public final void registerModules() {
        Module it;
        ClientUtils.INSTANCE.logInfo("[ModuleManager] Loading modules...");
        Iterable $this$forEach$iv = ClassUtils.INSTANCE.resolvePackage(Intrinsics.stringPlus(this.getClass().getPackage().getName(), ".modules"), Module.class);
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Class p0 = (Class)element$iv;
            boolean bl = false;
            this.registerModule(p0);
        }
        $this$forEach$iv = this.modules;
        $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            it = (Module)element$iv;
            boolean bl = false;
            it.onInitialize();
        }
        $this$forEach$iv = this.modules;
        $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            it = (Module)element$iv;
            boolean bl = false;
            it.onLoad();
        }
        CrossSine.INSTANCE.getEventManager().registerListener(AutoDisable.INSTANCE);
        ClientUtils.INSTANCE.logInfo("[ModuleManager] Loaded " + this.modules.size() + " modules.");
    }

    public final void registerModule(@NotNull Module module) {
        Intrinsics.checkNotNullParameter(module, "module");
        ((Collection)this.modules).add(module);
        Map map = this.moduleClassMap;
        Class<?> clazz = module.getClass();
        map.put(clazz, module);
        List<Module> $this$sortBy$iv = this.modules;
        boolean $i$f$sortBy = false;
        if ($this$sortBy$iv.size() > 1) {
            CollectionsKt.sortWith($this$sortBy$iv, new Comparator(){

                public final int compare(T a, T b) {
                    Module it = (Module)a;
                    boolean bl = false;
                    Comparable comparable = (Comparable)((Object)it.getName());
                    it = (Module)b;
                    Comparable comparable2 = comparable;
                    bl = false;
                    return ComparisonsKt.compareValues(comparable2, (Comparable)((Object)it.getName()));
                }
            });
        }
        this.generateCommand$CrossSine(module);
        CrossSine.INSTANCE.getEventManager().registerListener(module);
    }

    private final void registerModule(Class<? extends Module> moduleClass) {
        try {
            Module module = moduleClass.newInstance();
            Intrinsics.checkNotNullExpressionValue(module, "moduleClass.newInstance()");
            this.registerModule(module);
        }
        catch (IllegalAccessException e) {
            this.registerModule((Module)ClassUtils.INSTANCE.getObjectInstance(moduleClass));
        }
        catch (Throwable e) {
            ClientUtils.INSTANCE.logError("Failed to load module: " + moduleClass.getName() + " (" + e.getClass().getName() + ": " + e.getMessage() + ')');
        }
    }

    public final void unregisterModule(@NotNull Module module) {
        Intrinsics.checkNotNullParameter(module, "module");
        this.modules.remove(module);
        this.moduleClassMap.remove(module.getClass());
        CrossSine.INSTANCE.getEventManager().unregisterListener(module);
    }

    public final void generateCommand$CrossSine(@NotNull Module module) {
        Intrinsics.checkNotNullParameter(module, "module");
        if (!module.getModuleCommand()) {
            return;
        }
        List<Value<?>> values2 = module.getValues();
        if (values2.isEmpty()) {
            return;
        }
        CrossSine.INSTANCE.getCommandManager().registerCommand(new ModuleCommand(module, values2));
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final List<Module> getModulesByName(@NotNull String name) {
        void $this$filterTo$iv$iv;
        Intrinsics.checkNotNullParameter(name, "name");
        Iterable $this$filter$iv = this.modules;
        boolean $i$f$filter = false;
        Iterable iterable = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            Module it = (Module)element$iv$iv;
            boolean bl = false;
            String string = it.getName().toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            CharSequence charSequence = string;
            string = name.toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(string, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            if (!StringsKt.contains$default(charSequence, string, false, 2, null)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        return (List)destination$iv$iv;
    }

    @Nullable
    public final <T extends Module> T getModule(@NotNull Class<T> moduleClass) {
        Intrinsics.checkNotNullParameter(moduleClass, "moduleClass");
        return (T)this.moduleClassMap.get(moduleClass);
    }

    @Nullable
    public final <T extends Module> T get(@NotNull Class<T> clazz) {
        Intrinsics.checkNotNullParameter(clazz, "clazz");
        return this.getModule(clazz);
    }

    @Nullable
    public final Module getModule(@Nullable String moduleName) {
        Object v0;
        block1: {
            for (Object t : (Iterable)this.modules) {
                Module it = (Module)t;
                boolean bl = false;
                if (!StringsKt.equals(it.getName(), moduleName, true)) continue;
                v0 = t;
                break block1;
            }
            v0 = null;
        }
        return v0;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final List<Module> getKeyBind(int key) {
        void $this$filterTo$iv$iv;
        Iterable $this$filter$iv = this.modules;
        boolean $i$f$filter = false;
        Iterable iterable = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            Module it = (Module)element$iv$iv;
            boolean bl = false;
            if (!(it.getKeyBind() == key)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        return (List)destination$iv$iv;
    }

    /*
     * WARNING - void declaration
     */
    @EventTarget
    private final void onKey(KeyEvent event) {
        if (this.pendingBindModule == null) {
            void $this$filterTo$iv$iv;
            Iterable $this$filter$iv = CollectionsKt.toMutableList((Collection)this.modules);
            boolean $i$f$filter = false;
            Iterable iterable = $this$filter$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$filterTo = false;
            for (Object element$iv$iv : $this$filterTo$iv$iv) {
                Module it = (Module)element$iv$iv;
                boolean bl = false;
                if (!(it.getTriggerType() == EnumTriggerType.TOGGLE && it.getKeyBind() == event.getKey())) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            Iterable $this$forEach$iv = (List)destination$iv$iv;
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                Module it = (Module)element$iv;
                boolean bl = false;
                it.toggle();
            }
        } else {
            Module module = this.pendingBindModule;
            Intrinsics.checkNotNull(module);
            module.setKeyBind(event.getKey());
            StringBuilder stringBuilder = new StringBuilder().append("Bound module \u00a7a\u00a7l");
            Module module2 = this.pendingBindModule;
            Intrinsics.checkNotNull(module2);
            ClientUtils.INSTANCE.displayAlert(stringBuilder.append(module2.getName()).append("\u00a73 to key \u00a7a\u00a7l").append((Object)Keyboard.getKeyName((int)event.getKey())).append("\u00a73.").toString());
            this.pendingBindModule = null;
        }
    }

    /*
     * WARNING - void declaration
     */
    @EventTarget
    private final void onUpdate(UpdateEvent event) {
        void $this$filterTo$iv$iv;
        if (this.pendingBindModule != null || Minecraft.func_71410_x().field_71462_r != null) {
            return;
        }
        Iterable $this$filter$iv = this.modules;
        boolean $i$f$filter = false;
        Iterable iterable = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            Module it = (Module)element$iv$iv;
            boolean bl = false;
            if (!(it.getTriggerType() == EnumTriggerType.PRESS)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        Iterable $this$forEach$iv = (List)destination$iv$iv;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Module it = (Module)element$iv;
            boolean bl = false;
            it.setState(Keyboard.isKeyDown((int)it.getKeyBind()));
        }
    }

    @Override
    public boolean handleEvents() {
        return true;
    }
}

