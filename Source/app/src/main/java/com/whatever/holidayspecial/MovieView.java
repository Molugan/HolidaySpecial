package com.whatever.holidayspecial;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.whatever.holidayspecial.net.ImageTask;

/**
 * Created by Morgane on 26/11/16.
 */

public class MovieView extends LinearLayout {

    private View mValue;
    private ImageView moviePoster;
    private TextView movieTitle;
    private TextView movieSynopsis;
    private TextView releaseDate;

    public MovieView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MovieView);
        String movieName = a.getString(R.styleable.MovieView_name);
        String synopsis = a.getString(R.styleable.MovieView_synopsis);
        String date = a.getString(R.styleable.MovieView_releaseDate);
        int valueColor = a.getColor(R.styleable.MovieView_imgColor, getResources().getColor(android.R.color.holo_blue_light));
        a.recycle();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_movie, this, true);

        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

        movieTitle = (TextView) findViewById(R.id.name);
        movieTitle.setText(movieName);

        movieSynopsis = (TextView) findViewById(R.id.synopsis);
        movieSynopsis.setText(synopsis);

        moviePoster = (ImageView) findViewById(R.id.poster);
        moviePoster.setImageResource(R.drawable.shrek);

        releaseDate = (TextView) findViewById(R.id.releaseDate);
        releaseDate.setText(date);
    }

    public void updateFromMovie(Movie movie) {
        movieTitle.setText(movie.title);
        movieSynopsis.setText(movie.synopsis);
        releaseDate.setText(movie.released);
        ImageTask task = new ImageTask(moviePoster);
        task.execute(movie.poster);
    }

    public void reset() {
        movieTitle.setText(R.string.default_movie);
        movieSynopsis.setText(R.string.default_synopsis);
    }

    public MovieView(Context context) {
        this(context, null);
    }

    public void SetMovieTitle(String title){
        movieTitle.setText(title);
    }
}
