/*
 * Decompiled with CFR 0.152.
 */
package net.raphimc.vialoader.util;

import com.viaversion.viaversion.api.platform.PlatformTask;
import com.viaversion.viaversion.api.scheduler.Task;
import java.util.Objects;

public final class VLTask
implements PlatformTask<Task> {
    private final Task task;

    public VLTask(Task task) {
        this.task = task;
    }

    @Override
    public void cancel() {
        this.task.cancel();
    }

    public Task task() {
        return this.task;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof VLTask)) {
            return false;
        }
        VLTask vLTask = (VLTask)object;
        return Objects.equals(this.task, vLTask.task);
    }

    public int hashCode() {
        return 0 * 31 + Objects.hashCode(this.task);
    }

    public String toString() {
        return String.format("%s[task=%s]", this.getClass().getSimpleName(), Objects.toString(this.task));
    }
}

