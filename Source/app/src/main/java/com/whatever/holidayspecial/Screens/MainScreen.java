package com.whatever.holidayspecial.Screens;

/**
 * Created by Morgane on 07/12/16.
 */

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;

import com.whatever.holidayspecial.R;
import com.whatever.holidayspecial.Movie;

public class MainScreen extends AppCompatActivity {

    public final static int FIND_MOVIE_REQUEST = 0;
    public Button planNewMovieButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        planNewMovieButton = (Button) findViewById(R.id.searchMovie);
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
                planNewMovieButton.setText(selectedMovie.title);
            }

        }

    }


}
