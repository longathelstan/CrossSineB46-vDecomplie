/*
 * Decompiled with CFR 0.152.
 */
package org.intellij.lang.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jetbrains.annotations.NonNls;

@Documented
@Retention(value=RetentionPolicy.CLASS)
@Target(value={ElementType.PARAMETER, ElementType.METHOD})
public @interface Flow {
    @NonNls
    public static final String DEFAULT_SOURCE = "The method argument (if parameter was annotated) or this container (if instance method was annotated)";
    @NonNls
    public static final String THIS_SOURCE = "this";
    @NonNls
    public static final String DEFAULT_TARGET = "This container (if the parameter was annotated) or the return value (if instance method was annotated)";
    @NonNls
    public static final String RETURN_METHOD_TARGET = "The return value of this method";
    @NonNls
    public static final String THIS_TARGET = "this";

    public String source() default "The method argument (if parameter was annotated) or this container (if instance method was annotated)";

    public boolean sourceIsContainer() default false;

    public String target() default "This container (if the parameter was annotated) or the return value (if instance method was annotated)";

    public boolean targetIsContainer() default false;
}

