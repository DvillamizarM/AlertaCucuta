package com.example.alertacucuta;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.alertacucuta.Objetos.Accidente;
import com.example.alertacucuta.Objetos.Usuario;
import com.example.alertacucuta.ui.login.LoginActivity;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegistroAccidente extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference mDatabase;
    Intent intent;
//AIzaSyC8EtV1IG9Hq-DQpliQzbp_Nyc_JYEzQBk
    @BindView(R.id.barrioI) Spinner barrio;
    @BindView(R.id.direccionI) EditText direccion;
    @BindView(R.id.tipoI) Spinner tipo;
    @BindView(R.id.descripcionA) EditText descripcion;
    @BindView(R.id.descripcionVictima) EditText descripcionVictima;
    @BindView(R.id.horaA) Spinner hora;
    @BindView(R.id.horaB) Spinner minuto;
    @BindView(R.id.time) Spinner time;
    @BindView(R.id.registerAccidnete) Button btnRegistro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("accidentes");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_accidente);
        ButterKnife.bind(this);

        AuxMethods.createSpinner(AuxMethods.listaBarrios, barrio, this);
        barrio.onSaveInstanceState();
        AuxMethods.createSpinner(AuxMethods.tiposAccidentes, tipo, this);
        tipo.onSaveInstanceState();
        AuxMethods.createSpinner(AuxMethods.hora, hora, this);
        AuxMethods.createSpinner(AuxMethods.minuto, minuto, this);
        AuxMethods.createSpinner(AuxMethods.time, time, this);

        btnRegistro.setOnClickListener(v -> {
            if(barrio.getSelectedItemPosition()==0 || TextUtils.isEmpty(direccion.getText().toString()) || tipo.getSelectedItemPosition()==0
                    || TextUtils.isEmpty(descripcion.getText().toString()) || TextUtils.isEmpty(descripcionVictima.getText().toString())
                    || hora.getSelectedItemPosition()==0 || minuto.getSelectedItemPosition()==0 || time.getSelectedItemPosition()==0){
                Toast.makeText(getApplicationContext(), "por favor ingrese toda la informacion", Toast.LENGTH_LONG).show();
                return;
            }
            Accidente registro = new Accidente(user.getUid(), barrio.getSelectedItem().toString(), direccion.getText().toString(), tipo.getSelectedItem().toString(), descripcion.getText().toString(), descripcionVictima.getText().toString(), hora.getSelectedItem().toString()+":"+minuto.getSelectedItem().toString()+" "+time.getSelectedItem().toString());

            String keyId = mDatabase.push().getKey();
            mDatabase.child(keyId).setValue(registro);
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });


        Places.initialize(getApplicationContext(), "AIzaSyC8EtV1IG9Hq-DQpliQzbp_Nyc_JYEzQBk");

        direccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).build(RegistroAccidente.this);
                startActivityForResult(intent, 100);
            }
        });
    }

    public void back(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == RESULT_OK){
            Place place = Autocomplete.getPlaceFromIntent(data);
            direccion.setText(place.getAddress());
        }
        else{
            Status status = Autocomplete.getStatusFromIntent(data);
            Toast.makeText(getApplicationContext(),status.getStatusMessage(), Toast.LENGTH_LONG).show();
        }
    }
}