package com.intecap.proyectoautomoviles;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class AccionesAuto extends UniversalConexion{
    Context pantallaAuto;

    public AccionesAuto(@Nullable Context context) {
        super(context);
        this.pantallaAuto = context;
    }

    public Long insertarAuto(String marca, String linea, String tipo, String transmision,String modelo, int km, String traccion,String combustible,String color,Double precio,int cantidadPuertas,String foto) {
        long respuesta = 0;
        try {
            UniversalConexion conexion = new UniversalConexion(pantallaAuto);
            SQLiteDatabase accionInsertar= conexion.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("marca",marca);
            values.put("linea",linea);
            values.put("tipo",tipo);
            values.put("transmision",transmision);
            values.put("modelo",modelo);
            values.put("transmision",transmision);
            values.put("km",km);
            values.put("traccion",traccion);
            values.put("combustible",combustible);
            values.put("color",color);
            values.put("precio",precio);
            values.put("cantidad_puertas",cantidadPuertas);
            values.put("foto",foto);
            respuesta = accionInsertar.insert(TABLE_AUTOS,null,values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respuesta;
    }

    public ArrayList<Automovil> listarAutos(){
        UniversalConexion conexion = new UniversalConexion(pantallaAuto);
        SQLiteDatabase accionListar = conexion.getWritableDatabase();

        ArrayList<Automovil> listAutos = new ArrayList<>();
        Automovil auto=null;
        Cursor cursorAutos= accionListar.rawQuery("SELECT * FROM "+TABLE_AUTOS,null);

        if (cursorAutos.moveToFirst()){
            do{
                auto = new Automovil();
                auto.setCorrelativo(cursorAutos.getInt(0));
                auto.setMarca(cursorAutos.getString(1));
                auto.setLinea(cursorAutos.getString(2));
                auto.setTipo(cursorAutos.getString(3));
                auto.setTransmision(cursorAutos.getString(4));
                auto.setModelo(cursorAutos.getString(5));
                auto.setKm(cursorAutos.getInt(6));
                auto.setTraccion(cursorAutos.getString(7));
                auto.setCombustible(cursorAutos.getString(8));
                auto.setColor(cursorAutos.getString(9));
                auto.setPrecio(cursorAutos.getDouble(10));
                auto.setCantidadPuertas(cursorAutos.getInt(11));
                auto.setFoto(cursorAutos.getString(12));
                listAutos.add(auto);
            }while (cursorAutos.moveToNext());
        }
        cursorAutos.close();
        return listAutos;
    }

    public Automovil listarAutoPorId(int correlativo){
        UniversalConexion conexion = new UniversalConexion(pantallaAuto);
        SQLiteDatabase accionListar = conexion.getWritableDatabase();

        Automovil auto=null;
        Cursor cursorAutos= accionListar.rawQuery("SELECT * FROM "+TABLE_AUTOS + " WHERE correlativo="+correlativo,null);

        if (cursorAutos.moveToFirst()){
            auto = new Automovil();
            auto.setCorrelativo(cursorAutos.getInt(0));
            auto.setMarca(cursorAutos.getString(1));
            auto.setLinea(cursorAutos.getString(2));
            auto.setTipo(cursorAutos.getString(3));
            auto.setTransmision(cursorAutos.getString(4));
            auto.setModelo(cursorAutos.getString(5));
            auto.setKm(cursorAutos.getInt(6));
            auto.setTraccion(cursorAutos.getString(7));
            auto.setCombustible(cursorAutos.getString(8));
            auto.setColor(cursorAutos.getString(9));
            auto.setPrecio(cursorAutos.getDouble(10));
            auto.setCantidadPuertas(cursorAutos.getInt(11));
            auto.setFoto(cursorAutos.getString(12));
        }
        cursorAutos.close();
        return auto;
    }

    public Boolean editarAuto(String marca, String linea, String tipo, String transmision,String modelo, int km, String traccion,String combustible,String color,Double precio,int cantidadPuertas,String foto, int correlativo) {
        boolean respuesta = false;
        UniversalConexion conexion = new UniversalConexion(pantallaAuto);
        SQLiteDatabase accionEditar = conexion.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("marca",marca);
        values.put("linea",linea);
        values.put("tipo",tipo);
        values.put("transmision",transmision);
        values.put("modelo",modelo);
        values.put("transmision",transmision);
        values.put("km",km);
        values.put("traccion",traccion);
        values.put("combustible",combustible);
        values.put("color",color);
        values.put("precio",precio);
        values.put("cantidad_puertas",cantidadPuertas);
        values.put("foto",foto);
        try{
            accionEditar.update(TABLE_AUTOS, values, "correlativo=" + correlativo,null);
            respuesta=true;
        }catch (Exception ex){
            ex.toString();
            respuesta=false;
        }finally {
            conexion.close();
        }
        return respuesta;
    }

    public boolean eliminarAuto(int correlativo){
        boolean eliminacionCorrecta=false;

        UniversalConexion conexion = new UniversalConexion(pantallaAuto);
        SQLiteDatabase accionEliminar= conexion.getWritableDatabase();

        try{
            accionEliminar.execSQL("DELETE FROM "+TABLE_AUTOS+" WHERE correlativo="+correlativo);
            eliminacionCorrecta=true;
        }catch (Exception ex){
            ex.toString();
            eliminacionCorrecta=false;
        }finally {
            conexion.close();
        }

        return eliminacionCorrecta;
    }
}
