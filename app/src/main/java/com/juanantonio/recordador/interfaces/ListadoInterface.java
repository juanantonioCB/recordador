package com.juanantonio.recordador.interfaces;


import com.juanantonio.recordador.model.PersonEntity;

import java.util.List;

public interface ListadoInterface {
    public interface View{
    void abrirFormulario();
    void setLayout();
    void abrirBusqueda();
    void abrirPersona(int position);
    void reload(List<PersonEntity> p);
    }

    public interface Presenter{
        void getPersons();
        void cargarPersonaBuscada();
        void irFormulario();
        void setLayout();
        void abrirPersona(int position);
        void borrarPersona(int id);

        void cargarBusqueda(String nombre, String provincia, String fecha);
    }
}
