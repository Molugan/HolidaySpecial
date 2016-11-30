package start.morgane.holidayspecial;

import android.view.View;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.Context;
import android.view.ViewGroup;
import java.util.ArrayList;

import start.morgane.holidayspecial.Movie;

public class MovieAdapter extends ArrayAdapter<Movie> {

    public int mSelectedItem;

    public MovieAdapter(Context context, ArrayList<Movie> movies) {

        super(context, 0, movies);
        mSelectedItem = -1;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        Movie movie = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_movie_item, parent, false);
        }

        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.name);

        // Populate the data into the template view using the data object
        tvName.setText(movie.name);

        // Return the completed view to render on screen
        if(position == mSelectedItem){
            convertView.setBackgroundColor(android.graphics.Color.GRAY);
        }
        else{
            convertView.setBackgroundColor(android.graphics.Color.WHITE);
        }

        return convertView;

    }

}