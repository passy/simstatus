package net.rdrei.android.simstatus;

import net.rdrei.android.simstatus.SimStatusApplication;
import net.rdrei.android.simstatus.test.MainActivityTest;

import java.util.ArrayList;
import java.util.List;

public class TestApplication extends SimStatusApplication {
	@Override
	protected void setupCrashlyticsHandler() {
		// nop
	}

	@Override
	protected List<Object> getModules() {
		final ArrayList<Object> modules = new ArrayList<Object>(super.getModules());
		modules.add(new MainActivityTest.TestModule());

		return modules;
	}
}
