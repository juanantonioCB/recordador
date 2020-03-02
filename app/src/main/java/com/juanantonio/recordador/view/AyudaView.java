package com.juanantonio.recordador.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.juanantonio.recordador.R;
import com.juanantonio.recordador.interfaces.AyudaInterface;
import com.juanantonio.recordador.presenter.AyudaPresenter;

public class AyudaView extends AppCompatActivity implements AyudaInterface.View {
    AyudaPresenter presenter;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.webView = findViewById(R.id.webView);
        this.presenter = new AyudaPresenter(this);

        System.out.println(getIntent().getStringExtra("desde"));
        presenter.cargarAyuda(getIntent().getStringExtra("desde"));
    }

    @Override
    public void cargarAyuda(String desde) {
        System.out.println(desde);
        final WebSettings ajustesVisorWeb = webView.getSettings();
        ajustesVisorWeb.setJavaScriptEnabled(true);
        switch (desde) {
            case "listado":
                webView.loadUrl("https://juanantoniocb.github.io/recordador/listado-ayuda.html");
                break;
            case "formulario":
                webView.loadUrl("https://juanantoniocb.github.io/recordador/formulario.html");
                break;
            case "busqueda":
                webView.loadUrl("https://juanantoniocb.github.io/recordador/busqueda.html");
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
