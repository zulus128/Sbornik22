package com.vkassin.sbornik22;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class DetailTaskActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.detailtask_activity);

		TaskItem ti = Common.getCurTask();

		TextView tv = (TextView) this.findViewById(R.id.DetailTaskTextView);
		tv.setText(ti.text);

		TextView title = (TextView) this.findViewById(R.id.DetailTaskTitle);
		title.setText(ti.name);

		ImageView imgMy1 = (ImageView) this.findViewById(R.id.imageMy);
		imgMy1.setVisibility((ti.my1 == 1) ? View.VISIBLE : View.GONE);

		TextView idd = (TextView) this.findViewById(R.id.DetailTaskId);
		idd.setText("" + ti.getId());

	}

	public void goToPic(View view) {

		Intent i = new Intent(DetailTaskActivity.this, PictureActivity.class);
		startActivity(i);
	}

	public void goAnswer(View view) {

		Intent i = new Intent(DetailTaskActivity.this, AnswerActivity.class);
		startActivity(i);
	}

	public void goBack(View view) {

		super.onBackPressed();
	}

	public void goForward(View view) {

		Common.setNextTask();
		Intent i = new Intent(DetailTaskActivity.this, DetailTaskActivity.class);
//		i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(i);
	}

}
