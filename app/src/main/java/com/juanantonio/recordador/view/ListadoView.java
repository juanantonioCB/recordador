package com.juanantonio.recordador.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.juanantonio.recordador.R;
import com.juanantonio.recordador.model.Person;
import com.juanantonio.recordador.presenter.ListadoPresenter;

import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

public class ListadoView extends AppCompatActivity {

    private ListadoPresenter presenter;
    public FloatingActionButton addButton;
    public RecyclerView rv;
    private ArrayList<Person> persons;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.search) {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.sobre_appcrud) {
            Intent intent = new Intent(this, SobreAppCrud.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout();
        addButton = findViewById(R.id.addButton);
        rv = findViewById(R.id.recyclerView);

        presenter = new ListadoPresenter(this);


    }

    private void setLayout() {
        if (getResources().getConfiguration().orientation == 1) {
            setContentView(R.layout.listado_view);
        } else {
            setContentView(R.layout.listado_view_landcape);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
