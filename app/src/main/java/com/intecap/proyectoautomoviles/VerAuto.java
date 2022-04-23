package com.intecap.proyectoautomoviles;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class VerAuto extends AppCompatActivity {

    FloatingActionButton btnEnviarEdicion, btnEnviarEliminacion;
    Button btnActualizar,btnTomarFoto;
    TextView campoMarca,campoLinea,campoTipo,campoTransmision,campoModelo,campoKm,campoTraccion,campoCombustible,campoColor,campoPrecio,campoCantidadPuertas;
    Automovil automovil;
    ImageView vImagen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_auto);

        campoMarca = findViewById(R.id.campoMarca);
        campoLinea = findViewById(R.id.campoLinea);
        campoTipo = findViewById(R.id.campoTipo);
        campoTransmision = findViewById(R.id.campoTransmision);
        campoModelo = findViewById(R.id.campoModelo);
        campoKm = findViewById(R.id.campoKm);
        campoTraccion = findViewById(R.id.campoTraccion);
        campoCombustible = findViewById(R.id.campoCombustible);
        campoColor = findViewById(R.id.campoColor);
        campoPrecio = findViewById(R.id.campoPrecio);
        campoCantidadPuertas = findViewById(R.id.campoCantidadPuertas);


        btnActualizar = findViewById(R.id.btnEditar);
        btnTomarFoto = findViewById(R.id.btnTomarFoto);
        btnActualizar.setVisibility(View.INVISIBLE);
        vImagen = findViewById(R.id.visorImagen);
        btnTomarFoto.setVisibility(View.INVISIBLE);

        AccionesAuto accionesAuto=new AccionesAuto(VerAuto.this);
        Integer id = 0;

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras != null) {
                id= extras.getInt("id");
            }
        }
        automovil = accionesAuto.listarAutoPorId(id);
        if (automovil!=null){
            campoMarca.setText(automovil.getMarca());
            campoLinea.setText(automovil.getLinea());
            campoTipo.setText(automovil.getTipo());
            campoTransmision.setText(automovil.getTransmision());
            campoModelo.setText(automovil.getModelo());
            campoKm.setText(String.valueOf(automovil.getKm()));
            campoTraccion.setText(automovil.getTraccion());
            campoCombustible.setText(automovil.getCombustible());
            campoColor.setText(automovil.getColor());
            campoPrecio.setText(automovil.getPrecio().toString());
            campoCantidadPuertas.setText(String.valueOf(automovil.getCantidadPuertas()));

            vImagen.setImageURI(Uri.parse(automovil.getFoto()));
        }else{
            Toast.makeText(VerAuto.this,"Error en cargar la info",Toast.LENGTH_LONG).show();
        }
        campoMarca.setKeyListener(null);
        campoLinea.setKeyListener(null);
        campoTipo.setKeyListener(null);
        campoTransmision.setKeyListener(null);
        campoModelo.setKeyListener(null);
        campoKm.setKeyListener(null);
        campoTraccion.setKeyListener(null);
        campoCombustible.setKeyListener(null);
        campoColor.setKeyListener(null);
        campoPrecio.setKeyListener(null);
        campoCantidadPuertas.setKeyListener(null);

        btnEnviarEdicion = findViewById(R.id.btnEnviarEditar);
        Integer finalId = id;
        btnEnviarEdicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(VerAuto.this,EditarAuto.class);
                i.putExtra("id", finalId);
                startActivity(i);
            }
        });

        btnEnviarEliminacion = findViewById(R.id.btnEnviarEliminar);
        btnEnviarEliminacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mensaje = new AlertDialog.Builder(VerAuto.this);
                mensaje.setMessage("¿Desea eliminar el automovil?")
                        .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (accionesAuto.eliminarAuto(finalId)){
                                    Toast.makeText(VerAuto.this,"Auto eliminado con exito",Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(VerAuto.this,MainActivity.class);
                                    startActivity(i);
                                }


                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
            }
        });
    }

    public void regresar(View v) {
        Intent i = new Intent(VerAuto.this, MainActivity.class);
        startActivity(i);
    }
}