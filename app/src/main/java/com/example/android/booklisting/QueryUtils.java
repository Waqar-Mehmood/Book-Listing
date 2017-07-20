package com.example.android.booklisting;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static com.example.android.booklisting.MainActivity.LOG_TAG;

/**
 * Created by user on 19-Jul-17.
 */

public final class QueryUtils {

    // Empty private constructor because no one should ever create a QueryUtils object.
    private QueryUtils() {
    }

    //  Return a list of Book objects that has been built up from parsing a JSON response.
    public static ArrayList<Book> extractBooks(String requestURL) {
        URL url = createUrl(requestURL);

        String jsonResponse = "";
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create an {@link Event} object
        ArrayList<Book> books = extractFeatureFromJson(jsonResponse);
        return books;
    }

    // Returns new URL object from the given string URL.
    @Nullable
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e(LOG_TAG, "Error with creating URL", exception);
            return null;
        }
        return url;
    }

    // Make an HTTP request to the given URL and return a String as the response.*/
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
            else{
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem occured in retrieving Google Books JSON results.", e);

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    // Convert the InputStream into a String which contains the whole JSON response from the server.
    @NonNull
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    @Nullable
    private static ArrayList<Book> extractFeatureFromJson(String bookJSON) {

        if(TextUtils.isEmpty(bookJSON)){
            return null;
        }

        ArrayList<Book> books = new ArrayList<Book>();

        try {

            JSONObject baseJsonResponse  = new JSONObject(bookJSON);
            JSONArray booksArray = baseJsonResponse .getJSONArray("items");
            for(int i = 0; i < booksArray.length(); i++){
                JSONObject currentBook = booksArray.getJSONObject(i);
                JSONObject volumeInfo = currentBook.getJSONObject("volumeInfo");

                String title = volumeInfo.getString("title");
                String subtitle="";
                if(volumeInfo.has("subtitle")){
                    subtitle = volumeInfo.getString("subtitle");
                }


                JSONArray authors = volumeInfo.getJSONArray("authors");
                ArrayList<String> authorLsit = new ArrayList<>();
                for(int j = 0; j < authors.length(); j++){
                    authorLsit.add(authors.getString(j));
                }

                String publisher = volumeInfo.getString("publisher");
                String publishedDate = volumeInfo.getString("publishedDate");

                JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                String image = imageLinks.getString("smallThumbnail");

                books.add(new Book(title, subtitle, image, authorLsit, publisher, publishedDate));

            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the Google Books JSON results", e);
        }
        return books;
    }
}
