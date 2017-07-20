package com.example.android.booklisting;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 19-Jul-17.
 */

public class BookLoader extends AsyncTaskLoader<ArrayList<Book>>{

    private String mURL;

    public  BookLoader(Context context, String url){
        super(context);
        mURL = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Book> loadInBackground() {
        if (mURL == null) {
            return null;
        }

        ArrayList<Book> books = QueryUtils.extractBooks(mURL);
        return books;
    }
}
