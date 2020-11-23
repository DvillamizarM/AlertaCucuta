package com.example.alertacucuta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.alertacucuta.Objetos.Accidente;
import com.example.alertacucuta.Objetos.Crimen;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegistroCrimen extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference mDatabase;
    Intent intent;
    @BindView(R.id.barrioI) Spinner barrio;
    @BindView(R.id.direccionI) EditText direccion;
    @BindView(R.id.tipoI) Spinner tipo;
    @BindView(R.id.descripcionC) EditText descripcion;
    @BindView(R.id.descripcionVictima) EditText descripcionVictima;
    @BindView(R.id.descripcionSospechoso) EditText descripcionSospechoso;
    @BindView(R.id.horaA) Spinner hora;
    @BindView(R.id.horaB) Spinner minuto;
    @BindView(R.id.time) Spinner time;
    @BindView(R.id.registro) Button btnRegistro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("crimenes");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_crimen);
        ButterKnife.bind(this);

        AuxMethods.createSpinner(AuxMethods.listaBarrios, barrio, this);
        barrio.onSaveInstanceState();
        AuxMethods.createSpinner(AuxMethods.tiposCrimenes, tipo, this);
        tipo.onSaveInstanceState();
        AuxMethods.createSpinner(AuxMethods.hora, hora, this);
        AuxMethods.createSpinner(AuxMethods.minuto, minuto, this);
        AuxMethods.createSpinner(AuxMethods.time, time, this);


        btnRegistro.setOnClickListener(v -> {
            if(barrio.getSelectedItemPosition()==0 || TextUtils.isEmpty(direccion.getText().toString()) || tipo.getSelectedItemPosition()==0
                    || TextUtils.isEmpty(descripcion.getText().toString()) || TextUtils.isEmpty(descripcionVictima.getText().toString())  || TextUtils.isEmpty(descripcionSospechoso.getText().toString())
                    || hora.getSelectedItemPosition()==0 || minuto.getSelectedItemPosition()==0 || time.getSelectedItemPosition()==0){
                Toast.makeText(getApplicationContext(), "por favor ingrese toda la informacion", Toast.LENGTH_LONG).show();
                return;
            }
            Crimen registro = new Crimen(user.getUid(), barrio.getSelectedItem().toString(), tipo.getSelectedItem().toString(), direccion.getText().toString(),  descripcion.getText().toString(), descripcionVictima.getText().toString(), descripcionSospechoso.getText().toString(), hora.getSelectedItem().toString()+":"+minuto.getSelectedItem().toString()+" "+time.getSelectedItem().toString());

            String keyId = mDatabase.push().getKey();
            mDatabase.child(keyId).setValue(registro);
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

    }
    public void back(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}