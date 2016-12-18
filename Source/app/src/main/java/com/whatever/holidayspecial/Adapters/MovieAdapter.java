package com.whatever.holidayspecial.Adapters;

import android.view.View;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.content.Context;
import android.view.ViewGroup;

import com.whatever.holidayspecial.Movie;
import com.whatever.holidayspecial.R;

import java.util.ArrayList;

public class MovieAdapter extends ArrayAdapter<Movie> {

    public int mSelectedItem;
    protected int m_layout;

    public MovieAdapter(Context context, ArrayList<Movie> movies) {

        super(context, 0, movies);
        mSelectedItem = -1;
        m_layout = 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        Movie movie = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    m_layout, parent, false);
        }

        // Init the selected view
        InitViewFromMovie(movie, convertView);

        // Return the completed view to render on screen
        if (position == mSelectedItem) {
            convertView.setBackgroundColor(android.graphics.Color.GRAY);
        }
        else {
            convertView.setBackgroundColor(android.graphics.Color.WHITE);
        }

        return convertView;

    }

    //Must be overrided
    protected void InitViewFromMovie(Movie movie, View convertView){
    }

}

