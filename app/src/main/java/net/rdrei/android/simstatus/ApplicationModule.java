package net.rdrei.android.simstatus;

import javax.inject.Singleton;

import net.rdrei.android.simstatus.ui.MainActivity;
import android.content.Context;
import android.content.SharedPreferences;
import dagger.Module;
import dagger.Provides;

@Module(entryPoints = MainActivity.class)
public class ApplicationModule {
	private Context mContext;
	private static final String SHARED_PREFERENCES = "simstatus";

	public ApplicationModule(Context context) {
		mContext = context;
	}

	@Provides
	Context provideContext() {
		return mContext;
	}

	@Provides
	@Singleton
	StatusStore provideStatusStore(SharedPreferences preferences) {
		return new StatusStoreImpl(preferences);
	}

	@Provides
	StatusFetcher provideStatusFetcher() {
		return new StatusFetcherImpl();
	}

	@Provides
	SharedPreferences provideSharedPreferences() {
		return mContext.getSharedPreferences(SHARED_PREFERENCES,
				Context.MODE_PRIVATE);
	}
}
