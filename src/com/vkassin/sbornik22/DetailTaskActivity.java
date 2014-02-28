package com.vkassin.sbornik22;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

	}
	
}
