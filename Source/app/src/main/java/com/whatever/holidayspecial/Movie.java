package com.whatever.holidayspecial;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Morgane on 26/11/16.
 */

public class Movie {

    public String title;
    public String genre;
    public String type;
    public String year;
    public String runtime;
    public String director;
    public String writer;
    public String actors;
    public String language;
    public String plot;
    public String synopsis;
    public String poster;

    public String metascore;
    public String imdbRating;
    public String imdbVotes;
    public String imdbId;

    /**
     * Extract movie information from omdb JSON response.
     * @param json omdb search result
     */
    public void parse(JSONObject json) throws JSONException {
        /* Required parameters. */
        title = json.getString("Title");
        year = json.getString("Year");
        type = json.getString("Type");
        poster = json.getString("Poster");
        imdbId = json.getString("imdbID");
        /* Optional parameters. */
        genre = json.optString("Genre");
        runtime = json.optString("Runtime");
        director = json.optString("Director");
        writer = json.optString("Writer");
        actors = json.optString("Actors");
        language = json.optString("Language");
        plot = json.optString("Plot");
        metascore = json.optString("Metascore");
        imdbRating = json.optString("imdbRatings");
        imdbVotes = json.optString("imdbVotes");
    }

    public Movie() {
    }
}
