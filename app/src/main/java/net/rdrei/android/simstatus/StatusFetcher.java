package net.rdrei.android.simstatus;

import net.rdrei.android.simstatus.StatusResult.Status;

public interface StatusFetcher {

	public abstract Status fetchStatus(final String url);

}