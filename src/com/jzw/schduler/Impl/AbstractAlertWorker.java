package com.jzw.schduler.Impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import com.jzw.schduler.AlertTask;
import com.jzw.schduler.AlertWorker;
import com.jzw.schduler.Slot;
import com.jzw.schduler.SlotList;

public abstract class AbstractAlertWorker implements AlertWorker {

	private TimeUnit timeUnit;
	private SlotList slotList;
	ExecutorService pool = Executors.newCachedThreadPool(new ThreadFactory() {
		AtomicLong counter = new AtomicLong(0);
		@Override
		public Thread newThread(Runnable arg0) {
			Thread newThread = new Thread("TASK_HANDLE_THREAD_" + counter.incrementAndGet());
			newThread.setDaemon(true);
			return newThread;
		}
	});
	
	protected AbstractAlertWorker(TimeUnit timeUnit){
		this.timeUnit = timeUnit;
		
	}
	
	@Override
	public void start() {
		ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
		executorService.schedule(new Runnable() {
			
			@Override
			public void run() {
				try {
					Slot slot = slotList.nextSlot();
					pool.submit(new Runnable() {
						@Override
						public void run() {
							handleSlot(slot);
						}
					});
					
				}catch(Throwable ex) {
					
				}
				
			}

			
		}, 1, timeUnit);
	}

	protected void handleSlot(Slot slot) {
		if (slot.isNotEmpty()) {
			for (AlertTask task : slot.getAlertTasks()) {
				try {
					handlTask(task);
				} catch (Throwable ex) {
					// ÈÝ´í
				}
			}
		}
	}

	protected abstract void handlTask(AlertTask task);
	
}
