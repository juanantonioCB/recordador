package com.juanantonio.recordador.presenter;

import com.juanantonio.recordador.interfaces.FormularioInterface;
import com.juanantonio.recordador.model.PersonEntity;
import com.juanantonio.recordador.model.PersonaModel;
import com.juanantonio.recordador.view.FormularioView;


public class FormularioPresenter implements FormularioInterface.Presenter {
    FormularioInterface.View view;
    private PersonaModel db;

    public FormularioPresenter(final FormularioView view) {
        this.view = view;
        this.db = PersonaModel.get();
    }

    @Override
    public void cargarProvincias() {
        view.cargarProvincias(db.recuperarProvincias());
    }

    @Override
    public void addProvince() {
        view.addProvince();
    }

    @Override
    public void savePerson() {
        view.savePerson();
    }

    @Override
    public void deletePerson(int idPersona) {
        if (idPersona != 0) {
            db.eliminarPersona(idPersona);
        }
    }

    @Override
    public void insertPerson(PersonEntity p) {
        db.insertarPersona(p);
    }

    @Override
    public void updatePerson(PersonEntity p) {
        db.actualizarPersona(p);
    }

    @Override
    public void showDatePickerDialog() {
        view.showDatePicker();
    }

    @Override
    public void cargarImagen() {
        view.storagePermissions();
        view.cargarImagen();

    }

    @Override
    public void cargarPersona() {
        view.cargarPersona();
    }

    @Override
    public PersonEntity getPerson(int id) {
        return db.recuperarPersona(id);
    }
}
