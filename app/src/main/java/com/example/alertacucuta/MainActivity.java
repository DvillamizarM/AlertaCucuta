package com.example.alertacucuta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PostProcessor;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.alertacucuta.Objetos.Accidente;
import com.example.alertacucuta.Objetos.Crimen;
import com.example.alertacucuta.Objetos.Marcador;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity  implements OnMapReadyCallback {
    private SupportMapFragment mapFragment;

    @BindView(R.id.menu) LinearLayout menu;
    @BindView(R.id.imageButton2) ImageButton caiInfo;

    private FirebaseDatabase mFirebase;
    private DatabaseReference mReference;
    private Geocoder geocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        geocoder = new Geocoder(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //boilerplate map
       mapFragment = SupportMapFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.view, mapFragment)
                .commit();
        mapFragment.getMapAsync(this);


        //database
        mFirebase = FirebaseDatabase.getInstance();
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
       recolectarDirecciones("accidentes", "yellow_alert", googleMap);
        recolectarDirecciones("desastres", "blue_alerta", googleMap);
       recolectarDirecciones("crimenes", "red_alert", googleMap);

    }
    //end boilerplate gMaps


     public ArrayList<Marcador> recolectarDirecciones(String tipo, String icon, GoogleMap googleMap){
        ArrayList<Marcador> direcciones = new ArrayList<>();
         mReference = mFirebase.getReference(tipo);
        Query direccion = mReference.orderByChild("direccion");

        direccion.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                if(snapshot.exists()){
                   for(DataSnapshot dss :  snapshot.getChildren()){
                       Marcador item = new Marcador();
                       item.setCoordinates(conversionDireccion(dss.child("direccion").getValue(String.class)));
                       item.setId(dss.getKey());
                       item.setTipo(tipo);
                       direcciones.add(item);
                    }
                    for(Marcador m : direcciones){
                     if(m.getCoordinates()!=null) {
                         Marker marker = googleMap.addMarker(new MarkerOptions()
                                 .position(m.getCoordinates())
                                 .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(icon, 100, 120))));
                     }
                    // else if(m.getCoordinates()==null) Toast.makeText(getApplicationContext(), "Error cargando ubicaciones intent luego ", Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        return direcciones;
     }

    public Bitmap resizeMapIcons(String iconName,int width, int height){
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(iconName, "drawable", getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }


    public LatLng conversionDireccion(String direccion){
         LatLng latLng = null;
         List<Address> addresses;
         if(direccion != null) {
             try {
                 addresses = geocoder.getFromLocationName(direccion, 1);
                 if (addresses.size() > 0)
                     latLng = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
        return latLng;
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
        if (menu.getVisibility()==View.INVISIBLE){
            menu.setVisibility(View.VISIBLE);
            caiInfo.setVisibility(View.VISIBLE);
        }
        else{
            menu.setVisibility(View.INVISIBLE);
            caiInfo.setVisibility(View.INVISIBLE);
        }
        menu.bringToFront();
        caiInfo.bringToFront();
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

    public void showCAIsInfo(View view){
        Intent intent = new Intent(this, ListadoCAIs.class);
        startActivity(intent);
    }

}