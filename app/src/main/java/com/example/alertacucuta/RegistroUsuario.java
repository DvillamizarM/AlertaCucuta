package com.example.alertacucuta;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.alertacucuta.Objetos.Usuario;
import com.example.alertacucuta.ui.login.LoginActivity;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegistroUsuario extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    private static final String TAG = "RegistroUsuario";
    private Usuario user;

    @BindView(R.id.nombreU) EditText nombre;
    @BindView(R.id.apellidoU) EditText apellido;
    @BindView(R.id.cedulaU) EditText cedula;
    @BindView(R.id.telefonoU) EditText telefono;
    @BindView(R.id.correoU) EditText correo;
    @BindView(R.id.contrasenaU) EditText contrasena;
    @BindView(R.id.direccionU) EditText direccion;
    @BindView(R.id.barrioU) Spinner barrio;
    @BindView(R.id.registerUsuario) Button btnRegistro;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);
        ButterKnife.bind(this);
            intent = new Intent(this, LoginActivity.class);

        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("usuarios");
        mAuth = FirebaseAuth.getInstance();

        AuxMethods.createSpinner(AuxMethods.listaBarrios, barrio, this);

        Places.initialize(getApplicationContext(), "AIzaSyCJE4v1u_fCq2X8TAa1FyjHMo_c6P8oW5I");
        direccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList)
                        .setLocationRestriction(RectangularBounds.newInstance(
                                new LatLng(7.797340, -72.564975),
                                new LatLng(7.959478, -72.454595)
                        ))
                        .build(RegistroUsuario.this);
                startActivityForResult(intent, 100);
            }
        });

        btnRegistro.setOnClickListener(v -> {
            if(TextUtils.isEmpty(correo.getText().toString()) || TextUtils.isEmpty(contrasena.getText().toString()) || TextUtils.isEmpty(cedula.getText().toString()) || TextUtils.isEmpty(telefono.getText().toString())){
                Toast.makeText(getApplicationContext(), "por favor ingrese correo, contraseña, cédula y teléfono", Toast.LENGTH_LONG).show();
                return;
            }
            user = new Usuario(nombre.getText().toString(),  apellido.getText().toString(), cedula.getText().toString(), telefono.getText().toString(), correo.getText().toString(), contrasena.getText().toString(), direccion.getText().toString(), barrio.getSelectedItem().toString());
            enviarDatosUsuario(correo.getText().toString(), contrasena.getText().toString());
        });
    }

    public void enviarDatosUsuario(String correo, String contrasena) {
        mAuth.createUserWithEmailAndPassword(correo, contrasena)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegistroUsuario.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }

    public void updateUI(FirebaseUser currentUser){
     String keyId = mDatabase.push().getKey();
        mDatabase.child(keyId).setValue(user);
        intent = new Intent(this, LoginActivity.class);
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
