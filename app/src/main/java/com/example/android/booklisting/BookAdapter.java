package com.example.android.booklisting;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.android.booklisting.MainActivity.LOG_TAG;

/**
 * Created by user on 19-Jul-17.
 */

class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(Activity context, List<Book> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // create a listItemView object to populate the adapter view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext())
                    .inflate(R.layout.book_item, parent, false);
        }

        // initilize a book object to populate the listItemView at given position
        Book currentListItem = getItem(position);

        TextView title = (TextView) listItemView.findViewById(R.id.title);
        title.setText(currentListItem.getmTitle());

        TextView subtitle = (TextView) listItemView.findViewById(R.id.subtitle);
        if (TextUtils.isEmpty(currentListItem.getmSubtitle())) {
            subtitle.setVisibility(View.GONE);
        } else {
            subtitle.setText(currentListItem.getmSubtitle());
        }

        DownloadImageTask task = new DownloadImageTask((ImageView) listItemView.findViewById(R.id.image));
        task.execute(currentListItem.getmImageURL());

        TextView authors = (TextView) listItemView.findViewById(R.id.author);
        ArrayList<String> book_authors = currentListItem.getmAuthors();
        String author = book_authors.get(0);
        for (int i = 1; i < book_authors.size(); i++) {
            author += ", " + book_authors.get(i);
        }
        authors.setText(author);

        TextView publisher = (TextView) listItemView.findViewById(R.id.publisher);
        publisher.setText(currentListItem.getPublisher());

        TextView publishedDate = (TextView) listItemView.findViewById(R.id.publishedDate);
        publishedDate.setText(currentListItem.getDate());

        return listItemView;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView mImage;

        public DownloadImageTask(ImageView mImage) {
            this.mImage = mImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e(LOG_TAG, "Error in loading image from server", e);
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            mImage.setImageBitmap(result);
        }
    }
}


