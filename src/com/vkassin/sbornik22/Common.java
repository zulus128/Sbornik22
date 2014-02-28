package com.vkassin.sbornik22;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

public class Common {

	private static final String TAG = "Sbornik.Common";

	public static ArrayList<RazdelItem> razdels;
	public static ArrayList<TaskItem> tasks;
	public static Context app_ctx;

	public final static String JSON_TASK_TAG_ID = "id";
	public final static String JSON_TASK_TAG_ORDER = "order";
	public final static String JSON_TASK_TAG_LOCK = "lock";
	public final static String JSON_TASK_TAG_NAME = "name";
	public final static String JSON_TASK_TAG_TEXT = "text";
	public final static String JSON_TASK_TAG_ANSWER = "answer";
	public final static String JSON_TASK_TAG_SECTION = "section";

	public final static String JSON_RAZDEL_TAG_ID = "section";
	public final static String JSON_RAZDEL_TAG_ORDER = "order";
	public final static String JSON_RAZDEL_TAG_LOCK = "lock";
	public final static String JSON_RAZDEL_TAG_RATE = "rate";
	public final static String JSON_RAZDEL_TAG_NAME = "name";
	public final static String JSON_RAZDEL_TAG_TEXT = "opis_r";

	public static int curRazdel;
	public static int curTask;
	
//	static {
//
//		RazdelItem i1 = new RazdelItem(1);
//		i1.name = "Math";
//		i1.text = "Math description";
//		RazdelItem i2 = new RazdelItem(2);
//		i2.name = "Physics";
//		i2.text = "Physics description";
//		razdels = new ArrayList<RazdelItem>();
//		razdels.add(i1);
//		razdels.add(i2);
//	}
	
	public static void loadDatabase() {
		
		AssetManager assetManager = app_ctx.getAssets();
	    InputStream input;
		try {
			tasks = new ArrayList<TaskItem>();
			input = assetManager.open("task.json");
		    int size = input.available();
	        byte[] buffer = new byte[size];
	        input.read(buffer);
	        input.close();
	        String s = new String(buffer);
//	        Log.i(TAG, "s = " + s);
		    JSONArray tr = new JSONArray(s);
		    
		    
			for (int i = 0; i < tr.length(); i++) {

				JSONObject row = tr.getJSONObject(i);
				TaskItem ri = new TaskItem(row.getInt(JSON_TASK_TAG_ID));
				ri.order = row.getInt(JSON_TASK_TAG_ID);
				ri.section = row.getInt(JSON_TASK_TAG_SECTION);
				try{ ri.lock = row.getInt(JSON_TASK_TAG_LOCK); } catch (Exception e) {}
				ri.text = row.getString(JSON_TASK_TAG_TEXT);
				ri.name = row.getString(JSON_TASK_TAG_NAME);
				ri.answer = row.getString(JSON_TASK_TAG_ANSWER);
				tasks.add(ri);
//				Log.i(TAG, "row = " + row);
			}

			razdels = new ArrayList<RazdelItem>();
			input = assetManager.open("razdel.json");
		    size = input.available();
	        buffer = new byte[size];
	        input.read(buffer);
	        input.close();
	        s = new String(buffer);
		    JSONArray jr = new JSONArray(s);
			for (int i = 0; i < jr.length(); i++) {

				JSONObject row = jr.getJSONObject(i);
				RazdelItem ri = new RazdelItem(row.getInt(JSON_RAZDEL_TAG_ID));
				ri.order = row.getInt(JSON_RAZDEL_TAG_ID);
				ri.lock = row.getInt(JSON_RAZDEL_TAG_LOCK);
				ri.rate = row.getInt(JSON_RAZDEL_TAG_RATE);
				ri.text = row.getString(JSON_RAZDEL_TAG_TEXT);
				ri.name = row.getString(JSON_RAZDEL_TAG_NAME);
				razdels.add(ri);
				Log.i(TAG, "row = " + row);
			}

			Collections.sort(tasks, new Comparator<TaskItem>() {
			    
				public int compare(TaskItem a, TaskItem b) {
			    	
			        return Integer.valueOf(a.order).compareTo(Integer.valueOf(b.order));
			    }
			});
			
			Collections.sort(razdels, new Comparator<RazdelItem>() {
			    
				public int compare(RazdelItem a, RazdelItem b) {
			    	
			        return Integer.valueOf(a.order).compareTo(Integer.valueOf(b.order));
			    }
			});
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static TaskItem getCurTask() {
		
		TaskItem res = null;
		for (Iterator<TaskItem> it=Common.tasks.iterator(); it.hasNext();) {
		
			TaskItem ti = it.next();
			if(ti.getId() == curTask) {
				
				res =ti;
				break;
			}
		}
		
		return res;
	}
}
