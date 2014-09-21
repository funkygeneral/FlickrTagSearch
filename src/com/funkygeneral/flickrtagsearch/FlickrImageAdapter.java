package com.funkygeneral.flickrtagsearch;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FlickrImageAdapter extends BaseAdapter {
		 
	    private LayoutInflater mInflater;
	 
	    private List<FlickrImage> items = new ArrayList<FlickrImage>();
	 
	    public FlickrImageAdapter(Context context, List<FlickrImage> items) {
	        mInflater = LayoutInflater.from(context);
	        this.items = items;
	    }
	 
	    public int getCount() {
	        return items.size();
	    }
	 
	    public FlickrImage getItem(int position) {
	        return items.get(position);
	    }
	 
	    public long getItemId(int position) {
	        return position;
	    }
	 
	    public View getView(int position, View convertView, ViewGroup parent) {
	        ViewHolder holder;
	        FlickrImage flickrImage = items.get(position);
	        if (convertView == null) {
	            convertView = mInflater.inflate(R.layout.image_item, null);
	            holder = new ViewHolder();
	            holder.image = (ImageView) convertView.findViewById(R.id.flickrImageView);
	            convertView.setTag(holder);
	        } else {
	            holder = (ViewHolder) convertView.getTag();
	        }
	       
	        if (flickrImage.getPhoto() != null) {
	            holder.image.setImageBitmap(flickrImage.getPhoto());
	        } else {
	                // MY DEFAULT IMAGE
	            holder.image.setImageResource(R.drawable.ic_launcher);
	        }
	        return convertView;
	    }
	 
	    static class ViewHolder {
	    	ImageView image;
	    }
	 
}
