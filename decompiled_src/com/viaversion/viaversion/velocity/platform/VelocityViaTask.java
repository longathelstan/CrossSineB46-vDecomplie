/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.velocity.platform;

import com.velocitypowered.api.scheduler.ScheduledTask;
import com.viaversion.viaversion.api.platform.PlatformTask;
import java.util.Objects;

public final class VelocityViaTask
implements PlatformTask<ScheduledTask> {
    private final ScheduledTask task;

    public VelocityViaTask(ScheduledTask task) {
        this.task = task;
    }

    @Override
    public void cancel() {
        this.task.cancel();
    }

    public ScheduledTask task() {
        return this.task;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof VelocityViaTask)) {
            return false;
        }
        VelocityViaTask velocityViaTask = (VelocityViaTask)object;
        return Objects.equals(this.task, velocityViaTask.task);
    }

    public int hashCode() {
        return 0 * 31 + Objects.hashCode(this.task);
    }

    public String toString() {
        return String.format("%s[task=%s]", this.getClass().getSimpleName(), Objects.toString(this.task));
    }
}

