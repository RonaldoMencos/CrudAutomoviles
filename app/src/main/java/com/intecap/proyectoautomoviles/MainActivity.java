package com.intecap.proyectoautomoviles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ViewFlipper vFlipper;
    public int[] imagenesArreglo = {R.drawable.ima1,R.drawable.ima2,R.drawable.ima3,R.drawable.ima4,R.drawable.ima5};

    RecyclerView listaAutos;
    ArrayList<Automovil> listaArrayAutos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vFlipper = findViewById(R.id.vFlipper);

        for (int foto:imagenesArreglo) {
            carruselImagenes(foto);
        }

        listaAutos=findViewById(R.id.listadoAutos);
        listaAutos.setLayoutManager(new LinearLayoutManager(this));

        AccionesAuto accionesAuto= new AccionesAuto(this);

        listaArrayAutos = new ArrayList<>();
        AdaptadorAuto adaptador = new AdaptadorAuto(accionesAuto.listarAutos());
        listaAutos.setAdapter(adaptador);
    }

    public void carruselImagenes(int fotoActual){
        ImageView vistaImagen = new ImageView(this);
        vistaImagen.setBackgroundResource(fotoActual);
        vFlipper.addView(vistaImagen);
        vFlipper.setFlipInterval(3000);
        vFlipper.setAutoStart(true);
        vFlipper.setInAnimation(this,android.R.anim.slide_in_left);
    }

    public void irAgregarAuto(View v) {
        Intent i = new Intent(MainActivity.this, AgregarAuto.class);
        startActivity(i);
    }
}