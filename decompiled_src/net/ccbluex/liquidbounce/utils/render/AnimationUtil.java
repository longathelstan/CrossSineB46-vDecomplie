/*
 * Decompiled with CFR 0.152.
 */
package net.ccbluex.liquidbounce.utils.render;

public class AnimationUtil {
    public static float fastmax(float a, float b) {
        return a >= b ? a : b;
    }

    public static float fastmin(float a, float b) {
        return a <= b ? a : b;
    }

    public static float moveUD(float current, float end, float smoothSpeed, float minSpeed) {
        float movement = (end - current) * smoothSpeed;
        if (movement > 0.0f) {
            movement = AnimationUtil.fastmax(minSpeed, movement);
            movement = AnimationUtil.fastmin(end - current, movement);
        } else if (movement < 0.0f) {
            movement = AnimationUtil.fastmin(-minSpeed, movement);
            movement = AnimationUtil.fastmax(end - current, movement);
        }
        return current + movement;
    }

    public static double getAnimationState(double animation, double finalState, double speed) {
        float add = (float)(0.01 * speed);
        animation = animation < finalState ? Math.min(animation + (double)add, finalState) : Math.max(animation - (double)add, finalState);
        return animation;
    }

    public static float getAnimationState(float animation, float finalState, float speed) {
        float add = (float)(0.01 * (double)speed);
        animation = animation < finalState ? Math.min(animation + add, finalState) : Math.max(animation - add, finalState);
        return animation;
    }

    public static double animate(double target, double current, double speed) {
        boolean larger;
        boolean bl = larger = target > current;
        if (speed < 0.0) {
            speed = 0.0;
        } else if (speed > 1.0) {
            speed = 1.0;
        }
        if (target == current) {
            return target;
        }
        double dif = Math.max(target, current) - Math.min(target, current);
        double factor = Math.max(dif * speed, 1.0);
        if (factor < 0.1) {
            factor = 0.1;
        }
        current = larger ? (current + factor > target ? target : (current += factor)) : (current - factor < target ? target : (current -= factor));
        return current;
    }
}

