/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.api.minecraft;

import com.viaversion.viaversion.api.connection.StorableObject;
import com.viaversion.viaversion.api.minecraft.Environment;

public class ClientWorld
implements StorableObject {
    private Environment environment = Environment.NORMAL;

    public ClientWorld() {
    }

    public ClientWorld(Environment environment) {
        this.environment = environment;
    }

    public Environment getEnvironment() {
        return this.environment;
    }

    public boolean setEnvironment(int environmentId) {
        int previousEnvironmentId = this.environment.id();
        this.environment = Environment.getEnvironmentById(environmentId);
        return previousEnvironmentId != environmentId;
    }
}

