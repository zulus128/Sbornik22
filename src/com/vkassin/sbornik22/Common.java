package com.vkassin.sbornik22;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.widget.Toast;

public class Common {

	private static final String TAG = "Sbornik.Common";
	
	public static final String GP_URL = "https://play.google.com/store/apps/developer?id=Smart+Kids+Art+Studio";

	public static ArrayList<RazdelItem> razdels;
	public static ArrayList<TaskItem> tasks;
	public static ArrayList<TaskItem> filteredTasks;
	public static Context app_ctx;
	public static String secondListTitle;
	public static boolean isSearch;
	public static boolean isFavourites;

	public final static String JSON_TASK_TAG_ID = "id";
	public final static String JSON_TASK_TAG_ORDER = "order";
	public final static String JSON_TASK_TAG_LOCK = "lock";
	public final static String JSON_TASK_TAG_NAME = "name";
	public final static String JSON_TASK_TAG_TEXT = "text";
	public final static String JSON_TASK_TAG_ANSWER = "answer";
	public final static String JSON_TASK_TAG_SECTION = "section";
	public final static String JSON_TASK_TAG_MY1 = "my1";
	public final static String JSON_TASK_TAG_PIC = "pic";
	public final static String JSON_TASK_TAG_PICSIGN = "picsign";

	public final static String JSON_RAZDEL_TAG_ID = "section";
	public final static String JSON_RAZDEL_TAG_ORDER = "order";
	public final static String JSON_RAZDEL_TAG_LOCK = "lock";
	public final static String JSON_RAZDEL_TAG_RATE = "rate";
	public final static String JSON_RAZDEL_TAG_NAME = "name";
	public final static String JSON_RAZDEL_TAG_TEXT = "opis_r";
	public final static String JSON_RAZDEL_TAG_ICON = "icon";

	public static int curRazdel;
	public static String curRazdelName;
	public static int curTask;

	private static Set<Integer> myFavourites;
	private static String FLIST_FNAME = "my_favr";
	private static Set<Integer> myViewed;
	private static String VLIST_FNAME = "my_view";
	
