package com.juanantonio.recordador.presenter;
import android.database.sqlite.SQLiteDatabase;

import com.juanantonio.recordador.interfaces.ListadoInterface;
import com.juanantonio.recordador.model.PersonEntity;
import com.juanantonio.recordador.model.PersonaSQLiteHelper;
import com.juanantonio.recordador.view.ListadoView;

import java.util.List;

public class ListadoPresenter implements ListadoInterface.Presenter {
    private ListadoInterface.View view;

    PersonaSQLiteHelper personModel = PersonaSQLiteHelper.get();

    public ListadoPresenter(ListadoView v) {
        SQLiteDatabase db = personModel.getWritableDatabase();
        db.close();
        this.view = v;

    }


    public List<PersonEntity> getPersons() {
        List<PersonEntity> p = personModel.recuperarListado();
        return p;
    }

    public void cargarPersonaBuscada(){
        view.abrirBusqueda();
    }


    public void irFormulario() {
        view.abrirFormulario();
    }

    public void setLayout() {
        view.setLayout();
    }

    @Override
    public void abrirPersona(int position) {
        view.abrirPersona(position);
    }

    @Override
    public void borrarPersona(int id) {
        personModel.eliminarPersona(id);
    }
}

