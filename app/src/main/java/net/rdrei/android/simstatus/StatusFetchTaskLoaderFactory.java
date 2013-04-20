package net.rdrei.android.simstatus;

import javax.inject.Inject;

import net.rdrei.android.simstatus.ui.MainActivity;
import android.content.Context;

public class StatusFetchTaskLoaderFactory {

	@Inject
	Context mContect;

	public StatusFetchTaskLoaderFactory() {
	}

	public StatusFetchTaskLoader create(final MainActivity context,
			final StatusFetcher fetcher, final boolean forceRefresh) {
		return new StatusFetchTaskLoader(context, fetcher, forceRefresh);
	}

}
