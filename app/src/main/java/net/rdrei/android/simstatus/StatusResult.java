package net.rdrei.android.simstatus;

import java.util.Date;

import net.rdrei.android.simstatus.StatusStore.Status;

public class StatusResult {
	public Status status;
	public Date updated;
	
	public StatusResult() {
		this(Status.UNKNOWN, new Date(0));
	}
	
	public StatusResult(Status status, Date updated) {
		super();
		this.status = status;
		this.updated = updated;
	}
}
