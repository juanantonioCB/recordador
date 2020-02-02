package com.juanantonio.recordador.presenter;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;

import com.juanantonio.recordador.model.Person;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.core.app.ActivityCompat;

import com.google.android.material.snackbar.Snackbar;
import com.juanantonio.recordador.R;
import com.juanantonio.recordador.model.PersonaSQLiteHelper;
import com.juanantonio.recordador.view.FormularioView;
import com.juanantonio.recordador.view.ListadoView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class FormularioPresenter {
    FormularioView view;
    private Context myContext;
    private int idPersona;
    private PersonaSQLiteHelper db;
    public ArrayList<String> elementos;
    private ArrayAdapter<String> arrayAdapter = null;
    public FormularioPresenter(final FormularioView view) {
        this.view = view;
        this.db = PersonaSQLiteHelper.get();
        this.view.actionBar.setTitle("Añadir Usuario Nuevo");
        view.nombreTextView.setVisibility(View.GONE);
        view.emailTextView.setVisibility(View.GONE);
        view.localidadTextView.setVisibility(View.GONE);
        view.telefonoTextView.setVisibility(View.GONE);
        view.fechaTextView.setVisibility(View.GONE);
        Bundle bundle = view.getIntent().getExtras();

        if (bundle != null)
            idPersona = bundle.getInt("id");
        Log.d("ID persona", String.valueOf(idPersona));

        elementos = new ArrayList<>();
        elementos.add("Ninguna");

        if (db.recuperarProvincias() != null) {
            elementos = (ArrayList<String>) db.recuperarProvincias();
            arrayAdapter = new ArrayAdapter<String>(view, android.R.layout.simple_spinner_item, elementos);
        } else {
            arrayAdapter = new ArrayAdapter<String>(view, android.R.layout.simple_spinner_item, elementos);

        }
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        view.spinner.setAdapter(arrayAdapter);
        view.spinner.setOnItemSelectedListener(view);
        view.spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

        view.addElementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mydialog = new AlertDialog.Builder(view);
                mydialog.setTitle("Introduce una opción");
                final EditText opcionInput = new EditText(view);
                opcionInput.setInputType(InputType.TYPE_CLASS_TEXT);
                mydialog.setView(opcionInput);
                mydialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String myText = opcionInput.getText().toString();
                        if (myText.length() > 0) {
                            elementos.add(myText);
                            view.spinner.setSelection(elementos.size() - 1);
                        }

                    }
                });
                mydialog.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                mydialog.show();
            }
        });
        view.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarImagen();
            }
        });

        view.fechaText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.fechaText:
                        showDatePickerDialog();
                        break;
                }
            }
        });
        view.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePerson();


            }

        });

        view.nombreEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (view.nombreEditText.getText().toString().equals("")) {
                        view.nombreTextView.setText("EL nombre no puede ir vacío");
                        view.nombreTextView.setVisibility(View.VISIBLE);
                    } else {
                        view.nombreTextView.setVisibility(View.GONE);
                    }
                }
            }
        });
        view.emailEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (view.emailEditText.getText().toString().equals("")) {
                        view.emailTextView.setText("El email no puede ir vacío");
                        view.emailTextView.setVisibility(View.VISIBLE);
                    } else {
                        view.emailTextView.setVisibility(View.GONE);
                    }
                }
            }
        });
        view.localidadEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (view.localidadEditText.getText().toString().equals("")) {
                        view.localidadTextView.setText("La localidad no puede ir vacía");
                        view.localidadTextView.setVisibility(View.VISIBLE);
                    } else {
                        view.localidadTextView.setVisibility(View.GONE);
                    }
                }
            }
        });
        view.telefonoEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (view.telefonoEditText.getText().toString().equals("")) {
                        view.telefonoTextView.setText("El telefono no puede ir vacío");
                        view.telefonoTextView.setVisibility(View.VISIBLE);
                    } else {
                        view.telefonoTextView.setVisibility(View.GONE);
                    }
                }
            }
        });
        view.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(idPersona!=0){
                    db.eliminarPersona(idPersona);
                    view.onBackPressed();
                }

            }
        });
        if(idPersona==0){
            view.deleteButton.setVisibility(View.INVISIBLE);
        }
    }

    private void updatePerson() {

    }

    private void savePerson() {
        String fotoEnBase64 = null;
        try {
            Bitmap bitmap = ((BitmapDrawable) view.image.getDrawable()).getBitmap();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            fotoEnBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
        } catch (java.lang.ClassCastException e) {
            fotoEnBase64 = null;
        }

        Person p = new Person();

        //COMPROBAMOS SI TODO ESTA CORRECTO
        if (p.setName(view.nombreEditText.getText().toString()) &&
                p.setEmail(view.emailEditText.getText().toString()) &&
                p.setLocation(view.localidadEditText.getText().toString()) &&
                p.setPhone(view.telefonoEditText.getText().toString()) &&
                p.setDate(view.fechaText.getText().toString())) {
            p.setImage(fotoEnBase64);
            p.setState(view.statusSwitch.isChecked());

            p.setProvince(view.spinner.getSelectedItem().toString());
            System.out.println("ESTADO" + view.statusSwitch.isChecked());
            if (idPersona == 0) {
                db.insertarPersona(p);
                System.out.println("inserta");
            } else {
                p.setId(idPersona);
                db.actualizarPersona(p);
                System.out.println("actualiza");
            }
            /*Intent intent = new Intent(view.getApplicationContext(), ListadoView.class);
            view.startActivity(intent);*/
            view.onBackPressed();
        } else {
            if (!p.setName(view.nombreEditText.getText().toString())) {
                view.nombreTextView.setText("EL nombre es muy corto");
                view.nombreTextView.setVisibility(View.VISIBLE);
            } else {
                view.nombreTextView.setVisibility(View.GONE);
            }
            if (!p.setEmail(view.emailEditText.getText().toString())) {
                view.emailTextView.setText("EL email es incorrecto");
                view.emailTextView.setVisibility(View.VISIBLE);
            } else {
                view.emailTextView.setVisibility(View.GONE);
            }
            if (!p.setLocation(view.localidadEditText.getText().toString())) {
                view.localidadTextView.setText("La localización es incorrecta");
                view.localidadTextView.setVisibility(View.VISIBLE);
            } else {
                view.localidadTextView.setVisibility(View.GONE);
            }
            if (!p.setPhone(view.telefonoEditText.getText().toString())) {
                view.telefonoTextView.setText("EL teléfono es incorrecto");
                view.telefonoTextView.setVisibility(View.VISIBLE);
            } else {
                view.telefonoTextView.setVisibility(View.GONE);
            }
            if (!p.setDate(view.fechaText.getText().toString())) {
                view.fechaTextView.setText("La fecha es incorrecta");
                view.fechaTextView.setVisibility(View.VISIBLE);
            } else {
                view.fechaTextView.setVisibility(View.GONE);
            }
        }

    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                final String selectedDate = day + "/" + (month + 1) + "/" + year;
                view.fechaText.setText(selectedDate);
            }
        });
        newFragment.show(view.getSupportFragmentManager(), "datePicker");
    }


    private void cargarImagen() {
        storagePermissions();
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        view.startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    public void cargarPersona() {
        if (idPersona != 0) {
            this.view.actionBar.setTitle("Editar Usuario");
            Person p = db.recuperarPersona(idPersona);
            view.nombreEditText.setText(p.getName());
            view.emailEditText.setText(p.getEmail());
            view.localidadEditText.setText(p.getLocation());
            view.telefonoEditText.setText(p.getPhone());
            view.fechaText.setText(p.getDate());
            view.statusSwitch.setChecked(p.getState());
            view.spinner.setSelection(arrayAdapter.getPosition(p.getProvince()));

            if (p.getImage() != null) {
                byte[] decodedString = Base64.decode(p.getImage(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                view.image.setImageBitmap(decodedByte);
            }
        }
    }


    private void storagePermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(view, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(view)
                    .setTitle("Permission needed")
                    .setMessage("Se necesitan permisos para acceder a la galería")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(view,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Snackbar.make(view.getWindow().getDecorView().getRootView(), "Se necesitan permisos", Snackbar.LENGTH_SHORT).show();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(view, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

    }

}
