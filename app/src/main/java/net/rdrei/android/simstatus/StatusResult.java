package net.rdrei.android.simstatus;

import java.util.Date;

public class StatusResult {
	public enum Status {
		YES,
		NO,
		MAYBE,
		UNKNOWN,
		ERROR
	};
	
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

	@Override
	public String toString() {
		return "StatusResult [status=" + status + ", updated=" + updated + "]";
	}
}
