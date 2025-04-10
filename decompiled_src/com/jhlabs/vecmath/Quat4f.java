/*
 * Decompiled with CFR 0.152.
 */
package com.jhlabs.vecmath;

import com.jhlabs.vecmath.AxisAngle4f;
import com.jhlabs.vecmath.Matrix4f;
import com.jhlabs.vecmath.Tuple4f;

public class Quat4f
extends Tuple4f {
    public Quat4f() {
        this(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public Quat4f(float[] x) {
        this.x = x[0];
        this.y = x[1];
        this.z = x[2];
        this.w = x[3];
    }

    public Quat4f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Quat4f(Quat4f t) {
        this.x = t.x;
        this.y = t.y;
        this.z = t.z;
        this.w = t.w;
    }

    public Quat4f(Tuple4f t) {
        this.x = t.x;
        this.y = t.y;
        this.z = t.z;
        this.w = t.w;
    }

    public void set(AxisAngle4f a) {
        float halfTheta = a.angle * 0.5f;
        float cosHalfTheta = (float)Math.cos(halfTheta);
        float sinHalfTheta = (float)Math.sin(halfTheta);
        this.x = a.x * sinHalfTheta;
        this.y = a.y * sinHalfTheta;
        this.z = a.z * sinHalfTheta;
        this.w = cosHalfTheta;
    }

    public void normalize() {
        float d = 1.0f / (this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w);
        this.x *= d;
        this.y *= d;
        this.z *= d;
        this.w *= d;
    }

    public void set(Matrix4f m2) {
        float tr = m2.m00 + m2.m11 + m2.m22;
        if ((double)tr > 0.0) {
            float s = (float)Math.sqrt(tr + 1.0f);
            this.w = s / 2.0f;
            s = 0.5f / s;
            this.x = (m2.m12 - m2.m21) * s;
            this.y = (m2.m20 - m2.m02) * s;
            this.z = (m2.m01 - m2.m10) * s;
        } else {
            int i = 0;
            if (m2.m11 > m2.m00) {
                i = 1;
                if (m2.m22 > m2.m11) {
                    i = 2;
                }
            } else if (m2.m22 > m2.m00) {
                i = 2;
            }
            switch (i) {
                case 0: {
                    float s = (float)Math.sqrt(m2.m00 - (m2.m11 + m2.m22) + 1.0f);
                    this.x = s * 0.5f;
                    if ((double)s != 0.0) {
                        s = 0.5f / s;
                    }
                    this.w = (m2.m12 - m2.m21) * s;
                    this.y = (m2.m01 + m2.m10) * s;
                    this.z = (m2.m02 + m2.m20) * s;
                    break;
                }
                case 1: {
                    float s = (float)Math.sqrt(m2.m11 - (m2.m22 + m2.m00) + 1.0f);
                    this.y = s * 0.5f;
                    if ((double)s != 0.0) {
                        s = 0.5f / s;
                    }
                    this.w = (m2.m20 - m2.m02) * s;
                    this.z = (m2.m12 + m2.m21) * s;
                    this.x = (m2.m10 + m2.m01) * s;
                    break;
                }
                case 2: {
                    float s = (float)Math.sqrt(m2.m00 - (m2.m11 + m2.m22) + 1.0f);
                    this.z = s * 0.5f;
                    if ((double)s != 0.0) {
                        s = 0.5f / s;
                    }
                    this.w = (m2.m01 - m2.m10) * s;
                    this.x = (m2.m20 + m2.m02) * s;
                    this.y = (m2.m21 + m2.m12) * s;
                }
            }
        }
    }
}

