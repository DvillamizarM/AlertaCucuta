package com.example.alertacucuta;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.alertacucuta.Objetos.Marcador;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.FetchPhotoResponse;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity  implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private static final String TAG = "MainActivity";
    private ArrayList<Marcador> accidentes;
    private ArrayList<Marcador> desastres;
    private ArrayList<Marcador> crimenes;
    private SupportMapFragment mapFragment;
    private boolean caiClicked = false;

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

        Places.initialize(getApplicationContext(), "AIzaSyCJE4v1u_fCq2X8TAa1FyjHMo_c6P8oW5I");

        //boilerplate map
       mapFragment = SupportMapFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.view, mapFragment)
                .commit();
        mapFragment.getMapAsync(this);


        //database
        mFirebase = FirebaseDatabase.getInstance();

        caiInfo.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Dale click al mapa para refrescar", Toast.LENGTH_LONG).show();
            if(caiClicked) caiClicked = false;
            else caiClicked = true;
        });
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
       accidentes = recolectarDirecciones("accidentes", "yellow_alert", googleMap);
       desastres = recolectarDirecciones("desastres", "blue_alerta", googleMap);
       crimenes = recolectarDirecciones("crimenes", "red_alert", googleMap);


        googleMap.setOnMapClickListener(destination -> {
            mostrarCAI(googleMap, caiClicked);
        });

        googleMap.setOnInfoWindowClickListener(this);

    }

    //end boilerplate gMaps

    @Override
    public void onInfoWindowClick(Marker marker){
        String tipo = marker.getTitle().split(": ")[0];
        String id = marker.getSnippet().split("//")[1];
        Intent intent = new Intent(this, Mostrar.class);

                intent.putExtra("id", id);
                intent.putExtra("tipo", tipo);



        startActivity(intent);
    }

    public void infoWindowSetUp(GoogleMap googleMap){
        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter(){
            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }
            @Override
            public View getInfoContents(Marker marker) {

                LinearLayout info = new LinearLayout(MainActivity.this);
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(MainActivity.this);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(MainActivity.this);
                snippet.setText(marker.getSnippet());



                info.addView(title);
                info.addView(snippet);

                return info;
            }
        });
    }

     public ArrayList<Marcador> recolectarDirecciones(String tipo, String icon, GoogleMap googleMap){
        ArrayList<Marcador> direcciones = new ArrayList<>();
        mReference = mFirebase.getReference(tipo);
        Query value = mReference.orderByChild("direccion");
        value.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                if(snapshot.exists()){
                   for(DataSnapshot dss :  snapshot.getChildren()){
                           Marcador item = new Marcador();
                           item.setCoordinates(conversionDireccion(dss.child("direccion").getValue(String.class)));
                           item.setId(dss.getKey());
                           item.setTipo(tipo);
                           item.setFecha(dss.child("fechaRegistro").getValue(String.class));
                           item.setHora(dss.child("hora").getValue(String.class));
                           item.setTipoTipo(dss.child("tipo").getValue(String.class));
                           item.setDescripcion(dss.child("descripcion").getValue(String.class));
                           item.setEstado(dss.child("estado").getValue(String.class));

                       direcciones.add(item);
                       agregarMarcadores(direcciones, icon, googleMap);


                    }

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        return direcciones;
     }

     public void mostrarCAI(GoogleMap googleMap, Boolean visible){
        mReference = mFirebase.getReference("CAI");
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    for (DataSnapshot dss : snapshot.getChildren()) {
                        obtenerInfo(dss.child("id").getValue(String.class), googleMap, visible);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
     }

     protected void obtenerInfo(String placeId, GoogleMap googleMap, Boolean visib){
         final List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.LAT_LNG, Place.Field.NAME, Place.Field.PHONE_NUMBER, Place.Field.ADDRESS);
         final FetchPlaceRequest request = FetchPlaceRequest.newInstance(placeId, placeFields);
         PlacesClient placesClient = Places.createClient(this);
         placesClient.fetchPlace(request).addOnSuccessListener((response) -> {
             Place place = response.getPlace();
             Log.i(TAG, "Place found: " + place.getName());
             String tele = place.getPhoneNumber();
             if(tele ==null) tele = "N??mero no est?? registrado";
             Marker marker = googleMap.addMarker(new MarkerOptions()
                     .position(place.getLatLng())
                     .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("police_station", 100, 120)))
                     .title(place.getName())
                     .snippet("Tel??fono: " + tele + "\n Direcci??n" + place.getAddress())
                     .visible(visib)
             );

         }).addOnFailureListener((exception) -> {
             if (exception instanceof ApiException) {
                 final ApiException apiException = (ApiException) exception;
                 Log.e(TAG, "Place not found: " + exception.getMessage());
                 final int statusCode = apiException.getStatusCode();
                 // TODO: Handle error with given status code.
             }
         });

     }

     public void agregarMarcadores(ArrayList<Marcador> m, String icon, GoogleMap googleMap){
        for(Marcador item : m){
            if (item.getEstado() != null) {
            if (item.getEstado().equals("Activo")) {
                    Marker marker = googleMap.addMarker(new MarkerOptions()
                            .position(item.getCoordinates())
                            .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(icon, 100, 120)))
                            .title(item.getTipo() + ": " + item.getTipoTipo())
                            .snippet("Descripcion: " + item.getDescripcion() + "\nFecha: " + item.getFecha() + "   Hora: " + item.getHora() + "\n //" + item.getId())
                    );
                    infoWindowSetUp(googleMap);
                }
                // else if(m.getCoordinates()==null) Toast.makeText(getApplicationContext(), "Error cargando ubicaciones intent luego ", Toast.LENGTH_LONG).show();
            }
        }
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
    }

}