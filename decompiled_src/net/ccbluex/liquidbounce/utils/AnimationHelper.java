/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.utils;

import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.value.BoolValue;

public class AnimationHelper {
    public float animationX;
    public int alpha;

    public int getAlpha() {
        return this.alpha;
    }

    public float getAnimationX() {
        return this.animationX;
    }

    public void resetAlpha() {
        this.alpha = 0;
    }

    public AnimationHelper() {
        this.alpha = 0;
    }

    public void updateAlpha(int speed) {
        if (this.alpha < 255) {
            this.alpha += speed;
        }
    }

    public AnimationHelper(BoolValue value) {
        this.animationX = (Boolean)value.get() != false ? 5.0f : -5.0f;
    }

    public AnimationHelper(Module module) {
        this.animationX = module.getState() ? 5.0f : -5.0f;
    }

    public static double animate(double target, double current, double speed) {
        boolean larger = target > current;
        boolean bl = larger;
        if (speed < 0.0) {
            speed = 0.0;
        } else if (speed > 1.0) {
            speed = 1.0;
        }
        double dif = Math.max(target, current) - Math.min(target, current);
        double factor = dif * speed;
        if (factor < 0.1) {
            factor = 0.1;
        }
        current = larger ? (current = current + factor) : (current = current - factor);
        return current;
    }
}

