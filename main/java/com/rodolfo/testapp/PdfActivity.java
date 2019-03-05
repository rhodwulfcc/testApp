package com.rodolfo.testapp;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PdfActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        Button exportar = (Button)findViewById(R.id.crearButton);
        exportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Iniciando Scan...", Toast.LENGTH_SHORT).show();
                createPdf();
            }
        });
    }

    private void createPdf() {
        // crear un nuevo documento
        PdfDocument document = new PdfDocument();

        // crate a page description
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(598, 843, 1).create();

        // start a page
        PdfDocument.Page page = document.startPage(pageInfo);

        EditText texto1 = (EditText)findViewById(R.id.titulo1Text);
        String texto1a = texto1.getText().toString();

        Canvas canvas = page.getCanvas();
        Paint text1Paint = new Paint();
        text1Paint.setColor(getResources().getColor(R.color.colorBlackText));
        canvas.drawText(texto1a, 100, 100, text1Paint);
        // finish the page
        document.finishPage(page);

        EditText texto2 = (EditText)findViewById(R.id.titulo2Text);
        String texto2a = texto2.getText().toString();

        //start second page
        pageInfo = new PdfDocument.PageInfo.Builder(598, 843, 2).create();
        page = document.startPage(pageInfo);
        canvas = page.getCanvas();
        Paint text2Paint = new Paint();
        text2Paint.setColor(getResources().getColor(R.color.colorIndigoText));
        canvas.drawText(texto2a, 100, 100, text2Paint);
        // finish the page
        document.finishPage(page);

        // write the document content
        String targetPdf = Environment.getExternalStorageDirectory().getPath() + "/test1.pdf";
        //String targetPdf = "/sdcard/test.pdf";
        File filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));
            Toast.makeText(this, "Done", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(),
                    Toast.LENGTH_LONG).show();
        }

        // close the document
        document.close();
    }

}
