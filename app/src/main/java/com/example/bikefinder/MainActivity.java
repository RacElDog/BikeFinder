package com.example.bikefinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.icu.util.Calendar;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;







public class MainActivity extends AppCompatActivity{
    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Log.i("location manager set", "yes");
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,  10000, 0, locationListener);
            }
            else{
                Log.i("Location pERmission", "denied");
            }
        }
    }
    
   ListView listView;
  
  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final DatabaseHandler myDb;
        myDb = new DatabaseHandler(this);



        locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                 Log.i("Location", location.toString());
                 Log.i("Speed", "" + location.getSpeed());
                 double coordinates1 = location.getLatitude();
                 double coordinates2 = location.getLongitude();
                 String time = Calendar.getInstance().getTime().toString();
                 boolean isInserted = myDb.insertData(coordinates1,coordinates2,time);
                 if (isInserted)
                     Log.i("database", "hey, there is new data!!!");
                 else
                     Log.i("database", "hey, there is NO new data");

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }

          
        };
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            Log.i("Location Permission", "Requested");
        }else{
            Log.i("Location Permission", "Already Granted");
        }

        listView = (ListView)findViewById(R.id.listview);

        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add("1st location");
        arrayList.add("2nd location");
        arrayList.add("3rd location");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);

        listView.setAdapter(arrayAdapter);

    }




}

