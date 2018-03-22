package com.example.nguyenthanhxuan.gamebancocaro;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    Bitmap bitmap;
    Canvas canvas;
    Paint paint;
    public static final int ROW_NO=3;
    public static final int COL_NO=3;
    public static final int BITMAP_WIDTH=300;
    public static final int BITMAP_HEIGHT=300;
    ChessBoard chessBoard;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageView);

        bitmap = Bitmap.createBitmap(BITMAP_WIDTH,BITMAP_HEIGHT, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        paint = new Paint();
        paint.setARGB(255,255,0,0);
        paint.setStrokeWidth(2);
        chessBoard = new ChessBoard(MainActivity.this, canvas, paint,BITMAP_WIDTH,BITMAP_HEIGHT, COL_NO,ROW_NO);
        chessBoard.init();
        chessBoard.drawcheckboard();
        imageView.setImageBitmap(bitmap);

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return chessBoard.onTouch(v,event);
            }
        });

    }
}

