package com.foribus.cityatoms.common;

import androidx.annotation.NonNull;
import androidx.room.TypeConverter;

import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * For processing multiple objects through a queue on a single thread on a certain interval
 *
 * @param <T> type of object to be processed
 */
public abstract class SyncService<T> {
    private AtomicBoolean isRunning;
    private long lastSync;
    private long syncInterval;
    private Executor executor;

    public SyncService(long syncInterval, Executor executor) {
        isRunning = new AtomicBoolean();
        this.syncInterval = syncInterval;
        this.executor = executor;
    }

    /**
     * Call to sync anything that wasn't synced before app was closed
     */
    public void onAppStart() {
        add(null);
    }

    protected void add(T entity) {
        executor.execute(() -> {
            if (entity != null) {
                onEntityAdd(entity);
            }
            long now = System.currentTimeMillis();
            if (now - lastSync >= syncInterval && !isRunning.getAndSet(true)) {
                sync();
            }
        });
    }

    /**
     * Ran on executor. Handle adding entity to queue or database.
     *
     * @param entity entity being added
     */
    protected abstract void onEntityAdd(T entity);

    /**
     * Ran on executor. Handle syncing with server.
     */
    protected abstract void sync();

    /**
     * Call this when finished syncing
     */
    protected void finishSyncing() {
        lastSync = System.currentTimeMillis();
        isRunning.set(false);
    }

    /**
     * Call this to stop syncing without updating last sync time
     */
    protected void stopSyncing() {
        isRunning.set(false);
    }

    protected Executor getExecutor() {
        return executor;
    }

    public enum SyncStatus {
        PENDING(0),
        IN_PROGRESS(1),
        SYNCED(2),
        UNKNOWN(-1);

        private int value;

        SyncStatus(int value) {
            this.value = value;
        }

        public static SyncStatus instance(int b) {
            if (b == PENDING.value)
                return PENDING;
            else if (b == IN_PROGRESS.value)
                return IN_PROGRESS;
            else if (b == SYNCED.value)
                return SYNCED;

            return UNKNOWN;
        }

        public int value() {
            return this.value;
        }

        @NonNull
        @Override
        public String toString() {
            switch (this) {
                case PENDING:
                    return "Pending";
                case IN_PROGRESS:
                    return "In progress";
                case SYNCED:
                    return "Synced";
            }
            return "Unknown";
        }
    }

    public static class SyncStatusConverter {

        @TypeConverter
        public static SyncStatus toSyncStatus(int status) {
            return SyncStatus.instance(status);
        }

        @TypeConverter
        public static int toInteger(SyncStatus type) {
            return type.value;
        }
    }
}
