package com.example.nguyenthanhxuan.gamebancocaro;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nguyen Thanh Xuan on 3/21/2018.
 */

public class ChessBoard {

    Context context;
    Canvas canvas;
    Paint paint;
    boolean isEnable;

    int winCondition= 3;

    int bitmap_width,bitmap_height, col_no, row_no;

    List<Line> linenList;
    int [][] state;

    int player;

    int centerrec =50;

    public ChessBoard(Context context, Canvas canvas, Paint paint, int bitmap_width, int bitmap_height, int col_no, int row_no) {
        this.context = context;
        this.canvas = canvas;
        this.paint = paint;
        this.bitmap_width = bitmap_width;
        this.bitmap_height = bitmap_height;
        this.col_no = col_no;
        this.row_no = row_no;
    }

    public void init (){


        linenList = new ArrayList<Line>();
        isEnable=true;

        for (int i = 0; i <= row_no ; i++) {

            if (i==0){
                linenList.add(new Line(new Point(0,i*(bitmap_height/row_no + (int) paint.getStrokeWidth()/2)),
                        new Point(bitmap_width, i*(bitmap_height/row_no+(int) paint.getStrokeWidth()/2))));
            }

            if ( i==row_no){
                linenList.add(new Line(new Point(0,i*(bitmap_height/row_no)-(int) paint.getStrokeWidth()/2),
                        new Point(bitmap_width, i*(bitmap_height/row_no)-(int) paint.getStrokeWidth()/2)));
            }

            else linenList.add(new Line(new Point(0,i*(bitmap_height/row_no)),
                    new Point(bitmap_width, i*(bitmap_height/row_no))));
        }

        for (int i = 0; i <= col_no ; i++) {
            if (i==0){
                linenList.add(new Line(new Point(i*(bitmap_width/col_no) + (int) paint.getStrokeWidth()/2,0  + (int) paint.getStrokeWidth()/2),
                        new Point(i*(bitmap_width/col_no) + (int) paint.getStrokeWidth()/2 ,bitmap_height + (int) paint.getStrokeWidth()/2)));
            }

            if (i==col_no){
                linenList.add(new Line(new Point(i*(bitmap_width/col_no) - (int) paint.getStrokeWidth()/2,0  + (int) paint.getStrokeWidth()/2),
                        new Point(i*(bitmap_width/col_no) - (int) paint.getStrokeWidth()/2 ,bitmap_height + (int) paint.getStrokeWidth()/2)));
            }

            linenList.add(new Line(new Point(i*(bitmap_width/col_no),0),
                    new Point(i*(bitmap_width/col_no)  ,bitmap_height)));
        }
        Log.d("size",linenList.size() +"");
        player=0;
        state = new int[row_no][col_no];
        for (int i = 0; i <row_no ; i++) {
            for (int j = 0; j <col_no ; j++) {
                state[i][j]=-1;
            }
        }
    }

    public void drawcheckboard(){

        Line line;
        for (int i = 0; i <linenList.size() ; i++) {
            line = linenList.get(i);


            canvas.drawLine(line.getStartP().getX(),line.getStartP().getY(), line.getEndP().getX(), line.getEndP().getY(),
                    paint);
        }
    }

    public boolean onTouch (View view, MotionEvent event){
        if (isEnable==false) return false;
        if( event.getAction() == MotionEvent.ACTION_DOWN){
            int x= (int) event.getX();
            int y= (int)event.getY();

            int r= view.getWidth();//rộng lưới
            int h = view.getHeight();//cao lưới

//        int r = bitmap_width;
//        int h = bitmap_height;

            int r1 = r/col_no;//rộng 1 ô
            int h1 = h/row_no;//cao 1 ô

            Log.d("rong", x/r1 +" - "+ y/h1);

            if (state[y/h1][x/r1]== -1){        //vị trí ô vừa đánh nếu =-1
                state[y/h1][x/r1]= player;

                Point center = new Point((int) ((x/r1 +0.5 )*100), (int) ((y/h1+0.5)*100));

                Point leftTop = new Point(center.getX() - centerrec/2, center.getY() - centerrec/2);
                Point rightBottom = new Point(center.getX()+ centerrec/2, center.getY()+centerrec/2);

                Bitmap subBitmap=null;
                if (player==0) {
                    subBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.cross);
                }
                if (player==1) {
                    subBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.tick);
                }
                if (subBitmap!=null)
                    canvas.drawBitmap(subBitmap,new Rect(0,0, subBitmap.getWidth(),subBitmap.getHeight()),
                            new Rect(leftTop.getX(),leftTop.getY(),rightBottom.getX(), rightBottom.getY()),paint);

                view.invalidate();
            }

            if(isWin(y/h1,x/r1)) {
                Toast.makeText(context,player+" wins",Toast.LENGTH_SHORT).show();
                isEnable=false;
            }
            player = (player+1)%2;
        }


        return true;
    }

    public boolean isWin(int row, int col){
        int rowTemp,colTemp,count=1;
        rowTemp=row;
        while (true){
            rowTemp--;

            if (rowTemp>=0 && state[rowTemp][col]==player) {
                count++;
            }
            else{
                break;
            }

        }
        rowTemp=row;
        while (true){
            rowTemp++;

            if (rowTemp<row_no && state[rowTemp][col]==player) {
                count++;
            }
            else{
                break;
            }
        }
        if (count==winCondition)    return true;
        colTemp=col;
        count=1;
        while (true){
            colTemp--;
            if (colTemp>=0 && state[row][colTemp]==player)  count++;
            else break;
        }
        colTemp=col;
        while (true){
            colTemp++;
            if (colTemp<col_no && state[row][colTemp]==player)  count++;
            else break;
        }
        if (count==winCondition)    return true;

        colTemp=col;
        rowTemp=row;
        count=1;
        while (true){
            colTemp--;
            rowTemp--;
            if (colTemp>=0&&rowTemp>=0&& state[rowTemp][colTemp]==player)   count++;
            else break;
        }
        colTemp=col;
        rowTemp=row;
        while (true){
            colTemp++;
            rowTemp++;
            if (colTemp<col_no&&rowTemp<row_no&& state[rowTemp][colTemp]==player)   count++;
            else break;
        }
        if (count==winCondition)    return true;
        colTemp=col;
        rowTemp=row;
        count=1;
        while (true){
            colTemp++;
            rowTemp--;
            if (colTemp<col_no&&rowTemp>=0&& state[rowTemp][colTemp]==player)   count++;
            else break;
        }
        colTemp=col;
        rowTemp=row;
        while (true){
            colTemp--;
            rowTemp++;
            if (colTemp>=0&&rowTemp<row_no&& state[rowTemp][colTemp]==player)   count++;
            else break;
        }
        if (count==winCondition)    return true;
        return false;
    }

}

