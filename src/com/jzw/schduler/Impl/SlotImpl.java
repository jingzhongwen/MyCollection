package com.jzw.schduler.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import com.jzw.schduler.AlertTask;
import com.jzw.schduler.Slot;

public class SlotImpl implements Slot {
	
	private int capacity;
	

	Queue<AlertTask> alertTasks;
	
	public SlotImpl(){
		this(100);
	}

	public SlotImpl(int capacity) {
		this.capacity = capacity;
		this.alertTasks = new LinkedBlockingQueue<AlertTask>(capacity);
	}

	public SlotImpl(Slot slot) {
		this.capacity = slot.getCapacity();
		this.alertTasks = new LinkedBlockingQueue<AlertTask>(slot.getAlertTasks());
	}
	
	@Override
	public int getCapacity() {
		return capacity;
	}

	@Override
	public boolean isNotEmpty() {
		return !this.alertTasks.isEmpty();
	}

	@Override
	public List<AlertTask> getAlertTasks() {
		return new ArrayList<AlertTask>(this.alertTasks);
	}

	@Override
	public void clear() {
		this.clear();
		
	}

	@Override
	public boolean addTask(AlertTask task) {
		return this.alertTasks.offer(task);
	}

}
