package com.juanantonio.recordador.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.juanantonio.recordador.R;
import com.juanantonio.recordador.presenter.SearchPresenter;

public class SearchActivity extends AppCompatActivity {

    SearchPresenter presenter;
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
        presenter=new SearchPresenter(this);

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
