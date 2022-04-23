package com.intecap.proyectoautomoviles;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class UniversalConexion extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION =1;
    private static final String DATABASE_NAME ="DbAutomoviles.db";
    public static final String TABLE_AUTOS = "auto";

    public UniversalConexion(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_AUTOS + " (" +
                "correlativo INTEGER PRIMARY KEY AUTOINCREMENT," +
                "marca TEXT," +
                "linea TEXT," +
                "tipo TEXT," +
                "transmision TEXT," +
                "modelo TEXT," +
                "km NUMBER," +
                "traccion TEXT," +
                "combustible TEXT," +
                "color TEXT," +
                "precio REAL," +
                "cantidad_puertas INTEGER," +
                "foto TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE "+ TABLE_AUTOS);
        onCreate(db);
    }
}
