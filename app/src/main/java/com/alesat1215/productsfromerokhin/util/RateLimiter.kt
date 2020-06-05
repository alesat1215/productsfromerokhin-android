package com.alesat1215.productsfromerokhin.util

import android.os.SystemClock
import java.util.concurrent.TimeUnit

class RateLimiter(timeout: Int, timeUnit: TimeUnit)
{
    private var last: Long? = null
    private val timeout = timeUnit.toMillis(timeout.toLong())

    @Synchronized
    fun shouldFetch(): Boolean {
        val now = SystemClock.uptimeMillis()
        if (last == null) {
            last = now
            return true
        }
        if (now - (last ?: 0) > timeout) {
            last = now
            return true
        }
        return false
    }

    @Synchronized
    fun reset() {
        last = null
    }
}