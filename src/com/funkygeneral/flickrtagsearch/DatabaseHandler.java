package com.funkygeneral.flickrtagsearch;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "flickr_tag_search";

	// Contacts table name
	private static final String TABLE_HISTORY = "users";

	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_TAGWORD = "tagword";

	String[] cursorString = new String[2];

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		cursorString[0] = KEY_ID;
		cursorString[1] = KEY_TAGWORD;
		//Log.d("GAME STRING", getGameString());
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_HISTORY_TABLE = "CREATE TABLE " + TABLE_HISTORY + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_TAGWORD + " TEXT" +  ")";
		db.execSQL(CREATE_HISTORY_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);

		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */

	// Adding new HistoryItem
	public void addHistoryItem(HistoryItem historyItem) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_TAGWORD, historyItem.getTagWord());

		// Inserting Row
		db.insert(TABLE_HISTORY, null, values);
		db.close(); // Closing database connection
	}

	public HistoryItem getHistoryItem(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_HISTORY, cursorString, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		HistoryItem historyItem = new HistoryItem();
		historyItem.setID(Integer.parseInt(cursor.getString(0)));
		historyItem.setTagWord(cursor.getString(1));

		cursor.close();
		db.close();

		return historyItem;
	}

	public List<HistoryItem> getAllHistoryItems() {
		List<HistoryItem> itemsList = new ArrayList<HistoryItem>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_HISTORY;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				HistoryItem historyItem = new HistoryItem();
				historyItem.setID(Integer.parseInt(cursor.getString(0)));
				historyItem.setTagWord(cursor.getString(1));
				// Adding historyItem to list
				itemsList.add(historyItem);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();

		return itemsList;
	}

	// Updating single contact
	public void updateHistoryItem(HistoryItem historyItem) {
		Log.d("DatabaseHandler", "Updating Contact");
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_TAGWORD, historyItem.getTagWord());

		// updating row
		db.update(TABLE_HISTORY, values, KEY_ID + " = ?", new String[] { String.valueOf(historyItem.getID()) });
		db.close(); // Closing database connection
	}

	// Deleting single item
	public void deleteHistoryItem(HistoryItem historyItem) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_HISTORY, KEY_ID + " = ?",
				new String[] { String.valueOf(historyItem.getID()) });
		db.close();
	}


	// Getting items Count
	public int getItemsCount() {
		String countQuery = "SELECT  * FROM " + TABLE_HISTORY;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();

		return cursor.getCount();
	}
}
