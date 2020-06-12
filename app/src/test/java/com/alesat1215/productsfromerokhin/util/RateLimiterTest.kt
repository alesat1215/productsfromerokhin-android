package com.alesat1215.productsfromerokhin.util

import org.junit.Test

import org.junit.Assert.*
import java.util.concurrent.TimeUnit

class RateLimiterTest {

    @Test
    fun shouldFetchAndReset() {
        var limiter = RateLimiter(0, TimeUnit.MINUTES)
        assertTrue(limiter.shouldFetch())
        assertTrue(limiter.shouldFetch())
        limiter = RateLimiter(1, TimeUnit.MINUTES)
        assertTrue(limiter.shouldFetch())
        assertFalse(limiter.shouldFetch())
        limiter.reset()
        assertTrue(limiter.shouldFetch())
        assertFalse(limiter.shouldFetch())
    }
}