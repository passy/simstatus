package net.rdrei.android.simstatus.test;

import dagger.Module;
import dagger.Provides;
import net.rdrei.android.simstatus.ApplicationModule;
import net.rdrei.android.simstatus.ui.AdViewManagerFactory;

@Module(includes = ApplicationModule.class, overrides = true)
public class TestModule {
    @Provides
    public AdViewManagerFactory provideAdViewManagerFactory() {
        return new MainActivityTest.FakeAdViewManagerFactory();
    }
}
