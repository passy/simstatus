package net.rdrei.android.simstatus.ui;

import java.util.Date;

import javax.inject.Inject;

import net.rdrei.android.simstatus.ApplicationModule;
import net.rdrei.android.simstatus.R;
import net.rdrei.android.simstatus.StatusFetchTaskLoader;
import net.rdrei.android.simstatus.StatusResult;
import net.rdrei.android.simstatus.StatusStore;
import net.rdrei.android.simstatus.StatusStore.Status;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import butterknife.InjectView;
import butterknife.Views;

public class MainActivity extends Activity implements
		LoaderCallbacks<StatusResult> {

	private static final String TAG = "SCS:MainActivity";

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Don't work with potential null-pointers.
		mStatusResult = new StatusResult();

		Views.inject(this);
		ApplicationModule.getGraph().inject(this);

		getLoaderManager().initLoader(0, null, this);
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
		final java.text.DateFormat timeFormat = DateFormat.getTimeFormat(this);
		final java.text.DateFormat dateFormat = DateFormat.getDateFormat(this);
		final String text = String.format("%s %s", dateFormat.format(date),
				timeFormat.format(date));
		mUpdatedText.setText(text);
	}

	@Override
	public Loader<StatusResult> onCreateLoader(int id, Bundle args) {
		// Only one loader, so don't care for id / bundle
		Log.d(TAG, "Creating new StatusFetchTaskLoader.");
		StatusFetchTaskLoader loader = new StatusFetchTaskLoader(this);
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<StatusResult> loader,
			StatusResult statusResult) {
		Log.d(TAG, "New Status result receveid.");
		mStatusResult = statusResult;
		updateDisplay();
	}

	@Override
	public void onLoaderReset(Loader<StatusResult> arg0) {
		Log.d(TAG, "onLoaderReset");
	}
}
