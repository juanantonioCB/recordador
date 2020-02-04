package com.juanantonio.recordador.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.juanantonio.recordador.R;
import com.juanantonio.recordador.presenter.BusquedaPresenter;

public class BusquedaActivity extends AppCompatActivity {

    BusquedaPresenter presenter;
    public EditText nombreEditText;
    public EditText fechaEditText;
    public Spinner provinciaSpinner;
    public Button searchButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nombreEditText=findViewById(R.id.nombreEditText);
        fechaEditText=findViewById(R.id.fechaEditText);
        provinciaSpinner=findViewById(R.id.provinceSpinner);
        searchButton=findViewById(R.id.searchButton);
        presenter=new BusquedaPresenter(this);

    }

    public void cerrar(){
        Intent i =new Intent();
        i.putExtra("fecha",fechaEditText.getText().toString());
        i.putExtra("nombre",nombreEditText.getText().toString());

        //i.putExtra("provincia",provinciaSpinner.getSelectedItem().toString());
        setResult(RESULT_OK,i);
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
