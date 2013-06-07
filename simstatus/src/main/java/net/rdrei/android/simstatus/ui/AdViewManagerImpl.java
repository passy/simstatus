package net.rdrei.android.simstatus.ui;

import net.rdrei.android.simstatus.Constants;
import net.rdrei.android.simstatus.R;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Loads and injects an AdView if required into an existing layout.
 * 
 * @author pascal
 */
public class AdViewManagerImpl implements AdViewManager {
	private LayoutInflater mInflater;

	/**
	 * Create a new {@link AdViewManagerImpl} from the given Activity context.
	 * @param activity
	 */
	public AdViewManagerImpl(final Activity activity) {
		mInflater = activity.getLayoutInflater();
	}

	private View getAdView() {
		return mInflater.inflate(R.layout.adview, null, false);
	}

	/**
	 * Add the AdView to the given baseView.
	 * 
	 * @param baseView
	 *            The layout element to add the view to.
	 */
	@Override
    public void addToView(final ViewGroup baseView) {
		final View adView = getAdView();
		baseView.addView(adView);
	}

	/**
	 * Add to the given baseView, but only if the application is non-adfree.
	 * 
	 * @param baseView
	 * @return True if added to layout, false if not.
	 */
	@Override
    public boolean addToViewIfRequired(final ViewGroup baseView) {
		final boolean showAds = !Constants.ADFREE;
		if (showAds) {
			addToView(baseView);
		}

		return showAds;
	}
}