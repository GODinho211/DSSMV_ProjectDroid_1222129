package com.example.mobileapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.mobileapp.config.MySingleton;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;


public class WeatherFragment extends Fragment {

    public static final String QUERY_FOR_CITY_ID = "https://api.openweathermap.org/data/2.5/weather?q=";
    private static final String KEY = "781c9f404cad03d99cba3526779c63c1";
    private ImageView etlogo;
   // private Button forecast;
    private Button current;
    private Button local;
    private EditText etLocation;
    private EditText etTempVal;
    private EditText etSensVal;
    private EditText etTempMaxVal;
    private EditText etTempMinVal;
    private EditText etHumidadeVal;
    private Double latitude;
    private Double longitude;
    FusedLocationProviderClient fusedLocationProviderClient;

    public WeatherFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        //forecast = (Button) view.findViewById(R.id.forescastButton);
        current = (Button)view.findViewById(R.id.currentButton);
        etLocation = view.findViewById(R.id.location);
        etTempVal = view.findViewById(R.id.tempVal);
        etSensVal = view.findViewById(R.id.sensacaoVal);
        etTempMaxVal = view.findViewById(R.id.tempMaxVal);
        etTempMinVal = view.findViewById(R.id.tempMinVal);
        etHumidadeVal = view.findViewById(R.id.humidadeVal);
        etlogo = (ImageView) view.findViewById(R.id.logoWeather);
        local = (Button)view.findViewById(R.id.localDataButton);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(container.getContext());


        current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = QUERY_FOR_CITY_ID +etLocation.getText().toString()+ "&appid=" + KEY;
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Double temp=0.0;
                        Double tempMax =0.0;
                        Double tempMin=0.0;
                        Integer humidade=0;
                        Double sensaçao=0.0;
                        String descriçao="";
                        try {
                            JSONObject tempInfo = response.getJSONObject("main");
                             temp = (tempInfo.getDouble("temp")-273.15);
                             tempMax = (tempInfo.getDouble("temp_max")-273.15);
                             tempMin = (tempInfo.getDouble("temp_min")-273.15);
                             sensaçao = (tempInfo.getDouble("feels_like")-273.15);
                             humidade = tempInfo.getInt("humidity");
                            JSONArray values = response.getJSONArray("weather");
                            JSONObject weather = values.getJSONObject(0);
                            descriçao = weather.getString("main");



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        DecimalFormat frmt = new DecimalFormat("#.0");
                        etTempVal.setText(frmt.format(temp)+" ºC");
                        etTempMaxVal.setText(frmt.format(tempMax)+" ºC");
                        etTempMinVal.setText(frmt.format(tempMin)+" ºC");
                        etHumidadeVal.setText(frmt.format(humidade));
                        etSensVal.setText(frmt.format(sensaçao)+" ºC");
                        switch(descriçao){
                            case "Thunderstorm":
                                etlogo.setImageResource(R.drawable.thunderstorm);
                                break;
                            case "Drizzle":
                                etlogo.setImageResource(R.drawable.ligthrain);
                                break;
                            case "Rain":
                                etlogo.setImageResource(R.drawable.rain);
                                break;
                            case "Snow":
                                etlogo.setImageResource(R.drawable.snow);
                                break;
                            case "Mist":
                                etlogo.setImageResource(R.drawable.haze);
                                break;
                            case "Smoke":
                                etlogo.setImageResource(R.drawable.haze);
                                break;
                            case "Haze":
                                etlogo.setImageResource(R.drawable.haze);
                                break;
                            case "Dust":
                                etlogo.setImageResource(R.drawable.haze);
                                break;
                            case "Fog":
                                etlogo.setImageResource(R.drawable.haze);
                                break;
                            case "Sand":
                                etlogo.setImageResource(R.drawable.haze);
                                break;
                            case "Ash":
                                etlogo.setImageResource(R.drawable.haze);
                                break;
                            case "Squall":
                                etlogo.setImageResource(R.drawable.haze);
                                break;
                            case "Tornado":
                                etlogo.setImageResource(R.drawable.haze);
                                break;
                            case "Clear":
                                etlogo.setImageResource(R.drawable.icons8_sun_star_96);
                                break;
                            case "Clouds":
                                etlogo.setImageResource(R.drawable.clouds);
                                break;

                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                });
                MySingleton.getInstance(getContext()).addToRequestQueue(request);
            }
        });

        //forecast.setOnClickListener(new View.OnClickListener() {
            //@Override
           // public void onClick(View view) {
                //Navigation.findNavController(view).navigate(R.id.action_weatherFragment_to_forecastFragment);
            //}
        //});

        local.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ActivityCompat.checkSelfPermission(container.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    ActivityCompat.requestPermissions((Activity) container.getContext(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
                String url="https://api.openweathermap.org/data/2.5/weather?lat="+String.valueOf(latitude)+"&lon="+String.valueOf(longitude)+"&appid=" + KEY;

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Double temp=0.0;
                        Double tempMax =0.0;
                        Double tempMin=0.0;
                        Integer humidade=0;
                        Double sensaçao=0.0;
                        String cityName="";
                        try {
                            JSONObject tempInfo = response.getJSONObject("main");
                            temp = (tempInfo.getDouble("temp")-273.15);
                            tempMax = (tempInfo.getDouble("temp_max")-273.15);
                            tempMin = (tempInfo.getDouble("temp_min")-273.15);
                            sensaçao = (tempInfo.getDouble("feels_like")-273.15);
                            humidade = tempInfo.getInt("humidity");
                            cityName = response.getString("name");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        DecimalFormat frmt = new DecimalFormat("#.0");
                        etLocation.setText(cityName);
                        etTempVal.setText(frmt.format(temp)+" ºC");
                        etTempMaxVal.setText(frmt.format(tempMax)+" ºC");
                        etTempMinVal.setText(frmt.format(tempMin)+" ºC");
                        etHumidadeVal.setText(frmt.format(humidade));
                        etSensVal.setText(frmt.format(sensaçao)+" ºC");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                });
                MySingleton.getInstance(getContext()).addToRequestQueue(request);
            }
        });
        return view;
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    try {
                        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        latitude = addresses.get(0).getLatitude();
                        longitude = addresses.get(0).getLongitude();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
