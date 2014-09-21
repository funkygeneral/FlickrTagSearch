package com.funkygeneral.flickrtagsearch;

import android.util.Log;

public class HistoryItem {
     
    int _id;
    String _tagword;
    
    public HistoryItem(){
         
    }
    
    public HistoryItem(int id, String tagword){
        this._id = id;
        this._tagword = tagword;
    }
     
    public HistoryItem(String tagword){
        this._tagword = tagword;
    }
    
    public int getID(){
        return this._id;
    }
     
    public void setID(int id){
        this._id = id;
    }
     
    public String getTagWord(){
        return this._tagword;
    }
     
    public void setTagWord(String tagword){
        this._tagword = tagword;
    }
}
