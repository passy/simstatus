package net.rdrei.android.simstatus.ui;

import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import net.rdrei.android.simstatus.R;
import net.rdrei.android.simstatus.SimStatusApplication;
import net.rdrei.android.simstatus.StatusFetchTaskLoader;
import net.rdrei.android.simstatus.StatusFetchTaskLoaderFactory;
import net.rdrei.android.simstatus.StatusFetcher;
import net.rdrei.android.simstatus.StatusResult;
import net.rdrei.android.simstatus.StatusResult.Status;
import net.rdrei.android.simstatus.StatusStore;

import java.util.Date;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends Activity implements
		LoaderCallbacks<StatusResult>, SwipeRefreshLayout.OnRefreshListener {

	private static final int LOADER = 0;
	private static final String TAG = "SCS:MainActivity";
	private static final String FORCE_REFRESH = "force_refresh";
	@InjectView(R.id.status)
	TextView mStatusText;
	@InjectView(R.id.status_loading)
	View mLoadingSpinner;
	@InjectView(R.id.updated_layout)
	View mUpdatedLayout;
	@InjectView(R.id.ad_layout)
	ViewGroup mAdLayout;
	@InjectView(R.id.updated)
	TextView mUpdatedText;
	@InjectView(R.id.swipe_view)
	SwipeRefreshLayout mSwipeView;
	@Inject
	StatusStore mStatusStore;
	@Inject
	AdViewManagerFactory mAdViewManagerFactory;
	@Inject
	StatusFetchTaskLoaderFactory mStatusFetchTaskLoaderFactory;
	@Inject
	StatusFetcher mStatusFetcher;
	private StatusResult mStatusResult;
	private boolean mLoading = false;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

		setContentView(R.layout.main);

		((SimStatusApplication) getApplication()).inject(this);
		ButterKnife.inject(this);

		mStatusResult = mStatusStore.loadStatus();
		getLoaderManager().initLoader(LOADER, null, this);

		mSwipeView.setOnRefreshListener(this);
		mSwipeView.setColorScheme(R.color.accent, R.color.dark, R.color.accent, R.color.dark);

		showAds();
	}

	private void showAds() {
		mAdViewManagerFactory.create(this).addToViewIfRequired(mAdLayout);
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		final MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);

		final MenuItem refreshItem = menu.findItem(R.id.menu_refresh);
		if (refreshItem != null) {
			refreshItem.setVisible(!mLoading);
		}

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_about:
				launchAboutActivity();
				return true;

			case R.id.menu_refresh:
				forceRefresh();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void launchAboutActivity() {
		final Intent intent = new Intent(this, AboutActivity.class);
		startActivity(intent);
	}

	private void setLoading(final boolean enabled) {
		mLoading = enabled;
		mSwipeView.setRefreshing(mLoading);
		invalidateOptionsMenu();
	}

	private void forceRefresh() {
		final Bundle bundle = new Bundle();
		bundle.putBoolean(FORCE_REFRESH, true);
		getLoaderManager().restartLoader(LOADER, bundle, this);
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

	private void displayStatus(final Status status) {
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

	private void displayUpdated(final Date date) {
		final CharSequence text = DateUtils.getRelativeTimeSpanString(
				date.getTime(), (new Date()).getTime(),
				DateUtils.MINUTE_IN_MILLIS);
		mUpdatedText.setText(text);
	}

	@Override
	public Loader<StatusResult> onCreateLoader(final int id, final Bundle args) {
		if (mLoading) {
			Log.d(TAG, "Still loading, not creating new loader.");
			return null;
		}

		final boolean forceRefresh;

		setLoading(true);

		forceRefresh = args != null && args.getBoolean(FORCE_REFRESH, false);

		Log.d(TAG, "Creating new StatusFetchTaskLoader with force flag "
				+ forceRefresh);
		final StatusFetchTaskLoader loader = mStatusFetchTaskLoaderFactory
				.create(this, mStatusFetcher, forceRefresh);
		loader.setOldResult(mStatusResult);

		return loader;
	}

	@Override
	protected void onStop() {
		super.onStop();

		mStatusStore.saveStatus(mStatusResult);
	}

	@Override
	public void onLoadFinished(final Loader<StatusResult> loader,
			final StatusResult statusResult) {
		Log.d(TAG, "New Status result receveid: " + statusResult.toString());

		setLoading(false);
		mStatusResult = statusResult;
		updateDisplay();
	}

	@Override
	public void onLoaderReset(final Loader<StatusResult> arg0) {
		Log.d(TAG, "onLoaderReset");
		setLoading(false);
	}

	@Override
	public void onRefresh() {
		forceRefresh();
	}
}
