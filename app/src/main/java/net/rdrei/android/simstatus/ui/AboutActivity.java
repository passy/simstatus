package net.rdrei.android.simstatus.ui;

import net.rdrei.android.simstatus.R;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Displays the about screen in an HTML view.
 * 
 * @author pascal
 * 
 */
public class AboutActivity extends Activity {

	private WebView mWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().hide();
		
		setContentView(R.layout.about);
		mWebView = (WebView) findViewById(R.id.about_webview);
		
		mWebView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return false;
			}

		});
		mWebView.loadUrl("file:///android_asset/about.html");
	}
}
