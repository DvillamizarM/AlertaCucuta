package com.example.alertacucuta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.libraries.places.api.Places;


public class MainActivity extends AppCompatActivity {

    private LinearLayout menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menu = findViewById(R.id.menu);

    }


    public void showMenu(View view){
        if (menu.getVisibility()==View.INVISIBLE) menu.setVisibility(View.VISIBLE);
        else menu.setVisibility(View.INVISIBLE);
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