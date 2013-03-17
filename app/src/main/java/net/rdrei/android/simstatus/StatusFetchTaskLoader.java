package net.rdrei.android.simstatus;

import java.util.Date;

import net.rdrei.android.simstatus.StatusStore.Status;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class StatusFetchTaskLoader extends AsyncTaskLoader<StatusResult> {

	/**
	 * Time in which we won't request a new result unless explicitly requested
	 * in seconds.
	 */
	public static int CACHE_TIME = 5 * 60 * 1000;
	private static final String TAG = "SCS:StatusFetchTaskLoader";
	private StatusResult mOldResult;
	private boolean mForceRefresh;

	public StatusFetchTaskLoader(Context context) {
		this(context, false);
	}

	public StatusFetchTaskLoader(Context context, boolean forceRefresh) {
		super(context);
		Log.d(TAG, "Creating new loader task with force=" + forceRefresh);

		mForceRefresh = forceRefresh;
		// TODO: Load from store.
		mOldResult = new StatusResult();
	}

	@Override
	public StatusResult loadInBackground() {
		Log.d(TAG, "sending request");
		StatusFetcher fetcher = new StatusFetcherImpl();
		final Status status = fetcher.fetchStatus();
		Log.d(TAG, "loadInBackground() finished with status " + status.toString());
		return new StatusResult(status, new Date());
	}

	@Override
	public void deliverResult(StatusResult data) {
		if (data.status == Status.ERROR) {
			showErrorNotification();
			super.deliverResult(mOldResult);
			return;
		}

		Log.d(TAG, "Delivering result.");
		mOldResult = data;
		super.deliverResult(data);
	}

	private void showErrorNotification() {
		Toast.makeText(getContext(), "Couldn't connect to the internet.",
				Toast.LENGTH_SHORT).show();
	}

	private boolean needsRefresh() {
		long diff = (new Date()).getTime() - mOldResult.updated.getTime();

		return mForceRefresh || diff >= CACHE_TIME
				|| mOldResult.status == Status.UNKNOWN;
	}

	@Override
	protected void onStartLoading() {
		super.onStartLoading();

		if (needsRefresh()) {
			Log.d(TAG, "Refresh required. Forcing load.");
			forceLoad();
		} else {
			Log.d(TAG, "No refresh needed. Delivering old result.");
			deliverResult(mOldResult);
		}
	}
}