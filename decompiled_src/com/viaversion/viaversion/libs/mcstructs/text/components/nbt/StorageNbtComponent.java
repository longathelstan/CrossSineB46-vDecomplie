/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.mcstructs.text.components.nbt;

import com.viaversion.viaversion.libs.mcstructs.core.Identifier;
import com.viaversion.viaversion.libs.mcstructs.core.utils.ToString;
import com.viaversion.viaversion.libs.mcstructs.text.ATextComponent;
import com.viaversion.viaversion.libs.mcstructs.text.components.NbtComponent;
import java.util.Objects;

public class StorageNbtComponent
extends NbtComponent {
    private Identifier id;

    public StorageNbtComponent(String component, boolean resolve, Identifier id) {
        super(component, resolve);
        this.id = id;
    }

    public StorageNbtComponent(String component, boolean resolve, ATextComponent separator, Identifier id) {
        super(component, resolve, separator);
        this.id = id;
    }

    public Identifier getId() {
        return this.id;
    }

    public StorageNbtComponent setId(Identifier id) {
        this.id = id;
        return this;
    }

    @Override
    public ATextComponent copy() {
        return this.putMetaCopy(this.shallowCopy());
    }

    @Override
    public ATextComponent shallowCopy() {
        if (this.getSeparator() == null) {
            return new StorageNbtComponent(this.getComponent(), this.isResolve(), null, this.id).setStyle(this.getStyle().copy());
        }
        return new StorageNbtComponent(this.getComponent(), this.isResolve(), this.getSeparator(), this.id).setStyle(this.getStyle().copy());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        StorageNbtComponent that = (StorageNbtComponent)o;
        return Objects.equals(this.getSiblings(), that.getSiblings()) && Objects.equals(this.getStyle(), that.getStyle()) && Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getSiblings(), this.getStyle(), this.id);
    }

    @Override
    public String toString() {
        return ToString.of(this).add("siblings", this.getSiblings(), siblings -> !siblings.isEmpty()).add("style", this.getStyle(), style -> !style.isEmpty()).add("id", this.id).toString();
    }
}

