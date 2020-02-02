package com.juanantonio.recordador.presenter;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.juanantonio.recordador.R;
import com.juanantonio.recordador.model.Person;
import com.juanantonio.recordador.model.PersonaSQLiteHelper;
import com.juanantonio.recordador.view.FormularioView;
import com.juanantonio.recordador.view.ListadoView;

import java.util.ArrayList;
import java.util.List;

public class ListadoPresenter implements PersonAdapter.onPersonListener {
    private ListadoView listadoActivity;
    private RecyclerView recyclerView;
    private PersonAdapter adapter;
    private List<Person> persons;
    PersonaSQLiteHelper usdbh = PersonaSQLiteHelper.get();

    public ListadoPresenter(ListadoView v) {


        SQLiteDatabase db = usdbh.getWritableDatabase();

        db.close();

        persons = new ArrayList<>();
        this.listadoActivity = v;
        recyclerView = v.findViewById(R.id.RecyclerId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(v));
        listadoActivity.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), FormularioView.class);
                listadoActivity.startActivity(intent);
                listadoActivity.finish();
            }
        });

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {


                int id=persons.get(viewHolder.getAdapterPosition()).getId();
                usdbh.eliminarPersona(id);
                adapter.removeAt(viewHolder.getAdapterPosition());
                reload();
                Toast.makeText(listadoActivity.getApplicationContext(), "Borrado Correctamente", Toast.LENGTH_SHORT).show();
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

    }

    public void reload() {
        persons = usdbh.recuperarListado();
        adapter = new PersonAdapter(persons, listadoActivity, this);
        recyclerView.setAdapter(adapter);
        listadoActivity.nElementos.setText("Número de elementos: " + String.valueOf(adapter.getItemCount()));
    }

    @Override
    public void onPersonClick(int position) {
        persons.get(position);
        Intent intent = new Intent(listadoActivity.getApplicationContext(), FormularioView.class);
        intent.putExtra("id", persons.get(position).getId());
        listadoActivity.startActivity(intent);
        Log.d("clicked", String.valueOf(position));
    }
}

