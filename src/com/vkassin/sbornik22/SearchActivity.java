package com.vkassin.sbornik22;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_activity);
		
	}
	
	public void goSearch(View view) {

		EditText et = (EditText) this.findViewById(R.id.topic);
		Common.secondListTitle = et.getText().toString(); 
		Common.isSearch = true;
		Intent i = new Intent(SearchActivity.this, TaskListActivity.class);
		startActivity(i);
	}
	
	public void goToOglav(View view) {
		
		Intent i = new Intent(SearchActivity.this, RazdelListActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(i);
	}
	
	public void goBack(View view) {

		super.onBackPressed();
	}
	
	public void goForward(View view) {

		Common.setNextTask();
		Intent i = new Intent(SearchActivity.this, DetailTaskActivity.class);
//		i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(i);
	}
}
