package com.jzw.schduler;

import java.util.List;

public interface Slot {

	boolean isNotEmpty();

	List<AlertTask> getAlertTasks();

	void clear();

	boolean addTask(AlertTask task);

	int getCapacity();

}
