package com.jzw.schduler.Impl;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.jzw.schduler.AlertTask;

public class SyncAlertWorker extends AbstractAlertWorker {

	public SyncAlertWorker(TimeUnit timeUnit) {
		super(timeUnit);
	}

	@Override
	protected void handlTask(AlertTask task) {
		task.run();
	}

}
