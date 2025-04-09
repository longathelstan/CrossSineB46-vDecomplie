/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft.item.data;

import com.viaversion.nbt.tag.Tag;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.Types;
import com.viaversion.viaversion.api.type.types.misc.HolderType;
import io.netty.buffer.ByteBuf;
import java.util.Objects;

public final class ChatType {
    final ChatTypeDecoration chatDecoration;
    final ChatTypeDecoration narrationDecoration;
    public static final HolderType<ChatType> TYPE = new HolderType<ChatType>(){

        @Override
        public ChatType readDirect(ByteBuf buffer) {
            ChatTypeDecoration chatDecoration = (ChatTypeDecoration)ChatTypeDecoration.TYPE.read(buffer);
            ChatTypeDecoration narrationDecoration = (ChatTypeDecoration)ChatTypeDecoration.TYPE.read(buffer);
            return new ChatType(chatDecoration, narrationDecoration);
        }

        @Override
        public void writeDirect(ByteBuf buffer, ChatType value) {
            ChatTypeDecoration.TYPE.write(buffer, value.chatDecoration());
            ChatTypeDecoration.TYPE.write(buffer, value.narrationDecoration());
        }
    };

    public ChatType(ChatTypeDecoration chatDecoration, ChatTypeDecoration narrationDecoration) {
        this.chatDecoration = chatDecoration;
        this.narrationDecoration = narrationDecoration;
    }

    public ChatTypeDecoration chatDecoration() {
        return this.chatDecoration;
    }

    public ChatTypeDecoration narrationDecoration() {
        return this.narrationDecoration;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ChatType)) {
            return false;
        }
        ChatType chatType = (ChatType)object;
        return Objects.equals(this.chatDecoration, chatType.chatDecoration) && Objects.equals(this.narrationDecoration, chatType.narrationDecoration);
    }

    public int hashCode() {
        return (0 * 31 + Objects.hashCode(this.chatDecoration)) * 31 + Objects.hashCode(this.narrationDecoration);
    }

    public String toString() {
        return String.format("%s[chatDecoration=%s, narrationDecoration=%s]", this.getClass().getSimpleName(), Objects.toString(this.chatDecoration), Objects.toString(this.narrationDecoration));
    }

    public static final class ChatTypeDecoration {
        final String translationKey;
        final int[] parameters;
        final Tag style;
        public static final Type<ChatTypeDecoration> TYPE = new Type<ChatTypeDecoration>(ChatTypeDecoration.class){

            @Override
            public ChatTypeDecoration read(ByteBuf buffer) {
                String translationKey = (String)Types.STRING.read(buffer);
                int[] parameters = (int[])Types.INT_ARRAY_PRIMITIVE.read(buffer);
                Tag style = (Tag)Types.TAG.read(buffer);
                return new ChatTypeDecoration(translationKey, parameters, style);
            }

            @Override
            public void write(ByteBuf buffer, ChatTypeDecoration value) {
                Types.STRING.write(buffer, value.translationKey());
                Types.INT_ARRAY_PRIMITIVE.write(buffer, value.parameters());
                Types.TAG.write(buffer, value.style());
            }
        };

        public ChatTypeDecoration(String translationKey, int[] parameters, Tag style) {
            this.translationKey = translationKey;
            this.parameters = parameters;
            this.style = style;
        }

        public String translationKey() {
            return this.translationKey;
        }

        public int[] parameters() {
            return this.parameters;
        }

        public Tag style() {
            return this.style;
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof ChatTypeDecoration)) {
                return false;
            }
            ChatTypeDecoration chatTypeDecoration = (ChatTypeDecoration)object;
            return Objects.equals(this.translationKey, chatTypeDecoration.translationKey) && Objects.equals(this.parameters, chatTypeDecoration.parameters) && Objects.equals(this.style, chatTypeDecoration.style);
        }

        public int hashCode() {
            return ((0 * 31 + Objects.hashCode(this.translationKey)) * 31 + Objects.hashCode(this.parameters)) * 31 + Objects.hashCode(this.style);
        }

        public String toString() {
            return String.format("%s[translationKey=%s, parameters=%s, style=%s]", this.getClass().getSimpleName(), Objects.toString(this.translationKey), Objects.toString(this.parameters), Objects.toString(this.style));
        }
    }
}

