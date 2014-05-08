package com.vkassin.sbornik22;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
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

		Typeface tf = Typeface.createFromAsset(getAssets(),
		"fonts/GothaProReg.otf");
		TextView title = (TextView) this.findViewById(R.id.DetTaskListTitle01);
		title.setTypeface(tf);

	}

	public void goToFavourites(View view) {

		Common.isSearch = false;
		Common.isFavourites = true;
		Intent i = new Intent(SearchActivity.this, TaskListActivity.class);
		startActivity(i);
	}

	public void goToSearch(View view) {
		
	}
	
	public void goSearch(View view) {

		EditText et = (EditText) this.findViewById(R.id.topic);
		Common.secondListTitle = et.getText().toString();
		Common.isSearch = true;
		Common.isFavourites = false;

		Intent i = new Intent(SearchActivity.this, TaskListActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(i);
	}

	public void goToInfo(View view) {

		Intent i = new Intent(SearchActivity.this, RazdelListActivity.class);
		// i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);

		startActivity(i);
	}

	public void goBack(View view) {

		super.onBackPressed();
	}

	@Override
	public void onBackPressed() {

		Intent i = new Intent(this, TaskListActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(i);
	}

	public void goForward(View view) {

		boolean b = Common.setNextTask();
		Intent i;
		if (b) {

			i = new Intent(SearchActivity.this, DetailTaskActivity.class);
		} else {

			i = new Intent(SearchActivity.this, RazdelListActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		}
		startActivity(i);
	}
}
