package com.juanantonio.recordador.presenter;

import android.view.View;

import com.juanantonio.recordador.model.PersonaSQLiteHelper;
import com.juanantonio.recordador.view.BusquedaView;

public class BusquedaPresenter {
    BusquedaView view;
    private PersonaSQLiteHelper db;

    public BusquedaPresenter(BusquedaView view) {
        this.view = view;
        this.db=PersonaSQLiteHelper.get();
    }

    public void cerrarBusqueda(){
        view.cerrar();

    }

    public void cargarProvincias() {
        view.cargarProvincias(db.recuperarProvincias());
    }
}
