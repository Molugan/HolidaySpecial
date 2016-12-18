package com.whatever.holidayspecial;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.Date;

import android.os.Parcelable;
import android.os.Parcel;

/**
 * Created by Morgane on 26/11/16.
 */

public class Movie implements Parcelable {

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

    public Movie(Parcel in) {
        String[] data = new String[15];
        in.readStringArray(data);

        this.title      = data[0];
        this.genre      = data[1];
        this.type       = data[2];
        this.year       = data[3];
        this.runtime    = data[4];
        this.director   = data[5];
        this.writer     = data[6];
        this.actors     = data[7];
        this.language   = data[8];
        this.plot       = data[9];
        this.poster     = data[10];
        this.metascore  = data[11];
        this.imdbRating = data[12];
        this.imdbVotes  = data[13];
        this.imdbId     = data[14];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {
            this.title,
            this.genre,
            this.type,
            this.year,
            this.runtime,
            this.director,
            this.writer,
            this.actors,
            this.language,
            this.plot,
            this.poster,
            this.metascore,
            this.imdbRating,
            this.imdbVotes,
            this.imdbId
        });
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
