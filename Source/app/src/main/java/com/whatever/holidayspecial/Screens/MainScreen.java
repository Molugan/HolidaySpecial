package com.whatever.holidayspecial.Screens;

/**
 * Created by Morgane on 07/12/16.
 */

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.whatever.holidayspecial.R;
import com.whatever.holidayspecial.Movie;
import com.whatever.holidayspecial.Adapters.MovieAdapterSearchScreen;

import java.util.ArrayList;

public class MainScreen extends AppCompatActivity {

    public final static int FIND_MOVIE_REQUEST = 0;
    public Button planNewMovieButton;

    private ArrayList<Movie> movieList;
    private MovieAdapterSearchScreen movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        planNewMovieButton = (Button) findViewById(R.id.searchMovie);

        movieList = new ArrayList<Movie>();
        movieAdapter = new MovieAdapterSearchScreen(this, movieList);
        ListView listV = (ListView) findViewById(R.id.selected_movies_list);
        listV.setAdapter(movieAdapter);
        listV.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        listV.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long arg3) {
                view.setSelected(true);

                movieAdapter.mSelectedItem = position;
                movieAdapter.notifyDataSetChanged();
            }});
    }

    public void planMovie(View view){

        Intent intent = new Intent(this, MovieSearchScreen.class);
        startActivityForResult(intent, FIND_MOVIE_REQUEST);
    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // On vérifie tout d'abord à quel intent on fait référence ici à l'aide de notre identifiant

        if (requestCode == FIND_MOVIE_REQUEST) {

            // On vérifie aussi que l'opération s'est bien déroulée

            if (resultCode == RESULT_OK) {

                // On affiche le bouton qui a été choisi
                Movie selectedMovie = (Movie) data.getParcelableExtra("SELECTED_MOVIE");
                movieList.add(selectedMovie);
                movieAdapter.notifyDataSetChanged();
            }

        }

    }


}
