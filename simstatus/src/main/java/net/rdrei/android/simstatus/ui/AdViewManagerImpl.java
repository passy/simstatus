package net.rdrei.android.simstatus.ui;

import net.rdrei.android.simstatus.Constants;
import net.rdrei.android.simstatus.R;
import android.app.Activity;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.gu.option.Function;
import com.gu.option.Option;
import com.gu.option.UnitFunction;

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

	private Option<View> getAdView(final ViewGroup baseView) {
		try {
			return Option.some(mInflater.inflate(R.layout.adview, baseView, false));
		} catch (InflateException exc) {
			Crashlytics.logException(exc);
		}

		return Option.none();
	}

	private Option<AdView> setupView(final View view) {
		AdView adView;

		try {
			adView = (AdView) view;
		} catch (ClassCastException err) {
			adView = null;
			Crashlytics.logException(err);
		}

		if (adView != null) {
			final AdRequest adRequest = new AdRequest.Builder()
					.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
					.build();
			adView.loadAd(adRequest);
			return Option.some(adView);
		}

		return Option.none();
	}

	/**
	 * Add the AdView to the given baseView.
	 * 
	 * @param baseView
	 *            The layout element to add the view to.
	 */
	@Override
	public void addToView(final ViewGroup baseView) {
		final Option<View> adView = getAdView(baseView);
		adView.flatMap(new Function<View, Option<AdView>>() {
			@Override
			public Option<AdView> apply(View v) {
				return setupView(v);
			}
		}).foreach(new UnitFunction<AdView>() {
			@Override
			public void apply(AdView v) {
				baseView.addView(v);
			}
		});
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