package com.example.alertacucuta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.alertacucuta.Objetos.Accidente;
import com.example.alertacucuta.Objetos.Crimen;
import com.example.alertacucuta.Objetos.Desastre;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Mostrar extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference mDatabase;
    Intent intent;

    @BindView(R.id.barrioI) TextView barrio;
    @BindView(R.id.direccionI) TextView direccion;
    @BindView(R.id.tipoI) TextView tipo;
    @BindView(R.id.descripcionA) TextView descripcion;
    @BindView(R.id.hora) TextView hora;
    @BindView(R.id.btnDesactivarAccidnete) Button desactivar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        database = FirebaseDatabase.getInstance();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar);

        ButterKnife.bind(this);
        String id = getIntent().getStringExtra("id");
        String tipoInfo = getIntent().getStringExtra("tipo");
        mDatabase = database.getReference(tipoInfo);
        if(id!=null){
            DatabaseReference childA = mDatabase.child(id);
            childA.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        switch(tipoInfo){
                            case "accidentes":
                                Accidente accidente = snapshot.getValue(Accidente.class);
                                barrio.setText(accidente.getBarrio());
                                direccion.setText(accidente.getDireccion());
                                tipo.setText(accidente.getTipo());
                                descripcion.setText("General: "+ accidente.getDescripcion()
                                        +"\n Victimas: "+accidente.getDescripcionVictima());
                                hora.setText(accidente.getFechaRegistro() +" "+ accidente.getHora());
                                break;

                            case "desastres":
                                Desastre desastre = snapshot.getValue(Desastre.class);
                                barrio.setText(desastre.getBarrio());
                                direccion.setText(desastre.getDireccion());
                                tipo.setText(desastre.getTipo());
                                descripcion.setText(desastre.getDescripcion());
                                hora.setText(desastre.getFechaRegistro() +" "+ desastre.getHora());
                                break;

                            case "crimenes":
                                Crimen crimen = snapshot.getValue(Crimen.class);
                                barrio.setText(crimen.getBarrio());
                                direccion.setText(crimen.getDireccion());
                                tipo.setText(crimen.getTipo());
                                descripcion.setText("General: "+ crimen.getDescripcion()
                                        +"\n Victimas: "+crimen.getDescripcionVictima()
                                        +"\n Sospechosos: "+crimen.getDescripcionSospechoso());
                                hora.setText(crimen.getFechaRegistro() +" "+ crimen.getHora());
                                break;
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    System.out.println("The read failed: " + error.getCode());

                }
            });
            desactivar.setOnClickListener(v -> {
                childA.child("estado").setValue("Inactivo");

                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            });
        }
    }
}