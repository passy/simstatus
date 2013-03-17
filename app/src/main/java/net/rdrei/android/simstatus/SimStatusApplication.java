package net.rdrei.android.simstatus;

import android.app.Application;

public class SimStatusApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		Injector.setModule(new ApplicationModule(getApplicationContext()));
	}

}
