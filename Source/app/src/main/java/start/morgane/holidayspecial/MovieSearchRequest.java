package start.morgane.holidayspecial;

import java.io.*;
import java.net.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import org.json.*;

/**
 * Created by Henri Chataing on 30/11/16.
 */

public class MovieSearchRequest {

    public String title;
    public String type;
    public String plot;

    static String baseURL = "https://www.omdbapi.com/?r=json&";

    public MovieSearchRequest(String title) {
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

    public List<Movie> results() {
        LinkedList<Movie> movies = new LinkedList<Movie>();
        /* Fetch result from the omdb API. */
        try {
            URLConnection connection = url().openConnection();
            InputStream response = connection.getInputStream();
            /*
             * According to the github project the JSONTokener should be able to parse directly
             * from the input stream.
             * https://github.com/stleary/JSON-java/blob/master/JSONTokener.java
             */
            JSONTokener tokener = new JSONTokener(response.toString());
            JSONObject json = new JSONObject(tokener);
            /*
             * Search result should include the fields :
             *   "Search"
             *   "totalResults"
             *   "Response"
             */
            JSONArray results = json.getJSONArray("Results");
            for (int r = 0; r < results.length(); r++) {
                Movie movie = new Movie();
                try {
                    movie.parse(results.getJSONObject(r));
                    movies.addLast(movie);
                } catch (JSONException ex) {
                }
            }
        } catch (Exception ex) {
        }
        return movies;
    }
}
