/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package android.util


import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import android.log.Log

import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

//https://github.com/android/architecture-components-samples/blob/master/GithubBrowserSample/app/src/test-common/java/com/android/example/github/util/LiveDataTestUtil.kt
/**
 * Gets the value of a [LiveData] or waits for it to have one, with a timeout.
 *
 * Use this extension from host-side (JVM) tests. It's recommended to use it alongside
 * `InstantTaskExecutorRule` or a similar mechanism to execute tasks synchronously.
 */
fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2000,
    timeUnit: TimeUnit = TimeUnit.SECONDS,
    afterObserve: () -> Unit = {}
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    Log.tic(Thread.currentThread().id)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data = o
            Log.tic(Thread.currentThread().id)
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }
    Log.tic(Thread.currentThread().id)
    this.observeForever(observer)
    Log.tic(Thread.currentThread().id)
    afterObserve.invoke()

    // Don't wait indefinitely if the LiveData is not set.
    Log.tic(Thread.currentThread().id)
    if (!latch.await(time, timeUnit)) {
        Log.tic(Thread.currentThread().id)
        this.removeObserver(observer)
        throw TimeoutException("LiveData value was never set.")
    }
    Log.tic()
    @Suppress("UNCHECKED_CAST")
    return data as T
}