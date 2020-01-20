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
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.juanantonio.recordador.R;
import com.juanantonio.recordador.model.PersonaSQLiteHelper;
import com.juanantonio.recordador.view.FormularioView;
import com.juanantonio.recordador.view.ListadoView;

import java.io.ByteArrayOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormularioPresenter {
    FormularioView view;
    private Context myContext;
    private int idPersona;
    private PersonaSQLiteHelper db;

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
                        view.elementos.add(myText);
                        view.spinner.setSelection(view.elementos.size() - 1);
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
    }

    private void savePerson() {
        String fotoEnBase64;
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
                p.setImage(fotoEnBase64) &&
                p.setLocation(view.localidadEditText.getText().toString()) &&
                p.setPhone(view.telefonoEditText.getText().toString()) &&
                p.setDate(view.fechaText.getText().toString())) {
            db.insertarPersona(p);
            Intent intent = new Intent(view.getApplicationContext(), ListadoView.class);
            view.startActivity(intent);
        }else{
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

    private boolean validaEmail(String email) {
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(email);
        if (mather.find() == true) {
            return true;
        } else {
            return false;
        }
    }

    private boolean validaTelefono(String telefono) {
        Pattern pattern = Pattern
                .compile("^(\\+34|0034|34)?[6|7|8|9][0-9]{8}$");
        Matcher mather = pattern.matcher(telefono);
        if (mather.find() == true) {
            return true;
        } else {
            return false;
        }
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

            Person p = db.recuperarPersona(idPersona);
            view.nombreEditText.setText(p.getName());
            view.emailEditText.setText(p.getEmail());
            view.localidadEditText.setText(p.getLocation());
            view.telefonoEditText.setText(p.getPhone());
            view.fechaText.setText(p.getDate());
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
