package com.example.csaper6.myapplication;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

public class Board {

    private int width;
    private int height;

    private Space[][] spaces;

    public Board(Context context, ViewGroup layout, int width, int height) {
        this.height = height;
        this.width = width;
        this.spaces = new Space[width][height];

        final Drawable background = context.getResources().getDrawable(R.drawable.mudkip);
        int buttonWidth = layout.getMeasuredWidth() / width;
        int buttonHeight = layout.getMeasuredHeight() / height + 1;

        for(int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {

                ImageButton button = new ImageButton(context);
                button.setPadding(0,0,0,0);
                button.setScaleType(ImageView.ScaleType.FIT_CENTER);

//                button.setBackgroundColor((i + j) % 2 == 0 ? Color.WHITE : Color.BLACK);
//                button.setTextColor((i + j) % 2 == 0 ? Color.BLACK : Color.WHITE);
//                button.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(buttonWidth, buttonHeight);
                params.setMargins(buttonWidth*i, buttonHeight*j, 0, 0);
                button.setLayoutParams(params);


                spaces[i][j] = new Space(i, j, null, (float)buttonHeight/buttonWidth, context);
                spaces[i][j].setButton(button);

                button.setBackground(spaces[i][j].getBackground());


                layout.addView(button);
            }
        }
    }

    public void setSpaceAt(int x, int y, Space space) {
        if(!inBounds(x, y))
            throw new IllegalArgumentException("Not in da boundes " + x + " " + y);

        spaces[x][y] = space;
    }

    private boolean inBounds(int x, int y){
        return x<width && x>=0 && y>=0 && y<height;
    }
    public Space[][] getSpaces(){return spaces;}
}
