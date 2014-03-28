package net.rdrei.android.simstatus;

import java.util.Date;

import net.rdrei.android.simstatus.StatusResult.Status;
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
	private static final String STATUS_URL = "https://sc5status.herokuapp.com/status";
	private StatusResult mOldResult;

	private final boolean mForceRefresh;
	private final StatusFetcher mStatusFetcher;

	public StatusFetchTaskLoader(final Context context,
			final StatusFetcher fetcher) {
		this(context, fetcher, false);
	}

	public StatusFetchTaskLoader(final Context context,
			final StatusFetcher fetcher, final boolean forceRefresh) {
		super(context);
		Log.d(TAG, "Creating new loader task with force=" + forceRefresh);

		mForceRefresh = forceRefresh;
		mStatusFetcher = fetcher;
		// TODO: Load from store.
		mOldResult = new StatusResult();
	}

	@Override
	public StatusResult loadInBackground() {
		Log.d(TAG, "sending request");
		final Status status = mStatusFetcher.fetchStatus(STATUS_URL);
		Log.d(TAG, "loadInBackground() finished with status " + status.toString());
		return new StatusResult(status, new Date());
	}


	@Override
	public void deliverResult(final StatusResult data) {
		if (data.status == Status.ERROR) {
			showErrorNotification();
			super.deliverResult(mOldResult);
			return;
		}

		Log.d(TAG, "Delivering result.");
		mOldResult = data;
		super.deliverResult(data);
	}

	public void setOldResult(final StatusResult oldResult) {
		mOldResult = oldResult;
	}

	private void showErrorNotification() {
		Toast.makeText(getContext(), "Couldn't connect to the internet.",
				Toast.LENGTH_SHORT).show();
	}

	private boolean needsRefresh() {
		final long diff = (new Date()).getTime() - mOldResult.updated.getTime();

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