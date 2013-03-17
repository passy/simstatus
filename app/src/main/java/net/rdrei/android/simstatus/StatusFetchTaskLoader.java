package net.rdrei.android.simstatus;

import java.util.Date;

import net.rdrei.android.simstatus.StatusStore.Status;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

public class StatusFetchTaskLoader extends AsyncTaskLoader<StatusResult> {
	
	private static final String TAG = "SCS:StatusFetchTaskLoader";

	public StatusFetchTaskLoader(Context context) {
		super(context);
	}

	@Override
	public StatusResult loadInBackground() {
		Log.d(TAG, "sending request");
		StatusFetcher fetcher = new StatusFetcherImpl();
		final Status status = fetcher.fetchStatus();
		Log.d(TAG, "loadInBackground() finished");
		return new StatusResult(status, new Date());
	}

	@Override
	protected void onStartLoading() {
		super.onStartLoading();
		
		// At the moment, always force a new load.
		forceLoad();
	}
}