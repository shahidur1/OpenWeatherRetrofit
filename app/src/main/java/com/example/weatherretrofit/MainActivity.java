package com.example.weatherretrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    String baseUrl;
    String apiKey;
    String cityCountry;
    String url;
    EditText city;
    TextView result;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        baseUrl = "https://api.openweathermap.org/data/2.5/";
        apiKey = "a7fee9e6500f292304ce5c8cf4ed5a94";
        city = findViewById(R.id.city);
        result = findViewById(R.id.result);
        imageView = findViewById(R.id.imageView);
    }

    public void showData(View view) {
        String id = city.getText().toString().trim();
        //url = "weather?id=" + id + "&appid=" + apiKey;
        url = "weather?q=" + id + "&appid=" + apiKey;
        ServiceInterface serviceInterfac = MyRetrofitObject.getRetrofitObject(baseUrl).create(ServiceInterface.class);
        serviceInterfac.getWeatherInfo(url.trim()).enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                WeatherData weatherData = response.body();
                String icon=weatherData.weather.get(0).getIcon();
                Picasso.get().load("https://openweathermap.org/img/w/"+icon+".png").into(imageView);
                String country = "Country: " + weatherData.getSys().getCountry().toString() +
                        "\n City: " + weatherData.getName();


                Toast.makeText(getBaseContext(), country, Toast.LENGTH_LONG).show();
                result.setText(country);
            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                result.setText(t.getMessage());
            }
        });
    }
}
