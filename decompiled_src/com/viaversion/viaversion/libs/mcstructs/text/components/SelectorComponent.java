/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.mcstructs.text.components;

import com.viaversion.viaversion.libs.mcstructs.core.utils.ToString;
import com.viaversion.viaversion.libs.mcstructs.text.ATextComponent;
import java.util.Objects;
import javax.annotation.Nullable;

public class SelectorComponent
extends ATextComponent {
    private String selector;
    @Nullable
    private ATextComponent separator;

    public SelectorComponent(String selector) {
        this(selector, null);
    }

    public SelectorComponent(String selector, @Nullable ATextComponent separator) {
        this.selector = selector;
        this.separator = separator;
    }

    public String getSelector() {
        return this.selector;
    }

    public SelectorComponent setSelector(String selector) {
        this.selector = selector;
        return this;
    }

    @Nullable
    public ATextComponent getSeparator() {
        return this.separator;
    }

    public SelectorComponent setSeparator(@Nullable ATextComponent separator) {
        this.separator = separator;
        return this;
    }

    @Override
    public String asSingleString() {
        return this.selector;
    }

    @Override
    public ATextComponent copy() {
        return this.putMetaCopy(this.shallowCopy());
    }

    @Override
    public ATextComponent shallowCopy() {
        if (this.separator == null) {
            return new SelectorComponent(this.selector, null).setStyle(this.getStyle().copy());
        }
        return new SelectorComponent(this.selector, this.separator.copy()).setStyle(this.getStyle().copy());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        SelectorComponent that = (SelectorComponent)o;
        return Objects.equals(this.getSiblings(), that.getSiblings()) && Objects.equals(this.getStyle(), that.getStyle()) && Objects.equals(this.selector, that.selector) && Objects.equals(this.separator, that.separator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getSiblings(), this.getStyle(), this.selector, this.separator);
    }

    @Override
    public String toString() {
        return ToString.of(this).add("siblings", this.getSiblings(), siblings -> !siblings.isEmpty()).add("style", this.getStyle(), style -> !style.isEmpty()).add("selector", this.selector).add("separator", this.separator, Objects::nonNull).toString();
    }
}

