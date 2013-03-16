package net.rdrei.android.simstatus.ui;

import javax.inject.Inject;

import net.rdrei.android.simstatus.ApplicationModule;
import net.rdrei.android.simstatus.R;
import net.rdrei.android.simstatus.StatusStore;
import net.rdrei.android.simstatus.StatusStore.Status;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import butterknife.InjectView;
import butterknife.Views;

public class MainActivity extends Activity {

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		Views.inject(this);
		ApplicationModule.getGraph().inject(this);
		
		displayStatus(mStatusStore.getLastStatus());
		mStatusText.setVisibility(View.VISIBLE);
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
}
