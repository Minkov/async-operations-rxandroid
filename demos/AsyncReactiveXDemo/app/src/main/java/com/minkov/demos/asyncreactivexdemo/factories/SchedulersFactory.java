package com.minkov.demos.asyncreactivexdemo.factories;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by minkov on 10/18/17.
 */

public interface SchedulersFactory {
    Scheduler ui();
    Scheduler computation();
    Scheduler io();

}
