package com.jzw.schduler.Impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.jzw.schduler.AlertTask;

public class AsyncTaskWorker extends AbstractAlertWorker {

	public AsyncTaskWorker(TimeUnit timeUnit) {
		super(timeUnit);
	}
	ExecutorService executorService = Executors.newCachedThreadPool(Executors.defaultThreadFactory());
	@Override
	protected void handlTask(AlertTask task) {
		executorService.submit(task);

	}

}
