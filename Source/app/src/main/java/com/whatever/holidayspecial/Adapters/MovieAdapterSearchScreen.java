package com.whatever.holidayspecial.Adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.whatever.holidayspecial.Movie;
import com.whatever.holidayspecial.R;

import java.util.ArrayList;

/**
 * Created by Morgane on 18/12/16.
 */

public class MovieAdapterSearchScreen extends MovieAdapter{


    public MovieAdapterSearchScreen(Context context, ArrayList<Movie> movies) {

        super(context, movies);
        mSelectedItem = -1;
        m_layout = R.layout.view_movie_item;
    }

    @Override
    protected void InitViewFromMovie(Movie movie, View convertView){

        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.name);

        // Populate the data into the template view using the data object
        tvName.setText(movie.title);
    }
}