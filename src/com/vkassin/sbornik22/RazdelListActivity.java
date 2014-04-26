package com.vkassin.sbornik22;

import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class RazdelListActivity extends Activity {

	private static final String TAG = "Sbornik.RazdelListActivity";

	private ListView list;
	private RazdelArrayAdapter adapter;

//	@Override
//	protected void onResume() {
//	
//		super.onResume();
//		taskInfo();
//		
//	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		// try {
		// Thread.sleep(2000);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		setContentView(R.layout.razdel_activity);

//		if (getIntent().getBooleanExtra("Exit me", false)) {
//			finish();
//			return; // add this to prevent from doing unnecessary stuffs
//		}

		TextView text = (TextView) findViewById(R.id.first_screen_title);
		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/GothaProReg.otf");
		text.setTypeface(tf);

//		Common.app_ctx = getApplicationContext();
//		Common.loadDatabase();

//		ImageView okno = (ImageView) this.findViewById(R.id.imageOkno);
//		okno.setOnClickListener(new OnClickListener() {
//
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//
//				v.setVisibility(View.GONE);
//			}
//
//		});

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
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

				startActivity(i);
			}
		});

	}

	
//	public void taskInfo() {
//		
//		ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
//
//		List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(10);
//		for (RunningTaskInfo task : list) {
//			if (task.baseActivity.flattenToShortString().startsWith(
//					"com.vkassin")) {
//				Log.w(TAG, "------------------");
//				Log.w(TAG, "Count: " + task.numActivities);
//				Log.w(TAG,
//						"Root: " + task.baseActivity.flattenToShortString());
//				Log.w(TAG,
//						"Top: " + task.topActivity.flattenToShortString());
//			}
//		}
//	}

	@Override
	public void onBackPressed() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.dialog_message1).setTitle(
				R.string.dialog_title1);
		builder.setPositiveButton(R.string.case3,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

//						RazdelListActivity.super.onBackPressed();
						
						Intent intent = new Intent(RazdelListActivity.this, StartActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						intent.putExtra("Exit me", true);
						startActivity(intent);
						finish();
					}
				});
		builder.setNegativeButton(R.string.case4,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

						Intent browserIntent = new Intent(
								Intent.ACTION_VIEW,
								Uri.parse(Common.GP_URL));
						startActivity(browserIntent);
					}
				});
		AlertDialog dialog = builder.create();
		dialog.show();
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
