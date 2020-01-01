package com.juanantonio.recordador.presenter;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.juanantonio.recordador.R;
import com.juanantonio.recordador.view.FormularioView;
import com.juanantonio.recordador.view.ListadoView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormularioPresenter {
    FormularioView view;
    private Context myContext;


    public FormularioPresenter(final FormularioView view) {
        this.view = view;
        this.view.actionBar.setTitle("Añadir Usuario Nuevo");
        view.nombreTextView.setVisibility(View.GONE);
        view.emailTextView.setVisibility(View.GONE);
        view.localidadTextView.setVisibility(View.GONE);
        view.telefonoTextView.setVisibility(View.GONE);



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
                Intent intent = new Intent(v.getContext(), ListadoView.class);
                view.startActivity(intent);

            }
        });

        view.nombreEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (view.nombreEditText.getText().toString().equals("")) {
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
                    if (!validaEmail(view.emailEditText.getText().toString())) {

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
                    if (!validaTelefono(view.telefonoEditText.getText().toString())) {
                        view.telefonoTextView.setVisibility(View.VISIBLE);
                    } else {
                        view.telefonoTextView.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                final String selectedDate = day + " / " + (month + 1) + " / " + year;
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
        int InternetPermission = ContextCompat.checkSelfPermission(myContext, Manifest.permission.INTERNET);
        Log.d("MainActivity", "INTERNET Permission: " + InternetPermission);

        if (InternetPermission != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            String[] mimeTypes = {"image/jpeg", "image/png"};
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            // Launching the Intent
            view.startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
        } else {
            System.out.println("permiso denegado");
        }


    }
}
