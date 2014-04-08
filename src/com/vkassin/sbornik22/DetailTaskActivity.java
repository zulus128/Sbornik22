package com.vkassin.sbornik22;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ScrollView;
import android.widget.TextView;

public class DetailTaskActivity extends Activity {

	private static final String TAG = "Sbornik.DetailTaskActivity";
	private TaskItem ti;

	// step 1: add some instance
	private float mScale = 1f;
	private ScaleGestureDetector mScaleDetector;
	GestureDetector gestureDetector;

	// step 4: add private class GestureListener

	private class GestureListener extends
			GestureDetector.SimpleOnGestureListener {
		@Override
		public boolean onDown(MotionEvent e) {
			return true;
		}

		// event when double tap occurs
		@Override
		public boolean onDoubleTap(MotionEvent e) {
			// double tap fired.

			ScaleAnimation scaleAnimation = new ScaleAnimation(1, 1, 1, 1, 0, 0);
			scaleAnimation.setDuration(0);
			scaleAnimation.setFillAfter(true);
			// ScrollView layout =(ScrollView) findViewById(R.id.DetailScroll);
			// ImageView layout =(ImageView) findViewById(R.id.imagePicture);
			LinearLayout layout = (LinearLayout) findViewById(R.id.linDetail);
			layout.startAnimation(scaleAnimation);
			return true;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.detailtask_activity);

		ti = Common.getCurTask();

		TextView tv = (TextView) this.findViewById(R.id.DetailTaskTextView);
		tv.setText(ti.text);
		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/GothaProReg.otf");
		tv.setTypeface(tf);

		TextView title = (TextView) this.findViewById(R.id.DetTaskListTitle01);
		title.setText("БЭГ / " + Common.curRazdelName + " / ЗАДАЧА № "
				+ ti.getId());

		// ImageView imgMy1 = (ImageView) this.findViewById(R.id.imageMy);
		// imgMy1.setVisibility((ti.my1 == 1) ? View.VISIBLE : View.GONE);

		TextView idd = (TextView) this.findViewById(R.id.DetailNumTextView);
		idd.setText("" + ti.getId());
		TextView idname = (TextView) this.findViewById(R.id.DetailNameTextView);
		idname.setText(ti.name);
		Typeface tff = Typeface.createFromAsset(getAssets(),
				"fonts/GothaProMed.otf");
		idd.setTypeface(tff);
		idname.setTypeface(tff);

		// Log.i(TAG, "pic = " + ti.pic);

		if (ti.pic.equals("")) {

			ImageView imgDiv1 = (ImageView) this.findViewById(R.id.imageDiv1);
			imgDiv1.setVisibility(View.GONE);
			ImageView imgDiv2 = (ImageView) this.findViewById(R.id.imageDiv2);
			imgDiv2.setVisibility(View.GONE);
			ImageView imgPic = (ImageView) this.findViewById(R.id.imagePicture);
			imgPic.setVisibility(View.GONE);
			// ZoomableImageView imgPic = (ZoomableImageView)
			// this.findViewById(R.id.imagePicture);
			// imgPic.setVisibility(View.GONE);
			TextView ptext = (TextView) this
					.findViewById(R.id.DetailTaskPicTextView);
			ptext.setVisibility(View.GONE);

		} else {

			String uri = "drawable/" + ti.pic;
			int imageResource = getResources().getIdentifier(uri, null,
					getPackageName());
			ImageView imgPic = (ImageView) this.findViewById(R.id.imagePicture);
			imgPic.setImageResource(imageResource);

			// ZoomableImageView imgPic = (ZoomableImageView)
			// this.findViewById(R.id.imagePicture);
			// Bitmap bm = BitmapFactory.decodeResource(getResources(),
			// R.drawable.pic_1);
			// InputStream is =
			// getResources().openRawResource(R.drawable.pic_1);
			// Bitmap bm = BitmapFactory.decodeStream(is);
			// imgPic.setBitmap(bm);
			// imgPic.setDefaultScale(ZoomableImageView.DEFAULT_SCALE_ORIGINAL);

			// WebView imgPic = (WebView) this.findViewById(R.id.imagePicture);
			// imgPic.loadUrl("file:///android_res/drawable/pic_1.png");
			// imgPic.getSettings().setBuiltInZoomControls(true);
			// imgPic.getSettings().setSupportZoom(true);

			TextView ptext = (TextView) this
					.findViewById(R.id.DetailTaskPicTextView);
			ptext.setText(ti.picsign);
			Typeface tf1 = Typeface.createFromAsset(getAssets(),
					"fonts/GothaProReg.otf");
			ptext.setTypeface(tf1);

		}

		setFavButton();

		// step 2: create instance from GestureDetector(this step sholude be
		// place into onCreate())
		gestureDetector = new GestureDetector(this, new GestureListener());

		// animation for scalling
		mScaleDetector = new ScaleGestureDetector(this,
				new ScaleGestureDetector.SimpleOnScaleGestureListener() {
					@Override
					public boolean onScale(ScaleGestureDetector detector) {
						float scale = 1 - detector.getScaleFactor();

						float prevScale = mScale;
						mScale += scale;

						if (mScale < 0.5f) // Minimum scale condition:
							mScale = 0.5f;

						if (mScale > 1f) // Maximum scale condition:
							mScale = 1f;
						ScaleAnimation scaleAnimation = new ScaleAnimation(
								1f / prevScale, 1f / mScale, 1f / prevScale,
								1f / mScale, detector.getFocusX(), detector
										.getFocusY());
						scaleAnimation.setDuration(0);
						scaleAnimation.setFillAfter(true);
						// ScrollView layout =(ScrollView)
						// findViewById(R.id.DetailScroll);
						// ImageView layout =(ImageView)
						// findViewById(R.id.imagePicture);
						LinearLayout layout = (LinearLayout) findViewById(R.id.linDetail);
						layout.startAnimation(scaleAnimation);
						return true;
					}
				});

	}

