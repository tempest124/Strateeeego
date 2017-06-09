package com.example.csaper6.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageButton;

public class Space {

    private int x;
    private int y;
    private Piece piece;
    private ImageButton button;
    private Drawable background;

    public Space(int x, int y, Piece piece, float ratio, Context context) {
        this.x = x;
        this.y = y;
        this.piece = piece;
        generateBackground(ratio, context);
    }

    private void generateBackground(float ratio, Context context) {
        Bitmap bitmap = Bitmap.createBitmap(50, (int)(50 * ratio), Bitmap.Config.ARGB_4444);

        // draw into bitmap
        for (int x = 0; x < bitmap.getWidth(); x++) {
            for (int y = 0; y < bitmap.getHeight(); y++) {
                float noise = PerlinNoise.noise((x+this.x*bitmap.getWidth())/42.0f, (y+this.y*bitmap.getHeight())/42.0f);
                int red = (int) (noise*255) , green =(int) (noise*255), blue = (int) (noise*255);



                if((this.y<6 && this.y>3) && ((this.x>1 && this.x<4) || (this.x>5 && this.x<8)))
                {
                    if(blue>100)
                    {
                        red=green/4;
                        green=0;
                    }
                    else
                    {
                        red=green/4;
                        blue=0;
                    }
                }
                else {
                    if (blue > 220) {
                        green = 0;
                        red = 0;
                    }
                    else
                    {
                        green += 50;
                        green = Math.min(green, 255);
                        green = Math.max(green, 150);
                        red=green/2;
                        blue=green/3;
                    }
                }


                int color = Color.argb(255, red, green, blue);
                bitmap.setPixel(x, y, color);
            }
        }

        background = new BitmapDrawable(context.getResources(), bitmap);
//        bitmap.recycle();
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public Drawable getBackground() {
        return background;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public ImageButton getButton() { return this.button; }

    public void setButton(ImageButton button) { this.button = button; }


}
