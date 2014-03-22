package com.vkassin.sbornik22;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class RazdelListActivity extends Activity {

	private static final String TAG = "Sbornik.RazdelListActivity";

	private ListView list;
	private RazdelArrayAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.razdel_activity);

		TextView text = (TextView) findViewById(R.id.first_screen_title);
		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/GothaProReg.otf");
		text.setTypeface(tf);

		Common.app_ctx = getApplicationContext();
		Common.loadDatabase();

		list = (ListView) this.findViewById(R.id.RazdelList);
		adapter = new RazdelArrayAdapter(this, R.layout.razdel_item,
				Common.razdels);
		list.setAdapter(adapter);

		list.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				int lock = adapter.getItems().get(arg2).lock;
				if (lock != 0)
					return;
				Common.curRazdel = adapter.getItems().get(arg2).getId();
				Common.curRazdelName = adapter.getItems().get(arg2).name;
				// Common.secondListTitle = "БЭГ. " + Common.curRazdel + ". "
				// + adapter.getItems().get(arg2).name;
				Common.secondListTitle = "БЭГ / "
						+ adapter.getItems().get(arg2).name;
				Common.isSearch = false;
				Common.isFavourites = false;

				Intent i = new Intent(RazdelListActivity.this,
						TaskListActivity.class);
				startActivity(i);
			}
		});

	}

	public void goToInfo(View view) {
		// Do something in response to button click
	}

	public void goToFavourites(View view) {

		Common.isSearch = false;
		Common.isFavourites = true;
		Intent i = new Intent(RazdelListActivity.this, TaskListActivity.class);
		startActivity(i);
	}

	public void goToSearch(View view) {

		Intent i = new Intent(RazdelListActivity.this, SearchActivity.class);
		startActivity(i);
	}

	public void goToNetworks(View view) {
		
		String message = "БЭГ";
		Intent share = new Intent(Intent.ACTION_SEND);
		share.setType("text/plain");
		share.putExtra(Intent.EXTRA_TEXT, message);
		startActivity(Intent.createChooser(share, "Поделиться"));
			
	}
	
	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu items for use in the action bar
	// MenuInflater inflater = getMenuInflater();
	// inflater.inflate(R.menu.razdel_activity_actions, menu);
	// return true;//super.onCreateOptionsMenu(menu);
	// }
}
