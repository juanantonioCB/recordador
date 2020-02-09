package com.juanantonio.recordador.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.juanantonio.recordador.R;

public class Logo extends AppCompatActivity {
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
        if(getResources().getConfiguration().orientation==1){
            setContentView(R.layout.main);
        }else{
            setContentView(R.layout.main_landcape);
        }

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().hide();
        new Handler().postDelayed(new Runnable(){
            public void run(){
                Intent intent = new Intent(Logo.this, ListadoView.class);
                startActivity(intent);
                
            };
        }, 1000);
    }


}
