package com.funkygeneral.flickrtagsearch;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

public class FlickrImage {

	// Declaration of all needed information for the URL
	// Id of the photo
	String id;
	// The owner of the image
	String owner;
	// Both secret and server of the Image URL
	String secret;
	String server;
	// The farm on which the picture lives 
	String farm;

	// The URL String
	String URL;
	// Bitmap for large photo
	Bitmap photo;

	// Setting the ImageContener and getting it's thumb and large URLs based on the other passed properties
	public FlickrImage(String id, String owner, String secret, String server, String farm) {
		this.id = id;
		this.owner = owner;
		this.secret = secret;
		this.server = server;
		this.farm = farm;

		setURL(createPhotoURL());
	}
	
	private String createPhotoURL() {
		String tmp = null;
		tmp = "http://farm" + farm + ".staticflickr.com/" + server + "/" + id + "_" + secret + "_z.jpg";
		return tmp;
	}

	public Bitmap getPhoto() {
		return photo;
	}

	public void setPhoto(Bitmap photo) {
		this.photo = photo;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getFarm() {
		return farm;
	}

	public void setFarm(String farm) {
		this.farm = farm;
	}
	
	public String getURL() {
		return URL;
	}
	
	public void setURL(String URL) {
		this.URL = URL;
	}
}
