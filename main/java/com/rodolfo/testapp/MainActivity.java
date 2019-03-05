package com.rodolfo.testapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.imagenGaleriaButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imaGaleIntent = new Intent(MainActivity.this, ImagenGaleriaActivity.class);
                startActivity(imaGaleIntent);
            }
        });

        Button button2 = findViewById(R.id.datosWifiButton);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent datosWifiIntent = new Intent(MainActivity.this, DatosWifiActivity.class);
                startActivity(datosWifiIntent);
            }
        });

        Button button3 = findViewById(R.id.wifiActualButton);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent wifiActualIntent = new Intent(MainActivity.this, WifiActualActivity.class);
                startActivity(wifiActualIntent);
            }
        });

        Button button4 = findViewById(R.id.pdfButton);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pdfIntent = new Intent(MainActivity.this, PdfActivity.class);
                startActivity(pdfIntent);
            }
        });

        Button button5 = findViewById(R.id.puntosButton);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent puntosIntent = new Intent(MainActivity.this, PuntosActivity.class);
                startActivity(puntosIntent);
            }
        });
    }
}
