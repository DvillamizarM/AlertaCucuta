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

import com.example.alertacucuta.Objetos.CAI;
import com.example.alertacucuta.Objetos.Desastre;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.ButterKnife;
import butterknife.BindView;

public class RegistroCAI extends AppCompatActivity{

    FirebaseDatabase database;
    DatabaseReference mDatabase;
    Intent intent;
    @BindView(R.id.barrioCAI) Spinner barrio;
    @BindView(R.id.telefonoCAI) EditText telefono;
    @BindView(R.id.direccionCAI) EditText direccion;
    @BindView(R.id.registro) Button btnRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("CAI");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_c_a_i);
        ButterKnife.bind(this);
        AuxMethods.createSpinner(AuxMethods.listaBarrios, barrio, this);
        barrio.onSaveInstanceState();

        btnRegistro.setOnClickListener(v -> {
            if(barrio.getSelectedItemPosition()==0 || TextUtils.isEmpty(direccion.getText().toString()) || TextUtils.isEmpty(telefono.getText().toString()) ){
                Toast.makeText(getApplicationContext(), "por favor ingrese toda la informacion", Toast.LENGTH_LONG).show();
                return;
            }
            CAI registro = new CAI(telefono.getText().toString(), direccion.getText().toString(), barrio.getSelectedItem().toString());

            String keyId = mDatabase.push().getKey();
            mDatabase.child(keyId).setValue(registro);
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }
    public void submit(View view) {
        Snackbar.make(findViewById(R.id.viewCAIRegistro), R.string.app_name, Snackbar.LENGTH_SHORT).show();
    }
    public void back(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}