package com.jzw.schduler.Impl;

import com.jzw.schduler.AlertTask;

public abstract class AbstractAlertTask implements AlertTask {

	private int interval;
	
	@Override
	public int getInterval() {
		return this.interval;
	}

}
