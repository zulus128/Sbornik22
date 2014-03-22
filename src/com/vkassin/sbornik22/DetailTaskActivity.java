package com.vkassin.sbornik22;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;


public class DetailTaskActivity extends Activity {

	private static final String TAG = "Sbornik.DetailTaskActivity";
	private TaskItem ti;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.detailtask_activity);

		ti = Common.getCurTask();

		TextView tv = (TextView) this.findViewById(R.id.DetailTaskTextView);
		tv.setText(ti.text);
		Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/GothaProReg.otf");
		tv.setTypeface(tf);
		
		TextView title = (TextView) this.findViewById(R.id.DetTaskListTitle01);
		title.setText("БЭГ / " + Common.curRazdelName + " / ЗАДАЧА № " + ti.getId()); 

//		ImageView imgMy1 = (ImageView) this.findViewById(R.id.imageMy);
//		imgMy1.setVisibility((ti.my1 == 1) ? View.VISIBLE : View.GONE);

		TextView idd = (TextView) this.findViewById(R.id.DetailNumTextView);
		idd.setText("" + ti.getId());
		TextView idname = (TextView) this.findViewById(R.id.DetailNameTextView);
		idname.setText(ti.name);
		Typeface tff = Typeface.createFromAsset(getAssets(), "fonts/GothaProMed.otf");
		idd.setTypeface(tff);
		idname.setTypeface(tff);
		
//		Log.i(TAG, "pic = " + ti.pic);
		
		if(ti.pic.equals("")) {
			
			ImageView imgDiv1 = (ImageView) this.findViewById(R.id.imageDiv1);
			imgDiv1.setVisibility(View.GONE);
			ImageView imgDiv2 = (ImageView) this.findViewById(R.id.imageDiv2);
			imgDiv2.setVisibility(View.GONE);
			ImageView imgPic = (ImageView) this.findViewById(R.id.imagePicture);
			imgPic.setVisibility(View.GONE);
			TextView ptext = (TextView) this.findViewById(R.id.DetailTaskPicTextView);
			ptext.setVisibility(View.GONE);
			
		} else {
			
			String uri = "drawable/" + ti.pic;
			int imageResource = getResources().getIdentifier(uri, null, getPackageName());
			ImageView imgPic = (ImageView) this.findViewById(R.id.imagePicture);
			imgPic.setImageResource(imageResource);
			TextView ptext = (TextView) this.findViewById(R.id.DetailTaskPicTextView);
			ptext.setText(ti.picsign);
			Typeface tf1 = Typeface.createFromAsset(getAssets(), "fonts/GothaProReg.otf");
			ptext.setTypeface(tf1);
			
		}
		
		setFavButton();
	}

	private void setFavButton() {

		ImageButton myfav = (ImageButton)this.findViewById(R.id.detail_button_my);
        String urip = "drawable/dicon_11";
        String urim = "drawable/dicon_12";
        boolean ismy = Common.isMy(ti.getId());
		int imageResource = getResources().getIdentifier(ismy?urim:urip, null, getPackageName());
        myfav.setBackgroundResource(imageResource);

	}
	
	public void goMy(View view) {
		
		Common.switchMy(ti.getId());
		this.setFavButton();
	}
	
	public void goToPic(View view) {

		Intent i = new Intent(DetailTaskActivity.this, PictureActivity.class);
		startActivity(i);
	}

	public void goAnswer(View view) {

		Common.setViewed(ti.getId());
		Intent i = new Intent(DetailTaskActivity.this, AnswerActivity.class);
		startActivity(i);
	}

	public void goBack(View view) {

		super.onBackPressed();
	}

	public void goForward(View view) {

		Common.curTask = ti.getId();
		boolean b = Common.setNextTask();
		Intent i;
		if(b) {
			
			i = new Intent(DetailTaskActivity.this, DetailTaskActivity.class);
		}else {
			
			i = new Intent(DetailTaskActivity.this, RazdelListActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

		}
	
		startActivity(i);
	}

	public void goToFavourites(View view) {
		
		Common.isSearch = false;
		Common.isFavourites = true;
		Intent i = new Intent(DetailTaskActivity.this, TaskListActivity.class);
		startActivity(i);
	}

	public void goToSearch(View view) {

		Intent i = new Intent(DetailTaskActivity.this, SearchActivity.class);
		startActivity(i);
	}

	public void goToNetworks(View view) {
		// Do something in response to button click
	}
	
	public void goToInfo(View view) {
		
		Intent i = new Intent(DetailTaskActivity.this, RazdelListActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(i);
	}
}
