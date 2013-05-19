package net.rdrei.android.simstatus;

import net.rdrei.android.simstatus.ui.MainActivity;

import javax.inject.Inject;

public class StatusFetchTaskLoaderFactory {

    @Inject
	public StatusFetchTaskLoaderFactory() {
	}

	public StatusFetchTaskLoader create(final MainActivity context,
			final StatusFetcher fetcher, final boolean forceRefresh) {
		return new StatusFetchTaskLoader(context, fetcher, forceRefresh);
	}

}
