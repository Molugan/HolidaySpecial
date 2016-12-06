package com.whatever.holidayspecial.net;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.whatever.holidayspecial.Movie;

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

/**
 * Created by cergo on 06/12/16.
 */

public class RequestMovieInfoTask extends AsyncTask<Movie, Void, Void> {

    /**
     * Create a new empty task.
     */
    public RequestMovieInfoTask() { }

    /**
     * Format the request URL for downloading information about a movie.
     */
    private URL makeRequestURL(Movie movie) throws MalformedURLException {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("www.omdbapi.com")
                .appendQueryParameter("r", "json")
                .appendQueryParameter("v", "1")
                .appendQueryParameter("i", movie.imdbId)
                .appendQueryParameter("type", movie.type)
                .appendQueryParameter("plot", "short");
        return new URL(builder.build().toString());
    }

    /**
     * Send a request to the omdb API to obtain information about a movie.
     * @param movies requested movies
     */
    @Override
    protected Void doInBackground(Movie... movies) {
        /*
         * Send an information request for each selected movie. Failure in one request doesn't
         * impact the following requests.
         */
        for (int i = 0; i < movies.length; i++) {
            InputStream response = null;
            try {
                URL url = makeRequestURL(movies[i]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
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
                JSONTokener tokener = new JSONTokener(jsonSringBuilder.toString());
                JSONObject json = new JSONObject(tokener);
                movies[i].parse(json);
            } catch (IOException ex) {
                Log.e("RequestMovieInfoTask", "IO exception caught: " + ex.getMessage());
            } catch (JSONException ex) {
                Log.e("RequestMovieInfoTask", "JSON exception caught: " + ex.getMessage());
            } catch (Exception ex) {
                Log.e("RequestMovieInfoTask", "exception caught: " + ex.getMessage());
            } finally {
                try {
                    if (response != null)
                        response.close();
                    response = null;
                } catch (IOException ex) {
                }
            }
            /* Stop if cancelled. */
            if (isCancelled())
                return null;
        }
        return null;
    }


}
