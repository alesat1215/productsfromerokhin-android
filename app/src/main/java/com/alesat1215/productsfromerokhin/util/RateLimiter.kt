package com.alesat1215.productsfromerokhin.util

import android.os.SystemClock
import com.orhanobut.logger.Logger
import java.util.concurrent.TimeUnit

/** Set limit for "true" result for shouldFetch() by timeout & timeUnit */
class RateLimiter(timeout: Int, timeUnit: TimeUnit)
{
    /** Time in millis for last run shouldFetch() */
    private var last: Long? = null
    /** Timeout in millis for "true" result for shouldFetch() */
    private val timeout = timeUnit.toMillis(timeout.toLong())

    /** @return true if timeout is not over */
    @Synchronized
    fun shouldFetch(): Boolean {
        val now = SystemClock.uptimeMillis()
        /** Return true for first fetch & update last */
        if (last == null) {
            last = now
            Logger.d("shouldFetch() -> true, from null last is: ${last}, timeout: ${timeout}")
            return true
        }
        /** Return true if timeout is over & update last */
        if (now - (last ?: 0) >= timeout) {
            last = now
            Logger.d("shouldFetch() -> true, last is: ${last}, timeout: ${timeout}")
            return true
        }
        /** Return false if timeout isn't over */
        Logger.d("shouldFetch() -> false, last is: ${last}, timeout: ${timeout}")
        return false
    }

    /** Reset as if no fetch yet */
    @Synchronized
    fun reset() {
        last = null
        Logger.d("Reset last to null")
    }
}