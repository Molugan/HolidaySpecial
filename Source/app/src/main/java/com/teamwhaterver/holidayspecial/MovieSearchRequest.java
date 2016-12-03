package com.teamwhaterver.holidayspecial;

import android.os.AsyncTask;
import android.util.Log;

import java.io.*;
import java.net.*;
import java.util.LinkedList;
import java.util.List;

import org.json.*;

/**
 * Created by Henri Chataing on 30/11/16.
 */

public class MovieSearchRequest extends AsyncTask<Void, Void, List<Movie>> {

    public String title;
    public String type;
    public String plot;

    static String baseURL = "https://www.omdbapi.com/?r=json&";

    public MovieSearchRequest(String title) {
        Log.d("MovieSearch", "request for title " + title);
        this.title = title;
    }

    private URL url() throws MalformedURLException {
        StringBuilder fullURL = new StringBuilder(baseURL);
        fullURL.append("s=").append(title);
        if (type != null)
            fullURL.append("&type=").append(type);
        if (plot != null)
            fullURL.append("&plot=").append(plot);
        return new URL(fullURL.toString());
    }

    protected List<Movie> doInBackground(Void... _) {
        LinkedList<Movie> movies = new LinkedList<Movie>();
        InputStream response = null;
        /* Fetch result from the omdb API. */
        try {
            HttpURLConnection connection = (HttpURLConnection) url().openConnection();
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
            Log.d("MovieSearch", jsonSringBuilder.toString());
            JSONTokener tokener = new JSONTokener(jsonSringBuilder.toString());
            JSONObject json = new JSONObject(tokener);
            /*
             * Search result should include the fields :
             *   "Search"
             *   "totalResults"
             *   "Response"
             */
            JSONArray results = json.getJSONArray("Search");
            for (int r = 0; r < results.length(); r++) {
                Movie movie = new Movie();
                try {
                    movie.parse(results.getJSONObject(r));
                    movies.addLast(movie);
                } catch (JSONException ex) {
                    Log.e("MovieSearch", "JSON excepion caught parsing search result");
                }
            }
        } catch (JSONException ex) {
            Log.e("MovieSearch", "JSON exception caught");
        } catch (IOException ex) {
            Log.e("MovieSearch", "IO exception caught");
        } finally {
            Log.d("MovieSearch", "search complete");
            //    response.close();
            // if (response != null)
        }
        return movies;
    }
}
