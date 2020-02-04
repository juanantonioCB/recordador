package com.juanantonio.recordador.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.juanantonio.recordador.R;
import com.juanantonio.recordador.model.Person;
import com.juanantonio.recordador.presenter.ListadoPresenter;

import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

public class ListadoView extends AppCompatActivity {

    private ListadoPresenter presenter;
    public FloatingActionButton addButton;
    public RecyclerView rv;
    public TextView nElementos;
    private ArrayList<Person> persons;
    private static int resultCode = 1;
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.search) {
            presenter.cargarPersonaBuscada();
        }
        if (item.getItemId() == R.id.sobre_appcrud) {
            Intent intent = new Intent(this, SobreAppCrud.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void abrirBusqueda(){
        Intent i =new Intent(this, BusquedaActivity.class);

        startActivityForResult(i,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1){
            if(resultCode==RESULT_OK){
                System.out.println("rec "+data.getExtras().getString("fecha"));
                System.out.println("-----"+data.getExtras().getString("nombre"));
                System.out.println("-----"+data.getExtras().getString("provincia"));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.reload();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout();
        nElementos = findViewById(R.id.nElementosTextView);
        addButton = findViewById(R.id.addButton);
        rv = findViewById(R.id.recyclerView);
        presenter = new ListadoPresenter(this);
    }

    private void setLayout() {
        setContentView(R.layout.listado_view);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
