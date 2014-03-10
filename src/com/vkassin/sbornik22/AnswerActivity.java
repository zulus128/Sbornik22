package com.vkassin.sbornik22;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AnswerActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.answer_activity);
		
		TaskItem ti = Common.getCurTask();
		
		TextView title = (TextView) this.findViewById(R.id.PictureTaskTitle);
		title.setText(R.string.answer);
		
		ImageView imgMy1 = (ImageView) this.findViewById(R.id.imageMy);
		imgMy1.setVisibility((ti.my1 == 1)?View.VISIBLE:View.GONE);
		
		TextView idd = (TextView) this.findViewById(R.id.PictureTaskId);
		idd.setText("" + ti.getId());
		
		TextView descr = (TextView) this.findViewById(R.id.PictureTextView);
		descr.setText(ti.answer);

	}
	
	public void goToPic(View view) {

		Intent i = new Intent(AnswerActivity.this, PictureActivity.class);
		startActivity(i);
	}
	
	public void goBack(View view) {

		super.onBackPressed();
	}
	
//	public void goForward(View view) {
//
//		Common.setNextTask();
//		Intent i = new Intent(AnswerActivity.this, DetailTaskActivity.class);
////		i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//		startActivity(i);
//	}
	
	public void goForward(View view) {

		boolean b = Common.setNextTask();
		Intent i;
		if(b) {
			
			i = new Intent(AnswerActivity.this, DetailTaskActivity.class);
		}else {
			
			i = new Intent(AnswerActivity.this, RazdelListActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		}
		startActivity(i);
	}

}
