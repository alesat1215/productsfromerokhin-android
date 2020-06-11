package com.alesat1215.productsfromerokhin

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.alesat1215.productsfromerokhin.data.Group
import com.alesat1215.productsfromerokhin.data.Product
import com.alesat1215.productsfromerokhin.data.RemoteData
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

fun remoteDataMock() = RemoteData(
    "Title",
    "Img",
    "ImgTitle",
    "ProductsTitle",
    "ProductsTitle2"
).apply { groups = listOf(
    Group(1).apply { products = products123() },
    Group(2).apply { products = products456() }
) }

fun products123() = listOf(Product(1), Product(2), Product(3))
fun products456() = listOf(Product(4), Product(5), Product(6))

/**
 * Get the value from a LiveData object. We're waiting for LiveData to emit, for 2 seconds.
 * Once we got a notification via onChanged, we stop observing.
 */
fun <T> liveDataValue(liveData: LiveData<T>): T {
    val data = arrayOfNulls<Any>(1)
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data[0] = o
            latch.countDown()
            liveData.removeObserver(this)
        }
    }
    liveData.observeForever(observer)
    latch.await(2, TimeUnit.SECONDS)

    @Suppress("UNCHECKED_CAST")
    return data[0] as T
}