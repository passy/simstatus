package net.rdrei.android.simstatus.tests;

import static org.junit.Assert.assertEquals;
import net.rdrei.android.simstatus.R;
import net.rdrei.android.simstatus.ui.MainActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {

	@Test
	public void testSomething() {
		final MainActivity activity = new MainActivity();
		final String appName = activity.getResources().getString(
				R.string.app_name);
		assertEquals(appName, "Sim City Status");
	}

}
