package com.juanantonio.recordador.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class PersonaSQLiteHelper extends SQLiteOpenHelper {

    String sqlCreate = "CREATE TABLE Personas (codigo INTEGER, nombre TEXT, email TEXT, imagen TEXT, localidad TEXT, telefono TEXT, fecha TEXT)";
    public static final String DB_NAME = "DBPersona";
    public static final int DB_VERSION = 1;
    private Context mContext;

    //===========================================================
    //Singleton Pattern
    private static PersonaSQLiteHelper mInstance = null;
    public static PersonaSQLiteHelper get(Context context){
        if(mInstance == null) mInstance = new PersonaSQLiteHelper(context.getApplicationContext());
        return mInstance;
    }//func
    public PersonaSQLiteHelper(Context context) {
        super(context,DB_NAME,null,DB_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("crear","crear");
        db.execSQL(sqlCreate);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Personas");

        //Se crea la nueva versión de la tabla
        db.execSQL(sqlCreate);
    }

    public void insertarPersona(Person p) {
        ContentValues v = new ContentValues();
        v.put("nombre", p.getName());
        v.put("email", p.getEmail());
        v.put("imagen", p.getImage());
        v.put("localidad", p.getLocation());
        v.put("telefono", p.getPhone());
        v.put("fecha", p.getDate());
        this.getWritableDatabase().insert("Personas", null, v);
    }
}