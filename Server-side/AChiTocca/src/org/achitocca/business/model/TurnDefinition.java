package org.achitocca.business.model;

import org.achitocca.business.model.util.CircularList;



public class TurnDefinition {
	private CircularList daysOfWeek;

	public CircularList getDaysOfWeek() {
		return daysOfWeek;
	}

	public void setDaysOfWeek(CircularList daysOfWeek) {
		this.daysOfWeek = daysOfWeek;
	}
}
