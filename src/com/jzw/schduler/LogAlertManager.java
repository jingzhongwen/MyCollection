package com.jzw.schduler;

import java.util.concurrent.TimeUnit;

import com.jzw.schduler.Impl.SyncAlertWorker;

public class LogAlertManager {

	private SyncAlertWorker hourWorker = new SyncAlertWorker(TimeUnit.HOURS);
	private SyncAlertWorker minWorker = new SyncAlertWorker(TimeUnit.MINUTES);
	private SyncAlertWorker secWorker = new SyncAlertWorker(TimeUnit.SECONDS);
	
	void start(){
		// get task config in db, which added by user
		// add task to the worker
		// start a log data what
	}
}
