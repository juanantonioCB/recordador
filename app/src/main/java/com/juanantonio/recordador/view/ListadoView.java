package com.juanantonio.recordador.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.juanantonio.recordador.R;
import com.juanantonio.recordador.interfaces.ListadoInterface;
import com.juanantonio.recordador.model.PersonEntity;
import com.juanantonio.recordador.presenter.ListadoPresenter;
import com.juanantonio.recordador.presenter.PersonAdapter;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class ListadoView extends AppCompatActivity implements PersonAdapter.onPersonListener, ListadoInterface.View {

    private ListadoInterface.Presenter presenter;
    public FloatingActionButton addButton;
    public RecyclerView rv;
    public TextView nElementos;
    public List<PersonEntity> personEntities;
    public RecyclerView recyclerView;
    public PersonAdapter adapter;
    private boolean estaBuscando;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ListadoPresenter(this);
        presenter.setLayout();
        estaBuscando=false;
        personEntities = new ArrayList<>();
        nElementos = findViewById(R.id.nElementosTextView);
        addButton = findViewById(R.id.addButton);
        rv = findViewById(R.id.recyclerView);
        recyclerView = findViewById(R.id.RecyclerId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.irFormulario();
            }
        });

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int id=personEntities.get(viewHolder.getAdapterPosition()).getId();
                presenter.borrarPersona(id);
                adapter.removeAt(viewHolder.getAdapterPosition());
                presenter.getPersons();
                Toast.makeText(getApplicationContext(), "Borrado Correctamente", Toast.LENGTH_SHORT).show();
                Log.d("Borrado", String.valueOf(viewHolder.getAdapterPosition()));
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                final int fromPos = viewHolder.getAdapterPosition();
                final int toPos = target.getAdapterPosition();
                return true;
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        presenter.getPersons();

    }

    public void reload(List<PersonEntity> persons) {
        personEntities=persons;
        adapter = new PersonAdapter(personEntities, this, this);
        recyclerView.setAdapter(adapter);
        nElementos.setText("Número de elementos: " + String.valueOf(adapter.getItemCount()));
    }

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

    public void abrirBusqueda() {
        Intent i = new Intent(this, BusquedaView.class);
        startActivityForResult(i, 1);
    }

    @Override
    public void abrirPersona(int position) {
        Intent intent = new Intent(this, FormularioView.class);
        System.out.println(personEntities);
        intent.putExtra("id", personEntities.get(position).getId());
        startActivity(intent);
        Log.d("clicked", String.valueOf(position));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                presenter.cargarBusqueda(data.getExtras().getString("nombre"),
                        data.getExtras().getString("provincia"),
                        data.getExtras().getString("fecha"));
                estaBuscando=true;
            }
        }
    }

    public void abrirFormulario() {
        Intent intent = new Intent(this, FormularioView.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!estaBuscando){
            presenter.getPersons();
        }
        estaBuscando=false;

    }

    public void setLayout() {
        setContentView(R.layout.listado_view);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onPersonClick(int position) {
        presenter.abrirPersona(position);
    }
}
