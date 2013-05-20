package net.rdrei.android.simstatus.test;

import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricTestRunner;

/**
 * Use this runner instead of RobolectricTestRunner with @RunWith annotation.
 */
public class Runner extends RobolectricTestRunner {
    /**
     * Creates a runner to run {@code testClass}. Looks in your working directory for your AndroidManifest.xml file
     * and res directory.
     *
     * @param testClass the test class to be run
     * @throws org.junit.runners.model.InitializationError
     *          if junit says so
     */
    public Runner(Class<?> testClass) throws InitializationError {
        super(testClass);
    }
}
