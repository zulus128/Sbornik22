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

public class TaskListActivity extends Activity {

	private ListView list;
	private TaskArrayAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tasklist_activity);
		
		list = (ListView) this.findViewById(R.id.TaskList);
		ArrayList<TaskItem> filteredTasks = new ArrayList<TaskItem>();
		for (Iterator<TaskItem> it=Common.tasks.iterator(); it.hasNext();) {
			
			TaskItem task = it.next();
			if(task.section == Common.curRazdel)
				filteredTasks.add(task);
		}
		adapter = new TaskArrayAdapter(this, R.layout.tasklist_item, filteredTasks);
		list.setAdapter(adapter);

		list.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				
				Common.curTask = adapter.getItems().get(arg2).getId();
				Intent i = new Intent(TaskListActivity.this, DetailTaskActivity.class);
				startActivity(i);

			}
		});
	}
	
}
