package net.rdrei.android.simstatus.ui;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import net.rdrei.android.simstatus.ForApplication;
import net.rdrei.android.simstatus.SimStatusApplication;

import javax.inject.Singleton;

@Module(library = true)
public class AndroidModule {

    private final SimStatusApplication mApp;


    public AndroidModule(SimStatusApplication app) {
        mApp = app;
    }

    @Provides
    @Singleton
    @ForApplication
    Context provideApplicationContext() {
        return mApp;
    }

}