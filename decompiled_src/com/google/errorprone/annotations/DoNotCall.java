/*
 * Decompiled with CFR 0.152.
 */
package com.google.errorprone.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value=RetentionPolicy.CLASS)
@Target(value={ElementType.METHOD})
public @interface DoNotCall {
    public String value() default "";
}

