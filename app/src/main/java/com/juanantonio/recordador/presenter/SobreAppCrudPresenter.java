package com.juanantonio.recordador.presenter;
import com.juanantonio.recordador.view.SobreAppCrud;
public class SobreAppCrudPresenter {
    SobreAppCrud view;
    public SobreAppCrudPresenter(SobreAppCrud view){
        this.view=view;
        view.setTitle("Sobre AppCrud");
    }
}
