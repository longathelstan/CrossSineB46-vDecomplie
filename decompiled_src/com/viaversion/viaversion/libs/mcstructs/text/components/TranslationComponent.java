/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.mcstructs.text.components;

import com.viaversion.viaversion.libs.mcstructs.core.utils.ToString;
import com.viaversion.viaversion.libs.mcstructs.text.ATextComponent;
import com.viaversion.viaversion.libs.mcstructs.text.components.StringComponent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;

public class TranslationComponent
extends ATextComponent {
    private static final Function<String, String> NULL_TRANSLATOR = s -> null;
    private static final Pattern ARG_PATTERN = Pattern.compile("%(?:(\\d+)\\$)?([A-Za-z%]|$)");
    private String key;
    private Object[] args;
    @Nullable
    private String fallback;
    private Function<String, String> translator = NULL_TRANSLATOR;

    public TranslationComponent(String key, List<?> args2) {
        this.key = key;
        this.args = args2.toArray();
    }

    public TranslationComponent(String key, Object ... args2) {
        this.key = key;
        this.args = args2;
    }

    public String getKey() {
        return this.key;
    }

    public TranslationComponent setKey(String key) {
        this.key = key;
        return this;
    }

    public Object[] getArgs() {
        return this.args;
    }

    public TranslationComponent setArgs(Object[] args2) {
        this.args = args2;
        return this;
    }

    @Nullable
    public String getFallback() {
        return this.fallback;
    }

    public TranslationComponent setFallback(@Nullable String fallback) {
        this.fallback = fallback;
        return this;
    }

    public TranslationComponent setTranslator(@Nullable Function<String, String> translator) {
        this.translator = translator == null ? NULL_TRANSLATOR : translator;
        return this;
    }

    public ATextComponent resolveIntoComponents() {
        ArrayList<ATextComponent> components = new ArrayList<ATextComponent>();
        String translated = this.translator.apply(this.key);
        if (translated == null) {
            translated = this.fallback;
        }
        if (translated == null) {
            translated = this.key;
        }
        Matcher matcher = ARG_PATTERN.matcher(translated);
        int argIndex = 0;
        int start = 0;
        while (matcher.find(start)) {
            int matchStart = matcher.start();
            int matchEnd = matcher.end();
            if (matchStart > start) {
                components.add(new StringComponent(String.format(translated.substring(start, matchStart), new Object[0])));
            }
            start = matchEnd;
            String argType = matcher.group(2);
            String match = translated.substring(matchStart, matchEnd);
            if (argType.equals("%") && match.equals("%%")) {
                components.add(new StringComponent("%"));
                continue;
            }
            if (!argType.equals("s")) {
                throw new IllegalStateException("Unsupported format: '" + match + "'");
            }
            String rawIndex = matcher.group(1);
            int index2 = rawIndex == null ? argIndex++ : Integer.parseInt(rawIndex) - 1;
            if (index2 >= this.args.length) continue;
            Object arg = this.args[index2];
            if (arg instanceof ATextComponent) {
                components.add((ATextComponent)arg);
                continue;
            }
            if (arg == null) {
                components.add(new StringComponent("null"));
                continue;
            }
            components.add(new StringComponent(arg.toString()));
        }
        if (start < translated.length()) {
            components.add(new StringComponent(String.format(translated.substring(start), new Object[0])));
        }
        StringComponent out = new StringComponent();
        out.setStyle(this.getStyle());
        components.forEach(out::append);
        return out;
    }

    @Override
    public String asLegacyFormatString() {
        return this.resolveIntoComponents().asLegacyFormatString();
    }

    @Override
    public String asSingleString() {
        return this.resolveIntoComponents().asUnformattedString();
    }

    @Override
    public ATextComponent copy() {
        return this.putMetaCopy(this.shallowCopy());
    }

    @Override
    public ATextComponent shallowCopy() {
        Object[] copyArgs = new Object[this.args.length];
        for (int i = 0; i < this.args.length; ++i) {
            Object arg = this.args[i];
            copyArgs[i] = arg instanceof ATextComponent ? ((ATextComponent)arg).copy() : arg;
        }
        TranslationComponent copy = new TranslationComponent(this.key, copyArgs);
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
        TranslationComponent that = (TranslationComponent)o;
        return Objects.equals(this.getSiblings(), that.getSiblings()) && Objects.equals(this.getStyle(), that.getStyle()) && Objects.equals(this.key, that.key) && Arrays.equals(this.args, that.args) && Objects.equals(this.translator, that.translator);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(this.getSiblings(), this.getStyle(), this.key, this.translator);
        result = 31 * result + Arrays.hashCode(this.args);
        return result;
    }

    @Override
    public String toString() {
        return ToString.of(this).add("siblings", this.getSiblings(), siblings -> !siblings.isEmpty()).add("style", this.getStyle(), style -> !style.isEmpty()).add("key", this.key).add("args", this.args, args2 -> ((Object[])args2).length > 0, Arrays::toString).add("translator", this.translator, translator -> translator != NULL_TRANSLATOR).toString();
    }
}

