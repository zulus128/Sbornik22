package com.vkassin.sbornik22;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RazdelArrayAdapter extends ArrayAdapter<RazdelItem> {

	private ArrayList<RazdelItem> items;
	private Context ctx;
	private LayoutInflater inflater;


	private static final String TAG = "Sbornik.RazdelArrayAdapter";

	public RazdelArrayAdapter(Context context, int resourceId,
			ArrayList<RazdelItem> objects) {

		super(context, resourceId, objects);

		this.items = objects;
		this.ctx = context;
//		inflater = (LayoutInflater)context.getSystemService
//			      (Context.LAYOUT_INFLATER_SERVICE);
		inflater = LayoutInflater.from(context);

		}

	public View getView(int position, View convertView, ViewGroup parent) {


//		Log.i(TAG, "" + inflater);
			
       if (convertView == null)
			convertView = inflater.inflate(R.layout.razdel_item, null);

		RazdelItem item = getItems().get(position);
		if (item != null) {

			
			TextView title = (TextView) convertView
					.findViewById(R.id.RazdelNameTextView);
			Typeface tf = Typeface.createFromAsset(ctx.getAssets(), "fonts/GothaProMed.otf");
			title.setTypeface(tf);

			TextView text = (TextView) convertView
					.findViewById(R.id.RazdelTextView);
			tf = Typeface.createFromAsset(ctx.getAssets(), "fonts/GothaProReg.otf");
			text.setTypeface(tf);

//			title.setText("" + (item.getId() - 0) +". " + item.name);
			title.setText(item.name);
			text.setText(item.text);

			ImageView imgView = (ImageView) convertView.findViewById(R.id.razdelImageT);
			String uri = "drawable/" + ((item.lock == 0)?item.icon:"dicon_07");
			int imageResource = ctx.getResources().getIdentifier(uri, null, ctx.getPackageName());
			imgView.setImageResource(imageResource);
			
//			ImageView imgLock = (ImageView) convertView.findViewById(R.id.razdelLock);
//			if(item.lock == 0) {
//				
//				imgLock.setVisibility(View.INVISIBLE);
//			}
//			else {
//				
//				imgLock.setVisibility(View.VISIBLE);
//
//			}

		}

		return convertView;
	}

	public void setItems(ArrayList<RazdelItem> objects) {

		this.items.clear();
		this.items.addAll(objects);
	}

	public void addItems(ArrayList<RazdelItem> objects) {

		this.items.addAll(objects);
	}

	public ArrayList<RazdelItem> getItems() {

		return items;
	}
}
