package com.example.vn008xw.carbeat;

import com.example.vn008xw.carbeat.CarBeatApp;
import com.example.vn008xw.carbeat.MainActivity;
import com.example.vn008xw.carbeat.ui.movie.MovieFragment;
import com.example.vn008xw.carbeat.ui.movies.MoviesFragment;

/**
 * Created by vn008xw on 6/6/17.
 */

public interface AppComponent {
    void inject(CarBeatApp carBeatApp);

    void inject(MainActivity mainActivity);

    void inject(MoviesFragment moviesFragment);

    void inject(MovieFragment movieFragment);
}
