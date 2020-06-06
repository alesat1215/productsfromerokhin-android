package com.alesat1215.productsfromerokhin.util

import android.os.SystemClock
import android.util.Log
import java.util.concurrent.TimeUnit

class RateLimiter(timeout: Int, timeUnit: TimeUnit)
{
    private var last: Long = 0
    private val timeout = timeUnit.toMillis(timeout.toLong())

    @Synchronized
    fun shouldFetch(): Boolean {
        val now = SystemClock.uptimeMillis()
        if (last == 0L) {
            last = now
            Log.d("Limiter", "shouldFetch() -> true, from 0 last is: ${last}, timeout: ${timeout}")
            return true
        }
        if (now - last > timeout) {
            last = now
            Log.d("Limiter", "shouldFetch() -> true, last is: ${last}, timeout: ${timeout}")
            return true
        }
        Log.d("Limiter", "shouldFetch() -> false, last is: ${last}, timeout: ${timeout}")
        return false
    }

    @Synchronized
    fun reset() {
        last = 0
        Log.d("Limiter", "Reset last to null")
    }
}