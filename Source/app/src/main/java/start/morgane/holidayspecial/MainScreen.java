package start.morgane.holidayspecial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.content.Context;
import android.widget.AdapterView.OnItemClickListener;

import start.morgane.holidayspecial.Movie;
import start.morgane.holidayspecial.MovieAdapter;

import java.util.ArrayList;

public class MainScreen extends AppCompatActivity {

    private ArrayList<Movie> movieList;
    private MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        movieList = new ArrayList<Movie>();
        movieAdapter = new MovieAdapter(this, movieList);
        ListView listV = (ListView) findViewById(R.id.movie_list);
        listV.setAdapter(movieAdapter);
        listV.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        listV.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long arg3) {
                view.setSelected(true);
                MovieAdapter mp = (MovieAdapter) parent.getAdapter();
                MovieView movieView = (MovieView) findViewById(R.id.movie_screen);

                movieAdapter.mSelectedItem = position;
                movieAdapter.notifyDataSetChanged();
            }});
    }


    public void addMovie(View view) {
        EditText editText = (EditText) findViewById(R.id.edit_message);
        Movie newMovie = new Movie(editText.getText().toString());
        movieAdapter.add(newMovie);
        //MovieView currentMovie = (MovieView) movieList.getChildAt(0);

        //currentMovie.SetMovieTitle(editText.getText().toString());
    }

    public void getMovie(View view){
        if(movieList.size() == 0){
            return;
        }

        //ListView lv = (ListView) findViewById(R.id.movie_list);
        //int position = lv.getPositionForView(view);
        //Movie currentMovie = movieList.get(position);
    }

}
