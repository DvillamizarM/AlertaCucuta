package com.example.alertacucuta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;


public class MainActivity extends AppCompatActivity  implements OnMapReadyCallback {
    private SupportMapFragment mapFragment;
    private LinearLayout menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       mapFragment = SupportMapFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.view, mapFragment)
                .commit();
        mapFragment.getMapAsync(this);
        menu = findViewById(R.id.menu);

  }

    //boilerplate gMaps

    @Override
    protected void onResume() {
        super.onResume();
        mapFragment.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapFragment.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapFragment.onStop();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        UiSettings mUiSettings = googleMap.getUiSettings();
        LatLngBounds cucutaBounds = new LatLngBounds(
                new LatLng(7.797340, -72.564975), // SW bounds
                new LatLng(7.959478, -72.454595)  // NE bounds
        );
       googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(cucutaBounds, 0));
       googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
       mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setZoomGesturesEnabled(true);
     }

    @Override
    protected void onPause() {
        mapFragment.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mapFragment.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapFragment.onLowMemory();
    }

    //boilerplate gMaps


    public void showMenu(View view){
        if (menu.getVisibility()==View.INVISIBLE) menu.setVisibility(View.VISIBLE);
        else menu.setVisibility(View.INVISIBLE);
        menu.bringToFront();
    }

    public void showAccidenteRegistro(View view){
        Intent intent = new Intent(this, RegistroAccidente.class);
        startActivity(intent);
    }

    public void showDesastreRegistro(View view){
        Intent intent = new Intent(this, RegistroDesastre.class);
        startActivity(intent);
    }
    public void showCrimenRegistro(View view){
        Intent intent = new Intent(this, RegistroCrimen.class);
        startActivity(intent);
    }

}