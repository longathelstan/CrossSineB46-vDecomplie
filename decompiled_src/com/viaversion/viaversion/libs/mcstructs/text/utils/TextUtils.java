/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.mcstructs.text.utils;

import com.viaversion.viaversion.libs.mcstructs.core.TextFormatting;
import com.viaversion.viaversion.libs.mcstructs.text.ATextComponent;
import com.viaversion.viaversion.libs.mcstructs.text.components.KeybindComponent;
import com.viaversion.viaversion.libs.mcstructs.text.components.StringComponent;
import com.viaversion.viaversion.libs.mcstructs.text.components.TranslationComponent;
import com.viaversion.viaversion.libs.mcstructs.text.events.click.ClickEvent;
import com.viaversion.viaversion.libs.mcstructs.text.events.click.ClickEventAction;
import com.viaversion.viaversion.libs.mcstructs.text.events.hover.AHoverEvent;
import com.viaversion.viaversion.libs.mcstructs.text.events.hover.impl.EntityHoverEvent;
import com.viaversion.viaversion.libs.mcstructs.text.events.hover.impl.TextHoverEvent;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;

public class TextUtils {
    private static final String URL_PATTERN = "(?:https?://)?[\\w._-]+\\.\\w{2,}(?:/\\S*)?";

    public static ATextComponent makeURLsClickable(ATextComponent component) {
        return TextUtils.replace(component, URL_PATTERN, comp -> {
            comp.getStyle().setClickEvent(new ClickEvent(ClickEventAction.OPEN_URL, comp.asSingleString()));
            return comp;
        });
    }

    public static ATextComponent replace(ATextComponent component, Function<ATextComponent, ATextComponent> replaceFunction) {
        ATextComponent out = component.shallowCopy();
        if ((out = replaceFunction.apply(out)) instanceof TranslationComponent) {
            Object[] args2 = ((TranslationComponent)out).getArgs();
            for (int i = 0; i < args2.length; ++i) {
                if (!(args2[i] instanceof ATextComponent)) continue;
                args2[i] = TextUtils.replace((ATextComponent)args2[i], replaceFunction);
            }
        }
        for (ATextComponent sibling : component.getSiblings()) {
            out.append(TextUtils.replace(sibling, replaceFunction));
        }
        return out;
    }

    public static ATextComponent replace(ATextComponent component, String searchRegex, Function<ATextComponent, ATextComponent> replaceFunction) {
        ATextComponent out;
        Pattern pattern = Pattern.compile(searchRegex);
        if (component instanceof StringComponent) {
            String text = component.asSingleString();
            Matcher matcher = pattern.matcher(text);
            ArrayList<ATextComponent> parts = new ArrayList<ATextComponent>();
            int last = 0;
            while (matcher.find()) {
                ATextComponent replace;
                int start = matcher.start();
                String match = matcher.group();
                if (start > last) {
                    parts.add(new StringComponent(text.substring(last, start)).setStyle(component.getStyle().copy()));
                }
                if ((replace = replaceFunction.apply(new StringComponent(match).setStyle(component.getStyle().copy()))) != null) {
                    parts.add(replace);
                }
                last = matcher.end();
            }
            if (last < text.length()) {
                parts.add(new StringComponent(text.substring(last)).setStyle(component.getStyle().copy()));
            }
            if (parts.size() > 1) {
                out = new StringComponent("");
                for (ATextComponent part : parts) {
                    out.append(part);
                }
            } else {
                out = parts.size() == 1 ? ((ATextComponent)parts.get(0)).shallowCopy() : component.shallowCopy();
            }
        } else {
            out = component.shallowCopy();
        }
        for (ATextComponent sibling : component.getSiblings()) {
            ATextComponent replace = TextUtils.replace(sibling, searchRegex, replaceFunction);
            out.append(replace);
        }
        return out;
    }

