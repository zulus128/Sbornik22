package com.vkassin.sbornik22;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PictureActivity extends Activity  {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.picture_activity);
		
		TaskItem ti = Common.getCurTask();
		
		TextView title = (TextView) this.findViewById(R.id.PictureTaskTitle);
		title.setText(ti.name);
		
		ImageView imgMy1 = (ImageView) this.findViewById(R.id.imageMy);
		imgMy1.setVisibility((ti.my1 == 1)?View.VISIBLE:View.GONE);
		
		TextView idd = (TextView) this.findViewById(R.id.PictureTaskId);
		idd.setText("" + ti.getId());
		
		ImageView img = (ImageView) this.findViewById(R.id.imagePicture);
		String uri = "drawable/" + ti.pic;
		int imageResource = getResources().getIdentifier(uri, null, getPackageName());
		img.setImageResource(imageResource);
		
		TextView descr = (TextView) this.findViewById(R.id.PictureTextView);
		descr.setText(ti.picsign);

	}
}