	public static void saveFavrList() {

		Log.i(TAG, "saveFavrList()");
		FileOutputStream fos;
		try {

			fos = app_ctx.openFileOutput(FLIST_FNAME, Context.MODE_PRIVATE);
			ObjectOutputStream os = new ObjectOutputStream(fos);
			os.writeObject(myFavourites);
			os.close();
			fos.close();

		} catch (FileNotFoundException e) {

			Toast.makeText(app_ctx, "Файл не записан " + e.toString(),
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		} catch (IOException e) {

			Toast.makeText(app_ctx, "Файл не записан: " + e.toString(),
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}

	}

	public static void loadFavrList() {

		FileInputStream fileInputStream;
		try {

			fileInputStream = app_ctx.openFileInput(FLIST_FNAME);
			ObjectInputStream oInputStream = new ObjectInputStream(
					fileInputStream);
			Object one = oInputStream.readObject();
			myFavourites = (HashSet<Integer>) one;
			oInputStream.close();
			fileInputStream.close();

		} catch (FileNotFoundException e) {

			// e.printStackTrace();
			Log.i(TAG, "creates blank. no file " + FLIST_FNAME);
			myFavourites = new HashSet<Integer>();

		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// return favourites;
	}
	
	public static void saveViewedList() {

		Log.i(TAG, "saveViewedList()");
		FileOutputStream fos;
		try {

			fos = app_ctx.openFileOutput(VLIST_FNAME, Context.MODE_PRIVATE);
			ObjectOutputStream os = new ObjectOutputStream(fos);
			os.writeObject(myViewed);
			os.close();
			fos.close();

		} catch (FileNotFoundException e) {

			Toast.makeText(app_ctx, "Файл не записан " + e.toString(),
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		} catch (IOException e) {

			Toast.makeText(app_ctx, "Файл не записан: " + e.toString(),
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}

	}

	public static void loadViewedList() {

		FileInputStream fileInputStream;
		try {

			fileInputStream = app_ctx.openFileInput(VLIST_FNAME);
			ObjectInputStream oInputStream = new ObjectInputStream(
					fileInputStream);
			Object one = oInputStream.readObject();
			myViewed = (HashSet<Integer>) one;
			oInputStream.close();
			fileInputStream.close();

		} catch (FileNotFoundException e) {

			// e.printStackTrace();
			Log.i(TAG, "creates blank. no file " + VLIST_FNAME);
			myViewed = new HashSet<Integer>();

		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// return favourites;
	}
	
	public static boolean setNextTask() {

		for (Iterator<TaskItem> it = filteredTasks.iterator(); it.hasNext();) {

			TaskItem task = it.next();
			if(task.getId() == curTask) {
				
//				if(it.hasNext()) {
				while(it.hasNext()) {
				
					TaskItem nexttask = it.next();
					if(nexttask.lock != 0)
						continue;
					curTask = nexttask.getId();
					return true;
				}
				return false;
			}
		}
		
		return false;
	}

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
			// Log.i(TAG, "s = " + s);
			JSONArray tr = new JSONArray(s);

			for (int i = 0; i < tr.length(); i++) {

				JSONObject row = tr.getJSONObject(i);
				TaskItem ri = new TaskItem(row.getInt(JSON_TASK_TAG_ID));
				ri.order = row.getInt(JSON_TASK_TAG_ID);
				ri.section = row.getInt(JSON_TASK_TAG_SECTION);
				try {
					ri.lock = row.getInt(JSON_TASK_TAG_LOCK);
				} catch (Exception e) {
				}
				ri.text = row.getString(JSON_TASK_TAG_TEXT);
				ri.name = row.getString(JSON_TASK_TAG_NAME);
				ri.answer = row.getString(JSON_TASK_TAG_ANSWER);
				ri.my1 = row.getInt(JSON_TASK_TAG_MY1);
				ri.pic = row.getString(JSON_TASK_TAG_PIC);
				ri.picsign = row.getString(JSON_TASK_TAG_PICSIGN);
				tasks.add(ri);
				// Log.i(TAG, "row = " + row);
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
				ri.icon = row.getString(JSON_RAZDEL_TAG_ICON);
				razdels.add(ri);
				// Log.i(TAG, "row = " + row);
			}

			Collections.sort(tasks, new Comparator<TaskItem>() {

				public int compare(TaskItem a, TaskItem b) {

					return Integer.valueOf(a.order).compareTo(
							Integer.valueOf(b.order));
				}
			});

			Collections.sort(razdels, new Comparator<RazdelItem>() {

				public int compare(RazdelItem a, RazdelItem b) {

					return Integer.valueOf(a.order).compareTo(
							Integer.valueOf(b.order));
				}
			});

			loadFavrList();
			loadViewedList();
			
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
		for (Iterator<TaskItem> it = Common.tasks.iterator(); it.hasNext();) {

			TaskItem ti = it.next();
			if (ti.getId() == curTask) {

				res = ti;
				break;
			}
		}

		return res;
	}
	
	public static boolean isMy(int taskid) {
		
		Integer i = Integer.valueOf(taskid);
		return myFavourites.contains(i);
	}
	
	public static void switchMy(int taskid) {

		Integer i = Integer.valueOf(taskid);
		if (myFavourites.contains(i)) {
			
			myFavourites.remove(i);
		}
		else {
			
			myFavourites.add(i);
		}

		saveFavrList();
	}
	
	public static boolean isViewed(int taskid) {
		
		Integer i = Integer.valueOf(taskid);
		return myViewed.contains(i);
	}
	
	public static void setViewed(int taskid) {

		Integer i = Integer.valueOf(taskid);
		myViewed.add(i);
		
		saveViewedList();
	}
}
