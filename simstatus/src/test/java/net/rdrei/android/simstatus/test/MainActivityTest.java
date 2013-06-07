package net.rdrei.android.simstatus.test;

import android.app.Activity;
import android.view.ViewGroup;
import dagger.Module;
import dagger.ObjectGraph;
import dagger.Provides;
import net.rdrei.android.simstatus.Injector;
import net.rdrei.android.simstatus.R;
import net.rdrei.android.simstatus.ui.AdViewManager;
import net.rdrei.android.simstatus.ui.AdViewManagerFactory;
import net.rdrei.android.simstatus.ui.MainActivity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;

import java.lang.Override;

import static org.junit.Assert.assertEquals;

@RunWith(Runner.class)
public class MainActivityTest {

    @Before
    public void setUpInjection() {
        ObjectGraph graph = ObjectGraph.create(new TestModule());
        Injector.setModule(graph);
    }

    @Test
    public void testSomething() {
        final MainActivity activity = Robolectric.buildActivity(MainActivity.class).create().get();
        final String appName = activity.getResources().getString(R.string.app_name);
        assertEquals(appName, "Sim City Status");
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

    @Module(injects = MainActivityTest.class)
    public static class TestModule {
        @Provides
        private AdViewManagerFactory provideAdViewManagerFactory() {
            return new FakeAdViewManagerFactory();
        }
    }
}
