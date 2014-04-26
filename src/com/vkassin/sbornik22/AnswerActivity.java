package com.vkassin.sbornik22;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AnswerActivity extends Activity {

	private TaskItem ti;


	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.answer_activity);

		ti = Common.getCurTask();

		// TextView title = (TextView) this.findViewById(R.id.PictureTaskTitle);
		// title.setText(R.string.answer);
		//
		// ImageView imgMy1 = (ImageView) this.findViewById(R.id.imageMy);
		// imgMy1.setVisibility((ti.my1 == 1)?View.VISIBLE:View.GONE);
		//
		// TextView idd = (TextView) this.findViewById(R.id.PictureTaskId);
		// idd.setText("" + ti.getId());

		TextView idd = (TextView) this.findViewById(R.id.DetailNumTextView);
		idd.setText("" + ti.getId());
		TextView idname = (TextView) this.findViewById(R.id.DetailNameTextView);
		idname.setText(ti.name);
		Typeface tff = Typeface.createFromAsset(getAssets(),
				"fonts/GothaProMed.otf");
		idd.setTypeface(tff);
		idname.setTypeface(tff);

		TextView descr = (TextView) this.findViewById(R.id.AnswerTextView);
		descr.setText(ti.answer);
		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/GothaProReg.otf");
		idd.setTypeface(tff);
		idname.setTypeface(tff);

		TextView title = (TextView) this.findViewById(R.id.AnswerTitle01);
		title.setText("БЭГ / " + Common.curRazdelName + " / ЗАДАЧА № "
				+ ti.getId() + " / ОТВЕТ");

		if (ti.fig.equals("")) {

			ImageView imgDiv1 = (ImageView) this.findViewById(R.id.imageDiv1);
			imgDiv1.setVisibility(View.GONE);
			ImageView imgDiv2 = (ImageView) this.findViewById(R.id.imageDiv2);
			imgDiv2.setVisibility(View.GONE);
			ImageView imgPic = (ImageView) this.findViewById(R.id.imagePicture);
			imgPic.setVisibility(View.GONE);
			TextView ptext = (TextView) this
					.findViewById(R.id.DetailTaskPicTextView);
			ptext.setVisibility(View.GONE);

		} else {

			String uri = "drawable/" + ti.fig;
			int imageResource = getResources().getIdentifier(uri, null,
					getPackageName());
//			ImageView imgPic = (ImageView) this.findViewById(R.id.imagePicture);
//			imgPic.setImageResource(imageResource);
			
			TouchImageView imgPic = (TouchImageView) this.findViewById(R.id.imagePicture);
			imgPic.setImageResource(imageResource);

			TextView ptext = (TextView) this
					.findViewById(R.id.DetailTaskPicTextView);
			ptext.setText(ti.figsign);
			Typeface tf1 = Typeface.createFromAsset(getAssets(),
					"fonts/GothaProReg.otf");
			ptext.setTypeface(tf1);

		}

		this.setFavButton();

	}

	private void refresh() {

		ImageView imgStat = (ImageView) this
				.findViewById(R.id.answerImageStatus);
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
				.findViewById(R.id.answer_button_my);
		String urip = "drawable/dicon_11";
		String urim = "drawable/dicon_12";
		boolean ismy = Common.isMy(ti.getId());
		int imageResource = getResources().getIdentifier(ismy ? urim : urip,
				null, getPackageName());
		myfav.setBackgroundResource(imageResource);
		refresh();
	}

	@Override
	public void onBackPressed() {

		Intent i = new Intent(this, TaskListActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(i);
	}

	public void goBack(View view) {

		super.onBackPressed();
	}

	public void goForward(View view) {

		Common.curTask = ti.getId();
		boolean b = Common.setNextTask();
		if (b) {

			Intent i = new Intent(AnswerActivity.this, DetailTaskActivity.class);
			startActivity(i);

		} else {

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(R.string.dialog_message).setTitle(
					R.string.dialog_title);
			builder.setPositiveButton(R.string.case1,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {

							Intent i = new Intent(AnswerActivity.this,
									RazdelListActivity.class);
							i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
							startActivity(i);

						}
					});
			builder.setNegativeButton(R.string.case2,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {

							Intent browserIntent = new Intent(
									Intent.ACTION_VIEW,
									Uri.parse(Common.GP_URL));
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
		Intent i = new Intent(AnswerActivity.this, TaskListActivity.class);
		startActivity(i);
	}

	public void goMy(View view) {

		Common.switchMy(ti.getId());
		this.setFavButton();
	}

	public void goToSearch(View view) {

		Intent i = new Intent(AnswerActivity.this, SearchActivity.class);
		startActivity(i);
	}

	public void goToNetworks(View view) {

		String message = ti.name + " / " + ti.text + " / " + ti.answer;
		Intent share = new Intent(Intent.ACTION_SEND);
		share.setType("text/plain");
		share.putExtra(Intent.EXTRA_TEXT, message);
		startActivity(Intent.createChooser(share, "Поделиться"));

	}

	public void goToInfo(View view) {

		Intent i = new Intent(AnswerActivity.this, RazdelListActivity.class);
		// i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(i);
	}
}
