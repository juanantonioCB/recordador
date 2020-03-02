package com.juanantonio.recordador.interfaces;

public interface AyudaInterface {
    public interface View{
        void cargarAyuda(String desde);
    }
    public interface Presenter{
        void cargarAyuda(String desde);
    }
}
