/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.mcstructs.text.components;

import com.viaversion.viaversion.libs.mcstructs.core.utils.ToString;
import com.viaversion.viaversion.libs.mcstructs.text.ATextComponent;
import java.util.Objects;
import java.util.function.Function;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class KeybindComponent
extends ATextComponent {
    private static final Function<String, String> DEFAULT_TRANSLATOR = s -> s;
    private String keybind;
    private Function<String, String> translator = DEFAULT_TRANSLATOR;

    public KeybindComponent(String keybind) {
        this.keybind = keybind;
    }

    public KeybindComponent(String keybind, @Nonnull Function<String, String> translator) {
        this.keybind = keybind;
        this.translator = translator;
    }

    public String getKeybind() {
        return this.keybind;
    }

    public KeybindComponent setKeybind(String keybind) {
        this.keybind = keybind;
        return this;
    }

    public KeybindComponent setTranslator(@Nullable Function<String, String> translator) {
        this.translator = translator == null ? DEFAULT_TRANSLATOR : translator;
        return this;
    }

    @Override
    public String asSingleString() {
        return this.translator.apply(this.keybind);
    }

    @Override
    public ATextComponent copy() {
        return this.putMetaCopy(this.shallowCopy());
    }

    @Override
    public ATextComponent shallowCopy() {
        KeybindComponent copy = new KeybindComponent(this.keybind);
        copy.translator = this.translator;
        return copy.setStyle(this.getStyle().copy());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        KeybindComponent that = (KeybindComponent)o;
        return Objects.equals(this.getSiblings(), that.getSiblings()) && Objects.equals(this.getStyle(), that.getStyle()) && Objects.equals(this.keybind, that.keybind) && Objects.equals(this.translator, that.translator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getSiblings(), this.getStyle(), this.keybind, this.translator);
    }

    @Override
    public String toString() {
        return ToString.of(this).add("siblings", this.getSiblings(), siblings -> !siblings.isEmpty()).add("style", this.getStyle(), style -> !style.isEmpty()).add("keybind", this.keybind).add("translator", this.translator, translator -> translator != DEFAULT_TRANSLATOR).toString();
    }
}

