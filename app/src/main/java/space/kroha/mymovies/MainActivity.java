package space.kroha.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;

import space.kroha.mymovies.data.Movie;
import space.kroha.mymovies.utils.JSONUtils;
import space.kroha.mymovies.utils.NetworkUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        JSONObject jsonObject = NetworkUtils.getJSONFromNetwork(NetworkUtils.TOP_REATED,3);
        ArrayList<Movie> movies = JSONUtils.getMoviesFromJSON(jsonObject);
        StringBuilder builder = new StringBuilder();
        for (Movie movie : movies){
            builder.append(movie.getTitle()).append("\n");
        }
        Log.i("MyREsult", builder.toString());
        }
    }

