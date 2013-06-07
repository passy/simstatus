package net.rdrei.android.simstatus;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.StrictMode;
import dagger.ObjectGraph;
import net.rdrei.android.simstatus.ui.AndroidModule;

import java.util.Arrays;

import java.util.List;

public class SimStatusApplication extends Application {
    private ObjectGraph mObjectGraph;

    protected List<Object> getModules() {
        return Arrays.<Object>asList(
                new AndroidModule(this),
                new ApplicationModule()
        );
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mObjectGraph = ObjectGraph.create(getModules().toArray());

        if (isDebuggable()) {
            enableStrictMode();
        } else {
            // BugSenseHandler.initAndStartSession(this, getString(R.string.bugsense_token));
        }
    }

    public ObjectGraph getObjectGraph() {
        return mObjectGraph;
    }

    public void inject(Object object) {
        mObjectGraph.inject(object);
    }

    public boolean isDebuggable() {
        final int applicationFlags = this.getApplicationInfo().flags;
        return (applicationFlags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void enableStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                        // Only on 11+
                .detectLeakedClosableObjects().penaltyLog().build());
    }

}
