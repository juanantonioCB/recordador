package com.juanantonio.recordador.interfaces;

import com.juanantonio.recordador.model.PersonEntity;

import java.util.List;

public interface FormularioInterface {
    public interface View{
        void cargarImagen();
        void savePerson();
        void storagePermissions();
        void addProvince();
        void cargarProvincias(List<String> provinces);
        void cargarPersona();
        void setLayout();
        void showDatePicker();
    }
    public interface Presenter{
        void cargarProvincias();
        void addProvince();
        void savePerson();
        void deletePerson(int idPersona);
        void insertPerson(PersonEntity p);
        void updatePerson(PersonEntity p);
        void showDatePickerDialog();
        void cargarImagen();
        void cargarPersona();
        PersonEntity getPerson(int id);
    }
}
