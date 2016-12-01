package start.morgane.holidayspecial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ExecutionException;

public class MainScreen extends AppCompatActivity {

    private ArrayList<Movie> movieList;
    private MovieAdapter movieAdapter;
    private MovieView movieView;

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
                movieView = (MovieView) findViewById(R.id.movie_screen);

                movieView.updateFromMovie(movieList.get(position));
                movieAdapter.mSelectedItem = position;
                movieAdapter.notifyDataSetChanged();
            }});
    }


    public void addMovie(View view) {
        EditText editText = (EditText) findViewById(R.id.edit_message);
        MovieSearchRequest request = new MovieSearchRequest(editText.getText().toString());
        request.execute();
        List<Movie> movies = null;
        try {
            movies = request.get();
        } catch (InterruptedException ex) {
            Log.d("MainScreen", "search was interrupted");
        } catch (ExecutionException ex) {
            Log.d("MainScreen", "search failed for an unknown reason");
        }

        if (movies == null || movies.isEmpty())
            return;
        ListIterator<Movie> iterator = movies.listIterator();
        while (iterator.hasNext()) {
            movieAdapter.add(iterator.next());
        }
        editText.setText("");

        //MovieView currentMovie = (MovieView) movieList.getChildAt(0);
        //currentMovie.SetMovieTitle(editText.getText().toString());
    }

    public void removeMovie(View view) {
        int selectedMovie = movieAdapter.mSelectedItem;

        if(selectedMovie == -1)
        {
            return;
        }

        movieList.remove(selectedMovie);
        selectedMovie--;

        if(selectedMovie < 0 && movieList.size() > 0){
            selectedMovie = 0;
        }

        movieAdapter.mSelectedItem = selectedMovie;

        if(selectedMovie > -1){
            ListView listV = (ListView) findViewById(R.id.movie_list);
            listV.setSelection(selectedMovie);
            movieView = (MovieView) findViewById(R.id.movie_screen);

            movieView.updateFromMovie(movieList.get(selectedMovie));
        }
        else{
            movieView.reset();
        }
        movieAdapter.notifyDataSetChanged();
    }

    public void getMovie(View view) {
        if (movieList.isEmpty())
            return;

        //ListView lv = (ListView) findViewById(R.id.movie_list);
        //int position = lv.getPositionForView(view);
        //Movie currentMovie = movieList.get(position);
    }

}
