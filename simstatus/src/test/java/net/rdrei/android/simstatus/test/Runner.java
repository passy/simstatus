package net.rdrei.android.simstatus.test;

import org.junit.runners.model.InitializationError;
import org.robolectric.AndroidManifest;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.res.FileFsFile;
import org.robolectric.res.Fs;
import org.robolectric.res.FsFile;

import java.io.File;

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

    @Override
    protected AndroidManifest createAppManifest(FsFile manifestFile) {
        if (!manifestFile.exists()) {
            System.out.print("WARNING: No manifest file found at " + manifestFile.getPath() + ".");
            System.out.println("Falling back to the Android OS resources only.");
            System.out.println("To remove this warning, annotate your test class with @Config(manifest=Config.NONE).");
            return null;
        }

        // This is the working directory we specify in the gradle config.
        FsFile appBaseDir = Fs.currentDirectory();
        return new AndroidManifest(manifestFile, appBaseDir.join("res"), appBaseDir.join("assets"));
    }
}
