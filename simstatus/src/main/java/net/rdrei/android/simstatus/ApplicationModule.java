package net.rdrei.android.simstatus;

import android.content.Context;
import android.content.SharedPreferences;
import dagger.Module;
import dagger.Provides;
import net.rdrei.android.simstatus.ui.AdViewManagerFactory;
import net.rdrei.android.simstatus.ui.MainActivity;

import javax.inject.Singleton;

@Module(
        injects = MainActivity.class,
        complete = false
)
public class ApplicationModule {
    private static final String SHARED_PREFERENCES = "simstatus";

    @Provides
    @Singleton
    public StatusStore provideStatusStore(SharedPreferences preferences) {
        return new StatusStoreImpl(preferences);
    }

    @Provides
    public StatusFetcher provideStatusFetcher() {
        return new StatusFetcherImpl();
    }

    @Provides
    public SharedPreferences provideSharedPreferences(@ForApplication Context context) {
        return context.getSharedPreferences(SHARED_PREFERENCES,
                Context.MODE_PRIVATE);
    }

    @Provides
    public AdViewManagerFactory provideAdViewManagerFactory() {
        return new AdViewManagerFactory();
    }
}
