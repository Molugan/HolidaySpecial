package com.whatever.holidayspecial.net;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.whatever.holidayspecial.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;

/**
 * Created by cergo on 06/12/16.
 */

public class SearchMovieTask extends AsyncTask<String, Movie, Void> {

    private ArrayAdapter<Movie> adapter;
    private String type = "movie";

    private static String baseURL = "https://www.omdbapi.com/?r=json&v=1&";

    public SearchMovieTask(ArrayAdapter<Movie> adapter) {
        this.adapter = adapter;
    }

    private URL url(String title) throws MalformedURLException {
        StringBuilder fullURL = new StringBuilder(baseURL);
        fullURL.append("s=").append(title);
        if (type != null)
            fullURL.append("&type=").append(type);
        return new URL(fullURL.toString());
    }

    /**
     * Send a search request to the omdb API and parse the JSON reponse to extract search results.
     * The results are published through publishProgress, so that the UI can interpret them as soon
     * as possible.
     * @param titles movie titles to search for
     */
    @Override
    protected Void doInBackground(String... titles) {
        InputStream response = null;
        Log.d("SearchMovieTask", "search for title " + titles[0]);
        try {
            HttpURLConnection connection = (HttpURLConnection) url(titles[0]).openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000 /* milliseconds */);
            connection.setConnectTimeout(15000 /* milliseconds */);
            response = connection.getInputStream();
            /*
             * According to the github project the JSONTokener should be able to parse directly
             * from the input stream.
             * https://github.com/stleary/JSON-java/blob/master/JSONTokener.java
             */
            StringBuilder jsonSringBuilder = new StringBuilder();
            BufferedReader jsonStreamReader = new BufferedReader(
            new InputStreamReader(response, "UTF-8"));

            String line;
            while ((line = jsonStreamReader.readLine()) != null)
                jsonSringBuilder.append(line);
            Log.d("SearchMovieTask", jsonSringBuilder.toString());
            JSONTokener tokener = new JSONTokener(jsonSringBuilder.toString());
            JSONObject json = new JSONObject(tokener);
            /*
             * Search result should include the fields :
             *   "Search"
             *   "totalResults"
             *   "Response"public
             */
            JSONArray results = json.getJSONArray("Search");
            for (int r = 0; r < results.length(); r++) {
                Movie movie = new Movie();
                try {
                    movie.parse(results.getJSONObject(r));
                    publishProgress(movie);
                } catch (JSONException ex) {
                    Log.e("SearchMovieTask", "JSON exception caught parsing search result");
                }
            }
        } catch (JSONException ex) {
            Log.e("SearchMovieTask", "JSON exception caught");
        } catch (IOException ex) {
            Log.e("SearchMovieTask", "IO exception caught");
        } finally {
            Log.d("SearchMovieTask", "search complete");
            try {
                if (response != null)
                    response.close();
                response = null;
            } catch (IOException ex) {
            }
        }
        return null;
    }

    /**
     * Insert search results into the view.
     * @param movies next search results
     */
    @Override
    protected void onProgressUpdate(Movie... movies) {
        adapter.addAll(movies);
        adapter.notifyDataSetChanged();
        /**
         * Send a request at the same time for full movie information.
         */
        RequestMovieInfoTask task = new RequestMovieInfoTask();
        task.execute(movies);
    }
}
