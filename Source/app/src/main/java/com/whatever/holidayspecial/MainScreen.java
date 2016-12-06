package com.whatever.holidayspecial;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.view.inputmethod.EditorInfo;

import com.whatever.holidayspecial.net.SearchMovieTask;

import java.util.ArrayList;
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

        EditText editText = (EditText) findViewById(R.id.edit_message);
        //editText.setImeOptions(EditorInfo.IME_ACTION_SEARCH);

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent)
            {
                if ((keyEvent==null && actionId==EditorInfo.IME_ACTION_GO) || (actionId == EditorInfo.IME_NULL
                        && keyEvent.getAction() == KeyEvent.ACTION_DOWN))
                {
                    searchMovie(textView);
                    return true;
                }
                return false;
            }
        });
    }


    public void searchMovie(View view) {
        // Prepare the search task.
        EditText editText = (EditText) findViewById(R.id.edit_message);
        SearchMovieTask task = new SearchMovieTask(movieAdapter);
        String title = editText.getText().toString();

        // Hide the keyboard.
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);

        // Clear the view.
        movieList.clear();
        editText.setText("");

        // Start search.
        task.execute(title);
        movieAdapter.notifyDataSetChanged();
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
