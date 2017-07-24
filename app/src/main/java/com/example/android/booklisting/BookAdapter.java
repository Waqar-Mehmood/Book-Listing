package com.example.android.booklisting;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

class BookAdapter extends ArrayAdapter<Book> {

    private Context mContext;

    public BookAdapter(Activity context, List<Book> objects) {
        super(context, 0, objects);

        mContext = context;
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
            subtitle.setVisibility(View.VISIBLE);
            subtitle.setText(currentListItem.getmSubtitle());
        }

        TextView authors = (TextView) listItemView.findViewById(R.id.author);
        ArrayList<String> book_authors = currentListItem.getmAuthors();
        String author = book_authors.get(0);
        for (int i = 1; i < book_authors.size(); i++) {
            author += ", " + book_authors.get(i);
        }
        authors.setText(author);

        TextView publisher = (TextView) listItemView.findViewById(R.id.publisher);
        if (TextUtils.isEmpty(currentListItem.getPublisher())) {
            publisher.setVisibility(View.GONE);
        } else {
            publisher.setVisibility(View.VISIBLE);
            publisher.setText(currentListItem.getPublisher());
        }

        TextView publishedDate = (TextView) listItemView.findViewById(R.id.publishedDate);
        publishedDate.setText(currentListItem.getDate());

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);
        Picasso.with(mContext)
                .load(currentListItem.getmImageURL())
                .placeholder(R.drawable.progress_animation)
                .into(imageView);

        return listItemView;
    }
}


