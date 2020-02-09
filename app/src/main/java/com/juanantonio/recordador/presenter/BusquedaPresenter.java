package com.juanantonio.recordador.presenter;

import com.juanantonio.recordador.interfaces.BusquedaInterface;
import com.juanantonio.recordador.model.PersonaSQLiteHelper;
import com.juanantonio.recordador.view.BusquedaView;

public class BusquedaPresenter implements BusquedaInterface.Presenter {
    BusquedaInterface.View view;
    private PersonaSQLiteHelper db;

    public BusquedaPresenter(BusquedaView view) {
        this.view = view;
        this.db = PersonaSQLiteHelper.get();
    }

    @Override
    public void cerrarBusqueda() {
        view.cerrar();

    }

    @Override
    public void cargarProvincias() {
        view.cargarProvincias(db.recuperarProvincias());
    }

    @Override
    public void openDatePicker() {
        view.openDatePicker();
    }
}
