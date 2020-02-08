package com.juanantonio.recordador.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class PersonaSQLiteHelper extends SQLiteOpenHelper {

    String sqlCreate = "CREATE TABLE Personas (codigo INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nombre TEXT, " +
            "email TEXT, " +
            "imagen TEXT, " +
            "localidad TEXT, " +
            "telefono TEXT, " +
            "fecha TEXT," +
            "estado INTEGER," +
            "provincia TEXT)";
    String sqlRecoverAll = "SELECT codigo, nombre,email, imagen FROM 'Personas'";
    String sqlRecoverOne = "SELECT * FROM 'Personas' WHERE codigo=?";
    String sqlRecoverSearch = "SELECT * FROM 'Personas' WHERE nombre=? AND fecha=? /*AND provincia=?*/";
    String sqlRecoverProvince = "SELECT DISTINCT provincia FROM 'Personas'";

    public static final String DB_NAME = "DBPersona.db";
    public static final int DB_VERSION = 1;
    private Context mContext;

    private static PersonaSQLiteHelper mInstance = null;

    public static PersonaSQLiteHelper get() {
        if (mInstance == null) {
            mInstance = new PersonaSQLiteHelper(MyApplication.getContext());
        }
        return mInstance;
    }

    private PersonaSQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("crear", "crear");
        db.execSQL(sqlCreate);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Personas");
        db.execSQL(sqlCreate);
    }

    public void insertarPersona(PersonEntity p) {
        ContentValues v = new ContentValues();
        v.put("nombre", p.getName());
        v.put("email", p.getEmail());
        v.put("imagen", p.getImage());
        v.put("localidad", p.getLocation());
        v.put("telefono", p.getPhone());
        v.put("fecha", p.getDate());
        v.put("estado", p.getState() ? 1 : 0);
        System.out.println("aaa" + p.getProvince());
        v.put("provincia", p.getProvince());
        this.getWritableDatabase().insert("Personas", null, v);
    }

    public void actualizarPersona(PersonEntity p) {
        ContentValues v = new ContentValues();
        v.put("nombre", p.getName());
        v.put("email", p.getEmail());
        v.put("imagen", p.getImage());
        v.put("localidad", p.getLocation());
        v.put("telefono", p.getPhone());
        v.put("fecha", p.getDate());
        v.put("estado", p.getState() ? 1 : 0);
        v.put("provincia", p.getProvince());
        String[] args = new String[]{String.valueOf(p.getId())};
        this.getWritableDatabase().update("Personas", v, "codigo=?", args);
    }

    public void eliminarPersona(int id) {
        String[] args = new String[]{String.valueOf(id)};
        this.getWritableDatabase().delete("Personas", "codigo=?", args);
    }

    public List<PersonEntity> recuperarListado() {
        List<PersonEntity> p = null;
        Cursor c = this.getWritableDatabase().rawQuery(sqlRecoverAll, null);
        if (c.moveToFirst()) {
            p = new ArrayList<>();
            do {
                p.add(new PersonEntity(c.getInt(c.getColumnIndex("codigo")),
                        c.getString(c.getColumnIndex("nombre")),
                        c.getString(c.getColumnIndex("email")),
                        c.getString(c.getColumnIndex("imagen")),
                        null, null, null, null, null));
            } while (c.moveToNext());
        }
        return p;
    }

    public List<String> recuperarProvincias() {
        List<String> p = null;
        Cursor c = this.getWritableDatabase().rawQuery(sqlRecoverProvince, null);
        if (c.moveToFirst()) {
            p = new ArrayList<>();
            do {
                p.add(c.getString(c.getColumnIndex("provincia")));
            } while (c.moveToNext());
        }
        return p;
    }

    public PersonEntity recuperarPersona(int codigo) {
        PersonEntity p = null;
        String[] args = new String[]{String.valueOf(codigo)};
        Cursor c = this.getWritableDatabase().rawQuery(sqlRecoverOne, args);
        if (c.moveToFirst()) {
            p = new PersonEntity(c.getInt(c.getColumnIndex("codigo")),
                    c.getString(c.getColumnIndex("nombre")),
                    c.getString(c.getColumnIndex("email")),
                    c.getString(c.getColumnIndex("imagen")),
                    c.getString(c.getColumnIndex("localidad")),
                    c.getString(c.getColumnIndex("telefono")),
                    c.getString(c.getColumnIndex("fecha")),
                    (c.getInt(c.getColumnIndex("estado")) != 0),
                    c.getString(c.getColumnIndex("provincia")));
        }
        return p;
    }

    public List<PersonEntity> personasBusqueda(String nombre, String fecha, String provincia) {

        String[] args = new String[]{nombre, fecha/*, provincia*/};
        List<PersonEntity> p = null;
        Cursor c = this.getWritableDatabase().rawQuery(sqlRecoverSearch, args);
        System.out.println("entraaaaa");
        if (c.moveToFirst()) {
            System.out.println("instancia");
            p = new ArrayList<>();
            do {
                p.add(new PersonEntity(c.getInt(c.getColumnIndex("codigo")),
                        c.getString(c.getColumnIndex("nombre")),
                        c.getString(c.getColumnIndex("email")),
                        c.getString(c.getColumnIndex("imagen")),
                        null, null, null, null, null));
            } while (c.moveToNext());
        }
        return p;
    }
}