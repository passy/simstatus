package net.rdrei.android.simstatus.test;

import net.rdrei.android.simstatus.R;
import net.rdrei.android.simstatus.ui.MainActivity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;

import static org.junit.Assert.assertEquals;

@RunWith(Runner.class)
public class MainActivityTest {

	@Test
	public void testSomething() {
		final MainActivity activity = Robolectric.buildActivity(MainActivity.class).create().get();
		final String appName = activity.getResources().getString(R.string.app_name);
		assertEquals(appName, "Sim City Status");
	}
}
