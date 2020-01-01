package com.juanantonio.recordador.presenter;


import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.juanantonio.recordador.R;
import com.juanantonio.recordador.model.Person;
import com.juanantonio.recordador.view.FormularioView;
import com.juanantonio.recordador.view.ListadoView;

import java.util.ArrayList;
import java.util.List;


public class ListadoPresenter {
    private ListadoView listadoActivity;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Person> persons;


    public ListadoPresenter(ListadoView v) {
        this.listadoActivity = v;
        recyclerView=v.findViewById(R.id.RecyclerId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(v));
        persons=new ArrayList<>();
        persons.add(new Person(null,"nombre","apellido",null));
        adapter=new Adapter(persons,v);
        recyclerView.setAdapter(adapter);

        listadoActivity.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), FormularioView.class);
                listadoActivity.startActivity(intent);
                listadoActivity.finish();
            }
        });

    }



}

