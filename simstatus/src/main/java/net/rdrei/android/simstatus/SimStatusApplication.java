package net.rdrei.android.simstatus;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.StrictMode;

import com.bugsense.trace.BugSenseHandler;

public class SimStatusApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		Injector.setModule(new ApplicationModule(getApplicationContext()));

		if (isDebuggable()) {
			enableStrictMode();
		} else {
			BugSenseHandler.initAndStartSession(this,
					getString(R.string.bugsense_token));
		}
	}

	public boolean isDebuggable() {
		final int applicationFlags = this.getApplicationInfo().flags;
		return (applicationFlags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void enableStrictMode() {
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectAll().penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects()
				// Only on 11+
				.detectLeakedClosableObjects().penaltyLog().build());
	}

}
