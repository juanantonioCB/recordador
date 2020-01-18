package com.juanantonio.recordador.presenter;


import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

    public ListadoPresenter(ListadoView v) {
        this.listadoActivity = v;
        recyclerView = v.findViewById(R.id.RecyclerId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(v));
        persons = new ArrayList<>();
        persons.add(new Person(1,"a","d",null,"adf","df","df"));

        adapter = new PersonAdapter(persons, v, this);
        recyclerView.setAdapter(adapter);

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
                adapter.removeAt(viewHolder.getAdapterPosition());
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


        PersonaSQLiteHelper usdbh = PersonaSQLiteHelper.get(listadoActivity);
        SQLiteDatabase db = usdbh.getWritableDatabase();

        db.close();
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

