package com.aero51.moviedatabase.utils;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class AppExecutors {
    private final Executor diskIO;
    private final Executor networkIO;
    private final Executor mainThread;
    private final Executor others;
    private final Executor paging;

    public AppExecutors(Executor diskIO, Executor networkIO, Executor mainThread, Executor others, Executor paging) {
        this.diskIO = diskIO;
        this.networkIO = networkIO;
        this.mainThread = mainThread;
        this.others = others;
        this.paging = paging;
    }

      //this(Executors.newFixedThreadPool(3), Executors.newFixedThreadPool(3),
    public AppExecutors() {
        this(Executors.newSingleThreadExecutor(), Executors.newFixedThreadPool(3),
                new MainThreadExecutor(), Executors.newSingleThreadExecutor(),
                Executors.newFixedThreadPool(4));
    }

    public Executor diskIO() {
        return diskIO;
    }

    public Executor networkIO() {
        return networkIO;
    }

    public Executor mainThread() {
        return mainThread;
    }

    public Executor others() {
        return others;
    }

    public Executor paging() {
        return paging;
    }

    private static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());
        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}