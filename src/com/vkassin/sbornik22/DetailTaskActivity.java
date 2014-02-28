package com.vkassin.sbornik22;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class DetailTaskActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detailtask_activity);
		
		TextView tv = (TextView) this.findViewById(R.id.DetailTaskTextView);
		TaskItem ti = Common.getCurTask();
		tv.setText(ti.text);
		
//		getActionBar().setTitle("Hello world App");

//		Button button = (Button) findViewById(R.id.button_pic);
//		button.setOnClickListener(new View.OnClickListener() {
//		    public void onClick(View v) {
//		        // Do something in response to button click
//		    }
//		});
	}
	
	public void goToPic(View view) {
	    // Do something in response to button click
	}
}
