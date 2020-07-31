package com.alesat1215.productsfromerokhin.util

import org.junit.Test

import org.junit.Assert.*
import java.util.concurrent.TimeUnit

class RateLimiterTest {

    @Test
    fun needUpdateAndReset() {
        var limiter = RateLimiter(0, TimeUnit.MINUTES)
        assertTrue(limiter.needUpdate())
        assertTrue(limiter.needUpdate())
        limiter = RateLimiter(1, TimeUnit.MINUTES)
        assertTrue(limiter.needUpdate())
        assertFalse(limiter.needUpdate())
        limiter.reset()
        assertTrue(limiter.needUpdate())
        assertFalse(limiter.needUpdate())
    }
}