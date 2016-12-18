package com.whatever.holidayspecial.Screens;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.view.inputmethod.EditorInfo;

import com.whatever.holidayspecial.Movie;
import com.whatever.holidayspecial.Adapters.MovieAdapterSearchScreen;
import com.whatever.holidayspecial.MovieView;
import com.whatever.holidayspecial.R;
import com.whatever.holidayspecial.net.SearchMovieTask;

import java.util.ArrayList;

public class MovieSearchScreen extends AppCompatActivity {

    private ArrayList<Movie> movieList;
    private MovieAdapterSearchScreen movieAdapter;
    private MovieView movieView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);

        movieList = new ArrayList<Movie>();
        movieAdapter = new MovieAdapterSearchScreen(this, movieList);
        ListView listV = (ListView) findViewById(R.id.movie_list);
        listV.setAdapter(movieAdapter);
        listV.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        listV.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long arg3) {
                view.setSelected(true);
                MovieAdapterSearchScreen mp = (MovieAdapterSearchScreen) parent.getAdapter();
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
                if ((keyEvent==null && actionId==EditorInfo.IME_ACTION_SEARCH) || (actionId == EditorInfo.IME_NULL
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
        SearchMovieTask task = new SearchMovieTask(movieList, movieAdapter);
        String title = editText.getText().toString();

        //Hide the keyboard
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);

        //Clear the list
        movieList.clear();

        //Clear the text bar
        editText.setText("");

        // Start search.
        task.execute(title);
        movieAdapter.notifyDataSetChanged();
    }

    public void selectMovie(View view) {
        if (movieList.isEmpty())
            return;

        Movie currentMovie = movieList.get(movieAdapter.mSelectedItem);
        Intent intent = new Intent(this, MainScreen.class);
        intent.putExtra("SELECTED_MOVIE", currentMovie);
        setResult(RESULT_OK, intent);
        finish();
    }

}
