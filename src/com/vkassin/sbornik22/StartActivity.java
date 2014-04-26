package com.vkassin.sbornik22;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class StartActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);


		setContentView(R.layout.start_activity);

		if (getIntent().getBooleanExtra("Exit me", false)) {
		
			finish();
			return; // add this to prevent from doing unnecessary stuffs
		}
		
		Common.app_ctx = getApplicationContext();
		Common.loadDatabase();

		ImageView okno = (ImageView) this.findViewById(R.id.imageOkno);
		okno.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub

//				v.setVisibility(View.GONE);

				Intent i = new Intent(StartActivity.this,
						RazdelListActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);

			}

		});

	}

}
