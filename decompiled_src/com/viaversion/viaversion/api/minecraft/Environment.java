/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft;

public enum Environment {
    NORMAL(0),
    NETHER(-1),
    END(1);

    private final int id;

    private Environment(int id) {
        this.id = id;
    }

    public int id() {
        return this.id;
    }

    public static Environment getEnvironmentById(int id) {
        Environment environment;
        switch (id) {
            case 0: {
                environment = NORMAL;
                break;
            }
            case 1: {
                environment = END;
                break;
            }
            default: {
                environment = NETHER;
            }
        }
        return environment;
    }
}

