/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.scheduler;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.viaversion.viaversion.api.scheduler.Scheduler;
import com.viaversion.viaversion.api.scheduler.Task;
import com.viaversion.viaversion.scheduler.ScheduledTask;
import com.viaversion.viaversion.scheduler.SubmittedTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public final class TaskScheduler
implements Scheduler {
    private final ExecutorService executorService = Executors.newCachedThreadPool(new ThreadFactoryBuilder().setNameFormat("Via Async Task %d").build());
    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1, new ThreadFactoryBuilder().setNameFormat("Via Async Scheduler %d").build());

    @Override
    public Task execute(Runnable runnable) {
        return new SubmittedTask(this.executorService.submit(runnable));
    }

    @Override
    public Task schedule(Runnable runnable, long delay2, TimeUnit timeUnit) {
        return new ScheduledTask(this.scheduledExecutorService.schedule(runnable, delay2, timeUnit));
    }

    @Override
    public Task scheduleRepeating(Runnable runnable, long delay2, long period, TimeUnit timeUnit) {
        return new ScheduledTask(this.scheduledExecutorService.scheduleAtFixedRate(runnable, delay2, period, timeUnit));
    }

    @Override
    public void shutdown() {
        this.executorService.shutdown();
        this.scheduledExecutorService.shutdown();
        try {
            this.executorService.awaitTermination(1L, TimeUnit.SECONDS);
            this.scheduledExecutorService.awaitTermination(1L, TimeUnit.SECONDS);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

