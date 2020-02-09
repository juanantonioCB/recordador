package com.juanantonio.recordador.interfaces;

import java.util.List;

public interface BusquedaInterface {
    public interface View{
        void cargarProvincias(List<String> provincias);
        void cerrar();

        void openDatePicker();
    }
    public interface Presenter{
        void cerrarBusqueda();
        void cargarProvincias();

        void openDatePicker();
    }
}
