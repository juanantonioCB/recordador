package com.juanantonio.recordador.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.juanantonio.recordador.R;
import com.juanantonio.recordador.presenter.SobreAppCrudPresenter;

public class SobreAppCrud extends AppCompatActivity {

    SobreAppCrudPresenter presenter;
    @Override
    public void onStart(){
        super.onStart();
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onStop(){
        super.onStop();
    }

    @Override
    public void onRestart(){
        super.onRestart();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre_app_crud);
        presenter=new SobreAppCrudPresenter(this);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SobreAppCrud.this, ListadoView.class);
        finish();
        startActivity(intent);
    }
}
