package net.rdrei.android.simstatus.ui;

import static android.widget.Toast.LENGTH_SHORT;
import net.rdrei.android.simstatus.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.InjectView;
import butterknife.Views;

public class MainActivity extends Activity {
	@InjectView(R.id.title)
	TextView title;
	@InjectView(R.id.subtitle)
	TextView subtitle;
	@InjectView(R.id.hello)
	Button hello;
	@InjectView(R.id.list_of_things)
	ListView listOfThings;
	@InjectView(R.id.footer)
	TextView footer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Views.inject(this);

		// Contrived code to use the "injected" views.
		title.setText("Butter Knife");
		subtitle.setText("View \"injection\" for Android.");
		footer.setText("by Jake Wharton");
		hello.setText("Say Hello");
		hello.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(MainActivity.this, "Hello, views!", LENGTH_SHORT)
						.show();
			}
		});
		listOfThings.setAdapter(new SimpleAdapter(this, null, 0, null, null));
	}
}