	// step 3: override dispatchTouchEvent()
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		super.dispatchTouchEvent(event);
		mScaleDetector.onTouchEvent(event);
		gestureDetector.onTouchEvent(event);
		return gestureDetector.onTouchEvent(event);
	}

	private void refresh() {

		ImageView imgStat = (ImageView) this
				.findViewById(R.id.detailImageStatus);
		String uri = "drawable/";
		if (Common.isMy(ti.getId())) {

			uri += "dicon_08";

		} else if (Common.isViewed(ti.getId())) {

			uri += "dicon_06";

		}
		int imageResource = getResources().getIdentifier(uri, null,
				getPackageName());
		imgStat.setImageResource(imageResource);

	}

	@Override
	protected void onResume() {

		super.onResume();

		// Log.i(TAG, "--onResume");
		refresh();

		Common.curTask = ti.getId();
	};

	private void setFavButton() {

		ImageButton myfav = (ImageButton) this
				.findViewById(R.id.detail_button_my);
		String urip = "drawable/dicon_11";
		String urim = "drawable/dicon_12";
		boolean ismy = Common.isMy(ti.getId());
		int imageResource = getResources().getIdentifier(ismy ? urim : urip,
				null, getPackageName());
		myfav.setBackgroundResource(imageResource);
		refresh();
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
		if (b) {

			Intent i = new Intent(DetailTaskActivity.this, DetailTaskActivity.class);
			startActivity(i);
			
		} else {

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(R.string.dialog_message).setTitle(
					R.string.dialog_title);
			builder.setPositiveButton(R.string.case1, new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {

		        	   Intent i = new Intent(DetailTaskActivity.this, RazdelListActivity.class);
		        	   i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		        	   startActivity(i);

				}
		       });
			builder.setNegativeButton(R.string.case2, new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	  
		        	   Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=Smart+Kids+Art+Studio"));
		        	   startActivity(browserIntent);	
		        	   }
		       });
			AlertDialog dialog = builder.create();
			dialog.show();
		}

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

		String message = ti.name + " / " + ti.text;
		Intent share = new Intent(Intent.ACTION_SEND);
		share.setType("text/plain");
		share.putExtra(Intent.EXTRA_TEXT, message);
		startActivity(Intent.createChooser(share, "Поделиться"));

	}

	public void goToInfo(View view) {

		Intent i = new Intent(DetailTaskActivity.this, RazdelListActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(i);
	}
}
