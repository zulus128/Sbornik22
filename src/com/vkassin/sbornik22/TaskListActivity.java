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

public class TaskListActivity extends Activity {

	private ListView list;
	private TaskArrayAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.tasklist_activity);

		TextView title = (TextView) this.findViewById(R.id.TaskListTitle01);
		title.setText(Common.secondListTitle);

		list = (ListView) this.findViewById(R.id.TaskList);
		ArrayList<TaskItem> filteredTasks = new ArrayList<TaskItem>();
		for (Iterator<TaskItem> it = Common.tasks.iterator(); it.hasNext();) {

			TaskItem task = it.next();
			if(!Common.isSearch) {
				
				if ((task.section == Common.curRazdel) || (Common.curRazdel == 1))
					filteredTasks.add(task);
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
		
		Common.filteredTasks = filteredTasks;
		adapter = new TaskArrayAdapter(this, R.layout.tasklist_item,
				filteredTasks);
		list.setAdapter(adapter);

		list.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				Common.curTask = adapter.getItems().get(arg2).getId();
				Intent i = new Intent(TaskListActivity.this,
						DetailTaskActivity.class);
				startActivity(i);

			}
		});
	}

	public void goToFavourites(View view) {
		// Do something in response to button click
	}

	public void goToSearch(View view) {

		Intent i = new Intent(TaskListActivity.this, SearchActivity.class);
		startActivity(i);
	}

	public void goToOglav(View view) {
		
		Intent i = new Intent(TaskListActivity.this, RazdelListActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(i);
	}

}
