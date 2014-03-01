package com.vkassin.sbornik22;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class RazdelListActivity extends Activity {

	private ListView list;
	private RazdelArrayAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
//		requestWindowFeature(Window.FEATURE_NO_TITLE); 
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
		setContentView(R.layout.razdel_activity);
		
		Common.app_ctx = getApplicationContext();
		Common.loadDatabase();
		
		list = (ListView) this.findViewById(R.id.RazdelList);
		adapter = new RazdelArrayAdapter(this, R.layout.razdel_item, Common.razdels);
		list.setAdapter(adapter);

		list.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				
				Common.curRazdel = adapter.getItems().get(arg2).getId();
				Common.secondListTitle = "БЭГ. " + Common.curRazdel + ". " + adapter.getItems().get(arg2).name;
				Intent i = new Intent(RazdelListActivity.this, TaskListActivity.class);
				startActivity(i);
			}
		});
	}

	public void goToInfo(View view) {
	    // Do something in response to button click
	}
	public void goToFavourites(View view) {
	    // Do something in response to button click
	}
	public void goToSearch(View view) {
	    // Do something in response to button click
	}
	public void goToNetworks(View view) {
	    // Do something in response to button click
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//	    // Inflate the menu items for use in the action bar
//	    MenuInflater inflater = getMenuInflater();
//	    inflater.inflate(R.menu.razdel_activity_actions, menu);
//	    return true;//super.onCreateOptionsMenu(menu);
//	}
}
