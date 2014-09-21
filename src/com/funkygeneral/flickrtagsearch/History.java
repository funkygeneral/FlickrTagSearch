package com.funkygeneral.flickrtagsearch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.os.Build;

public class History extends ListActivity {


	private static final String TAG = "History";
	private static final String TAG_TAGWORD = "tagword";

	ArrayList<HashMap<String, String>> historyList;

	DatabaseHandler dbHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);

		dbHandler = new DatabaseHandler(this);

		historyList = new ArrayList<HashMap<String, String>>();
		
		List<HistoryItem> historyItems = dbHandler.getAllHistoryItems();
		for(HistoryItem historyItem : historyItems) {
			String tagWord = historyItem.getTagWord();
			Log.d(TAG, tagWord);
			HashMap<String, String> map = new HashMap<String, String>();
			map.put(TAG_TAGWORD, tagWord);
			historyList.add(map);
		} 
		ListAdapter adapter = new SimpleAdapter(
				History.this, historyList,
				R.layout.history_item, new String[] { TAG_TAGWORD },
				new int[] { R.id.historyText });
		setListAdapter(adapter);
	}
	
	@Override  
	public void onListItemClick(ListView l, View v, int position, long id) {  
		
		String tagword = historyList.get(position).get(TAG_TAGWORD);

		Intent in = new Intent(getApplicationContext(), Main.class);
		in.putExtra(TAG_TAGWORD, tagword);

		startActivityForResult(in, 100);
	}  
}
