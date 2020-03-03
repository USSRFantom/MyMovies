package space.kroha.mymovies.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import space.kroha.mymovies.data.Movie;

public class JSONUtils {
    private static final String KEY_RESULTS = "results"; //ключ к массиву
    private static final String KEY_VOTE_COUNT = "vote_count"; //ключ к колличеству голосов
    private static final String KEY_ID = "id"; //ключ к id
    private static final String KEY_TITLE = "title"; //ключ к title
    private static final String KEY_ORIGINAL_TITLE = "original_title"; //ключ к original_title
    private static final String KEY_OVERVIEW = "overview"; //ключ к описанию
    private static final String KEY_POSTER_PATH = "poster_path"; //ключ к постеру
    private static final String KEY_BACKDROP_PATH = "backdrop_path"; //ключ к основному постеру
    private static final String KEY_VOTE_AVERAGE = "vote_average"; //ключ к рейтинг
    private static final String KEY_RELEASE_DATE = "release_date"; //ключ к рейтинг

    public static ArrayList<Movie> getMoviesFromJSON(JSONObject jsonObject){
        ArrayList<Movie> result = new ArrayList<>(); //создаем  List для хранения

        if (jsonObject == null){
            return result;
        }

        try {
            JSONArray jsonArray = jsonObject.getJSONArray(KEY_RESULTS); //получаем массив обьектов
            //получаем фильмы
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject objectMovie = jsonArray.getJSONObject(i);
                int id = objectMovie.getInt(KEY_ID);
                int voteCount = objectMovie.getInt(KEY_VOTE_COUNT);
                String title = objectMovie.getString(KEY_TITLE);
                String originalTitle = objectMovie.getString(KEY_ORIGINAL_TITLE);
                String overview = objectMovie.getString(KEY_OVERVIEW);
                String posterPath = objectMovie.getString(KEY_POSTER_PATH);
                String backdropPath = objectMovie.getString(KEY_BACKDROP_PATH);
                double voteAverage = objectMovie.getDouble(KEY_VOTE_AVERAGE);
                String releaseDate = objectMovie.getString(KEY_RELEASE_DATE);
                //создаем обьект и передаем все данные
                Movie movie = new Movie(id, voteCount,title, originalTitle, overview, posterPath, backdropPath, voteAverage, releaseDate);
                result.add(movie); // передаем в массив наш фильм
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}
