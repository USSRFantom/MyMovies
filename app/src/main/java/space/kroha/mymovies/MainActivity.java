package space.kroha.mymovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;

import space.kroha.mymovies.data.Movie;
import space.kroha.mymovies.utils.JSONUtils;
import space.kroha.mymovies.utils.NetworkUtils;

public class MainActivity extends AppCompatActivity {
    private Switch switchSort;
    private RecyclerView recyclerViewPosters;
    private MovieAdapter movieAdapter;
    private TextView textViewTopRated;
    private TextView textViewPopulatity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        switchSort = findViewById(R.id.switchSort);
        textViewTopRated = findViewById(R.id.textViewTopRetad);
        textViewPopulatity = findViewById(R.id.textViewPopularity);
        recyclerViewPosters = findViewById(R.id.recyclerViewPosters);
        recyclerViewPosters.setLayoutManager(new GridLayoutManager(this, 2));
        movieAdapter = new MovieAdapter();
        recyclerViewPosters.setAdapter(movieAdapter);
        switchSort.setChecked(true);
        switchSort.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            setMethodOfSort(isChecked);
            }
        });
            switchSort.setChecked(false);
            movieAdapter.setOnPosterClickListener(new MovieAdapter.OnPosterClickListener() {
                @Override
                public void onPosterClick(int position) {
                    Toast.makeText(MainActivity.this, "Click " + position, Toast.LENGTH_SHORT).show();
                }
            });
            movieAdapter.setOnReachEndListener(new MovieAdapter.OnReachEndListener() {
                @Override
                public void OnReachEnd() {
                    Toast.makeText(MainActivity.this, "Конец списка", Toast.LENGTH_SHORT).show();
                }
            });
        }

    public void onClickSetPopularity(View view) {
        setMethodOfSort(false);
        switchSort.setChecked(false);
    }

    public void onClickSetTopRated(View view) {
        setMethodOfSort(true);
        switchSort.setChecked(true);
    }


    private void setMethodOfSort(boolean isTopRated){
        int methodOfSort;
        if (isTopRated){
            textViewTopRated.setTextColor(getResources().getColor(R.color.colorAccent));
            textViewPopulatity.setTextColor(getResources().getColor(R.color.white_color));
            methodOfSort = NetworkUtils.TOP_RATED;
        }else{
            textViewPopulatity.setTextColor(getResources().getColor(R.color.colorAccent));
            textViewTopRated.setTextColor(getResources().getColor(R.color.white_color));
            methodOfSort = NetworkUtils.POPULARITY;
        }
        JSONObject jsonObject = NetworkUtils.getJSONFromNetwork(methodOfSort,1);
        ArrayList<Movie> movies = JSONUtils.getMoviesFromJSON(jsonObject);
        movieAdapter.setMovies(movies);
    }
}


























    /* Проверка списка фильмов

            JSONObject jsonObject = NetworkUtils.getJSONFromNetwork(NetworkUtils.TOP_REATED,3);
        ArrayList<Movie> movies = JSONUtils.getMoviesFromJSON(jsonObject);
        StringBuilder builder = new StringBuilder();
        for (Movie movie : movies){
            builder.append(movie.getTitle()).append("\n");
        }
        Log.i("MyREsult", builder.toString());
     */
