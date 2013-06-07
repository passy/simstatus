package net.rdrei.android.simstatus.test;

import dagger.ObjectGraph;
import net.rdrei.android.simstatus.SimStatusApplication;
import net.rdrei.android.simstatus.ui.AndroidModule;

import java.util.Arrays;
import java.util.List;

public class TestApplication extends SimStatusApplication {
    protected List<Object> getModules() {
        return Arrays.<Object>asList(
                new AndroidModule(this),
                new TestModule()
        );
    }
}
