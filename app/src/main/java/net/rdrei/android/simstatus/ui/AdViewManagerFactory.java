package net.rdrei.android.simstatus.ui;

import android.app.Activity;

public class AdViewManagerFactory {
	public AdViewManager create(Activity activity) {
		return new AdViewManager(activity);
	}
}
