/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.bukkit.platform;

import com.viaversion.viaversion.api.platform.PlatformTask;
import com.viaversion.viaversion.api.scheduler.Task;

public class BukkitViaTaskTask
implements PlatformTask<Task> {
    private final Task task;

    public BukkitViaTaskTask(Task task) {
        this.task = task;
    }

    @Override
    public void cancel() {
        this.task.cancel();
    }
}

