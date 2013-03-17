package net.rdrei.android.simstatus;

import net.rdrei.android.simstatus.StatusStore.Status;

public interface StatusFetcher {

	public abstract Status fetchStatus();

}