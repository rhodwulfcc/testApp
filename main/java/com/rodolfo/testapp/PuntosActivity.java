package com.rodolfo.testapp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;

public class PuntosActivity extends AppCompatActivity {

    int marcador = 0;
    float posX;
    float posY;
    Bitmap bitmap;
    Canvas myCanvas;
    Paint myPaint;
    Bitmap myBitmap;
    ImageView view;
    int currentColor = R.color.colorRojo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntos);

        ImageView transparentView = findViewById(R.id.transparentView);
        transparentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    posX = event.getX();
                    posY = event.getY();
                    ImageView view = (ImageView)findViewById(R.id.transparentView);
                    int widthView = view.getWidth();
                    int heightView = view.getHeight();
                    Bitmap myBitmap = Bitmap.createBitmap(widthView, heightView, Bitmap.Config.ARGB_8888);
                    Canvas myCanvas = new Canvas(myBitmap);
                    Paint myPaint = new Paint();
                    myPaint.setColor(getResources().getColor(currentColor));

                    BitmapDrawable bitmapDrawable = (BitmapDrawable) view.getDrawable();
                    if (bitmapDrawable != null) {
                        Bitmap bitmap = bitmapDrawable.getBitmap();
                        myCanvas.drawBitmap(bitmap, new Matrix(), null);
                        myCanvas.drawCircle((float) posX, (float) posY, (float) 20, myPaint);
                        view.setImageDrawable(new BitmapDrawable(getResources(), myBitmap));
                    }
                    else {
                        myCanvas.drawCircle((float) posX, (float) posY, (float) 20, myPaint);
                        view.setImageDrawable(new BitmapDrawable(getResources(), myBitmap));
                        marcador = 1;
                    }
                    /*if (marcador == 1) {
                        Bitmap bitmap = ((BitmapDrawable) view.getDrawable()).getBitmap();
                        myCanvas.drawBitmap(bitmap, new Matrix(), null);
                        myCanvas.drawCircle((float) posX, (float) posY, (float) 20, myPaint);
                        view.setImageDrawable(new BitmapDrawable(getResources(), myBitmap));
                    }
                    else {
                        myCanvas.drawCircle((float) posX, (float) posY, (float) 20, myPaint);
                        view.setImageDrawable(new BitmapDrawable(getResources(), myBitmap));
                        marcador = 1;
                    }*/
                }
                return true;
            }
        });
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rojoRadio:
                if (checked)
                    currentColor = R.color.colorRojo;
                    break;
            case R.id.azulRadio:
                if (checked)
                    currentColor = R.color.colorAzul;
                    break;
            case R.id.amarilloRadio:
                if (checked)
                    currentColor = R.color.colorAmarillo;
                break;
        }
    }

}
