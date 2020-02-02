package com.juanantonio.recordador.presenter;

import android.content.Intent;
import android.view.View;

import com.juanantonio.recordador.view.ListadoView;
import com.juanantonio.recordador.view.SearchActivity;

public class SearchPresenter {
    SearchActivity view;

    public SearchPresenter(SearchActivity view) {
        this.view = view;
        view.searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openListado();
            }
        });
    }

    private void openListado(){
        Intent i = new Intent(view.getApplicationContext(), ListadoView.class);
        if(view.nombreEditText.getText().length()>0){
            i.putExtra("nombre",view.nombreEditText.getText());
            
        }
        view.startActivity(i);

    }
}
