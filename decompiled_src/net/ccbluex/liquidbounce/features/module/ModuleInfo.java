/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.features.module;

import java.lang.annotation.RetentionPolicy;
import kotlin.Metadata;
import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.Retention;
import net.ccbluex.liquidbounce.features.module.EnumAutoDisableType;
import net.ccbluex.liquidbounce.features.module.EnumTriggerType;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;

@Retention(value=AnnotationRetention.RUNTIME)
@java.lang.annotation.Retention(value=RetentionPolicy.RUNTIME)
@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\b\u0087\u0002\u0018\u00002\u00020\u0001Bj\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\t\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f\u0012\b\b\u0002\u0010\r\u001a\u00020\t\u0012\b\b\u0002\u0010\u000e\u001a\u00020\t\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0012\u001a\u00020\tR\u000f\u0010\n\u001a\u00020\t\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u0013R\u000f\u0010\u000b\u001a\u00020\f\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\u0014R\u000f\u0010\b\u001a\u00020\t\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\u0013R\u000f\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\u0015R\u000f\u0010\u000e\u001a\u00020\t\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\u0013R\u000f\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0016R\u000f\u0010\u0012\u001a\u00020\t\u00a2\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u000f\u0010\u0011\u001a\u00020\u0003\u00a2\u0006\u0006\u001a\u0004\b\u0011\u0010\u0017R\u000f\u0010\r\u001a\u00020\t\u00a2\u0006\u0006\u001a\u0004\b\r\u0010\u0013R\u000f\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0006\u001a\u0004\b\u0002\u0010\u0017R\u000f\u0010\u000f\u001a\u00020\u0010\u00a2\u0006\u0006\u001a\u0004\b\u000f\u0010\u0018\u00a8\u0006\u0019"}, d2={"Lnet/ccbluex/liquidbounce/features/module/ModuleInfo;", "", "name", "", "category", "Lnet/ccbluex/liquidbounce/features/module/ModuleCategory;", "keyBind", "", "canEnable", "", "array", "autoDisable", "Lnet/ccbluex/liquidbounce/features/module/EnumAutoDisableType;", "moduleCommand", "defaultOn", "triggerType", "Lnet/ccbluex/liquidbounce/features/module/EnumTriggerType;", "module", "loadConfig", "()Z", "()Lnet/ccbluex/liquidbounce/features/module/EnumAutoDisableType;", "()Lnet/ccbluex/liquidbounce/features/module/ModuleCategory;", "()I", "()Ljava/lang/String;", "()Lnet/ccbluex/liquidbounce/features/module/EnumTriggerType;", "CrossSine"})
public @interface ModuleInfo {
    public String name();

    public ModuleCategory category();

    public int keyBind() default 0;

    public boolean canEnable() default true;

    public boolean array() default true;

    public EnumAutoDisableType autoDisable() default EnumAutoDisableType.NONE;

    public boolean moduleCommand() default true;

    public boolean defaultOn() default false;

    public EnumTriggerType triggerType() default EnumTriggerType.TOGGLE;

    public String module() default "";

    public boolean loadConfig() default true;
}