    public static ATextComponent replaceRGBColors(ATextComponent component) {
        ATextComponent out = component.copy();
        out.forEach(comp -> {
            if (comp.getStyle().getColor() != null && comp.getStyle().getColor().isRGBColor()) {
                comp.getStyle().setFormatting(TextFormatting.getClosestFormattingColor(comp.getStyle().getColor().getRgbValue()));
            }
        });
        return out;
    }

    public static ATextComponent join(ATextComponent separator, ATextComponent ... components) {
        if (components.length == 0) {
            return new StringComponent("");
        }
        if (components.length == 1) {
            return components[0].copy();
        }
        ATextComponent out = null;
        for (ATextComponent component : components) {
            if (out == null) {
                out = new StringComponent("").append(component.copy());
                continue;
            }
            out.append(separator.copy()).append(component.copy());
        }
        return out;
    }

    public static void iterateAll(ATextComponent component, Consumer<ATextComponent> consumer) {
        consumer.accept(component);
        if (component instanceof TranslationComponent) {
            TranslationComponent translationComponent = (TranslationComponent)component;
            for (Object arg : translationComponent.getArgs()) {
                if (!(arg instanceof ATextComponent)) continue;
                TextUtils.iterateAll((ATextComponent)arg, consumer);
            }
        }
        if (component.getStyle().getHoverEvent() != null) {
            ATextComponent name;
            AHoverEvent hoverEvent = component.getStyle().getHoverEvent();
            if (hoverEvent instanceof TextHoverEvent) {
                TextUtils.iterateAll(((TextHoverEvent)hoverEvent).getText(), consumer);
            } else if (hoverEvent instanceof EntityHoverEvent && (name = ((EntityHoverEvent)hoverEvent).getName()) != null) {
                TextUtils.iterateAll(name, consumer);
            }
        }
        for (ATextComponent sibling : component.getSiblings()) {
            TextUtils.iterateAll(sibling, consumer);
        }
    }

    public static void setTranslator(ATextComponent component, @Nullable Function<String, String> translator) {
        TextUtils.setTranslator(component, translator, translator);
    }

    public static void setTranslator(ATextComponent component, @Nullable Function<String, String> textTranslator, @Nullable Function<String, String> keyTranslator) {
        TextUtils.iterateAll(component, comp -> {
            if (comp instanceof TranslationComponent) {
                TranslationComponent translationComponent = (TranslationComponent)comp;
                translationComponent.setTranslator(textTranslator);
            } else if (comp instanceof KeybindComponent) {
                KeybindComponent keybindComponent = (KeybindComponent)comp;
                keybindComponent.setTranslator(keyTranslator);
            }
        });
    }

    public static ATextComponent[] split(ATextComponent component, String split, boolean resolveTranslations) {
        ATextComponent rootCopy = component.copy();
        rootCopy.applyParentStyle();
        ArrayList components = new ArrayList();
        ArrayList current = new ArrayList();
        Runnable addCurrent = () -> {
            boolean wasEmpty = current.isEmpty();
            current.removeIf(comp -> comp instanceof StringComponent && comp.asSingleString().isEmpty());
            if (current.size() == 1) {
                components.add(current.get(0));
            } else if (!wasEmpty) {
                StringComponent part = new StringComponent("");
                for (ATextComponent textComponent : current) {
                    part.append(textComponent);
                }
                components.add(part);
            }
            current.clear();
        };
        rootCopy.forEach(comp -> {
            if (comp instanceof StringComponent || comp instanceof TranslationComponent && resolveTranslations) {
                String text = comp.asSingleString();
                if (text.contains(split)) {
                    String[] parts = text.split(split, -1);
                    for (int i = 0; i < parts.length; ++i) {
                        String part = parts[i];
                        ATextComponent partComp = new StringComponent(part).setStyle(comp.getStyle().copy());
                        current.add(partComp);
                        if (i == parts.length - 1) continue;
                        addCurrent.run();
                    }
                } else {
                    current.add(comp.shallowCopy());
                }
            } else {
                current.add(comp.shallowCopy());
            }
        });
        addCurrent.run();
        return components.toArray(new ATextComponent[0]);
    }
}

