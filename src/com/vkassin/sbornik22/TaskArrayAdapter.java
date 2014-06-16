package com.vkassin.sbornik22;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TaskArrayAdapter extends ArrayAdapter<TaskItem> {

	private ArrayList<TaskItem> items;
	private Context ctx;
	private LayoutInflater inflater;
	
	public TaskArrayAdapter(Context context, int resourceId, ArrayList<TaskItem> objects) {
		
		super(context, resourceId, objects);
		
		this.items = objects;
		this.ctx = context;
//		inflater = (LayoutInflater)context.getSystemService
//			      (Context.LAYOUT_INFLATER_SERVICE);
		inflater = LayoutInflater.from(context);

	}

	public View getView(int position, View convertView, ViewGroup parent) {

    	if(convertView == null)
    		convertView= inflater.inflate(R.layout.tasklist_item, null);

    	TaskItem item = getItems().get(position);
    	if (item != null) {
    		
    		TextView title = (TextView) convertView.findViewById(R.id.TaskNameTextView);
			Typeface tf = Typeface.createFromAsset(ctx.getAssets(), "fonts/GothaProMed.otf");
			title.setTypeface(tf);
    		title.setText(item.name);

    		TextView text = (TextView) convertView.findViewById(R.id.TaskTextView);
    		Typeface tf1 = Typeface.createFromAsset(ctx.getAssets(), "fonts/GothaProReg.otf");
			text.setTypeface(tf1);
    		text.setText(item.text);
    		
    		TextView number = (TextView) convertView.findViewById(R.id.TaskNumberTextView);
			number.setTypeface(tf);
    		number.setText("" + item.getId());

//			ImageView imgMy1 = (ImageView) convertView.findViewById(R.id.imageMy1);
//			imgMy1.setVisibility((item.my1 == 1)?View.VISIBLE:View.GONE);
    		
			ImageView imgStat = (ImageView) convertView.findViewById(R.id.imageStatus);
			String uri = "drawable/";
			if(item.lock != 0) {
				
				uri += "dicon_07";
			} else if (Common.isMy(item.getId())){

				uri += "dicon_08";
				
			} else if (Common.isViewed(item.getId())){

				uri += "dicon_06";
				
			}
			int imageResource = ctx.getResources().getIdentifier(uri, null, ctx.getPackageName());
			imgStat.setImageResource(imageResource);


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
