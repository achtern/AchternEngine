/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Christian Gärtner
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.achtern.AchternEngine.core.util.async;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Basic Helper for executing stuff aysnc.
 *
 * This is a single threaded scheduled Executor, so do not fire big tasks at this class.
 *
 * @see java.util.concurrent.Executors#newSingleThreadScheduledExecutor
 */
public class AsyncHandler {

    private static final ScheduledExecutorService worker = Executors.newSingleThreadScheduledExecutor();

    /**
     * Shutdown the worker thread.
     *
     * Do not call this method if you want to use this class at some point in the future,
     *  to stop a single submitted Runnable call {@link java.util.concurrent.ScheduledFuture#cancel}
     *
     * @see java.util.concurrent.ScheduledExecutorService#shutdown
     */
    public static void shutdown() {
        worker.shutdown();
    }

    public ScheduledFuture<?> postDelayed(Runnable runnable, long millis) {
        return postDelayed(runnable, millis, TimeUnit.MILLISECONDS);
    }

    public ScheduledFuture<?> postDelayed(Runnable runnable, long time, TimeUnit unit) {
        return worker.schedule(runnable, time, unit);
    }

}
