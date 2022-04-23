package com.intecap.proyectoautomoviles;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AgregarAuto extends AppCompatActivity {

    EditText campoMarca,campoLinea,campoTipo,campoTransmision,campoModelo,campoKm,campoTraccion,campoCombustible,campoColor,campoPrecio,campoCantidadPuertas;
    Button btnRegistrar,btnCam;
    ImageView vImagen;
    String ubicacionFoto;

    private static final int REQUEST_PERMISSION_CAMERA =20;
    private static final int REQUEST_IMAGE_CAMERA = 52;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_auto);

        campoMarca = findViewById(R.id.idMarca);
        campoLinea = findViewById(R.id.idLinea);
        campoTipo = findViewById(R.id.idTipo);
        campoTransmision = findViewById(R.id.idTransmision);
        campoModelo = findViewById(R.id.idModelo);
        campoKm = findViewById(R.id.idKm);
        campoTraccion = findViewById(R.id.idTraccion);
        campoCombustible = findViewById(R.id.idCombustible);
        campoColor = findViewById(R.id.idColor);
        campoPrecio = findViewById(R.id.idPrecio);
        campoCantidadPuertas = findViewById(R.id.idCantidadPuertas);

        btnCam = findViewById(R.id.btnCamara);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        vImagen = findViewById(R.id.imagenAgregada);

        btnCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
                    if (ActivityCompat.checkSelfPermission(AgregarAuto.this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED) {
                        capturarFoto();
                    } else {
                        ActivityCompat.requestPermissions(AgregarAuto.this,new String[]{Manifest.permission.CAMERA},REQUEST_PERMISSION_CAMERA);
                    }
                } else {
                    capturarFoto();
                }
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccionesAuto AccionesAuto = new AccionesAuto(AgregarAuto.this);
                long resspuesta = AccionesAuto.insertarAuto(campoMarca.getText().toString(),campoLinea.getText().toString(),campoTipo.getText().toString(),
                        campoTransmision.getText().toString(),campoModelo.getText().toString(),Integer.parseInt(campoKm.getText().toString()),campoTraccion.getText().toString(),
                        campoCombustible.getText().toString(),campoColor.getText().toString(),Double.parseDouble(campoPrecio.getText().toString()),Integer.parseInt(campoCantidadPuertas.getText().toString()),ubicacionFoto);
                if (resspuesta>0){
                    Toast.makeText(AgregarAuto.this,"Automovil registrado",Toast.LENGTH_LONG).show();
                    Intent i = new Intent(AgregarAuto.this,MainActivity.class);
                    startActivity(i);
                }else{
                    Toast.makeText(AgregarAuto.this,"Error, intentar de nuevo",Toast.LENGTH_LONG).show();
                    Intent i = new Intent(AgregarAuto.this,MainActivity.class);
                    startActivity(i);
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CAMERA) {
            if (permissions.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                capturarFoto();
            } else {
                Toast.makeText(this,"Debe de brindar permisos.", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_IMAGE_CAMERA && resultCode == Activity.RESULT_OK){
            vImagen.setImageURI(Uri.parse(ubicacionFoto));
        }
    }


    private void capturarFoto() {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (i.resolveActivity(getPackageManager())!=null) {
            File imagenArchivo = null;

            try {
                imagenArchivo = crearArchivo();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (imagenArchivo != null) {
                Uri fotoUri = FileProvider.getUriForFile(AgregarAuto.this,"com.intecap.proyectoautomoviles",imagenArchivo);
                i.putExtra(MediaStore.EXTRA_OUTPUT,fotoUri);

                startActivityForResult(i,REQUEST_IMAGE_CAMERA);
            }
        }
    }

    private File crearArchivo() throws IOException {
        String formatoFecha = new SimpleDateFormat("yyyyMMdd_Hh-mm-ss", Locale.getDefault()).format(new Date());
        String nombreArchivo = "IMG_"+formatoFecha;
        File nuevaUbicacion = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imagenTemp = File.createTempFile(nombreArchivo,".jpg",nuevaUbicacion);
        ubicacionFoto = imagenTemp.getAbsolutePath();
        return imagenTemp;
    }
}