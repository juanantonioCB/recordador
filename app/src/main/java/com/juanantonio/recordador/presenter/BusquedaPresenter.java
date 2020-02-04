package com.juanantonio.recordador.presenter;

import android.view.View;

import com.juanantonio.recordador.view.BusquedaActivity;

public class BusquedaPresenter {
    BusquedaActivity view;

    public BusquedaPresenter(BusquedaActivity view) {
        this.view = view;
        view.searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openListado();
            }
        });
    }

    private void openListado(){
        view.cerrar();

    }
}
