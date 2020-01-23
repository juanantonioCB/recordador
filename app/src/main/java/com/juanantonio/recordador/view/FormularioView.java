package com.juanantonio.recordador.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.juanantonio.recordador.R;
import com.juanantonio.recordador.presenter.FormularioPresenter;

import java.io.IOException;
import java.util.ArrayList;

public class FormularioView extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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

    FormularioPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout();
        addButton = findViewById(R.id.addImageButton);
        image = findViewById(R.id.image);
        actionBar = getSupportActionBar();
        saveButton = findViewById(R.id.saveButton);
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
        addElementButton = findViewById(R.id.addButton);
        statusSwitch=findViewById(R.id.switch10);
        spinner = findViewById(R.id.spinner);
        /*elementos = new ArrayList<>();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, elementos);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);*/
        presenter = new FormularioPresenter(this);
        presenter.cargarPersona();
    }

    private void setLayout() {
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
}
