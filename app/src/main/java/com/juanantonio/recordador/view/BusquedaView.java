package com.juanantonio.recordador.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.juanantonio.recordador.R;
import com.juanantonio.recordador.interfaces.BusquedaInterface;
import com.juanantonio.recordador.model.PersonEntity;
import com.juanantonio.recordador.presenter.BusquedaPresenter;
import com.juanantonio.recordador.presenter.DatePickerFragment;

import java.util.ArrayList;
import java.util.List;

public class BusquedaView extends AppCompatActivity implements BusquedaInterface.View {

    BusquedaInterface.Presenter presenter;
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
        fechaEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.openDatePicker();
            }
        });
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

    @Override
    public void cargarProvincias(List<String> provincias) {
        if (provincias != null) {
            this.provincias = (ArrayList<String>) provincias;

        } else {
            this.provincias = new ArrayList<>();
            //this.provincias.add("Ninguna");
        }

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, this.provincias);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        provinciaSpinner.setAdapter(arrayAdapter);
    }

    @Override
    public void cerrar() {
        Intent i = new Intent();
        i.putExtra("fecha", fechaEditText.getText().toString());
        i.putExtra("nombre", nombreEditText.getText().toString());

        i.putExtra("provincia", provinciaSpinner.getSelectedItem().toString());
        setResult(RESULT_OK, i);
        finish();
    }

    @Override
    public void onStart(){
        super.onStart();
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onStop(){
        super.onStop();
    }

    @Override
    public void onRestart(){
        super.onRestart();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    @Override
    public void openDatePicker() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                String d=String.valueOf(day);
                String m=String.valueOf(month+1);
                String y=String.valueOf(year);
                if(day<10){
                    d="0"+d;
                }
                if(month<10){
                    m="0"+m;
                }
                final String selectedDate = d + "/" + m + "/" + y;
                fechaEditText.setText(selectedDate);
            }
        });
        newFragment.show(getSupportFragmentManager(), "datePicker");
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
