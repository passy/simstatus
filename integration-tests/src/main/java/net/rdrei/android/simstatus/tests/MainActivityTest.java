package net.rdrei.android.simstatus.tests;

import net.rdrei.android.simstatus.ui.MainActivity;
import android.test.ActivityInstrumentationTestCase2;

public class MainActivityTest extends
		ActivityInstrumentationTestCase2<MainActivity> {

	public MainActivityTest() {
		super(MainActivity.class);
	}

	/**
	 * Verify activity exists
	 */
	public void testActivityExists() {
		assertNotNull(getActivity());
	}
}
