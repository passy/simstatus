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
    private StatusStore provideStatusStore(SharedPreferences preferences) {
        return new StatusStoreImpl(preferences);
    }

    @Provides
    private StatusFetcher provideStatusFetcher() {
        return new StatusFetcherImpl();
    }

    @Provides
    private SharedPreferences provideSharedPreferences(@ForApplication Context context) {
        return context.getSharedPreferences(SHARED_PREFERENCES,
                Context.MODE_PRIVATE);
    }

    @Provides
    private AdViewManagerFactory provideAdViewManagerFactory() {
        return new AdViewManagerFactory();
    }
}
