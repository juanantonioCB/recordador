package com.juanantonio.recordador.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.juanantonio.recordador.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class PersonaModel extends SQLiteOpenHelper {

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
    String sqlRecoverSearch = "SELECT * FROM 'Personas' WHERE nombre LIKE ? AND fecha LIKE ? AND provincia LIKE ?";
    String sqlRecoverProvince = "SELECT DISTINCT provincia FROM 'Personas'";

    public static final String DB_NAME = "DBPersona.db";
    public static final int DB_VERSION = 1;
    private Context mContext;

    private static PersonaModel mInstance = null;

    public static PersonaModel get() {
        if (mInstance == null) {
            mInstance = new PersonaModel(MyApplication.getContext());
        }
        return mInstance;
    }

    private PersonaModel(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("crear", "crear");
        db.execSQL(sqlCreate);
        loadExampleData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Personas");
        db.execSQL(sqlCreate);
    }

    private void loadExampleData(SQLiteDatabase db) {
        Log.d("PersonaModel", "Importando datos de ejemplo");
        for (PersonEntity p : getExamplePersons()) {
            ContentValues v = new ContentValues();
            v.put("nombre", p.getName());
            v.put("email", p.getEmail());
            v.put("imagen", p.getImage());
            v.put("localidad", p.getLocation());
            v.put("telefono", p.getPhone());
            v.put("fecha", p.getDate());
            v.put("estado", p.getState() ? 1 : 0);
            v.put("provincia", p.getProvince());
            db.insert("Personas", null, v);
        }
    }

    public boolean insertarPersona(PersonEntity p) {
        try {
            ContentValues v = new ContentValues();
            v.put("nombre", p.getName());
            v.put("email", p.getEmail());
            v.put("imagen", p.getImage());
            v.put("localidad", p.getLocation());
            v.put("telefono", p.getPhone());
            v.put("fecha", p.getDate());
            v.put("estado", p.getState() ? 1 : 0);
            v.put("provincia", p.getProvince());
            this.getWritableDatabase().insert("Personas", null, v);
        } catch (Exception e) {
            return false;
        } finally {
            return true;
        }


    }

    public boolean actualizarPersona(PersonEntity p) {
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
        if (this.getWritableDatabase().update("Personas", v, "codigo=?", args) != 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean eliminarPersona(int id) {
        String[] args = new String[]{String.valueOf(id)};
        if (this.getWritableDatabase().delete("Personas", "codigo=?", args) != 0) {
            return true;
        } else {
            return false;
        }
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
        String[] args = new String[]{'%' + nombre + '%', '%' + fecha + '%', '%' + provincia + '%'};
        List<PersonEntity> p = null;
        Cursor c = this.getWritableDatabase().rawQuery(sqlRecoverSearch, args);
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

    public List<PersonEntity> getExamplePersons() {
        List<PersonEntity> persons = new ArrayList<>();
        Log.d("PersonaModel", "Creando Contacto 1");
        PersonEntity p = new PersonEntity();
        p.setName("Manuel");
        p.setEmail("manuel@manuel.com");
        p.setImage(null);
        p.setLocation("Aguilar");
        p.setPhone("654654654");
        p.setDate("10/10/2000");
        p.setProvince("Malaga");
        p.setState(true);
        persons.add(p);

        Log.d("PersonaModel", "Creando Contacto 2");
        PersonEntity p2 = new PersonEntity();
        p2.setName("Gonzalo");
        p2.setEmail("gonzalo@gonzalo.com");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap bitmap = BitmapFactory.decodeResource(MyApplication.getContext().getResources(), R.drawable.foto1);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        p2.setImage(imageString);
        p2.setLocation("Lucena");
        p2.setPhone("698632458");
        p2.setDate("25/11/2014");
        p2.setProvince("Córdoba");
        p2.setState(true);
        persons.add(p2);

        Log.d("PersonaModel", "Creando Contacto 3");
        PersonEntity p3 = new PersonEntity();
        p3.setName("Isabel");
        p3.setEmail("isabel@isabel.com");
        ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
        Bitmap bitmap1 = BitmapFactory.decodeResource(MyApplication.getContext().getResources(), R.drawable.foto2);
        bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, baos1);
        byte[] imageBytes1 = baos1.toByteArray();
        String imageString1 = Base64.encodeToString(imageBytes1, Base64.DEFAULT);
        p3.setImage(imageString1);
        p3.setLocation("Fernan Núñez");
        p3.setPhone("614657952");
        p3.setDate("10/11/1995");
        p3.setProvince("Córdoba");
        p3.setState(true);
        persons.add(p3);

        Log.d("PersonaModel", "Creando Contacto 4");
        PersonEntity p4 = new PersonEntity();
        p4.setName("Marta");
        p4.setEmail("marta@marta.com");
        ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
        Bitmap bitmap2 = BitmapFactory.decodeResource(MyApplication.getContext().getResources(), R.drawable.foto3);
        bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, baos2);
        byte[] imageBytes2 = baos2.toByteArray();
        String imageString2 = Base64.encodeToString(imageBytes2, Base64.DEFAULT);
        p4.setImage(imageString2);
        p4.setLocation("Montilla");
        p4.setPhone("675159753");
        p4.setDate("14/10/1985");
        p4.setProvince("Sevilla");
        p4.setState(true);
        persons.add(p4);

        Log.d("PersonaModel", "Creando Contacto 5");
        PersonEntity p5 = new PersonEntity();
        p5.setName("Alberto");
        p5.setEmail("alberto@alberto.com");
        ByteArrayOutputStream baos3 = new ByteArrayOutputStream();
        Bitmap bitmap3 = BitmapFactory.decodeResource(MyApplication.getContext().getResources(), R.drawable.foto4);
        bitmap3.compress(Bitmap.CompressFormat.JPEG, 100, baos3);
        byte[] imageBytes3 = baos3.toByteArray();
        String imageString3 = Base64.encodeToString(imageBytes3, Base64.DEFAULT);
        p5.setImage(imageString3);
        p5.setLocation("Cabra");
        p5.setPhone("632789542");
        p5.setDate("1/5/1937");
        p5.setProvince("Huelva");
        p5.setState(true);
        persons.add(p5);

        Log.d("PersonaModel", "Creando Contacto 6");
        PersonEntity p6 = new PersonEntity();
        p6.setName("Andrea");
        p6.setEmail("andrea@andrea.com");
        p6.setImage(null);
        p6.setLocation("Madrid");
        p6.setPhone("789452144");
        p6.setDate("1/5/1985");
        p6.setProvince("Madrid");
        p6.setState(true);
        persons.add(p6);

        Log.d("PersonaModel", "Creando Contacto 7");
        PersonEntity p7 = new PersonEntity();
        p7.setName("Ángel");
        p7.setEmail("angel@angel.com");
        p7.setImage(null);
        p7.setLocation("Barcelona");
        p7.setPhone("65492146");
        p7.setDate("4/10/1998");
        p7.setProvince("Barcelona");
        p7.setState(true);
        persons.add(p7);

        Log.d("PersonaModel", "Creando Contacto 8");
        PersonEntity p8 = new PersonEntity();
        p8.setName("Raúl");
        p8.setEmail("raul@raul.com");
        p8.setImage(null);
        p8.setLocation("Galicia");
        p8.setPhone("621975642");
        p8.setDate("6/11/1991");
        p8.setProvince("Galicia");
        p8.setState(true);
        persons.add(p8);

        Log.d("PersonaModel", "Creando Contacto 9");
        PersonEntity p9 = new PersonEntity();
        p9.setName("Luis");
        p9.setEmail("luis@luis.com");
        ByteArrayOutputStream baos4 = new ByteArrayOutputStream();
        Bitmap bitmap4 = BitmapFactory.decodeResource(MyApplication.getContext().getResources(), R.drawable.foto5);
        bitmap4.compress(Bitmap.CompressFormat.JPEG, 100, baos4);
        byte[] imageBytes4 = baos4.toByteArray();
        String imageString4 = Base64.encodeToString(imageBytes4, Base64.DEFAULT);
        p9.setImage(imageString4);
        p9.setLocation("Baena");
        p9.setPhone("635798452");
        p9.setDate("16/4/1994");
        p9.setProvince("Córdoba");
        p9.setState(true);
        persons.add(p9);

        Log.d("PersonaModel", "Creando Contacto 10");
        PersonEntity p10 = new PersonEntity();
        p10.setName("Ana");
        p10.setEmail("ana@ana.com");
        p10.setImage(null);
        p10.setLocation("Espejo");
        p10.setPhone("675987215");
        p10.setDate("20/6/1993");
        p10.setProvince("Córdoba");
        p10.setState(true);
        persons.add(p10);
        return persons;
    }
}