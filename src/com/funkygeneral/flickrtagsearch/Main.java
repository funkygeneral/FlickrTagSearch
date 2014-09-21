package com.funkygeneral.flickrtagsearch;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.os.Build;

public class Main extends ActionBarActivity {

	JSONParser jParser = new JSONParser();
	// String to create Flickr API urls
	private static final String url_get_flickr_images = "https://api.flickr.com/services/rest/";
	private static final String NUMBER_OF_PHOTOS = "20";
	private static final String SEARCH_METHOD = "flickr.photos.search";

	private static final String APIKEY_SEARCH_STRING = "64c0f179f8aec0444033c8b2c57a7db0";

	private static final String METHOD = "method";
	private static final String API_KEY = "api_key";
	private static final String TAGS_STRING = "tags";
	private static final String FORMAT_STRING = "format";
	private static final String PER_PAGE = "per_page";
	private static final String MEDIA = "media";

	private static final String TAG_STAT = "stat";
	private static final String TAG_PHOTOS = "photos";
	private static final String TAG_ID = "id";
	private static final String TAG_PHOTO = "photo";
	private static final String TAG_FARM = "farm";
	private static final String TAG_OWNER = "owner";
	private static final String TAG_SECRET = "secret";
	private static final String TAG_SERVER = "server";

	String TAG = "Main Activity";

	EditText text_input;
	Button searchButton;
	ListView imageListView;

	ArrayList<FlickrImage> imageList = new ArrayList<FlickrImage>();
	private FlickrImageAdapter imageAdapter;

	String tagWord;

	private ProgressDialog pDialog;
	
	DatabaseHandler dbHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		dbHandler = new DatabaseHandler(this);
		
		text_input = (EditText) findViewById(R.id.text_input);
		searchButton = (Button) findViewById(R.id.search_button);
		searchButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				tagWord = text_input.getText().toString();
				Log.d(TAG, tagWord);
				dbHandler.addHistoryItem(new HistoryItem(tagWord));
				new LookForTag().execute();
			}

		});

		imageListView = (ListView) findViewById(R.id.list);

		imageAdapter = new FlickrImageAdapter(Main.this, imageList);
		imageListView.setAdapter(imageAdapter);
	}

	class LookForTag extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Main.this);
			pDialog.setMessage("Looking for images with tag " + tagWord + "...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		protected String doInBackground(String... args) {
			// https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=64c0f179f8aec0444033c8b2c57a7db0&tags=dog&format=json&per_page=20&media=photos
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(METHOD, SEARCH_METHOD));
			params.add(new BasicNameValuePair(API_KEY, APIKEY_SEARCH_STRING));
			params.add(new BasicNameValuePair(TAGS_STRING, tagWord));
			params.add(new BasicNameValuePair(FORMAT_STRING, "json"));
			params.add(new BasicNameValuePair(PER_PAGE, NUMBER_OF_PHOTOS));
			params.add(new BasicNameValuePair(MEDIA, "photos"));
			JSONObject json = jParser.makeHttpRequest(url_get_flickr_images, "GET", params);

			Log.d(TAG, json.toString());

			imageList.clear();

			try {
				String status = json.getString(TAG_STAT);

				if (status.equals("ok")) {
					JSONObject photos = json.getJSONObject(TAG_PHOTOS);
					JSONArray photoArray = photos.getJSONArray(TAG_PHOTO);
					for (int i = 0; i < photoArray.length(); i++) {
						JSONObject photo = photoArray.getJSONObject(i);

						String id = photo.getString(TAG_ID);
						String owner = photo.getString(TAG_OWNER);
						String secret = photo.getString(TAG_SECRET);
						String server = photo.getString(TAG_SERVER);
						String farm = photo.getString(TAG_FARM);

						FlickrImage flickrImage = new FlickrImage(id, owner, secret, server, farm);
						imageList.add(flickrImage);
					}
				} else {
					Log.d("Zalora Test", "No products found");
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(String file_url) {
			pDialog.dismiss();
			runOnUiThread(new Runnable() {
				public void run() {

					// CREATE BASE ADAPTER
					//productAdapter = new ProductAdapter(Zalora.this, productList);
					imageAdapter.notifyDataSetChanged();

					for(FlickrImage product : imageList) {
						product.loadImage(imageAdapter);
					}
				}
			});
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent intent = new Intent(getApplicationContext(), History.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
