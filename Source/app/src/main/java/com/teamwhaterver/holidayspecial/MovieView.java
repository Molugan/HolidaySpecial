package com.teamwhaterver.holidayspecial;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Morgane on 26/11/16.
 */

public class MovieView extends LinearLayout {

    private View mValue;
    private ImageView mImage;
    private TextView movieTitle;
    private TextView movieSynopsis;

    public MovieView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MovieView);
        String movieName = a.getString(R.styleable.MovieView_name);
        String synopsis = a.getString(R.styleable.MovieView_synopsis);
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

        mImage =(ImageView) findViewById(R.id.poster);
        mImage.setImageResource(R.drawable.chatcool);
    }

    public void updateFromMovie(Movie movie){

        movieTitle.setText(movie.title);
        movieSynopsis.setText(movie.synopsis);
    }

    public void reset(){
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
