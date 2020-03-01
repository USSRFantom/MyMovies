package space.kroha.mymovies.utils;

import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class NetworkUtils {

    private static final String BASE_URL = "https://api.themoviedb.org/3/discover/movie"; //Базовый url
    private static final String PARAMS_API_KEY = "api_key"; //параметр для ключа
    private static final String PARAMS_LANGUAGE = "language"; // язык
    private static final String PARAMS_SORT_BY = "sort_by"; //параметр сортировки
    private static final String PARAMS_PAGE = "page"; //страница
    private static final String API_KEY = "ae0626cfdbab150eddb9954348ac9aad"; //Ключ Api
    private static final String LANGUAGE_VALUE = "ru-RU"; //наш язык ру
    private static final String SORT_BY_POPULARITY = "popularity.desc"; //сортировка по полулярности
    private static final String SORT_BY_TOP_REATED = "vote_average.desc"; //сорттировка по рейтингу
    public static final int POPULARITY = 0; //создаем интовые переменные для изменения запроса по популярности
    public static final int TOP_REATED = 0; //создаем интовые переменные для изменения запроса по рейтингу

//Метод для формирования запроса начало
    private static URL buildURL(int sortBy, int page) {
        URL result = null; //создаем url который будем возвращать уже готовый
        String methodOfSort; //переменная для хранения метода сортировки

        //проверяем как сортируем по поулярности или рейтингу
        if (sortBy == POPULARITY){
            methodOfSort = SORT_BY_POPULARITY;
        }else {
            methodOfSort = SORT_BY_TOP_REATED;
        }

        //Формируем запрос
        Uri uri = Uri.parse(BASE_URL).buildUpon()    //получили стоку в виде адерса и прекрипили параметры
        .appendQueryParameter(PARAMS_API_KEY, API_KEY) //прикрепляем ключ
        .appendQueryParameter(PARAMS_LANGUAGE, LANGUAGE_VALUE) //прикрепиляем выбранный язык
        .appendQueryParameter(PARAMS_SORT_BY, methodOfSort) //прикрепляем параметр сортировки
        .appendQueryParameter(PARAMS_PAGE, Integer.toString(page)) //прикрепляем номер страницы
        .build();

        try {
            result = new URL(uri.toString()); //возвращаем наш  готовый  url
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return result;
    }
//Метод для формирования запроса конец


//Метод который будет получать JSON из сети. брать сформированную ссылку и отправлять в класс для получения JSONObject
    public static JSONObject getJSONFromNetwork(int sortBy, int page){
        JSONObject result = null;
        URL url = buildURL(sortBy, page); //получаем урл
        try {
            result = new JSONLoadTask().execute(url).get();//отправляем урл в наш класс и возвращаем наш обьект
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }


//Класс который будет получать из интернета наш JSONObject
    private static class JSONLoadTask extends AsyncTask<URL, Void, JSONObject>{ //получаем на собранный урл, возвращаем уже спарсенный JSONObject

        @Override
        protected JSONObject doInBackground(URL... urls) {
            JSONObject result = null;
            if (urls == null || urls.length == 0){
                return result;
            }
            HttpsURLConnection connection = null; //создаем соединение
            try {
                connection = (HttpsURLConnection) urls[0].openConnection(); //открываем соединение
                InputStream inputStream = connection.getInputStream(); //создаем поток ввода
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);//чтение
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader); //создаем bufferedReader чтобы читать строками
                String line = bufferedReader.readLine(); //читаем данные
                StringBuilder builder = new StringBuilder(); //сохраняем сюда нашу строку
                //цикл для чтение всех срок
                while (line != null){
                    builder.append(line); //добавление строки в builder
                    line = bufferedReader.readLine();  //читаем следующую строку
                }
                result = new JSONObject(builder.toString()); //сохранем нашу строку в JSONObject

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null){
                    connection.disconnect();
                }
            }
            return result;
        }
    }
    //Класс который будет получать из интернета наш JSONObject конец

}
