/*
 * Decompiled with CFR 0.152.
 */
package com.google.errorprone.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Documented
@Target(value={ElementType.CONSTRUCTOR, ElementType.METHOD})
public @interface MustBeClosed {
}

