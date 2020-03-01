package space.kroha.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONObject;

import space.kroha.mymovies.utils.NetworkUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        JSONObject jsonObject = NetworkUtils.getJSONFromNetwork(NetworkUtils.TOP_REATED,3);
        if (jsonObject == null){
            Toast.makeText(this, "Успешно", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "ОШИБКА", Toast.LENGTH_SHORT).show();
        }
    }
}
