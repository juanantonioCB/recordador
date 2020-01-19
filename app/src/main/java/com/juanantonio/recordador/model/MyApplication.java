package com.juanantonio.recordador.model;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

public class MyApplication extends Application {
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("llamar");
        sContext = getApplicationContext();
    }


    public static Context getContext() {
        System.out.println("contexto  " +sContext);

        return sContext;
    }
}
