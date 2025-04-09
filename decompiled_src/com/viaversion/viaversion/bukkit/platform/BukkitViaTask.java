/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.bukkit.platform;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.platform.PlatformTask;
import java.util.Objects;
import org.bukkit.scheduler.BukkitTask;

public final class BukkitViaTask
implements PlatformTask<BukkitTask> {
    private final BukkitTask task;

    public BukkitViaTask(BukkitTask task) {
        this.task = task;
    }

    @Override
    public void cancel() {
        Preconditions.checkArgument((this.task != null ? 1 : 0) != 0, (Object)"Task cannot be cancelled");
        this.task.cancel();
    }

    public BukkitTask task() {
        return this.task;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof BukkitViaTask)) {
            return false;
        }
        BukkitViaTask bukkitViaTask = (BukkitViaTask)object;
        return Objects.equals(this.task, bukkitViaTask.task);
    }

    public int hashCode() {
        return 0 * 31 + Objects.hashCode(this.task);
    }

    public String toString() {
        return String.format("%s[task=%s]", this.getClass().getSimpleName(), Objects.toString(this.task));
    }
}

