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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EditarAuto extends AppCompatActivity {

    FloatingActionButton btnEnviarEdicion, btnEnviarEliminacion;
    Button btnActualizar,btnTomarFoto,btnRegresar;
    TextView campoMarca,campoLinea,campoTipo,campoTransmision,campoModelo,campoKm,campoTraccion,campoCombustible,campoColor,campoPrecio,campoCantidadPuertas;
    Automovil automovil;
    ImageView vImagen;
    String ubicacionFoto;

    private static final int REQUEST_PERMISSION_CAMERA =20;
    private static final int REQUEST_IMAGE_CAMERA = 52;
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

        btnRegresar = findViewById(R.id.btnRegresar);
        btnRegresar.setVisibility(View.INVISIBLE);
        btnActualizar = findViewById(R.id.btnEditar);
        btnTomarFoto = findViewById(R.id.btnTomarFoto);
        vImagen = findViewById(R.id.visorImagen);

        btnEnviarEdicion = findViewById(R.id.btnEnviarEditar);
        btnEnviarEliminacion = findViewById(R.id.btnEnviarEliminar);
        btnEnviarEdicion.setVisibility(View.INVISIBLE);
        btnEnviarEliminacion.setVisibility(View.INVISIBLE);

        btnTomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
                    if (ActivityCompat.checkSelfPermission(EditarAuto.this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED) {
                        capturarFoto();
                    } else {
                        ActivityCompat.requestPermissions(EditarAuto.this,new String[]{Manifest.permission.CAMERA},REQUEST_PERMISSION_CAMERA);
                    }
                } else {
                    capturarFoto();
                }
            }
        });

        AccionesAuto accionesAuto=new AccionesAuto(EditarAuto.this);
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
            Toast.makeText(EditarAuto.this,"Error en cargar la info",Toast.LENGTH_LONG).show();
        }

        Integer finalId = id;
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!campoMarca.getText().toString().equals("") && !campoLinea.getText().toString().equals("") && !campoTipo.getText().toString().equals("")
                        && !campoTransmision.getText().toString().equals("") && !campoModelo.getText().toString().equals("") && !campoKm.getText().toString().equals("")
                        && !campoTraccion.getText().toString().equals("") && !campoCombustible.getText().toString().equals("") && !campoColor.getText().toString().equals("")
                        && !campoPrecio.getText().toString().equals("") && !campoCantidadPuertas.getText().toString().equals("")){
                    boolean respuesta=accionesAuto.editarAuto(campoMarca.getText().toString(),campoLinea.getText().toString(),campoTipo.getText().toString(),
                            campoTransmision.getText().toString(),campoModelo.getText().toString(),Integer.parseInt(campoKm.getText().toString()),campoTraccion.getText().toString(),campoCombustible.getText().toString(),campoColor.getText().toString(),
                            Double.parseDouble(campoPrecio.getText().toString()),Integer.parseInt(campoCantidadPuertas.getText().toString()),ubicacionFoto, finalId);
                    if (respuesta){
                        Toast.makeText(EditarAuto.this,"Actualizado correctamente",Toast.LENGTH_LONG).show();

                    }else{
                        Toast.makeText(EditarAuto.this,"Error, intente de nuevo",Toast.LENGTH_LONG).show();
                    }
                    Intent i = new Intent(EditarAuto.this,VerAuto.class);
                    i.putExtra("id", finalId);
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
                Uri fotoUri = FileProvider.getUriForFile(EditarAuto.this,"com.intecap.proyectoautomoviles",imagenArchivo);
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