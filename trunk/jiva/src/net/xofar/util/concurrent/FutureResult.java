/*
 * Copyright (C) 2007 Lalit Pant <pant.lalit@gmail.com>
 *
 * The contents of this file are subject to the GNU General Public License 
 * Version 2 or later (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of
 * the License at http://www.gnu.org/copyleft/gpl.html
 *
 * Software distributed under the License is distributed on an "AS
 * IS" basis, WITHOUT WARRANTY OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * rights and limitations under the License.
 *
 */

package net.xofar.util.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureResult<V> {
    private volatile V result;

    private volatile Exception problem;

    private final FutureTask<V> resultSyncer;

    public FutureResult() {
        Callable<V> resultReturner = new Callable<V>() {
            public V call() throws Exception {
                if (problem != null) {
                    throw problem;
                } else {
                    return result;
                }
            }
        };
        resultSyncer = new FutureTask<V>(resultReturner);
    }

    public void set(V result) {
        this.result = result;
        resultSyncer.run();
    }

    public void setException(Exception problem) {
        this.problem = problem;
        resultSyncer.run();
    }

    public V get() throws InterruptedException, ExecutionException {
        return resultSyncer.get();
    }
}
