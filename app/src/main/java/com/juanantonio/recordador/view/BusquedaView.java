package com.juanantonio.recordador.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.juanantonio.recordador.R;
import com.juanantonio.recordador.model.PersonEntity;
import com.juanantonio.recordador.presenter.BusquedaPresenter;

import java.util.ArrayList;
import java.util.List;

public class BusquedaView extends AppCompatActivity {

    BusquedaPresenter presenter;
    public EditText nombreEditText;
    public EditText fechaEditText;
    public Spinner provinciaSpinner;
    public Button searchButton;
    public List<String> provincias;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new BusquedaPresenter(this);

        nombreEditText = findViewById(R.id.nombreEditText);
        fechaEditText = findViewById(R.id.fechaEditText);
        provinciaSpinner = findViewById(R.id.provinceSpinner);
        searchButton = findViewById(R.id.searchButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.cerrarBusqueda();
            }
        });

        presenter.cargarProvincias();
    }

    public void cargarProvincias(List<String> provincias) {
        if (provincias != null) {
            this.provincias = (ArrayList<String>) provincias;

        } else {
            this.provincias = new ArrayList<>();
            this.provincias.add("Ninguna");
        }

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, this.provincias);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        provinciaSpinner.setAdapter(arrayAdapter);
    }

    public void cerrar() {
        Intent i = new Intent();
        i.putExtra("fecha", fechaEditText.getText().toString());
        i.putExtra("nombre", nombreEditText.getText().toString());

        i.putExtra("provincia", provinciaSpinner.getSelectedItem().toString());
        setResult(RESULT_OK, i);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
