package com.vkassin.sbornik22;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RazdelArrayAdapter extends ArrayAdapter<RazdelItem> {

	private ArrayList<RazdelItem> items;
	private Context ctx;

	// private int resourceId;

	public RazdelArrayAdapter(Context context, int resourceId,
			ArrayList<RazdelItem> objects) {

		super(context, resourceId, objects);

		this.items = objects;
		this.ctx = context;
		// this.resourceId = resourceId;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater vi = (LayoutInflater) ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (convertView == null)
			convertView = vi.inflate(R.layout.razdel_item, null);

		RazdelItem item = getItems().get(position);
		if (item != null) {

			TextView title = (TextView) convertView
					.findViewById(R.id.RazdelNameTextView);
			TextView text = (TextView) convertView
					.findViewById(R.id.RazdelTextView);

			title.setText("" + (item.getId() - 0) +". " + item.name);
			text.setText(item.text);

			ImageView imgView = (ImageView) convertView.findViewById(R.id.razdelImageT);
			String uri = "drawable/" + item.icon;
			int imageResource = ctx.getResources().getIdentifier(uri, null, ctx.getPackageName());
			imgView.setImageResource(imageResource);
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
