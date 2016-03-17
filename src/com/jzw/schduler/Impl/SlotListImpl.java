package com.jzw.schduler.Impl;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.jzw.schduler.AlertTask;
import com.jzw.schduler.Slot;
import com.jzw.schduler.SlotList;

public class SlotListImpl implements SlotList {
	
	private static final int DEFAULT_SIZE = 1 << 6; // 64
	private static final int SIZE_MASK = DEFAULT_SIZE - 1;
	Slot[] slots;
	ConcurrentLinkedQueue<AlertTask> reTryQueue = new ConcurrentLinkedQueue<AlertTask>();
	private AtomicInteger next = new AtomicInteger(0);
	
	public SlotListImpl() {
		slots = new SlotImpl[DEFAULT_SIZE];
		startRetryJob();
	}
	
	private void startRetryJob() {
		ScheduledExecutorService pool = Executors.newScheduledThreadPool(1, new ThreadFactory() {
			AtomicInteger counter = new AtomicInteger(0);
			@Override
			public Thread newThread(Runnable r) {
				Thread newThread = new Thread("RETRY_TASK_RESTATTER_JOB_" + counter.incrementAndGet());
				newThread.setDaemon(true);
				return newThread;
			}
		});
		pool.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				// TODO: do retry
				
			}
		}, 1, 1, TimeUnit.MINUTES);
		
	}

	private int nextItem(int curInt) {
		return (curInt + 1)  & SIZE_MASK;
	}

	@Override
	public Slot nextSlot() {
		int curInt = next.get();
		// move next pointer
		while (!next.compareAndSet(curInt, nextItem(curInt))){
			curInt = next.get();
		} 
		// copy the slot for return
		Slot oldSlot = slots[curInt];
		Slot resultSlot = new SlotImpl(oldSlot);
		// clear slot
		oldSlot.clear();
		// restatter the task to right slot
		for (AlertTask task : resultSlot.getAlertTasks()) {
			int position = next.get() + task.getInterval();
			int pos = position;
			// try put task in the futrue slot
	        while(!slots[nextItem(pos ++ )].addTask(task) && (pos - 1 != position));
	        // no where to put
	        if (pos - 1 == position) {
				// add to retry queue
	        	reTryQueue.offer(task);
	        }
		}
		return resultSlot;
	}

}
