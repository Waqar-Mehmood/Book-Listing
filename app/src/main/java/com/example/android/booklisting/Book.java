package com.example.android.booklisting;

import java.util.ArrayList;

/**
 * Created by user on 19-Jul-17.
 */

public class Book
{
    private String mTitle;
    private String mSubtitle;
    private String mImageURL;
    private ArrayList<String> mAuthors;
    private String Publisher;
    private String date;

    public Book(String mTitle, String mSubtitle, String mImageURL, ArrayList<String> mAuthors, String publisher, String date) {
        this.mTitle = mTitle;
        this.mSubtitle = mSubtitle;
        this.mImageURL = mImageURL;
        this.mAuthors = mAuthors;
        Publisher = publisher;
        this.date = date;
    }


    public String getmTitle() {
        return mTitle;
    }

    public String getmSubtitle() {
        return mSubtitle;
    }

    public String getmImageURL() {
        return mImageURL;
    }

    public ArrayList<String> getmAuthors() {
        return mAuthors;
    }

    public String getPublisher() {
        return Publisher;
    }

    public String getDate() {
        return date;
    }
}


