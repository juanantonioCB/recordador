package com.juanantonio.recordador.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.util.Base64;
import android.util.Log;
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

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {

    private List<Person> listPersons;
    private Context context;
    private onPersonListener monPersonListener;

    public PersonAdapter(List<Person> listPersons, Context context, onPersonListener onPersonListener) {
        this.listPersons = listPersons;
        this.context = context;
        this.monPersonListener = onPersonListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        return new ViewHolder(v, monPersonListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Person p = listPersons.get(position);
        holder.textViewName.setText(p.getName());
        holder.textViewSurname.setText(p.getSurname());
        byte[] decodedString = Base64.decode(p.getImage(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        holder.imageView.setImageBitmap(decodedByte);
    }

    @Override
    public int getItemCount() {
        return listPersons.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageView;
        public TextView textViewName;
        public TextView textViewSurname;
        onPersonListener personListener;

        public ViewHolder(@NonNull View itemView, onPersonListener personListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageRecyclerView);
            textViewName = itemView.findViewById(R.id.nameRecyclerView);
            textViewSurname = itemView.findViewById(R.id.surnameRecyclerView);
            this.personListener = personListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            personListener.onPersonClick(getAdapterPosition());
        }
    }

    public interface onPersonListener {
        void onPersonClick(int position);
    }

    public void removeAt(int position) {
        listPersons.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listPersons.size());
    }


}
