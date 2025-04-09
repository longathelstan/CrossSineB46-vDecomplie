/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft.item.data;

import com.viaversion.viaversion.api.minecraft.item.data.FilterableComponent;
import com.viaversion.viaversion.api.minecraft.item.data.FilterableString;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import io.netty.buffer.ByteBuf;
import java.util.Objects;

public final class WrittenBook {
    final FilterableString title;
    final String author;
    final int generation;
    final FilterableComponent[] pages;
    final boolean resolved;
    public static final Type<WrittenBook> TYPE = new Type<WrittenBook>(WrittenBook.class){

        @Override
        public WrittenBook read(ByteBuf buffer) {
            FilterableString title2 = (FilterableString)FilterableString.TYPE.read(buffer);
            String author = (String)Types.STRING.read(buffer);
            int generation = Types.VAR_INT.readPrimitive(buffer);
            FilterableComponent[] pages = (FilterableComponent[])FilterableComponent.ARRAY_TYPE.read(buffer);
            boolean resolved = buffer.readBoolean();
            return new WrittenBook(title2, author, generation, pages, resolved);
        }

        @Override
        public void write(ByteBuf buffer, WrittenBook value) {
            FilterableString.TYPE.write(buffer, value.title);
            Types.STRING.write(buffer, value.author);
            Types.VAR_INT.writePrimitive(buffer, value.generation);
            FilterableComponent.ARRAY_TYPE.write(buffer, value.pages);
            buffer.writeBoolean(value.resolved);
        }
    };

    public WrittenBook(FilterableString title2, String author, int generation, FilterableComponent[] pages, boolean resolved) {
        this.title = title2;
        this.author = author;
        this.generation = generation;
        this.pages = pages;
        this.resolved = resolved;
    }

    public FilterableString title() {
        return this.title;
    }

    public String author() {
        return this.author;
    }

    public int generation() {
        return this.generation;
    }

    public FilterableComponent[] pages() {
        return this.pages;
    }

    public boolean resolved() {
        return this.resolved;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof WrittenBook)) {
            return false;
        }
        WrittenBook writtenBook = (WrittenBook)object;
        return Objects.equals(this.title, writtenBook.title) && Objects.equals(this.author, writtenBook.author) && this.generation == writtenBook.generation && Objects.equals(this.pages, writtenBook.pages) && this.resolved == writtenBook.resolved;
    }

    public int hashCode() {
        return ((((0 * 31 + Objects.hashCode(this.title)) * 31 + Objects.hashCode(this.author)) * 31 + Integer.hashCode(this.generation)) * 31 + Objects.hashCode(this.pages)) * 31 + Boolean.hashCode(this.resolved);
    }

    public String toString() {
        return String.format("%s[title=%s, author=%s, generation=%s, pages=%s, resolved=%s]", this.getClass().getSimpleName(), Objects.toString(this.title), Objects.toString(this.author), Integer.toString(this.generation), Objects.toString(this.pages), Boolean.toString(this.resolved));
    }
}

