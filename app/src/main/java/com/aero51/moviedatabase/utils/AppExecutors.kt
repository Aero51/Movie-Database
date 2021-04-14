package com.aero51.moviedatabase.utils

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutors //this(Executors.newFixedThreadPool(3), Executors.newFixedThreadPool(3),
@JvmOverloads constructor(private val diskIO: Executor = Executors.newSingleThreadExecutor(), private val networkIO: Executor = Executors.newFixedThreadPool(3), private val mainThread: Executor =
        MainThreadExecutor(), private val others: Executor = Executors.newSingleThreadExecutor(), private val paging: Executor =
                                  Executors.newFixedThreadPool(4)) {
    fun diskIO(): Executor {
        return diskIO
    }

    fun networkIO(): Executor {
        return networkIO
    }

    fun mainThread(): Executor {
        return mainThread
    }

    fun others(): Executor {
        return others
    }

    fun paging(): Executor {
        return paging
    }

    private class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
}