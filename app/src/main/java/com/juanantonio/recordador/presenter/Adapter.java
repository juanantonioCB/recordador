package com.juanantonio.recordador.presenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.juanantonio.recordador.R;
import com.juanantonio.recordador.model.Person;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

    private List<Person> listPersons;
    private Context context;

    public Adapter(List<Person> listPersons, Context context) {
        this.listPersons = listPersons;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Person p=listPersons.get(position);
        holder.textViewName.setText(p.getName());
        System.out.println(p.getSurname());
        holder.textViewSurname.setText(p.getSurname());

    }

    @Override
    public int getItemCount() {
        return listPersons.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageView;
        public TextView textViewName;
        public TextView textViewSurname;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageRecyclerView);
            textViewName=itemView.findViewById(R.id.nameRecyclerView);
            textViewName=itemView.findViewById(R.id.surnameRecyclerView);
        }
    }
}
