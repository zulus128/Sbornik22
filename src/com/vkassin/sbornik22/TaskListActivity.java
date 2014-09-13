package com.vkassin.sbornik22;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class TaskListActivity extends Activity {

	private static final String TAG = "Sbornik.TaskListActivity";

	private ListView list;
	private TaskArrayAdapter adapter;
	private Runnable run;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.tasklist_activity);

		setTasks();
		
		run = new Runnable(){
            public void run(){

            	adapter.notifyDataSetChanged();
            	list.invalidateViews();
            	list.refreshDrawableState();
            }
       };
       
		AdView adView = (AdView)this.findViewById(R.id.adView);
		AdRequest adRequest = new AdRequest.Builder()
		    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
		    .build();
		adView.loadAd(adRequest);

	}

	private void setTasks() {

		TextView title = (TextView) this.findViewById(R.id.TaskListTitle01);
		title.setText(Common.isFavourites?"Мои задачи":Common.secondListTitle);
		Typeface tf = Typeface.createFromAsset(getAssets(),
				"fonts/GothaProReg.otf");
		title.setTypeface(tf);
		
		ArrayList<TaskItem> filteredTasks = new ArrayList<TaskItem>();
		for (Iterator<TaskItem> it = Common.tasks.iterator(); it.hasNext();) {

			TaskItem task = it.next();
			if(!Common.isSearch) {
				
				if(Common.isFavourites) {

					if (Common.isMy(task.getId()))
						filteredTasks.add(task);
				}
				else {

					if ((task.section == Common.curRazdel) || (Common.curRazdel == 1))
						filteredTasks.add(task);
				}
			}
			else {
				
				if(task.name.toLowerCase().contains(Common.secondListTitle.toLowerCase()))
					filteredTasks.add(task);
				else
					if(task.text.toLowerCase().contains(Common.secondListTitle.toLowerCase()))
						filteredTasks.add(task);
					else
						if(task.picsign.toLowerCase().contains(Common.secondListTitle.toLowerCase()))
							filteredTasks.add(task);
						else
							if(task.answer.toLowerCase().contains(Common.secondListTitle.toLowerCase()))
								filteredTasks.add(task);

			}
		}
		
		list = (ListView) this.findViewById(R.id.TaskList);

		Common.filteredTasks = filteredTasks;
		adapter = new TaskArrayAdapter(this, R.layout.tasklist_item,
				filteredTasks);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				if(adapter.getItems().get(arg2).lock != 0)
					return;
				
				Common.curTask = adapter.getItems().get(arg2).getId();
				Intent i = new Intent(TaskListActivity.this,
						DetailTaskActivity.class);
				startActivity(i);

			}
		});


	}
	
	@Override
	public void onBackPressed() {

		Intent i = new Intent(this, RazdelListActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(i);
	}

	@Override
	protected void onResume() {
	
		super.onResume();
		
		setTasks();

//		Log.i(TAG, "--onResume");
		runOnUiThread(run);
	};
	
//	@Override
//	protected void onPause() {
//
//	   super.onPause();
//
//		Log.i(TAG, "--onPause");
//	}
	
	public void goToNetworks(View view) {
		
		String message = "БЭГ";
		Intent share = new Intent(Intent.ACTION_SEND);
		share.setType("text/plain");
		share.putExtra(Intent.EXTRA_TEXT, message);
		startActivity(Intent.createChooser(share, "Поделиться"));
			
	}

	public void goToFavourites(View view) {
		
		Common.isSearch = false;
		Common.isFavourites = true;
		Intent i = new Intent(TaskListActivity.this, TaskListActivity.class);
		startActivity(i);
	}

	public void goToSearch(View view) {

		Intent i = new Intent(TaskListActivity.this, SearchActivity.class);
		startActivity(i);
	}

	public void goToInfo(View view) {
		
		Intent i = new Intent(TaskListActivity.this, RazdelListActivity.class);
//		i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(i);
	}

}
