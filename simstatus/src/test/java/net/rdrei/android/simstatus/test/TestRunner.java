package net.rdrei.android.simstatus.test;

import org.junit.runners.model.InitializationError;
import org.robolectric.AndroidManifest;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.res.Fs;

// TODO: Verify that I actually need this.
public class TestRunner extends RobolectricTestRunner {
	public TestRunner(Class<?> testClass) throws InitializationError {
		super(testClass);
	}

	@Override
	protected AndroidManifest getAppManifest(Config config) {
		final String myAppPath = TestRunner.class.getProtectionDomain()
				.getCodeSource()
				.getLocation()
				.getPath();
		final String manifestPath = myAppPath + "../../../src/test/AndroidManifest.xml";
		final String resPath = myAppPath + "../../../src/main/res";
		final String assetPath = myAppPath + "../../../src/main/assets";
		return createAppManifest(Fs.fileFromPath(manifestPath), Fs.fileFromPath(resPath), Fs.fileFromPath(assetPath));
	}
}
