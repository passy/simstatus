package net.rdrei.android.simstatus;

public interface StatusStore {

	public abstract StatusResult loadStatus();
	public abstract void saveStatus(StatusResult result);

}