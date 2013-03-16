package net.rdrei.android.simstatus;

import javax.inject.Singleton;

import net.rdrei.android.simstatus.ui.MainActivity;

import dagger.Module;
import dagger.ObjectGraph;
import dagger.Provides;

@Module(
		entryPoints = MainActivity.class
)
public class ApplicationModule {
	
	public static ObjectGraph getGraph() {
		return ObjectGraph.create(new ApplicationModule());
	}
	
	@Provides @Singleton StatusStore provideStatusStore() {
		return new StatusStoreImpl();
	}
}
