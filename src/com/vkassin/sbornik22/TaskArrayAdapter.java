package com.vkassin.sbornik22;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TaskArrayAdapter extends ArrayAdapter<TaskItem> {

	private ArrayList<TaskItem> items;
	private Context ctx;

	public TaskArrayAdapter(Context context, int resourceId, ArrayList<TaskItem> objects) {
		
		super(context, resourceId, objects);
		
		this.items = objects;
		this.ctx = context;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater vi = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    	if(convertView == null)
    		convertView= vi.inflate(R.layout.tasklist_item, null);

    	TaskItem item = getItems().get(position);
    	if (item != null) {
    		
    		TextView title = (TextView) convertView.findViewById(R.id.TaskNameTextView);
    		TextView text = (TextView) convertView.findViewById(R.id.TaskTextView);
    		
    		title.setText(item.name);
    		text.setText(item.text);
    		
			ImageView imgMy1 = (ImageView) convertView.findViewById(R.id.imageMy1);
			imgMy1.setVisibility((item.my1 == 1)?View.VISIBLE:View.GONE);

    	}
    	
    	return convertView;
    }

	public void setItems(ArrayList<TaskItem> objects) {
		
		this.items.clear();
		this.items.addAll(objects);
	}

	public void addItems(ArrayList<TaskItem> objects) {
		
		this.items.addAll(objects);
	}

	public ArrayList<TaskItem> getItems() {
		
		return items;
	}
}
