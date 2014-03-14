package net.rdrei.android.simstatus.test;

import android.app.Activity;
import android.app.Application;
import android.view.ViewGroup;
import android.widget.TextView;

import net.rdrei.android.simstatus.ApplicationModule;
import net.rdrei.android.simstatus.R;
import net.rdrei.android.simstatus.ui.AdViewManager;
import net.rdrei.android.simstatus.ui.AdViewManagerFactory;
import net.rdrei.android.simstatus.ui.MainActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;

import dagger.Module;
import dagger.Provides;

import static org.junit.Assert.assertEquals;

@RunWith(TestRunner.class)
public class MainActivityTest {
	@Test
	public void testSomething() {
		final MainActivity activity = Robolectric.buildActivity(MainActivity.class).create().get();
		final String appName = ((TextView) activity.findViewById(R.id.header)).getText().toString();
		assertEquals(appName, "Is Sim City playable right now?");
	}

	public static class FakeAdViewManagerFactory extends AdViewManagerFactory {
		@Override
		public AdViewManager create(Activity activity) {
			return new AdViewManager() {
				@Override
				public void addToView(ViewGroup baseView) {
				}

				@Override
				public boolean addToViewIfRequired(ViewGroup baseView) {
					return false;
				}
			};
		}
	}

	@Module(overrides = true, library = true)
	public static class TestModule {
		@Provides
		public AdViewManagerFactory provideAdViewManagerFactory() {
			return new FakeAdViewManagerFactory();
		}
	}
}
