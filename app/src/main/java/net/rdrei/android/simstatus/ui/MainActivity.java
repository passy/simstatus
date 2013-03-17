package net.rdrei.android.simstatus.ui;

import java.util.Date;

import javax.inject.Inject;

import net.rdrei.android.simstatus.Injector;
import net.rdrei.android.simstatus.R;
import net.rdrei.android.simstatus.StatusFetchTaskLoader;
import net.rdrei.android.simstatus.StatusResult;
import net.rdrei.android.simstatus.StatusResult.Status;
import net.rdrei.android.simstatus.StatusStore;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import butterknife.InjectView;
import butterknife.Views;

public class MainActivity extends Activity implements
		LoaderCallbacks<StatusResult> {

	private static final int LOADER = 0;

	private static final String TAG = "SCS:MainActivity";

	private static final String FORCE_REFRESH = "force_refresh";

	@InjectView(R.id.status)
	TextView mStatusText;

	@InjectView(R.id.status_loading)
	View mLoadingSpinner;

	@InjectView(R.id.updated_layout)
	View mUpdatedLayout;

	@InjectView(R.id.updated)
	TextView mUpdatedText;

	@Inject
	StatusStore mStatusStore;

	private StatusResult mStatusResult;
	private boolean mLoading = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Injector.inject(this);
		Views.inject(this);
		
		mStatusResult = mStatusStore.loadStatus();
		
		getLoaderManager().initLoader(LOADER, null, this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_about:
			Log.w(TAG, "Not implemented.");
			return true;

		case R.id.menu_refresh:
			forceRefresh();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void forceRefresh() {
		mStatusResult.status = Status.UNKNOWN;
		updateDisplay();

		Bundle bundle = new Bundle();
		bundle.putBoolean(FORCE_REFRESH, true);
		getLoaderManager().restartLoader(LOADER, null, this);
	}

	private void updateDisplay() {
		if (mStatusResult.status == Status.UNKNOWN) {
			mLoadingSpinner.setVisibility(View.VISIBLE);

			mStatusText.setVisibility(View.GONE);
			mUpdatedLayout.setVisibility(View.GONE);
		} else {
			mLoadingSpinner.setVisibility(View.GONE);
			displayStatus(mStatusResult.status);
			displayUpdated(mStatusResult.updated);

			mStatusText.setVisibility(View.VISIBLE);
			mUpdatedLayout.setVisibility(View.VISIBLE);
		}
	}

	private void displayStatus(Status status) {
		final String statusDisplay;

		switch (status) {
		case MAYBE:
			statusDisplay = "maybe";
			break;
		case NO:
			statusDisplay = "no";
			break;
		case YES:
			statusDisplay = "yes";
			break;
		default:
			statusDisplay = "unknown";
			break;
		}

		mStatusText.setText(statusDisplay);
	}

	private void displayUpdated(Date date) {
		final CharSequence text = DateUtils.getRelativeTimeSpanString(
				date.getTime(), (new Date()).getTime(),
				DateUtils.MINUTE_IN_MILLIS);
		mUpdatedText.setText(text);
	}

	@Override
	public Loader<StatusResult> onCreateLoader(int id, Bundle args) {
		if (mLoading) {
			Log.d(TAG, "Still loading, not creating new loader.");
			return null;
		}
		
		final boolean forceRefresh;

		Log.d(TAG, "Creating new StatusFetchTaskLoader.");
		mLoading = true;

		if (args != null) {
			forceRefresh = args.getBoolean(FORCE_REFRESH, false);
		} else {
			forceRefresh = false;
		}
		
		StatusFetchTaskLoader loader = new StatusFetchTaskLoader(this, forceRefresh);
		loader.setOldResult(mStatusResult);
		
		return loader;
	}

	@Override
	protected void onStop() {
		super.onStop();
		
		mStatusStore.saveStatus(mStatusResult);
	}

	@Override
	public void onLoadFinished(Loader<StatusResult> loader,
			StatusResult statusResult) {
		Log.d(TAG, "New Status result receveid: " + statusResult.toString());

		mLoading = false;
		mStatusResult = statusResult;
		updateDisplay();
	}

	@Override
	public void onLoaderReset(Loader<StatusResult> arg0) {
		Log.d(TAG, "onLoaderReset");
		mLoading = false;
	}
}
