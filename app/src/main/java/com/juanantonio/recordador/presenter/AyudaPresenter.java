package com.juanantonio.recordador.presenter;

import com.juanantonio.recordador.interfaces.AyudaInterface;
import com.juanantonio.recordador.view.AyudaView;

public class AyudaPresenter implements AyudaInterface.Presenter {
    AyudaView view;
    public AyudaPresenter(AyudaView view){
        this.view=view;
    }
    @Override
    public void cargarAyuda(String desde) {
        this.view.cargarAyuda(desde);
    }
}
