package com.juanantonio.recordador.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.juanantonio.recordador.R;
import com.juanantonio.recordador.interfaces.FormularioInterface;
import com.juanantonio.recordador.model.PersonEntity;
import com.juanantonio.recordador.presenter.DatePickerFragment;
import com.juanantonio.recordador.presenter.FormularioPresenter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FormularioView extends AppCompatActivity implements AdapterView.OnItemSelectedListener, FormularioInterface.View {

    public Button addButton;
    public ImageView image;
    public ActionBar actionBar;
    public Button saveButton;
    public EditText nombreEditText;
    public EditText emailEditText;
    public EditText localidadEditText;
    public TextView nombreTextView;
    public TextView emailTextView;
    public TextView telefonoTextView;
    public TextView localidadTextView;
    public EditText telefonoEditText;
    public EditText fechaText;
    public Button addElementButton;
    public Spinner spinner;
    public TextView fechaTextView;
    public Switch statusSwitch;
    public Button deleteButton;
    public int idPersona;
    public ArrayList<String> elementos;
    private ArrayAdapter<String> arrayAdapter;

    FormularioInterface.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addButton = findViewById(R.id.addImageButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.cargarImagen();
            }
        });
        image = findViewById(R.id.image);
        actionBar = getSupportActionBar();
        deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.deletePerson(idPersona);
                onBackPressed();
            }
        });

        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.savePerson();
            }
        });
        nombreEditText = findViewById(R.id.nombreText);
        nombreTextView = findViewById(R.id.nombreTextView);
        emailEditText = findViewById(R.id.emailText);
        emailTextView = findViewById(R.id.emailTextView);
        telefonoTextView = findViewById(R.id.telefonoTextView);
        localidadTextView = findViewById(R.id.localidadTextView);
        localidadEditText = findViewById(R.id.LocalidadText);
        telefonoEditText = findViewById(R.id.telefonoText);
        fechaTextView = findViewById(R.id.fechaTextView);
        fechaText = findViewById(R.id.fechaText);
        fechaText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.showDatePickerDialog();
            }
        });
        addElementButton = findViewById(R.id.addButton);
        addElementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.addProvince();
            }
        });
        statusSwitch = findViewById(R.id.switch10);
        spinner = findViewById(R.id.spinner);

        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);
        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

        //OCULTAMOS LOS MENSAJES DE ERROR
        actionBar.setTitle("Añadir Usuario Nuevo");
        nombreTextView.setVisibility(View.GONE);
        emailTextView.setVisibility(View.GONE);
        localidadTextView.setVisibility(View.GONE);
        telefonoTextView.setVisibility(View.GONE);
        fechaTextView.setVisibility(View.GONE);

        //MENSAJES ONFOCUS
        nombreEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (nombreEditText.getText().toString().equals("")) {
                        nombreTextView.setText("EL nombre no puede ir vacío");
                        nombreTextView.setVisibility(View.VISIBLE);
                    } else {
                        nombreTextView.setVisibility(View.GONE);
                    }
                }
            }
        });
        emailEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (emailEditText.getText().toString().equals("")) {
                        emailTextView.setText("El email no puede ir vacío");
                        emailTextView.setVisibility(View.VISIBLE);
                    } else {
                        emailTextView.setVisibility(View.GONE);
                    }
                }
            }
        });
        localidadEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (localidadEditText.getText().toString().equals("")) {
                        localidadTextView.setText("La localidad no puede ir vacía");
                        localidadTextView.setVisibility(View.VISIBLE);
                    } else {
                        localidadTextView.setVisibility(View.GONE);
                    }
                }
            }
        });
        telefonoEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (telefonoEditText.getText().toString().equals("")) {
                        telefonoTextView.setText("El telefono no puede ir vacío");
                        telefonoTextView.setVisibility(View.VISIBLE);
                    } else {
                        telefonoTextView.setVisibility(View.GONE);
                    }
                }
            }
        });
        presenter = new FormularioPresenter(this);
        presenter.cargarProvincias();
        presenter.cargarPersona();

    }

    @Override
    public void cargarImagen() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    @Override
    public void savePerson() {
        String fotoEnBase64 = null;
        try {
            Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            fotoEnBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
        } catch (java.lang.ClassCastException e) {
            fotoEnBase64 = null;
        }
        PersonEntity p = new PersonEntity();

        //COMPROBAMOS SI TODO ESTA CORRECTO

        if (p.setName(nombreEditText.getText().toString()) &&
                p.setEmail(emailEditText.getText().toString()) &&
                p.setLocation(localidadEditText.getText().toString()) &&
                p.setPhone(telefonoEditText.getText().toString()) &&
                p.setDate(fechaText.getText().toString())) {
            p.setImage(fotoEnBase64);
            p.setState(statusSwitch.isChecked());

            p.setProvince(spinner.getSelectedItem().toString());
            System.out.println("ESTADO" + statusSwitch.isChecked());
            if (idPersona == 0) {
                presenter.insertPerson(p);
                System.out.println("inserta");
            } else {
                p.setId(idPersona);
                presenter.updatePerson(p);
                System.out.println("actualiza");
            }
            onBackPressed();
        } else {
            if (!p.setName(nombreEditText.getText().toString())) {
                nombreTextView.setText("EL nombre es muy corto");
                nombreTextView.setVisibility(View.VISIBLE);
            } else {
                nombreTextView.setVisibility(View.GONE);
            }
            if (!p.setEmail(emailEditText.getText().toString())) {
                emailTextView.setText("EL email es incorrecto");
                emailTextView.setVisibility(View.VISIBLE);
            } else {
                emailTextView.setVisibility(View.GONE);
            }
            if (!p.setLocation(localidadEditText.getText().toString())) {
                localidadTextView.setText("La localización es incorrecta");
                localidadTextView.setVisibility(View.VISIBLE);
            } else {
                localidadTextView.setVisibility(View.GONE);
            }
            if (!p.setPhone(telefonoEditText.getText().toString())) {
                telefonoTextView.setText("EL teléfono es incorrecto");
                telefonoTextView.setVisibility(View.VISIBLE);
            } else {
                telefonoTextView.setVisibility(View.GONE);
            }
            if (!p.setDate(fechaText.getText().toString())) {
                fechaTextView.setText("La fecha es incorrecta");
                fechaTextView.setVisibility(View.VISIBLE);
            } else {
                fechaTextView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void storagePermissions() {
        int WriteExternalStoragePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        Log.d("MainActivity", "WRITE_EXTERNAL_STORAGE Permission: " + WriteExternalStoragePermission);

        if (WriteExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
            // Permiso denegado
            // A partir de Marshmallow (6.0) se pide aceptar o rechazar el permiso en tiempo de ejecución
            // En las versiones anteriores no es posible hacerlo
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123);
                // Una vez que se pide aceptar o rechazar el permiso se ejecuta el método "onRequestPermissionsResult" para manejar la respuesta
                // Si el usuario marca "No preguntar más" no se volverá a mostrar este diálogo
            } else {
                /*Snackbar.make(constraintLayoutMainActivity, getResources().getString(R.string.write_permission_denied), Snackbar.LENGTH_LONG)
                        .show();*/
            }
        } else {
            // Permiso aceptado
           /* Snackbar.make(constraintLayoutMainActivity, getResources().getString(R.string.write_permission_granted), Snackbar.LENGTH_LONG)
                    .show();*/
        }

    }

    @Override
    public void addProvince() {
        AlertDialog.Builder mydialog = new AlertDialog.Builder(this);
        mydialog.setTitle("Introduce una opción");
        final EditText opcionInput = new EditText(this);
        opcionInput.setInputType(InputType.TYPE_CLASS_TEXT);
        mydialog.setView(opcionInput);
        mydialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String myText = opcionInput.getText().toString();
                System.out.println(myText);
                if (myText.length() > 0) {
                    elementos.add(myText);
                    spinner.setSelection(elementos.size() - 1);
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

    @Override
    public void cargarProvincias(List<String> provinces) {
        if (provinces != null) {
            elementos = (ArrayList<String>) provinces;
        } else {
            elementos = new ArrayList<>();
            elementos.add("Ninguna");
        }
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, elementos);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
    }

    @Override
    public void cargarPersona() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
            idPersona = bundle.getInt("id");
        Log.d("ID persona", String.valueOf(idPersona));
        deleteButton.setVisibility(View.GONE);

        if (idPersona != 0) {
            actionBar.setTitle("Editar Usuario");
            deleteButton.setVisibility(View.VISIBLE);
            PersonEntity p = presenter.getPerson(idPersona);
            nombreEditText.setText(p.getName());
            emailEditText.setText(p.getEmail());
            localidadEditText.setText(p.getLocation());
            telefonoEditText.setText(p.getPhone());
            fechaText.setText(p.getDate());
            statusSwitch.setChecked(p.getState());
            spinner.setSelection(arrayAdapter.getPosition(p.getProvince()));
            if (p.getImage() != null) {
                byte[] decodedString = Base64.decode(p.getImage(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                image.setImageBitmap(decodedByte);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: //hago un case por si en un futuro agrego mas opciones
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setLayout() {
        if (getResources().getConfiguration().orientation == 1) {
            setContentView(R.layout.formulario_view);
        } else {
            setContentView(R.layout.formulario_view);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                Bitmap imagenFinal = Bitmap.createScaledBitmap(bitmap, 100, 100, false);
                image.setImageBitmap(imagenFinal);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(FormularioView.this, ListadoView.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        System.out.println(item);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void showDatePicker() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                final String selectedDate = day + "/" + (month + 1) + "/" + year;
                fechaText.setText(selectedDate);
            }
        });
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
}
