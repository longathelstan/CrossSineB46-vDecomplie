/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viarewind.api.minecraft.math;

import com.viaversion.viarewind.api.minecraft.math.Vector3d;

public class Ray3d {
    Vector3d start;
    Vector3d dir;

    public Ray3d(Vector3d start, Vector3d dir) {
        this.start = start;
        this.dir = dir;
    }

    public Vector3d getStart() {
        return this.start;
    }

    public Vector3d getDir() {
        return this.dir;
    }
}

