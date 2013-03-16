package net.rdrei.android.simstatus;

public interface StatusStore {
	public enum Status {
		YES,
		NO,
		MAYBE,
		UNKNOWN
	};

	public abstract Status getLastStatus();

}